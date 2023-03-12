package com.uwan.message.net.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * netty 客户端
 * @author lanx
 * @date 2022/3/20
 */
public class Client {

    private static void start() throws InterruptedException {
        //线程工作组
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        //辅助类 帮我我们创建netty服务
        Bootstrap b = new Bootstrap();
        b.group( workerGroup)//绑定两个工作组
                .channel(NioSocketChannel.class)//设置NIO模式

                //初始化绑定服务通道
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        //为通道进行初始化：数据传输过来的时候会进行拦截和执行 (可以有多个拦截器)
                        sc.pipeline().addLast(new ClientHandler());
                    }
                });
        ChannelFuture cf = b.connect("127.0.0.1",8765).syncUninterruptibly();

//        cf.channel().writeAndFlush(Unpooled.copiedBuffer("netty client request data".getBytes()));
        //释放连接
        cf.channel().closeFuture().sync();
        workerGroup.shutdownGracefully();
    }

    public static void main(String[] args) throws InterruptedException {
        Client.start();
    }
}
