package com.abc.system.strategy.login;

import com.abc.system.security.context.SecurityAuthContext;
import com.abc.common.util.AssertUtils;
import com.abc.system.convert.UserConvert;
import com.abc.system.domain.dto.LoginDTO;
import com.abc.common.domain.dto.LoginUserDTO;
import com.abc.system.domain.dto.RegisterDTO;
import com.abc.common.domain.entity.User;
import com.abc.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AccountAuthStrategy implements AuthStrategy {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Override
    public LoginUserDTO authenticate(LoginDTO loginDTO) {
        preLoginCheck(loginDTO);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        SecurityAuthContext.setContext(authToken);
        Authentication authentication = authenticationManager.authenticate(authToken);

        return (LoginUserDTO) authentication.getPrincipal();
    }

    public void preLoginCheck(LoginDTO loginDTO) {
        loginDTO.checkParams();
    }

    @Override
    public void doRegister(RegisterDTO registerDTO) {
        preRegisterCheck(registerDTO);
        User user = UserConvert.convertToUserByRegisterDTO(registerDTO);
        userService.saveUser(user);
    }

    public void preRegisterCheck(RegisterDTO registerDTO) {
        registerDTO.checkParams();
        User user = userService.getUserByUsername(registerDTO.getUsername());
        AssertUtils.isEmpty(user, "用户已存在");
    }
}
