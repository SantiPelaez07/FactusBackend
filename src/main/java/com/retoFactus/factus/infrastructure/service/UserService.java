package com.retoFactus.factus.infrastructure.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.retoFactus.factus.api.request.UserRequest;
import com.retoFactus.factus.api.response.UserResponse;
import com.retoFactus.factus.api.response.secundaryResponse.InvoiceSecundaryResponse;
import com.retoFactus.factus.domain.entities.User;
import com.retoFactus.factus.domain.repositories.UserRepository;
import com.retoFactus.factus.infrastructure.abstract_service.IUserService;
import com.retoFactus.factus.utils.SortType;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserResponse create(UserRequest request) {
        User user = this.requestToEntity(request);
        // String encodedPassword = passwordEncoder.encode(user.getPassword());
        // user.setPassword(encodedPassword);
        return this.entityToResponse(this.userRepository.save(user));
    }

    @Override
    public UserResponse getById(Long id) {
        User user = this.userRepository.findById(id).orElseThrow();
        return this.entityToResponse(user);
    }

    @Override
    public UserResponse update(UserRequest request, Long id) {
        User user = this.getId(id);
        User update = new User();
        if (user != null) {
            update = this.requestToEntity(request);
            update.setIdUser(id);
        }
        return this.entityToResponse(this.userRepository.save(update));
    }

    @Override
    public void delete(Long id) {
        User user = this.getId(id);
        this.userRepository.delete(user);
    }

    @Override
    public Page<UserResponse> getAll(int page, int size, SortType sort) {
        if (page < 0)
            page = 0;
        PageRequest pagination = null;
        switch (sort) {
            case NONE -> pagination = PageRequest.of(page, size);
            case ASC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());

        }
        return this.userRepository.findAll(pagination).map(this::entityToResponse);
    }

    private User requestToEntity(UserRequest request) {
        return User.builder()
                .nameUser(request.getNameUser())
                .lastNameUser(request.getLastNameUser())
                .dni(request.getDni())
                .departament(request.getDepartament())
                .address(request.getAddress())
                .mail(request.getMail())
                .phone(request.getPhone()).build();
    }

    private UserResponse entityToResponse(User entity) {
        List<InvoiceSecundaryResponse> invoiceList = new ArrayList<>();
        if (entity.getInvoices() == null) {
            entity.setInvoices(null);
        } else {
            invoiceList = entity.getInvoices().stream().map(
                    invoiceUser -> {
                        InvoiceSecundaryResponse invoice = new InvoiceSecundaryResponse();
                        BeanUtils.copyProperties(invoiceUser, invoice);
                        return invoice;
                    }).collect(Collectors.toList());
        }
        return UserResponse.builder()
                .idUser(entity.getIdUser())
                .nameUser(entity.getNameUser())
                .lastNameUser(entity.getLastNameUser())
                .dni(entity.getDni())
                .departament(entity.getDepartament())
                .address(entity.getAddress())
                .mail(entity.getMail())
                .phone(entity.getPhone())
                .invoiceSecundaryResponse(invoiceList)
                .build();

    }

    private User getId(Long id) {
        User user = this.userRepository.findById(id).orElseThrow();
        return user;
    }

    // public User createdUserRegister(UserRequest request, Role userRole){
    // User newUser = this.requestToEntity(request);
    // newUser.setPassword(passwordEncoder.encode(request.getPassword()));
    // System.out.println("Hasta aquí llego");
    // newUser.setRoles(Set.of(userRole));
    // return this.userRepository.save(newUser);
    // }

}

// User user = this.userService.requestToEntity(request);
// user.setPassword(passwordEncoder.encode(request.getPassword()));
// user.setRoles(Set.of(userRole));
// this.userRepository.save(user);
// System.out.print("Hasta aquí llego");