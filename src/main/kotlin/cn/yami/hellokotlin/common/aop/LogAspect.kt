package cn.yami.hellokotlin.common.aop

import cn.yami.hellokotlin.common.constant.Constants
import cn.yami.hellokotlin.common.util.HttpContextUtil
import cn.yami.hellokotlin.common.util.IPUtil
import cn.yami.hellokotlin.db.SysLog
import cn.yami.hellokotlin.db.SysLogRepository
import cn.yami.hellokotlin.db.User
import cn.yami.hellokotlin.service.UserService
import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.CodeSignature
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.validation.BindingResult
import java.lang.StringBuilder
import java.util.*
import javax.servlet.http.HttpServletRequest


@Aspect
@Component
class LogAspect {

    private val logger = KotlinLogging.logger {}

    @Autowired
    private lateinit var userService: UserService
    @Autowired
    private lateinit var sysLogRepository: SysLogRepository

    @Suppress("unused")
    @Pointcut("@annotation(cn.yami.hellokotlin.common.annotation.Log)")
    fun log() {
    }

    @Around("log()")
    fun logAround(joinPoint: ProceedingJoinPoint) {

        val beginTime: Long = System.currentTimeMillis()
        joinPoint.proceed()
        val time = System.currentTimeMillis() - beginTime
        saveLog(joinPoint, time)
    }

    private fun saveLog(joinPoint: ProceedingJoinPoint, time: Long) {
        val signature: MethodSignature = joinPoint.signature as MethodSignature
        // val method: Method = signature.method
        val sysLog = SysLog()
        // 请求的方法名
        val className: String = joinPoint.target.javaClass.name
        val methodName: String = signature.declaringTypeName + "." + signature.name
        sysLog.method = "$className.$methodName()"
        // 请求的方法参数值
        val args = joinPoint.args
        // 请求的方法参数名称
        // val u = LocalVariableTableParameterNameDiscoverer()
        val paramNames = (joinPoint.signature as CodeSignature).parameterNames
        val params = StringBuilder("")
        for (i in args.indices) {
            if (args[i] !is BindingResult) {
                params.append(" $paramNames[i] : $args[i]")
            }
        }
        sysLog.params = params.toString()
        // 获取request
        val request: HttpServletRequest = HttpContextUtil.getHttpServletRequest()
        // 获取token
        val token: String = request.getHeader(Constants.TOKEN_HEADER_NAME)
        val user: User? = userService.findUserByToken(token)

        // sysLog.ip = IPUtil.getIpAddress(request)
        sysLog.ip = request.remoteAddr
        val methodOther = request.method
        logger.info("待观察的方法名：$methodOther")
        if (null != user) {
            sysLog.userId = user.id
            sysLog.userName = user.name
        }
        sysLog.time = time.toInt()
        sysLog.createTime = Date()

        sysLogRepository.save(sysLog)
    }
}