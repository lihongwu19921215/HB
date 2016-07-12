<%@page import="club.zhcs.monitor.Application"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title><%=Application.NAME%></title>

<meta name="description" content="User login page" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<link href="${base}/resources/images/LOGO.png" rel="shortcut icon">
<!-- bootstrap & fontawesome -->
<link rel="stylesheet" href="${base}/resources/ace/css/bootstrap.css" />
<link rel="stylesheet" href="${base}/resources/ace/css/font-awesome.css" />

<!-- text fonts -->
<link rel="stylesheet" href="${base}/resources/ace/css/ace-fonts.css" />

<!-- ace styles -->
<link rel="stylesheet" href="${base}/resources/ace/css/ace.css" />

<!--[if lte IE 9]>
			<link rel="stylesheet" href="${base}/resources/ace/css/ace-part2.css" />
		<![endif]-->
<link rel="stylesheet" href="${base}/resources/ace/css/ace-rtl.css" />
<script type="text/javascript">
	var contextPath = '${base}';
</script>
<!--[if lte IE 9]>
		  <link rel="stylesheet" href="${base}/resources/ace/css/ace-ie.css" />
		<![endif]-->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

<!--[if lt IE 9]>
		<script src="${base}/resources/ace/js/html5shiv.js"></script>
		<script src="${base}/resources/ace/js/respond.js"></script>
		<![endif]-->
<style type="text/css">
.light-login {
	background: url(${base}/resources/images/background.png);
	filter: "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale')";
	-moz-background-size: 100% 100%;
	background-size: 100% 100%;
}
</style>
</head>

<body class="login-layout light-login">
	<div class="main-container">
		<div class="main-content">
			<div class="row">
				<div class="col-sm-10 col-sm-offset-1">
					<div class="login-container">
						<div class="center">
							<h1>
								<i class="ace-icon fa fa-heartbeat red"></i> <span style="font-weight: 900"><%=Application.NAME%></span>
							</h1>
						</div>

						<div class="space-6"></div>

						<div class="position-relative">
							<div id="login-box" class="login-box visible widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">
										<h4 class="header blue lighter bigger">
											<i class="ace-icon fa fa-coffee green"></i> 系统登录
										</h4>

										<div class="space-6"></div>

										<form id="loginForm">
											<fieldset>
												<label class="block clearfix"> <span class="block input-icon input-icon-right"> <input type="text" class="form-control" placeholder="请输入用户名"
														name="userName" id="user" value="${loginInfo.user }" data-type="required" data-error="请输入用户名" /> <i class="ace-icon fa fa-user"></i>
												</span>
												</label> <label class="block clearfix"> <span class="block input-icon input-icon-right"> <input type="password" class="form-control" placeholder="请输入密码"
														id="password" name="password" value="${loginInfo.password }" data-type="required" data-error="请输入密码" /> <i class="ace-icon fa fa-lock"></i>
												</span>
												</label> <label class="block clearfix">
													<div class="input-group">
														<input type="text" id="captcha" class="form-control search-query" placeholder="请输入验证码" name="captcha" data-type="required" data-error="请输入验证码" /> <span
															class="input-group-btn"> <a href="javascript:;" class="captchaRefresh"> <img  title="点击刷新验证码" src="${base}/captcha" height="34px">
														</a>
														</span>
													</div>
												</label>

												<div class="space"></div>

												<div class="clearfix">
													<label class="inline"> <input type="checkbox" class="ace" name="checkBox" id="rememberMeCheckBox" ${loginInfo.rememberMe ? 'checked' : '' } /> <span
														class="lbl"> 记住我</span>
													</label>

													<button type="button" id="loginSubmit" class="width-35 pull-right btn btn-sm btn-primary" onclick="doLogin(this)">
														<i class="ace-icon fa fa-key"></i> <span class="bigger-110">登录</span>
													</button>
												</div>

												<div class="space-4"></div>
											</fieldset>
										</form>
									</div>
									<!-- /.widget-main -->
									<div class="toolbar clearfix">
										<div class="col-md-12">
											<%=Application.COPYRIGHT%>
										</div>
									</div>
								</div>
								<!-- /.widget-body -->
							</div>
							<!-- /.login-box -->
							<div id="forgot-box" class="forgot-box widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">
										<h4 class="header red lighter bigger">
											<i class="ace-icon fa fa-key"></i> 找回密码
										</h4>

										<div class="space-6"></div>
										<p>请输入你的注册邮箱地址</p>

										<form>
											<fieldset>
												<label class="block clearfix"> <span class="block input-icon input-icon-right"> <input type="email" class="form-control" placeholder="请输入你的注册邮箱地址">
														<i class="ace-icon fa fa-envelope"></i>
												</span>
												</label>

												<div class="clearfix">
													<button type="button" class="width-35 pull-right btn btn-sm btn-danger">
														<i class="ace-icon fa fa-lightbulb-o"></i> <span class="bigger-110">发送邮件</span>
													</button>
												</div>
											</fieldset>
										</form>
									</div>
									<!-- /.widget-main -->
									<div class="center">
										<h5 class="pink" id="id-company-text"><%=Application.COPYRIGHT%></h5>
									</div>
									<div class="toolbar center">
										<a href="#" data-target="#login-box" class="back-to-login-link"> 返回登录 <i class="ace-icon fa fa-arrow-right"></i>
										</a>
									</div>
								</div>
								<!-- /.widget-body -->
							</div>

							<div id="signup-box" class="signup-box widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">
										<h4 class="header green lighter bigger">
											<i class="ace-icon fa fa-users blue"></i> 新用户注册
										</h4>

										<div class="space-6"></div>

										<form>
											<fieldset>
												<label class="block clearfix"> <span class="block input-icon input-icon-right"> <input type="email" class="form-control" placeholder="请输入电子邮箱"> <i
														class="ace-icon fa fa-envelope"></i>
												</span>
												</label> <label class="block clearfix"> <span class="block input-icon input-icon-right"> <input type="text" class="form-control" placeholder="请输入用户名"> <i
														class="ace-icon fa fa-user"></i>
												</span>
												</label> <label class="block clearfix"> <span class="block input-icon input-icon-right"> <input type="password" class="form-control" placeholder="请输入密码">
														<i class="ace-icon fa fa-lock"></i>
												</span>
												</label> <label class="block clearfix"> <span class="block input-icon input-icon-right"> <input type="password" class="form-control" placeholder="请再次输入密码">
														<i class="ace-icon fa fa-retweet"></i>
												</span>
												</label> <label class="block"> <input type="checkbox" class="ace"> <span class="lbl"> 我接收 <a href="#">用户协议</a>
												</span>
												</label>

												<div class="space-24"></div>

												<div class="clearfix">
													<button type="reset" class="width-30 pull-left btn btn-sm">
														<i class="ace-icon fa fa-refresh"></i> <span class="bigger-110">重置</span>
													</button>

													<button type="button" class="width-65 pull-right btn btn-sm btn-success">
														<span class="bigger-110">注册</span> <i class="ace-icon fa fa-arrow-right icon-on-right"></i>
													</button>
												</div>
											</fieldset>
										</form>
									</div>
									<div class="center">
										<h5 class="pink" id="id-company-text"><%=Application.COPYRIGHT%></h5>
									</div>
									<div class="toolbar center">
										<a href="#" data-target="#login-box" class="back-to-login-link"> <i class="ace-icon fa fa-arrow-left"></i> 返回登录
										</a>
									</div>
								</div>
								<!-- /.widget-body -->
							</div>
						</div>
						<!-- /.position-relative -->

						<!-- div class="navbar-fixed-top align-right">
							<br /> &nbsp; <a id="btn-login-dark" href="#">Dark</a> &nbsp; <span class="blue">/</span> &nbsp; <a id="btn-login-blur" href="#">Blur</a> &nbsp; <span class="blue">/</span>
							&nbsp; <a id="btn-login-light" href="#">Light</a> &nbsp; &nbsp; &nbsp;
						</div-->
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /.main-content -->
	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->

	<!--[if !IE]> -->
	<script type="text/javascript">
		window.jQuery || document.write("<script src='${base}/resources/ace/js/jquery.js'>" + "<"+"/script>");
	</script>
	<script src="${base}/resources/layer/layer.js"></script>
	<script src="${base}/resources/layer/extend/layer.ext.js"></script>

	<!-- <![endif]-->
	<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='${base}/resources/ace/js/jquery1x.js'>"+"<"+"/script>");
</script>
<![endif]-->
	<script type="text/javascript">
		if ('ontouchstart' in document.documentElement)
			document.write("<script src='${base}/resources/ace/js/jquery.mobile.custom.js'>" + "<"+"/script>");
	</script>

	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		jQuery(function($) {
			$('.login-container').css('margin-top', (window.screen.height - $('.login-container').height() - 100) / 2 + 'px')
			$(document).on('click', '.toolbar a[data-target]', function(e) {
				e.preventDefault();
				var target = $(this).data('target');
				$('.widget-box.visible').removeClass('visible');//hide others
				$(target).addClass('visible');//show target
			});

			//开发阶段免输
			$("#user").val("admin");
			$("#password").val("123456");
		});

		$('.captchaRefresh').on('click', function() {
			$(this).find('img').attr('src', getRootPath() + "/captcha?" + Math.random());
		})
		//回车提交
		$('body').on('keydown', function() {
			if (event.keyCode == 13) {
				$('#loginSubmit').click();
			}
		})

		//you don't need this, just used for changing background
		jQuery(function($) {
			$('#btn-login-dark').on('click', function(e) {
				$('body').attr('class', 'login-layout');
				$('#id-text2').attr('class', 'white');
				$('#id-company-text').attr('class', 'blue');

				e.preventDefault();
			});
			$('#btn-login-light').on('click', function(e) {
				$('body').attr('class', 'login-layout light-login');
				$('#id-text2').attr('class', 'grey');
				$('#id-company-text').attr('class', 'blue');

				e.preventDefault();
			});
			$('#btn-login-blur').on('click', function(e) {
				$('body').attr('class', 'login-layout blur-login');
				$('#id-text2').attr('class', 'white');
				$('#id-company-text').attr('class', 'light-blue');

				e.preventDefault();
			});
		});
		function doLogin(dom) {
			if ($('input').validation()) {
				$.post(getRootPath() + '/system/login', {
					user : $('#user').val(),
					password : $('#password').val(),
					captcha : $('#captcha').val(),
					rememberMe : $('#rememberMeCheckBox')[0].checked
				}, function(result) {
					if (result.operationState == 'SUCCESS') {
						//TODO 跳转到系统主界面
						location.href = '${base}/system/main'
					} else {
						$('.captchaRefresh').find('img').attr('src', getRootPath() + "/captcha?" + Math.random());
						validationFail(result.data.reason, dom);
					}
				}, 'json');
			}
		}
	</script>
	<script type="text/javascript" src="${base}/resources/ace/js/jquery.js"></script>
	<script src='${base}/resources/js/common/common.js'></script>
	<script src="${base}/resources/js/common/validation.js"></script>
</body>
</html>