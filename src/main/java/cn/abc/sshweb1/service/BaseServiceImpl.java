package cn.abc.sshweb1.service;

import org.springframework.beans.factory.annotation.Autowired;

import cn.abc.sshweb1.dao.BaseDao;

public class BaseServiceImpl<T> implements BaseService<T> {
	
	@Autowired
	protected BaseDao<T> baseDao;
	
	
	@Override
	public T add(T t) {
		return baseDao.add(t);
	}

	@Override
	public void delete(int id) {
		baseDao.delete(id);
	}

	@Override
	public void update(T t) {
		baseDao.update(t);
	}

	@Override
	public T load(int id) {
		
		return baseDao.load(id);
	}

}
