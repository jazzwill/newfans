package com.r1123.fans.core.type;


import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 分类类型-type 分类的显示地方可能不一样 有些是商品的分類，有些是首頁的推广分类
 * 
 * @author hanming
 * 
 */
public class CategoryType implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Map<String, CategoryType> TYPES = new LinkedHashMap<String, CategoryType>();

	public static final CategoryType HOME = new CategoryType("HOME", "首页分类");
	public static final CategoryType BRAND = new CategoryType("BRAND", "品牌分类");
	public static final CategoryType ADVERT = new CategoryType("ADVERT", "广告分类");
	public static final CategoryType SLIDE = new CategoryType("SLIDE", "幻灯分类");

	public static final CategoryType RECOMMEND = new CategoryType("RECOMMEND", "推荐商品");
	public static final CategoryType CATEGORY = new CategoryType("CATEGORY", "商品分类");


	public static CategoryType getInstance(final String type) {
		return TYPES.get(type);
	}

	private String type;
	private String friendlyType;

	public CategoryType() {
		// do nothing
	}

	public CategoryType(final String type, final String friendlyType) {
		this.friendlyType = friendlyType;
		setType(type);
	}

	public String getType() {
		return type;
	}

	public String getFriendlyType() {
		return friendlyType;
	}

	private void setType(final String type) {
		this.type = type;
		if (!TYPES.containsKey(type)) {
			TYPES.put(type, this);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!getClass().isAssignableFrom(obj.getClass()))
			return false;
		CategoryType other = (CategoryType) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
