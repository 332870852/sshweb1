package cn.abc.sshweb1.model;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
	
	private int id;
	private String text;
	private List<TreeNode> children= new ArrayList<>();
	private Integer parentId;  			//¸¸½Úµã
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	@Override
	public String toString() {
		return "TreeNode [id=" + id + ", text=" + text + ", children=" + children + ", parentId=" + parentId + "]";
	}
	
	
}
