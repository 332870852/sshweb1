package cn.abc.sshweb1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.abc.sshweb1.dao.RoleDao;
import cn.abc.sshweb1.model.Pager;
import cn.abc.sshweb1.model.Role;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

	@Autowired
	private RoleDao roleDao;
	
	@Override
	public List<Role> getAllRoles() {
		
		return roleDao.getAllRoles();
	}

	@Override
	public Pager<Role> getAllPagerRoles() {
		
		return roleDao.getAllPagerRoles();
	}

}
