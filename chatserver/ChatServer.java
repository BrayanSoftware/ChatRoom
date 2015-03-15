/*
 * ChatServer.java
 *
 * @author Brayan Melroni
 *
 */

package chatserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class ChatServer
{
   private static LinkedList<Socket> clientList = new LinkedList<Socket>();
   
   public ChatServer() throws IOException{
      int port = 8190;
      ServerSocket listener = new ServerSocket(port);
      
      while (true){
         Socket client = listener.accept();
         ChatHandler handler = new ChatHandler(client);
         handler.start();
         
         clientList.add(client);
         
      }
   }
   
   static synchronized void forward(String message, String name) throws IOException{
      // sends the message to every client including the sender
      Socket s;
      PrintWriter p;
      
      for (int i = 0; i < clientList.size(); i++){
         s = (Socket) clientList.get(i);
         p = new PrintWriter(s.getOutputStream(), true);
         p.println(name + ": " + message);
      }
   }
   
   static synchronized void remove(Socket s){
      clientList.remove(s);
   }
   
    public static void main(String[] args){
      try{
         System.out.println("Chat Server is ready.");
         new ChatServer();
      }
      catch(IOException e){
         System.out.println("Received an IO Exception " + e);
      }
   }
}
