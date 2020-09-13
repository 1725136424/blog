<%@ page import="com.study.pojo.user.User" %>
<%@ page import="com.study.util.DateUtils" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <!--可以让部分国产浏览器默认采用高速模式渲染页面-->
    <meta content="webkit" name="renderer">
    <!--为了让 IE 浏览器运行最新的渲染模式下-->
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta content="博客 Java Web 社区讨论 万佳豪的博客系统" name="keywords">
    <meta content="万佳豪的博客系统" name="description">
    <title>用户主页</title>
    <link rel="shortcut icon" href="${ pageContext.request.contextPath }/favicon.ico" type="image/x-icon">
    <link href="${ pageContext.request.contextPath }/static/css/fore/reset.css" rel="stylesheet">
    <!-- Bootstrap -->
    <link href="${ pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"
          rel="stylesheet">
    <%--BootstrapValidator--%>
    <link href="${ pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrapValidator.css"
          rel="stylesheet">
    <link href="${ pageContext.request.contextPath }/static/css/fore/home.css" rel="stylesheet">
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
                        <li class="<c:if test="${  curCategory.id == category.id }">active</c:if>">
                            <a href="${ pageContext.request.contextPath }/home/page?userId=${ user.id }&categoryId=${ category.id }">${ category.name }</a>
                            <ul class="children">
                                <c:forEach items="${ category.children }" var="children">
                                    <li>
                                        <a href="${ pageContext.request.contextPath }/home/page?userId=${ user.id }&categoryId=${ children.id }">${ children.name }</a>
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
<div class="content">
    <div class="container">
        <div class="left">
            <div class="content-top">
                <a href="${ pageContext.request.contextPath }/index/page?categoryId=-1">
                    返回首页
                </a>
            </div>
            <ul class="content-body">
                <c:forEach items="${ articles }" var="article">
                    <li class="article">
                        <a href="${ pageContext.request.contextPath }/articleDesc/page?articleId=${ article.id }">
                            <div class="left">
                                <img alt="" src="${ article.picture }">
                            </div>
                            <div class="right">
                                <div class="title">${ article.title }</div>
                                <div class="text">
                                        ${ article.content }
                                </div>
                                <div class="publishDate">
                                    <div class="dot">·</div>
                                    <fmt:formatDate value="${ article.editDate }" pattern="yyyy-MM-dd"/>
                                </div>
                            </div>
                        </a>
                    </li>
                </c:forEach>
            </ul>
            <%@include file="/static/common/fore/pageNav.jsp" %>
        </div>
        <div class="right">
            <div class="user">
                <div class="user-top">
                    用户
                    <div class="line"></div>
                </div>
                <div class="user-content">
                    <div class="user_top">
                        <img alt=""
                             src="${ user.picture }">
                        <div class="desc">
                            <p>${ user.name }</p>
                            <p>码龄
                                <%
                                    User user = (User) request.getAttribute("user");
                                    DateUtils.DayCompare dayCompare = DateUtils.dayComparePrecise(user.getRegisterDate(), new Date());
                                    out.print(dayCompare.getYear());
                                %>
                                年</p>
                        </div>
                    </div>
                    <ul class="user_content">
                        <li>
                            <p>${ user.publishCount }</p>
                            <p>发文</p>
                        </li>
                        <li>
                            <p>${ user.fansCount }</p>
                            <p>粉丝</p>
                        </li>
                        <li>
                            <p>${ user.reviewCount }</p>
                            <p>评论</p>
                        </li>
                    </ul>
                    <ul class="user_bottom">
                        <li>
                            <a href="javascript:" class="fens">
                                他的粉丝
                                <span>${ user.fansCount }</span>
                            </a>
                        </li>
                        <li>
                            <a href="javascript:" class="start">
                                他的关注
                                <span>${ user.followCount }</span>
                            </a>
                        </li>
                        <li class="follow">
                            <div class="follow-box">
                                <a href="#" class="<c:if test="${ !isStart }">active</c:if>">
                                    关注
                                </a>
                                <a href="#" class="<c:if test="${ isStart }">active</c:if>" style="border: 1px solid #e67e22;color: #ffffff;background: #e67e22;">
                                    已关注
                                </a>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="box">
            </div>
        </div>
    </div>
</div>
<div class="returnTop">
    ^
</div>
<div class="footer">
    <p>create by wanjiahao | <a href="http://www.beian.miit.gov.cn/"> 赣ICP备20010007号</a></p>
</div>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-body">
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
                            <img id="codeImage" src="${pageContext.request.contextPath }/home/createCode">
                            <a class="changeImage" href="javascript:void(0);">看不清，换一张</a>
                        </div>
                    </div>
                    <div class="service">
                        <a href="${ pageContext.request.contextPath }/loginAndRegister/page?method=2">没有账号？</a>
                        <a href="${ pageContext.request.contextPath }/loginAndRegister/page?method=3">忘记密码？</a>
                    </div>
                    <input type="submit" value="登录">
                </form>
            </div>
        </div>
    </div>
</div>
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="${ pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="${ pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script src="${ pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrapValidator.js"></script>
<script src="${ pageContext.request.contextPath }/static/js/fore/swiper.js"></script>
<script src="${ pageContext.request.contextPath }/static/js/fore/clamp.js"></script>
<script>
    let uid =
    ${ user.id }
</script>
<script src="${ pageContext.request.contextPath }/static/js/fore/home.js"></script>
</body>
</html>