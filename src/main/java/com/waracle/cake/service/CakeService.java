package com.waracle.cake.service;

import java.util.List;

import com.waracle.cake.entity.Cake;

public interface CakeService {

	List<Cake> getAllCakes();

	List<Cake> listCakes(int page, int size);
	
	Cake getCake(int id);
	
	Cake createCake(Cake cake);
	
	Cake updateCake(int id, Cake updatedCake);
	
	boolean deleteCake(int id);
}
