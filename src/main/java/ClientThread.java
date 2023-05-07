import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientThread extends Thread implements Runnable{
    private final Socket socket;
    private final DataOutputStream dos;
    private final DataInputStream dis;
    private String name = null;
    private boolean isConnected = false;
    private final Logger logger = Logger.getLogger(Thread.currentThread().getName());

    public ClientThread(Socket socket) {
        this.socket = socket;
        try {
            this.dos = new DataOutputStream(socket.getOutputStream());
            this.dis = new DataInputStream(socket.getInputStream());
            this.isConnected = true;
        } catch (IOException e) {
            this.isConnected = false;
            throw new RuntimeException(e);
        }
    }

    private ClientThread() {
        this.socket = null;
        this.dos = null;
        this.dis = null;
    }

    @Override
    public void run() {
        try {
        } catch () {
            this.isConnected = false;
        } finally {
        }
    }

    public boolean isConnected() {
        return isConnected;
    }
}
