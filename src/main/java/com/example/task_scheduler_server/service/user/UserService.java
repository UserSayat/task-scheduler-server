package com.example.task_scheduler_server.service.user;

import com.example.task_scheduler_server.common.Position;
import com.example.task_scheduler_server.domain.User;
import com.example.task_scheduler_server.dto.user.UserRequest;
import com.example.task_scheduler_server.dto.user.UserResponse;
import com.example.task_scheduler_server.exception.EntityNotFoundException;
import com.example.task_scheduler_server.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(),
                user.getLastName(),
                user.getFirstName(),
                user.getMiddleName(),
                user.getPosition(),
                user.isFree());
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        User user = new User(request.getLastName(),
                request.getFirstName(),
                request.getMiddleName(),
                request.getPosition(),
                request.isFree(),
                ZonedDateTime.now(ZoneOffset.UTC),
                ZonedDateTime.now(ZoneOffset.UTC));

        return toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse getUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User: " + id + " not found"));

        return toUserResponse(user);
    }

    @Override
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(this::toUserResponse)
                .toList();
    }

    @Override
    public UserResponse editUser(long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User: " + id + " not found"));

        user.setLastName(request.getLastName());
        user.setFirstName(request.getFirstName());
        user.setMiddleName(request.getMiddleName());
        user.setPosition(request.getPosition());
        user.setFree(request.isFree());
        user.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));

        return toUserResponse(userRepository.save(user));
    }

    @Override
    public void deleteUserById(long id) {
        if (!userRepository.existsById(id))
            throw new EntityNotFoundException("User: " + id + " not found");

        userRepository.deleteById(id);
    }

    @Override
    public void changePosition(long id, Position newPosition) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User: " + id + " not found"));

        user.setPosition(newPosition);

        userRepository.save(user);
    }

    @Override
    public void changeAvailability(long id, boolean isFree) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User: " + id + " not found"));

        user.setFree(isFree);

        userRepository.save(user);
    }
}
