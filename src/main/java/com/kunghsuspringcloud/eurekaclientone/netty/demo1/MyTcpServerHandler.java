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
        ctx.channel().writeAndFlush(Thread.currentThread().getName() + "accept message "+ msg);
        ctx.close();
    }

    //异常相关处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        System.out.println("get server exception :"+cause.getMessage());
    }
}
