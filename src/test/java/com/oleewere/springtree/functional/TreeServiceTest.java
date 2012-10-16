package com.oleewere.springtree.functional;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.oleewere.springtree.services.BinarySearchTreeService;
import com.oleewere.springtree.services.BinarySearchTreeServiceImpl;
import com.oleewere.springtree.services.BinarySearchTreeWebServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test/root-context.xml" ,"classpath:test/servlet-context.xml"})
public class TreeServiceTest {

	private BinarySearchTreeService underTest;
	private BinarySearchTreeService underTestWebService;
	
	@Before
	public void setUp(){
		underTest = new BinarySearchTreeServiceImpl();
		underTestWebService = new BinarySearchTreeWebServiceImpl();
		
	}
	
	@Test
	public void Test(){		
	}
	
}
