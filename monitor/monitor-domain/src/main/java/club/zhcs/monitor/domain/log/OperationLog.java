package club.zhcs.monitor.domain.log;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.lang.Times;

import club.zhcs.titans.utils.db.po.Entity;

/**
 * 
 * 
 * @author 王贵源
 *
 * @email kerbores@kerbores.com
 *
 * @description 关键业务操作日志
 * 
 * @copyright copyright©2016 zhcs.club
 *
 * @createTime 2016年6月29日 下午2:16:12
 */
@Table("t_opt_log")
public class OperationLog extends Entity {

	@Column("opt_account")
	@Comment("操作人员账户")
	private String account;

	@Column("opt_ip")
	@Comment("操作人员 ip 地址")
	private String ip;

	@Column("opt_module")
	@Comment("操作功能模块")
	private String module;

	@Column("opt_action")
	@Comment("操作的具体功能")
	private String action;

	@Column("opt_description")
	@Comment("功能描述")
	private String description;

	@Column("opt_action_time")
	@Comment("操作时间")
	private Date actionTime = Times.now();

	@Column("opt_execution_time")
	@Comment("方法执行时间")
	private long operationTime;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getActionTime() {
		return actionTime;
	}

	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
	}

	public long getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(long operationTime) {
		this.operationTime = operationTime;
	}

}
