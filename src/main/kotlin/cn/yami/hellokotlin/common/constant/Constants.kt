package cn.yami.hellokotlin.common.constant

class Contants {

    companion object {
        /**
         * 公共服务的内部 token 仅用于测试及内部系统间内网调用
         */
        const val Common_TOKEN: String = "e24df12a81fd814017980d0c1fb2f968"

        /**
         * 认证短信数字长度
         */
        const val  SMS_CODE_LENGTH: Int = 6

        /**
         * Token 长度
         */
        const val TOKEN_LENGTH: Int = 30

        /**
         * 用于加密的密钥
         */
        const val SEC_COMPANYCODE_SALT: String = "MACROSS"

        const val SEC_COMPANYCODE_SPLITER: String = "#"

        /**
         * Token Header Name
         */
        const val Token_Header_Name: String = "x-auth-token"

        /**
         * secID header name
         */
        const val SEC_ID_HEADER_NAME: String= "x-auth-secid"

        const val One_Hour_MilSec: Long = 60 * 60 * 1000

        /**
         * JSON Content Type
         */
        const val JSON_CONTENT_TYPE: String = "application/json"


        const val Date_Warning: String = "当前查询日期早于服务确认日期"

        /** 数据不存在标志**/
        const val DATA_NO: String = "-"

        const val DATA_NEGAIVE: Int = -1

        const val DATA_ZERO: Int = 0


        /**会员系统的appid和签名**/
        const val MEMBERDATASERVER_APPID: Int = 10011

        const val MEMBERDATASERVER_MD5KEY: String = "d38f0f36bae62fdb13869710776f8e66"
    }
}