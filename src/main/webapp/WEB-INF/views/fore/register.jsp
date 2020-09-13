<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
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
    <title>注册</title>
    <link rel="shortcut icon" href="${ pageContext.request.contextPath }/favicon.ico" type="image/x-icon">
    <link href="${ pageContext.request.contextPath }/static/css/fore/reset.css" rel="stylesheet">
    <!-- Bootstrap -->
    <link href="${ pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"
          rel="stylesheet">
    <%--BootstrapValidator--%>
    <link href="${ pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrapValidator.css"
          rel="stylesheet">
    <link href="${ pageContext.request.contextPath }/static/css/fore/register.css" rel="stylesheet">
    <!-- HTML5 shim 和 Respond.js 是为了让 IE8 支持 HTML5 元素和媒体查询（media queries）功能 -->
    <!-- 警告：通过 file:// 协议（就是直接将 html 页面拖拽到浏览器中）访问页面时 Respond.js 不起作用 -->
    <!--[if lt IE 9]>
    <script src="${ pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/html5shiv.min.js"></script>
    <script src="${ pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="top">
    <div class="container">
        <shiro:hasRole name="user">
            <div class="user">
                <img src="<shiro:principal property="picture"/>" alt="">
                <a href="${ pageContext.request.contextPath }/logout">注销</a>
            </div>
        </shiro:hasRole>
        <div class="btnAry">
            <shiro:lacksRole name="user">
                <div class="login">
                    登录
                </div>
            </shiro:lacksRole>
            <div class="publish">
                发表文章
                <c:if test="${ reviewCount != 0 && reviewCount != null }">
                    <i>${ reviewCount }</i>
                </c:if>
            </div>
        </div>
    </div>
</div>
<div class="navBar">
    <nav class="navbar navbar-default">
        <div class="container">
            <div class="navbar-header">
                <button class="navbar-toggle collapsed" data-target="#bs-example-navbar-collapse-1"
                        data-toggle="collapse"
                        type="button">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">豪大大博客</a>
            </div>
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav categoryList">
                    <c:forEach items="${ categories }" var="category">
                        <li class="<c:if test="${  curCategory.id == category.id || curCategory.parentId == category.id }">active</c:if>">
                            <a href="${ pageContext.request.contextPath }/index/page?categoryId=${ category.id }">${ category.name }</a>
                            <ul class="children">
                                <c:forEach items="${ category.children }" var="children">
                                    <li>
                                        <a href="${ pageContext.request.contextPath }/index/page?categoryId=${ children.id }">${ children.name }</a>
                                    </li>
                                </c:forEach>
                                <i></i>
                            </ul>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </nav>
</div>
<div class="container">
    <div class="box">
        <ul class="nav nav-tabs">
            <li role="presentation" class="<c:if test='${ method == 1 }'>active</c:if>"><a href="#">登录</a></li>
            <li role="presentation" class="<c:if test='${ method == 2 }'>active</c:if>"><a href="#">注册</a></li>
            <li role="presentation" class="<c:if test='${ method == 3 }'>active</c:if>"><a href="#">修改密码</a></li>
        </ul>
        <ul class="body">
            <li class="<c:if test='${ method == 1 }'>active</c:if>">
                <form action="${ pageContext.request.contextPath }/home/login"
                      id="loginForm"
                      method="post">
                    <div class="form-group">
                        <input type="text" placeholder="请输入邮箱" name="username">
                    </div>
                    <div class="form-group">
                        <input type="password" placeholder="请输入密码" name="password">
                    </div>
                    <div class="codeBox">
                        <div class="left form-group">
                            <input type="text" placeholder="请输入验证码" name="code">
                        </div>
                        <div class="right">
                            <img class="codeImage" src="${pageContext.request.contextPath }/home/createCode">
                            <a class="changeImage" href="javascript:void(0);">看不清，换一张</a>
                        </div>
                    </div>
                    <div class="service">
                        <a href="${ pageContext.request.contextPath }/loginAndRegister/page?method=2">没有账号？</a>
                        <a href="${ pageContext.request.contextPath }/loginAndRegister/page?method=3">忘记密码？</a>
                    </div>
                    <input type="submit" value="登录">
                </form>
            </li>
            <li class="<c:if test='${ method == 2 }'>active</c:if>">
                <form action="${ pageContext.request.contextPath }/loginAndRegister/register"
                      id="registerForm"
                      method="post">
                    <div class="form-group">
                        <input type="text" placeholder="请输入昵称" name="name">
                    </div>
                    <div class="form-group">
                        <input type="text" placeholder="请输入邮箱" name="username">
                    </div>
                    <div class="form-group">
                        <input type="password" placeholder="请输入密码" name="password">
                    </div>
                    <div class="codeBox">
                        <div class="left form-group">
                            <input type="text" placeholder="请输入验证码" name="code">
                        </div>
                        <div class="right">
                            <img class="codeImage" src="${pageContext.request.contextPath }/home/createCode">
                            <a class="changeImage" href="javascript:void(0);">看不清，换一张</a>
                        </div>
                    </div>
                    <div class="picture">
                        <div class="left">头像</div>
                        <div class="right">
                            <input type="file" name="image">
                        </div>
                    </div>
                    <input type="submit" value="注册">
                </form>
            </li>
            <li class="<c:if test='${ method == 3 }'>active</c:if>">
                <form action="${ pageContext.request.contextPath }/loginAndRegister/sendEmail"
                      id="editForm"
                      method="post">
                    <div class="form-group">
                        <input type="text" placeholder="请输入邮箱" name="username">
                    </div>
                    <div class="codeBox">
                        <div class="left form-group">
                            <input type="text" placeholder="请输入验证码" name="code">
                        </div>
                        <div class="right">
                            <img class="codeImage" src="${pageContext.request.contextPath }/home/createCode">
                            <a class="changeImage" href="javascript:void(0);">看不清，换一张</a>
                        </div>
                    </div>
                    <input type="submit" value="修改密码">
                    <div class="alert alert-warning alert-dismissible tip" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <strong>提示:</strong> 发送邮件成功, 请耐心等待。。。
                    </div>
                </form>
            </li>
        </ul>
    </div>
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
<script src="${ pageContext.request.contextPath }/static/js/fore/register.js"></script>
</html>