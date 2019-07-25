package turnstiles;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ControllerInput_Time implements Initializable {
 
    @FXML
    private TextField hours;
    @FXML
    private TextField minutes;
    @FXML 
    private Text LabelM;
    
     @FXML 
    private HBox hBoxM;
   
    private Stage dialogStage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    UnaryOperator<TextFormatter.Change> filter = change -> {
                   String text = change.getText();
                        if (text.matches("[0-9]*")) {
                            return change;
                        }
                   return null;
              };
    TextFormatter<String> textFormatterHours = new TextFormatter<>(filter);    
    hours.setTextFormatter(textFormatterHours);
    TextFormatter<String> textFormatterMinutes = new TextFormatter<>(filter); 
    minutes.setTextFormatter(textFormatterMinutes);
    
    }

    public void setH(int h) {
        this.hours.setText(String.valueOf(h));
    }

    
    public void setM(int m) {
        this.minutes.setText(String.valueOf(m));
    }
    
    public void VisibleM(boolean showM) {
        minutes.setVisible(showM);
        LabelM.setVisible(showM);
        hBoxM.setVisible(showM);
    }
    
    
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    
     @FXML
    private void FontHandleAction(MouseEvent event) {
        this.dialogStage.close();
    }
    
     @FXML
    private void OK(ActionEvent event) {
        String strHours = hours.getText();
            if ("".equals(strHours))
                { 
                  Alert alert = new Alert(Alert.AlertType.ERROR);
                  alert.setTitle("Ошибка");
                  alert.setHeaderText("Ошибка заполнения поля часы");
                  alert.setContentText("Необходимо указать часы либо 0 вместо них!");
                  alert.showAndWait();
                  return;
                }
            if (LabelM.isVisible() == true)
               {
                 String strMinutes = minutes.getText();
                     if ("".equals(strMinutes))
                        { 
                          Alert alert = new Alert(Alert.AlertType.ERROR);
                          alert.setTitle("Ошибка");
                          alert.setHeaderText("Ошибка заполнения поля минуты");
                          alert.setContentText("Необходимо указать минуты либо 0 вместо них!");
                          alert.showAndWait();
                          return;
                        }
               }     
        this.dialogStage.close(); 
    }
    
    
        
public class Result {
    public int h;
    public int m;
   
    public Result(int h, int m) {
        this.h = h;
        this.m = m;
    }
    
     public Result(){}
}   

public ControllerInput_Time.Result returnResult() {
       ControllerInput_Time.Result result = new ControllerInput_Time.Result(Integer.parseInt(this.hours.getText()), Integer.parseInt(this.minutes.getText()));
       return result;
    }    

}
