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
	 * ʵ�ֽڵ�Ĳ�νṹ������һ���в�νṹ��list����
	 */
	@Override
	public List<TreeNode> getAllNode() {
		//��ȡ�������еĽڵ� ,ÿ���ڵ����children���ǿյ�
		List<TreeNode> nodes=treeNodeDao.getAllNode();
		//���ֳ����ڵ㣬�Ѷ����ֽڵ�ŵ���Ӧ�ĸ��ڵ��children������ȥ
		List<TreeNode> parents =new ArrayList<>(); //������ϣ���û�и��ڵ�ĸ��ڵ�Ž�ȥ
		Map<Integer,TreeNode>map=new HashMap<>();  //nodes���еĽڵ���󶼷Ž�ȥ
		
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
