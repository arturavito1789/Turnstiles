package turnstiles;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="DataTurnstiles")
public class DataTurnstiles implements Serializable{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int idDataTurnstiles; 
    @Column(name="tekDay")
    private Date tekDay;
    @Column(name="firstDayMonth")
    private Date firstDayMonth;
    @Column(name="idEmployees")
    private Integer idEmployees;
    @Column(name="idSubdivision")
    private Integer idSubdivision;   
    @Column(name="workingHours")
    private int workingHours;
   
    @Column(name="absenceNotWorkingHours")
    private int absenceNotWorkingHours;//отсутствие невыходы, больничные, отпуска, командировки  - не учитываем в рабочем времени
    @Column(name="strAbsenceNotWorkingHours")
    private String strAbsenceNotWorkingHours;
    @Column(name="workTime")
    private long workTime;//отработанное время 
    @Column(name="strWorkTime")
    private String strWorkTime;
    @Column(name="tardiness")
    private long tardiness;//опоздание
    @Column(name="strTardiness")
    private String strTardiness;
    @Column(name="recast")
    private long recast;//переработка
    @Column(name="strRecast")
    private String strRecast;
    @Column(name="notFinalized")
    private long notFinalized;//не доработано
    @Column(name="strNotFinalized")
    private String strNotFinalized;
    @Column(name="workTimeFileAbsenceEqual")
    private long workTimeFileAbsenceEqual;//время выхода по журналу
    @Column(name="strWorkTimeFileAbsenceEqual")
    private String strWorkTimeFileAbsenceEqual;
    
    @OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumns({
                @JoinColumn(name = "idEmployees", insertable = false, updatable = false),
                @JoinColumn(name = "idSubdivision", insertable = false, updatable = false)
                })   
    private Employees employees;
   
    @Transient
    private Time firstEntry;
    @Transient
    private Time lastExit;
    @Transient 
    private boolean thereIsNoDistributedData;
    @Transient 
    public ArrayList<Integer> listIdFileTurnstiles = new ArrayList<>();
    @Transient 
    public ArrayList<Integer> listIdAbsenceJournal = new ArrayList<>();
    @Transient 
    public ObservableList<FileTurnstiles> dataFileTurnstiles;
    @Transient 
    public ObservableList<AbsenceJournal> dataAbsenceJournal;
    @Transient 
    public ObservableList<Documents1c> dataDocuments1c;
    @Transient 
    VBox vbox;   
    @Transient 
    private Turnstiles mainApp;
    @Transient 
    ControllerDataTurnstilesLoading controllerDataTurnstilesLoading;
    
    public DataTurnstiles() {
     dataFileTurnstiles = FXCollections.observableArrayList(); 
     dataAbsenceJournal = FXCollections.observableArrayList();
     dataDocuments1c = FXCollections.observableArrayList();
     workingHours = 0;
     recast = 0;//переработка
     tardiness = 0;//опоздание
     notFinalized = 0;//не доработано
     absenceNotWorkingHours = 0;//отсутствие невыходы, больничные, отпуска, командировки  - не учитываем в рабочем времени
     workTime = 0;//отработанное время 
     workTimeFileAbsenceEqual = 0;//время выхода по журналу
     strAbsenceNotWorkingHours = "ч: " + 0;
     strWorkTime = "ч: " + 0 + " м: " + 0;
     strTardiness = "ч: " + 0 + " м: " + 0;;
     strRecast = "ч: " + 0 + " м: " + 0;;
     strNotFinalized = "ч: " + 0 + " м: " + 0;;
     strWorkTimeFileAbsenceEqual = "ч: " + 0 + " м: " + 0;;
    
    }

    
    
    public void setMainApp(Turnstiles mainApp) {
        this.mainApp = mainApp;
    }
    
    public void FillInTheDisplayFields (ControllerDataTurnstilesLoading controller) {
   
    UnaryOperator<TextFormatter.Change> filter = change -> {
                   String text = change.getText();
                        if (text.matches("[0-9]*")) {
                            return change;
                        }
                   return null;
              };
    TextFormatter<String> textFormatter = new TextFormatter<>(filter);    
    this.controllerDataTurnstilesLoading = controller;    
    HBox HBox1 = new HBox();    
    Label label = new Label("Норма часов:");
    label.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    label.setTextFill(Color.WHITE);
    label.setVisible(true);
    label.setAlignment(Pos.TOP_LEFT);
    label.setPrefWidth(90); 
    TextField textField =  new TextField();
    textField.setText(String.valueOf(workingHours));
    textField.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    textField.setAlignment(Pos.TOP_LEFT);
    textField.setPrefWidth(100);
    textField.setTextFormatter(textFormatter);
    textField.textProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue.equals("") == true)
           {
             workingHours = 0;  
           }
        else
           {
             workingHours = Integer.parseInt(newValue);  
           }
    });
    HBox1.getChildren().add(label);
    Separator separator = new Separator();
    separator.setVisible(false);
    HBox1.getChildren().add(separator);    
    HBox1.getChildren().add(textField);
    Separator separator2 = new Separator();
    separator2.setVisible(false);
    HBox1.getChildren().add(separator2);    
    Label label2 = new Label("Отработано:");
    label2.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    label2.setTextFill(Color.WHITE);
    label2.setVisible(true);
    label2.setAlignment(Pos.TOP_LEFT);
    label2.setPrefWidth(90); 
    HBox1.getChildren().add(label2);    
    Separator separator3 = new Separator();
    separator3.setVisible(false);
    HBox1.getChildren().add(separator3);  
    TextField textField2 =  new TextField();
    textField2.setText(strWorkTime);
    textField2.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    textField2.setAlignment(Pos.TOP_LEFT);
    textField2.setPrefWidth(100);
    textField2.textProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue.equals(strWorkTime))
           {
             return;
             // при textField2.setText(newValue) - процедура вызывается второй раз
           }
        int h =0;
        int m = 0;
            if (workTime > 0)
               {
                 h = (int) (workTime / 3600);
                 m = (int) ((workTime - h * 3600) / 60);
               }
        ControllerInput_Time.Result result  = mainApp.LoadInput_Time(h, m, true);
             if (result != null)
               {
                 strWorkTime = "ч: " + result.h + " м: " + result.m;
                 workTime = (3600 * result.h) + (result.m * 60);
               }
        newValue = strWorkTime;
        textField2.setText(newValue);
    });
    HBox1.getChildren().add(textField2);  
    Separator separator4 = new Separator();
    separator4.setVisible(false);
    HBox1.getChildren().add(separator4);  
    Label label3 = new Label("Опоздание:");
    label3.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    label3.setTextFill(Color.WHITE);
    label3.setVisible(true);
    label3.setAlignment(Pos.TOP_LEFT);
    label3.setPrefWidth(90); 
    HBox1.getChildren().add(label3);    
    Separator separator5 = new Separator();
    separator5.setVisible(false);
    HBox1.getChildren().add(separator5);  
    TextField textField3 =  new TextField();
    textField3.setText(strTardiness);
    textField3.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    textField3.setAlignment(Pos.TOP_LEFT);
    textField3.setPrefWidth(100);
    textField3.textProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue.equals(strTardiness))
           {
             return;
             // при textField2.setText(newValue) - процедура вызывается второй раз
           }
        int h =0;
        int m = 0;
            if (tardiness > 0)
               {
                 h = (int) (tardiness / 3600);
                 m = (int) ((tardiness - h * 3600) / 60);
               }
        ControllerInput_Time.Result result  = mainApp.LoadInput_Time(h, m, true);
             if (result != null)
               {
                 strTardiness = "ч: " + result.h + " м: " + result.m;
                 tardiness = (3600 * result.h) + (result.m * 60);
               }
        newValue = strTardiness;
        textField3.setText(newValue);
    });
    HBox1.getChildren().add(textField3);  
    
    HBox HBox2 = new HBox();
    Label label4 = new Label("Переработка:");
    label4.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    label4.setTextFill(Color.WHITE);
    label4.setVisible(true);
    label4.setAlignment(Pos.TOP_LEFT);
    label4.setPrefWidth(90); 
    HBox2.getChildren().add(label4);    
    Separator separator6 = new Separator();
    separator6.setVisible(false);
    HBox2.getChildren().add(separator6);  
    TextField textField4 =  new TextField();
    textField4.setText(strRecast);
    textField4.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    textField4.setAlignment(Pos.TOP_LEFT);
    textField4.setPrefWidth(100);
    textField4.textProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue.equals(strRecast))
           {
             return;
             // при textField2.setText(newValue) - процедура вызывается второй раз
           }
        int h =0;
        int m = 0;
            if (recast > 0)
               {
                 h = (int) (recast / 3600);
                 m = (int) ((recast - h * 3600) / 60);
               }
        ControllerInput_Time.Result result  = mainApp.LoadInput_Time(h, m, true);
             if (result != null)
               {
                 strRecast = "ч: " + result.h + " м: " + result.m;
                 recast = (3600 * result.h) + (result.m * 60);
               }
        newValue = strRecast;
        textField4.setText(newValue);
    });
    HBox2.getChildren().add(textField4);  
    Separator separator7 = new Separator();
    separator7.setVisible(false);
    HBox2.getChildren().add(separator7);   
    Label label5 = new Label("Не доработано:");
    label5.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    label5.setTextFill(Color.WHITE);
    label5.setVisible(true);
    label5.setAlignment(Pos.TOP_LEFT);
    label5.setPrefWidth(90); 
    HBox2.getChildren().add(label5);    
    Separator separator8 = new Separator();
    separator8.setVisible(false);
    HBox2.getChildren().add(separator8);  
    TextField textField5 =  new TextField();
    textField5.setText(strNotFinalized);
    textField5.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    textField5.setAlignment(Pos.TOP_LEFT);
    textField5.setPrefWidth(100);
    textField5.textProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue.equals(strNotFinalized))
           {
             return;
             // при textField2.setText(newValue) - процедура вызывается второй раз
           }
        int h =0;
        int m = 0;
            if (notFinalized > 0)
               {
                 h = (int) (notFinalized / 3600);
                 m = (int) ((notFinalized - h * 3600) / 60);
               }
        ControllerInput_Time.Result result  = mainApp.LoadInput_Time(h, m, true);
             if (result != null)
               {
                 strNotFinalized = "ч: " + result.h + " м: " + result.m;
                 notFinalized = (3600 * result.h) + (result.m * 60);
               }
        newValue = strNotFinalized;
        textField5.setText(newValue);
    });
    HBox2.getChildren().add(textField5); 
    Separator separator20 = new Separator();
    separator20.setVisible(false);
    HBox2.getChildren().add(separator20);  
    CheckBox checkBox = new CheckBox("Убрать ошибки");
    checkBox.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    checkBox.setTextFill(Color.WHITE);
        if (thereIsNoDistributedData == false)
        {checkBox.setDisable(true);}
    checkBox.setOnMouseClicked((MouseEvent event) -> {
        if (checkBox.isSelected() == true)
           {
             thereIsNoDistributedData =  false;
             controllerDataTurnstilesLoading.clearErrorFlagDay();
           }
        });
    HBox2.getChildren().add(checkBox);  
     
    HBox HBox3 = new HBox(); 
    Label label6 = new Label("Отсутствие по данным 1с:");
    label6.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    label6.setTextFill(Color.WHITE);
    label6.setVisible(true);
    label6.setAlignment(Pos.TOP_LEFT);
    label6.setPrefWidth(160); 
    HBox3.getChildren().add(label6); 
    Separator separator9 = new Separator();
    separator9.setVisible(false);
    HBox3.getChildren().add(separator9);   
    TextField textField6 =  new TextField();
    textField6.setText(strAbsenceNotWorkingHours);
    textField6.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    textField6.setAlignment(Pos.TOP_LEFT);
    textField6.setPrefWidth(100);
    textField6.textProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue.equals(strAbsenceNotWorkingHours))
           {
             return;
             // при textField2.setText(newValue) - процедура вызывается второй раз
           }
        int h = absenceNotWorkingHours;
        int m = 0;
        ControllerInput_Time.Result result  = mainApp.LoadInput_Time(h, m, false);
             if (result != null)
               {
                 strAbsenceNotWorkingHours = "ч: " + result.h;
                 absenceNotWorkingHours = result.h;
               }
        newValue = strAbsenceNotWorkingHours;
        textField6.setText(newValue);
    });
    HBox3.getChildren().add(textField6);  
    Separator separator10 = new Separator();
    separator10.setVisible(false);
    HBox3.getChildren().add(separator10); 
    Label label7 = new Label("Отсутствие по данным журнала:");
    label7.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    label7.setTextFill(Color.WHITE);
    label7.setVisible(true);
    label7.setAlignment(Pos.TOP_LEFT);
    label7.setPrefWidth(200); 
    HBox3.getChildren().add(label7);    
    Separator separator11 = new Separator();
    separator11.setVisible(false);
    HBox3.getChildren().add(separator11);  
    TextField textField7 =  new TextField();
    textField7.setText(strWorkTimeFileAbsenceEqual);
    textField7.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    textField7.setAlignment(Pos.TOP_LEFT);
    textField7.setPrefWidth(100);
    textField7.textProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue.equals(strWorkTimeFileAbsenceEqual))
           {
             return;
             // при textField2.setText(newValue) - процедура вызывается второй раз
           }
        int h =0;
        int m = 0;
            if (workTimeFileAbsenceEqual > 0)
               {
                 h = (int) (workTimeFileAbsenceEqual / 3600);
                 m = (int) ((workTimeFileAbsenceEqual - h * 3600) / 60);
               }
        ControllerInput_Time.Result result  = mainApp.LoadInput_Time(h, m, true);
             if (result != null)
               {
                 strWorkTimeFileAbsenceEqual = "ч: " + result.h + " м: " + result.m;
                 workTimeFileAbsenceEqual = (3600 * result.h) + (result.m * 60);
               }
        newValue = strWorkTimeFileAbsenceEqual;
        textField7.setText(newValue);
    });
    HBox3.getChildren().add(textField7);
    
    VBox VBox1 = new VBox();
    VBox1.autosize();
    VBox1.getChildren().add(HBox1); 
    Separator separator12 = new Separator();
    separator12.setVisible(false);
    VBox1.getChildren().add(separator12);  
    VBox1.getChildren().add(HBox2); 
    Separator separator13 = new Separator();
    separator13.setVisible(false);
    VBox1.getChildren().add(separator13);  
    VBox1.getChildren().add(HBox3); 
    this.vbox = VBox1;
    
    }

    public int getIdDataTurnstiles() {
        return idDataTurnstiles;
    }

    public void setIdDataTurnstiles(int idDataTurnstiles) {
        this.idDataTurnstiles = idDataTurnstiles;
    }
    
    public VBox getVbox() {
        return vbox;
    }

    public void setVbox(VBox vbox) {
        this.vbox = vbox;
    }

    public Date getFirstDayMonth() {
        return firstDayMonth;
    }

    public void setFirstDayMonth(Date firstDayMonth) {
        this.firstDayMonth = firstDayMonth;
    }

    public Integer getIdEmployees() {
        return idEmployees;
    }

    public void setIdEmployees(Integer idEmployees) {
        this.idEmployees = idEmployees;
    }

    public Integer getIdSubdivision() {
        return idSubdivision;
    }

    public void setIdSubdivision(Integer idSubdivision) {
        this.idSubdivision = idSubdivision;
    }

    
    
    public boolean isThereIsNoDistributedData() {
        return thereIsNoDistributedData;
    }

    public void setThereIsNoDistributedData(boolean thereIsNoDistributedData) {
        this.thereIsNoDistributedData = thereIsNoDistributedData;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    public long getNotFinalized() {
        return notFinalized;
    }

    public void setNotFinalized(long notFinalized) {
        this.notFinalized = notFinalized;
    }

    public String getStrRecast() {
        return strRecast;
    }

    public void setStrRecast(String strRecast) {
        this.strRecast = strRecast;
    }

    public String getStrTardiness() {
        return strTardiness;
    }

    public void setStrTardiness(String strTardiness) {
        this.strTardiness = strTardiness;
    }

    public String getStrNotFinalized() {
        return strNotFinalized;
    }

    public void setStrNotFinalized(String strNotFinalized) {
        this.strNotFinalized = strNotFinalized;
    }

    public String getStrAbsenceNotWorkingHours() {
        return strAbsenceNotWorkingHours;
    }

    public void setStrAbsenceNotWorkingHours(String strAbsenceNotWorkingHours) {
        this.strAbsenceNotWorkingHours = strAbsenceNotWorkingHours;
    }

    public long getWorkTime() {
        return workTime;
    }

    public void setWorkTime(long workTime) {
        this.workTime = workTime;
    }

    public String getStrWorkTime() {
        return strWorkTime;
    }

    public void setStrWorkTime(String strWorkTime) {
        this.strWorkTime = strWorkTime;
    }

    public long getWorkTimeFileAbsenceEqual() {
        return workTimeFileAbsenceEqual;
    }

    public void setWorkTimeFileAbsenceEqual(long workTimeFileAbsenceEqual) {
        this.workTimeFileAbsenceEqual = workTimeFileAbsenceEqual;
    }

    public String getStrWorkTimeFileAbsenceEqual() {
        return strWorkTimeFileAbsenceEqual;
    }

    public void setStrWorkTimeFileAbsenceEqual(String strWorkTimeFileAbsenceEqual) {
        this.strWorkTimeFileAbsenceEqual = strWorkTimeFileAbsenceEqual;
    }

    
    public Date getTekDay() {
        return tekDay;
    }

    public void setTekDay(Date tekDay) {
        this.tekDay = tekDay;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }

    public Time getFirstEntry() {
        return firstEntry;
    }

    public void setFirstEntry(Time firstEntry) {
        this.firstEntry = firstEntry;
    }

    public Time getLastExit() {
        return lastExit;
    }

    public void setLastExit(Time lastExit) {
        this.lastExit = lastExit;
    }

    public long getRecast() {
        return recast;
    }

    public void setRecast(long recast) {
        this.recast = recast;
    }

    public long getTardiness() {
        return tardiness;
    }

    public void setTardiness(long tardiness) {
        this.tardiness = tardiness;
    }

    public int getAbsenceNotWorkingHours() {
        return absenceNotWorkingHours;
    }

    public void setAbsenceNotWorkingHours(int absenceNotWorkingHours) {
        this.absenceNotWorkingHours = absenceNotWorkingHours;
    }

    
    
}
