package com.kunghsuspringcloud.eurekaclientone.netty.demo2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//Netty就是一个同时支持多协议的网络通信框架
/**
 * 这里例子中运用了netty对HTTP协议的支持,模拟一个Tomcat应用
 * Created by xuyaokun On 2021/4/10 23:25
 * @desc:
 */
public class CustomTomcat {

    //打开Tomcat源码，全局搜索ServerSocket

    private int port = 8080;

    private static Map<String, CustomServlet> servletMapping = new HashMap<String, CustomServlet>();

    private Properties webProperties = new Properties();

    private void init() {
        //加载web.xml文件,同时初始化 ServletMapping对象
        try {
            /**
             * 无论是随意一个类的main方法启动或者是web方式启动，
             * 这里this.getClass().getResource("/")都是classes目录
             * resources目录下的内容会被移动到classes目录下
             */
            String WEB_INF = this.getClass().getResource("/").getPath();
            FileInputStream fis = new FileInputStream(WEB_INF + "netty/tomcat.properties");
            //加载配置文件
            webProperties.load(fis);
            System.out.println("加载配置文件内容：" + webProperties.toString());
            for (Object k : webProperties.keySet()) {
                String key = k.toString();
                if (key.endsWith(".url")) {
                    String servletName = key.replaceAll("\\.url$", "");
                    String url = webProperties.getProperty(key);
                    String className = webProperties.getProperty(servletName + ".className");
                    CustomServlet obj = (CustomServlet) Class.forName(className).newInstance();
                    servletMapping.put(url, obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取servlet容器
     * @return
     */
    public static Map<String, CustomServlet> getServletMapping(){
        return servletMapping;
    }

    public void start() {
        init();
        //Netty封装了NIO，Reactor模型，Boss，worker
        // Boss线程
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // Worker线程
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // Netty服务
            //ServetBootstrap   ServerSocketChannel
            ServerBootstrap server = new ServerBootstrap();
            // 链路式编程
            server.group(bossGroup, workerGroup)
                    // 主线程处理类,看到这样的写法，底层就是用反射
                    .channel(NioServerSocketChannel.class)
                    // 子线程处理类 , Handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // 客户端初始化处理
                        protected void initChannel(SocketChannel client) throws Exception {
                            // 无锁化串行编程
                            //Netty对HTTP协议的封装，顺序有要求
                            // HttpResponseEncoder 编码器
                            client.pipeline().addLast(new HttpResponseEncoder());
                            // HttpRequestDecoder 解码器
                            client.pipeline().addLast(new HttpRequestDecoder());
                            // 业务逻辑处理
                            client.pipeline().addLast(new CustomTomcatHandler());
                        }

                    })
                    // 针对主线程的配置 分配线程最大数量 128
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 针对子线程的配置 保持长连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // 启动服务器
            ChannelFuture f = server.bind(port).sync();
            System.out.println("CustomTomcat 已启动，监听的端口是：" + port);
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭线程池
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        //启动
        new CustomTomcat().start();
    }
}
