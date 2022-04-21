package fornt.front;

import java.io.IOException;

import Back.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class getname {
	@FXML
	private TextField tf;
	@FXML
	private Label lb;
	App app = new App();
	public void next(ActionEvent e) throws IOException {
		if(tf.getText().equals("")) {
			lb.setText("Enter a valid username");
			return;
		}
		if(tf.getText().equals("admin")) {
			lb.setText("Password reset is not allowed for this user");
			return;
		}if(!app.checkUser(tf.getText())) {
			lb.setText("Username doesn't exist");
			return;
		}
		Runnable task = () -> {
            Platform.runLater(() -> {
            	try {
            		String ecode = app.sendResetEmail(tf.getText());
            		FXMLLoader loader = new FXMLLoader(getClass().getResource("reset.fxml"));
            	    Parent parent=(Parent) loader.load();
            	    Reset controller = loader.getController();
            	    controller.init(ecode, tf.getText());
            	    Stage s = new Stage();
            	    s.setScene(new Scene(parent));
            	    s.show();
            	    final Node source = (Node) e.getSource();
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
        lb.setText("Please wait...");
	}
	public void back(ActionEvent e) throws IOException {
		Parent x = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(x);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
	}
}
