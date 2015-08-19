package com.openim.fileserver.service.impl;

import com.openim.common.bean.CommonResult;
import com.openim.common.bean.ResultCode;
import com.openim.common.util.FileUtil;
import com.openim.fileserver.service.IFileService;
import org.apache.commons.io.IOUtils;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Created by shihc on 2015/8/17.
 */
public class FastDFSFileServiceImpl implements IFileService, InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(FastDFSFileServiceImpl.class);

    private static final String SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR = "/";

    private StorageClient storageClient;

    @Override
    public CommonResult<String> upload(MultipartFile file) {
        int code = ResultCode.success;
        String fileId = null;
        String error = null;
        try {
            String[] results = storageClient.upload_file(null, file.getSize(),
                    new UploadLocalFileSender(file.getInputStream()), FileUtil.getSuffix(file.getOriginalFilename()), null);

            if (results == null) {
                code = ResultCode.error;
                error = "上传至文件服务器失败";
                LOG.error("upload file fail, error code: " + storageClient.getErrorCode());
            } else {
                String groupName = results[0];
                String remoteFileName = results[1];
                fileId = groupName + SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR + remoteFileName;
            }
        } catch (Exception e) {
            code = ResultCode.error;
            LOG.error(e.toString());
        }

        return new CommonResult<String>(code, fileId, error);
    }

    @Override
    public void download(String fileId, OutputStream outputStream) {
        try {
            String[] results = fileId.split(SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR);
            int error = storageClient.download_file(results[0], results[1], new DownloadFileWriter(outputStream));
            if (error != 0) {
                LOG.error("Download file fail, error no: " + error);
            }
        } catch (Exception e) {
            LOG.error(e.toString());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        URL fileURL = this.getClass().getResource("/fastDFS_client.conf");
        String configFile = fileURL.getFile();

        ClientGlobal.init(configFile);

        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();

        StorageServer storageServer = null;

  		/*
          storageServer = tracker.getStoreStorage(trackerServer);
  		if (storageServer == null)
  		{
  			System.out.println("getStoreStorage fail, error code: " + tracker.getErrorCode());
  			return;
  		}
  		*/

        storageClient = new StorageClient(trackerServer, storageServer);
    }


    private class UploadLocalFileSender implements UploadCallback {
        private InputStream inputStream;

        public UploadLocalFileSender(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        /**
         * send file content callback function, be called only once when the file uploaded
         *
         * @param out output stream for writing file content
         * @return 0 success, return none zero(errno) if fail
         */
        public int send(OutputStream out) throws IOException {
            IOUtils.copy(inputStream, out);
            return 0;
        }
    }

    private class DownloadFileWriter implements DownloadCallback {
        private OutputStream outputStream = null;
        private long current_bytes = 0;

        public DownloadFileWriter(OutputStream outputStream) {
            this.outputStream = outputStream;
        }

        public int recv(long file_size, byte[] data, int bytes) {

            try {

                this.outputStream.write(data, 0, bytes);
                this.current_bytes += bytes;

                if (this.current_bytes == file_size) {
                    this.outputStream.close();
                    this.outputStream = null;
                    this.current_bytes = 0;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                return -1;
            }

            return 0;
        }

        protected void finalize() throws Throwable {
            if (this.outputStream != null) {
                this.outputStream.close();
                this.outputStream = null;
            }
        }
    }
}
