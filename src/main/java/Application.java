import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class Application {
    public static void main(String[] args) {
        var logger = Logger.getLogger("Main client logger");
        var port = 1234;
        var host = "localhost";
        logger.info("Connecting...");
        try (var socket = new Socket(host, port)){
            var client = new ClientThread(socket);
            logger.info("Connected");
            client.start();
            while (client.isConnected()) {
//                Thread.sleep(10);
            }
        } catch (IOException e) {
            logger.info("I/O exception occurred on connection");
        } /*catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
    }
}
