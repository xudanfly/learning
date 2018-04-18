package com.jianeye.cuckoo3.webside.financialProduct.model;

import java.util.Date;
import java.util.List;

import com.jianeye.cuckoo3.webside.attachment.model.Attachment;
import com.jianeye.cuckoo3.webside.base.basemodel.BaseEntity;

public class FinancialProduct extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**表名*/
	private static final String tableName = "tb_financial_product";
	/**表ID名*/
	private static final String idName = "f_id";

    private String title;

    private String subtitle;

    private String summary;

    private Integer deleteStatus;

    private String groupName;

    private Integer creatorId;

    private String creatorName;

    private Date createTime;

    private Date updateTime;

    private String content;
    
    private List<Attachment> attachments;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(Integer deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static String getTablename() {
		return tableName;
	}
	
	public static String getIdname() {
		return idName;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	
	@Override
	public String toString() {
		return "FinancialProduct [id=" + id + ", title=" + title + ", subtitle=" + subtitle 
				+ ", status=" + deleteStatus + ", summary=" + summary 
				+ ", content=" + content + ", groupName=" + groupName 
				+ ", creatorId=" + creatorId + ", creatorName=" + creatorName + ", createTime=" + createTime 
				+ ", updateTime=" + updateTime + ", attachments=" + attachments 
				+ "]";
	}
}