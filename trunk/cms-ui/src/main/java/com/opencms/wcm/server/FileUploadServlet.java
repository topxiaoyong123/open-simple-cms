package com.opencms.wcm.server;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-12
 * Time: 18:40:25
 * To change this template use File | Settings | File Templates.
 */
public class FileUploadServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(FileUploadServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = null;
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List items = null;
        String charset = request.getCharacterEncoding();
        if (charset == null) {
            upload.setHeaderEncoding("UTF-8");
        }
        try {
            items = upload.parseRequest(request);
        } catch (FileUploadException ex) {
            throw new ServletException(ex);
        }

        Iterator iter = items.iterator();
        FileItem i = null;
        while (iter.hasNext()) {
            FileItem item = (FileItem) iter.next();
            logger.debug(item.getFieldName());
            if(item.isFormField() == false){
                i = item;
            } else{
                logger.debug(item.getString());
                if("path".equals(item.getFieldName())){
                    path = item.getString();
                }
            }
        }
        logger.debug("文件路径:{}", path);
        if(i != null && path != null && !"".equals(path)){
            File f = new File(path, i.getName());
            logger.debug("文件路径:{}", f.getAbsolutePath());
            try {
                i.write(f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
