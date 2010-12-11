package com.hundsun.fund.website.mail.mailapi;

import com.hundsun.fund.website.mail.core.service.MailService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: mid
 * Date: 2010-1-8
 * Time: 13:25:38
 * To change this template use File | Settings | File Templates.
 */
public interface MailBuilder {

    public MailService getMailService();

    /*----是否SSL认证，0、否；1、是-------*/
    public void setSSL(String SSL);

    public void setTemplate(String template);

    public void setTemplatePath(String templatePath);

    /**
     * 非freekmarker邮件模板
     * @param text  邮件内容
     */
    public void buildMail(String text);

    /**
     * 非freekmarker邮件模板
     * @param text     邮件内容
     * @param subject 主题
     */
    public void buildMail(String subject, String text);

    /**
     * 非freekmarker邮件模板
     * @param text      邮件内容
     * @param subject  主题
     * @param mailto   收件人
     */
    public void buildMail( String mailto, String subject, String text);

    /**
     * 非freekmarker邮件模板
     * @param text      邮件内容
     * @param subject  主题
     * @param mailtos   收件人
     */
    public void buildMail(String[] mailtos, String subject, String text);

    /**
     * 非freekmarker邮件模板
     * @param text      邮件内容
     * @param subject  主题
     * @param mailtos   收件人
     * @param mailfrom  发件人
     */
    public void buildMail(String mailfrom, String[] mailtos, String subject, String text);

    /**
     * freekmarker邮件模板
     * @param map  邮件内容
     */
    public void buildMail(Map map);

    /**
     * freekmarker邮件模板
     * @param map     邮件内容
     * @param subject 主题
     */
    public void buildMail(String subject, Map map);

    /**
     * freekmarker邮件模板
     * @param map      邮件内容
     * @param subject  主题
     * @param mailto   收件人
     */
    public void buildMail(String mailto, String subject, Map map);

    /**
     * freekmarker邮件模板
     * @param map      邮件内容
     * @param subject  主题
     * @param mailtos   收件人
     */
    public void buildMail(String[] mailtos, String subject, Map map);

    /**
     * freekmarker邮件模板
     * @param map      邮件内容
     * @param subject  主题
     * @param mailtos   收件人
     * @param mailfrom  发件人
     */
    public void buildMail(String mailfrom, String[] mailtos, String subject, Map map);

    public void addFile(String file);
    public void addFile(String filename, byte[] data, HttpServletRequest request);
    public void addFile(String filename, byte[] data, String path);
}
