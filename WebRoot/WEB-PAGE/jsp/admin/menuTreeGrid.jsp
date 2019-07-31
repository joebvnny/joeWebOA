<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
//当前处于编辑(增、改)状态的行标
var menuRowEditing = -1;

var iconArray = [{
    text : 'default',
    value: ''
},{
    text : 'icon-menu',
    value: 'icon-menu'
},{
    text : 'icon-add',
    value: 'icon-add'
},{
    text : 'icon-edit',
    value: 'icon-edit'
},{
    text : 'icon-remove',
    value: 'icon-remove'
},{
    text : 'icon-save',
    value: 'icon-save'
},{
    text : 'icon-cut',
    value: 'icon-cut'
},{
    text : 'icon-ok',
    value: 'icon-ok'
},{
    text : 'icon-no',
    value: 'icon-no'
},{
    text : 'icon-cancel',
    value: 'icon-cancel'
},{
    text : 'icon-reload',
    value: 'icon-reload'
},{
    text : 'icon-search',
    value: 'icon-search'
},{
    text : 'icon-print',
    value: 'icon-print'
},{
    text : 'icon-help',
    value: 'icon-help'
},{
    text : 'icon-undo',
    value: 'icon-undo'
},{
    text : 'icon-redo',
    value: 'icon-redo'
},{
    text : 'icon-back',
    value: 'icon-back'
},{
    text : 'icon-sum',
    value: 'icon-sum'
},{
    text : 'icon-tip',
    value: 'icon-tip'
}];

$(function() {
    var admin_menu_treeGrid = $('#admin_menu_treeGrid').treegrid({
        url: '/menu/listMenuTree.do',
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
            title: '菜单名称',
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
            width: 100,
            align: 'center',
            halign: 'center',
            title: '菜单图标',
            field: 'iconCls',
            formatter: function(value) {
                return '<span class=' + value + ' style="display:inline-block;vertical-align:middle;width:16px;height:16px"/>';
            }
        },{
            width: 160,
            align: 'center',
            halign: 'center',
            title: '上级菜单',
            field: 'pid',
            formatter: function(value, row, index) {
                return row.ptext;
            }
        },{
            width: 300,
            halign: 'center',
            title: '视图链接',
            field: 'url',
            editor: {
                type: 'text'
            }
        }]],
        toolbar: [{
            text: '增加',
            iconCls: 'icon-add',
            handler: function() {
                admin_menu_append();
            }
        },'-',{
            text: '删除',
            iconCls: 'icon-remove',
            handler: function() {
                admin_menu_delete();
            }
        },'-',{
            text: '修改',
            iconCls: 'icon-edit',
            handler: function() {
                admin_menu_update();
            }
        },'-','-',{
            text: '取消',
            iconCls: 'icon-undo',
            handler: function() {
                if(menuRowEditing > -1 && menuRowEditing < 1) {
                    admin_menu_treeGrid.treegrid('remove', menuRowEditing);
                }
                admin_menu_treeGrid.treegrid('cancelEdit', menuRowEditing);
                admin_menu_treeGrid.treegrid('unselectAll');
                menuRowEditing = -1;
            }
        },'-',{
            text: '保存',
            iconCls: 'icon-save',
            handler: function() {
                if(menuRowEditing != -1) {
                    admin_menu_treeGrid.treegrid('endEdit', menuRowEditing);
                }
            }
        },'-',{
            text: '刷新',
            iconCls: 'icon-reload',
            handler: function() {
                admin_menu_refresh();
            }
        }],
        onContextMenu: function(e, row) {
            e.preventDefault();
            $(this).treegrid('unselectAll');
            $(this).treegrid('select', row.id);
            $('#admin_menuTreeGrid_menu').menu('show', {left: e.pageX, top: e.pageY});
        },
        onDblClickRow: function(index, row) {
            if(menuRowEditing == -1) {
                changeEditorOnMenuEditing();
                var node = admin_menu_treeGrid.treegrid('getSelected');
                admin_menu_treeGrid.treegrid('beginEdit', node.id);
                menuRowEditing = node.id;
                admin_menu_treeGrid.treegrid('unselectAll');
            }
        },
        onAfterEdit: function(row, changes) {
            var menuItem = {
                id:  row.id,
                pid: row.pid,
                seq: row.seq,
                url: row.url,
                text:row.text,
                iconCls: row.iconCls
            };
            $.ajax({
                url: '/menu/editMenu.do',
                data: menuItem,
                dataType: 'json',
                success: function(result) {
                    if(result.success) {
                        admin_menu_treeGrid.treegrid('acceptChanges');
                        $.messager.show({title: '提示', msg: result.msg});
                    } else {
                        admin_menu_treeGrid.treegrid('rejectChanges');
                        $.messager.alert('错误', result.msg, 'error');
                    }
                    menuRowEditing = -1;
                    admin_menu_treeGrid.treegrid('unselectAll');
                    admin_menu_treeGrid.treegrid('reload');
                }
            });
        }
    });
});

function admin_menu_append() {
    if(menuRowEditing == -1) {
        var node = $('#admin_menu_treeGrid').treegrid('getSelected');
        if(node) {
            changeEditorOnMenuEditing();
            var row = {id: 0, pid: node.id, seq: 1};
            $('#admin_menu_treeGrid').treegrid('append', {parent:node.id, data:[row]});
            $('#admin_menu_treeGrid').treegrid('select', row.id)
            $('#admin_menu_treeGrid').treegrid('beginEdit', row.id);
            menuRowEditing = row.id;
        } else {
            $.messager.alert('提示', '请选择待建菜单所属的“上级菜单”！', 'warning');
        }
    }
}

function admin_menu_delete() {
    var selectedRows = $('#admin_menu_treeGrid').treegrid('getSelections');
    if(selectedRows.length > 0) {
        $.messager.confirm('确认', '确定删除所选中的菜单（及其子菜单）？', function(yes) {
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
                        url: '/menu/deleteMenu.do',
                        data: {
                            ids: ids.join(',')
                        },
                        dataType: 'json',
                        success: function(result) {
                            $('#admin_menu_treeGrid').treegrid('unselectAll');
                            if(result.success) {
                                $('#admin_menu_treeGrid').treegrid('reload');
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
        $.messager.alert('提示', '请选择要删除的菜单项！', 'warning');
    }
}

function admin_menu_update() {
    if(menuRowEditing == -1) {
        var selectedRows = $('#admin_menu_treeGrid').treegrid('getSelections');
        if(selectedRows.length == 1) {
            changeEditorOnMenuEditing();
            var node = $('#admin_menu_treeGrid').treegrid('getSelected');
            $('#admin_menu_treeGrid').treegrid('beginEdit', node.id);
            menuRowEditing = node.id;
            $('#admin_menu_treeGrid').treegrid('unselectAll');
        } else {
            $.messager.alert('提示', '请选择一条要修改的菜单项！', 'warning');
        }
    }
}

function admin_menu_refresh() {
    if(menuRowEditing != -1) {
        menuRowEditing = -1;
        $('#admin_menu_treeGrid').treegrid('rejectChanges');
    }
    $('#admin_menu_treeGrid').treegrid('unselectAll');
    $('#admin_menu_treeGrid').treegrid('reload');
}

function changeEditorOnMenuEditing() {
    $('#admin_menu_treeGrid').treegrid('addEditor', [{
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
        field: 'iconCls',
        editor: {
            type: 'combobox',
            options: {
                data: iconArray,
                editable: false,
                formatter: function(ia) {
                    return '<span class=' + ia.value + ' style="display:inline-block;vertical-align:middle;width:16px;height:16px"/>';
                }
            }
        }
    },{
        field: 'pid',
        editor: {
            type: 'combotree',
            options: {
                url: '/menu/listMenuTree.do',
                lines: true,
                editable: false
            }
        }
    }]);
}
</script>

<table id="admin_menu_treeGrid"></table>
<div id="admin_menuTreeGrid_menu" class="easyui-menu" style="width:100px;display:none">
    <div onclick="admin_menu_append()" data-options="iconCls:'icon-add'">增加</div>
    <div onclick="admin_menu_delete()" data-options="iconCls:'icon-remove'">删除</div>
    <div onclick="admin_menu_update()" data-options="iconCls:'icon-edit'">修改</div>
    <div class="menu-sep"></div>
    <div onclick="admin_menu_refresh()" data-options="iconCls:'icon-reload'">刷新</div>
</div>