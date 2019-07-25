
package turnstiles;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;


public class TotalsTurnstilesDisplay {
  
     VBox vbox;
     ControllerDataTurnstilesLoading controllerDataTurnstilesLoading;
     ControllerTotalsTurnstilesLoading controllerTotalsTurnstilesLoading;
     public Label labelM[];
     public TextField textFieldM[];

    public TotalsTurnstilesDisplay() {
    }

    public VBox getVbox() {
        return vbox;
    }

    public void setVbox(VBox vbox) {
        this.vbox = vbox;
    }
     
    public <T extends Control> void  SetHandleMouseClickedController(T Control, Label label) {
        Control.setOnMouseClicked((MouseEvent event) -> {
                             if (controllerDataTurnstilesLoading != null)
                                {
                                  this.controllerDataTurnstilesLoading.CurrentlabelHandleMouseClickedTotalsTurnstilesDisplay(label);
                                }
                             else
                                {
                                  this.controllerTotalsTurnstilesLoading.CurrentlabelHandleMouseClickedTotalsTurnstilesDisplay(label);
                                }
                            }
            );  
    } 
    
    public void FillInTheDisplayFields (List dataTotalsTurnstiles, ControllerDataTurnstilesLoading controllerDataTurnstilesLoading, ControllerTotalsTurnstilesLoading controllerTotalsTurnstilesLoading) {
       this.controllerDataTurnstilesLoading = controllerDataTurnstilesLoading;
       this.controllerTotalsTurnstilesLoading = controllerTotalsTurnstilesLoading;
       int interperson = 4;
       int enlargement = 0;
       int count = dataTotalsTurnstiles.size();
       labelM = new Label[count]; 
       textFieldM = new TextField[count];
       SimpleDateFormat dtFormat = new SimpleDateFormat("EEEE - dd");
       HBox HBoxItog = new HBox();
            while("1" == "1") {
                if (count ==0)
                    {
                      break;
                    }
                if (interperson > count)
                    {
                       interperson = count;
                       count = 0;
                    }
                else{count = count - interperson;}
                 
                VBox VBox = new VBox();
                int tekinter = enlargement;
                enlargement = enlargement + interperson;   
                    while("1" == "1") {
                          tekinter = tekinter + 1;
                              if (tekinter > enlargement)
                                  {break;}
                          Object element[] = (Object[]) dataTotalsTurnstiles.get(tekinter - 1);
                          String labelStr = dtFormat.format(element[0]);
                          Label label = new Label(labelStr);
                          label.setAccessibleText(element[0].toString());
                          label.setFont(Font.font("System",  FontPosture.ITALIC, 12));
                          label.setTextFill(Color.WHITE);
                          label.setVisible(true);
                          label.setAlignment(Pos.TOP_CENTER);
                          label.setPrefWidth(155);
                          labelM[tekinter - 1] = label;
                          SetHandleMouseClickedController(label, label);
                          TextField textField =  new TextField();
                          textField.setText(element[1].toString());
                          textField.setFont(Font.font("System",  FontPosture.ITALIC, 12));
                          textField.setAlignment(Pos.TOP_LEFT);
                          textField.setPrefWidth(60);
                          SetHandleMouseClickedController(textField, label);
                          textFieldM[tekinter - 1] = textField;
                          HBox HBox = new HBox();
                          HBox.getChildren().add(label); 
                          Separator separator = new Separator();
                          separator.setVisible(false);
                          SetHandleMouseClickedController(separator, label);
                          HBox.getChildren().add(separator);
                          HBox.getChildren().add(textField);  
                          VBox.getChildren().add(HBox);
                          Separator separator2 = new Separator();
                          separator2.setVisible(false);
                          SetHandleMouseClickedController(separator2, label);
                          VBox.getChildren().add(separator2);
                         }
                HBoxItog.getChildren().add(VBox);  
                Separator separator = new Separator();
                separator.setVisible(false);
                HBoxItog.getChildren().add(separator);
            }
        VBox VBox1 = new VBox();
        VBox1.getChildren().add(HBoxItog); 
        VBox1.autosize();
        
        this.vbox = VBox1;
    }
     
}
