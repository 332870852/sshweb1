package cn.abc.sshweb1.service;

public interface BaseService<T> {
	
	/**
	 * Ìí¼Ó
	 * @param t
	 * @return
	 */
	public T add(T t);
	
	/**
	 * É¾³ı
	 * @param id
	 */
	public void delete(int id);
	
	/**
	 * ĞŞ¸Ä
	 * @param t
	 */
	public void update(T t);
	
	/**
	 * ²éÑ¯
	 * @param id
	 * @return
	 */
	public T load(int id);
}
