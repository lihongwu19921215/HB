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
 * @createTime 2016年8月5日 下午2:37:01
 */
@Table("t_db_record_${id}")
@Comment("数据库监测记录")
public class DataBaseMonitorRecode extends MonitorRecord {

}
