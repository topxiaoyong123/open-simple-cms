<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type='text/javascript' src='dwr/interface/Msg.js'></script>
    <script type='text/javascript' src='dwr/engine.js'></script>
    <script type='text/javascript'></script>
    <script type="text/javascript">
        function showMessage(msg) {
            alert(msg);
        }
        function sendMessage(msg) {
            Msg.outMessage(msg);
        }
    </script>
</head>
 <body onload="dwr.engine.setActiveReverseAjax(true);">
<h2>Hello World!</h2>
 <p>输入信息: <input id="text" onkeypress="sendMessage(this.value)" />
</body>
</html>
