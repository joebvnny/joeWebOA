<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
$(function() {
    var admin_region_dataTree = $('#admin_region_dataTree').tree({
        url: '/region/regionTree.do',
        lines: true,
        idFiled: 'id',
        textFiled: 'text',
        parentField: 'pid'
    });
});
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" style="padding:8px">
        <ul id="admin_region_dataTree"></ul>
    </div>
</div>