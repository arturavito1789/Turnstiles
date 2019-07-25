/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnstiles;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="AbsenceJournal")
public class AbsenceJournal implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int idAbsenceJournal;
    
    //одна запись из журнала AbsenceJournal может соответсвовать только одному сотруднику
    @OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumns({
                @JoinColumn(name = "idEmployees", insertable = false, updatable = false),
                @JoinColumn(name = "idSubdivision", insertable = false, updatable = false)
                })   
    private Employees employees;
    
    @Column(name="idEmployees")
    private Integer idEmployees;
    @Column(name="idSubdivision")
    private Integer idSubdivision;

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
    
    @Column(name="reasonAbsence")
    private String reasonAbsence;
    @Column(name="tekDay")
    private Date tekDay;
    @Column(name="exitTime")
    private Time exitTime;
    @Column(name="entryTime")
    private Time entryTime;
    @Column(name="firstDayMonth")
    private Date firstDayMonth; 

  
    @Transient 
    VBox vbox;
    @Transient
    MainController controller;
    @Transient 
    private boolean thereIsMatchFileTurnstiles;
    
    public AbsenceJournal() {
    }

    public boolean isThereIsMatchFileTurnstiles() {
        return thereIsMatchFileTurnstiles;
    }

    public void setThereIsMatchFileTurnstiles(boolean thereIsMatchFileTurnstiles) {
        this.thereIsMatchFileTurnstiles = thereIsMatchFileTurnstiles;
    }

    public int getIdAbsenceJournal() {
        return idAbsenceJournal;
    }

    public void setIdAbsenceJournal(int idAbsenceJournal) {
        this.idAbsenceJournal = idAbsenceJournal;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    public Date getFirstDayMonth() {
        return firstDayMonth;
    }

    public void setFirstDayMonth(Date firstDayMonth) {
        this.firstDayMonth = firstDayMonth;
    }

    
    public String getReasonAbsence() {
        return reasonAbsence;
    }

    public void setReasonAbsence(String reasonAbsence) {
        this.reasonAbsence = reasonAbsence;
    }

   
    public Date getTekDay() {
        return tekDay;
    }

    public void setTekDay(LocalDate tekDay) {
        this.tekDay = java.sql.Date.valueOf(tekDay);
    }

    public Time getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalTime exitTime) {
        this.exitTime = java.sql.Time.valueOf(exitTime);
     
    }

    public Time getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalTime entryTime) {
        this.entryTime = java.sql.Time.valueOf(entryTime);
    }
    
    public VBox getVbox() {
        return vbox;
    }

    public void setVbox(VBox vbox) {
        this.vbox = vbox;
    }
    
     public <T extends Control> void  SetHandleMouseClickedMainController(T Control, Label label) {
       Control.setOnMouseClicked((MouseEvent event) -> {
                              this.controller.CurrentlabelAbsenceJournalHandleMouseClicked(this, label);
                            }
            );  
   }
    
     public void FillInTheDisplayFields (MainController controller) {
         
        StringConverter converter = new LocalTimeStringConverter(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM), null);
        this.controller = controller;
        VBox VBox1 = new VBox();
        VBox1.autosize();
       
        Label label = new Label(employees.getFio());
        label.setFont(Font.font("System",  FontPosture.ITALIC, 18));
        label.setTextFill(Color.WHITE);
        label.setVisible(true);
        label.setAlignment(Pos.TOP_LEFT);
        label.setPrefWidth(428);
        SetHandleMouseClickedMainController(label, label);
        Label label2 = new Label("День");
        label2.setFont(Font.font("System",  FontPosture.ITALIC, 18));
        label2.setTextFill(Color.WHITE);
        label2.setVisible(true);
        label2.setAlignment(Pos.TOP_LEFT);
        label2.setPrefWidth(200);
        SetHandleMouseClickedMainController(label2, label);
     
        JFXDatePicker datePicker = new JFXDatePicker();
        datePicker.getStylesheets().add("/CSS/controlJFX.css");
        LocalDate localDate = tekDay.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        datePicker.setValue(localDate);
        datePicker.setEditable(false);
        datePicker.setOnAction(event -> {
            datePicker.setValue(localDate);
        });
        SetHandleMouseClickedMainController(datePicker, label);
     
        HBox hboxLabel2 = new HBox();
        hboxLabel2.getChildren().add(label2);
        hboxLabel2.getChildren().add(datePicker);
        
        Label label3 = new Label("Время ухода");
        label3.setFont(Font.font("System",  FontPosture.ITALIC, 18));
        label3.setTextFill(Color.WHITE);
        label3.setVisible(true);
        label3.setAlignment(Pos.TOP_LEFT);
        label3.setPrefWidth(200);
        SetHandleMouseClickedMainController(label3, label);
        JFXTimePicker timePicker = new JFXTimePicker();
        timePicker.setConverter(converter);
        timePicker.getStylesheets().add("/CSS/controlJFX.css");
        LocalTime localTime = exitTime.toLocalTime();
        timePicker.setValue(localTime);
        timePicker.setEditable(false);
        timePicker.setOnAction(event -> {
            timePicker.setValue(localTime);
        });
        SetHandleMouseClickedMainController(timePicker, label);
        HBox hboxLabel3 = new HBox();
        hboxLabel3.getChildren().add(label3);
        hboxLabel3.getChildren().add(timePicker);
        
        Label label4 = new Label("Время прихода");
        label4.setFont(Font.font("System",  FontPosture.ITALIC, 18));
        label4.setTextFill(Color.WHITE);
        label4.setVisible(true);
        label4.setAlignment(Pos.TOP_LEFT);
        label4.setPrefWidth(200);
        SetHandleMouseClickedMainController(label4, label);
        JFXTimePicker timePicker2 = new JFXTimePicker();
        timePicker2.setConverter(converter);        
        timePicker2.getStylesheets().add("/CSS/controlJFX.css");
        LocalTime localTime2 = entryTime.toLocalTime();
        timePicker2.setValue(localTime2);
        timePicker2.setEditable(false);
        timePicker2.setOnAction(event -> {
            timePicker2.setValue(localTime2);
        });
        SetHandleMouseClickedMainController(timePicker2, label);
        HBox hboxLabel4 = new HBox();
        hboxLabel4.getChildren().add(label4);
        hboxLabel4.getChildren().add(timePicker2);
        
        Label label5 = new Label("Причина отсутствия");
        label5.setFont(Font.font("System",  FontPosture.ITALIC, 18));
        label5.setTextFill(Color.WHITE);
        label5.setVisible(true);
        label5.setAlignment(Pos.TOP_LEFT);
        label5.setPrefWidth(200);
        SetHandleMouseClickedMainController(label5, label);
        Label label6 = new Label(reasonAbsence);
        label6.setFont(Font.font("System",  FontPosture.ITALIC, 18));
        label6.setTextFill(Color.WHITE);
        label6.setVisible(true);
        label6.setAlignment(Pos.TOP_LEFT);
        label6.setPrefWidth(500);   
        SetHandleMouseClickedMainController(label6, label);
        
        HBox hboxLabel5 = new HBox();
        hboxLabel5.getChildren().add(label5);
        hboxLabel5.getChildren().add(label6);
        
        VBox vboxLabel = new VBox();
        vboxLabel.getChildren().add(label);
        vboxLabel.getChildren().add(hboxLabel2);
        vboxLabel.getChildren().add(hboxLabel3);
        vboxLabel.getChildren().add(hboxLabel4);
        vboxLabel.getChildren().add(hboxLabel5);
        
        HBox hboxCircle = new HBox();
        Image img = new Image(new ByteArrayInputStream(employees.getFoto()));
        Circle circle = new Circle();
        circle.setStroke(Color.web("#0fced9"));
        circle.setRadius(50);
        circle.setFill(new ImagePattern(img));
        circle.setCursor(Cursor.HAND);
        circle.setOnMouseClicked((MouseEvent event) -> {
                              this.controller.CurrentlabelAbsenceJournalHandleMouseClicked(this, label);
                            }
            );  
        hboxCircle.getChildren().add(circle);
        Separator separator = new Separator();
        separator.setVisible(false);
        SetHandleMouseClickedMainController(separator, label);
        hboxCircle.getChildren().add(separator); 
        hboxCircle.getChildren().add(vboxLabel);
        VBox1.getChildren().add(hboxCircle);
        Separator separator2 = new Separator();
        SetHandleMouseClickedMainController(separator2, label);
        VBox1.getChildren().add(separator2);
        this.vbox = VBox1;
   }
  
     
   public void FillInTheDisplayFieldsDataTurnstiles() {
    HBox hBox = new HBox();
    hBox.autosize();
    Color color = Color.WHITE;
         if (thereIsMatchFileTurnstiles == false)
            {
              color = Color.RED; 
            }      
    Label label1 = new Label("Выход:");
    label1.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label1.setTextFill(color);
    label1.setVisible(true);
    label1.setAlignment(Pos.TOP_LEFT);
    label1.setPrefWidth(45);
    hBox.getChildren().add(label1); 
    Separator separator = new Separator();
    separator.setVisible(false);
    hBox.getChildren().add(separator); 
    Label label2 = new Label(exitTime.toString());
    label2.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label2.setTextFill(color);
    label2.setVisible(true);
    label2.setAlignment(Pos.TOP_LEFT);
    label2.setPrefWidth(55);
    hBox.getChildren().add(label2); 
    Separator separator2 = new Separator();
    separator2.setVisible(false);
    hBox.getChildren().add(separator2);    
    Label label3 = new Label("Вход:");
    label3.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label3.setTextFill(color);
    label3.setVisible(true);
    label3.setAlignment(Pos.TOP_LEFT);
    label3.setPrefWidth(45);
    hBox.getChildren().add(label3); 
    Separator separator3 = new Separator();
    separator3.setVisible(false);
    hBox.getChildren().add(separator3); 
    Label label4 = new Label(entryTime.toString());
    label4.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label4.setTextFill(color);
    label4.setVisible(true);
    label4.setAlignment(Pos.TOP_LEFT);
    label4.setPrefWidth(55);
    hBox.getChildren().add(label4); 
    Separator separator4 = new Separator();
    separator4.setVisible(false);
    hBox.getChildren().add(separator4); 
    
    Label label5 = new Label("Причина отсутствия");
    label5.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    label5.setTextFill(color);
    label5.setVisible(true);
    label5.setAlignment(Pos.TOP_LEFT);
    label5.setPrefWidth(130);
    hBox.getChildren().add(label5);
    Separator separator5 = new Separator();
    separator5.setVisible(false);
    hBox.getChildren().add(separator5); 
    Label label6 = new Label(reasonAbsence);
    label6.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    label6.setTextFill(color);
    label6.setVisible(true);
    label6.setAlignment(Pos.TOP_LEFT);
    label6.setPrefWidth(250);
    hBox.getChildren().add(label6); 
    
    VBox VBox1 = new VBox();
    VBox1.autosize();
    VBox1.getChildren().add(hBox); 
    Separator separator6 = new Separator();
    VBox1.getChildren().add(separator6);
    this.vbox = VBox1;

    }
  
     
    
}
