package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.basic.BasicDesktopIconUI;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @author HLxxx
 * @version 1.0
 * 通用接口
 */
@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * ファイルのアップロード
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("ファイルのアップロード")
    public Result<String> upload(MultipartFile file) {
        log.info("ファイルのアップロード:{}", file);

        try {
            String originalFilename = file.getOriginalFilename();
            int index = originalFilename.lastIndexOf(".");
            String extname = originalFilename.substring(index);
            String objectName = UUID.randomUUID().toString() + extname;
            log.info("新しいファイルの名:{}", objectName);
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            return Result.success(filePath);
        } catch (IOException e) {
            log.error("ファイルのアップロー失敗しました", e);
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);

       //file.transferTo(new File("/Users/dreammtank125/the-sky-take-out/sky-take-out/image/"+newFileName));
        //return Result.success(newFileName);
    }
//    @GetMapping("/download")
//    public void download(String name, HttpServletResponse response){
//        response.setContentType("image/jpeg");
//        try {
//            FileInputStream fileInputStream = new FileInputStream(new File("/Users/dreammtank125/the-sky-take-out/sky-take-out/image/" + name));
//            ServletOutputStream outputStream = response.getOutputStream();
//            int len = 0;
//            byte[] bytes = new  byte[1024];
//            while((len=fileInputStream.read(bytes)) != -1){
//                outputStream.write(bytes,0,len);
//                outputStream.flush();
//            }
//            outputStream.close();
//            fileInputStream.close();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
