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

public class UserPanel implements Initializable {
	@FXML
    private ComboBox<String> cat1;
    ObservableList<String> category1 = FXCollections.observableArrayList("Science", "Art", "Religion", "History" , "Geography", "Category: All");
    @FXML
    private ComboBox<String> searchFields;
    ObservableList<String> searchItems = FXCollections.observableArrayList("ISBN", "title", "author", "publisher", "year" ,"price");
    @FXML
    private Label error;
    @FXML
    private TextField search;
    @FXML
    private Label i1;
    @FXML
    private Label i2;
    @FXML
    private Label i3;
    @FXML
    private Label t1;
    @FXML
    private Label t2;
    @FXML
    private Label t3;
    @FXML
    private Label p1;
    @FXML
    private Label p2;
    @FXML
    private Label p3;
    App app;
    ArrayList<String[]> sbooks;
    int ind = 0;
    boolean flag = false;
    Label[] is = new Label[3];
    Label[] ts = new Label[3];
    Label[] ps = new Label[3];

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cat1.setItems(category1);
        searchFields.setItems(searchItems);
        is[0] = i1;
        is[1] = i2;
        is[2] = i3;
        
        ts[0] = t1;
        ts[1] = t2;
        ts[2] = t3;
        
        ps[0] = p1;
        ps[1] = p2;
        ps[2] = p3;
    }
    public void init(App app) {
    	this.app = app;
    }
    public void settings(ActionEvent e) throws IOException{
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("editUser.fxml"));
        Parent parent = (Parent) loader.load();
        EditUser controller = loader.getController();
        controller.init(app);
        Stage s = new Stage();
        s.setScene(new Scene(parent));
        s.show();
    }
    public void Search(ActionEvent e){
        if(search.getText().equals("") || searchFields.getValue() == null){
            error.setText("Please enter a search key and choose search attribute");
            return;
        }
        String cat = cat1.getValue();
        String att = searchFields.getValue();
        if(att.equals("year")) {
        	att = "publication_year";
        }
        if(cat == null || cat.equals("Category: All")) {
        	cat = "All";
        }
        sbooks = app.searchBooks2(search.getText(), att, cat);
        resetLabels();
        ind = 0;
        if(sbooks.get(0).length == 1) {
        	t1.setText(sbooks.get(0)[0]);
        }else {
        	updateLabels();
        }
    }
    private void resetLabels() {
    	int j;
    	for(j = 0; j < 3; j++) {
    		is[j].setText("");
    		ts[j].setText("");
    		ps[j].setText("");
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
    		is[j].setText(arr[0]);
    		ts[j].setText(arr[1]);
    		ps[j].setText(arr[2]);
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
    public void add1(ActionEvent e) {
    	if(!is[0].getText().equals("")) {
    		app.addToCart(Integer.valueOf(is[0].getText()));
    		error.setText("Book added to cart!");
    	}
    }
    public void add2(ActionEvent e) {
    	if(!is[1].getText().equals("")) {
    		app.addToCart(Integer.valueOf(is[1].getText()));
    		error.setText("Book added to cart!");
    	}
    }
    public void add3(ActionEvent e) {
    	if(!is[2].getText().equals("")) {
    		app.addToCart(Integer.valueOf(is[2].getText()));
    		error.setText("Book added to cart!");
    	}
    }
    public void cart (ActionEvent e) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("cart.fxml"));
        Parent parent = (Parent) loader.load();
        Cart controller = loader.getController();
        controller.init(app);
        Stage s = new Stage();
        s.setScene(new Scene(parent));
        s.show();
    }
}
