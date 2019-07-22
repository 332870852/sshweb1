package cn.abc.sshweb1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.abc.sshweb1.dao.PermissionDao;
import cn.abc.sshweb1.model.Pager;
import cn.abc.sshweb1.model.Permission;

@Service("PermissionService")
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {
	
	@Autowired
	private PermissionDao permissionDao;
	
	@Override
	public List<Permission> getAllPermissions() {
		
		return permissionDao.getAllPermissions();
	}

	@Override
	public Pager<Permission> getAllPagerPermission() {
		
		return permissionDao.getAllPagerPermission();
	}

	@Override
	public void initPermissions(List<String> resources) {
		//ѭ������list����
		//���������У�Ҫ�ж�������ֵ���ǲ��Ǵ��������ڵ�permission����
		//������ڣ���������
		boolean isExist=false;
		for(String resource:resources) {
			 isExist=permissionDao.isExistResouce(resource); //����true����
			 if(!isExist) {//resource ������ִ�в���
				 Permission permission=new Permission();
				 permission.setResource(resource);
				 permission.setState(1);
				 permissionDao.add(permission); //����dao���������ݿ�
				 
			 }
		}
	}

}
