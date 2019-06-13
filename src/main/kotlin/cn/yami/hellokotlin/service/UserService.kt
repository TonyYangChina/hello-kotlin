package cn.yami.hellokotlin.service

import cn.yami.hellokotlin.common.constant.Contants
import cn.yami.hellokotlin.common.constant.ErrorCode
import cn.yami.hellokotlin.db.User
import cn.yami.hellokotlin.db.UserReponsitory
import cn.yami.hellokotlin.db.validateBeforeSave
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import org.apache.commons.lang3.RandomStringUtils
import org.apache.commons.lang3.StringUtils

interface UserService {

    fun sendValidCodeSMS(phone: String): Map<String, Any>

    fun login(phone: String, validCode: String, deviceId: String): Map<String, Any>

    fun findUserByToken(token: String): User?
}


@Service
@Suppress("unused")
class UserServiceImpl : UserService {

    @Autowired
    private lateinit var userReponsitory: UserReponsitory

    companion object {

        // kotlin 静态类型，变量类型根据上下文来自动判断，省略类型声明
        const val TEST_PHONE = "13888888888"
        const val TEST_VALICODE = "888888"
        const val SMS_ID_SALT = "superman"

        fun codeVerify(phone: String, validCode: String, user: User): Boolean {
            if (phone == TEST_PHONE && validCode == "888888") {
                return true
            }
            return StringUtils.equalsAnyIgnoreCase(user.validCode, validCode)
        }
    }

    override fun sendValidCodeSMS(phone: String): Map<String, Any> {

        userReponsitory.findByPhoneAndStatus(phone, 1)
                ?: return mapOf("ret" to ErrorCode.User_Not_Exist_Code, "msg" to "您的账号暂未开通，请联系相关人员开通")

        // java是否匹配kotlin使用？
        // 答：Kotlin的互操作性，java和kotlin相互调用
        // kotlin没有自己的集合库
        val validateCode: String = RandomStringUtils.randomNumeric(Contants.SMS_CODE_LENGTH)

        return mapOf("ret" to 0, "msg" to "短信已发送，请查收", "validCode" to validateCode)
    }

    override fun login(phone: String, validCode: String, deviceId: String): Map<String, Any> {
        // val user: User? = userReponsitory.findByPhoneAndStatus(phone, 1)
                // ?: return mapOf("ret" to ErrorCode.User_Not_Exist_Code, "msg" to "您的账号暂未开通，请联系相关人员开通")


        return mapOf()
    }

    override fun findUserByToken(token: String): User? {

        val user: User? = userReponsitory.findByTokenAndStatus(token, 1)
        return user
    }


    fun saveUser(user: User) {
        // 1. 局部函数
        // 局部函数可以访问所在函数的参数和变量
        // fun validate(user: User,
        /*fun validate(value: String,
                     fieldName: String) {
            if (value.isEmpty()) {
                throw IllegalArgumentException(
                        "cannot save user ${user.id}: empty $fieldName")
            }
        }*/

        // validate(user, user.name, "name")
        // validate(user.name, "name")
        // 对于可能存在null的属性，
        // validate(user, user.phone.toString(),"phone")
        // roomList?.size ?: 0
        /*validate(user.phone
                ?:throw java.lang.IllegalArgumentException("cannot save user ${user.id}: empty phone"),
                "phone")*/

        // 2.使用扩展函数
        user.validateBeforeSave()

    }




}