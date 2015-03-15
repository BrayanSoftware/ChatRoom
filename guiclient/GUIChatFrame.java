/*
 * GUIChatFrame.java
 * @author Brayan Melroni
 */

package guiclient;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;
import javax.swing.text.*;

public class GUIChatFrame extends JFrame implements ActionListener
{
   private ChatClient client;
   private ChatRoom room;
   /**
    * Creates a new instance of GUIChatFrame
    */
   public GUIChatFrame(ChatRoom room, ChatClient client)
   {
      this.room = room;
      this.client = client;
      createWindow();
   }
   
   // elements of the GUI
   private JTextPane chatRoomText;   // text pane to display all chatter's messages
   private JTextArea intext;         // text are for adding the current chatter's message
   private JScrollPane chatRoom;     // the chat display is scrollable
   private JButton quitButton;       // leave the chat room
   private JButton sendButton;        // send the message
   
   private void createWindow() throws HeadlessException
   {
      final int CLIENT_WINDOW_WIDTH = 400;
      final int CLIENT_WINDOW_HEIGHT = 450;
      //JFrame frame = new JFrame("M362 Chat Server Demo");
      //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      chatRoomText = new JTextPane();
      chatRoomText.setSize(400, 400);
      JScrollPane chatRoom =
            new JScrollPane(chatRoomText,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
      chatRoomText.setEditable(false);
      intext = new JTextArea(6,30);
      intext.setEditable(true);
      
      createStyles();
      JScrollPane inputPane = new JScrollPane(intext,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      sendButton = new JButton("Send");
      sendButton.addActionListener(this);
      
      quitButton = new JButton("Quit");
      quitButton.addActionListener(this);
      
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new GridLayout(0, 1));
      buttonPanel.add(sendButton);
      buttonPanel.add(quitButton);
      
      JPanel inputArea = new JPanel();
      inputArea.setLayout(new BorderLayout());
      inputArea.add(inputPane, BorderLayout.CENTER);
      inputArea.add(buttonPanel, BorderLayout.EAST);
      Container content = getContentPane();
      content.add(chatRoom, BorderLayout.CENTER);
      content.add(inputArea, BorderLayout.SOUTH);
      setSize(CLIENT_WINDOW_WIDTH, CLIENT_WINDOW_HEIGHT);
      
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
   
   // button event handling
   public void actionPerformed(ActionEvent ae)
   {
      Object buttonClicked = ae.getSource();
      if(buttonClicked.equals(sendButton))
      {
         JTextArea i = getInputArea();
         room.send(i.getText());
         
         i.setText("");
      }
      else if(buttonClicked.equals(quitButton))
      {
         room.send("BYE");
         System.exit(0);
      }
   }
   
   public JTextPane getChatRoom()
   {
      return chatRoomText;
   }
   
   public JTextArea getInputArea()
   {
      return intext;
   }
   
   DefaultStyledDocument doc;
   
   // predefined styles
   StyleContext styles = new StyleContext();
   Style smile, normal, boldstyle;
   void createStyles()
   {
      smile = styles.addStyle("smile", null);
      
      URL url = this.getClass().getClassLoader().getResource("guiclient/smile.jpg");
      StyleConstants.setIcon(smile, new ImageIcon(url));
      
      normal = styles.addStyle("normal", null);
      boldstyle = styles.addStyle("bold", null);
      StyleConstants.setBold(boldstyle, true);
      doc = new DefaultStyledDocument();
   }
   
   void setOutputText(String message)
   {
      try
      {      
         // the following is using JTextPane: for richer formats
         // always add a new line
         doc.insertString(doc.getLength(), "\n", normal);
         
         // emphasize the chatter's name
         if (message.contains(":"))
         {
            String chatter = message.substring(0, message.indexOf(":"));
            doc.insertString(doc.getLength(), chatter, boldstyle);
            message = message.substring(message.indexOf(":"));
         }
         
         // convert smiley symbols into images
         String sn = ":-)";
         
         while (message.contains(sn))
         {
            String pre = message.substring(0, message.indexOf(sn));
            message = message.substring(message.indexOf(sn) + sn.length());
            doc.insertString(doc.getLength(), pre, normal);
            doc.insertString(doc.getLength(), sn, smile);
            System.out.println(pre + sn);        
         }
         // print the remaining message
         if(message.length() > 0)
            doc.insertString(doc.getLength(), message, normal);
         
         getChatRoom().setDocument(doc);
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }
}
