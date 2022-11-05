import java.net.*;
import java.io.*;

public class Client
{
    // initialize socket and input output streams
    private Socket socket            = null;
    private DataInputStream  input   = null;
    private DataOutputStream out     = null;
    private DataInputStream serverMessagesStream = null;

    // constructor to put ip address and port
    public Client(String address, int port)
    {
        // establish a connection
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // takes input from terminal
            input  = new DataInputStream(System.in);

            // sends output to the socket
            out    = new DataOutputStream(socket.getOutputStream());
            serverMessagesStream = new DataInputStream(socket.getInputStream());

            Thread readServerMessagesThread = new Thread( () -> {
                String serverMessage = "";
                while (true) {
                    try {
                        serverMessage = serverMessagesStream.readUTF();
                        System.out.println(serverMessage);
                    } catch (IOException i) {
                        System.out.println(i);
                    }
                }
            });
            readServerMessagesThread.start();
//            Thread readCommandsThread = new Thread( () -> {
//                String line = "";
//                while (!line.equals("Over")) {
//                    try {
//                        line = input.readLine();
//                        out.writeUTF(line);
//                    } catch (IOException i) {
//                        System.out.println(i);
//                    }
//                }
//            });
//            readCommandsThread.start();
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }

        // close the connection
        try
        {
            input.close();
            out.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public static void main(String args[])
    {
        Client client = new Client("127.0.0.1", 5000);
    }
}