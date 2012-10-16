package com.oleewere.springtree.services;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StringCuttingService {
	public List<Integer> StringToNumbers(String numbers) {
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
		if (StringUtils.isNumericSpace(numbers)
				&& !StringUtils.isEmpty(numbers)) {
			log.info("Valid formátum a számok konverziójára.");
			return true;
		} else {
			return false;
		}
	}
}
