<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
//当前处于编辑(增、改)状态的行标
var funcRowEditing = -1;

$(function() {
    var admin_func_treeGrid = $('#admin_func_treeGrid').treegrid({
        url: '/func/listFuncTree.do',
        idField: 'id',
        treeField: 'text',
        parentField: 'pid',
        fit: true,
        border: false,
        frozenColumns: [[{
            hidden: true,
            title: 'ID',
            field: 'id'
        },{
            width: 200,
            halign: 'center',
            title: '职能名称',
            field: 'text',
            editor: {
                type: 'validatebox',
                options: {
                    required: true
                }
            }
        }]],
        columns: [[{
            width: 80,
            align: 'center',
            halign: 'center',
            title: '本级序号',
            field: 'seq'
        },{
            width: 160,
            align: 'center',
            halign: 'center',
            title: '上级职能',
            field: 'pid',
            formatter: function(value, row, index) {
                return row.ptext;
            }
        },{
            width: 300,
            halign: 'center',
            title: '功能链接',
            field: 'url',
            editor: {
                type: 'text'
            }
        }]],
        toolbar: [{
            text: '增加',
            iconCls: 'icon-add',
            handler: function() {
                admin_func_append();
            }
        },'-',{
            text: '删除',
            iconCls: 'icon-remove',
            handler: function() {
                admin_func_delete();
            }
        },'-',{
            text: '修改',
            iconCls: 'icon-edit',
            handler: function() {
                admin_func_update();
            }
        },'-','-',{
            text: '取消',
            iconCls: 'icon-undo',
            handler: function() {
                if(funcRowEditing > -1 && funcRowEditing < 1) {
                    admin_func_treeGrid.treegrid('remove', funcRowEditing);
                }
                admin_func_treeGrid.treegrid('cancelEdit', funcRowEditing);
                admin_func_treeGrid.treegrid('unselectAll');
                funcRowEditing = -1;
            }
        },'-',{
            text: '保存',
            iconCls: 'icon-save',
            handler: function() {
                if(funcRowEditing != -1) {
                    admin_func_treeGrid.treegrid('endEdit', funcRowEditing);
                }
            }
        },'-',{
            text: '刷新',
            iconCls: 'icon-reload',
            handler: function() {
                admin_func_refresh();
            }
        }],
        onContextMenu: function(e, row) {
            e.preventDefault();
            $(this).treegrid('unselectAll');
            $(this).treegrid('select', row.id);
            $('#admin_funcTreeGrid_menu').menu('show', {left: e.pageX, top: e.pageY});
        },
        onDblClickRow: function(index, row) {
            if(funcRowEditing == -1) {
                changeEditorOnFuncEditing();
                var node = admin_func_treeGrid.treegrid('getSelected');
                admin_func_treeGrid.treegrid('beginEdit', node.id);
                funcRowEditing = node.id;
                admin_func_treeGrid.treegrid('unselectAll');
            }
        },
        onAfterEdit: function(row, changes) {
            var funcItem = {
                id:  row.id,
                pid: row.pid,
                seq: row.seq,
                url: row.url,
                text:row.text,
                iconCls: row.iconCls
            };
            $.ajax({
                url: '/func/editFunc.do',
                data: funcItem,
                dataType: 'json',
                success: function(result) {
                    if(result.success) {
                        admin_func_treeGrid.treegrid('acceptChanges');
                        $.messager.show({title: '提示', msg: result.msg});
                    } else {
                        admin_func_treeGrid.treegrid('rejectChanges');
                        $.messager.alert('错误', result.msg, 'error');
                    }
                    funcRowEditing = -1;
                    admin_func_treeGrid.treegrid('unselectAll');
                    admin_func_treeGrid.treegrid('reload');
                }
            });
        }
    });
});

function admin_func_append() {
    if(funcRowEditing == -1) {
        var node = $('#admin_func_treeGrid').treegrid('getSelected');
        if(node) {
            changeEditorOnFuncEditing();
            var row = {id: 0, pid: node.id, seq: 1};
            $('#admin_func_treeGrid').treegrid('append', {parent:node.id, data:[row]});
            $('#admin_func_treeGrid').treegrid('select', row.id)
            $('#admin_func_treeGrid').treegrid('beginEdit', row.id);
            funcRowEditing = row.id;
        } else {
            $.messager.alert('提示', '请选择待建职能所属的“职能分类”！', 'warning');
        }
    }
}

function admin_func_delete() {
    var selectedRows = $('#admin_func_treeGrid').treegrid('getSelections');
    if(selectedRows.length > 0) {
        $.messager.confirm('确认', '确定删除所选中的职能（及其职能项）？', function(yes) {
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
                        url: '/func/deleteFunc.do',
                        data: {
                            ids: ids.join(',')
                        },
                        dataType: 'json',
                        success: function(result) {
                            $('#admin_func_treeGrid').treegrid('unselectAll');
                            if(result.success) {
                                $('#admin_func_treeGrid').treegrid('reload');
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
        $.messager.alert('提示', '请选择要删除的职能项！', 'warning');
    }
}

function admin_func_update() {
    if(funcRowEditing == -1) {
        var selectedRows = $('#admin_func_treeGrid').treegrid('getSelections');
        if(selectedRows.length == 1) {
            changeEditorOnFuncEditing();
            var node = $('#admin_func_treeGrid').treegrid('getSelected');
            $('#admin_func_treeGrid').treegrid('beginEdit', node.id);
            funcRowEditing = node.id;
            $('#admin_func_treeGrid').treegrid('unselectAll');
        } else {
            $.messager.alert('提示', '请选择一条要修改的职能项！', 'warning');
        }
    }
}

function admin_func_refresh() {
    if(funcRowEditing != -1) {
        funcRowEditing = -1;
        $('#admin_func_treeGrid').treegrid('rejectChanges');
    }
    $('#admin_func_treeGrid').treegrid('unselectAll');
    $('#admin_func_treeGrid').treegrid('reload');
}

function changeEditorOnFuncEditing() {
    $('#admin_func_treeGrid').treegrid('addEditor', [{
        field: 'seq',
        editor: {
            type: 'numberspinner',
            options: {
                required: true,
                editable: true,
                min: 0
            }
        }
    },{
        field: 'pid',
        editor: {
            type: 'combotree',
            options: {
                url: '/func/listFuncTree.do',
                lines: true,
                editable: false
            }
        }
    }]);
}
</script>

<table id="admin_func_treeGrid"></table>
<div id="admin_funcTreeGrid_menu" class="easyui-menu" style="width:100px;display:none">
    <div onclick="admin_func_append()" data-options="iconCls:'icon-add'">增加</div>
    <div onclick="admin_func_delete()" data-options="iconCls:'icon-remove'">删除</div>
    <div onclick="admin_func_update()" data-options="iconCls:'icon-edit'">修改</div>
    <div class="menu-sep"></div>
    <div onclick="admin_func_refresh()" data-options="iconCls:'icon-reload'">刷新</div>
</div>