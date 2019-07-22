package cn.abc.sshweb1.dao;

import java.util.List;

import cn.abc.sshweb1.model.TreeNode;

public interface TreeNodeDao extends BaseDao<TreeNode>{
	
	//获取所有的j节点对象
	public List<TreeNode> getAllNode();
	
	//获取最新的节点id值，所有节点id最大值
	public int getNewestId();	
	
}
