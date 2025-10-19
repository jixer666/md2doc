package com.abc.system.service.impl;

import com.abc.system.security.context.SecurityAuthContext;
import com.abc.common.util.AssertUtils;
import com.abc.common.util.SecurityUtils;
import com.abc.common.domain.dto.LoginUserDTO;
import com.abc.common.domain.entity.User;
import com.abc.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        AssertUtils.isNotEmpty(user, "用户不存在");
        AssertUtils.isTrue(SecurityUtils.matchesPassword(SecurityAuthContext.getContext().getCredentials().toString(),
                user.getPassword()), "账号或者密码错误");

        return new LoginUserDTO(user, null);
    }
}
