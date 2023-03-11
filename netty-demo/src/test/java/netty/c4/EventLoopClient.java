package netty.c4;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class EventLoopClient {
    public static void main(String[] args) throws InterruptedException {


        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override // 在连接建立后被调用
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                // 1. 连接服务器
                // 异步非阻塞，main 发起了调用，真正执行连接 connect 是 nio 线程
                .connect(new InetSocketAddress("localhost", 8081));

        // 2.1 使用 sync 方法同步处理结果
/*
        channelFuture.sync(); //阻塞当前线程，直到 nio 线程连接建立处理
//        TimeUnit.SECONDS.sleep(3);
        // 无阻塞向下执行获取 channel
        Channel channel = channelFuture.channel();
        // 向服务器发送数据
        channel.writeAndFlush("1221");
*/

        //2.2 使用 addListener(回调对象) 方法异步处理结果
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            // 在 nio 线程连接建立好之后，会调用 operationComplete
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                Channel channel = channelFuture.channel();
                channel.writeAndFlush("1221");
            }
        });

    }
}
