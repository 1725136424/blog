<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2020/7/14
  Time: 17:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>员工管理</title>
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
            overflow: hidden !important;
        }

        .pictureView {
            display: none;
            margin-top: 15px;
            justify-content: start;
        }

        .right img {
            border-radius: 8px;
            margin-left: 40px;
            height: 60px;
        }
    </style>
</head>
<body>
<div id="tb">
    <shiro:hasPermission name="announcement:save">
        <a href="#" class="easyui-linkbutton save" data-options="iconCls:'icon-add',plain:true">新增</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="announcement:edit">
        <a href="#" class="easyui-linkbutton edit" data-options="iconCls:'icon-edit',plain:true">编辑</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="announcement:delete">
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
    <form id="saveForm" method="post" enctype="multipart/form-data">
        <input type="text" name="id" hidden>
        <div class="input_box">
            <label for="title">标题: </label>
            <input type="text" name="title"
                   id="title"
                   class="easyui-validatebox"
                   data-options="required:true">
        </div>
        <div class="input_box">
            <label for="sort">排序: </label>
            <input type="text" name="sort"
                   id="sort"
                   placeholder="从大到小"
                   class="easyui-validatebox"
                   data-options="required:true">
        </div>
        <div class="input_box">
            <label for="content">内容: </label>
            <input type="text" name="content"
                   id="content"
                   class="easyui-validatebox"
                   data-options="required:true">
        </div>
        <div class="input_box">
            <label for="picture">图片: </label>
            <input type="file"
                   accept="image/*"
                   style="width: 154px"
                   name="image"
                   id="picture">
        </div>
        <div class="pictureView">
            <div class="left">
                预览:
            </div>
            <div class="right">
                <img src="" alt="">
            </div>
        </div>
    </form>
</div>
<script src="${ pageContext.request.contextPath }/static/js/back/announcement.js"></script>
</body>
</html>
