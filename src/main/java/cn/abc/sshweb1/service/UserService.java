package cn.abc.sshweb1.service;

import java.util.List;

import cn.abc.sshweb1.model.Pager;
import cn.abc.sshweb1.model.User;

public interface UserService extends BaseService<User>{
	
	/**
	 * ��ȡ���е��û�����֧�ַ�ҳ
	 * @return
	 */
	public List<User> getAllUsers();
	
	/**
	 * ��ȡ�û���Ϣ��֧�ַ�ҳ
	 * @return
	 */
	public Pager<User> getAllPagerUsers(User user);
	
	public void updateUser(User user);
	
	/**
	 * �жϵ�½�û���д���û����������Ƿ���ȷ
	 * @param username
	 * @param password
	 * @return
	 */
	public User login(String username, String password);
	
	/**
	 * ͨ���û���ѯ�û��Ƿ����
	 * @param username
	 * @return
	 */
	public User loadUserByUsername(String username);
}
