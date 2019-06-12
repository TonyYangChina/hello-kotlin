// @file:JvmName("StringUtils")
package cn.yami.hellokotlin.common.util

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

// 接收者对象成员可以不用this来访问
// 扩展函数是静态函数
fun String.lastChar(): Char = get(length - 1)


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

/*class StringUtils {

    companion object {*/
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
/*    }
}*/
