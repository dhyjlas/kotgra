package com.example.kotgra

import com.example.kotgra.equipment.EquipmentServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotgraApplication

fun main(args: Array<String>) {
    runApplication<KotgraApplication>(*args)

    EquipmentServer(8081).start()
}
