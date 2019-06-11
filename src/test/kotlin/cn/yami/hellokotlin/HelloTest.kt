package cn.yami.hellokotlin

import org.junit.Test

class HelloTest {

    /**
     * 函数参数使用Pascal表示法定义，每个参数必须有显示类型
     */
    @Test
    fun hello() {
        println("hello Kotlin")

        returnNoneMean()

        // 创建数组
        // 单次赋值变量
        val arr = arrayOf("February", "June", "July")
        // 可重新赋值变量
        var arrChange = intArrayOf(1, 2, 3)

        // ############## 函数 ##################
        // 默认参数函数调用
        defaultValue(arr)
        // 通过使用命名参数调用该函数来使用
        defaultValue2(arr, end = arr.size)
        arrChange = defaultValue3(arrChange, end = arrChange.size)

        println(message = arrChange)


        // ############ NPE #############
        var a: String = "abc"
        a = "bcd"
        println(a)

        // 编译报错
        // a = null
        var b: String? = "abc"
        b = null
        // 编译器安全性检查，null
        // val bl = b.length
        // 改方式，最好是b是val
        // val bl = if (b != null) b.length else -1
        // 安全调用 ?
        val bl2 = b?.length
        println("空类型的长度：$bl2")
        var bl3 = b?.length ?: 0
        println("空类型, 默认的长度：$bl3")

        // 非空断言运算符，空则抛出异常
        // val l = b!!.length


        // ################ 循环 ##############
        val items = listOf("apple", "banana", "orange", "apricot")
        for (item in items) {
            print("$item \t")
        }
        println(whenTest("Hello"))
        println(whenTest(1))


        // ################ lamdba ############
        items.filter { it.startsWith("a") }
                .sortedBy { it }
                .map { it.toUpperCase() }
                .forEach{println(it)}

        // ############### 集合 ##############
        val user: HashMap<String,String> = HashMap()
        user.put("name","yami")
        user.put("age","26")
        user.put("phone","18888888888")

        for ((key, value) in user) {
            println("$key -> $value")
        }


        typeTest()


        // ############### 类型投影 ##############
        val x: Array<Int> = arrayOf(1, 2, 3)
        val any = Array<Any>(3) {""}
        copy(x, any)
        any.forEach { print("$it \t") }

    }

    /**
     * 函数返回无意义的值
     *  Unit可以省略
     *
     */
    private fun returnNoneMean() {
        // fun returnNoneMean(): Unit {
        println("fdsfldsjfls")
    }

    @Suppress("unused")
    private fun returnNullAble(obj: Any): Int? {
        if (obj is String) {
            return obj.length
        }
        return null
    }

    /**
     * 参数默认值 + 函数体单条执行语句
     * @param arr 字符串数组
     * @param begin 起始位置
     * @param end 结束位置
     */
    private fun defaultValue(arr: Array<String>, begin: Int = 0, end: Int = arr.size)
            : Array<String> = arr.copyOfRange(begin, end)

    /**
     * 参数默认值 + 对一个默认参数在一个无默认值的参数之前
     */
    private fun defaultValue2(arr: Array<String>, begin: Int = 0, end: Int)
            : Array<String> = arr.copyOfRange(begin, end)

    /**
     * 参数默认值 + 对一个默认参数在一个无默认值的参数之前
     */
    private fun defaultValue3(arr: IntArray, begin: Int = 0, end: Int)
            : IntArray = arr.copyOfRange(begin, end)


    private fun whenTest(obj: Any): String =
            when (obj) {
                1           -> "One"
                "Hello"     -> "Greeting"
                is Long     -> "Long"
                !is String  -> "Not a String"
                else        -> "Unkown"
            }

    /**
     * 类型测试
     */
    private fun typeTest(): Long{
        val b: Byte = 1
        // i = b 错误，只能显示转换
        val i: Int = b.toInt()
        // 算术会有重载做适当转换
        val l = 1L + i

        return l+1
    }

    /**
     * out 类型投影，限制from，
     *
     * Array<T> 在T上是不型变的，Array<Int>和Array<Any>不是另一个的子类型
     * 防止做坏事：copy，将String写到from
     */
    private fun copy(from: Array<out Any>, to: Array<Any>) {
        assert(from.size == to.size)
        for (i in from.indices) {
            to[i] = from[i]
        }

    }
}