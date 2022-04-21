package fornt.front;

import java.io.IOException;
import java.util.Calendar;

import Back.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class checkout {
	 @FXML
	 private TextField month;
	 @FXML
	 private TextField year;	
	 @FXML
	 private Label error;
	 @FXML
	 private ComboBox<String> address;
	 @FXML
	 private ComboBox<String> card;
	 @FXML
	 private Label total;
	 @FXML
	 private Button ob;
	 private App app;
	 public void set(App app){
		 this.app = app;
	     ObservableList<String> addresslist = FXCollections.observableArrayList(app.getAddress());
	     address.setItems(addresslist);
	     ObservableList<String> cardslist=FXCollections.observableArrayList(app.getCreditCards());
	     card.setItems(cardslist);
	     total.setText(String.valueOf(app.getCartPrice()) + " EGP");
	 }
	 public void chooseAddress(ActionEvent e){
	     address.setPromptText(address.getValue());
	 }
	 public void order(ActionEvent e) {
		 	if(address.getValue() == null || card.getValue() == null) {
		 		error.setText("Please choose a billing address and a card!");
		 		return;
	    	}
		 	String cardNum = card.getValue();
	    	if(cardNum.length() != 16) {
	    		error.setText("Card number must be 16 digits");
	    		return;
	    	}try {
	    		Long.valueOf(cardNum);
	    		Integer.valueOf(month.getText());
	    		Integer.valueOf(year.getText());
	    	}catch(Exception ex) {
	    		error.setText("Card number and expiry date must be numeric");
	    		return;
	    	}
	    	if(cardNum.charAt(0) != '4' && cardNum.charAt(0) != '5') {
	    		error.setText("Card must be Visa or Mastercard");
	    		return;
	    	}
	    	if(Integer.parseInt(month.getText()) > 12) {
	    		error.setText("please a valid expiry date");
	    		return;
	    	}
	    	int currMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
	    	int currYear = Calendar.getInstance().get(Calendar.YEAR);
	    	if(Integer.valueOf(year.getText()) < currYear) {
	    		error.setText("Card is expired!");
	    		return;
	    	}else if(Integer.valueOf(year.getText()) == currYear) {
	    		if(Integer.valueOf(month.getText()) < currMonth) {
	    			error.setText("Card is expired!");
	        		return;
	    		}
	    	}
	    	
	    	app.placeOrder(address.getValue());
	    	error.setText("Thank you, Order placed successfully!");
	    	ob.setVisible(false);
	 }
	 public void back(ActionEvent e) throws IOException {
		 FXMLLoader loader = new FXMLLoader(getClass().getResource("cart.fxml"));
	        Parent parent = (Parent) loader.load();
	        Cart controller = loader.getController();
	        controller.set(app);
	        Stage s = new Stage();
	        s.setScene(new Scene(parent));
	        s.initStyle(StageStyle.UNDECORATED);
	        s.show();
	        final Node source = (Node) e.getSource();
	        final Stage curr = (Stage) source.getScene().getWindow();
	        curr.close();
	 }
	 public void settings(ActionEvent e) throws IOException {
		 FXMLLoader loader = new FXMLLoader(getClass().getResource("modifyUser.fxml"));
	        Parent parent = (Parent) loader.load();
	        ModifyUser controller = loader.getController();
	        controller.setApp(app);
	        Stage s = new Stage();
	        s.setScene(new Scene(parent));
	        s.initStyle(StageStyle.UNDECORATED);
	        s.show();
	        final Node source = (Node) e.getSource();
	        final Stage curr = (Stage) source.getScene().getWindow();
	        curr.close();
	 }
}
