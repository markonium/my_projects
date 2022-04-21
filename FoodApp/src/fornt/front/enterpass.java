package fornt.front;

import java.io.IOException;

import Back.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class enterpass {
	@FXML
	private PasswordField pass;
	@FXML
	private Label error;
	String username = "";
	public void init(String username) {
		this.username = username;
	}
	public void confirm(ActionEvent e) throws IOException {
		if(pass.getText().length() < 8) {
			error.setText("Password length should be > 8");
			return;
		}
		App app = new App();
		app.resetPassword(username, pass.getText());
		FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
		 Parent parent=(Parent) loader.load();
		 HelloController controller = loader.getController();
		 controller.suc("Password changed successfully!");
		 Stage s = new Stage();
		 s.setScene(new Scene(parent));
		 s.show();
		 final Node source = (Node) e.getSource();
		 final Stage curr = (Stage) source.getScene().getWindow();
		 curr.close();
	}
	public void cancel(ActionEvent e) throws IOException {
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
