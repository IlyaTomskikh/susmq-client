import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class Application {
    public static void main(String[] args) {
        var logger = Logger.getLogger("Main client logger");
        var host = "localhost";
        logger.info("Connecting...");
        var scanner = new Scanner(System.in);
        if (args[0].equalsIgnoreCase("console"))
            args[0] = scanner.nextLine();
        if (args[0].equals("p")) {
            var port = 1234;
            try (var socket = new Socket(host, port)) {
                var client = new ClientProducer(socket);
                logger.info("Producer connected");
                client.start();
                while (client.isConnected()) ;
            } catch (IOException e) {
                logger.info("I/O exception occurred on connection");
            }
        } else if (args[0].equals("c")) {
            var port = 4321;
            try (var socket = new Socket(host, port)) {
                var client = new ClientConsumer(socket);
                logger.info("Consumer connected");
                client.start();
                while (client.isConnected()) ;
            } catch (IOException e) {
                logger.info("I/O exception occurred on connection");
            }
        }
    }
}
