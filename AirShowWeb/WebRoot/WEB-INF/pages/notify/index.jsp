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
                    <li><a href="../activity/index">档案管理</a></li>
                    <li><a href="../image/index">图片管理</a></li>
                	<li class="active"><a href="../notify/index">消息发送</a></li>
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
	                    <li class="active"><a href="index">发送消息</a></li>
	                    <li><a href="history">消息历史</a></li>
					</ul>
				</div>
			</div>

			<div class="col-md-9" role="main">
				<div id="add">
                	<br/>
					<h2>发送消息</h2>
					<br />
					<form role="form" action="send" method="post">
						<div class="form-group">
                            <label for="content">消息类型</label>
                            <select id="type" name="type"  class="form-control">
                                <option value="1">展会通告</option>
                                <option value="2">会展服务</option>
                                <option value="3">新闻通稿</option>
                                <option value="4">普通通知</option>
                            </select>


                            <label for="content">消息内容</label> 
							<textarea id="content" class="form-control" name="content" rows="8" ></textarea>
							
							<br />
                            <div style="text-align: center">
                                <button type="submit" class="btn btn-default">发送</button>
                            </div>
                        </div>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>