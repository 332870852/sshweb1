package cn.abc.sshweb1.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.abc.sshweb1.model.TreeNode;

@Repository("treeNodeDao")
public class TreeNodeDaoImpl extends BaseDaoImpl<TreeNode> implements TreeNodeDao {


	@Override
	public List<TreeNode> getAllNode() {
		String hql="from TreeNode";
		return super.list(hql, null, null);
	}

	@Override
	public int getNewestId() {
		String hql="select max(id) from TreeNode";
		return Integer.parseInt(super.queryByHql(hql, null, null).toString());
		
	}

}
