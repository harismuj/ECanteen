package com.jungkatjungkit.ecanteen.config;

import com.jungkatjungkit.ecanteen.config.models.User;

import java.util.List;

public class ApiResponse {
    private String message;
    private String result;
    private List<User> users;

    // Constructors
    public ApiResponse() {
    }

    public ApiResponse(String message, String result, List<User> users) {
        this.message = message;
        this.result = result;
        this.users = users;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
