package jeju_friend_server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import jeju_friend_server.Elements.*;


public class server {
    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSock = new ServerSocket(PORT);
        Socket sock = null;
        DBmanager Database = new DBmanager();
        APIManager apiManager = new APIManager();
        Database.startDB();
        System.out.println("DB연결 완료");
        try {
            while(true)
            {
                sock = serverSock.accept();
                System.out.println("["+sock.getInetAddress()+"] : 접속");
                new SocketManager(sock,Database,apiManager).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            serverSock.close();
        }
        
    }
}



//실제 클라이언트와 연결되어 통신을 수행하고, 요청을 처리하는 클래스
class SocketManager extends Thread
{
    DBmanager db;
    Socket sock;
    APIManager api;
    public SocketManager(Socket sock, DBmanager db, APIManager api)
    {
        this.sock = sock;
        this.db = db;
        this.api = api;
    }

    @Override
    public void run()
    {
        byte protocolType;
        byte protocolCode;
        byte protocolCodeEx;
        byte protocolUser;
        boolean isExit = false;
        try {
            OutputStream os = sock.getOutputStream();
            InputStream is = sock.getInputStream();
            Protocol protocol = new Protocol();

            while(true)
            {
                if(isExit)
                    break;    
                
                // buffer 크기 = 10MB
                byte buf[] = new byte[Protocol.LEN_PACKET];

                // 1.수신한 패킷을 buf에 저장.
                int bytesRead = is.read(buf,0,buf.length);  //현재 읽어온 byte크기를 저장.

                // 2. 수신한 패킷의 헤더로부터 body 길이를 추출
                int body_length = Protocol.byteArrayToint(Arrays.copyOfRange(buf,4,Protocol.LEN_HEADER));
                
                int currentRead = bytesRead;

                // 2-2. 패킷이 분할된 경우 패킷을 추가로 수신하여, buf에 추가
                while(currentRead < body_length+Protocol.LEN_HEADER)
                {
                    bytesRead = is.read(buf,currentRead,(body_length+Protocol.LEN_HEADER - currentRead));
                    if(bytesRead >= 0)
                        currentRead += bytesRead;
                }


                // 수신한 패킷의 Type을 추출
                protocolType = buf[0];
                switch(protocolType)
                {
                    // 종료 요청 수신 시
                    case Protocol.PT_EXIT:
                        isExit = true;
                        System.out.println("["+sock.getInetAddress()+"] : 종료 요청");
                        break;
                    // 요청
                    case Protocol.PT_REQUEST:
                        protocolCode = buf[1];
                        protocolCodeEx = buf[2];
                        protocolUser = buf[3];
                        // 패킷의 Code에 따른 작업 수행
                        switch(protocolCode)
                        {
                            // 로그인
                            case Protocol.PT_SIGNIN:
                                protocol.setPacket(buf);
                                Login user = Login.toLogin(protocol.getBody());
                                if(db.getLoginResult(user.getID(), user.getPW()))
                                {
                                    // 로그인 성공
                                    protocol.setPacket(Protocol.PT_RESPONSE, Protocol.PT_SIGNIN, Protocol.PT_SUCCESS, Protocol.PT_UNKNOWN);
                                    os.write(protocol.getPacket());
                                    System.out.println("["+sock.getInetAddress()+", "+user.getID()+"] : 로그인 성공");
                                }
                                else
                                {
                                    // 로그인 실패
                                    protocol.setPacket(Protocol.PT_RESPONSE, Protocol.PT_SIGNIN, Protocol.PT_FAIL, Protocol.PT_UNKNOWN);
                                    os.write(protocol.getPacket());
                                    System.out.println("["+sock.getInetAddress()+"] : 로그인 실패");
                                }
                                break;
                            // 사용자 정보
                            case Protocol.PT_USERINFO:
                                switch(protocolCodeEx)
                                {
                                    case Protocol.PT_APPLY:
                                        protocol.setPacket(buf);
                                        UserInfo userInfo = UserInfo.toUser(protocol.getBody());
                                        if(db.userApply(userInfo.getId(), userInfo.getPw(), userInfo.getNickName(), userInfo.getAge(), userInfo.getGender(),userInfo.getInterestArea()))
                                        {
                                            // 회원가입 완료
                                            protocol.setPacket(Protocol.PT_RESPONSE,Protocol.PT_USERINFO,Protocol.PT_SUCCESS,Protocol.PT_UNKNOWN);
                                            os.write(protocol.getPacket());
                                            System.out.println("["+sock.getInetAddress()+"] : 회원가입 성공");
                                        }
                                        else 
                                        {
                                            // 회원가입 정상 등록 실패
                                            protocol.setPacket(Protocol.PT_RESPONSE,Protocol.PT_USERINFO,Protocol.PT_FAIL,Protocol.PT_UNKNOWN);
                                            os.write(protocol.getPacket());
                                            System.out.println("["+sock.getInetAddress()+"] : 회원가입 실패");
                                        }
                                        break;
                                    case Protocol.PT_LOOKUP:
                                        break;
                                    case Protocol.PT_DELETE:
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            // 관광지
                            case Protocol.PT_TOURIST_SPOT:
                                switch(protocolCodeEx)
                                {
                                    case Protocol.PT_APPLY:
                                        break;
                                    case Protocol.PT_LOOKUP:
                                        break;
                                    case Protocol.PT_DELETE:
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            // 식당
                            case Protocol.PT_RESTAURANT:
                                switch(protocolCodeEx)
                                {
                                    case Protocol.PT_APPLY:
                                        break;
                                    case Protocol.PT_LOOKUP:
                                        break;
                                    case Protocol.PT_DELETE:
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            // 날씨
                            case Protocol.PT_WEATHER:
                                switch(protocolCodeEx)
                                {
                                    case Protocol.PT_APPLY:
                                        break;
                                    case Protocol.PT_LOOKUP:
                                        break;
                                    case Protocol.PT_DELETE:
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            // 여행일정
                            case Protocol.PT_TOURPLAN:
                                switch(protocolCodeEx)
                                {
                                    case Protocol.PT_APPLY:
                                        break;
                                    case Protocol.PT_LOOKUP:
                                        break;
                                    case Protocol.PT_DELETE:
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            default:
                                System.out.println("잘못된 패킷 수신");
                                break;
                        }

                }

            }


            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}