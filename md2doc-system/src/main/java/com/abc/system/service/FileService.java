package com.abc.system.service;

import com.abc.common.domain.vo.PageResult;
import com.abc.system.domain.dto.FileDTO;
import com.abc.system.domain.entity.File;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 文件接口
 *
 * @author LiJunXi
 * @date 2025-10-07
 */
public interface FileService extends IService<File> {

    PageResult getFilePageWithUiParam(FileDTO fileDTO);

    void updateFile(FileDTO fileDTO);

    void saveFile(FileDTO fileDTO);

    void deleteFile(FileDTO fileDTO);

    File checkAndGet(Long fileId);

}
