<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2020/7/14
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>权限管理</title>
    <%@include file="/static/common/back/common.jsp" %>
    <style>
        .search-box input {
            padding-left: 5px;
            font-size: 12px;
            color: rgba(0, 0, 0, 0.6);
        }

        .search-box ::-webkit-input-placeholder {
            font-size: 12px;
            color: rgba(0, 0, 0, 0.6);
        }

        #saveForm {
            padding: 20px 15px 0 15px;
        }

        .dialog_top {
            display: flex;
            justify-content: space-between;
            align-items: center;
            height: 60px;
        }

        .dialog_top input {
            padding-left: 5px;
            font-size: 12px;
            color: #000000;
        }

        .dialog_bottom {
            width: 100%;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .dialog_bottom .left, .dialog_bottom .right {
            width: 46%;
            height: 250px;
        }

        .dialog_bottom .right {
            margin-right: 2px;
        }

        .datagrid-body {
            overflow-x: hidden !important;
        }

        .left .datagrid-body {
            overflow: auto !important;
        }

        .right .datagrid-body {
            overflow: auto !important;
        }
    </style>
</head>
<body>
<div id="tb">
    <shiro:hasPermission name="role:save">
        <a href="#" class="easyui-linkbutton save" data-options="iconCls:'icon-add',plain:true">新增</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="role:edit">
    <a href="#" class="easyui-linkbutton edit" data-options="iconCls:'icon-edit',plain:true">编辑</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="role:delete">
    <a href="#" class="easyui-linkbutton delete" data-options="iconCls:'icon-remove',plain:true">删除</a>
    </shiro:hasPermission>
    <a href="#" class="easyui-linkbutton reload" data-options="iconCls:'icon-reload',plain:true">刷新</a>
    <div class="search-box" style="display: inline-block">
        <input type="text" name="keywords" placeholder="根据名称名搜索">
        <a href="#" class="easyui-linkbutton search"
           data-options="iconCls:'icon-search',plain:true">搜索</a>
    </div>
</div>
<div id="datagrid"></div>
<div id="dialog">
    <form method="post" id="saveForm">
        <div class="dialog_top">
            <div class="left">
                <input type="text" name="id" hidden>
                <label for="number">编号:</label>
                <input type="text"
                       class="easyui-validatebox"
                       name="number"
                       id="number"
                       data-options="required:true">
            </div>
            <div class="right">
                <label for="name">名称:</label>
                <input type="text"
                       class="easyui-validatebox"
                       name="name"
                       id="name"
                       data-options="required:true">
            </div>
        </div>
        <div class="dialog_bottom" data-isload="false">
            <div class="left">
                <div class="allPermission"></div>
            </div>
            <div class="right">
                <div class="selectedPermission"></div>
            </div>
        </div>
    </form>
</div>
<script src="${ pageContext.request.contextPath }/static/js/back/role.js"></script>
</body>
</html>

