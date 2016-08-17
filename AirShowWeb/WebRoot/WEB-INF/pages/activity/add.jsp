<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台管理系统</title>
<link href="../Public/css/bootstrap.css" rel="stylesheet">
<link href="../Public/css/docs.css" rel="stylesheet">
<link type="text/css" href="../Public/css/jquery-ui.css" rel="stylesheet" />

<link href="../Public/css/jquery-ui-timepicker-addon.css" type="text/css" />
<link href="../Public/css/demos.css" rel="stylesheet" type="text/css" />

<script src="../Public/js/jquery.min.js"></script>
<script src="../Public/js/bootstrap.js"></script>


<script src="../Public/js/jquery-ui.min.js"></script>
<script src="../Public/js/jquery-ui-timepicker-addon.js" type="text/javascript"></script>

<script type="text/javascript">
    jQuery(function () {
        // 时间设置
        jQuery('#time').datetimepicker({
            timeFormat: "HH:mm:ss",
            dateFormat: "yy-mm-dd"
        });

    });
</script>
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
                        <li><a href="../activity/index">查看活动</a></li>
                        <li class="active"><a href="../activity/add">添加活动</a></li>
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
					<h2>添加活动</h2>
					<br />
					<form  class="form-horizontal" role="form" action="addCheck" method="post" enctype="multipart/form-data">

                        <div class="form-group">
							<label for="type"  class="col-sm-2 control-label">活动类型</label>
    						<div class="col-sm-10">
								<select name="type" id="type" class="form-control">
                                    <option value="1">媒体日</option>
                                    <option value="2">现场报道</option>
                                    <option value="3">活动排期</option>
                                </select>
							</div> 
						</div>
						
						<div class="form-group">
							<label for="title"  class="col-sm-2 control-label">标题</label>
    						<div class="col-sm-10">
								<input type="text" class="form-control" id="title" name="title"
								placeholder="输入标题">
							</div>
						</div>
                        
                        <div class="form-group">    
                            <label for="image" class="col-sm-2 control-label">上传图片</label>
    						<div class="col-sm-10"> 
                            	<input type="file" class="form-control" name="image" id="image">
                            </div>
                        </div>
                        
                        <div class="form-group">    
                            <label for="introduction" class="col-sm-2 control-label">内容</label>
    						<div class="col-sm-10">
								<textarea id="introduction" class="form-control" name="introduction" rows="13" style="text-align: left"></textarea>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="time" class="col-sm-2 control-label">时间</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="time" name="time" placeholder="输入时间">
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