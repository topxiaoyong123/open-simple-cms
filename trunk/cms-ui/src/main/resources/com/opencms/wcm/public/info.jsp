<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.net.InetAddress" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.StringTokenizer" %>
<%
    //String agent = request.getHeader("user-agent");
    //StringTokenizer st = new StringTokenizer(agent,";");
    //st.nextToken();
    //String userbrowser = st.nextToken();
    //String useros = st.nextToken();
    InetAddress   localhost   =   InetAddress.getLocalHost();
    String   ip=localhost.getHostAddress();
      SimpleDateFormat formatter   =new   SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E");
      Date   currentTime=new   Date();
    String date=formatter.format(currentTime);
    String path=request.getRequestURL().toString().replace("info.jsp","Wcm.html");

%>
<table align="left" class="Hs_table">
<tr><th>服务器IP地址</th><td><%=ip%></td></tr>
<tr><th>浏览器代理IP地址</th><td><%out.print(request.getRemoteAddr());%></td></tr>
<tr><th>浏览器IP地址</th><td><%if (request.getHeader("x-forwarded-for") == null){out.print(request.getRemoteAddr());}else{out.print(request.getHeader("x-forwarded-for"));}%></td></tr>
<!-- <tr><th>当前操作系统</th><td><%//=useros%></td></tr>
<tr><th>当前浏览器</th><td><%//=userbrowser%></td></tr> -->
<tr><th>用户名</th><td><%=session.getAttribute("username")%></td></tr>
<tr><th>服务器系统时间</th><td><%=date%></td></tr>
</table>