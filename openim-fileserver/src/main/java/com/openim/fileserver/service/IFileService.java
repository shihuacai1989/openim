package com.openim.fileserver.service;

import com.openim.common.im.bean.CommonResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;

/**
 * Created by shihc on 2015/8/17.
 */
public interface IFileService {
    CommonResult<String> upload(MultipartFile file);
    void download(String fileId, OutputStream outputStream);
}
