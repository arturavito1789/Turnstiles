/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnstiles;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ControllerToolTipDolzhnostiEmployees implements Initializable {
   
      @FXML
    private Label labelTool;
    
      @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }
    
    public void setValueLabel(String value) {
      labelTool.setText(value);
    }
    
}
