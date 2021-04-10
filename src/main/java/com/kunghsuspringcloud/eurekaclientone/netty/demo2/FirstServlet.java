package com.kunghsuspringcloud.eurekaclientone.netty.demo2;

/**
 * 模拟servlet
 * 访问：http://localhost:8080/firstServlet.do
 *
 * Created by xuyaokun On 2021/4/10 23:35
 * @desc:
 */
public class FirstServlet extends CustomServlet {
    @Override
    public void doGet(CustomRequest request, CustomResponse response) throws Exception {
        this.doPost(request, response);
    }
    @Override
    public void doPost(CustomRequest request, CustomResponse response) throws Exception {
        response.write("This is First Serlvet");
    }
}

