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
                	<li class="active"><a href="../product/index">展品管理</a></li>
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
						<li><a href="../product/index">查看展品</a></li>
                    	<li class="active"><a href="../product/add">添加展品</a></li>
                        <li><a href="../product/uploadExcel">excel导入</a></li>
					</ul>
				</div>
			</div>

			<div class="col-md-9" role="main">
				<div id="add">
					<br />
					<h2>添加展品</h2>
					<br />
					<form  class="form-horizontal" role="form" action="addCheck" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="type"  class="col-sm-2 control-label">类型</label>
                            <div class="col-sm-10 form-inline">
                                <select class="form-control" id="type" name="type">
                                    <option value="1">发动机</option>
                                    <option value="2">航电</option>
                                    <option value="3">机电</option>
                                    <option value="4">民机</option>
                                    <option value="5">通飞</option>
                                    <option value="6">直升机</option>
                                    <option value="7">装备</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
							<label for="name"  class="col-sm-2 control-label">展商</label>
    						<div class="col-sm-10 form-inline">
								<input type="text" style="width:200px" class="form-control" id="keyword" name="keyword" placeholder="搜索关键字">
								<select class="form-control" style="width:250px" id="bid" name="bid">
								</select>
								<button type="button" id="search" class="btn btn-default">查找</button>
							</div> 
						</div>
						
						<div class="form-group">
							<label for="name"  class="col-sm-2 control-label">产品名</label>
    						<div class="col-sm-10">
								<input type="text" class="form-control" id="name" name="name"
								placeholder="输入产品名字">
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
								<textarea id="introduction" class="form-control" name="introduction" rows="10" style="text-align: left"></textarea>
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
	
	<script type="text/javascript">
		$(function(){
			$('#search').click(function(){
				var keyword = $('#keyword').val();
				if(keyword==""){
					alert("请输入关键字!");
				}else{
					$.post("../businessman/search",{keyword:keyword},function(result){
						var optionStr = "";
						for(var item in result){
							optionStr = optionStr + '<option value="'+result[item].id+'">'+result[item].name+'</option>';
						}
						console.log(optionStr);
						$('#bid').html(optionStr);
					});
					
				}
			});
		});
	</script>
</body>
</html>