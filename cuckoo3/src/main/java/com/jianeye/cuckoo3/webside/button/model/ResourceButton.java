package com.jianeye.cuckoo3.webside.button.model;

import java.util.Date;

import com.jianeye.cuckoo3.webside.base.basemodel.BaseEntity;

/**
 * 
 * ResourceButton.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 按钮资源
 */
public class ResourceButton extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String element;
	
	private String description;
	
	private Integer deleteStatus;
	 
	private Date createTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(Integer deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "ResourceButton [id=" + id + "name=" + name + ", element=" + element
				+ ", description=" + description + ", deleteStatus="
				+ deleteStatus + ", createTime=" + createTime + "]";
	}
	
	
	
} 
