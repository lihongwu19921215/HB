package club.zhcs.monitor.service.record;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.lang.Lang;

import club.zhcs.monitor.domain.record.FtpServerMonitroRecord;
import club.zhcs.titans.utils.biz.BaseService;

import com.google.common.collect.Lists;

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
	public Long[] getConnectionTimes(int id) {
		Sql sql = create("get.ftp.monitor.record.duration.by.type");
		sql.params().set("type", FtpServerMonitroRecord.CheckType.CONNECTION);
		sql.vars().set("id", id);
		sql.setCallback(new SqlCallback() {

			@Override
			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
				List<Long> target = Lists.newArrayList();
				while (rs.next()) {
					target.add(rs.getLong(1));
				}
				return target;
			}
		});
		dao().execute(sql);

		return Lang.collection2array(sql.getList(Long.class));
	}

	/**
	 * @param id
	 * @return
	 */
	public Long[] getDownloadTimes(int id) {
		Sql sql = create("get.ftp.monitor.record.duration.by.type");
		sql.params().set("type", FtpServerMonitroRecord.CheckType.DOWNLOAD);
		sql.vars().set("id", id);
		sql.setCallback(new SqlCallback() {

			@Override
			public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
				List<Long> target = Lists.newArrayList();
				while (rs.next()) {
					target.add(rs.getLong(1));
				}
				return target;
			}
		});
		dao().execute(sql);

		return Lang.collection2array(sql.getList(Long.class));
	}

}
