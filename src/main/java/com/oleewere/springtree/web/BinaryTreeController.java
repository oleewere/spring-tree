package com.oleewere.springtree.web;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oleewere.springtree.domain.NodeCommand;
import com.oleewere.springtree.services.BinarySearchTreeService;

@Controller
@Slf4j
public class BinaryTreeController {

	@Autowired
	private BinarySearchTreeService binarySearchTreeService;

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
		final List<Integer> numbers = StringToNumbers(nodeCommand.getNumbers());
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

	private List<Integer> StringToNumbers(String numbers) {
		final List<Integer> nums = new ArrayList<Integer>();
		if (validateNumbers(numbers)) {
			addNumbersToNumList(nums, numbers);
		} else {
			log.error("Hibás szám konvrzió. (A bemenetben )");
		}
		return nums;
	}

	private void addNumbersToNumList(List<Integer> nums, String numbers) {
		final String[] tokens = numbers.split(" ");
		for (String token : tokens) {
			nums.add(NumberUtils.toInt(token));
		}
	}

	private boolean validateNumbers(String numbers) {
		if (StringUtils.isNumericSpace(numbers) && !StringUtils.isEmpty(numbers)) {
			log.info("Valid formátum a számok konverziójára.");
			return true;
		} else {
			return false;
		}
	}

}
