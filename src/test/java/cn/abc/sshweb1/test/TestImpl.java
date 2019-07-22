package cn.abc.sshweb1.test;

import javax.activation.DataSource;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.abc.sshweb1.service.UserService;

public class TestImpl {
	
	@Autowired
	private UserService userService;
	
	@Test
	public void test() {
		
		ApplicationContext context=new ClassPathXmlApplicationContext("spring.xml");
		DataSource dataSource= (DataSource) context.getBean("dataSource");
		System.out.println(dataSource.getName());
	}
}
