package com.epam.springtree.web;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.epam.springtree.domain.NodeCommand;
import com.epam.springtree.services.BinarySearchTreeService;

@Controller
public class BinaryTreeController {
	
	@Autowired
	private BinarySearchTreeService binarySearchTreeService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String get(ModelMap model){
		NodeCommand nodeCommand = new NodeCommand();	
		model.addAttribute("tree", nodeCommand);	
		return "home";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createTree(ModelMap model, @ModelAttribute("tree") NodeCommand nodeCommand, BindingResult result){
		final List<Integer>numbers = StringToNumbers(nodeCommand.getNumbers());
		final JSONObject obj = JSONObject.fromObject(binarySearchTreeService.getTreeFromList(numbers));
		model.addAttribute("createdTree", obj);
		return "result";		
	}

	private List<Integer> StringToNumbers(String numbers) {
		final List<Integer> nums = new ArrayList<Integer>();
		nums.add(10);
		nums.add(20);
		nums.add(2);
		nums.add(4);
		nums.add(8);
		nums.add(1);
		nums.add(22);
		nums.add(19);
		return nums;
	}
	
}
