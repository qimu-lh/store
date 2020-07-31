package cn.edu.home.netty;

import cn.edu.home.entity.User;
import cn.edu.home.service.IUserService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

//这里TextWebSocketFrame类型，表示一个文本帧(frame)
public class MyFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        System.out.println("服务器收到消息 " + textWebSocketFrame.text());
        //将数据装入User实体类中

        if ("建立连接".equals(textWebSocketFrame.text())){
            //模拟室内温度
            new Timer().schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            int tem=new Random().nextInt(40); //产生0~40之间的数
                            channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame(tem+""));
                        }
                    }
                    , 1000, 1000);
        }else{
            Integer uid = Integer.parseInt(textWebSocketFrame.text().substring(textWebSocketFrame.text().indexOf("X") + 1, textWebSocketFrame.text().indexOf("Y")));
            String username = textWebSocketFrame.text().substring(textWebSocketFrame.text().indexOf("Y") + 1, textWebSocketFrame.text().indexOf("A"));
            User user = new User();
            user.setUser(textWebSocketFrame.text());
            System.out.println(user.toString());
            /*
             * 将数据存入数据库
             * IUserService为我想要获取的service层中的类
             */
            IUserService userService = (IUserService) MyFrameHandler.getBean(IUserService.class);
            userService.changeInfo(uid, username, user);

            //根据指令做相应处理
            //回复消息
            final Integer[] airTemperature = {user.getAirTemperature()};
            int humidity = 66;
            System.out.println("空调温度："+airTemperature[0]);
            if ("调温".equals(user.getAirMode())) {
                channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame(airTemperature[0] + ""));
            }
            if ("调湿".equals(user.getAirMode())) {
                channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame(humidity + ""));
            }
        }


    }

    //当web客户端连接后， 触发方法
    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        //id 表示唯一的值，LongText 是唯一的 ShortText 不是唯一
        System.out.println("handlerAdded 被调用" + channelHandlerContext.channel().id().asLongText());
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {

        System.out.println("handlerRemoved 被调用" + channelHandlerContext.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) throws Exception {
        System.out.println("异常发生 " + cause.getMessage());
        channelHandlerContext.close(); //关闭连接
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (MyFrameHandler.applicationContext == null) {
            MyFrameHandler.applicationContext = applicationContext;
            System.out.println(
                    "========ApplicationContext配置成功,在普通类可以通过调用ToolSpring.getAppContext()获取applicationContext对象,applicationContext="
                            + applicationContext + "========");
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

}

