package cn.yami.hellokotlin.common.constant

/**
 * 产品类型 枚举类
 */
enum class ProductType(
        val code: Int
) {
    FILM(100),
    AUDIO_ALBUM(200),
    ELECTRONICS(300),
    AGRICULTURAL(400)
}