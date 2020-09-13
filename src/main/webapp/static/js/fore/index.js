$(function () {
    let mySwiper = new Swiper('.swiper-container', {
        loop: true, // 循环模式选项

        // 如果需要分页器
        pagination: {
            el: '.swiper-pagination',
        },

        // 如果需要前进后退按钮
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },
        autoplay: true,
    })

    $('.swiper-slide').mouseenter(function () {
        mySwiper.autoplay.stop();
    })
    $('.swiper-slide').mouseleave(function () {
        mySwiper.autoplay.start();
    })

    $(".search-content>input[type=text]").focus(function () {
        $(this).addClass("onfocus")
        return false;
    }).blur(function () {
        $(this).removeClass("onfocus")
    })

    // 搜索
    $(".search-content>input[type=submit]").click(function () {
        let keywords = $(".search-content>input[type=text]").val().trim();
        if (keywords) {
            window.location.href = `${ contextPath }/index/page?categoryId=-1&keywords=${ keywords }`
        }
    })

    // 关键字点击
    $(".keywords-content li a").click(function () {
        let keywords = $(this).text().trim()
        if (keywords) {
            window.location.href = `${ contextPath }/index/page?categoryId=-1&keywords=${ keywords }`
        }
    })

    // 发表文章
    $(".publish").click(function () {
        let url = `${contextPath}/home/findStatus`
        $.ajax({
            url,
            dataType: 'json',
            success: function (data) {
                if (data.isSuccess) {
                    let url = `${ contextPath }/back`
                    let open = window.open('about:blank')
                    if (open === null || typeof(open) === 'undefined') {
                        window.location.href = url
                        return
                    }
                    setTimeout(() => {
                        open.location = url
                    }, 100)
                } else {
                    window.location.href = `${ contextPath }/loginAndRegister/page?method=1`
                }
            }
        })
    })

    // 限定行数
    let titleAry = $(".article .right .title")
    $.each(titleAry, (i, v) => {
        $clamp(v, {clamp: 1})
    })

    let textAry = $(".article .right .text")
    $.each(textAry, (i, v) => {
        $clamp(v, {clamp: 4})
    })

    let publishDateAry = $(".article .right .publishDate")
    $.each(publishDateAry, (i, v) => {
        $clamp(v, {clamp: 1})
    })

    // 导航栏吸顶
    $(window).scroll(function () {
        let targetHeight = $(".top").height()
        if (this.scrollY >= targetHeight) {
            $(".navBar").css({
                position: "fixed",
                left: 0,
                top: 0,
            })
            $(".returnTop").show("300")
        } else {
            $(".navBar").css({
                position: "relative",
                left: 0,
                top: 0
            })
            $(".returnTop").hide("300")
        }
    })

    // 返回顶部
    $(".returnTop").click(function () {
        window.scrollTo(0, 0)
    })

    // 登录
    $(".login").click(function () {
        window.location.href = `${ contextPath }/loginAndRegister/page?method=1`
    })
})
