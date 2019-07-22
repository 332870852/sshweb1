package cn.abc.sshweb1.dao;

import java.util.List;

import cn.abc.sshweb1.model.Pager;
import cn.abc.sshweb1.model.User;

public interface UserDao extends BaseDao<User>{
	
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
	
	/**
	 * ͨ���û���ѯ�û��Ƿ����
	 * @param username
	 * @return
	 */
	public User loadUserByUsername(String username);
}
