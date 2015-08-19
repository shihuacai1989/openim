package com.openim.fileserver.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.openim.common.bean.CommonResult;
import com.openim.common.bean.ResultCode;
import com.openim.fileserver.service.IFileService;
import org.ektorp.AttachmentInputStream;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.UUID;

/**
 * Created by shihc on 2015/8/17.
 */
public class CouchDBFileServiceImpl implements IFileService {

    @Override
    public CommonResult<String> upload(MultipartFile file) {
        int code = ResultCode.success;
        String fileId = null;


        try {
            HttpClient httpClient = new StdHttpClient.Builder()
                    .url("http://admin:123@localhost:5984")
                            //.username()
                            //.password()
                    .build();
            // httpClient.
            CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);

            CouchDbConnector db = new StdCouchDbConnector("openIM", dbInstance);
            db.createDatabaseIfNotExists();

            ObjectMapper mapper=new ObjectMapper();
            ObjectNode pull=mapper.createObjectNode();
            String id = UUID.randomUUID().toString();
            pull.put("_id",id);
            //pull.put("title2","test222");

            db.create(pull);

            String rev = db.getCurrentRevision(id);
            InputStream data = null;
            try {
                data = file.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String contentType = "image/png";

            AttachmentInputStream a = new AttachmentInputStream(file.getOriginalFilename(),
                    data,
                    contentType);

            rev = db.createAttachment(id, rev, a);


            /*InputStream data2 = null;
            try {
                data2 = new FileInputStream("C:\\Users\\xxx\\Desktop\\aa.html");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String contentType2 = "text/html";

            AttachmentInputStream a2 = new AttachmentInputStream("cc.html",
                    data2,
                    contentType2);
            rev = db.createAttachment(id, rev, a2);*/
            /*try {
                File file = new File("C:\\Users\\xxxx\\Desktop\\QQ截图20150326220232.png");
                InputStream tt = new FileInputStream(file);
                db.updateMultipart("document_id",
                        tt,
                        "abc_boundary",
                        file.length(),
                        new Options());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public void download(String fileId, OutputStream outputStream) {
        
    }
}
