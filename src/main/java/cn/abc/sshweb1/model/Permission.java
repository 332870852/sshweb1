package cn.abc.sshweb1.model;

/**
 * 权限
 * @author 何旭杰
 *
 */
public class Permission {
	
	private int id;
	/**
	 * 资源
	 */
	private String resource;
	
	private Integer state;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Permission [id=" + id + ", resource=" + resource + ", state=" + state + "]";
	}

	public Permission() {
		super();
	}
	
	
}
