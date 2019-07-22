package cn.abc.sshweb1.service;

import java.util.List;

import cn.abc.sshweb1.model.Pager;
import cn.abc.sshweb1.model.Permission;

public interface PermissionService extends BaseService<Permission>{
	
	public List<Permission> getAllPermissions();
	
	public Pager<Permission> getAllPagerPermission();
	
	/**
	 * 初始化权限时获取到权限的标记list，标记写入到数据库
	 * 对于同样的标记，只写入数据库一次
	 * @param resources
	 */
	public void initPermissions(List<String> resources);
}
