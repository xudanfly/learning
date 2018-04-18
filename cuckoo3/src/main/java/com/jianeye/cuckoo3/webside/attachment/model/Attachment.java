package com.jianeye.cuckoo3.webside.attachment.model;

import java.util.Date;

import com.jianeye.cuckoo3.webside.base.basemodel.BaseEntity;

public class Attachment  extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

    private String type;

    private Long size;

    private String path;

    private String url;

    private String fkTable;

    private String fkIdName;

    private Long fkId;

    private String status;

    private Integer creatorId;

    private String creatorName;

    private Date createtime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFkTable() {
        return fkTable;
    }

    public void setFkTable(String fkTable) {
        this.fkTable = fkTable;
    }
    
    public String getFkIdName() {
		return fkIdName;
	}

	public void setFkIdName(String fkIdName) {
		this.fkIdName = fkIdName;
	}

    public Long getFkId() {
        return fkId;
    }

    public void setFkId(Long fkId) {
        this.fkId = fkId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
    
    @Override
	public String toString() {
		return "Attachment [id=" + id + ", name=" + name + ", type=" + type 
				+ ", size=" + size + ", path=" + path 
				+ ", url=" + url + ", fkTable=" + fkTable 
				+ ", fkIdName=" + fkIdName + ", fkId=" + fkId +",status="+status
				+ ", creatorId=" + creatorId + ", creatorName=" + creatorName + ", createtime=" + createtime 
				+ "]";
	}

}