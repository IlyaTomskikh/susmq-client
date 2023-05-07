import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class ClientConsumer extends Thread implements Runnable {
    private final Socket socket;
    private final DataOutputStream dos;
    private final DataInputStream dis;
    private String name = null;
    private final AtomicBoolean isConnected = new AtomicBoolean();
    private final Logger logger = Logger.getLogger(Thread.currentThread().getName());

    public ClientConsumer(Socket socket) {
        this.socket = socket;
        try {
            this.dos = new DataOutputStream(socket.getOutputStream());
            this.dis = new DataInputStream(socket.getInputStream());
            this.isConnected.set(true);
        } catch (IOException e) {
            this.isConnected.set(false);
            throw new RuntimeException(e);
        }
    }

    private ClientConsumer() {
        this.socket = null;
        this.dos = null;
        this.dis = null;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (this.dis.read() > -1) logger.info(this.dis.readUTF());
                Thread.sleep(10);
            }
        } catch (IOException | InterruptedException e) {
            this.isConnected.set(false);
            throw new RuntimeException(e);
        }
//        this.isConnected.set(false);
    }

    public boolean isConnected() {
        return isConnected.get();
    }
}
