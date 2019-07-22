package cn.abc.sshweb1.dao;

public interface BaseDao<T> {
	//Dao: �������ݿ�
	/**
	 * ���
	 * @param t
	 * @return
	 */
	public T add(T t);
	
	/**
	 * ɾ��
	 * @param id
	 */
	public void delete(int id);
	
	/**
	 * �޸�
	 * @param t
	 * @return 
	 */
	public void update(T t);
	
	/**
	 * ��ѯ
	 * @param id
	 * @return
	 */
	public T load(int id);
}
