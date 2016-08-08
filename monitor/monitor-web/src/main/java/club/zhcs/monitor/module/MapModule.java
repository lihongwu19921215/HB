package club.zhcs.monitor.module;

import org.nutz.http.Http;
import org.nutz.http.Response;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import club.zhcs.monitor.Application;
import club.zhcs.titans.nutz.module.base.AbstractBaseModule;
import club.zhcs.titans.utils.db.Result;

/**
 * @author 王贵源
 * @description 地理信息相关
 * @Copyright 内部代码,禁止转发
 * @date 2015年12月29日 下午10:42:52
 *
 */
@At("map")
public class MapModule extends AbstractBaseModule {

	@Override
	public String _getNameSpace() {
		return "map";
	}

	@At("/report")
	@Filters
	public Result report(@Param("longitude") double longitude, @Param("latitude") double latitude) {

		return Result.success();
	}

	@At("/show")
	@Filters
	@Ok("beetl:pages/map/show.html")
	public Result show(int mid) {
		String url = String.format("http://api.map.baidu.com/location/ip?ak=%s&ip=%s&coor=bd09ll", Application.BAIDUMAPKEY, _ip());
		Response response = Http.get(url);
		if (response.isOK() && Lang.map(response.getContent()).getInt("status") == 0) {
			NutMap data = Lang.map(response.getContent());
			return Result.success().addData("ak", Application.BAIDUMAPKEY).addData("lng", data.getAs("content", NutMap.class).getAs("point", NutMap.class).get("x"))
					.addData("lat", data.getAs("content", NutMap.class).getAs("point", NutMap.class).get("y"));
		}
		return Result.success().addData("ak", Application.BAIDUMAPKEY).addData("lng", 106.582656).addData("lat", 29.530382);
	}
}