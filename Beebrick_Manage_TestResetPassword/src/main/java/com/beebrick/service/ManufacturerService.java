package com.beebrick.service;

import java.util.List;
import java.util.Optional;

import com.beebrick.entity.Manufacturer;

public interface ManufacturerService {
	
	List<Manufacturer> getAll();
	
	void save(Manufacturer manufacturer);
	
	void delete(Integer manufacturerID);
	
	Optional<Manufacturer> findById(Integer manufacturerID);
	
	List<Manufacturer> findByName(String manufacturerName);
}
