package example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by zxt on 2014/4/29.
 */
public class SimpleClient {
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 8123;
    private final static Logger LOGGER = Logger.getLogger("SimpleClient");

    public static void main(String[] args) {
        InetAddress addr = null;
        try {
            addr = InetAddress.getByName(DEFAULT_HOST);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        int port = DEFAULT_PORT;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
                if (port < 0 || port >= 65535) {
                    LOGGER.log(Level.SEVERE, "port must between 0 ~ 65535");
                    return;
                }
            } catch (NumberFormatException e) {
                //use default port
            }
        }
        Socket socket = null;
        try {
            socket = new Socket(addr, port);
            LOGGER.log(Level.INFO, "connected to addr: " + addr + ",  tcp: " + socket);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            String line;
            //read
            while (true) {
                line = in.readLine();
                if (null != line) {
                    System.out.println("remote: " + line);
                    if (line.equals("quit")) break;
                } else break;
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Connection to " + addr + ":" + port + " failed.");
//            e.printStackTrace();
        } finally {
            try {
                if (null != socket)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
