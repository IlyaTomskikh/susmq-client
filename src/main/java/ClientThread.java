import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientThread extends Thread implements Runnable{
    private final BufferedReader in;
    private final Logger logger = Logger.getLogger(Thread.currentThread().getName());

    public ClientThread(Socket socket) throws IOException {
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (true) {
                var m = this.in.readLine();
                if (m.equalsIgnoreCase("exit")) break;
                this.logger.info(m);
            }
        } catch (IOException e) {
            this.logger.info("I/O exception occurred on logging");
        } finally {
            try {
                this.in.close();
            } catch (IOException e) {
                this.logger.info("I/O exception occurred on closing");
            }
        }
    }
}
