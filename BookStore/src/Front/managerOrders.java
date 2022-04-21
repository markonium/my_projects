package Front;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Back.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class managerOrders implements Initializable{
    @FXML
    private Label id1;
    @FXML
    private Label error;
    @FXML
    private Label id2;
    @FXML
    private Label id3;
    @FXML
    private Label i1;
    @FXML
    private Label i2;
    @FXML
    private Label i3;
    @FXML
    private Label am1;
    @FXML
    private Label am2;
    @FXML
    private Label am3;
    @FXML
    private Label p1;
    @FXML
    private Label p2;
    @FXML
    private Label p3;
    @FXML
    private Label s1;
    @FXML
    private Label s2;
    @FXML
    private Label s3;
    @FXML
    private Button c3;
    @FXML
    private Button c2;
    @FXML
    private Button c1;
    @FXML
    private Button d1;
    @FXML
    private Button d2;
    @FXML
    private Button d3;
    @FXML
    private TextField title;
    App app;
    ArrayList<String[]> orders;
    String status;
    int ind = 0;
    boolean flag = false;
    Label[] ids = new Label[3];
    Label[] is = new Label[3];
    Label[] ams = new Label[3];
    Label[] ps = new Label[3];
    Label[] ss = new Label[3];
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	ids[0] = id1;
        ids[1] = id2;
        ids[2] = id3;
    	
        is[0] = i1;
        is[1] = i2;
        is[2] = i3;
        
        ams[0] = am1;
        ams[1] = am2;
        ams[2] = am3;
        
        ps[0] = p1;
        ps[1] = p2;
        ps[2] = p3;
        
        ss[0] = s1;
        ss[1] = s2;
        ss[2] = s3;
        
    }
    public void init(App app, String status) {
    	this.app = app;
    	ind = 0; 
    	this.status = status;
    	orders = app.getOrders(this.status);
    	if(status.equals("received")) {
    		c1.setVisible(false);
    		c2.setVisible(false);
    		c3.setVisible(false);
    	}else {
    		d1.setVisible(false);
    		d2.setVisible(false);
    		d3.setVisible(false);
    	}
    	updateLabels();
    }
    private void resetLabels() {
    	int j;
    	for(j = 0; j < 3; j++) {
    		ids[j].setText("");
    		is[j].setText("");
    		ams[j].setText("");
    		ps[j].setText("");
    		ss[j].setText("");
    	}
    }
    private void updateLabels() {  	
    	resetLabels();
    	int j;
    	for(j = 0; j < 3; j++) {
    		if(ind >= orders.size()) {
    			break;
    		}
    		String[] arr = orders.get(ind++);
    		ids[j].setText(arr[0]);
    		is[j].setText(arr[1]);
    		ams[j].setText(arr[2]);
    		ss[j].setText(arr[3]);
    		ps[j].setText(arr[4]);
    	}
    }
    public void next(ActionEvent e) {
    	if(orders == null || ind < orders.size()) {
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
    public void del1(ActionEvent e) throws IOException{
    	if(!ids[0].getText().equals("")) {
    		boolean check = app.deleteOrder(ids[0].getText());
    		if(check) {
    			error.setText("Deleted successfully");
    			init(app, status);
    		}else {
    			error.setText("Cannot delete a waiting order");
    		}
    	}
    }public void del2(ActionEvent e) throws IOException{
    	if(!ids[1].getText().equals("")) {
    		boolean check = app.deleteOrder(ids[1].getText());
    		if(check) {
    			error.setText("Deleted successfully");
    			init(app, status);
    		}else {
    			error.setText("Cannot delete a waiting order");
    		}
    	}
    }
    public void del3(ActionEvent e) throws IOException{
    	if(!ids[2].getText().equals("")) {
    		boolean check = app.deleteOrder(ids[2].getText());
    		if(check) {
    			error.setText("Deleted successfully");
    			init(app, status);
    		}else {
    			error.setText("Cannot delete a waiting order");
    		}
    	}
    }
    public void con1(ActionEvent e) throws IOException{
    	if(!ids[0].getText().equals("")) {
    		app.confirmOrder(Integer.parseInt(ids[0].getText()), is[0].getText(), ams[0].getText());
    		init(app, status);
    		error.setText("Order confirmed and stock increased!");
    	}
    }
    public void con2(ActionEvent e) throws IOException{
    	if(!ids[1].getText().equals("")) {
    		app.confirmOrder(Integer.parseInt(ids[1].getText()), is[1].getText(), ams[1].getText());
    		init(app, status);
    		error.setText("Order confirmed and stock increased!");
    	}	
    }
    public void con3(ActionEvent e) throws IOException{
    	if(!ids[2].getText().equals("")) {
    		app.confirmOrder(Integer.parseInt(ids[2].getText()), is[2].getText(), ams[2].getText());
    		init(app, status);
    		error.setText("Order confirmed and stock increased!");
    	}
    }
    
	}
