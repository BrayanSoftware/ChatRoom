/* 
 * ChatRoom.java
 * @author Brayan Melroni
 */

package guiclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


public class ChatRoom
{
   private Socket socket; // socket to server
   static final int SERVER_PORT_NUMBER = 8190;
   private final static String newline = "\n";
   
   public ChatRoom()throws Exception
   {
      try
      {
         socket = new Socket(InetAddress.getLocalHost(), 8190);
         fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         toServer = new PrintWriter(socket.getOutputStream(),true);
         String name = promptForName();
         send(name);
      }
      catch (Exception e)
      {
         System.out.println("Can't join the chat room" + e);
         throw e;
      }
   }
   
   public void send(String message)
   {
      if (toServer != null)
         toServer.println(message);
   }
   
   public String receive()
   {
      String message = null;
      try
      {
         message = fromServer.readLine();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
      return message;
   }
   
   private PrintWriter toServer;
   private BufferedReader fromServer;
   
   public String promptForName()
   {
      System.out.println("Please enter your user name");
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      String name = "";
      try
      {
         name = in.readLine();
      }
      catch (IOException ex)
      {
         ex.printStackTrace();
      }
      return name;
   }
}
