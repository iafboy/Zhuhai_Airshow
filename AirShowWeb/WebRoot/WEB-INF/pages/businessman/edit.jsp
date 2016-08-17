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
	                <li class="active"><a href="../businessman/index">展商管理</a></li>
                	<li><a href="../product/index">展品管理</a></li>
	                <li><a href="../news/index">新闻管理</a></li>
	                <li><a href="../ad/index">广告管理</a></li>
                	<li><a href="../activity/index">活动管理</a></li>
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
                        <li class="active"><a href="../businessman/index">查看展商</a></li>
                        <li><a href="../businessman/add">添加展商</a></li>
                        <li><a href="../businessman/uploadExcel">excel导入</a></li>
					</ul>
				</div>
			</div>

			<div class="col-md-9" role="main">
				<div id="add">
					<h1>编辑展商</h1>
					<br />
					<form class="form-horizontal" role="form" action="../businessman/editCheck?id=${user.id}" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="pavilion_id" class="col-sm-2 control-label">展馆</label>
                            <div class="col-sm-10">
                                <select id="pavilion_id" name="pavilion_id" class="form-control">
                                    <c:forEach items="${pavilionList}" var="pavilion">
                                        <option value="${pavilion.id}" <c:if test="${pavilion.id==user.pavilion_id}">selected="selected" </c:if>>${pavilion.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
							<label for="name" class="col-sm-2 control-label">姓名</label>
    						<div class="col-sm-10">
								<input type="text" class="form-control" id="name" name="name" value="${user.name}">
							</div>
                        </div>
                        
                        <div class="form-group">  
	                        <label for="website" class="col-sm-2 control-label">网站</label>
    						<div class="col-sm-10"> 
								<input type="text" class="form-control" id="website" name="website" value="${user.website}">
							</div>
                        </div>
                        
						<div class="form-group">		
							<label for="phone" class="col-sm-2 control-label">电话</label> 
    						<div class="col-sm-10">
								<input type="text" class="form-control" id="phone" name="phone"
								value="${user.phone}">
							</div>
                        </div>
                        
                        <div class="form-group">   
                            <label for="email" class="col-sm-2 control-label">email</label> 
    						<div class="col-sm-10">
								<input type="text" class="form-control" id="email" name="email"
								value="${user.email}">
							</div>
                        </div>
                        
                        <div class="form-group">   
                            <label for="address" class="col-sm-2 control-label">住址</label>
    						<div class="col-sm-10"> 
								<input type="text" class="form-control" id="address" name="address" value="${user.address}">
							</div>
                        </div>
                        
	                    <div class="form-group">  
	                        <label for="fax" class="col-sm-2 control-label">传真</label>
    						<div class="col-sm-10"> 
								<input type="text" class="form-control" id="fax" name="fax" value="${user.fax}">
							</div>
                        </div>
                        
                        <div class="form-group">
                        	<label for="weixin" class="col-sm-2 control-label">微信</label>
    						<div class="col-sm-10"> 
								<input type="text" class="form-control" id="weixin" name="weixin" value="${user.weixin}">
							</div>
                        </div>
                        
	                    <div class="form-group">  
	                        <label for="weibo" class="col-sm-2 control-label">微博</label>
    						<div class="col-sm-10"> 
								<input type="text" class="form-control" id="weibo" name="weibo" value="${user.weibo}">
							</div>
                        </div>

                        <div class="form-group">
                            <label for="logo" class="col-sm-2 control-label">上传logo</label>
                            <div class="col-sm-10">
                                <input type="file" class="form-control" name="logo" id="logo">
                            </div>
                        </div>

                        <div class="form-group">                        
                            <label for="image" class="col-sm-2 control-label">上传图片</label>
    						<div class="col-sm-10"> 
                            	<input type="file" class="form-control" name="image" id="image">
							</div>
                        </div>
                        
                        <div class="form-group"> 
                            <label for="introduction" class="col-sm-2 control-label">介绍</label>
    						<div class="col-sm-10"> 
								<textarea id="introduction" class="form-control" name="introduction" rows="8">${user.introduction}</textarea>
							</div>
                        </div>
                        
                        <div style="text-align: center">
                            <button type="submit" class="btn btn-default">修改</button>
                        </div>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>