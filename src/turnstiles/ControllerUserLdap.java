package turnstiles;

import java.net.URL;
import java.util.Collections;
import static java.util.Collections.list;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;

public class ControllerUserLdap implements Initializable{
    @FXML
    private TableView tableviewUserLdap;
    
    private Stage dialogStage;
    
    public ObservableList<UserLdap> dataUserLdap;
    
    private Label currentlabelUserLdap;
    public UserLdap currentUserLdap;
   

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
      TableColumn vbox = new TableColumn("vbox");      
      tableviewUserLdap.getColumns().addAll(vbox);
      vbox.setCellValueFactory(
              new PropertyValueFactory<UserLdap,String>("vbox")
             );  
      dataUserLdap = FXCollections.observableArrayList(); 
    }
    
    public void fillTableviewUserLdap() {
      Hashtable env = new Hashtable();
      env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
      env.put(Context.SECURITY_AUTHENTICATION, "simple");
      env.put(Context.PROVIDER_URL, "ldap://dc02:389");
      env.put(Context.SECURITY_PRINCIPAL, "turniket");
      env.put(Context.SECURITY_CREDENTIALS, "sdflksj45tR");
     //   env.put(Context.SECURITY_PRINCIPAL, "ArakelovAE@CORP.SMETIZ.RU");
     //   env.put(Context.SECURITY_CREDENTIALS, "Fktrcfylhf");
      
      try{
          InitialDirContext ldapContext = new InitialDirContext(env);
          SearchControls searchControls = new SearchControls();
          searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
          searchControls.setTimeLimit(30000);
          NamingEnumeration<javax.naming.directory.SearchResult> answer = ldapContext.search("OU=Компания Стройметиз,DC=corp,DC=smetiz,DC=ru", "(objectCategory=person)",  searchControls);
              while (answer.hasMore()) {
                    javax.naming.directory.SearchResult sr = answer.next();
                    Attributes attrs = sr.getAttributes();
                    UserLdap userLdap = new UserLdap();
                    Attribute displayName = attrs.get("displayName");
                    Attribute samAccountName = attrs.get("samAccountName");
                        if (displayName == null || samAccountName == null) continue;
                    NamingEnumeration<?> attrValuesEnum = displayName.getAll();
                         while (attrValuesEnum.hasMore()) {
                               userLdap.setDisplayName(attrValuesEnum.next().toString());
                         }
                    attrValuesEnum = samAccountName.getAll();
                         while (attrValuesEnum.hasMore()) {
                               userLdap.setSamAccountName(attrValuesEnum.next().toString());
                         }
                    Attribute mail = attrs.get("mail");     
                         if (mail != null)
                            { 
                              attrValuesEnum = mail.getAll();
                                  while (attrValuesEnum.hasMore()) {
                                        userLdap.setMail(attrValuesEnum.next().toString());
                                  }
                            }
                    userLdap.FillInTheDisplayFields(this);
                    dataUserLdap.add(userLdap);
                }
	}
        catch (Exception ex) {
            System.out.println(ex);
        }
      
      Collections.sort(dataUserLdap, comparator);
      tableviewUserLdap.setItems(dataUserLdap);  
      tableviewUserLdap.refresh();

    }
    
    
    Comparator<UserLdap> comparator = new Comparator<UserLdap>() {
      @Override
        public int compare(UserLdap o1, UserLdap o2) {
           
            return o1.getDisplayName().compareTo(o2.getDisplayName());
        }
    };
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    @FXML
    private void FontHandleAction(MouseEvent event) {
         this.dialogStage.close();
    }
    
    
     @FXML
    private void FormDataHandleAction(ActionEvent event) {
       if (currentUserLdap==null)
          {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Необходимо щелкнуть мышью по строке с данными");
            alert.setContentText("Необходимо щелкнуть мышью по строке с данными!");
            alert.showAndWait();
            return;
          }
        this.dialogStage.close();
    }
    
    
     public  void CurrentlabelHandleMouseClickedUserLdap (UserLdap userLdap, Label label1) {
    //Вызывается из классов Subdivision и dolzhnosti для его элементов управления встроенных в vbox
        if (currentlabelUserLdap != null) 
            {currentlabelUserLdap.setTextFill(Color.WHITE);}
              
        currentlabelUserLdap = label1; 
        currentlabelUserLdap.setTextFill(Color.web("#0fced9"));
        currentUserLdap = userLdap; 
     }
    
}
