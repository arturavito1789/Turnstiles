/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnstiles;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.ByteArrayInputStream;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
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
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;
import org.hibernate.annotations.Where;


@Entity
@Table(name="Employees")
public class Employees implements Serializable {
  
    //первичный ключ состоит из двух полей. Одно из полей первичного ключа является внешним ключем.
    @EmbeddedId
    @AttributeOverrides({
          @AttributeOverride(name = "idEmployees", column = @Column(name = "idEmployees", nullable = false)),
          @AttributeOverride(name = "idSubdivision", column = @Column(name = "idSubdivision", nullable = false)) })
    private EmployeesPrimaryKey primaryKey;

    
    //@EmbeddedId
    //@AttributeOverrides({
    //      @AttributeOverride(name = "idEmployees", column = @Column(name = "idEmployees", nullable = false)),
    //    @AttributeOverride(name = "idSubdivision", column = @Column(name = "idSubdivision", nullable = false)) })
    // эти же параметры можно заполнить для свойства
    public EmployeesPrimaryKey getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(EmployeesPrimaryKey primaryKey) {
        this.primaryKey = primaryKey;
    }

   public void setNewValuePrimaryKey(int idEmployees, int idSubdivision) {
        this.primaryKey.setIdEmployees(idEmployees);
        this.primaryKey.setIdSubdivision(idSubdivision);
    }
    
    @Column(name = "fio")
    private String fio;// varchar(200) NOT NULL,
    @Column(name = "email")
    private String email;// varchar(100) NOT NULL,
    @Column(name = "notRelevant")
    private boolean notRelevant; //bit - уволен
    @Column(name="rukovoditel")
    private boolean rukovoditel;// bit, начальник отдела
    @Column(name = "headOrganization")
    private boolean headOrganization; //bit - руководитель организации
    @Column(name="phone")
    private String phone;// varchar(3) NOT NULL,
    @Column(name="domainUsername")
    private String domainUsername; //varchar(50),
    @Column(name="rightToEditInformation")
    private boolean rightToEditInformation; // bit,
    @Column(name="foto")
    private byte[] foto;
    @Column(name="countWorkingHours")
    private int countWorkingHours; 
    
    @OneToOne (fetch=FetchType.EAGER, cascade = CascadeType.REFRESH)//у сотрудника может быть только одна должность
    @JoinColumn(name = "nameDolzhnosti")//- имя поле в текущей таблице внешний ключ
    private Dolzhnosti dolzhnosti; //у сотрудника может быть только одна должность

     //@MapsId("idSubdivision") //  название поле ключа из таблицы подразделений
    @ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "idSubdivision", nullable = false, insertable = false, updatable = false)// - это название поле внешнего ключа в текущей таблице
    private Subdivision linkSubdivision; // когда hibernete - возврашает данные из базы данных это поле уже будет заполнено классом Subdivision 

    @OneToOne(fetch=FetchType.EAGER, mappedBy="employees", cascade = CascadeType.REFRESH)// указывается поле из класса а не из таблицы
    public CorrespondenceOfSurnames correspondenceOfSurnames;
    
    
    @Transient 
    VBox vbox;
    @Transient
    MainController controller;
    
    @Transient
    public CheckBox SelectedLoadCheckBox;
    @Transient
    ControllerDataTurnstilesLoading controllerDataTurnstilesLoading;
    @Transient
    public ObservableList<DataTurnstiles> dataTurnstiles;
    
    
    //@ManyToOne
    //@JoinColumn(name = "idSubdivision", nullable = false, insertable = false, updatable = false)
    public Subdivision getLinkSubdivision() {
        return linkSubdivision;
    }

   public void setLinkSubdivision(Subdivision linkSubdivision) {
        this.linkSubdivision = linkSubdivision;
    }
    
    public <T extends Control> void  SetHandleMouseClickedMainController(T Control, Label label1) {
       Control.setOnMouseClicked((MouseEvent event) -> {
                              this.controller.CurrentlabelHandleMouseClickedEmployees(this, label1);
                            }
            );  
   } 
    
     public <T extends Control> void  SetHandleMouseClickedControllerDataTurnstilesLoading(T Control, Label label1) {
       Control.setOnMouseClicked((MouseEvent event) -> {
                              this.controllerDataTurnstilesLoading.CurrentlabelHandleMouseClickedEmployees(this, label1);
                            }
            );  
   } 
    
   public void FillInTheDisplayFields (MainController controller) {
        this.controller = controller;
        VBox VBox1 = new VBox();
        VBox1.autosize();
       
        Label label1 = new Label(fio);
        label1.setFont(Font.font("System",  FontPosture.ITALIC, 18));
        label1.setTextFill(Color.WHITE);
        label1.setVisible(true);
        label1.setAlignment(Pos.TOP_LEFT);
        label1.setPrefWidth(428);
        Label label2 = new Label("Почта: " + email);
        label2.setFont(Font.font("System",  FontPosture.ITALIC, 18));
        label2.setTextFill(Color.WHITE);
        label2.setVisible(true);
        label2.setAlignment(Pos.TOP_LEFT);
        label2.setPrefWidth(428);
        Label label3 = new Label("Телефон: " + phone);
        label3.setFont(Font.font("System",  FontPosture.ITALIC, 18));
        label3.setTextFill(Color.WHITE);
        label3.setVisible(true);
        label3.setAlignment(Pos.TOP_LEFT);
        label3.setPrefWidth(428);
        Label label4 = new Label("Должность: " + dolzhnosti.getNameDolzhnosti());
        label4.setFont(Font.font("System",  FontPosture.ITALIC, 18));
        label4.setTextFill(Color.WHITE);
        label4.setVisible(true);
        label4.setAlignment(Pos.TOP_LEFT);
        label4.setPrefWidth(428);
        String currentState = "";
              if (notRelevant == true)
              {currentState = "Не актуален";}
              else
              {currentState = "Работает";}    
        Label label5 = new Label("Текущее состояние: " + currentState);
        label5.setFont(Font.font("System",  FontPosture.ITALIC, 18));
        label5.setTextFill(Color.WHITE);
        label5.setVisible(true);
        label5.setAlignment(Pos.TOP_LEFT);
        label5.setPrefWidth(428);
        SetHandleMouseClickedMainController(label1, label1);
        SetHandleMouseClickedMainController(label2, label1);
        SetHandleMouseClickedMainController(label3, label1);
        SetHandleMouseClickedMainController(label4, label1);
        SetHandleMouseClickedMainController(label5, label1);        
        
       
        HBox hbox = new HBox();
        Image img = new Image(new ByteArrayInputStream(foto));
        Circle circle = new Circle();
        circle.setStroke(Color.web("#0fced9"));
        circle.setRadius(50);
        circle.setFill(new ImagePattern(img));
        circle.setCursor(Cursor.HAND);
        circle.setOnMouseClicked((MouseEvent event) -> {
                                        this.controller.CurrentlabelHandleMouseClickedEmployees(this, label1);
                                   }
                  );
        hbox.getChildren().add(circle);
        Separator separator = new Separator();
        separator.setVisible(false);
        SetHandleMouseClickedMainController(separator, label1);
        hbox.getChildren().add(separator); 
        
        VBox VBoxLabel = new VBox();
        VBoxLabel.autosize();
        VBoxLabel.getChildren().add(label1);
        VBoxLabel.getChildren().add(label2);
        VBoxLabel.getChildren().add(label3);
        VBoxLabel.getChildren().add(label4);
        VBoxLabel.getChildren().add(label5);
        hbox.getChildren().add(VBoxLabel);
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
        Separator separator2 = new Separator();
        separator2.setVisible(false);
        SetHandleMouseClickedMainController(separator2, label1);
        hbox2.getChildren().add(separator2); 
        hbox2.getChildren().add(fontAwesomeIconAbsenceJournal);        
        VBox1.getChildren().add(hbox2);         
        Separator separator3 = new Separator();
        SetHandleMouseClickedMainController(separator3, label1);        
        VBox1.getChildren().add(separator3); 
        this.vbox = VBox1;
   }
   
   public void FillInTheDisplayFieldsDataTurnstilesLoading (ControllerDataTurnstilesLoading controller) {
     this.controllerDataTurnstilesLoading = controller;
     Label label1 = new Label(fio);
     label1.setFont(Font.font("System",  FontPosture.ITALIC, 14));
     label1.setTextFill(Color.WHITE);
     label1.setVisible(true);
     label1.setAlignment(Pos.TOP_LEFT);
     label1.setPrefWidth(428);
     SetHandleMouseClickedControllerDataTurnstilesLoading(label1, label1);
     SelectedLoadCheckBox = new CheckBox("Выбран для загрузки");
     SelectedLoadCheckBox.setFont(Font.font("System",  FontPosture.ITALIC, 14));
     SelectedLoadCheckBox.setTextFill(Color.WHITE);
     boolean thereIsNoDistributedData = false;
        for (Iterator<DataTurnstiles> it = dataTurnstiles.iterator(); it.hasNext();) {
             DataTurnstiles dayEmployeesTek = (DataTurnstiles) it.next();
                if (dayEmployeesTek.isThereIsNoDistributedData() == true)
                   {
                     thereIsNoDistributedData = true;
                     break;
                   }
        } 
        if (thereIsNoDistributedData == false) 
           {SelectedLoadCheckBox.setSelected(true);}
        
      SelectedLoadCheckBox.setOnMouseClicked((MouseEvent event) -> {
          this.controllerDataTurnstilesLoading.CurrentlabelHandleMouseClickedEmployees(this, label1);
          if (SelectedLoadCheckBox.isSelected() == true)
             {
               boolean thereError = false;
                   for (Iterator<DataTurnstiles> it = dataTurnstiles.iterator(); it.hasNext();) {
                        DataTurnstiles dayEmployeesTek = (DataTurnstiles) it.next();
                            if (dayEmployeesTek.isThereIsNoDistributedData() == true)
                               {
                                thereError = true;
                                break;
                               }
                        } 
                   if (thereError == true)
                      {
                        SelectedLoadCheckBox.setSelected(false);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Предупреждение");
                        alert.setHeaderText("Нельзя указывать выбран для загрузки.");
                        alert.setContentText("Нельзя указывать выбран для загрузки так как есть дни с ошибками (выделены красным цветом).");
                        alert.showAndWait(); 
                      }
             }
      });   
     VBox VBoxLabel = new VBox();
     VBoxLabel.autosize();
     VBoxLabel.getChildren().add(label1);
     VBoxLabel.getChildren().add(SelectedLoadCheckBox);  
     HBox hbox = new HBox(); 
     Image img = new Image(new ByteArrayInputStream(foto));
     Circle circle = new Circle();
     circle.setStroke(Color.web("#0fced9"));
     circle.setRadius(40);
     circle.setFill(new ImagePattern(img));
     circle.setCursor(Cursor.HAND); 
     circle.setOnMouseClicked((MouseEvent event) -> {
                            this.controllerDataTurnstilesLoading.CurrentlabelHandleMouseClickedEmployees(this, label1);
                            }
                  );
     hbox.getChildren().add(circle);
     Separator separator = new Separator();
     separator.setVisible(false);
     SetHandleMouseClickedControllerDataTurnstilesLoading(separator, label1);
     hbox.getChildren().add(separator);
     hbox.getChildren().add(VBoxLabel);
     VBox VBox1 = new VBox();
     VBox1.autosize();
     VBox1.getChildren().add(hbox); 
     Separator separator2 = new Separator();
     VBox1.getChildren().add(separator2);
     SetHandleMouseClickedControllerDataTurnstilesLoading(separator2, label1);
     this.vbox = VBox1;
   }

    public int getCountWorkingHours() {
        return countWorkingHours;
    }

    public void setCountWorkingHours(int countWorkingHours) {
        this.countWorkingHours = countWorkingHours;
    }

    public boolean isHeadOrganization() {
        return headOrganization;
    }

    public void setHeadOrganization(boolean headOrganization) {
        this.headOrganization = headOrganization;
    }
   
      
    public Dolzhnosti getDolzhnosti() {
        return dolzhnosti;
    }

    public void setDolzhnosti(Dolzhnosti dolzhnosti) {
        this.dolzhnosti = dolzhnosti;
    }
    
    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isNotRelevant() {
        return notRelevant;
    }

    public void setNotRelevant(boolean notRelevant) {
        this.notRelevant = notRelevant;
    }

  

    public boolean isRukovoditel() {
        return rukovoditel;
    }

    public void setRukovoditel(boolean rukovoditel) {
        this.rukovoditel = rukovoditel;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDomainUsername() {
        return domainUsername;
    }

    public void setDomainUsername(String domainUsername) {
        this.domainUsername = domainUsername;
    }


    public boolean isRightToEditInformation() {
        return rightToEditInformation;
    }

    public void setRightToEditInformation(boolean rightToEditInformation) {
        this.rightToEditInformation = rightToEditInformation;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public VBox getVbox() {
        return vbox;
    }

    public void setVbox(VBox vbox) {
        this.vbox = vbox;
    }
     
    public Employees() {
        dataTurnstiles = FXCollections.observableArrayList();    
    }

    
 
    
}



