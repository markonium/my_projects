package fornt.front;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Reset {
	@FXML
	private TextField code;
	@FXML
	private Label error;
	String ecode = "";
	String username = "";
	public void init(String ecode, String username) {
		 this.ecode = ecode;
		 this.username = username;
	}
	public void confirm(ActionEvent e) throws IOException {
		 if(!code.getText().equals(ecode)) {
			 error.setText("Invalid code");
			 return;
		 }
		 FXMLLoader loader = new FXMLLoader(getClass().getResource("enterpass.fxml"));
		 Parent parent=(Parent) loader.load();
		 enterpass controller = loader.getController();
		 controller.init(username);
		 Stage s = new Stage();
		 s.setScene(new Scene(parent));
		 s.show();
		 final Node source = (Node) e.getSource();
		 final Stage curr = (Stage) source.getScene().getWindow();
		 curr.close();
	 }
	public void back(ActionEvent e) throws IOException {
		Parent x = FXMLLoader.load(getClass().getResource("getname.fxml"));
        Scene scene = new Scene(x);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
	}
}
