<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2020/7/30
  Time: 20:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!--可以让部分国产浏览器默认采用高速模式渲染页面-->
    <meta content="webkit" name="renderer">
    <!--为了让 IE 浏览器运行最新的渲染模式下-->
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta content="博客 Java Web 社区讨论 万佳豪的博客系统" name="keywords">
    <meta content="万佳豪的博客系统" name="description">
    <title>修改密码</title>
    <link rel="shortcut icon" href="${ pageContext.request.contextPath }/favicon.ico" type="image/x-icon">
    <link href="${ pageContext.request.contextPath }/static/css/fore/reset.css" rel="stylesheet">
    <!-- Bootstrap -->
    <link href="${ pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"
          rel="stylesheet">
    <%--BootstrapValidator--%>
    <link href="${ pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrapValidator.css"
          rel="stylesheet">
    <link href="${ pageContext.request.contextPath }/static/css/fore/editPassword.css" rel="stylesheet">
    <!-- HTML5 shim 和 Respond.js 是为了让 IE8 支持 HTML5 元素和媒体查询（media queries）功能 -->
    <!-- 警告：通过 file:// 协议（就是直接将 html 页面拖拽到浏览器中）访问页面时 Respond.js 不起作用 -->
    <!--[if lt IE 9]>
    <script src="${ pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/html5shiv.min.js"></script>
    <script src="${ pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="container">
    <form action="${ pageContext.request.contextPath }/loginAndRegister/editPassword"
          id="editForm"
          method="post">
        <div class="form-group">
            <input type="password" placeholder="请输入密码" name="password">
        </div>
        <div class="form-group">
            <input type="password" class="confirm" placeholder="请再次输入密码">
        </div>
        <input type="submit" value="修改密码">
    </form>
</div>
</body>
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="${ pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="${ pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script src="${ pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrapValidator.js"></script>
<script>
    let contextPath = '${ pageContext.request.contextPath }'
</script>
<script src="${ pageContext.request.contextPath }/static/js/fore/editPassword.js"></script>
</html>
