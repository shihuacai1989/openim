package com.openim.fileserver.service.impl;

import com.openim.common.im.bean.CommonResult;
import com.openim.fileserver.service.IFileService;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;

/**
 * Created by shihc on 2015/8/17.
 */
public class MongoFileServiceImpl implements IFileService {
    @Override
    public CommonResult<String> upload(MultipartFile file) {
        return null;
    }

    @Override
    public void download(String fileId, OutputStream outputStream) {

    }
}
