package com.jianeye.cuckoo3.webside.region.model;

import java.util.Date;

import com.jianeye.cuckoo3.webside.base.basemodel.BaseEntity;
import com.jianeye.cuckoo3.webside.role.model.RoleEntity;

/**
 * 
 * Region.java
 * @author 张孟志
 * @date 2016年7月1日
 * @caption 纳税人区域
 */
public class Region extends BaseEntity {

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_region.region_name
     *
     * @mbggenerated Mon Jun 20 09:28:57 CST 2016
     */
    private String regionName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_region.delete_status
     *
     * @mbggenerated Mon Jun 20 09:28:57 CST 2016
     * 
     * 页面使用checkbox空间的话，不会将未选择的值传到服务器，因此需要默认
     */
    private Integer deleteStatus = 0;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_region.locked
     *
     * @mbggenerated Mon Jun 20 09:28:57 CST 2016
     * 
     * 页面使用checkbox空间的话，不会将未选择的值传到服务器，因此需要默认
     */
    private Integer locked = 0;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_region.description
     *
     * @mbggenerated Mon Jun 20 09:28:57 CST 2016
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_region.creator_id
     *
     * @mbggenerated Mon Jun 20 09:28:57 CST 2016
     */
    private Integer creatorId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_region.create_time
     *
     * @mbggenerated Mon Jun 20 09:28:57 CST 2016
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tb_region.update_time
     *
     * @mbggenerated Mon Jun 20 09:28:57 CST 2016
     */
    private Date updateTime;
    
	/*
	 * 所属角色
	 */
	private RoleEntity role;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_region.region_name
     *
     * @return the value of tb_region.region_name
     *
     * @mbggenerated Mon Jun 20 09:28:57 CST 2016
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_region.region_name
     *
     * @param regionName the value for tb_region.region_name
     *
     * @mbggenerated Mon Jun 20 09:28:57 CST 2016
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_region.delete_status
     *
     * @return the value of tb_region.delete_status
     *
     * @mbggenerated Mon Jun 20 09:28:57 CST 2016
     */
    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_region.delete_status
     *
     * @param deleteStatus the value for tb_region.delete_status
     *
     * @mbggenerated Mon Jun 20 09:28:57 CST 2016
     */
    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_region.locked
     *
     * @return the value of tb_region.locked
     *
     * @mbggenerated Mon Jun 20 09:28:57 CST 2016
     */
    public Integer getLocked() {
        return locked;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_region.locked
     *
     * @param locked the value for tb_region.locked
     *
     * @mbggenerated Mon Jun 20 09:28:57 CST 2016
     */
    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_region.description
     *
     * @return the value of tb_region.description
     *
     * @mbggenerated Mon Jun 20 09:28:57 CST 2016
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_region.description
     *
     * @param description the value for tb_region.description
     *
     * @mbggenerated Mon Jun 20 09:28:57 CST 2016
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_region.creator_id
     *
     * @return the value of tb_region.creator_id
     *
     * @mbggenerated Mon Jun 20 09:28:57 CST 2016
     */
    public Integer getCreatorId() {
        return creatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_region.creator_id
     *
     * @param creatorId the value for tb_region.creator_id
     *
     * @mbggenerated Mon Jun 20 09:28:57 CST 2016
     */
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_region.create_time
     *
     * @return the value of tb_region.create_time
     *
     * @mbggenerated Mon Jun 20 09:28:57 CST 2016
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_region.create_time
     *
     * @param createTime the value for tb_region.create_time
     *
     * @mbggenerated Mon Jun 20 09:28:57 CST 2016
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_region.update_time
     *
     * @return the value of tb_region.update_time
     *
     * @mbggenerated Mon Jun 20 09:28:57 CST 2016
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_region.update_time
     *
     * @param updateTime the value for tb_region.update_time
     *
     * @mbggenerated Mon Jun 20 09:28:57 CST 2016
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}
}