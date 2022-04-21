package fornt.front;

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
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloController {
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private Label error;
    public void signin(ActionEvent e) throws IOException {
        String name=userName.getText();
        String pass=password.getText();
        App app = new App();
        boolean check = app.login(name,pass);
        if(!check){
        error.setText("Wrong username or password\nor already logged in");
        }else{
            if(name.equals("admin")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("menuEdit.fxml"));
                Parent parent = (Parent) loader.load();
                MenuEdit controller = loader.getController();
                controller.set(app);
                Stage s = new Stage();
                s.setScene(new Scene(parent));
                s.initStyle(StageStyle.UNDECORATED);
                s.show();
                final Node source = (Node) e.getSource();
                final Stage curr = (Stage) source.getScene().getWindow();
                curr.close();
            }else if(app.currUser.getLevel() == 0){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
                Parent parent = (Parent) loader.load();
                Menu controller = loader.getController();
                controller.set(app);
                Stage s = new Stage();
                s.setScene(new Scene(parent));
                s.initStyle(StageStyle.UNDECORATED);
                s.show();
                final Node source = (Node) e.getSource();
                final Stage curr = (Stage) source.getScene().getWindow();
                curr.close();
            }else {
            	FXMLLoader loader = new FXMLLoader(getClass().getResource("ot.fxml"));
                Parent parent = (Parent) loader.load();
                OT controller = loader.getController();
                controller.set(app);
                Stage s = new Stage();
                s.setScene(new Scene(parent));
                s.initStyle(StageStyle.UNDECORATED);
                s.show();
                final Node source = (Node) e.getSource();
                final Stage curr = (Stage) source.getScene().getWindow();
                curr.close();
            }
        }
    }
    public void signup(ActionEvent e) throws IOException {
        Parent x = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene=new Scene(x);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void resetPass(ActionEvent e) throws IOException {
        Parent x = FXMLLoader.load(getClass().getResource("getname.fxml"));
        Scene scene=new Scene(x);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void suc(String x){
        error.setText(x);
    }

}