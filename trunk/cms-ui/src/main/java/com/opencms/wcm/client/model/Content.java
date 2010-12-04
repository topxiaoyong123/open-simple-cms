package com.opencms.wcm.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-4
 * Time: 12:16:55
 * To change this template use File | Settings | File Templates.
 */
public class Content implements IsSerializable {

    public Content() {
        super();
    }

    private String contentId;
    private String level;
    private String summary;
    private String content;
    private String url;
    private String pic;
    private String auditor;
    private String type;
    private String modificationUsername;
    private String author;
    private String postil;
    private String language;
    private String state;
    private String editor;
    private String categoryId;
    private String keywords;
    private String title;
    private String auditingState;
    private String source;
    private String template;
    private double no = ClientJdbcUtil.DOUBLENULLVALUE;
    private Date createdate;

    private String typename;//类型的中文名字
    private String address;//文章地址

    private Date activation_date;
    private String activation_time;
    private Date expiration_date;
    private String expiration_time;

    private String contenttypename;//是否映射过来的
    private String contenttypelink;//映射过来的数字

    private String needurl;


   // private List Attachments;

    public String toString() {
        return "Content[contentId=" + contentId + ",level=" + level + ",summary=" + summary + ",auditor=" + auditor + "]";
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSummary() {
        if(content!=null&&!"".equals(content)){
           if(summary==null||"".equals(summary)){
           summary = splitAndFilterString(this.content,500);
         }
        }

        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModificationUsername() {
        return modificationUsername;
    }

    public void setModificationUsername(String modificationUsername) {
        this.modificationUsername = modificationUsername;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPostil() {
        return postil;
    }

    public void setPostil(String postil) {
        this.postil = postil;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuditingState() {
        return auditingState;
    }

    public void setAuditingState(String auditingState) {
        this.auditingState = auditingState;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public double getNo() {
        return no;
    }

    public void setNo(double no) {
        this.no = no;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }


    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getActivation_date() {
        return activation_date;
    }

    public void setActivation_date(Date activation_date) {
        this.activation_date = activation_date;
    }



    public Date getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Date expiration_date) {
        this.expiration_date = expiration_date;
    }


    public String getActivation_time() {
        return activation_time;
    }

    public void setActivation_time(String activation_time) {
        this.activation_time = activation_time;
    }

    public String getExpiration_time() {
        return expiration_time;
    }

    public void setExpiration_time(String expiration_time) {
        this.expiration_time = expiration_time;
    }

    public String getContenttypename() {
        return contenttypename;
    }

    public void setContenttypename(String contenttypename) {
        this.contenttypename = contenttypename;
    }

    public String getContenttypelink() {
        return contenttypelink;
    }

    public void setContenttypelink(String contenttypelink) {
        this.contenttypelink = contenttypelink;
    }

    public String getNeedurl() {
        return needurl;
    }

    public void setNeedurl(String needurl) {
        this.needurl = needurl;
    }

    private static String splitAndFilterString(String input, int length) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		// 去掉所有html元素,
		String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
				"<[^>]*>", "");
		str = str.replaceAll("[(/>)<]", "");
		int len = str.length();
		if (len <= length) {
			return str;
		} else {
			str = str.substring(0, length);
			str += "......";
		}
		return str;
	}


    private boolean uploadpic = false;

    public boolean isUploadpic() {
        return uploadpic;
    }

    public void setUploadpic(boolean uploadpic) {
        this.uploadpic = uploadpic;
    }
}
