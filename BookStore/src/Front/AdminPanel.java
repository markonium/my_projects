package Front;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

public class AdminPanel implements Initializable {
    @FXML
    private ComboBox<String> cat;
    ObservableList<String> category = FXCollections.observableArrayList("Science", "Art", "Religion", "History" , "Geography");
    @FXML
    private ComboBox<String> cat1;
    ObservableList<String> category1 = FXCollections.observableArrayList("Science", "Art", "Religion", "History" , "Geography", "Category: All");
    @FXML
    private ComboBox<String> searchFields;
    ObservableList<String> searchItems = FXCollections.observableArrayList("ISBN", "title", "author", "publisher", "year" ,"price");
    @FXML
    private Label error;
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
    @FXML
    private TextField title;
    @FXML
    private TextField isbn;
    @FXML
    private TextField threshold;
    @FXML
    private TextField publisher;
    @FXML
    private TextField year;
    @FXML
    private TextField price;
    @FXML
    private TextField author;
    @FXML
    private TextField user;
    @FXML
    private TextField search;
    App app;
    ArrayList<String[]> sbooks;
    int ind = 0;
    Label[] is = new Label[3];
    Label[] ts = new Label[3];
    Label[] ps = new Label[3];
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cat.setItems(category);
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
    public void Add(ActionEvent e) throws IOException{
        if(title.getText().equals("") || author.getText().equals("") || publisher.getText().equals("") || year.getText().equals("") || price.getText().equals("") || cat.getValue() == null || isbn.getText().equals("") || threshold.getText().equals("")){
            error.setText("Please complete all fields");
            return;
        }
        try {
        	Integer.parseInt(isbn.getText());
        	Double.parseDouble(price.getText());
        	Integer.parseInt(threshold.getText());
        }catch(Exception ex) {
        	System.out.println(ex);
        	error.setText("ISBN, Threshold and Price must be numeric");
        	return;
        }
        String[] auth = author.getText().split("/");
        ArrayList<String> authors = new ArrayList<String>();
        for(int i = 0; i < auth.length; i++) {
        	authors.add(auth[i]);
        }
        int ecode = app.addBook(Integer.parseInt(isbn.getText()), title.getText(), publisher.getText(), year.getText(), Double.parseDouble(price.getText()), cat.getValue(), Integer.parseInt(threshold.getText()), authors);
        if(ecode == 2) {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("add_pub.fxml"));
            Parent parent = (Parent) loader.load();
            add_pub controller = loader.getController();
            controller.init(app, "Publisher does not exist, add the publisher first");
            Stage s = new Stage();
            s.setScene(new Scene(parent));
            s.show();
        }else if(ecode == 1) {
        	error.setText("ISBN already exists");
        }else {
        	error.setText("Book addded successfully!");
        }
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
        sbooks = app.searchBooks(search.getText(), att, cat);
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
    public void upgrade(ActionEvent e) {
    	if(user.getText().equals("")) {
    		error.setText("Enter a username first");
    		return;
    	}
    	boolean check = app.upgradeUser(user.getText());
    	if(check) {
    		error.setText("User upgraded to manager");
    	}else {
    		error.setText("Username doesn't exist");
    	}
    }
    public void downgrade(ActionEvent e) {
    	if(user.getText().equals("")) {
    		error.setText("Enter a username first");
    		return;
    	}
    	boolean check = app.downgradeUser(user.getText());
    	if(check) {
    		error.setText("User downgraded to normal customer");
    	}else {
    		error.setText("Username doesn't exist");
    	}
    }
    public void edit1(ActionEvent e) throws IOException{
    	if(!is[0].getText().equals("")) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("edit.fxml"));
            Parent parent = (Parent) loader.load();
            Edit controller = loader.getController();
            controller.init(is[0].getText(), app);
            Stage s = new Stage();
            s.setScene(new Scene(parent));
            s.show();
    	}else {
    		error.setText("Search for a book first!");
    	}
    }
    public void edit2(ActionEvent e) throws IOException{
    	if(!is[1].getText().equals("")) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("edit.fxml"));
            Parent parent = (Parent) loader.load();
            Edit controller = loader.getController();
            controller.init(is[1].getText(), app);
            Stage s = new Stage();
            s.setScene(new Scene(parent));
            s.show();
    	}else {
    		error.setText("Search for a book first!");
    	}
    }
    public void edit3(ActionEvent e) throws IOException{
    	if(!is[2].getText().equals("")) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("edit.fxml"));
            Parent parent = (Parent) loader.load();
            Edit controller = loader.getController();
            controller.init(is[2].getText(), app);
            Stage s = new Stage();
            s.setScene(new Scene(parent));
            s.show();
    	}else {
    		error.setText("Search for a book first!");
    	}
    }
    public void po1(ActionEvent e) throws IOException{
    	if(!is[0].getText().equals("")) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("placeOrder.fxml"));
            Parent parent = (Parent) loader.load();
            placeOrder controller = loader.getController();
            ArrayList<String> arr = app.getBookDetails(is[0].getText());
            
            controller.init(is[0].getText(), arr.get(1), app);
            Stage s = new Stage();
            s.setScene(new Scene(parent));
            s.show();
    	}else {
    		error.setText("Search for a book first!");
    	}
    }
    public void po2(ActionEvent e) throws IOException{
    	if(!is[1].getText().equals("")) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("placeOrder.fxml"));
            Parent parent = (Parent) loader.load();
            placeOrder controller = loader.getController();
            
            ArrayList<String> arr = app.getBookDetails(is[1].getText());
            
            controller.init(is[1].getText(), arr.get(1), app);
            Stage s = new Stage();
            s.setScene(new Scene(parent));
            s.show();
    	}else {
    		error.setText("Search for a book first!");
    	}
    }
    public void po3(ActionEvent e) throws IOException{
    	if(!is[2].getText().equals("")) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("placeOrder.fxml"));
            Parent parent = (Parent) loader.load();
            placeOrder controller = loader.getController();
            
            ArrayList<String> arr = app.getBookDetails(is[2].getText());
            
            controller.init(is[2].getText(), arr.get(1), app);
            Stage s = new Stage();
            s.setScene(new Scene(parent));
            s.show();
    	}else {
    		error.setText("Search for a book first!");
    	}
    }
    public void vieww(ActionEvent e) throws IOException{
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("manager_orders.fxml"));
        Parent parent = (Parent) loader.load();
        managerOrders controller = loader.getController();
        controller.init(app, "waiting");
        Stage s = new Stage();
        s.setScene(new Scene(parent));
        s.show();
    }
    public void viewr(ActionEvent e) throws IOException{
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("manager_orders.fxml"));
        Parent parent = (Parent) loader.load();
        managerOrders controller = loader.getController();
        controller.init(app, "received");
        Stage s = new Stage();
        s.setScene(new Scene(parent));
        s.show();
    }
    public void mode(ActionEvent e) throws IOException{
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
    public void det1(ActionEvent e) throws IOException {
    	if(!is[0].getText().equals("")) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("Details.fxml"));
            Parent parent = (Parent) loader.load();
            Details controller = loader.getController();
            controller.init(app.getBookDetails(is[0].getText()), is[0].getText());
            Stage s = new Stage();
            s.setScene(new Scene(parent));
            s.show();
    	}
    }public void det2(ActionEvent e) throws IOException {
    	if(!is[1].getText().equals("")) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("Details.fxml"));
            Parent parent = (Parent) loader.load();
            Details controller = loader.getController();
            controller.init(app.getBookDetails(is[1].getText()), is[1].getText());
            Stage s = new Stage();
            s.setScene(new Scene(parent));
            s.show();
    	}
    }public void det3(ActionEvent e) throws IOException {
    	if(!is[2].getText().equals("")) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("Details.fxml"));
            Parent parent = (Parent) loader.load();
            Details controller = loader.getController();
            controller.init(app.getBookDetails(is[2].getText()), is[2].getText());
            Stage s = new Stage();
            s.setScene(new Scene(parent));
            s.show();
    	}
    }
    
}
