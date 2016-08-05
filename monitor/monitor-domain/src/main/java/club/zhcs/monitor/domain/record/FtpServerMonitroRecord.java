package club.zhcs.monitor.domain.record;

import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;

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

}
