package fornt.front;

import java.io.IOException;
import java.util.ArrayList;

import Back.App;
import Back.Item;
import Back.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class OT {
	@FXML
	private Label id1;
	@FXML
	private Label id2;
	@FXML
	private Label id3;
	@FXML
	private Label id4;
	@FXML
	private Label id5;
	@FXML
	private Label t1;
	@FXML
	private Label t2;
	@FXML
	private Label t3;
	@FXML
	private Label t4;
	@FXML
	private Label t5;
	@FXML
	private Label s1;
	@FXML
	private Label s2;
	@FXML
	private Label s3;
	@FXML
	private Label s4;
	@FXML
	private Label s5;
	private Label[] id = new Label[5];
	private Label[] status = new Label[5];
	private Label[] time = new Label[5];
	ArrayList<Order> orders= new ArrayList<Order>();
	private int last=0;
	private App app;
	public void set(App app2){
        this.app = app2;
        orders = app.refreshforOT();
        id[0] = id1;
        id[1] = id2;
        id[2] = id3;
        id[3] = id4;
        id[4] = id5;
        status[0] = s1;
        status[1] = s2;
        status[2] = s3;
        status[3] = s4;
        status[4] = s5;
        time[0] = t1;
        time[1] = t2;
        time[2] = t3;
        time[3] = t4;
        time[4] = t5;
        load();
    }
	public void load(){
    	if(orders.size() == 0) {
    		id[0].setText("");
    		status[0].setText("");
    		time[0].setText("");
    		return;
    	}
        if(last == orders.size()){
            return;
        }
        int i = 0;
        int j = 0;
        for(i = last; i < last + 5 && i < orders.size(); i++){
            id[j].setText(String.valueOf(orders.get(i).getId()));
            status[j].setText(orders.get(i).getState());
            time[j].setText(orders.get(i).getTime());
            j++;
        }
        last = i;
        for(j = j; j < 5; j++){
            id[j].setText("");
            status[j].setText("");
            time[j].setText("");
        }

    }
	public void next(ActionEvent e){
        load();
    }
	public void prev(ActionEvent e){
        int temp = last - (last % 5);
        if(last == temp){
            temp = temp - 10;
        }else{
            temp = temp - 5;
        }

        if(temp < 0){
            return;
        }
        last = temp;
        load();
    }
	public void signout(ActionEvent e) throws IOException {
        app.signout();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent parent = (Parent) loader.load();
        //HelloController controller = loader.getController();
        Stage s = new Stage();
        s.setScene(new Scene(parent));
        s.show();
        final Node source = (Node) e.getSource();
        final Stage curr = (Stage) source.getScene().getWindow();
        curr.close();
    }
	
	public void d1(ActionEvent e) throws IOException{
		if(id1.getText().equals("")) {
			return;
		}
       Order order = getOrder(id[0].getText());
       ArrayList<Item> items= order.getItems();
       ArrayList<Integer> items_no= order.getItemsNum();
       String det = "Username: " + order.getUsername() + "\nPhone: " + app.getPhone(order.getUsername()) + "\nAddress: " + order.getAddress() + "\n\n";
       for(int i = 0; i < items.size(); i++) {
    	   det = det + items_no.get(i) + " x " + items.get(i).getName() + "\n";
       }
       sc(det);
    }
	public void d2(ActionEvent e) throws IOException{
		if(id2.getText().equals("")) {
			return;
		}
		Order order = getOrder(id[1].getText());
	       ArrayList<Item> items= order.getItems();
	       ArrayList<Integer> items_no= order.getItemsNum();
	       String det = "Username: " + order.getUsername() + "\nPhone: " + app.getPhone(order.getUsername()) + "\nAddress: " + order.getAddress() + "\n\n";
	       for(int i = 0; i < items.size(); i++) {
	    	   det = det + items_no.get(i) + " x " + items.get(i).getName() + "\n";
	       }  
	       sc(det);
    }
	public void d3(ActionEvent e) throws IOException{
		if(id3.getText().equals("")) {
			return;
		}
		Order order = getOrder(id[2].getText());
	       ArrayList<Item> items= order.getItems();
	       ArrayList<Integer> items_no= order.getItemsNum();
	       String det = "Username: " + order.getUsername() + "\nPhone: " + app.getPhone(order.getUsername()) + "\nAddress: " + order.getAddress() + "\n\n";
	       for(int i = 0; i < items.size(); i++) {
	    	   det = det + items_no.get(i) + " x " + items.get(i).getName() + "\n";
	       }
	       sc(det);
    }
	public void d4(ActionEvent e) throws IOException{
		if(id4.getText().equals("")) {
			return;
		}
		Order order = getOrder(id[3].getText());
	       ArrayList<Item> items= order.getItems();
	       ArrayList<Integer> items_no= order.getItemsNum();
	       String det = "Username: " + order.getUsername() + "\nPhone: " + app.getPhone(order.getUsername()) + "\nAddress: " + order.getAddress() + "\n\n";
	       for(int i = 0; i < items.size(); i++) {
	    	   det = det + items_no.get(i) + " x " + items.get(i).getName() + "\n";
	       }
	       sc(det);
    }
	public void d5(ActionEvent e) throws IOException{
		if(id5.getText().equals("")) {
			return;
		}
		Order order = getOrder(id[4].getText());
	       ArrayList<Item> items= order.getItems();
	       ArrayList<Integer> items_no= order.getItemsNum();
	       String det = "Username: " + order.getUsername() + "\nPhone: " + app.getPhone(order.getUsername()) + "\nAddress: " + order.getAddress() + "\n\n";
	       for(int i = 0; i < items.size(); i++) {
	    	   det = det + items_no.get(i) + " x " + items.get(i).getName() + "\n";
	       }
	       sc(det);
    }
	
	public void mp1(ActionEvent e){
		if(id[0].getText().equals("")) {
			return;
		}
		app.markOrderInProgress(id[0].getText());
		last = 0;
		load();
    }
	public void mp2(ActionEvent e){
		if(id[1].getText().equals("")) {
			return;
		}
		app.markOrderInProgress(id[1].getText());
		last = 0;
		load();
    }
	public void mp3(ActionEvent e){
		if(id[2].getText().equals("")) {
			return;
		}
		app.markOrderInProgress(id[2].getText());
		last = 0;
		load();
    }
	public void mp4(ActionEvent e){
		if(id[3].getText().equals("")) {
			return;
		}
		app.markOrderInProgress(id[3].getText());
		last = 0;
		load();
    }
	public void mp5(ActionEvent e){
		if(id[4].getText().equals("")) {
			return;
		}
		app.markOrderInProgress(id[4].getText());
		last = 0;
		load();
    }
	
	public void md1(ActionEvent e){
		if(id[0].getText().equals("")) {
			return;
		}
	    app.markOrderDeliverd(id[0].getText());
	    last = 0;
		set(app);
    }
	public void md2(ActionEvent e){
		if(id[1].getText().equals("")) {
			return;
		}
		app.markOrderDeliverd(id[1].getText());
		last = 0;
		set(app);
    }
	public void md3(ActionEvent e){
		if(id[2].getText().equals("")) {
			return;
		}
		app.markOrderDeliverd(id[2].getText());
		last = 0;
		set(app);
    }
	public void md4(ActionEvent e){
		if(id[3].getText().equals("")) {
			return;
		}
		app.markOrderDeliverd(id[3].getText());
		last = 0;
		set(app);
    }
	public void md5(ActionEvent e){
		if(id[4].getText().equals("")) {
			return;
		}
		app.markOrderDeliverd(id[4].getText()); 
		last = 0;
		set(app);
    }
	private void sc(String det) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("order_det.fxml"));
		Parent parent = (Parent) loader.load();
		order_det controller = loader.getController();
		controller.set(det);
		Stage s = new Stage();
		s.setScene(new Scene(parent));
		s.show();
	}
	private Order getOrder(String idd) {
		int id = Integer.valueOf(idd);
		for(int i = 0; i < orders.size(); i++) {
			if(orders.get(i).getId() == id) {
				return orders.get(i);
			}
		}
		return null;
	}
	public void refresh(ActionEvent e) {
		last = 0;
		set(app);
	}
}
