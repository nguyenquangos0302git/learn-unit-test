package com.appsdeveloperblog.estore.service;

public interface UserService {

    void createUser(String firstName, String lastName, String email, String password, String repeatPassword);

}
