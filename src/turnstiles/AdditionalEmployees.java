
package turnstiles;

import com.jfoenix.controls.JFXButton;
import java.io.ByteArrayInputStream;
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
import javafx.scene.text.TextAlignment;


public class AdditionalEmployees {
  
    VBox vbox; 
    Employees employees;
    ControllerAddDocuments1c controllerAddDocuments1c;
    ControllerFileTurnstilesLoading controllerFileTurnstilesLoading;
    //поля в 1с: _Description - наименование, _Code - код, _Fld1956 - дата увольнения, _Marked - признак пометки на удаления
    public ObservableList<Documents1cOf1C> documents1cOf1C;
    public CheckBox SelectedLoadCheckBox;
    public ObservableList<DataExcelDay> dataExcelDay;
    public ObservableList<DataExcelDay> dataExcelDayErr;
    private boolean errorDataExcelDay;
    private String fioNofindEmployees;
    public Label label1;
    public Label labelHotCountedDays;
    
    public AdditionalEmployees() {
      dataExcelDay = FXCollections.observableArrayList(); 
      dataExcelDayErr = FXCollections.observableArrayList();
    }

    public void addDataExcelDay(DataExcelDay dataExcelDay) {
        this.dataExcelDay.add(dataExcelDay);
    }
    
    public void addDataExcelDayErr(DataExcelDay dataExcelDay) {
        this.dataExcelDayErr.add(dataExcelDay);
    }

    public boolean isErrorDataExcelDay() {
        return errorDataExcelDay;
    }

    public void setErrorDataExcelDay(boolean errorDataExcelDay) {
        this.errorDataExcelDay = errorDataExcelDay;
    }


    public String getFioNofindEmployees() {
        return fioNofindEmployees;
    }

    public void setFioNofindEmployees(String fioNofindEmployees) {
        this.fioNofindEmployees = fioNofindEmployees;
    }

    public ObservableList<Documents1cOf1C> getDocuments1cOf1C() {
        return documents1cOf1C;
    }

    public void setDocuments1cOf1C() {
        this.documents1cOf1C =  FXCollections.observableArrayList(); ;
    }
  
    
    public VBox getVbox() {
        return vbox;
    }

    public void setVbox(VBox vbox) {
        this.vbox = vbox;
    }

    
    public <T extends Control> void  SetHandleMouseClickedControllerAddDocuments1c(T Control, Label label1) {
        Control.setOnMouseClicked((MouseEvent event) -> {
                              this.controllerAddDocuments1c.CurrentlabelHandleMouseClickedAdditionalEmployees(this, label1);
                            }
            );  
    } 
    
    public <T extends Control> void  SetHandleMouseClickedControllerFileTurnstilesLoading(T Control, Label label1) {
        Control.setOnMouseClicked((MouseEvent event) -> {
                              this.controllerFileTurnstilesLoading.CurrentlabelHandleMouseClickedAdditionalEmployees(this, label1);
                            }
            );  
    } 
    
    public void FillInTheDisplayFieldsAddDocuments1c (boolean find, ControllerAddDocuments1c controller) {
    
    this.controllerAddDocuments1c = controller;
    HBox hbox = new HBox();    
    Image img = new Image(new ByteArrayInputStream(employees.getFoto()));
    label1 = new Label(employees.getFio());
    label1.setFont(Font.font("System",  FontPosture.ITALIC, 18));
    label1.setTextFill(Color.WHITE);
    label1.setVisible(true);
    label1.setAlignment(Pos.TOP_LEFT);
    label1.setPrefWidth(428);
    SetHandleMouseClickedControllerAddDocuments1c(label1, label1);
    Circle circle = new Circle();
    circle.setStroke(Color.web("#0fced9"));
    circle.setRadius(40);
    circle.setFill(new ImagePattern(img));
    circle.setCursor(Cursor.HAND);
    circle.setOnMouseClicked((MouseEvent event) -> {
                                        this.controllerAddDocuments1c.CurrentlabelHandleMouseClickedAdditionalEmployees(this, label1);
                                   }
                  );
    hbox.getChildren().add(circle);
    Separator separator = new Separator();
    separator.setVisible(false);
    SetHandleMouseClickedControllerAddDocuments1c(separator, label1);
    hbox.getChildren().add(separator);
    VBox VBoxLabel = new VBox();
    VBoxLabel.autosize();
    VBoxLabel.getChildren().add(label1);
    SelectedLoadCheckBox = new CheckBox("Выбран для загрузки");
    SelectedLoadCheckBox.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    SelectedLoadCheckBox.setTextFill(Color.WHITE);
    SelectedLoadCheckBox.setOnMouseClicked((MouseEvent event) -> {
                                      this.controllerAddDocuments1c.CurrentlabelHandleMouseClickedAdditionalEmployees(this, label1);  
                                      this.controllerAddDocuments1c.selectDocuments1cOf1C(SelectedLoadCheckBox.isSelected(), this);
                                   }
                  );
        if (find == false)
           {
             Label label2 = new Label("Не найден сотрудник в 1с");
             label2.setFont(Font.font("System",  FontPosture.ITALIC, 14));
             label2.setTextFill(Color.RED);
             label2.setVisible(true);
             label2.setAlignment(Pos.TOP_LEFT);
             label2.setPrefWidth(428);
             SetHandleMouseClickedControllerAddDocuments1c(label2, label1);
             VBoxLabel.getChildren().add(label2);
             SelectedLoadCheckBox.setDisable(true);
           }
    VBoxLabel.getChildren().add(SelectedLoadCheckBox);
    hbox.getChildren().add(VBoxLabel);
    VBox VBox1 = new VBox();
    VBox1.autosize();
    VBox1.getChildren().add(hbox); 
    Separator separator2 = new Separator();
    SetHandleMouseClickedControllerAddDocuments1c(separator2, label1);
    VBox1.getChildren().add(separator2);
    this.vbox = VBox1;
   }

    public void FillInTheDisplayFieldsFileTurnstilesLoading (ControllerFileTurnstilesLoading controller) {
    
    this.controllerFileTurnstilesLoading = controller;
    HBox hbox = new HBox();   
    String fioStr ="";
        if(employees == null)
           {
             fioStr = fioNofindEmployees;
           }  
        else
           {
             fioStr = employees.getFio();
           }  
   
    label1 = new Label(fioStr);
    label1.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    label1.setTextFill(Color.WHITE);
    label1.setVisible(true);
    label1.setAlignment(Pos.TOP_LEFT);
    label1.setPrefWidth(428);
    SetHandleMouseClickedControllerFileTurnstilesLoading(label1, label1);
    VBox VBoxLabel = new VBox();
    VBoxLabel.autosize();
           if(employees == null)
           {
             JFXButton button = new JFXButton();  
             button.setText("Подобрать сотрудника:");
             button.setPrefWidth(428);
             button.setAlignment(Pos.TOP_LEFT);
             button.setTextAlignment(TextAlignment.LEFT);
             button.setFont(Font.font("System",  FontPosture.ITALIC, 14));
             button.setTextFill(Color.WHITE);
             button.setOnMouseClicked((MouseEvent event) -> {
                                            this.controllerFileTurnstilesLoading.changeEmployeesHandleMouseClickedAdditionalEmployees(this, label1);
                                    
                            }
                        );
             VBoxLabel.getChildren().add(button);
           }
    VBoxLabel.getChildren().add(label1);
        if(employees != null)
          { 
            Image img = new Image(new ByteArrayInputStream(employees.getFoto()));
            Circle circle = new Circle();
            circle.setStroke(Color.web("#0fced9"));
            circle.setRadius(40);
            circle.setFill(new ImagePattern(img));
            circle.setCursor(Cursor.HAND);
            circle.setOnMouseClicked((MouseEvent event) -> {
                                            this.controllerFileTurnstilesLoading.CurrentlabelHandleMouseClickedAdditionalEmployees(this, label1);
                                    }
                );
            hbox.getChildren().add(circle);
            Separator separator = new Separator();
            separator.setVisible(false);
            SetHandleMouseClickedControllerFileTurnstilesLoading(separator, label1);
            hbox.getChildren().add(separator);
          }
        else
          { 
            Label label2 = new Label("Не найден сотрудник в программе");
            label2.setFont(Font.font("System",  FontPosture.ITALIC, 14));
            label2.setTextFill(Color.RED);
            label2.setVisible(true);
            label2.setAlignment(Pos.TOP_LEFT);
            label2.setPrefWidth(428);
            SetHandleMouseClickedControllerFileTurnstilesLoading(label2, label1);
            VBoxLabel.getChildren().add(label2);
           }
         if (errorDataExcelDay == true)
           { 
            labelHotCountedDays = new Label("Есть не расчитанные дни");
            labelHotCountedDays.setFont(Font.font("System",  FontPosture.ITALIC, 14));
            labelHotCountedDays.setTextFill(Color.RED);
            labelHotCountedDays.setVisible(true);
            labelHotCountedDays.setAlignment(Pos.TOP_LEFT);
            labelHotCountedDays.setPrefWidth(428);
            SetHandleMouseClickedControllerFileTurnstilesLoading(labelHotCountedDays, label1);
            VBoxLabel.getChildren().add(labelHotCountedDays);
           }
    SelectedLoadCheckBox = new CheckBox("Выбран для загрузки");
    SelectedLoadCheckBox.setFont(Font.font("System",  FontPosture.ITALIC, 14));
    SelectedLoadCheckBox.setTextFill(Color.WHITE);
    SelectedLoadCheckBox.setOnMouseClicked((MouseEvent event) -> {
        this.controllerFileTurnstilesLoading.CurrentlabelHandleMouseClickedAdditionalEmployees(this, label1);
            if (SelectedLoadCheckBox.isSelected() == true && employees == null)
                {
                  SelectedLoadCheckBox.setSelected(false);
                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                  alert.setTitle("Предупреждение");
                  alert.setHeaderText("Нельзя указывать выбран для загрузки.");
                  alert.setContentText("Нельзя указывать выбран для загрузки так как нету сотрудника.");
                  alert.showAndWait(); 
                }
             if (SelectedLoadCheckBox.isSelected() == true && dataExcelDayErr.size() > 0)
                {
                  SelectedLoadCheckBox.setSelected(false);
                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                  alert.setTitle("Предупреждение");
                  alert.setHeaderText("Нельзя указывать выбран для загрузки.");
                  alert.setContentText("Нельзя указывать выбран для загрузки так как есть дни с ошибками.");
                  alert.showAndWait(); 
                }
        }
     );
        if (errorDataExcelDay == false && employees != null)
            { 
              SelectedLoadCheckBox.setSelected(true);                 
            }
    VBoxLabel.getChildren().add(SelectedLoadCheckBox);        
    hbox.getChildren().add(VBoxLabel);
    VBox VBox1 = new VBox();
    VBox1.autosize();
    VBox1.getChildren().add(hbox); 
    Separator separator2 = new Separator();
    SetHandleMouseClickedControllerFileTurnstilesLoading(separator2, label1);
    VBox1.getChildren().add(separator2);
    this.vbox = VBox1;
   }
    
    
    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    
}
