package com.NettyHtml;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslContext;

/**
 * Created by DELL on 14-7-18.
 */
public class HttpSnoopServerInitializer extends ChannelInitializer<SocketChannel> {
    private final SslContext sslCtx;
    public HttpSnoopServerInitializer(SslContext sslCtx) {
        this.sslCtx=sslCtx;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline p=socketChannel.pipeline();
        if(sslCtx !=null){
            p.addLast(sslCtx.newHandler(socketChannel.alloc()));
        }
          p.addLast(new HttpRequestDecoder());
          // Uncomment the following line if you don't want to handle HttpChunks.
          //p.addLast(new HttpObjectAggregator(1048576));
          p.addLast(new HttpResponseEncoder());
          // Remove the following line if you don't want automatic content compression.
          //p.addLast(new HttpContentCompressor());
          p.addLast(new HttpSnoopServerHandler());

    }
}
