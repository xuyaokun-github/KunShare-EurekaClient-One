package com.kunghsuspringcloud.eurekaclientone.netty.demo2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

//业务处理handler
public class CustomTomcatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        /**
         * HttpRequest这个是Netty提供的对象，表示http协议的请求
         * 假如你使用http协议来访问netty提供的端口，那这里将会匹配成功，进行处理
         */
        if (msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) msg;
            // 转交给我们自己的request实现
            CustomRequest request = new CustomRequest(ctx, req);
            // 转交给我们自己的response实现
            CustomResponse response = new CustomResponse(ctx, req);
            // 实际业务处理
            String url = request.getUrl();
            //根据url映射找处理类
            if (CustomTomcat.getServletMapping().containsKey(url)) {
                CustomTomcat.getServletMapping().get(url).service(request, response);
            } else {
                response.write("404 - Not Found");
            }
        }
    }

    //异常处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    }
}