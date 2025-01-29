package com.retoFactus.factus.infrastructure.abstract_service;

import com.retoFactus.factus.api.request.UserRequest;
import com.retoFactus.factus.api.response.UserResponse;

public interface IUserService extends CrudDefault<UserRequest, UserResponse, Long> {
public final String FIELD_BY_SORT = "nameUser";
}
