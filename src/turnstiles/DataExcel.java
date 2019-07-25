/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnstiles;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

public class DataExcel{
    private Date tekDay;
    private Date date;
    private long timeAbsenceSecond; //3600 секунд - 1 час
    private String strTimeAbsenceSecond; //3600 секунд - 1 час
    //возможные ошибки - количество входов <> не равно количеству выходов либо время входа раньше времени выхода
    private boolean error;
    private boolean entry;
    private boolean guestPass;
    private boolean firstEntryLastExit;
    private String fio;
    private Time time;
    private String TypeTurnstiles;
    private String NameTurnstiles;  
    private Time timeEntry;
    private String TypeTurnstilesEntry;
    private String NameTurnstilesEntry;
    private Time timeExit;
    private String TypeTurnstilesExit;
    private String NameTurnstilesExit;
   
    VBox vbox; 

    public VBox getVbox() {
        return vbox;
    }

    public void setVbox(VBox vbox) {
        this.vbox = vbox;
    }

    public DataExcel() {
    }

    public boolean isFirstEntryLastExit() {
        return firstEntryLastExit;
    }

    public void setFirstEntryLastExit(boolean firstEntryLastExit) {
        this.firstEntryLastExit = firstEntryLastExit;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getStrTimeAbsenceSecond() {
        return strTimeAbsenceSecond;
    }

    public void setStrTimeAbsenceSecond(String strTimeAbsenceSecond) {
        this.strTimeAbsenceSecond = strTimeAbsenceSecond;
    }

    
    
    public long getTimeAbsenceSecond() {
        return timeAbsenceSecond;
    }

    public void setTimeAbsenceSecond(long timeAbsenceSecond) {
        this.timeAbsenceSecond = timeAbsenceSecond;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

 

    public String getNameTurnstilesEntry() {
        return NameTurnstilesEntry;
    }

    public void setNameTurnstilesEntry(String NameTurnstilesEntry) {
        this.NameTurnstilesEntry = NameTurnstilesEntry;
    }

    public String getTypeTurnstilesEntry() {
        return TypeTurnstilesEntry;
    }

    public void setTypeTurnstilesEntry(String TypeTurnstilesEntry) {
        this.TypeTurnstilesEntry = TypeTurnstilesEntry;
    }

    public String getTypeTurnstilesExit() {
        return TypeTurnstilesExit;
    }

    public void setTypeTurnstilesExit(String TypeTurnstilesExit) {
        this.TypeTurnstilesExit = TypeTurnstilesExit;
    }

    
    public String getNameTurnstilesExit() {
        return NameTurnstilesExit;
    }

    public void setNameTurnstilesExit(String NameTurnstilesExit) {
        this.NameTurnstilesExit = NameTurnstilesExit;
    }

  
 
    public String getNameTurnstiles() {
        return NameTurnstiles;
    }

    public void setNameTurnstiles(String NameTurnstiles) {
        this.NameTurnstiles = NameTurnstiles;
    }

    public Time getTimeEntry() {
        return timeEntry;
    }

    public void setTimeEntry(Time timeEntry) {
        this.timeEntry = timeEntry;
    }

    public Time getTimeExit() {
        return timeExit;
    }

    public void setTimeExit(Time timeExit) {
        this.timeExit = timeExit;
    }

    public String getTypeTurnstiles() {
        return TypeTurnstiles;
    }

    public void setTypeTurnstiles(String TypeTurnstiles) {
        this.TypeTurnstiles = TypeTurnstiles;
    }

   
    
    public Date getTekDay() {
        return tekDay;
    }

    public void setTekDay(Date tekDay) {
        this.tekDay = tekDay;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isEntry() {
        return entry;
    }

    public void setEntry(boolean entry) {
        this.entry = entry;
    }

    public boolean isGuestPass() {
        return guestPass;
    }

    public void setGuestPass(boolean guestPass) {
        this.guestPass = guestPass;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }
 
  public void FillInTheDisplayFields () {
   
    HBox hBox = new HBox();
    hBox.autosize();
    String namelabel1 = "";
    String namelabel2 = "";
    String timelabel3 = "";
    String timelabel4 = "";
         if (firstEntryLastExit == true)
            {
              namelabel1 = "Вход:";
              namelabel2 = "Выход:";
              timelabel3 = timeEntry.toString();
              timelabel4 = timeExit.toString();
            }
         else
            {
              namelabel1 = "Выход:";
              namelabel2 = "Вход:";
              timelabel3 = timeExit.toString();
              timelabel4 = timeEntry.toString();
            }
    Label label1 = new Label(namelabel1);
    label1.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label1.setTextFill(Color.WHITE);
    label1.setVisible(true);
    label1.setAlignment(Pos.TOP_LEFT);
    label1.setPrefWidth(45);
    hBox.getChildren().add(label1); 
    Separator separator = new Separator();
    separator.setVisible(false);
    hBox.getChildren().add(separator); 
    Label label2 = new Label(timelabel3);
    label2.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label2.setTextFill(Color.WHITE);
    label2.setVisible(true);
    label2.setAlignment(Pos.TOP_LEFT);
    label2.setPrefWidth(55);
    hBox.getChildren().add(label2); 
    Separator separator2 = new Separator();
    separator2.setVisible(false);
    hBox.getChildren().add(separator2);    
    Label label3 = new Label(namelabel2);
    label3.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label3.setTextFill(Color.WHITE);
    label3.setVisible(true);
    label3.setAlignment(Pos.TOP_LEFT);
    label3.setPrefWidth(45);
    hBox.getChildren().add(label3); 
    Separator separator3 = new Separator();
    separator3.setVisible(false);
    hBox.getChildren().add(separator3); 
    Label label4 = new Label(timelabel4);
    label4.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label4.setTextFill(Color.WHITE);
    label4.setVisible(true);
    label4.setAlignment(Pos.TOP_LEFT);
    label4.setPrefWidth(55);
    hBox.getChildren().add(label4); 
    Separator separator4 = new Separator();
    separator4.setVisible(false);
    hBox.getChildren().add(separator4); 
    String strLabel5 = "";
         if (firstEntryLastExit == true)
             {strLabel5 = "Присутствие:";}
         else
             {strLabel5 = "Отсутствие:";}
    Label label5 = new Label(strLabel5);
    label5.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label5.setTextFill(Color.WHITE);
    label5.setVisible(true);
    label5.setAlignment(Pos.TOP_LEFT);
    label5.setPrefWidth(105);
    hBox.getChildren().add(label5);
    Separator separator5 = new Separator();
    separator5.setVisible(false);
    hBox.getChildren().add(separator5); 
    Label label6 = new Label(strTimeAbsenceSecond);
    label6.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label6.setTextFill(Color.WHITE);
    label6.setVisible(true);
    label6.setAlignment(Pos.TOP_LEFT);
    label6.setPrefWidth(150);
    hBox.getChildren().add(label6); 
    
    VBox VBox1 = new VBox();
    VBox1.autosize();
    VBox1.getChildren().add(hBox); 
    Separator separator6 = new Separator();
    VBox1.getChildren().add(separator6);
    this.vbox = VBox1;
  } 
  
  
  public void FillInTheDisplayFieldsErr () {
   
    HBox hBox = new HBox();
    hBox.autosize();
    Label label1 = new Label(TypeTurnstiles);
    label1.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    label1.setTextFill(Color.WHITE);
    label1.setVisible(true);
    label1.setAlignment(Pos.TOP_LEFT);
    label1.setPrefWidth(180);
    hBox.getChildren().add(label1); 
    Separator separator = new Separator();
    separator.setVisible(false);
    hBox.getChildren().add(separator); 
    Label label2 = new Label(NameTurnstiles);
    label2.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    label2.setTextFill(Color.WHITE);
    label2.setVisible(true);
    label2.setAlignment(Pos.TOP_LEFT);
    label2.setPrefWidth(180);
    hBox.getChildren().add(label2); 
    Separator separator2 = new Separator();
    separator2.setVisible(false);
    hBox.getChildren().add(separator2);    
    Label label3 = new Label(time.toString());
    label3.setFont(Font.font("System",  FontPosture.ITALIC, 12));
    label3.setTextFill(Color.WHITE);
    label3.setVisible(true);
    label3.setAlignment(Pos.TOP_LEFT);
    label3.setPrefWidth(50);
    hBox.getChildren().add(label3); 
    VBox VBox1 = new VBox();
    VBox1.autosize();
    VBox1.getChildren().add(hBox); 
    Separator separator6 = new Separator();
    VBox1.getChildren().add(separator6);
    this.vbox = VBox1;
  } 
  


}
