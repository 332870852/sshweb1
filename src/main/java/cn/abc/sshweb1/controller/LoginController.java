package cn.abc.sshweb1.controller;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.abc.sshweb1.model.Permission;
import cn.abc.sshweb1.model.Role;
import cn.abc.sshweb1.model.User;
import cn.abc.sshweb1.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	/**
	 * ��ҳ�ǵ�½ҳ
	 * @return
	 */
	@RequestMapping(value= {"","/","/login"},method=RequestMethod.GET)
	public String index() {	
		return "login";
	}
	
	@RequestMapping(value= {"","/","/login"},method=RequestMethod.POST)
	public String login(String username,String password,HttpSession session) {
		//�ж�username��password�Բ���
		/*try {*/
			User loginUser=userService.login(username,password); //��ȷ����User���󣬷����׳��쳣
			session.setAttribute("loginUser", loginUser);
			//�ж��Ƿ��ǳ�������Ա
			Set<Role>roles= loginUser.getRoles();
			boolean isadmin=false;	
			Set<String> userAllpermRes=new HashSet<>();
			Set<Permission>permissions;
			for(Role role:roles) {
				if("��������Ա".equals(role.getRoleName())) {
					isadmin=true;
					break;	//��������ѭ��
				}
				//���ǳ�������Ա�������,�ѳɹ���½���û�������������Ȩ�ޱ��ȡ����
				permissions=role.getPermissions();
				for(Permission permission:permissions) {		
					userAllpermRes.add(permission.getResource());
				}
			}
			//ѭ����Ϻ�userAllpermRes������ �˵�½�ɹ����û�����ӵ�е�����Ȩ�޵ı��
			session.setAttribute("isadmin", isadmin);
			if(!isadmin) {
				session.setAttribute("userAllpermRes", userAllpermRes);
			}
		/*} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			session.setAttribute("note", e.getMessage());
		}*/
		return "redirect:/main";		
	}
	
	@RequestMapping(value= {"","/","/main"})
	public String main() {		
		return "main";
	}
	
	@RequestMapping(value="/main/index",method=RequestMethod.GET)
	public String mainIndex() {
		
		return "/main_index";
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("isadmin");
		session.removeAttribute("userAllpermRes");
		session.removeAttribute("loginUser");
		session.invalidate();
		return "/login";
	}
}
