package cn.abc.sshweb1.dao;

import java.util.List;

import cn.abc.sshweb1.model.Pager;
import cn.abc.sshweb1.model.Role;

public interface RoleDao extends BaseDao<Role>{
	
	/**
	 * 获取所有角色信息
	 * @return
	 */
	public List<Role> getAllRoles();
	
	/**
	 * 支持分页查询
	 * @return
	 */
	public Pager<Role> getAllPagerRoles();
}
