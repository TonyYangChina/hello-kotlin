package cn.yami.hellokotlin.db

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

/**
 * mins 2019-6-16 18:17:56
 */
@Entity
@Table(name = "sys_log")
data class SysLog(
        @Id
        @GeneratedValue(strategy= GenerationType.AUTO)
        val id: Int? = null,
        @Column(name = "user_id")
        var userId: Int? = null,
        @Column(name = "user_name")
        var userName: String? = null,
        @Column(name = "operation")
        val operation: String? = null,
        @Column(name = "time")
        var time: Int? = null,
        @Column(name = "method")
        var method: String? = "",
        @Column(name = "params")
        var params: String? = null,
        @Column(name = "ip")
        var ip: String? = null,
        @Column(name = "create_time")
        var createTime: Date? = null
)

@Repository
interface SysLogRepository : JpaRepository<SysLog, Int> {

        fun findByUserId(userId: Int): List<SysLog>
}