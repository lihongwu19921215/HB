package club.zhcs.monitor.module;

import java.io.IOException;
import java.util.List;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.lang.Streams;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import club.zhcs.titans.nutz.module.base.AbstractBaseModule;
import club.zhcs.titans.qiniu.QiNiuUploader;
import club.zhcs.titans.utils.db.Result;

import com.google.common.collect.Lists;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project app
 *
 * @file FileUploadModule.java
 *
 * @description 文件上传下载
 *
 * @time 2016年3月24日 下午1:46:46
 *
 */
@Filters
public class FileModule extends AbstractBaseModule {

	@Inject
	QiNiuUploader uploader;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kerbores.nutz.module.base.AbstractBaseModule#_getNameSpace()
	 */
	@Override
	public String _getNameSpace() {
		return null;
	}

	/**
	 * ajax 文件上传
	 * 
	 * @param files
	 * @return
	 * @throws IOException
	 */
	@At
	@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
	public Result upload(@Param("files") TempFile[] files) throws IOException {
		List<NutMap> data = Lists.newArrayList();
		for (TempFile tempFile : files) {
			NutMap temp = NutMap.NEW();
			String key = uploader.upload(Streams.readBytes(tempFile.getInputStream()));
			temp.put("key", key);
			temp.put("url", uploader.privateUrl(key));
			temp.put("name", tempFile.getName());
			temp.put("localName", tempFile.getSubmittedFileName());
			temp.put("size", tempFile.getSize());
			temp.put("type", tempFile.getContentType());
			data.add(temp);
		}
		return Result.success().addData("data", data);
	}

	@At("/kind/upload")
	@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
	public NutMap upload(TempFile imgFile) throws IOException {
		String key = uploader.upload(Streams.readBytes(imgFile.getInputStream()));
		if (Strings.isBlank(key)) {
			return NutMap.NEW().addv("error", 1).addv("message", "上传失败");
		}
		return NutMap.NEW().addv("error", 0).addv("url", uploader.privateUrl(key));
	}

}
