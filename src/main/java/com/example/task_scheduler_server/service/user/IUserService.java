package com.example.task_scheduler_server.service.user;

import com.example.task_scheduler_server.common.Position;
import com.example.task_scheduler_server.dto.user.UserRequest;
import com.example.task_scheduler_server.dto.user.UserResponse;

import java.util.List;

public interface IUserService {

    UserResponse createUser(UserRequest request);
    UserResponse getUserById(long id);
    List<UserResponse> getUsers();
    UserResponse editUser(long id, UserRequest request);
    void deleteUserById(long id);
    void changePosition(long id, Position newPosition);
    void changeAvailability(long id, boolean isFree);
}
