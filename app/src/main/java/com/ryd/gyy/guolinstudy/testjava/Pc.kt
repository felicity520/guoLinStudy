package com.ryd.gyy.guolinstudy.testjava

/***
 * 建造者模式:对象的构造和表示分离,即对象的创建和赋值分开
 * 优点：---------------------------
 * 1、创建和赋值解耦
 * 2、相同的创建过程来得到不同的产品。也就说细节依赖抽象。
 * 3、复杂产品的创建步骤分解在不同的方法中，使得创建过程更加清晰
 * 4、易于拓展，增加新的具体建造者无需修改原有类库的代码，易于拓展，符合“开闭原则“。
 * 缺点：
 * 缺点方面：-----------------------
 * 1、建造者模式所创建的产品一般具有较多的共同点，其组成部分相似；差异大的不适合用建造者模式，因此使用范围有限制
 * 2、如果产品的内部变化复杂，可能会导致需要定义很多具体建造者类来实现这种变化，导致系统变得很庞大。
 * 应用场景：----------------------------------
 * 需要生成的产品对象有复杂的内部结构，这些产品对象具备共性；隔离复杂对象的创建和使用，并使得相同的创建过程可以创建不同的产品
 */

//这是 Product
class Pc {

    private var mName: String? = null
    private var mCpu: String? = null
    private var mRomSize: Int? = null

    private fun setName(name: String) {
        this.mName = name
    }

    private fun setCpu(cpu: String) {
        this.mCpu = cpu
    }

    private fun setRomSize(romSize: Int) {
        this.mRomSize = romSize
    }

    fun getName(): String {
        return mName ?: "没名字"
    }

    fun getCpu(): String {
        return mCpu ?: "没名字"
    }

    fun getRomSize(): Int {
        return mRomSize ?: 0
    }

    fun printInfo() {
        print("笔记本的名字：$mName ,Cpu型号为：$mCpu")
    }

    //这是具体的 Builder
//    class SurfaceBookBuilder : PcBuilder {
//
//        private var pc: Pc
//
//        init {
//            pc = Pc()
//        }
//
//        override fun setName() {
//            pc.setName("Surface Book")
//        }
//
//        override fun setCpu() {
//            pc.setCpu("Intel i7")
//        }
//
//        override fun setRomSize() {
//            pc.setRomSize(512)
//        }
//
//        override fun createPc(): Pc {
//            return pc
//        }
//    }


    //这是具体的 Builder
//    class MacBookBuilder : PcBuilder {
//
//        private var pc: Pc
//
//        init {
//            pc = Pc()
//        }
//
//        override fun setName() {
//            pc.setName("MacBook")
//        }
//
//        override fun setCpu() {
//            pc.setCpu("Intel i7")
//        }
//
//        override fun setRomSize() {
//            pc.setRomSize(1024)
//        }
//
//        override fun createPc(): Pc {
//            return pc
//        }
//    }

    //这是组装类
//    class PcDirector(private val mBuilder: PcBuilder) {
//
//        fun getPc(): Pc {
//            constructPc()
//            return mBuilder.createPc()
//        }
//
//        private fun constructPc() {
//            mBuilder.setName()
//            mBuilder.setCpu()
//            mBuilder.setRomSize()
//        }
//    }


    //Dirctor 会变得臃肿，会有多个不同的MacBookBuilder SurfaceBookBuilder，所以在 Builder 类的每一个 setter 方法中，返回 this ，那么就可以使用链式的效果
    class Builder {

        private var pc: Pc

        init {
            pc = Pc()
        }

        fun setName(name: String): Builder {
            pc.setName(name)
            return this
        }

        fun setCpu(cpu: String): Builder {
            pc.setCpu(cpu)
            return this
        }

        fun setRomSize(size: Int): Builder {
            pc.setRomSize(size)
            return this
        }

        fun createPc(): Pc {
            return pc
        }
    }

}

//这是 Builder 接口
interface PcBuilder {
    fun setName()
    fun setCpu()
    fun setRomSize()
    fun createPc(): Pc
}


fun main() {
    println("1/0=" + 1 / 0)

    //创建一个 SurfaceBook
    val surfaceBook = Pc.Builder()
            .setCpu("cpu")
            .setName("hp")
            .setRomSize(512)
            .createPc()

    surfaceBook.printInfo()
}
