package cn.abc.sshweb1.service;

import java.util.List;

import cn.abc.sshweb1.model.Pager;
import cn.abc.sshweb1.model.User;

public interface UserService extends BaseService<User>{
	
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
	
	public void updateUser(User user);
	
	/**
	 * 判断登陆用户填写的用户名和密码是否正确
	 * @param username
	 * @param password
	 * @return
	 */
	public User login(String username, String password);
	
	/**
	 * 通过用户查询用户是否存在
	 * @param username
	 * @return
	 */
	public User loadUserByUsername(String username);
}
