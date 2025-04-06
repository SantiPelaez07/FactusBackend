package com.retoFactus.factus.infrastructure.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.retoFactus.factus.api.request.EmployeeRequest;
import com.retoFactus.factus.api.request.LoginEmployee;
import com.retoFactus.factus.api.response.EmployeeResponse;
import com.retoFactus.factus.domain.entities.Employee;
import com.retoFactus.factus.domain.repositories.EmployeeRepository;
import com.retoFactus.factus.infrastructure.abstract_service.IEmployeeService;
import com.retoFactus.factus.utils.SortType;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeService implements IEmployeeService<EmployeeRequest, LoginEmployee, EmployeeResponse, Long> {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponse create(EmployeeRequest request) {
        Employee employee = this.requestToEntity(request);
        return this.entityToResponse(this.employeeRepository.save(employee));
    }

    @Override
    public EmployeeResponse getById(Long id) {
        return this.entityToResponse(this.findById(id));
    }

    @Override
    public EmployeeResponse update(EmployeeRequest request, Long id) {
        Employee employee = this.findById(id);
        Employee update = new Employee();
        if (employee != null) {
            update = employee;
            update.setId(employee.getId());
        }
        return this.entityToResponse(update);
        
    }

    @Override
    public void delete(Long id) {
        Employee employee = this.findById(id);
        if(employee != null){
            this.employeeRepository.delete(employee);
        }else {
            throw new IllegalArgumentException("No se encontró el empleado registrado con ese ID");
        }
    }

    @Override
    public Page<EmployeeResponse> getAll(int page, int size, SortType sortType) {
        if (size < 0) size = 0;

        Pageable pagination = null;
        switch (sortType) {
            case NONE -> pagination = PageRequest.of(page, size);
            case ASC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }

        return this.employeeRepository.findAll(pagination).map(this::entityToResponse);
    }

       //Request to Entity
   public Employee requestToEntity(EmployeeRequest request){
    return Employee.builder()
    .userName(request.getUserName())
    .password(request.getPassword())
    .address(request.getAddress())
    .dni(request.getDni())
    .mail(request.getMail())
    .phone(request.getPhone()).build();
}

public EmployeeResponse entityToResponse(Employee entity){
    return EmployeeResponse.builder()
    .userName(entity.getUserName())
    .address(entity.getAddress())
    .dni(entity.getDni())
    .mail(entity.getMail())
    .phone(entity.getPhone()).build();
}

private Employee findById(Long id){
    Employee employee = this.employeeRepository.findById(id).orElseThrow();
    return employee;
}




}
