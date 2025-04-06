package com.retoFactus.factus.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retoFactus.factus.api.request.EmployeeRequest;
import com.retoFactus.factus.api.response.EmployeeResponse;
import com.retoFactus.factus.domain.entities.Employee;
import com.retoFactus.factus.infrastructure.service.EmployeeService;
import com.retoFactus.factus.utils.SortType;

import lombok.AllArgsConstructor;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("Employee")
@AllArgsConstructor
@RestController
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody EmployeeRequest request){
        return ResponseEntity.ok(this.employeeService.create(request));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<EmployeeResponse> getById(@Validated @PathVariable Long id){
        return ResponseEntity.ok(this.employeeService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeResponse>> getAll(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestHeader(required = false) SortType sortType
    ){
        if(Objects.isNull(sortType)) sortType = SortType.NONE;
        return ResponseEntity.ok(this.employeeService.getAll(page - 1, size, sortType));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@Validated @PathVariable Long id, @RequestBody EmployeeRequest request){
        return ResponseEntity.ok(this.employeeService.update(request, id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteEmployee(@Validated @PathVariable Long id){
        this.employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
