package com.hundsun.fund.website.mail.core.service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.File;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: mid
 * Date: 2010-1-8
 * Time: 10:03:20
 * To change this template use File | Settings | File Templates.
 */
public interface MailService {

    /**
     * 发送邮件
     */
    public void sendMail();

    /**
     * 发送邮件
     * @param from
     * @param to
     * @param subject
     * @param text
     */
    public void sendMail(String from, String to, String subject, String text);

    /**
     * 发送邮件
     * @param isSSL
     */
    public void setSSL(Boolean isSSL);

    /**
     * 附件
     * @param files
     */
    public void setFiles(List files);

    /**
     * 附件
     * @param file
     */
    public void addFile(String file);

    /**
     * 附件
     * @param path
     * @param filename
     * @param data
     */
    public void addFile(String path, String filename, byte[] data);
}
