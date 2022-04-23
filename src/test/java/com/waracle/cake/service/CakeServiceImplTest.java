package com.waracle.cake.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.waracle.cake.entity.Cake;
import com.waracle.cake.repo.CakeRepository;

@ExtendWith(MockitoExtension.class)
public class CakeServiceImplTest {

	@InjectMocks
	private CakeServiceImpl cakeServiceImpl;

	@Mock
	private CakeRepository cakeRepository;

	@Test
	public void testGetAllCakes() {
		List<Cake> expected = new ArrayList<Cake>();
		when(cakeRepository.findAll()).thenReturn(expected);
		List<Cake> actual = cakeServiceImpl.getAllCakes();
		assertEquals(expected, actual);
	}

	@Test
	public void testListCakes() {
		List<Cake> list = new ArrayList<Cake>();
		list.add(new Cake("TestCake", "TestType"));
		Page<Cake> expected = new PageImpl<Cake>(list);
		when(cakeRepository.findAll(any(Pageable.class))).thenReturn(expected);
		List<Cake> actual = cakeServiceImpl.listCakes(0, 1);
		assertEquals(expected.toList(), actual);
		assertEquals(expected.toList().get(0).getName(), "TestCake");
		assertEquals(expected.toList().get(0).getType(), "TestType");
	}

	@Test
	public void testGetCake_forValidId() {
		Cake expected = new Cake("TestCake", "TestType");
		when(cakeRepository.findById(1)).thenReturn(Optional.of(expected));
		Cake actual = cakeServiceImpl.getCake(1);
		assertEquals(expected, actual);
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getType(), actual.getType());
	}

	@Test
	public void testGetCake_forInValidId() {
		Cake actual = cakeServiceImpl.getCake(1);
		assertNull(actual);
	}

	@Test
	public void testCreateCake() {
		Cake cakeInput = new Cake();
		Cake expected = new Cake();
		when(cakeRepository.save(any(Cake.class))).thenReturn(expected);
		Cake actual = cakeServiceImpl.createCake(cakeInput);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateCake_forException() {
		when(cakeRepository.save(any(Cake.class))).thenThrow(RuntimeException.class);
		Cake cakeInput = new Cake();
		assertThrows(RuntimeException.class, () -> cakeServiceImpl.createCake(cakeInput));
	}

	@Test
	public void testUpdateCake() {
		Cake cakeInput = new Cake();
		Cake expected = new Cake();
		when(cakeRepository.findById(1)).thenReturn(Optional.of(expected));
		when(cakeRepository.save(any(Cake.class))).thenReturn(expected);
		Cake actual = cakeServiceImpl.updateCake(1, cakeInput);
		assertNotNull(actual);
		assertEquals(expected, actual);
	}

	@Test
	public void testUpdateCake_forCakeAbsentInDb() {
		Cake cakeInput = new Cake();
		Cake actual = cakeServiceImpl.updateCake(1, cakeInput);
		assertNull(actual);
		verify(cakeRepository).findById(anyInt());
		verify(cakeRepository, times(0)).save(any(Cake.class));
	}

	@Test
	public void testDeleteCake() {
		Cake expected = new Cake();
		when(cakeRepository.findById(1)).thenReturn(Optional.of(expected));
		boolean actual = cakeServiceImpl.deleteCake(1);
		assertTrue(actual);
		verify(cakeRepository, times(1)).deleteById(anyInt());
	}

	@Test
	public void testDeleteCake_forCakeAbsentInDb() {
		boolean actual = cakeServiceImpl.deleteCake(1);
		assertFalse(actual);
		verify(cakeRepository, times(0)).deleteById(anyInt());
	}
}
