package cn.abc.sshweb1.service;

import java.util.List;

import cn.abc.sshweb1.model.Pager;
import cn.abc.sshweb1.model.Role;

public interface RoleService extends BaseService<Role>{
	
	/**
	 * ��ȡ���н�ɫ��Ϣ
	 * @return
	 */
	public List<Role> getAllRoles();
	
	/**
	 * ֧�ַ�ҳ��ѯ
	 * @return
	 */
	public Pager<Role> getAllPagerRoles();
}
