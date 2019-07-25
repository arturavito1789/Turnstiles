/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnstiles;

import java.util.Properties;
import java.io.*;
import java.util.List;
import java.util.Enumeration;
import java.util.Iterator;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;


/**
 *
 * @author Артур
 */
public class Turnstiles extends Application {
    
    public Stage stageMain;
    private Stage dialogStageToolTipDolzhnostiEmployees;
    private Stage dialogStageChoiceSubdivisionEmployeesAbsenceJournal;
    private Stage dialogStageChoiceSubdivisionEmployeesDocuments1c;
    private Stage dialogStageChoiceSubdivisionEmployeesTurnstiles;
   
    public static ChoiceSubdivisionEmployeesController choiceSubdivisionEmployeesControllerAbsenceJournal;
    public static ChoiceSubdivisionEmployeesController choiceSubdivisionEmployeesControllerDocuments1c;
    public static ChoiceSubdivisionEmployeesController choiceSubdivisionEmployeesControllerTurnstiles;
    public MainController mainController;
    
    private ControllerToolTipDolzhnostiEmployees controllerToolTipDolzhnostiEmployees;    
    private boolean maximumWindowMode;
    public static String nameTekMaket;// static - потому что вызывается из контроллера при загрузки когда еще mainApp
    // в контроллере не установлен
    public String errorMessageConnection;
    public String usernameConnection;
    public String passwordConnection;
    public String servernameConnection;
    public String portConnection;
    public static SessionFactory sessionfactory = null;//создаем одно sessionfactory на все соединение с базой - дальше 
    //вызываем его методы. Делаем ее статической - что вызывать напрямую через имя класса : Turnstiles.sessionfactory
    public static Employees user = null; // текущий юзер
    public static String currentAnchorPane = "";
    
    
    @Override
    public void start(Stage stage) throws Exception {
        
        stageMain = stage;
        stageMain.initStyle(StageStyle.UNDECORATED);
    
    
        
        //1) проверяем настройки подключения к бд если все нормально подключаемся и отоброжаем данные
        //   иначе выводим окно настройки программы 
        checkingTheProgramSettings();
            if (errorMessageConnection  == "")
               {
                 nameTekMaket = "Main.fxml";
                 java.util.Properties properties = System.getProperties();
                 String tekName  = properties.getProperty("user.name");
                 Session currentsession = Turnstiles.sessionfactory.openSession();
                 List data = currentsession.createQuery("From Employees").list();
                      if (data.size() > 0)
                         {
                           for (Iterator<Employees> it = data.iterator(); it.hasNext();) {
                               Employees userEmp = (Employees) it.next();
                                   if (tekName.equals(userEmp.getDomainUsername()))
                                      {
                                        user = userEmp;
                                        break;
                                      }
                           } 
                           if (user == null)
                           {
                               Alert alert = new Alert(Alert.AlertType.ERROR);
                               alert.setTitle("Ошибка входа");
                               alert.setHeaderText("Ошибка входа");
                               alert.setContentText("Пользователь с доменным именем " + tekName + " не заведен в программу обратитесь в it отдел или к Юле Гавриш!");
                               alert.showAndWait();
                               return;
                           }
                         }  
                      else
                         {
                           Turnstiles.user = new Employees();
                           Turnstiles.user.setRightToEditInformation(true);
                         }
                 currentsession.close();
               }
            else
                {
                  nameTekMaket = "ProgramsSetup.fxml";
                }
        
        displayTheCurrentScene();
            
        
    }
  
   public void displayTheCurrentScene() throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nameTekMaket));
        Parent root = (Parent) loader.load();
        setMainAppInController(loader);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/CSS/StyleSheet.css");
        stageMain.setScene(scene);
        
        stageMain.getIcons().add(new Image("/IMG/if_Branding_1562683.png"));
        stageMain.show();  
    }
    
 public void checkingTheProgramSettings()  {
     FileInputStream fis;
     Properties property = new Properties();
        try {
            fis = new FileInputStream("src/turnstiles/Hibernate.properties");
                try {
                      property.load(fis);
                      fis.close();
                      String url = property.getProperty("hibernate.connection.url");
                      //если в качестве url указан test значит еще не установлены настройки подключения к бд. 
                            if ("test".equals(url))
                               {
                                  errorMessageConnection = "test";
                                  return; 
                                }
                       // не указан тест пытаемся подключиться к бд 
                       usernameConnection = property.getProperty("hibernate.connection.username");
                       passwordConnection = property.getProperty("hibernate.connection.password");
                       int Indexh = url.indexOf("/");
                       //jdbc:sqlserver://1c8:1433;databaseName=Turnstiles
                       String url2 = url.substring(Indexh+2);
                       int Indexh2 = url2.indexOf(";");
                       String url3 = url2.substring(0, Indexh2);
                       int Indexh3 = url3.indexOf(":");
                       servernameConnection = url3.substring(0, Indexh3);
                       portConnection = url3.substring(Indexh3+1);;
                       connectDB(property);
                     
                    } catch (IOException ex) {
 
                    }
            } 
        catch (FileNotFoundException ex) {
            }
    
 }
 
 
 public void setMainAppInController(FXMLLoader loader) {
      if ("Main.fxml".equals(nameTekMaket)) 
          { 
             mainController = loader.getController();
             mainController.setMainApp(this); 
           }
      else 
           {   
             ProgramsSetup controller = loader.getController();
             controller.setMainApp(this); 
           }
        
    }
 
    public static void main(String[] args) {
        launch(args);
    }
 
    public void stageSetMaximized() {
      
         if(maximumWindowMode == false)               
            maximumWindowMode = true;
         else
            maximumWindowMode = false;       
       
         if(maximumWindowMode == true)         
            stageMain.setMaximized(true);
         else
            stageMain.setMaximized(false);
      }
    
    public void stageSetIconified() {
          stageMain.setIconified(true);
    }
    
    
    public void connectDB(Properties property) {
        errorMessageConnection = "";
        FileInputStream fis;
        Properties propertyFirstStart = new Properties();
        String FirstStart = "";
            try {
                 fis = new FileInputStream("src/turnstiles/FirstStart.properties");
                       try {
                              propertyFirstStart.load(fis);
                              fis.close();
                              FirstStart = propertyFirstStart.getProperty("FirstStart");
                            } 
                        catch (IOException ex) {
 
                             }
                 }  
            catch (FileNotFoundException ex) {
                 }
         
            try {
		  Configuration configuration = new Configuration();
                        if ("false".equals(FirstStart))      
                           {
                            File fileConfigurationXml = new File("src/turnstiles/hibernate.cfg.xml");
                            configuration.configure(fileConfigurationXml);
                           }
                  // в xml файле определен мапинг, в property свойств параметры подключения к базе
                  sessionfactory = configuration.mergeProperties(property).buildSessionFactory();
		
		  Session session = sessionfactory.openSession();
                  boolean notables = false;
                  //проверяем существование таблиц если их нет создаем   
                  Query query = session.createSQLQuery("Select count(*) from Dolzhnosti");
                        try {
                             List<Integer> obj = query.list();
                            }
                        catch (Exception ex)
                            {
                               notables = true;    
                            }    
                        if (notables == true)  
                            {
                               session.beginTransaction();
                               session.createSQLQuery("CREATE TABLE Subdivision (idSubdivision int IDENTITY(1,1), nameSubdivision varchar(100) NOT NULL CONSTRAINT pk_idSubdivision PRIMARY KEY (idSubdivision), CONSTRAINT UN_nameSubdivision UNIQUE (nameSubdivision))").executeUpdate();
                               session.createSQLQuery("CREATE TABLE Dolzhnosti (nameDolzhnosti varchar(100) NOT NULL CONSTRAINT pk_nameDolzhnosti PRIMARY KEY (nameDolzhnosti))").executeUpdate();
                               session.createSQLQuery("CREATE TABLE Employees (idEmployees int NOT NULL, fio varchar(200) NOT NULL, email varchar(100) NOT NULL, countWorkingHours int NOT NULL, phone varchar(3) NOT NULL, idSubdivision int NOT NULL, foto varbinary(max) NOT NULL, notRelevant bit, headOrganization bit, domainUsername varchar(50) NOT NULL, nameDolzhnosti varchar(100) NOT NULL, rightToEditInformation bit, rukovoditel bit, CONSTRAINT fk_id_idSubdivision PRIMARY KEY (idEmployees, idSubdivision), CONSTRAINT UN_fio_email UNIQUE (fio,email), CONSTRAINT fk_nameSubdivision FOREIGN KEY (idSubdivision) REFERENCES Subdivision(idSubdivision) ON UPDATE CASCADE, CONSTRAINT fk_nameDolzhnosti FOREIGN KEY (nameDolzhnosti) REFERENCES Dolzhnosti(nameDolzhnosti) ON UPDATE CASCADE)").executeUpdate();
                               session.createSQLQuery("ALTER TABLE Subdivision ADD idEmployees int NULL, idSubdivisionEmployees int NULL, CONSTRAINT fk_rukovoditel FOREIGN KEY (idEmployees, idSubdivisionEmployees) REFERENCES Employees(idEmployees, idSubdivision)").executeUpdate();
                               session.createSQLQuery("CREATE TABLE AbsenceJournal (idAbsenceJournal int IDENTITY(1,1), idEmployees int NOT NULL, idSubdivision int NOT NULL, firstDayMonth date NOT NULL, reasonAbsence varchar(250) NOT NULL, exitTime time NOT NULL, entryTime time NOT NULL, tekDay date NOT NULL, CONSTRAINT pk_idAbsenceJournal PRIMARY KEY (idAbsenceJournal), CONSTRAINT UN_AbsenceJournal UNIQUE (idEmployees, idSubdivision,  exitTime, entryTime,  tekDay), CONSTRAINT fk_Employees FOREIGN KEY (idEmployees, idSubdivision) REFERENCES Employees(idEmployees, idSubdivision) ON UPDATE CASCADE)").executeUpdate();
                               session.createSQLQuery("CREATE TABLE Documents1c (idEmployees int NOT NULL, idSubdivision int NOT NULL, documentType varchar(50)  NOT NULL, number varchar(20)  NOT NULL, date_Time date NOT NULL, dayS date NOT NULL, dayPo date NOT NULL, tekDay date NOT NULL, hours int, hoursDay int NOT NULL, reasonAbsence varchar(500), posted bit, firstDayMonth date NOT NULL, CONSTRAINT pk_idDocuments1c PRIMARY KEY (idEmployees, idSubdivision, documentType, number, date_Time, tekDay), CONSTRAINT fk_EmployeesDocuments1c  FOREIGN KEY (idEmployees, idSubdivision) REFERENCES Employees(idEmployees, idSubdivision) ON UPDATE CASCADE)").executeUpdate();
                               session.createSQLQuery("CREATE TABLE CorrespondenceOfSurnames (idCorrespondenceOfSurnames int IDENTITY(1,1), idEmployees int NOT NULL, idSubdivision int NOT NULL, employeesReplece varchar(250) NOT NULL, CONSTRAINT pk_idCorrespondenceOfSurnames PRIMARY KEY (idCorrespondenceOfSurnames), CONSTRAINT fk_Employees1 FOREIGN KEY (idEmployees, idSubdivision) REFERENCES Employees(idEmployees, idSubdivision) ON UPDATE CASCADE)").executeUpdate();
                               session.createSQLQuery("CREATE TABLE FileTurnstiles (idFileTurnstiles int IDENTITY(1,1), idEmployees int NOT NULL, idSubdivision int NOT NULL, tekDay date NOT NULL, exitTime time NOT NULL, entryTime time NOT NULL, timeAbsenceSecond bigint NOT NULL, strTimeAbsenceSecond varchar(100) NOT NULL, firstDayMonth date NOT NULL, firstEntryLastExit bit NOT NULL CONSTRAINT pk_idFileTurnstiles PRIMARY KEY (idFileTurnstiles), CONSTRAINT fk_Employees2 FOREIGN KEY (idEmployees, idSubdivision) REFERENCES Employees(idEmployees, idSubdivision) ON UPDATE CASCADE)").executeUpdate();
                               session.createSQLQuery("CREATE TABLE TotalsTurnstiles (idTotalsTurnstiles int IDENTITY(1,1), tekDay date NOT NULL, workingHours int NOT NULL, firstDayMonth date NOT NULL CONSTRAINT pk_idTotalsTurnstiles PRIMARY KEY (idTotalsTurnstiles))").executeUpdate();
                               session.createSQLQuery("CREATE TABLE DataTurnstiles (idDataTurnstiles int IDENTITY(1,1), idEmployees int NOT NULL, idSubdivision int NOT NULL, tekDay date NOT NULL, firstDayMonth date NOT NULL, workingHours int NOT NULL, absenceNotWorkingHours int, workTimeFileAbsenceEqual bigint, workTime bigint, tardiness bigint, recast bigint, notFinalized bigint, strAbsenceNotWorkingHours varchar(100), strWorkTime varchar(100), strTardiness varchar(100), strRecast varchar(100), strNotFinalized varchar(100), strWorkTimeFileAbsenceEqual varchar(100) CONSTRAINT pk_idDataTurnstiles PRIMARY KEY (idDataTurnstiles), CONSTRAINT fk_Employees3 FOREIGN KEY (idEmployees, idSubdivision) REFERENCES Employees(idEmployees, idSubdivision) ON UPDATE CASCADE)").executeUpdate();
                               session.getTransaction().commit();
                               session.close();
                            }
                        if ("true".equals(FirstStart))
                            {
                                propertyFirstStart.setProperty("FirstStart", "false");
                                    try {
                                         PrintWriter writerf = new PrintWriter("src/turnstiles/FirstStart.properties"); 
                                              for (Enumeration e = propertyFirstStart.propertyNames(); e.hasMoreElements();) {
                                                   String key = (String) e.nextElement();
                                                   writerf.println(key + "=" + propertyFirstStart.getProperty(key));
                                                  }
                                         writerf.close();
                                        } 
                                    catch (IOException ex) {
                                        }
                                errorMessageConnection = " Подключение успешно установлено. Вам необходимо заново нажать на кнопку Применить параметры подключения ";  
                            }
               }  
			             
	    catch (HibernateException e) {
		//необходимо получить самое первое исключение
		 Throwable visibleException  = e.getCause(); 
		    if (visibleException == null)
		      	 {errorMessageConnection = "Ошибка соединения с базой данных turnstiles " +e;}
		    else {
		      	    while("1" == "1") {
		       	    	  Throwable visibleException2  = visibleException.getCause(); 
				       if (visibleException2 == null)
					   {break;}
				       else
				           {visibleException = visibleException2;}
			         }
			     errorMessageConnection = "Ошибка соединения с базой данных turnstiles " +visibleException;
                           }
			            	      
		}
    }
 
   //Функции вызываются событиями в элементах управления в классах должности, подразделения, сотрудники
    public void ToolTipDolzhnostiEmployeesHandleOnMouseEntered (MouseEvent event, String fio) {
    //Вызывается из класса Dolzhnosti для вывода всплывающей подсказки
    if(dialogStageToolTipDolzhnostiEmployees == null)
           {
            try {
                 FXMLLoader loader = new FXMLLoader();
	         loader.setLocation(getClass().getResource("ToolTipDolzhnostiEmployees.fxml"));
                 Label page = (Label) loader.load();
                 dialogStageToolTipDolzhnostiEmployees = new Stage();
	         dialogStageToolTipDolzhnostiEmployees.initModality(Modality.NONE);
                 dialogStageToolTipDolzhnostiEmployees.initStyle(StageStyle.UNDECORATED);
                 dialogStageToolTipDolzhnostiEmployees.initOwner(stageMain);
                 Scene scene = new Scene(page);
	         dialogStageToolTipDolzhnostiEmployees.setScene(scene);
                 controllerToolTipDolzhnostiEmployees = loader.getController();
                
                 }
            catch (Exception e) {
                 }
           }
        controllerToolTipDolzhnostiEmployees.setValueLabel(fio); 
        dialogStageToolTipDolzhnostiEmployees.setX(event.getScreenX());
        dialogStageToolTipDolzhnostiEmployees.setY(event.getScreenY());
        if(dialogStageToolTipDolzhnostiEmployees.isShowing() == false)
           {
            dialogStageToolTipDolzhnostiEmployees.show();
           }
    }
    
   //Функции вызываются событиями в элементах управления в классах должности, подразделения, сотрудники
    public void ToolTipDolzhnostiEmployeesHandleOnMouseExited () {
    //Вызывается из класса Dolzhnosti для вывода всплывающей подсказки
    if (dialogStageToolTipDolzhnostiEmployees != null)
        {
         if(dialogStageToolTipDolzhnostiEmployees.isShowing() == true)
            {
              dialogStageToolTipDolzhnostiEmployees.close();
            }
        }
    }
        
    
    public ControllerSubdivision.Result AddEditSubdivision(Subdivision currentSubdivision) {
     ControllerSubdivision.Result result = null;
        
        try {
	      FXMLLoader loader = new FXMLLoader();
	      loader.setLocation(getClass().getResource("Subdivision.fxml"));
              AnchorPane page = (AnchorPane) loader.load();
              Stage dialogStage = new Stage();
	      dialogStage.initModality(Modality.WINDOW_MODAL);
              dialogStage.initStyle(StageStyle.UNDECORATED);
              dialogStage.initOwner(stageMain);
	      Scene scene = new Scene(page);
	      dialogStage.setScene(scene);
              ControllerSubdivision controller = loader.getController();
	      controller.setDialogStage(dialogStage);
              controller.setMainApp(this);
              controller.setCurrentSubdivision(currentSubdivision);
              dialogStage.showAndWait();
              result = controller.returnResult();
              return result;
            } catch (Exception e) {
              return result;
            }
        
    }
   
    
    
    public ControllerDolzhnosti.Result AddEditDolzhnosti(Dolzhnosti currentDolzhnosti) {
        
    ControllerDolzhnosti.Result result = null;
        try {
	      FXMLLoader loader = new FXMLLoader();
	      loader.setLocation(getClass().getResource("Dolzhnosti.fxml"));
              AnchorPane page = (AnchorPane) loader.load();
              Stage dialogStage = new Stage();
	      dialogStage.initModality(Modality.WINDOW_MODAL);
              dialogStage.initStyle(StageStyle.UNDECORATED);
              dialogStage.initOwner(stageMain);
	      Scene scene = new Scene(page);
	      dialogStage.setScene(scene);
              ControllerDolzhnosti controller = loader.getController();
	      controller.setDialogStage(dialogStage);
              controller.setMainApp(this);
              controller.setCurrentDolzhnosti(currentDolzhnosti);
              dialogStage.showAndWait();
              result = controller.returnResult();
              return result;
            } catch (Exception e) {
              return result;
            }
        
    }
    
    
    public ControllerEmployees.Result AddEditEmployees(Subdivision currentSubdivision, Employees currentEmployees) {
     
    ControllerEmployees.Result result = null;    
        try {
	      FXMLLoader loader = new FXMLLoader();
	      loader.setLocation(getClass().getResource("Employees.fxml"));
              AnchorPane page = (AnchorPane) loader.load();
              Stage dialogStage = new Stage();
	      dialogStage.initModality(Modality.WINDOW_MODAL);
              dialogStage.initStyle(StageStyle.UNDECORATED);
              dialogStage.initOwner(stageMain);
	      Scene scene = new Scene(page);
	      dialogStage.setScene(scene);
              ControllerEmployees controller = loader.getController();
	      controller.setDialogStage(dialogStage);
              controller.setMainApp(this);
              controller.setCurrenSubdivisionEmployees(currentSubdivision, currentEmployees);
              dialogStage.showAndWait();
              result = controller.returnResult();
              return result;
            } catch (Exception e) {
              return result;
            }
        
    }
    
    public void ChoiceSubdivisionEmployees(String nameStage){
        
        if (nameStage.equals("Turnstiles"))
           {
            if(dialogStageChoiceSubdivisionEmployeesTurnstiles == null)
               {
                 try {
                      FXMLLoader loader = new FXMLLoader();
                      loader.setLocation(getClass().getResource("ChoiceSubdivisionEmployeesTurnstiles.fxml"));
                      VBox page = (VBox) loader.load();
                      dialogStageChoiceSubdivisionEmployeesTurnstiles = new Stage();  
                      choiceSubdivisionEmployeesControllerTurnstiles = loader.getController();
                      choiceSubdivisionEmployeesControllerTurnstiles.initializereportType();
                      dialogStageChoiceSubdivisionEmployeesTurnstiles.initModality(Modality.NONE);
                      dialogStageChoiceSubdivisionEmployeesTurnstiles.initStyle(StageStyle.TRANSPARENT);
                      dialogStageChoiceSubdivisionEmployeesTurnstiles.initOwner(stageMain);
                      //  Scene scene = new Scene(page, Color.TRANSPARENT);
                      Scene scene = new Scene(page);
	              dialogStageChoiceSubdivisionEmployeesTurnstiles.setScene(scene);
                     }
                  catch (Exception e) {
                     }
               }    
               Choice(nameStage, dialogStageChoiceSubdivisionEmployeesTurnstiles, choiceSubdivisionEmployeesControllerTurnstiles);
           }
        else if (nameStage.equals("Documents1c"))
           {
            if(dialogStageChoiceSubdivisionEmployeesDocuments1c == null)
               {
                 try {
                      FXMLLoader loader = new FXMLLoader();
                      loader.setLocation(getClass().getResource("ChoiceSubdivisionEmployeesAbsenceJournalDocuments1c.fxml"));
                      VBox page = (VBox) loader.load();
                      dialogStageChoiceSubdivisionEmployeesDocuments1c = new Stage();  
                      choiceSubdivisionEmployeesControllerDocuments1c = loader.getController();
                      dialogStageChoiceSubdivisionEmployeesDocuments1c.initModality(Modality.NONE);
                      dialogStageChoiceSubdivisionEmployeesDocuments1c.initStyle(StageStyle.TRANSPARENT);
                      dialogStageChoiceSubdivisionEmployeesDocuments1c.initOwner(stageMain);
                      //  Scene scene = new Scene(page, Color.TRANSPARENT);
                      Scene scene = new Scene(page);
	              dialogStageChoiceSubdivisionEmployeesDocuments1c.setScene(scene);
                     }
                  catch (Exception e) {
                     }
               }    
               
               Choice(nameStage, dialogStageChoiceSubdivisionEmployeesDocuments1c, choiceSubdivisionEmployeesControllerDocuments1c);
           }
        else
           {
            if(dialogStageChoiceSubdivisionEmployeesAbsenceJournal == null)
               {
                 try {
                      FXMLLoader loader = new FXMLLoader();
                      loader.setLocation(getClass().getResource("ChoiceSubdivisionEmployeesAbsenceJournalDocuments1c.fxml"));
                      VBox page = (VBox) loader.load();
                      dialogStageChoiceSubdivisionEmployeesAbsenceJournal = new Stage();  
                      choiceSubdivisionEmployeesControllerAbsenceJournal = loader.getController();
                      dialogStageChoiceSubdivisionEmployeesAbsenceJournal.initModality(Modality.NONE);
                      dialogStageChoiceSubdivisionEmployeesAbsenceJournal.initStyle(StageStyle.TRANSPARENT);
                      dialogStageChoiceSubdivisionEmployeesAbsenceJournal.initOwner(stageMain);
                      //  Scene scene = new Scene(page, Color.TRANSPARENT);
                      Scene scene = new Scene(page);
	              dialogStageChoiceSubdivisionEmployeesAbsenceJournal.setScene(scene);
                
                     }
                  catch (Exception e) {
                     }
                }    
               Choice(nameStage, dialogStageChoiceSubdivisionEmployeesAbsenceJournal, choiceSubdivisionEmployeesControllerAbsenceJournal);
           }
     } 


    public void Choice(String nameStage, Stage dialogStage, ChoiceSubdivisionEmployeesController Controller){
        if (dialogStage.isShowing() == true) 
        {
            dialogStage.close();
                 if(nameStage.equals("AbsenceJournal") == true && currentAnchorPane.equals("AnchorPaneAbsenceJournal") == true)
                   {
                     ControllerAbsenceJournal.Result result = null;
                     this.mainController.FilltableviewAbsenceJournal(result);
                   }
                 if(nameStage.equals("Documents1c") == true && currentAnchorPane.equals("AnchorPaneDocuments1c") == true)
                   {
                     this.mainController.FilltableviewDocuments1c();
                   }
                 
        }
        else {
              //dialogStage.setX(550);
              //dialogStage.setY(140);
              dialogStage.sizeToScene();
              dialogStage.show();
                   if(nameStage.equals("Turnstiles") == false)
                      {  
                        if (Turnstiles.user.isRightToEditInformation() == false && Turnstiles.user.isRukovoditel() == false)
                           {
                             Controller.showEmployees.setVisible(false);
                           }
                        else
                           {
                             Controller.showEmployees.setVisible(true);
                           }
                      }
             }
    }
    
    public void closeDialogStageChoiceSubdivisionEmployees(){
       if(dialogStageChoiceSubdivisionEmployeesAbsenceJournal != null)
         {
          if (dialogStageChoiceSubdivisionEmployeesAbsenceJournal.isShowing() == true) 
             {
              dialogStageChoiceSubdivisionEmployeesAbsenceJournal.close();
             }
         }
       
       if(dialogStageChoiceSubdivisionEmployeesDocuments1c != null)
         {
          if (dialogStageChoiceSubdivisionEmployeesDocuments1c.isShowing() == true) 
             {
              dialogStageChoiceSubdivisionEmployeesDocuments1c.close();
             }
         }
       
       if(dialogStageChoiceSubdivisionEmployeesTurnstiles != null)
         {
          if (dialogStageChoiceSubdivisionEmployeesTurnstiles.isShowing() == true) 
             {
              dialogStageChoiceSubdivisionEmployeesTurnstiles.close();
             }
         }
    }
    
    
    public ControllerAbsenceJournal.Result AddEditAbsenceJournal(AbsenceJournal currentAbsenceJournal, boolean edit) {
     
    ControllerAbsenceJournal.Result result = null;  
        try {
	      FXMLLoader loader = new FXMLLoader();
	      loader.setLocation(getClass().getResource("AbsenceJournal.fxml"));
              AnchorPane page = (AnchorPane) loader.load();
              Stage dialogStage = new Stage();
	      dialogStage.initModality(Modality.WINDOW_MODAL);
              dialogStage.initStyle(StageStyle.UNDECORATED);
              dialogStage.initOwner(stageMain);
	      Scene scene = new Scene(page);
	      dialogStage.setScene(scene);
              ControllerAbsenceJournal controller = loader.getController();
	      controller.setDialogStage(dialogStage);
              controller.setMainApp(this);
              controller.setCurrenAbsenceJournal(currentAbsenceJournal);
              controller.setTekDay(edit);
              dialogStage.showAndWait();
              result = controller.returnResult();
              return result;
            } catch (Exception e) {
              return result;
            }
        
    }
    
    
    public boolean AddEditDocuments1c(boolean edit, Documents1cOf1C currentDocuments1cOf1C) {
    boolean updateDb = false; 
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("Documents1cLoading.fxml"));
        try {
              AnchorPane page = (AnchorPane) loader.load();
              Stage dialogStage = new Stage();
	      dialogStage.initModality(Modality.WINDOW_MODAL);
              dialogStage.initStyle(StageStyle.UNDECORATED);
              dialogStage.initOwner(stageMain);
	      Scene scene = new Scene(page);
	      dialogStage.setScene(scene);
              ControllerAddDocuments1c controller = loader.getController();
	      controller.setDialogStage(dialogStage);
              controller.setMainApp(this);
                  if (edit==true)
                     {
                       controller.LabelPeriodS.setVisible(false);
                       controller.LabelPeriodPo.setVisible(false);
                       controller.Button1c.setVisible(false);
                       controller.dayS.setVisible(false);
                       controller.dayPo.setVisible(false);
                       controller.currentEditDocuments1cOf1C = currentDocuments1cOf1C;
                       controller.edit = edit;
                       controller.LoadCurrentEditDocuments1cOf1C();
                     }
              dialogStage.showAndWait();
              updateDb = controller.updateDb;
              return updateDb;
              
            } 
        catch (Exception e) {
              return updateDb;
            }
    }
 
    
    
    public boolean AddEditCorrespondenceOfSurnames(CorrespondenceOfSurnames currentCorrespondenceOfSurnames) {
     
    boolean updateDb = false;
        try {
	      FXMLLoader loader = new FXMLLoader();
	      loader.setLocation(getClass().getResource("CorrespondenceOfSurnames.fxml"));
              AnchorPane page = (AnchorPane) loader.load();
              Stage dialogStage = new Stage();
	      dialogStage.initModality(Modality.WINDOW_MODAL);
              dialogStage.initStyle(StageStyle.UNDECORATED);
              dialogStage.initOwner(stageMain);
	      Scene scene = new Scene(page);
	      dialogStage.setScene(scene);
              ControllerCorrespondenceOfSurnames controller = loader.getController();
	      controller.setDialogStage(dialogStage);
              //controller.setMainApp(this);
              controller.setCurrentCorrespondenceOfSurnames(currentCorrespondenceOfSurnames);
              dialogStage.showAndWait();
              updateDb = controller.updateDb;
              return updateDb;
            } catch (Exception e) {
              return false;
            }
        
    }
   
    
    
    public void FileTurnstilesLoading() {
     
         try {
	      FXMLLoader loader = new FXMLLoader();
	      loader.setLocation(getClass().getResource("FileTurnstilesLoading.fxml"));
              AnchorPane page = (AnchorPane) loader.load();
              Stage dialogStage = new Stage();
	      dialogStage.initModality(Modality.WINDOW_MODAL);
              dialogStage.initStyle(StageStyle.UNDECORATED);
              dialogStage.initOwner(stageMain);
	      Scene scene = new Scene(page);
	      dialogStage.setScene(scene);
              ControllerFileTurnstilesLoading controller = loader.getController();
	      controller.setDialogStage(dialogStage);
              controller.setMainApp(this);
              dialogStage.showAndWait();
             } catch (Exception e) {
            }
        
    }
   
    public boolean changeEmployeesFileTurnstilesLoading(AdditionalEmployees additionalEmployees) {
         boolean updateDb = false;
         try {
	      FXMLLoader loader = new FXMLLoader();
	      loader.setLocation(getClass().getResource("changeEmployeesFileTurnstilesLoading.fxml"));
              AnchorPane page = (AnchorPane) loader.load();
              Stage dialogStage = new Stage();
	      dialogStage.initModality(Modality.WINDOW_MODAL);
              dialogStage.initStyle(StageStyle.UNDECORATED);
              dialogStage.initOwner(stageMain);
	      Scene scene = new Scene(page);
	      dialogStage.setScene(scene);
              ControllerchangeEmployeesFileTurnstilesLoading controller = loader.getController();
	      controller.setDialogStage(dialogStage);
              controller.additionalEmployees = additionalEmployees;
              dialogStage.showAndWait();
              updateDb = controller.updateDb;
              return updateDb;
             } catch (Exception e) {
               return updateDb;
             }
        
    }
    
    
    public void TotalsTurnstilesLoading() {
     
         try {
	      FXMLLoader loader = new FXMLLoader();
	      loader.setLocation(getClass().getResource("TotalsTurnstilesLoading.fxml"));
              AnchorPane page = (AnchorPane) loader.load();
              Stage dialogStage = new Stage();
	      dialogStage.initModality(Modality.WINDOW_MODAL);
              dialogStage.initStyle(StageStyle.UNDECORATED);
              dialogStage.initOwner(stageMain);
	      Scene scene = new Scene(page);
	      dialogStage.setScene(scene);
              ControllerTotalsTurnstilesLoading controller = loader.getController();
	      controller.setDialogStage(dialogStage);
              controller.setMainApp(this);
              dialogStage.showAndWait();
             } catch (Exception e) {
            }
    }
   
    public UserLdap UserLdapLoading() {
    UserLdap userLdap = null; 
         try {
	      FXMLLoader loader = new FXMLLoader();
	      loader.setLocation(getClass().getResource("UserLdap.fxml"));
              AnchorPane page = (AnchorPane) loader.load();
              Stage dialogStage = new Stage();
	      dialogStage.initModality(Modality.WINDOW_MODAL);
              dialogStage.initStyle(StageStyle.UNDECORATED);
              dialogStage.initOwner(stageMain);
	      Scene scene = new Scene(page);
	      dialogStage.setScene(scene);
              ControllerUserLdap controller = loader.getController();
	      controller.setDialogStage(dialogStage);
              controller.fillTableviewUserLdap();
              dialogStage.showAndWait();
              userLdap = controller.currentUserLdap;
             } catch (Exception e) {
             }
    return  userLdap;
    }
    
    
    
    
     public void DataTurnstilesLoading() {
     
         try {
	      FXMLLoader loader = new FXMLLoader();
	      loader.setLocation(getClass().getResource("DataTurnstilesLoading.fxml"));
              AnchorPane page = (AnchorPane) loader.load();
              Stage dialogStage = new Stage();
	      dialogStage.initModality(Modality.WINDOW_MODAL);
              dialogStage.initStyle(StageStyle.UNDECORATED);
              dialogStage.initOwner(stageMain);
	      Scene scene = new Scene(page);
	      dialogStage.setScene(scene);
              ControllerDataTurnstilesLoading controller = loader.getController();
	      controller.setDialogStage(dialogStage);
              controller.setMainApp(this);
              dialogStage.showAndWait();
             } catch (Exception e) {
            }
    }
    
     
    public ControllerInput_Time.Result LoadInput_Time(int h,int m, boolean showM) {
    ControllerInput_Time.Result result = null; 
         try {
	      FXMLLoader loader = new FXMLLoader();
	      loader.setLocation(getClass().getResource("Input_Time.fxml"));
              AnchorPane page = (AnchorPane) loader.load();
              Stage dialogStage = new Stage();
	      dialogStage.initModality(Modality.WINDOW_MODAL);
              dialogStage.initStyle(StageStyle.UNDECORATED);
              dialogStage.initOwner(stageMain);
	      Scene scene = new Scene(page);
	      dialogStage.setScene(scene);
              ControllerInput_Time controller = loader.getController();
	      controller.setDialogStage(dialogStage);
              controller.setH(h);
              controller.setM(m);
              controller.VisibleM(showM);
              dialogStage.showAndWait();
              result = controller.returnResult();
              return result;
            
             } catch (Exception e) {
               return result;
            
             }
    }
      
}
