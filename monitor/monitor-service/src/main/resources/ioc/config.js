var ioc = {
	config : {
		type : "org.nutz.ioc.impl.PropertiesProxy",
		fields : {
			ignoreResourceNotFound : true,
			paths : [ 'conf','/var/config','C:\config' ],
			utf8 : false
		}
	}
}