package cn.yami.hellokotlin.web

import cn.yami.hellokotlin.common.constant.Constants
import cn.yami.hellokotlin.common.constant.ErrorCode
import cn.yami.hellokotlin.common.util.Validator
import cn.yami.hellokotlin.service.UserService
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/user")
@RestController
class UserController {
    @Autowired
    private lateinit var userService: UserService

    @PostMapping("send_smg")
    fun sendValidMsg(@RequestBody body: Map<String, Any>): Map<String, Any>{
        val phone: String = body.get("phone") as String
        return if (!Validator.validPhone(phone)) {
            mapOf("ret" to ErrorCode.INVALIDATE_PARAMETER_CODE, "msg" to "手机号码参数异常")
        } else {
            userService.sendValidCodeSMS(phone)
        }
    }


    @RequestMapping("/login")
    fun login(@RequestBody body: Map<String, Any>): Map<String, Any> {
        val phone: String?= body["phone"] as String
        val validCode: String? = body["valid_code"] as String

        phone?:return mapOf("ret" to ErrorCode.INVALIDATE_PARAMETER_CODE, "msg" to "手机号码:$phone 参数异常")
        if (!Validator.validPhone(phone)) {
            return mapOf("ret" to ErrorCode.INVALIDATE_PARAMETER_CODE, "msg" to "手机号码:$phone 参数异常")
        }
        if (StringUtils.isBlank(validCode) || validCode?.length != Constants.SMS_CODE_LENGTH) {
            return mapOf("ret" to ErrorCode.INVALIDATE_PARAMETER_CODE, "msg" to "短信验证码:$validCode 参数异常")
        }
        return userService.login(phone, validCode, "")
    }

}