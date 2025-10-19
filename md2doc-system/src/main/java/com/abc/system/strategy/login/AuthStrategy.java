package com.abc.system.strategy.login;

import com.abc.system.domain.dto.LoginDTO;
import com.abc.common.domain.dto.LoginUserDTO;
import com.abc.system.domain.dto.RegisterDTO;

public interface AuthStrategy {

    LoginUserDTO authenticate(LoginDTO loginDTO);

    void doRegister(RegisterDTO registerDTO);

}
