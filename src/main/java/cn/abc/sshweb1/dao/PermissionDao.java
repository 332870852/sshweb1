package cn.abc.sshweb1.dao;

import java.util.List;

import cn.abc.sshweb1.model.Pager;
import cn.abc.sshweb1.model.Permission;

public interface PermissionDao extends BaseDao<Permission>{
	
	public List<Permission> getAllPermissions();
	
	public Pager<Permission> getAllPagerPermission();
	
	/**
	 * 判断resource是否在数据库中存在，存在返回true
	 * @param resource
	 * @return
	 */
	public boolean isExistResouce(String resource);
}
