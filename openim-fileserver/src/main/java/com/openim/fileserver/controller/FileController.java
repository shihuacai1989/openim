package com.openim.fileserver.controller;

import com.openim.common.bean.CommonResult;
import com.openim.fileserver.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by shihc on 2015/8/17.
 */
@RequestMapping("/file")
@Controller
public class FileController {

    @Autowired
    private IFileService fileService;

    @RequestMapping(value = "/upload", method = {RequestMethod.GET, RequestMethod.POST}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public CommonResult upload(@RequestParam MultipartFile file){
        return fileService.upload(file);
    }

    @RequestMapping(value = "/download", method = {RequestMethod.GET, RequestMethod.POST})
    public void download(String fileId, HttpServletResponse response){
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM.getType());
        /*byte[] csvFileByte = new byte[(int)csvFile.length()];
        FileInputStream fileInputStream = new FileInputStream(file);
        fileInputStream.read(csvFileByte);
        //Writing file to output
        response.setContentType("application/xml");
        response.addProperty("Content-disposition", "atachment; filename="+csvFile.toString());
        OutputStream out = response.getPortletOutputStream();
        out.write(csvFileByte);
        out.flush();
        out.close();*/
    }
}
