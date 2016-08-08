package club.zhcs.monitor.domain.record;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project monitor-domain
 *
 * @file ApplicationMonitorRecord.java
 *
 * @description //TODO description
 *
 * @time 2016年7月7日 下午7:25:45
 *
 */
@Table("t_app_record_${id}")
@Comment("应用监测记录")
public class ApplicationMonitorRecord extends MonitorRecord {

	@Column("r_code")
	@Comment("响应状态吗")
	private int code;

	@Column("r_time_escape")
	@Comment("请求耗时")
	private long timeEscape;

	@Column("r_error_msg")
	@Comment("错误信息")
	private String errorMsg;

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the timeEscape
	 */
	public long getTimeEscape() {
		return timeEscape;
	}

	/**
	 * @param timeEscape
	 *            the timeEscape to set
	 */
	public void setTimeEscape(long timeEscape) {
		this.timeEscape = timeEscape;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg
	 *            the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
