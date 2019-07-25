/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnstiles;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class ControllerEmployees implements Initializable {
    
    private Turnstiles mainApp;
    
    private Stage dialogStage;
    
    public Subdivision currentSubdivision;
    
    public Subdivision currentSubdivisionOld;
    
    public Employees currentEmployees;
    
    public Dolzhnosti currentDolzhnosti;
    
    public Dolzhnosti currentDolzhnostiOld;
    
    private boolean addOperation;
    
    public boolean updateDb;
    
    private String newPathImage;    
    
    @FXML
    private ImageView foto;
    
    @FXML
    private TextField fio;
    
    @FXML
    private TextField email;
    
    @FXML
    private TextField phone;    
    
    @FXML
    private CheckBox notRelevant;
    
    @FXML
    private CheckBox rukovoditel;
    
    @FXML
    private CheckBox headOrganization;
    
    @FXML
    private CheckBox rightToEditInformation;
    
    @FXML
    private TextField domainUsername;    
    
    @FXML
    private TextField countWorkingHours;    
    
    @FXML
    private ChoiceBox <String> nameDolzhnosti;
   
    @FXML
    private ChoiceBox <String> nameSubdivision;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
      updateDb = false;  
      Tooltip nameDolzhnostiTooltip = new Tooltip("Должность");
      Tooltip idSubdivisionTooltip = new Tooltip("Подразделение");
      nameDolzhnosti.setTooltip(nameDolzhnostiTooltip);
      nameSubdivision.setTooltip(idSubdivisionTooltip);     
      
      Session currentsession =  Turnstiles.sessionfactory.openSession();
      String sql = "From Dolzhnosti Sub ORDER BY Sub.nameDolzhnosti";
      List <Dolzhnosti> list  = currentsession.createQuery(sql).list();
          for (Iterator<Dolzhnosti> it = list.iterator(); it.hasNext();) {
                 Dolzhnosti dolzhnosti = (Dolzhnosti) it.next();
                 nameDolzhnosti.getItems().add(dolzhnosti.getNameDolzhnosti());
                } 
      String sql2 = "From Subdivision Sub ORDER BY Sub.idSubdivision";   
      List <Subdivision> list2  = currentsession.createQuery(sql2).list();
          for (Iterator<Subdivision> it2 = list2.iterator(); it2.hasNext();) {
                Subdivision subdivision = (Subdivision) it2.next();
                nameSubdivision.getItems().add(subdivision.getNameSubdivision());
               } 
      currentsession.close();  
      
    }
    
    
    
      @FXML
    private void FontHandleAction(MouseEvent event) {
       this.dialogStage.close();
    }
    
    @FXML
    private void SaveEntityDbAction(ActionEvent event) {
       // проверяем все ли поля заполнены
        if (Turnstiles.user.isRightToEditInformation() == false)
            {
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Предупреждение");
              alert.setHeaderText("Предупреждение");
              alert.setContentText("У вас нет прав на выполнение этой команды!");
              alert.showAndWait();
              return;
            }
       String nameDolzhnostiStr = nameDolzhnosti.getValue();
       String nameSubdivisionStr = nameSubdivision.getValue();
       String fioStr = fio.getText();
       String emailStr = email.getText();
       String phoneStr = phone.getText();
       String countWorkingHoursStr = countWorkingHours.getText();
       String domainUsernameStr = domainUsername.getText();        
       
       if (nameDolzhnostiStr == null)
          { 
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка заполнения поля должности");
            alert.setContentText("Не указана должность!");
            alert.showAndWait();
            return;
          }
       
      if (nameSubdivisionStr == null)
          { 
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка заполнения поля подразделение");
            alert.setContentText("Не указано подразделение!");
            alert.showAndWait();
            return;
          }
      
      if ("".equals(fioStr))
          { 
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка заполнения поля ФИО");
            alert.setContentText("Не указано ФИО!");
            alert.showAndWait();
            return;
          }
      
      if ("".equals(domainUsernameStr))
          { 
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка заполнения поля имя пользователя в домене");
            alert.setContentText("Не указано имя пользователя в домене!");
            alert.showAndWait();
            return;
          }
      
      if ("".equals(emailStr))
          { 
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка заполнения поля электронная почта");
            alert.setContentText("Не указана электронная почта!");
            alert.showAndWait();
            return;
          }
      
      if ("".equals(phoneStr))
          { 
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка заполнения поля телефон");
            alert.setContentText("Не указан телефон!");
            alert.showAndWait();
            return;
          }
      if (phoneStr.length() > 3)
          { 
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка заполнения поля телефон");
            alert.setContentText("Длина значения поле телефон не должна превышать 3 - х символов!");
            alert.showAndWait();
            return;
          }
       if ("".equals(countWorkingHoursStr))
          { 
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка заполнения поля рабочие часы");
            alert.setContentText("Не указано количество рабочих часов!");
            alert.showAndWait();
            return;
          } 
      int countWorkingHoursInt = 0; 
      
      try {
            countWorkingHoursInt = Integer.parseInt(countWorkingHoursStr);
          }
      catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка заполнения поля рабочие часы");
            alert.setContentText("В поле рабочие часы можно указывать только числа!");
            alert.showAndWait();
            return;
          }
    
      if (foto.getImage() == null)
          {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка заполнения поля фото");
            alert.setContentText("Вы не указали фото!");
            alert.showAndWait();
            return;
           } 
      //необходимо проверить что файл с фото реально лежит в положенном месте.
      if (newPathImage != null)
          {
            File fileImage =new File(newPathImage);
                 if (fileImage.exists() == false)
                    {
                      String newPathImage1 = newPathImage.replace("\"/\"","\\\\");
                      Alert alert = new Alert(Alert.AlertType.ERROR);
                      alert.setTitle("Ошибка");
                      alert.setHeaderText("Файл с рисунком не обнаружен");
                      alert.setContentText("Проверьте наличие файла с рисунком по адресу: " + newPathImage1);
                      alert.showAndWait();
                      return;  
                    }
         }        
      Session currentsession =  this.mainApp.sessionfactory.openSession();
      Transaction transaction =  currentsession.beginTransaction();        
      Subdivision subdivision = null;
      currentDolzhnosti = currentsession.get(Dolzhnosti.class, nameDolzhnostiStr);  
      List <Subdivision> list = (List <Subdivision>) currentsession.createQuery("From Subdivision Sub where Sub.nameSubdivision = :nameSubdivision").setString("nameSubdivision", nameSubdivisionStr).list();
           for (Iterator<Subdivision> it = list.iterator(); it.hasNext();) {
                subdivision = (Subdivision) it.next();
               } 
      boolean queryAdditional = false;
           if (addOperation == true)
               {
                 queryAdditional = true;
                 currentEmployees = new Employees();
                 currentEmployees.setDolzhnosti(currentDolzhnosti);
                 currentEmployees.setLinkSubdivision(subdivision);  
                 int idEmployees  = 0;
                 EmployeesPrimaryKey employeesPrimaryKey = new EmployeesPrimaryKey(); 
                 employeesPrimaryKey.setIdSubdivision(subdivision.getIdSubdivision());
                 List <Employees> list2 = (List <Employees>) currentsession.createQuery("From Employees Emp where Emp.linkSubdivision = :linkSubdivision ORDER BY Emp.primaryKey.idEmployees DESC").setParameter("linkSubdivision", subdivision).list();
                      for (Iterator<Employees> it = list2.iterator(); it.hasNext();) {
                          Employees employees = (Employees) it.next();
                          idEmployees = employees.getPrimaryKey().getIdEmployees();
                          break;           
                          } 
                 idEmployees = idEmployees + 1;
                 employeesPrimaryKey.setIdEmployees(idEmployees);
                 currentEmployees.setPrimaryKey(employeesPrimaryKey);
               }
            else
               {
                  Query query = currentsession.createQuery("From Employees where idEmployees = :idEmployees and idSubdivision = :idSubdivision");
                  query.setParameter("idEmployees", currentEmployees.getPrimaryKey().getIdEmployees());
                  query.setParameter("idSubdivision", currentEmployees.getPrimaryKey().getIdSubdivision());
                  List <Employees> results = query.list();
                       for (Iterator<Employees> it = results.iterator(); it.hasNext();) {
                           currentEmployees = (Employees) it.next();
                       };
                       if (currentSubdivision.getIdSubdivision() != subdivision.getIdSubdivision())
                       {
                          int IdEmployeesNew = 0;
                          currentSubdivisionOld = currentSubdivision;
                          int IdEmployeesOld = currentEmployees.getPrimaryKey().getIdEmployees();
                          int idSubdivisionOld = currentEmployees.getPrimaryKey().getIdSubdivision();
                          currentEmployees.setLinkSubdivision(subdivision);
                          currentSubdivision = subdivision;
                          List <Employees> list2 = (List <Employees>) currentsession.createQuery("From Employees Emp where Emp.linkSubdivision = :linkSubdivision ORDER BY Emp.primaryKey.idEmployees DESC").setParameter("linkSubdivision", subdivision).list();
                               for (Iterator<Employees> it = list2.iterator(); it.hasNext();) {
                                    Employees employees = (Employees) it.next();
                                    IdEmployeesNew = employees.getPrimaryKey().getIdEmployees();
                                    break;           
                                } 
                          IdEmployeesNew = IdEmployeesNew + 1;
                          int idSubdivisionNew = subdivision.getIdSubdivision();
                          currentEmployees.setNewValuePrimaryKey(IdEmployeesNew, idSubdivisionNew);
                          queryAdditional = true;
                                try {
                                     //дополнительно применяем инструкцию update sql так как hibernete не позволяет менять первичные ключи
                                     query = currentsession.createQuery("update Employees set IdEmployees = :IdEmployeesNew, idSubdivision = :idSubdivisionNew where IdEmployees = :IdEmployeesOld and idSubdivision = :idSubdivisionOld ");
                                     query.setParameter("IdEmployeesNew", IdEmployeesNew);
                                     query.setParameter("idSubdivisionNew", idSubdivisionNew);
                                     query.setParameter("IdEmployeesOld", IdEmployeesOld);
                                     query.setParameter("idSubdivisionOld", idSubdivisionOld);
                                     query.executeUpdate();
                                    }
                                 catch (Exception ex) {
                                      transaction.rollback();
                                      currentsession.close(); 
                                      Alert alert = new Alert(Alert.AlertType.ERROR);
                                      alert.setTitle("Ошибка");
                                      alert.setHeaderText("Ошибка сохранения записи");
                                      alert.setContentText("Не удалось вставить запись. Скорее всего запись с таким сотрудником уже имеется.");
                                      alert.showAndWait();
                                      return;
                                  }
                          
                       } 
                       else {currentSubdivisionOld = null;} 
                       if (currentEmployees.getDolzhnosti().getNameDolzhnosti() != nameDolzhnostiStr) 
                          {
                            currentDolzhnostiOld = currentEmployees.getDolzhnosti();
                            currentEmployees.setDolzhnosti(currentDolzhnosti);
                            queryAdditional = true;
                           }
                       else{currentDolzhnostiOld = null;}
               }
     currentEmployees.setFio(fioStr);
     currentEmployees.setEmail(emailStr);
     currentEmployees.setPhone(phoneStr);
     currentEmployees.setCountWorkingHours(countWorkingHoursInt);
     currentEmployees.setDomainUsername(domainUsernameStr);
     currentEmployees.setRukovoditel(rukovoditel.isSelected());
     currentEmployees.setNotRelevant(notRelevant.isSelected());
     currentEmployees.setHeadOrganization(headOrganization.isSelected());
     currentEmployees.setRightToEditInformation(rightToEditInformation.isSelected());
          if (newPathImage != null) 
             {
               try {
                    File fileImage =new File(newPathImage);
                    BufferedImage bufferedImage=ImageIO.read(fileImage);
                    ByteArrayOutputStream byteOutStream=new ByteArrayOutputStream();
                    ImageIO.write(bufferedImage, "png", byteOutStream);
                    currentEmployees.setFoto(byteOutStream.toByteArray());
                   } 
               catch (Exception e) {
                   }
             }
      
            try {
                 currentsession.save(currentEmployees);
                 transaction.commit();
                 currentsession.close(); 
                        if (queryAdditional == true)
                           {
                             currentsession =  this.mainApp.sessionfactory.openSession();
                             // c учетом добавления нового сотрудника обновляем текущюю должность и текущее подразделение. Сессию необходимо перезакрыть
                              // иначе не добавляет нового сотрудника в подразделении и должности
                             currentDolzhnosti = currentsession.get(Dolzhnosti.class, currentDolzhnosti.getNameDolzhnosti());
                             currentSubdivision = currentsession.get(Subdivision.class, currentSubdivision.getIdSubdivision());
                                  if (currentDolzhnostiOld != null)
                                     { currentDolzhnostiOld = currentsession.get(Dolzhnosti.class, currentDolzhnostiOld.getNameDolzhnosti());}
                                  if (currentSubdivisionOld != null)
                                     {currentSubdivisionOld = currentsession.get(Subdivision.class, currentSubdivisionOld.getIdSubdivision());}
                             
                             currentsession.close();
                           }
                        else {currentSubdivision = subdivision;}
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setTitle("Сообщение");
                 alert.setHeaderText("Сохранения записи");
                 alert.setContentText("Запись успешно сохранена в базе данных");
                 alert.showAndWait();  
                 updateDb = true;
                      if (addOperation == false)
                        {
                          String fio1 = Turnstiles.user.getFio();
                          String fio2 = currentEmployees.getFio();
                               if (fio1.equals(fio2) == true)
                                   {
                                     Turnstiles.user = currentEmployees;
                                   }
                        } 
                 addOperation = false;
                }
            catch (Exception ex) {
                 transaction.rollback();
                 currentsession.close(); 
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Ошибка");
                 alert.setHeaderText("Ошибка сохранения записи");
                 alert.setContentText("Не удалось вставить запись. Скорее всего запись с таким сотрудником уже имеется.");
                 alert.showAndWait();
                }
     }
    
      @FXML
    private void InsertImageHandleAction(ActionEvent event) throws IOException {
       
        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Укажите фото");//Заголовок диалога
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(dialogStage);//Указываем текущую сцену CodeNote.mainStage
             if (selectedFile != null) {
                  try {
                        String path = selectedFile.getAbsolutePath().toString();
                        String PathImage =  "file:/" + path.replace("\\", "/");
                        Image img = new Image(PathImage);
                        foto.setImage(img);
                        newPathImage = PathImage.replace("file:/","");
                       } 
                  catch (Exception e) {
                       }
             }
       
    }
    
    
      @FXML
    private void InputFildAd(ActionEvent event) throws IOException {
        UserLdap userLdap = this.mainApp.UserLdapLoading(); 
            if (userLdap != null)
               {
                 fio.setText(userLdap.getDisplayName());
                 email.setText(userLdap.getMail());
                 domainUsername.setText(userLdap.getSamAccountName());
               } 
    }
    
     public void setMainApp(Turnstiles mainApp) {
        this.mainApp = mainApp;
    }
    
        
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
     public void setCurrenSubdivisionEmployees(Subdivision subdivision, Employees employees) {
        currentSubdivision = subdivision;
        currentEmployees = employees;
        //Устанавливаем зависимые поля
        nameSubdivision.setValue(currentSubdivision.getNameSubdivision());
            if (currentEmployees != null)
               {
                 fio.setText(currentEmployees.getFio());
                 email.setText(currentEmployees.getEmail());
                 phone.setText(currentEmployees.getPhone());   
                 rukovoditel.setSelected(currentEmployees.isRukovoditel());
                 notRelevant.setSelected(currentEmployees.isNotRelevant());
                 headOrganization.setSelected(currentEmployees.isHeadOrganization());
                 rightToEditInformation.setSelected(currentEmployees.isRightToEditInformation());
                 domainUsername.setText(currentEmployees.getDomainUsername());
                 int icount = currentEmployees.getCountWorkingHours();
                 countWorkingHours.setText(String.valueOf(icount));   
                 nameDolzhnosti.setValue(currentEmployees.getDolzhnosti().getNameDolzhnosti());
                 Image img = new Image(new ByteArrayInputStream(currentEmployees.getFoto()));
                 foto.setImage(img);    
                 addOperation = false;
               }
            else
               {
                 addOperation = true;
               }
                
    }
   
     
         
public class Result {
    public boolean updateDb;
    public Subdivision currentSubdivision;
    public Dolzhnosti currentDolzhnosti;
    public Subdivision currentSubdivisionOld;
    public Dolzhnosti currentDolzhnostiOld;
    public Employees currentEmployees;

    
    public Result(boolean update, Subdivision subdivision, Dolzhnosti dolzhnosti, Subdivision subdivisionOld, Dolzhnosti dolzhnostiOld, Employees employees) {
        this.updateDb = update;
        this.currentSubdivision = subdivision;
        this.currentDolzhnosti = dolzhnosti;
        this.currentSubdivisionOld = subdivisionOld;
        this.currentDolzhnostiOld = dolzhnostiOld;
        this.currentEmployees = employees;
    }
    public Result(){}
}   
  
public Result returnResult() {
     Result result = new Result(updateDb, currentSubdivision, currentDolzhnosti, currentSubdivisionOld, currentDolzhnostiOld, currentEmployees);
     return result;
}      
    
     
}
