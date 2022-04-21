package Front;

import Back.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class EditUser {
    @FXML
    private TextField userName;
    @FXML
    private TextField Fname;
    @FXML
    private TextField Lname;
    @FXML
    private TextField Email;
    @FXML
    private TextField phone;
    @FXML
    private TextField address;
    @FXML
    private PasswordField pass;
    @FXML
    private PasswordField confirmPass;
    @FXML
    private Label error;
    App app;
    public void init(App app) {
    	this.app = app;
    }
    public void ef(ActionEvent e) {
    	if(Fname.getText().equals("")) {
    		error.setText("Enter a value first");
    		return;
    	}app.updateFname(Fname.getText());
    	error.setText("First name changed successfully");
    }
    public void el(ActionEvent e) {
    	if(Lname.getText().equals("")) {
    		error.setText("Enter a value first");
    		return;
    	}app.updateLname(Lname.getText());
    	error.setText("Last name changed successfully");
    }
    public void ee(ActionEvent e) {
    	if(Email.getText().equals("")) {
    		error.setText("Enter a value first");
    		return;
    	}boolean check = app.updateEmail(Email.getText());
    	if(check) {
    		error.setText("Email changed successfully");
    	}else {
    		error.setText("Email already taken");
    	}
    }
    public void ep(ActionEvent e) {
    	if(phone.getText().equals("")) {
    		error.setText("Enter a value first");
    		return;
    	}app.updatePhone(phone.getText());
    	error.setText("Phone changed successfully");
    }
    public void ea(ActionEvent e) {
    	if(address.getText().equals("")) {
    		error.setText("Enter a value first");
    		return;
    	}
    	app.updateAddress(address.getText());
    	error.setText("Address changed successfully");
    }
    public void epass(ActionEvent e) {
    	if(pass.getText().equals("")) {
    		error.setText("Enter a password first");
    		return;
    	}if(!pass.getText().equals(confirmPass.getText())) {
    		error.setText("Passwords don't match");
    		return;
    	}app.updatePassword(pass.getText());
    	error.setText("Password changed successfully");
    }
    public void eu(ActionEvent e) {
    	if(userName.getText().equals("")) {
    		error.setText("Enter a value first");
    		return;
    	}boolean check = app.updateUsername(userName.getText());
    	if(check) {
    		error.setText("Username changed successfully");
    	}else {
    		error.setText("Username already taken");
    	}
    }
}
