$(function () {

    $(".modal .modal-body input").focus(function () {
        $(this).addClass("onfocus")
    }).blur(function () {
        $(this).removeClass("onfocus")
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

    // 粉丝
    $(".fens").click(function () {
        $(".box-top").remove()
        $(".box-content").remove()
        // 获取粉丝列表
        let url = `${contextPath}/home/findFans?userId=${uid}`
        $.ajax({
            url,
            dataType: "json",
            async: false,
            success: function (data) {
                let $html = $(`<div class="box-top">
                    他的粉丝<span>(${data.length})</span>
                    <div class="line"></div>
                </div>
                <ul class="box-content">
                </ul>`)

                data.forEach(v => {
                    let $li = $(`<li>
                        <a href="${contextPath}/home/page?userId=${v.id}&categoryId=-1">
                            <div class="box-left">
                                <img src="${v.picture}"
                                     alt="">
                                <p>${v.name}</p>
                            </div>
                            <div class="box-right">
                                他的主页
                            </div>
                        </a>
                    </li>`)
                    $html.siblings(".box-content").append($li)
                })

                $(".box").append($html)

            }
        })
        $(".box").show("200")
    })

    // 关注
    $(".start").click(function () {
        $(".box-top").remove()
        $(".box-content").remove()
        // 获取粉丝列表
        let url = `${contextPath}/home/findStart?userId=${uid}`
        $.ajax({
            url,
            dataType: "json",
            async: false,
            success: function (data) {
                let $html = $(`<div class="box-top">
                    他的关注<span>(${data.length})</span>
                    <div class="line"></div>
                </div>
                <ul class="box-content">
                </ul>`)

                data.forEach(v => {
                    let $li = $(`<li>
                        <a href="${contextPath}/home/page?userId=${v.id}&categoryId=-1">
                            <div class="box-left">
                                <img src="${v.picture}"
                                     alt="">
                                <p>${v.name}</p>
                            </div>
                            <div class="box-right">
                                他的主页
                            </div>
                        </a>
                    </li>`)
                    $html.siblings(".box-content").append($li)
                })

                $(".box").append($html)

            }
        })
        $(".box").show("200")
    })


    // 关注按钮点击
    $(".follow").click(function () {
        let text = $(this).find("a.active").html().trim()
        // 发送ajax请求用户是否已经登录
        let url
        if (text === "关注") {
            url = `${contextPath}/home/findStatus`
            $.ajax({
                url,
                dataType: 'json',
                success: function (data) {
                    if (data.isSuccess) {
                        // 关注用户
                        let url = `${contextPath}/articleDesc/followUser?uid=` + uid
                        $.ajax({
                            url,
                            dataType: 'json',
                            success: function (data) {
                                if (data.isSuccess) {
                                    $(".follow a").eq(1).addClass("active")
                                        .siblings().removeClass("active")
                                } else {
                                    alert(data.message)
                                }
                            }
                        })
                    } else {
                        // 弹出模态窗口
                        $('#myModal').modal('show')
                    }
                }
            })
        } else if (text === "取消关注") {
            url = `${contextPath}/articleDesc/cancelFollow?uid=` + uid
            $.ajax({
                url,
                dataType: 'json',
                success: function (data) {
                    if (data.isSuccess) {
                        $(".follow a.active").html("关注")
                        window.location.reload()
                    } else {
                        alert(data.message)
                    }
                }
            })
        }
    })

    let text
    // 关注动效
    $(".follow").hover(function () {
        let curObject = $(".follow").find("a.active")
        text = curObject.html().trim()
        if (text === "已关注") {
            curObject.removeClass("active").siblings()
                .addClass("active").html("取消关注")
        }
    }, function () {
        if (text === "已关注") {
            let curObject = $(this).find("a.active")
            curObject.html("关注")
                .removeClass("active")
                .siblings()
                .addClass("active")
        }
    })

    // 生成验证码
    $("#codeImage").click(function () {
        $(this).get(0).src = `${contextPath}/home/createCode?x=` + Math.floor(Math.random() * 100)
    })

    $(".changeImage").click(function () {
        $("#codeImage").trigger("click");
    })

    // Bootstrap表单验证
    $('#loginForm').bootstrapValidator({
        message: 'This value is not valid',
        //提供输入验证图标提示
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username: {
                message: '邮箱验证失败',
                validators: {
                    notEmpty: {
                        message: '邮箱不能为空'
                    },
                    regexp: {
                        regexp: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/ ,
                        message: '请输入正确的邮箱地址'
                    },
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 18,
                        message: '密码长度必须在6到12位之间'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_]+$/,
                        message: '密码只能包含大写、小写、数字和下划线'
                    }
                }
            },
            code: {
                validators: {
                    notEmpty: {
                        message: '验证码不能为空'
                    },
                    stringLength: {
                        min: 4,
                        max: 4,
                        message: '验证码必须为4位'
                    },
                },
            }
        }
    })
        .on('success.form.bv', function (e) {//点击提交之后
            e.preventDefault();
            let $form = $(e.target);
            let bv = $form.data('bootstrapValidator');
            // Use Ajax to submit form data 提交至form标签中的action，result自定义
            $.post($form.attr('action'), $form.serialize(), function (result) {
                if (result.isSuccess) {
                    // 关闭模态窗
                    $("#myModal").modal("hide")
                    window.location.reload(true)
                } else {
                    // 刷新验证码
                    result.parent = $("#loginForm")
                    new LoginValidator(result);
                }
            });
        });

    // 登录
    $(".login").click(function () {
        window.location.href = `${ contextPath }/loginAndRegister/page?method=1`
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



    class LoginValidator {
        constructor(config) {
            this.parent = config.parent
            this.message = config.message
            this.errorClassification = config.errorClassification
            this.initValidator();
        }

        // 初始化验证方法
        initValidator() {
            switch (this.errorClassification) {
                case 1:
                    this.object = "code"
                    break;
                case 2:
                    this.object = "username"
                    break;
                case 3:
                    this.object = "password"
                    break;
                case 4:
                    alert(this.message)
                    break;
            }
            if (this.errorClassification !== 4) {
                this.echoError();
            }
        }

        // 回显错误
        echoError() {
            this.parent.find(`input[name='${ this.object }']`)
                .parent()
                .append('<small ' +
                    'data-bv-validator="notEmpty"' +
                    'data-bv-validator-for="'+ this.object +'" ' +
                    'class="help-block start"' +
                    'data-bv-result="INVALID">' + this.message+ '</small>')
                .addClass("has-error");
            this.removeError()
        }

        // 移出错误
        removeError() {
            this.parent.find(`input[name='${ this.object }']`).one("input", function () {
                $(this).siblings(".start").remove()
            })
        }
    }
})
