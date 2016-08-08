package club.zhcs.monitor.chain;

import java.util.List;

import org.nutz.integration.shiro.NutShiroProcessor;
import org.nutz.lang.ContinueLoop;
import org.nutz.lang.Each;
import org.nutz.lang.ExitLoop;
import org.nutz.lang.Lang;
import org.nutz.lang.LoopException;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.ActionChain;
import org.nutz.mvc.ActionInfo;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Processor;
import org.nutz.mvc.impl.NutActionChain;
import org.nutz.mvc.impl.processor.ActionFiltersProcessor;

import club.zhcs.titans.nutz.chain.KerboresActionChainMaker;
import club.zhcs.titans.nutz.processor.KerboresFailProcessor;

/**
 * 
 * 
 * @author 王贵源
 *
 * @email kerbores@kerbores.com
 *
 * @description 项目动作链
 * 
 * @copyright copyright©2016 zhcs.club
 *
 * @createTime 2016年6月29日 下午3:26:50
 */
public class MonitorChainMaker extends KerboresActionChainMaker {

	private Log log = Logs.get();

	@Override
	public ActionChain eval(final NutConfig config, final ActionInfo ai) {
		List<Processor> list = normalList();

		addBefore(list, ActionFiltersProcessor.class, new NutShiroProcessor());

		Processor error = new KerboresFailProcessor();
		Lang.each(list, new Each<Processor>() {

			@Override
			public void invoke(int paramInt1, Processor processor, int paramInt2) throws ExitLoop, ContinueLoop, LoopException {
				try {
					processor.init(config, ai);
				} catch (Throwable e) {
					log.error(e);
				}
			}
		});
		try {
			error.init(config, ai);
		} catch (Throwable e) {
			log.error(e);
		}
		return new NutActionChain(list, error, ai);
	}
}
