package com.example.task_scheduler_server.domain;

import com.example.task_scheduler_server.common.Position;
import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lastName;
    private String firstName;
    private String middleName;

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private Position position;

    private boolean isFree;


    @OneToMany(mappedBy = "executor")
    private List<Task> tasks = new ArrayList<>();

    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public User() {
    }

    public User(String lastName, String firstName, String middleName,
                Position position, boolean status, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.position = position;
        this.isFree = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        this.isFree = free;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id == user.id
                && isFree == user.isFree
                && Objects.equals(lastName, user.lastName)
                && Objects.equals(firstName, user.firstName)
                && Objects.equals(middleName, user.middleName)
                && position == user.position;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
