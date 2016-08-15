import java.net.*;
import java.io.*;
import java.util.*;

public class Server_thread extends Thread {
    private Socket socket;
    public String name;
    //private String message;
    private Server_frame frame;

    private BufferedReader input;
    private PrintWriter output;

    public Server_thread(String name, Socket socket, Server_frame frame) {
        this.name = name;
        this.socket = socket;
        this.frame = frame;

        try{
            input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            output = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()));

        }
        catch(Exception e) {

        }
    }

    public String getThreadName() {
        return this.name;
    }

    public void sendMessage(String name, String message) {
        output.println(name + "\t" + message);
        output.flush();
    }

    public void run() {
        try{
            String receiveMessage = "";

            output.println(name);
            output.flush();

            while(true) {
                receiveMessage = input.readLine();
                StringTokenizer stInput = new StringTokenizer(receiveMessage, "\t");
                frame.receive(stInput.nextToken(), stInput.nextToken(), stInput.nextToken());
            }


        }catch(Exception e) {

        }
    }
}
