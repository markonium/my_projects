package fornt.front;

import java.io.IOException;

import Back.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class confirm {
	@FXML
	private TextField code;
	@FXML
	private Label error;
	private String ecode, email, name, pass, phone;
	 public void back(ActionEvent e) throws IOException {
	        Parent x = FXMLLoader.load(getClass().getResource("login.fxml"));
	        Scene scene=new Scene(x);
	        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
	        stage.setScene(scene);
	        stage.show();
	    }
	 public void init(String ecode, String email, String name, String pass, String phone) {
		 this.ecode = ecode;
		 this.email = email;
		 this.name = name;
		 this.pass = pass;
		 this.phone = phone;
	 }
	 public void confirmSignup(ActionEvent e) throws IOException {
		 if(!code.getText().equals(ecode)) {
			 error.setText("Invalid code");
			 return;
		 }
		 App app = new App();
		 app.signUp(name, pass, email, phone);
		 FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
		 Parent parent=(Parent) loader.load();
		 HelloController controller = loader.getController();
		 controller.suc("sign up successful");
		 Stage s = new Stage();
		 s.setScene(new Scene(parent));
		 s.show();
		 final Node source = (Node) e.getSource();
		 final Stage curr = (Stage) source.getScene().getWindow();
		 curr.close();
	 }
}
