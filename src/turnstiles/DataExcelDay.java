package turnstiles;

import com.jfoenix.controls.JFXDatePicker;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

public class DataExcelDay {
    
    VBox vbox;
    public Label label1;
    boolean error;
    private Date tekDay;
    private String fio;
    public ObservableList<DataExcel> dataExcel;
    public ObservableList<DataExcel> dataExcelErr;
    public ObservableList<DataExcelDayEdit> dataExcelDayEdit;
    public ControllerFileTurnstilesLoading controller;
    
    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    
    public Date getTekDay() {
        return tekDay;
    }

    public void setTekDay(Date tekDay) {
        this.tekDay = tekDay;
    }

    public VBox getVbox() {
        return vbox;
    }

    public void setVbox(VBox vbox) {
        this.vbox = vbox;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public DataExcelDay() {
     dataExcel = FXCollections.observableArrayList(); 
     dataExcelErr = FXCollections.observableArrayList(); 
     dataExcelDayEdit = FXCollections.observableArrayList(); 
    }
    
    public <T extends Control> void  SetHandleMouseClickedController(T Control, Label label1) {
        Control.setOnMouseClicked((MouseEvent event) -> {
                              this.controller.CurrentlabelHandleMouseClickedDataExcelDay(this, label1);
                            }
            );  
    } 
    
    
    public void FillInTheDisplayFields (ControllerFileTurnstilesLoading controller) {
       this.controller = controller;
       SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
       String labelStr = dtFormat.format(tekDay);
       labelStr.replaceAll("-", ".");
       label1 = new Label(labelStr);
       label1.setFont(Font.font("System",  FontPosture.ITALIC, 14));
       label1.setTextFill(Color.WHITE);
       label1.setVisible(true);
       label1.setAlignment(Pos.TOP_LEFT);
       label1.setPrefWidth(100);
       SetHandleMouseClickedController(label1, label1);
       VBox VBox1 = new VBox();
       VBox1.autosize();
       VBox1.getChildren().add(label1); 
       Separator separator2 = new Separator();
       SetHandleMouseClickedController(separator2, label1);
       VBox1.getChildren().add(separator2);
       this.vbox = VBox1;       
    } 
    
 
    
}
