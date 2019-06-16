package cn.yami.hellokotlin.service

import cn.yami.hellokotlin.common.constant.Constants
import cn.yami.hellokotlin.common.constant.ErrorCode
import cn.yami.hellokotlin.db.User
import cn.yami.hellokotlin.db.UserRepository
import cn.yami.hellokotlin.db.validateBeforeSave
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import cn.yami.hellokotlin.common.util.StringUtils as YamsStringUtils

import org.apache.commons.lang3.RandomStringUtils
import org.apache.commons.lang3.StringUtils
import java.util.*

interface UserService {

    fun sendValidCodeSMS(phone: String): Map<String, Any>

    fun login(phone: String, validCode: String, deviceId: String): Map<String, Any>

    fun findUserByToken(token: String): User?
}


@Service
@Suppress("unused")
class UserServiceImpl : UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    companion object {

        // kotlin 静态类型，变量类型根据上下文来自动判断，省略类型声明
        const val TEST_PHONE = "13888888888"
        const val TEST_VALID_CODE = "888888"
        const val SMS_ID_SALT = "superman"

        fun codeVerify(phone: String, validCode: String, user: User): Boolean {
            if (phone == TEST_PHONE && validCode == "888888") {
                return true
            }
            return StringUtils.equalsAnyIgnoreCase(user.validCode, validCode)
        }
    }

    override fun sendValidCodeSMS(phone: String): Map<String, Any> {

        val user: User = userRepository.findByPhoneAndStatus(phone, 1)
                ?: return mapOf("ret" to ErrorCode.User_Not_Exist_Code, "msg" to "您的账号暂未开通，请联系相关人员开通")

        // java是否匹配kotlin使用？
        // 答：Kotlin的互操作性，java和kotlin相互调用
        // kotlin没有自己的集合库
        val validateCode: String = RandomStringUtils.randomNumeric(Constants.SMS_CODE_LENGTH)

        val resultMsg = mapOf("ret" to 0, "msg" to "短信已发送，请查收", "validCode" to validateCode)

        if (resultMsg["ret"] == 0) {
            user.validCode = validateCode
            userRepository.save(user)
        }

        return resultMsg
    }

    override fun login(phone: String, validCode: String, deviceId: String): Map<String, Any> {
        val user: User? = userRepository.findByPhoneAndStatus(phone, 1)

        if (user != null) {
            if (user.validCode.isBlank() || validCode.isBlank()) {
                if (validCode != TEST_VALID_CODE) {
                    return mapOf("ret" to ErrorCode.INVALIDATE_VALIDATE_CODE, "msg" to "您的验证码不正确")
                }
            }

            if(!codeVerify(phone, validCode, user)) {
                return mapOf("ret" to ErrorCode.INVALIDATE_VALIDATE_CODE, "msg" to "您的验证码不正确")
            }


            val now = Date()
            if (now.time - user.lastUpdated.time > Constants.One_Hour_MilSec) {
                //当前时间与最后更新时间（短信发送时间）相差1小时以上
                if (phone != TEST_PHONE) //测试手机账号没有验证码过期
                    return mapOf("ret" to ErrorCode.VALIDATE_CODE_OUTDATED_CODE, "msg" to "短信验证码超时，请重试")
            }

            var token: String = RandomStringUtils.randomAlphanumeric(Constants.TOKEN_LENGTH)
            if (phone == TEST_PHONE) {
                token = YamsStringUtils.multiply('8', Constants.TOKEN_LENGTH)
            }
            user.token = token
            user.validCode = ""
            if (phone == TEST_PHONE) {
                user.validCode = TEST_VALID_CODE
            }
            
            userRepository.updateUserTokenAndValidCode(user.token, user.validCode, user.id)

            val data = mapOf("uid" to user.id, "name" to user.name, "sex" to user.sex,
                    "confirmation" to user.confirmation, "phone" to user.phone)
            return mapOf("ret" to 0, "access_token" to token, "data" to data)
        } else {
            return mapOf("ret" to ErrorCode.User_Not_Exist_Code, "msg" to "您的账号暂未开通，请联系相关人员开通")
        }

    }

    override fun findUserByToken(token: String): User? {

        val user: User? = userRepository.findByTokenAndStatus(token, 1)
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