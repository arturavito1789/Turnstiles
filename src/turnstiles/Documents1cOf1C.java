/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnstiles;

import com.jfoenix.controls.JFXDatePicker;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

/**
 *
 * @author ArakelovAE
 */
public class Documents1cOf1C {
    
    private AdditionalEmployees additionalEmployees;
    private Employees employees;
    private String documentType;
    private String Number;
    private Date Date_Time;
    private boolean Posted;
    private Date dayS;
    private Date dayPo;
    private String reasonAbsence;
    private Integer Hours; //часы задаются только в невыходах остальное дни то есть на каждую дату периода по 8 часов.

    public ObservableList<Documents1c> documents1c;
    public CheckBox SelectedLoadCheckBox; 
    public Label label1; 
    VBox vbox; 
    ControllerAddDocuments1c controllerAddDocuments1c;
    MainController mainController;

    public Integer getHours() {
        return Hours;
    }

    public void setHours(Integer Hours) {
        this.Hours = Hours;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }
         

    public Documents1cOf1C() {
     documents1c = FXCollections.observableArrayList(); 
    }

    public AdditionalEmployees getAdditionalEmployees() {
        return additionalEmployees;
    }

    public void setAdditionalEmployees(AdditionalEmployees additionalEmployees) {
        this.additionalEmployees = additionalEmployees;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String Number) {
        this.Number = Number;
    }

    public Date getDate_Time() {
        return Date_Time;
    }

    public void setDate_Time(Date Date_Time) {
        this.Date_Time = Date_Time;
    }

    public boolean isPosted() {
        return Posted;
    }

    public void setPosted(boolean Posted) {
        this.Posted = Posted;
    }

    public Date getDayS() {
        return dayS;
    }

    public void setDayS(Date dayS) {
        this.dayS = dayS;
    }

    public Date getDayPo() {
        return dayPo;
    }

    public void setDayPo(Date dayPo) {
        this.dayPo = dayPo;
    }

    public String getReasonAbsence() {
        return reasonAbsence;
    }

    public void setReasonAbsence(String reasonAbsence) {
        this.reasonAbsence = reasonAbsence;
    }

    public VBox getVbox() {
        return vbox;
    }

    public void setVbox(VBox vbox) {
        this.vbox = vbox;
    }
   
    public <T extends Control> void  SetHandleMouseClickedControllerAddDocuments1c(T Control, Label label1) {
        Control.setOnMouseClicked((MouseEvent event) -> {
                              this.controllerAddDocuments1c.CurrentlabelHandleMouseClickedDocuments1cOf1C(this, label1);
                            }
            );  
    } 
    
     public <T extends Control> void  SetHandleMouseClickedMainController(T Control, Label label1) {
        Control.setOnMouseClicked((MouseEvent event) -> {
                              this.mainController.CurrentlabelHandleMouseClickedDocuments1c(this, label1);
                            }
            );  
    } 
    
    
  public void FillInTheDisplayFieldsAdditionalEmployees (ControllerAddDocuments1c controller) {
    this.controllerAddDocuments1c = controller;
    VBox VBoxLabel = new VBox();
    VBoxLabel.autosize();
    VBox VBoxLabel2 = new VBox();
    VBoxLabel2.autosize();
    label1 = new Label(this.documentType);
    label1.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label1.setTextFill(Color.WHITE);
    label1.setVisible(true);
    label1.setAlignment(Pos.TOP_LEFT);
    label1.setPrefWidth(200);
    SetHandleMouseClickedControllerAddDocuments1c(label1, label1);
    VBoxLabel.getChildren().add(label1);
    
    HBox hbox1 = new HBox(); 
    hbox1.autosize();
    Label label2 = new Label("№:");
    label2.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label2.setTextFill(Color.WHITE);
    label2.setVisible(true);
    label2.setAlignment(Pos.TOP_LEFT);
    label2.setPrefWidth(20);
    SetHandleMouseClickedControllerAddDocuments1c(label2, label1);
    hbox1.getChildren().add(label2);
    Separator separator = new Separator();
    separator.setVisible(false);
    SetHandleMouseClickedControllerAddDocuments1c(separator, label1);
    hbox1.getChildren().add(separator);    
    Label label3 = new Label(this.Number);
    label3.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label3.setTextFill(Color.WHITE);
    label3.setVisible(true);
    label3.setAlignment(Pos.TOP_LEFT);
    label3.setPrefWidth(130);
    SetHandleMouseClickedControllerAddDocuments1c(label3, label1);
    hbox1.getChildren().add(label3);
    VBoxLabel.getChildren().add(hbox1);
    
    HBox hbox2 = new HBox(); 
    hbox2.autosize();
    Label label4 = new Label("Дата:");
    label4.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label4.setTextFill(Color.WHITE);
    label4.setVisible(true);
    label4.setAlignment(Pos.TOP_LEFT);
    label4.setPrefWidth(60);
    SetHandleMouseClickedControllerAddDocuments1c(label4, label1);
    hbox2.getChildren().add(label4);
    Separator separator2 = new Separator();
    separator2.setVisible(false);
    SetHandleMouseClickedControllerAddDocuments1c(separator2, label1);
    hbox2.getChildren().add(separator2);    
    JFXDatePicker datePicker = new JFXDatePicker();
    datePicker.getStylesheets().add("/CSS/controlJFX.css");
    LocalDate localDate = this.Date_Time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    datePicker.setValue(localDate);
    datePicker.setEditable(false);
    datePicker.setPrefWidth(128);
    datePicker.setOnAction(event -> {
            datePicker.setValue(localDate);
        });
    SetHandleMouseClickedControllerAddDocuments1c(datePicker, label1);
    hbox2.getChildren().add(datePicker);
    VBoxLabel.getChildren().add(hbox2);
   
    CheckBox checkBox = new CheckBox("Проведен");
    checkBox.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    checkBox.setTextFill(Color.WHITE); 
    checkBox.setDisable(true);
    checkBox.setSelected(this.Posted);
    SetHandleMouseClickedControllerAddDocuments1c(checkBox, label1);   
    VBoxLabel.getChildren().add(checkBox);
   
    
    HBox hbox3 = new HBox(); 
    hbox3.autosize();
    Label label5 = new Label("Период c:");
    label5.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label5.setTextFill(Color.WHITE);
    label5.setVisible(true);
    label5.setAlignment(Pos.TOP_LEFT);
    label5.setPrefWidth(100);
    SetHandleMouseClickedControllerAddDocuments1c(label5, label1); 
    hbox3.getChildren().add(label5);
    Separator separator3 = new Separator();
    separator3.setVisible(false);
    SetHandleMouseClickedControllerAddDocuments1c(separator3, label1);    
    hbox3.getChildren().add(separator3);    
    JFXDatePicker datePicker2 = new JFXDatePicker();
    datePicker2.getStylesheets().add("/CSS/controlJFX.css");
    LocalDate localDate2 = this.dayS.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    datePicker2.setValue(localDate2);
    datePicker2.setEditable(false);
    datePicker2.setPrefWidth(128);
    SetHandleMouseClickedControllerAddDocuments1c(datePicker2, label1);
    hbox3.getChildren().add(datePicker2);
    VBoxLabel2.getChildren().add(hbox3);
    
    HBox hbox4 = new HBox(); 
    hbox4.autosize();
    Label label6 = new Label("Период по:");
    label6.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label6.setTextFill(Color.WHITE);
    label6.setVisible(true);
    label6.setAlignment(Pos.TOP_LEFT);
    label6.setPrefWidth(100);
    SetHandleMouseClickedControllerAddDocuments1c(label6, label1);
    hbox4.getChildren().add(label6);
    Separator separator4 = new Separator();
    separator4.setVisible(false);
    SetHandleMouseClickedControllerAddDocuments1c(separator4, label1);
    hbox4.getChildren().add(separator4);    
    JFXDatePicker datePicker3 = new JFXDatePicker();
    datePicker3.getStylesheets().add("/CSS/controlJFX.css");
    LocalDate localDate3 = this.dayPo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    datePicker3.setValue(localDate3);
    datePicker3.setEditable(false);
    datePicker3.setPrefWidth(128);
    SetHandleMouseClickedControllerAddDocuments1c(datePicker3, label1);
    hbox4.getChildren().add(datePicker3);
    VBoxLabel2.getChildren().add(hbox4);
    
    
    HBox hbox5 = new HBox(); 
    hbox5.autosize();
    Label label7 = new Label("Причина:");
    label7.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label7.setTextFill(Color.WHITE);
    label7.setVisible(true);
    label7.setAlignment(Pos.TOP_LEFT);
    label7.setPrefWidth(100);
    SetHandleMouseClickedControllerAddDocuments1c(label7, label1);
    hbox5.getChildren().add(label7);
    Separator separator5 = new Separator();
    separator5.setVisible(false);
    SetHandleMouseClickedControllerAddDocuments1c(separator5, label1);
    hbox5.getChildren().add(separator5);    
    Label label8 = new Label(this.reasonAbsence);
    label8.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label8.setTextFill(Color.WHITE);
    label8.setVisible(true);
    label8.setAlignment(Pos.TOP_LEFT);
    label8.setPrefWidth(340);
    Tooltip label8Tooltip = new Tooltip(this.reasonAbsence);
    label8.setTooltip(label8Tooltip);
    SetHandleMouseClickedControllerAddDocuments1c(label8, label1);
    hbox5.getChildren().add(label8);
    VBoxLabel2.getChildren().add(hbox5);
    
    HBox hbox6 = new HBox(); 
    hbox6.autosize();
    Label label9 = new Label("Часы:");
    label9.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label9.setTextFill(Color.WHITE);
    label9.setVisible(true);
    label9.setAlignment(Pos.TOP_LEFT);
    label9.setPrefWidth(100);
    SetHandleMouseClickedControllerAddDocuments1c(label9, label1);
    hbox6.getChildren().add(label9);
    Separator separator6 = new Separator();
    separator6.setVisible(false);
    SetHandleMouseClickedControllerAddDocuments1c(separator6, label1);
    hbox6.getChildren().add(separator6);    
    TextField textField = new TextField();//this.Hours);
    textField.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    textField.setAlignment(Pos.TOP_LEFT);
    textField.setPrefWidth(80);
    SetHandleMouseClickedControllerAddDocuments1c(textField, label1);
        if (this.Hours > 0)
        {
         textField.setText(this.Hours.toString());
        }
    textField.setDisable(true);
    hbox6.getChildren().add(textField);
    Separator separator7 = new Separator();
    separator7.setVisible(false);
    SetHandleMouseClickedControllerAddDocuments1c(separator7, label1);
    hbox6.getChildren().add(separator7);
    SelectedLoadCheckBox = new CheckBox("Выбран для загрузки");
    SelectedLoadCheckBox.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    SelectedLoadCheckBox.setTextFill(Color.WHITE); 
    SetHandleMouseClickedControllerAddDocuments1c(SelectedLoadCheckBox, label1);
    hbox6.getChildren().add(SelectedLoadCheckBox);
    VBoxLabel2.getChildren().add(hbox6);
  
    
    HBox НBox1 = new HBox();
    НBox1.getChildren().add(VBoxLabel); 
    Separator separator8 = new Separator();
    separator8.setVisible(false);
    SetHandleMouseClickedControllerAddDocuments1c(separator8, label1);
    
    НBox1.getChildren().add(separator8);
    НBox1.getChildren().add(VBoxLabel2); 
    VBox VBox1 = new VBox();
    VBox1.autosize();
    VBox1.getChildren().add(НBox1); 
    Separator separator9 = new Separator();
    SetHandleMouseClickedControllerAddDocuments1c(separator9, label1);    
    VBox1.getChildren().add(separator9);
    this.vbox = VBox1;
   }   
   
 
  
   public void FillInTheDisplayFieldsEmployees (MainController controller) {
    this.mainController = controller;
    
    Image img = new Image(new ByteArrayInputStream(employees.getFoto()));
    VBox VBoxLabel = new VBox();
    VBoxLabel.autosize();
    VBox VBoxLabel2 = new VBox();
    VBoxLabel2.autosize();
    VBox VBoxLabel3 = new VBox();
    VBoxLabel3.autosize();
    
    
    Circle circle = new Circle();
    circle.setStroke(Color.web("#0fced9"));
    circle.setRadius(40);
    circle.setFill(new ImagePattern(img));
    circle.setCursor(Cursor.HAND);
    circle.setOnMouseClicked((MouseEvent event) -> {
                                        this.mainController.CurrentlabelHandleMouseClickedDocuments1c (this, label1);
                                 }
                  );
    VBoxLabel.getChildren().add(circle);
    
    label1 = new Label(this.documentType);
    label1.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label1.setTextFill(Color.WHITE);
    label1.setVisible(true);
    label1.setAlignment(Pos.TOP_LEFT);
    label1.setPrefWidth(200);
    SetHandleMouseClickedMainController(label1, label1);
    VBoxLabel2.getChildren().add(label1);
    
    HBox hbox1 = new HBox(); 
    hbox1.autosize();
    Label label2 = new Label("№:");
    label2.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label2.setTextFill(Color.WHITE);
    label2.setVisible(true);
    label2.setAlignment(Pos.TOP_LEFT);
    label2.setPrefWidth(20);
    SetHandleMouseClickedMainController(label2, label1);
    hbox1.getChildren().add(label2);
    Separator separator = new Separator();
    separator.setVisible(false);
    SetHandleMouseClickedMainController(separator, label1);
    hbox1.getChildren().add(separator);    
    Label label3 = new Label(this.Number);
    label3.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label3.setTextFill(Color.WHITE);
    label3.setVisible(true);
    label3.setAlignment(Pos.TOP_LEFT);
    label3.setPrefWidth(130);
    SetHandleMouseClickedMainController(label3, label1);
    hbox1.getChildren().add(label3);
    VBoxLabel2.getChildren().add(hbox1);
    
    HBox hbox2 = new HBox(); 
    hbox2.autosize();
    Label label4 = new Label("Дата:");
    label4.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label4.setTextFill(Color.WHITE);
    label4.setVisible(true);
    label4.setAlignment(Pos.TOP_LEFT);
    label4.setPrefWidth(60);
    SetHandleMouseClickedMainController(label4, label1);
    hbox2.getChildren().add(label4);
    Separator separator2 = new Separator();
    separator2.setVisible(false);
    SetHandleMouseClickedMainController(separator2, label1);
    hbox2.getChildren().add(separator2);    
    JFXDatePicker datePicker = new JFXDatePicker();
    datePicker.getStylesheets().add("/CSS/controlJFX.css");
    LocalDate localDate = this.Date_Time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    datePicker.setValue(localDate);
    datePicker.setEditable(false);
    datePicker.setPrefWidth(128);
    datePicker.setOnAction(event -> {
            datePicker.setValue(localDate);
        });
    SetHandleMouseClickedMainController(datePicker, label1);
    hbox2.getChildren().add(datePicker);
    VBoxLabel2.getChildren().add(hbox2);
   
    CheckBox checkBox = new CheckBox("Проведен");
    checkBox.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    checkBox.setTextFill(Color.WHITE); 
    checkBox.setDisable(true);
    checkBox.setSelected(this.Posted);
    SetHandleMouseClickedMainController(checkBox, label1);   
    VBoxLabel2.getChildren().add(checkBox);
   
    
    HBox hbox3 = new HBox(); 
    hbox3.autosize();
    Label label5 = new Label("Период c:");
    label5.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label5.setTextFill(Color.WHITE);
    label5.setVisible(true);
    label5.setAlignment(Pos.TOP_LEFT);
    label5.setPrefWidth(100);
    SetHandleMouseClickedMainController(label5, label1); 
    hbox3.getChildren().add(label5);
    Separator separator3 = new Separator();
    separator3.setVisible(false);
    SetHandleMouseClickedMainController(separator3, label1);    
    hbox3.getChildren().add(separator3);    
    JFXDatePicker datePicker2 = new JFXDatePicker();
    datePicker2.getStylesheets().add("/CSS/controlJFX.css");
    LocalDate localDate2 = this.dayS.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    datePicker2.setValue(localDate2);
    datePicker2.setEditable(false);
    datePicker2.setPrefWidth(128);
    datePicker2.setOnAction(event -> {
            datePicker2.setValue(localDate2);
        });
    SetHandleMouseClickedMainController(datePicker2, label1);
    hbox3.getChildren().add(datePicker2);
    VBoxLabel3.getChildren().add(hbox3);
    
    HBox hbox4 = new HBox(); 
    hbox4.autosize();
    Label label6 = new Label("Период по:");
    label6.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label6.setTextFill(Color.WHITE);
    label6.setVisible(true);
    label6.setAlignment(Pos.TOP_LEFT);
    label6.setPrefWidth(100);
    SetHandleMouseClickedMainController(label6, label1);
    hbox4.getChildren().add(label6);
    Separator separator4 = new Separator();
    separator4.setVisible(false);
    SetHandleMouseClickedMainController(separator4, label1);
    hbox4.getChildren().add(separator4);    
    JFXDatePicker datePicker3 = new JFXDatePicker();
    datePicker3.getStylesheets().add("/CSS/controlJFX.css");
    LocalDate localDate3 = this.dayPo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    datePicker3.setValue(localDate3);
    datePicker3.setEditable(false);
    datePicker3.setPrefWidth(128);
    datePicker3.setOnAction(event -> {
            datePicker3.setValue(localDate3);
        });
    SetHandleMouseClickedMainController(datePicker3, label1);
    hbox4.getChildren().add(datePicker3);
    VBoxLabel3.getChildren().add(hbox4);
    
    
    HBox hbox5 = new HBox(); 
    hbox5.autosize();
    Label label7 = new Label("Причина:");
    label7.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label7.setTextFill(Color.WHITE);
    label7.setVisible(true);
    label7.setAlignment(Pos.TOP_LEFT);
    label7.setPrefWidth(100);
    SetHandleMouseClickedMainController(label7, label1);
    hbox5.getChildren().add(label7);
    Separator separator5 = new Separator();
    separator5.setVisible(false);
    SetHandleMouseClickedMainController(separator5, label1);
    hbox5.getChildren().add(separator5);    
    Label label8 = new Label(this.reasonAbsence);
    label8.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label8.setTextFill(Color.WHITE);
    label8.setVisible(true);
    label8.setAlignment(Pos.TOP_LEFT);
    label8.setPrefWidth(340);
    Tooltip label8Tooltip = new Tooltip(this.reasonAbsence);
    label8.setTooltip(label8Tooltip);
    SetHandleMouseClickedMainController(label8, label1);
    hbox5.getChildren().add(label8);
    VBoxLabel3.getChildren().add(hbox5);
    
    HBox hbox6 = new HBox(); 
    hbox6.autosize();
    Label label9 = new Label("Часы:");
    label9.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label9.setTextFill(Color.WHITE);
    label9.setVisible(true);
    label9.setAlignment(Pos.TOP_LEFT);
    label9.setPrefWidth(100);
    SetHandleMouseClickedMainController(label9, label1);
    hbox6.getChildren().add(label9);
    Separator separator6 = new Separator();
    separator6.setVisible(false);
    SetHandleMouseClickedMainController(separator6, label1);
    hbox6.getChildren().add(separator6);    
    TextField textField = new TextField();//this.Hours);
    textField.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    textField.setAlignment(Pos.TOP_LEFT);
    textField.setPrefWidth(80);
    SetHandleMouseClickedMainController(textField, label1);
        if (this.Hours > 0)
        {
         textField.setText(this.Hours.toString());
        }
    textField.setDisable(true);
    hbox6.getChildren().add(textField);
    Separator separator7 = new Separator();
    separator7.setVisible(false);
    SetHandleMouseClickedMainController(separator7, label1);
    hbox6.getChildren().add(separator7);
    VBoxLabel3.getChildren().add(hbox6);
  
    
    HBox НBox1 = new HBox();
    НBox1.getChildren().add(VBoxLabel); 
    Separator separator8 = new Separator();
    separator8.setVisible(false);
    SetHandleMouseClickedMainController(separator8, label1);
    НBox1.getChildren().add(separator8);
    НBox1.getChildren().add(VBoxLabel2);
    Separator separator9 = new Separator();
    separator9.setVisible(false);
    SetHandleMouseClickedMainController(separator9, label1);
    НBox1.getChildren().add(separator9);
    НBox1.getChildren().add(VBoxLabel3);
     
    VBox VBox1 = new VBox();
    VBox1.autosize();
    VBox1.getChildren().add(НBox1); 
    Separator separator10 = new Separator();
    SetHandleMouseClickedMainController(separator10, label1);    
    VBox1.getChildren().add(separator10);
    this.vbox = VBox1;
   }   
 
  
  
  
  public void setDocuments1c(){
        Calendar calendar = Calendar.getInstance(Locale.US);
      //  calendar.getsetFirstDayOfWeek(Calendar.SUNDAY);
        if (dayS.equals(dayPo) == true)
          {
            Documents1cPrimaryKey documents1cPrimaryKey = new Documents1cPrimaryKey(); 
            documents1cPrimaryKey.setIdSubdivision(additionalEmployees.employees.getPrimaryKey().getIdSubdivision());
            documents1cPrimaryKey.setIdEmployees(additionalEmployees.employees.getPrimaryKey().getIdEmployees());
            documents1cPrimaryKey.setDate_Time(Date_Time);
            documents1cPrimaryKey.setDocumentType(documentType);
            documents1cPrimaryKey.setNumber(Number);
            documents1cPrimaryKey.setTekDay(dayS);
            Documents1c document1c = new Documents1c();
            document1c.setEmployees(additionalEmployees.employees);
            document1c.setPrimaryKey(documents1cPrimaryKey); 
            calendar.setTime(dayS);
            calendar.set(Calendar.DAY_OF_MONTH,01);
            Date firstDayMonthDate = calendar.getTime();
            document1c.setFirstDayMonth(firstDayMonthDate);
            document1c.setDayS(dayS);
            document1c.setDayPo(dayPo);
            document1c.setHours(Hours);
            document1c.setPosted(Posted);
            document1c.setReasonAbsence(reasonAbsence);
                 if (documentType.equals("Начисление по больничному") == true || documentType.equals("Отпуска организаций") || documentType.equals("Командировки организаций"))
                    {document1c.setHoursDay(8);}
                 else
                    {document1c.setHoursDay(Hours);}
            document1c.FillInTheDisplayFields();     
            documents1c.add(document1c);
          }     
       else
          {
           calendar.setTime(dayS);
           int intHours = Hours;
                while("1" == "1") {
                     Date tekData = calendar.getTime();
                         if (tekData.after(dayPo))
                            {
                             break;
                            }
                     Documents1cPrimaryKey documents1cPrimaryKey = new Documents1cPrimaryKey(); 
                     documents1cPrimaryKey.setIdSubdivision(additionalEmployees.employees.getPrimaryKey().getIdSubdivision());
                     documents1cPrimaryKey.setIdEmployees(additionalEmployees.employees.getPrimaryKey().getIdEmployees());
                     documents1cPrimaryKey.setDate_Time(Date_Time);
                     documents1cPrimaryKey.setDocumentType(documentType);
                     documents1cPrimaryKey.setNumber(Number);
                     documents1cPrimaryKey.setTekDay(tekData);
                     Documents1c document1c = new Documents1c();
                     document1c.setEmployees(additionalEmployees.employees);
                     document1c.setPrimaryKey(documents1cPrimaryKey);
                     Calendar calendar2 = Calendar.getInstance();
                     calendar2.setTime(tekData);
                     calendar2.set(Calendar.DAY_OF_MONTH,01);
                     Date firstDayMonthDate = calendar2.getTime();
                     document1c.setFirstDayMonth(firstDayMonthDate);
                     document1c.setDayS(dayS);
                     document1c.setDayPo(dayPo);
                     document1c.setHours(Hours);
                     document1c.setPosted(Posted);
                     document1c.setReasonAbsence(reasonAbsence);
                        if (documentType.equals("Начисление по больничному") == true || documentType.equals("Отпуска организаций") || documentType.equals("Командировки организаций"))
                           {document1c.setHoursDay(8);}
                        else
                           {
                            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                                 if (dayOfWeek != 7 & dayOfWeek != 1)
                                    { 
                                      if (intHours > 8)
                                         {
                                           document1c.setHoursDay(8);
                                           intHours = intHours - 8;
                                         }
                                      else
                                         {
                                            document1c.setHoursDay(intHours);
                                            intHours = 0;
                                          }
                                    }
                                 else
                                    {document1c.setHoursDay(0);}
                           }
                     document1c.FillInTheDisplayFields();  
                     documents1c.add(document1c);
                     calendar.add(Calendar.DATE, 1);   
                    }
          }    
           
  }
    
}
