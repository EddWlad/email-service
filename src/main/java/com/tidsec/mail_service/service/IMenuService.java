package com.tidsec.mail_service.service;

import com.tidsec.mail_service.entities.Menu;

import java.util.List;

public interface IMenuService {
    List<Menu> getMenusByUsername(String username);
}
