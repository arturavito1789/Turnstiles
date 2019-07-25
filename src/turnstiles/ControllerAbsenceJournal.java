/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnstiles;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class ControllerAbsenceJournal implements Initializable {
    
    private Stage dialogStage;
    private Turnstiles mainApp;
    private AbsenceJournal currentAbsenceJournal;
    private boolean addOperation;
    public boolean updateDb;
      
    @FXML  
    private  Text caption;
    @FXML  
    private  TextField reasonAbsence;
    @FXML
    private JFXDatePicker tekDay;
    @FXML
    private JFXTimePicker exitTime;
    @FXML
    private JFXTimePicker entryTime;
    @FXML
    private ImageView foto;
    
    
    private Employees currentEmployees;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
        Tooltip reasonAbsenceTooltip = new Tooltip("Причина отсутствия");
        Tooltip tekDayTooltip = new Tooltip("Текущий день");
        Tooltip exitTimeTooltip = new Tooltip("Время ухода");
        Tooltip entryTimeTooltip = new Tooltip("Время прихода");
        
        reasonAbsence.setTooltip(reasonAbsenceTooltip);
        tekDay.setTooltip(tekDayTooltip);
        exitTime.setTooltip(exitTimeTooltip);
        entryTime.setTooltip(entryTimeTooltip);
        StringConverter converter = new LocalTimeStringConverter(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM), null);
        exitTime.setConverter(converter);
        entryTime.setConverter(converter);
    
    }
   public void setTekDay(boolean edit) {
        if (edit == false)
            {
             //если редактируем текущую запись день не устанавливаем - это может быть данные за прошлый период
              tekDay.setValue(LocalDate.now());
              addOperation = true;
            }
        else 
            {
              tekDay.setValue(currentAbsenceJournal.getTekDay().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            }
        tekDay.setOnAction(event -> {
            if (edit == false)
                {tekDay.setValue(LocalDate.now());}
            else {tekDay.setValue(currentAbsenceJournal.getTekDay().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());}
        });
   }
   
    @FXML
    private void FontHandleAction(MouseEvent event) {
        this.dialogStage.close();
    }
    
    @FXML
    private void SaveEntityDbAction(ActionEvent event) {
       String reasonAbsenceStr = reasonAbsence.getText();
       LocalTime exitTimeStr = exitTime.getValue();
       LocalTime entryTimeStr =entryTime.getValue();
     
       if ("".equals(reasonAbsenceStr))
          { 
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка заполнения поля причина отсутствия");
            alert.setContentText("Не указана причина отсутствия!");
            alert.showAndWait();
            return;
          }
       
        if (exitTimeStr == null)
          { 
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Ошибка");
             alert.setHeaderText("Ошибка заполнения поля время ухода");
             alert.setContentText("Не указано время ухода!");
             alert.showAndWait();
             return;
           }
      
        if (entryTimeStr == null)
           { 
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("Ошибка");
              alert.setHeaderText("Ошибка заполнения поля время прихода");
              alert.setContentText("Не указано время прихода! Если вы не уверены когда придете вы можете указать приблизительное значение впоследствии его можно будет изменить.");
              alert.showAndWait();
              return;
            }
        if (exitTimeStr.isAfter(entryTimeStr))
            {
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("Ошибка");
              alert.setHeaderText("Ошибка время ухода больше времени прихода");
              alert.setContentText("Время ухода больше времени прихода!");
              alert.showAndWait();
              return;
             } 
        
        String StrEntryTime =  entryTimeStr.toString().substring(0, 2);
        String StrExitTime = exitTimeStr.toString().substring(0, 2);
       
        if (StrEntryTime.equals("07") == false && StrEntryTime.equals("08") == false && StrEntryTime.equals("09") == false && StrEntryTime.equals("10") == false && StrEntryTime.equals("11") == false && StrEntryTime.equals("12") == false && StrEntryTime.equals("13") == false && StrEntryTime.equals("14") == false && StrEntryTime.equals("15") == false && StrEntryTime.equals("16") == false && StrEntryTime.equals("17") == false && StrEntryTime.equals("18") == false)
           {
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("Ошибка");
              alert.setHeaderText("Ошибка в значении время прихода");
              alert.setContentText("При выборе значения времени если вы указываете время до 12 часов необходимо указывать AM иначе указывайте PM. Время вы можете указывать в пределах от 7 до 18 часов.");
              alert.showAndWait();
              return;
           }
          if (StrExitTime.equals("07") == false && StrExitTime.equals("08") == false && StrExitTime.equals("09") == false && StrExitTime.equals("10") == false && StrExitTime.equals("11") == false && StrExitTime.equals("12") == false && StrExitTime.equals("13") == false && StrExitTime.equals("14") == false && StrExitTime.equals("15") == false && StrExitTime.equals("16") == false && StrExitTime.equals("17") == false && StrExitTime.equals("18") == false)
           {
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("Ошибка");
              alert.setHeaderText("Ошибка в значении время ухода");
              alert.setContentText("При выборе значения времени если вы указываете время до 12 часов необходимо указывать AM иначе указывайте PM. Время вы можете указывать в пределах от 7 до 18 часов.");
              alert.showAndWait();
              return;
           }
        
        Session currentsession =  this.mainApp.sessionfactory.openSession();
             if (addOperation == false)
                {
                 currentAbsenceJournal = currentsession.get(AbsenceJournal.class, currentAbsenceJournal.getIdAbsenceJournal());
                }
      
        currentAbsenceJournal.setReasonAbsence(reasonAbsenceStr);
        currentAbsenceJournal.setEntryTime(entryTimeStr);
        currentAbsenceJournal.setExitTime(exitTimeStr);
        currentAbsenceJournal.setTekDay(tekDay.getValue());
        currentAbsenceJournal.setIdEmployees(currentEmployees.getPrimaryKey().getIdEmployees());
        currentAbsenceJournal.setIdSubdivision(currentEmployees.getPrimaryKey().getIdSubdivision());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentAbsenceJournal.getTekDay());
        calendar.set(Calendar.DAY_OF_MONTH,01);
        Date firstDayMonth = calendar.getTime();
        currentAbsenceJournal.setFirstDayMonth(firstDayMonth);
        Transaction transaction =  currentsession.beginTransaction();  
            try {
                 currentsession.save(currentAbsenceJournal);
                 transaction.commit();
                 currentsession.close(); 
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setTitle("Сообщение");
                 alert.setHeaderText("Сохранения записи");
                 alert.setContentText("Запись успешно сохранена в базе данных");
                 alert.showAndWait();  
                 updateDb = true;
                 addOperation = false;
                }
            catch (Exception ex) {
                 transaction.rollback();
                 currentsession.close(); 
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Ошибка");
                 alert.setHeaderText("Ошибка сохранения записи");
                 alert.setContentText("Не удалось вставить запись. Скорее всего запись с такими значениями уже имеется.");
                 alert.showAndWait();
                }
    } 

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMainApp(Turnstiles mainApp) {
        this.mainApp = mainApp;
    }

    public void setCurrenAbsenceJournal(AbsenceJournal currentAbsenceJournal) {
        this.currentAbsenceJournal = currentAbsenceJournal;
        this.currentEmployees = this.currentAbsenceJournal.getEmployees();
        Image img = new Image(new ByteArrayInputStream(currentEmployees.getFoto()));
        foto.setImage(img);  
        caption.setText("Журнал отсутствия: " + currentEmployees.getFio());
        reasonAbsence.setText(this.currentAbsenceJournal.getReasonAbsence());
        Time entryTimeA = this.currentAbsenceJournal.getEntryTime();
             if (entryTimeA != null) 
                {
                 entryTime.setValue(entryTimeA.toLocalTime());
                }
        Time exitTimeA = this.currentAbsenceJournal.getExitTime();
             if (exitTimeA != null) 
                {
                 exitTime.setValue(exitTimeA.toLocalTime());
                }     
            if (currentAbsenceJournal.getReasonAbsence() == null)
               {reasonAbsence.setText("Обед");}
    }
  
 public class Result {
    public boolean updateDb;
    public AbsenceJournal currentAbsenceJournal;

    public Result(boolean update, AbsenceJournal absenceJournal) {
        this.updateDb = update;
        this.currentAbsenceJournal = absenceJournal;
    }
    public Result(){}
}   
  
public Result returnResult() {
     Result result = new Result(updateDb, currentAbsenceJournal);
     return result;
}   
    
    
}