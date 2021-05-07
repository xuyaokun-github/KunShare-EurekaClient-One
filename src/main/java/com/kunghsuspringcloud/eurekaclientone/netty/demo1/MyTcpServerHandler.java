package com.kunghsuspringcloud.eurekaclientone.netty.demo1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 服务端的消息处理器
 * Created by xuyaokun On 2021/4/10 18:03
 * @desc:
 */
public class MyTcpServerHandler extends ChannelInboundHandlerAdapter {

    //建⽴连接时，发送⼀条消息
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(Thread.currentThread().getName() + "chanelActive>>>>>>>");
    }

    //业务逻辑处理
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //调用channelActive和channelRead的是同一个线程
        System.out.println(Thread.currentThread().getName() + "server receive message:" + msg);
        ctx.channel().writeAndFlush("server say accept message "+ msg);
        /*
            这里假如不调close会怎样？
            那客户端那边的f.channel().closeFuture().sync()将阻塞住
            channel应该由服务端关还是客户端关呢？
         */
        if(ctx.channel().isOpen() && ((String)msg).contains("再次")){
            //判断已经收到最后一次消息，假如客户端不进行关闭，那就由服务端进行关闭
            ctx.close();
        }
    }

    //异常相关处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        System.out.println("get server exception :"+cause.getMessage());
    }
}
