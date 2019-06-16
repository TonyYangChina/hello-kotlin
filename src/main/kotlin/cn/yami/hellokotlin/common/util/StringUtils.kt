// @file:JvmName("StringUtils")
package cn.yami.hellokotlin.common.util

import org.apache.commons.lang3.StringUtils
import java.lang.StringBuilder

// 顶层属性
// const = public static final
const val SEPARATOR = ","

/*fun <T> joinToString(
        collection: Collection<T>,
        separator: String = SEPARATOR,
        prefix: String = "",
        suffix: String = ""
): String {
    val result = StringBuilder(prefix)
    for ((index, element) in collection.withIndex()) {
        if (index > 0 ) result.append(separator)
        result.append(element)
    }

    result.append(suffix)
    return result.toString()
}*/


// 给别人的类添加方法
// 接收者类型即String，成员可以不用this来访问
// 扩展函数是静态函数
// fun String.lastChar(): Char = this.get(this.length - 1)
fun String.lastChar(): Char = get(length - 1)

// 扩展属性
var StringBuffer.lastCharA: Char
    get() = get(length -1)
    set(value)  {
        this.setCharAt(length-1, value)
    }


// 类的成员函数和扩展函数有相同签名，那么成员函数会被优先使用
fun <T> Collection<T>.joinToString(
        separator: String = SEPARATOR,
        prefix: String = "",
        suffix: String = ""
): String {
    val result = StringBuilder(prefix)
    for ((index, element) in this.withIndex()) {
        if (index > 0 ) result.append(separator)
        result.append(element)
    }

    result.append(suffix)
    return result.toString()
}

/**
 * object修饰的类为静态类，里面的方法和变量都为静态的
 */
object StringUtils {

    fun multiply(self: Char, factor: Number): String {
        val size = factor.toInt()
        return when {
            size == 0 -> ""
            size < 0 -> throw IllegalArgumentException("multiply() should be called with a number of 0 or greater not: $size")
            else -> {
                val answer = StringBuilder().append(self)
                for (i in 1 until size) {
                    answer.append(self)
                }
                answer.toString()
            }
        }
    }

/*companion object {*/
        // 1. 使用默认参数
        // kotlin消除静态工具类：顶层函数
        /*fun <T> joinToString(
                collection: Collection<T>,
                separator: String = ",",
                prefix: String = "",
                suffix: String = ""
        ): String {
            val result = StringBuilder(prefix)
            for ((index, element) in collection.withIndex()) {
                if (index > 0 ) result.append(separator)
                result.append(element)
            }

            result.append(suffix)
            return result.toString()
        }*/
/*    }*/
}
