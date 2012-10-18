package com.oleewere.springtree.unit;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import com.oleewere.springtree.wssupport.MyNumberUtils;
import com.oleewere.springtree.wssupport.StringCuttingService;

public class StringCuttingServiceTest {
	 @InjectMocks
     private StringCuttingService underTest = new StringCuttingService();
     
     private static final String NUMBERS = "10 12 14";
     private List<Integer> nums=Arrays.asList(10,12,14);
     
     @Mock
     private MyNumberUtils myNumberUtils;
     
     @Before
     public void setUp(){
    	 MockitoAnnotations.initMocks(this);
     }
     @Test
     public void testStringToNumbersMethodWithTrue() {
    	//GIVEN
    	 BDDMockito.given(myNumberUtils.validateNumbers(NUMBERS)).willReturn(true);
    	 //WHEN
    	 List<Integer> result = underTest.StringToNumbers(NUMBERS);
    	 
    	 BDDMockito.verify(myNumberUtils, times(1)).validateNumbers(NUMBERS);
     	 //THAN
    	 assertEquals(result,nums);
     }
     @Test
     public void testStringToNumbersMethodWithFalse() {
    	//GIVEN
    	 BDDMockito.given(myNumberUtils.validateNumbers(NUMBERS)).willReturn(false);
    	 //WHEN
    	 List<Integer> result = underTest.StringToNumbers(NUMBERS);
    	 
    	 BDDMockito.verify(myNumberUtils, times(1)).validateNumbers(NUMBERS);
     	 //THAN
    	 assertEquals(0,result.size());
     }
     
	
}
