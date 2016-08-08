package test;

import org.junit.Test;

import club.zhcs.monitor.domain.resource.FtpServer;
import club.zhcs.monitor.hb.checker.impl.FTPConnectionChecker;
import club.zhcs.monitor.service.resource.FTPServerService;

/**
 * 
 * @author 王贵源
 *
 * @email kerbores@kerbores.com
 *
 * @description //TODO description
 * 
 * @copyright copyright©2016 zhcs.club
 *
 * @createTime 2016年8月8日 下午1:37:17
 */
public class A extends Base {

	@Test
	public void checkFtp() {
		FTPConnectionChecker ftpChecker = ioc.get(FTPConnectionChecker.class);
		FTPServerService ftpServerService = ioc.get(FTPServerService.class);
		FtpServer server = ftpServerService.fetch(2);
		ftpChecker.check(server);
	}
}
