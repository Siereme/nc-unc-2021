package com.app.service.mail.job;

import com.app.model.emailInfo.NewEmail;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

public class QueueItemProcessor implements ItemProcessor<NewEmail, NewEmail> {

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    @Override
    public NewEmail process(NewEmail email) throws Exception {
        Context context = new Context();
        context.setVariable("type", email.getType());
        context.setVariable("text", email.getText());
        context.setVariable("from", email.getFrom());

        String html = thymeleafTemplateEngine.process("email.html", context);
        email.setText(html);
        return email;
    }
}
