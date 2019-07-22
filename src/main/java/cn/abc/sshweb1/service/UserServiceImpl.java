package cn.abc.sshweb1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.abc.sshweb1.dao.UserDao;
import cn.abc.sshweb1.model.Pager;
import cn.abc.sshweb1.model.User;
import cn.abc.sshweb1.utils.SecurityUtil;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User>implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public List<User> getAllUsers() {
		
		return userDao.getAllUsers();
	}

	@Override
	public Pager<User> getAllPagerUsers(User user) {
		
		return userDao.getAllPagerUsers(user);
	}

	@Override
	public void updateUser(User user) {
		int userid=user.getId();
		User userDb=userDao.load(userid); //�����ݿ����õ�Ԥ�޸ĵĶ���
		/*userDb.setUsername(user.getUsername());*/
		userDb.setRegDate(user.getRegDate());
		userDb.setRoles(user.getRoles());
		userDb.setState(user.getState());
		if(user.getPassword()==null||user.getPassword().equals("")) {//����Ϊ��
			
		}else {
			/*userDb.setPassword(SecurityUtil.md5(user.getUsername(),user.getPassword()));*/ //md5����
			userDb.setPassword(user.getPassword());
		}
		 userDao.update(userDb);;
	}

	@Override
	public User login(String username, String password) {
		//���ݴ�������usernameȥ���ݿ����ң��Ƿ����
		User user=userDao.loadUserByUsername(username);
		/*if(user==null) throw new RuntimeException("�û������������!");
		if(!password.equals(user.getPassword()))  throw new RuntimeException("�û������������!");
		if(user.getState()!=1) throw new RuntimeException("���û��ѱ�����!");*/
		if(user==null) throw new RuntimeException("�û������������!");
		if(!password.equals(user.getPassword()))  throw new RuntimeException("�û������������!");
		if(user.getState()!=1) throw new RuntimeException("���û��ѱ�����!");
		return user;
	}

	@Override
	public User loadUserByUsername(String username) {
		
		return userDao.loadUserByUsername(username);
	}

	

}
