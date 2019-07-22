package cn.abc.sshweb1.model;

import java.util.Set;

public class Role {
	
	private int id;
	private String roleName;
	private Integer  state;  //1:Õý³££¬ 0:½ûÖ¹
	private Set<Permission> permissions;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Set<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + ", state=" + state + ", permissions=" + permissions + "]";
	}
	public Role() {
		super();
	}
	
}
