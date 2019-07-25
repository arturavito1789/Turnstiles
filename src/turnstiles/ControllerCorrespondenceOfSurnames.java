
package turnstiles;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class ControllerCorrespondenceOfSurnames  implements Initializable{
   
    private Stage dialogStage;
    
    public boolean updateDb;
    List <Employees> listEmp;
    
    @FXML
    private TextField employeesReplece;
    
    @FXML
    private ImageView foto;
    
     @FXML
    private ChoiceBox <String> employees;
     
    private CorrespondenceOfSurnames currentCorrespondenceOfSurnames;
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      Tooltip tooltip = new Tooltip("Сотрудник");
      employees.setTooltip(tooltip);
      Session currentsession =  Turnstiles.sessionfactory.openSession();
      String sql = "From Employees Emp where Emp.notRelevant = :notRelevant ORDER BY Emp.primaryKey.idSubdivision, Emp.primaryKey.idEmployees";
      listEmp  = currentsession.createQuery(sql).setParameter("notRelevant", false).list();
          for (Iterator<Employees> it = listEmp.iterator(); it.hasNext();) {
              Employees emp = (Employees) it.next();
              employees.getItems().add(emp.getFio());
            } 
          
       employees.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                   String fio = employees.getItems().get((Integer) number2);
                       if (fio.equals("") == false)
                          {
                             for (Iterator<Employees> it = listEmp.iterator(); it.hasNext();) {
                                  Employees emp = (Employees) it.next();
                                      if (fio.equals(emp.getFio()) == true)
                                         {
                                           Image img = new Image(new ByteArrayInputStream(emp.getFoto()));
                                           foto.setImage(img);
                                           break;
                                         }
                                          
                             } 
                          }
                   
                  }
           });   
     currentsession.close();
    }

    public CorrespondenceOfSurnames getCurrentCorrespondenceOfSurnames() {
        return currentCorrespondenceOfSurnames;
    }

    public void setCurrentCorrespondenceOfSurnames(CorrespondenceOfSurnames currentCorrespondenceOfSurnames) {
        this.currentCorrespondenceOfSurnames = currentCorrespondenceOfSurnames;
            if (currentCorrespondenceOfSurnames != null)
               {
                 employees.setValue(currentCorrespondenceOfSurnames.getEmployees().getFio());
                 Image img = new Image(new ByteArrayInputStream(currentCorrespondenceOfSurnames.getEmployees().getFoto()));
                 foto.setImage(img);
                 employeesReplece.setText(currentCorrespondenceOfSurnames.getEmployeesReplece());
               } 
        
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
        
       String employeesStr = employees.getValue();
       String employeesRepleceStr = employeesReplece.getText();
       Employees currentEmp = null;        
       
       if (employeesRepleceStr.equals("") == true)
          { 
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка заполнения поля замена");
            alert.setContentText("Не указана замена!");
            alert.showAndWait();
            return;
          }
        
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
       
        Session currentsession =  Turnstiles.sessionfactory.openSession();
        Transaction transaction =  currentsession.beginTransaction();  
            if (currentCorrespondenceOfSurnames == null)
               {currentCorrespondenceOfSurnames = new CorrespondenceOfSurnames();}
            else
               {
                 currentCorrespondenceOfSurnames = currentsession.get(CorrespondenceOfSurnames.class, currentCorrespondenceOfSurnames.getIdCorrespondenceOfSurnames());
               }
        
        currentCorrespondenceOfSurnames.setEmployees(currentEmp);
        currentCorrespondenceOfSurnames.setEmployeesReplece(employeesRepleceStr);
        currentCorrespondenceOfSurnames.setIdEmployees(currentEmp.getPrimaryKey().getIdEmployees());
        currentCorrespondenceOfSurnames.setIdSubdivision(currentEmp.getPrimaryKey().getIdSubdivision());
        
            try {
                 currentsession.save(currentCorrespondenceOfSurnames);
                 transaction.commit();
                 currentsession.close(); 
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setTitle("Сообщение");
                 alert.setHeaderText("Сохранения записи");
                 alert.setContentText("Запись успешно сохранена в базе данных");
                 alert.showAndWait();  
                 updateDb = true;
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
        
        
        currentsession.close();
    }
}
