package com.openim.fileserver.configuration;

import com.openim.fileserver.service.IFileService;
import com.openim.fileserver.service.impl.LocalFileServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by shihc on 2015/8/17.
 */
@Configuration
public class FileServerConfiguration {

    @Bean
    IFileService fileService() {
        return new LocalFileServiceImpl();
    }
}
