package cn.abc.sshweb1.model;

/**
 * ThreadLocal:���ݹ��������̱߳���������ݣ�ÿ���߳��д����������ڶ����߳��У������������ֵ��
 * ÿ���߳�ʹ�ò����ͻ
 * ���з�ҳ��Ϣͨ��
 * ������Ϣ��ͨ��
 * @author �����
 *
 */
public class SystemContext {
	
	//��ҳ��С,ÿҳ��ʾ����������
	private static ThreadLocal<Integer> pageSize=new ThreadLocal<>();
	//ÿһҳ����ʼ����������
	private static ThreadLocal<Integer> pageOffset=new ThreadLocal<>();
	//������ֶ�
	private static ThreadLocal<String> sort=new ThreadLocal<>();
	//����ʽ
	private static ThreadLocal<String> order=new ThreadLocal<>();
	
	public static Integer getPageSize() {
		return pageSize.get();
	}
	
	public static void setPageSize(Integer _pageSize) {
		pageSize.set(_pageSize);
	}
	
	public static Integer getPageOffset() {
		return pageOffset.get();
	}
	
	public static void setPageOffset(Integer _pageOffset) {
		 pageOffset.set(_pageOffset);
	}
	
	public static String getSort() {
		return sort.get();
	}
	
	public static void setSort(String _sort) {
		 sort.set(_sort);
	}
	
	public static String getOrder() {
		return order.get();
	}
	
	public static void setOrder(String _order) {
		 order.set(_order);
	}
	
	//�Ƴ�
	public static void removePageOffset() {
		pageOffset.remove();
	}
	
	public static void removeSort() {
		sort.remove();
	}
	
	public static void removePageSize() {
		pageSize.remove();
	}
	
	public static void removeOrder() {
		order.remove();
	}
	
}
