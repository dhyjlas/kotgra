package com.example.kotgra.equipment


import io.netty.channel.Channel

class Equipment {
    var id: String? = null
    var sendNum: Int = 0
    var scene: String? = null
    var channel: Channel? = null
    var isSuccess = false

    constructor() {}

    constructor(channel: Channel) {
        this.sendNum = 0
        this.channel = channel
    }
}
