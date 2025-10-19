package com.abc.system.service;

import com.abc.common.domain.entity.User;
import com.abc.common.domain.vo.PageResult;
import com.abc.system.domain.dto.UserDTO;
import com.abc.system.domain.dto.UserResetPwdDTO;
import com.abc.system.domain.vo.UserRoleVO;
import com.abc.system.domain.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;


public interface UserService extends IService<User> {

    User getUserById(Long userId);

    User getUserByUsername(String username);

    void saveUser(User user);

    UserVO getUserInfoWithUiParam(Long userId);

    PageResult getUserPageWithUiParam(UserDTO userDTO);

    UserRoleVO getUserRole(Long userId);

    void saveUserRole(UserDTO userDTO);

    void resetPassword(UserResetPwdDTO userResetPwdDTO);

    void updateUser(UserDTO userDTO);
}
