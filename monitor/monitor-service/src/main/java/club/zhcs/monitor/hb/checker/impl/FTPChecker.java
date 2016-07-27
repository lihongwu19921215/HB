package club.zhcs.monitor.hb.checker.impl;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;

import club.zhcs.monitor.domain.record.MonitorRecord;
import club.zhcs.monitor.domain.resource.FtpServer;
import club.zhcs.monitor.domain.resource.FtpServer.Type;
import club.zhcs.monitor.domain.resource.Resource;
import club.zhcs.monitor.hb.checker.Checker;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * 
 * @author 王贵源
 *
 * @email kerbores@kerbores.com
 *
 * @description ftp检测器
 * 
 * @copyright copyright©2016 zhcs.club
 *
 * @createTime 2016年7月27日 上午9:39:49
 */
@IocBean
public class FTPChecker implements Checker {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * club.zhcs.monitor.hb.checker.Checker#check(club.zhcs.monitor.domain.resource
	 * .Resource)
	 */
	@Override
	public MonitorRecord check(Resource resource) {
		FtpServer server = null;
		if (resource instanceof FtpServer) {
			server = (FtpServer) resource;
		} else {
			throw Lang.makeThrow("resource is not instanceof %s", FtpServer.class.getName());
		}

		if (server.getType() == Type.FTP) {
			return checkFTP(server.getServer(), server.getPort(), server.getUser(), server.getPassword());
		}
		return checkSFTP(server.getServer(), server.getPort(), server.getUser(), server.getPassword());
	}

	private MonitorRecord checkSFTP(String server, int port, String user, String password) {
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
			return null;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
		return null;
	}

	private MonitorRecord checkFTP(String server, int port, String user, String password) {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(server, port);
			ftpClient.login(user, password);
		} catch (IOException e) {
			log.error(e);
			return null;
		}
		return null;
	}
}
