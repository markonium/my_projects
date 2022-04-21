package Front;

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

import Back.App;

public class HelloController {
    @FXML
    private TextField name;
    @FXML
    private PasswordField pass;
    @FXML
    private Label error;
    public void signIn(ActionEvent e) throws IOException{
        if(name.getText().equals("") || pass.getText().equals("")){
            error.setText("Please fill all fields");
            return;
        }
        App app = new App();
        Boolean check = app.signIn(name.getText(), pass.getText());
        if(!check) {
        	error.setText("Username or password is incorrect");
        	return;
        }
        if(app.getLevel() == 1) {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPanel.fxml"));
            Parent parent=(Parent) loader.load();
            AdminPanel controller = loader.getController();
            controller.init(app);
            Stage s = new Stage();
            s.setScene(new Scene(parent));
            s.show();
            final Node source = (Node) e.getSource();
            final Stage curr = (Stage) source.getScene().getWindow();
            curr.close();
        }else {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("UserPanel.fxml"));
            Parent parent=(Parent) loader.load();
            UserPanel controller = loader.getController();
            controller.init(app);
            Stage s = new Stage();
            s.setScene(new Scene(parent));
            s.show();
            final Node source = (Node) e.getSource();
            final Stage curr = (Stage) source.getScene().getWindow();
            curr.close();
        }
    }
    public void signUp(ActionEvent e) throws IOException {
        Parent x = FXMLLoader.load(getClass().getResource("signUp.fxml"));
        Scene scene=new Scene(x);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void init(String s) {
    	error.setText(s);
    }

}