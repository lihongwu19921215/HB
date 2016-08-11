package test;

import org.junit.Test;
import org.nutz.lang.ContinueLoop;
import org.nutz.lang.Each;
import org.nutz.lang.ExitLoop;
import org.nutz.lang.Lang;
import org.nutz.lang.LoopException;

import club.zhcs.monitor.domain.resource.FtpServer;
import club.zhcs.monitor.hb.checker.impl.FTPServerChecker;
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
		FTPServerChecker ftpChecker = ioc.get(FTPServerChecker.class);
		FTPServerService ftpServerService = ioc.get(FTPServerService.class);

		for (int i = 0; i < 300; i++) {
			Lang.each(ftpServerService.queryAll(), new Each<FtpServer>() {

				@Override
				public void invoke(int index, FtpServer server, int length) throws ExitLoop, ContinueLoop, LoopException {
					ftpChecker.check(server);
				}
			});
		}
	}
}
