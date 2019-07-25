/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnstiles;

import com.jfoenix.controls.JFXTimePicker;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;


public class DataExcelDayEdit {
    
    private Time timeEntry;
    private Time timeExit;
    private long timeAbsenceSecond;
    private String strTimeAbsenceSecond;
    
    public ControllerFileTurnstilesLoading controllerFileTurnstilesLoading;
    public boolean error;
    private JFXTimePicker timeEntryTimePicker;
    private JFXTimePicker timeExitTimePicker;
    public CheckBox firstEntryLastExit; 
    private TextField timeAbsenceSecondTextField;


    public DataExcelDayEdit() {
         
         StringConverter converter = new LocalTimeStringConverter(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM), null);
         this.firstEntryLastExit = new CheckBox();  
         this.timeEntryTimePicker = new JFXTimePicker();
         this.timeEntryTimePicker.setConverter(converter);
         this.timeEntryTimePicker.setEditable(false);
         this.timeExitTimePicker = new JFXTimePicker();
         this.timeExitTimePicker.setConverter(converter);
         this.timeExitTimePicker.setEditable(false);
         this.timeAbsenceSecondTextField = new TextField();
         this.timeAbsenceSecondTextField.setEditable(false);
         this.firstEntryLastExit.setOnAction(event -> {
             SetHandleMouseClickedController(); 
             ControlValue();              
          });
         this.timeEntryTimePicker.setOnAction(event -> {
              SetHandleMouseClickedController(); 
              LocalTime timeStr = timeEntryTimePicker.getValue();
                   if (timeStr != null)
                      {
                        ControlValue();
                      }
          });
         this.timeExitTimePicker.setOnAction(event -> {
              SetHandleMouseClickedController(); 
              LocalTime timeStr = timeExitTimePicker.getValue();
                   if (timeStr != null)
                      {
                        ControlValue();
                      }
          });
         
    }

    public void  SetHandleMouseClickedController() {
        this.controllerFileTurnstilesLoading.CurrentDataExcelDayEditHandleMouseClicked(this);
    } 
    
    
    public Time getTimeEntry() {
        return timeEntry;
    }

    public void setTimeEntry(Time timeEntry) {
        this.timeEntry = timeEntry;
    }

    public Time getTimeExit() {
        return timeExit;
    }

    public void setTimeExit(Time timeExit) {
        this.timeExit = timeExit;
    }

    public long getTimeAbsenceSecond() {
        return timeAbsenceSecond;
    }

    public void setTimeAbsenceSecond(long timeAbsenceSecond) {
        this.timeAbsenceSecond = timeAbsenceSecond;
    }

    
    
    public String getStrTimeAbsenceSecond() {
        return strTimeAbsenceSecond;
    }

    public void setStrTimeAbsenceSecond(String strTimeAbsenceSecond) {
        this.strTimeAbsenceSecond = strTimeAbsenceSecond;
    }

    
    
    public TextField getTimeAbsenceSecondTextField() {
        return timeAbsenceSecondTextField;
    }

    public void setTimeAbsenceSecondTextField(TextField timeAbsenceSecondTextField) {
        this.timeAbsenceSecondTextField = timeAbsenceSecondTextField;
    }

    public CheckBox getFirstEntryLastExit() {
        return firstEntryLastExit;
    }

    public void setFirstEntryLastExit(CheckBox firstEntryLastExit) {
        this.firstEntryLastExit = firstEntryLastExit;
    }

    private void ControlValue(){
     error = false;
     LocalTime exitTimeStr = timeExitTimePicker.getValue();
         if (exitTimeStr != null)
            { 
              String StrExitTime = exitTimeStr.toString().substring(0, 2);
                  if (StrExitTime.equals("07") == false && StrExitTime.equals("08") == false && StrExitTime.equals("09") == false && StrExitTime.equals("10") == false && StrExitTime.equals("11") == false && StrExitTime.equals("12") == false && StrExitTime.equals("13") == false && StrExitTime.equals("14") == false && StrExitTime.equals("15") == false && StrExitTime.equals("16") == false && StrExitTime.equals("17") == false && StrExitTime.equals("18") == false)
                     {
                       Alert alert = new Alert(Alert.AlertType.ERROR);
                       alert.setTitle("Ошибка");
                       alert.setHeaderText("Ошибка в значении времени ухода");
                       alert.setContentText("При выборе значения времени если вы указываете время до 12 часов необходимо указывать AM иначе указывайте PM. Время вы можете указывать в пределах от 7 до 18 часов.");
                       alert.showAndWait();
                       SetError("Ошибка в значении времени ухода");
                       return;
                     }
            }
     LocalTime entryTimeStr = timeEntryTimePicker.getValue();
         if (entryTimeStr != null)
            { 
             String StrEntryTime =  entryTimeStr.toString().substring(0, 2);
                if (StrEntryTime.equals("07") == false && StrEntryTime.equals("08") == false && StrEntryTime.equals("09") == false && StrEntryTime.equals("10") == false && StrEntryTime.equals("11") == false && StrEntryTime.equals("12") == false && StrEntryTime.equals("13") == false && StrEntryTime.equals("14") == false && StrEntryTime.equals("15") == false && StrEntryTime.equals("16") == false && StrEntryTime.equals("17") == false && StrEntryTime.equals("18") == false)
                   {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText("Ошибка в значении время прихода");
                    alert.setContentText("При выборе значения времени если вы указываете время до 12 часов необходимо указывать AM иначе указывайте PM. Время вы можете указывать в пределах от 7 до 18 часов.");
                    alert.showAndWait();
                    SetError("Ошибка в значении время прихода");
                    return;
                   }
            }
     
        if (exitTimeStr == null || entryTimeStr == null)
           { 
            //проверка на значения null в этих полях идет при сохранении в базе данных
            return;
           }
        
        if (firstEntryLastExit.isSelected() == true)
            {
             if (entryTimeStr.isAfter(exitTimeStr))
                {
                  Alert alert = new Alert(Alert.AlertType.ERROR);
                  alert.setTitle("Ошибка");
                  alert.setHeaderText("Ошибка время прихода должно быть раньше времени ухода");
                  alert.setContentText("Время прихода должно быть раньше времени ухода!");
                  alert.showAndWait();
                  SetError("Время прихода должно быть раньше времени ухода!");
                  return;
                } 
            }
        else
            {
              if (exitTimeStr.isAfter(entryTimeStr))
                 {
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle("Ошибка");
                   alert.setHeaderText("Ошибка время ухода должно быть раньше времени прихода");
                   alert.setContentText("Время ухода должно быть раньше времени прихода!");
                   alert.showAndWait();
                   SetError("Время ухода должно быть раньше времени прихода!");
                   return;
                 } 
            }
      
     timeEntry = java.sql.Time.valueOf(entryTimeStr);
     timeExit = java.sql.Time.valueOf(exitTimeStr);  
     long second = 0;
          if (firstEntryLastExit.isSelected() == true)
             { 
                second = (timeExit.getTime() - timeEntry.getTime())/1000;   
                     if (second > 3600)
                        {
                          second = second - 3600;
                        }
             }
          else
             {
                second = (timeEntry.getTime() - timeExit.getTime())/1000;
             }
           
          if (second < 0)
             {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Разница в значении времени не может быть отрицательной");
                alert.setContentText("Разница в значении времени отрицательна!");
                alert.showAndWait();
                SetError("Разница в значении времени отрицательна!");
              }
          else
             {
                int hour = (int) (second / 3600);
                int min = (int) ((second - hour * 3600) / 60);
                strTimeAbsenceSecond = "ч: " + hour + " м: " + min;
                timeAbsenceSecond = second;
                timeAbsenceSecondTextField.setText(strTimeAbsenceSecond);
              }
          
    }      
    private void SetError(String strError){
     timeAbsenceSecondTextField.setText(strError);
     timeEntry = null;
     timeExit = null;
     timeAbsenceSecond = 0;
     strTimeAbsenceSecond = "";
     error = true;
    }
    
    public JFXTimePicker getTimeEntryTimePicker() {
        return timeEntryTimePicker;
    }

    public void setTimeEntryTimePicker(JFXTimePicker timeEntryTimePicker) {
        this.timeEntryTimePicker = timeEntryTimePicker;
    }

    public JFXTimePicker getTimeExitTimePicker() {
        return timeExitTimePicker;
    }

    public void setTimeExitTimePicker(JFXTimePicker timeExitTimePicker) {
        this.timeExitTimePicker = timeExitTimePicker;
    }
    
   
    
    
    
    
}
