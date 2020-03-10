package com.xin.controller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.xin.exception.SysException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by xinBa.
 * User: 辛聪明
 * Date: 2020/3/9
 */
@Controller
@RequestMapping("/user")
public class FileUploadController {

    @RequestMapping("interceptor")
    public String interceptor(){
        System.out.println("interceptor controller执行了");
        return "success";
    }

    @RequestMapping("/exception")
    public String exception() throws SysException{
        try {
            int a = 10/0;
        } catch (Exception e) {
            e.printStackTrace();
            //抛出自定义异常
            throw new SysException("计算错误。。。");
        }
        return "success";
    }

    /**
     * 跨服务器文件上传
     * @return
     */
    @RequestMapping("/fileupload3")
    public String fileuoload3(MultipartFile upload) throws Exception {
        System.out.println("跨服务器文件上传...");

        // 定义上传文件服务器路径
        String path = "http://localhost:9090/uploads/";

        // 说明上传文件项
        // 获取上传文件的名称
        String filename = upload.getOriginalFilename();
        // 把文件的名称设置唯一值，uuid
        String uuid = UUID.randomUUID().toString().replace("-", "");
        filename = uuid+"_"+filename;

        // 创建客户端的对象
        Client client = Client.create();

        // 和图片服务器进行连接
        WebResource webResource = client.resource(path + filename);

        // 上传文件
        webResource.put(upload.getBytes());

        return "success";
    }

    @RequestMapping("/fileupload2")
    public void fileupload2(HttpServletRequest request, HttpServletResponse response,MultipartFile upload) throws Exception {
        System.out.println("SpringMVC上传文件");

        //上传位置
        String path = request.getSession().getServletContext().getRealPath("/uploads2/");
        System.out.println("path: "+path);
        //判断，不存在创建
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }

        String name = upload.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        name = uuid+"_"+name;
        upload.transferTo(new File(path,name));

        request.getRequestDispatcher("/WEB-INF/success.jsp").forward(request, response);
        return;
    }

    @RequestMapping("/fileupload")
    public ModelAndView fileupload(HttpServletRequest request, HttpServletResponse response) throws Exception {

        System.out.println("上传文件");

        //上传位置
        String path = request.getSession().getServletContext().getRealPath("/uploads/");
        System.out.println("path: "+path);

        //判断，不存在创建
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }

        //解析request对象，获取上传项
        DiskFileItemFactory factory = new DiskFileItemFactory();  //磁盘文件项工厂
        ServletFileUpload upload = new ServletFileUpload(factory);
        //解析request
        List<FileItem> items = upload.parseRequest(request);
        for(FileItem item : items){
            //进行判断，是否是上传文件项目（即判断enctype是否是文件上传）
            if(item.isFormField()){
                //是普通表单
            }else {
                String name1 = item.getName();
                String name2 = UUID.randomUUID().toString().replace("-", "");

                String name = name2+"_"+name1;
                item.write(new File(path,name));
                //删除临时文件，大于10k就会保存临时文件，小于10k缓存
                item.delete();
            }
        }

        //使用视图解析器
        ModelAndView mv = new ModelAndView();
        mv.addObject("username", "张三");
        mv.setViewName("success");      // 返回sucess界面
        return mv;
    }
}
