package cn.abc.sshweb1.web;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) //����ָ����ע��@AuthClass����������
public @interface AuthClass {
	
	public String value() default "";
	
}
