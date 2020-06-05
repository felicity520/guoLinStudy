package com.ryd.gyy.guolinstudy.testjava

//abstract class Car(val name: String, val modelNum: String) : Cloneable {
//
//    fun cloneCar(): Car {
//        return super.clone() as Car    //或者直接是 clone() as Car
//    }
//
//    abstract fun getProductPlace(): String
//
//    class CarFactory {
//
//        companion object {
//
//            @JvmStatic
//            fun <C : Car> produceCar(clazz: Class<C>): Car? {
//                when (clazz) {
//                    Lynk::class.java -> {
//                        return Lynk("05")
//                    }
//                    VW::class.java -> {
//                        return Lynk("迈腾")
//                    }
//                    BMW::class.java -> {
//                        return Lynk("750i")
//                    }
//                }
//                return null
//            }
//        }
//    }
//}
//
//class Lynk(modelNum: String) : Car("领克", modelNum) {
//
//    override fun getProductPlace(): String = "中国"
//}
//
//class VW(modelNum: String) : Car("打死奥拓", modelNum) {
//
//    override fun getProductPlace(): String = "德国"
//}
//
//class BMW(modelNum: String) : Car("宝骏", modelNum) {
//
//    override fun getProductPlace(): String = "德国"
//}
//
//
//fun main() {
//    println("gyy " + Lynk::class.java)
//    val lynk = Car.CarFactory.produceCar(Lynk::class.java)
//    val bmw = Car.CarFactory.produceCar(BMW::class.java)
//    lynk?.getProductPlace()
//}


//-----------------------浅拷贝
//class Car(val brand: String, val modelNum: String) : Cloneable {
//    fun cloneCar(): Car {
//        return super.clone() as Car    //或者直接是 clone() as Car
//    }
//}
//
//fun main() {
//    val car = Car("大众", "辉腾")
//    val cloneCar = car.cloneCar()
//    println("car      ==> hashCode:${car.hashCode()},brand:${car.brand}")
//    println("cloneCar ==> hashCode:${cloneCar.hashCode()},brand:${cloneCar.brand}")
//
//}



