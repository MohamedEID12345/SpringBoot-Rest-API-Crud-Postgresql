package com.eidsoft.first.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eidsoft.first.exception.ResourceNotFoundException;
import com.eidsoft.first.models.Emploee;
import com.eidsoft.first.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeerepository;
	
	//get employees
	@GetMapping("/employees")
	public List<Emploee> getAllEmployees(){
		return this.employeerepository.findAll();
	}
	//get employees by id
	@GetMapping("/employees/{id}")
	public ResponseEntity<Emploee> getEmployeeById(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Emploee employee = employeerepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		return ResponseEntity.ok().body(employee);
	}

	//save employee
	@PostMapping("/employees")
	public Emploee createEmployee(@Validated @RequestBody Emploee employee) {
		return employeerepository.save(employee);
	}
	//put employee
	@PutMapping("/employees/{id}")
	public ResponseEntity<Emploee> updateEmployee(@PathVariable(value = "id") Long employeeId,
			@Validated @RequestBody Emploee employeeDetails) throws ResourceNotFoundException {
		Emploee employee = employeerepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employee.setId(employeeDetails.getId());
		employee.setLastName(employeeDetails.getLastName());
		employee.setFirstName(employeeDetails.getFirstName());
		final Emploee updatedEmployee = employeerepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}
	//delete employee
	@DeleteMapping("/employees/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Emploee employee = employeerepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employeerepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
