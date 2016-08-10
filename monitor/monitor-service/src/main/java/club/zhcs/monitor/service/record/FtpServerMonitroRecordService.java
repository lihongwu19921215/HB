package club.zhcs.monitor.service.record;

import java.util.List;

import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;

import club.zhcs.monitor.domain.record.FtpServerMonitroRecord;
import club.zhcs.titans.utils.biz.BaseService;

/**
 * 
 * @author 王贵源
 *
 * @email kerbores@kerbores.com
 *
 * @description
 * 
 * @copyright copyright©2016 zhcs.club
 *
 * @createTime 2016年8月8日 下午3:20:27
 */
public class FtpServerMonitroRecordService extends BaseService<FtpServerMonitroRecord> {

	/**
	 * @param id
	 * @return
	 */
	public List<Record> getConnectionTimes(int id) {
		Sql sql = create("get.ftp.monitor.record.duration.by.type");
		sql.params().set("type", FtpServerMonitroRecord.CheckType.CONNECTION);
		sql.vars().set("id", id);
		return search(sql);
	}

	/**
	 * @param id
	 * @return
	 */
	public List<Record> getDownloadTimes(int id) {
		Sql sql = create("get.ftp.monitor.record.duration.by.type");
		sql.params().set("type", FtpServerMonitroRecord.CheckType.DOWNLOAD);
		sql.vars().set("id", id);
		return search(sql);
	}

}
