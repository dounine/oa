package net.yasion.common.core.logger.aop.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.yasion.common.constant.CommonConstants;
import net.yasion.common.core.http.manager.HttpInternalObjectManager;
import net.yasion.common.core.logger.aop.annotation.Logger;
import net.yasion.common.core.logger.aop.enumeration.LoggerType;
import net.yasion.common.model.TbLog;
import net.yasion.common.model.TbUser;
import net.yasion.common.service.ILogService;
import net.yasion.common.utils.AfxBeanUtils;
import net.yasion.common.utils.HttpUtils;
import net.yasion.common.utils.UserUtils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Component
@Aspect
public class LoggerAspect {

	private ILogService logService;

	public ILogService getLogService() {
		return logService;
	}

	@Autowired
	public void setLogService(ILogService logService) {
		this.logService = logService;
	}

	@Pointcut(value = "@within(logger)", argNames = "logger")
	public void atWithinPointcut(Logger logger) {
	}

	@Pointcut(value = "@annotation(logger)", argNames = "logger")
	public void atAnnotationPointcut(Logger logger) {
	}

	@Pointcut(value = "!@annotation(net.yasion.common.core.logger.aop.annotation.Logger)")
	public void notAtAnnotationLoggerPointcut() {
	}

	@Pointcut(value = "!@within(net.yasion.common.core.logger.aop.annotation.NoLogger)")
	public void notAtWithinNoLoggerPointcut() {
	}

	@Pointcut(value = "!@annotation(net.yasion.common.core.logger.aop.annotation.NoLogger)")
	public void notAtAnnotationNoLoggerPointcut() {
	}

	@Around(value = "atWithinPointcut(logger) and notAtAnnotationLoggerPointcut() and notAtWithinNoLoggerPointcut() and notAtAnnotationNoLoggerPointcut()", argNames = "logger")
	public Object atWithinAroundAdvice(ProceedingJoinPoint pjp, Logger logger) throws Throwable {
		return this.atAnnotationAroundAdvice(pjp, logger);
	}

	@Around(value = "atAnnotationPointcut(logger) and notAtWithinNoLoggerPointcut() and notAtAnnotationNoLoggerPointcut()", argNames = "logger")
	public Object atAnnotationAroundAdvice(ProceedingJoinPoint pjp, Logger logger) throws Throwable {
		Object[] functionArgs = pjp.getArgs();
		Object retVal = null;
		Throwable throwsEx = null;
		TbUser user = null;
		user = UserUtils.getCurrentUser();// 执行前获取一次user,因为操作后可能失去user的数据,比如注销
		try {
			if (null != functionArgs && 0 < functionArgs.length) {
				retVal = pjp.proceed(functionArgs);
			} else {
				retVal = pjp.proceed();
			}
		} catch (Throwable e) {
			throwsEx = e;
		}
		if (null == user) {// 执行后再获取,执行前可能没有用户,执行后才有,比如登录
			user = UserUtils.getCurrentUser();
		}
		try {// 捕获保存日志出错,避免整个线程崩溃
			LoggerType loggerType = logger.type();
			if (LoggerType.LOGIN.equals(loggerType)) {
				this.saveLoginLog(pjp, throwsEx, user);
			} else if (LoggerType.NORMAL.equals(loggerType)) {
				this.saveNormalLog(pjp, retVal, throwsEx, user);
			} else {
				// 其他
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != throwsEx) {
			throw throwsEx;
		}
		return retVal;
	}

	protected void saveLoginLog(JoinPoint jp, Throwable throwsEx, TbUser user) {
		TbLog log = new TbLog();
		Object[] argArr = jp.getArgs();
		ModelMap model = null;
		for (int i = 0; i < argArr.length; i++) {
			if (null != argArr[i]) {
				if (ModelMap.class.isAssignableFrom(argArr[i].getClass())) {
					model = (ModelMap) argArr[i];
				}
			}
		}
		log.setTitle("登陆操作");
		log.setLoggerType(LoggerType.LOGIN.getType());
		log.setLogTime(DateFormatUtils.format(new Date(), CommonConstants.COMMON_DATA_TIME_FORMAT));
		HttpServletRequest request = HttpInternalObjectManager.getCurrentRequest();
		if (null != request) {
			log.setIpAddress(HttpUtils.getIpAddr(request));
			log.setUrl(request.getRequestURI());
			if (null != user) {
				log.setUserId(user.getId());
				log.setUserName(user.getUsername());
				log.setContent("登陆成功");
			} else {
				log.setContent("登陆失败:" + (model == null ? "UNKOWN" : model.get(CommonConstants.LOGIN_ERR_MSG)));
			}
		}
		if (null != throwsEx) {
			log.setContent("登陆失败:出现异常" + throwsEx);
		}
		this.saveLog(log);
	}

	protected void saveNormalLog(JoinPoint jp, Object retVal, Throwable throwsEx, TbUser user) {
		TbLog log = new TbLog();
		Object[] argArr = jp.getArgs();
		log.setTitle("普通操作");
		log.setLoggerType(LoggerType.NORMAL.getType());
		log.setLogTime(DateFormatUtils.format(new Date(), CommonConstants.COMMON_DATA_TIME_FORMAT));
		HttpServletRequest request = HttpInternalObjectManager.getCurrentRequest();
		if (null != user) {
			log.setUserId(user.getId());
			log.setUserName(user.getUsername());
		}
		if (null != request) {
			log.setIpAddress(HttpUtils.getIpAddr(request));
			log.setUrl(request.getRequestURI());
		}
		StringBuilder builder = new StringBuilder();
		Signature signature = jp.getSignature();
		builder.append("TARGET:" + jp.getTarget().getClass().getName() + ";");
		builder.append("DECLARINGTYPE:" + signature.getDeclaringTypeName() + ";FUNCTIONNAME:" + signature.getName() + ";ARGS:(");
		Class<?>[] argTypeArr = new Class<?>[argArr.length];
		if (null != argArr && 0 < argArr.length) {
			for (int i = 0; i < argArr.length; i++) {
				if (null != argArr[i]) {
					argTypeArr[i] = argArr[i].getClass();
					builder.append(argArr[i].getClass().getName() + " " + argArr[i].toString());
				} else {
					argTypeArr[i] = null;
					builder.append("null");
				}
				if (i < argArr.length - 1) {
					builder.append(",");
				}
			}
		}
		builder.append(");");
		Method method = AfxBeanUtils.getMethod(jp.getTarget().getClass(), signature.getName(), argTypeArr);
		Class<?> retValType = null;
		if (null != method) {
			retValType = method.getReturnType();
		}
		if (null != throwsEx) {
			builder.append("EXCEPTION:" + throwsEx + ";");
		} else if (null != retValType || null != retVal) {
			String retTypeName = null;
			if (null != retValType) {
				retTypeName = retValType.getName();
				builder.append("RETURNVALUE:(" + retTypeName + " " + retVal + ");");
			} else {
				retTypeName = retVal.getClass().getName();
				builder.append("RETURNTARGETVALUE:(" + retTypeName + " " + retVal + ");");
			}
		} else {
			builder.append("RETURNNKNOWVALUE:(? ?);");
		}
		log.setContent(builder.toString());
		this.saveLog(log);
	}

	protected TbLog saveLog(TbLog log) {
		if (null != log) {
			try {
				log.setCreateDate(DateFormatUtils.format(new Date(), CommonConstants.COMMON_DATA_TIME_FORMAT));
				log.setCreateUserId(CommonConstants.SYSTEM_UNIT_ID);
				log.setCreateUnitId(CommonConstants.SYSTEM_UNIT_ID);
				return this.logService.save(log);
			} catch (Exception e) {
				e.printStackTrace();
				TbLog logEx = new TbLog();
				logEx.setTitle("异常特殊记录");
				logEx.setLoggerType("Exception");
				logEx.setLogTime(DateFormatUtils.format(new Date(), CommonConstants.COMMON_DATA_TIME_FORMAT));
				logEx.setUserId(log.getUserId());
				logEx.setUserName(log.getUserName());
				logEx.setIpAddress(log.getIpAddress());
				logEx.setUrl(log.getUrl());
				logEx.setContent("SaveLogException:" + e + ";" + e.getCause());
				logEx.setCreateDate(DateFormatUtils.format(new Date(), CommonConstants.COMMON_DATA_TIME_FORMAT));
				logEx.setCreateUserId(CommonConstants.SYSTEM_UNIT_ID);
				logEx.setCreateUnitId(CommonConstants.SYSTEM_UNIT_ID);
				try {
					this.logService.save(logEx);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		return null;
	}
}