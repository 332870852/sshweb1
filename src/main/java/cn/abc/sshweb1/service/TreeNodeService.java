package cn.abc.sshweb1.service;

import java.util.List;

import cn.abc.sshweb1.model.TreeNode;

public interface TreeNodeService extends BaseService<TreeNode>{
	
	//��ȡ���е�j�ڵ����
	public List<TreeNode> getAllNode();
		
	//��ȡ���µĽڵ�idֵ�����нڵ�id���ֵ
	public int getNewestId();	
}
