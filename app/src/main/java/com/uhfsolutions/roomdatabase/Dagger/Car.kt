package com.uhfsolutions.roomdatabase.Dagger



/*
* DEPENDENCY INJECTION
*   injecting services into clients from outside
*
* init{} we are Car class creating engine and tyre itself then it will use them
* but when we supply in constructor then it just simply use them
* */


class Car(engine: Engine,tyre: Tyre) {
//    private var engine:Engine
//    private var tyre:Tyre
//
//    init{
//        engine = Engine()
//        tyre = Tyre()
//    }

    fun drive(){
        println("Car is being drive")
    }
}