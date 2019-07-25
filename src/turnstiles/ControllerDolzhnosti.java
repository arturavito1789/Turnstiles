/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnstiles;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ArakelovAE
 */
public class ControllerDolzhnosti implements Initializable {
  
    private Turnstiles mainApp;
    
    private Stage dialogStage;
    
    private boolean addOperation;
    
    public boolean updateDb;
    
    Dolzhnosti currentDolzhnosti;
    
    @FXML
    private TextField nameDolzhnosti;
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
     updateDb = false;       
    }

        
    public void setCurrentDolzhnosti(Dolzhnosti dolzhnosti) {
        this.currentDolzhnosti = dolzhnosti;
             if (this.currentDolzhnosti != null)
                {
                  nameDolzhnosti.setText(this.currentDolzhnosti.getNameDolzhnosti());
                  addOperation = false;
                }
             else {addOperation = true;}
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
    
        String nameDolzhnostiText = nameDolzhnosti.getText();
            if ("".equals(nameDolzhnostiText))
                { 
                  Alert alert = new Alert(Alert.AlertType.ERROR);
                  alert.setTitle("Ошибка");
                  alert.setHeaderText("Ошибка заполнения поля наименование");
                  alert.setContentText("У Должности не указано поле наименование!");
                  alert.showAndWait();
                  return;
                }
        Session currentsession =  this.mainApp.sessionfactory.openSession();
        String oldNameDolzhnosti = "";
            if (addOperation == true)
               {
                 currentDolzhnosti = new Dolzhnosti();
                 currentDolzhnosti.setNameDolzhnosti(nameDolzhnostiText);
               }
            else
               {
                 oldNameDolzhnosti = currentDolzhnosti.getNameDolzhnosti();
               }
        Transaction transaction =  currentsession.beginTransaction();
            try {
                  if (addOperation == true)
                     {
                       currentsession.save(currentDolzhnosti);
                     }
                 else
                    {
                       //дополнительно применяем инструкцию update sql так как hibernete не позволяет менять первичные ключи
                        String sql = "update Dolzhnosti set NameDolzhnosti = '" +nameDolzhnostiText + "' where NameDolzhnosti = '"  + oldNameDolzhnosti + "'";
                        Query query = currentsession.createSQLQuery(sql);
                        query.executeUpdate();
                        currentDolzhnosti = currentsession.get(Dolzhnosti.class, nameDolzhnostiText);
                    };
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
                   alert.setContentText("Не удалось вставить запись. Скорее всего запись с такой должностью уже имеется.");
                   alert.showAndWait();
                 }
        }
        
    
public class Result {
    public boolean updateDb;
    public Dolzhnosti currentDolzhnosti;

    public Result(boolean update, Dolzhnosti dolzhnosti) {
        this.updateDb = update;
        this.currentDolzhnosti = dolzhnosti;
    }
    
     public Result(){}
}   
  
public Result returnResult() {
        Result result = new Result(updateDb, currentDolzhnosti);
        return result;
    }    

}
