/*
 * ChatHandler.java
 * @author Brayan Melroni
 */

package chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ChatHandler extends Thread{
/* the ChatHandler class is called from the chat server:
 * one thread for each client coming in to chat
 */
   private BufferedReader in;
   private PrintWriter out;
   private Socket toClient;
   private String name;
   
   ChatHandler(Socket s){
      toClient = s;
   }
   
   private void openStreams()throws IOException{
      in = new BufferedReader(new InputStreamReader(toClient.getInputStream()));
      out = new PrintWriter(toClient.getOutputStream(), true);
   }
   
   @Override
   public void run(){
      try{
         openStreams();
         
         // welcome new client to the Chat Room
         out.println("*** Welcome to the Chat Room ***");
         out.flush();
         out.println("Type BYE to end or press Quit");
         out.flush();
         out.println("What is your name?");
         out.flush();
         
         String name = in.readLine();
         ChatServer.forward(name + " has joined the discussion.", "Chatter");
         
         // read lines and send them off for forwarding
         while (true){
            String s = in.readLine();
            if (s.startsWith("BYE")){
               ChatServer.forward(name + " has left the discussion.", "Chatter");
               break;
            }
            ChatServer.forward(s, name);
         }
         
         ChatServer.remove(toClient);
         toClient.close();
      }
      catch  (Exception e){
         System.out.println("Chatter error: " + e);
      }
   }
}
