package turnstiles;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class ControllerSubdivision implements Initializable{
   
    // Reference to the main application.
    private Turnstiles mainApp;
    
    private Stage dialogStage;
    
    private boolean addOperation;
    
    public boolean updateDb;
    
    Subdivision currentSubdivision;
    
    @FXML
    private TextField nameSubdivision;
    
    @FXML
    private TextField  rukovoditel;
    
    @FXML
    private ImageView foto;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     updateDb = false;    
     rukovoditel.setEditable(false);
    }
    
    public void setCurrentSubdivision(Subdivision subdivision) {
        this.currentSubdivision = subdivision;
             if (this.currentSubdivision != null)
                {
                  nameSubdivision.setText(this.currentSubdivision.getNameSubdivision());
                  addOperation = false;
                  if (this.currentSubdivision.rukovoditelEmployees != null)
                     {
                        rukovoditel.setText(this.currentSubdivision.rukovoditelEmployees.getFio());
                        Image img = new Image(new ByteArrayInputStream(this.currentSubdivision.rukovoditelEmployees.getFoto()));
                        foto.setImage(img);   
                     }
                }
             else {
                   addOperation = true; 
                   rukovoditel.setText("");
                 }
    }
    
    public void setMainApp(Turnstiles mainApp) {
        this.mainApp = mainApp;
    }
    
        
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    @FXML
    private void FontHandleAction(MouseEvent event) {
         this.dialogStage.close();
    }
    
    
    @FXML
    private void SaveEntityDbAction(ActionEvent event) {
    
        String nameSubdivisionText = nameSubdivision.getText();
            if ("".equals(nameSubdivisionText))
                { 
                  Alert alert = new Alert(AlertType.ERROR);
                  alert.setTitle("Ошибка");
                  alert.setHeaderText("Ошибка заполнения поля наименование");
                  alert.setContentText("У подразделения не указано поле наименование!");
                  alert.showAndWait();
                  return;
                }
        Session currentsession =  this.mainApp.sessionfactory.openSession();
        
            if (addOperation == true)
               {
                 currentSubdivision = new Subdivision();
               }
            else
               {
                 currentSubdivision = currentsession.get(Subdivision.class, currentSubdivision.getIdSubdivision());
               }
        currentSubdivision.setNameSubdivision(nameSubdivisionText);
            
        Transaction transaction =  currentsession.beginTransaction();
            try {
                  currentsession.save(currentSubdivision);
                  transaction.commit();
                  currentsession.close();
                  Alert alert = new Alert(AlertType.INFORMATION);
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
                   Alert alert = new Alert(AlertType.ERROR);
                   alert.setTitle("Ошибка");
                   alert.setHeaderText("Ошибка сохранения записи");
                   alert.setContentText("Не удалось вставить запись. Скорее всего запись с таким подразделением уже имеется.");
                   alert.showAndWait();
                 }
        }
        
    
public class Result {
    public boolean updateDb;
    public Subdivision currentSubdivision;

    public Result(boolean update, Subdivision subdivision) {
        this.updateDb = update;
        this.currentSubdivision = subdivision;
    }
    public Result(){}
}   
  
public Result returnResult() {
     Result result = new Result(updateDb, currentSubdivision);
     return result;
}      
    


}

