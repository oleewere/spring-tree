package com.oleewere.springtree.unit;

import static org.junit.Assert.*;
import java.util.List;

import net.sf.json.JSONObject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;


import com.oleewere.springtree.domain.Node;
import com.oleewere.springtree.domain.NodeCommand;
import com.oleewere.springtree.services.BinarySearchTreeService;
import com.oleewere.springtree.web.BinaryTreeController;

@RunWith(PowerMockRunner.class)
@PrepareForTest({BinaryTreeController.class,JSONObject.class})
public class TreeControllerTest {
	private static final String numbersParam = "10 12 5";
	@InjectMocks
	private BinaryTreeController underTest = PowerMockito.spy(new BinaryTreeController());
	@Mock
	private ModelMap model;
	
	@Mock
	private BinarySearchTreeService binarySearchTreeService;
	@Mock
	private List<Integer> numbers;
	@Mock
	private Node<Integer> root;
	@Mock
	private NodeCommand nodeCommand;
	@Mock
	private BindingResult result;
	@Mock
	private JSONObject jsonObj;
	
	@Before
	public void setUp() {
	   MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testControllerWithGet() throws Exception{
		//GIVEN
		PowerMockito.whenNew(NodeCommand.class).withNoArguments().thenReturn(nodeCommand);
		BDDMockito.given(model.addAttribute(Mockito.anyString(),Mockito.anyObject())).willReturn(model);
		//WHEN
		String testResult = underTest.get(model);
		PowerMockito.verifyNew(NodeCommand.class).withNoArguments();
		BDDMockito.verify(model,Mockito.times(1)).addAttribute(Mockito.anyString(),Mockito.anyObject());	
		//THAN
		assertEquals("home",testResult);
	}
	
	@Test
	public void testControllerWithPost() throws Exception {
		//GIVEN
		PowerMockito.doReturn(numbers).when(underTest,"StringToNumbers",numbersParam);
		PowerMockito.mockStatic(JSONObject.class);
		BDDMockito.given(JSONObject.fromObject(Mockito.anyObject())).willReturn(jsonObj);
		BDDMockito.given(binarySearchTreeService.getTreeFromList(numbers)).willReturn(root);
		BDDMockito.given(numbers.isEmpty()).willReturn(false);
		BDDMockito.given(nodeCommand.getNumbers()).willReturn(numbersParam);
		BDDMockito.given(model.addAttribute(Mockito.anyString(),Mockito.anyObject())).willReturn(model);
		//WHEN
		String testResult = underTest.createTree(model, nodeCommand, result);
		
		PowerMockito.verifyPrivate(underTest,Mockito.times(1)).invoke("StringToNumbers",numbersParam);
		PowerMockito.verifyStatic(Mockito.times(1));
		JSONObject.fromObject(root);
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
		PowerMockito.doReturn(numbers).when(underTest,"StringToNumbers",numbersParam);
		BDDMockito.given(numbers.isEmpty()).willReturn(true);
		BDDMockito.given(nodeCommand.getNumbers()).willReturn(numbersParam);
		BDDMockito.doNothing().when(result).rejectValue(Mockito.anyString(), Mockito.anyString() , Mockito.anyString());
		//WHEN
		String testResult = underTest.createTree(model, nodeCommand, result);
		
		PowerMockito.verifyPrivate(underTest,Mockito.times(1)).invoke("StringToNumbers",numbersParam);
		BDDMockito.verify(nodeCommand, Mockito.times(1)).getNumbers();
		BDDMockito.verify(numbers,Mockito.times(1)).isEmpty();
		BDDMockito.verify(result,Mockito.times(1)).rejectValue(Mockito.anyString(), Mockito.anyString() , Mockito.anyString());
		//THAN
		assertEquals("home",testResult);
	}
	
	@Test
	public void testStringToNumbersPrivateMethod(){
		
	}
	
}
