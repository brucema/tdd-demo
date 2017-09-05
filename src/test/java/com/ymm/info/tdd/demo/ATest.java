package com.ymm.info.tdd.demo;

import static org.mockito.Matchers.anySet;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.SetUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.util.CollectionUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({A.class})
public class ATest {

	private A sut = new A();
	
	private B b;

	private DAO dao;

	@Before
	public void setup() {
		b = mock(B.class);
		sut.setB(b);
	}

	@Test
	public void test_getString_ifScoreGreaterThan10_should_returnA() {
		int score = 11;
		String expected = "A";
		when(b.getString(score)).thenReturn("B");
		String actual = sut.getString(score);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void test_getString_ifScoreSmallerThan10_should_returnB() {
		int score = 10;
		String expected = "B";
		when(b.getString(score)).thenReturn("B");
		String actual = sut.getString(score);
		Assert.assertEquals(expected, actual);

	}

	@Test
	public void test_getInt_should_Return1() {
		int expected = 1;
		// mock静态方法,结合：
		// 	@RunWith(PowerMockRunner.class)
		//	@PrepareForTest({A.class})
		PowerMockito.mockStatic(A.class);
		PowerMockito.when(A.getDefaultResult()).thenReturn(1);
		int actual = sut.getInt();
		Assert.assertTrue(expected == actual);
	}
	
	@Test
	public void test_getDouble_should_ReturnHalfofDefault() throws Exception {
		double a = 1.0;
		double expected = a / 2;
		// mock私有方法
		A spy = PowerMockito.spy(sut);
		PowerMockito.doReturn(a).when(spy, "getDefaultDouble");
		double actual = spy.getDouble();
		// 验证该方法被调用过
		PowerMockito.verifyPrivate(spy).invoke("getDefaultDouble");
		Assert.assertTrue(expected == actual);
	}
	
	
	@Test
	public void test_getList_ifIdSetIsNotNullorNotEmpty_should_returnList() {  
		List expected =  Arrays.asList(getMap(1, "A"), getMap(2,"B"));
		dao = mock(DAO.class);
		sut.setDao(dao);
		when(dao.getList(anySet())).thenReturn(mockDaoData());
		List actual = sut.getList(CollectionUtils.asSet(1, 2));
		// 验证dao.getList()被调用过；
        verify(dao, times(1)).getList(anySet());
		ReflectionAssert.assertReflectionEquals(expected, actual);
	}
	

	@Test
	public void test_getList_ifIdSetIsNullorEmpty_should_returnNull() {  
		List expected = null;
		dao = mock(DAO.class);
		sut.setDao(dao);
		when(dao.getList(anySet())).thenReturn(mockDaoData());
		List actual = sut.getList(SetUtils.EMPTY_SET);
		// 验证dao.getList()没有被调用过；
        verify(dao, times(0)).getList(anySet());
        ReflectionAssert.assertReflectionEquals(expected, actual);
	}


	private List mockDaoData() {
		return  Arrays.asList(getMap(1, "A"), getMap(2,"B"));
	}

	private Map<Integer, String> getMap(int id, String value) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(id, value);
		return map;
	}
}
