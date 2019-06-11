package cn.yami.hellokotlin.service

import cn.yami.hellokotlin.db.User
import cn.yami.hellokotlin.db.UserReponsitory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

interface UserService {

    fun sendValidCodeSMS(phone: String): Map<String, Any>

    fun login(phone: String, validCode: String, deviceId: String): Map<String, Any>

    fun findUserByToken(token: String): User
}


@Service
class UserServiceImpl : UserService {

    @Autowired
    private lateinit var userReponsitory: UserReponsitory

    override fun sendValidCodeSMS(phone: String): Map<String, Any> {

        var user = userReponsitory.findByPhoneAndStatus(phone, 1)
        if (user == null) {
            return
        }

        return HashMap<String,Any>()
    }

    override fun login(phone: String, validCode: String, deviceId: String): Map<String, Any> {

    }

    override fun findUserByToken(token: String): User {

    }




}