<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<form id="admin_role_editForm" method="post">
    <table cellspacing="8">
        <tr>
            <td><input type="hidden" name="id" /></td>
        </tr>
        <tr>
            <th>角色名称：</th>
            <td><input name="rolename" class="easyui-validatebox" data-options="required:true" /></td>
        </tr>
        <tr>
            <th>分配职能：</th>
            <td><input id="admin_role_funcs" name="funcIds" class="easyui-combotree" data-options="
                url: '/func/listFuncTree.do',
                parentField: 'pid',
                lines: true,
                multiple: true,
                onlyLeafCheck: true,
                onCheck: function(node, checked) {
                    var isLeaf = $(this).tree('isLeaf', node.target);
                    var isCascade = $(this).tree('options')['cascadeCheck'];
                    var isOnlyLeaf = $(this).tree('options')['onlyLeafCheck'];
                    if(!isLeaf) {
                    } else {
                    }
                }"
                style="width:300px" />&emsp;<img src="/WEB-PAGE/lib/jeasyui-1.5.2/themes/icons/cancel.png" style="cursor:pointer" onclick="$('#admin_role_funcs').combotree('clear')" /></td>
        </tr>
    </table>
</form>