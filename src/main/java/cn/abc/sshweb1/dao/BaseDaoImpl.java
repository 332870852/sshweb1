package cn.abc.sshweb1.dao;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import cn.abc.sshweb1.model.Pager;
import cn.abc.sshweb1.model.SystemContext;

/**
 * BaseDaoImpl:操作数据库的基本方法代码
 * @author 何旭杰
 *
 * @param <T>
 */
public class BaseDaoImpl<T> implements BaseDao<T> {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Class<?>clazz;
	/**
	 * 获取连接数据库的基本方法
	 */
	public Session getSession() {	
		return sessionFactory.getCurrentSession();
	}
	
	public Class<?> getClazz(){		
		if(clazz==null) {
			clazz=((Class<?>) ((((ParameterizedType)
					(this.getClass().getGenericSuperclass())))
					.getActualTypeArguments()[0]));	
		}
		return clazz;
	}	
	
	@Override
	public T add(T t) {
		getSession().save(t);
		return t;
	}

	@Override
	public void delete(int id) {
		getSession().delete(load(id));
	}

	@Override
	public void update(T t) {		
		getSession().update(t);
		
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public T load(int id) {		
		return (T) getSession().get(getClazz(), id);
		//return (T) getSession().load(getClazz(), id);
	}
	
	/**
	 * 用hql语句查询多条记录，没有分页，list返回
	 * @param hql
	 * @param objs	替换hql语句中？占位符的实参
	 * @param alias	替换hql语句中:name占位符的实参
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> list(String hql,Object[] objs,Map<String,Object> alias){
		//给hql语句加排序规则
		hql=initSort(hql);//初始化排序规则
		Query<T> query=getSession().createQuery(hql);
		setParameter(query,objs); //将hql里的？占位符替换
		setAliasParameter(query,alias);
		return query.list();
	}
	
	/**
	 * 替换hql语句中:name占位符的实参
	 * @param query
	 * @param alias
	 */
	@SuppressWarnings("rawtypes")
	private void setAliasParameter(Query<T> query, Map<String, Object> alias) {
		if(alias!=null) {
			Set<String> keys=alias.keySet();
			for(String key:keys) {
				Object val=alias.get(key);
				if(val instanceof Collection) {//判断是不是一个集合
					query.setParameterList(key, (Collection)val);
				}else {//不是集合
					query.setParameter(key, val);
				}
			}
		}		
}

	/**
	 * /将hql里的？占位符替换
	 * @param query
	 * @param objs
	 */
	private void setParameter(Query<T> query, Object[] objs) {
		if(objs!=null&&objs.length>0) {
			int index=0;
			for(Object obj:objs) {
				System.out.println(obj);
				query.setParameter(index++, obj);
			}
		}
		
	}

	
	/**
	 * 排序
	 * @param hql
	 * @return
	 */
	private String initSort(String hql) {			
		String sort=SystemContext.getSort(); //数据可以在Controller放
		String order=SystemContext.getOrder();		
				if(sort!=null&&sort.trim().equals("")) {
					hql+=" order by "+sort;
					if(!"desc".equals(order)) {
						hql+=" asc";
					}else {
						hql+=" desc";
					}
					
				}
			return hql;				
	}

	
	/**
	 * 支持分页的查询多条数据
	 * @param query
	 * @param alias
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Pager<T> find(String hql, Map<String, Object>[] objs, Map<String, Object> alias){
		
		hql=initSort(hql);//初始化排序规则
		Query<T> query=getSession().createQuery(hql);
		setParameter(query,objs); //将hql里的？占位符替换
		setAliasParameter(query,alias);
		Pager<T> pager=new Pager<>();
		setPager(query,pager);
		
		List<T>datas=query.list();
		pager.setRows(datas);
		//还需要一个查询的总条数 select count(*)  from users where id>15
		String countHql=getCountHql(hql);
		Query countQuery=getSession().createQuery(countHql);
		setParameter(query,objs); //将hql里的？占位符替换
		setAliasParameter(query,alias);
		long total=(long) countQuery.uniqueResult();
		pager.setTotal(total);
		return pager;
	}

	private String getCountHql(String hql) {
		String hhql=hql.substring(hql.indexOf("from")); //拿到hql语句的from开始的后半部分：from users where id >15
		String countHql="select count(*) "+hhql;
		//hql语句，fetch
		countHql=countHql.replace("fetch", "");
		return countHql;
	}

	private void setPager(Query<T> query, Pager<T> pager) {
		Integer pageSize=SystemContext.getPageSize();
		Integer pageOffset=SystemContext.getPageOffset();
		if(pageOffset==null||pageOffset<0) {	
			pageOffset=0;	//pageOffset的默认值
		}
		if(pageSize==null||pageSize<0) {
			pageSize=15;	//pageSize的默认值，默认显示15条
		}
		
		pager.setOffset(pageOffset);
		pager.setSize(pageSize);
		
		//data数据，也就是分页的数据 select * from users where id>15 limit 0,15 一张页面上显示的数据
		query.setFirstResult(pageOffset).setMaxResults(pageSize);
		
	}
	
	//使用sql语句查询的方法，list ，pager
	
	/**
	 * 	针对一些特殊的查询
	 * @param hql
	 * @param objs
	 * @param alias
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object queryByHql(String hql, Object[] objs,Map<String, Object> alias) {
		
		Query query=getSession().createQuery(hql);
		setParameter(query,objs); //将hql里的？占位符替换
		setAliasParameter(query,alias);
		
		return query.uniqueResult();
	}
	
	/**
	 * 应对某些特殊的方式更新数据
	 * @param hql
	 * @param objs
	 * @param alias
	 */
	@SuppressWarnings("unchecked")
	public void updateByHql(String hql, Map<String, Object>[] objs, Map<String, Object> alias) {
		
		Query<T> query=getSession().createQuery(hql);
		setParameter(query,objs); //将hql里的？占位符替换
		setAliasParameter(query,alias);
		query.executeUpdate();
	}
	
}


