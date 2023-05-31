import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static ArrayList<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket socket;

        try{
            serverSocket = new ServerSocket(3001);
            while (true){
                System.out.println("Waiting for Client....");
                socket = serverSocket.accept();
                System.out.println("New Client Connected....");
                ClientHandler handler = new ClientHandler(socket,clients);
                clients.add(handler);
                handler.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
