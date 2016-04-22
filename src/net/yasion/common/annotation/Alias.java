package net.yasion.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 别名注解，用于 @ModelAliasLink 内部，代表一个别名组合 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Alias {

	/** 一个别名的组合，可以多级别名 */
	String item();

	/** 对于item的别名 */
	String alias();
}