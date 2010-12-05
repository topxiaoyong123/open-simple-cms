package com.opencms.wcm.client;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	F:/workspace/opencms/trunk/cms-ui/src/main/java/com/opencms/wcm/client/WcmMessages.properties'.
 */
public interface WcmMessages extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "新增".
   * 
   * @return translated "新增"
   */
  @DefaultMessage("新增")
  @Key("add")
  String add();

  /**
   * Translated "登录".
   * 
   * @return translated "登录"
   */
  @DefaultMessage("登录")
  @Key("login")
  String login();

  /**
   * Translated "密码".
   * 
   * @return translated "密码"
   */
  @DefaultMessage("密码")
  @Key("password")
  String password();

  /**
   * Translated "请等待...".
   * 
   * @return translated "请等待..."
   */
  @DefaultMessage("请等待...")
  @Key("pleasewait")
  String pleasewait();

  /**
   * Translated "重置".
   * 
   * @return translated "重置"
   */
  @DefaultMessage("重置")
  @Key("reset")
  String reset();

  /**
   * Translated "用户名".
   * 
   * @return translated "用户名"
   */
  @DefaultMessage("用户名")
  @Key("username")
  String username();
}
