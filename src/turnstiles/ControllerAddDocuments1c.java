/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnstiles;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.type.UUIDBinaryType;
import static turnstiles.Turnstiles.sessionfactory;

public class ControllerAddDocuments1c implements Initializable {
   
    private Stage dialogStage;
    
    @FXML
    private TableView tableviewAdditionalEmployees;
     
    @FXML
    private TableView tableviewDocuments1c;
    
    @FXML
    private TableView tableviewDocuments1cOf1C;
    
    @FXML
    public JFXDatePicker dayS;
    @FXML
    public JFXDatePicker dayPo;
    
    @FXML
    public Label LabelPeriodS;
    @FXML
    public Label LabelPeriodPo;
    @FXML
    public JFXButton Button1c;
    
    
    public boolean updateDb;
    public boolean edit;
    public Documents1cOf1C currentEditDocuments1cOf1C;
    private Documents1cOf1C currentDocuments1cOf1C;
    private Label currentlabelDocuments1cOf1C;
    private AdditionalEmployees currentAdditionalEmployees;
    private Label currentlabelAdditionalEmployees;
    private Turnstiles mainApp;
    
    ObservableList<AdditionalEmployees> dataAdditionalEmployees;
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    TableColumn vboxEmployees = new TableColumn("vbox");      
    tableviewAdditionalEmployees.getColumns().addAll(vboxEmployees);
    vboxEmployees.setCellValueFactory(
              new PropertyValueFactory<AdditionalEmployees,String>("vbox")
             );
    
    TableColumn vboxDocuments1cOf1C = new TableColumn("vbox");      
    tableviewDocuments1cOf1C.getColumns().addAll(vboxDocuments1cOf1C);
    vboxDocuments1cOf1C.setCellValueFactory(
              new PropertyValueFactory<Documents1cOf1C,String>("vbox")
             );
    
    TableColumn vboxDocuments1c = new TableColumn("vbox");      
    tableviewDocuments1c.getColumns().addAll(vboxDocuments1c);
    vboxDocuments1c.setCellValueFactory(
              new PropertyValueFactory<Documents1c,String>("vbox")
             );
     
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
    private void GetData1C(ActionEvent event) {
        currentAdditionalEmployees = null;
        currentlabelAdditionalEmployees = null; 
        dataAdditionalEmployees = FXCollections.observableArrayList(); 
        Date daySValue = null;
        Date dayPoValue = null;
        LocalDate daySL = dayS.getValue();
        LocalDate dayPoL = dayPo.getValue();
        Calendar calendar = Calendar.getInstance();
            if (daySL != null)
               {
                Date daySBr = java.sql.Date.valueOf(daySL);
                calendar.setTime(daySBr);
                String daySBrStr = daySBr.toString();
                String daySYearStr = daySBrStr.substring(0, 4);
                int daySYearInt = Integer.parseInt(daySYearStr);
                daySYearInt = daySYearInt + 2000;
                calendar.set(Calendar.YEAR, daySYearInt);
                daySValue= calendar.getTime(); // получ
               }
            if (dayPoL != null)
               {
                Date dayPoBr = java.sql.Date.valueOf(dayPoL);            
                calendar.setTime(dayPoBr);
                String dayPoBrStr = dayPoBr.toString();
                String dayPoYearStr = dayPoBrStr.substring(0, 4);
                int dayPoYearInt = Integer.parseInt(dayPoYearStr);
                dayPoYearInt = dayPoYearInt + 2000;
                calendar.set(Calendar.YEAR, dayPoYearInt);
                dayPoValue= calendar.getTime(); // получ
               }      
        boolean whereItWas = false;
        String whereDate = ""; 
            if (daySValue != null || dayPoValue != null)
               {
                whereItWas = true;
                   if (daySValue != null && dayPoValue != null)
                      {
                        whereDate = "_Date_Time BETWEEN :dayS AND :dayPo";
                      }
                   else
                      {
                        if (daySValue != null)
                           {
                             whereDate = "_Date_Time >= :dayS";
                           }
                        if (dayPoValue != null)
                           {
                             whereDate = "_Date_Time <= :dayPo";
                           }
                      }  
                   
               }
        //1) Получаем всех сотрудников из программы далее из 1с. Показываем тех у которых не нашли соответствие + тех
        //по которым есть документы за период.
        Session currentsession =  Turnstiles.sessionfactory.openSession();
        String sql = "From Employees Emp where Emp.notRelevant = :notRelevant ORDER BY Emp.primaryKey.idSubdivision,  Emp.primaryKey.idEmployees";
        List <Employees> employeesProgram = currentsession.createQuery(sql).setParameter("notRelevant", false).list();
        List <Employees> employeesDocuments = new ArrayList<>();
        List <String> fioList = new ArrayList<>(); 
             for (Iterator it = employeesProgram.iterator(); it.hasNext();) {
                  Employees emp = (Employees) it.next();
                  fioList.add(emp.getFio());
             }
        //Подключаемся к зупу
        FileInputStream fis;
        Properties property = new Properties();
            try {
                 fis = new FileInputStream("src/turnstiles/Hibernate_Zup.properties");
                     try {
                           property.load(fis);
                           fis.close();
                          } 
                     catch (IOException ex) {
 
                          }
                } 
            catch (FileNotFoundException ex) {
                }
        Configuration configuration = new Configuration();
        SessionFactory sessionfactoryZup = configuration.mergeProperties(property).buildSessionFactory();
        Session sessionZup = sessionfactoryZup.openSession();
        Query query = sessionZup.createSQLQuery("select _Reference128._Description, _Reference128._Fld1956 From _Reference128 where _Reference128._Marked = :Market and _Reference128._Description in (:fioList)");
        byte market = 0;
        query.setParameter("Market", market);
        query.setParameterList("fioList", fioList);
        List additionalEmployees = query.list();
        fioList.clear();
        //Проверяем всех ли нашли
            for (Iterator it = employeesProgram.iterator(); it.hasNext();) {
                 Employees emp = (Employees) it.next();
                 String fio = emp.getFio();
                 boolean find = false;
                    for (Iterator it2 = additionalEmployees.iterator(); it2.hasNext();) {
                        Object [] emp1C = (Object[]) it2.next();
                            if (emp1C[0].equals(fio) == true)
                               {
                                 find = true;
                                 break;
                                }
                             
                    }
                    if (find == false)
                       {
                         AdditionalEmployees empNew = new AdditionalEmployees();
                         empNew.setEmployees(emp);
                         empNew.setDocuments1cOf1C();
                         empNew.FillInTheDisplayFieldsAddDocuments1c(find,this);
                         dataAdditionalEmployees.add(empNew);
                             if (currentAdditionalEmployees == null)
                                {
                                 HBox hbox =  (HBox) empNew.vbox.getChildren().get(0);
                                 VBox vbox =  (VBox) hbox.getChildren().get(2);
                                 currentlabelAdditionalEmployees = (Label) vbox.getChildren().get(0);
                                 currentlabelAdditionalEmployees.setTextFill(Color.web("#0fced9"));
                                 currentAdditionalEmployees = empNew;
                                }
                       }
                    else
                       {
                         fioList.add(emp.getFio());
                         employeesDocuments.add(emp);
                       }
             }
        
        String sqlDok = "select DISTINCT 'Начисление по больничному', _Reference128._Description, _Document208._Number, _Document208._Date_Time as _Date_Time,  _Document208._Posted, _Document208._Fld4067, _Document208._Fld4069, 'Больничный' as reasonAbsence, '' as reasonAbsence2,  CAST(0 AS int) as offHours, CAST(0 AS int) as clockNorm From _Reference128 " +
               " INNER JOIN _Document208_VT4172 ON _Reference128._Description in (:fioList) " +
               " and _Reference128._IDRRef =  _Document208_VT4172._Fld4198RRef " +
               " INNER JOIN _Document208 ON _Document208_VT4172._Document208_IDRRef =  _Document208._IDRRef  and _Document208._Marked = :Market ";
            if (whereItWas == true)
               {
                sqlDok = sqlDok + " and _Document208." + whereDate; 
               }
        sqlDok = sqlDok + " UNION " +
               " select DISTINCT 'Отпуска организаций', _Reference128._Description, _Document222._Number, _Document222._Date_Time as _Date_Time,  _Document222._Posted, _Document222_VT4776._Fld4781, _Document222_VT4776._Fld4782, 'Отпуск' as reasonAbsence, '' as reasonAbsence2,  CAST(0 AS int) as offHours, CAST(0 AS int) as clockNorm From _Reference128 " +
               " INNER JOIN _Document222_VT4776 ON _Reference128._Description in (:fioList) " +
               " and _Reference128._IDRRef =  _Document222_VT4776._Fld4778RRef " +
               " INNER JOIN _Document222 ON _Document222_VT4776._Document222_IDRRef =  _Document222._IDRRef  and _Document222._Marked = :Market  "; 
            if (whereItWas == true)
               {
                sqlDok = sqlDok + " and _Document222." + whereDate; 
               }
        sqlDok = sqlDok + " UNION " +
               " select 'Командировки организаций', _Reference128._Description, _Document201._Number, _Document201._Date_Time as _Date_Time,   _Document201._Posted, _Document201_VT3646._Fld3650, _Document201_VT3646._Fld3651, CAST(_Document201_VT3646._Fld3654 AS varchar(1000)), '' as reasonAbsence2, CAST(0 AS int) as offHours, CAST(0 AS int) as clockNorm  From _Reference128 " +
               " INNER JOIN _Document201_VT3646 ON _Reference128._Description in (:fioList) " +
               " and _Reference128._IDRRef =  _Document201_VT3646._Fld3648RRef " +
               " INNER JOIN _Document201 ON _Document201_VT3646._Document201_IDRRef =  _Document201._IDRRef  and _Document201._Marked = :Market ";
            if (whereItWas == true)
               {
                 sqlDok = sqlDok + " and _Document201." + whereDate; 
               }                        
        sqlDok = sqlDok + " UNION " +
               " select DISTINCT 'Невыходы в организациях', _Reference128._Description, _Document213._Number, _Document213._Date_Time as _Date_Time,  _Document213._Posted, _Document213_VT4536._Fld4541, _Document213_VT4536._Fld4542, _CKinds8._Description, _CKinds7._Description, CAST(_Document213_VT4536._Fld4562 AS int), CAST( _Document213_VT4536._Fld4552 AS int) From _Reference128 " +
               " INNER JOIN _Document213_VT4536 ON _Reference128._Description in (:fioList) " +
               " and _Reference128._IDRRef =  _Document213_VT4536._Fld4538RRef " +
               " LEFT JOIN _CKinds8 ON _Document213_VT4536._Fld4540_RRRef = _CKinds8._IDRRef " +
               " LEFT JOIN _CKinds7 ON _Document213_VT4536._Fld4540_RRRef = _CKinds7._IDRRef " +
               " INNER JOIN _Document213 ON _Document213_VT4536._Document213_IDRRef = _Document213._IDRRef  and _Document213._Marked = :Market ";
            if (whereItWas == true)
               {
                sqlDok = sqlDok + " and _Document213." + whereDate; 
               }
        sqlDok = sqlDok + " Order By _Date_Time ";
        Query queryDok = sessionZup.createSQLQuery(sqlDok);
        queryDok.setParameter("Market", market);
        queryDok.setParameterList("fioList", fioList);
            if (daySValue != null)
                {
                  queryDok.setParameter("dayS", daySValue);
                }
            if (dayPoValue != null)    
                { 
                  queryDok.setParameter("dayPo", dayPoValue);   
                }  
        List dokEmployees1C = queryDok.list();
            for (Iterator it = employeesDocuments.iterator(); it.hasNext();) {
                 boolean find = false;
                 Employees emp = (Employees) it.next();
                 String fio = emp.getFio();
                 AdditionalEmployees empNew = null;
                    for (Iterator it2 = dokEmployees1C.iterator(); it2.hasNext();) {
                        Object [] dokEmp1C = (Object[]) it2.next();
                           if (dokEmp1C[1].equals(fio) == true)
                              {
                                find = true;
                                    if (empNew == null)
                                    {
                                      empNew = new AdditionalEmployees();
                                      empNew.setDocuments1cOf1C();
                                      empNew.setEmployees(emp);
                                    }
                                Documents1cOf1C docNew = new Documents1cOf1C();
                                Date dataDok = (Date)dokEmp1C[3];
                                calendar.setTime(dataDok);
                                String dataDokStr = dataDok.toString();
                                String dataDokYearStr = dataDokStr.substring(0, 4);
                                int dataDokYearInt = Integer.parseInt(dataDokYearStr);
                                dataDokYearInt = dataDokYearInt - 2000;
                                calendar.set(Calendar.YEAR, dataDokYearInt);
                                dataDok = calendar.getTime(); 
                                docNew.setDate_Time(dataDok);
                                Date dataDokS = (Date) dokEmp1C[5];
                                calendar.setTime(dataDokS);
                                String dataDokSStr = dataDokS.toString();
                                String dataDokSYearStr = dataDokSStr.substring(0, 4);
                                int dataDokSYearInt = Integer.parseInt(dataDokSYearStr);
                                dataDokSYearInt = dataDokSYearInt - 2000;
                                calendar.set(Calendar.YEAR, dataDokSYearInt);
                                dataDokS = calendar.getTime();
                                docNew.setDayS(dataDokS);
                                Date dataDokPo = (Date) dokEmp1C[6];
                                calendar.setTime(dataDokPo);
                                String dataDokPoStr = dataDokPo.toString();
                                String dataDokPoYearStr = dataDokPoStr.substring(0, 4);
                                int dataDokPoYearInt = Integer.parseInt(dataDokPoYearStr);
                                dataDokPoYearInt = dataDokPoYearInt - 2000;
                                calendar.set(Calendar.YEAR, dataDokPoYearInt);
                                dataDokPo = calendar.getTime();
                                docNew.setDayPo(dataDokPo);
                                String reasonAbsence =  (String) dokEmp1C[7]; 
                                     if (reasonAbsence.equals("") == true)
                                        {reasonAbsence =  (String) dokEmp1C[8];}
                                docNew.setReasonAbsence(reasonAbsence);
                                Integer offHours = (Integer) dokEmp1C[9];
                                Integer clockNorm = (Integer) dokEmp1C[10];
                                Integer Hours = clockNorm;
                                     if (Hours == 0 && offHours > 0)
                                        {Hours = offHours;}    
                                docNew.setHours(Hours);
                                boolean posted = false;
                                byte[] binary = (byte[]) dokEmp1C[4]; 
                                    if (binary[0] == 1)
                                       {posted = true;}
                                docNew.setPosted(posted);
                                String documentType = (String) dokEmp1C[0];
                                docNew.setDocumentType(documentType);
                                String Number = (String) dokEmp1C[2];
                                docNew.setNumber(Number);
                                docNew.setAdditionalEmployees(empNew);
                                docNew.FillInTheDisplayFieldsAdditionalEmployees(this);
                                docNew.setDocuments1c();
                                empNew.documents1cOf1C.add(docNew);
                              }    
                    }
                    
                    if (empNew != null)
                       {
                         empNew.FillInTheDisplayFieldsAddDocuments1c(find,this);
                              if (currentAdditionalEmployees == null)
                                 {
                                   HBox hbox =  (HBox) empNew.vbox.getChildren().get(0);
                                   VBox vbox =  (VBox) hbox.getChildren().get(2);
                                   currentlabelAdditionalEmployees = (Label) vbox.getChildren().get(0);
                                   currentlabelAdditionalEmployees.setTextFill(Color.web("#0fced9"));
                                   currentAdditionalEmployees = empNew;
                                  }
                         dataAdditionalEmployees.add(empNew);
                       } 
            }    
     
        tableviewAdditionalEmployees.setItems(dataAdditionalEmployees);
        LoadTableviewDocuments1cOf1C();
        sessionZup.close();
        sessionfactoryZup.close();
        currentsession.close();
        
        
    }
    
     @FXML
    private void LoadData1C(ActionEvent event) {
        String strError = "";
        List<Documents1c> loadDocuments1c = new ArrayList<Documents1c>();
            for (Iterator<AdditionalEmployees> it = dataAdditionalEmployees.iterator(); it.hasNext();) {
                AdditionalEmployees additionalEmployees = (AdditionalEmployees) it.next();
                     if (additionalEmployees.SelectedLoadCheckBox.isSelected() == true)
                        {
                          for (Iterator<Documents1cOf1C> it2 = additionalEmployees.documents1cOf1C.iterator(); it2.hasNext();) {
                               Documents1cOf1C documents1cOf1C = (Documents1cOf1C) it2.next();
                                    if (documents1cOf1C.SelectedLoadCheckBox.isSelected() == true)
                                       {
                                        Integer Hours = 0; 
                                        boolean itsNevixod = false;
                                            if (documents1cOf1C.getDocumentType().equals("Невыходы в организациях") == true)
                                               {
                                                 itsNevixod = true; 
                                               }
                                            for (Iterator<Documents1c> it3 = documents1cOf1C.documents1c.iterator(); it3.hasNext();) {
                                                 Documents1c documents1c = (Documents1c) it3.next();
                                                 loadDocuments1c.add(documents1c);
                                                      if (itsNevixod == true)
                                                         {
                                                           Hours = Hours + documents1c.getHoursDay();
                                                         }
                                                }    
                                            if (itsNevixod == true)
                                                {
                                                 Integer HoursDoc = documents1cOf1C.getHours(); 
                                                    if (Hours.equals(HoursDoc) == false)
                                                       {
                                                        strError = strError + " По сотруднику " +  additionalEmployees.employees.getFio() + " в документе " + documents1cOf1C.getDocumentType() + " № " + documents1cOf1C.getNumber() + " от " + documents1cOf1C.getDate_Time().toString() + " указано часов : " + HoursDoc.toString() + ".\n";
                                                        strError = strError + " При редактировании часов вы указали: " + Hours + ".\n";
                                                        strError = strError + " Часы указанные при редактировании должны быть равны часам указанным в документе.\n ";
                                                       }
                                                }     
                                         
                                       }
                                } 
                        }
                } 
            if (strError.equals("")== false)
               {
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setTitle("Предупреждение");
                 alert.setHeaderText("Обнаружено расхождение по часам не выхода:");
                 alert.setContentText(strError);
                 alert.showAndWait();
                 return;
               }
            if (loadDocuments1c.size() == 0)
               {
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setTitle("Предупреждение");
                 alert.setHeaderText("Укажите документы для загрузки!");
                 alert.setContentText(strError);
                 alert.showAndWait();
                 return;
               }    
        Session currentsession =  this.mainApp.sessionfactory.openSession();
        Transaction transaction =  currentsession.beginTransaction(); 
            try {
                 for (Iterator<Documents1c> it = loadDocuments1c.iterator(); it.hasNext();) {
                      Documents1c documents1c = (Documents1c) it.next();
                      Integer hoursDay = documents1c.getHoursDay();
                           if (edit==true)
                              {
                                documents1c = currentsession.get(Documents1c.class, documents1c.getPrimaryKey());
                                    if (hoursDay.equals(documents1c.getHoursDay()) == false)
                                       {
                                        documents1c.setHoursDay(hoursDay);
                                       }
                              }
                      currentsession.save(documents1c);
                     }
                  transaction.commit();
                  currentsession.close(); 
                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                  alert.setTitle("Сообщение");
                  alert.setHeaderText("Сохранения записи");
                  alert.setContentText("Записи успешно сохранены в базе данных");
                  alert.showAndWait();  
                  updateDb = true;
                }
            catch (Exception ex) {
                   transaction.rollback();
                   currentsession.close(); 
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle("Ошибка");
                   alert.setHeaderText("Ошибка сохранения записей");
                   alert.setContentText("Не удалось вставить записи. Скорее всего записи с такими полями уже сушествуют.");
                   alert.showAndWait();
                 }
            
    }
    
    
    private void LoadTableviewDocuments1cOf1C() {
        if (currentAdditionalEmployees != null)
             {
                tableviewDocuments1cOf1C.setItems(currentAdditionalEmployees.documents1cOf1C);
                currentDocuments1cOf1C = null;
                    if (currentlabelDocuments1cOf1C != null) 
                       {currentlabelDocuments1cOf1C.setTextFill(Color.WHITE);}
                    if (currentAdditionalEmployees.documents1cOf1C.size() > 0)
                       {
                        currentDocuments1cOf1C = currentAdditionalEmployees.documents1cOf1C.get(0);
                        currentlabelDocuments1cOf1C = currentDocuments1cOf1C.label1;
                        currentlabelDocuments1cOf1C.setTextFill(Color.web("#0fced9"));
                       }
                 LoadTableviewDocuments1c();          
             }
    }
    
    
    public  void CurrentlabelHandleMouseClickedAdditionalEmployees (AdditionalEmployees additionalEmployees, Label label1) {
    //Вызывается из классов Subdivision и dolzhnosti для его элементов управления встроенных в vbox
        currentAdditionalEmployees =  additionalEmployees;
              if (currentlabelAdditionalEmployees != null) 
                 {currentlabelAdditionalEmployees.setTextFill(Color.WHITE);}
              
        currentlabelAdditionalEmployees = label1; 
        currentlabelAdditionalEmployees.setTextFill(Color.web("#0fced9"));
        LoadTableviewDocuments1cOf1C();          
    }
     
    public void  selectDocuments1cOf1C(boolean selected, AdditionalEmployees additionalEmployees) {
         for (Iterator<Documents1cOf1C> it = additionalEmployees.documents1cOf1C.iterator(); it.hasNext();) {
             Documents1cOf1C documents1cOf1C = (Documents1cOf1C) it.next();
             documents1cOf1C.SelectedLoadCheckBox.setSelected(selected);
            }   
    } 
     
    public  void CurrentlabelHandleMouseClickedDocuments1cOf1C (Documents1cOf1C documents1cOf1C, Label label1) {
    //Вызывается из классов Subdivision и dolzhnosti для его элементов управления встроенных в vbox
    currentDocuments1cOf1C  =  documents1cOf1C;
         if (currentlabelDocuments1cOf1C != null) 
            {currentlabelDocuments1cOf1C.setTextFill(Color.WHITE);}
              
     currentlabelDocuments1cOf1C = label1; 
     currentlabelDocuments1cOf1C.setTextFill(Color.web("#0fced9"));
     LoadTableviewDocuments1c();          
    }
    
    private void LoadTableviewDocuments1c() {
        if (currentDocuments1cOf1C != null)
             {
                tableviewDocuments1c.setItems(currentDocuments1cOf1C.documents1c);
             }
        else 
             {
                ObservableList<Documents1c> documents = FXCollections.observableArrayList();
                tableviewDocuments1c.setItems(documents);
             }
        tableviewDocuments1c.refresh();
    }
    
    public void LoadCurrentEditDocuments1cOf1C() {
        dataAdditionalEmployees = FXCollections.observableArrayList(); 
        AdditionalEmployees  empNew = new AdditionalEmployees();
        empNew.setDocuments1cOf1C();
        empNew.setEmployees(currentEditDocuments1cOf1C.getEmployees());
        empNew.documents1cOf1C.add(currentEditDocuments1cOf1C);
        empNew.FillInTheDisplayFieldsAddDocuments1c(true,this);
        empNew.SelectedLoadCheckBox.setSelected(true);
        currentAdditionalEmployees = empNew;
        HBox hbox =  (HBox) empNew.vbox.getChildren().get(0);
        VBox vbox =  (VBox) hbox.getChildren().get(2);
        currentlabelAdditionalEmployees = (Label) vbox.getChildren().get(0);
        currentlabelAdditionalEmployees.setTextFill(Color.web("#0fced9"));
        currentEditDocuments1cOf1C.setAdditionalEmployees(empNew);
        currentEditDocuments1cOf1C.FillInTheDisplayFieldsAdditionalEmployees(this);
        currentEditDocuments1cOf1C.SelectedLoadCheckBox.setSelected(true);
        dataAdditionalEmployees.add(empNew);
        tableviewAdditionalEmployees.setItems(dataAdditionalEmployees);
        LoadTableviewDocuments1cOf1C();
        
        
    }
    
}
