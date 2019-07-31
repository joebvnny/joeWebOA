<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>tomcat@JoeVPC(192.168.1.11)</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="/WEB-PAGE/css/login.css"/>
    <script type="text/javascript" src="/WEB-PAGE/lib/jquery-1.11.1.js"></script>
</head>
<body>
    <form id="loginForm">
        <div class="login_putin">
            <ul>
                <li>用户名：<input id="username" name="username" type="text" /></li>
                <li>密&emsp;码：<input id="password" name="password" type="text" /></li>
            </ul>
        </div>
        <div class="login_btn">
            <input type="button" value="登录" onclick="doLogin()">
        </div>
    </form>
</body>
<script>
    function doLogin() {
        $.ajax({
            url: "/doLogin.do",
            type: "post",
            dataType: "json",
            data: {
              "username": $("#username").val(),
              "password": $("#password").val()
            },
            success: function(data) {
                alert(data.message);
                if(data.success) {
                    window.location.href = "/userCenter.do";
                }
            }
        });
    }
</script>
</html>