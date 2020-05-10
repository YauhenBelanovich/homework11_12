package com.gmail.yauhen2012.service;

import java.util.List;

import com.gmail.yauhen2012.service.model.AddUserDTO;
import com.gmail.yauhen2012.service.model.UserDTO;

public interface UserService {

    void add(AddUserDTO addUserDTO);

    List<UserDTO> findAll();

}
