package cn.abc.sshweb1.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class User {	
	private int id;
	private String username;
	private String password;
	private Integer state;	//0:禁用， 1：正常
	/**
	 * 用户注册时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd ",timezone="GMT+8")
	@DateTimeFormat(pattern="yyyy-MM-dd ")
	private Date regDate;	
	
	/**
	 * 用户角色
	 */
	private Set<Role> roles= new HashSet<>();
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public User() {
		super();
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", state=" + state
				+ ", regDate=" + regDate + ", roles=" + roles + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	
}
