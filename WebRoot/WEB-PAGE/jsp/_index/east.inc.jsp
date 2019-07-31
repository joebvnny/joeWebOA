<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
$(function() {
    $('#layout_east_userlist').datagrid({
        url: '/user/listOnlineUsers.do',
        fit: true,
        fitColumns: true,
        border: false,
        nowarp: false,
        pagination: true,
        pageSize: 10,
        idField: 'userId',
        remoteSort: false,
        sortName: 'loginTime',
        sortOrder: 'desc',
        frozenColumns : [[{
            hidden: true,
            width: 100,
            title: 'ID',
            field: 'userId'
        }]],
        columns: [[{
            width: 120,
            sortable: true,
            title: '登录名',
            field: 'username'
        }, {
            width: 160,
            title: '来访IP',
            field: 'clientIp',
            hidden: true
        }, {
            width: 160,
            sortable: true,
            title: '登录时间',
            field: 'loginTime'
        }]],
        onLoadSuccess: function(result) {
            $('#layout_east_online').panel('setTitle', '在线 [' + result.total + '] 人');
        },
	    onClickRow: function(rowIndex, rowData) {
	    }
    }).datagrid('getPager').pagination({
        showPageList: false,
        showRefresh: false,
        displayMsg: '',
        beforePageText: '',
        afterPageText: '/{pages}'
    });
	
    $('#layout_east_online').panel({
        tools: [{
            iconCls: 'icon-reload',
            handler: function() {
                $('#layout_east_userlist').datagrid('reload');
            }
        }]
    });
    
    window.setInterval(function() {
        $('#layout_east_userlist').datagrid('reload');
    }, 300000);
	
});
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height:120px;overflow:hidden;padding:6px">
        <iframe id="layout_east_weather" src="http://i.tianqi.com/index.php?c=code&id=34" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
    </div>
    <div data-options="region:'center',border:false" style="overflow:hidden">
        <div id="layout_east_online" data-options="fit:true,border:false,title:'在线用户列表'">
            <table id="layout_east_userlist"></table>
        </div>
    </div>
</div>