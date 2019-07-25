/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnstiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import com.jfoenix.controls.JFXButton;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


public class ControllerFileTurnstilesLoading implements Initializable{
  
    private Stage dialogStage;
    
    @FXML
    private JFXButton removedErrorDay;
    
    @FXML
    private TableView tableviewDataExcelDay;
    
    @FXML
    private TableView tableviewDataExcel;
    
    @FXML
    private TableView tableviewDataExcelDayErr;
    
    @FXML
    private TableView tableviewDataExcelErr;
    
    @FXML
    private TableView tableviewAdditionalEmployees;
    
    @FXML
    private TableView tableviewEdit;
    
    ObservableList<AdditionalEmployees> dataAdditionalEmployees;  
    
    private AdditionalEmployees currentAdditionalEmployees;
    private Label currentlabelAdditionalEmployees;
    
    private DataExcelDay currentDataExcelDay;
    private Label currentlabelDataExcelDay;
    
    private DataExcelDay currentDataExcelDayErr;
    private Label currentlabelDataExcelDayErr;
    
    private DataExcelDayEdit currentDataExcelDayEdit;
    
    private Turnstiles mainApp;
    private boolean filterTableviewAdditionalEmployees = false;
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
    TableColumn vboxEmployees = new TableColumn("vbox");      
    tableviewAdditionalEmployees.getColumns().addAll(vboxEmployees);
    vboxEmployees.setCellValueFactory(
              new PropertyValueFactory<AdditionalEmployees,String>("vbox")
             );
        
    TableColumn vboxDataExcelDay = new TableColumn("vbox");      
    tableviewDataExcelDay.getColumns().addAll(vboxDataExcelDay);
    vboxDataExcelDay.setCellValueFactory(
              new PropertyValueFactory<DataExcelDay,String>("vbox")
             );
        
    TableColumn vboxDataExcelDayErr = new TableColumn("vbox");      
    tableviewDataExcelDayErr.getColumns().addAll(vboxDataExcelDayErr);
    vboxDataExcelDayErr.setCellValueFactory(
              new PropertyValueFactory<DataExcelDay,String>("vbox")
             );
        
    
    
    TableColumn vboxDataExcel = new TableColumn("vbox");      
    tableviewDataExcel.getColumns().addAll(vboxDataExcel);
    vboxDataExcel.setCellValueFactory(
              new PropertyValueFactory<DataExcel,String>("vbox")
             );
    
    TableColumn vboxDataExcelErr = new TableColumn("vbox");      
    tableviewDataExcelErr.getColumns().addAll(vboxDataExcelErr);
    vboxDataExcelErr.setCellValueFactory(
              new PropertyValueFactory<DataExcel,String>("vbox")
             );
    
     TableColumn firstEntryLastExitDataExcel = new TableColumn("Первый вход");  
     firstEntryLastExitDataExcel.setCellValueFactory(     
             new PropertyValueFactory<DataExcelDayEdit,String>("firstEntryLastExit")
              );      
     TableColumn timeExitDataExcel = new TableColumn("Время ухода");  
     timeExitDataExcel.setCellValueFactory(     
             new PropertyValueFactory<DataExcelDayEdit,String>("timeExitTimePicker")
              );  
     TableColumn timeEntryDataExcel = new TableColumn("Время прихода");  
     timeEntryDataExcel.setCellValueFactory(     
             new PropertyValueFactory<DataExcelDayEdit,String>("timeEntryTimePicker")
              );   
     TableColumn timeAbsenceSecondDataExcel = new TableColumn("Разница во времени"); 
     timeAbsenceSecondDataExcel.setCellValueFactory(     
             new PropertyValueFactory<DataExcelDayEdit,String>("timeAbsenceSecondTextField")
              );   
     
     
     tableviewEdit.getColumns().addAll(firstEntryLastExitDataExcel,timeExitDataExcel,timeEntryDataExcel,timeAbsenceSecondDataExcel);
     
     
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
    private void RemovedErrorDayHandleAction(ActionEvent event) {
        if (currentDataExcelDayErr != null)
           {
             if (currentDataExcelDayErr.dataExcelDayEdit.size() == 0)
                {
                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                  alert.setTitle("Предупреждение");
                  alert.setHeaderText("Вы не отредактировали дни с ошибками.");
                  alert.setContentText("Вы не отредактировали дни с ошибками.");
                  alert.showAndWait(); 
                  return; 
                }
             boolean firstEntryLastExit = false;
             boolean error = false;
             DataExcelDayEdit firstEntryLastExitDayEdit = null;
             
                 for (Iterator<DataExcelDayEdit> it = currentDataExcelDayErr.dataExcelDayEdit.iterator(); it.hasNext();) {
                      DataExcelDayEdit dataExcelDayEdit = (DataExcelDayEdit) it.next();
                          if (dataExcelDayEdit.error == true || dataExcelDayEdit.getTimeEntry() == null || dataExcelDayEdit.getTimeExit() == null)
                              {error = true;}
                          if (dataExcelDayEdit.firstEntryLastExit.isSelected() == true)
                              {
                                firstEntryLastExit = true;
                                firstEntryLastExitDayEdit = dataExcelDayEdit;
                              }
                 }   
                if (firstEntryLastExit == false)
                   {
                     Alert alert = new Alert(Alert.AlertType.INFORMATION);
                     alert.setTitle("Предупреждение");
                     alert.setHeaderText("Нет данных о первом входе.");
                     alert.setContentText("Нет данных о первом входе.");
                     alert.showAndWait(); 
                     return; 
                    }
                if (error == true)
                   {
                     Alert alert = new Alert(Alert.AlertType.INFORMATION);
                     alert.setTitle("Предупреждение");
                     alert.setHeaderText("В отредактированных днях есть ошибки");
                     alert.setContentText("В отредактированных днях есть ошибки.");
                     alert.showAndWait(); 
                     return; 
                    }
                
                DataExcelDay dataExcelDay = new DataExcelDay();
                dataExcelDay.setTekDay(currentDataExcelDayErr.getTekDay());
                dataExcelDay.setFio(currentDataExcelDayErr.getFio());
                dataExcelDay.FillInTheDisplayFields(this);
                DataExcel dataExcel = new DataExcel();
                dataExcel.setTekDay(currentDataExcelDayErr.getTekDay());
                dataExcel.setFio(currentDataExcelDayErr.getFio());
                dataExcel.setFirstEntryLastExit(true);
                dataExcel.setTimeEntry(firstEntryLastExitDayEdit.getTimeEntry());
                dataExcel.setTimeExit(firstEntryLastExitDayEdit.getTimeExit());
                long second = firstEntryLastExitDayEdit.getTimeAbsenceSecond();
                dataExcel.setTimeAbsenceSecond(second);
                dataExcel.setStrTimeAbsenceSecond(firstEntryLastExitDayEdit.getStrTimeAbsenceSecond());
                dataExcel.FillInTheDisplayFields();
                dataExcelDay.dataExcel.add(dataExcel);
                    for (Iterator<DataExcelDayEdit> it = currentDataExcelDayErr.dataExcelDayEdit.iterator(); it.hasNext();) {
                        DataExcelDayEdit dataExcelDayEdit = (DataExcelDayEdit) it.next();
                              if (dataExcelDayEdit.firstEntryLastExit.isSelected() == true)
                                 {
                                   continue;
                                 }
                        dataExcel = new DataExcel();
                        dataExcel.setTekDay(currentDataExcelDayErr.getTekDay());
                        dataExcel.setFio(currentDataExcelDayErr.getFio());
                        dataExcel.setTimeEntry(dataExcelDayEdit.getTimeEntry());
                        dataExcel.setTimeExit(dataExcelDayEdit.getTimeExit());
                        second = dataExcelDayEdit.getTimeAbsenceSecond();
                        dataExcel.setTimeAbsenceSecond(second);
                        dataExcel.setStrTimeAbsenceSecond(dataExcelDayEdit.getStrTimeAbsenceSecond());
                        dataExcel.FillInTheDisplayFields();
                        dataExcelDay.dataExcel.add(dataExcel);
                    }   
                currentAdditionalEmployees.dataExcelDay.add(dataExcelDay);
                
                Collections.sort(currentAdditionalEmployees.dataExcelDay, comparatorTekDayDataExcelDay);
                tableviewDataExcelDay.refresh();
                currentAdditionalEmployees.dataExcelDayErr.remove(currentDataExcelDayErr);
                    if (currentAdditionalEmployees.dataExcelDayErr.size() == 0)
                       {currentAdditionalEmployees.labelHotCountedDays.setVisible(false);}
                currentDataExcelDayErr = null;
                currentlabelDataExcelDayErr = null;
                currentDataExcelDayEdit = null;
                ObservableList<DataExcel> dataExcelClear = FXCollections.observableArrayList();
                tableviewDataExcelErr.setItems(dataExcelClear);
                ObservableList<DataExcelDayEdit> dataExcelDayEdit = FXCollections.observableArrayList();
                tableviewEdit.setItems(dataExcelDayEdit);
                tableviewDataExcelErr.refresh();
                tableviewEdit.refresh();
                tableviewDataExcelDayErr.refresh();
           }
    }
    
    
     @FXML
    private void SaveEntityDbAction(ActionEvent event) {
        Calendar calendar = Calendar.getInstance();
        ObservableList<Employees> employeesList =  FXCollections.observableArrayList();
        ObservableList<AdditionalEmployees> selectAdditionalEmployees = FXCollections.observableArrayList();   
            for (Iterator it = dataAdditionalEmployees.iterator(); it.hasNext();) {
                 AdditionalEmployees additionalEmployees = (AdditionalEmployees) it.next();
                     if (additionalEmployees.SelectedLoadCheckBox.isSelected() == true)
                        {
                         selectAdditionalEmployees.add(additionalEmployees);
                         employeesList.add(additionalEmployees.employees);
                         calendar.setTime(additionalEmployees.dataExcelDay.get(0).getTekDay());
                        }
                } 
            if (selectAdditionalEmployees.size() == 0)
                {
                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                  alert.setTitle("Предупреждение");
                  alert.setHeaderText("Необходимо выбрать сотрудников для загрузки.");
                  alert.setContentText("Необходимо выбрать сотрудников для загрузки");
                  alert.showAndWait(); 
                  return; 
                }
         calendar.set(Calendar.DAY_OF_MONTH,01);
         Date firstDayMonth = calendar.getTime();
        //1 сначало удаляем все записи потом записываем заново.
         Session currentsession =  Turnstiles.sessionfactory.openSession();
         Transaction transaction =  currentsession.beginTransaction();
               try {
                    Query query = currentsession.createQuery("delete from FileTurnstiles Fl where Fl.firstDayMonth=:firstDayMonth and Fl.employees in (:employeesList) ");
                    query.setParameter("firstDayMonth", firstDayMonth);
                    query.setParameterList("employeesList", employeesList); 
                    query.executeUpdate();
                        for (Iterator<AdditionalEmployees> it = selectAdditionalEmployees.iterator(); it.hasNext();) {
                              AdditionalEmployees additionalEmployees = (AdditionalEmployees) it.next();
                                  for (Iterator<DataExcelDay> it2 = additionalEmployees.dataExcelDay.iterator(); it2.hasNext();) {
                                       DataExcelDay dataExcelDay = (DataExcelDay) it2.next();
                                            for (Iterator<DataExcel> it3 = dataExcelDay.dataExcel.iterator(); it3.hasNext();) {
                                                 DataExcel dataExcel = (DataExcel) it3.next();
                                                 FileTurnstiles fileTurnstiles = new FileTurnstiles();
                                                 fileTurnstiles.setEmployees(additionalEmployees.employees);
                                                 fileTurnstiles.setIdEmployees(additionalEmployees.employees.getPrimaryKey().getIdEmployees());
                                                 fileTurnstiles.setIdSubdivision(additionalEmployees.employees.getPrimaryKey().getIdSubdivision());
                                                 fileTurnstiles.setTekDay(dataExcelDay.getTekDay());
                                                 fileTurnstiles.setEntryTime(dataExcel.getTimeEntry());
                                                 fileTurnstiles.setExitTime(dataExcel.getTimeExit());
                                                 fileTurnstiles.setFirstEntryLastExit(dataExcel.isFirstEntryLastExit());
                                                 fileTurnstiles.setStrTimeAbsenceSecond(dataExcel.getStrTimeAbsenceSecond());
                                                 fileTurnstiles.setTimeAbsenceSecond(dataExcel.getTimeAbsenceSecond());
                                                 fileTurnstiles.setFirstDayMonth(firstDayMonth);
                                                 currentsession.save(fileTurnstiles);
                                             }
                                  }
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
    
    @FXML
    private void DeletedTableviewHandleAction(MouseEvent event) {
         if (currentDataExcelDayErr != null &&  currentDataExcelDayEdit != null) 
            {
             currentDataExcelDayErr.dataExcelDayEdit.remove(currentDataExcelDayEdit);
             tableviewEdit.refresh();
            }
    }
    
    @FXML
    private void AddTableviewHandleAction(MouseEvent event) {
        if (currentDataExcelDayErr != null)
           {
            DataExcelDayEdit dataExcelDayEdit = new DataExcelDayEdit();//new DataExcelDayEdit(java.sql.Time.valueOf(LocalTime.now()), java.sql.Time.valueOf(LocalTime.now()), false);
            dataExcelDayEdit.controllerFileTurnstilesLoading = this;
            currentDataExcelDayErr.dataExcelDayEdit.add(dataExcelDayEdit);
            tableviewEdit.refresh();
           }
    }
    
    
    
     @FXML
    private void FilterTableviewAdditionalEmployees(ActionEvent event) {
        if (dataAdditionalEmployees == null || dataAdditionalEmployees.size() == 0)
           {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Предупреждение");
            alert.setHeaderText("Нет данных для установки фильтра.");
            alert.setContentText("Необходимо нажать на кнопку прочесть файл");
            alert.showAndWait(); 
            return;                 
           }
        
        if (filterTableviewAdditionalEmployees == false)
            {filterTableviewAdditionalEmployees = true;}
        else
            {filterTableviewAdditionalEmployees = false;}
        
        if (filterTableviewAdditionalEmployees == true)
            {
              ObservableList<AdditionalEmployees> filteredData = FXCollections.observableArrayList();
                  for (Iterator<AdditionalEmployees> it = dataAdditionalEmployees.iterator(); it.hasNext();) {
                       AdditionalEmployees additionalEmployees = (AdditionalEmployees) it.next();
                           if (additionalEmployees.isErrorDataExcelDay() == true)
                               {filteredData.add(additionalEmployees);}
                  }
              tableviewAdditionalEmployees.setItems(filteredData);
            }
        else
            { 
              tableviewAdditionalEmployees.setItems(dataAdditionalEmployees);
            }
        tableviewAdditionalEmployees.refresh();
        
        //очистка всех вспомогательных переменных
        if (currentlabelAdditionalEmployees != null) 
            {currentlabelAdditionalEmployees.setTextFill(Color.WHITE);}
        if (currentlabelDataExcelDay != null) 
            {currentlabelDataExcelDay.setTextFill(Color.WHITE);}
        if (currentlabelDataExcelDayErr != null) 
            {currentlabelDataExcelDayErr.setTextFill(Color.WHITE);}
        currentAdditionalEmployees = null;
        currentlabelAdditionalEmployees = null;
        currentDataExcelDay =  null;
        currentlabelDataExcelDay = null;
        currentDataExcelDayErr =  null;
        currentlabelDataExcelDayErr = null;
        currentDataExcelDayEdit = null;
        removedErrorDay.setText("Убрать ошибки за");
        ObservableList<DataExcel> dataExcel1 = FXCollections.observableArrayList();
        ObservableList<DataExcelDay> dataExcelDay = FXCollections.observableArrayList();
        ObservableList<DataExcelDayEdit> dataExcelDayEdit = FXCollections.observableArrayList();
                  
        tableviewDataExcelDay.setItems(dataExcelDay);
        tableviewDataExcelDayErr.setItems(dataExcelDay);
        tableviewDataExcel.setItems(dataExcel1);
        tableviewDataExcelErr.setItems(dataExcel1);
        tableviewEdit.setItems(dataExcelDayEdit);
                  
        tableviewDataExcelDay.refresh();
        tableviewDataExcelDayErr.refresh();
        tableviewDataExcel.refresh();
        tableviewDataExcelErr.refresh();
        tableviewEdit.refresh();
        
    }
    
     @FXML
    private void LoadFile(ActionEvent event) {
    
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Файл с данными турникетов");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("EXCEL Files", "*.xlsx"));
        File selectedFile = fileChooser.showOpenDialog(dialogStage);
             if (selectedFile == null) 
                {return;}
             try
                {  
            
                  currentDataExcelDay =  null;
                  currentlabelDataExcelDay = null;
                  currentDataExcelDayErr =  null;
                  currentlabelDataExcelDayErr = null;
                  currentDataExcelDayEdit = null;
                  removedErrorDay.setText("Убрать ошибки за");
                  ObservableList<DataExcel> dataExcel1 = FXCollections.observableArrayList();
                  ObservableList<DataExcelDay> dataExcelDay = FXCollections.observableArrayList();
                  ObservableList<DataExcelDayEdit> dataExcelDayEdit = FXCollections.observableArrayList();
                  
                  tableviewDataExcelDay.setItems(dataExcelDay);
                  tableviewDataExcelDayErr.setItems(dataExcelDay);
                  tableviewDataExcel.setItems(dataExcel1);
                  tableviewDataExcelErr.setItems(dataExcel1);
                  tableviewEdit.setItems(dataExcelDayEdit);
                  
                  tableviewDataExcelDay.refresh();
                  tableviewDataExcelDayErr.refresh();
                  tableviewDataExcel.refresh();
                  tableviewDataExcelErr.refresh();
                  tableviewEdit.refresh();
           
                    
                  DateFormat formatterDateWithoutTime = new SimpleDateFormat("dd/MM/yyyy");  
                  dataAdditionalEmployees = FXCollections.observableArrayList();   
                  ObservableList<DataExcel> listDataExcel = FXCollections.observableArrayList();   
                  ObservableList<DataExcelDay> listDataExcelDay = FXCollections.observableArrayList();  
                  ArrayList fioTurnstiles = new ArrayList();
                  ArrayList errornameTurnstiles = new ArrayList();
                  ArrayList errortypeTurnstiles = new ArrayList();
                  ArrayList typeTurnstiles = new ArrayList();
                  typeTurnstiles.add("Фактический выход");
                  typeTurnstiles.add("Нормальный выход по ключу");
                  typeTurnstiles.add("Фактический вход");
                  typeTurnstiles.add("Нормальный вход по ключу");
                  
                  ArrayList nameTurnstiles = new ArrayList();
                  nameTurnstiles.add("Турникет_4Д_1эт");
                  nameTurnstiles.add("Турникет_2Б_1этаж_вход2");
                  nameTurnstiles.add("Турникет_2Б_1этаж_вход2");
                  nameTurnstiles.add("Дверь_2Б_1эт_холл");
                  nameTurnstiles.add("Дверь_2Б_1эт_двор");
                  nameTurnstiles.add("Шлагбаум_2Б_4м_2");
                  nameTurnstiles.add("Дверь_4В_1эт_двор");
                  nameTurnstiles.add("Турникет_4В_1эт_вход2");
                  nameTurnstiles.add("Турникет_4В_1эт_вход3");
                  nameTurnstiles.add("Турникет_2Б_1эт_вход1_гости");
                  
                  //сортируем день, сотрудник, дата время
                  
                  XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(selectedFile.getPath()));
                  XSSFSheet sheet = workbook.getSheetAt(0);
                        for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
			    boolean guestPass = false;
                            XSSFRow hssfRow = (XSSFRow) sheet.getRow(rowNum);
                   	         if (hssfRow != null) {
				     boolean errorRow = false;
                                     boolean entry = false;
                                     String stringTypeTurnstiles = hssfRow.getCell(1).getStringCellValue();
                                     String stringNameTurnstiles = hssfRow.getCell(2).getStringCellValue();
                                        if (nameTurnstiles.contains(stringNameTurnstiles) == false)
                                           {
                                            if (errornameTurnstiles.contains(stringNameTurnstiles) == false)
                                               {
                                                errornameTurnstiles.add(stringNameTurnstiles);
                                               } 
                                            errorRow = true;
                                           }
                                        else if(stringNameTurnstiles.equals("Турникет_2Б_1эт_вход1_гости") == true)
                                           {
                                             guestPass = true;
                                           }
                                        if (stringNameTurnstiles.equals("Турникет_2Б_1эт_вход1_гости") == false)
                                           {
                                             if (typeTurnstiles.contains(stringTypeTurnstiles) == false)
                                                {
                                                 if (errortypeTurnstiles.contains(stringTypeTurnstiles) == false)
                                                    {
                                                     errortypeTurnstiles.add(stringTypeTurnstiles);
                                                    } 
                                                  errorRow = true;
                                                } 
                                             else
                                                {
                                                  if (stringTypeTurnstiles.equals("Фактический вход") || stringTypeTurnstiles.equals("Нормальный вход по ключу"))
                                                     {entry = true;}         
                                                }
                                           }
                                        if (errorRow == false)
                                           {
                                             Date dataRow = hssfRow.getCell(0).getDateCellValue();
                                             Date tekDay = formatterDateWithoutTime.parse(formatterDateWithoutTime.format(dataRow));
                                             String fio = hssfRow.getCell(3).getStringCellValue();
                                             DataExcel dataExcel = new DataExcel();
                                             dataExcel.setDate(dataRow);
                                             dataExcel.setTekDay(tekDay);
                                             dataExcel.setFio(fio);
                                             dataExcel.setEntry(entry);
                                             dataExcel.setGuestPass(guestPass);
                                             dataExcel.setNameTurnstiles(stringNameTurnstiles);
                                             dataExcel.setTypeTurnstiles(stringTypeTurnstiles);
                                             listDataExcel.add(dataExcel);
                                                if (fioTurnstiles.contains(fio) == false)
                                                   {
                                                     fioTurnstiles.add(fio);
                                                   } 
                                           }   
                                          
			           }
		        }
                        if (errornameTurnstiles.size() > 0)
                            {
                              String datastr = "";
                                  for (Iterator it = errornameTurnstiles.iterator(); it.hasNext();) {
                                      Object dat = it.next();
                                      datastr = datastr + dat.toString() + "\n";
                                   } 
                              Alert alert = new Alert(Alert.AlertType.INFORMATION);
                              alert.setTitle("Предупреждение");
                              alert.setHeaderText("В колонке №3 обнаружены не запрограммированные виды турникетов. Обратитесь по этому поводу к программисту разработчику этой программы.");
                              alert.setContentText(datastr);
                              alert.showAndWait(); 
                            } 
                         if (errortypeTurnstiles.size() > 0)
                            {
                              String datastr = "";
                                  for (Iterator it = errortypeTurnstiles.iterator(); it.hasNext();) {
                                      Object dat = it.next();
                                      datastr = datastr + dat.toString() + "\n";
                                   } 
                              Alert alert = new Alert(Alert.AlertType.INFORMATION);
                              alert.setTitle("Предупреждение");
                              alert.setHeaderText("В колонке №2 обнаружены не запрограммированные виды входов - выходов. Обратитесь по этому поводу к программисту разработчику этой программы.");
                              alert.setContentText(datastr);
                              alert.showAndWait(); 
                            }      
                     Collections.sort(listDataExcel, comparatorDataExcel);
                     boolean errorFirstLast = false;
                     String previousTypeTurnstiles = "";
                     Date tekDay = null;
                     String tekFio = "";
                     int numerEntry = 0;
                     int numerExit = 0;
                     int numerGuestPass = 0;
                     int numerAllEntryExit = 0;
                     Map<String, Object[]> linkedHashMapAllEntryExit = new LinkedHashMap<String,Object[]>();
                     Map<String, Object[]> linkedHashMapGuestPass = new LinkedHashMap<String,Object[]>();
                     Map<String, Object[]> linkedHashMapEntry = new LinkedHashMap<String,Object[]>();
                     Map<String, Object[]> linkedHashMapExit = new LinkedHashMap<String, Object[]>();
                     String strDateFormat = "kk:mm"; //Date format is Specified
                     SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strDateFormat); 
                     Calendar convertTime = Calendar.getInstance();
                        for (Iterator<DataExcel> it = listDataExcel.iterator(); it.hasNext();) {
                              DataExcel dataExcel = (DataExcel) it.next();
                              Date getTekDay = dataExcel.getTekDay();
                              String getFio = dataExcel.getFio();
                              Date getDate = dataExcel.getDate();
                              boolean entry = dataExcel.isEntry();
                              boolean guestPass = dataExcel.isGuestPass();
                              String datFormat = simpleDateFormat.format(getDate) + ":00";
                              Time valueTime = java.sql.Time.valueOf(datFormat);
                              String TypeTurnstiles = dataExcel.getTypeTurnstiles();
                              String NameTurnstiles = dataExcel.getNameTurnstiles();
                                if (NameTurnstiles.equals("Дверь_2Б_1эт_холл") == true || NameTurnstiles.equals("Дверь_2Б_1эт_двор"))
                                   {NameTurnstiles = "Дверь_2Б";}
                              Object[] objects = new Object[] {valueTime, TypeTurnstiles, NameTurnstiles};
                                if (tekDay == null)
                                    {
                                     tekDay = getTekDay;
                                        if (TypeTurnstiles.equals("Фактический вход") == false && TypeTurnstiles.equals("Нормальный вход по ключу") == false)
                                            {errorFirstLast = true;}     
                                             //первым ввсегда должен быть вход а не выход
                                    }
                                else
                                    {
                                     if (tekDay.before(getTekDay) == true)
                                        {
                                         if (previousTypeTurnstiles.equals("Фактический выход") == false && previousTypeTurnstiles.equals("Нормальный выход по ключу") == false)
                                             {errorFirstLast = true;}         
                                         //последний должен быть выходом   
                                         AddlistDataExcelTotals(errorFirstLast, listDataExcelDay, linkedHashMapGuestPass, linkedHashMapEntry, linkedHashMapExit, linkedHashMapAllEntryExit, tekDay, tekFio);
                                         numerEntry = 0;
                                         numerExit = 0;
                                         numerGuestPass = 0;
                                         numerAllEntryExit = 0;
                                         linkedHashMapAllEntryExit.clear();
                                         linkedHashMapGuestPass.clear();
                                         linkedHashMapEntry.clear();
                                         linkedHashMapExit.clear();
                                         errorFirstLast = false;
                                         tekFio = "";
                                         tekDay = getTekDay;
                                              if (TypeTurnstiles.equals("Фактический вход") == false && TypeTurnstiles.equals("Нормальный вход по ключу") == false)
                                                 {errorFirstLast = true;}     
                                               //первым ввсегда должен быть вход а не выход  
                                        }
                                    }
                             
                                if (tekFio == "")
                                    {
                                     tekFio = getFio;
                                    }
                                else
                                    {
                                      if (tekFio.equals(getFio) == false)
                                         {
                                            if (previousTypeTurnstiles.equals("Фактический выход") == false && previousTypeTurnstiles.equals("Нормальный выход по ключу") == false)
                                               {errorFirstLast = true;}         
                                            //последний должен быть выходом   
                                            AddlistDataExcelTotals(errorFirstLast, listDataExcelDay, linkedHashMapGuestPass, linkedHashMapEntry, linkedHashMapExit, linkedHashMapAllEntryExit, tekDay, tekFio);
                                            numerEntry = 0;
                                            numerExit = 0;
                                            numerGuestPass = 0;
                                            numerAllEntryExit = 0;
                                            linkedHashMapAllEntryExit.clear();
                                            linkedHashMapGuestPass.clear();
                                            linkedHashMapEntry.clear();
                                            linkedHashMapExit.clear();
                                            errorFirstLast = false;
                                            tekFio = getFio;
                                            if (TypeTurnstiles.equals("Фактический вход") == false && TypeTurnstiles.equals("Нормальный вход по ключу") == false)
                                                 {errorFirstLast = true;}     
                                               //первым ввсегда должен быть вход а не выход
                                          }
                                     }
                              previousTypeTurnstiles  = TypeTurnstiles;    
                                if (guestPass == true)
                                   {
                                    boolean find = false;
                                        for (Iterator<Map.Entry<String, Object[]>> it3 = linkedHashMapGuestPass.entrySet().iterator(); it3.hasNext();) {
                                             Map.Entry<String, Object[]> entryKeyVal = it3.next();
                                             Object[] value = entryKeyVal.getValue();
                                                 if (value[0].equals(objects[0]) == true)
                                                    {
                                                     find = true;   
                                                     break;
                                                    }   
                                                 else if (value[2].equals(objects[2]) == true && value[2].equals("Дверь_2Б") == true)
                                                     {
                                                        Time time = (Time) value[0];
                                                        long second = (valueTime.getTime() - time.getTime())/1000;
                                                             if (second < 0)
                                                                {
                                                                 if (second > -300)
                                                                     {
                                                                       find = true;  
                                                                       break;
                                                                      }
                                                                 }
                                                              else if (second < 300)
                                                                 {
                                                                   find = true;  
                                                                   break;
                                                                  }
                                                       }
                                            } 
                                        if (find == false)
                                           {
                                             numerGuestPass = numerGuestPass + 1;
                                             linkedHashMapGuestPass.put("GuestPass" + numerGuestPass, objects);
                                             numerAllEntryExit = numerAllEntryExit + 1;
                                             linkedHashMapAllEntryExit.put("AllEntryExit" + numerAllEntryExit, objects);
                                           }; 
                                   }
                                else
                                   {
                                    if (entry == true)
                                        {
                                         boolean find = false;
                                            for (Iterator<Map.Entry<String, Object[]>> it3 = linkedHashMapEntry.entrySet().iterator(); it3.hasNext();) {
                                                  Map.Entry<String, Object[]> entryKeyVal = it3.next();
                                                  Object[] value = entryKeyVal.getValue();
                                                        if (value[0].equals(objects[0]) == true)
                                                           {
                                                             find = true;  
                                                             break;
                                                           }    
                                                        else if (value[2].equals(objects[2]) == true && value[2].equals("Дверь_2Б") == true)
                                                           {
                                                              Time time = (Time) value[0];
                                                              long second = (valueTime.getTime() - time.getTime())/1000;
                                                                 if (second < 0)
                                                                    {
                                                                      if (second > -300)
                                                                         {
                                                                           find = true;  
                                                                           break;
                                                                         }
                                                                    }
                                                                 else if (second < 300)
                                                                     {
                                                                      find = true;  
                                                                      break;
                                                                     }
                                                           }
                                                } 
                                            
                                            if (find == false)
                                               {
                                                 numerEntry = numerEntry + 1;
                                                 linkedHashMapEntry.put("Entry" + numerEntry, objects);
                                                 numerAllEntryExit = numerAllEntryExit + 1;
                                                 linkedHashMapAllEntryExit.put("AllEntryExit" + numerAllEntryExit, objects);
                                               }; 
                                        }
                                    else
                                        {
                                         boolean find = false;
                                            for (Iterator<Map.Entry<String, Object[]>> it3 = linkedHashMapExit.entrySet().iterator(); it3.hasNext();) {
                                                  Map.Entry<String, Object[]> entryKeyVal = it3.next();
                                                  Object[] value = entryKeyVal.getValue();
                                                        if (value[0].equals(objects[0]) == true)
                                                           {
                                                             find = true;   
                                                             break;
                                                           }   
                                                        else if (value[2].equals(objects[2]) == true && value[2].equals("Дверь_2Б") == true)
                                                           {
                                                              Time time = (Time) value[0];
                                                              long second = (valueTime.getTime() - time.getTime())/1000;
                                                                 if (second < 0)
                                                                    {
                                                                      if (second > -300)
                                                                         {
                                                                           find = true;  
                                                                           break;
                                                                         }
                                                                    }
                                                                 else if (second < 300)
                                                                     {
                                                                      find = true;  
                                                                      break;
                                                                     }
                                                                     
                                                           }

                                                } 
                                             if (find == false)
                                                 {
                                                   numerExit = numerExit + 1;
                                                   linkedHashMapExit.put("Exit" + numerExit, objects);
                                                   numerAllEntryExit = numerAllEntryExit + 1;
                                                   linkedHashMapAllEntryExit.put("AllEntryExit" + numerAllEntryExit, objects);
                                                 }; 
                                         }
                                    }
                           }    
                         if (linkedHashMapGuestPass.size() > 0 || linkedHashMapEntry.size() > 0 || linkedHashMapExit.size() > 0)
                           {
                             if (previousTypeTurnstiles.equals("Фактический выход") == false && previousTypeTurnstiles.equals("Нормальный выход по ключу") == false)
                                {errorFirstLast = true;}      
                             AddlistDataExcelTotals(errorFirstLast, listDataExcelDay, linkedHashMapGuestPass, linkedHashMapEntry, linkedHashMapExit, linkedHashMapAllEntryExit, tekDay, tekFio);
                           }
                     // заполняем сотрудников
                     Session currentsession =  Turnstiles.sessionfactory.openSession();
                     Query Qw = currentsession.createQuery("From Employees Emp where Emp.notRelevant = :notRelevant and (Emp.fio in (:fioList) or Emp.correspondenceOfSurnames.employeesReplece in (:fioList))");
                     Qw.setParameter("fioList", fioTurnstiles);
                     Qw.setParameter("notRelevant", false);                     
                     ObservableList<Employees> dataEmployees = FXCollections.observableArrayList(Qw.list());
                     fioTurnstiles.sort(comparatorfioTurnstiles);
                          for (Iterator it = fioTurnstiles.iterator(); it.hasNext();) {
                              String fio = (String) it.next(); 
                              AdditionalEmployees additionalEmployees = new AdditionalEmployees();  
                              boolean find = false;
                                   for (Iterator it2 = dataEmployees.iterator(); it2.hasNext();) {
                                       Employees emp = (Employees) it2.next();
                                       String fio2 = emp.getFio();
                                            if (fio.equals(fio2) == true)
                                               {
                                                 find = true; 
                                                 additionalEmployees.setEmployees(emp);
                                                 break;
                                               }
                                            else if (emp.correspondenceOfSurnames != null)
                                               {
                                                 if (fio.equals(emp.correspondenceOfSurnames.getEmployeesReplece()) == true)
                                                    {
                                                     find = true; 
                                                     additionalEmployees.setEmployees(emp);
                                                     break;
                                                    }
                                               }
                                                
                                    }     
                                  if (find == false)
                                    {
                                      additionalEmployees.setFioNofindEmployees(fio);
                                     }
                                   for (Iterator it2 = listDataExcelDay.iterator(); it2.hasNext();) {
                                       DataExcelDay dtExcelDay = (DataExcelDay) it2.next();
                                       String fio2 = dtExcelDay.getFio();
                                            if (fio.equals(fio2) == true)
                                               {
                                                dtExcelDay.FillInTheDisplayFields(this);
                                                FillTheDetailsOfTheDays(dtExcelDay, dtExcelDay.isError());
                                                    if (dtExcelDay.isError() == false)
                                                       {
                                                         additionalEmployees.dataExcelDay.add(dtExcelDay);
                                                       } 
                                                    else
                                                       {
                                                          additionalEmployees.dataExcelDayErr.add(dtExcelDay);
                                                          additionalEmployees.setErrorDataExcelDay(true);
                                                       }
                                               }
                                    }     
                                additionalEmployees.FillInTheDisplayFieldsFileTurnstilesLoading(this);
                                dataAdditionalEmployees.add(additionalEmployees);
                            }   
                          
                      tableviewAdditionalEmployees.setItems(dataAdditionalEmployees);
                      currentsession.close();
                }  
             catch (IOException ex) {
                } catch (ParseException ex) {
        }
             
    }
  
private void FillTheDetailsOfTheDays(DataExcelDay dtExcelDay, boolean error){
  
   if (error== true)
      {
        for (Iterator it2 = dtExcelDay.dataExcelErr.iterator(); it2.hasNext();) {
            DataExcel dtExcel = (DataExcel) it2.next();
            dtExcel.FillInTheDisplayFieldsErr();
        }
      }
   else
      {
        for (Iterator it2 = dtExcelDay.dataExcel.iterator(); it2.hasNext();) {
            DataExcel dtExcel = (DataExcel) it2.next();
            dtExcel.FillInTheDisplayFields();
        }       
      }    
}


private void AddlistDataExcelTotals(boolean errorFirstLast, ObservableList<DataExcelDay> listDataExcelDay, Map<String, Object[]> linkedHashMapGuestPass, Map<String,Object[]> linkedHashMapEntry, Map<String, Object[]> linkedHashMapExit, Map<String, Object[]>  linkedHashMapAllEntryExit, Date tekDay, String tekFio ) {
    
  int sizeGuestPass = linkedHashMapGuestPass.size();  
  int sizeEntry = linkedHashMapEntry.size();  
  int sizeExit = linkedHashMapExit.size(); 
  boolean errorTime = false;
  DataExcelDay dataExcelDayErr = new DataExcelDay();
  dataExcelDayErr.setTekDay(tekDay);
  dataExcelDayErr.setFio(tekFio);  
  dataExcelDayErr.setError(true);
  DataExcelDay dataExcelDay = new DataExcelDay();
  dataExcelDay.setTekDay(tekDay);
  dataExcelDay.setFio(tekFio);  
  
      if (sizeGuestPass > 0 | sizeEntry != sizeExit)
         {
            errorTime = true;
         }
      else if (sizeEntry == sizeExit && errorFirstLast == false)
         {
            int tekNumber = 0;
                while("1" == "1") {
                    tekNumber = tekNumber + 1;  
                        if (tekNumber > sizeEntry)  
                           {break;}
                        if (errorTime == true)
                            {break;}
                    Object[] objEntry = linkedHashMapEntry.get("Entry" + tekNumber);
                    Object[] objExit = linkedHashMapExit.get("Exit" + tekNumber);
                    int tekNumberEntry = 0;
                    int tekNumberExit = 0;
                    DataExcel dataExcel = new DataExcel();
                    dataExcel.setTekDay(tekDay);
                    dataExcel.setFio(tekFio);
                        if (tekNumber == 1)
                           {
                             tekNumberEntry = tekNumber;// первый вход
                             tekNumberExit = sizeEntry; // последний выход
                             dataExcel.setFirstEntryLastExit(true);
                            }
                        else
                            {
                              tekNumberEntry = tekNumber;
                              tekNumberExit = tekNumber - 1; //выход всегда раньше входа
                             }
                                        
                    objEntry = linkedHashMapEntry.get("Entry" + tekNumberEntry);
                    Time timeEntry = (Time) objEntry[0];
                    String TypeTurnstilesEntry = (String) objEntry[1];
                    String NameTurnstilesEntry = (String) objEntry[2];                                
                    dataExcel.setTimeEntry(timeEntry);
                    dataExcel.setNameTurnstilesEntry(NameTurnstilesEntry);
                    dataExcel.setTypeTurnstilesEntry(TypeTurnstilesEntry);
                    objExit = linkedHashMapExit.get("Exit" + tekNumberExit);
                    Time timeExit = (Time) objExit[0];
                    String TypeTurnstilesExit = (String) objExit[1];
                    String NameTurnstilesExit = (String) objExit[2];                                
                    dataExcel.setTimeExit(timeExit);
                    dataExcel.setNameTurnstilesExit(NameTurnstilesExit);
                    dataExcel.setTypeTurnstilesExit(TypeTurnstilesExit);
                        //выход раньше входа за исключением первого элемента
                    long second = 0;
                        if (tekNumber == 1)
                            { 
                              second = (timeExit.getTime() - timeEntry.getTime())/1000;   
                              //вычитаем обед 
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
                              errorTime = true;   
                            }
                        else
                            {
                              dataExcel.setTimeAbsenceSecond(second);
                              int hour = (int) (second / 3600);
                              int min = (int) ((second - hour * 3600) / 60);
                              String strTimeAbsenceSecond = "ч: " + hour + " м: " + min;
                              dataExcel.setStrTimeAbsenceSecond(strTimeAbsenceSecond);
                             }
                     dataExcelDay.dataExcel.add(dataExcel);
                }
        }
        
        if (errorTime == true || errorFirstLast == true)
           {
             int sizeAllEntryExit = linkedHashMapAllEntryExit.size(); 
             int tekNumber = 0;
                 while("1" == "1") {
                       tekNumber = tekNumber + 1;  
                           if (tekNumber > sizeAllEntryExit)  
                              {break;}  
                       Object[] objEntry = linkedHashMapAllEntryExit.get("AllEntryExit" + tekNumber);
                       DataExcel dataExcelErr = new DataExcel();
                       dataExcelErr.setTekDay(tekDay);
                       dataExcelErr.setFio(tekFio);  
                       Time time = (Time) objEntry[0];
                       String TypeTurnstiles = (String) objEntry[1];
                       String NameTurnstiles = (String) objEntry[2];                                
                       dataExcelErr.setTime(time);
                       dataExcelErr.setNameTurnstiles(NameTurnstiles);
                       dataExcelErr.setTypeTurnstiles(TypeTurnstiles);
                       dataExcelErr.setError(true);
                       dataExcelDayErr.dataExcelErr.add(dataExcelErr);
                     }
              listDataExcelDay.add(dataExcelDayErr);                 
           }
        else
           {
               listDataExcelDay.add(dataExcelDay);                 
           }
}
     
     
    Comparator<DataExcel> comparatorDataExcel = new Comparator<DataExcel>() {
        @Override
        public int compare(DataExcel o1, DataExcel o2) {
             int tekDayComp = o1.getTekDay().compareTo(o2.getTekDay());
             int fioComp = o1.getFio().compareTo(o2.getFio());
             
             return ( (tekDayComp == 0) ? (fioComp == 0) ? o1.getDate().compareTo(o2.getDate()) : fioComp : tekDayComp  );
        }
    };
    
    Comparator<String> comparatorfioTurnstiles = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
             return o1.compareTo(o2);
        }
    };
    
    Comparator<DataExcelDay> comparatorTekDayDataExcelDay = new Comparator<DataExcelDay>() {
      @Override
        public int compare(DataExcelDay o1, DataExcelDay o2) {
           
            return o1.getTekDay().compareTo(o2.getTekDay());
        }
    };
    
    public  void CurrentlabelHandleMouseClickedAdditionalEmployees (AdditionalEmployees additionalEmployees, Label label1) {
    //Вызывается из классов Subdivision и dolzhnosti для его элементов управления встроенных в vbox
        currentAdditionalEmployees =  additionalEmployees;
            if (currentlabelAdditionalEmployees != null) 
                 {currentlabelAdditionalEmployees.setTextFill(Color.WHITE);}
              
        currentlabelAdditionalEmployees = label1; 
        currentlabelAdditionalEmployees.setTextFill(Color.web("#0fced9"));
             if (currentlabelDataExcelDay != null) 
                     {currentlabelDataExcelDay.setTextFill(Color.WHITE);}
             if (currentlabelDataExcelDayErr != null) 
                     {currentlabelDataExcelDayErr.setTextFill(Color.WHITE);}
        
        currentDataExcelDay =  null;
        currentlabelDataExcelDay = null;
        currentDataExcelDayErr =  null;
        currentlabelDataExcelDayErr = null;
        currentDataExcelDayEdit = null;
        removedErrorDay.setText("Убрать ошибки за");
         
        //загружаем таблицу дней 
        LoadTableviewDataExcelDay();          
    }
     
    public void LoadTableviewDataExcelDay() {
        boolean clearDataExcel = false;
        boolean clearDataExcelErr = false;
        
        
        if (currentAdditionalEmployees != null)
             {
                tableviewDataExcelDay.setItems(currentAdditionalEmployees.dataExcelDay);
                tableviewDataExcelDayErr.setItems(currentAdditionalEmployees.dataExcelDayErr);
                    if (currentAdditionalEmployees.dataExcelDay.size() > 0)
                       {
                         DataExcelDay dataExcelDay  =  currentAdditionalEmployees.dataExcelDay.get(0);
                         CurrentlabelHandleMouseClickedDataExcelDay (dataExcelDay, dataExcelDay.label1);
                       }
                    else
                       {
                         clearDataExcel = true;
                       }
                    if (currentAdditionalEmployees.dataExcelDayErr.size() > 0)
                       {
                         DataExcelDay dataExcelDay  =  currentAdditionalEmployees.dataExcelDayErr.get(0);
                         CurrentlabelHandleMouseClickedDataExcelDay (dataExcelDay, dataExcelDay.label1);
                       }
                    else
                       {
                         clearDataExcelErr = true;
                       }
             }
        else 
             {
                ObservableList<DataExcelDay> dataExcelDay = FXCollections.observableArrayList();
                tableviewDataExcelDay.setItems(dataExcelDay);
                tableviewDataExcelDayErr.setItems(dataExcelDay);
                clearDataExcel = true;
                clearDataExcelErr = true;
        
             }
        tableviewDataExcelDay.refresh();
        tableviewDataExcelDayErr.refresh();
        
        if (clearDataExcel == true || clearDataExcelErr == true)
             {
               ObservableList<DataExcel> dataExcel = FXCollections.observableArrayList();
                    if (clearDataExcel == true)
                        {
                          tableviewDataExcel.setItems(dataExcel);
                          tableviewDataExcel.refresh();
                        }
                    if (clearDataExcelErr == true)
                        {
                          tableviewDataExcelErr.setItems(dataExcel);
                          tableviewDataExcelErr.refresh();
                          ObservableList<DataExcelDayEdit> dataExcelDayEdit = FXCollections.observableArrayList();
                          tableviewEdit.setItems(dataExcelDayEdit);
                          tableviewEdit.refresh();
                        }
             }
    }
    
    
     public  void CurrentlabelHandleMouseClickedDataExcelDay (DataExcelDay dataExcelDay, Label label1) {
    //Вызывается из классов Subdivision и dolzhnosti для его элементов управления встроенных в vbox
        if (dataExcelDay.isError() == false)
           { 
             currentDataExcelDay =  dataExcelDay;
                  if (currentlabelDataExcelDay != null) 
                     {currentlabelDataExcelDay.setTextFill(Color.WHITE);}
             currentlabelDataExcelDay = label1; 
             currentlabelDataExcelDay.setTextFill(Color.web("#0fced9"));
           }
        else
           {
             currentDataExcelDayErr =  dataExcelDay;
                  if (currentlabelDataExcelDayErr != null) 
                     {currentlabelDataExcelDayErr.setTextFill(Color.WHITE);}
             currentlabelDataExcelDayErr = label1; 
             currentlabelDataExcelDayErr.setTextFill(Color.web("#0fced9"));
             currentDataExcelDayEdit = null;
             removedErrorDay.setText("Убрать ошибки за " + currentlabelDataExcelDayErr.getText());
             
           }
       
        //загружаем таблицу деталей по днм
        LoadTableviewDataExcel(dataExcelDay.isError());          
    }
    
        
    public void LoadTableviewDataExcel(boolean errorDay) {
      
        if (errorDay == false)
           { 
             if (currentDataExcelDay!= null)
                {
                 tableviewDataExcel.setItems(currentDataExcelDay.dataExcel);
                }
             else 
                {
                 ObservableList<DataExcel> dataExcel = FXCollections.observableArrayList();
                 tableviewDataExcel.setItems(dataExcel);
                }
             tableviewDataExcel.refresh();
           }  
        else
           {
            if (currentDataExcelDayErr!= null)
                {
                 tableviewDataExcelErr.setItems(currentDataExcelDayErr.dataExcelErr);
                 tableviewEdit.setItems(currentDataExcelDayErr.dataExcelDayEdit);
                }
            else 
                {
                 ObservableList<DataExcel> dataExcel = FXCollections.observableArrayList();
                 tableviewDataExcelErr.setItems(dataExcel);
                 ObservableList<DataExcelDayEdit> dataExcelDayEdit = FXCollections.observableArrayList();
                 tableviewEdit.setItems(dataExcelDayEdit);
                }
            tableviewDataExcelErr.refresh();
            tableviewEdit.refresh();
           }
      
    }
   
    
    public  void CurrentDataExcelDayEditHandleMouseClicked (DataExcelDayEdit dataExcelDayEdit) {
        currentDataExcelDayEdit = dataExcelDayEdit;
    }
    
    
    public  void changeEmployeesHandleMouseClickedAdditionalEmployees (AdditionalEmployees additionalEmployees, Label label1) {
    //Вызывается из классов Subdivision и dolzhnosti для его элементов управления встроенных в vbox
    //1)открываем окно выбора нового сотрудника
    CurrentlabelHandleMouseClickedAdditionalEmployees (additionalEmployees, label1); 
    boolean  updateDb = this.mainApp.changeEmployeesFileTurnstilesLoading(additionalEmployees);
    //2) меняем его перезаполняем additionalEmployees
         if (updateDb == true)
            {
              additionalEmployees.FillInTheDisplayFieldsFileTurnstilesLoading(this);
              String fio = additionalEmployees.employees.getFio();
                  for (Iterator it2 = additionalEmployees.dataExcelDay.iterator(); it2.hasNext();) {
                       DataExcelDay dtExcelDay = (DataExcelDay) it2.next();
                       dtExcelDay.setFio(fio); 
                           for (Iterator it3 = dtExcelDay.dataExcel.iterator(); it3.hasNext();) {
                                DataExcel dtExcel = (DataExcel) it3.next();
                                dtExcel.setFio(fio); 
                            }    
                           for (Iterator it3 = dtExcelDay.dataExcelErr.iterator(); it3.hasNext();) {
                                DataExcel dtExcel = (DataExcel) it3.next();
                                dtExcel.setFio(fio); 
                            }       
                  }     
                  
                  for (Iterator it2 = additionalEmployees.dataExcelDayErr.iterator(); it2.hasNext();) {
                       DataExcelDay dtExcelDay = (DataExcelDay) it2.next();
                       dtExcelDay.setFio(fio); 
                           for (Iterator it3 = dtExcelDay.dataExcel.iterator(); it3.hasNext();) {
                                DataExcel dtExcel = (DataExcel) it3.next();
                                dtExcel.setFio(fio); 
                            }    
                           for (Iterator it3 = dtExcelDay.dataExcelErr.iterator(); it3.hasNext();) {
                                DataExcel dtExcel = (DataExcel) it3.next();
                                dtExcel.setFio(fio); 
                            }       
                   }     
              //3) обновляем tableviewAdditionalEmployees
              tableviewAdditionalEmployees.refresh();
              //4) обновляем currentAdditionalEmployees и  currentlabelAdditionalEmployees
              CurrentlabelHandleMouseClickedAdditionalEmployees (additionalEmployees, additionalEmployees.label1); 
            }
            
    } 
   
    
}


