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
	                <li class="active"><a href="../ad/index">广告管理</a></li>
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
	                    <li class="active"><a href="../ad/index">查看广告</a></li>
	                    <li><a href="../ad/add">添加广告</a></li>
					</ul>
				</div>
			</div>

			<div class="col-md-9" role="main">
				<div id="add">
					<br />
					<h2>编辑广告</h2>
					<br />
					<form class="form-horizontal" role="form" action="editCheck?id=${ad.id}" method="post" enctype="multipart/form-data">
						
						<div class="form-group">	
							<label for="title"class="col-sm-2 control-label">标题</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="title" name="title"
									value="${ad.title}">
                            </div>
                        </div>
                        
                        <div class="form-group">    
                            <label for="introduction" class="col-sm-2 control-label">介绍</label> 
							<div class="col-sm-10">
								<textarea id="introduction" class="form-control" name="introduction" rows="5"  style="text-align: left">${ad.introduction}</textarea>
							</div>
						</div>
						
 						<div class="form-group" class="col-sm-2 control-label">		
							<label for="image"class="col-sm-2 control-label">上传图片</label> 
							<div class="col-sm-10">
                            	<input type="file" class="form-control" name="image" id="image">
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
	<script type="text/javascript">
		$(function(){
			$('#search').attr("disabled","disabled");
			$('#source').change(function(){
				if($('#source').val() == 3){
					$('#search').attr("disabled","disabled");
				}else{
					$('#search').removeAttr("disabled");
				}
			});
			$('#search').click(function(){
				var keyword = $('#keyword').val();
				if(keyword==""){
					alert("请输入关键字!");
				}else{
					if($('#source').val() == 2){
						$.post("../businessman/search",{keyword:keyword},function(result){
							var optionStr = "";
							for(var item in result){
								optionStr = optionStr + '<option value="'+result[item].id+'">'+result[item].name+'</option>';
							}
							console.log(optionStr);
							$('#tid').html(optionStr);
						});
					}
					
					if($('#source').val() == 1){
						$.post("../product/search",{keyword:keyword},function(result){
							var optionStr = "";
							for(var item in result){
								optionStr = optionStr + '<option value="'+result[item].id+'">'+result[item].name+'</option>';
							}
							$('#tid').html(optionStr);
						});
					}
				}
			});
		});
	</script>
</body>
</html>