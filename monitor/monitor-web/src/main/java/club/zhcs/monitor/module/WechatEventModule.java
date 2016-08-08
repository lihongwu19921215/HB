package club.zhcs.monitor.module;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.weixin.bean.WxInMsg;
import org.nutz.weixin.bean.WxOutMsg;
import org.nutz.weixin.impl.AbstractWxHandler;
import org.nutz.weixin.repo.com.qq.weixin.mp.aes.WXBizMsgCrypt;
import org.nutz.weixin.spi.WxApi2;
import org.nutz.weixin.spi.WxHandler;
import org.nutz.weixin.util.Wxs;

import club.zhcs.titans.nutz.module.base.AbstractBaseModule;

/**
 * @author 王贵源
 * @description 绑定
 * @Copyright 内部代码,禁止转发
 * @date 2016年1月7日 下午9:17:12
 *
 */
@Filters
public class WechatEventModule extends AbstractBaseModule {

	@Inject("wxApi")
	WxApi2 api;

	@Inject
	PropertiesProxy config;

	{
		Wxs.enableDevMode();
	}

	/**
	 * 只注册我们关心的事件（视频，语音等消息暂时一概忽略）
	 */
	protected WxHandler wxHandler = new AbstractWxHandler() {

		@Override
		public WxOutMsg voice(WxInMsg msg) {
			return Wxs.respVoice(null, msg.getRecognition());
		};

		/**
		 * 微信服务号的验证
		 * 
		 * @see http://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login
		 */
		@Override
		public boolean check(String signature, String timestamp, String nonce, String key) {
			return Wxs.check(config.get("token"), signature, timestamp, nonce);
		}

		/*
		 * 什么都不做，免得点菜单时各种触发defaultMsg by SK.Loda
		 * 
		 * @see
		 * com.kerbores.wx.api.impl.AbstractWxHandler#eventClick(com.kerbores
		 * .wx.bean.WxInMsg)
		 */
		@Override
		public WxOutMsg eventClick(WxInMsg msg) {
			return defaultMsg(msg);
		}

		@Override
		public WxOutMsg eventView(WxInMsg msg) {
			return defaultMsg(msg);
		}

		/**
		 * 默认返回的消息，应该是一个帮助类的提示文本
		 */
		@Override
		public WxOutMsg defaultMsg(WxInMsg msg) {
			return Wxs.respText(null, "你想说: '" + msg.getContent() + "' 吗?");
		}

		/**
		 * 处理文字消息
		 */
		@Override
		public WxOutMsg text(WxInMsg msg) {
			return defaultMsg(msg);
		}

		/**
		 * 微信关注事件 1. 根据openid看看是谁,存入用户数据库 2. 首次关注，如有推荐人同时记录推荐人 3. 响应欢迎页面
		 */
		@Override
		public WxOutMsg eventSubscribe(WxInMsg msg) {
			return Wxs.respText(null, "欢迎关注!");
		};

		/**
		 * 取消关注事件
		 */
		@Override
		public WxOutMsg eventUnsubscribe(WxInMsg msg) {
			// 将客户状态设置为禁用
			return defaultMsg(msg);
		}

		@Override
		public WXBizMsgCrypt getMsgCrypt() {
			return null;
		}
	};

	/**
	 * 微信消息回调入口
	 * 
	 * @param key
	 * @param req
	 * @return
	 * @throws IOException
	 */
	@At({ "/wechat", "/wechat/?" })
	@Fail("http:200")
	public View msgIn(String key, HttpServletRequest req) throws IOException {
		return Wxs.handle(wxHandler, req, key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kerbores.nutz.module.base.AbstractBaseModule#_getNameSpace()
	 */
	@Override
	public String _getNameSpace() {
		return null;
	}

}