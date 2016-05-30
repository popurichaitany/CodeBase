package Home;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.io.*;
import javax.swing.JTextField;

public class BrowsePath extends JFrame implements ActionListener
{
      static BrowsePath frame; 
      JButton button, button1,backBtn,analyseBtn,cancelBtn;
      JTextField field1,field2;
      //String path;
      JLabel inputLabel,outputLabel;
      public static String outpath,inputpath,className="BrowsePath",rootDir; 
      static   String field1text,field2text;
     static String a[]=new String[3];
     static String val="";
     

	public BrowsePath()
      {
            this.setLayout(null);
            //frame= new BrowsePath ();
            button = new JButton("Browse");
            
           // button = new JButton("Browse");
            
            field1 = new JTextField();
            field2=new JTextField();
            
            inputLabel=new JLabel("Input Directory:");
            outputLabel=new JLabel("Output Directory:");
            // field.setBounds(30, 50, 20, 25);
            
            this.add(field1);
            this.add(field2);
            this.add(button);//browse
           
            button.addActionListener(this);
            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
           
            inputLabel.setBounds(15, 50,100, 40);
            outputLabel.setBounds(15, 100,100, 40);
            
            field1.setBounds(135, 50, 250, 20);
            button.setBounds(400, 50, 100, 20);
            field2.setBounds(135,100,250,20);
            button1 = new JButton("Browse ");
            // button1.setBounds(240, 50, , 50);
            button1.setBounds(400, 100, 100, 20);
           
            this.add(button1);
      
            
            button1.addActionListener(this);
            
            backBtn = new JButton("Back");
            backBtn.addActionListener(this);
            
            analyseBtn = new JButton("Analyse Code");
            analyseBtn.addActionListener(this);
            
            cancelBtn = new JButton("Cancel");
            cancelBtn.addActionListener(this);
            
            backBtn.setBounds(45, 160, 100, 20);
            analyseBtn.setBounds(165, 160, 120, 20);
            cancelBtn.setBounds(300, 160, 100, 20);
            this.add(backBtn);
            this.add(analyseBtn);
            this.add(cancelBtn);
            this.add(inputLabel);
            this.add(outputLabel);
      
      }
	
	public static String getVal() {
		return val;
	}

	public static void setVal(String val) {
		BrowsePath.val = val;
	}

      public void actionPerformed(ActionEvent e) 
      {
            String src=e.getActionCommand();
          // System.out.println(e.getActionCommand().getClass());
            
            System.out.println("inside action performd");
            if(src.equals("Browse"))//for field1
            {
                 // Chooser frame = new Chooser();
                  JFileChooser chooser;
                  String fileName=null;
                  chooser = new JFileChooser();
               // chooser.setCurrentDirectory(new java.io.File("."));
               chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                  
                  chooser.setAcceptAllFileFilterUsed(false);
                  int r = chooser.showOpenDialog(new JFrame());
                  if (r == JFileChooser.APPROVE_OPTION)
                  {
                        fileName = chooser.getSelectedFile().getPath();
                  }
                  field1.setText(fileName);
                  field1text=field1.getText();
                 // field1.enable(false);
            }
            else if(src.equals("Browse "))
            {
            	if(field1text.equals(""))
            	{
            		JOptionPane.showMessageDialog(this.frame,"Please select the input Directory first and then try for browsing the Output");
            	}
                 // Chooser frame = new Chooser();
            	else
            	{
                  JFileChooser chooser;
                  String fileName=null;
                  chooser = new JFileChooser();
               // chooser.setCurrentDirectory(new java.io.File("."));
               chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                  
                  chooser.setAcceptAllFileFilterUsed(false);
                  int r = chooser.showOpenDialog(new JFrame());
                  if (r == JFileChooser.APPROVE_OPTION)
                  {
                        fileName = chooser.getSelectedFile().getPath();
                  }
                  field2.setText(fileName);
                //  field2.enable(false);
            	}
            }
            
            else if(src.equals("Analyse Code"))
            {
            	frame.setVisible(false);
           System.out.println("analysing..");
           XML_Formation xf=null;
           int nvgFlag=0;
           
				if(!(field1text==(""))&& !(field2text==("")))
            	{
            		inputpath=field1.getText();//main directory
            		val=new File(inputpath).getName();
            		System.out.println("inside field1 if");
            	}
            	else
            	{
            		JOptionPane.showMessageDialog(this.frame,"Please select the input and output directory and then click Submit");
            		nvgFlag=1;
            	} 
            	if(!(field2.getText().equals("")))
            	{
            		outpath=field2.getText();
            		
//                	/*if (((outpath.endsWith("\\"))))//if(outpath.endsWith("\\"))
//    				{
//                	//	outpath= outpath.substring(0, outpath.length() - 1);
//                		System.out.println("dkdf"+outpath);
//                		
//                		a[0]=outpath+"CodeCrawler_Reports.xml";
//                		a[1]="BrowsePath";
//                		System.out.println("******************"+outpath);
//                		
//    				}
//                	else
//                	{
//                		
//                		a[0]=outpath+"\\"+"CodeCrawler_Reports.xml";
//                		a[1]="BrowsePath";
//                		System.out.println("******************"+outpath);
//                		    // the \ is the last character, remove it
//                			
//                	}	*/
//            			System.out.println(a[0]);	
//                	 GUI1.main(a);
            	}
            	else
            	{
            		JOptionPane.showMessageDialog(this.frame,"Please select the output directory and then click Submit");
            		nvgFlag=1;
            	}
            	if(nvgFlag==0)
            	{
            		 xf=new XML_Formation(inputpath,outpath);
            		 if(xf.proceed==1)
                 	{
                 		JOptionPane.showMessageDialog(this.frame,"XML has been created Successfully at the location"+outpath+" ..");
                 		a[0]=outpath+"\\CodeCrawler_Reports.xml"; 
                 		a[1]=inputpath;
                 		a[2]=className; // the main root directory 
                 		new GUI1(a);
                 	}
                 	else
                 	{
                 		HomePage.run(new HomePage(),300,300); 
                 	}
            		//a[0]=outpath+"CodeCrawler_Reports.xml";
            	}
                 /* path=field1.getText();
                  System.out.println(path);
                  if(path.equals(""))
                  {
                        JOptionPane.showMessageDialog(this.frame,"Please select the path and then click Submit");

                  }*/
           //frame.setVisible(false);
            	
            	//Report1.main(a);
            	
            	//GUI1.main(null);        
                 
                
            }
            else if(src.equals("Back"))
            {
            	frame.setVisible(false);
            	HomePage.main(null);
            	
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
            frame = new BrowsePath ();
            frame.setSize(550, 300);
            frame.setLocation(200, 100);
            frame.setTitle("Browser to Generate XML");
            //frame.setSize(325,320);
            frame.setResizable(false);
            frame.setVisible(true);
      }
}

