package ir.etkastores.app.Models.profile;

import com.google.gson.annotations.SerializedName;

public class RegisterUserRequestModel{

	@SerializedName("Email")
	private String email;

	@SerializedName("FirstName")
	private String firstName;

	@SerializedName("CellPhone")
	private String cellPhone;

	@SerializedName("LastName")
	private String lastName;

	@SerializedName("Gender")
	private String gender;

	@SerializedName("Password")
	private String password;

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setCellPhone(String cellPhone){
		this.cellPhone = cellPhone;
	}

	public String getCellPhone(){
		return cellPhone;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

}