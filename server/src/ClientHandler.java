import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

// Java implementation of Server side
// It contains two classes : Server and ClientHandler
// Save file as Server.java

import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

// ClientHandler class
class ClientHandler extends Thread
{
    DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat fortime = new SimpleDateFormat("hh:mm:ss");
    DataInputStream dis;
    DataOutputStream dos;
    ObjectOutputStream out;
    final Socket s;

    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    public ClientHandler(Socket s_in)
    {
        this.s = s_in;
        this.dis = null;
        this.dos = null;
        try {
            this.dis = new DataInputStream(s_in.getInputStream());
            this.dos = new DataOutputStream(s_in.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        String received;
        String toreturn;
        while (true)
            try {

                // Ask user what he wants
                dos.writeUTF("What do you want?[Date | Time]..\n" +
                        "Type Exit to terminate connection.");
                dos.flush();
                // receive the answer from client
                received = dis.readUTF();

                if (received.equals("Exit")) {
                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }

                // creating Date object
                Date date = new Date();

                // write on output stream based on the
                // answer from the client
                switch (received) {

                    case "Date":
                        toreturn = fordate.format(date);
                        this.dos = new DataOutputStream(s.getOutputStream());
                        dos.writeUTF(toreturn);
                        break;

                    case "Time":
                        toreturn = fortime.format(date);
                        this.dos = new DataOutputStream(s.getOutputStream());
                        dos.writeUTF(toreturn);
                        break;

                    case "o":
                        ObiektTestowy OT = new ObiektTestowy("hello world");

                        System.out.println(OT.getSlowo());
                        this.dos = new DataOutputStream(s.getOutputStream());
                        //out.writeObject(OT.getSlowo());
                        dos.writeUTF(OT.getSlowo());
                        //out.flush();
                        //dos.writeUTF(toreturn);
                        break;

                    default:
                        dos.writeUTF("Invalid input");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();
            //this.out.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}

