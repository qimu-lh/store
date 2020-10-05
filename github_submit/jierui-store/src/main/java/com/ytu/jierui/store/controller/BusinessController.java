package com.ytu.jierui.store.controller;

import com.ytu.jierui.store.entity.Attach;
import com.ytu.jierui.store.entity.Business;
import com.ytu.jierui.store.entity.Email;
import com.ytu.jierui.store.mapper.BusinessMapper;
import com.ytu.jierui.store.service.IAttachService;
import com.ytu.jierui.store.service.IBusinessService;
import com.ytu.jierui.store.service.IEmailService;
import com.ytu.jierui.store.service.ex.*;
import com.ytu.jierui.store.util.JsonResult;
import com.ytu.jierui.store.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("businesses")
public class BusinessController extends BaseController{

    /**
     * 上传的头像的最大大小
     */
    private static final long AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    /**
     * 上传时允许的头像文件的类型
     */
    private static final List<String> AVATAR_CONTENT_TYPES = new ArrayList<>();
    /**
     * 初始化上传时允许的头像文件的类型
     */
    static {
        AVATAR_CONTENT_TYPES.add("image/jpeg");
        AVATAR_CONTENT_TYPES.add("image/png");
        AVATAR_CONTENT_TYPES.add("image/jpg");
    }

    /**
     * 邮件发送者
     */
    private static final String SENDER_EMAIL="1908773440@qq.com";
    /**
     * 邮件主题
     */
    private static final String EMAIL_SUBJECT="激活链接";

    @Autowired
    private IBusinessService businessService;
    @Autowired
    private IAttachService attachService;
    @Autowired
    private IEmailService emailService;
    @Autowired
    private BusinessMapper businessMapper;

    @RequestMapping("reg")
    public JsonResult<Void> reg(@RequestParam("business") String business,
                                @RequestParam("idCardFace") MultipartFile file01,
                                @RequestParam("idCardBack") MultipartFile file02,
                                @RequestParam("personCard") MultipartFile file03,
                                HttpServletRequest request) {
        // 检查文件是否为空
        if (file01.isEmpty()||file02.isEmpty()||file03.isEmpty()) {
            throw new FileEmptyException("上传失败！请选择有效的文件！");
        }

        // 检查文件大小
        if (file01.getSize() > AVATAR_MAX_SIZE||file02.getSize() > AVATAR_MAX_SIZE||file03.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("上传失败！不允许使用超过" + (AVATAR_MAX_SIZE / 1024) + "KB的文件！");
        }

        // 检查文件类型
        if (!AVATAR_CONTENT_TYPES.contains(file01.getContentType())
        ||!AVATAR_CONTENT_TYPES.contains(file02.getContentType())
        ||!AVATAR_CONTENT_TYPES.contains(file03.getContentType())) {
            throw new FileTypeException("上传失败！仅允许使用以下类型的图片文件：" + AVATAR_CONTENT_TYPES);
        }

        //将字符串传为business对象
        //System.out.println(business);
        Business businessObject=ObjectUtil.toBusiness(business);
        //为发邮件做好参数赋值
        String sendIdCard=businessObject.getIdCard();
        String sendReceiverEmail=businessObject.getEmail();

        //根据idCard查询用户，以确保身份证唯一
        String idCard=businessObject.getIdCard();
        Business result = businessMapper.selectByCardId(idCard);
        // 判断查询结果是否不为null
        if (result != null) {
            // 是：用户名已存在，不允许注册，抛出UsernameConflictException异常
            throw new UsernameConflictException("身份证号为(" + idCard + ")已经被占用！");
        }

        //先使插入的idCard为空，后续通过邮件激活，再讲其存入数据库
        businessObject.setIdCard(null);
        businessService.reg(businessObject);
        //System.out.println(businessObject);
        // 确定文件夹
        String dirPath = request.getServletContext().getRealPath("upload");
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 确定文件名
        String filename01 = getFilename(file01);
        String filename02 = getFilename(file02);
        String filename03 = getFilename(file03);

        // 执行保存
        File dest01 = new File(dir, filename01);
        File dest02 = new File(dir, filename02);
        File dest03 = new File(dir, filename03);
        try {
            file01.transferTo(dest01);
            file02.transferTo(dest02);
            file03.transferTo(dest03);
            System.out.println("保存成功");
        } catch (IllegalStateException e) {
            throw new FileUploadStateException("上传失败！请检查原文件是否存在并可以被访问！");
        } catch (IOException e) {
            throw new FileUploadIOException("上传失败！读出数据时出现未知错误！");
        }

        // 更新数据表
        String idCardFace = "/upload/" + filename01;
        String idCardBack = "/upload/" + filename02;
        String personCard = "/upload/" + filename03;
        Attach attach=new Attach();
        attach.setIdCardFace(idCardFace);
        attach.setIdCardBack(idCardBack);
        attach.setPersonCard(personCard);
        attachService.saveAttach(attach);

        System.out.println(dirPath);
        //发送激活邮件
        Email email=new Email();
        email.setSender(SENDER_EMAIL);
        email.setSubject(EMAIL_SUBJECT);
        email.setReceiver(sendReceiverEmail);
        String sendContext="http://127.0.0.1:8080/businesses/activate?idCard="+sendIdCard+"&email="+sendReceiverEmail;
        email.setContext(sendContext);
        emailService.sendSimpleEmail(email);

        return new JsonResult<>(SUCCESS);
    }

    @RequestMapping("activate")
    public String updateIdCard(@RequestParam("idCard") String idCard,
                               @RequestParam("email") String email){
        businessService.updateId(idCard, email);
        return "index";
    }
}
