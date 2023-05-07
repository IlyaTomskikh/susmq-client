import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class ClientProducer extends Thread implements Runnable {
    private final Socket socket;
    private final DataOutputStream dos;
    private final DataInputStream dis;
    private String name = null;
    private final AtomicBoolean isConnected = new AtomicBoolean();
    private final Logger logger = Logger.getLogger(Thread.currentThread().getName());

    public ClientProducer(Socket socket) {
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

    private ClientProducer() {
        this.socket = null;
        this.dos = null;
        this.dis = null;
    }

    @Override
    public void run() {
        var br = new BufferedReader(new InputStreamReader(System.in));
        try {
//            if (br.ready()) {
                this.logger.info("Enter the client's name");
                this.name = br.readLine();
                this.dos.writeUTF(this.name);
                this.dos.flush();
                this.logger.info("Enter the message");
                var input = br.readLine();
                while (!input.equals("exit")) {
                    Thread.sleep(10);
                    this.dos.writeUTF(this.name + " says " + input);
                    this.dos.flush();
                    this.logger.info("Enter the message");
                    input = br.readLine();
                }
//            }
        } catch (IOException | InterruptedException e) {
            this.isConnected.set(false);
            throw new RuntimeException(e);
        }
        this.isConnected.set(false);
    }

    public boolean isConnected() {
        return isConnected.get();
    }
}
