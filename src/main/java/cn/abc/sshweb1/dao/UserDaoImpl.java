package cn.abc.sshweb1.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.abc.sshweb1.model.Pager;
import cn.abc.sshweb1.model.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	@Override
	public List<User> getAllUsers() {
		String hql="from User";
		return super.list(hql, null, null);
	}

	@Override
	public Pager<User> getAllPagerUsers(User user) {
		String hql="from User u where 1=1";
		if(user.getId()>0)
			hql+=" and u.id="+user.getId();
		if(user.getUsername()!=null&&!"".equals(user.getUsername())) 
			hql+=" and u.username like '%"+user.getUsername()+"%'";
		return super.find(hql, null, null);
	}

	@Override
	public User loadUserByUsername(String username) {
		String hql="select u from User u where u.username=?0";
		return (User) super.queryByHql(hql, new Object[] {username}, null);
	}


}
