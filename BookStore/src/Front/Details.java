package Front;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Details {
	@FXML
    private Label det;
    @FXML
    private Label title;
    @FXML
    private Label pub;
    @FXML
    private Label year;
    @FXML
    private Label price;
    @FXML
    private Label cat;
    @FXML
    private Label thre;
    @FXML
    private Label stock;
    public  void init(ArrayList<String> arr, String isbn) {
    	det.setText("Details for book with ISBN: " + isbn);
    	this.title.setText(arr.get(0));
    	this.pub.setText(arr.get(1));
    	this.year.setText(arr.get(2));
    	this.price.setText(arr.get(3));
    	this.cat.setText(arr.get(4));
    	this.thre.setText(arr.get(5));
    	this.stock.setText(arr.get(6));
    }
}
