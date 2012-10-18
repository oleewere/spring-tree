package com.oleewere.springtree.unit;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.oleewere.springtree.domain.Node;
import com.oleewere.springtree.jsonsupport.JSONHelper;
import com.oleewere.springtree.jsonsupport.WrappedList;
import com.oleewere.springtree.services.BinarySearchTreeWebServiceImpl;
import com.oleewere.springtree.wssupport.WsSupport;

public class TreeWebServiceClientTest {

	private List<Integer> numbers = Arrays.asList(1,2,3,4);
	private static final String RESPONSE = "response";
	@InjectMocks
	private BinarySearchTreeWebServiceImpl underTest = new BinarySearchTreeWebServiceImpl();
	@Mock
	private RestTemplate restTemplate;
	@Mock
	private WrappedList wl;
	@Mock 
	private JSONHelper jsonHelper;
	@Mock
	private WsSupport wsSupport;
	@Mock
	private Node<Integer> node;
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	@Test
	public void testRestTemplate(){
		//GIVEN
		BDDMockito.given(restTemplate.postForObject(anyString(), anyList(), eq(String.class))).willReturn(RESPONSE);
		BDDMockito.given(wsSupport.fillWrappedListWithIntegers(wl, numbers))
				.willReturn(wl);
		BDDMockito.given(wsSupport.setUpConverter(restTemplate)).willReturn(restTemplate);
		BDDMockito.given(jsonHelper.fromJsonToNode(anyString())).willReturn(node);
		//WHEN
		Node<Integer> result = underTest.getTreeFromList(numbers);

		BDDMockito.verify(restTemplate,times(1)).postForObject(anyString(), anyList(), eq(String.class));
		BDDMockito.verify(wsSupport,times(1)).fillWrappedListWithIntegers(wl,numbers);
		BDDMockito.verify(wsSupport,times(1)).setUpConverter(Mockito.<RestTemplate>anyObject());
		BDDMockito.verify(jsonHelper,times(1)).fromJsonToNode(anyString());
		//THAN
		assertEquals(node, result);
		
		
	}
}
