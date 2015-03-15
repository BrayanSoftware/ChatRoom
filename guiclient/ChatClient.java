/*
 * ChatClient.java
 * @author Brayan Melroni
 */


package guiclient;

public interface ChatClient extends Runnable
{
   void output(String message);
}
