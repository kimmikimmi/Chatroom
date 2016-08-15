import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server_frame extends JFrame implements ActionListener {
    private JTextArea display; //â���
    private JTextField inputField;
    private JButton sendButton;
    private JPanel bottomPanel;
    private JScrollPane topPanel;

    private ConcurrentLinkedQueue<Course> courseQ;

    private ArrayList<Server_thread> threadList; //Server_threadŬ������ ���ڷ� �޴´�
    private int n = 1; //client�� ID

    public Server_frame() {
        courseQ = new ConcurrentLinkedQueue<Course>(); // scheduling 을 위한 concurrentQueue.
        threadList = new ArrayList<Server_thread>(); //각각의 클라이언트랑 메핑되는 각각의 스레드를 가지고 있는 리스트.

        display = new JTextArea();
        display.setEditable(false);

        inputField = new JTextField(15);
        inputField.addActionListener(this);

        sendButton = new JButton("Send");//�ʱ�ȭ
        sendButton.addActionListener(this);

        topPanel = new JScrollPane(display); //�ٷ� textarea�� ���̱�
        topPanel.setAutoscrolls(true);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(inputField);
        bottomPanel.add(sendButton);



        this.setLayout(new BorderLayout());
        this.add(topPanel, "Center");
        this.add(bottomPanel, "South");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("server");
        this.setSize(300, 300);
        this.setVisible(true);
    }

    public ConcurrentLinkedQueue<Course> getCourseQ() {
        return courseQ;
    }

    public void acceptClient() { //client와 연결.
        try {
            ServerSocket server = new ServerSocket(5500);
            Socket socket = null;

            while(true) {
                socket = server.accept(); //client�� �ö����� ��� ���~
                Server_thread thread = new Server_thread("client" +  n, socket, this);  //���⼭ this�� ������������.
                thread.start();
                threadList.add(thread);
                n = n + 1;
                String newName = threadList.get( threadList.size()-1 ).getThreadName();
                display.append(newName+" is accepted to enter."+"\n");
            }

        }catch(Exception e) {

            System.out.println("!!!!");
            e.printStackTrace();
        }
    }

    public void receive(String flag, String name, String message) { //message�޾����ϱ� ����ϴ� �κ�
        //	display.append("["+flag+"] "+name + " : " + message + "\n");
        if(flag.equals("0")){
            display.append(name + " : " + message + "\n");
            send(name, message); //��� �� ������.
        }
        else if(flag.equals("1")){//�г��� ������û
            for(int i = 0 ; i < threadList.size() ; i++) {
                if(threadList.get(i).name.equals(name)) { //
                    send(name, "�г��Ӻ��� -> "+ message);
                    display.append(name+": �г��Ӻ��� -> "+ message+"\n");
                    threadList.get(i).name = message;

                    break;
                }
            }
        }
        else if(flag.equals("2")){//��������
            for(int i = 0 ; i < threadList.size() ; i++) {
                if(threadList.get(i).name.equals(name)) { //
                    threadList.remove(i);
                    display.append(name+" ���� ������ ���� �Ͽ����ϴ�.\n");
                    send(name, name+" ���� ������ ���� �Ͽ����ϴ�.");
                    break;
                }
            }
        }

    }

    public synchronized void send(String name, String message) { //자기자신이 아닌 다른 모든 스레드에게 message 를 보낸다.
        for(int i = 0 ; i < threadList.size() ; i++) {
            if(!threadList.get(i).getThreadName().equals(name)) {
                threadList.get(i).sendMessage(name, message);
            }
        }

    }

    public void actionPerformed(ActionEvent ae){ // text를 입력하고 버튼을 누르면 메세지가 넘어가는ㄱ ㅘ정.

        if(ae.getSource() == inputField || ae.getSource() == sendButton) {
            String message = inputField.getText().trim();
            if(message.equals("")) return;

            send("Server", message);
            display.append("Server" + " : " + message + "\n");
            inputField.setText("");
        }
    }

    public static void main(String[] args) {
        new Server_frame().acceptClient();
    }
}
