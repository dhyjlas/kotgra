package com.example.kotgra.synctest

class Worker : Thread(){
    override fun run(){
        for(i in 0..10) {
            Sync.add()
            Thread.sleep(1000)
            System.out.println(Sync.ssEntity.num)
        }
    }
}
