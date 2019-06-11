package cn.yami.hellokotlin.db

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.transaction.Transactional


@Entity
@Table(name = "user")
data class User(
        @Id
        val id: Int,
        @Column(name = "name")
        val name: String,
        @Column(name = "status")
        var status: Int,
        @Column(name = "token")
        var token: String,
        @Column(name = "phone")
        var phone: String? = "",
        @Column(name = "valid_code")
        var validCode: String
)

@Repository
@Suppress("unused")
interface UserReponsitory : JpaRepository<User, Int> {
    // 查询
    fun findByTokenAndStatus(token: String, status: Int = 1): User?

    fun findByPhoneAndStatus(phone: String, status: Int = 1): User?

    override fun findById(id: Int): Optional<User>

    // 更新
    @Transactional
    @Modifying
    @Query("UPDATE user u SET u.token =:token, u.valid_code =:validCode WHERE u.id =:id")
    fun updateUserTokenAndValidCode(@Param("token") token: String,
                                    @Param("validCode") validCode: String,
                                    @Param("id") id: Int)


}