package Front;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import Back.App;

public class Cart implements Initializable {
    @FXML
    private Label error;
    @FXML
    private Label name1;
    @FXML
    private Label name2;
    @FXML
    private Label name3;
    @FXML
    private Label price1;
    @FXML
    private Label price2;
    @FXML
    private Label price3;
    @FXML
    private Label num1;
    @FXML
    private Label num2;
    @FXML
    private Label num3;
    @FXML
    private Label p1;
    @FXML
    private Label p2;
    @FXML
    private Label p3;
    @FXML
    private Label total;
    @FXML
    private TextField cardNum;
    @FXML
    private TextField month;
    @FXML
    private TextField year;
    
    
    private Label[] names = new Label[3];
    private Label[] prices = new Label[3];
    private Label[] nums = new Label[3];
    private Label[] isbn = new Label[3];
    ArrayList<String[]> sbooks;
    int ind = 0;
    App app;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        names[0] = name1;
        names[1] = name2;
        names[2] = name3;
        prices[0] = price1;
        prices[1] = price2;
        prices[2] = price3;
        nums[0] = num1;
        nums[1] = num2;
        nums[2] = num3;
        isbn[0] = p1;
        isbn[1] = p2;
        isbn[2] = p3;
    }public void init(App app) {
    	this.app = app;
    	ind = 0;
    	sbooks = app.getCart();
    	updateLabels();
    	getTotal();
    }
    private void getTotal() {
    	Double tp = 0.0;
    	for(int i = 0; i < sbooks.size(); i++) {
    		String arr[] = sbooks.get(i);
    		tp = tp + Double.valueOf(arr[1]) * Double.valueOf(arr[2]);
    	}
    	total.setText(String.valueOf(tp) + " $");
    }
    private void resetLabels() {
    	int j;
    	for(j = 0; j < 3; j++) {
    		names[j].setText("");
    		prices[j].setText("");
    		nums[j].setText("");
    		isbn[j].setText("");
    	}
    }
    private void updateLabels() {  	
    	resetLabels();
    	int j;
    	for(j = 0; j < 3; j++) {
    		if(ind >= sbooks.size()) {
    			break;
    		}
    		String[] arr = sbooks.get(ind++);
    		names[j].setText(arr[0]);
    		prices[j].setText(arr[1]);
    		nums[j].setText(arr[2]);
    		isbn[j].setText(arr[3]);
    	}
    }
    public void next(ActionEvent e) {
    	if(sbooks == null || ind < sbooks.size()) {
    		updateLabels();
    	}
    }
    public void prev(ActionEvent e) {
    	if(ind > 3) {
    		resetLabels();
    		if(ind % 3 == 0) {
    			ind -= 6;
    		}else {
    			ind = ind - (ind % 3) - 3;
    		}
    		updateLabels();
    	}
    }
    public void rem1(ActionEvent e) {
    	if(!names[0].getText().equals("")) {
    		app.removeFromCart(Integer.valueOf(isbn[0].getText()));
    		init(app);
    	}
    }
    public void rem2(ActionEvent e) {
    	if(!names[1].getText().equals("")) {
    		app.removeFromCart(Integer.valueOf(isbn[1].getText()));
    		init(app);
    	}
    }
    public void rem3(ActionEvent e) {
    	if(!names[2].getText().equals("")) {
    		app.removeFromCart(Integer.valueOf(isbn[2].getText()));
    		init(app);
    	}
    }
    public void order(ActionEvent e) {
    	if(cardNum.getText().length() != 16) {
    		error.setText("Card number must be 16 digits");
    		return;
    	}try {
    		Long.valueOf(cardNum.getText());
    		Integer.valueOf(month.getText());
    		Integer.valueOf(year.getText());
    	}catch(Exception ex) {
    		error.setText("Card number and expiry date must be numeric");
    		return;
    	}
    	if(cardNum.getText().charAt(0) != '4' && cardNum.getText().charAt(0) != '5') {
    		error.setText("Card must be Visa or Mastercard");
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
    	app.placeOrderUser();
    	error.setText("Order placed successfully!");
    	init(app);
    }
}
