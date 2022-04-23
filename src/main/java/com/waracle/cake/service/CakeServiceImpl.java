package com.waracle.cake.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waracle.cake.entity.Cake;
import com.waracle.cake.repo.CakeRepository;

@Service
@Transactional
public class CakeServiceImpl implements CakeService {
	
	@Autowired
	private CakeRepository cakeRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Cake> getAllCakes() {
		return cakeRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cake> listCakes(int pageNum, int size) {
		Pageable pageRequest = PageRequest.of(pageNum, size, Sort.by("name").ascending());
		Page<Cake> page = cakeRepository.findAll(pageRequest);
		return page.isEmpty() ? new ArrayList<Cake>() : page.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public Cake getCake(int id) {
		Optional<Cake> cakeOptional = cakeRepository.findById(id);
		return cakeOptional.isPresent() ? cakeOptional.get() : null;
	}

	@Override
	public Cake createCake(Cake cake) {
		return cakeRepository.save(cake);
	}

	@Override
	public Cake updateCake(int id, Cake updatedCake) {
		Cake modifiedCake = null;
		Cake originalCake = getCake(id);
		if (originalCake != null) {
			originalCake.setName(updatedCake.getName());
			originalCake.setType(updatedCake.getType());
			modifiedCake = createCake(originalCake);
		}
		return modifiedCake;
	}

	@Override
	public boolean deleteCake(int id) {
		boolean deleted = false;
		Cake cake = getCake(id);
		if (cake != null) {
			cakeRepository.deleteById(id);
			deleted = true;
		}
		return deleted;
	}

}
