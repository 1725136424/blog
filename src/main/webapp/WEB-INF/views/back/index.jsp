<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2020/7/21
  Time: 11:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>博客后台管理系统</title>
    <link rel="shortcut icon" href="${ pageContext.request.contextPath }/favicon.ico" type="image/x-icon">
    <%@include file="/static/common/back/common.jsp" %>
</head>
<body class="easyui-layout">
<div data-options="region:'north'" style="height:100px;overflow: hidden">
    <div class="north_top" style="background: #02a2aa; height: 100px;
                display: flex; justify-content: start; align-items: center; padding-left: 20px">
        <img src="${ pageContext.request.contextPath }/static/images/back/login_logo.png" alt="">
    </div>
    <div style="position: absolute; right: 50px; top: 30px;">
        <img src="<shiro:principal property="picture"/>"
             style="vertical-align: middle; margin-right: 10px; width: 50px; height: 50px; border-radius: 50%">
        <span style="color: white; font-size: 20px; margin-right: 5px;"><shiro:principal property="name"/></span>
        <a style="font-size: 18px; color: white;text-decoration: none;"
           href="${pageContext.request.contextPath}/logout">注销</a>
    </div>
</div>
<div data-options="region:'west',split:true" style="width:180px;">
    <div id="aa" class="easyui-accordion" data-options="fit: true" style="width:300px;height:200px;">
        <div title="菜单" data-options="iconCls:'icon-save'">
            <div id="tree"></div>
        </div>
    </div>
</div>
<div data-options="region:'center'" style="padding:5px;background:#eee;">
    <div id="tab"></div>
</div>
<script src="${ pageContext.request.contextPath }/static/js/back/index.js"></script>
</body>
</html>
