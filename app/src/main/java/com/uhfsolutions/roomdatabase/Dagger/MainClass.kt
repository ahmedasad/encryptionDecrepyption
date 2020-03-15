package com.uhfsolutions.roomdatabase.Dagger

class MainClass() {
    init {
        val engine = Engine(1,1)
        val tyre = Tyre()
        val car = Car(engine,tyre)
    }
}