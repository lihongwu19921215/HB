package club.zhcs.monitor.hb.checker;

import org.nutz.log.Log;
import org.nutz.log.Logs;

import club.zhcs.monitor.domain.resource.Resource;

/**
 * 
 * @author 王贵源
 *
 * @email kerbores@kerbores.com
 *
 * @description 心跳检测器
 * 
 * @copyright copyright©2016 zhcs.club
 *
 * @createTime 2016年7月27日 上午9:37:01
 */
public interface Checker<T extends Resource> {

	Log log = Logs.get();

	public void check(T resource);
}
