package com.openim.fileserver.service.impl;

import com.openim.common.bean.CommonResult;
import com.openim.common.bean.ResultCode;
import com.openim.common.util.UUIDUtil;
import com.openim.fileserver.service.IFileService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Created by shihc on 2015/8/17.
 */
public class LocalFileServiceImpl implements IFileService {
    private static final Logger LOG = LoggerFactory.getLogger(LocalFileServiceImpl.class);

    @Value("${local.file.dir}")
    private String localFileDir;


    @Override
    public CommonResult<String> upload(MultipartFile file) {
        String fileId = UUIDUtil.genericUUID();
        int code = ResultCode.success;
        String error = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = file.getInputStream();
            outputStream = new FileOutputStream(localFileDir + File.separator + fileId);

            IOUtils.copy(inputStream, outputStream);
        }catch (Exception e){
            LOG.error(e.toString());
            code = ResultCode.error;
            error =e.toString();
        }finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }

        return new CommonResult<String>(code, fileId, error);
    }

    @Override
    public void download(String fileId, OutputStream outputStream) {
        try {
            InputStream inputStream  = new FileInputStream(localFileDir + File.separator + fileId);
            IOUtils.copy(inputStream, outputStream);
        } catch (FileNotFoundException e) {
            LOG.error(e.toString());
        } catch (IOException e) {
            LOG.error(e.toString());
        }
    }
}
