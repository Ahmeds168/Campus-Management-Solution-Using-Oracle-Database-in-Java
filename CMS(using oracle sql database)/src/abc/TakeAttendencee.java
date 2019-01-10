
package abc;


import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import javafx.scene.control.CheckBox;

public class TakeAttendencee extends Application {
String date = "";
    String tableName="";
    int i=0;
    TextField dateTextField = new TextField();
Button back = new Button ("Back");
Button createAttendenceBut = new Button("Create Attendence"); 
String courseID="";
    CheckBox checkBox[] = new CheckBox[50];
    Label label[] = new Label[50];
Button submitBut = new Button("Submit");
    public void start(Stage s){
        
        
        AnchorPane pane = new AnchorPane();
 File f = new File("src/abc/AdminStyle.css");
        pane.getStylesheets().add(f.toURI().toString());
        pane.getChildren().add(dateTextField);
        pane.getChildren().add(back);
        pane.getChildren().add(createAttendenceBut);
        pane.getChildren().add(submitBut);
        ///////////////
        back.setLayoutX(30);
        back.setLayoutY(30);
        
        dateTextField.setPromptText("Date");
        dateTextField.setFont(Font.font("calibiri", 20));
        dateTextField.setPrefSize(200, 40);
        dateTextField.setLayoutX(200);
        dateTextField.setLayoutY(30);
        
        createAttendenceBut.setLayoutX(500);
        createAttendenceBut.setLayoutY(30);
        
        submitBut.setLayoutX(900);
        submitBut.setLayoutY(500);
        /////////////
        ///// Event Handler
        back.setOnAction(new EventHandler<ActionEvent>(){
        public void handle(ActionEvent e){
            TeacherAccounttttt tA = new TeacherAccounttttt();
            Stage s109 = new Stage();
            
            try{tA.start(s109);}catch(ClassNotFoundException ex){}catch(SQLException exc){}
            
            
            s.hide();
            
            
        }
        
        
        });
        createAttendenceBut.setOnAction(new EventHandler<ActionEvent>(){
        public void handle(ActionEvent e){
           try{ Class.forName("oracle.jdbc.driver.OracleDriver");
        System.out.println("driver loaded");
        
Connection    con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.110:1521:xe","syedahmed","ahmeds168");
               System.out.println("Connnection established");
Statement stmt = con.createStatement();
String sql = "select name from course168 where cid='"+courseID+"'";
               System.out.println("executing query getting table name");
ResultSet rs = stmt.executeQuery(sql);

while(rs.next()){
    tableName = rs.getString("name");
    
    
}
tableName = tableName+"Attendence";
               System.out.println("got table name");
date = dateTextField.getText();
sql = "Alter Table '"+tableName+"' add '"+date+"' varchar2(10) default 'Present'";
               System.out.println("adding new column to attendence table");
stmt.executeQuery(sql);

sql = "select sid from student_course where cid='"+courseID+"'";

   int y=80;
   int x=50;
rs=stmt.executeQuery(sql);
               System.out.println(" retrieving students");
           
 

   //for (i=0; i<10; ++i){
   while(rs.next()){
   String cmsID= rs.getString("sid");
   
   String sql2 = "select name from student168 where cmsid='"+cmsID+"'";
   Statement innerStmt = con.createStatement();
   ResultSet rss = innerStmt.executeQuery(sql);
   while(rss.next()){
       String name = rss.getString("name");
       
       label[i]= new Label(name);
   checkBox[i] = new CheckBox("Present");
   checkBox[i].setSelected(true);
   checkBox[i].setScaleX(1.4);
   checkBox[i].setScaleY(1.4);
   pane.getChildren().add(label[i]);
   pane.getChildren().add(checkBox[i]);
   label[i].setFont(Font.font("calibiri", 20));
   label[i].setTextFill(Paint.valueOf("blue"));
   
   label[i].setLayoutX(x);
   label[i].setLayoutY(y);
   
   checkBox[i].setLayoutX(x+300);
   checkBox[i].setLayoutY(y);
   checkBox[i].setAccessibleHelp(cmsID);
   checkBox[i].setOnAction(new EventHandler<ActionEvent>(){
int z=i;
       public void handle(ActionEvent e){
       if(checkBox[z].isSelected()){
           System.out.println(checkBox[z].getAccessibleHelp()+" is present");
           checkBox[z].setAccessibleText("Present");
       }
       else{
           System.out.println(checkBox[z].getAccessibleHelp()+" is absent");
           checkBox[z].setAccessibleText("Absent");
       }
       
       
   }
   
  
   });
   
   y=y+40; 
       
   }
   
       
      
     //}
           }
   stmt.close();
   con.close();
           }
     
     catch(ClassNotFoundException ex){}catch(SQLException exc){}
        }
   
                
        });
        submitBut.setOnAction(new EventHandler<ActionEvent>(){
        public void handle(ActionEvent e ){
            int totalCheckBoxes=checkBox.length;
            int j=0;
         try{  Class.forName("oracle.jdbc.driver.OracleDriver");
        System.out.println("driver loaded");
        
Connection    con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.110:1521:xe","syedahmed","ahmeds168");
Statement stmt = con.createStatement();
                
            for(j=0; j<totalCheckBoxes; ++j){
                String cmsId = checkBox[j].getAccessibleHelp();
                String present_absent = checkBox[j].getAccessibleText();
            String sql = "Update '"+tableName+"' set  '"+date+"'  = '"+present_absent+"' where cmsID='"+cmsId+"' ";    
             stmt.executeQuery(sql);
             
                
            }
            stmt.close();
            con.close();
        }
            catch(ClassNotFoundException ex){}catch(SQLException exc){}
            
        }
        
        
        });
        
        
        /////
        
        Scene scene =new Scene(pane,1300,695);
        s.setScene(scene);
        s.setTitle("Take Attendence");
        s.show();
        
    }
    public static void main(String [] args){
        launch(args);
        
        
    }
}
