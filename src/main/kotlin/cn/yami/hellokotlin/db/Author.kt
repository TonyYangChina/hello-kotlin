package cn.yami.hellokotlin.db

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "author")
data class Author(
        @Id
        val id: Int,
        val name: String,
        val status: Int,
        val age: Int,
        @Column(name = "nick_name")
        var nickName: String,
        var isAlive: Boolean
) {
        // 自定义访问器
        val isOlder: Boolean
                get() = age > 50
}

// 只有数据没有其他代码的类，即值对象
// data 修饰class，表示是数据类
// public 是默认可见的，可省略
/**
 *  java属性是private，访问和设置通过get/set，kotlin如何体现？
 *      kotlin，val和var体现，声明即配置了访问器
 *      val只读，生成一个字段和一个简单getter
 *      var可写，一个字段和一个getter和setter
 *
 */



