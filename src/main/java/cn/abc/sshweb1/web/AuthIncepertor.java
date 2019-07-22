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
		if(handler instanceof HandlerMethod) { //ǰ���url��ַ����д�ģ�false
			HandlerMethod handlerMethod=(HandlerMethod) handler;
			Method method=handlerMethod.getMethod();
			RequestMapping requestMapping=method.getAnnotation(RequestMapping.class);
			resource=requestMapping.value()[0]; //��ǰ����ҳ���Ӧ��Ȩ�ޱ��
			
		}else {
			System.out.println(handler+"/////");
			throw new RuntimeException("�����ʵ�ҳ�治����!");
		}
		//�õ�ϵͳ������Ҫ��ϵͳ���Ƶķ�������Ӧ��Ȩ�޿��Ʊ��
		List<String>resources=(List<String>) request.getServletContext().getAttribute("allPermRes");
		
		//�õ�Ŀǰ��½�û�����ӵ�е�����Ȩ�޶�Ӧ��Ȩ�ޱ��
		Set<String>userAllperRes=(Set<String>) session.getAttribute("userAllpermRes");
		//�õ�Ŀǰ�ɹ���½���û�����
		User loginUser=(User) session.getAttribute("loginUser");
		if(loginUser==null) { //û�е�½����ת����½ҳ��
			response.sendRedirect(request.getContextPath()+"/login");
		}else {		
			boolean isadmin=(boolean) session.getAttribute("isadmin"); //�Ƿ� ��������Ա
			if(!isadmin&&resources.contains(resource)) {//���ǳ�������Ա�͵�ǰ��ҳҪ��Ȩ�޿���
				//���п��ƣ��Ա�����������userAllperRes��������resource
				if(!userAllperRes.contains(resource)) {
					throw new RuntimeException("��û��Ȩ�޷��ʸù���!");
				}
			}
		}
		
		return super.preHandle(request, response, handler);
		
	}
	
	
}
