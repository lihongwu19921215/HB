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
import club.zhcs.monitor.domain.resource.FtpServer;
import club.zhcs.monitor.domain.resource.FtpServer.Type;
import club.zhcs.monitor.hb.checker.Checker;
import club.zhcs.monitor.service.record.FtpServerMonitroRecordService;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor;

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
public class FTPServerChecker implements Checker<FtpServer> {

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
			checkSFTP(server);
		}
	}

	public FtpServerMonitroRecord checkSFTPConnection(FtpServer server) {
		TableName.set(server.getId());
		FtpServerMonitroRecord monitorRecord = new FtpServerMonitroRecord();
		monitorRecord.setResourceId(server.getId());
		monitorRecord.setCheckType(CheckType.CONNECTION);
		Session session = null;
		JSch jsch = new JSch();
		try {
			if (server.getPort() <= 0) {
				session = jsch.getSession(server.getUser(), server.getServer());
			} else {
				session = jsch.getSession(server.getUser(), server.getServer(), server.getPort());
			}

			if (session == null) {
				throw new Exception("session is null");
			}
			session.setPassword(server.getPassword());// 设置密码
			session.setConfig("StrictHostKeyChecking", "no");
			// 设置登陆超时时间
			session.connect(30000);
		} catch (JSchException e) {
			log.error(e);
			monitorRecord.setOk(false);
			monitorRecord.setError(e.getMessage());
		} catch (Exception e) {
			log.error(e);
			monitorRecord.setOk(false);
			monitorRecord.setError(e.getMessage());
		}
		return monitorRecord;
	}

	public FtpServerMonitroRecord checkSFTPDownload(FtpServer server) {
		TableName.set(server.getId());
		FtpServerMonitroRecord monitorRecord = new FtpServerMonitroRecord();
		monitorRecord.setResourceId(server.getId());
		monitorRecord.setCheckType(CheckType.DOWNLOAD);
		Session session = null;
		JSch jsch = new JSch();
		try {
			if (server.getPort() <= 0) {
				session = jsch.getSession(server.getUser(), server.getServer());
			} else {
				session = jsch.getSession(server.getUser(), server.getServer(), server.getPort());
			}

			if (session == null) {
				throw new Exception("session is null");
			}
			session.setPassword(server.getPassword());// 设置密码
			session.setConfig("StrictHostKeyChecking", "no");
			// 设置登陆超时时间
			session.connect(30000);
		} catch (JSchException e) {
			log.error(e);
			monitorRecord.setOk(false);
			monitorRecord.setError(e.getMessage());
		} catch (Exception e) {
			log.error(e);
			monitorRecord.setOk(false);
			monitorRecord.setError(e.getMessage());
		}
		if (!monitorRecord.isOk()) {
			return monitorRecord;
		}
		String path = server.getTestResourcePath().substring(0, server.getTestResourcePath().lastIndexOf("/"));
		String name = server.getTestResourcePath().substring(server.getTestResourcePath().lastIndexOf("/") + 1);
		String suffix = name.substring(0, name.lastIndexOf(".") + 1);
		try {
			ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
			channel.connect();
			channel.cd(path);
			channel.get(name, Streams.fileOut(pool.createFile(suffix)), new SftpProgressMonitor() {

				@Override
				public void init(int arg0, String arg1, String arg2, long arg3) {

				}

				@Override
				public void end() {

				}

				@Override
				public boolean count(long arg0) {
					return false;
				}
			});

		} catch (JSchException | SftpException e) {
			log.error(e);
			monitorRecord.setOk(false);
			monitorRecord.setError(e.getMessage());
		}
		return monitorRecord;
	}

	private void checkSFTP(FtpServer server) {
		Stopwatch watch = Stopwatch.beginNano();
		FtpServerMonitroRecord monitorRecord = checkSFTPConnection(server);
		watch.stop();
		monitorRecord.setDuration(watch.getDuration());
		ftpServerMonitroRecordService.save(monitorRecord);

		if (monitorRecord.isOk()) {// 可连接,测试下载
			Stopwatch downloadStopwatch = Stopwatch.beginNano();
			FtpServerMonitroRecord downloadMonitroRecord = checkSFTPDownload(server);
			downloadStopwatch.stop();
			downloadMonitroRecord.setDuration(downloadStopwatch.getDuration());
			ftpServerMonitroRecordService.save(downloadMonitroRecord);
		}
	}

	public FtpServerMonitroRecord checkFTPConnection(FtpServer server) {
		TableName.set(server.getId());
		FtpServerMonitroRecord monitorRecord = new FtpServerMonitroRecord();
		monitorRecord.setResourceId(server.getId());
		monitorRecord.setCheckType(CheckType.CONNECTION);
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(server.getServer(), server.getPort());
			ftpClient.login(server.getUser(), server.getPassword());
		} catch (IOException e) {
			log.error(e);
			monitorRecord.setOk(false);
			monitorRecord.setError(e.getMessage());
		}
		return monitorRecord;
	}

	public FtpServerMonitroRecord checkFTPDownload(FtpServer server) {
		TableName.set(server.getId());
		FtpServerMonitroRecord monitorRecord = new FtpServerMonitroRecord();
		monitorRecord.setResourceId(server.getId());
		monitorRecord.setCheckType(CheckType.DOWNLOAD);
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(server.getServer(), server.getPort());
			ftpClient.login(server.getUser(), server.getPassword());
		} catch (IOException e) {
			log.error(e);
			monitorRecord.setOk(false);
			monitorRecord.setError(e.getMessage());
		}
		if (!monitorRecord.isOk()) {
			return monitorRecord;
		}
		String path = server.getTestResourcePath().substring(0, server.getTestResourcePath().lastIndexOf("/"));
		String name = server.getTestResourcePath().substring(server.getTestResourcePath().lastIndexOf("/") + 1);
		String suffix = name.substring(0, name.lastIndexOf(".") + 1);
		try {
			ftpClient.changeWorkingDirectory(path);
			if (ftpClient.retrieveFile(name, Streams.fileOut(pool.createFile(suffix)))) {

			} else {
				monitorRecord.setOk(false);
			}
		} catch (IOException e) {
			log.error(e);
			monitorRecord.setOk(false);
			monitorRecord.setError(e.getMessage());
		}
		return monitorRecord;
	}

	private void checkFTP(FtpServer server) {
		Stopwatch watch = Stopwatch.beginNano();
		FtpServerMonitroRecord monitorRecord = checkFTPConnection(server);
		watch.stop();
		monitorRecord.setDuration(watch.getDuration());
		ftpServerMonitroRecordService.save(monitorRecord);

		if (monitorRecord.isOk()) {// 可连接,测试下载
			Stopwatch downloadStopwatch = Stopwatch.beginNano();
			FtpServerMonitroRecord downloadMonitroRecord = checkFTPDownload(server);
			downloadStopwatch.stop();
			downloadMonitroRecord.setDuration(downloadStopwatch.getDuration());
			ftpServerMonitroRecordService.save(downloadMonitroRecord);
		}
	}
}
