
package turnstiles;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Session;


public class ControllerchangeEmployeesFileTurnstilesLoading implements Initializable  {
   
    private Stage dialogStage;
    public boolean updateDb;
    public AdditionalEmployees additionalEmployees;
    
     @FXML
    private ChoiceBox employees;
     
    List <Employees> listEmp;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      Session currentsession =  Turnstiles.sessionfactory.openSession();
      String sql = "From Employees Emp ORDER BY Emp.primaryKey.idSubdivision, Emp.primaryKey.idEmployees";
      listEmp = currentsession.createQuery(sql).list();
          for (Iterator<Employees> it = listEmp.iterator(); it.hasNext();) {
              Employees emp = (Employees) it.next();
              employees.getItems().add(emp.getFio());
            } 
       currentsession.close();
    }
  
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    @FXML
    private void FontHandleAction(MouseEvent event) {
         this.dialogStage.close();
    }
    
     @FXML
    private void SaveAction(ActionEvent event) {
        String employeesStr = (String) employees.getValue();
        Employees currentEmp = null;      
            if (employeesStr == null)
               { 
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка заполнения поля сотрудник");
                alert.setContentText("Не указан сотрудник!");
                alert.showAndWait();
                return;
               }
             for (Iterator<Employees> it = listEmp.iterator(); it.hasNext();) {
                 Employees emp = (Employees) it.next();
                     if (employeesStr.equals(emp.getFio()) == true)
                        {
                          currentEmp = emp;
                          break;
                        }
             }   
        additionalEmployees.setEmployees(currentEmp);
        updateDb = true;
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Изменение сотрудника.");
        alert.setHeaderText("Сотрудник изменен. Текущее окно можете закрыть.");
        alert.setContentText("Сотрудник изменен. Текущее окно можете закрыть!");
        alert.showAndWait();
    }




}
