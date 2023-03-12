package com.uwan.message.net.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.EventExecutorGroup;


public class ServerHandler extends ChannelInboundHandlerAdapter {


    /**
     * 当我们的通道被激活的时候触发的监听
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("--------服务端通道激活---------");
    }

    /**
     * 当我们通道里有数据进行读取的时候触发的监听
     *
     * @param ctx netty服务上下文
     * @param msg 实际传输的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            // NIO 通信 （传输的数据是什么？ ---------> Buffer 对象）
            ByteBuf buf = (ByteBuf) msg;
            //定义byte数组
            byte[] req = new byte[buf.readableBytes()];
            // 从缓冲区获取数据到 req
            buf.readBytes(req);
            //读取到的数据转换为字符串
            String body = new String(req, "utf-8");
            System.out.println("服务端读取到数据：" + body);

            //响应给客户端的数据
            ctx.writeAndFlush(Unpooled.copiedBuffer("netty server response data".getBytes()));
            // 添加 addListener 可以触发关闭通道监听事件（客户端短连接场景使用）
            // .addListener(ChannelFutureListener.CLOSE);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //释放数据
            ReferenceCountUtil.release(msg);
        }

    }

    /**
     * 当我们读取完成数据的时候触发的监听
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("--------服务端数据读取完毕---------");
    }

    /**
     * 当我们读取数据异常的时候触发的监听
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("--------服务端数据读取异常---------");
        ctx.close();
    }
}
