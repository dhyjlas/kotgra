package com.example.kotgra.equipment


import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import org.slf4j.LoggerFactory

class EquipmentServerHandler : SimpleChannelInboundHandler<String>() {

    /**
     * 建立连接时，返回消息
     * @param ctx
     * @throws Exception
     */
    override fun channelActive(ctx: ChannelHandlerContext) {
        val address = ctx.channel().remoteAddress()
        log.warn("连接的客户端地址:{}", address)
        val successMsg = "Welcome!!!$address\r\n"
        val body = String(successMsg.toByteArray(), Charsets.UTF_8)
        ctx.writeAndFlush(body)

        //加入在线设备
        EquipmentUtils.put(address.toString(), Equipment(ctx.channel()))

        super.channelActive(ctx)
    }

    /**
     * 接收到消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    override fun channelRead0(ctx: ChannelHandlerContext, msg: String) {
        // 收到消息直接打印输出
        val address = ctx.channel().remoteAddress()
        log.info("接受来自设备{}的消息 : {}", address, msg)

        val sendNum = EquipmentUtils.getSendNum(address.toString())
        if (sendNum == -1) {  //检测设备是否在线
            log.warn("未在在线设备列表中找到{}，请尝试重新连接", address)
            ctx.writeAndFlush("unit exception, please reconnect\r\n")
            ctx.close()
        } else {
            val equipment = EquipmentUtils.get(address.toString())
            if (equipment == null) {
                log.warn("未在在线设备列表中找到{}，请尝试重新连接", address)
                ctx.writeAndFlush("unit exception, please reconnect\r\n")
                ctx.close()
            }else {
                if (!equipment.isSuccess) {  //检测是否已经确认连接成功
                    log.warn("首次连接设备{}，确认连接成功", address)
                    equipment.isSuccess = true
                    ctx.writeAndFlush("success\r\n")
                } else if (equipment.scene == null || "" == equipment.scene) {  //检测设备ID是否已经记录
                    val addevice = "D://123"
                    equipment.scene = msg
                    log.warn("返回设备{}二维码地址{}，绑定ID：{}", address, addevice, msg)
                    ctx.writeAndFlush(addevice + "\r\n")
                } else {
                    ctx.writeAndFlush("thredim\r\n")  //心跳返回
                }
            }
        }
    }


    /**
     * 连接中断
     * @param ctx
     */
    override fun channelInactive(ctx: ChannelHandlerContext) {
        val address = ctx.channel().remoteAddress()

        //从在线设备中去除
        EquipmentUtils.remove(address.toString())
        log.warn("与设备{}的连接已中断", address)
    }

    /**
     * 心跳检测
     * @param ctx
     * @param evt
     */
    override fun userEventTriggered(ctx: ChannelHandlerContext, evt: Any) {
        val address = ctx.channel().remoteAddress()

        log.warn("180s未收到设备{}的心跳信息，中断连接", address)
        ctx.writeAndFlush("Connection timeout, disconnected connection")

        //从在线设备中去除
        EquipmentUtils.remove(address.toString())
        ctx.close()
    }

    companion object {
        private val log = LoggerFactory.getLogger(EquipmentServerHandler::class.java)
    }
}
