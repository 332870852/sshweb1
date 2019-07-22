package cn.abc.sshweb1.model;

/**
 * ThreadLocal:数据共享，本地线程变量里的数据，每个线程中创建副本，在多条线程中，给这个变量赋值，
 * 每条线程使用不会冲突
 * 进行分页信息通信
 * 排序信息的通信
 * @author 何旭杰
 *
 */
public class SystemContext {
	
	//分页大小,每页显示的数据条数
	private static ThreadLocal<Integer> pageSize=new ThreadLocal<>();
	//每一页的起始的条数索引
	private static ThreadLocal<Integer> pageOffset=new ThreadLocal<>();
	//排序的字段
	private static ThreadLocal<String> sort=new ThreadLocal<>();
	//排序方式
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
	
	//移除
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
