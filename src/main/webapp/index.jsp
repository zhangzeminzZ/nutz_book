<%--
  Created by IntelliJ IDEA.
  User: zhang
  Date: 2020/3/25
  Time: 10:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%pageContext.setAttribute("contextPath", request.getContextPath());%>--%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>NutzBook demo</title>
</head>
<body>
<div id="login_div">
    <form action="#" id="loginForm" method="post">
        用户名 <input name="username" type="text" value="admin">
        密码 <input name="password" type="password" value="123456">
        <button id="login_button">提交</button>
    </form>
</div>
<div id="user_info_div">
    <p id="userInfo"></p>
    <a href="${base}/user/logout">登出</a>
</div>
<script src="${base}/lib/jquery/jquery-2.1.4.js"></script>
<script>
    var me = '<%=session.getAttribute("me") %>';
    var base = '${base}';
    $(function() {
        $("#login_button").click(function() {
            $.ajax({
                url : base + "/user/login",
                type: "POST",
                data:$('#loginForm').serialize(),
                error: function(request) {
                    alert("Connection error");
                },
                dataType:"json",
                success: function(data) {
                    alert(data);
                    if (data == true) {
                        alert("登陆成功");
                        location.reload();
                    } else {
                        alert("登陆失败,请检查账号密码")
                    }
                }
            });
            return false;
        });
        if (me != "null") {
            $("#login_div").hide();
            $("#userInfo").html("您的Id是" + me);
            $("#user_info_div").show();
        } else {
            $("#login_div").show();
            $("#user_info_div").hide();
        }
    });
</script>
</body>
</html>
