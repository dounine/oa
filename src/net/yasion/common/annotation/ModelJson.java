package net.yasion.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 针对SpringMVC的参数解析，表示参数传入的是JSON，需要转换成具体的对象(VO、POJO)，使用了ModelJson注解的方法，request.getReader可能已经失效 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ModelJson {

}