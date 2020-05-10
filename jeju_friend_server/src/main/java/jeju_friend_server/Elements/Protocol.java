package Elements;

public class Protocol {
    //type
    public static final byte PT_EXIT = 0;       // 종료
    public static final byte PT_REQUEST = 1;    // 요청
    public static final byte PT_RESPONSE = 2;   // 응답
    public static final byte PT_MESSAGE = 3;    // 메시지
    public static final byte PT_UNDEFINED = 15; // 미정의 

    //Code
    public static final byte PT_SIGNIN = 1;         // 로그인
    public static final byte PT_USERINFO = 2;       // 사용자정보
    public static final byte PT_TOURIST_SPOT = 3;   // 관광지
    public static final byte PT_RESTAURANT = 4;     // 식당 - 관광지와 같은 테이블을 사용하나 구분으로 구분되어 있음.
    public static final byte PT_WEATHER = 5;        // 날씨
    public static final byte PT_TOURPLAN = 6;       // 여행일정
    

    //Code 확장
    public static final byte PT_EMPTY = 0;      // 없음
    public static final byte PT_APPLY = 1;      // 등록
    public static final byte PT_LOOKUP = 2;     // 조회
    public static final byte PT_DELETE = 3;     // 삭제
    public static final byte PT_MODIFY = 4;     // 수정

    // 사용자
    public static final byte PT_UNKNOWN = 0;    // 모름
    public static final byte PT_USER = 1;       // 사용자
    public static final byte PT_ADMIN = 2;      // 관리자

    //헤더 길이
    public static final int LEN_TYPE_FIELD = 1;
    public static final int LEN_CODE_FIELD = 1;
    public static final int LEN_CODE_EXPANSION_FIELD = 1;
    public static final int LEN_USER_FIELD = 1;
    public static final int LEN_BODY_LENGTH_FIELD = 4;
    public static final int LEN_HEADER = 8;
    public static final int LEN_PACKET = 1024*1024*10;


    // 변수 - 헤더 총 8byte로 구성
    protected byte protocolType;
    protected byte protocolCode;
    protected byte protocolCodeExpansion;
    protected byte protocolUser;
    protected int bodyLength;

    byte[] packet;


    // -----------------------------------  함수  ----------------------------------
    public void setPacket(byte protocolType, byte protocolCode, byte protocolCodeEx, byte protocolUser, byte[] buffer)
    {   
        this.packet = null;
        packet = new byte[buffer.length];
        
        byte[] bodyLength = new byte[4];
        bodyLength[0] = buffer[4];
        bodyLength[1] = buffer[5];
        bodyLength[2] = buffer[6];
        bodyLength[3] = buffer[7];

        this.protocolType = protocolType;
        this.protocolCode = protocolCode;
        this.protocolCodeExpansion = protocolCodeEx;
        this.protocolUser = protocolUser;
        this.bodyLength = byteArrayToint(bodyLength);
       
        System.arraycopy(buffer, 0, this.packet, 0, buffer.length);
    }

    public void setPacket(byte[] packet)
    {
        this.packet = null;

        byte[] bodyLength = new byte[4];
        bodyLength[0] = packet[4];
        bodyLength[1] = packet[5];
        bodyLength[2] = packet[6];
        bodyLength[3] = packet[7];

        this.protocolType = packet[0];
        this.protocolCode = packet[1];
        this.protocolCodeExpansion = packet[2];
        this.protocolUser = packet[3];
        this.bodyLength = byteArrayToint(bodyLength);

        System.arraycopy(packet, 0, this.packet, 0, packet.length);
    }








    // ---------------------------------------  기타 함수   --------------------------------------

	// Integer -> Byte[] 로 변환하는 함수
	public static byte[] intToByteArray(int val) {
		byte[] bytearr = new byte[4];
		bytearr[0] = (byte) (val);
		bytearr[1] = (byte) (val >> 8);
		bytearr[2] = (byte) (val >> 16);
		bytearr[3] = (byte) (val >> 24);
		return bytearr;
	}

	// Byte[] -> Integer 로 변환하는 함수
	public static int byteArrayToint(byte[] bytearr) {
		return ((int) (bytearr[0] & 0xff) + (int) ((bytearr[1] & 0xff) << 8) + (int) ((bytearr[2] & 0xff) << 16)
				+ (int) ((bytearr[3] & 0xff) << 24));
	}


}