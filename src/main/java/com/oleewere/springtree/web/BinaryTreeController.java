package com.oleewere.springtree.web;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oleewere.springtree.domain.NodeCommand;
import com.oleewere.springtree.services.BinarySearchTreeService;
import com.oleewere.springtree.services.StringCuttingService;

@Controller
@Slf4j
public class BinaryTreeController {

	@Autowired
	private BinarySearchTreeService binarySearchTreeService;

	@Autowired
	private StringCuttingService stringCuttingService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String get(ModelMap model) {
		final NodeCommand nodeCommand = new NodeCommand();
		model.addAttribute("tree", nodeCommand);
		return "home";
		}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String createTree(ModelMap model,
			@ModelAttribute("tree") NodeCommand nodeCommand,
			BindingResult result) {
		final List<Integer> numbers = stringCuttingService.StringToNumbers(nodeCommand.getNumbers());
		if (!numbers.isEmpty()) {
			final JSONObject obj = JSONObject
					.fromObject(binarySearchTreeService
							.getTreeFromList(numbers));
			model.addAttribute("createdTree", obj.toString());
			return "result";
		} else {
			result.rejectValue("numbers", "required" , "Hibás bemenet!");
			return "home";
		}
	}


}
