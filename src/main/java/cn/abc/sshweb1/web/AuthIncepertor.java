package cn.abc.sshweb1.web;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.abc.sshweb1.model.User;

public class AuthIncepertor extends HandlerInterceptorAdapter{

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session=request.getSession();
		String resource="";
		if(handler instanceof HandlerMethod) { //前面的url地址，乱写的，false
			HandlerMethod handlerMethod=(HandlerMethod) handler;
			Method method=handlerMethod.getMethod();
			RequestMapping requestMapping=method.getAnnotation(RequestMapping.class);
			resource=requestMapping.value()[0]; //当前访问页面对应的权限标记
			
		}else {
			System.out.println(handler+"/////");
			throw new RuntimeException("您访问的页面不存在!");
		}
		//拿到系统中所有要求系统控制的方法，对应的权限控制标记
		List<String>resources=(List<String>) request.getServletContext().getAttribute("allPermRes");
		
		//拿到目前登陆用户，所拥有的所有权限对应的权限标记
		Set<String>userAllperRes=(Set<String>) session.getAttribute("userAllpermRes");
		//拿到目前成功登陆的用户对象
		User loginUser=(User) session.getAttribute("loginUser");
		if(loginUser==null) { //没有登陆，跳转到登陆页面
			response.sendRedirect(request.getContextPath()+"/login");
		}else {		
			boolean isadmin=(boolean) session.getAttribute("isadmin"); //是否 超级管理员
			if(!isadmin&&resources.contains(resource)) {//不是超级管理员和当前网页要求权限控制
				//进行控制，对比两个东西，userAllperRes包不包含resource
				if(!userAllperRes.contains(resource)) {
					throw new RuntimeException("您没有权限访问该功能!");
				}
			}
		}
		
		return super.preHandle(request, response, handler);
		
	}
	
	
}
