package com.work.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
/**
 * 
 * ClassName: Achat 
 * @Description: 用户发布请求实体
 * @author crossoverJie
 * @date 2016年4月16日 下午10:13:56
 */
@Entity
public class Achat {
	private int id ;
	private String title ;
	private String content ;
	private String create_user ;
	private String create_username ;//发起人名字
	
	private Date create_date ;
	private String category_id ;
	
	
	private String category_name ;
	private String parseDate ;
	
	/**
	 * 状态 0：管理员处理中      1：供应商处理中
	 * 2：会员处理中                  3：供应商上架中   
	 */
	private String state ;
	
	@Id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@Transient
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	
	@Transient
	public String getParseDate() {
		return parseDate;
	}
	public void setParseDate(String parseDate) {
		this.parseDate = parseDate;
	}
	@Transient
	public String getCreate_username() {
		return create_username;
	}
	public void setCreate_username(String create_username) {
		this.create_username = create_username;
	}
}