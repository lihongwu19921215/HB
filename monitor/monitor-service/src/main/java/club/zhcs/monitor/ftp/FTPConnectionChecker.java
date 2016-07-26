package club.zhcs.monitor.ftp;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import club.zhcs.monitor.domain.resource.FtpServer.Type;
import club.zhcs.titans.utils.db.Result;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * 
 * @author 王贵源
 *
 * @email kerbores@kerbores.com
 *
 * @description FTP连接检查器
 * 
 * @copyright copyright©2016 zhcs.club
 *
 * @createTime 2016年7月26日 上午11:35:19
 */
@IocBean
public class FTPConnectionChecker {

	Log log = Logs.get();

	public Result check(Type type, String server, int port, String user, String password) {
		if (type == Type.FTP) {
			return checkFTP(server, port, user, password);
		}
		return checkSFTP(server, port, user, password);
	}

	/**
	 * @param server
	 * @param port
	 * @param user
	 * @param password
	 * @return
	 */
	private Result checkSFTP(String server, int port, String user, String password) {
		Session session = null;
		JSch jsch = new JSch();

		try {
			if (port <= 0) {
				session = jsch.getSession(user, server);
			} else {
				session = jsch.getSession(user, server, port);
			}

			if (session == null) {
				throw new Exception("session is null");
			}
			session.setPassword(password);// 设置密码
			session.setConfig("StrictHostKeyChecking", "no");
			// 设置登陆超时时间
			session.connect(30000);
		} catch (JSchException e) {
			log.error(e);
			return Result.fail("连接失败!");
		} catch (Exception e) {
			log.error(e);
			return Result.fail("连接失败!");
		}
		return Result.success();
	}

	/**
	 * @param server
	 * @param port
	 * @param user
	 * @param password
	 * @return
	 */
	private Result checkFTP(String server, int port, String user, String password) {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(server, port);
			ftpClient.login(user, password);
		} catch (IOException e) {
			log.error(e);
			return Result.fail("连接失败!");
		}
		return Result.success();
	}

}
