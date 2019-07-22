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
		//循环遍历list集合
		//遍历过程中，要判断这个标记值，是不是存在于现在的permission表中
		//如果存在，放弃插入
		boolean isExist=false;
		for(String resource:resources) {
			 isExist=permissionDao.isExistResouce(resource); //返回true存在
			 if(!isExist) {//resource 不存在执行插入
				 Permission permission=new Permission();
				 permission.setResource(resource);
				 permission.setState(1);
				 permissionDao.add(permission); //调用dao来插入数据库
				 
			 }
		}
	}

}
