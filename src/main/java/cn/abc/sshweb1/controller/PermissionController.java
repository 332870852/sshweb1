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
import cn.abc.sshweb1.model.Permission;
import cn.abc.sshweb1.model.SystemContext;
import cn.abc.sshweb1.service.PermissionService;
import cn.abc.sshweb1.web.AuthClass;
import cn.abc.sshweb1.web.AuthMethod;

@AuthClass
@Controller
public class PermissionController {
	
	@Autowired
	private PermissionService permissionService;
	
	@AuthMethod
	@RequestMapping(value="/permissionManager",method=RequestMethod.GET)
	public String permissionManager() {
		
		return "permission_manager";
	}
	/**
	 * 获取所有权限
	 * @return
	 */
	@AuthMethod
	@ResponseBody  
	@RequestMapping(value="/getAllPermissions",method=RequestMethod.POST)
	public List<Map<String,String>> getAllpermissions(){	
		System.out.println("000000");
		List<Permission> permissions=permissionService.getAllPermissions();
		List<Map<String,String>>permissionList=new ArrayList<>();
		for(Permission permission:permissions) {
			Map<String,String>permissionMap=new HashMap<>();
			permissionMap.put("id", permission.getId()+"");
			permissionMap.put("text",permission.getResource());
			permissionList.add(permissionMap);
		}
		return permissionList;
	}
	
	/**
	 * 支持分页显示的查询
	 * @return
	 */
	@AuthMethod
	@ResponseBody
	@RequestMapping(value="/getAllPagerpermissions",method=RequestMethod.POST)
	public Pager<Permission> getAllPagerPermissions(Integer page,Integer rows){		
		if(rows!=null&&rows>0)
			SystemContext.setPageOffset((page-1)*rows);
		if(page!=null&&page>0)
			SystemContext.setPageSize(rows);
		Pager<Permission> pager=permissionService.getAllPagerPermission();
		SystemContext.removePageOffset();
		SystemContext.removePageSize();	
		return permissionService.getAllPagerPermission();	
	}
	
	/**
	 * 添加角色
	 * @param role
	 * @return
	 */
	@AuthMethod
	@ResponseBody
	@RequestMapping(value="/addPermission",method=RequestMethod.POST)
	public String addRole(@RequestBody Permission permission) {	
		try {
			permissionService.add(permission);
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
	@RequestMapping(value="/updatePermission",method=RequestMethod.POST)
	public String updateRole(@RequestBody Permission permission) {	
		try {
			permissionService.update(permission);
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
	@RequestMapping(value="/deletePermission",method=RequestMethod.POST)
	public String deleteRole(@RequestParam(value="ids[]") int[] ids){		
		try {
			if(ids!=null) {
				for(int id:ids) {
				permissionService.delete(id);
				}
			}		
		} catch (Exception e) {
			return "error";
		}		
		return "ok";
	}
}
