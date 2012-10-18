package com.oleewere.springtree.unit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import com.oleewere.springtree.domain.Node;
import com.oleewere.springtree.jsonsupport.JSONHelper;
import com.oleewere.springtree.jsonsupport.WrappedList;
import com.oleewere.springtree.nodeservices.NodeComparator;
import com.oleewere.springtree.nodeservices.NodeHelper;
import com.oleewere.springtree.ws.BinarySearchTreeWebService;

public class TreeWebServiceTest {
 @InjectMocks
 private BinarySearchTreeWebService underTest = new BinarySearchTreeWebService();
 @Mock
 private WrappedList wl;
 @Mock
 private Node<Integer> node;
 @Mock
 private NodeHelper nodeHelper;
 @Mock
 private JSONHelper jsonHelper;
 @BeforeClass
 public static void setUpTestObject(){
 }
 @Before
 public void setUp(){
	 MockitoAnnotations.initMocks(this);
 }

 @Test
 public void testRestService() throws Exception{
	 //GIVEN
     BDDMockito.given(nodeHelper.buildBinarySearchTree(
    		 Mockito.<WrappedList>anyObject(), 
    		 Mockito.<NodeComparator<Integer>>anyObject()))
    		 .willReturn(node);
     BDDMockito.given(jsonHelper.NodeToJson(Mockito.<Node<Integer>>anyObject())).willReturn("data");
	 //WHEN
	 String result = underTest.restService(wl);
	 
	 BDDMockito.verify(jsonHelper,times(1)).NodeToJson(Mockito.<Node<Integer>>anyObject());
	 BDDMockito.verify(nodeHelper,times(1)).buildBinarySearchTree(Mockito.<WrappedList>anyObject(), 
    		 Mockito.<NodeComparator<Integer>>anyObject());
	 //THAN
	 assertEquals("data",result);
	  
 }
}
