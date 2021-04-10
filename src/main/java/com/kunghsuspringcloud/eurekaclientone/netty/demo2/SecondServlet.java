package com.kunghsuspringcloud.eurekaclientone.netty.demo2;

public class SecondServlet extends CustomServlet {
    @Override
    public void doGet(CustomRequest request, CustomResponse response) throws Exception {
        this.doPost(request, response);
    }
    @Override
    public void doPost(CustomRequest request, CustomResponse response) throws Exception {
        response.write("This is Second Serlvet");
    }
}
