package Front;

import java.io.IOException;
import Back.App;
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

public class SignUp {
    @FXML
    private TextField userName;
    @FXML
    private TextField Fname;
    @FXML
    private TextField Lname;
    @FXML
    private TextField address;
    @FXML
    private TextField Email;
    @FXML
    private TextField phone;
    @FXML
    private PasswordField pass;
    @FXML
    private PasswordField confirmPass;
    @FXML
    private Label error;

    public void signUp(ActionEvent e) throws IOException{
        if(userName.getText().equals("") || Fname.getText().equals("") || Lname.getText().equals("") || address.getText().equals("") || Email.getText().equals("") || phone.getText().equals("") || pass.getText().equals("") || confirmPass.getText().equals("") ){
            error.setText("Please complete all fields");
            return;
        }
        if(!pass.getText().equals(confirmPass.getText())){
            error.setText("Password doesn't match");
            return;
        }
        App app = new App();
        Boolean check = app.signUp(userName.getText(), Fname.getText(), Lname.getText(), Email.getText(), pass.getText(), phone.getText(), address.getText());
        if(!check) {
        	error.setText("Username or Email already taken!");
        	return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent parent=(Parent) loader.load();
        HelloController controller = loader.getController();
        controller.init("Sign up successful");
        Stage s = new Stage();
        s.setScene(new Scene(parent));
        s.show();
        final Node source = (Node) e.getSource();
        final Stage curr = (Stage) source.getScene().getWindow();
        curr.close();
    }


}
