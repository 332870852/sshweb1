package cn.abc.sshweb1.dao;

public interface BaseDao<T> {
	//Dao: 操作数据库
	/**
	 * 添加
	 * @param t
	 * @return
	 */
	public T add(T t);
	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(int id);
	
	/**
	 * 修改
	 * @param t
	 * @return 
	 */
	public void update(T t);
	
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	public T load(int id);
}
