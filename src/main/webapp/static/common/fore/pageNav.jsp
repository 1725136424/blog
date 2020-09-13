<style>
    nav {
        text-align: center;
    }
    nav .pagination li a {
        transition: all 0.3s;
        color: #e67e22 !important;
    }
    nav .pagination li:hover a {
        background: rgba(0, 0, 0, 0.1) !important;
    }
    nav .pagination li.active a {
        border: 1px solid #e67e22 !important;
        background: #e67e22 !important;
        color: #ffffff !important;
    }

</style>
<nav aria-label="Page navigation">
    <ul class="pagination">
        <li>
            <a class="btn <c:if test="${ not articlePageInfo.hasPreviousPage }">disabled</c:if>"
               href="${ pageContext.request.contextPath }/index/page?categoryId=${ curCategory.id }&page=${ articlePageInfo.pageNum - 1 }&keywords=${ search }"
               aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>
        <c:forEach items="${ articlePageInfo.navigatepageNums }" var="num">
            <li class="<c:if test="${ articlePageInfo.pageNum == num }">active</c:if> ">
                <a href="${ pageContext.request.contextPath }/index/page?categoryId=${ curCategory.id }&page=${ num }&keywords=${ search }">${ num }</a>
            </li>
        </c:forEach>
        <li class="<c:if test="${ not articlePageInfo.hasNextPage }">disabled</c:if>">
            <a class="btn <c:if test="${ not articlePageInfo.hasNextPage }">disabled</c:if>"
               href="${ pageContext.request.contextPath }/index/page?categoryId=${ curCategory.id }&page=${ articlePageInfo.pageNum + 1 }&keywords=${ search }"
               aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    </ul>
</nav>
<script>
    const contextPath = "${ pageContext.request.contextPath }"
</script>
