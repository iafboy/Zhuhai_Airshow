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
	<header class="navbar navbar-inverse navbar-fixed-top bs-docs-nav" role="banner">
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
			<nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
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
					<br />
					<h2>展商信息</h2>
					<br />

					<form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="pavilion_id" class="col-sm-2 control-label">展馆</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="pavilion_id" name="pavilion_id" value="${businessman.pavilion_id}" readonly="readonly">
                            </div>
                        </div>

						<div class="form-group">
							<label for="name" class="col-sm-2 control-label">姓名</label>
    						<div class="col-sm-10">
								<input type="text" class="form-control" id="name" name="name" value="${businessman.name}" readonly="readonly">
                        	</div>
                        </div>
                        
						<div class="form-group">   
                           <label for="website" class="col-sm-2 control-label">网站</label>
    						<div class="col-sm-10">
	    						<input type="text" class="form-control" id="website" name="website" value="${businessman.website}" readonly="readonly">
							</div>
						</div>
											
						<div class="form-group">	
							<label for="phone" class="col-sm-2 control-label">电话</label> 
    						<div class="col-sm-10">
	    						<input type="text" class="form-control" id="phone" name="phone" value="${businessman.phone}" readonly="readonly">
    						</div>
    					</div>
    					                    
						<div class="form-group">   
                        	<label for="email" class="col-sm-2 control-label">email</label> 
    						<div class="col-sm-10">
	    						<input type="text" class="form-control" id="email" name="email" value="${businessman.email}" readonly="readonly">
    						</div>
    					</div>                    
						
						<div class="form-group">
                        	<label for="address" class="col-sm-2 control-label">住址</label> 
    						<div class="col-sm-10">
    							<input type="text" class="form-control" id="address" name="address" value="${businessman.address}" readonly="readonly">
							</div>
						</div>                        
						
						<div class="form-group">
                       		<label for="fax" class="col-sm-2 control-label">传真</label> 
    						<div class="col-sm-10">	
								<input type="text" class="form-control" id="fax" name="fax" value="${businessman.fax}" readonly="readonly">
							</div>
						</div>                        
                        
						<div class="form-group">
                        	<label for="weixin" class="col-sm-2 control-label">微信</label> 
    						<div class="col-sm-10">
	    						<input type="text" class="form-control" id="weixin" name="weixin" value="${businessman.weixin}" readonly="readonly">
    						</div>
    					</div>
    					                    
						<div class="form-group">
                        	<label for="weibo" class="col-sm-2 control-label">微博</label> 
    						<div class="col-sm-10">
								<input type="text" class="form-control" id="weibo" name="weibo" value="${businessman.weibo}" readonly="readonly">
                        	</div>
                        </div>

                        <div class="form-group">
                            <label for="logo" class="col-sm-2 control-label">logo</label> <br />
                            <div class="col-sm-10">
                                <img id="logo" src="..${businessman.logo}" style="height:50px" />
                            </div>
                        </div>
                        
						<div class="form-group">
                        	<label for="image" class="col-sm-2 control-label">图片</label> <br />
    						<div class="col-sm-10">
    							<img id="image" src="..${businessman.path}" style="height:200px" />
                        	</div>
                        </div>
                         
						<div class="form-group">  
                        	<label for="introduction" class="col-sm-2 control-label">介绍</label> 
    						<div class="col-sm-10">
    							<textarea id="introduction" class="form-control" name="introduction" readonly="readonly" style="text-align: left;height:200px">${businessman.introduction}</textarea>
                			</div>
                		</div>
                	</form>
                    <!--
					<h3>展品信息</h3>
					<div class="col-sm-offset-1  col-sm-11">
						<table class="table table-bordered table-hover" >
		                    <thead>
		                    <tr>
		                        <th style="width:50px">id</th>
		                        <th>名字</th>
		                        <th style="width:300px">操作</th>
		                    </tr>
		                    </thead>
		                    <tbody id="user_list">
							<c:forEach items="${productList}" var="product">
		                        <tr>
		                            <td>${product.id}</td>
		                            <td>${product.name}</td>
		                            <td> <button onclick="deleteProduct(${product.id})" class="btn btn-danger" type="button">删除</button></td>
		                        </tr>
		                        <tr>
		                        	<td colspan="2">
	                          			<img src="..${product.path}" style="height:200px" />
	                          		</td>
		                            <td colspan="2">${product.introduction}</td>
		                        </tr>
		                    </c:forEach>
	                    	</tbody>
	                	</table>
	                	
	                	<h3><a href="../businessman/addProduct?id=${businessman.id}">添加展品</a></h3>
                	</div>
                	<script>
			            function deleteProduct(id) {
			            	var choice = confirm("确认删除");
			            	if(choice == true){
			                	window.location.href = "deleteProduct?id=" + id;
			                }
			            }
		            </script>
		            -->
				</div>
			</div>
		</div>
	</div>

</body>
</html>