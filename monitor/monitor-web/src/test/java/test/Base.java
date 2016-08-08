package test;

import org.junit.After;
import org.junit.Before;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.combo.ComboIocLoader;

/**
 * 
 * @author 王贵源
 *
 * @email kerbores@kerbores.com
 *
 * @description 测试
 * 
 * @copyright copyright©2016 zhcs.club
 *
 * @createTime 2016年8月8日 下午1:32:55
 */
public class Base {

	protected Ioc ioc;

	@Before
	public void init() {
		try {
			ioc = new NutIoc(new ComboIocLoader("*anno", "club.zhcs", "*tx", "*js", "ioc", "*async", "*quartz", "quartz"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@After
	public void destroy() {
		// 销毁IOC容器
		if (ioc != null) {
			ioc.depose();
		}
	}

}
