package com.nowcoder.community;

import com.nowcoder.community.util.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTest {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testTxtMail(){
        mailClient.senMail("1505040475@qq.com","i","haha");
    }

    @Test
    public void testHtmlMail(){
        Context context = new Context();
        context.setVariable("username","张三");
        String content = templateEngine.process("/mail/demo",context);
        //827379564@qq.com
        mailClient.senMail("827379564@qq.com","你是猪","猪猪");
    }
}
