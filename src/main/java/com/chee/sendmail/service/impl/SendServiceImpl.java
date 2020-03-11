package com.chee.sendmail.service.impl;

import com.chee.sendmail.config.GroupSetConfig;
import com.chee.sendmail.config.MailToConfig;
import com.chee.sendmail.entity.TableRow;
import com.chee.sendmail.service.SendService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SendServiceImpl implements SendService {
    @Autowired
    private MailToConfig mailToConfig;
    @Autowired
    private GroupSetConfig groupSetConfig;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendToClient(List<TableRow> sendList, LocalDate sendDate) {
        List<GroupSetConfig.Group> group = groupSetConfig.getGroup();

        List<MailToConfig.Client> clients = mailToConfig.getClient();
        for (MailToConfig.Client client : clients) {
            Context context = new Context();
            context.setVariable("nickname", client.getNickname());
            List<String> member = group.stream().filter(oneGroup -> oneGroup.getName().equals(client.getGroup())).findFirst().orElseThrow(RuntimeException::new).getMember();
            List<TableRow> collect = sendList.stream().filter(tableRow -> member.contains(tableRow.getName())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(collect)) {
                continue;
            }
            context.setVariable("sendList", collect);
            context.setVariable("groupName", client.getGroup());
            context.setVariable("date", sendDate.getMonthValue() + "月" + sendDate.getDayOfMonth() + "日");
            String emailContent = templateEngine.process("SendToClient", context);
            this.sendHtmlMail(client.getMailAddress(), client.getGroup() + sendDate.getMonthValue() + "月" + sendDate.getDayOfMonth() + "日工作情况", emailContent);
        }

    }

    @Override
    public void sendToManager(String filePath, LocalDate sendDate) {
        List<MailToConfig.Manager> manager = mailToConfig.getManager();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(manager.stream().map(MailToConfig.Manager::getMailAddress).toArray(String[]::new));
            helper.setSubject("还消组" + sendDate.getMonthValue() + "月" + sendDate.getDayOfMonth() + "日工作情况");
            String call = manager.stream().map(MailToConfig.Manager::getNickname).collect(Collectors.joining("、", "", "：\n"));
            helper.setText(call + "\t还消组" + sendDate.getMonthValue() + "月" + sendDate.getDayOfMonth() + "日工作情况见附件。\n");

            // 附件
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = file.getFilename();
            helper.addAttachment(fileName, file);

            JavaMailSenderImpl javaMailSender = (JavaMailSenderImpl) this.javaMailSender;
            javaMailSender.getJavaMailProperties().put("mail.smtp.auth", true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }

    private void sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            JavaMailSenderImpl javaMailSender = (JavaMailSenderImpl) this.javaMailSender;
            javaMailSender.getJavaMailProperties().put("mail.smtp.auth", true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }

}
