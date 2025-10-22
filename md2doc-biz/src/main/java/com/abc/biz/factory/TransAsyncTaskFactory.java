package com.abc.biz.factory;

import cn.hutool.extra.spring.SpringUtil;
import com.abc.biz.domain.dto.TransDTO;
import com.abc.biz.service.TransService;
import lombok.extern.slf4j.Slf4j;

import java.util.TimerTask;

/**
 * 积分异步工厂（产生任务用）
 *
 * @author LiJunxi
 */
@Slf4j
public class TransAsyncTaskFactory {

    public static TimerTask recordTransTask(TransDTO transDTO, String content) {
        return new TimerTask() {
            @Override
            public void run() {
                TransService transService = SpringUtil.getBean(TransService.class);
                transDTO.setTransContent(content);
                transService.saveTrans(transDTO);
            }
        };
    }
}
