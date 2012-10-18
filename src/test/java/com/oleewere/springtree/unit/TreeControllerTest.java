package com.oleewere.springtree.unit;

import static org.junit.Assert.*;
import java.util.List;
import net.sf.json.JSONObject;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;


import com.oleewere.springtree.domain.Node;
import com.oleewere.springtree.domain.NodeCommand;
import com.oleewere.springtree.jsonsupport.JSONHelper;
import com.oleewere.springtree.services.BinarySearchTreeService;
import com.oleewere.springtree.web.BinaryTreeController;
import com.oleewere.springtree.wssupport.StringCuttingService;

public class TreeControllerTest {
	private static final String numbersParam = "10 12 5";
	@InjectMocks
	private BinaryTreeController underTest = new BinaryTreeController();
	@Mock
	private BinarySearchTreeService binarySearchTreeService;
	@Mock
	private StringCuttingService stringCuttingService;
	@Mock
	private ModelMap model;
	@Mock
	private List<Integer> numbers;
	@Mock
	private Node<Integer> root;
	@Mock
	private NodeCommand nodeCommand;
	@Mock
	private BindingResult result;
	@Mock
	private JSONHelper jsonHelper;
	@Before
	public void setUp() {
	   MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testControllerWithGet() {
		//GIVEN	
		BDDMockito.given(model.addAttribute(Mockito.anyString(),Mockito.anyObject())).willReturn(model);
		//WHEN
		String testResult = underTest.get(model);
		BDDMockito.verify(model,Mockito.times(1)).addAttribute(Mockito.anyString(),Mockito.anyObject());	
		//THAN
		assertEquals("home",testResult);
	}
	
	@Test
	public void testControllerWithPost() {
		//GIVEN
		JSONObject jsonObj = new JSONObject();
		BDDMockito.given(jsonHelper.convertNodeToJSONObject(Mockito.<Node<Integer>>anyObject())).willReturn(jsonObj);
		BDDMockito.given(stringCuttingService.StringToNumbers(numbersParam)).willReturn(numbers);
		BDDMockito.given(binarySearchTreeService.getTreeFromList(numbers)).willReturn(root);
		BDDMockito.given(numbers.isEmpty()).willReturn(false);
		BDDMockito.given(nodeCommand.getNumbers()).willReturn(numbersParam);
		BDDMockito.given(model.addAttribute(Mockito.anyString(),Mockito.anyObject())).willReturn(model);
		//WHEN
		String testResult = underTest.createTree(model, nodeCommand, result);
		
		BDDMockito.verify(jsonHelper, Mockito.times(1)).convertNodeToJSONObject(Mockito.<Node<Integer>>anyObject());
		BDDMockito.verify(stringCuttingService,Mockito.times(1)).StringToNumbers(numbersParam);
		BDDMockito.verify(model,Mockito.times(1)).addAttribute(Mockito.anyString(),Mockito.anyObject());
		BDDMockito.verify(numbers,Mockito.times(1)).isEmpty();
		BDDMockito.verify(nodeCommand, Mockito.times(1)).getNumbers();
		BDDMockito.verify(binarySearchTreeService, Mockito.times(1)).getTreeFromList(numbers);
		
		//THAN
		assertEquals("result",testResult);
	}

	@Test
	public void testControllerWithPostWhenNumbersIsEmpty() throws Exception{
		//GIVEN
		BDDMockito.given(stringCuttingService.StringToNumbers(numbersParam)).willReturn(numbers);
		BDDMockito.given(numbers.isEmpty()).willReturn(true);
		BDDMockito.given(nodeCommand.getNumbers()).willReturn(numbersParam);
		BDDMockito.doNothing().when(result).rejectValue(Mockito.anyString(), Mockito.anyString() , Mockito.anyString());
		//WHEN
		String testResult = underTest.createTree(model, nodeCommand, result);
		
		BDDMockito.verify(stringCuttingService,Mockito.times(1)).StringToNumbers(numbersParam);
		BDDMockito.verify(nodeCommand, Mockito.times(1)).getNumbers();
		BDDMockito.verify(numbers,Mockito.times(1)).isEmpty();
		BDDMockito.verify(result,Mockito.times(1)).rejectValue(Mockito.anyString(), Mockito.anyString() , Mockito.anyString());
		//THAN
		assertEquals("home",testResult);
	}
	
}
