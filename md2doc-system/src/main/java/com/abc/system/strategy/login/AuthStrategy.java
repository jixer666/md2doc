package com.abc.system.strategy.login;

import com.abc.common.domain.entity.User;
import com.abc.system.domain.dto.LoginDTO;
import com.abc.common.domain.dto.LoginUserDTO;
import com.abc.system.domain.dto.RegisterDTO;

public interface AuthStrategy {

    LoginUserDTO authenticate(LoginDTO loginDTO);

    User doRegister(RegisterDTO registerDTO);

}
