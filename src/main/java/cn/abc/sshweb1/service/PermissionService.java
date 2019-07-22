package cn.abc.sshweb1.service;

import java.util.List;

import cn.abc.sshweb1.model.Pager;
import cn.abc.sshweb1.model.Permission;

public interface PermissionService extends BaseService<Permission>{
	
	public List<Permission> getAllPermissions();
	
	public Pager<Permission> getAllPagerPermission();
	
	/**
	 * ��ʼ��Ȩ��ʱ��ȡ��Ȩ�޵ı��list�����д�뵽���ݿ�
	 * ����ͬ���ı�ǣ�ֻд�����ݿ�һ��
	 * @param resources
	 */
	public void initPermissions(List<String> resources);
}
