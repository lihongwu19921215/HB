package club.zhcs.monitor.domain.record;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;

import club.zhcs.monitor.domain.resource.Resource.ResourceType;

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
 * @createTime 2016年8月5日 下午2:37:32
 */
@Table("t_ftp_record_${id}")
@Comment("FTP监测记录")
public class FtpServerMonitroRecord extends MonitorRecord {

	@Column("r_check_type")
	@Comment("检查类型")
	private CheckType checkType;

	/**
	 * @return the checkType
	 */
	public CheckType getCheckType() {
		return checkType;
	}

	/**
	 * @param checkType
	 *            the checkType to set
	 */
	public void setCheckType(CheckType checkType) {
		this.checkType = checkType;
	}

	public static enum CheckType {
		CONNECTION("连接"), DOWNLOAD("下载");
		private String name;

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @param name
		 */
		private CheckType(String name) {
			this.name = name;
		}

	}

	{
		setType(ResourceType.FTP);
	}

}
