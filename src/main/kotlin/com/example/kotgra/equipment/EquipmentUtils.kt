package com.example.kotgra.equipment

import java.util.concurrent.ConcurrentHashMap

/**
 * 设备操作工具类
 * 记录并操作所有的在线设备
 */
object EquipmentUtils {
    fun put(id: String, equipment: Equipment) {
        map[id] = equipment
    }

    operator fun get(id: String): Equipment? {
        return map[id]
    }

    fun remove(id: String) {
        map.remove(id)
    }

    /**
     * 记录并获取连接记录
     * @param id
     * @return
     */
    fun getSendNum(id: String): Int {
        val equipment = map[id] ?: return -1

        var sendNum = equipment.sendNum
        equipment.sendNum = ++sendNum
        return sendNum
    }

    /**
     * 通过设备ID获取设备
     * @param scene
     * @return
     */
    fun getEquipmentForScene(scene: String): Equipment? {
        for (key in map.keys) {
            val equipment = map[key]
            if (scene == equipment!!.scene) {
                return equipment
            }
        }

        return null
    }

    fun getEquipmentById(id: String): Equipment? {
        for (key in map.keys) {
            val equipment = map[key]
            if (id == equipment!!.id) {
                return equipment
            }
        }

        return null
    }

    private val map = ConcurrentHashMap<String, Equipment>()
}
