package com.nowcoder.community;

import com.nowcoder.community.util.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.swing.text.AbstractDocument;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTests {
    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testTextMail() {
        mailClient.sendMail("1012052510@qq.com", "我的测试", "springBoot邮件测试！");
    }

    @Test // 由于是在Test方法里测试而不是在controller里运用，所以不能用@RequestMapping返回HTML，只需要借用TemplateEngine即可。
    public void testHtmlMail() {
        Context context = new Context();  //利用thymeleaf下的content传入变量
        context.setVariable("username", "liam");

        String content = templateEngine.process("/mail/demo", context); //借用变量，生成HTML
        System.out.println(content);

        mailClient.sendMail("1012052510@qq.com", "测试", content);
    }
}
