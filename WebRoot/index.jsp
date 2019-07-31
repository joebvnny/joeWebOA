<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>JoeBunny JavaWeb</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" type="text/css" href="/WEB-PAGE/lib/jeasyui-1.5.2/themes/<c:out value="${cookie.easyuiTheme.value}" default="default"/>/easyui.css" id="easyuiTheme"/>
<link rel="stylesheet" type="text/css" href="/WEB-PAGE/lib/jeasyui-1.5.2/themes/icon.css"/>
<script type="text/javascript" src="/WEB-PAGE/lib/jeasyui-1.5.2/jquery-1.11.3.js"></script>
<script type="text/javascript" src="/WEB-PAGE/lib/jeasyui-1.5.2/easyui-1.5.2.min.js"></script>
<script type="text/javascript" src="/WEB-PAGE/lib/jeasyui-1.5.2/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/WEB-PAGE/lib/jeasyui-1.5.2/jquery.cookie.js"></script>
<link rel="stylesheet" type="text/css" href="/WEB-PAGE/css/index.css"/>
<script type="text/javascript" src="/WEB-PAGE/lib/commonUtils.js"></script>
</head>
<body class="easyui-layout">
<div id="loadingMask">Loading......</div>
<jsp:include page="/WEB-PAGE/jsp/_index/login.inc.jsp"/>
<div data-options="region:'north',href:'/WEB-PAGE/jsp/_index/north.inc.jsp'" style="height:50px;overflow:hidden"></div>
<div data-options="region:'south',href:'/WEB-PAGE/jsp/_index/south.inc.jsp'" style="height:20px;overflow:hidden"></div>
<div data-options="region:'east',title:'关注',iconCls:'icon-eyeon',href:'/WEB-PAGE/jsp/_index/east.inc.jsp'" style="width:240px;overflow:hidden"></div>
<div data-options="region:'west',title:'导航',iconCls:'icon-large-smartart',href:'/WEB-PAGE/jsp/_index/west.inc.jsp'" style="width:200px;overflow:hidden"></div>
<div data-options="region:'center',title:'工作台',iconCls:'icon-large-clipart',href:'/WEB-PAGE/jsp/_index/center.inc.jsp'" style="overflow:hidden"></div>
</body>
</html>