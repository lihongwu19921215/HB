import org.nutz.http.Http;
import org.nutz.http.Response;
import org.nutz.lang.ContinueLoop;
import org.nutz.lang.Each;
import org.nutz.lang.ExitLoop;
import org.nutz.lang.Lang;
import org.nutz.lang.LoopException;

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
 * @createTime 2016年7月6日 下午2:01:02
 */
public class A {
	public static void main(String[] args) {
		Response response = Http.get("http://www.baidu.com");
		Lang.each(response.getHeader().keys(), new Each<String>() {

			@Override
			public void invoke(int index, String ele, int length) throws ExitLoop, ContinueLoop, LoopException {
				System.err.println(ele + " -----------------------> " + response.getHeader().get(ele));
			}
		});

	}
}
