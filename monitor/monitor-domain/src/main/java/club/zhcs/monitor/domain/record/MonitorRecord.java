package club.zhcs.monitor.domain.record;

import java.util.Date;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.lang.Times;

import club.zhcs.monitor.domain.resource.Resource.ResourceType;
import club.zhcs.titans.utils.db.po.Entity;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project monitor-domain
 *
 * @file MonitorRecord.java
 *
 * @description //TODO description
 *
 * @time 2016年7月7日 下午7:26:34
 *
 */
public class MonitorRecord extends Entity {

	@Column("r_resource_type")
	@Comment("资源类型")
	private ResourceType type = ResourceType.Application;

	@Column("r_resource_id")
	@Comment("资源id")
	private int resourceId;

	@Column("r_monitor_time")
	@Comment("监测时间")
	private Date monitorTime = Times.now();

	@Column("r_status")
	@Comment("监测结果状态")
	private boolean ok = true;

	@Column("r_duration")
	@Comment("持续时间")
	private long duration;

	@Column("r_error")
	@Comment("错误信息")
	@ColDefine(width = 500)
	private String error;

	@Column("r_alarmed")
	@Comment("已告警")
	private boolean alarmed = false;

	/**
	 * @return the alarmed
	 */
	public boolean isAlarmed() {
		return alarmed;
	}

	/**
	 * @param alarmed
	 *            the alarmed to set
	 */
	public void setAlarmed(boolean alarmed) {
		this.alarmed = alarmed;
	}

	/**
	 * @return the type
	 */
	public ResourceType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(ResourceType type) {
		this.type = type;
	}

	/**
	 * @return the resourceId
	 */
	public int getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId
	 *            the resourceId to set
	 */
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * @return the monitorTime
	 */
	public Date getMonitorTime() {
		return monitorTime;
	}

	/**
	 * @param monitorTime
	 *            the monitorTime to set
	 */
	public void setMonitorTime(Date monitorTime) {
		this.monitorTime = monitorTime;
	}

	/**
	 * @return the ok
	 */
	public boolean isOk() {
		return ok;
	}

	/**
	 * @param ok
	 *            the ok to set
	 */
	public void setOk(boolean ok) {
		this.ok = ok;
	}

	/**
	 * @return the duration
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(long duration) {
		this.duration = duration;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error
	 *            the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

}
