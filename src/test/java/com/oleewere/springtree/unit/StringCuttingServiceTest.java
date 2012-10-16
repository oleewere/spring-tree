package com.oleewere.springtree.unit;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
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

import com.oleewere.springtree.services.StringCuttingService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({StringCuttingService.class, StringUtils.class, NumberUtils.class})
public class StringCuttingServiceTest {
     private StringCuttingService underTest = PowerMockito.spy(new StringCuttingService());
     public static final String NUMBERS = "10 12 14";
     @Mock 
     private ArrayList<Integer> nums;
     @Before
     public void setUp(){
    	 MockitoAnnotations.initMocks(this);
     }
     @Test
     public void testStringToNumbersMethodWithTrue() throws Exception {
    	//GIVEN
    	 PowerMockito.whenNew(ArrayList.class).withNoArguments().thenReturn(nums);
    	 PowerMockito.doReturn(true).when(underTest,"validateNumbers",NUMBERS);
    	 PowerMockito.doNothing().when(underTest,"addNumbersToNumList",nums,NUMBERS);
    	 //WHEN
    	 List<Integer> result = underTest.StringToNumbers(NUMBERS);
    	 
    	 PowerMockito.verifyNew(ArrayList.class).withNoArguments();
    	 PowerMockito.verifyPrivate(underTest,Mockito.times(1)).invoke("validateNumbers",NUMBERS);
    	 PowerMockito.verifyPrivate(underTest,Mockito.times(1)).invoke("addNumbersToNumList",nums,NUMBERS);
    	 //THAN
    	 assertEquals(result,nums);
     }
     @Test
     public void testStringToNumbersMethodWithFalse() throws Exception{
     	//GIVEN
    	 PowerMockito.whenNew(ArrayList.class).withNoArguments().thenReturn(nums);
    	 PowerMockito.doReturn(true).when(underTest,"validateNumbers",NUMBERS);
    	 //WHEN
    	 List<Integer> result = underTest.StringToNumbers(NUMBERS);
    	 
    	 PowerMockito.verifyNew(ArrayList.class).withNoArguments();
    	 PowerMockito.verifyPrivate(underTest,Mockito.times(1)).invoke("validateNumbers",NUMBERS);
    	 //THAN
    	 assertEquals(result,nums);
     }
     @Test
     public void addNumbersToNumListPrivateMethod() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
    	 //GIVEN
    	 PowerMockito.mockStatic(NumberUtils.class);
    	 BDDMockito.given(NumberUtils.toInt(Mockito.anyString())).willReturn(10);
    	 List<Integer> numbers = new ArrayList<Integer>(); 	 
    	 //WHEN
    	 Method method = StringCuttingService.class.getDeclaredMethod("addNumbersToNumList", List.class, String.class);
    	 method.setAccessible(true);
    	 method.invoke(underTest, numbers, NUMBERS);
    	 
    	 PowerMockito.verifyStatic(Mockito.atMost(3));
    	 NumberUtils.toInt(Mockito.anyString());
    	 //THAN
    	 assertEquals(Arrays.asList(10,10,10),numbers);
     }
     
     @Test
     public void validateNumbersTest() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
    	 //GIVEN
    	 PowerMockito.mockStatic(StringUtils.class);
 		 BDDMockito.given(StringUtils.isNumericSpace(Mockito.anyString())).willReturn(true);
 		 BDDMockito.given(StringUtils.isEmpty(Mockito.anyString())).willReturn(false);
    	 //WHEN
    	 Method method = StringCuttingService.class.getDeclaredMethod("validateNumbers", String.class);
    	 method.setAccessible(true);
    	 boolean result = (Boolean) method.invoke(underTest, NUMBERS);
    	 
    	 PowerMockito.verifyStatic(Mockito.times(1));
    	 StringUtils.isNumericSpace(Mockito.anyString());
    	 PowerMockito.verifyStatic(Mockito.times(1));
    	 StringUtils.isEmpty(Mockito.anyString());
    	 //THAN
    	 assertEquals(result, true);
    	 
     }
     @Test
     public void validateNumbersTestFalse() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
    	 //GIVEN
    	 PowerMockito.mockStatic(StringUtils.class);
 		 BDDMockito.given(StringUtils.isNumericSpace(Mockito.anyString())).willReturn(true);
 		 BDDMockito.given(StringUtils.isEmpty(Mockito.anyString())).willReturn(true);
    	 //WHEN
    	 Method method = StringCuttingService.class.getDeclaredMethod("validateNumbers", String.class);
    	 method.setAccessible(true);
    	 boolean result = (Boolean) method.invoke(underTest, NUMBERS);
    	 
    	 PowerMockito.verifyStatic(Mockito.times(1));
    	 StringUtils.isNumericSpace(Mockito.anyString());
    	 PowerMockito.verifyStatic(Mockito.times(1));
    	 StringUtils.isEmpty(Mockito.anyString());
    	 //THAN
    	 assertEquals(result, false);
    	 
     }
	
}
