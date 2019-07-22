package cn.abc.sshweb1.dao;

import java.util.List;

import cn.abc.sshweb1.model.Pager;
import cn.abc.sshweb1.model.Permission;

public interface PermissionDao extends BaseDao<Permission>{
	
	public List<Permission> getAllPermissions();
	
	public Pager<Permission> getAllPagerPermission();
	
	/**
	 * �ж�resource�Ƿ������ݿ��д��ڣ����ڷ���true
	 * @param resource
	 * @return
	 */
	public boolean isExistResouce(String resource);
}
