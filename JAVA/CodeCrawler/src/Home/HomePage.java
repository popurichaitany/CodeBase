package Home;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;


public class HomePage extends JApplet implements ActionListener
{
      static JFrame frame; 
      static GUI1 frame1;
      String path;
      JLabel label;
      JButton cancelBtn,option1,option2;
      
     // JRadioButton option1,option2;

      public HomePage()
      { 
    	  this.setLayout(null);
    	
    	  System.out.println("inside Constructor");
    	   frame = new JFrame();
    	   
    	  // frame.setBackground(c);
    	   frame.setLocation(200, 100);
    	   //this.setLayout(new CardLayout());
    	   //frame.setTitle("Code CrawlerHome");
    	   label=new JLabel("WELCOME TO CODE CRAWLER...");
    	  // frame.setLayout(new FlowLayout());
    	   this.add(label);
    	
    	   label.setBounds(50, 20, 350, 25);
    	    
    	    
    	    cancelBtn=new JButton("Cancel");
    	   
    	    cancelBtn.setBounds(95,140,100,20);
    	   
    	     option1 = new JButton("Analyse Code");
    	     option2 = new JButton("Generate Report through XML");
    	   option1.setBounds(20, 60, 250, 25);
    	    
    	    
    	  option2.setBounds(20, 100, 250, 25);
    	   
    	    option1.addActionListener(this);
    	    option2.addActionListener(this);
    	   this. add(option1);
    	    this.add(option2);
    	    this.add(cancelBtn);//cancel
            cancelBtn.addActionListener(this);
    	  
    	   frame.show();

    	  
      }
      public static void main(String args[]) 
      {
    	  System.out.println("inside main");
    	 run(new  HomePage(), 300, 300);
           
      }
      public void actionPerformed(ActionEvent e) 
      {
    	  String src=e.getActionCommand();
    	  System.out.println("inside action Performed");

    	  if(src.equals("Analyse Code"))
    	  {
    		  frame.setVisible(false);
    		  
    		  BrowsePath.main(null);
    		  
    		  // Chooser frame = new Chooser();
    		  //field1.setText(frame.fileName);
    	  }
    	  else if(src.equals("Generate Report through XML"))
    	  {
    		  frame.setVisible(false);
    		  String arr[]={"",""};
    		  GUI1.main(arr);
    		  /*frame.setSize(300, 300);
               frame.setLocation(200, 1000);
                frame.setTitle("Code Crawler");
                //frame.setSize(325,320);
                frame.setResizable(false);
                frame.setVisible(true);*/
    	  }



    	  else if(src.equals("Cancel"))
    	  {

    		  int confirm = JOptionPane.showOptionDialog(frame,
    				  "Are You Sure to Close this Application?",
    				  "Exit Confirmation", JOptionPane.YES_NO_OPTION,
    				  JOptionPane.QUESTION_MESSAGE, null, null, null);
    		  if (confirm == JOptionPane.YES_OPTION) 
    		  {
    			  System.exit(1);
    		  }

    	  }
      }
      
      
      public static void run(JApplet applet, int width, int height)
      {
    	  System.out.println("inside run");
    	   // frame = new JFrame();
    	    frame.setTitle("Home");
    	    //frame.setLocation(200, 1000);
    	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	    frame.getContentPane().add(applet);
    	    frame.setSize(width, height);
    	    frame.setResizable(false);
    	    applet.init();
    	    applet.start();
    	    frame.setVisible(true);
    	  }
   
}



