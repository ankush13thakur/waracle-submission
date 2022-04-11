package com.waracle.cake.entity;

import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "cake")
@EntityListeners(AuditingEntityListener.class)
public class Cake {
	
	private Integer id;
	
	@NotEmpty(message = "Name cannot be empty")
	private String name;

	@NotEmpty(message = "Type cannot be empty")
	private String type;
	
	@CreatedDate
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	
	@CreatedBy
	@Column(name = "created_by")
	private String createdBy;
	
	@LastModifiedDate
    @Column(name = "updated_date")
	private LocalDateTime updatedDate;
	
	@LastModifiedBy
    @Column(name = "updated_by")
	private String updatedBy;

	public Cake() {
		super();
	}

	public Cake(@NotEmpty(message = "Name cannot be empty") String name,
			@NotEmpty(message = "Type cannot be empty") String type) {
		super();
		this.name = name;
		this.type = type;
	}

	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Basic
    @Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
    @Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JsonFormat(pattern="yyyy-MM-dd hh:MM:ss a")
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@JsonFormat(pattern="yyyy-MM-dd hh:MM:ss a")
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
}
