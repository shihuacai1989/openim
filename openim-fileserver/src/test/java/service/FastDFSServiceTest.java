package service;

import com.openim.fileserver.FileServerApplication;
import com.openim.fileserver.service.IFileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by shihc on 2015/8/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = FileServerApplication.class)
@WebAppConfiguration
public class FastDFSServiceTest {

    @Autowired
    IFileService fileService;

    @Test
    public void uploadFile(){

    }

    @Test
    public void downFile(){

    }
}
