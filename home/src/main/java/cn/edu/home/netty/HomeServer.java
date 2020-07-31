package cn.edu.home.netty;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

@Component
public class HomeServer {
    /**
     * 让HomeServer类作为springboot的一个组件进行启动
     * 作为单线程启动，即为单例
     */
    private static class SingletonHomeServer {
        static final HomeServer instance = new HomeServer();
    }

    public static HomeServer getInstance() {
        return SingletonHomeServer.instance;
    }

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private ServerBootstrap serverBootstrap;
    private ChannelFuture channelFuture;

    public HomeServer() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                //给workerGroup赋予初始化器
                .childHandler(new HomeServerInitializer());
    }

    public void start() {
        this.channelFuture = serverBootstrap.bind(8088);
        System.err.println(".....服务器启动了.....");
    }

}

