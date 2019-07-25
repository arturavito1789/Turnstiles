



package turnstiles;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import org.hibernate.Session;



public class ChoiceSubdivisionEmployeesController implements Initializable {
  
    @FXML
    private JFXListView <Label> listSubdivision;
    @FXML
    private JFXListView <Label> listemployees;
    @FXML
    public JFXComboBox <String> reportType; 
    @FXML
    public JFXCheckBox  showEmployees;
    @FXML
    public HBox HBoxReportType;
    @FXML
    public HBox HboxSubdivision; 
    @FXML
    public HBox HBoxEmployees;
    @FXML
    public JFXDatePicker dayS;
    @FXML
    public JFXDatePicker dayPo;
    
    
    public static boolean showReportType;
    public List <Subdivision> subdivisionSelected;    
    public List <Employees> employeesSelected;  
    private List <Subdivision> subdivision;    
    private List <Employees> employees;  
    private Map labelEmployees = new HashMap<Label, Employees>(); 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       subdivisionSelected =  new ArrayList<Subdivision>();
       employeesSelected =  new ArrayList<Employees>();
       listSubdivision.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
       listemployees.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
      
       
       Session currentsession =  Turnstiles.sessionfactory.openSession();
       //если нет прав на редактирования необходимо установить отборы
         if (Turnstiles.user.isRightToEditInformation() == true ||  Turnstiles.user.isHeadOrganization() == true)
            {
              subdivision = currentsession.createQuery("From Subdivision Sub ORDER BY Sub.idSubdivision").list();
              employees = currentsession.createQuery("From Employees Emp ORDER BY Emp.primaryKey.idSubdivision, Emp.primaryKey.idEmployees").list();
            }
         else if (Turnstiles.user.isRukovoditel() == true)  
            {
              subdivision = currentsession.createQuery("From Subdivision Sub where Sub.idSubdivision = :idSubdivision").setParameter("idSubdivision", Turnstiles.user.getLinkSubdivision().getIdSubdivision()).list();
              employees = currentsession.createQuery("From Employees Emp where Emp.linkSubdivision = :linkSubdivision ORDER BY Emp.primaryKey").setParameter("linkSubdivision", Turnstiles.user.getLinkSubdivision()).list();
            } 
         else
            {
               subdivision = currentsession.createQuery("From Subdivision Sub where Sub.idSubdivision = :idSubdivision").setParameter("idSubdivision", Turnstiles.user.getLinkSubdivision().getIdSubdivision()).list();
               employees = currentsession.createQuery("From Employees Emp where Emp.primaryKey = :primaryKey ORDER BY Emp.primaryKey").setParameter("primaryKey", Turnstiles.user.getPrimaryKey()).list();
            }
             
       currentsession.close();
       Image image = new Image(getClass().getResourceAsStream("/IMG/if_Branding_1562683.png")); 
       
       dayS.setOnAction(event -> {
            LocalDate daySvalue = dayS.getValue();
            LocalDate dayPovalue = dayPo.getValue();
                if (daySvalue != null && dayPovalue != null)
                   {
                     if (daySvalue.isAfter(dayPovalue))
                        {
                          Alert alert = new Alert(Alert.AlertType.ERROR);
                          alert.setTitle("Ошибка");
                          alert.setHeaderText("Ошибка период с больше периода по");
                          alert.setContentText("Период с больше периода по!");
                          alert.showAndWait();
                          dayS.setValue(dayPovalue);
                        }
                   }
        });
      
        dayPo.setOnAction(event -> {
            LocalDate daySvalue = dayS.getValue();
            LocalDate dayPovalue = dayPo.getValue();
                if (daySvalue != null && dayPovalue != null)
                   {
                     if (daySvalue.isAfter(dayPovalue))
                        {
                          Alert alert = new Alert(Alert.AlertType.ERROR);
                          alert.setTitle("Ошибка");
                          alert.setHeaderText("Ошибка период с больше периода по");
                          alert.setContentText("Период с больше периода по!");
                          alert.showAndWait();
                          dayS.setValue(dayPovalue);
                        }
                   }
        });
       
          for (Iterator<Subdivision> it = subdivision.iterator(); it.hasNext();) {
               Subdivision subdivision = (Subdivision) it.next();
               ImageView imageView = new ImageView(image);
               imageView.setFitHeight(30);
               imageView.setFitWidth(30);
               Label lbl = new Label(subdivision.getNameSubdivision());
               lbl.setGraphic(imageView);
               lbl.setTextFill(Color.web("#0fced9"));
               lbl.setFont(Font.font("System",  FontPosture.ITALIC, 14));
               listSubdivision.getItems().add(lbl);
              } 
           for (Iterator<Employees> it = employees.iterator(); it.hasNext();) {
               Employees employees = (Employees) it.next();
               Image img = new Image(new ByteArrayInputStream(employees.getFoto()));
               ImageView imageView = new ImageView(img);
               imageView.setFitHeight(50);
               imageView.setFitWidth(50);
               Label lbl = new Label(employees.getFio());
               lbl.setGraphic(imageView);
               lbl.setTextFill(Color.web("#0fced9"));
               lbl.setFont(Font.font("System",  FontPosture.ITALIC, 14));
               listemployees.getItems().add(lbl);
               labelEmployees.put(lbl,employees); 
              } 
          
    }    
   
    public void initializereportType(){
       reportType.getItems().add("По сотрудникам в целом");
       reportType.getItems().add("Группировать по подразделениям");
    }
    
    
    @FXML
    public void ClickedSubdivisionHandle(MouseEvent event) {
       //при каждом щелканье формируем заново список выбранных. Очишаем список сотрудников
       listemployees.getSelectionModel().clearSelection();
       List<Integer> selectedIndices = listSubdivision.getSelectionModel().getSelectedIndices();
       subdivisionSelected.clear();
       employeesSelected.clear();  
           if(event.getButton().equals(MouseButton.SECONDARY))
              {
               //снимаем выделение с текущего элемента списка 
               boolean currentIndex = false;  
               Integer index = 0;               
               Label currentLabel = (Label) event.getPickResult().getIntersectedNode().getParent();
                    for (Iterator<Integer> it = selectedIndices.iterator(); it.hasNext();) {
                        index = (Integer) it.next();
                        Label item = listSubdivision.getItems().get(index);
                             if (item.equals(currentLabel) == true)
                                {
                                  currentIndex = true;
                                  break;
                                }
                     }
                     if (currentIndex == true)
                     {
                       listSubdivision.getSelectionModel().clearSelection(index);
                       selectedIndices = listSubdivision.getSelectionModel().getSelectedIndices();
                     }
              }
           for (Iterator<Integer> it = selectedIndices.iterator(); it.hasNext();) {
                 Integer index = (Integer) it.next();
                 Label item = listSubdivision.getItems().get(index);
                 String nameSubdivision = item.getText();
                        for (Subdivision subdivision : subdivision) {
                            String nameSubdivision2 = subdivision.getNameSubdivision();
                                   if (nameSubdivision.equals(nameSubdivision2))
                                       {   
                                        subdivisionSelected.add(subdivision);
                                            for (Employees employees : subdivision.getEmployees()) {
                                                 employeesSelected.add(employees);
                                                 Set set = labelEmployees.entrySet();
                                                 Iterator iterator = set.iterator();
                                                      while(iterator.hasNext()) {
                                                            Map.Entry mentry = (Map.Entry)iterator.next();
                                                                if (employees.equals(mentry.getValue()))
                                                                   {
                                                                     Label label = (Label) mentry.getKey();
                                                                     listemployees.getSelectionModel().select(label);
                                                                     break;
                                                                    }
                                                           }
                                            }
                                       }
                        }    
                } 
      
    }
    
    @FXML
    public void ClickedEmployeesHandle(MouseEvent event) {
    
       List<Integer> selectedIndices = listemployees.getSelectionModel().getSelectedIndices();
       employeesSelected.clear();  
            if(event.getButton().equals(MouseButton.SECONDARY))
              {
               //снимаем выделение с текущего элемента списка 
               boolean currentIndex = false;  
               Integer index = 0;               
               Label currentLabel = (Label) event.getPickResult().getIntersectedNode().getParent();
                    for (Iterator<Integer> it = selectedIndices.iterator(); it.hasNext();) {
                        index = (Integer) it.next();
                        Label item = listemployees.getItems().get(index);
                             if (item.equals(currentLabel) == true)
                                {
                                  currentIndex = true;
                                  break;
                                }
                     }
                     if (currentIndex == true)
                     {
                       listemployees.getSelectionModel().clearSelection(index);
                       selectedIndices = listemployees.getSelectionModel().getSelectedIndices();
                     }
              }
       
       
           for (Iterator<Integer> it = selectedIndices.iterator(); it.hasNext();) {
                 Integer index = (Integer) it.next();
                 Label item = listemployees.getItems().get(index);
                 Set set = labelEmployees.entrySet();
                 Iterator iterator = set.iterator();
                      while(iterator.hasNext()) {
                            Map.Entry mentry = (Map.Entry)iterator.next();
                                if (item.equals(mentry.getKey()))
                                    {
                                     employeesSelected.add((Employees) mentry.getValue());
                                     break;
                                    }
                        }
                } 
      
    }
    
    
}
