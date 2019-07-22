package cn.abc.sshweb1.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.deser.std.ObjectArrayDeserializer;

import cn.abc.sshweb1.model.Pager;
import cn.abc.sshweb1.model.Permission;

@Repository("permissionDao")
public class PermissionDaoImpl extends BaseDaoImpl<Permission>implements PermissionDao {

	@Override
	public List<Permission> getAllPermissions() {
		String hql="from Permission";
		return super.list(hql, null, null);
	}

	@Override
	public Pager<Permission> getAllPagerPermission() {
		String hql="from Permission";
		return super.find(hql, null, null);
	}

	@Override
	public boolean isExistResouce(String resource) {
		
		String hql="select count(*) from Permission p where p.resource=?0";
		long count = (long) super.queryByHql(hql,new Object[] {resource},null);
		System.out.println(hql);
		return count>0?true:false;
		
	}

}
