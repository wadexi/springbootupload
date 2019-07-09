package com.alan.demo.web;

import com.alan.demo.entity.SimpleReponse;
import com.alan.demo.entity.SimpleRequest;
import com.alibaba.fastjson.JSON;
import com.sun.org.apache.bcel.internal.util.ClassPath;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;


@Controller
public class SimpleController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/simpleweb")
    @ResponseBody
    public String simpleWeb(SimpleRequest request){
        System.out.println(request.toString());
        SimpleReponse reponse = new SimpleReponse();
        reponse.setCode(200);
        reponse.setSuccesed(true);
        reponse.setMessage("ok");
        return JSON.toJSONString(reponse);
    }

//    @RequestMapping("/download/absolute")
//    public void downLoadAbsoluteFile(HttpServletResponse response) throws UnsupportedEncodingException {
//        String filename="test.docx";
//        String filePath = "/usr/local/tomcat/apache-tomcat-8.5.34/webapps/test.docx" ;
//        File file = new File(filePath + "/" + filename);
//        if(file.exists()){ //判断文件父目录是否存在
//            response.setContentType("application/octet-stream;charset=UTF-8");
//            response.setCharacterEncoding("UTF-8");
//            // response.setContentType("application/force-download");
//            response.setHeader("Content-Disposition", "attachment;fileName=" +   java.net.URLEncoder.encode(filename,"UTF-8"));
//            byte[] buffer = new byte[1024];
//            FileInputStream fis = null; //文件输入流
//            BufferedInputStream bis = null;
//
//            OutputStream os = null; //输出流
//            try {
//                os = response.getOutputStream();
//                fis = new FileInputStream(file);
//                bis = new BufferedInputStream(fis);
//                int i = bis.read(buffer);
//                while(i != -1){
//                    os.write(buffer);
//                    i = bis.read(buffer);
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            System.out.println("----------file download---" + filename);
//            try {
//                bis.close();
//                fis.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


    @RequestMapping("/linux/download/absolute")
    public ResponseEntity<InputStreamResource> linuxDownloadFile() throws IOException {
        String filePath = "/usr/local/tomcat/apache-tomcat-8.5.34/webapps/test.docx";
        FileSystemResource file = new FileSystemResource(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
    }

    @RequestMapping(value = "/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        try {
//            if (file.
//            // 获取文件名isEmpty()) {
//                return "文件为空";
//            }
            String fileName = file.getOriginalFilename();
            System.out.println("上传的文件名为：" + fileName);
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            System.out.println("文件的后缀名为：" + suffixName);
            // 设置文件存储路径
//            String filePath = "/Users/dalaoyang/Downloads/";
            ClassPathResource classPathResource = new ClassPathResource("uploadFiles/");
            String path = classPathResource.getPath() + fileName;
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入
            return "上传成功";
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }


    @RequestMapping("/windows/download/absolute")
    public ResponseEntity<InputStreamResource> windowsDownloadFile()
            throws IOException {
        String filePath = "D:\\test.docx";
        FileSystemResource file = new FileSystemResource(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
    }

    @RequestMapping("/classpath/download/absolute")
    public ResponseEntity<InputStreamResource> windows2DownloadFile() throws IOException {
//        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
//        String filePath = path +"static/test.docx";
//        FileSystemResource file = new FileSystemResource(filePath);
        ClassPathResource classPathResource = new ClassPathResource("static/test.docx");
        System.out.println(classPathResource.getPath());
        InputStream inputStream = classPathResource.getInputStream();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", "test.docx"));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(inputStream.available())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(inputStream));
    }


//    @RequestMapping("/download/relatively")
//    public String downLoadRelativelyFile(HttpServletResponse response) throws UnsupportedEncodingException {
//        String filename="2.xlsx";
//        String filePath = "D:/test.docx" ;
//        File file = new File(filePath + "/" + filename);
//        if(file.exists()){ //判断文件父目录是否存在
//            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
//            response.setCharacterEncoding("UTF-8");
//            // response.setContentType("application/force-download");
//            response.setHeader("Content-Disposition", "attachment;fileName=" +   java.net.URLEncoder.encode(filename,"UTF-8"));
//            byte[] buffer = new byte[1024];
//            FileInputStream fis = null; //文件输入流
//            BufferedInputStream bis = null;
//
//            OutputStream os = null; //输出流
//            try {
//                os = response.getOutputStream();
//                fis = new FileInputStream(file);
//                bis = new BufferedInputStream(fis);
//                int i = bis.read(buffer);
//                while(i != -1){
//                    os.write(buffer);
//                    i = bis.read(buffer);
//                }
//
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            System.out.println("----------file download---" + filename);
//            try {
//                bis.close();
//                fis.close();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }

}
