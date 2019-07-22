package cn.abc.sshweb1.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import cn.abc.sshweb1.web.AuthClass;
import cn.abc.sshweb1.web.AuthMethod;

@AuthClass
@Controller
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@AuthMethod
	@ResponseBody
	@RequestMapping(value="/getAllRoles",method=RequestMethod.POST)
	public List<Map<String,String>> getAllRoles(){
		
		List<Role> roles=roleService.getAllRoles();
		List<Map<String,String>> roleList=new ArrayList<>();
		for(Role role:roles) {
			Map<String,String> roleMap=new HashMap<>();
			roleMap.put("id", role.getId()+"");
			roleMap.put("text", role.getRoleName());
			roleList.add(roleMap);
		}		
		return roleList;
	}
	
	@AuthMethod
	@RequestMapping(value="/roleManager",method=RequestMethod.GET)
	public String roleManager() {	
		return "role_manager";
	}
	
	/**
	 * 分页查询
	 * @param page
	 * @param rows
	 * @return
	 */
	@AuthMethod
	@ResponseBody
	@RequestMapping(value="/getAllPagerRoles",method=RequestMethod.POST)
	public Pager<Role> getAllPagerRoles(Integer page,Integer rows){
		if(rows!=null&&rows>0)
			SystemContext.setPageOffset((page-1)*rows);
		if(page!=null&&page>0)
			SystemContext.setPageSize(rows);
		Pager<Role> pager=roleService.getAllPagerRoles();
		SystemContext.removePageOffset();
		SystemContext.removePageSize();	
		return roleService.getAllPagerRoles();	
	}
	
	/**
	 * 添加角色
	 * @param role
	 * @return
	 */
	@AuthMethod
	@ResponseBody
	@RequestMapping(value="/addRole",method=RequestMethod.POST)
	public String addRole(@RequestBody Role role) {	
		try {
			roleService.add(role);
		} catch (Exception e) {
			return "error";
		}
		return "ok";
	}
	
	/**
	 * 更新角色
	 * @param role
	 * @return
	 */
	@AuthMethod
	@ResponseBody
	@RequestMapping(value="/updateRole",method=RequestMethod.POST)
	public String updateRole(@RequestBody Role role) {	
		try {
			roleService.update(role);
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
	@RequestMapping(value="/deleteRole",method=RequestMethod.POST)
	public String deleteRole(@RequestParam(value="ids[]") int[] ids){		
		try {
			if(ids!=null) {
				for(int id:ids) {
				roleService.delete(id);
				}
			}		
		} catch (Exception e) {
			return "error";
		}		
		return "ok";
	}
}
