<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>付国瑞-家居网购</title>
    <base href="<%=request.getContextPath() + "/"%>">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <link rel="stylesheet" href="assets/css/vendor/vendor.min.css"/>
    <link rel="stylesheet" href="assets/css/plugins/plugins.min.css"/>
    <link rel="stylesheet" href="assets/css/style.min.css"/>
    <script type="text/javascript" src="script/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">
        $(function (){
            /*----------------------------
       Cart Plus Minus Button  购物车数量+与-
   ------------------------------ */
            var CartPlusMinus = $(".cart-plus-minus");
            CartPlusMinus.prepend('<div class="dec qtybutton">-</div>');
            CartPlusMinus.append('<div class="inc qtybutton">+</div>');
            $(".qtybutton").on("click", function() {
                var $button = $(this);
                var oldValue = $button.parent().find("input").val();
                if ($button.text() === "+") {
                    var newVal = parseFloat(oldValue) + 1;
                } else {
                    // Don't allow decrementing below zero
                    if (oldValue > 1) {
                        var newVal = parseFloat(oldValue) - 1;
                    } else {
                        newVal = 1;
                    }
                }
                $button.parent().find("input").val(newVal);
                var furnId = $button.parent().find("input").attr("furnId");
                //这里发出修改购物车的请求
                location.href = "cartServlet?action=updateCount&count=" + newVal + "&id=" + furnId;
            });
            //清空购物车
            $("a.clearCart").click(function (){
                return confirm("是否清空购物车？")
            })
            //删除购物车
            $("a.deleteItem").click(function (){
                //jquery 拿到家居名
                var product_name = $(this).parent().parent().find("td:eq(1)").text();
                //确认弹窗
                return confirm("是否从购物车删除【" + product_name +"】？")
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
                <!-- Header Action Start -->
                <div class="col align-self-center">
                    <div class="header-actions">
                        <div class="header-bottom-set dropdown">
                            <a>欢迎: ${sessionScope.member.username}</a>
                        </div>
                        <div class="header-bottom-set dropdown">
                            <a href="orderServlet?action=queryOrderById">订单管理</a>
                        </div>
                        <div class="header-bottom-set dropdown">
                            <a href="#">安全退出</a>
                        </div>
                    </div>
                </div>
                <!-- Header Action End -->
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
                        <a href="index.jsp"><img width="280px" src="assets/images/logo/logo.png"
                                                  alt="Site Logo"/></a>
                    </div>
                </div>
                <!-- Header Logo End -->
            </div>
        </div>
    </div>
    <!-- Main Menu Start -->
    <div style="width: 100%;height: 50px;background-color: black"></div>
    <!-- Main Menu End -->
</div>
<!-- Header Area End  -->

<!-- OffCanvas Cart Start -->

<!-- OffCanvas Cart End -->

<!-- OffCanvas Menu Start -->

<!-- OffCanvas Menu End -->


<!-- breadcrumb-area start -->


<!-- breadcrumb-area end -->

<!-- Cart Area Start -->
<div class="cart-main-area pt-100px pb-100px">
    <div class="container">
        <h3 class="cart-page-title">您的购物车</h3>
        <div class="row">
            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                <form action="#">
                    <span class="loginErrorMsg"
                          style="float: right; font-weight: bold; font-size: 20pt; margin-left: 10px;">${requestScope.msg}</span>
                    <div class="table-content table-responsive cart-table-content">
                        <table>
                            <thead>
                            <tr>
                                <th>图片</th>
                                <th>家居名</th>
                                <th>单价</th>
                                <th>数量</th>
                                <th>金额</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${not empty requestScope.page.items}">
                            <%--items中取出的是HashMap<Integer,CartItem> 的k-v 所以var是一个entry--%>
                            <c:forEach items="${requestScope.page.items}" var="item" >
                            <tr>
                                <td class="product-thumbnail">
                                    <a href="#"><img class="img-responsive ml-3" src="assets/images/product-image/1.jpg"
                                                     alt=""/></a>
                                </td>
                                <td class="product-name"><a href="#">${item.name}</a></td>
                                <td class="product-price-cart"><span class="amount">￥${item.price}</span></td>
                                <td class="product-quantity">
                                    <div class="cart-plus-minus">
                                        <input class="cart-plus-minus-box" type="text" name="qtybutton" furnId="${item.id}" value="${item.count}"/>
                                    </div>
                                </td>
                                <td class="product-subtotal">￥${item.totalPrice}</td>
                                <td class="product-remove">
                                    <a class="deleteItem" href="cartServlet?action=deleteItem&id=${item.id}&pageNum=${requestScope.page.pageNumber}"><i class="icon-close"></i></a>
                                </td>
                            </tr>
                            </c:forEach>
                            </c:if>
                            </tbody>
                        </table>
                    </div>
                    <%--购物车的page 购物车的显示需要添加条件--%>
                    <div class="pro-pagination-style text-center mb-md-30px mb-lm-30px mt-6" data-aos="fade-up">
                        <ul>
                            <c:if test="${requestScope.page.pageNumber > 1}">
                                <li><a href="${requestScope.page.url}&pageNum=1">首页</a></li>
                                <li><a href="${requestScope.page.url}&pageNum=${requestScope.page.pageNumber - 1}">上页</a></li>
                            </c:if>
                            <c:if test="${requestScope.page.pageNumber < 4 }" >
                                <c:set var="begin" value="1"/>
                            </c:if>
                            <c:if test="${requestScope.page.pageNumber >= 4}">
                                <c:set var="begin" value="${requestScope.page.pageNumber - 2}"/>
                            </c:if>
                            <c:set var="end" value="${requestScope.page.pageTotalCount}"/>
                            <c:forEach begin="${begin}" end="${end}" var="i">
                                <c:if test="${requestScope.page.pageNumber == i}">
                                    <li><a class="active" href="${requestScope.page.url}&pageNum=${i}">${i}</a></li>
                                </c:if>
                                <c:if test="${requestScope.page.pageNumber != i}">
                                    <li><a href="${requestScope.page.url}&pageNum=${i}">${i}</a></li>
                                </c:if>
                            </c:forEach>
                            <c:if test="${requestScope.page.pageNumber < requestScope.page.pageTotalCount}">
                                <li><a href="${requestScope.page.url}&pageNum=${requestScope.page.pageNumber + 1}">下页</a></li>
                                <li><a href="${requestScope.page.url}&pageNum=${requestScope.page.pageTotalCount}">末页</a></li>
                            </c:if>
                            <li><a>共${requestScope.page.pageTotalCount}页</a></li>
                            <li><a>共${requestScope.page.totalRow}记录</a></li>
                        </ul>
                    </div>
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="cart-shiping-update-wrapper">
                                <h4>共${sessionScope.cart.totalCount}件商品 总价 ${sessionScope.cart.cartTotalPrice()}元</h4>
                                <div class="cart-shiping-update">
                                    <a href="orderServlet?action=saveOrder">购 物 车 结 账</a>
                                </div>
                                <div class="cart-clear">
                                    <button ><a href="index.jsp">继 续 购 物</a></button>
                                    <a  class="clearCart" href="cartServlet?action=clear">清 空 购 物 车</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>

            </div>
        </div>
    </div>
</div>
<!-- Cart Area End -->

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
                                        <li class="li"><a class="single-link" href="cart.jsp">我的购物车</a></li>
                                        <li class="li"><a class="single-link" href="login.html">登录</a></li>
                                        <li class="li"><a class="single-link" href="wishlist.html">感兴趣的</a></li>
                                        <li class="li"><a class="single-link" href="../order/checkout.jsp">结账</a></li>
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