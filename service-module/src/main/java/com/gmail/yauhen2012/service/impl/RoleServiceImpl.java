package com.gmail.yauhen2012.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.gmail.yauhen2012.repository.ConnectionRepository;
import com.gmail.yauhen2012.repository.RoleRepository;
import com.gmail.yauhen2012.repository.TableRepository;
import com.gmail.yauhen2012.repository.enums.CreateRolesEnum;
import com.gmail.yauhen2012.repository.impl.ConnectionRepositoryImpl;
import com.gmail.yauhen2012.repository.impl.RoleRepositoryImpl;
import com.gmail.yauhen2012.repository.impl.TableRepositoryImpl;
import com.gmail.yauhen2012.repository.model.Role;
import com.gmail.yauhen2012.service.RoleService;
import com.gmail.yauhen2012.service.model.RoleDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static volatile RoleService instance;
    private RoleRepository roleRepository;
    private ConnectionRepository connectionRepository;
    private TableRepository tableRepository;

    private RoleServiceImpl(
            ConnectionRepository connectionRepository,
            RoleRepository roleRepository,
            TableRepository tableRepository) {
        this.connectionRepository = connectionRepository;
        this.roleRepository = roleRepository;
        this.tableRepository = tableRepository;
    }

    public static RoleService getInstance() {
        RoleService localInstance = instance;
        if (localInstance == null) {
            synchronized (RoleService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new RoleServiceImpl(
                            ConnectionRepositoryImpl.getInstance(),
                            RoleRepositoryImpl.getInstance(),
                            TableRepositoryImpl.getInstance());
                }
            }
        }
        return localInstance;
    }

    @Override
    public void createRoles() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (CreateRolesEnum action : CreateRolesEnum.values()) {
                    tableRepository.executeQuery(connection, action.getQuery());
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public List<RoleDTO> findAll() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Role> roleList = roleRepository.findAll(connection);
                List<RoleDTO> roleDTOList = convertDatabaseRoleToDTO(roleList);
                connection.commit();
                return roleDTOList;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    private List<RoleDTO> convertDatabaseRoleToDTO(List<Role> roleList) {
        return roleList.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private RoleDTO convert(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName(role.getName());
        roleDTO.setDescription(role.getDescription());

        return roleDTO;
    }

}
