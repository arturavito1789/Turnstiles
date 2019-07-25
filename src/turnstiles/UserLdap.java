/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turnstiles;

import java.text.SimpleDateFormat;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

public class UserLdap {
    
private String samAccountName;// имя по ад
private String displayName;//имя по русски
private String mail;
ControllerUserLdap controller;
VBox vbox;

    public VBox getVbox() {
        return vbox;
    }

    public void setVbox(VBox vbox) {
        this.vbox = vbox;
    }

    public String getSamAccountName() {
        return samAccountName;
    }

    public void setSamAccountName(String samAccountName) {
        this.samAccountName = samAccountName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void FillInTheDisplayFields (ControllerUserLdap controller) {
       this.controller = controller;
       HBox hBox = new HBox();
       Label label = new Label(displayName);
       label.setFont(Font.font("System",  FontPosture.ITALIC, 12));
       label.setTextFill(Color.WHITE);
       label.setVisible(true);
       label.setAlignment(Pos.TOP_CENTER);
       label.setPrefWidth(210);
       SetHandleMouseClickedController(label, label);
       hBox.getChildren().add(label); 
       Separator separator = new Separator();
       separator.setVisible(false);
       hBox.getChildren().add(separator);
       Label label2 = new Label(samAccountName);
       label2.setFont(Font.font("System",  FontPosture.ITALIC, 12));
       label2.setTextFill(Color.WHITE);
       label2.setVisible(true);
       label2.setAlignment(Pos.TOP_CENTER);
       label2.setPrefWidth(210);
       SetHandleMouseClickedController(label2, label);
       hBox.getChildren().add(label2);
       Separator separator2 = new Separator();
       separator2.setVisible(false);
       hBox.getChildren().add(separator2);
       Label label3 = new Label(mail);
       label3.setFont(Font.font("System",  FontPosture.ITALIC, 12));
       label3.setTextFill(Color.WHITE);
       label3.setVisible(true);
       label3.setAlignment(Pos.TOP_CENTER);
       label3.setPrefWidth(210);
       SetHandleMouseClickedController(label3, label);
       hBox.getChildren().add(label3);
       VBox VBox1 = new VBox();
       VBox1.getChildren().add(hBox); 
       VBox1.autosize();
       this.vbox = VBox1;
    }
    
 
    public <T extends Control> void  SetHandleMouseClickedController(T Control, Label label) {
        Control.setOnMouseClicked((MouseEvent event) -> {
        controller.CurrentlabelHandleMouseClickedUserLdap(this, label);
       }); 
    } 
    
}
