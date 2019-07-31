<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
$(function() {
    $('#admin_user_dataGrid').datagrid({
        url: '/user/userGrid.do',
        fit: true,
        border: false,
        nowarp: false,
        pagination: true,
        pageSize: 10,
        pageList: [10, 20, 30, 40, 50],
        idField: 'userId',
        sortName: 'id',
        sortOrder: 'asc',
        frozenColumns: [[{
            hidden: false,
            checkbox: true,
            title: 'ID',
            field: 'userId'
        },{
            width: 150,
            halign: 'center',
            title: '用户名',
            field: 'username'
        }]],
        columns: [[{
            width: 40,
            hidden: true,
            halign: 'center',
            align: 'center',
            title: '密码',
            field: 'password',
            formatter: function(value, row, index) {
                return '*****';
            }
        },{
            width: 80,
            halign: 'center',
            align: 'center',
            title: '生日',
            field: 'birthday'
        },{
            width: 40,
            halign: 'center',
            align: 'center',
            title: '性别',
            field: 'gender',
            formatter: function(value, row, index) {
                if(value == 'M') return '男';
                if(value == 'F') return '女';
            }
        },{
            width: 80,
            hidden: true,
            title: '部门ID',
            field: 'deptId'
        },{
            width: 80,
            halign: 'center',
            title: '部门名称',
            field: 'deptName'
        },{
            width: 80,
            hidden: true,
            title: '角色ID',
            field: 'roleIds'
        },{
            width: 300,
            halign: 'center',
            title: '担任角色',
            field: 'roleNames'
        }]],
        toolbar: [{
            text: '增加',
            iconCls: 'icon-add',
            handler: function() {
                admin_user_edit('增加用户');
            }
        },'-',{
            text: '删除',
            iconCls: 'icon-remove',
            handler: function() {
                var selectedRows = $('#admin_user_dataGrid').datagrid('getSelections');
                if(selectedRows.length > 0) {
                    $.messager.confirm('确认', '确定删除所选中的记录？', function(yes) {
                        var ids = [];
                        if(yes) {
                            var id;
                            for(var i=0; i<selectedRows.length; i++) {
                                id = selectedRows[i].userId;
                                if(!commonUtils.isEmpty(id)) {
                                    ids.push(id);
                                }
                            }
                            if(ids.length > 0) {
                                $.ajax({
                                    url: '/user/deleteUser.do',
                                    data: {
                                        ids: ids.join(',')
                                    },
                                    dataType: 'json',
                                    success: function(result) {
                                        $('#admin_user_dataGrid').datagrid('unselectAll');
                                        if(result.success) {
                                            $('#admin_user_dataGrid').datagrid('reload');
                                            $.messager.show({title: '提示', msg: result.msg});
                                        } else {
                                            $.messager.alert('错误', result.msg, 'error');
                                        }
                                    }
                                });
                            }
                        }
                    });
                } else {
                    $.messager.alert('提示', '请选择要删除的记录！', 'warning');
                }
            }
        },'-',{
            text: '修改',
            iconCls: 'icon-edit',
            handler: function() {
                var selectedRows = $('#admin_user_dataGrid').datagrid('getSelections');
                if(selectedRows.length == 1) {
                    var theIndex = $('#admin_user_dataGrid').datagrid('getRowIndex', selectedRows[0]);
                    admin_user_edit('修改用户', theIndex);
                } else {
                    $.messager.alert('提示', '请选择一条要修改的记录！', 'warning');
                }
            }
        },'-','-',{
            text: '刷新',
            iconCls: 'icon-reload',
            handler: function() {
                $('#admin_user_dataGrid').datagrid('unselectAll');
                $('#admin_user_dataGrid').datagrid('reload');
            }
        }]
    });
});

function admin_user_edit(title, rowId) {
    $('<div id="adminUserEditDialog"/>').dialog({
        href: '/WEB-PAGE/jsp/admin/userEdit.jsp',
        width: 460,
        height: 240,
        modal: true,
        title: title,
        buttons: [{
            text: '保存',
            iconCls: 'icon-save',
            handler: function() {
                $('#admin_user_editForm').form('submit', {
                    url: '/user/editUser.do',
                    success: function(result) {
                        try {
                            var r = $.parseJSON(result);
                            if(r.success) {
                                $('#adminUserEditDialog').dialog('destroy');
                                $('#admin_user_dataGrid').datagrid('reload');
                            }
                            $.messager.show({
                                title: '提示',
                                msg: r.msg
                            });
                        } catch(e) {
                            $.messager.alert('提示', result);
                        }
                    }
                });
            }
        }],
        onLoad: function() {
            if(rowId != undefined) {
                var rowArr = $('#admin_user_dataGrid').datagrid('getRows');
                var theRow = rowArr[rowId];
                if(typeof theRow.funcIds == 'string') {
                    theRow.funcIds = commonUtils.str2Array(theRow.funcIds);
                }
                $('#admin_user_editForm').form('load', theRow);
            }
        },
        onClose: function() {
            $(this).dialog('destroy');
        }
    });
}
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" style="overflow:hidden">
        <table id="admin_user_dataGrid"></table>
    </div>
</div>