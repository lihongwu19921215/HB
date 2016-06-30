package club.zhcs.monitor.domain.resource;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.lang.random.R;

import club.zhcs.titans.utils.db.po.Entity;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project monitor-domain
 *
 * @file Resource.java
 *
 * @description 资源
 *
 * @time 2016年6月30日 下午8:24:10
 *
 */
public class Resource extends Entity {

	@Column("r_resource_type")
	@Comment("资源类型")
	private ResourceType resourceType = ResourceType.Application;

	@Column("r_uuid")
	@Comment("资源uuid,避免使用数字id可被猜解")
	private String uuid = R.UU16();

	@Column("r_owner_id")
	@Comment("资源主id")
	private int ownerId;

	@Column("r_owner_type")
	@Comment("资源主类型")
	private OwnerType ownerType = OwnerType.PRIVATE;

	/**
	 * @return the ownerId
	 */
	public int getOwnerId() {
		return ownerId;
	}

	/**
	 * @param ownerId
	 *            the ownerId to set
	 */
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * @return the ownerType
	 */
	public OwnerType getOwnerType() {
		return ownerType;
	}

	/**
	 * @param ownerType
	 *            the ownerType to set
	 */
	public void setOwnerType(OwnerType ownerType) {
		this.ownerType = ownerType;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the resourceType
	 */
	public ResourceType getResourceType() {
		return resourceType;
	}

	/**
	 * @param resourceType
	 *            the resourceType to set
	 */
	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	/**
	 * 
	 * @author Kerbores(kerbores@gmail.com)
	 *
	 * @project monitor-domain
	 *
	 * @file Resource.java
	 *
	 * @description 资源类型
	 *
	 * @time 2016年6月30日 下午8:25:05
	 *
	 */
	public static enum ResourceType {
		Application("应用程序"), FTP("FTP资源"), DATABASE("数据库");
		private String name;

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @param name
		 */
		private ResourceType(String name) {
			this.name = name;
		}

	}

	public static enum OwnerType {
		TEAM("团队"), PRIVATE("私人");
		private String name;

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @param name
		 */
		private OwnerType(String name) {
			this.name = name;
		}

	}
}
