$(function () {
    // 生成验证码
    $(".codeImage").click(function () {
        $(this).get(0).src = `${contextPath}/home/createCode?x=` + Math.floor(Math.random() * 100)
    })

    $(".changeImage").click(function () {
        $(".codeImage").trigger("click");
    })

    // Bootstrap登录表单验证
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
            // Use Ajax to submit form data 提交至form标签中的action，result自定义
            $.post($form.attr('action'), $form.serialize(), function (result) {
                if (result.isSuccess) {
                    // 跳转首页
                    window.location.href = `${ contextPath }/index/page?categoryId=-1`
                } else {
                    // 刷新验证码
                    result.parent = $("#loginForm")
                    new LoginValidator(result);
                }
            });
        });

    // Bootstrap注册表单验证
    $('#registerForm').bootstrapValidator({
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
                    threshold :  4 , //设置4字符以上开始请求服务器
                    //有待验证，备注以备下次使用。
                    //bootstrap的remote验证器需要的返回结果一定是json格式的数据 :
                    //{"valid":false} //表示不合法，验证不通过
                    //{"valid":true} //表示合法，验证通过
                    remote: {//发起Ajax请求。
                        url: `${ contextPath }/loginAndRegister/checkUsername`,//验证地址
                        data:{ username:$('#registerForm input[name="username"]').val() },
                        message: '用户已存在',//提示消息
                        delay :  2000,//设置2秒发起名字验证
                        type: 'POST' //请求方式
                    }
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
            name: {
                validators: {
                    notEmpty: {
                        message: '名称不能为空'
                    },
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
            // Use Ajax to submit form data 提交至form标签中的action，result自定义
            let formData = new FormData($form[0])
            $.ajax({
                url: $form.attr('action'),
                dataType: 'json',
                type:'POST',
                async: false,
                data: formData,
                processData : false, // 使数据不做处理
                contentType : false, // 不要设置Content-Type请求头
                success: function (result) {
                    if (result.isSuccess) {
                        window.location.href = `${ contextPath }/loginAndRegister/page?method=1`
                    } else {
                        // 刷新验证码
                        result.parent = $("#registerForm")
                        new LoginValidator(result);
                    }
                }
            })
        });

    // Bootstrap修改密码表单验证
    $('#editForm').bootstrapValidator({
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
        .on('success.form.bv', function (e) {
            //点击提交之后
            e.preventDefault();
            let $form = $(e.target);
            // Use Ajax to submit form data 提交至form标签中的action，result自定义
            $.post($form.attr('action'), $form.serialize(), function (result) {
                if (result.isSuccess) {
                    $(".tip").show("200", function () {
                        setTimeout(function () {
                            $(".tip").hide()
                        }, 4000)
                    })
                } else {
                    // 刷新验证码
                    result.parent = $("#editForm")
                    new LoginValidator(result);
                }
            });
        });

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


    // 登录
    $(".login").click(function () {
        window.location.href = `${ contextPath }/loginAndRegister/page?method=1`
    })

    // tab选项卡点击
    $(".nav-tabs li").click(function () {
        let index = $(this).index()
        $(this).addClass("active").siblings().removeClass("active")
        $(".body li").eq(index).addClass("active").siblings().removeClass("active")
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