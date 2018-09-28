package com.example.kotgra.test

open class Animal(var s: String){
    var i: Int? = 0
    var ss: String? = s

    init{
        this.i = i
        this.ss = s
        System.out.println("1111, $s, $i, $ss")
    }

    constructor(i: Int, s: String): this(s){
        this.i = i
        this.ss = s
        System.out.println("2222, $s, $i, $ss")
    }

    constructor(i: Int): this(){
        this.i = i
        this.ss = s
        System.out.println("3333, $s, $i, $ss")
    }

    constructor(): this(111, "ffffff") {
        this.i = i
        this.ss = s
        System.out.println("4444, $s, $i, $ss")
    }
}

//fun main(args: Array<String>) {
//    var animal = Animal(444)
//    animal.i = 555
//    animal.s = "asdf"
//    System.out.println("${animal.i}, ${animal.s}, ${animal.ss}")
//    System.out.println("${animal.hashCode()}")
//}