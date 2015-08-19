package com.openim.fileserver.service;

import com.openim.common.bean.CommonResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;

/**
 * Created by shihc on 2015/8/17.
 */
public interface IFileService {
    public CommonResult<String> upload(MultipartFile file);
    public void download(String fileId, OutputStream outputStream);
}
