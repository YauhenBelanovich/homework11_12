package com.gmail.yauhen2012.controller.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.gmail.yauhen2012.service.RoleService;
import com.gmail.yauhen2012.service.TableService;
import com.gmail.yauhen2012.service.impl.RoleServiceImpl;
import com.gmail.yauhen2012.service.impl.TableServiceImpl;

public class AppDestroyListener implements ServletContextListener {

    private TableService tableService = TableServiceImpl.getInstance();
    private RoleService roleService = RoleServiceImpl.getInstance();

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        tableService.deleteAllTables();
        tableService.createAllTables();
        roleService.createRoles();
    }

}
