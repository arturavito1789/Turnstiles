/*
создаем бд и создаем в ней все нужные таблицы


 */
package turnstiles;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

public class ProgramsSetup implements Initializable{
    
    private Turnstiles mainApp;
    
    @FXML
    private TextField username;
    
    @FXML
    private TextField password;
    
    @FXML
    private TextField servername;
    
    @FXML
    private TextField port;
    
    @FXML
    private TextArea errorMessageConnection;
    
    @FXML
    private FontAwesomeIcon font_close;
    
    @FXML
    private FontAwesomeIcon font_to_curtail;

    public void setMainApp(Turnstiles mainApp) {
        this.mainApp = mainApp;
        Tooltip servernameTooltip = new Tooltip("servername");
        Tooltip usernameTooltip = new Tooltip("username");
        Tooltip passwordTooltip = new Tooltip("password");
        Tooltip portTooltip = new Tooltip("port");
        
        servername.setTooltip(servernameTooltip);
        username.setTooltip(usernameTooltip);
        password.setTooltip(passwordTooltip);
        port.setTooltip(portTooltip);
        
            if ("test".equals(this.mainApp.errorMessageConnection))
                {
                 //параметры подключения не были указны
                 errorMessageConnection.setText("Необходимо указать параметры подключения");
                }
            else
                {
                  errorMessageConnection.setText(this.mainApp.errorMessageConnection);
                  username.setText(this.mainApp.usernameConnection);
                  password.setText(this.mainApp.passwordConnection);
                  servername.setText(this.mainApp.servernameConnection);
                  port.setText(this.mainApp.portConnection);
                }
     
    }
     
      
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

        
    }    
    
@FXML
    private void FontHandleAction(MouseEvent event) {
            if(event.getSource() == font_close)               
               System.exit(0);
            else if(event.getSource() == font_to_curtail)        
               mainApp.stageSetIconified();
    }
    
  
    @FXML
    private void LoginHandleAction(ActionEvent event) {
         
        boolean thereAreNullValues = false;
        errorMessageConnection.setText("");
        String strservername = servername.getText();
        String strport = port.getText();
        String strusername = username.getText();
        String strpassword = password.getText();
        
                if ("".equals(strservername))
                   { 
                     thereAreNullValues = true;
                     errorMessageConnection.appendText("Укажите имя сервера \n");
                   }
                if ("".equals(strport))
                   { 
                     thereAreNullValues = true;
                     errorMessageConnection.appendText("Укажите номер порта \n");
                   }
                if ("".equals(strusername))
                   { 
                     thereAreNullValues = true;
                     errorMessageConnection.appendText("Укажите имя пользователя \n");
                   }
                if ("".equals(strpassword))
                   { 
                     thereAreNullValues = true;
                     errorMessageConnection.appendText("Укажите пароль \n");
                   }
                if (thereAreNullValues == true)
                   {return;}
        // записываем файл свойств
         FileInputStream fis;
         Properties property = new Properties();
                try {
                      fis = new FileInputStream("src/turnstiles/Hibernate.properties");
                            try {
                                 property.load(fis);
                                 fis.close();
                                 property.setProperty("hibernate.connection.username", strusername);
                                 property.setProperty("hibernate.connection.password", strpassword);
                                 property.setProperty("hibernate.connection.url", "jdbc:sqlserver://"+strservername + ":" + strport + ";databaseName=turnstiles");
                                 PrintWriter writerf = new PrintWriter("src/turnstiles/Hibernate.properties"); 
                                      for (Enumeration e = property.propertyNames(); e.hasMoreElements();) {
                                           String key = (String) e.nextElement();
                                           writerf.println(key + "=" + property.getProperty(key));
                                           }
                                  writerf.close();
                                  this.mainApp.connectDB(property);
                                  errorMessageConnection.setText(this.mainApp.errorMessageConnection);                                  
                                 }
                             catch (IOException ex) {
                                 }
                    } 
                catch (FileNotFoundException ex) {
                    }
         
                if ("".equals(errorMessageConnection.getText()))
                    {
                       try {             
                            Turnstiles.nameTekMaket = "Main.fxml";
                            mainApp.displayTheCurrentScene();
                           }
                       catch (Exception ex) {
                           }
                    }
                
         }
    
    
}
