<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
$(function() {
    $('#admin_role_dataGrid').datagrid({
        url: '/role/roleGrid.do',
        fit: true,
        border: false,
        nowarp: false,
        pagination: true,
        pageSize: 10,
        pageList: [10, 20, 30, 40, 50],
        idField: 'id',
        sortName: 'id',
        sortOrder: 'asc',
        frozenColumns: [[{
            hidden: false,
            checkbox: true,
            title: 'ID',
            field: 'id'
        },{
            width: 150,
            halign: 'center',
            title: '角色名称',
            field: 'rolename'
        }]],
        columns: [[{
            width: 300,
            hidden: true,
            title: '职能ID',
            field: 'funcIds'
        },{
            width: 500,
            halign: 'center',
            title: '具有职能',
            field: 'funcNames',
            formatter: function(value, row, index) {
                if(row.id == '0') {
                    return '具有所有系统职能';
                } else {
                    return value;
                }
            }
        }]],
        toolbar: [{
            text: '增加',
            iconCls: 'icon-add',
            handler: function() {
                admin_role_edit('增加角色');
            }
        },'-',{
            text: '删除',
            iconCls: 'icon-remove',
            handler: function() {
                var selectedRows = $('#admin_role_dataGrid').datagrid('getSelections');
                if(selectedRows.length > 0) {
                    $.messager.confirm('确认', '确定删除所选中的记录？', function(yes) {
                        var ids = [];
                        if(yes) {
                            var id;
                            for(var i=0; i<selectedRows.length; i++) {
                                id = selectedRows[i].id;
                                if(!commonUtils.isEmpty(id)) {
                                    ids.push(id);
                                }
                            }
                            if(ids.length > 0) {
                                $.ajax({
                                    url: '/role/deleteRole.do',
                                    data: {
                                        ids: ids.join(',')
                                    },
                                    dataType: 'json',
                                    success: function(result) {
                                        $('#admin_role_dataGrid').datagrid('unselectAll');
                                        if(result.success) {
                                            $('#admin_role_dataGrid').datagrid('reload');
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
                var selectedRows = $('#admin_role_dataGrid').datagrid('getSelections');
                if(selectedRows.length == 1) {
                    var theIndex = $('#admin_role_dataGrid').datagrid('getRowIndex', selectedRows[0]);
                    admin_role_edit('修改角色', theIndex);
                } else {
                    $.messager.alert('提示', '请选择一条要修改的记录！', 'warning');
                }
            }
        },'-','-',{
            text: '刷新',
            iconCls: 'icon-reload',
            handler: function() {
                $('#admin_role_dataGrid').datagrid('unselectAll');
                $('#admin_role_dataGrid').datagrid('reload');
            }
        }]
    });
});

function admin_role_edit(title, rowId) {
    $('<div id="adminRoleEditDialog"/>').dialog({
        href: '/WEB-PAGE/jsp/admin/roleEdit.jsp',
        width: 440,
        height: 200,
        modal: true,
        title: title,
        buttons: [{
            text: '保存',
            iconCls: 'icon-save',
            handler: function() {
                $('#admin_role_editForm').form('submit', {
                    url: '/role/editRole.do',
                    success: function(result) {
                        try {
                            var r = $.parseJSON(result);
                            if(r.success) {
                                $('#adminRoleEditDialog').dialog('destroy');
                                $('#admin_role_dataGrid').datagrid('reload');
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
                var rowArr = $('#admin_role_dataGrid').datagrid('getRows');
                var theRow = rowArr[rowId];
                if(typeof theRow.funcIds == 'string') {
                    theRow.funcIds = commonUtils.str2Array(theRow.funcIds);
                }
                $('#admin_role_editForm').form('load', theRow);
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
        <table id="admin_role_dataGrid"></table>
    </div>
</div>