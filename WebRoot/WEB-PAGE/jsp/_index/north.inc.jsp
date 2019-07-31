<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
function logout(flag) {
	$.getJSON('/user/userLogout.do', function(result) {
	    if(result.success) {
	        if(flag==='lock') {
	            $('#userLoginDialog').dialog({'title':'锁定','iconCls':'icon-ulock'});
	            $('#user_login_username').textbox({readonly: 'readonly'});
	            $('#user_login_password').textbox('setValue', '');
	            $('#userLoginDialog').dialog('open');
	        } else if(flag==='relog') {
				window.location.reload();
			} else if(flag==='exit') {
			    commonUtils.closeWindowPage();
			}
	    }
	});
}

function modifyUserPwd() {
    $('<div id="userModifyPwdDialog"/>').dialog({
        href: '/user/modifyPwdInfo.do',
        width: 260,
        height: 200,
        modal: true,
        title: '更改密码',
        iconCls: 'icon-key',
        buttons: [{
            text: '更改密码',
            iconCls: 'icon-edit',
            handler: function() {
                $('#user_modifyPwd_form').form('submit', {
                    url: '/user/modifyUserPwd.do',
                    success: function(result) {
                        try {
                            var r = $.parseJSON(result);
                            if(r.success) {
                                $('#userModifyPwdDialog').dialog('destroy');
                            }
                            $.messager.show({title: '提示', msg: r.msg});
                        } catch(e) {
                            $.messager.alert('提示', result);
                        }
                    }
                });
            }
        }],
        onClose: function() {
            $(this).dialog('destroy');
        }
    });
}
</script>

<div id="userLoginInfo" class="header"></div>
<div style="position:absolute;right:0px;bottom:0px">
	<a href="javascript:void(0)" class="easyui-menubutton" data-options="menu:'#layout_north_ztMenu'">更换主题</a>&emsp;<a href="javascript:void(0)" class="easyui-menubutton" data-options="menu:'#layout_north_yhMenu'">用户操作</a>
</div>
<div id="layout_north_ztMenu" style="width:100px; display:none">
	<div onclick="commonUtils.changeTheme('default')">default</div>
	<div onclick="commonUtils.changeTheme('metro')">metro</div>
	<div onclick="commonUtils.changeTheme('black')">black</div>
	<div onclick="commonUtils.changeTheme('bootstrap')">bootstrap</div>
</div>
<div id="layout_north_yhMenu" style="width:100px; display:none">
    <div onclick="modifyUserPwd()">更改密码</div>
	<div class="menu-sep"></div>
	<div onclick="logout('lock')">锁定窗口</div>
	<div onclick="logout('relog')">切换用户</div>
	<div class="menu-sep"></div>
	<div onclick="logout('exit')">退出系统</div>
</div>