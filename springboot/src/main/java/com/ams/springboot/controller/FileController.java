package com.ams.springboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import com.ams.springboot.common.Constants;
import com.ams.springboot.utils.AliOssUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ams.springboot.common.Result;
import com.ams.springboot.entity.Files;
import com.ams.springboot.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/file")
public class FileController {

    @Value("${files.upload.path}")
    private String fileUploadPath;
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    @Autowired
    private FileMapper fileMapper;
    /**
     * 文件存储
     * **/
    @PostMapping("/upload_photo")
    public Result upload_photo(@RequestParam MultipartFile file) {
        String url=null;
        JSONObject resp = new JSONObject();
        try{
            url=AliOssUtil.upload("photo",file);
            resp.set("url",url);
            return Result.success(resp);
        }catch (Exception e){
            resp.set("msg",e.getMessage());
            return Result.error(Constants.CODE_400,"图片上传失败");
        }
    }

    @PostMapping("/upload_annex")
    public Result upload_annex(@RequestParam MultipartFile file) {
        String url=null;
        JSONObject resp = new JSONObject();
        try{
            url=AliOssUtil.upload("annex",file);
            resp.set("url",url);
            return Result.success(resp);
        }catch (Exception e){
            resp.set("msg",e.getMessage());
            return Result.error(Constants.CODE_400,"图片上传失败");
        }
    }
    @PostMapping("/upload1")
    public String upload1(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        //自定义一个文件的唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid + StrUtil.DOT + type;

        File uploadFile = new File(fileUploadPath + fileUUID);
        //判断配置的文件目录是否存在，若不存在创建一个新文件夹
        if(!uploadFile.getParentFile().exists()){
            uploadFile.getParentFile().mkdirs();
        }
        String md5;
        String url;
        //上传文件到磁盘
        file.transferTo(uploadFile);
        //获取文件的md5
        md5 = SecureUtil.md5(uploadFile);
        //查询是否存相同文件
        Files files = getFileByMd5(md5);
        if(files!=null){
            //使用数据库中已有的文件的url，并且删除本次上传的文件
            url = files.getUrl();
            uploadFile.delete();
        }else {
            //把获取到的文件存储到磁盘目录
            url = "http://localhost:9090/file/" + fileUUID;
        }

        //存储数据库
        //将下载链接存入数据库
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setSize((size/1024));
        saveFile.setType(type);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileMapper.insert(saveFile);
        return url;
    }
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
       //根据文件的唯一标识码获取文件
        File uploadFile  = new File(fileUploadPath + fileUUID);
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileUUID,"UTF-8"));
        response.setContentType("application/octet-stream");

        //读取上传字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    //通过文件的md5查询文件

    /**
     * 分页查询接口
     * **/
    private Files getFileByMd5(String md5){
        //查询文件的md5是否存在
        QueryWrapper<Files> queryWrapper = new QueryWrapper();
        queryWrapper.eq("md5",md5);
        return fileMapper.selectOne(queryWrapper);
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {
        QueryWrapper<Files> queryWrapper=new QueryWrapper<>();
        //查询未删除的文件
        queryWrapper.eq("is_delete",false);
        queryWrapper.orderByDesc("id");
        if(!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success(fileMapper.selectPage(new Page<>(pageNum, pageSize),queryWrapper));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        Files files = fileMapper.selectById(id);
        files.setIsDelete(true);
        //对逻辑删除字段进行更新
        fileMapper.updateById(files);
        return Result.success();
    }

    //批量删除
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        QueryWrapper<Files> queryWrapper=new QueryWrapper<>();
        queryWrapper.in("id",ids);
        List<Files> files = fileMapper.selectList(queryWrapper);
        for (Files file : files) {
            file.setIsDelete(true);
            fileMapper.updateById(file);
        }
        return Result.success();
    }

    //更新
    @PostMapping("/update")
    public Result save(@RequestBody Files files) {
        return Result.success(fileMapper.updateById(files));
    }

}
