import java.net.*;
import java.io.*;
import java.util.*;

public class Client_thread extends Thread { //��ӹ޴� ��, �����ϴ� �� �ΰ��� �ֽ�
    private Socket socket;
    //private String message;
    private Client_frame frame;

    private BufferedReader input;
    private PrintWriter output;

    public Client_thread(Socket socket, Client_frame frame) {
        this.socket = socket;
        this.frame = frame;

        try{
            input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            output = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            this.frame.display.append("���� ����!!\n");
        }
        catch(Exception e) {

        }

    }

    public void sendMessage(String flag, String name, String message) {
        output.println(flag +"\t" +name + "\t" + message);
        output.flush();
    }

    public void run() {
        try{
            String receiveMessage = "";

            frame.name = input.readLine();

            while(true) {
                //this.frame.display.append("���� ����!!\n");
                receiveMessage = input.readLine();
                StringTokenizer stInput = new StringTokenizer(receiveMessage, "\t");
                frame.display.append(stInput.nextToken()+" : "+stInput.nextToken()+ "\n");
            }


        }catch(Exception e) {

        }
    }
}
