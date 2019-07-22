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
	//������ʼ�� ,һ����ʼ����spring��ioc���������ó�ʼ����InitWebServlet���һ����̬����
	
	
	private static ApplicationContext applicationContext;
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void init() throws ServletException {
		
		//һ����ʼ����spring��ioc����
		ServletContext context=getServletContext();
		applicationContext=WebApplicationContextUtils.getWebApplicationContext(context);
		try {
			
			//�ڶ�����ʼ������ʼ��Ȩ��
			//packageNameʵʩȨ�޿��Ƶİ���
			String packageName="cn.abc.sshweb1.controller";
			
			String packageNamePath=packageName.replace(".", "/");
			
			//�õ�����·��
			String packageNameRealPath=this.getClass().getClassLoader().getResource(packageNamePath).getPath();
			
			File file= new File(packageNameRealPath);  //file����controller�ڴ����ϵ��ļ���
			System.out.println(packageNameRealPath);
			System.out.println(file.exists()+"file");
			//��������ļ���
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
				//.class��׺ȥ��
				classFileName=classFileName.substring(0, classFileName.indexOf(".class"));
				//�õ���İ�ȫ��
				String classAllpackageName=packageName+"."+classFileName;
				
				//�õ�����İ�ȫ��
				Class clazz=Class.forName(classAllpackageName);
				
				//�õ���Щcontroller�Ķ��󣬻�ȡ���������ϵ�ע��
				if(!clazz.isAnnotationPresent(AuthClass.class)) {//�����û��@AuthClassע�⣬�Ͳ���ҪȨ�޿���
					continue;
				}
				//ʣ�µĶ�����@AuthClassע�����
				//�õ���Щ������з���
				Method[] methods=clazz.getDeclaredMethods();
				//����������з���
				for(Method method:methods) {
					if(!method.isAnnotationPresent(AuthMethod.class)) continue;
					//ʣ�µĶ�����@AuthClassע��ķ���,�õ����浽permission����resource��ֵ
					//1. cn.abc.sshweb1.controller.UserController.addUser �ŵ����ݿ�
					/*String resource=classAllpackageName+"."+method.getName();*/
					//����2.��requestMapping��ӳ��·���ŵ����ݿ�
					RequestMapping requestMapping=method.getAnnotation(RequestMapping.class);
					resources.add(requestMapping.value()[0]); //ֻ���õ�һ��value()[0]
					
				}
				//List<String> resouces ������controller�������б�@AuthClass��@AuthMehod��ͬ���õ�
				//���������requestMapping��valueֵ��������
				PermissionService permissionService=(PermissionService) applicationContext.getBean("PermissionService");
				permissionService.initPermissions(resources);
				context.setAttribute("allPermRes", resources); //ϵͳ��s���е���ҪȨ�޿��Ʒ�����Ȩ�޿��Ʊ��
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
