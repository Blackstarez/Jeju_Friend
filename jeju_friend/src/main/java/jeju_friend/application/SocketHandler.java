package jeju_friend.application;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

import jeju_friend.Elements.Protocol;

public class SocketHandler {
    public final static String SERVER_IP = "127.0.0.1";
    public final static int port = 12345;
    private Socket socket;

    public SocketHandler() {
        try {
            this.socket = new Socket(SERVER_IP, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Protocol request(Protocol protocol) throws Exception {
        InputStream is = socket.getInputStream();
        Protocol result = new Protocol();
        try {
            socket.getOutputStream().write(protocol.getPacket());   //송신

            // ------------------ 수신 ---------------------------
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
            result.setPacket(buf);


            // 서버와 연결 끊기
            disconnect();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void disconnect()
    {
        Protocol p = new Protocol();
        p.setPacket(Protocol.PT_EXIT, Protocol.PT_EMPTY, Protocol.PT_EMPTY, Protocol.PT_EMPTY);
        try {
            socket.getOutputStream().write(p.getPacket());   
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}