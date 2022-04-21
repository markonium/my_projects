package Front;

import Back.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class add_pub {
    @FXML
    private Label error;
    @FXML
    private TextField name;
    @FXML
    private TextField address;
    @FXML
    private TextField phone;
    App app;
    public void init(App app, String e) {
    	this.app = app;
    	error.setText(e);
    }
    public void addPub(ActionEvent e) {
    	if(name.getText().equals("") || address.getText().equals("") || phone.getText().equals("")) {
    		error.setText("Complete all fields");
    		return;
    	}app.addPublisher(name.getText(), address.getText(), phone.getText());
    	error.setText("Publisher added successfully!");
    }
}
