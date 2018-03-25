package ir.etkastores.app.models.profile;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordRequestModel {

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

	public void setConfirmPassword(String confirmPassword){
		this.confirmPassword = confirmPassword;
	}

	public String getConfirmPassword(){
		return confirmPassword;
	}

	public void setCurrentPassword(String currentPassword){
		this.currentPassword = currentPassword;
	}

	public String getCurrentPassword(){
		return currentPassword;
	}

}