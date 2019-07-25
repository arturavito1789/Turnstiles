package turnstiles;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
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
@Table(name="FileTurnstiles")
public class FileTurnstiles implements Serializable{
    
     @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int idFileTurnstiles;
    
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
    @Column(name="tekDay")
    private Date tekDay;
    @Column(name="entryTime")
    private Time entryTime;    
    @Column(name="exitTime")
    private Time exitTime;
    @Column(name="firstEntryLastExit")
    private boolean firstEntryLastExit;
    @Column(name="timeAbsenceSecond")
    private long timeAbsenceSecond;
    @Column(name="strTimeAbsenceSecond")
    private String strTimeAbsenceSecond;
    @Column(name="firstDayMonth")
    private Date firstDayMonth; 
    
    @Transient 
    private boolean thereIsMatchAbsenceJournal;
    @Transient 
    VBox vbox;

    public FileTurnstiles() {
    }

    public VBox getVbox() {
        return vbox;
    }

    public void setVbox(VBox vbox) {
        this.vbox = vbox;
    }

    
    
    public boolean isThereIsMatchAbsenceJournal() {
        return thereIsMatchAbsenceJournal;
    }

    public void setThereIsMatchAbsenceJournal(boolean thereIsMatchAbsenceJournal) {
        this.thereIsMatchAbsenceJournal = thereIsMatchAbsenceJournal;
    }

    
    
    public Date getFirstDayMonth() {
        return firstDayMonth;
    }

    public void setFirstDayMonth(Date firstDayMonth) {
        this.firstDayMonth = firstDayMonth;
    }

    
    
    public int getIdFileTurnstiles() {
        return idFileTurnstiles;
    }

    public void setIdFileTurnstiles(int idFileTurnstiles) {
        this.idFileTurnstiles = idFileTurnstiles;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
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

    public Date getTekDay() {
        return tekDay;
    }

    public void setTekDay(Date tekDay) {
        this.tekDay = tekDay;
    }

    public Time getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Time entryTime) {
        this.entryTime = entryTime;
    }

    public Time getExitTime() {
        return exitTime;
    }

    public void setExitTime(Time exitTime) {
        this.exitTime = exitTime;
    }

  

    public boolean isFirstEntryLastExit() {
        return firstEntryLastExit;
    }

    public void setFirstEntryLastExit(boolean firstEntryLastExit) {
        this.firstEntryLastExit = firstEntryLastExit;
    }

    public long getTimeAbsenceSecond() {
        return timeAbsenceSecond;
    }

    public void setTimeAbsenceSecond(long timeAbsenceSecond) {
        this.timeAbsenceSecond = timeAbsenceSecond;
    }

    public String getStrTimeAbsenceSecond() {
        return strTimeAbsenceSecond;
    }

    public void setStrTimeAbsenceSecond(String strTimeAbsenceSecond) {
        this.strTimeAbsenceSecond = strTimeAbsenceSecond;
    }
    
    
    public void FillInTheDisplayFields() {
    HBox hBox = new HBox();
    hBox.autosize();
    String namelabel1 = "";
    String namelabel2 = "";
    String timelabel3 = "";
    String timelabel4 = "";
    Color color = Color.WHITE;
         if (thereIsMatchAbsenceJournal == false)
            {
              color = Color.RED; 
            }
         if (firstEntryLastExit == true)
            {
              namelabel1 = "Вход:";
              namelabel2 = "Выход:";
              timelabel3 = entryTime.toString();
              timelabel4 = exitTime.toString();
            }
         else
            {
              namelabel1 = "Выход:";
              namelabel2 = "Вход:";
              timelabel3 = exitTime.toString();
              timelabel4 = entryTime.toString();
            }
    Label label1 = new Label(namelabel1);
    label1.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label1.setTextFill(color);
    label1.setVisible(true);
    label1.setAlignment(Pos.TOP_LEFT);
    label1.setPrefWidth(45);
    hBox.getChildren().add(label1); 
    Separator separator = new Separator();
    separator.setVisible(false);
    hBox.getChildren().add(separator); 
    Label label2 = new Label(timelabel3);
    label2.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label2.setTextFill(color);
    label2.setVisible(true);
    label2.setAlignment(Pos.TOP_LEFT);
    label2.setPrefWidth(55);
    hBox.getChildren().add(label2); 
    Separator separator2 = new Separator();
    separator2.setVisible(false);
    hBox.getChildren().add(separator2);    
    Label label3 = new Label(namelabel2);
    label3.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label3.setTextFill(color);
    label3.setVisible(true);
    label3.setAlignment(Pos.TOP_LEFT);
    label3.setPrefWidth(45);
    hBox.getChildren().add(label3); 
    Separator separator3 = new Separator();
    separator3.setVisible(false);
    hBox.getChildren().add(separator3); 
    Label label4 = new Label(timelabel4);
    label4.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label4.setTextFill(color);
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
    label5.setTextFill(color);
    label5.setVisible(true);
    label5.setAlignment(Pos.TOP_LEFT);
    label5.setPrefWidth(105);
    hBox.getChildren().add(label5);
    Separator separator5 = new Separator();
    separator5.setVisible(false);
    hBox.getChildren().add(separator5); 
    Label label6 = new Label(strTimeAbsenceSecond);
    label6.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label6.setTextFill(color);
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

    
    
}
