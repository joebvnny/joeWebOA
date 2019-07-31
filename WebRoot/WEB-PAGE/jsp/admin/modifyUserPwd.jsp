<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div align="center" style="padding:4px">
    <form id="user_modifyPwd_form" method="post">
        <input type="hidden" id="thePwd" value="${userInfo.password}" />
        <input type="hidden" name="userId" value="${userInfo.userId}" />
        <input type="hidden" name="loginTime" value="${userInfo.loginTime}" />
        <table>
            <tr>
                <th>用户名：</th>
                <td><input type="text" name="username" readonly="readonly" value="${userInfo.username}" /></td>
            </tr>
            <tr>
                <th>登录IP：</th>
                <td><input type="text" name="clientIp" readonly="readonly" value="${userInfo.clientIp}" /></td>
            </tr>
            <tr>
                <th>原密码：</th>
                <td><input type="password" name="oldPwd" class="easyui-validatebox" data-options="required:'true',missingMessage:'原密码必填',invalidMessage:'原密码填写错误'" validType="equals['#thePwd']" /></td>
            </tr>
            <tr>
                <th>新密码：</th>
                <td><input type="password" name="password" class="easyui-validatebox" data-options="required:'true',missingMessage:'新密码必填',validType:'length[5,15]'" /></td>
            </tr>
        </table>
    </form>
</div>