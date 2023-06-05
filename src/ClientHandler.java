import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread{
    private ArrayList<ClientHandler> clients;
    private Socket socket;
    public BufferedReader bufferedReader;
    public PrintWriter printWriter;

    public ClientHandler(Socket socket, ArrayList<ClientHandler> clientHandlers) throws IOException {
        this.socket = socket;
        this.clients = clientHandlers;
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.printWriter =new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run(){
        try{
            String msg;

            while ((msg =bufferedReader.readLine()) != null){
                if (msg.equalsIgnoreCase("Exit")){
                    break;
                }
                for (ClientHandler cl : clients) {
                    cl.printWriter.println(msg);
                    System.out.println(msg);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
                printWriter.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
