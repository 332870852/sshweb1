package cn.abc.sshweb1.web;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.abc.sshweb1.service.PermissionService;

public class InitWebServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//两个初始化 ,一个初始化：spring的ioc容器的引用初始化到InitWebServlet类的一个静态方法
	
	
	private static ApplicationContext applicationContext;
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void init() throws ServletException {
		
		//一个初始化：spring的ioc容器
		ServletContext context=getServletContext();
		applicationContext=WebApplicationContextUtils.getWebApplicationContext(context);
		try {
			
			//第二个初始化：初始化权限
			//packageName实施权限控制的包名
			String packageName="cn.abc.sshweb1.controller";
			
			String packageNamePath=packageName.replace(".", "/");
			
			//拿到绝对路径
			String packageNameRealPath=this.getClass().getClassLoader().getResource(packageNamePath).getPath();
			
			File file= new File(packageNameRealPath);  //file就是controller在磁盘上的文件夹
			System.out.println(packageNameRealPath);
			System.out.println(file.exists()+"file");
			//遍历这个文件夹
			String[]classFileNames= file.list(new FilenameFilter() {		
				@Override
				public boolean accept(File dir, String name) {
					if(name.endsWith(".class")) {
						return true;
					}
					return false;
				}
			});
			List<String> resources=new ArrayList<>();
			
			for(String classFileName: classFileNames) {
				//.class后缀去掉
				classFileName=classFileName.substring(0, classFileName.indexOf(".class"));
				//拿到类的包全名
				String classAllpackageName=packageName+"."+classFileName;
				
				//拿到纯粹的包全名
				Class clazz=Class.forName(classAllpackageName);
				
				//拿到这些controller的对象，获取到他们身上的注解
				if(!clazz.isAnnotationPresent(AuthClass.class)) {//这个类没有@AuthClass注解，就不需要权限控制
					continue;
				}
				//剩下的都是有@AuthClass注解的类
				//拿到这些类的所有方法
				Method[] methods=clazz.getDeclaredMethods();
				//遍历类的所有方法
				for(Method method:methods) {
					if(!method.isAnnotationPresent(AuthMethod.class)) continue;
					//剩下的都是有@AuthClass注解的方法,拿到保存到permission表里resource的值
					//1. cn.abc.sshweb1.controller.UserController.addUser 放到数据库
					/*String resource=classAllpackageName+"."+method.getName();*/
					//或者2.把requestMapping的映射路径放到数据库
					RequestMapping requestMapping=method.getAnnotation(RequestMapping.class);
					resources.add(requestMapping.value()[0]); //只能拿到一个value()[0]
					
				}
				//List<String> resouces 包含了controller包，所有被@AuthClass和@AuthMehod共同作用的
				//方法上面的requestMapping的value值都在里面
				PermissionService permissionService=(PermissionService) applicationContext.getBean("PermissionService");
				permissionService.initPermissions(resources);
				context.setAttribute("allPermRes", resources); //系统中s所有的需要权限控制方法的权限控制标记
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
