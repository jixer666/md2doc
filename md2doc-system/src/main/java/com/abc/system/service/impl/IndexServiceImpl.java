package com.abc.system.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import com.abc.biz.convert.PointsConvert;
import com.abc.biz.domain.dto.PointsDTO;
import com.abc.biz.domain.enums.PointsRuleTypeEnum;
import com.abc.biz.domain.enums.PointsUserTypeEnum;
import com.abc.biz.service.PointsService;
import com.abc.common.constant.CacheConstants;
import com.abc.common.constant.MenuConstants;
import com.abc.common.core.threadlocal.ThreadLocalTempHelp;
import com.abc.common.core.threadlocal.ThreadLocalTempVar;
import com.abc.common.domain.entity.User;
import com.abc.common.domain.enums.CommonRoleEnum;
import com.abc.common.exception.GlobalException;
import com.abc.common.util.AssertUtils;
import com.abc.common.util.RedisUtils;
import com.abc.common.util.SecurityUtils;
import com.abc.system.convert.CaptchaConvert;
import com.abc.system.convert.MenuConvert;
import com.abc.system.domain.dto.CaptchaDTO;
import com.abc.system.domain.dto.EmailDTO;
import com.abc.system.domain.dto.LoginDTO;
import com.abc.common.domain.dto.LoginUserDTO;
import com.abc.system.domain.dto.RegisterDTO;
import com.abc.system.domain.entity.Menu;
import com.abc.system.domain.enums.EmailTypeEnum;
import com.abc.system.domain.enums.MenuTypeEnum;
import com.abc.system.domain.vo.CaptchaVO;
import com.abc.system.domain.vo.EmailVO;
import com.abc.system.domain.vo.MenuRouterVO;
import com.abc.system.factory.LoginStrategyFactory;
import com.abc.system.service.*;
import com.abc.system.strategy.login.AuthStrategy;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FastByteArrayOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private MenuService menuService;

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PointsService pointsService;

    @Override
    public String login(LoginDTO loginDTO) {
        AuthStrategy authStrategy = LoginStrategyFactory.getAuthStrategy(loginDTO.getAuthType());
        LoginUserDTO loginUser = authStrategy.authenticate(loginDTO);
        return tokenService.createToken(loginUser);
    }

    @Override
    @Transactional
    public void register(RegisterDTO registerDTO) {
        AuthStrategy authStrategy = LoginStrategyFactory.getAuthStrategy(registerDTO.getAuthType());
        User user = authStrategy.doRegister(registerDTO);

        roleService.saveUserRoleByRoleKeys(user.getUserId(), Collections.singletonList(CommonRoleEnum.COMMON_USER.getRoleKey()));

        initUserPoints(user);
    }

    private void initUserPoints(User user) {
        // 设置临时变量，用于后续操作中通过SecurityUtils.getUserId()获取到用户信息
        ThreadLocalTempHelp.execute(ThreadLocalTempVar.TEMP_USER_ID_VAR, user.getUserId(), () -> {
            PointsDTO initPointsDTO = PointsConvert.buildPointsDTOByInitPoints(user.getUserId(), PointsUserTypeEnum.COMMON_USER.getType(),
                    PointsRuleTypeEnum.NEWCOMER.getPoints(), PointsRuleTypeEnum.NEWCOMER.getType());
            pointsService.initPoints(initPointsDTO);
        });
    }

    @Override
    public List<MenuRouterVO> getMenuRoutes() {
        List<Menu> menus = menuService.getMenusByUserId(SecurityUtils.getUserId());

        return buildMenuRouter(MenuConstants.DEFAULT_MENU_PARENT_ID, menus);
    }

    private List<MenuRouterVO> buildMenuRouter(Long menuId, List<Menu> menus) {
        List<MenuRouterVO> routers = new ArrayList<>();

        List<Menu> filterMenus = menus.stream().filter(item -> item.getParentId().equals(menuId)).sorted(new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                return o1.getOrderNum() - o2.getOrderNum();
            }
        }).collect(Collectors.toList());

        for (Menu menu : filterMenus) {
            if (menu.getParentId().equals(menuId)) {
                List<MenuRouterVO> children = buildMenuRouter(menu.getMenuId(), menus);
                routers.add(MenuConvert.convertToMenuRouterVO(menu, children));
            }
        }

        return routers;
    }

    @Override
    public List<MenuRouterVO> getMenuWhiteRoutes() {
        List<Menu> menus = menuService.getMenusByMenuType(MenuTypeEnum.FRONT.getType());

        return buildMenuRouter(MenuConstants.DEFAULT_MENU_PARENT_ID, menus);
    }

    @Override
    public CaptchaVO getCaptchaImg(CaptchaDTO captchaDTO) {
        String uuid = IdUtil.simpleUUID();
        captchaDTO.setUuid(uuid);

        buildCaptchaImg(captchaDTO);
        validCaptcha(captchaDTO);

        return CaptchaConvert.convertCaptchaVOByCaptchaDTO(captchaDTO);
    }

    private void validCaptcha(CaptchaDTO captchaDTO) {
        String captchaCacheKey = CacheConstants.getFinalKey(CacheConstants.CAPTCHA_UUID, captchaDTO.getUuid());
        RedisUtils.set(captchaCacheKey, captchaDTO.getCode(), CacheConstants.CAPTCHA_UUID_EXPIRE_TIME, TimeUnit.MINUTES);
    }

    private void buildCaptchaImg(CaptchaDTO captchaDTO) {
        String code = null;
        BufferedImage imageBuffer = null;
        if (captchaDTO.isMathType()) {
            String capText = captchaProducerMath.createText();
            String capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            imageBuffer = captchaProducerMath.createImage(capStr);
        } else if (captchaDTO.isCharType()) {
            String capStr = captchaProducer.createText();
            code = capStr;
            imageBuffer = captchaProducer.createImage(capStr);
        } else {
            throw new GlobalException("无效验证码类型");
        }

        FastByteArrayOutputStream fastByteArrayOutputStream = new FastByteArrayOutputStream();
        try {
            ImageIO.write(imageBuffer, "jpg", fastByteArrayOutputStream);
        } catch (IOException e) {
            log.error("图形验证码转换出错：{}", e.getMessage());
            throw new GlobalException("图形验证码转换出错");
        }

        captchaDTO.setCode(code);
        captchaDTO.setImg(Base64.encode(fastByteArrayOutputStream.toByteArray()));
    }

    @Override
    public Boolean checkCaptchaImg(String uuid, String code) {
        AssertUtils.isNotEmpty(uuid, "图像验证码uuid不能为空");
        AssertUtils.isNotEmpty(code, "图像验证码不能为空");

        String captchaCacheKey = CacheConstants.getFinalKey(CacheConstants.CAPTCHA_UUID, uuid);
        String trueCode = RedisUtils.get(captchaCacheKey);
        AssertUtils.isNotEmpty(trueCode, "验证码已失效，请刷新页面重试");

        return trueCode.equalsIgnoreCase(code);
    }

    @Override
    public void invalidCaptcha(String uuid) {
        AssertUtils.isNotEmpty(uuid, "图像验证码uuid不能为空");
        String captchaCacheKey = CacheConstants.getFinalKey(CacheConstants.CAPTCHA_UUID, uuid);
        RedisUtils.del(captchaCacheKey);
    }

    @Override
    public Boolean checkEmailCode(String emailUuid, String emailCode) {
        AssertUtils.isNotEmpty(emailUuid, "邮箱验证码uuid不能为空");
        AssertUtils.isNotEmpty(emailCode, "邮箱验证码不能为空");

        String emailCacheKey = CacheConstants.getFinalKey(CacheConstants.EMAIL_UUID, emailUuid);
        String trueCode = RedisUtils.get(emailCacheKey);
        AssertUtils.isNotEmpty(trueCode, "邮箱验证码已失效");

        return trueCode.equalsIgnoreCase(emailCode);
    }

    @Override
    public EmailVO sendRegisterEmail(EmailDTO emailDTO) {
        AssertUtils.isNotEmpty(emailDTO.getEmail(), "邮箱不能为空");
        emailDTO.setEmailType(EmailTypeEnum.REGISTER.getType());

        return emailService.sendEmail(emailDTO);
    }
}
