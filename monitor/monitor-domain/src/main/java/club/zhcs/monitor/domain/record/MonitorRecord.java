package club.zhcs.monitor.domain.record;

import java.util.Date;

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
	private boolean ok;

}
