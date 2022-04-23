package com.waracle.cake.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import com.waracle.cake.entity.Cake;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CakeRepositoryTest {

	@Autowired
	private CakeRepository cakeRepository;

	private String expectedName = "testCake";
	private String expectedType = "testType";

	@Test
	@Order(1)
	@Rollback(value = false)
	public void saveEmployeeTest() {
		String expectedName = "testCake";
		String expectedType = "testType";
		Cake cake = cakeRepository.save(new Cake(expectedName, expectedType));
		Assertions.assertThat(cake.getId()).isGreaterThan(0);
	}

	@Test
	@Order(2)
	public void getEmployeeTest() {
		Cake cake = cakeRepository.findById(1).get();
		Assertions.assertThat(cake.getId()).isEqualTo(1L);
	}

	@Test
	@Order(3)
	public void getListOfEmployeesTest() {
		Pageable pageable = PageRequest.of(0, 1);
		Page<Cake> page = cakeRepository.findAll(pageable);
		List<Cake> list = page.toList();
		Cake actual = list.get(0);
		assertEquals(expectedName, actual.getName());
		assertEquals(expectedType, actual.getType());

	}

	@Test
	@Order(4)
	@Rollback(value = false)
	public void updateEmployeeTest() {
		Cake cake = cakeRepository.findById(1).get();
		cake.setName("newName");
		Cake updatedCake = cakeRepository.save(cake);
		Assertions.assertThat(updatedCake.getName()).isEqualTo("newName");
	}

	@Test
	@Order(5)
	@Rollback(value = false)
	public void deleteEmployeeTest() {
		cakeRepository.deleteById(1);
		Optional<Cake> optional = cakeRepository.findById(1);
		assertFalse(optional.isPresent());
	}
}
