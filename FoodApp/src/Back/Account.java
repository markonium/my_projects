package Back;

import Database.*;

public abstract class Account {
private String username,Password,Email;
private int level;
protected DBUser db = new DBUser();
protected DBMenuOrder dbmo = new DBMenuOrder();
protected DBOrderTaker dbot = new DBOrderTaker();

Account(String userName,String password,String email,int lvl){
	username=userName;level=lvl;Password=password;Email=email;
}

Account(){}

/**
 * Change current user username's
 * @param name the new username
 * @return true if changed successfully, false otherwise
 */
public boolean setUsername(String name) {
	DBUser userSession=new DBUser();
	// if new username already exists;
	if(userSession.checkUser(name))
		return false;
	
	db.setUsername(name, this.username);
	this.username = name;	
	return true;
}
/**
 * Change user's password
 * @param oldPassword old password
 * @param newPassword new password
 * @return true if password changed successfully, false if old password is not correct or old password is the same as new password
 */
public boolean setPassword(String oldPassword, String newPassword) {
	//Old password is wrong
	if(!Password.equals(oldPassword)){
		return false;
	}
	//New password the same as the old password
	if(newPassword.equals(Password)) {
		return false;
	}
	db.setPassword(newPassword, this.username);
	this.Password = newPassword;
	return true;
}
/**
 * Change user's email
 * @param email the new email
 * @return true if changed, false if email already taken
 */
public boolean setEmail(String email) {
	if(db.setEmail(email, this.username)) {
		this.Email = email;
		return true;
	}
	return false;
}

/**
 * Getter for username
 * @return user's username
 */
public String getUsername() {
	return username;
}
/**
 * Getter for email
 * @return user's email
 */
public String getEmail() {
	return Email;
}
/**
 * Getter for password
 * @return user's password
 */
public String getPassword() {
	return Password;
}
/**
 * Getter for level
 * @return user's level
 */
public int getLevel() {
	return level;
}
}
