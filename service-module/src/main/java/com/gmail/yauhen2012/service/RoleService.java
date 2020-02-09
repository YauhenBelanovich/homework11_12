package com.gmail.yauhen2012.service;

import java.util.List;

import com.gmail.yauhen2012.service.model.RoleDTO;

public interface RoleService {

    void createRoles();

    List<RoleDTO> findAll();

}
