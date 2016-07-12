package club.zhcs.monitor.service.monitor;

import java.util.ArrayList;

import org.nutz.repo.cache.simple.LRUCache;

import club.zhcs.monitor.service.monitor.MonitorDataCache.FixedLengthList;

/**
 * 
 * @author 王贵源
 *
 * @email kerbores@kerbores.com
 *
 * @description 监控数据缓存
 * 
 * @copyright copyright©2016 zhcs.club
 *
 * @createTime 2016年7月12日 下午2:35:06
 */
public class MonitorDataCache extends LRUCache<Integer, FixedLengthList> {

	/**
	 * 定长列表
	 * 
	 * @author 王贵源
	 *
	 * @email kerbores@kerbores.com
	 *
	 * @description 定长列表
	 * 
	 * @copyright copyright©2016 zhcs.club
	 *
	 * @createTime 2016年7月12日 下午2:43:06
	 */
	public static class FixedLengthList extends ArrayList {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private int length = 100;

		/**
		 * @return the length
		 */
		public int getLength() {
			return length;
		}

		/**
		 * @param length
		 *            the length to set
		 */
		public void setLength(int length) {
			this.length = length;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.ArrayList#add(java.lang.Object)
		 */
		@Override
		public boolean add(Object e) {
			if (size() <= length) {
				return super.add(e);
			}
			remove(0);
			return super.add(e);

		}
	}

	{
	}

	/**
	 * 
	 */
	public MonitorDataCache() {
		super(1000);
	}

	/**
	 * @param cacheSize
	 */
	public MonitorDataCache(int cacheSize) {
		super(cacheSize);
	}

}
