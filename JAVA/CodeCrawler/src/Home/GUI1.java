package Home;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class GUI1 extends JFrame implements ActionListener
{
      static GUI1 frame; 
      JButton button,backBtn,submitBtn,cancelBtn;
      JTextField field1;
      String path;
	static String flag;
	static String inputPath;
      JLabel inputLabel;
      static String path1,path2,field1text,className="GUI1";
      static String a[]=new String[3];

      public GUI1(String h[])
      {
  
          System.out.println("Submitting xml..");
          a[0]=h[0];
          a[1]=h[1];
          a[2]=h[2];
//        a[0]=field1text;
//        a[1]="hello";  
//        a[2]=className;  

          Report1.main(a);
      }
      
      public GUI1()
      {
    	     
            this.setLayout(null);
            //frame= new BrowsePath ();
            //frame=(GUI1) new JFrame();
            button = new JButton("Browse");
            
           // button = new JButton("Browse");
            
            field1 = new JTextField();
            
            inputLabel=new JLabel("Input Directory:");
           
            // field.setBounds(30, 50, 20, 25);
            
            this.add(field1);
            
            this.add(button);//browse
           
            button.addActionListener(this);
            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
           
            inputLabel.setBounds(15, 50,100, 40);
           
            
            field1.setBounds(135, 50, 250, 20);
            button.setBounds(400, 50, 100, 20);
            
            backBtn = new JButton("Back");
            backBtn.addActionListener(this);
            submitBtn = new JButton("Submit");
            submitBtn.addActionListener(this);
            
            cancelBtn = new JButton("Cancel");
            cancelBtn.addActionListener(this);
            
            backBtn.setBounds(45, 160, 100, 20);
            submitBtn.setBounds(165, 160, 100, 20);
            cancelBtn.setBounds(285, 160, 100, 20);
            this.add(backBtn);
            this.add(submitBtn);
            this.add(cancelBtn);
            this.add(inputLabel);
           
           /* if(flag.equalsIgnoreCase("BrowsePath"))
            {
            	field1.setText(inputPath);
            	field1.enableInputMethods(false);
            	button.enable(false);
            }*/
      
      }

      public void actionPerformed(ActionEvent e) 
      {
            String src=e.getActionCommand();
           
           
            if(src.equals("Browse"))
            {
                 // Chooser frame = new Chooser();
                  JFileChooser chooser;
                  String fileName=null;
                  chooser = new JFileChooser();
               // chooser.setCurrentDirectory(new java.io.File("."));
             
                  
                  chooser.setAcceptAllFileFilterUsed(false);
                  int r = chooser.showOpenDialog(new JFrame());
                  if (r == JFileChooser.APPROVE_OPTION)
                  {
                        fileName = chooser.getSelectedFile().getPath();
                       
                  }
                  if(fileName.endsWith(".xml"))
                  {
                  field1.setText(fileName);
                  field1text=field1.getText();
                  }
                  else
                  {
                	  JOptionPane.showMessageDialog(this, "Please select XML file only");
                  }
            }
          
            
           else if(src.equals("Back"))
            {
        	   
               frame.setVisible(false);
            	HomePage.main(null);  	
            }
         
            
            else if(src.equals("Submit"))
            {
            		 
            	if(field1text.equals("") || !(field1text.contains("//"))) 
            	{
            		JOptionPane.showMessageDialog(this.frame,"Please select the input XML first and then try for browsing the Output");
            	}
                 // Chooser frame = new Chooser();
            	else
            	{
            		frame.setVisible(false);
		            System.out.println("Submitting xml..");
		            a[0]=field1text;
		            a[1]="hello";
		            a[2]=className;
		            Report1.main(a);
            	}  
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
      
      


 public static void main(String args[]) 
      {

            frame = new GUI1 ();
            
            frame.setSize(550, 300);
            frame.setLocation(200, 100);
            frame.setTitle("Browse an existing XML");
            //frame.setSize(325,320);
            frame.setResizable(false);
            frame.setVisible(true);
      }
}

class Chooser extends JFrame
{

      JFileChooser chooser;
      String fileName;

      public Chooser() 
      {
            chooser = new JFileChooser();
            //chooser.setCurrentDirectory(new java.io.File("."));
           //chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            
            chooser.setAcceptAllFileFilterUsed(false);
            int r = chooser.showOpenDialog(new JFrame());
            if (r == JFileChooser.APPROVE_OPTION)
            {
                  fileName = chooser.getSelectedFile().getPath();
            }
      }
}
