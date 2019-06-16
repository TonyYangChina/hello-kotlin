package cn.yami.hellokotlin.common.util

import org.apache.commons.lang3.StringUtils
import java.util.regex.Pattern


object Validator {

    fun validPhone(phone: String): Boolean {
        // phone?: return false
        if (phone.length != 11) {
            return false
        }
        if (!StringUtils.isNumeric(phone)) {
            return false
        }
        return true
    }

    @Suppress("unused")
    fun validateDateStr(dateStr: String): Boolean {
        // dateStr?: return false
        return DATE_PATTERN.matcher(dateStr).matches()
    }
}

val DATE_PATTERN: Pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}")
