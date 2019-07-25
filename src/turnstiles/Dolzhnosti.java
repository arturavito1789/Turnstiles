package turnstiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.ByteArrayInputStream;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OrderBy;
import org.hibernate.annotations.Where;


@Entity
@Table(name="Dolzhnosti")
public class Dolzhnosti implements Serializable{ 
    
    //без аннотации ID аннотации Entity и Table не работают ;
    @Id
    private String nameDolzhnosti;

    
    @OneToMany(fetch=FetchType.EAGER, mappedBy="dolzhnosti", cascade = CascadeType.REFRESH)// указывается поле из класса а не из таблицы
    @OrderBy("rukovoditel DESC, idEmployees")
    @Where(clause = "notRelevant = 'False'")
    public List<Employees> employees;
    
    //@Transient - означает поле не будет персистентным, т.е. не будет сохраняться в БД и хибернейт его не использует
    @Transient 
    VBox vbox; 
    @Transient
    MainController controller;

    public Dolzhnosti() {
    }

    public VBox getVbox() {
        return vbox;
    }

    public void setVbox(VBox vbox) {
        this.vbox = vbox;
    }
   
    public String getNameDolzhnosti() {
      return nameDolzhnosti;
    }

   public void setNameDolzhnosti(String nameDolzhnosti) {
     this.nameDolzhnosti = nameDolzhnosti;
   }

    public List<Employees> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employees> employees) {
        this.employees = employees;
    }
   public <T extends Control> void  SetHandleMouseClickedMainController(T Control, Label label) {
       Control.setOnMouseClicked((MouseEvent event) -> {
                              this.controller.CurrentlabelHandleMouseClicked(null, this, label);
                            }
            );  
   }
           

   public void FillInTheDisplayFields (MainController controller) {
     
     this.controller = controller;
     Label label = new Label(nameDolzhnosti);
     label.setFont(Font.font("System",  FontPosture.ITALIC, 18));
     label.setTextFill(Color.WHITE);
     label.setVisible(true);
     label.setAlignment(Pos.TOP_LEFT);
     label.setPrefWidth(328);
     SetHandleMouseClickedMainController(label, label);
     
     int countEmployees = employees.size();
     Label label2 = new Label("C такой должностью работают: " + countEmployees);
     label2.setFont(Font.font("System",  FontPosture.ITALIC, 18));
     label2.setTextFill(Color.WHITE);
     label2.setVisible(true);
     label2.setAlignment(Pos.TOP_LEFT);
     label2.setPrefWidth(328);
     SetHandleMouseClickedMainController(label2, label);
       
     VBox VBoxLabel = new VBox();
     VBoxLabel.autosize();
     VBoxLabel.getChildren().add(label);
     VBoxLabel.getChildren().add(label2);
     VBoxLabel.setOnMouseEntered((MouseEvent event) -> {
                                    this.controller.mainApp.ToolTipDolzhnostiEmployeesHandleOnMouseExited();
                              }
     );
     VBoxLabel.setOnMouseExited((MouseEvent event) -> {
                                    this.controller.mainApp.ToolTipDolzhnostiEmployeesHandleOnMouseExited();
                              }
     );
   
     HBox HBox = new HBox();
     HBox.getChildren().add(VBoxLabel);
          if (countEmployees > 0) {
              VBox VBoxCircle = new VBox();
              VBoxCircle.autosize();
              int interperson = 5;
              int enlargement = 0;
                  while("1" == "1") {
                        if (countEmployees ==0)
                           {
                             break;
                           }
                        if (interperson > countEmployees)
                           {
                             interperson = countEmployees;
                             countEmployees = 0;
                           }
                        else{countEmployees = countEmployees - interperson;}
                        int tekinter = enlargement;
                        enlargement = enlargement + interperson;   
                        HBox HBoxCircle = new HBox();
                            while("1" == "1") {
                                  tekinter = tekinter + 1;
                                       if (tekinter > enlargement)
                                       {break;}
                                  Employees tekEmployees = employees.get(tekinter - 1);
                                  Image img = new Image(new ByteArrayInputStream(tekEmployees.getFoto()));
                                  Circle circle = new Circle();
                                  circle.setStroke(Color.web("#0fced9"));
                                  circle.setRadius(40);
                                  circle.setFill(new ImagePattern(img));
                                  circle.setCursor(Cursor.HAND);
                                  circle.setOnMouseEntered((MouseEvent event) -> {
                                        this.controller.mainApp.ToolTipDolzhnostiEmployeesHandleOnMouseEntered(event, tekEmployees.getFio());
                                         }
                                  );
                                  
                                  circle.setOnMouseClicked((MouseEvent event) -> {
                                    ControllerEmployees.Result result = null; 
                                    Subdivision subdivision = tekEmployees.getLinkSubdivision();
                                    result = this.controller.mainApp.AddEditEmployees(subdivision,  tekEmployees);
                                         if (result != null)
                                            {
                                              if (result.updateDb ==true)
                                                 {
                                                   this.controller.changeDataEmployees(result);
                                                 }
                                            }   
                                  });
                                  HBoxCircle.getChildren().add(circle);  
                                  Separator separator = new Separator();
                                  separator.setVisible(false);
                                  SetHandleMouseClickedMainController(separator, label);
                                  HBoxCircle.getChildren().add(separator);
                            }
                         VBoxCircle.getChildren().add(HBoxCircle); 
                         Separator separator = new Separator();
                         separator.setVisible(false);
                         SetHandleMouseClickedMainController(separator, label);
                         VBoxCircle.getChildren().add(separator); 
                  }
              HBox.getChildren().add(VBoxCircle);    
          }
         
     VBox VBox1 = new VBox();
     VBox1.autosize();
     VBox1.getChildren().add(HBox);     
     Separator separator = new Separator();
     SetHandleMouseClickedMainController(separator, label);        
     VBox1.getChildren().add(separator);  
     this.vbox = VBox1;
   }
}
