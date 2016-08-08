package club.zhcs.monitor.domain.resource;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.http.Request.METHOD;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project monitor-domain
 *
 * @file Application.java
 *
 * @description 应用程序
 *
 * @time 2016年6月30日 下午8:31:02
 *
 */
@Table("t_application")
@Comment("应用程序")
public class Application extends Resource {

	@Column("a_url")
	@Comment("应用监测地址")
	private String url;

	@Column("a_request_method")
	@Comment("应用监测请求方法")
	private METHOD method;

	@Column("a_request_timeout")
	@Comment("应用监测超时时间")
	private long timeOut = 5000;

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the method
	 */
	public METHOD getMethod() {
		return method;
	}

	/**
	 * @param method
	 *            the method to set
	 */
	public void setMethod(METHOD method) {
		this.method = method;
	}

	/**
	 * @return the timeOut
	 */
	public long getTimeOut() {
		return timeOut;
	}

	/**
	 * @param timeOut
	 *            the timeOut to set
	 */
	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}

}
