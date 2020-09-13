$(function () {

    // 导航栏吸顶
    $(window).scroll(function () {
        let targetHeight = $(".top").height()
        let userTargetHeigth = targetHeight + $(".navBar").height()
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

    $(".review-content>textarea").focus(function () {
        $(this).addClass("onfocus")
        return false;
    }).blur(function () {
        $(this).removeClass("onfocus")
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
                        regexp: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
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

    // 登录
    $(".login").click(function () {
        window.location.href = `${contextPath}/loginAndRegister/page?method=1`
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
                    window.location.href = `${contextPath}/loginAndRegister/page?method=1`
                }
            }
        })
    })

    // 更多多评论
    $(".review-bottom>span").click(function () {
        let url = `${contextPath}/articleDesc/remainReview?articleId=` + articleId
        $.ajax({
            url,
            dataType: 'json',
            success: function (data) {
                data.forEach(v => {
                    $(".review-bottom>span").hide("200")
                    let $html = $(`<li>
                        <div class="box">
                            <div class="user">
                                <a href="${contextPath}/home/page?userId=${v.user.id}&categoryId=-1">
                                    <img src="${v.user.picture}"
                                         alt="">
                                    <p>${v.user.name}</p>
                                </a>
                            </div>
                            <div class="desc">
                                <span>${v.content}</span>
                                <span>&nbsp;&nbsp;<i>·&nbsp;</i>${v.remarkDate}</span>
                            </div>
                            <div class="btn btn-primary" data-id="${ v.id }">回复</div>
                        </div>
                        <ul class="children">
                        </ul>
               
                    </li>`)
                    v.children.forEach(v1 => {
                        let $children = $(`<li>
                                    <div class="box">
                                        <div class="user">
                                            <a href="${ contextPath }/home/page?userId=${ v1.user.id }&categoryId=-1">
                                                <img src="${ v1.user.picture }"
                                                     alt="">
                                                <p>${ v1.user.name }</p>
                                            </a>
                                        </div>
                                        <div class="desc">
                                            <i>回复:</i>
                                            <b>${ v1.parent }</b>
                                            <span>${ v1.content }</span>
                                            <span>&nbsp;&nbsp;<i>·&nbsp;</i>${ v1.remarkDate }</span>
                                        </div>
                                        <div class="btn btn-primary" data-id="${ v1.id }">回复</div>
                                    </div>
                                </li>`)
                        $html.find('.children').append($children)
                    })
                    let object = $(".review-bottom>li")
                    let length = object.length
                    let lastObj = object.eq(length - 1)
                    lastObj.after($html)
                })
            }
        })
    })

    // 回复
    $(".review-content>span").click(function () {
        let object = $(".review-content>textarea")
        let text = object.val().trim()
        if (text) {
            let url1 = `${contextPath}/home/findStatus`
            $.get(url1, function (data) {
                let parentId = object.data('id')
                if (data.isSuccess) {
                    let url
                    let review = {
                        content: text,
                        articleId,
                    }
                    if (parentId) {
                        // 回复当前评论
                        review.parentId = parentId
                        url = `${contextPath}/articleDesc/answerReview`
                        $.ajax({
                            url,
                            data: review,
                            dataType: 'json',
                            success: function (data) {
                                if (data.isSuccess) {
                                    object.val('')
                                    object.get(0).dataset.id = undefined
                                    window.location.reload(true)
                                } else {
                                    alert(data.message)
                                }
                            }
                        })
                    } else {
                        // 提交评论首次评论
                        url = `${contextPath}/articleDesc/commitReview`
                        $.ajax({
                            url,
                            data: review,
                            dataType: 'json',
                            success: function (data) {
                                if (data.isSuccess) {
                                    object.val('')
                                    $(".review-top>.tip .text").html("提交成功，等待审核")
                                    $(".review-top>.tip").show("200", function () {
                                        setTimeout(() => {
                                            $(this).hide("200")
                                        }, 3000)
                                    })
                                } else {
                                    alert(data.message)
                                }
                            }
                        })
                    }
                } else {
                    $("#myModal").modal('show')
                }
            }, 'json')
        } else {
            $(".review-top>.tip .text").html("请输入内容")
            $(".review-top>.tip").show("200", function () {
                setTimeout(() => {
                    $(this).hide("200")
                }, 3000)
            })
        }
    })

    // 再次回复点击
    $(".review-bottom .btn").click(function () {
        console.log($(this).siblings(".user").find('a>p'));
        let username = $(this).siblings(".user").find('a>p').text()
        $(".review-content textarea").attr('placeholder', `回复:${username}`)
            .get(0).dataset.id = $(this).data('id')
        window.scrollTo(0, $(".review").position().top)
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
            this.parent.find(`input[name='${this.object}']`)
                .parent()
                .append('<small ' +
                    'data-bv-validator="notEmpty"' +
                    'data-bv-validator-for="' + this.object + '" ' +
                    'class="help-block start"' +
                    'data-bv-result="INVALID">' + this.message + '</small>')
                .addClass("has-error");
            this.removeError()
        }

        // 移出错误
        removeError() {
            this.parent.find(`input[name='${this.object}']`).one("input", function () {
                $(this).siblings(".start").remove()
            })
        }
    }
})
