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
 * BaseDaoImpl:�������ݿ�Ļ�����������
 * @author �����
 *
 * @param <T>
 */
public class BaseDaoImpl<T> implements BaseDao<T> {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Class<?>clazz;
	/**
	 * ��ȡ�������ݿ�Ļ�������
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
	 * ��hql����ѯ������¼��û�з�ҳ��list����
	 * @param hql
	 * @param objs	�滻hql����У�ռλ����ʵ��
	 * @param alias	�滻hql�����:nameռλ����ʵ��
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> list(String hql,Object[] objs,Map<String,Object> alias){
		//��hql�����������
		hql=initSort(hql);//��ʼ���������
		Query<T> query=getSession().createQuery(hql);
		setParameter(query,objs); //��hql��ģ�ռλ���滻
		setAliasParameter(query,alias);
		return query.list();
	}
	
	/**
	 * �滻hql�����:nameռλ����ʵ��
	 * @param query
	 * @param alias
	 */
	@SuppressWarnings("rawtypes")
	private void setAliasParameter(Query<T> query, Map<String, Object> alias) {
		if(alias!=null) {
			Set<String> keys=alias.keySet();
			for(String key:keys) {
				Object val=alias.get(key);
				if(val instanceof Collection) {//�ж��ǲ���һ������
					query.setParameterList(key, (Collection)val);
				}else {//���Ǽ���
					query.setParameter(key, val);
				}
			}
		}		
}

	/**
	 * /��hql��ģ�ռλ���滻
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
	 * ����
	 * @param hql
	 * @return
	 */
	private String initSort(String hql) {			
		String sort=SystemContext.getSort(); //���ݿ�����Controller��
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
	 * ֧�ַ�ҳ�Ĳ�ѯ��������
	 * @param query
	 * @param alias
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Pager<T> find(String hql, Map<String, Object>[] objs, Map<String, Object> alias){
		
		hql=initSort(hql);//��ʼ���������
		Query<T> query=getSession().createQuery(hql);
		setParameter(query,objs); //��hql��ģ�ռλ���滻
		setAliasParameter(query,alias);
		Pager<T> pager=new Pager<>();
		setPager(query,pager);
		
		List<T>datas=query.list();
		pager.setRows(datas);
		//����Ҫһ����ѯ�������� select count(*)  from users where id>15
		String countHql=getCountHql(hql);
		Query countQuery=getSession().createQuery(countHql);
		setParameter(query,objs); //��hql��ģ�ռλ���滻
		setAliasParameter(query,alias);
		long total=(long) countQuery.uniqueResult();
		pager.setTotal(total);
		return pager;
	}

	private String getCountHql(String hql) {
		String hhql=hql.substring(hql.indexOf("from")); //�õ�hql����from��ʼ�ĺ�벿�֣�from users where id >15
		String countHql="select count(*) "+hhql;
		//hql��䣬fetch
		countHql=countHql.replace("fetch", "");
		return countHql;
	}

	private void setPager(Query<T> query, Pager<T> pager) {
		Integer pageSize=SystemContext.getPageSize();
		Integer pageOffset=SystemContext.getPageOffset();
		if(pageOffset==null||pageOffset<0) {	
			pageOffset=0;	//pageOffset��Ĭ��ֵ
		}
		if(pageSize==null||pageSize<0) {
			pageSize=15;	//pageSize��Ĭ��ֵ��Ĭ����ʾ15��
		}
		
		pager.setOffset(pageOffset);
		pager.setSize(pageSize);
		
		//data���ݣ�Ҳ���Ƿ�ҳ������ select * from users where id>15 limit 0,15 һ��ҳ������ʾ������
		query.setFirstResult(pageOffset).setMaxResults(pageSize);
		
	}
	
	//ʹ��sql����ѯ�ķ�����list ��pager
	
	/**
	 * 	���һЩ����Ĳ�ѯ
	 * @param hql
	 * @param objs
	 * @param alias
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object queryByHql(String hql, Object[] objs,Map<String, Object> alias) {
		
		Query query=getSession().createQuery(hql);
		setParameter(query,objs); //��hql��ģ�ռλ���滻
		setAliasParameter(query,alias);
		
		return query.uniqueResult();
	}
	
	/**
	 * Ӧ��ĳЩ����ķ�ʽ��������
	 * @param hql
	 * @param objs
	 * @param alias
	 */
	@SuppressWarnings("unchecked")
	public void updateByHql(String hql, Map<String, Object>[] objs, Map<String, Object> alias) {
		
		Query<T> query=getSession().createQuery(hql);
		setParameter(query,objs); //��hql��ģ�ռλ���滻
		setAliasParameter(query,alias);
		query.executeUpdate();
	}
	
}


