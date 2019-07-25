
package turnstiles;



import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.TextArea;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.FontPosture;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javafx.scene.Cursor;
import javafx.scene.control.Control;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Where;



@Entity
@Table(name="Subdivision")
public class Subdivision implements Serializable {

 
   
    //без аннотации ID аннотации Entity и Table не работают ;
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int idSubdivision;
    @Column(name = "nameSubdivision", unique = true)
    private String nameSubdivision;
  
    //private Set<Employees> employees;
    //@Transient - означает поле не будет персистентным, т.е. не будет сохраняться в БД и хибернейт его не использует
    @Transient 
    VBox vbox; 
    @Transient
    MainController controller;
    @Transient
    public Employees rukovoditelEmployees;
  
    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.REFRESH, mappedBy="linkSubdivision")// указывается поле из класса а не из таблицы
    @OrderBy("notRelevant asc, rukovoditel DESC, idEmployees ")
    public List<Employees> employees;
  
   
    public Subdivision()
    { }
       
    
    public <T extends Control> void  SetHandleMouseClickedMainController(T Control, Label label) {
       Control.setOnMouseClicked((MouseEvent event) -> {
                              this.controller.CurrentlabelHandleMouseClicked(this, null, label);
                            }
            );  
   } 
    
   public void FillInTheDisplayFields (MainController controller) {
        this.controller = controller;
        
        Label label = new Label(nameSubdivision);
        label.setFont(Font.font("System",  FontPosture.ITALIC, 18));
        label.setTextFill(Color.WHITE);
        label.setAlignment(Pos.TOP_CENTER);
        label.setPrefWidth(328);
        SetHandleMouseClickedMainController(label, label);
      
        VBox VBox1 = new VBox();
        VBox1.autosize();
        VBox1.getChildren().add(label);
       
        HBox hbox = new HBox();
        TextArea textArea = new TextArea();
        rukovoditelEmployees = null; //при изменении сотрудников например при перемещении в другой отдел
        //заново переформировываем vbox соответственно очишаем ссылку на руководителя.
        String dataRukovoditel = "Начальник отдела ";
        String rukovoditelFio ="";
            for (Iterator<Employees> it = employees.iterator(); it.hasNext();) {
                Employees employees = (Employees) it.next();
                    if (employees.isRukovoditel() == true && employees.isNotRelevant() == false )
                       { 
                          rukovoditelEmployees = employees; 
                          rukovoditelFio = rukovoditelEmployees.getFio();
                          break;
                       }
            } 
        
            if (rukovoditelFio=="")
                {dataRukovoditel = dataRukovoditel + "не указан. ";}
            else
                {dataRukovoditel = dataRukovoditel + rukovoditelFio +". Телефон ";
                 String rukovoditelTelefon = rukovoditelEmployees.getPhone();
                     if (rukovoditelTelefon=="")
                         dataRukovoditel = dataRukovoditel + "не указан. ";
                     else
                         dataRukovoditel = dataRukovoditel + rukovoditelTelefon + ". ";
                         dataRukovoditel = dataRukovoditel + "Почта: "; 
                         String rukovoditelEmail = rukovoditelEmployees.getEmail();
                                if (rukovoditelEmail=="")
                                    dataRukovoditel = dataRukovoditel + "не указана. ";
                                else
                                    dataRukovoditel = dataRukovoditel + rukovoditelEmail + ". ";
                             
                }
                      
        dataRukovoditel = dataRukovoditel + "Сотрудников в отделе: " + employees.size();      
         
        textArea.setText(dataRukovoditel);
        textArea.setWrapText(true);
        textArea.setEditable(false);
        textArea.setFont(Font.font("System",  FontPosture.ITALIC, 15));
        SetHandleMouseClickedMainController(textArea, label);
        hbox.getChildren().add(textArea);
             if (rukovoditelEmployees != null) 
                {
                  Image img = new Image(new ByteArrayInputStream(rukovoditelEmployees.getFoto()));
                  Circle circle = new Circle();
                  circle.setStroke(Color.web("#0fced9"));
                  circle.setRadius(50);
                  circle.setFill(new ImagePattern(img));
                  circle.setCursor(Cursor.HAND);
                  circle.setOnMouseClicked((MouseEvent event) -> {
                                        this.controller.CurrentlabelHandleMouseClicked(this, null, label);
                                   }
                  );
                  hbox.getChildren().add(circle);
                }
         
        VBox1.getChildren().add(hbox);
        
        HBox hbox2 = new HBox();
        FontAwesomeIcon fontAwesomeIconTurnstile = new FontAwesomeIcon();
        fontAwesomeIconTurnstile.setIconName("NEWSPAPER_ALT");
        fontAwesomeIconTurnstile.setFill(Color.web("#0fced9"));
        fontAwesomeIconTurnstile.setCursor(Cursor.HAND);
        fontAwesomeIconTurnstile.setSize("1.5em");
        hbox2.getChildren().add(fontAwesomeIconTurnstile);        
        FontAwesomeIcon fontAwesomeIconAbsenceJournal = new FontAwesomeIcon();
        fontAwesomeIconAbsenceJournal.setIconName("CALENDAR");
        fontAwesomeIconAbsenceJournal.setFill(Color.web("#0fced9"));
        fontAwesomeIconAbsenceJournal.setCursor(Cursor.HAND);
        fontAwesomeIconAbsenceJournal.setSize("1.5em");
        Separator separator = new Separator();
        separator.setVisible(false);
        hbox2.getChildren().add(separator); 
        hbox2.getChildren().add(fontAwesomeIconAbsenceJournal);        
        VBox1.getChildren().add(hbox2); 
        Separator separator2 = new Separator();
        SetHandleMouseClickedMainController(separator2, label);
        VBox1.getChildren().add(separator2);  
        this.vbox = VBox1;
   }
   
   public List<Employees> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employees> employees) {
        this.employees = employees;
    }
   
   public int getIdSubdivision() {
        return idSubdivision;
    }

    public void setIdSubdivision(int idSubdivision) {
        this.idSubdivision = idSubdivision;
    }

    public String getNameSubdivision() {
        return nameSubdivision;
    }

    public void setNameSubdivision(String nameSubdivision) {
         this.nameSubdivision = nameSubdivision;
    }

      public VBox getVbox() {
        return vbox;
    }

    public void setVbox(VBox vbox) {
        this.vbox = vbox;
    }


   
    
    
    
}
