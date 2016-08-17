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
				<a href="index.html" class="navbar-brand">后台管理系统</a>
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
                        <li class="active"><a href="../user/index">查看用户</a></li>
                        <li><a href="../user/add">添加用户</a></li>
					</ul>
				</div>
			</div>

			<div class="col-md-9" role="main">
				<div id="add">
					<br />
					<h2>编辑用户</h2>
					<br />
					<form class="form-horizontal" role="form" action="editCheck?id=${user.id}" method="post">
                        <div class="form-group">
                            <label for="id" class="col-sm-1 control-label">id</label>
                            <div class="col-sm-11">
                                <input type="text" class="form-control" id="id" name="id" value="${user.id}" readonly>
                            </div>
                        </div>

                        <div class="form-group">
							<label for="phone" class="col-sm-1 control-label">手机</label>
    						<div class="col-sm-11">
								<input type="text" class="form-control" id="phone" name="phone" value="${user.phone}">
							</div>
                        </div>
                        
						<div class="form-group">	
							<label for="email" class="col-sm-1 control-label">密码</label> 
    						<div class="col-sm-11">
								<input type="email" class="form-control" id="email" name="email" value="${user.email}">
							</div>
                        </div>
                        
                        <div class="form-group">	
							<label for="introduction" class="col-sm-1 control-label">介绍</label> 
    						<div class="col-sm-11">
								<textarea class="form-control" id="introduction" name="introduction" rows="5">${user.introduction}</textarea>
							</div>
                        </div>
                        
                        <div class="form-group">    
                            <div style="text-align: center">
                                <button type="submit" class="btn btn-default">修改</button>
                            </div>
                        </div>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>