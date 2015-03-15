/*
 * ChatClientImpl.java
 * @author Brayan Melroni
 */

package guiclient;

import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;


public class ChatClientImpl implements ChatClient
{
   ChatRoom room;
   GUIChatFrame frame;
   
   public ChatClientImpl(ChatRoom room)
   {
      this.room = room;
      frame = new GUIChatFrame(room, (ChatClient) this);
      frame.setTitle("Chat Room");
      frame.setVisible(true);
      frame.setAlwaysOnTop(true);
   }
   
   public void output(String newmessage)
   {
      if (frame == null)
         return;
      frame.setOutputText(newmessage);
   }
   
   public void run()
   {
      if (room == null)
         return;
      while (true)
      {
         try
         {
            String newmessage = room.receive();
            if (newmessage != null)
            {
               if (!newmessage.equals(""))
                  output(newmessage);
            }
         }
         catch (Exception e)
         {
            System.out.println("Communication problem");
         }
      }
   }
}
