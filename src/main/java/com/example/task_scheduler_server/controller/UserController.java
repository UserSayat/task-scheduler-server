package com.example.task_scheduler_server.controller;

import com.example.task_scheduler_server.common.CommonResponse;
import com.example.task_scheduler_server.common.Position;
import com.example.task_scheduler_server.dto.user.UserRequest;
import com.example.task_scheduler_server.dto.user.UserResponse;
import com.example.task_scheduler_server.service.user.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse<UserResponse>> createUser(@RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponse<>(userService.createUser(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<UserResponse>> getUserById(@PathVariable long id) {
        return ResponseEntity.ok(new CommonResponse<>(userService.getUserById(id)));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<UserResponse>>> getUsers() {
        return ResponseEntity.ok(new CommonResponse<>(userService.getUsers()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<UserResponse>> editUser(@PathVariable long id,
                                                                 @RequestBody UserRequest request) {
        return ResponseEntity.ok(new CommonResponse<>(userService.editUser(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteUserById(@PathVariable long id) {
        userService.deleteUserById(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/position")
    public ResponseEntity<CommonResponse<Void>> changePosition(@PathVariable long id,
                                                               @RequestParam Position newPosition) {
        userService.changePosition(id, newPosition);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/availability")
    public ResponseEntity<CommonResponse<Void>> changeAvailability(@PathVariable long id,
                                                                @RequestParam boolean isFree) {
        userService.changeAvailability(id, isFree);

        return ResponseEntity.noContent().build();
    }
}

// Создал бизнес логику управления пользователями,
// проработать процесс добавления задачи пользователю и/или закрепления пользователя на задачу