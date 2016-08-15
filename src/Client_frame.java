import java.net.*;
import java.io.*;
import java.awt.*; //GUI
import java.awt.event.*; //eventhandling�� ���Ͽ�

import javax.jws.Oneway;
import javax.swing.*; //swing�� javaȮ��
import java.util.*; //token�и�

public class Client_frame extends JFrame implements ActionListener ,WindowListener{ // Frame ���, Action ����
    public JTextArea display; // display�� ��
    private JTextField inputField; // Text�κ�
    private JButton sendButton; // ������ ��ư
    private JPanel bottomPanel; // �ؽ�Ʈ�� ��ư�� ��� Panel
    private JScrollPane topPanel; // ä�� ���� ��� Panel
    public String name;
    private Socket socket = null;
    private Client_thread thread = null;
    public Client_frame() { // ������
        // display = new JTextArea(5, 15); //5�ٿ� �ѹ�, 1�ٿ� 15��
        display = new JTextArea();
        display.setEditable(false);
        inputField = new JTextField(15);
        inputField.addActionListener(this);
        this.addWindowListener(this);

        sendButton = new JButton("Send");
        sendButton.addActionListener(this);
        topPanel = new JScrollPane(display);
        topPanel.setAutoscrolls(true);
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(inputField);
        bottomPanel.add(sendButton);


		/*
		 * JFrame frame = new JFrame(); 
		 * frame.add();...
		 *
		 * this.getContentPane().add(....);
		 */

        this.setLayout(new BorderLayout());
        this.add(topPanel, "Center");
        this.add(bottomPanel, "South");

        this.setSize(300, 300);
        this.setTitle("Client");
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        inputField.setText("/getin 127.0.0.1");
    }

    public synchronized void send(String flag, String name, String message) {
        if(thread != null)
            thread.sendMessage(flag, name, message);
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == inputField || ae.getSource() == sendButton) {
            String message = inputField.getText().trim();


            if(message.equals(""))
                return;

            else if(message.startsWith("/")){
                display.append("��ɾ�" + " : " + message+"\n");
                String str[] ;
                str = message.split(" ");
                if(str[0].equals("/����") && socket == null){
                    try {
                        socket = new Socket(str[1], 5500);
                        thread = new Client_thread(socket, this);
                        thread.start();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        display.append("���� ����] �ٽ� Ȯ���� ������."+"\n");
                    }
                }

                else if(str[0].equals("/�̸�����") || str[0].equals("/�г��Ӻ���")){
                    send("1", name, str[1]);
                    this.name = str[1];
                    display.append("�г��Ӻ��� :"+this.name+"\n");

                }
                else{
                    display.append("���� ��ɾ� �Դϴ�. \n");
                }
            }

            else{
                display.append(name + " : " + message +"\n");
                send("0",name, message);
            }


            inputField.setText("");
        }
    }

    @Override
    public void windowClosed(WindowEvent e) { }
    @Override
    public void windowClosing(WindowEvent e) {
        send("2", name, "��������");
        System.out.println(name+" Ŭ���̾�Ʈ ����");
        dispose();
        setVisible(false);
        System.exit(0);
    }

    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {}
    @Override
    public void windowOpened(WindowEvent e){}



    public static void main(String[] args) {
        //new Client_frame().communication("127.0.0.1");
        new Client_frame();
    }



}
