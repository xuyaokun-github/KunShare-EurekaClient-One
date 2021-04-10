package com.kunghsuspringcloud.eurekaclientone.netty.demo2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

public class CustomRequest {

    private ChannelHandlerContext ctx;

    private HttpRequest req;

    public CustomRequest(ChannelHandlerContext ctx, HttpRequest req) {
        this.ctx = ctx;
        this.req = req;
    }

    public String getUrl() {
        return req.uri();
    }

    public String getMethod() {
        return req.method().name();
    }
}
