package bulletinboard.beans;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	//カラム名
	private int id; //
	private String account; //ログインID
	private String password; //password
	private String name; //名称（ユーザー名称）
	private String branchID;
	private String departmentID;
	private boolean stopped;
	private Date insertDate;
	private Date updateDate;

	//getter,setter
	public int getId(){
		return id;
	}

	public void setId(int id){
		this.id = id;
	}

	public String getAccount(){
		return account;
	}

	public void setAccount(String account){
		this.account = account;
	}

	public String getPassword(){
		return password;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getBranchID(){
		return branchID;
	}

	public void setBranchID(String branchID){
		this.branchID = branchID;
	}

	public String getDepartmentID(){
		return departmentID;
	}

	public void setDepartmentID(String departmentID){
		this.departmentID = departmentID;
	}

	public Boolean getStopped(){
		return stopped;
	}

	public void setStopped(Boolean stopped){
		this.stopped = stopped;
	}


	public Date getInsertDate(){
		return insertDate;
	}

	public void setInsertDate(Date insertDate){
		this.insertDate = insertDate;
	}


	public Date getUpdateDate(){
		return updateDate;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}
}
