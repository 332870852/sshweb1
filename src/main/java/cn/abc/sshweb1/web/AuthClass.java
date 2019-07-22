package cn.abc.sshweb1.web;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) //定义指定的注解@AuthClass的生命周期
public @interface AuthClass {
	
	public String value() default "";
	
}
