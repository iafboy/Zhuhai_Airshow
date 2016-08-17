<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>后台管理系统</title>
    <link href="../Public/css/bootstrap.css" rel="stylesheet">
    <link href="../Public/css/docs.css" rel="stylesheet">
    <script src="../Public/js/jquery.min.js"></script>
    <script src="../Public/js/bootstrap.js"></script>
</head>

<body>
<header class="navbar navbar-inverse navbar-fixed-top bs-docs-nav"
        role="banner">
    <div class="container">
        <div class="navbar-header">
            <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".bs-navbar-collapse">
                <span class="sr-only">Toggle navigation</span> 
                <span class="icon-bar"></span> 
                <span class="icon-bar"></span> 
                <span class="icon-bar"></span>
            </button>
            <a href="#" class="navbar-brand">后台管理系统</a>
        </div>
        <nav class="collapse navbar-collapse bs-navbar-collapse"
             role="navigation">
            <ul class="nav navbar-nav">
                <li><a href="../user/index">用户管理</a></li>
                <li><a href="../pavilion/index">展馆管理</a></li>
                <li><a href="../businessman/index">展商管理</a></li>
                <li><a href="../product/index">展品管理</a></li>
                <li><a href="../news/index">新闻管理</a></li>
                <li><a href="../ad/index">广告管理</a></li>
                <li><a href="../activity/index">活动管理</a></li>
                <li><a href="../document/index">档案管理</a></li>
                <li class="active"><a href="../image/index">图片管理</a></li>
                <li><a href="../notify/index">消息发送</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
				<li><a href="../exit/index">退出</a></li>
            </ul>
        </nav>
    </div>
</header>

<div class="container bs-docs-container">
    <div class="row">
        <div class="col-md-3">
            <div class="bs-sidebar hidden-print affix" role="complementary">
                <ul class="nav bs-sidenav">
                    <li class="active"><a href="../image/index">查看图片</a></li>
                    <li><a href="../image/uploadExcel">excel导入</a></li>
                </ul>
            </div>
        </div>

        <div class="col-md-9" role="main">
            <div id="basic">
				<br />
                <h2>信息</h2>
                <br/>
                <table class="table table-bordered table-hover table-condensed">
                    <thead>
                    <tr>
                        <th style="width: 50px">id</th>
                        <th style="width: 200px">名字</th>
                        <th style="width: 150px">图片路径</th>
                        <th>介绍</th>
                    </tr>
                    </thead>
                    <tbody id="user_list">
                    <c:forEach items="${documentList}" var="document">
                        <tr>
                            <td>${document.id}</td>
                            <td>${document.name}</td>
                            <td>${document.path}</td>
                            <td>${document.introduction}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <br/>
            <script>
	            function editUser(id) {
	                window.location.href = "edit?id=" + id;
	            }
	            function deleteUser(id) {
	            	var choice = confirm("确认删除");
	            	if(choice == true){
	                	window.location.href = "delete?id=" + id;
	                }
	            }
            </script>
            <div style="text-align: center">
                <ul class="pagination">
                    <li id="pre"><a href="index?page_id=${prePage}">&laquo;</a></li>
                    <li class="active"><a href="#">${curPage}</a></li>
                    <li id="las"><a href="index?page_id=${lasPage}">&raquo;</a></li>
                </ul>

                <script>
                    $(function(){
                        if(${curPage} == 1 && ${curPage} != ${allPages}){
                            $('#pre').attr('class',"disabled");
                            $('#las').attr('class',"active");
                        }
                        if(${curPage} != 1 && ${curPage} == ${allPages}){
                            $('#pre').attr('class',"active");
                            $('#las').attr('class',"disabled");
                        }
                        if(${curPage} != 1 && ${curPage} != ${allPages}){
                            $('#pre').attr('class',"active");
                            $('#las').attr('class',"active");
                        }
                        if(${curPage} == 1 && ${curPage} == ${allPages}){
                            $('#pre').attr('class',"disable");
                            $('#las').attr('class',"disable");
                        }
                    })
                </script>
            </div>

        </div>
    </div>
</div>

</body>
</html>