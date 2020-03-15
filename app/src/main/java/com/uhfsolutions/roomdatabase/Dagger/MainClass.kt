package com.uhfsolutions.roomdatabase.Dagger

class MainClass() {
    init {
        val engine = Engine()
        val tyre = Tyre()
        val car = Car(engine,tyre)
    }
}