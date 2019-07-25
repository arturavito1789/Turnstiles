
package turnstiles;

import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


public class ControllerTotalsTurnstilesLoading implements Initializable{
    
    private Stage dialogStage;
     
    @FXML
    private JFXDatePicker firstDayMonth;
    
    
    @FXML
    private TableView tableviewTotalsTurnstiles;
    
    
    private Turnstiles mainApp;
    
    private ObservableList<TotalsTurnstilesDisplay> dataTotalsTurnstilesDisplay;
    boolean wasPressed = false;
    private Label currentlabeldataTotalsTurnstilesDisplay;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn vboxTotalsTurnstilesDisplay = new TableColumn("vbox");      
        tableviewTotalsTurnstiles.getColumns().addAll(vboxTotalsTurnstilesDisplay);
        vboxTotalsTurnstilesDisplay.setCellValueFactory(
              new PropertyValueFactory<TotalsTurnstilesDisplay,String>("vbox")
             );
         
         dataTotalsTurnstilesDisplay = FXCollections.observableArrayList(); 
         firstDayMonth.setEditable(false);
        
        
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setMainApp(Turnstiles mainApp) {
        this.mainApp = mainApp;
    }
    
    @FXML
    private void FontHandleAction(MouseEvent event) {
         this.dialogStage.close();
    }
    
    
     @FXML
    private void FormDataHandleAction(ActionEvent event) {
      if (wasPressed == true)
         {
          wasPressed = false;
          return;         
         }
      tableviewTotalsTurnstiles.getItems().clear();
      currentlabeldataTotalsTurnstilesDisplay = null;
      Date dataValue = java.sql.Date.valueOf(firstDayMonth.getValue());  
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(dataValue);
      calendar.set(Calendar.DAY_OF_MONTH,01);
      Date firstDayMonthDate = calendar.getTime();
           if (dataValue.equals(firstDayMonthDate) == false)
              { 
                wasPressed = true; //вызывается второй раз после setValue - соответсвенно второй вызов не обрабатываем
                firstDayMonth.setValue(firstDayMonthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
              }
     
      Session currentsession =  Turnstiles.sessionfactory.openSession();
      List dataTotalsTurnstiles = currentsession.createSQLQuery("select distinct FileTurnstiles.tekDay, ISNULL(TotalsTurnstiles.workingHours, 8) From FileTurnstiles LEFT JOIN TotalsTurnstiles ON FileTurnstiles.firstDayMonth = :firstDayMonth and FileTurnstiles.firstDayMonth  =  TotalsTurnstiles.firstDayMonth and FileTurnstiles.tekDay  =  TotalsTurnstiles.tekDay where FileTurnstiles.firstDayMonth = :firstDayMonth").setParameter("firstDayMonth", firstDayMonthDate).list();
           if (dataTotalsTurnstiles.size() == 0)
              {
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setTitle("Предупреждение");
                 alert.setHeaderText("Нет данных для загрузки");
                 alert.setContentText("За месяц " + firstDayMonthDate + " нет данных для загрузки. Видимо вы не загрузили данные по турнекетам.");
                 alert.showAndWait(); 
                 return; 
              }
       TotalsTurnstilesDisplay totalsTurnstilesDisplay = new TotalsTurnstilesDisplay(); 
       ControllerDataTurnstilesLoading controllerDataTurnstilesLoading = null;
       totalsTurnstilesDisplay.FillInTheDisplayFields(dataTotalsTurnstiles, controllerDataTurnstilesLoading, this);
       dataTotalsTurnstilesDisplay.add(totalsTurnstilesDisplay);
       tableviewTotalsTurnstiles.setItems(dataTotalsTurnstilesDisplay);
       currentsession.close();
    }
    
     @FXML
    private void SaveEntityDbAction(ActionEvent event) {
        if (dataTotalsTurnstilesDisplay.size() == 0)
           {
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("Предупреждение");
             alert.setHeaderText("Нет данных для загрузки");
             alert.setContentText("Нет данных для загрузки. Сначало укажите месяц!");
             alert.showAndWait(); 
             return; 
           }
        TotalsTurnstilesDisplay totalsTurnstilesDisplay = dataTotalsTurnstilesDisplay.get(0);
        Session currentsession =  Turnstiles.sessionfactory.openSession();
        Transaction transaction =  currentsession.beginTransaction();
             
        try {
               Query query = currentsession.createQuery("delete from TotalsTurnstiles Tl where Tl.firstDayMonth=:firstDayMonth");
               Date dataValue = java.sql.Date.valueOf(firstDayMonth.getValue());  
               query.setParameter("firstDayMonth", dataValue);
               query.executeUpdate();
               DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    for (int i = 0; i < totalsTurnstilesDisplay.textFieldM.length; i++) {
                        TotalsTurnstiles totalsTurnstiles = new TotalsTurnstiles();
                        totalsTurnstiles.setFirstDayMonth(dataValue);
                        int workingHours = Integer.parseInt(totalsTurnstilesDisplay.textFieldM[i].getText());
                        totalsTurnstiles.setWorkingHours(workingHours);
                        String strDate = totalsTurnstilesDisplay.labelM[i].getAccessibleText();
                        Date tekDay = format.parse(strDate);
                        totalsTurnstiles.setTekDay(tekDay);
                        currentsession.save(totalsTurnstiles);
                    }
        
               transaction.commit();
               currentsession.close();
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Сообщение");
               alert.setHeaderText("Сохранения записей");
               alert.setContentText("Записи успешно сохранены в базе данных");
               alert.showAndWait();  
             }
        catch (Exception ex) {
                transaction.rollback();
                currentsession.close(); 
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка сохранения записей");
                alert.setContentText("Не удалось сохранить записи. Попробуйте позже или обратитесь к программисту.");
                alert.showAndWait();
              }
    }
    
     public  void CurrentlabelHandleMouseClickedTotalsTurnstilesDisplay (Label label1) {
    //Вызывается из классов Subdivision и dolzhnosti для его элементов управления встроенных в vbox
        if (currentlabeldataTotalsTurnstilesDisplay != null) 
            {currentlabeldataTotalsTurnstilesDisplay.setTextFill(Color.WHITE);}
              
        currentlabeldataTotalsTurnstilesDisplay = label1; 
        currentlabeldataTotalsTurnstilesDisplay.setTextFill(Color.web("#0fced9"));
             
     }
    
      
     
}
