<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
$(function() {
    //当前处于编辑(增、改)状态的行标
    var regionRowEditing = -1;
    
    var admin_region_dataGrid = $('#admin_region_dataGrid').datagrid({
        url: '/region/regionGrid.do',
        fit: true,
        border: false,
        nowarp: false,
        pagination: true,
        pageSize: 10,
        pageList: [10, 20, 30, 40, 50],
        idField: 'id',
        remoteSort: false,
        sortName: 'areaCode',
        sortOrder: 'asc',
        frozenColumns: [[{
            hidden: false,
            checkbox: true,
            title: 'ID',
            field: 'id'
        },{
            width: 100,
            halign: 'center',
            sortable: true,
            title: '地区编码',
            field: 'areaCode',
            editor: {
                type: 'validatebox',
                options: {
                    required: true
                }
            }
        }]],
        columns: [[{
            width: 160,
            halign: 'center',
            sortable: true,
            title: '地区名称',
            field: 'name',
            editor: {
                type: 'validatebox',
                options: {
                    required: true
                }
            }
        },{
            width: 100,
            align: 'right',
            halign: 'center',
            title: '区域中心经度',
            field: 'centerLng',
            editor: {
                type: 'text'
            }
        },{
            width: 100,
            align: 'right',
            halign: 'center',
            title: '区域中心纬度',
            field: 'centerLat',
            editor: {
                type: 'text'
            }
        },{
            width: 70,
            align: 'right',
            halign: 'center',
            title: '电话区号',
            field: 'cityCode',
            editor: {
                type: 'text'
            }
        },{
            width: 70,
            align: 'right',
            halign: 'center',
            title: '邮政编码',
            field: 'zipCode',
            editor: {
                type: 'text'
            }
        },{
            width: 130,
            align: 'right',
            halign: 'center',
            title: '创建时间',
            field: 'createdTime'
        }]],
        toolbar: [{
            text: '增加',
            iconCls: 'icon-add',
            handler: function() {
                if(regionRowEditing == -1) {
                    changeEditorOnRegionAddRow();
                    admin_region_dataGrid.datagrid('insertRow', {index:0, row:{}});
                    admin_region_dataGrid.datagrid('beginEdit', 0);
                    regionRowEditing = 0;
                }
            }
        },'-',{
            text: '删除',
            iconCls: 'icon-remove',
            handler: function() {
                if(regionRowEditing == -1) {
                    var selectedRows = admin_region_dataGrid.datagrid('getSelections');
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
                                        url: '/region/deleteRegion.do',
                                        data: {
                                            ids: ids.join(',')
                                        },
                                        dataType: 'json',
                                        success: function(result) {
                                            admin_region_dataGrid.datagrid('unselectAll');
                                            if(result.success) {
                                                admin_region_dataGrid.datagrid('reload');
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
            }
        },'-',{
            text: '修改',
            iconCls: 'icon-edit',
            handler: function() {
                if(regionRowEditing == -1) {
                    var selectedRows = admin_region_dataGrid.datagrid('getSelections');
                    if(selectedRows.length == 1) {
                        changeEditorOnRegionEditRow();
                        var theIndex = admin_region_dataGrid.datagrid('getRowIndex', selectedRows[0]);
                        admin_region_dataGrid.datagrid('beginEdit', theIndex);
                        regionRowEditing = theIndex;
                        admin_region_dataGrid.datagrid('unselectAll');
                    } else {
                        $.messager.alert('提示', '请选择一条要修改的记录！', 'warning');
                    }
                }
            }
        },'-','-',{
            text: '取消',
            iconCls: 'icon-undo',
            handler: function() {
                admin_region_dataGrid.datagrid('rejectChanges');
                admin_region_dataGrid.datagrid('unselectAll');
                regionRowEditing = -1;
            }
        },'-',{
            text: '保存',
            iconCls: 'icon-save',
            handler: function() {
                if(regionRowEditing != -1) {
                    admin_region_dataGrid.datagrid('endEdit', regionRowEditing);
                }
            }
        },'-',{
            text: '刷新',
            iconCls: 'icon-reload',
            handler: function() {
                if(regionRowEditing != -1) {
                    regionRowEditing = -1;
                    admin_region_dataGrid.datagrid('rejectChanges');
                }
                admin_region_dataGrid.datagrid('reload');
            }
        },'-','-',{
            text: '<form id="region_search_form"><input type="text" id="region_search_input" class="easyui-searchbox" menu="#searchMenu" style="width:240px"/>' +
                  '<div id="searchMenu"><div name="name">地区名称</div><div name="areaCode">地区编码</div></div></form>'
        },{
            text: '重置',
            iconCls: 'icon-reset',
            handler: function() {
                $('#region_search_input').searchbox('setValue','');
                admin_region_dataGrid.datagrid('load', {});
            }
        }],
        onDblClickRow: function(index, row) {
            if(regionRowEditing == -1) {
                changeEditorOnRegionEditRow();
                admin_region_dataGrid.datagrid('beginEdit', index);
                regionRowEditing = index;
            }
        },
        onAfterEdit: function(index, row, changes) {
            var inserted = admin_region_dataGrid.datagrid('getChanges', 'inserted');
            var updated = admin_region_dataGrid.datagrid('getChanges', 'updated');
            var bizUrl = '';
            if(inserted.length > 0) {
                bizUrl = '/region/insertRegion.do';
            }
            if(updated.length > 0) {
                bizUrl = '/region/updateRegion.do';
            }
            $.ajax({
                url: bizUrl,
                data: row,
                dataType: 'json',
                success: function(result) {
                    if(result.success) {
                        admin_region_dataGrid.datagrid('acceptChanges');
                        $.messager.show({title: '提示', msg: result.msg});
                    } else {
                        admin_region_dataGrid.datagrid('rejectChanges');
                        $.messager.alert('错误', result.msg, 'error');
                    }
                    regionRowEditing = -1;
                    admin_region_dataGrid.datagrid('unselectAll');
                    //admin_region_dataGrid.datagrid('reload');
                }
            });
        }
    });
    
    $('#region_search_input').searchbox({
        menu: '#searchMenu',
        prompt: '选择字段并输入查询值',
        searcher: function(fieldValue, fieldName) {
            if(!commonUtils.isEmpty(fieldValue)) {
                admin_region_dataGrid.datagrid('load', commonUtils.serializeForm($('#region_search_form')));
            }
        }
    });
    
    var changeEditorOnRegionAddRow = function() {
        admin_region_dataGrid.datagrid('addEditor', [{
            field: 'createdTime',
            editor: {
                type: 'datetimebox',
                options: {
                    editable: false
                }
            }
        }]);
    };
    
    var changeEditorOnRegionEditRow = function() {
        admin_region_dataGrid.datagrid('removeEditor', ['createdTime']);
    };
    
});
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" style="overflow:hidden">
        <table id="admin_region_dataGrid"></table>
    </div>
</div>