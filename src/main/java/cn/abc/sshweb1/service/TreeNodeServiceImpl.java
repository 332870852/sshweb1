package cn.abc.sshweb1.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.abc.sshweb1.dao.TreeNodeDao;
import cn.abc.sshweb1.model.TreeNode;

@Service("treeNodeService")
public class TreeNodeServiceImpl  extends BaseServiceImpl<TreeNode>implements TreeNodeService {

	@Autowired
	private TreeNodeDao treeNodeDao;
	/**
	 * 实现节点的层次结构，返回一个有层次结构的list集合
	 */
	@Override
	public List<TreeNode> getAllNode() {
		//获取到了所有的节点 ,每个节点对象children都是空的
		List<TreeNode> nodes=treeNodeDao.getAllNode();
		//区分出父节点，把对象字节点放到对应的父节点的children属性里去
		List<TreeNode> parents =new ArrayList<>(); //这个集合，把没有父节点的根节点放进去
		Map<Integer,TreeNode>map=new HashMap<>();  //nodes所有的节点对象都放进去
		
		for(TreeNode node:nodes) {
			map.put(node.getId(), node);
			if(node.getParentId()==null) {
				parents.add(node);
			}
		}
		
		for(TreeNode node:nodes) {
			TreeNode parent=map.get(node.getParentId()); //null
			if(parent!=null) {
				parent.getChildren().add(node);
			}
		}
		//System.out.println("parents: "+parents);
		return parents;
	}

	@Override
	public int getNewestId() {		
		return treeNodeDao.getNewestId();
	}

}
