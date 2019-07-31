<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
$(function() {
    $('#layout_west_menuTree').tree({
        url: '/menu/listMenuTree.do',
        lines: true,
        idFiled: 'id',
        textFiled: 'text',
        parentField: 'pid',
        onClick: function(node) {
            var url = node.url;
            if(!commonUtils.isEmpty(url)) {
                layout_center_openTab({
                    closable: true,
                    title: node.text,
                    href: url
                });
            } else {
                if(node.state == 'closed') {
                    $(this).tree('expand', node.target);
                } else {
                    $(this).tree('collapse', node.target);
                }
            }
        }
    })
});

function openNamedTab(item) {
    var title = $(item).html();
    var url = $(item).attr('url');
    var content = $(item).attr('content');
    if(url) {
        layout_center_openTab({
            closable: true,
            title: title,
            href: url
        });
    }
    if(content) {
        layout_center_openTab({
            closable: true,
            title: title,
            content: '<iframe src="' + content + '" frameborder="0" style="border:0;width:100%;height:100%"/>'
        });
    }
}
</script>

<div class="easyui-accordion" data-options="fit:true,border:false">
    <div title="系统管理" data-options="tools: [{iconCls:'icon-reload', handler:function(){$('#layout_west_menuTree').tree('reload');}}]">
        <ul id="layout_west_menuTree"></ul>
    </div>
    <div title="资源监控">
        <ul><a href="javascript:void(0)" content="http://localhost/manager/status" onclick="openNamedTab(this)">虚拟机状态表</a></ul>
        <ul><a href="javascript:void(0)" content="/druid" onclick="openNamedTab(this)">数据库连接池</a></ul>
        <ul><a href="javascript:void(0)" content="/redis/redisMonitor.do" onclick="openNamedTab(this)">缓存池状态表</a></ul>
    </div>
    <div title="其它栏目">
        <ul><a href="javascript:void(0)" content="http://www.google.com.hk" onclick="openNamedTab(this)">谷歌</a></ul>
        <ul><a href="javascript:void(0)" content="http://www.baidu.com" onclick="openNamedTab(this)">百度</a></ul>
        <ul><a href="javascript:void(0)" content="http://www.qq.com" onclick="openNamedTab(this)">腾讯</a></ul>
        <ul><a href="javascript:void(0)" content="http://www.taobao.com" onclick="openNamedTab(this)">淘宝</a></ul>
        <ul><a href="javascript:void(0)" content="http://www.sina.com.cn" onclick="openNamedTab(this)">新浪</a></ul>
    </div>
</div>