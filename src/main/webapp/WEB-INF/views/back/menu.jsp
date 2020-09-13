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
    <title>菜单管理</title>
    <%@include file="/static/common/back/common.jsp" %>
    <style>
        .datagrid-body {
            overflow-x: hidden !important;
        }

        #saveForm {
            padding: 20px 35px 0 35px;
            box-sizing: border-box;
        }

        #saveForm .input_box {
            height: 40px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        #saveForm .input_box input {
            font-size: 12px;
            padding-left: 5px;
            box-sizing: border-box;
        }

        .textbox.textbox-invalid.combo.datebox {
            position: relative;
            left: 9px;
            width: 145px !important;
        }

        .textbox.combo {
            position: relative;
            left: 8px;
            width: 145px !important;
        }

        .search-box input {
            padding-left: 5px;
            font-size: 12px;
            color: rgba(0, 0, 0, 0.6);
        }

        .search-box ::-webkit-input-placeholder {
            font-size: 12px;
            color: rgba(0, 0, 0, 0.6);
        }
    </style>
</head>
<body>
<div id="tb">
    <shiro:hasPermission name="menu:save">
        <a href="#" class="easyui-linkbutton save" data-options="iconCls:'icon-add',plain:true">新增</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="menu:edit">
        <a href="#" class="easyui-linkbutton edit" data-options="iconCls:'icon-edit',plain:true">编辑</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="menu:delete">
        <a href="#" class="easyui-linkbutton delete" data-options="iconCls:'icon-remove',plain:true">删除</a>
    </shiro:hasPermission>
    <a href="#" class="easyui-linkbutton reload" data-options="iconCls:'icon-reload',plain:true">刷新</a>
    <div class="search-box" style="display: inline-block">
        <input type="text" name="keywords" placeholder="根据名称搜索">
        <a href="#" class="easyui-linkbutton search"
           data-options="iconCls:'icon-search',plain:true">搜索</a>
    </div>
</div>
<table id="dg"></table>
<div id="dialog">
    <form id="saveForm" method="post">
        <input type="text" name="id" hidden>
        <div class="input_box">
            <label for="text">名称: </label>
            <input type="text" name="text"
                   id="text"
                   class="easyui-validatebox"
                   data-options="required:true">
        </div>
        <div class="input_box">
            <label for="url">请求路径: </label>
            <input type="text" name="url"
                   id="url"
                   class="easyui-validatebox"
                   data-options="required:true">
        </div>
        <div class="input_box">
            <label for="parent">父菜单: </label>
            <input type="text" name="parentId"
                   id="parent">
        </div>
        <div class="input_box">
            <label for="permission">访问权限: </label>
            <input type="text" name="permissionId"
                   id="permission">
        </div>
    </form>
</div>
<script src="${ pageContext.request.contextPath }/static/js/back/menu.js"></script>
</body>
</html>
