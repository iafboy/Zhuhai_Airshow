<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
                    <li><a href="../activity/index">活动管理</a></li>
                    <li class="active"><a href="../activity/index">档案管理</a></li>
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
                        <li><a href="../activity/index">查看活动</a></li>
                        <li><a href="../activity/add">添加活动</a></li>
                        <li><a href="../activity/indexPeople">查看活动人员</a></li>
                        <li class="active"><a href="../activity/uploadExcelPeople">导入活动人员</a></li>
                        <li><a href="../activity/indexPeopleImage">查看活动图片</a></li>
                        <li><a href="../activity/uploadExcelPeopleImage">导入活动图片信息</a></li>
					</ul>
				</div>
			</div>

			<div class="col-md-9" role="main">
				<div id="add">
					<br />
					<h2>上传Excel</h2>
					<br />
					<form class="form-horizontal" role="form" action="uploadExcelPeopleCheck" method="post" enctype="multipart/form-data">
						
                        
                        <div class="form-group">                        
                            <label for="file" class="col-sm-2 control-label">上传Excel</label>
    						<div class="col-sm-10"> 
                            	<input type="file" class="form-control" name="file" id="file">
							</div>
                        </div>
                        
                        
                        <div style="text-align: center">
                            <button type="submit" class="btn btn-default">添加</button>
                        </div>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>