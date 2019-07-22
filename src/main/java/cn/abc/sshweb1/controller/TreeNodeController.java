package cn.abc.sshweb1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.abc.sshweb1.model.TreeNode;
import cn.abc.sshweb1.service.TreeNodeService;

@Controller
public class TreeNodeController {
	
	@Autowired
	private TreeNodeService treeNodeService;
	
	@ResponseBody
	@RequestMapping(value="/getAllNodes",method=RequestMethod.POST)
	public List<TreeNode> getAllNodes(){
		
		List<TreeNode> nodes=treeNodeService.getAllNode();
		return nodes;
	}
	
	@ResponseBody
	@RequestMapping(value="/getNewestId",method=RequestMethod.POST)
	public int getNewestId() {
		
		return treeNodeService.getNewestId();
	}
	
	@RequestMapping(value="/addNode")
	public String addNode(TreeNode node) {
		System.out.println("node: "+node);
		treeNodeService.add(node);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value="/removeNode",method=RequestMethod.POST)
	public String removeNode(@RequestParam("id") int id) {		
		treeNodeService.delete(id);
		return "success";
	}
}
