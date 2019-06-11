package cn.yami.hellokotlin.common.constant

@Suppress("unused")
class ErrorCode {

    companion object {
        const val User_Not_Exist_Code: Int = 80000
        const val INVIDATE_TOKEN_CODE: Int = 90000
        const val DECRYPT_ERROR_CODE: Int = 90001
        const val RIGHTS_AUTH_CODE: Int = 90002

        const val Validate_Code_Outdated_code: Int = 80001
        /**
         * 手机号码格式异常
         */
        const val INVALIDATE_PARAMETER_CODE: Int = 80002

        /**
         * 验证码不正确
         */
        const val INVALIDATE_VALIDATE_CODE: Int = 80003
        const val SERVER_ERROR_CODE: Int = 80004
        const val NO_DATA_ERROR_CODE: Int = 80005
        const val User_Alredy_Confirmed_CODE: Int = 80006
        const val User_Statues_Error_Code: Int = 80007
        const val SERVER_RESPONSE_ERROR: Int = 80008

        val User_Status_Error: Map<String, Any> = mapOf("ret" to User_Statues_Error_Code, "msg" to "您的账号已经停用，如有疑问请咨询相关人员")

        val User_Dont_Exist = mapOf("ret" to User_Not_Exist_Code, "msg" to "您的账号暂未开通，请联系相关人员开通")

        val Validate_Code_Error = mapOf("ret" to INVALIDATE_VALIDATE_CODE, "msg" to "您的验证码不正确")

        val Validate_Code_Outdated = mapOf("ret" to Validate_Code_Outdated_code, "msg" to "短信验证码超时，请重试")

        val Invalidate_Token = mapOf("ret" to INVIDATE_TOKEN_CODE, "msg" to "校验失败，请重新登录")
    }


}