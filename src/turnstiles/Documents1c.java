
package turnstiles;

import com.jfoenix.controls.JFXDatePicker;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.UnaryOperator;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="Documents1c")
public class Documents1c implements Serializable {

    //PRIMARY KEY (idEmployees, idSubdivision, documentType, number, date_Time)
    
    @EmbeddedId
    @AttributeOverrides({
          @AttributeOverride(name = "idEmployees", column = @Column(name = "idEmployees", nullable = false)),
          @AttributeOverride(name = "idSubdivision", column = @Column(name = "idSubdivision", nullable = false)),
          @AttributeOverride(name = "documentType", column = @Column(name = "documentType", nullable = false)),
          @AttributeOverride(name = "number", column = @Column(name = "number", nullable = false)),
          @AttributeOverride(name = "date_Time", column = @Column(name = "date_Time", nullable = false)),
          @AttributeOverride(name = "tekDay", column = @Column(name = "tekDay", nullable = false))})
    private Documents1cPrimaryKey primaryKey;
    
     //одна запись из журнала Documents1c может соответсвовать только одному сотруднику
    @OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumns({
                @JoinColumn(name = "idEmployees", insertable = false, updatable = false),
                @JoinColumn(name = "idSubdivision", insertable = false, updatable = false)
                })   
    private Employees employees;
  
    @Column(name="dayS")
    private Date dayS;
    @Column(name="dayPo")
    private Date dayPo;
    @Column(name="hours")
    private int hours;
    @Column(name="hoursDay")
    private Integer hoursDay;
    @Column(name="reasonAbsence")
    private String reasonAbsence;
    @Column(name="posted")
    private boolean posted;
    @Column(name="firstDayMonth")
    private Date firstDayMonth; 
    
    @Transient 
    VBox vbox;
    
    public void FillInTheDisplayFields(){
    HBox НBox = new HBox();
    
    Label label1 = new Label("День:");
    label1.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label1.setTextFill(Color.WHITE);
    label1.setVisible(true);
    label1.setAlignment(Pos.TOP_LEFT);
    label1.setPrefWidth(100);
    НBox.getChildren().add(label1);
    Separator separator = new Separator();
    separator.setVisible(false);
    НBox.getChildren().add(separator);    
    JFXDatePicker datePicker = new JFXDatePicker();
    datePicker.getStylesheets().add("/CSS/StyleSheetBlueTableView.css");
    LocalDate localDate = this.primaryKey.getTekDay().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    datePicker.setValue(localDate);
    datePicker.setEditable(false);
    datePicker.setPrefWidth(128);
    datePicker.setOnAction(event -> {
            datePicker.setValue(localDate);
        });
    НBox.getChildren().add(datePicker); 
    Separator separator2 = new Separator();
    separator2.setVisible(false);
    НBox.getChildren().add(separator2);  
    Label label2 = new Label("Часы:");
    label2.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label2.setTextFill(Color.WHITE);
    label2.setVisible(true);
    label2.setAlignment(Pos.TOP_LEFT);
    label2.setPrefWidth(100);
    НBox.getChildren().add(label2);
    Separator separator3 = new Separator();
    separator3.setVisible(false);
    НBox.getChildren().add(separator3); 
    TextField textField = new TextField();
    textField.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    textField.setAlignment(Pos.TOP_LEFT);
    textField.setPrefWidth(80);
    textField.textProperty().addListener((observable, oldValue, newValue) -> {
        if (primaryKey.getDocumentType().equals("Невыходы в организациях"))
           {
            if (newValue.equals("") == false) 
               {
                 Integer inewValue = Integer.parseInt(newValue);
                     if (inewValue != hoursDay)
                        {
                          if (inewValue > 8)
                             {inewValue = hoursDay;}
                          hoursDay = inewValue;
                          textField.setText(hoursDay.toString());
                        }
               }
            else 
               {
                 if(hoursDay != 0)
                   {
                     hoursDay = 0;
                     textField.setText(hoursDay.toString());
                   }
               }
           }
       
    });
    
        if (hoursDay > 0)
           {
             textField.setText(hoursDay.toString());
           }
        if (this.getPrimaryKey().getDocumentType().equals("Невыходы в организациях") == false)
            {textField.setDisable(true);}
        else
            {
              UnaryOperator<TextFormatter.Change> filter = change -> {
                   String text = change.getText();
                        if (text.matches("[0-9]*")) {
                            return change;
                        }
                   return null;
              };
             TextFormatter<String> textFormatter = new TextFormatter<>(filter);
             textField.setTextFormatter(textFormatter);   
            }
    НBox.getChildren().add(textField);
    
    
    VBox VBox1 = new VBox();
    VBox1.autosize();
    VBox1.getChildren().add(НBox); 
    Separator separator9 = new Separator();
    VBox1.getChildren().add(separator9);
    this.vbox = VBox1;
    }
    
    
    public void FillInTheDisplayFieldsDataTurnstiles() {
    HBox hBox = new HBox();
    hBox.autosize();
    Label label1 = new Label(primaryKey.getDocumentType());
    label1.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    label1.setTextFill(Color.WHITE);
    label1.setVisible(true);
    label1.setAlignment(Pos.TOP_LEFT);
    label1.setPrefWidth(160);
    hBox.getChildren().add(label1); 
    Separator separator = new Separator();
    separator.setVisible(false);
    hBox.getChildren().add(separator); 
    
    Label label2 = new Label("№:");
    label2.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    label2.setTextFill(Color.WHITE);
    label2.setVisible(true);
    label2.setAlignment(Pos.TOP_LEFT);
    label2.setPrefWidth(20);
    hBox.getChildren().add(label2); 
    Separator separator2 = new Separator();
    separator2.setVisible(false);
    hBox.getChildren().add(separator2);    
    Label label3 = new Label(primaryKey.getNumber());
    label3.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    label3.setTextFill(Color.WHITE);
    label3.setVisible(true);
    label3.setAlignment(Pos.TOP_LEFT);
    label3.setPrefWidth(70);
    hBox.getChildren().add(label3); 
    Separator separator3 = new Separator();
    separator3.setVisible(false);
    hBox.getChildren().add(separator3);
    
    Label label4 = new Label("Дата");
    label4.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    label4.setTextFill(Color.WHITE);
    label4.setVisible(true);
    label4.setAlignment(Pos.TOP_LEFT);
    label4.setPrefWidth(40);
    hBox.getChildren().add(label4); 
    Separator separator4 = new Separator();
    separator4.setVisible(false);
    hBox.getChildren().add(separator4); 
    
    Label label5 = new Label(primaryKey.getDate_Time().toString().substring(0, 10));
    label5.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    label5.setTextFill(Color.WHITE);
    label5.setVisible(true);
    label5.setAlignment(Pos.TOP_LEFT);
    label5.setPrefWidth(80);
    hBox.getChildren().add(label5);
    Separator separator5 = new Separator();
    separator5.setVisible(false);
    hBox.getChildren().add(separator5); 
    
    Label label6 = new Label("Часы:");
    label6.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    label6.setTextFill(Color.WHITE);
    label6.setVisible(true);
    label6.setAlignment(Pos.TOP_LEFT);
    label6.setPrefWidth(40);
    hBox.getChildren().add(label6); 
    Separator separator6 = new Separator();
    separator6.setVisible(false);
    hBox.getChildren().add(separator6);
    Label label7 = new Label(hoursDay.toString());
    label7.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    label7.setTextFill(Color.WHITE);
    label7.setVisible(true);
    label7.setAlignment(Pos.TOP_LEFT);
    label7.setPrefWidth(40);
    hBox.getChildren().add(label7); 
    Separator separator7 = new Separator();
    separator7.setVisible(false);
    hBox.getChildren().add(separator7);
    CheckBox checkBox = new CheckBox("Проведен");
    checkBox.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    checkBox.setTextFill(Color.WHITE); 
    checkBox.setDisable(true);
    checkBox.setSelected(posted);
    hBox.getChildren().add(checkBox);
    
    
    HBox hBox2 = new HBox();
    hBox2.autosize();
    Label label8 = new Label("Причина");
    label8.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    label8.setTextFill(Color.WHITE);
    label8.setVisible(true);
    label8.setAlignment(Pos.TOP_LEFT);
    label8.setPrefWidth(50);
    hBox2.getChildren().add(label8); 
    Separator separator8 = new Separator();
    separator8.setVisible(false);
    hBox2.getChildren().add(separator8); 
    
    Label label9 = new Label(reasonAbsence);
    label9.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    label9.setTextFill(Color.WHITE);
    label9.setVisible(true);
    label9.setAlignment(Pos.TOP_LEFT);
    label9.setPrefWidth(270);
    hBox2.getChildren().add(label9); 
    Separator separator9 = new Separator();
    separator9.setVisible(false);
    hBox2.getChildren().add(separator9); 
    
    Label label10 = new Label("Период с");
    label10.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    label10.setTextFill(Color.WHITE);
    label10.setVisible(true);
    label10.setAlignment(Pos.TOP_LEFT);
    label10.setPrefWidth(50);
    hBox2.getChildren().add(label10); 
    Separator separator10 = new Separator();
    separator10.setVisible(false);
    hBox2.getChildren().add(separator10); 
    
    Label label11 = new Label(dayS.toString().substring(0, 10));
    label11.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    label11.setTextFill(Color.WHITE);
    label11.setVisible(true);
    label11.setAlignment(Pos.TOP_LEFT);
    label11.setPrefWidth(80);
    hBox2.getChildren().add(label11); 
    Separator separator11 = new Separator();
    separator11.setVisible(false);
    hBox2.getChildren().add(separator11); 
    
    Label label12 = new Label("Период по");
    label12.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    label12.setTextFill(Color.WHITE);
    label12.setVisible(true);
    label12.setAlignment(Pos.TOP_LEFT);
    label12.setPrefWidth(60);
    hBox2.getChildren().add(label12); 
    Separator separator12 = new Separator();
    separator12.setVisible(false);
    hBox2.getChildren().add(separator12); 
    
    Label label13 = new Label(dayPo.toString().substring(0, 10));
    label13.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    label13.setTextFill(Color.WHITE);
    label13.setVisible(true);
    label13.setAlignment(Pos.TOP_LEFT);
    label13.setPrefWidth(80);
    hBox2.getChildren().add(label13); 
    Separator separator13 = new Separator();
    separator13.setVisible(false);
    hBox2.getChildren().add(separator13); 
    
    
    VBox VBox1 = new VBox();
    VBox1.autosize();
    VBox1.getChildren().add(hBox); 
    Separator separator14 = new Separator();
    separator14.setVisible(false);
    VBox1.getChildren().add(separator14);
    VBox1.getChildren().add(hBox2); 
    Separator separator15 = new Separator();
    VBox1.getChildren().add(separator15);
    this.vbox = VBox1;

    }
  
  
    
    
    
    public Documents1c() {
    }

    public VBox getVbox() {
        return vbox;
    }

    public void setVbox(VBox vbox) {
        this.vbox = vbox;
    }

    public Documents1cPrimaryKey getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Documents1cPrimaryKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
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

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getHoursDay() {
        return hoursDay;
    }

    public void setHoursDay(int hoursDay) {
        this.hoursDay = hoursDay;
    }

    public String getReasonAbsence() {
        return reasonAbsence;
    }

    public void setReasonAbsence(String reasonAbsence) {
        this.reasonAbsence = reasonAbsence;
    }

    public boolean isPosted() {
        return posted;
    }

    public void setPosted(boolean posted) {
        this.posted = posted;
    }

    public Date getFirstDayMonth() {
        return firstDayMonth;
    }

    public void setFirstDayMonth(Date firstDayMonth) {
        this.firstDayMonth = firstDayMonth;
    }
   
 
                                                   
                                                   
                                                   
    
    
    
    
}
