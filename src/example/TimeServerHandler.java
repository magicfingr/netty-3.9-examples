package example;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;


/**
 * Created by zxt on 2014/4/29.
 * <p/>
 * Time Protocol
 * This protocol provides a site-independent, machine readable date and
 * time.  The Time service sends back to the originating source the time in
 * seconds since midnight on January first 1900.
 *
 * 问题：传回客户端的是乱码
 */
public class TimeServerHandler extends SimpleChannelHandler {
    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.channelConnected(ctx, e);
        Channel channel = e.getChannel();
        ChannelBuffer time = ChannelBuffers.buffer(8);
//        int t = (int) (System.currentTimeMillis() / 1000L + 2208988800L);
        long t = System.currentTimeMillis() / 1000L + 2208988800L;
        System.out.println("time: " + t);
//        time.writeInt(t);
        time.writeLong(t);
        ChannelFuture f = channel.write(time);

        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                Channel channel = future.getChannel();
                channel.close();
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        super.exceptionCaught(ctx, e);
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}
