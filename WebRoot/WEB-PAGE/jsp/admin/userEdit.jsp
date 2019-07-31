<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
    $(function() {
        $('#admin_user_roles').combogrid({
            url: '/role/roleGrid.do',
            editable: false,
            multiple: true,
            nowrap: false,
            panelWidth: 400,
            panelHeight: 320,
            fitColumns: true,
            pagination: false,
            idField: 'id',
            textField: 'rolename',
            columns: [[{
                hidden: false,
                checkbox: true,
                title: 'ID',
                field: 'id'
            },{
                width: 150,
                halign: 'center',
                title: '角色名称',
                field: 'rolename'
            },{
                width: 80,
                hidden: true,
                title: '职能ID',
                field: 'funcIds'
            },{
                width: 500,
                halign: 'center',
                title: '具有职能',
                field: 'funcNames'
            }]]
        });
    });
    
    $(function() {
        $('#admin_user_dept').combobox({
            url: '/dept/listDepts.do',
            editable: false,
            valueField: 'id',
            textField: 'deptname'
        });
    });
</script>

<form id="admin_user_editForm" method="post">
    <table cellspacing="8">
        <tr>
            <td><input type="hidden" name="userId" /></td>
            <td><input type="hidden" name="password" /></td>
        </tr>
        <tr>
            <th>用户名：</th>
            <td colspan="3"><input name="username" class="easyui-validatebox" data-options="required:true" /></td>
        </tr>
        <tr>
            <th>性别：</th>
            <td><input name="gender" type="radio" value="M"/>男&nbsp;<input name="gender" type="radio" value="F"/>女</td>
            <th>生日：</th>
            <td><input name="birthday" class="easyui-datebox" /></td>
        </tr>
        <tr>
            <th>所属部门：</th>
            <td colspan="3"><input id="admin_user_dept" name="deptId" style="width:320px" />&emsp;<img src="/WEB-PAGE/lib/jeasyui-1.5.2/themes/icons/cancel.png" style="cursor:pointer" onclick="$('#admin_user_dept').combobox('clear')" /></td>
        </tr>
        <tr>
            <th>分配角色：</th>
            <td colspan="3"><input id="admin_user_roles" name="roleIds" style="width:320px" />&emsp;<img src="/WEB-PAGE/lib/jeasyui-1.5.2/themes/icons/cancel.png" style="cursor:pointer" onclick="$('#admin_user_roles').combogrid('clear')" /></td>
        </tr>
    </table>
</form>