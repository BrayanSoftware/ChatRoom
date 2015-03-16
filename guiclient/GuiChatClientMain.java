/* GuiChatClientMain.java
 * @author Brayan Melroni
 */

package guiclient;

public class GuiChatClientMain
{
   public static void main(String[] args)
   {
      try
      {
         ChatRoom room2 = new ChatRoom();
         ChatClient client2 = new ChatClientImpl(room2);
         Thread listener2 = new Thread(client2);
         listener2.start();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }
}
