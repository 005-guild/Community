package com.nowcoder.community.controller;

import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Value("${community.path.domain}")
    private String domain;
    @Value("${community.path.upload}")
    private String uploadPath;
    @Autowired
    private HostHolder hostHolder;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(path = "/setting", method = RequestMethod.GET)
    public String getSettingPage() {
        return "/site/setting";
    }

    /**
     * 把文件存到服务器中
     * @param multipartFile
     * @param model
     * @return
     */
    @RequestMapping(path="/upload",method = RequestMethod.POST)
    public String uploadHeader(MultipartFile multipartFile, Model model){
        if(multipartFile==null){
            model.addAttribute("error","您没有上传任何图片");
            return "/site/setting";
        }
        String fileName = multipartFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //System.out.println(suffix.equals(".jpg"));
        if(StringUtils.isBlank(suffix)||!(suffix.equals(".png")||suffix.equals(".jpg"))){
            model.addAttribute("error","文件格式错误，请重新上传");
            return "/site/setting";
        }
        fileName = CommunityUtil.generateUUID()+suffix;
        File dst = new File(uploadPath+"/"+fileName);
        try {
            multipartFile.transferTo(dst);
        } catch (IOException e) {
            logger.error("上传失败："+e.getMessage());
            throw new RuntimeException("服务器发生失败，上传出现异常"+e);
        }
        //更新headerUrl这里规定格式张这样
        //http://localhost:8080/community/user/header/filename
        String headerUrl = domain+contextPath+"/user/header/"+fileName;
        User user = hostHolder.getUser();
        userService.updateHeader(user.getId(),headerUrl);
        return "redirect:/user/setting";
    }
    /**
     * 当用户读取headerUrl时从本地读取后返回
     * @param filename
     * @param response
     */
    @RequestMapping(path = "/header/{filename}",method = RequestMethod.GET)
    public void getImg(@PathVariable("filename")String filename, HttpServletResponse response){
        //服务器存放地址
        filename = uploadPath+"/"+filename;
        try (ServletOutputStream os = response.getOutputStream();
             InputStream is = new FileInputStream(filename);)
        {
            int len = 0;
            byte[] buffer = new byte[1024];
            while((len=is.read(buffer))!=-1){
                os.write(buffer,0,len);
            }
        } catch (IOException e) {
            logger.error("读取文件失败:"+e.getMessage());
        }
    }

    @RequestMapping(path = "/changePassword", method = {RequestMethod.GET,RequestMethod.POST})
    //修改密码，model变量用来向页面返回数据
    public String changePassword(String oldPassword,String newPassword,String confirmPassword, Model model) {
        User user = hostHolder.getUser();
        Map<String, Object> map = userService.changePassword(user,oldPassword, newPassword, confirmPassword);
        if(map == null || map.isEmpty()){
            return "redirect:/index";
        }else {
            model.addAttribute("oldPasswordMsg", map.get("oldPasswordMsg"));
            model.addAttribute("newPasswordMsg", map.get("newPasswordMsg"));
            model.addAttribute("confirmPasswordMsg", map.get("confirmPasswordMsg"));
            return "/site/setting";
        }
    }
}
