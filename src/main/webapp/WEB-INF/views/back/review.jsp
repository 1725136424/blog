<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2020/7/21
  Time: 12:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>用户管理</title>
    <%@include file="/static/common/back/common.jsp" %>
    <style>
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

        .datagrid-body {
            overflow-x: hidden !important;
        }
    </style>
</head>
<body>
<div id="tb">
    <shiro:hasPermission name="user:authorize">
        <a href="#" class="easyui-linkbutton commit" data-options="iconCls:'icon-add',plain:true">通过</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="user:authorize">
        <a href="#" class="easyui-linkbutton rollback" data-options="iconCls:'icon-add',plain:true">撤回</a>
    </shiro:hasPermission>
    <a href="#" class="easyui-linkbutton reload" data-options="iconCls:'icon-reload',plain:true">刷新</a>
    <div class="search-box" style="display: inline-block">
        <input type="text" name="keywords" placeholder="根据内容搜索">
        <a href="#" class="easyui-linkbutton search"
           data-options="iconCls:'icon-search',plain:true">搜索</a>
    </div>
</div>
<table id="datagrid"></table>
<script src="${ pageContext.request.contextPath }/static/js/back/review.js"></script>
</body>
</html>