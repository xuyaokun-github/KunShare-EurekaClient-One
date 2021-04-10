package com.kunghsuspringcloud.eurekaclientone.netty.demo2;

/**
 * 虽然名字叫servlet,但它并不是原tomcat容器里的servlet
 * 这里是为了好理解，才起名servlet
 *
 * Created by xuyaokun On 2021/4/10 23:35
 * @desc:
 */
public abstract class CustomServlet {

    public void service(CustomRequest request, CustomResponse response) throws Exception{

        //由service方法来决定，是调用doGet或者调用doPost
        if("GET".equalsIgnoreCase(request.getMethod())){
            doGet(request, response);
        }else{
            doPost(request, response);
        }
    }

    public abstract void doGet(CustomRequest request, CustomResponse response) throws Exception;

    public abstract void doPost(CustomRequest request, CustomResponse response) throws Exception;
}