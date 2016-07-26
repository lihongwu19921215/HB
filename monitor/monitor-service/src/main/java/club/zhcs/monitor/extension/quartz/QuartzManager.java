package club.zhcs.monitor.extension.quartz;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;

import club.zhcs.monitor.domain.resource.FtpServer;

/**
 * 
 * @author 王贵源
 *
 * @email kerbores@kerbores.com
 *
 * @description quartz任务管理器
 * 
 * @copyright copyright©2016 zhcs.club
 *
 * @createTime 2016年7月26日 下午12:39:07
 */
@IocBean
public class QuartzManager {

	@Inject
	Scheduler scheduler;

	/**
	 * 添加一个定时任务
	 * 
	 * @param jobName
	 *            任务名称
	 * @param jobGroup
	 *            任务分组
	 * @param triggerName
	 *            触发器名称
	 * @param triggerGroup
	 *            触发器分组
	 * @param job
	 *            job 类
	 * @param cron
	 *            cron表达式
	 * @param data
	 *            job数据
	 * 
	 * @throws SchedulerException
	 */
	public void addJob(String jobName, String jobGroup, String triggerName, String triggerGroup, Class<? extends Job> job, String cron, JobDataMap data) throws SchedulerException {

		JobDetail jobDetail = JobBuilder.newJob(job).withIdentity(jobName, jobGroup).usingJobData(data).build();

		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroup).withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();

		scheduler.scheduleJob(jobDetail, trigger);
		// 启动
		if (!scheduler.isShutdown())
			scheduler.start();
	}

	/**
	 * 添加FTP检查任务
	 * 
	 * @param server
	 *            ftp服务器
	 * @throws SchedulerException
	 */
	public void addFtpCheckJob(FtpServer server) throws SchedulerException {
		// TODO 固定频率而非cron设置的
		JobDataMap data = new JobDataMap();
		data.put("ftpServer", server);
		addJob(server.getUuid(), server.getOwnerType() + "_" + server.getOwnerId(), "FTPCHECKTRIGGER", "FTPCHECKTRIGGER" + server.getOwnerId(), FTPMonitorJob.class,
				server.getTaskCron(), data);
	}

	// /**
	// * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	// *
	// * @param jobName
	// * @param time
	// * @throws SchedulerException
	// * @throws ParseException
	// */
	// public static void modifyJobTime(String jobName, String time) throws
	// SchedulerException, ParseException {
	// Scheduler sched = sf.getScheduler();
	// Trigger trigger = sched.getTrigger(jobName, TRIGGER_GROUP_NAME);
	// if (trigger != null) {
	// CronTrigger ct = (CronTrigger) trigger;
	// ct.setCronExpression(time);
	// sched.resumeTrigger(jobName, TRIGGER_GROUP_NAME);
	// }
	// }
	//
	// /** */
	// /**
	// * 修改一个任务的触发时间
	// *
	// * @param triggerName
	// * @param triggerGroupName
	// * @param time
	// * @throws SchedulerException
	// * @throws ParseException
	// */
	// public static void modifyJobTime(String triggerName, String
	// triggerGroupName, String time) throws SchedulerException, ParseException
	// {
	// Scheduler sched = sf.getScheduler();
	// Trigger trigger = sched.getTrigger(triggerName, triggerGroupName);
	// if (trigger != null) {
	// CronTrigger ct = (CronTrigger) trigger;
	// // 修改时间
	// ct.setCronExpression(time);
	// // 重启触发器
	// sched.resumeTrigger(triggerName, triggerGroupName);
	// }
	// }
	//
	// /** */
	// /**
	// * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
	// *
	// * @param jobName
	// * @throws SchedulerException
	// */
	// public static void removeJob(String jobName) throws SchedulerException {
	// Scheduler sched = sf.getScheduler();
	// sched.pauseTrigger(jobName, TRIGGER_GROUP_NAME);// 停止触发器
	// sched.unscheduleJob(jobName, TRIGGER_GROUP_NAME);// 移除触发器
	// sched.deleteJob(jobName, JOB_GROUP_NAME);// 删除任务
	// }
	//
	// /** */
	// /**
	// * 移除一个任务
	// *
	// * @param jobName
	// * @param jobGroupName
	// * @param triggerName
	// * @param triggerGroupName
	// * @throws SchedulerException
	// */
	// public static void removeJob(String jobName, String jobGroupName, String
	// triggerName, String triggerGroupName) throws SchedulerException {
	// Scheduler sched = sf.getScheduler();
	// sched.pauseTrigger(triggerName, triggerGroupName);// 停止触发器
	// sched.unscheduleJob(triggerName, triggerGroupName);// 移除触发器
	// sched.deleteJob(jobName, jobGroupName);// 删除任务
	// }
}
