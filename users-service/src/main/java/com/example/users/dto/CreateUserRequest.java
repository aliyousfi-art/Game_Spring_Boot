package com.example.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateUserRequest {

    @NotBlank(message = "name ne peut pas être vide")
    @Size(min = 1, max = 100, message = "name doit faire entre 1 et 100 caractères")
    private String name;

    public CreateUserRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
