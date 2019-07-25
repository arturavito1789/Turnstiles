
package turnstiles;

import com.jfoenix.controls.JFXDatePicker;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
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
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


public class ControllerDataTurnstilesLoading implements Initializable{
    
    private Stage dialogStage;
     
    @FXML
    private JFXDatePicker firstDayMonth;
    
    @FXML
    private TableView tableviewEmployees;
    
    @FXML
    private TableView tableviewTotalsTurnstiles;
    
    @FXML
    private TableView tableviewDataTurnstiles;
    
    @FXML
    private TableView tableviewFileTurnstiles;
    
    @FXML
    private TableView tableviewAbsenceJournal;
    
    @FXML
    private TableView tableviewDocuments1c;
    
    private Turnstiles mainApp;
    
    private ObservableList<Employees> dataEmployees;
    private ObservableList<TotalsTurnstilesDisplay> dataTotalsTurnstilesDisplay;
  
    boolean wasPressed = false;
    private Label currentlabeldataTotalsTurnstilesDisplay;
    private Employees currentEmployees;
    private Label currentlabelEmployees;
    boolean filter = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         TableColumn vboxEmployees = new TableColumn("vbox");      
         tableviewEmployees.getColumns().addAll(vboxEmployees);
         vboxEmployees.setCellValueFactory(
              new PropertyValueFactory<Employees,String>("vbox")
             );
         
        TableColumn vboxTotalsTurnstilesDisplay = new TableColumn("vbox");      
        tableviewTotalsTurnstiles.getColumns().addAll(vboxTotalsTurnstilesDisplay);
        vboxTotalsTurnstilesDisplay.setCellValueFactory(
              new PropertyValueFactory<TotalsTurnstilesDisplay,String>("vbox")
             );
        
        TableColumn vboxDataTurnstiles = new TableColumn("vbox");      
        tableviewDataTurnstiles.getColumns().addAll(vboxDataTurnstiles);
        vboxDataTurnstiles.setCellValueFactory(
              new PropertyValueFactory<DataTurnstiles,String>("vbox")
             );
        
        TableColumn vboxFileTurnstiles = new TableColumn("vbox");      
        tableviewFileTurnstiles.getColumns().addAll(vboxFileTurnstiles);
        vboxFileTurnstiles.setCellValueFactory(
              new PropertyValueFactory<FileTurnstiles,String>("vbox")
             );
       
        TableColumn vboxAbsenceJournal = new TableColumn("vbox");      
        tableviewAbsenceJournal.getColumns().addAll(vboxAbsenceJournal);
        vboxAbsenceJournal.setCellValueFactory(
              new PropertyValueFactory<AbsenceJournal,String>("vbox")
             );
       
        TableColumn vboxDocuments1c = new TableColumn("vbox");      
        tableviewDocuments1c.getColumns().addAll(vboxDocuments1c);
        vboxDocuments1c.setCellValueFactory(
              new PropertyValueFactory<Documents1c,String>("vbox")
             );
       
       
        
        
        
        dataEmployees = FXCollections.observableArrayList(); 
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
    @SuppressWarnings("null")
    private void FormDataHandleAction(ActionEvent event) {
      if (wasPressed == true)
         {
          wasPressed = false;
          return;         
         }
      tableviewTotalsTurnstiles.getItems().clear();
      tableviewEmployees.getItems().clear();  
      tableviewDataTurnstiles.getItems().clear();
      tableviewFileTurnstiles.getItems().clear();
      tableviewAbsenceJournal.getItems().clear();
      tableviewDocuments1c.getItems().clear();    
      
      currentlabeldataTotalsTurnstilesDisplay = null;
      currentEmployees = null;
      currentlabelEmployees = null;
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
      List dataTotalsTurnstiles = currentsession.createSQLQuery("select TotalsTurnstiles.tekDay, TotalsTurnstiles.workingHours From TotalsTurnstiles where TotalsTurnstiles.firstDayMonth = :firstDayMonth").setParameter("firstDayMonth", firstDayMonthDate).list();
           if (dataTotalsTurnstiles.size() == 0)
              {
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setTitle("Предупреждение");
                 alert.setHeaderText("Нет данных для загрузки");
                 alert.setContentText("За месяц " + firstDayMonthDate + " нет данных для загрузки. Видимо вы не выполнили шаг 2.");
                 alert.showAndWait(); 
                 return; 
              }
       TotalsTurnstilesDisplay totalsTurnstilesDisplay = new TotalsTurnstilesDisplay(); 
       ControllerTotalsTurnstilesLoading controllerTotalsTurnstilesLoading = new ControllerTotalsTurnstilesLoading();
       totalsTurnstilesDisplay.FillInTheDisplayFields(dataTotalsTurnstiles, this, controllerTotalsTurnstilesLoading);
       dataTotalsTurnstilesDisplay.add(totalsTurnstilesDisplay);
       tableviewTotalsTurnstiles.setItems(dataTotalsTurnstilesDisplay);
       dataEmployees = FXCollections.observableArrayList(currentsession.createQuery("From Employees Emp where Emp.notRelevant = :notRelevant").setParameter("notRelevant", false).list());
            for (Iterator it = dataTotalsTurnstiles.iterator(); it.hasNext();) {
                 Object day[] = (Object[]) it.next();
                 Date dateDay = (Date) day[0];
                 int workingHoursDay = (int) day[1];
                 Query query  = currentsession.createQuery("From Documents1c Fl where Fl.primaryKey.tekDay = :tekDay");   
                 query.setParameter("tekDay", dateDay);
                 List<Documents1c> dataDocuments1c = query.list();
                 
                 query  = currentsession.createQuery("From FileTurnstiles Fl where Fl.firstEntryLastExit = :firstEntryLastExit and Fl.tekDay = :tekDay");   
                 query.setParameter("tekDay", dateDay);
                 query.setParameter("firstEntryLastExit", true);
                 List<FileTurnstiles> dataFileTurnstilesFirst = query.list();
                
                 query  = currentsession.createSQLQuery("select  Fl.idEmployees, Fl.idSubdivision, Fl.timeAbsenceSecond,  Fl.idFileTurnstiles, AJ.idAbsenceJournal From FileTurnstiles Fl INNER JOIN AbsenceJournal AJ ON Fl.firstEntryLastExit = :firstEntryLastExit and Fl.tekDay = :tekDay and Fl.idEmployees =  AJ.idEmployees and Fl.idSubdivision =  AJ.idSubdivision and Fl.firstDayMonth =  AJ.firstDayMonth and Fl.tekDay =  AJ.tekDay and ((datediff(n, Fl.entryTime, AJ.entryTime) <= 5 and datediff(n, Fl.entryTime, AJ.entryTime) >= 0) or (datediff(n, AJ.entryTime, Fl.entryTime) <= 5 and datediff(n, AJ.entryTime, Fl.entryTime) >= 0)) and ((datediff(n, Fl.exitTime, AJ.exitTime) <= 5 and datediff(n, Fl.exitTime, AJ.exitTime) >= 0) or (datediff(n, AJ.exitTime, Fl.exitTime) <= 5 and datediff(n, AJ.exitTime, Fl.exitTime) >= 0)) "); 
                 query.setParameter("tekDay", dateDay);
                 query.setParameter("firstEntryLastExit", false);
                 List dataFileAbsenceEqual = query.list();
                
                 query  = currentsession.createQuery("From FileTurnstiles Fl where Fl.tekDay = :tekDay ");
                 query.setParameter("tekDay", dateDay);
                 List dataFileTurnstiles = query.list();
                 
                 query  = currentsession.createQuery("From AbsenceJournal AJ where AJ.tekDay = :tekDay ");
                 query.setParameter("tekDay", dateDay);
                 List dataAbsenceJournal = query.list();                 
                 
                     for (Iterator it2 = dataEmployees.iterator(); it2.hasNext();) {
                         Employees emp = (Employees) it2.next();
                         boolean foundDataInDb = false;
                         int idEmployeesEmp = emp.getPrimaryKey().getIdEmployees();
                         int idSubdivisionEmp = emp.getPrimaryKey().getIdSubdivision();                         
                         int workingHours = emp.getCountWorkingHours();
                         int absenceNotWorkingHours = 0;//отсутствие невыходы, больничные, отпуска  - не учитываем в рабочем времени
                         long longAbsenceNotWorkingHours = 0;
                         int difference = 0;
                         long notFinalized = 0;
                         boolean thereIsNoDistributedData = false;
                         DataTurnstiles dataTurnstiles = new DataTurnstiles();
                         dataTurnstiles.setTekDay(dateDay);
                         dataTurnstiles.setEmployees(emp);
                         dataTurnstiles.setMainApp(mainApp);
                         dataTurnstiles.setIdEmployees(idEmployeesEmp);
                         dataTurnstiles.setIdSubdivision(idSubdivisionEmp);
                         dataTurnstiles.setFirstDayMonth(firstDayMonthDate);
                         query  = currentsession.createQuery("From DataTurnstiles Dl where Dl.tekDay = :tekDay and Dl.employees = :emp ");
                         query.setParameter("tekDay", dateDay);
                         query.setParameter("emp", emp);
                         List dataDataTurnstiles = query.list();
                              for (Iterator it3 = dataDataTurnstiles.iterator(); it3.hasNext();) {
                                  foundDataInDb = true;
                                  DataTurnstiles dataTur = (DataTurnstiles) it3.next();
                                  dataTurnstiles.setWorkingHours(dataTur.getWorkingHours());
                                  dataTurnstiles.setNotFinalized(dataTur.getNotFinalized());
                                  dataTurnstiles.setStrNotFinalized(dataTur.getStrNotFinalized());
                                  dataTurnstiles.setWorkTime(dataTur.getWorkTime());
                                  dataTurnstiles.setStrWorkTime(dataTur.getStrWorkTime());
                                  dataTurnstiles.setWorkTimeFileAbsenceEqual(dataTur.getWorkTimeFileAbsenceEqual());
                                  dataTurnstiles.setStrWorkTimeFileAbsenceEqual(dataTur.getStrWorkTimeFileAbsenceEqual());
                                  dataTurnstiles.setRecast(dataTur.getRecast());
                                  dataTurnstiles.setStrRecast(dataTur.getStrRecast());
                                  dataTurnstiles.setTardiness(dataTur.getTardiness());
                                  dataTurnstiles.setStrTardiness(dataTur.getStrTardiness());
                                  dataTurnstiles.setAbsenceNotWorkingHours(dataTur.getAbsenceNotWorkingHours());
                                  dataTurnstiles.setStrAbsenceNotWorkingHours(dataTur.getStrAbsenceNotWorkingHours());
                                  break;
                              }
                           
                            if(workingHoursDay < 8)
                                { 
                                  //если это праздник то меньше часов соответственно уменьшаем график
                                  difference = 8 - workingHoursDay;   
                                  workingHours = workingHours - difference;    
                                }
                            if (foundDataInDb == false)
                                {
                                  dataTurnstiles.setWorkingHours(workingHours);
                                }
                            for (Iterator it3 = dataDocuments1c.iterator(); it3.hasNext();) {
                                  Documents1c doc1c = (Documents1c) it3.next();
                                  Employees emp1c = doc1c.getEmployees();
                                      if(emp.equals(emp1c) == true)
                                        {
                                         doc1c.FillInTheDisplayFieldsDataTurnstiles();
                                         dataTurnstiles.dataDocuments1c.add(doc1c);
                                         int hoursDay = doc1c.getHoursDay();
                                         absenceNotWorkingHours = absenceNotWorkingHours + hoursDay;
                                        }
                               }    
                            if (foundDataInDb == false)
                                {dataTurnstiles.setAbsenceNotWorkingHours(absenceNotWorkingHours);}
                            
                        longAbsenceNotWorkingHours = absenceNotWorkingHours * 3600; 
                              if (absenceNotWorkingHours > 0 && foundDataInDb == false) 
                                  {
                                    String  str = "ч: " + absenceNotWorkingHours;
                                    dataTurnstiles.setStrAbsenceNotWorkingHours(str);
                                  }
                               if (workingHours > absenceNotWorkingHours)
                                  {
                                    long workTime = 0;
                                    long workTimeFileAbsenceEqual = 0;
                                    //если часов работы по графику в день больше чем человек был в невыходах.
                                    //к примеру есть невыход на 2 часа - необходимо подсчитать остальное рабочее время.
                                    //1) определяем полностью рабочее время  
                                    if (foundDataInDb == false)
                                       {
                                         for (Iterator it3 = dataFileTurnstilesFirst.iterator(); it3.hasNext();) {
                                             FileTurnstiles fileTurnstiles = (FileTurnstiles) it3.next();
                                             Employees emp1c = fileTurnstiles.getEmployees();
                                                if(emp.equals(emp1c) == true)
                                                   { 
                                                    dataTurnstiles.setFirstEntry(fileTurnstiles.getEntryTime());
                                                    dataTurnstiles.setLastExit(fileTurnstiles.getExitTime());
                                                    workTime = fileTurnstiles.getTimeAbsenceSecond();
                                                    dataTurnstiles.setWorkTime(workTime);
                                                    dataTurnstiles.setStrWorkTime(fileTurnstiles.getStrTimeAbsenceSecond());
                                                    break;
                                                   }
                                            }
                                        }
                                     //2)определяем соответствие времени файла турнекета и журнала. 
                                     for (Iterator it3 =  dataFileAbsenceEqual.iterator(); it3.hasNext();) {
                                          Object obj[] = (Object[]) it3.next();
                                          int idEmployees = (int) obj[0];
                                          int idSubdivision = (int) obj[1];
                                              if(idEmployees == idEmployeesEmp && idSubdivision == idSubdivisionEmp)
                                                { 
                                                  int idFileTurnstiles = (int) obj[3];
                                                  dataTurnstiles.listIdFileTurnstiles.add(idFileTurnstiles);
                                                  int idAbsenceJournal = (int) obj[4];
                                                  dataTurnstiles.listIdAbsenceJournal.add(idAbsenceJournal);
                                                  BigInteger workTimeFileAbsenceEq = new BigInteger(obj[2].toString());
                                                  workTimeFileAbsenceEqual = workTimeFileAbsenceEqual + workTimeFileAbsenceEq.longValue();
                                                }
                                     }
                                     //3) добавляем все соответствующие записи по таблицам FileTurnstiles и AbsenceJournal при этом 
                                     //для каждой записи если найдено соответствие в обоих таблицах указываем это.
                                     int sizeListIdFileTurnstiles = dataTurnstiles.listIdFileTurnstiles.size();
                                     int sizeListIdAbsenceJournal = dataTurnstiles.listIdAbsenceJournal.size();          
                                     for (Iterator it3 =  dataFileTurnstiles.iterator(); it3.hasNext();) {
                                          FileTurnstiles fileTurnstiles = (FileTurnstiles) it3.next();
                                          Employees emp1c = fileTurnstiles.getEmployees();
                                              if(emp.equals(emp1c) == true)
                                                { 
                                                   boolean thereIsMatchFileTurnstiles = false;
                                                        if (fileTurnstiles.isFirstEntryLastExit() == true) 
                                                           {
                                                             thereIsMatchFileTurnstiles = true;
                                                           } 
                                                        else
                                                           {
                                                             if (sizeListIdFileTurnstiles > 0)
                                                                {
                                                                  int idFileTurnstiles = fileTurnstiles.getIdFileTurnstiles();
                                                                      if (dataTurnstiles.listIdFileTurnstiles.contains(idFileTurnstiles) == true)
                                                                         {
                                                                           thereIsMatchFileTurnstiles = true;
                                                                         }
                                                                }
                                                           }
                                                        
                                                        if (thereIsMatchFileTurnstiles == false)
                                                           {
                                                             thereIsNoDistributedData = true;
                                                           }  
                                                   fileTurnstiles.setThereIsMatchAbsenceJournal(thereIsMatchFileTurnstiles);
                                                   fileTurnstiles.FillInTheDisplayFields();
                                                   dataTurnstiles.dataFileTurnstiles.add(fileTurnstiles);
                                                }   
                                     }
                                     for (Iterator it3 =  dataAbsenceJournal.iterator(); it3.hasNext();) {
                                          AbsenceJournal absenceJournal = (AbsenceJournal) it3.next();
                                          Employees emp1c = absenceJournal.getEmployees();
                                              if(emp.equals(emp1c) == true)
                                                { 
                                                   boolean thereIsMatchAbsenceJournal = false;
                                                        if (sizeListIdFileTurnstiles > 0)
                                                           {
                                                             int idAbsenceJournal = absenceJournal.getIdAbsenceJournal();
                                                                 if (dataTurnstiles.listIdAbsenceJournal.contains(idAbsenceJournal) == true)
                                                                    {
                                                                     thereIsMatchAbsenceJournal = true;
                                                                    }
                                                           }
                                                        if (thereIsMatchAbsenceJournal == false)
                                                           {
                                                             thereIsNoDistributedData = true;
                                                           }  
                                                    absenceJournal.setThereIsMatchFileTurnstiles(thereIsMatchAbsenceJournal);
                                                    absenceJournal.FillInTheDisplayFieldsDataTurnstiles();
                                                    dataTurnstiles.dataAbsenceJournal.add(absenceJournal);
                                                }   
                                     }
                                     dataTurnstiles.setThereIsNoDistributedData(thereIsNoDistributedData);
                                    //4)если сотрудник был в невыходах (время не учитываемое в рабочем времени) и при этом сделал запись в 
                                     //  журнале (время учитываемое в рабочем времени) необходимо уменьшить время в журнале на время по невыходам
                                     if (absenceNotWorkingHours > 0 && workTimeFileAbsenceEqual > 0 && foundDataInDb == false)
                                         {
                                            if (longAbsenceNotWorkingHours > workTimeFileAbsenceEqual) 
                                                {
                                                  workTimeFileAbsenceEqual = 0; 
                                                }
                                            else
                                                {
                                                  workTimeFileAbsenceEqual = workTimeFileAbsenceEqual - longAbsenceNotWorkingHours;
                                                }
                                          }
                                      //5) уменьшаем время на обед
                                      if (foundDataInDb == false)
                                         {
                                           if  (workTimeFileAbsenceEqual > 3600)
                                             {
                                               workTimeFileAbsenceEqual = workTimeFileAbsenceEqual -3600; // уменьшаем на обед
                                             }
                                           else
                                             { 
                                               workTimeFileAbsenceEqual = 0;
                                             }
                                         }
                                      //6) определяем поля: отработано часов(workTime), переработка(recast), недоработка(норма - отработано часов), 
                                      //   опоздание(tardiness), отсутствие учитывается в рабочем времени (workTimeFileAbsenceEqual) 
                                      if (workTime > 0 && foundDataInDb == false)
                                          {
                                            boolean tardinessTek = false;
                                            long recast = 0;//переработка
                                            calendar.setTime(dateDay);
                                            calendar.set(Calendar.DAY_OF_YEAR, 1);
                                            calendar.set(Calendar.MONTH, 1);
                                            calendar.set(Calendar.YEAR, 1970);
                                            calendar.set(Calendar.MINUTE, 0);
                                            calendar.set(Calendar.HOUR, 9);
                                            Date DateH = calendar.getTime();
                                            long second = (DateH.getTime() - dataTurnstiles.getFirstEntry().getTime())/1000; 
                                            //second - если значение положительное то это переработка ( 9 ч - 8 = 1) 
                                            //если отрицательное то это опоздание (9-10 = (-1))
                                                  if (second < 0)
                                                     {
                                                       second = (-1) * second;
                                                       tardinessTek = true;                                           
                                                     }
                                                   if (tardinessTek == true)
                                                      {
                                                        int hour = (int) (second / 3600);
                                                        int min = (int) ((second - hour * 3600) / 60);
                                                        String str = "ч: " + hour + " м: " + min; 
                                                        dataTurnstiles.setTardiness(second);
                                                        dataTurnstiles.setStrTardiness(str);
                                                        notFinalized = second;
                                                           if (longAbsenceNotWorkingHours > 0)
                                                              {
                                                                if (second > longAbsenceNotWorkingHours)
                                                                   {
                                                                    longAbsenceNotWorkingHours = 0;
                                                                   } 
                                                                else
                                                                   {
                                                                    longAbsenceNotWorkingHours = longAbsenceNotWorkingHours - second;
                                                                   }
                                                              }
                                                      }
                                                   else
                                                      {
                                                        recast = second;
                                                      }
                                              calendar.set(Calendar.HOUR, 18 - difference);
                                              DateH = calendar.getTime();
                                              second = (DateH.getTime() - dataTurnstiles.getLastExit().getTime())/1000;
                                                    if (second < 0)
                                                       {
                                                         second = (-1) * second;
                                                         recast = recast + second;
                                                       }
                                                    else
                                                       {
                                                         notFinalized = notFinalized + second;
                                                            if (longAbsenceNotWorkingHours > 0)
                                                               {
                                                                if (second > longAbsenceNotWorkingHours)
                                                                   {
                                                                    longAbsenceNotWorkingHours = 0;
                                                                   } 
                                                                else
                                                                   {
                                                                    longAbsenceNotWorkingHours = longAbsenceNotWorkingHours - second;
                                                                   }
                                                               }
                                                         
                                                       }
                                                notFinalized = notFinalized + longAbsenceNotWorkingHours;    
                                                    if (recast > 0 & notFinalized > 0)
                                                       {
                                                         if (notFinalized > recast)
                                                            {
                                                              notFinalized = notFinalized -  recast; 
                                                              recast = 0;  
                                                            }
                                                         else
                                                            {
                                                               recast = recast - notFinalized;
                                                               notFinalized = 0;
                                                            }
                                                       }
                                               dataTurnstiles.setRecast(recast);
                                               dataTurnstiles.setWorkTimeFileAbsenceEqual(workTimeFileAbsenceEqual);
                                                    if (longAbsenceNotWorkingHours > 0)
                                                        {
                                                          // есть три варианта:
                                                          // 1) пришла с утра. Время longAbsenceNotWorkingHours - будет уменьшено за счет опоздания
                                                          // в workTime попадет недоработка так как пришла поже
                                                          // 2) ушла раньше. Время longAbsenceNotWorkingHours будет уменьшено за счет выхода раньше 18
                                                          // также это отризатся на workTime.
                                                          // 3) ушла в период рабочего дня и вернулась надо уменьшить  workTime 
                                                          if (workTime > longAbsenceNotWorkingHours)  
                                                              {
                                                               workTime = workTime - longAbsenceNotWorkingHours;
                                                              }
                                                          else
                                                              {
                                                               workTime = 0;
                                                              }
                                                          int hour = (int) (workTime / 3600);
                                                          int min = (int) ((workTime - hour * 3600) / 60);
                                                          String str = "ч: " + hour + " м: " + min; 
                                                          dataTurnstiles.setWorkTime(workTime);
                                                          dataTurnstiles.setStrWorkTime(str);                                                          
                                                        }

                                                    if (workTimeFileAbsenceEqual > 0)
                                                        {
                                                          int hour = (int) (workTimeFileAbsenceEqual / 3600);
                                                          int min = (int) ((workTimeFileAbsenceEqual - hour * 3600) / 60);
                                                          String str = "ч: " + hour + " м: " + min; 
                                                          dataTurnstiles.setStrWorkTimeFileAbsenceEqual(str);
                                                        }
                                                     if (recast > 0)
                                                        {
                                                          int hour = (int) (recast / 3600);
                                                          int min = (int) ((recast - hour * 3600) / 60);
                                                          String str = "ч: " + hour + " м: " + min; 
                                                          dataTurnstiles.setStrRecast(str);
                                                        }
                                            }
                                        else
                                            {
                                              notFinalized = workingHours * 3600;
                                            }
                                  }
                               else
                                  {
                                    notFinalized = workingHours * 3600;
                                  }
                               if (notFinalized > 0 && foundDataInDb == false)
                                   {
                                       dataTurnstiles.setNotFinalized(notFinalized);
                                       String str = "";
                                           if (notFinalized > 8)
                                               {
                                                 int hour = (int) (notFinalized / 3600);
                                                 int min = (int) ((notFinalized - hour * 3600) / 60);
                                                 str = "ч: " + hour + " м: " + min; 
                                               }
                                           else
                                               {
                                                 str = "ч: "  + notFinalized; 
                                               }
                                      dataTurnstiles.setStrNotFinalized(str);
                                    }
                            dataTurnstiles.FillInTheDisplayFields(this);
                            emp.dataTurnstiles.add(dataTurnstiles);    
                     }    
                         
             }
       currentsession.close();   
           for (Iterator<Employees> it = dataEmployees.iterator(); it.hasNext();) {
               Employees employees = (Employees) it.next();
               employees.FillInTheDisplayFieldsDataTurnstilesLoading(this);
            }
       tableviewEmployees.setItems(dataEmployees);
    }
    
     @FXML
    private void SaveEntityDbAction(ActionEvent event) {
        LocalDate datLocal = firstDayMonth.getValue();
            if (datLocal == null)
               {
                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                  alert.setTitle("Предупреждение");
                  alert.setHeaderText("Необходимо указать дату.");
                  alert.setContentText("Необходимо указать дату.");
                  alert.showAndWait(); 
                  return; 
               
               }
        ObservableList<Employees> employeesList =  FXCollections.observableArrayList();
          
            for (Iterator<Employees> it = dataEmployees.iterator(); it.hasNext();) {
                Employees employees = (Employees) it.next();
                   if (employees.SelectedLoadCheckBox.isSelected() == true)
                      {
                        employeesList.add(employees);                   
                      }
            } 
            
            if (employeesList.size() == 0)
                {
                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                  alert.setTitle("Предупреждение");
                  alert.setHeaderText("Необходимо выбрать сотрудников для загрузки.");
                  alert.setContentText("Необходимо выбрать сотрудников для загрузки");
                  alert.showAndWait(); 
                  return; 
                }
            
        Date dataValue = java.sql.Date.valueOf(datLocal);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataValue);
        calendar.set(Calendar.DAY_OF_MONTH,01);
        Date firstDayMonthDate = calendar.getTime();
        
        Session currentsession =  Turnstiles.sessionfactory.openSession();
        Transaction transaction =  currentsession.beginTransaction();
       
            try {
                 Query query = currentsession.createQuery("delete from DataTurnstiles Dl where Dl.firstDayMonth=:firstDayMonth and Dl.employees in (:employeesList) ");
                 query.setParameter("firstDayMonth", firstDayMonthDate);
                 query.setParameterList("employeesList", employeesList); 
                 query.executeUpdate();
                      for (Iterator<Employees> it = employeesList.iterator(); it.hasNext();) {
                          Employees employees = (Employees) it.next();
                              for (Iterator<DataTurnstiles> it2 = employees.dataTurnstiles.iterator(); it2.hasNext();) {
                                  DataTurnstiles dataTurnstiles = (DataTurnstiles) it2.next();
                                  currentsession.save(dataTurnstiles);
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
                   System.out.println(ex);
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
            {
               Paint color = currentlabeldataTotalsTurnstilesDisplay.getTextFill();  
                   if (color.equals(Color.RED) == false) 
                       {currentlabeldataTotalsTurnstilesDisplay.setTextFill(Color.WHITE);}
            }
              
        currentlabeldataTotalsTurnstilesDisplay = label1; 
        Paint color = currentlabeldataTotalsTurnstilesDisplay.getTextFill();  
             if (color.equals(Color.RED) == false) 
                {currentlabeldataTotalsTurnstilesDisplay.setTextFill(Color.web("#0fced9"));}
        fillAdditionalTable();
             
     }
    
    public  void CurrentlabelHandleMouseClickedEmployees (Employees employees, Label label1) {
    //Вызывается из классов Subdivision и dolzhnosti для его элементов управления встроенных в vbox
        currentEmployees =  employees;
            if (currentlabelEmployees != null) 
                 {currentlabelEmployees.setTextFill(Color.WHITE);}
              
        currentlabelEmployees = label1; 
        currentlabelEmployees.setTextFill(Color.web("#0fced9"));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        TotalsTurnstilesDisplay totalsTurnstilesDisplay = dataTotalsTurnstilesDisplay.get(0);
            for (int i = 0; i < totalsTurnstilesDisplay.labelM.length; i++) {
                Label label = totalsTurnstilesDisplay.labelM[i];
                    if (currentlabeldataTotalsTurnstilesDisplay != null)
                        {
                          if (currentlabeldataTotalsTurnstilesDisplay.equals(label) == false)
                             {label.setTextFill(Color.WHITE);} 
                          else
                             {label.setTextFill(Color.web("#0fced9"));}
                        }
                    else
                        {label.setTextFill(Color.WHITE);}
               }
            for (Iterator<DataTurnstiles> it = currentEmployees.dataTurnstiles.iterator(); it.hasNext();) {
                DataTurnstiles dataTurnstiles = (DataTurnstiles) it.next();
                    if (dataTurnstiles.isThereIsNoDistributedData() == true)
                       {
                         Date tekDayEmp =  dataTurnstiles.getTekDay();
                              for (int i = 0; i < totalsTurnstilesDisplay.labelM.length; i++) {
                                  Label label = totalsTurnstilesDisplay.labelM[i];
                                  String strDate = label.getAccessibleText();
                                  Date tekDay = null;
                                      try {
                                            tekDay = format.parse(strDate);
                                           }
                                       catch (Exception ex) {
                                           }
                                       if (tekDayEmp.equals(tekDay))
                                          {  
                                            label.setTextFill(Color.RED);  
                                            break;
                                          }  
                              }
                       }    
            }    
        fillAdditionalTable();     
    }
     
    public  void fillAdditionalTable() {
       
        if (currentlabeldataTotalsTurnstilesDisplay == null || currentlabelEmployees == null) 
           {
             return;
           }
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String strDate = currentlabeldataTotalsTurnstilesDisplay.getAccessibleText();
        Date tekDay = null;
            try {
                 tekDay = format.parse(strDate);
            }
            catch (Exception ex) {
                
            }
            
        for (Iterator<DataTurnstiles> it = currentEmployees.dataTurnstiles.iterator(); it.hasNext();) {
             DataTurnstiles dayEmp = (DataTurnstiles) it.next();
             Date tekDayEmp =  dayEmp.getTekDay();
                  if (tekDayEmp.equals(tekDay))
                     {
                       ObservableList<DataTurnstiles> dataDayEmployees = FXCollections.observableArrayList(); 
                       dataDayEmployees.add(dayEmp);
                       tableviewDataTurnstiles.setItems(dataDayEmployees);
                       tableviewFileTurnstiles.setItems(dayEmp.dataFileTurnstiles);
                       tableviewAbsenceJournal.setItems(dayEmp.dataAbsenceJournal);
                       tableviewDocuments1c.setItems(dayEmp.dataDocuments1c);
                       break;   
                     }
            }      

   }
  
   public  void clearErrorFlagDay() {
    currentlabeldataTotalsTurnstilesDisplay.setTextFill(Color.web("#0fced9"));  
   } 
   
     @FXML
    private void Filter(ActionEvent event) {
        if (dataEmployees.size() == 0)
           {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Предупреждение");
            alert.setHeaderText("Нет данных для установки фильтра.");
            alert.setContentText("Необходимо выбрать дату");
            alert.showAndWait(); 
            return;                 
           }
        
        if (filter == false)
            {filter = true;}
        else
            {filter = false;}
        
        if (filter == true)
            {
              ObservableList<Employees> filteredData = FXCollections.observableArrayList();
                  for (Iterator<Employees> it = dataEmployees.iterator(); it.hasNext();) {
                       Employees employees = (Employees) it.next();
                       boolean thereIsNoDistributedData = false;
                           for (Iterator<DataTurnstiles> it2 = employees.dataTurnstiles.iterator(); it2.hasNext();) {
                               DataTurnstiles dataTurnstiles = (DataTurnstiles) it2.next();
                               thereIsNoDistributedData = dataTurnstiles.isThereIsNoDistributedData();
                                   if (thereIsNoDistributedData == true)
                                       {break;}
                           }   
                           if (thereIsNoDistributedData == true)
                               {filteredData.add(employees);}
                  }
              tableviewEmployees.setItems(filteredData);
            }
        else
            { 
              tableviewEmployees.setItems(dataEmployees);
            }
       tableviewEmployees.refresh();
        
       TotalsTurnstilesDisplay totalsTurnstilesDisplay = dataTotalsTurnstilesDisplay.get(0);
            for (int i = 0; i < totalsTurnstilesDisplay.labelM.length; i++) {
                Label label = totalsTurnstilesDisplay.labelM[i];
                label.setTextFill(Color.WHITE); 
               }
        if (currentlabelEmployees != null) 
            {currentlabelEmployees.setTextFill(Color.WHITE);} 
        //очистка всех вспомогательных переменных
        currentlabeldataTotalsTurnstilesDisplay = null;
        currentEmployees = null;
        currentlabelEmployees =  null;
        ObservableList<DataTurnstiles> dataTurnstiles = FXCollections.observableArrayList();
        ObservableList<FileTurnstiles> fileTurnstiles = FXCollections.observableArrayList();
        ObservableList<AbsenceJournal> absenceJournal = FXCollections.observableArrayList();
        ObservableList<Documents1c> documents1c = FXCollections.observableArrayList();      
        tableviewDataTurnstiles.setItems(dataTurnstiles);
        tableviewFileTurnstiles.setItems(fileTurnstiles);
        tableviewAbsenceJournal.setItems(absenceJournal);
        tableviewDocuments1c.setItems(documents1c); 
        tableviewDataTurnstiles.refresh();
        tableviewFileTurnstiles.refresh();
        tableviewAbsenceJournal.refresh();
        tableviewDocuments1c.refresh();
        
    }
   
   
}
