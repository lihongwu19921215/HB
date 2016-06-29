package club.zhcs.monitor.extension.beetl;

import org.beetl.core.Context;
import org.beetl.core.Function;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project biz
 *
 * @file StringToEunm.java
 *
 * @description // TODO write some description of this class
 *
 * @time 2016年3月29日 上午9:33:21
 *
 */
public class AlarmTypeFun implements Function {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.beetl.core.Function#call(java.lang.Object[],
	 * org.beetl.core.Context)
	 */
	@Override
	public Object call(Object[] paras, Context ctx) {

		Object types = paras[0];

		Object check = paras[1];

		if (types == null) {
			return false;
		}

		if (check == null) {
			return true;
		}

		return types.toString().indexOf(check.toString()) >= 0;
	}

}
