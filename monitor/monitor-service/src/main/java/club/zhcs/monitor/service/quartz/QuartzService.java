package club.zhcs.monitor.service.quartz;

import java.util.List;

import org.nutz.dao.pager.Pager;
import org.nutz.integration.quartz.QuartzJob;
import org.nutz.integration.quartz.QuartzManager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.TriggerBuilder;

import club.zhcs.monitor.domain.resource.Application;
import club.zhcs.monitor.domain.resource.FtpServer;
import club.zhcs.monitor.domain.resource.Resource;
import club.zhcs.monitor.extension.quartz.ApplicationMonitorJob;
import club.zhcs.monitor.extension.quartz.DataBaseMonitorJob;
import club.zhcs.monitor.extension.quartz.FTPMonitorJob;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project monitor-service
 *
 * @file QuartzService.java
 *
 * @description Quartz
 *
 * @time 2016年8月1日 下午4:38:13
 *
 */
@IocBean
public class QuartzService {

	@Inject
	QuartzManager quartzManager;

	/**
	 * 添加任务
	 * 
	 * @param resource
	 */
	public void addResourceMonitorJob(Resource resource) {
		JobDataMap data = new JobDataMap();
		data.put("resource", resource);
		// 组装一个 quartzJob对象,代码稍显复杂
		QuartzJob job = new QuartzJob(JobKey.jobKey(resource.getName(), resource.getResourceType().getName()),
				TriggerBuilder.newTrigger().withIdentity(resource.getName(), resource.getResourceType().getName())
						.withSchedule(CronScheduleBuilder.cronSchedule(resource.getTaskCron())).build(),
				JobBuilder.newJob(resource instanceof FtpServer ? FTPMonitorJob.class
						: resource instanceof Application ? ApplicationMonitorJob.class : DataBaseMonitorJob.class)
						.withIdentity(resource.getName(), resource.getResourceType().getName())
						.usingJobData(data).build());
		quartzManager.add(job);
	}

	public List<QuartzJob> query(String namePatten, String groupPatten, Pager pager) {
		return quartzManager.query(namePatten, groupPatten, pager);
	}

	public boolean delete(JobKey jobKey) {
		return quartzManager.delete(jobKey);
	}

	public boolean exist(JobKey jobKey) {
		return quartzManager.exist(jobKey);
	}

	public void pause(JobKey jobKey) {
		quartzManager.pause(jobKey);
	}

	public void resume(JobKey jobKey) {
		quartzManager.resume(jobKey);
	}

	public void interrupt(JobKey jobKey) {
		quartzManager.interrupt(jobKey);
	}

	public void clear() {
		quartzManager.clear();
	}
}
