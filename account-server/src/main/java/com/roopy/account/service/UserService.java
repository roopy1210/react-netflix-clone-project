package com.roopy.account.service;

import com.roopy.account.entity.User;
import com.roopy.dto.UserDTO;

import javax.validation.Valid;

public interface UserService {
    /**
     * 신규 사용자 등록
     *
     * @param userDTO 사용자등록 정보
     * @return User 사용자등록 정보
     */
    User createUser(@Valid UserDTO userDTO);
}
