package com.citizenweb.marketplace.model.model;

import com.citizenweb.marketplace.model.DTO.UserDataDTO;

@FunctionalInterface
public interface DataHolder {

    UserDataDTO loadUserData(User user);

}
