<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
$(function() {
    var userLoginForm = $('#userLoginForm').form({
        url: '/user/userLogin.do',
        method: 'post',
        cache: false,
        dataType: 'json',
        success: function(result) {
            var r = $.parseJSON(result);
            if(r.success) {
                $('#userLoginDialog').dialog('close');
                $('#userLoginInfo').html('[<strong>' + r.data.username + '</strong>] @ ' + r.data.clientIp);
                $('#layout_east_userlist').datagrid('reload');
            } else {
                $.messager.show({title: '用户名或密码错误', msg: r.msg});
            }
        }
    });
    
    $('#user_login_username').textbox().focus();
    
    userLoginForm.find('input').on('keyup', function(event) {
        if(event.keyCode == '13') {
            userLoginForm.submit();
        }
    });
    
    $('#userLoginDialog').dialog({
        modal: true,
        title: '登录',
        iconCls: 'icon-user',
        closable: false,
        buttons: [{
            text: '登录',
            handler: function() {
                userLoginForm.submit();
            }
        }, {
            text: '退出',
            handler : function() {
                commonUtils.closeWindowPage();
            }
        }]
    });
});
</script>

<div id="userLoginDialog" style="display:none;padding:4px;overflow:hidden">
    <form id="userLoginForm" method="post">
    <table>
        <tr><th>用户名：</th><td><input type="text" name="username" id="user_login_username" class="easyui-textbox" data-options="required:true,iconCls:'icon-man'" style=" width:150px" /></td></tr>
        <tr><th>密&emsp;码：</th><td><input type="password" name="password" id="user_login_password" class="easyui-textbox" data-options="required:true,iconCls:'icon-lock'" style=" width:150px" /></td></tr>
    </table>
    </form>
</div>