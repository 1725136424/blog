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
            padding: 20px 35px 0 35px;
            box-sizing: border-box;
        }

        #saveForm .input_box {
            height: 50px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        #saveForm .input_box input {
            font-size: 12px;
            padding-left: 5px;
            box-sizing: border-box;
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


        .background {
            display: none;
            left: 0;
            top: 0;
            position: absolute;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.4);
        }

        .background .modal {
            position: absolute;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
            width: 80%;
            height: 95%;
            background: #ffffff;
        }

        .btnAry {
            position: absolute;
            bottom: 50px;
            right: 10px;
        }

        .pictureView{
            display: none;
            margin-top: 15px;
            justify-content: start;
        }

        .pictureView.show{
            display: flex;
        }

        .right img{
            border-radius: 8px;
            margin-left: 40px;
            height: 60px;
        }
    </style>
</head>
<body>
<div id="tb">
    <shiro:hasPermission name="article:save">
        <a href="#" class="easyui-linkbutton save" data-options="iconCls:'icon-add',plain:true">新增</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="article:edit">
        <a href="#" class="easyui-linkbutton edit" data-options="iconCls:'icon-edit',plain:true">编辑</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="article:delete">
        <a href="#" class="easyui-linkbutton delete" data-options="iconCls:'icon-remove',plain:true">删除</a>
    </shiro:hasPermission>
    <a href="#" class="easyui-linkbutton reload" data-options="iconCls:'icon-reload',plain:true">刷新</a>
    <div class="search-box" style="display: inline-block">
        <input type="text" name="keywords" placeholder="根据名称名搜索">
        <a href="#" class="search"
           data-options="iconCls:'icon-search',plain:true">搜索</a>
    </div>
</div>
<div id="datagrid"></div>
<div class="background">
    <div class="modal">
        <div id="editor" style="width: 100%; height: 320px"></div>
        <div class="btnAry">
            <a href="#" class="easyui-linkbutton saveArticle" data-options="iconCls:'icon-save'">保存</a>
            <a href="#" class="easyui-linkbutton closeArticle" data-options="iconCls:'icon-remove'">关闭</a>
        </div>
    </div>
</div>
<div id="dialog">
    <form method="post" id="saveForm" enctype="multipart/form-data">
        <input type="text" name="id" hidden>
        <div class="input_box">
            <label for="title">标题: </label>
            <input type="text" name="title"
                   id="title"
                   class="easyui-validatebox"
                   data-options="required:true">
        </div>
        <div class="input_box">
            <label for="category">所属分类: </label>
            <input type="text"
                   name="categoryId"
                   id="category">
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
<div id="menu"></div>
<script src="${ pageContext.request.contextPath }/static/ueditor/ueditor.config.js"></script>
<script src="${ pageContext.request.contextPath }/static/ueditor/ueditor.all.min.js"></script>
<script src="${ pageContext.request.contextPath }/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script src="${ pageContext.request.contextPath }/static/js/back/article.js"></script>
</body>
</html>

