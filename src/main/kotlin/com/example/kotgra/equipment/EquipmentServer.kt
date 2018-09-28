package com.example.kotgra.equipment


import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.DelimiterBasedFrameDecoder
import io.netty.handler.codec.Delimiters
import io.netty.handler.codec.string.StringDecoder
import io.netty.handler.codec.string.StringEncoder
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.handler.timeout.IdleStateHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.util.concurrent.TimeUnit

class EquipmentServer(private val port: Int) : Thread() {

    override fun run() {
        val boss = NioEventLoopGroup()
        val work = NioEventLoopGroup()

        try {
            val serverBootstrap = ServerBootstrap()
                    .group(boss, work)
                    .channel(NioServerSocketChannel::class.java)
                    .handler(LoggingHandler(LogLevel.INFO))
                    .childHandler(object : ChannelInitializer<SocketChannel>() {
                        override fun initChannel(socketChannel: SocketChannel) {
                            socketChannel.pipeline().addLast("framer", DelimiterBasedFrameDecoder(8192, *Delimiters.lineDelimiter()))
                            socketChannel.pipeline().addLast("decoder", StringDecoder())
                            socketChannel.pipeline().addLast("encoder", StringEncoder())

                            // 进行设置心跳检测
                            socketChannel.pipeline().addLast(IdleStateHandler(180, 0, 0, TimeUnit.SECONDS))
                            // 配置通道处理  来进行业务处理
                            socketChannel.pipeline().addLast(EquipmentServerHandler())
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)

            // 绑定端口，开始接收进来的连接
            val future = serverBootstrap.bind(port).sync()
            log.warn("Equipment Server start listen at {}", port)
            future.channel().closeFuture().sync()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            //关闭EventLoopGroup，释放掉所有资源包括创建的线程
            boss.shutdownGracefully()
            work.shutdownGracefully()
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(EquipmentServer::class.java)
    }
}

