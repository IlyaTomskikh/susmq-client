import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class Application {
    public static void main(String[] args) {
        var logger = Logger.getLogger("Main client logger");
        var host = "localhost";
        logger.info("Connecting...");
        if (args[0].equals("producer")) {
            var port = 1234;
            try (var socket = new Socket(host, port)) {
                var client = new ClientProducer(socket);
                logger.info("Connected");
                client.start();
                while (client.isConnected()) ;
            } catch (IOException e) {
                logger.info("I/O exception occurred on connection");
            }
        } else if (args[0].equals("consumer")) {
            var port = 4321;
            try (var socket = new Socket(host, port)) {
                var client = new ClientConsumer(socket);
                logger.info("Connected");
                client.start();
                while (client.isConnected()) ;
            } catch (IOException e) {
                logger.info("I/O exception occurred on connection");
            }
        }
    }
}
