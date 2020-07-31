package cn.edu.home.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HomeServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        //因为基于HTTP协议，使用HTTP的编码和解码器
        channelPipeline.addLast(new HttpServerCodec());
        //是以块方式写，添加ChunkedWriteHandler处理器
        channelPipeline.addLast(new ChunkedWriteHandler());
        /**
         *说明
         *1. http数据在传输过程中是分段, HttpObjectAggregator ，就是可以将多个段聚合
         *2.这就就是为什么，当浏览器发送大量数据时，就会发出多次http请求
         */
        channelPipeline.addLast(new HttpObjectAggregator(8192));
        /**
         * 说明
         *1. 对应websocket ，它的数据是以 帧(frame) 形式传递
         *2. 可以看到WebSocketFrame 下面有六个子类
         *3. 浏览器请求时 ws://localhost:8088/hello 表示请求的uri
         *4. WebSocketServerProtocolHandler 核心功能是将 http协议升级为 ws协议 , 保持长连接
         *5. 是通过一个 状态码 101
         */
        channelPipeline.addLast(new WebSocketServerProtocolHandler("/home"));
        //自定义handler，处理业务逻辑
        channelPipeline.addLast(new MyFrameHandler());
    }
}

