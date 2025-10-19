package com.abc.system.service;

import com.abc.system.domain.vo.FileVO;
import com.abc.system.domain.dto.OssFileUploadDTO;
import org.springframework.http.ResponseEntity;

public interface OssService {

    FileVO uploadOss(OssFileUploadDTO req);

    ResponseEntity<byte[]> downloadOss(Long fileId);

}
