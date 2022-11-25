<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge" />
    <title>付国瑞-家居网购</title>
<!--    使用固定的参考路径，之后在第二次优化-->
    <base href="<%=request.getContextPath() + "/"%>">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <link rel="stylesheet" href="assets/css/vendor/vendor.min.css"/>
    <link rel="stylesheet" href="assets/css/plugins/plugins.min.css"/>
    <link rel="stylesheet" href="assets/css/style.min.css"/>
<!--    引入jquery   ctrl+home 快速到页首 ctrl+end 快速到页尾 -->
    <script type="text/javascript" src="script/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">
        $(function (){
            //使用blur 动态验证验证码是否正确
            $("#code").blur(function (){
                var code = this.value;
                $.getJSON("memberServlet", {
                    "action": "isExistCode",
                    "code": code
                    },
                    function (data){
                    console.log("data.isExist",data.isExist);
                    if (data.isExist){
                        $("span.codeErrorMsg").text("验证码正确");
                    }else{
                        $("span.codeErrorMsg").text("验证码不正确");
                    }
                    }

                )
            })
            //给用户名输入框绑定一个blur
            $("#username").blur(function (){
                //获取输入的用户名
                var username = this.value;
                //发送ajax请求
                //携带的数据是通过json对象放入
                $.getJSON("memberServlet", {
                    "action": "isExistUsername",
                    "username": username
                    },
                        function (data){
                            console.log("data",data.isExist);
                            if (data.isExist){
                                $("span.loginErrorMsg").text("用户名存在");
                            } else{
                                $("span.loginErrorMsg").text("用户名可用");
                            }
                        }
                )
            })

            //模拟一个点击事件选中注册
            //决定显示登录还是注册tab "" 不能少
            //注册失败显示注册tab  而不是默认登录tab
            if ("${requestScope.active}" == "register"){
                $("#register_tab")[0].click();//模拟点击
            }

            //对验证码图片进行处理,获取新的验证码
            $("#codeImg").click(function (){
                //url没有变化的时候，图片不会发出新的请求
                //为了防止不请求，携带变化的参数
                this.src = "<%=request.getContextPath()%>/kaptchaServlet?d=" + new Date();
            })
            //登录按钮的提交绑定
            // <input type="text" name="loginUsername" placeholder="Username"/>
            // <input type="password" name="loginPassword" placeholder="Password"/>
            $("#login-btn").click(function (){
                var loginUsernameVal = $("#loginUsername").val();
                var usernamePattern = /^\w{6,10}$/;
                if (!usernamePattern.test(loginUsernameVal)){
                    $("span.loginErrorMsg").text("用户名长度应在6-10");
                    return false;
                }
                var loginPasswordVal = $("#loginPassword").val();
                var passwordPattern = /^\w{6,10}$/;
                if (!passwordPattern.test(loginPasswordVal)){
                    $("span.loginErrorMsg").text("密码长度应在6-10");
                    return false;
                }

                //验证通过
                return true;
            })
            //注册按钮的提交绑定
            $("#sub-btn").click(function (){

                //验证用户名是否符合格式
                var usernameVal = $("#username").val();
                var usernamePattern = /^\w{6,10}$/;
                if (!usernamePattern.test(usernameVal)){
                    $("span[class=errorMsg]").text("用户名格式错误，需数字或字符6-10位");
                    return false;
                }

                //验证密码是否符合格式
                var passwordVal = $("#password").val();
                var passwordPattern = /^\w{6,10}$/;
                if (!passwordPattern.test(passwordVal)){
                    $("span.errorMsg").text("密码格式错误，需数字或字符6-10位");
                    return false;
                }

                //验证第二次密码与第一次密码输入是否一致
                var repwdVal = $("#repwd").val();
                if (repwdVal != passwordVal){
                    $("span.errorMsg").text("两次密码输入不一致");
                    return false;
                }

                //验证邮箱格式是否正确
                var emailVal = $("#email").val();
                //在java中正则表达式转义是两个\\  js中正则表达式是一个\
                var emailPattern = /^[\w-]+@([a-zA-Z]+\.)[a-zA-Z]+$/;
                if (!emailPattern.test(emailVal)){
                    $("span.errorMsg").text("电子邮件格式不正确")
                    return  false;
                }

                //验证码不能为空
                var code = $("#code").val();
                $.trim(code);
                if (null == code || "" == code){
                    $("span.errorMsg").text("验证码不能为空！")
                    return  false;
                }

                //验证通过，但暂时先不提交
                $("span.errorMsg").text("验证通过。。")
                //验证通过后提交给后台
                return  true;
            })
        })
    </script>
</head>

<body>
<!-- Header Area start  -->
<div class="header section">
    <!-- Header Top Message Start -->
    <!-- Header Top  End -->
    <!-- Header Bottom  Start -->
    <div class="header-bottom d-none d-lg-block">
        <div class="container position-relative">
            <div class="row align-self-center">
                <!-- Header Logo Start -->
                <div class="col-auto align-self-center">
                    <div class="header-logo">
                        <a href="index.jsp"><img src="assets/images/logo/logo.png" alt="Site Logo"/></a>
                    </div>
                </div>
                <!-- Header Logo End -->

            </div>
        </div>
    </div>
    <!-- Header Bottom  Start 手机端的header -->
    <div class="header-bottom d-lg-none sticky-nav bg-white">
        <div class="container position-relative">
            <div class="row align-self-center">
                <!-- Header Logo Start -->
                <div class="col-auto align-self-center">
                    <div class="header-logo">
                        <a href="index.jsp"><img width="280px" src="assets/images/logo/logo.png" alt="Site Logo" /></a>
                    </div>
                </div>
                <!-- Header Logo End -->
            </div>
        </div>
    </div>
    <!-- Main Menu Start -->
    <div style="width: 100%;height: 50px;background-color: black"></div>
</div>
<!-- Header Area End  -->
<!-- login area start -->
<div class="login-register-area pt-70px pb-100px">
    <div class="container">
        <div class="row">
            <div class="col-lg-7 col-md-12 ml-auto mr-auto">
                <div class="login-register-wrapper">
                    <div class="login-register-tab-list nav">
                        <a class="active" data-bs-toggle="tab" href="#lg1">
                            <h4>会员登录</h4>
                        </a>
                        <a id="register_tab" data-bs-toggle="tab" href="#lg2">
                            <h4>会员注册</h4>
                        </a>
                    </div>
                    <div class="tab-content">
                        <div id="lg1" class="tab-pane active">
                            <div class="login-form-container">
                                <div class="login-register-form">
                                    <span class="loginErrorMsg"
                                          style="float: right; font-weight: bold; font-size: 20pt; margin-left: 10px;">${requestScope.msg == null ? "" : requestScope.msg}</span>
                                    <%--登录--%>
                                    <form action="memberServlet" method="post">
                                        <%--增加隐藏域 表示login请求--%>
                                        <input type="hidden" name="action" value="login">
                                        <input type="text" name="username" id="loginUsername" value="${requestScope.username}" placeholder="Username"/>
                                        <input type="password" name="password" id="loginPassword" placeholder="Password"/>
                                        <div class="button-box">
                                            <div class="login-toggle-btn">
                                                <input type="checkbox"/>
                                                <a class="flote-none" href="javascript:void(0)">Remember me</a>
                                                <a href="#">Forgot Password?</a>
                                            </div>
                                            <button type="submit" id="login-btn"><span>Login</span></button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div id="lg2" class="tab-pane">
                            <div class="login-form-container">
                                <div class="login-register-form">
                                    <span class="loginErrorMsg"
                                          style="float: right; font-weight: bold; font-size: 20pt; margin-left: 10px;">${requestScope.msg == null ? "" : requestScope.msg}</span>
                                   <%--注册表单--%>
                                    <form action="memberServlet" method="post">
                                        <%--增加隐藏域 表示register请求--%>
                                        <input type="hidden" name="action" value="register">
                                        <input type="text" id="username" name="username" value="${requestScope.username}" placeholder="Username"/>
                                        <input type="password" id="password" name="password" placeholder="输入密码"/>
                                        <input type="password" id="repwd" name="repwd" placeholder="确认密码"/>
                                        <input name="email" id="email" placeholder="电子邮件" value="${requestScope.email}" type="email"/>
                                        <input type="text" id="code" name="code" style="width: 50%"
                                               placeholder="验证码"/>　　<img id="codeImg" alt="" src="kaptchaServlet" style="width:120px;height: 50px">
                                            <span class="codeErrorMsg"
                                                  style="float: right; font-weight: bold; font-size: 20pt; margin-left: 10px;"></span>
                                        <div class="button-box">
                                            <button type="submit" id="sub-btn"><span>会员注册</span></button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- login area end -->

<!-- Footer Area Start -->
<div class="footer-area">
    <div class="footer-container">
        <div class="footer-top">
            <div class="container">
                <div class="row">
                    <!-- Start single blog -->
                    <!-- End single blog -->
                    <!-- Start single blog -->
                    <div class="col-md-6 col-sm-6 col-lg-3 mb-md-30px mb-lm-30px" data-aos="fade-up"
                         data-aos-delay="400">
                        <div class="single-wedge">
                            <h4 class="footer-herading">信息</h4>
                            <div class="footer-links">
                                <div class="footer-row">
                                    <ul class="align-items-center">
                                        <li class="li"><a class="single-link" href="about.html">关于我们</a></li>
                                        <li class="li"><a class="single-link" href="#">交货信息</a></li>
                                        <li class="li"><a class="single-link" href="privacy-policy.html">隐私与政策</a></li>
                                        <li class="li"><a class="single-link" href="#">条款和条件</a></li>
                                        <li class="li"><a class="single-link" href="#">制造</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- End single blog -->
                    <!-- Start single blog -->
                    <div class="col-md-6 col-lg-2 col-sm-6 mb-lm-30px" data-aos="fade-up" data-aos-delay="600">
                        <div class="single-wedge">
                            <h4 class="footer-herading">我的账号</h4>
                            <div class="footer-links">
                                <div class="footer-row">
                                    <ul class="align-items-center">
                                        <li class="li"><a class="single-link" href="my-account.html">我的账号</a>
                                        </li>
                                        <li class="li"><a class="single-link" href="cart.html">我的购物车</a></li>
                                        <li class="li"><a class="single-link" href="login.jsp">登录</a></li>
                                        <li class="li"><a class="single-link" href="wishlist.html">感兴趣的</a></li>
                                        <li class="li"><a class="single-link" href="checkout.html">结账</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- End single blog -->
                    <!-- Start single blog -->
                    <div class="col-md-6 col-lg-3" data-aos="fade-up" data-aos-delay="800">

                    </div>
                    <!-- End single blog -->
                </div>
            </div>
        </div>
        <div class="footer-bottom">
            <div class="container">
                <div class="row flex-sm-row-reverse">
                    <div class="col-md-6 text-right">
                        <div class="payment-link">
                            <img src="#" alt="">
                        </div>
                    </div>
                    <div class="col-md-6 text-left">
                        <p class="copy-text">Copyright &copy; 2021 韩顺平教育~</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Footer Area End -->
<script src="assets/js/vendor/vendor.min.js"></script>
<script src="assets/js/plugins/plugins.min.js"></script>
<!-- Main Js -->
<script src="assets/js/main.js"></script>
</body>
</html>