package cn.yami.hellokotlin.db

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*
import javax.transaction.Transactional

fun User.validateBeforeSave() {
    fun validate(value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException(
                    "cannot save user $id: empty $fieldName")
        }

    }

    validate(name, "name")
    validate(phone?:throw IllegalArgumentException(
            "cannot save user $id: empty phone"),
            "phone")
}

/**
 * kotlin的构造函数重载，都被默认参数实现了
 * 只有一个（）主构造函数，无从构造函数
 */
@Entity
@Table(name = "user")
data class User(
        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        val id: Int,
        @Column(name = "name")
        val name: String,
        @Column(name = "status")
        var status: Int = 0,
        @Column(name = "sex")
        val sex: Int,
        @Column(name = "token")
        var token: String,
        @Column(name = "phone")
        var phone: String? = "",
        @Column(name = "valid_code")
        var validCode: String,
        @Column(name = "confirmation")
        var confirmation: String?,
        @Column(name = "last_updated")
        val lastUpdated: Date
) {
    /*@Column(name = "status")
    var status: Int = 0
        private set*/

    // 重写toString方法
    //override fun toString() = "User: name=$name, phone=$phone"

    // 不自定义equals和hashcode，默认是根据主构造函数声明的所有属性进行判断
    // 重写equals方法，对于kotlin的 == 操作安全
    // is 是java中istanceof的模拟
    /*override fun equals(other: Any?): Boolean {
        if (other == null || other !is User) {
            return false
        }
        return name == other.name &&
                id == other.id
    }*/
    // 重写了equals，那么hashCode少不了
    // override fun hashCode(): Int = name.hashCode() * 31 + id.hashCode() * 32}
}
// TODO JPA是什么？需要注意那些点，什么原理？

@Repository
@Suppress("unused")
interface UserRepository : JpaRepository<User, Int> {
    // 查询
    fun findByTokenAndStatus(token: String, status: Int = 1): User?

    fun findByPhoneAndStatus(phone: String, status: Int = 1): User?

    override fun findById(id: Int): Optional<User>

    // 更新
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.token =:token, u.validCode =:validCode WHERE u.id =:id")
    fun updateUserTokenAndValidCode(@Param("token") token: String,
                                    @Param("validCode") validCode: String,
                                    @Param("id") id: Int)


}