package cn.yami.hellokotlin

import cn.yami.hellokotlin.common.constant.ProductType
import cn.yami.hellokotlin.common.util.lastCharA
import cn.yami.hellokotlin.common.util.joinToString as joinStr
// 使用as重命名函数
import cn.yami.hellokotlin.common.util.lastChar as last
import cn.yami.hellokotlin.db.Author
import org.junit.Test
import java.io.BufferedReader
import java.io.StringReader
import java.lang.NumberFormatException
import java.util.*

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


        val arrlist = listOf("February", "June", "July")


        // ###################### 扩展函数 ###################
        // println("默认参数拼接字符：" + joinToString(arrlist))
        println("默认参数拼接字符：" + arrlist.joinStr())
        /*println("配置参数拼接字符：" + StringUtils.joinToString(
                arrlist, prefix = "|"))
        println("配置参数拼接字符：" + StringUtils.joinToString(
                arrlist, separator = "-", prefix = "(", suffix = ")"))
        */
        // "happy"是接收者对象
        println("happy的最后一个字符串是：${"happy".last()}")

        val sb = StringBuffer("kotlin?")
        sb.lastCharA = '!'
        println("属性扩展：$sb")


        val strings: List<String> = listOf("first", "second", "third")
        println(strings.last())

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

        // 区间
        val oneToTen = 10..16
        for (i in oneToTen) {
            print("$i \t")
        }

        // 迭代map
        val binaryReps = TreeMap<Char, String>()
        for (c in 'A'..'F') {
            var binary = Integer.toBinaryString(c.toInt())
            binaryReps[c] = binary
        }

        for ((letter, binary) in binaryReps) {
            println("$letter = $binary")
        }


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


        // kotlin并没有创建自己的集合类
        val set = hashSetOf(1,2,2,3,4)
        val list = arrayListOf("1212","1212")
        val hash = hashMapOf(1 to "1", 2 to "2")
        println(set.javaClass)
        println(list.javaClass)
        println(hash.javaClass)
        println("list集合：$list")

        for (i in set) {
            println("$i \t")
        }
        println(set.max())
        println(list.first())

        // ############### 类型投影 ##############
        val x: Array<Int> = arrayOf(1, 2, 3)
        val any = Array<Any>(3) {""}
        copy(x, any)
        any.forEach { print("$it \t") }


        // ############### 对象和属性 #############
        println()
        val author = Author(129, "yami", 1, 49, "mi", true)
        author.isAlive=false
        println("${author.name}, ${author.nickName}, " +
                "${author.isAlive}, ${if (author.isOlder) "老人" else "年轻人"}！")


        when (ProductType.AGRICULTURAL) {
            ProductType.FILM -> println("Film")
            ProductType.AUDIO_ALBUM -> println("Audio Album")
            ProductType.ELECTRONICS -> println("Electronics")
            ProductType.AGRICULTURAL -> println("AGRICULTURAL")
        }

        // try 异常
        // val reader = BufferedReader(StringReader("11"))
        val reader = BufferedReader(StringReader("df"))
        readNumber(reader)


        // 中缀调用
        val (number, name) = 1 to "yami"
        println("$number -  $name")

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

    private fun readNumber(read: BufferedReader) {
        val number = try {
            Integer.parseInt(read.readLine())
        } catch (e: NumberFormatException) {
            // null
            mapOf("ret" to "0", "msg" to "转换失败，数据异常")

        }
        println(number)

    }


}