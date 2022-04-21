package fornt.front;

import Back.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Login {
    @FXML
    private TextField UserName;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirm;
    @FXML
    private TextField phone;
    @FXML
    private Label error;
    public void signUp(ActionEvent event) throws IOException {
        if(UserName.getText().equals("")||password.getText().equals("")||confirm.getText().equals("")||phone.getText().equals("")||email.getText().equals("")){
            error.setText("please complete all fields below");
            return;
        }if(password.getText().length() < 8){
            error.setText("Password length must be at least 8 characters");
            return;
        }if(password.getText().charAt(0) == ' '){
            error.setText("Password cannot start with a space");
            return;
        }
        if(!password.getText().equals(confirm.getText())){
            error.setText("Passwords don't match");
            return;
        }
        if(phone.getText().length() != 11){
            error.setText("Phone number is invalid");
            return;
        }
        
        if(!checkEmail(email.getText())) {
        	error.setText("Please enter a valid email. only gmail, outlook or hotmail");
            return;
        }
        Runnable task = () -> {
            Platform.runLater(() -> {
            	try {
            		App app = new App();
                    String ecode = app.signupCheck(email.getText(), UserName.getText());
                    if(ecode.equals("1")) {
                    	error.setText("Username already taken!");
                    	return;
                    }
                    
                    if(ecode.equals("2")) {
                    	error.setText("E-mail already signed up, try to sign in!");
                    	return;
                    }
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("confirm.fxml"));
                    Parent parent=(Parent) loader.load();
                    confirm controller = loader.getController();
                    controller.init(ecode, email.getText(), UserName.getText(), password.getText(), phone.getText());
                    Stage s = new Stage();
                    s.setScene(new Scene(parent));
                    s.show();
                    final Node source = (Node) event.getSource();
                    final Stage curr = (Stage) source.getScene().getWindow();
                    curr.close();
            	}catch(Exception er) {
            		System.out.println(er);
            	}
            });
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();  
        error.setText("Please wait...");
    }
    private boolean checkEmail(String email) {
    	if(!Character.isAlphabetic(email.charAt(0))) {
    		return false;
    	}
    	int i = 0;
    	String check = "";
    	for(i = 0; i < email.length(); i++) {
    		if(email.charAt(i) == '@') {
    			break;
    		}
    	}
    	check = email.substring(i);
    	if(check.equals("@gmail.com") || check.equals("@outlook.com") || check.equals("@hotmail.com")) {
    		return true;
    	}
    	return false;
    }
    public void back(ActionEvent e) throws IOException {
		 FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
		 Parent parent=(Parent) loader.load();
		 HelloController controller = loader.getController();
		 controller.suc("");
		 Stage s = new Stage();
		 s.setScene(new Scene(parent));
		 s.show();
		 final Node source = (Node) e.getSource();
		 final Stage curr = (Stage) source.getScene().getWindow();
		 curr.close();
	 }
}
