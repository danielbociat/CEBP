import java.net.*;
import java.io.*;
import java.util.*;

public class Server
{
    //initialize socket and input stream
    private List<Thread> clientSocketThreads = new ArrayList<Thread>();
    private ServerSocket server = null;

    // constructor with port
    public Server(int port)
    {
        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");

            for (;;)
            {
                Socket socket = server.accept();
                Thread newClient = new Thread(() -> handleClient(socket));
                clientSocketThreads.add(newClient);
                newClient.start();
            }

        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
    private static void handleClient(Socket socket)
    {
        try
        {
            System.out.println("Client accepted");
            DataInputStream inputStream;
            // takes input from the client socket
            inputStream = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));

            String line = "";

            // reads message from client until "Over" is sent
            while (!line.equals("Over"))
            {
                try
                {
                    line = inputStream.readUTF();
                    System.out.println(line);

                }
                catch(IOException i)
                {
                    System.out.println(i);
                }
            }
            System.out.println("Closing connection");

            // close connection
            socket.close();
            inputStream.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public static void main(String args[])
    {
        Server server = new Server(5000);
    }

}