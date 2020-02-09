package com.gmail.yauhen2012.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.gmail.yauhen2012.repository.ConnectionRepository;
import com.gmail.yauhen2012.repository.UserRepository;
import com.gmail.yauhen2012.repository.impl.ConnectionRepositoryImpl;
import com.gmail.yauhen2012.repository.impl.UserRepositoryImpl;
import com.gmail.yauhen2012.repository.model.User;
import com.gmail.yauhen2012.service.UserService;
import com.gmail.yauhen2012.service.model.AddUserDTO;
import com.gmail.yauhen2012.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static volatile UserService instance;
    private UserRepository userRepository;
    private ConnectionRepository connectionRepository;

    private UserServiceImpl(
            ConnectionRepository connectionRepository,
            UserRepository userRepository) {
        this.connectionRepository = connectionRepository;
        this.userRepository = userRepository;
    }

    public static UserService getInstance() {
        UserService localInstance = instance;
        if (localInstance == null) {
            synchronized (UserService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new UserServiceImpl(
                            ConnectionRepositoryImpl.getInstance(),
                            UserRepositoryImpl.getInstance());
                }
            }
        }
        return localInstance;
    }

    @Override
    public void add(AddUserDTO addUserDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User toDatabaseUser = convertUserDTOToDatabaseUser(addUserDTO);
                User addedUser = userRepository.add(connection, toDatabaseUser);
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
    public List<UserDTO> findAll() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<User> userList = userRepository.findAll(connection);
                List<UserDTO> userDTOList = convertDatabaseUserToDTO(userList);
                connection.commit();
                return userDTOList;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    private User convertUserDTOToDatabaseUser(AddUserDTO addUserDTO) {
        User toDatabaseUser = new User();
        toDatabaseUser.setUserName(addUserDTO.getUserName());
        toDatabaseUser.setPassword(addUserDTO.getPassword());
        toDatabaseUser.setRole(addUserDTO.getRole());

        return toDatabaseUser;
    }

    private List<UserDTO> convertDatabaseUserToDTO(List<User> userList) {
        return userList.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private UserDTO convert(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUserName(user.getUserName());
        userDTO.setPassword(user.getPassword());
        userDTO.setCreatedBy(user.getCreatedBy());
        userDTO.setRole(user.getRole());

        return userDTO;
    }

}
