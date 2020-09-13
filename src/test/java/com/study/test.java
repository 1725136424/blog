package com.study;


import com.study.pojo.user.User;
import com.study.service.UserService;
import com.study.util.MailUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class test {

    @Autowired
    private UserService userService;

    @Autowired
    private MailUtils mailUtils;

    @Test
    public void test() throws MessagingException {
        String username = "1725136424@qq.com";
        User user = userService.findByUsername(username);
        // 验证用户名是否存在
        // 生成秘钥
        String secretKey = UUID.randomUUID().toString();
        // 生成过期时间
        Timestamp outTime = new Timestamp(System.currentTimeMillis() + (30 * 60 * 1000));
        long time = outTime.getTime() / 1000 * 1000; // 忽略毫秒数
        // 设置用户密钥
        user.setValidateCode(secretKey);
        user.setRegisterDate(new Date(outTime.getTime()));
        userService.update(user);
        // 生成唯一的key
        String key = user.getUsername() + "$" + time + "$" + secretKey;
        String digitalSignature = new Md5Hash(key).toString();
        String title = "豪哥找回密码";
        String url = "/resetPassword?sid=" + digitalSignature + "&username=" + user.getUsername();
        String text = "请勿回复本邮件.点击下面的链接,重设密码<br/><a href=" + url + " target='_BLANK'>点击我重新设置密码</a>" +
                "<br/>tips:本邮件超过30分钟,链接将会失效，需要重新申请'找回密码'" + key + "\t" + digitalSignature;
        System.out.println(System.currentTimeMillis());
        mailUtils.sendMail(username, title, text);
        System.out.println(System.currentTimeMillis());
    }

}
