package com.example.task_scheduler_server.dto.user;

import com.example.task_scheduler_server.common.Position;

public class UserRequest {

    private String lastName;
    private String firstName;
    private String middleName;
    private Position position;
    private boolean isFree;

    public UserRequest() {
    }

    public UserRequest(String lastName, String firstName, String middleName, Position position, boolean isFree) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.position = position;
        this.isFree = isFree;
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
        isFree = free;
    }
}
