package cn.abc.sshweb1.dao;

import java.util.List;

import cn.abc.sshweb1.model.Pager;
import cn.abc.sshweb1.model.User;

public interface UserDao extends BaseDao<User>{
	
	/**
	 * 获取所有的用户，不支持分页
	 * @return
	 */
	public List<User> getAllUsers();
	
	/**
	 * 获取用户信息，支持分页
	 * @return
	 */
	public Pager<User> getAllPagerUsers(User user);
	
	/**
	 * 通过用户查询用户是否存在
	 * @param username
	 * @return
	 */
	public User loadUserByUsername(String username);
}
