package club.zhcs.monitor.domain.acl;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

import club.zhcs.titans.utils.db.po.Entity;

/**
 * 
 * 
 * @author 王贵源
 *
 * @email kerbores@kerbores.com
 *
 * @description 权限实体类
 * 
 * @copyright copyright©2016 zhcs.club
 *
 * @createTime 2016年6月29日 下午2:11:23
 */
@Table("t_permission")
@Comment("权限表")
public class Permission extends Entity {

	@Column("p_name")
	@Name
	@Comment("权限名称")
	private String name;

	@Column("p_url")
	@Comment("权限对应url")
	private String url;

	@Column("p_icon")
	@Comment("菜单icon")
	private String icon;

	@Column("p_desc")
	@Comment("描述")
	private String description;

	@Column("installed")
	@Comment("内置标识")
	private boolean installed;

	@Column("p_is_menu")
	@Comment("是否菜单标识")
	private boolean menu;

	@Column("p_need")
	@Comment("前置权限ID")
	private String needPermission;

	@Column("p_hilight_key")
	@Comment("菜单高亮关键字,如果是菜单的时候此字段建议填写")
	private String hilightKey;

	@Column("p_level")
	@Comment("权限级别,0为顶级菜单儿")
	private int level = 0;

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon
	 *            the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	public String getHilightKey() {
		return hilightKey;
	}

	public void setHilightKey(String hilightKey) {
		this.hilightKey = hilightKey;
	}

	public boolean isMenu() {
		return menu;
	}

	public void setMenu(boolean menu) {
		this.menu = menu;
	}

	public String getNeedPermission() {
		return needPermission;
	}

	public void setNeedPermission(String needPermission) {
		this.needPermission = needPermission;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the installed
	 */
	public boolean isInstalled() {
		return installed;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param installed
	 *            the installed to set
	 */
	public void setInstalled(boolean installed) {
		this.installed = installed;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}