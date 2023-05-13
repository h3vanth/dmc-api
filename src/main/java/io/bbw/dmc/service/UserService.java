package io.bbw.dmc.service;

import io.bbw.dmc.pojo.User;

public interface UserService {
    void createUser(User user);

    User getUser(String email);

    String[] getCategories(String userId);

    void updateCategories(String[] categories, String userId);
}
