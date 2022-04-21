package Front;

import java.io.IOException;

import Back.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class placeOrder {
	@FXML
    private Label error;
	@FXML
    private TextField amount;
	
	String isbn;
	String pub;
    App app;
    public void init(String isbn, String pub, App app) {
    	this.isbn = isbn;
    	this.pub = pub;
    	this.app = app;
    	error.setText("Ordering an amount for book with ISBN = " + this.isbn);
    }
    public void order(ActionEvent e) throws IOException{
    	if(amount.getText().equals("")) {
    		error.setText("Please enter an amount first");
    		return;
    	}
    	int am;
    	try {
    		am = Integer.parseInt(amount.getText());
    	}catch(Exception ex) {
    		error.setText("Amount has to be an integer value");
    		return;
    	}
    	if(am < 0) {
    		error.setText("Amount cannot be negative");
    		return;
    	}
    	app.placeOrderManager(Integer.valueOf(isbn), am, pub);
    	error.setText("Order placed successfully");
    }
}
