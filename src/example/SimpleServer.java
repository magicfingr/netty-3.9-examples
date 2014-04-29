package example;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by zxt on 2014/4/28.
 */
public class SimpleServer {
    public static final int DEFAULT_PORT = 8123;
    public static final String DEFAULT_HANDLER = "example.DiscardServerHandler";
    private static SimpleChannelHandler handler = null;

    public static void main(String[] args) {
        String handlerClassName = DEFAULT_HANDLER;
        if(args.length > 0) {
            handlerClassName = args[0];
        }
        try {
            handler = (SimpleChannelHandler)
                    Class.forName(handlerClassName).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ChannelFactory factory = new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool()
        );
        ServerBootstrap bootstrap = new ServerBootstrap(factory);
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
//                return Channels.pipeline(new DiscardServerHandler());
//                return Channels.pipeline(new EchoServerHandler());
                return Channels.pipeline(new TimeServerHandler());
//                return Channels.pipeline(handler);
            }
        });
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("child.keepAlive", true);

        bootstrap.bind(new InetSocketAddress(DEFAULT_PORT));
        System.out.println("[INFO] server started at port: " + DEFAULT_PORT);
    }

}
