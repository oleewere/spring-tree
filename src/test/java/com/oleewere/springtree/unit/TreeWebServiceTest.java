package com.oleewere.springtree.unit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.oleewere.springtree.domain.Node;
import com.oleewere.springtree.domain.NodeComparator;
import com.oleewere.springtree.jsonsupport.WrappedList;
import com.oleewere.springtree.ws.BinarySearchTreeWebService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({BinarySearchTreeWebService.class, Node.class})
public class TreeWebServiceTest {
 private BinarySearchTreeWebService underTest = PowerMockito.spy(new BinarySearchTreeWebService());
 @Mock
 private WrappedList wl;
 @Mock
 private NodeComparator<Integer> nodeComparator;
 @Mock
 private Node<Integer> node;
 @Before
 public void setUp(){
	 MockitoAnnotations.initMocks(this);
 }

 @Test
 public void testRestService() throws Exception{
	 //GIVEN
	 PowerMockito.mockStatic(Node.class);
	 BDDMockito.given(Node.buildBinarySearchTree(wl, nodeComparator)).willReturn(node);
	 PowerMockito.whenNew(NodeComparator.class).withNoArguments().thenReturn(nodeComparator);
	 PowerMockito.doReturn("data").when(underTest,"NodeToJson",node);
	 //WHEN
	 String result = underTest.restService(wl);
	 
	 PowerMockito.verifyStatic(Mockito.times(1));
	 Node.buildBinarySearchTree(wl, nodeComparator);
	 PowerMockito.verifyPrivate(underTest,Mockito.times(1)).invoke("NodeToJson",node);
	 PowerMockito.verifyNew(NodeComparator.class).withNoArguments();
	 //THAN
	 assertEquals("data",result);
	  
 }
}
