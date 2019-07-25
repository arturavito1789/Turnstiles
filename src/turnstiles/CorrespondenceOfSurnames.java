
package turnstiles;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
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
@Table(name="CorrespondenceOfSurnames")
public class CorrespondenceOfSurnames implements Serializable  {
 
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int idCorrespondenceOfSurnames;
    
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
    
    @Column(name="employeesReplece")
    private String employeesReplece;

    @Transient 
    VBox vbox; 
    @Transient
    MainController controller;
    
    public CorrespondenceOfSurnames() {
    }

    public int getIdCorrespondenceOfSurnames() {
        return idCorrespondenceOfSurnames;
    }

    public void setIdCorrespondenceOfSurnames(int idCorrespondenceOfSurnames) {
        this.idCorrespondenceOfSurnames = idCorrespondenceOfSurnames;
    }

    public VBox getVbox() {
        return vbox;
    }

    public void setVbox(VBox vbox) {
        this.vbox = vbox;
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

    public String getEmployeesReplece() {
        return employeesReplece;
    }

    public void setEmployeesReplece(String employeesReplece) {
        this.employeesReplece = employeesReplece;
    }
    
    
      public <T extends Control> void  SetHandleMouseClickedMainController(T Control, Label label1) {
        Control.setOnMouseClicked((MouseEvent event) -> {
                              this.controller.CurrentlabelHandleMouseClickedCorrespondenceOfSurnames(this, label1);
                            }
            );  
    } 
    
public void FillInTheDisplayFieldsEmployees (MainController controller) {
    this.controller = controller;
    
    Image img = new Image(new ByteArrayInputStream(employees.getFoto()));
    HBox HBoxLabel = new HBox();
    HBoxLabel.autosize();
  
    Label label1 = new Label(employees.getFio());
    label1.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label1.setTextFill(Color.WHITE);
    label1.setVisible(true);
    label1.setAlignment(Pos.TOP_LEFT);
    label1.setPrefWidth(200);
    SetHandleMouseClickedMainController(label1, label1);
    
    Circle circle = new Circle();
    circle.setStroke(Color.web("#0fced9"));
    circle.setRadius(40);
    circle.setFill(new ImagePattern(img));
    circle.setCursor(Cursor.HAND);
    circle.setOnMouseClicked((MouseEvent event) -> {
                                        this.controller.CurrentlabelHandleMouseClickedCorrespondenceOfSurnames(this, label1);
                                 }
                  );
    HBoxLabel.getChildren().add(circle);
    Separator separator = new Separator();
    separator.setVisible(false);
    SetHandleMouseClickedMainController(separator, label1);
    HBoxLabel.getChildren().add(separator);  
    HBoxLabel.getChildren().add(label1);
    Separator separator2 = new Separator();
    separator2.setVisible(false);
    SetHandleMouseClickedMainController(separator2, label1);
    HBoxLabel.getChildren().add(separator2);  
    
    Label label2 = new Label("Замена:");
    label2.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label2.setTextFill(Color.WHITE);
    label2.setVisible(true);
    label2.setAlignment(Pos.TOP_LEFT);
    label2.setPrefWidth(70);
    SetHandleMouseClickedMainController(label2, label1);
    HBoxLabel.getChildren().add(label2);
    Separator separator3 = new Separator();
    separator3.setVisible(false);
    SetHandleMouseClickedMainController(separator3, label1);
    HBoxLabel.getChildren().add(separator3);  
    Label label3 = new Label(this.employeesReplece);
    label3.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label3.setTextFill(Color.WHITE);
    label3.setVisible(true);
    label3.setAlignment(Pos.TOP_LEFT);
    label3.setPrefWidth(230);
    SetHandleMouseClickedMainController(label3, label1);
    HBoxLabel.getChildren().add(label3);     
    VBox VBox1 = new VBox();
    VBox1.autosize();
    VBox1.getChildren().add(HBoxLabel); 
    Separator separator4 = new Separator();
    SetHandleMouseClickedMainController(separator4, label1);    
    VBox1.getChildren().add(separator4);
    this.vbox = VBox1;
   }   
     
    
    
    
    
}
