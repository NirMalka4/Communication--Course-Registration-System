package bgu.spl.net.impl.echo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

    public static void main(String[] args) throws IOException {

        String arg = "localhost"; 

        //BufferedReader and BufferedWriter automatically using UTF-8 encoding
        try (Socket sock = new Socket(arg, 7777);
                BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));)
            {
            String line;
            Scanner input = new Scanner(System.in);
            while(true){
                byte[] message = {(byte)0, (byte)4};
                System.out.print(">");
                out.write(input.nextLine());
                System.out.println("sending message to server");
                //out.newLine();
                out.flush();
                System.out.println("awaiting response");
                line = in.readLine();
                System.out.println("message from server: " + line);
            }
        }
    }
}
