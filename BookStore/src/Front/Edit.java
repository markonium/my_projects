package Front;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Back.App;

public class Edit implements Initializable {
    @FXML
    private ComboBox<String> cat;
    ObservableList<String> category = FXCollections.observableArrayList("Science", "Art", "Religion", "History" , "Geography");
    @FXML
    private Label error;
    @FXML
    private TextField title;
    @FXML
    private TextField author;
    @FXML
    private TextField publisher;
    @FXML
    private TextField year;
    @FXML
    private TextField price;
    @FXML
    private TextField stock;
    @FXML
    private TextField thre;
    String isbn;
    App app;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cat.setItems(category);
    }
    public void init(String isbn, App app) {
    	this.isbn = isbn;
    	this.app = app;
    }
    public void edit(ActionEvent e) throws IOException{
        if(title.getText().equals("") && author.getText().equals("") && publisher.getText().equals("") && year.getText().equals("") && price.getText().equals("") && cat.getValue() == null && stock.getText().equals("") && thre.getText().equals("")){
            error.setText("Please fill at least one field");
            return;
        }
        ArrayList<String> arr = app.getBookDetails(isbn);
        if(!title.getText().equals("")) {
        	arr.set(0, title.getText());
        }
        if(!publisher.getText().equals("")) {
        	arr.set(1, publisher.getText());
        }
        if(!year.getText().equals("")) {
        	arr.set(2, year.getText());
        }
        if(!price.getText().equals("")) {
        	arr.set(3, price.getText());
        }
        if(cat.getValue() != null) {
        	arr.set(4, cat.getValue());
        }
        if(!thre.getText().equals("")) {
        	if(Integer.valueOf(thre.getText()) < 0) {
        		error.setText("Threshold cannot be a negative value");
        		return;
        	}
        	arr.set(5, thre.getText());
        }
        if(!stock.getText().equals("")) {
        	if(Integer.valueOf(stock.getText()) < 0) {
        		error.setText("Stock cannot be a negative value");
        		return;
        	}
        	arr.set(6, stock.getText());
        }
        boolean check = app.updateBook(isbn, arr.get(0), arr.get(1), arr.get(2), arr.get(3), arr.get(4), arr.get(5), arr.get(6));
        if(!check) {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("add_pub.fxml"));
            Parent parent = (Parent) loader.load();
            add_pub controller = loader.getController();
            controller.init(app, "Publisher does not exist, add the publisher first");
            Stage s = new Stage();
            s.setScene(new Scene(parent));
            s.show();
        }else {
        	error.setText("Book updated successfully");
        }
    }
}
