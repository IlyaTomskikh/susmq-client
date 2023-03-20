import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class Application {
    public static void main(String[] args) {
        var logger = Logger.getLogger("Main client logger");
        var port = 5000;
        var host = "localhost";
        var scanner = new Scanner(System.in);
        String clientsIn, clientName = "null";
        logger.info("Authorize client");
        clientsIn = scanner.nextLine();
        try (var socket = new Socket(host, port)){
            var out = new PrintWriter(socket.getOutputStream(), true);
            var client = new ClientThread(socket);
            client.start();
            while(true) {
                if (clientName.equalsIgnoreCase("null")) {
                    clientName = clientsIn;
                    if (clientsIn.equalsIgnoreCase("exit")) break;
                    out.println(clientName);
                }
                else break;
                var msg = new StringBuilder('[' + clientName + ']' + " message = ");
                logger.info(msg.toString());
                clientsIn = scanner.nextLine();
                if (clientsIn.equalsIgnoreCase("exit")) break;
                msg.append(clientsIn);
                while (socket.isClosed()) {
                    try {
                        socket.connect(new InetSocketAddress(host, port), 0);
                        out.println(clientName);
                        break;
                    } catch (IOException e) {
                        logger.info("OIException");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                logger.info("InterruptedException");
                            }
                    }
                }
                out.println(msg);
            }
        } catch (IOException e) {
            logger.info("I/O exception occurred on connection");
        }
    }
}
