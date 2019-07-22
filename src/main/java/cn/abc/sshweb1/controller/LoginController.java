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
	 * 首页是登陆页
	 * @return
	 */
	@RequestMapping(value= {"","/","/login"},method=RequestMethod.GET)
	public String index() {	
		return "login";
	}
	
	@RequestMapping(value= {"","/","/login"},method=RequestMethod.POST)
	public String login(String username,String password,HttpSession session) {
		//判断username和password对不对
		/*try {*/
			User loginUser=userService.login(username,password); //正确返回User对象，否则抛出异常
			session.setAttribute("loginUser", loginUser);
			//判断是否是超级管理员
			Set<Role>roles= loginUser.getRoles();
			boolean isadmin=false;	
			Set<String> userAllpermRes=new HashSet<>();
			Set<Permission>permissions;
			for(Role role:roles) {
				if("超级管理员".equals(role.getRoleName())) {
					isadmin=true;
					break;	//结束本次循环
				}
				//不是超级管理员的情况下,把成功登陆的用户，关联的所有权限标记取出来
				permissions=role.getPermissions();
				for(Permission permission:permissions) {		
					userAllpermRes.add(permission.getResource());
				}
			}
			//循环完毕后，userAllpermRes：包括 了登陆成功的用户，所拥有的所有权限的标记
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
