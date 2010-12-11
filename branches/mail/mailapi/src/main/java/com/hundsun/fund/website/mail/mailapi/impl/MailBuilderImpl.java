package com.hundsun.fund.website.mail.mailapi.impl;

import com.hundsun.fund.website.mail.mailapi.MailBuilder;
import com.hundsun.fund.website.mail.core.service.MailService;

import java.util.Map;
import java.util.Locale;
import java.util.Date;
import java.io.IOException;
import java.io.StringWriter;
import java.io.File;
import java.text.SimpleDateFormat;

import freemarker.template.Configuration;
import freemarker.template.Template;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: mid
 * Date: 2010-1-8
 * Time: 13:25:58
 * To change this template use File | Settings | File Templates.
 */
public class MailBuilderImpl implements MailBuilder {

    private MailService mailService;

    public MailService getMailService() {
        return mailService;
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    /*-------邮件模板文件-------*/
    private String template = "mail-default.ftl";
    /*-------邮件模板路径-------*/
    private String templatePath = "/mail/template";

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public void buildMail(String text) {
        try {
            mailService.sendMail(null, null, null, text);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void buildMail(String subject, String text) {
        try {
            mailService.sendMail(null, null, subject, text);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void buildMail(String mailto, String subject, String text) {
        try {
            mailService.sendMail(null, mailto, subject, text);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void buildMail(String[] mailtos, String subject, String text) {
        try {
            String s = null;
            if(mailtos != null && mailtos.length > 0){
                s = "";
                for(int i=0; i<mailtos.length; i++){
                    s = s + mailtos[i] + ";";
                }
            }
            mailService.sendMail(null, s, subject, text);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void buildMail(String mailfrom, String[] mailtos, String subject, String text) {
        try {
            String s = null;
            if(mailtos != null && mailtos.length > 0){
                s = "";
                for(int i=0; i<mailtos.length; i++){
                    s = s + mailtos[i] + ";";
                }
            }
            mailService.sendMail(mailfrom, s, subject, text);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /*----是否SSL认证，0、否；1、是-------*/
    private String SSL;

    public void setSSL(String SSL) {
        this.SSL = SSL;
        this.init();
    }

    public String getSSL() {
        return SSL;
    }

    /**
     * freekmarker邮件模板
     * @param map  邮件内容
     */
    public void buildMail(Map map){
        String text = "";
        try {
            text = doFreemarkerMail(map);
            mailService.sendMail(null, null, null, text);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * freekmarker邮件模板
     * @param map     邮件内容
     * @param subject 主题
     */
    public void buildMail(String subject, Map map){
        String text = "";
        try {
            text = doFreemarkerMail(map);
            mailService.sendMail(null, null, subject, text);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * freekmarker邮件模板
     * @param map      邮件内容
     * @param subject  主题
     * @param mailto   收件人
     */
    public void buildMail(String mailto, String subject, Map map){
        String text = "";
        try {
            text = doFreemarkerMail(map);
            mailService.sendMail(null, mailto, subject, text);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * freekmarker邮件模板
     * @param map      邮件内容
     * @param subject  主题
     * @param mailtos   收件人
     */
    public void buildMail(String[] mailtos, String subject, Map map){
        String text = "";
        try {
            text = doFreemarkerMail(map);
            String s = null;
            if(mailtos != null && mailtos.length > 0){
                s = "";
                for(int i=0; i<mailtos.length; i++){
                    s = s + mailtos[i] + ";";    
                }
            }
            mailService.sendMail(null, s, subject, text);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * freekmarker邮件模板
     * @param map      邮件内容
     * @param subject  主题
     * @param mailtos   收件人
     * @param mailfrom  发件人
     */
    public void buildMail(String mailfrom, String[] mailtos, String subject, Map map) {
        String text = "";
        try {
            text = doFreemarkerMail(map);
            String s = null;
            if(mailtos != null && mailtos.length > 0){
                s = "";
                for(int i=0; i<mailtos.length; i++){
                    s = s + mailtos[i] + ";";
                }
            }
            mailService.sendMail(mailfrom, s, subject, text);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 根据模板生成邮件内容
     * @param map
     * @return
     * @throws Exception
     */
    private String doFreemarkerMail(Map map) throws Exception{
        /*-------模板配置-------*/
        Configuration cfg = new Configuration();
        cfg.setEncoding(Locale.getDefault(), "utf-8");
        cfg.setClassForTemplateLoading(getClass(), this.getTemplatePath());
        Template template = cfg.getTemplate(this.getTemplate());
        StringWriter writer = new StringWriter();
        template.process(map, writer);
        return writer.toString();
    }

    /**
     * 初始化
     */
    public void init() {
        if("1".equals(this.getSSL())){
            mailService.setSSL(true);    
        } else{
            mailService.setSSL(false);
        }
    }

    public void addFile(String file){
        mailService.addFile(file);
    }

    public void addFile(String filename, byte[] data, HttpServletRequest request) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd/HH");
        String dir = "/_tmp/_email_attachments/" + f.format(new Date());
        mailService.addFile(request.getSession().getServletContext().getRealPath(dir), filename, data);
    }

    public void addFile(String filename, byte[] data, String path) {
        mailService.addFile(path, filename, data);
    }
}
