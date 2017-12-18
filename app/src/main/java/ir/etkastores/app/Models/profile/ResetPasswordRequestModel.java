package ir.etkastores.app.Models.profile;

import com.google.gson.annotations.SerializedName;

public class ResetPasswordRequestModel{

	@SerializedName("NewPassword")
	private String newPassword;

	@SerializedName("UserId")
	private String userId;

	@SerializedName("ConfirmPassword")
	private String confirmPassword;

	@SerializedName("CurrentPassword")
	private String currentPassword;

	public void setNewPassword(String newPassword){
		this.newPassword = newPassword;
	}

	public String getNewPassword(){
		return newPassword;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setCOnfirmPassword(String cOnfirmPassword){
		this.confirmPassword = cOnfirmPassword;
	}

	public String getCOnfirmPassword(){
		return confirmPassword;
	}

	public void setCurrentPassword(String currentPassword){
		this.currentPassword = currentPassword;
	}

	public String getCurrentPassword(){
		return currentPassword;
	}

}