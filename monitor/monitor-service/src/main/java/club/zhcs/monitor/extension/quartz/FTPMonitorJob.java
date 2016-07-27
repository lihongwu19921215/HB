package club.zhcs.monitor.extension.quartz;

import org.nutz.castor.Castors;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import club.zhcs.monitor.domain.record.MonitorRecord;
import club.zhcs.monitor.domain.resource.FtpServer;
import club.zhcs.monitor.hb.checker.impl.FTPChecker;

/**
 * 
 * @author 王贵源
 *
 * @email kerbores@kerbores.com
 *
 * @description FTP检测任务,此处只管数据的收集
 * 
 * @copyright copyright©2016 zhcs.club
 *
 * @createTime 2016年7月26日 上午9:13:54
 */
@IocBean
public class FTPMonitorJob implements Job {

	@Inject
	FTPChecker checker;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDetail detail = context.getJobDetail();
		JobDataMap data = detail.getJobDataMap();
		FtpServer server = Castors.me().castTo(data.get("ftpServer"), FtpServer.class);// 获取到ftp服务器的数据进行检测
		System.err.println(server);
		/**
		 * 1.检测可连接性<br>
		 * 2.检测资源可下载性<br>
		 * 3.记录数据<br>
		 */
		MonitorRecord record = checker.check(server);
		System.err.println(record);
	}
}
