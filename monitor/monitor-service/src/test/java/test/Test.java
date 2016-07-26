package test;

import java.io.File;

import org.nutz.http.Header;
import org.nutz.http.Http;
import org.nutz.http.Response;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;

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
 * @createTime 2016年7月20日 上午9:38:55
 */
public class Test {
	public static void main(String[] args) {

		NutMap paras = NutMap.NEW();
		paras.put("img", new File("C:\\Users\\Kerbores\\Desktop\\a99c106djw8evmby70d9fj216o16otaw.jpg"));
		Response response = Http.upload("http://121.40.97.230:8089/image", paras, Header.create(), 10000);
		if (response.isOK()) {
			System.err.println(org.nutz.json.Json.toJson(Lang.map(response.getContent())));
			System.err.println(response.getContent());
		} else {
			System.err.println(response.getStatus());
		}
	}
}
