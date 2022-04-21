package fornt.front;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class desc {
	@FXML
	private Label desc;
	
	public void set(String description) {
		desc.setText(description);
	}
}
