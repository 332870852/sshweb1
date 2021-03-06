package cn.abc.sshweb1.service;

public interface BaseService<T> {
	
	/**
	 * ����
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
	 */
	public void update(T t);
	
	/**
	 * ��ѯ
	 * @param id
	 * @return
	 */
	public T load(int id);
}
