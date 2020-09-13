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
    <meta name="baidu-site-verification" content="MarlcEWlhm" />
    <meta content="博客 Java Web 社区讨论 万佳豪的博客系统" name="keywords">
    <meta content="万佳豪的博客系统" name="description">
    <title>博客首页</title>
    <link rel="shortcut icon" href="${ pageContext.request.contextPath }/favicon.ico" type="image/x-icon">
    <link href="${ pageContext.request.contextPath }/static/css/fore/reset.css" rel="stylesheet">
    <!-- Bootstrap -->
    <link href="${ pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="${ pageContext.request.contextPath }/static/css/fore/swiper.css" rel="stylesheet">
    <link href="${ pageContext.request.contextPath }/static/css/fore/index.css" rel="stylesheet">
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
<div class="content">
    <div class="container">
        <div class="left">
            <div class="content-top">
                <a href="${ pageContext.request.contextPath }/index/page?categoryId=${ curCategory.id }">
                    ${ curCategory.name }
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
            <%@include file="/static/common/fore/pageNav.jsp"%>
        </div>
        <div class="right">
            <div class="banner">
                <div class="banner-top">
                    热门
                    <div class="line"></div>
                </div>
                <div class="banner-content">
                    <div class="swiper-container">
                        <div class="swiper-wrapper">
                            <c:forEach items="${ banners }" var="banner">
                                <div class="swiper-slide">
                                    <a href="${ banner.url }">
                                        <img alt="" src="${ banner.picture }">
                                    </a>
                                </div>
                            </c:forEach>
                        </div>

                        <!-- 如果需要导航按钮 -->
                        <div class="swiper-button-prev"></div>
                        <div class="swiper-button-next"></div>

                    </div>
                </div>
            </div>
            <div class="search">
                <div class="search-top">
                    站内搜索
                    <div class="line"></div>
                </div>
                <div class="search-content">
                    <input placeholder="请输入您要搜索的内容"
                           type="text"
                           value="<c:if test="${ !empty search }">${ search }</c:if>">
                    <input type="submit" value="搜索">
                </div>
            </div>
            <div class="announcement">
                <div class="announcement-top">
                    网站公告
                    <div class="line"></div>
                </div>
                <div class="announcement-content">
                    <div class="announcement-title">
                        ${ announcement.title }
                    </div>
                    <div class="announcement-content">
                        ${ announcement.content }
                    </div>
                    <div class="announcement-image">
                        <a href="#">
                            <img alt="" src="${ announcement.picture }">
                        </a>
                    </div>
                </div>
            </div>
            <div class="keywords">
                <div class="keywords-top">
                    关键字
                    <div class="line"></div>
                </div>
                <ul class="keywords-content">
                    <c:forEach items="${ keywords }" var="keyword">
                        <li>
                            <a href="#">${ keyword.name }</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="link">
                <div class="link-top">
                    友情链接
                    <div class="line"></div>
                </div>
                <ul class="link-content">
                    <c:forEach items="${ links }" var="link">
                        <li>
                            <a href="${ link.url }">${ link.name }</a>
                        </li>
                    </c:forEach>
                </ul>
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
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="${ pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="${ pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script src="${ pageContext.request.contextPath }/static/js/fore/swiper.js"></script>
<script src="${ pageContext.request.contextPath }/static/js/fore/clamp.js"></script>
<script src="${ pageContext.request.contextPath }/static/js/fore/index.js"></script>
</body>
</html>