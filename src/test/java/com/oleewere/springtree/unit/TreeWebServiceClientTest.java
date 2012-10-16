package com.oleewere.springtree.unit;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.oleewere.springtree.domain.Node;
import com.oleewere.springtree.jsonsupport.WrappedList;
import com.oleewere.springtree.services.BinarySearchTreeWebServiceImpl;

@RunWith(PowerMockRunner.class)
@PrepareForTest(BinarySearchTreeWebServiceImpl.class)
public class TreeWebServiceClientTest {

	private List<Integer> numbers = Arrays.asList(1,2,3,4);
	private static final String RESPONSE = "response";
	@InjectMocks
	private BinarySearchTreeWebServiceImpl underTest = PowerMockito.spy(new BinarySearchTreeWebServiceImpl());
	@Mock
	private RestTemplate restTemplate;
	@Mock
	private WrappedList wl;
	@Mock
	private List<HttpMessageConverter<?>> messageConverters;
	@Mock
	private Node<Integer> node;
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	@Test
	public void testRestTemplate() throws Exception{
		//GIVEN
		PowerMockito.doReturn(wl).when(underTest,"fillWrappedListWithIntegers",numbers);
		PowerMockito.doReturn(messageConverters).when(underTest, "setUpConverter");
		PowerMockito.doReturn(node).when(underTest,"convertFromJsonToNode",RESPONSE);
		PowerMockito.whenNew(RestTemplate.class).withNoArguments().thenReturn(restTemplate);
		BDDMockito.given(restTemplate.postForObject(anyString(), anyList(), eq(String.class))).willReturn(RESPONSE);
		//WHEN
		Node<Integer> result = underTest.getTreeFromList(numbers);
		PowerMockito.verifyNew(RestTemplate.class).withNoArguments();
		PowerMockito.verifyPrivate(underTest,times(1)).invoke("setUpConverter");
		PowerMockito.verifyPrivate(underTest,times(1)).invoke("fillWrappedListWithIntegers", numbers);
		PowerMockito.verifyPrivate(underTest,times(1)).invoke("convertFromJsonToNode", RESPONSE);
		BDDMockito.verify(restTemplate,times(1)).postForObject(anyString(), anyList(), eq(String.class));
		//THAN
		assertEquals(node, result);
		
		
	}
}
