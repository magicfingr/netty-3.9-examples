package example;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.*;

/**
 * Created by zxt on 2014/4/28.
 */
public class DiscardServerHandler extends SimpleChannelHandler {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        super.messageReceived(ctx, e);
//        System.out.println("attachment: " + e.getMessage());/
        ChannelBuffer buffer = (ChannelBuffer) e.getMessage();
        while(buffer.readable()){
            char c = (char) buffer.readByte();
            if('.' == c)
                return;
            System.out.println(c);
            System.out.flush();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        super.exceptionCaught(ctx, e);
        Channel channel = e.getChannel();
        channel.close();
    }
}
