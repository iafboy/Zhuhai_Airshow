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
				<button class="navbar-toggle" type="button" data-toggle="collapse"
					data-target=".bs-navbar-collapse">
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
                	<li class="active"><a href="../activity/index">活动管理</a></li>
                    <li><a href="../document/index">档案管理</a></li>
                    <li><a href="../image/index">图片管理</a></li>
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
                        <li class="active"><a href="../activity/index">查看活动</a></li>
                        <li><a href="../activity/add">添加活动</a></li>
                        <li><a href="../activity/indexPeople">查看活动人员</a></li>
                        <li><a href="../activity/uploadExcelPeople">导入活动人员</a></li>
                        <li><a href="../activity/indexPeopleImage">查看活动图片</a></li>
                        <li><a href="../activity/uploadExcelPeopleImage">导入活动图片信息</a></li>
					</ul>
				</div>
			</div>

			<div class="col-md-9" role="main">
				<div id="add">
					<br />
					<h2>活动信息</h2>
					<br />

                    <form class="form-horizontal" role="form" >
                        <div class="form-group">
                            <label for="title"  class="col-sm-2 control-label">标题</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="title" name="title" value="${activity.title}" readonly="readonly">
                            </div>
                        </div>

                        <c:if test="${activity.path != null}">
                            <div class="form-group">
                                <label for="image" class="col-sm-2 control-label">图片</label>
                                <div class="col-sm-10">
                                    <img id="image" src="..${activity.path}" style="height:200px" />
                                </div>
                            </div>
                        </c:if>

                        <div class="form-group">
                            <label for="introduction" class="col-sm-2 control-label">内容</label>
                            <div class="col-sm-10">
                                <textarea id="introduction" class="form-control" name="introduction" rows="13" style="text-align: left" readonly="readonly">${activity.introduction}</textarea>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="time" class="col-sm-2 control-label">时间</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="time" name="time" value="${activity.time}" readonly="readonly">
                            </div>
                        </div>
                    </form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>