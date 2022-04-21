package fornt.front;

import Back.App;
import Back.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;

public class Menu {
    @FXML
    private Label name1;
    @FXML
    private Label name2;
    @FXML
    private Label name3;
    @FXML
    private Label name4;
    @FXML
    private Label name5;
    @FXML
    private Label price1;
    @FXML
    private Label price2;
    @FXML
    private Label price3;
    @FXML
    private Label price4;
    @FXML
    private Label price5;
    @FXML
    private Label error;
    private int[] id = new int[5];
    private Label[] names = new Label[5];
    private Label[] price= new Label[5];
    private int last=0;
    private ArrayList<Item> items;
    private App app;
    public void set(App app){
        this.app=app;
        items=app.getMenu();
        id[0] = 0;
        id[1] = 0;
        id[2] = 0;
        id[3] = 0;
        id[4] = 0;
        names[0] = name1;
        names[1] = name2;
        names[2] = name3;
        names[3] = name4;
        names[4] = name5;
        price[0]=price1;
        price[1]=price2;
        price[2]=price3;
        price[3]=price4;
        price[4]=price5;
        load();
    }
    public void load(){
        if(last==items.size()){
            return;
        }
        int i = 0;
        int j =0;
        for(i = last; i < last+5 && i < items.size(); i++){
            id[j]=items.get(i).getId();
            price[j].setText(String.valueOf(items.get(i).getPrice()));
            names[j].setText(items.get(i).getName());
            j++;
        }
        last=i;
        for( j=j ;j<5;j++){
            id[j]=0;
            price[j].setText("");
            names[j].setText("");
        }

    }
    public void next(ActionEvent e){
        load();
    }
    public void prev(ActionEvent e){
        int temp=last-(last%5);
        if(last == temp){
            temp = temp - 10;
        }else{
            temp = temp-5;
        }

        if(temp<0){
            return;
        }
        last=temp;
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
    public void add1(ActionEvent e){
        if(id[0]==0){
            return;
        }
        else{
            app.addItemToCart(id[0]);
            error.setText(names[0].getText() + " added to cart!");
        }
    }
    public void add2(ActionEvent e){
        if(id[1]==0){
            return;
        }
        else{
            app.addItemToCart(id[1]);
            error.setText(names[1].getText() + " added to cart!");
        }
    }
    public void add3(ActionEvent e){
        if(id[2]==0){
            return;
        }
        else{
            app.addItemToCart(id[2]);
            error.setText(names[2].getText() + " added to cart!");
        }
    }
    public void add4(ActionEvent e){
        if(id[3]==0){
            return;
        }
        else{
            app.addItemToCart(id[3]);
            error.setText(names[3].getText() + " added to cart!");
        }
    }
    public void add5(ActionEvent e){
        if(id[4]==0){
            return;
        }
        else{
            app.addItemToCart(id[4]);
            error.setText(names[4].getText() + " added to cart!");
        }
    }
    public void cart(ActionEvent e) throws IOException {
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
    public void d1(ActionEvent e)throws IOException {
    	if(id[0] == 0) {
    		return;
    	}
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("desc.fxml"));
        Parent parent = (Parent) loader.load();
        desc controller = loader.getController();
        controller.set(addn(app.getItemDetails(id[0]).get(3)));
        Stage s = new Stage();
        s.setScene(new Scene(parent));
        s.show();
    }public void d2(ActionEvent e)throws IOException {
    	if(id[1] == 0) {
    		return;
    	}
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("desc.fxml"));
        Parent parent = (Parent) loader.load();
        desc controller = loader.getController();
        controller.set(addn(app.getItemDetails(id[1]).get(3)));
        Stage s = new Stage();
        s.setScene(new Scene(parent));
        s.show();
    }public void d3(ActionEvent e)throws IOException {
    	if(id[2] == 0) {
    		return;
    	}
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("desc.fxml"));
        Parent parent = (Parent) loader.load();
        desc controller = loader.getController();
        controller.set(addn(app.getItemDetails(id[2]).get(3)));
        Stage s = new Stage();
        s.setScene(new Scene(parent));
        s.show();
    }public void d4(ActionEvent e)throws IOException {
    	if(id[3] == 0) {
    		return;
    	}
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("desc.fxml"));
        Parent parent = (Parent) loader.load();
        desc controller = loader.getController();
        controller.set(addn(app.getItemDetails(id[3]).get(3)));
        Stage s = new Stage();
        s.setScene(new Scene(parent));
        s.show();
    }public void d5(ActionEvent e)throws IOException {
    	if(id[4] == 0) {
    		return;
    	}
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("desc.fxml"));
        Parent parent = (Parent) loader.load();
        desc controller = loader.getController();
        controller.set(addn(app.getItemDetails(id[4]).get(3)));
        Stage s = new Stage();
        s.setScene(new Scene(parent));
        s.show();
    }
    public String addn(String d) {
    	int count = 0;
    	for(int i = 0; i < d.length(); i++) {
    		if(d.charAt(i) == ' ') {
    			count++;
    			if(count == 4) {
    				d = d.substring(0, i) + "\n" + d.substring(i + 1);
    				count = 0;
    			}
    		}
    	}
    	return d;
    }
    public void hist(ActionEvent e) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("history.fxml"));
        Parent parent = (Parent) loader.load();
        history controller = loader.getController();
        controller.set(app);
        Stage s = new Stage();
        s.setScene(new Scene(parent));
        s.initStyle(StageStyle.UNDECORATED);
        s.show();
        final Node source = (Node) e.getSource();
        final Stage curr = (Stage) source.getScene().getWindow();
        curr.close();
    }
}
