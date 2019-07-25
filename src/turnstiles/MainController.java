/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnstiles;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.util.Iterator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.query.JRHibernateQueryExecuterFactory;

/**
 *
 * @author Артур
 */
public class MainController implements Initializable {
    
    // Reference to the main application.
    public Turnstiles mainApp;
    
    private Subdivision currentSubdivision;
    
    private Employees currentEmployees;
    
    private Dolzhnosti currentDolzhnosti;
    
    private AbsenceJournal currentAbsenceJournal;
    
    private Documents1cOf1C currentDocuments1cOf1C;
    
    private CorrespondenceOfSurnames currentCorrespondenceOfSurnames;
    
    private Label currentlabelEmployees;
    
    private Label currentlabelSubdivision; 
    
    private Label currentlabelDolzhnosti; 
    
    private Label currentlabelAbsenceJournal; 
    
    private Label currentlabelDocuments1cOf1C; 
    
    private Label currentlabelCorrespondenceOfSurnames; 
    
    private boolean LayoutEmployees; 
    
    private boolean LayoutDolzhnosti;
    
    private boolean LayoutTurnstiles;
    
    private boolean LayoutAbsenceJournal;
    
    private boolean LayoutDocuments1c;
     
    private boolean LayoutCorrespondenceOfSurnames;
    
    private boolean LayoutReference;
   
    @FXML
    private JFXHamburger hamburgerAbsenceJournal;
    
    @FXML
    private JFXHamburger hamburgerTurnstiles;
    
    @FXML
    private JFXHamburger hamburgerDocuments1c;
    
    @FXML
    private JFXButton MenuEmployees;
     
    @FXML
    private JFXButton MenuDolzhnosti;
    
    @FXML
    private JFXButton MenuTurnstiles;
   
    @FXML
    private JFXButton MenuAbsenceJournal;
    
    @FXML
    private JFXButton MenuDocuments1c;
     
    @FXML
    private JFXButton MenuCorrespondenceOfSurnames;
    
    @FXML
    private JFXButton MenuReference;    
    
    @FXML
    private AnchorPane AnchorPaneSubdivision;
            
    @FXML
    private AnchorPane AnchorPaneEmployees;
    
    @FXML
    private AnchorPane AnchorPaneDolzhnosti;
   
    @FXML
    private AnchorPane AnchorPaneTurnstiles;
     
    @FXML
    private AnchorPane AnchorPaneAbsenceJournal;
    
    @FXML
    private AnchorPane AnchorPaneDocuments1c;
    
    @FXML
    private AnchorPane AnchorPaneCorrespondenceOfSurnames;
    
    @FXML
    private AnchorPane AnchorPaneReference;    
    
    @FXML
    private FontAwesomeIcon font_close;
    
    @FXML
    private FontAwesomeIcon font_maximize;
    
    @FXML
    private FontAwesomeIcon font_to_curtail;
    
    @FXML
    private FontAwesomeIcon font_close1;
    
    @FXML
    private FontAwesomeIcon font_maximize1;
    
    @FXML
    private FontAwesomeIcon font_to_curtail1;
    
     @FXML
    private FontAwesomeIcon font_close2;
    
    @FXML
    private FontAwesomeIcon font_maximize2;
    
    @FXML
    private FontAwesomeIcon font_to_curtail2;
    
    @FXML
    private FontAwesomeIcon font_close3;
    
    @FXML
    private FontAwesomeIcon font_maximize3;
    
    @FXML
    private FontAwesomeIcon font_to_curtail3;  
    
    
    @FXML
    private FontAwesomeIcon font_close4;
    
    @FXML
    private FontAwesomeIcon font_maximize4;
    
    @FXML
    private FontAwesomeIcon font_to_curtail4;  
    
    
    @FXML
    private FontAwesomeIcon font_close5;
    
    @FXML
    private FontAwesomeIcon font_maximize5;
    
    @FXML
    private FontAwesomeIcon font_to_curtail5;     
    
     @FXML
    private FontAwesomeIcon font_close6;
    
    @FXML
    private FontAwesomeIcon font_maximize6;
    
    @FXML
    private FontAwesomeIcon font_to_curtail6;     
   
    @FXML
    private ImageView addSubdivision;
     
    @FXML
    private ImageView editSubdivision;
    
    @FXML
    private ImageView addDolzhnosti;
     
    @FXML
    private ImageView editDolzhnosti;
    
    @FXML
    private ImageView addEmployees;
     
    @FXML
    private ImageView editEmployees;
        
    @FXML
    private ImageView addAbsenceJournal;
     
    @FXML
    private ImageView editAbsenceJournal;
    
    @FXML
    private ImageView addCorrespondenceOfSurnames;
     
    @FXML
    private ImageView editCorrespondenceOfSurnames;
    
    @FXML
    private ImageView reportView;
    
    @FXML
    private TableView tableviewSubdivision;
    
    @FXML
    private TableView tableviewDolzhnosti;
    
    @FXML
    private TableView tableviewEmployees;
    
    @FXML
    private TableView  tableviewAbsenceJournal;
    
    @FXML
    private TableView  tableviewDocuments1cOf1C;
    
    @FXML
    private TableView  tableviewCorrespondenceOfSurnames;
    
    @FXML
    public Pane paneAbsenceJournal;
    
    @FXML 
    private Spinner pageNumbers;
     
    private JasperPrint jasperPrint;
    
    ObservableList<Subdivision> dataSubdivision;
    ObservableList<Dolzhnosti> dataDolzhnosti;
    ObservableList<Employees> dataEmployees;
    ObservableList<AbsenceJournal> dataAbsenceJournal;
    ObservableList<Documents1cOf1C> dataDocuments1cOf1C;
    ObservableList<CorrespondenceOfSurnames> dataCorrespondenceOfSurnames;
    
    
    @FXML
    private void FontHandleAction(MouseEvent event) {
            if(event.getSource() == font_close || event.getSource() == font_close1 || event.getSource() == font_close2 || event.getSource() == font_close3 || event.getSource() == font_close4 || event.getSource() == font_close5 || event.getSource() == font_close6)               
               System.exit(0);
            else if(event.getSource() == font_maximize || event.getSource() == font_maximize1 || event.getSource() == font_maximize2 || event.getSource() == font_maximize3 || event.getSource() == font_maximize4 || event.getSource() == font_maximize5 || event.getSource() == font_maximize6)        
               mainApp.stageSetMaximized();
            else if(event.getSource() == font_to_curtail || event.getSource() == font_to_curtail1 || event.getSource() == font_to_curtail2 || event.getSource() == font_to_curtail3 || event.getSource() == font_to_curtail4 || event.getSource() == font_to_curtail5 || event.getSource() == font_to_curtail6)        
               mainApp.stageSetIconified();
    }
    
    
     public  void CurrentlabelHandleMouseClickedEmployees (Employees employees, Label label1) {
    //Вызывается из классов Subdivision и dolzhnosti для его элементов управления встроенных в vbox
        currentEmployees =  employees;
              if (currentlabelEmployees != null) 
                 {currentlabelEmployees.setTextFill(Color.WHITE);}
              
        currentlabelEmployees = label1; 
        currentlabelEmployees.setTextFill(Color.web("#0fced9"));
    }
    
    public  void CurrentlabelHandleMouseClicked (Subdivision subdivision, Dolzhnosti dolzhnosti, Label label) {
    //Вызывается из классов Subdivision и dolzhnosti для его элементов управления встроенных в vbox
       if (subdivision != null) {
             currentSubdivision =  subdivision;
                if (currentlabelSubdivision != null) 
                      {currentlabelSubdivision.setTextFill(Color.WHITE);}
             currentlabelSubdivision = label; 
             currentlabelSubdivision.setTextFill(Color.web("#0fced9"));
             FilltableviewEmployees(currentSubdivision);
            }
       else {
              currentDolzhnosti =  dolzhnosti;
                   if (currentlabelDolzhnosti != null) 
                       {currentlabelDolzhnosti.setTextFill(Color.WHITE);}
              currentlabelDolzhnosti = label; 
              currentlabelDolzhnosti.setTextFill(Color.web("#0fced9"));
            }
    }
    
    public  void CurrentlabelAbsenceJournalHandleMouseClicked (AbsenceJournal absenceJournal, Label label) {
    //Вызывается из классов Subdivision и dolzhnosti для его элементов управления встроенных в vbox
        currentAbsenceJournal =  absenceJournal;
            if (currentlabelAbsenceJournal != null) 
                {currentlabelAbsenceJournal.setTextFill(Color.WHITE);}
        currentlabelAbsenceJournal = label; 
        currentlabelAbsenceJournal.setTextFill(Color.web("#0fced9"));
    }
    
    public  void CurrentlabelHandleMouseClickedDocuments1c (Documents1cOf1C documents1c, Label label1) {
    //Вызывается из классов Subdivision и dolzhnosti для его элементов управления встроенных в vbox
    currentDocuments1cOf1C =  documents1c;
           if (currentlabelDocuments1cOf1C != null) 
              {currentlabelDocuments1cOf1C.setTextFill(Color.WHITE);}
              
    currentlabelDocuments1cOf1C = label1; 
    currentlabelDocuments1cOf1C.setTextFill(Color.web("#0fced9"));
    }    
    
    public  void CurrentlabelHandleMouseClickedCorrespondenceOfSurnames (CorrespondenceOfSurnames correspondenceOfSurnames, Label label1) {
    //Вызывается из классов Subdivision и dolzhnosti для его элементов управления встроенных в vbox
    currentCorrespondenceOfSurnames =  correspondenceOfSurnames;
           if (currentlabelCorrespondenceOfSurnames != null) 
              {currentlabelCorrespondenceOfSurnames.setTextFill(Color.WHITE);}
              
    currentlabelCorrespondenceOfSurnames = label1; 
    currentlabelCorrespondenceOfSurnames.setTextFill(Color.web("#0fced9"));
    }
    
    
    public void setMainApp(Turnstiles mainApp) {
        this.mainApp = mainApp;
    }
      
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           LayoutEmployees = true;
           HamburgerSlideCloseTransition transition = new HamburgerSlideCloseTransition(hamburgerAbsenceJournal);
           transition.setRate(-1);
           hamburgerAbsenceJournal.addEventHandler(MouseEvent.MOUSE_PRESSED,(e)->{
                     transition.setRate(transition.getRate()*-1);
                     transition.play();
            this.mainApp.ChoiceSubdivisionEmployees("AbsenceJournal");       
           });
           
           HamburgerSlideCloseTransition transition2 = new HamburgerSlideCloseTransition(hamburgerTurnstiles);
           transition2.setRate(-1);
           hamburgerTurnstiles.addEventHandler(MouseEvent.MOUSE_PRESSED,(e)->{
                     transition2.setRate(transition2.getRate()*-1);
                     transition2.play();
            this.mainApp.ChoiceSubdivisionEmployees("Turnstiles");        
                     
           });
     
           HamburgerSlideCloseTransition transition3 = new HamburgerSlideCloseTransition(hamburgerDocuments1c);
           transition3.setRate(-1);
           hamburgerDocuments1c.addEventHandler(MouseEvent.MOUSE_PRESSED,(e)->{
                     transition3.setRate(transition3.getRate()*-1);
                     transition3.play();
            this.mainApp.ChoiceSubdivisionEmployees("Documents1c");                      
           });
           
           
           DisplayTableview(); // настройка Tableview
           DataOutputDb(); //вывод данных из бд
           
           //вывод турникетов
           //вывод журнала отсутствия
           DefineСurrentLayoutРanel();
           
           pageNumbers.valueProperty().addListener((obs, oldValue, newValue) -> {
               if (!"".equals(newValue)) {
                   int numberP =  (int) newValue - 1;
                   viewPageReport(numberP);
                   } 
            });
    }    
  
    
    private void DisplayTableview() {
    
    TableColumn vboxDolzhnosti = new TableColumn("vbox");
      
    tableviewDolzhnosti.getColumns().addAll(vboxDolzhnosti);
    vboxDolzhnosti.setCellValueFactory(
             new PropertyValueFactory<Dolzhnosti,String>("vbox")
             );
    
    tableviewDolzhnosti.setRowFactory(tv -> {
            TableRow<Dolzhnosti> row = new TableRow<>();
            row.setOnMouseEntered(event -> {
               this.mainApp.ToolTipDolzhnostiEmployeesHandleOnMouseExited();
            });
            return row ;
        });
    
    
    TableColumn vboxSubdivision = new TableColumn("vbox");
    tableviewSubdivision.getColumns().addAll(vboxSubdivision);
    vboxSubdivision.setCellValueFactory(
              new PropertyValueFactory<Subdivision,String>("vbox")
             );
      
    TableColumn vboxEmployees = new TableColumn("vbox");      
    tableviewEmployees.getColumns().addAll(vboxEmployees);
    vboxEmployees.setCellValueFactory(
              new PropertyValueFactory<Subdivision,String>("vbox")
             );
    
    TableColumn vboxAbsenceJournal = new TableColumn("vbox");      
    tableviewAbsenceJournal.getColumns().addAll(vboxAbsenceJournal);
    vboxAbsenceJournal.setCellValueFactory(
              new PropertyValueFactory<AbsenceJournal,String>("vbox")
             );
    
    TableColumn vboxDocuments1c = new TableColumn("vbox");      
    tableviewDocuments1cOf1C.getColumns().addAll(vboxDocuments1c);
    vboxDocuments1c.setCellValueFactory(
              new PropertyValueFactory<Documents1c,String>("vbox")
             );
    
    TableColumn vboxCorrespondenceOfSurnames = new TableColumn("vbox");      
    tableviewCorrespondenceOfSurnames.getColumns().addAll(vboxCorrespondenceOfSurnames);
    vboxCorrespondenceOfSurnames.setCellValueFactory(
              new PropertyValueFactory<CorrespondenceOfSurnames,String>("vbox")
             );
    
    }
    
    private void FilltableviewDolzhnosti(ControllerDolzhnosti.Result result) {
         dataDolzhnosti.clear();
         Session currentsession =  Turnstiles.sessionfactory.openSession();
         String sql = "From Dolzhnosti Sub ORDER BY Sub.nameDolzhnosti";
         dataDolzhnosti = FXCollections.observableArrayList(currentsession.createQuery(sql).list());
         // ListChangeListener для dataSubdivision не работает так как вызывается при добавлении или изменении элементов
         // уже в сформированном списке 
            for (Iterator<Dolzhnosti> it = dataDolzhnosti.iterator(); it.hasNext();) {
                 Dolzhnosti dolzhnosti = (Dolzhnosti) it.next();
                 dolzhnosti.FillInTheDisplayFields(this);
                } 
        
         tableviewDolzhnosti.setItems(dataDolzhnosti);
            if (result !=  null)
               {
                 if (result.currentDolzhnosti != null)
                    {
                      if (result.currentDolzhnosti.employees.size() > 0)
                         {
                           //получаем список подразделений и заново обновляем
                           List<Subdivision> otborSubdivision = new ArrayList<Subdivision>();
                               for (Iterator<Employees> it = result.currentDolzhnosti.employees.iterator(); it.hasNext();) {
                                    Employees employees = (Employees) it.next();
                                    Subdivision subdivision = employees.getLinkSubdivision();
                                        if (otborSubdivision.contains(subdivision) == false)
                                           {
                                            otborSubdivision.add(subdivision);
                                           } 
                                   }   
                          // ObservableList<Subdivision> dataSubdivision1 = FXCollections.observableArrayList(currentsession.createQuery("From Subdivision Sub WHERE Sub.idSubdivision IN (?1)").setParameter(1, otborSubdivision).list());
                                for (Iterator<Subdivision> it = otborSubdivision.iterator(); it.hasNext();) {
                                    Subdivision subdivision1 = (Subdivision) it.next();
                                    String name1 = subdivision1.getNameSubdivision();
                                         for (Iterator<Subdivision> it2 = dataSubdivision.iterator(); it2.hasNext();) {
                                              Subdivision subdivision2 = (Subdivision) it2.next();
                                              String name2 = subdivision2.getNameSubdivision();
                                                   if (name1.equals(name2))
                                                   {
                                                     subdivision2.setEmployees(subdivision1.getEmployees());
                                                         if (currentSubdivision != null)
                                                            {
                                                               String name3 = currentSubdivision.getNameSubdivision();
                                                                    if (name1.equals(name2))
                                                                        {
                                                                          currentSubdivision =  subdivision2;  
                                                                          FilltableviewEmployees(currentSubdivision);
                                                                        }
                                                            }
                                                      break;   
                                                   }
                                            }   
                                }   
                                
                         } 
                    }
               
               }
         currentDolzhnosti = null;
         currentsession.close();         
    }
    
     public void changeDataEmployees(ControllerEmployees.Result result) {
        changeDataEmployeesSubdivision(result.currentSubdivision);
             if (result.currentSubdivisionOld != null)
                {
                  changeDataEmployeesSubdivision(result.currentSubdivisionOld);
                }
        changeDataEmployeesDolzhnosti(result.currentDolzhnosti);
              if (result.currentDolzhnostiOld != null)
                {
                  changeDataEmployeesDolzhnosti(result.currentDolzhnostiOld);
                }
        changeDataEmployeesDocuments1cOf1C(result.currentEmployees);      
        changeDataEmployeesCorrespondenceOfSurnames(result.currentEmployees);      
        tableviewDolzhnosti.refresh();
        tableviewSubdivision.refresh();
        tableviewDocuments1cOf1C.refresh();
        tableviewCorrespondenceOfSurnames.refresh();
            if (result.currentEmployees != null && currentEmployees != null)
                {
                    String nameEmployees = result.currentEmployees.getFio();
                    String nameEmployees2 = currentEmployees.getFio();
                           if (nameEmployees.equals(nameEmployees2))
                               {
                                 currentEmployees = result.currentEmployees;
                                      if (result.currentSubdivision != result.currentSubdivisionOld && currentSubdivision != null)
                                         {
                                           if (currentSubdivision.getNameSubdivision().equals(currentEmployees.getLinkSubdivision().getNameSubdivision()) == false)
                                              {currentEmployees = null;}
                                         }
                                 
                               }
                 }
              
     }
    
     private void changeDataEmployeesDolzhnosti(Dolzhnosti resultDolzhnosti) {
        String nameDolzhnosti = resultDolzhnosti.getNameDolzhnosti();        
                for (Iterator<Dolzhnosti> it = dataDolzhnosti.iterator(); it.hasNext();) {
                      Dolzhnosti dolzhnosti = (Dolzhnosti) it.next();
                      String nameDolzhnosti2 = dolzhnosti.getNameDolzhnosti();
                             if (nameDolzhnosti.equals(nameDolzhnosti2))
                                 {
                                   dolzhnosti.setEmployees(resultDolzhnosti.getEmployees());
                                   dolzhnosti.FillInTheDisplayFields(this);
                                        if (currentDolzhnosti != null)
                                           {
                                              String nameDolzhnosti3 = currentDolzhnosti.getNameDolzhnosti();
                                                   if (nameDolzhnosti.equals(nameDolzhnosti3))
                                                      {
                                                        currentDolzhnosti = resultDolzhnosti;  
                                                      }
                                            }
                                   break;
                                 }
                    }   
     }
     
    private void changeDataEmployeesDocuments1cOf1C(Employees resultEmployees) {
        String fio = resultEmployees.getFio();        
            for (Iterator<Documents1cOf1C> it = dataDocuments1cOf1C.iterator(); it.hasNext();) {
                Documents1cOf1C documents1cOf1C = (Documents1cOf1C) it.next();
                Employees empDok = documents1cOf1C.getEmployees();
                String fio2 = empDok.getFio();      
                    if (fio.equals(fio2))
                       {
                         documents1cOf1C.setEmployees(resultEmployees);
                             for (Iterator<Documents1c> it2 = documents1cOf1C.documents1c.iterator(); it2.hasNext();) {
                                   Documents1c documents1c = (Documents1c) it2.next();
                                   documents1c.setEmployees(resultEmployees);
                                   documents1c.getPrimaryKey().setIdEmployees(resultEmployees.getPrimaryKey().getIdEmployees());
                                   documents1c.getPrimaryKey().setIdSubdivision(resultEmployees.getPrimaryKey().getIdSubdivision());
                             }   
                         if (currentDocuments1cOf1C != null && currentDocuments1cOf1C.getEmployees().getFio().equals(fio2) == true)
                             {   
                               String documentType = documents1cOf1C.getDocumentType();
                               String Number = documents1cOf1C.getNumber();
                               Date dataDok = documents1cOf1C.getDate_Time();
                               String documentTypeCur = currentDocuments1cOf1C.getDocumentType();
                               String NumberCur = currentDocuments1cOf1C.getNumber();
                               Date dataDokCur = currentDocuments1cOf1C.getDate_Time();
                                    if (documentType.equals(documentTypeCur) == true && Number.equals(NumberCur) == true  && dataDok.equals(dataDokCur) == true)
                                       {
                                         currentDocuments1cOf1C = documents1cOf1C;
                                       } 
                             }  
                       }
            }   
     }
     
    
    private void changeDataEmployeesCorrespondenceOfSurnames(Employees resultEmployees) {
        String fio = resultEmployees.getFio();        
            for (Iterator<CorrespondenceOfSurnames> it = dataCorrespondenceOfSurnames.iterator(); it.hasNext();) {
                CorrespondenceOfSurnames correspondenceOfSurnames = (CorrespondenceOfSurnames) it.next();
                Employees empDok = correspondenceOfSurnames.getEmployees();
                String fio2 = empDok.getFio();      
                    if (fio.equals(fio2))
                       {
                         correspondenceOfSurnames.setEmployees(resultEmployees);
                             if (currentCorrespondenceOfSurnames != null && currentCorrespondenceOfSurnames.getEmployees().getFio().equals(fio2) == true)
                                {   
                                  currentCorrespondenceOfSurnames = correspondenceOfSurnames;
                                 }  
                       }
            }   
     }
    
    
     private void changeDataEmployeesSubdivision(Subdivision resultSubdivision) {
         String nameSubdivision = resultSubdivision.getNameSubdivision();
                for (Iterator<Subdivision> it = dataSubdivision.iterator(); it.hasNext();) {
                    Subdivision subdivision = (Subdivision) it.next();
                    String nameSubdivision2 = subdivision.getNameSubdivision();
                           if (nameSubdivision.equals(nameSubdivision2))
                              {
                                subdivision.setEmployees(resultSubdivision.getEmployees());
                                subdivision.FillInTheDisplayFields(this);
                                     if (currentSubdivision != null)
                                        {
                                          String nameSubdivision3 = currentSubdivision.getNameSubdivision();
                                                if (nameSubdivision.equals(nameSubdivision3))
                                                   {
                                                     currentSubdivision = resultSubdivision;  
                                                     FilltableviewEmployees(currentSubdivision);
                                                    }
                                         }
                                 break;    
                               }
                      }      
     }
     
     
     private void FilltableviewEmployees(Subdivision subdivision) {
          dataEmployees = FXCollections.observableArrayList(subdivision.employees);
              for (Iterator<Employees> it = dataEmployees.iterator(); it.hasNext();) {
                    Employees employees = (Employees) it.next();
                    employees.FillInTheDisplayFields(this);
              } 
          tableviewEmployees.setItems(dataEmployees);
     }
     
     
    private void DataOutputDb() {
    
        Session currentsession =  Turnstiles.sessionfactory.openSession();
        dataDolzhnosti = FXCollections.observableArrayList(currentsession.createQuery("From Dolzhnosti Sub ORDER BY Sub.nameDolzhnosti").list());
         // ListChangeListener для dataSubdivision не работает так как вызывается при добавлении или изменении элементов
         // уже в сформированном списке 
            for (Iterator<Dolzhnosti> it = dataDolzhnosti.iterator(); it.hasNext();) {
                 Dolzhnosti dolzhnosti = (Dolzhnosti) it.next();
                 dolzhnosti.FillInTheDisplayFields(this);
                } 
        tableviewDolzhnosti.setItems(dataDolzhnosti);
         
        dataSubdivision = FXCollections.observableArrayList(currentsession.createQuery("From Subdivision Sub ORDER BY Sub.idSubdivision").list());
         // ListChangeListener для dataSubdivision не работает так как вызывается при добавлении или изменении элементов
         // уже в сформированном списке 
         boolean ferstSubdivision = false;
            for (Iterator<Subdivision> it = dataSubdivision.iterator(); it.hasNext();) {
                 Subdivision subdivision = (Subdivision) it.next();
                 subdivision.FillInTheDisplayFields(this);
                     if (ferstSubdivision == false)
                        {   
                          currentlabelSubdivision = (Label) subdivision.vbox.getChildren().get(0);
                          currentlabelSubdivision.setTextFill(Color.web("#0fced9"));
                          currentSubdivision = subdivision;
                          ferstSubdivision = true;
                          FilltableviewEmployees(subdivision);
                        }
                         
                } 
        tableviewSubdivision.setItems(dataSubdivision);
        if (Turnstiles.user.getFio() != null)
           {FilltableviewDocuments1c();}
        FilltableviewCorrespondenceOfSurnames();
        currentsession.close();
    }
    
    private void FilltableviewSubdivision(ControllerSubdivision.Result result) {
         dataSubdivision.clear();
         Session currentsession =  Turnstiles.sessionfactory.openSession();
         String sql = "From Subdivision Sub ORDER BY Sub.idSubdivision";
         dataSubdivision = FXCollections.observableArrayList(currentsession.createQuery(sql).list());
         // ListChangeListener для dataSubdivision не работает так как вызывается при добавлении или изменении элементов
         // уже в сформированном списке 
         boolean ferstSubdivision = false;
            for (Iterator<Subdivision> it = dataSubdivision.iterator(); it.hasNext();) {
                 Subdivision subdivision = (Subdivision) it.next();
                 subdivision.FillInTheDisplayFields(this);
                     if (ferstSubdivision == false)
                        {   
                          currentlabelSubdivision = (Label) subdivision.vbox.getChildren().get(0);
                          currentlabelSubdivision.setTextFill(Color.web("#0fced9"));
                          currentSubdivision = subdivision;
                          ferstSubdivision = true;
                          FilltableviewEmployees(subdivision);
                        }
                } 
          
         tableviewSubdivision.setItems(dataSubdivision);
         
         //пересчитать сотрудников в должностях в случае изменения подразделения
         
          if (result !=  null)
               {
                 if (result.currentSubdivision != null)
                    {
                      if (result.currentSubdivision.employees.size() > 0)
                         {
                           //получаем список должностей  и заново обновляем
                           List<Dolzhnosti> otborDolzhnosti = new ArrayList<Dolzhnosti>();
                               for (Iterator<Employees> it = result.currentSubdivision.employees.iterator(); it.hasNext();) {
                                    Employees employees = (Employees) it.next();
                                    Dolzhnosti dolzhnosti = employees.getDolzhnosti();
                                        if (otborDolzhnosti.contains(dolzhnosti) == false)
                                           {
                                             otborDolzhnosti.add(dolzhnosti);
                                           } 
                                   }   
                                for (Iterator<Dolzhnosti> it = otborDolzhnosti.iterator(); it.hasNext();) {
                                    Dolzhnosti dolzhnosti1 = (Dolzhnosti) it.next();
                                    String name1 = dolzhnosti1.getNameDolzhnosti();
                                         for (Iterator<Dolzhnosti> it2 = dataDolzhnosti.iterator(); it2.hasNext();) {
                                              Dolzhnosti dolzhnosti2 = (Dolzhnosti) it2.next();
                                              String name2 = dolzhnosti2.getNameDolzhnosti();
                                                   if (name1.equals(name2))
                                                      {
                                                        dolzhnosti2.setEmployees(dolzhnosti1.getEmployees());
                                                        dolzhnosti2.FillInTheDisplayFields(this);
                                                            if (currentDolzhnosti != null)
                                                               {
                                                                 String name3 = currentDolzhnosti.getNameDolzhnosti();
                                                                        if (name1.equals(name3))
                                                                           {
                                                                              currentDolzhnosti = dolzhnosti2;  
                                                                           }
                                                               }
                                                        break;    
                                                      }
                                            }   
                                    }   
                            tableviewDolzhnosti.refresh();     
                         } 
                    }
               
               }
         
         currentsession.close();
        
    }
    
    public void FilltableviewAbsenceJournal(ControllerAbsenceJournal.Result result) {
     
     currentAbsenceJournal = null;
     
     Session currentsession =  Turnstiles.sessionfactory.openSession();
    
     Date dayS = null;
     Date dayPo = null;
     boolean showEmployees = false;
     //если стоит показывать по всем сотрудникам тогда если это начальник отдела показываем по всем 
     //сотрудникам, если у начальника указан отбор показываем только сотрудников из отбора
     List <Employees> employeesSelected = null;
     List <Employees> employeesRukovoditel = null;
        if (Turnstiles.user.isRukovoditel() == true && Turnstiles.user.isRightToEditInformation() == false)
            {
              employeesRukovoditel = Turnstiles.user.getLinkSubdivision().getEmployees();
            }
        if (Turnstiles.choiceSubdivisionEmployeesControllerAbsenceJournal != null)
            {
               showEmployees = Turnstiles.choiceSubdivisionEmployeesControllerAbsenceJournal.showEmployees.isSelected();
               LocalDate daySL = Turnstiles.choiceSubdivisionEmployeesControllerAbsenceJournal.dayS.getValue();
               LocalDate dayPoL = Turnstiles.choiceSubdivisionEmployeesControllerAbsenceJournal.dayPo.getValue();
               employeesSelected = Turnstiles.choiceSubdivisionEmployeesControllerAbsenceJournal.employeesSelected;
                    if (daySL != null)
                       {
                         dayS = java.sql.Date.valueOf(daySL);
                       }
                    if (dayPoL != null)
                       {
                         dayPo = java.sql.Date.valueOf(dayPoL);
                       }          
             }
     
     String sql = "From AbsenceJournal AbsJ ";
     String where = "where"; 
     boolean whereItWas = false;
         if (dayS != null || dayPo != null)
            {
              whereItWas = true;
                   if (dayS != null && dayPo != null)
                      {
                        where = where + " AbsJ.tekDay BETWEEN :dayS AND :dayPo";
                      }
                   else
                      {
                        if (dayS != null)
                           {
                             where = where + " AbsJ.tekDay >= :dayS ";
                           }
                        if (dayPo != null)
                           {
                             where = where + " AbsJ.tekDay <= :dayPo";
                           }
                      }
            }   
         if (employeesSelected != null && employeesSelected.size() > 0)
            {
              if (whereItWas == true)
                 {
                   where = where + " and  AbsJ.employees IN (:emp)"; 
                 }
              else
                 {
                   where = where + " AbsJ.employees IN (:emp)"; 
                 }
              whereItWas = true;
            }
         else if (showEmployees == true)
            {
              whereItWas = true;
                   if (employeesRukovoditel != null && employeesRukovoditel.size() > 0)
                      {
                        if (whereItWas = true)
                           {
                             where = where + " and  AbsJ.employees IN (:emp)"; 
                           }
                        else
                           {
                             where = where + " AbsJ.employees IN (:emp)"; 
                           }
                       }
            }
             
         if (whereItWas == true)   
            {
             boolean selectCurrentUser = true;
                if (employeesSelected != null && employeesSelected.size() > 0)
                    {
                      selectCurrentUser = false;
                    }
                if (employeesRukovoditel != null && employeesRukovoditel.size() > 0)
                    {
                      selectCurrentUser = false;
                    } 
                 if (Turnstiles.user.isRightToEditInformation() == true && showEmployees == true)
                     { 
                       selectCurrentUser = false;
                     }
                 if (selectCurrentUser == true)     
                     {
                       where  = where + " and AbsJ.employees = :user";
                     } 
                  if (where.equals("where"))
                     {
                       where = ""; // не задали условия показ только всех сотрудников}
                     }
             sql = sql + where + " ORDER BY AbsJ.idAbsenceJournal";
             Query query = currentsession.createQuery(sql);
                  if (dayS != null)
                      {
                       query.setParameter("dayS", dayS);
                      }
                   if (dayPo != null)    
                      { 
                       query.setParameter("dayPo", dayPo);   
                      }  
                   if (employeesSelected != null && employeesSelected.size() > 0)
                      {
                       query.setParameterList("emp", employeesSelected);
                      }
                   else if (showEmployees == true && employeesRukovoditel != null && employeesRukovoditel.size() > 0)
                      {
                        query.setParameterList(1, employeesRukovoditel); 
                      }
                   else if (selectCurrentUser == true)
                      {
                        query.setParameter("user", Turnstiles.user);
                      }
              dataAbsenceJournal = FXCollections.observableArrayList(query.list());
            }
         else
            {
              sql = sql + " where AbsJ.employees = :user ORDER BY AbsJ.idAbsenceJournal";
              dataAbsenceJournal = FXCollections.observableArrayList(currentsession.createQuery(sql).setParameter("user", Turnstiles.user).list());
            }
         
         for (Iterator<AbsenceJournal> it = dataAbsenceJournal.iterator(); it.hasNext();) {
              AbsenceJournal absenceJournal = (AbsenceJournal) it.next();
              absenceJournal.FillInTheDisplayFields(this);
            } 
     tableviewAbsenceJournal.setItems(dataAbsenceJournal);
         if (result != null)
            {
                for (Iterator<AbsenceJournal> it = dataAbsenceJournal.iterator(); it.hasNext();) {
                    AbsenceJournal absenceJournal = (AbsenceJournal) it.next();
                    Integer idCurrentAbsenceJournal = result.currentAbsenceJournal.getIdAbsenceJournal();
                    Integer idAbsenceJournal = absenceJournal.getIdAbsenceJournal();                    
                        if (idCurrentAbsenceJournal.equals(idAbsenceJournal))
                           {
                             currentAbsenceJournal = absenceJournal;
                             VBox vbox = currentAbsenceJournal.getVbox();
                             HBox hbox = (HBox) vbox.getChildren().get(0);
                             VBox vbox2 = (VBox) hbox.getChildren().get(2);
                             Label label = (Label) vbox2.getChildren().get(0);
                             currentlabelAbsenceJournal = label; 
                             currentlabelAbsenceJournal.setTextFill(Color.web("#0fced9"));
                           }
                     }
             }
     currentsession.close();
    }
      
    public void FilltableviewDocuments1c() {
     dataDocuments1cOf1C = FXCollections.observableArrayList();
     Session currentsession =  Turnstiles.sessionfactory.openSession();
     currentDocuments1cOf1C = null;
     Date dayS = null;
     Date dayPo = null;
     boolean showEmployees = false;
     //если стоит показывать по всем сотрудникам тогда если это начальник отдела показываем по всем 
     //сотрудникам, если у начальника указан отбор показываем только сотрудников из отбора
     List <Employees> employeesSelected = null;
     List <Employees> employeesRukovoditel = null;
        if (Turnstiles.user.isRukovoditel() == true && Turnstiles.user.isRightToEditInformation() == false)
            {
              employeesRukovoditel = Turnstiles.user.getLinkSubdivision().getEmployees();
            }
        if (Turnstiles.choiceSubdivisionEmployeesControllerDocuments1c != null)
            {
               showEmployees = Turnstiles.choiceSubdivisionEmployeesControllerDocuments1c.showEmployees.isSelected();
               LocalDate daySL = Turnstiles.choiceSubdivisionEmployeesControllerDocuments1c.dayS.getValue();
               LocalDate dayPoL = Turnstiles.choiceSubdivisionEmployeesControllerDocuments1c.dayPo.getValue();
               employeesSelected = Turnstiles.choiceSubdivisionEmployeesControllerDocuments1c.employeesSelected;
                    if (daySL != null)
                       {
                         dayS = java.sql.Date.valueOf(daySL);
                       }
                    if (dayPoL != null)
                       {
                         dayPo = java.sql.Date.valueOf(dayPoL);
                       }          
             }
     
     String where = "where"; 
     boolean whereItWas = false;
         if (dayS != null || dayPo != null)
            {
              whereItWas = true;
                   if (dayS != null && dayPo != null)
                      {
                        where = where + " Doc.primaryKey.date_Time BETWEEN :dayS AND :dayPo";
                      }
                   else
                      {
                        if (dayS != null)
                           {
                             where = where + " Doc.primaryKey.date_Time >= :dayS ";
                           }
                        if (dayPo != null)
                           {
                             where = where + " Doc.primaryKey.date_Time <= :dayPo";
                           }
                      }
            }   
         if (employeesSelected != null && employeesSelected.size() > 0)
            {
              if (whereItWas == true)
                 {
                   where = where + " and  Doc.employees IN (:emp)"; 
                 }
              else
                 {
                   where = where + " Doc.employees IN (:emp)"; 
                 }
              whereItWas = true;
            }
         else if (showEmployees == true)
            {
              whereItWas = true;  
                  if (employeesRukovoditel != null && employeesRukovoditel.size() > 0)
                     {
                       if (whereItWas = true)
                          {
                            where = where + " and Doc.employees IN (:emp)"; 
                          }
                       else
                          {
                             where = where + " Doc.employees IN (:emp)"; 
                          }
                      }
            }
     String sql = "select distinct Doc.employees, Doc.primaryKey.documentType, Doc.primaryKey.number, Doc.primaryKey.date_Time, Doc.dayS, Doc.dayPo, Doc.hours, Doc.reasonAbsence, Doc.posted From Documents1c Doc ";
     String sql2 = "From Documents1c Doc ";
     boolean selectCurrentUser = true;
         if (whereItWas == true)   
            {
              if (employeesSelected != null && employeesSelected.size() > 0)
                  {
                   selectCurrentUser = false;
                  }
              if (employeesRukovoditel != null && employeesRukovoditel.size() > 0)
                  {
                   selectCurrentUser = false;
                  } 
              if (Turnstiles.user.isRightToEditInformation() == true && showEmployees == true)
                  { 
                   selectCurrentUser = false;
                  }
              if (selectCurrentUser == true)     
                  {
                    where  = where + " and Doc.employees = :user";
                  } 
              if (where.equals("where"))
                  {
                      where = ""; // не задали условия показ только всех сотрудников}
                   }
              sql = sql + where + " ORDER BY Doc.primaryKey.date_Time";
              sql2 = sql2 + where + " ORDER BY Doc.primaryKey.date_Time";
             }
         else
             {
               sql = sql + " where Doc.employees = :user ORDER BY Doc.primaryKey.date_Time";
               sql2 = sql2 + " where Doc.employees = :user ORDER BY Doc.primaryKey.date_Time";
             }
         
     Query query = currentsession.createQuery(sql);
     Query query2 = currentsession.createQuery(sql2);
           if (whereItWas == true)   
              {   
                if (dayS != null)
                   {
                    query.setParameter("dayS", dayS);
                    query2.setParameter("dayS", dayS);
                   }
                if (dayPo != null)    
                   { 
                    query.setParameter("dayPo", dayPo); 
                    query2.setParameter("dayPo", dayPo); 
                   }  
                if (employeesSelected != null && employeesSelected.size() > 0)
                   {
                     query.setParameterList("emp", employeesSelected);
                     query2.setParameterList("emp", employeesSelected);
                    }
                else if (showEmployees == true && employeesRukovoditel != null && employeesRukovoditel.size() > 0)
                    {
                     query.setParameterList(1, employeesRukovoditel); 
                     query2.setParameterList(1, employeesRukovoditel); 
                    }
                else if (selectCurrentUser == true)
                    {
                      query.setParameter("user", Turnstiles.user);
                      query2.setParameter("user", Turnstiles.user);
                    }
              }  
           else
              {
                query.setParameter("user", Turnstiles.user);
                query2.setParameter("user", Turnstiles.user);
              } 
     List data1cOf1C = query.list();
     List data1c = query2.list();
    
          for (Iterator it = data1cOf1C.iterator(); it.hasNext();) {
                Object [] documents1cOf1C = (Object[]) it.next();
                Employees emp = (Employees) documents1cOf1C[0]; 
                Documents1cOf1C docNew = new Documents1cOf1C();
                docNew.setEmployees(emp);
                String documentType = (String) documents1cOf1C[1];
                docNew.setDocumentType(documentType);
                String Number = (String) documents1cOf1C[2];
                docNew.setNumber(Number);
                Date dataDok = (Date)documents1cOf1C[3];
                docNew.setDate_Time(dataDok);
                Date dataDokS = (Date)documents1cOf1C[4];
                docNew.setDayS(dataDokS);
                Date dataDokPo = (Date)documents1cOf1C[5];
                docNew.setDayPo(dataDokPo);
                Integer Hours = (Integer) documents1cOf1C[6];
                docNew.setHours(Hours);
                String reasonAbsence =  (String) documents1cOf1C[7]; 
                docNew.setReasonAbsence(reasonAbsence);
                boolean posted  = (boolean) documents1cOf1C[8];
                docNew.setPosted(posted);
                docNew.FillInTheDisplayFieldsEmployees(this);
                    for (Iterator it2 = data1c.iterator(); it2.hasNext();) {
                         Documents1c doc1c = (Documents1c) it2.next();  
                         Employees emp1c = doc1c.getEmployees();
                         String documentType1c = doc1c.getPrimaryKey().getDocumentType();
                         String Number1c = doc1c.getPrimaryKey().getNumber();
                         Date dataDok1c = doc1c.getPrimaryKey().getDate_Time(); 
                             if (emp.equals(emp1c) == true && documentType.equals(documentType1c) == true && Number.equals(Number1c) == true  && dataDok.equals(dataDok1c) == true)
                                {
                                  doc1c.FillInTheDisplayFields();
                                  docNew.documents1c.add(doc1c);
                                }
                         
                    }
                dataDocuments1cOf1C.add(docNew);
               }
    tableviewDocuments1cOf1C.setItems(dataDocuments1cOf1C);
    currentsession.close();
}
  
    public void FilltableviewCorrespondenceOfSurnames() {
    
    Session currentsession =  Turnstiles.sessionfactory.openSession();
    currentCorrespondenceOfSurnames = null;
    String sql = "From CorrespondenceOfSurnames ";
    dataCorrespondenceOfSurnames = FXCollections.observableArrayList(currentsession.createQuery(sql).list());
    
        for (Iterator it =  dataCorrespondenceOfSurnames.iterator(); it.hasNext();) {
              CorrespondenceOfSurnames сorrespondenceOfSurnames = (CorrespondenceOfSurnames) it.next();
              сorrespondenceOfSurnames.FillInTheDisplayFieldsEmployees(this);
        }
    tableviewCorrespondenceOfSurnames.setItems(dataCorrespondenceOfSurnames);
    currentsession.close();
}

    
     @FXML
    private void MainMenu(ActionEvent event) {
   
        if (event.getSource()== MenuEmployees)
           {
              LayoutEmployees = true;
              LayoutDolzhnosti = false;
              LayoutTurnstiles = false;
              LayoutAbsenceJournal = false;
              LayoutDocuments1c = false;
              LayoutCorrespondenceOfSurnames = false;
              LayoutReference = false;
            }
        else if (event.getSource()== MenuDolzhnosti)
            {
              LayoutEmployees = false;
              LayoutDolzhnosti = true;
              LayoutTurnstiles = false;
              LayoutAbsenceJournal = false;
              LayoutDocuments1c = false;
              LayoutCorrespondenceOfSurnames = false;
              LayoutReference = false;
             }
        else if (event.getSource()== MenuTurnstiles)
             {
               LayoutEmployees = false;
               LayoutDolzhnosti = false;
               LayoutTurnstiles = true;
               LayoutAbsenceJournal = false;
               LayoutDocuments1c = false;
               LayoutCorrespondenceOfSurnames = false;
               LayoutReference = false;
              }
         else if (event.getSource()== MenuAbsenceJournal)
              {
               LayoutEmployees = false;
               LayoutDolzhnosti = false;
               LayoutTurnstiles = false;
               LayoutAbsenceJournal = true; 
               LayoutDocuments1c = false;
               LayoutCorrespondenceOfSurnames = false;
               LayoutReference = false;
              }
         else if (event.getSource()== MenuDocuments1c)
              {
               LayoutEmployees = false;
               LayoutDolzhnosti = false;
               LayoutTurnstiles = false;
               LayoutAbsenceJournal = false; 
               LayoutDocuments1c = true;
               LayoutCorrespondenceOfSurnames = false;
               LayoutReference = false;
              }
        
         else if (event.getSource()== MenuCorrespondenceOfSurnames)
              {
               LayoutEmployees = false;
               LayoutDolzhnosti = false;
               LayoutTurnstiles = false;
               LayoutAbsenceJournal = false; 
               LayoutDocuments1c = false;
               LayoutCorrespondenceOfSurnames = true;
               LayoutReference = false;
              }
          else if (event.getSource()== MenuReference)
              {
               LayoutEmployees = false;
               LayoutDolzhnosti = false;
               LayoutTurnstiles = false;
               LayoutAbsenceJournal = false; 
               LayoutDocuments1c = false;
               LayoutCorrespondenceOfSurnames = false;
               LayoutReference = true;
              }
         DefineСurrentLayoutРanel();
    }
    
    private void DefineСurrentLayoutРanel() {
        
        MenuEmployees.setTextFill(Color.WHITE);
        MenuDolzhnosti.setTextFill(Color.WHITE);
        MenuTurnstiles.setTextFill(Color.WHITE);
        MenuAbsenceJournal.setTextFill(Color.WHITE);
        MenuDocuments1c.setTextFill(Color.WHITE);
        MenuCorrespondenceOfSurnames.setTextFill(Color.WHITE);
        MenuReference.setTextFill(Color.WHITE);
       
            if (LayoutEmployees == true)
                {
                  AnchorPaneSubdivision.setVisible(LayoutEmployees);
                  AnchorPaneEmployees.setVisible(LayoutEmployees);
                  AnchorPaneDolzhnosti.setVisible(false);
                  AnchorPaneTurnstiles.setVisible(false);
                  AnchorPaneAbsenceJournal.setVisible(false);
                  AnchorPaneDocuments1c.setVisible(false);
                  AnchorPaneCorrespondenceOfSurnames.setVisible(false);
                  AnchorPaneReference.setVisible(false);
                  MenuEmployees.setTextFill(Color.web("#0fced9"));
                  Turnstiles.currentAnchorPane = "AnchorPaneSubdivision";
                }
            if (LayoutDolzhnosti == true)
                {
                  AnchorPaneSubdivision.setVisible(false);
                  AnchorPaneEmployees.setVisible(false);
                  AnchorPaneDolzhnosti.setVisible(LayoutDolzhnosti);
                  AnchorPaneTurnstiles.setVisible(false);
                  AnchorPaneAbsenceJournal.setVisible(false);
                  AnchorPaneDocuments1c.setVisible(false);
                  AnchorPaneCorrespondenceOfSurnames.setVisible(false);
                  AnchorPaneReference.setVisible(false);
                  MenuDolzhnosti.setTextFill(Color.web("#0fced9"));
                  Turnstiles.currentAnchorPane = "AnchorPaneDolzhnosti";
                }
            if (LayoutTurnstiles == true)
                {
                  AnchorPaneSubdivision.setVisible(false);
                  AnchorPaneEmployees.setVisible(false);
                  AnchorPaneDolzhnosti.setVisible(false);
                  AnchorPaneTurnstiles.setVisible(LayoutTurnstiles);
                  AnchorPaneAbsenceJournal.setVisible(false);
                  AnchorPaneDocuments1c.setVisible(false);
                  AnchorPaneCorrespondenceOfSurnames.setVisible(false);
                  AnchorPaneReference.setVisible(false);
                  MenuTurnstiles.setTextFill(Color.web("#0fced9"));
                  Turnstiles.currentAnchorPane = "AnchorPaneTurnstiles";
                }
            if (LayoutAbsenceJournal == true)
                {
                  AnchorPaneSubdivision.setVisible(false);
                  AnchorPaneEmployees.setVisible(false);
                  AnchorPaneDolzhnosti.setVisible(false);
                  AnchorPaneTurnstiles.setVisible(false);
                  AnchorPaneAbsenceJournal.setVisible(LayoutAbsenceJournal);
                  AnchorPaneDocuments1c.setVisible(false);
                  AnchorPaneCorrespondenceOfSurnames.setVisible(false);
                  MenuAbsenceJournal.setTextFill(Color.web("#0fced9"));
                  AnchorPaneReference.setVisible(false);
                  ControllerAbsenceJournal.Result result = null;
                  FilltableviewAbsenceJournal(result);  
                  Turnstiles.currentAnchorPane = "AnchorPaneAbsenceJournal";
                }
            if (LayoutDocuments1c == true)
                {
                  AnchorPaneSubdivision.setVisible(false);
                  AnchorPaneEmployees.setVisible(false);
                  AnchorPaneDolzhnosti.setVisible(false);
                  AnchorPaneTurnstiles.setVisible(false);
                  AnchorPaneAbsenceJournal.setVisible(false);
                  AnchorPaneDocuments1c.setVisible(LayoutDocuments1c);
                  AnchorPaneCorrespondenceOfSurnames.setVisible(false);
                  AnchorPaneReference.setVisible(false);
                  MenuDocuments1c.setTextFill(Color.web("#0fced9"));
                  Turnstiles.currentAnchorPane = "AnchorPaneDocuments1c";
                }
            if (LayoutCorrespondenceOfSurnames == true)
                {
                  AnchorPaneSubdivision.setVisible(false);
                  AnchorPaneEmployees.setVisible(false);
                  AnchorPaneDolzhnosti.setVisible(false);
                  AnchorPaneTurnstiles.setVisible(false);
                  AnchorPaneAbsenceJournal.setVisible(false);
                  AnchorPaneDocuments1c.setVisible(false);
                  AnchorPaneCorrespondenceOfSurnames.setVisible(LayoutCorrespondenceOfSurnames);
                  AnchorPaneReference.setVisible(false);
                  MenuCorrespondenceOfSurnames.setTextFill(Color.web("#0fced9"));
                  Turnstiles.currentAnchorPane = "AnchorPaneCorrespondenceOfSurnames";
                }
             if (LayoutReference == true)
                {
                  AnchorPaneSubdivision.setVisible(false);
                  AnchorPaneEmployees.setVisible(false);
                  AnchorPaneDolzhnosti.setVisible(false);
                  AnchorPaneTurnstiles.setVisible(false);
                  AnchorPaneAbsenceJournal.setVisible(false);
                  AnchorPaneDocuments1c.setVisible(false);
                  AnchorPaneCorrespondenceOfSurnames.setVisible(false);
                  AnchorPaneReference.setVisible(LayoutReference);
                  MenuReference.setTextFill(Color.web("#0fced9"));
                  Turnstiles.currentAnchorPane = "AnchorPaneReference";
                }
              if (this.mainApp != null)
              {this.mainApp.closeDialogStageChoiceSubdivisionEmployees();}      
    }
 
     @FXML
    private void AddEditSubdivision(MouseEvent event) {
        //выводим новое окно для создания подразделения. Открываем его модально.
        ControllerSubdivision.Result result = null;
        if (Turnstiles.user.isRightToEditInformation() == false)
            {
             Alert alert = new Alert(AlertType.INFORMATION);
             alert.setTitle("Предупреждение");
             alert.setHeaderText("Предупреждение");
             alert.setContentText("У вас нет прав на выполнение этой команды!");
             alert.showAndWait();
             return;
            }
        if(event.getSource() == editSubdivision) 
            {
              if (currentSubdivision == null)
                 {
                   Alert alert = new Alert(AlertType.INFORMATION);
                   alert.setTitle("Предупреждение");
                   alert.setHeaderText("Предупреждение");
                   alert.setContentText("Не обходимо выделить текущее подразделение!");
                   alert.showAndWait();
                   return;
                  }
               result = this.mainApp.AddEditSubdivision(currentSubdivision);
             }   
        else {
               Subdivision subdivision = null; 
               result = this.mainApp.AddEditSubdivision(subdivision);
             }
        if (result != null)
            {
               if (result.updateDb ==true)
                  {FilltableviewSubdivision(result);}
            }   
        
    }
    
    
     @FXML
    private void DeletedSubdivision(MouseEvent event) {
        //выводим новое окно для создания подразделения. Открываем его модально.
        boolean updateDb = false;
        ControllerSubdivision.Result result = null;
            if (Turnstiles.user.isRightToEditInformation() == false)
               {
                 Alert alert = new Alert(AlertType.INFORMATION);
                 alert.setTitle("Предупреждение");
                 alert.setHeaderText("Предупреждение");
                 alert.setContentText("У вас нет прав на выполнение этой команды!");
                 alert.showAndWait();
                 return;
                }
            if (currentSubdivision == null)
                {
                   Alert alert = new Alert(AlertType.INFORMATION);
                   alert.setTitle("Предупреждение");
                   alert.setHeaderText("Предупреждение");
                   alert.setContentText("Не обходимо выделить текущее подразделение!");
                   alert.showAndWait();
                   return;
                 }
        
        Session currentsession =  this.mainApp.sessionfactory.openSession();
        List<Employees> data = currentsession.createQuery("From Employees Emp WHERE Emp.linkSubdivision = :linkSubdivision").setParameter("linkSubdivision", currentSubdivision).list();
             if (data.size() > 0)
                 {
                   String datastr = "";
                        for (Iterator<Employees> it = data.iterator(); it.hasNext();) {
                            Employees employees = (Employees) it.next();
                            datastr = datastr + employees.getFio() + "\n";
                        } 
                   Alert alert = new Alert(AlertType.INFORMATION);
                   alert.setTitle("Предупреждение");
                   alert.setHeaderText("С таким подразделением есть сотрудники. С начало необходимо удалить их.");
                   alert.setContentText(datastr);
                   alert.showAndWait();
                   return;                 
                 } 
        currentSubdivision = currentsession.get(Subdivision.class, currentSubdivision.getIdSubdivision());
        Transaction transaction =  currentsession.beginTransaction();
             try {
                  currentsession.delete(currentSubdivision);
                  transaction.commit();
                  currentsession.close();
                  Alert alert = new Alert(AlertType.INFORMATION);
                  alert.setTitle("Сообщение");
                  alert.setHeaderText("Удаление записи");
                  alert.setContentText("Запись успешно удалена в базе данных");
                  alert.showAndWait();  
                  updateDb = true;
                }
            catch (Exception ex) {
                   transaction.rollback();
                   currentsession.close(); 
                   Alert alert = new Alert(AlertType.ERROR);
                   alert.setTitle("Ошибка");
                   alert.setHeaderText("Ошибка удаления записи");
                   alert.setContentText("Не удалось удалить запись. Попробуйте заново.");
                   alert.showAndWait();
                 } 
            if (updateDb==true)
               {FilltableviewSubdivision(result);}
    }
    
    
     @FXML
    private void AddEditDolzhnosti(MouseEvent event) {
        //выводим новое окно для создания подразделения. Открываем его модально.
        ControllerDolzhnosti.Result result = null;
        if (Turnstiles.user.isRightToEditInformation() == false)
            {
              Alert alert = new Alert(AlertType.INFORMATION);
              alert.setTitle("Предупреждение");
              alert.setHeaderText("Предупреждение");
              alert.setContentText("У вас нет прав на выполнение этой команды!");
              alert.showAndWait();
              return;
            }
        if(event.getSource() == editDolzhnosti) 
            {
              if (currentDolzhnosti == null)
                 {
                   Alert alert = new Alert(AlertType.INFORMATION);
                   alert.setTitle("Предупреждение");
                   alert.setHeaderText("Предупреждение");
                   alert.setContentText("Не обходимо выделить текущую должность!");
                   alert.showAndWait();
                   return;
                  }
               result = this.mainApp.AddEditDolzhnosti(currentDolzhnosti);
             }   
        else {
               Dolzhnosti dolzhnosti = null; 
               result = this.mainApp.AddEditDolzhnosti(dolzhnosti);
             }
       
         if (result != null)
            {
               if (result.updateDb ==true)
                  {FilltableviewDolzhnosti(result);}
            }   
    }
    
    
     @FXML
    private void DeletedDolzhnosti(MouseEvent event) {
        //выводим новое окно для создания должности. Открываем его модально.
        boolean updateDb = false;
        ControllerDolzhnosti.Result result = null;
            if (Turnstiles.user.isRightToEditInformation() == false)
               {
                 Alert alert = new Alert(AlertType.INFORMATION);
                 alert.setTitle("Предупреждение");
                 alert.setHeaderText("Предупреждение");
                 alert.setContentText("У вас нет прав на выполнение этой команды!");
                 alert.showAndWait();
                 return;
                }
            if (currentDolzhnosti == null)
                {
                   Alert alert = new Alert(AlertType.INFORMATION);
                   alert.setTitle("Предупреждение");
                   alert.setHeaderText("Предупреждение");
                   alert.setContentText("Не обходимо выделить текущую должность!");
                   alert.showAndWait();
                   return;
                 }
        
        Session currentsession =  this.mainApp.sessionfactory.openSession();
        //проверяем есть ли ссылки
        List<Employees> data = currentsession.createQuery("From Employees Emp WHERE Emp.dolzhnosti = :dolzhnosti").setParameter("dolzhnosti", currentDolzhnosti).list();
             if (data.size() > 0)
                 {
                   String datastr = "";
                        for (Iterator<Employees> it = data.iterator(); it.hasNext();) {
                            Employees employees = (Employees) it.next();
                            datastr = datastr + employees.getFio() + "\n";
                        } 
                   Alert alert = new Alert(AlertType.INFORMATION);
                   alert.setTitle("Предупреждение");
                   alert.setHeaderText("С такой должностью есть сотрудники. С начало необходимо удалить их.");
                   alert.setContentText(datastr);
                   alert.showAndWait();
                   return;                 
                 } 
        currentDolzhnosti = currentsession.get(Dolzhnosti.class, currentDolzhnosti.getNameDolzhnosti());
        Transaction transaction =  currentsession.beginTransaction();
             try {
                  currentsession.delete(currentDolzhnosti);
                  transaction.commit();
                  currentsession.close();
                  Alert alert = new Alert(AlertType.INFORMATION);
                  alert.setTitle("Сообщение");
                  alert.setHeaderText("Удаление записи");
                  alert.setContentText("Запись успешно удалена в базе данных");
                  alert.showAndWait();  
                  updateDb = true;
                }
            catch (Exception ex) {
                   transaction.rollback();
                   currentsession.close(); 
                   Alert alert = new Alert(AlertType.ERROR);
                   alert.setTitle("Ошибка");
                   alert.setHeaderText("Ошибка удаления записи");
                   alert.setContentText("Не удалось удалить запись. Попробуйте заново.");
                   alert.showAndWait();
                 } 
            if (updateDb==true)
                {FilltableviewDolzhnosti(result);}
    }
    
     @FXML
    private void AddEditEmployees(MouseEvent event) {
        ControllerEmployees.Result result = null; 
            if (currentSubdivision == null)
               {
                 Alert alert = new Alert(AlertType.INFORMATION);
                 alert.setTitle("Предупреждение");
                 alert.setHeaderText("Предупреждение");
                 alert.setContentText("Не обходимо выделить текущее подразделение!");
                 alert.showAndWait();
                 return;
                }
             if(event.getSource() == editEmployees) 
                {
                 if (currentEmployees == null)
                      {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Предупреждение");
                        alert.setHeaderText("Предупреждение");
                        alert.setContentText("Не обходимо выделить текущего сотрудника!");
                        alert.showAndWait();
                        return;
                       }
                  result = this.mainApp.AddEditEmployees(currentSubdivision, currentEmployees);
                 }   
              else 
                 {
                    Employees employees = null; 
                    result = this.mainApp.AddEditEmployees(currentSubdivision, employees);
                 }
            if (result != null)
                 {
                   if (result.updateDb ==true)
                      {
                        changeDataEmployees(result);
                      }
                 }   


    }
    
      @FXML
    private void DeletedEmployees(MouseEvent event) {
        ControllerEmployees controllerEmployees = new ControllerEmployees(); 
        ControllerEmployees.Result result = controllerEmployees.new Result(); 
        boolean updateDb = false;
            if (Turnstiles.user.isRightToEditInformation() == false)
               {
                 Alert alert = new Alert(AlertType.INFORMATION);
                 alert.setTitle("Предупреждение");
                 alert.setHeaderText("Предупреждение");
                 alert.setContentText("У вас нет прав на выполнение этой команды!");
                 alert.showAndWait();
                 return;
                }
            if (currentEmployees == null)
                {
                   Alert alert = new Alert(AlertType.INFORMATION);
                   alert.setTitle("Предупреждение");
                   alert.setHeaderText("Предупреждение");
                   alert.setContentText("Не обходимо выделить текущего сотрудника!");
                   alert.showAndWait();
                   return;
                 }
        Session currentsession =  this.mainApp.sessionfactory.openSession();
        //проверяем есть ли ссылки
        List<Documents1c> data = currentsession.createQuery("From Documents1c Doc WHERE Doc.employees = :currentEmployees").setParameter("currentEmployees", currentEmployees).list();
             if (data.size() > 0)
                 {
                   Alert alert = new Alert(AlertType.INFORMATION);
                   alert.setTitle("Предупреждение");
                   alert.setHeaderText("С таким сотрудником есть документы 1с их можно посмотреть с помощью соответствующей команды. С начало необходимо удалить их.");
                   alert.setContentText("С таким сотрудником есть документы 1с их можно посмотреть с помощью соответствующей команды. С начало необходимо удалить их.");
                   alert.showAndWait();
                   return;                 
                 } 
         List<CorrespondenceOfSurnames> data2 = currentsession.createQuery("From CorrespondenceOfSurnames Cor WHERE Cor.employees = :currentEmployees").setParameter("currentEmployees", currentEmployees).list();
             if (data2.size() > 0)
                 {
                   Alert alert = new Alert(AlertType.INFORMATION);
                   alert.setTitle("Предупреждение");
                   alert.setHeaderText("С таким сотрудником есть записи соответствие фамилий их можно посмотреть с помощью соответствующей команды. С начало необходимо удалить их.");
                   alert.setContentText("С таким сотрудником есть записи соответствие фамилий их можно посмотреть с помощью соответствующей команды. С начало необходимо удалить их.");
                   alert.showAndWait();
                   return;                 
                 }     
        currentEmployees = currentsession.get(Employees.class, currentEmployees.getPrimaryKey());
        result.currentDolzhnosti = currentEmployees.getDolzhnosti();
        result.currentSubdivision = currentEmployees.getLinkSubdivision();
        Transaction transaction =  currentsession.beginTransaction();
             try {
                   currentsession.delete(currentEmployees);
                   transaction.commit(); 
                   currentsession.close();
                   currentsession =  this.mainApp.sessionfactory.openSession();
                   // c учетом удаления текущего сотрудника обновляем текущюю должность и текущее подразделение. Сессию необходимо перезакрыть
                   // иначе сохраняет удаленного сотрудника в подразделении и должности
                   result.currentDolzhnosti = currentsession.get(Dolzhnosti.class, result.currentDolzhnosti.getNameDolzhnosti());
                   result.currentSubdivision = currentsession.get(Subdivision.class, result.currentSubdivision.getIdSubdivision());
                   currentsession.close();
                   Alert alert = new Alert(AlertType.INFORMATION);
                   alert.setTitle("Сообщение");
                   alert.setHeaderText("Удаление записи");
                   alert.setContentText("Запись успешно удалена в базе данных");
                   alert.showAndWait();  
                   updateDb = true;
                  }
              catch (Exception ex) {
                   System.out.println(ex);
                   transaction.rollback();
                   currentsession.close(); 
                   Alert alert = new Alert(AlertType.ERROR);
                   alert.setTitle("Ошибка");
                   alert.setHeaderText("Ошибка удаления записи");
                   alert.setContentText("Не удалось удалить запись. Попробуйте заново.");
                   alert.showAndWait();
                  } 
              if (updateDb==true)
                {changeDataEmployees(result);}
    }
   
    
    @FXML
    private void AddEditAbsenceJournal(MouseEvent event) {
        ControllerAbsenceJournal.Result result = null; 
        if(event.getSource() == editAbsenceJournal) 
            {
              if (currentAbsenceJournal == null)
                 {
                   Alert alert = new Alert(AlertType.INFORMATION);
                   alert.setTitle("Предупреждение");
                   alert.setHeaderText("Предупреждение");
                   alert.setContentText("Не обходимо выделить текущую запись!");
                   alert.showAndWait();
                   return;
                  }
              if (Turnstiles.user.isRightToEditInformation() == false)
                  {
                   String tekDay = currentAbsenceJournal.getTekDay().toString().substring(0, 10);
                   String currentDay = java.sql.Date.valueOf(LocalDate.now()).toString().substring(0, 10);
                        if (tekDay.equals(currentDay) == false)
                           {
                             Alert alert = new Alert(AlertType.INFORMATION);
                             alert.setTitle("Предупреждение");
                             alert.setHeaderText("Предупреждение");
                             alert.setContentText("Редактировать можно только данные за сегодняшний день!");
                             alert.showAndWait();
                             return;
                           }
                        
                  }
               result = this.mainApp.AddEditAbsenceJournal(currentAbsenceJournal, true);
            }   
        else 
            {
               AbsenceJournal absenceJournal = new AbsenceJournal();
               absenceJournal.setEmployees(Turnstiles.user);
               result = this.mainApp.AddEditAbsenceJournal(absenceJournal, false);
            }
         if (result != null)
            {
              if (result.updateDb ==true)
                 {
                   FilltableviewAbsenceJournal(result);
                 }
            }   


    }
    
       @FXML
    private void DeletedAbsenceJournal(MouseEvent event) {
  
        //ControllerAbsenceJournal controllerAbsenceJournal = new ControllerAbsenceJournal(); 
        //ControllerAbsenceJournal.Result result = controllerAbsenceJournal.new Result(); 
        ControllerAbsenceJournal.Result result = null; 
        boolean updateDb = false;
            if (currentAbsenceJournal == null)
                 {
                   Alert alert = new Alert(AlertType.INFORMATION);
                   alert.setTitle("Предупреждение");
                   alert.setHeaderText("Предупреждение");
                   alert.setContentText("Не обходимо выделить текущую запись!");
                   alert.showAndWait();
                   return;
                  }
              if (Turnstiles.user.isRightToEditInformation() == false)
                  {
                   String tekDay = currentAbsenceJournal.getTekDay().toString().substring(0, 10);
                   String currentDay = java.sql.Date.valueOf(LocalDate.now()).toString().substring(0, 10);
                        if (tekDay.equals(currentDay) == false)
                           {
                             Alert alert = new Alert(AlertType.INFORMATION);
                             alert.setTitle("Предупреждение");
                             alert.setHeaderText("Предупреждение");
                             alert.setContentText("Удалять можно только данные за сегодняшний день!");
                             alert.showAndWait();
                             return;
                           }
                        
                  }
        
        Session currentsession =  this.mainApp.sessionfactory.openSession();
        currentAbsenceJournal = currentsession.get(AbsenceJournal.class, currentAbsenceJournal.getIdAbsenceJournal());
        Transaction transaction =  currentsession.beginTransaction();
             try {
                   currentsession.delete(currentAbsenceJournal);
                   transaction.commit(); 
                   currentsession.close();
                   Alert alert = new Alert(AlertType.INFORMATION);
                   alert.setTitle("Сообщение");
                   alert.setHeaderText("Удаление записи");
                   alert.setContentText("Запись успешно удалена в базе данных");
                   alert.showAndWait();  
                   updateDb = true;
                  }
              catch (Exception ex) {
                   transaction.rollback();
                   currentsession.close(); 
                   Alert alert = new Alert(AlertType.ERROR);
                   alert.setTitle("Ошибка");
                   alert.setHeaderText("Ошибка удаления записи");
                   alert.setContentText("Не удалось удалить запись. Попробуйте заново.");
                   alert.showAndWait();
                  } 
              if (updateDb==true)
                {
                  FilltableviewAbsenceJournal(result);
                }
    }
    
   @FXML
    private void AddDocuments1c(MouseEvent event) {
     
        if (Turnstiles.user.isRightToEditInformation() == false)
           {
             Alert alert = new Alert(AlertType.INFORMATION);
             alert.setTitle("Предупреждение");
             alert.setHeaderText("Предупреждение");
             alert.setContentText("У вас нет прав загружать данные из 1с!");
             alert.showAndWait();
             return;
           }
        Documents1cOf1C documents1cOf1C = null; 
        boolean updateDb = this.mainApp.AddEditDocuments1c(false, documents1cOf1C);
             
        if (updateDb == true)
           {
             FilltableviewDocuments1c();
           }
    }
    
    
    @FXML
    private void DeletedDocuments1c(MouseEvent event) {
       
        if (Turnstiles.user.isRightToEditInformation() == false)
           {
             Alert alert = new Alert(AlertType.INFORMATION);
             alert.setTitle("Предупреждение");
             alert.setHeaderText("Предупреждение");
             alert.setContentText("У вас нет прав на выполнение этой команды!");
             alert.showAndWait();
             return;
           }
        if (currentDocuments1cOf1C == null)
           {
             Alert alert = new Alert(AlertType.INFORMATION);
             alert.setTitle("Предупреждение");
             alert.setHeaderText("Предупреждение");
             alert.setContentText("Не обходимо выделить текущую запись!");
             alert.showAndWait();
             return;
            }
        boolean updateDb = false;
        Session currentsession =  this.mainApp.sessionfactory.openSession();
        Transaction transaction =  currentsession.beginTransaction();
            try {
                 for (Iterator<Documents1c> it = currentDocuments1cOf1C.documents1c.iterator(); it.hasNext();) {
                      Documents1c documents1c = (Documents1c) it.next();
                      documents1c = currentsession.get(Documents1c.class, documents1c.getPrimaryKey());
                      currentsession.delete(documents1c);
                    }
             
                 transaction.commit(); 
                 currentsession.close();
                 Alert alert = new Alert(AlertType.INFORMATION);
                 alert.setTitle("Сообщение");
                 alert.setHeaderText("Удаление записи");
                 alert.setContentText("Запись успешно удалена в базе данных");
                 alert.showAndWait();  
                 updateDb = true;
                }
            catch (Exception ex) {
                 transaction.rollback();
                 currentsession.close(); 
                 Alert alert = new Alert(AlertType.ERROR);
                 alert.setTitle("Ошибка");
                 alert.setHeaderText("Ошибка удаления записи");
                 alert.setContentText("Не удалось удалить запись. Попробуйте заново.");
                 alert.showAndWait();
                } 
             if (updateDb==true)
                {
                  FilltableviewDocuments1c();
                }

        
    }
 
    @FXML
    private void EditDocuments1c(MouseEvent event) {
        
        if (Turnstiles.user.isRightToEditInformation() == false)
           {
             Alert alert = new Alert(AlertType.INFORMATION);
             alert.setTitle("Предупреждение");
             alert.setHeaderText("Предупреждение");
             alert.setContentText("У вас нет прав на выполнение этой команды!");
             alert.showAndWait();
             return;
           }
        if (currentDocuments1cOf1C == null)
           {
             Alert alert = new Alert(AlertType.INFORMATION);
             alert.setTitle("Предупреждение");
             alert.setHeaderText("Предупреждение");
             alert.setContentText("Не обходимо выделить текущую запись!");
             alert.showAndWait();
             return;
            }
        boolean updateDb = this.mainApp.AddEditDocuments1c(true, currentDocuments1cOf1C);
             
             if (updateDb == true)
                {
                  FilltableviewDocuments1c();
                }
         
    }

    
    @FXML
    private void AddEditCorrespondenceOfSurnames(MouseEvent event) {
        boolean updateDb = false;
        if (Turnstiles.user.isRightToEditInformation() == false)
           {
             Alert alert = new Alert(AlertType.INFORMATION);
             alert.setTitle("Предупреждение");
             alert.setHeaderText("Предупреждение");
             alert.setContentText("У вас нет прав на выполнение этой команды!");
             alert.showAndWait();
             return;
           }
        if(event.getSource() == editCorrespondenceOfSurnames && currentCorrespondenceOfSurnames == null) 
            {
              Alert alert = new Alert(AlertType.INFORMATION);
              alert.setTitle("Предупреждение");
              alert.setHeaderText("Предупреждение");
              alert.setContentText("Не обходимо выделить текущую запись!");
              alert.showAndWait();
              return;
            } 
         if(event.getSource() == editCorrespondenceOfSurnames)
            {
              updateDb = this.mainApp.AddEditCorrespondenceOfSurnames(currentCorrespondenceOfSurnames);  
            }
         else
            {
               CorrespondenceOfSurnames correspondenceOfSurnames = null; 
               updateDb = this.mainApp.AddEditCorrespondenceOfSurnames(correspondenceOfSurnames);   
            }   
        if (updateDb == true)
            {
              FilltableviewCorrespondenceOfSurnames();  
            }
          
    }

     @FXML
    private void DeletedCorrespondenceOfSurnames(MouseEvent event) {
      
        if (Turnstiles.user.isRightToEditInformation() == false)
           {
             Alert alert = new Alert(AlertType.INFORMATION);
             alert.setTitle("Предупреждение");
             alert.setHeaderText("Предупреждение");
             alert.setContentText("У вас нет прав на выполнение этой команды!");
             alert.showAndWait();
             return;
           }
        if (currentCorrespondenceOfSurnames == null)
           {
             Alert alert = new Alert(AlertType.INFORMATION);
             alert.setTitle("Предупреждение");
             alert.setHeaderText("Предупреждение");
             alert.setContentText("Не обходимо выделить текущую запись!");
             alert.showAndWait();
             return;
            }
        
        boolean updateDb = false;
        Session currentsession =  this.mainApp.sessionfactory.openSession();
        Transaction transaction =  currentsession.beginTransaction();
        
            try {
                 currentCorrespondenceOfSurnames = currentsession.get(CorrespondenceOfSurnames.class, currentCorrespondenceOfSurnames.getIdCorrespondenceOfSurnames());
                 currentsession.delete(currentCorrespondenceOfSurnames);
                 transaction.commit(); 
                 currentsession.close();
                 Alert alert = new Alert(AlertType.INFORMATION);
                 alert.setTitle("Сообщение");
                 alert.setHeaderText("Удаление записи");
                 alert.setContentText("Запись успешно удалена в базе данных");
                 alert.showAndWait();  
                 updateDb = true;
                }
            catch (Exception ex) {
                 System.out.println(ex);
                 transaction.rollback();
                 currentsession.close(); 
                 Alert alert = new Alert(AlertType.ERROR);
                 alert.setTitle("Ошибка");
                 alert.setHeaderText("Ошибка удаления записи");
                 alert.setContentText("Не удалось удалить запись. Попробуйте заново.");
                 alert.showAndWait();
                }
             if (updateDb == true)
                {
                 FilltableviewCorrespondenceOfSurnames();  
                }
        
    }
  
     @FXML
    private void FileTurnstilesLoading(ActionEvent event) {
     
        if (Turnstiles.user.isRightToEditInformation() == false)
           {
             Alert alert = new Alert(AlertType.INFORMATION);
             alert.setTitle("Предупреждение");
             alert.setHeaderText("Предупреждение");
             alert.setContentText("У вас нет прав на выполнение этой команды!");
             alert.showAndWait();
             return;
           }
    
    this.mainApp.FileTurnstilesLoading(); 
    }
    
    
    @FXML
    private void TotalsTurnstilesLoading(ActionEvent event) {
     
        if (Turnstiles.user.isRightToEditInformation() == false)
           {
             Alert alert = new Alert(AlertType.INFORMATION);
             alert.setTitle("Предупреждение");
             alert.setHeaderText("Предупреждение");
             alert.setContentText("У вас нет прав на выполнение этой команды!");
             alert.showAndWait();
             return;
           }
    
    this.mainApp.TotalsTurnstilesLoading(); 
    
    }
    
    
    @FXML
    private void DataTurnstilesLoading(ActionEvent event) {
     
        if (Turnstiles.user.isRightToEditInformation() == false)
           {
             Alert alert = new Alert(AlertType.INFORMATION);
             alert.setTitle("Предупреждение");
             alert.setHeaderText("Предупреждение");
             alert.setContentText("У вас нет прав на выполнение этой команды!");
             alert.showAndWait();
             return;
           }
    
    this.mainApp.DataTurnstilesLoading(); 
    
    }
    
    private WritableImage getImageReport(int pageNumber){
	BufferedImage image = null;
	    try {
		 image = (BufferedImage) JasperPrintManager.printPageToImage(jasperPrint, pageNumber, 2);
	        } 
            catch (Exception e) {
	        }
	WritableImage fxImage = new WritableImage( jasperPrint.getPageWidth(), jasperPrint.getPageHeight());
	return SwingFXUtils.toFXImage(image, fxImage);
    }
    
    private void viewPageReport(int pageNumber){
        reportView.setImage(getImageReport(pageNumber));
    }
    
     @FXML
    private void saveReport(MouseEvent event) {
        SpinnerValueFactory val = pageNumbers.getValueFactory(); 
             if (val == null)
               {
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Нет данных для сохранения");
                 alert.setHeaderText("Нет данных для сохранения");
                 alert.setContentText("Нет данных для сохранения");
                 alert.showAndWait();
                 return;
               }
        FileChooser fileChooser = new FileChooser();
	fileChooser.setTitle("Save File");
	fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("DOCX Document",Arrays.asList("*.docx", "*.DOCX")));
        File file= fileChooser.showSaveDialog(this.mainApp.stageMain);   
             if( fileChooser.getSelectedExtensionFilter() !=null && fileChooser.getSelectedExtensionFilter().getExtensions()!=null){
		List<String> selectedExtension = fileChooser.getSelectedExtensionFilter().getExtensions();
		JRDocxExporter exporter = new JRDocxExporter();
              	exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,file.getAbsolutePath());
		    try {
			 exporter.exportReport();
			} catch (JRException e) {
		    }
             }
    }
    
     @FXML
    private void FormReport(MouseEvent event) {
     List <Employees> employeesSelected = new ArrayList<Employees>();
     Date firstDataValue = null;
     Date lastDataValue = null;
     boolean viewSubdivision = false;
          if (Turnstiles.choiceSubdivisionEmployeesControllerTurnstiles != null)
             {
               if (Turnstiles.choiceSubdivisionEmployeesControllerTurnstiles.dayS.getValue() != null)
                  {
                    firstDataValue = java.sql.Date.valueOf(Turnstiles.choiceSubdivisionEmployeesControllerTurnstiles.dayS.getValue());  
                  }
               if (Turnstiles.choiceSubdivisionEmployeesControllerTurnstiles.dayPo.getValue() != null)
                  {
                    lastDataValue = java.sql.Date.valueOf(Turnstiles.choiceSubdivisionEmployeesControllerTurnstiles.dayPo.getValue());  
                  }
               if ("Группировать по подразделениям".equals(Turnstiles.choiceSubdivisionEmployeesControllerTurnstiles.reportType.getValue())) 
                  {
                    viewSubdivision = true;
                  } 
               employeesSelected = Turnstiles.choiceSubdivisionEmployeesControllerTurnstiles.employeesSelected;
             }
          if (employeesSelected.size() == 0)
             {
               if (Turnstiles.user.isHeadOrganization() == false && Turnstiles.user.isRukovoditel() == false && Turnstiles.user.isRightToEditInformation() == false)
                  {
                    employeesSelected.add(Turnstiles.user);                      
                  }
               else if (Turnstiles.user.isRukovoditel() == true && Turnstiles.user.isRightToEditInformation() == false)
                  {
                    employeesSelected = Turnstiles.user.getLinkSubdivision().getEmployees();  
                  }
             }
          if (firstDataValue == null || lastDataValue == null)
             {
                Calendar calendar = Calendar.getInstance();
                int month = calendar.get(Calendar.MONTH);
                    if (month == 0)
                       {
                         month = 11;
                         calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
                       }
                    else
                       {
                         month = month - 1;
                       }
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH,01);
                firstDataValue = calendar.getTime();
                calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
                lastDataValue = calendar.getTime();
             }
      DateFormat dateFormat = new SimpleDateFormat("dd MMMM y");
      Map<String,Object> map = new HashMap<>();
      String strPeriod = "c " + dateFormat.format(firstDataValue) + " по " + dateFormat.format(lastDataValue);
      map.put("Period", strPeriod);
      
      try {
            JasperReport report = null;
            Query query = null;
            String[] fields = null;
            int sizeFio = employeesSelected.size();
            Session currentsession =  Turnstiles.sessionfactory.openSession();
                if (viewSubdivision == false)
                   {
                     fields = new String[] {"fio", "tekDay", "workingHours", "strWorkTime", "strTardiness", "strRecast", "strNotFinalized"};  
                     report = JasperCompileManager.compileReport("src/turnstiles/Employees.jrxml");
                     String str =  "select DT.employees.fio AS fio, CAST(DT.tekDay AS string) AS tekDay, CAST(SUM(DT.workingHours) AS string) AS workingHours, 'ч: ' + RTRIM(CAST(SUM(DT.workTime / 3600) AS string)) + ' м: ' + RTRIM(CAST(SUM((DT.workTime - (DT.workTime / 3600) *3600)/60) AS string))  as strWorkTime,  'ч: ' + RTRIM(CAST(SUM(DT.tardiness / 3600) AS string)) + ' м: ' + RTRIM(CAST(SUM((DT.tardiness - (DT.tardiness / 3600) *3600)/60) AS string))  as strTardiness, 'ч: ' + RTRIM(CAST(SUM(DT.recast / 3600) AS string)) + ' м: ' + RTRIM(CAST(SUM((DT.recast - (DT.recast / 3600) *3600)/60) AS string))  as strRecast, 'ч: ' + RTRIM(CAST(SUM(DT.notFinalized / 3600) AS string)) + ' м: ' + RTRIM(CAST(SUM((DT.notFinalized - (DT.notFinalized / 3600) *3600)/60) AS string))  as strNotFinalized From DataTurnstiles DT where DT.tekDay BETWEEN :firstDataValue AND :lastDataValue ";
                         if (sizeFio > 0)
                            {
                              str = str + " AND DT.employees in (:fioList) ";
                            }                       
                     str = str + " group by rollup (DT.employees.fio, CAST(DT.tekDay AS string)) order by DT.employees.fio, CAST(DT.tekDay AS string)";
                     query = currentsession.createQuery(str);
                   }
                else
                   {
                     fields = new String[] {"nameSubdivision", "fio", "tekDay", "workingHours", "strWorkTime", "strTardiness", "strRecast", "strNotFinalized"};  
                     report = JasperCompileManager.compileReport("src/turnstiles/Subdivision.jrxml");
                     String str = "select DT.employees.linkSubdivision.nameSubdivision AS nameSubdivision, DT.employees.fio AS fio, CAST(DT.tekDay AS string) AS tekDay, CAST(SUM(DT.workingHours) AS string) AS workingHours, 'ч: ' + RTRIM(CAST(SUM(DT.workTime / 3600) AS string)) + ' м: ' + RTRIM(CAST(SUM((DT.workTime - (DT.workTime / 3600) *3600)/60) AS string))  as strWorkTime,  'ч: ' + RTRIM(CAST(SUM(DT.tardiness / 3600) AS string)) + ' м: ' + RTRIM(CAST(SUM((DT.tardiness - (DT.tardiness / 3600) *3600)/60) AS string))  as strTardiness, 'ч: ' + RTRIM(CAST(SUM(DT.recast / 3600) AS string)) + ' м: ' + RTRIM(CAST(SUM((DT.recast - (DT.recast / 3600) *3600)/60) AS string))  as strRecast, 'ч: ' + RTRIM(CAST(SUM(DT.notFinalized / 3600) AS string)) + ' м: ' + RTRIM(CAST(SUM((DT.notFinalized - (DT.notFinalized / 3600) *3600)/60) AS string))  as strNotFinalized From DataTurnstiles DT where DT.tekDay BETWEEN :firstDataValue AND :lastDataValue ";
                          if (sizeFio > 0)
                            {
                              str = str + " AND DT.employees in (:fioList) ";
                            }                                
                     str = str + " group by rollup (DT.employees.linkSubdivision.nameSubdivision, DT.employees.fio, CAST(DT.tekDay AS string)) order by DT.employees.linkSubdivision.nameSubdivision, DT.employees.fio, CAST(DT.tekDay AS string)";
                     query = currentsession.createQuery(str);
                   }
            query.setParameter("firstDataValue", firstDataValue);
            query.setParameter("lastDataValue", lastDataValue);
                if (sizeFio > 0)
                   {
                     query.setParameterList("fioList", employeesSelected);
                   }  
            List dataListDb = query.list();
            
                for (int i = 0; i < dataListDb.size(); i++) {
                     Object [] element = (Object[]) dataListDb.get(i);
                         if (element[0] == null && element[1] == null )
                            {
                              element[0] = "Общий итог";
                              element[1] = "";  
                                  if (viewSubdivision == true)
                                     {element[2] = "";}
                            }
                         else 
                            {
                              if (element[1] == null)
                                {
                                  element[1] = "";
                                }
                              if (viewSubdivision == true)
                                { 
                                  if (element[2] == null)
                                     {
                                      element[2] = "";
                                     }
                                }    
                            }
                }
            HibernateQueryResultDataSource dataDb = new HibernateQueryResultDataSource(dataListDb, fields);  
            //JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource((Collection<?>) dataDb);
            jasperPrint = JasperFillManager.fillReport(report, map, dataDb);
            int imageHeight = jasperPrint.getPageHeight();
            int imageWidth = jasperPrint.getPageWidth();
            reportView.setFitHeight(imageHeight);
            reportView.setFitWidth(imageWidth);
            SpinnerValueFactory valuePageNumbers = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, jasperPrint.getPages().size());
            pageNumbers.setValueFactory(valuePageNumbers);
            viewPageReport(0);
            currentsession.close();
   
         }
      
      catch (Exception ex) {
           System.out.println(ex);
         }
      
      
      
    }
}
