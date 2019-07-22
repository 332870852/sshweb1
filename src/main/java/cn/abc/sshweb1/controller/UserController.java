package cn.abc.sshweb1.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.abc.sshweb1.model.Pager;
import cn.abc.sshweb1.model.Role;
import cn.abc.sshweb1.model.SystemContext;
import cn.abc.sshweb1.model.User;
import cn.abc.sshweb1.service.RoleService;
import cn.abc.sshweb1.service.UserService;
import cn.abc.sshweb1.utils.SecurityUtil;
import cn.abc.sshweb1.web.AuthClass;
import cn.abc.sshweb1.web.AuthMethod;

@AuthClass
@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@AuthMethod
	@RequestMapping(value="/userManager",method=RequestMethod.GET)
	public String userManager() {
		return "users/user_manager";
	}
	
	@RequestMapping(value= {"","/","/index"})
	public String index() {
		
		return "index";
	}
	
	@ResponseBody
	@RequestMapping(value="/getAllusers",method=RequestMethod.GET)
	public List<User> getAllusers() {	
		List<User> users=userService.getAllUsers();		
		return users;
	}
	
	@AuthMethod
	@ResponseBody
	@RequestMapping(value="/getAllPagerUsers",method=RequestMethod.POST)
	public Pager<User> getAllPagerUsers(Integer page,Integer rows,
			@RequestParam(value="id",required=false) Integer id,
			@RequestParam(value="username",required=false) String username) {
		if(rows!=null&&rows>0)
			SystemContext.setPageOffset((page-1)*rows);
		if(page!=null&&page>0)
			SystemContext.setPageSize(rows);
		User user=new User();
		if(id!=null&&id>0) user.setId(id);
		if(username!=null&&!username.equals("")) user.setUsername(username);
		

		Pager<User> pager=userService.getAllPagerUsers(user);
		SystemContext.removePageOffset();
		SystemContext.removePageSize();	
		return pager;
	}
	
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	@AuthMethod
	@ResponseBody
	@RequestMapping(value="/updateUser",method=RequestMethod.POST)
	public String updateUser(@RequestBody User user) {
		try {
			userService.updateUser(user);
		} catch (Exception e) {
			return "error";
		}		
		return "ok";
	}
	
	/**
	 * 添加用户信息
	 * @param user
	 * @return
	 * @throws ParseException 
	 */
	/*@ResponseBody
	@RequestMapping(value="/addUser",method=RequestMethod.POST)
	public String addUser(String username,String password,String state,String roles,
			String regDate) throws ParseException {
		
		User user=new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setState(Integer.parseInt(state));
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		//sdf.parse(regDate);
		Date reg_date=sdf.parse(regDate);
		user.setRegDate(reg_date);
		
		String []roleidarr=roles.split(",");
		Set<Role> setRoles=new HashSet<>();
		int int_rid=0;
		for(String rid:roleidarr) {
			int_rid=Integer.parseInt(rid);
			Role role=roleService.load(int_rid);
		
			setRoles.add(role);
		}
		user.setRoles(new HashSet<>(setRoles));
		System.out.println("---"+user);
		try {
			userService.add(user);
		} catch (Exception e) {
			return "error";
		}		
		return "ok";
	}*/
	@AuthMethod
	@ResponseBody
	@RequestMapping(value="/addUser",method=RequestMethod.POST)
	public String addUser(@RequestBody User user){
		/*MD5加密 密码
		String password=user.getPassword();
		String username=user.getUsername();
		password=SecurityUtil.md5(username, password);
		user.setPassword(password);
		System.out.println("===="+user);*/
		User u=userService.loadUserByUsername(user.getUsername());
		if(u!=null) {//账号已经存在
			return "exits";
		}
		try {
			userService.add(user);
		} catch (Exception e) {
			return "error";
		}		
		return "ok";
	}
	
	/**
	 * 删除用户
	 * @param ids
	 * @return
	 */
	@AuthMethod
	@ResponseBody
	@RequestMapping(value="/deleteUser",method=RequestMethod.POST)
	public String deleteUser(@RequestParam(value="ids[]") int[] ids){		
		try {
			if(ids!=null) {
				for(int id:ids) {
				userService.delete(id);
				}
			}		
		} catch (Exception e) {
			return "error";
		}		
		return "ok";
	}
}
