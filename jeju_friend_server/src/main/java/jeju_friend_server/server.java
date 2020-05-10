package Jeju_Friend_server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import Elements.Protocol;



public class server {
    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSock = new ServerSocket(PORT);
        Socket sock = null;
        DBmanager Database = new DBmanager();
        APIManager apiManager = new APIManager();
        Database.startDB();
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
                    case Protocol.PT_EXIT:
                        isExit = true;
                        System.out.println("["+sock.getInetAddress()+"] : 종료 요청");
                        break;
                    default:
                        protocolCode = buf[1];
                        protocolCodeEx = buf[2];
                        protocolUser = buf[3];
                        





                }

            }

















            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}