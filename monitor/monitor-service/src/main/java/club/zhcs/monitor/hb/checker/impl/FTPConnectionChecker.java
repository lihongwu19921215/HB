package club.zhcs.monitor.hb.checker.impl;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.nutz.dao.TableName;
import org.nutz.filepool.NutFilePool;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Stopwatch;
import org.nutz.lang.Streams;

import club.zhcs.monitor.domain.record.FtpServerMonitroRecord;
import club.zhcs.monitor.domain.record.FtpServerMonitroRecord.CheckType;
import club.zhcs.monitor.domain.record.MonitorRecord;
import club.zhcs.monitor.domain.resource.FtpServer;
import club.zhcs.monitor.domain.resource.FtpServer.Type;
import club.zhcs.monitor.hb.checker.Checker;
import club.zhcs.monitor.service.record.FtpServerMonitroRecordService;

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
public class FTPConnectionChecker implements Checker<FtpServer> {

	@Inject
	private FtpServerMonitroRecordService ftpServerMonitroRecordService;

	@Inject
	private NutFilePool pool;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * club.zhcs.monitor.hb.checker.Checker#check(club.zhcs.monitor.domain.resource
	 * .Resource)
	 */
	@Override
	public synchronized void check(FtpServer resource) {
		FtpServer server = null;
		if (resource instanceof FtpServer) {
			server = resource;
		} else {
			throw Lang.makeThrow("resource is not instanceof %s", FtpServer.class.getName());
		}

		if (server.getType() == Type.FTP) {
			checkFTP(server);
		} else {
			checkSFTP(server.getServer(), server.getPort(), server.getUser(), server.getPassword());
		}
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

	private void checkFTP(FtpServer server) {
		TableName.set(server.getId());
		FtpServerMonitroRecord monitorRecord = new FtpServerMonitroRecord();
		monitorRecord.setResourceId(server.getId());
		monitorRecord.setCheckType(CheckType.CONNECTION);
		FTPClient ftpClient = new FTPClient();
		Stopwatch watch = Stopwatch.beginNano();
		try {
			ftpClient.connect(server.getServer(), server.getPort());
			ftpClient.login(server.getUser(), server.getPassword());
		} catch (IOException e) {
			log.error(e);
			monitorRecord.setOk(false);
			monitorRecord.setError(e.getMessage());
		} finally {
			watch.stop();
		}
		monitorRecord.setDuration(watch.getDuration());
		ftpServerMonitroRecordService.save(monitorRecord);

		if (monitorRecord.isOk()) {// 可连接,测试下载
			FtpServerMonitroRecord downloadMonitroRecord = new FtpServerMonitroRecord();
			downloadMonitroRecord.setResourceId(server.getId());
			downloadMonitroRecord.setCheckType(CheckType.DOWNLOAD);
			String path = server.getTestResourcePath().substring(0, server.getTestResourcePath().lastIndexOf("/"));
			String name = server.getTestResourcePath().substring(server.getTestResourcePath().lastIndexOf("/") + 1);
			String suffix = name.substring(0, name.lastIndexOf(".") + 1);
			Stopwatch downloadStopwatch = Stopwatch.beginNano();
			try {
				ftpClient.changeWorkingDirectory(path);
				if (ftpClient.retrieveFile(name, Streams.fileOut(pool.createFile(suffix)))) {

				} else {
					downloadMonitroRecord.setOk(false);
				}
			} catch (IOException e) {
				log.error(e);
				downloadMonitroRecord.setOk(false);
				downloadMonitroRecord.setError(e.getMessage());
			} finally {
				downloadStopwatch.stop();
			}
			downloadMonitroRecord.setDuration(downloadStopwatch.getDuration());
			ftpServerMonitroRecordService.save(downloadMonitroRecord);
		}
	}
}
