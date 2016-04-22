package net.yasion.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 针对SpringMVC的参数解析，表示参数传入的是作为搜索使用的对象，需要重写BaseDTO的generateDefOperateRelation实现相关生成 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ModelDTOSearch {

}