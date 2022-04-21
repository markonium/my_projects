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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;

public class MenuEdit {
    @FXML
    private Label id1;
    @FXML
    private Label name1;
    @FXML
    private Label id2;
    @FXML
    private Label name2;
    @FXML
    private Label id3;
    @FXML
    private Label name3;
    @FXML
    private Label id4;
    @FXML
    private Label name4;
    @FXML
    private Label id5;
    @FXML
    private Label name5;
    @FXML
    private TextField name;
    @FXML
    private TextField type;
    @FXML
    private TextField price;
    @FXML
    private TextField description;
    @FXML
    private Label error;
    private Label[] id = new Label[5];
    private Label[] names = new Label[5];
    private int last=0;
    private ArrayList<Item> items;
    private App app;
    public void set(App app){
        this.app=app;
        items=app.getMenu();
        id[0] = id1;
        id[1] = id2;
        id[2] = id3;
        id[3] = id4;
        id[4] = id5;
        names[0] = name1;
        names[1] = name2;
        names[2] = name3;
        names[3] = name4;
        names[4] = name5;
        load();
    }
    public void load(){
    	if(items.size() == 0) {
    		id[0].setText("");
    		names[0].setText("");
    		return;
    	}
        if(last==items.size()){
            return;
        }
        int i = 0;
        int j =0;
        for(i = last; i < last+5 && i < items.size(); i++){
            id[j].setText(String.valueOf(items.get(i).getId()));
            names[j].setText(items.get(i).getName());
            j++;
        }
        last=i;
        for( j=j ;j<5;j++){
            id[j].setText("");
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
    public void delete1(ActionEvent e){
        if(id[0].getText().equals("")){
            return;
        }else{
            app.deleteItem(Integer.parseInt(id[0].getText()));
            items=app.getMenu();
            last=0;
            load();
        }
    }public void delete2(ActionEvent e){
        if(id[1].getText().equals("")){
            return;
        }else{
            app.deleteItem(Integer.parseInt(id[1].getText()));
            items=app.getMenu();
            last=0;
            load();
        }
    }public void delete3(ActionEvent e){
        if(id[2].getText().equals("")){
            return;
        }else{
            app.deleteItem(Integer.parseInt(id[2].getText()));
            items=app.getMenu();
            last=0;
            load();
        }
    }public void delete4(ActionEvent e){
        if(id[3].getText().equals("")){
            return;
        }else{
            app.deleteItem(Integer.parseInt(id[3].getText()));
            items=app.getMenu();
            last=0;
            load();
        }
    }public void delete5(ActionEvent e){
        if(id[4].getText().equals("")){
            return;
        }else{
            app.deleteItem(Integer.parseInt(id[4].getText()));
            items=app.getMenu();
            last=0;
            load();
        }
    }
    public void add(ActionEvent e){
        if (name.getText().equals("")||type.getText().equals("")||price.getText().equals("")||description.getText().equals("")){
            error.setText("Invalid input");
            return;
        }
        double p=0.0;
        try{
            p=Double.parseDouble(price.getText());

        }catch (Exception exception){
            error.setText("Enter a numeric value for price");
            return;
        }
        app.updateItem(0,name.getText(),type.getText(),description.getText(),p);
        items=app.getMenu();
        last=0;
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
