<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
$(function() {
    $('#layout_center_tabsMenu').menu({
        onClick: function(item) {
            var thisTabTitle = $(this).data('tabTitle');
            var actionName = $(item.target).attr('action');
            if(actionName === 'refresh') {
                layout_center_refreshTab(thisTabTitle);
                return;
            }
            if(actionName === 'close') {
                var thisTab = $('#layout_center_tabs').tabs('getTab', thisTabTitle);
                if(thisTab.panel('options').closable) {
                    $('#layout_center_tabs').tabs('close', thisTabTitle);
                }
                return;
            }
            var allTabs = $('#layout_center_tabs').tabs('tabs');
            var closeTabsTitle = [];
            $.each(allTabs, function() {
                var opt = $(this).panel('options');
                if(opt.closable && opt.title != thisTabTitle && actionName === 'closeOther') {
                    closeTabsTitle.push(opt.title);
                } else if(opt.closable && actionName === 'closeAll') {
                    closeTabsTitle.push(opt.title);
                }
            });
            for(var i=0; i<closeTabsTitle.length; i++) {
                $('#layout_center_tabs').tabs('close', closeTabsTitle[i]);
            }
        }
    });
    
    $('#layout_center_tabs').tabs({
        fit: true,
        border: false,
        onContextMenu: function(event, title, index) {
            event.preventDefault();
            $('#layout_center_tabsMenu').menu('show', {
                left: event.pageX,
                top: event.pageY
            }).data('tabTitle', title);
        }
    });
    
    layout_center_openTab({
        title: '首页',
        closable: false,
        content: '<iframe src="/WEB-PAGE/jsp/_index/homepage.jsp" frameborder="0" style="border:0;width:100%;height:100%"/>'
    });
});

function layout_center_refreshTab(title) {
    $('#layout_center_tabs').tabs('getTab', title).panel('refresh');
}

function layout_center_openTab(opts) {
    var tabPanel = $('#layout_center_tabs');
    if(tabPanel.tabs('exists', opts.title)) {
        tabPanel.tabs('select', opts.title);
    } else {
        tabPanel.tabs('add', opts);
    }
}
</script>

<div id="layout_center_tabs" style="overflow:hidden"></div>
<div id="layout_center_tabsMenu" style="width:120px;display:none">
    <div action="refresh">刷新</div>
    <div class="menu-sep"></div>
    <div action="close">关闭</div>
    <div action="closeOther">关闭其它</div>
    <div action="closeAll">关闭所有</div>
</div>