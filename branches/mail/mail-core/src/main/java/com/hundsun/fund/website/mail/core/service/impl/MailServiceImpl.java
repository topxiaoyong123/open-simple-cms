package com.hundsun.fund.website.mail.core.service.impl;

import com.hundsun.fund.website.mail.core.service.MailService;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.core.io.FileSystemResource;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: mid
 * Date: 2010-1-8
 * Time: 10:03:46
 * To change this template use File | Settings | File Templates.
 */
public class MailServiceImpl implements MailService {

    private JavaMailSenderImpl mailSender;

    public void setMailSender(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    private JavaMailSenderImpl mailSenderSSL;

    public void setMailSenderSSL(JavaMailSenderImpl mailSenderSSL) {
        this.mailSenderSSL = mailSenderSSL;
    }

    private String mailto;

    private String[] mailtos;

    private String mailfrom;

    private String subject;

    private String text;

    //TODO 附件
    private List files = new ArrayList();

    public void addFile(String file){
        files.add(file);
    }

    public void addFile(String path, String filename, byte[] data){
        try{
            File filedir = new File(path);
            if(!filedir.exists()){
                filedir.mkdirs();
            }
            File file = new File(filedir, filename);
            FileOutputStream os = new FileOutputStream(file);
            os.write(data);
            os.close();
            files.add(file.getAbsolutePath());
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public List getFiles() {
        return files;
    }

    public void setFiles(List files) {
        this.files = files;
    }

    public String getMailto() {
        return mailto;
    }

    public void setMailto(String mailto) {
        this.mailto = mailto;
    }

    public String[] getMailtos() {
        return mailtos;
    }

    public void setMailtos(String[] mailtos) {
        this.mailtos = mailtos;
    }

    public String getMailfrom() {
        return mailfrom;
    }

    public void setMailfrom(String mailfrom) {
        this.mailfrom = mailfrom;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private Boolean isSSL = false;

    public Boolean isSSL() {
        return isSSL;
    }

    public void setSSL(Boolean SSL) {
        isSSL = SSL;
    }

    public void sendMail() {
        JavaMailSenderImpl finalMailSender = mailSender;
        if(this.isSSL()){
            finalMailSender = mailSenderSSL;    
        }
        MimeMessage mailMessage = finalMailSender.createMimeMessage();
        try {
            String[] mailto = this.getMailtos();
            if(mailto == null){
                mailto = this.getMailto().split(";");
            }
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, "utf-8");
            helper.setFrom(this.getMailfrom());
            helper.setTo(mailto);

            //TODO 发送附件
            try{
                if(files != null && files.size() > 0){
                    for(int i=0; i<files.size(); i++){
                        FileSystemResource file = new FileSystemResource((String)files.get(i));
                        helper.addAttachment(file.getFilename(), file);
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }

            helper.setSubject(this.getSubject());
            helper.setText(this.getText(), true);
            finalMailSender.send(mailMessage);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("O . 发送Email失败了....");
            throw new RuntimeException(e.getMessage());
        }
    }

    public void sendMail(String from, String to, String subject, String text) {
        try {
            if(from != null)
                this.setMailfrom(from);
            if(to != null)
                this.setMailto(to);
            if(subject != null)
                this.setSubject(subject);
            if(text != null)
                this.setText(text);
            this.sendMail();
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}
