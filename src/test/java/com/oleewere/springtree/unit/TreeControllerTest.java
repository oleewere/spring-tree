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
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
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

	@InjectMocks
	private BinaryTreeController underTest = PowerMockito.spy(new BinaryTreeController());
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
	
	private String numbersParam = "10 12 5";
	@Before
	public void setUp() {
	   MockitoAnnotations.initMocks(this);
       model = new ModelMap();
	}
	
	@Test
	public void testControllerWithGet(){
		assertEquals("home",underTest.get(model));
	}
	
	@Test
	public void testControllerWithPost() throws Exception {
		//GIVEN
		PowerMockito.doReturn(numbers).when(underTest,"StringToNumbers",numbersParam);
		//PowerMockito.mockStatic(JSONObject.class);
		//BDDMockito.given(JSONObject.fromObject("{\"data\": }")).willReturn(jsonObj);
		//BDDMockito.given(JSONObject.fromObject(BDDMockito.given(binarySearchTreeService.getTreeFromList(numbers)).willReturn(root))).willReturn(jsonObj);
		//Mockito.when(JSONObject.fromObject("{\"data\": }")).thenReturn(jsonObj);
		//BDDMockito.given(jsonObj.toString()).willReturn("{\"data\": }");
		BDDMockito.given(binarySearchTreeService.getTreeFromList(numbers)).willReturn(root);
		BDDMockito.given(numbers.isEmpty()).willReturn(false);
		BDDMockito.given(nodeCommand.getNumbers()).willReturn(numbersParam);
		//WHEN
		String testResult = underTest.createTree(model, nodeCommand, result);
		
		PowerMockito.verifyPrivate(underTest,Mockito.times(1)).invoke("StringToNumbers",numbersParam);
		//PowerMockito.verifyStatic(Mockito.times(1));
		//JSONObject.fromObject(jsonObj);
		//BDDMockito.verify(jsonObj.toString());
		BDDMockito.verify(numbers,Mockito.times(1)).isEmpty();
		BDDMockito.verify(nodeCommand, Mockito.times(1)).getNumbers();
		BDDMockito.verify(binarySearchTreeService, Mockito.times(1)).getTreeFromList(numbers);
		
		//THAN
		assertEquals("result",testResult);	
	}
	
}
