package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

/**
 * @author HLxxx
 * @version 1.0
 */
public interface UserService {

    /**
     * WeChatロクイン
     * @param userLoginDTO
     * @return
     */
    User wcLogin(UserLoginDTO userLoginDTO);
}
