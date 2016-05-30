package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import Home.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import converte.DotToImageConverter;

public class ImageZoomerFrame extends JFrame implements MouseListener,
		ActionListener {

	private ImagePanel m_imagePanel;
	JPanel panel1,topPanel;
	private JScrollPane m_srollPane;
	private JPanel m_imageContainer;
	private JLabel m_zoomedInfo;
	private JButton m_zoomInButton;
	private JButton m_zoomOutButton;
	private JButton m_originalButton;
	private Cursor m_zoomCursor;
	JButton cancelBtn,saveBtn,report2Btn,report3Btn;
	JMenu File, Help;
	JMenuItem open, converte, Save, exit, How, Aboutus;
	JMenuBar editor;
	String FullPath;
	DotToImageConverter obj;
    JFrame h;
	public ImageZoomerFrame(Image image, double zoomPercentage, String imageName,JFrame j,DotToImageConverter o) {
		super("Image Zoomer [" + imageName + "]");

		if (image == null) {
			add(new JLabel("Image " + imageName + " not Found"));
		}

		{
			h=j;
			obj=o;
			this.setLayout(new BorderLayout());
			panel1=new JPanel();
			topPanel = new JPanel();
			
			this.add(panel1,BorderLayout.NORTH); 
			this.add(topPanel,BorderLayout.SOUTH);  
			
			m_zoomInButton = new JButton("Zoom In");
			m_zoomInButton.addActionListener(this);

			m_zoomOutButton = new JButton("Zoom Out");
			m_zoomOutButton.addActionListener(this);

			m_originalButton = new JButton("Original");
			m_originalButton.addActionListener(this);

			m_zoomedInfo = new JLabel("Zoomed to 100%");

			topPanel.add(new JLabel("Zoom Percentage is "
					+ (int) zoomPercentage + "%"));
			topPanel.add(m_zoomInButton);
			topPanel.add(m_originalButton);
			topPanel.add(m_zoomOutButton);
			topPanel.add(m_zoomedInfo);

			m_imagePanel = new ImagePanel(image, zoomPercentage);
			m_imagePanel.addMouseListener(this);

			m_imageContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
			m_imageContainer.setBackground(Color.BLACK);
			m_imageContainer.add(m_imagePanel);

			m_srollPane = new JScrollPane(m_imageContainer);
			m_srollPane.setAutoscrolls(true);

			getContentPane().add(BorderLayout.SOUTH, topPanel);
			getContentPane().add(BorderLayout.CENTER, m_srollPane);
//			getContentPane().add(
//					BorderLayout.SOUTH,
//					new JLabel("Left Click to Zoom In,"
//							+ " Right Click to Zoom Out", JLabel.CENTER));

			m_imagePanel.repaint();
			
			saveBtn=new JButton("Save Image");
			saveBtn.setBounds(120,0,150,20);
			saveBtn.addActionListener(this);
			panel1.add(saveBtn);
			
			report2Btn=new JButton("File Property");
			report2Btn.setBounds(290,0,170,20);
			report2Btn.addActionListener(this);
			panel1.add(report2Btn);
			
			report3Btn=new JButton("Control Flow Report");
			report3Btn.setBounds(480,0,170,20);
			report3Btn.addActionListener(this);
			panel1.add(report3Btn);
		}

		pack();
		setLocation(380, 100);
		setSize(700, 400);
		setVisible(true);
	}
	
	public ImageZoomerFrame(Image image, double zoomPercentage, String imageName) {
		super("Image Zoomer [" + imageName + "]");

		if (image == null) {
			add(new JLabel("Image " + imageName + " not Found"));
		}

		{

			JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

			m_zoomInButton = new JButton("Zoom In");
			m_zoomInButton.addActionListener(this);

			m_zoomOutButton = new JButton("Zoom Out");
			m_zoomOutButton.addActionListener(this);

			m_originalButton = new JButton("Original");
			m_originalButton.addActionListener(this);

			m_zoomedInfo = new JLabel("Zoomed to 100%");

			topPanel.add(new JLabel("Zoom Percentage is "
					+ (int) zoomPercentage + "%"));
			topPanel.add(m_zoomInButton);
			topPanel.add(m_originalButton);
			topPanel.add(m_zoomOutButton);
			topPanel.add(m_zoomedInfo);

			m_imagePanel = new ImagePanel(image, zoomPercentage);
			m_imagePanel.addMouseListener(this);

			m_imageContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
			m_imageContainer.setBackground(Color.BLACK);
			m_imageContainer.add(m_imagePanel);

			m_srollPane = new JScrollPane(m_imageContainer);
			m_srollPane.setAutoscrolls(true);

			getContentPane().add(BorderLayout.NORTH, topPanel);
			getContentPane().add(BorderLayout.CENTER, m_srollPane);
			getContentPane().add(
					BorderLayout.SOUTH,
					new JLabel("Left Click to Zoom In,"
							+ " Right Click to Zoom Out", JLabel.CENTER));

			m_imagePanel.repaint();
		}

		pack();
		setLocation(380, 100);
		setSize(700, 400);
		setVisible(true);
	}
	
	

	/**
	 * Action Listener method taking care of actions on the buttons
	 */
	public void actionPerformed(ActionEvent ae) {
		String str, CurrentDirectory;
		String src = (String) ae.getActionCommand();
		if (ae.getSource().equals(m_zoomInButton)) {
			m_imagePanel.zoomIn();
			adjustLayout();
		} else if (ae.getSource().equals(m_zoomOutButton)) {
			m_imagePanel.zoomOut();
			adjustLayout();
		} else if (ae.getSource().equals(m_originalButton)) {
			m_imagePanel.originalSize();
			adjustLayout();
		}else if (ae.getSource().equals(report2Btn)) {
			h.setVisible(true);
			this.setVisible(false); 
			
		}else if (ae.getSource().equals(report3Btn)) {
			String t[]={Report1.xlf.getAbsolutePath()}; 
			ControlFlowReport.main(t);
			this.setVisible(false); 
			
		}else if (ae.getSource().equals(saveBtn)){ 
			

//					str = new File("currentdirectory").getAbsolutePath();
//					Integer index = str.indexOf("currentdirectory");
//					CurrentDirectory = str.substring(0, index);
//					
//					String ext = new String("jpg");
//					BufferedImage src1=null;
//					try
//					{
//					 src1= ImageIO.read(new File(CurrentDirectory
//							+ obj.outputFile));
//					}catch(IOException ioe)
//					{
//						
//					}
//
//					File file = new File(new File(Report1.intr_bk_path).getParent()+"\\"+obj.outputFile); 
//					String apnd=new File(Report1.intr_bk_path).getParent()+"\\"+obj.outputFile;
//					JOptionPane.showMessageDialog(this,"Class diagram saved as:"+apnd);
//					try {
//						ImageIO.write(src1, ext, file);
//					} catch (IOException e1) {
//						System.out.println("Write error for " + file.getPath()
//								+ ": " + e1.getMessage());
//					}

			JFileChooser chooser = new JFileChooser();
			chooser.setMultiSelectionEnabled(false);
			chooser.setFileFilter(new TextFileFilter1());
			chooser.setAcceptAllFileFilterUsed(false);
			int retVal = chooser.showSaveDialog(this);

			try {
				if (retVal == JFileChooser.APPROVE_OPTION) {

					str = new File("currentdirectory").getAbsolutePath();
					Integer index = str.indexOf("currentdirectory");
					CurrentDirectory = str.substring(0, index);

					String ext = new String("jpg");

					BufferedImage src1 = ImageIO.read(new File(CurrentDirectory
							+ obj.outputFile));

					String desc = chooser.getSelectedFile().getAbsolutePath()
							.toString();
					File file = new File(desc);
					System.out.print(desc);
					try {
						ImageIO.write(src1, ext, file);
					} catch (IOException e1) {
						System.out.println("Write error for " + file.getPath()
								+ ": " + e1.getMessage());
					}

				}

			} catch (Exception exp) {
				JOptionPane.showMessageDialog(this, exp);
			}

				
			
		}
		

	}

	/**
	 * This method takes the Zoom Cursor Image and creates the Zoom Custom
	 * Cursor which is shown on the Image Panel on mouse over
	 */
	public void setZoomCursorImage(Image zoomcursorImage) {
		m_zoomCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				zoomcursorImage, new Point(0, 0), "ZoomCursor");
	}

	/**
	 * This method adjusts the layout after zooming
	 */
	private void adjustLayout() {
		m_imageContainer.doLayout();
		m_srollPane.doLayout();

		m_zoomedInfo.setText("Zoomed to " + (int) m_imagePanel.getZoomedTo()
				+ "%");
	}

	/**
	 * This method handles mouse clicks
	 */

	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			m_imagePanel.zoomIn();
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			m_imagePanel.zoomOut();
		}

		adjustLayout();
	}

	public void mouseEntered(MouseEvent e) {
		m_imageContainer.setCursor(m_zoomCursor);
	}

	public void mouseExited(MouseEvent e) {
		m_imageContainer.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	/**
	 * This class is the Image Panel where the image is drawn and scaled.
	 * 
	 * @author Rahul Sapkal(rahul@javareference.com)
	 */
	public class ImagePanel extends JPanel {
		private double m_zoom = 1.0;
		private double m_zoomPercentage;
		private Image m_image;

		/**
		 * Constructor
		 * 
		 * @param image
		 * @param zoomPercentage
		 */
		public ImagePanel(Image image, double zoomPercentage) {
			m_image = image;
			m_zoomPercentage = zoomPercentage / 100;
		}

		/**
		 * This method is overridden to draw the image and scale the graphics
		 * accordingly
		 */
		public void paintComponent(Graphics grp) {
			Graphics2D g2D = (Graphics2D) grp;

			// set the background color to white
			g2D.setColor(Color.WHITE);
			// fill the rect
			g2D.fillRect(0, 0, getWidth(), getHeight());

			// scale the graphics to get the zoom effect
			g2D.scale(m_zoom, m_zoom);

			// draw the image
			g2D.drawImage(m_image, 0, 0, this);
		}

		/**
		 * This method is overridden to return the preferred size which will be
		 * the width and height of the image plus the zoomed width width and
		 * height. while zooming out the zoomed width and height is negative
		 */

		public Dimension getPreferredSize() {
			return new Dimension(
					(int) (m_image.getWidth(this) + (m_image.getWidth(this) * (m_zoom - 1))),
					(int) (m_image.getHeight(this) + (m_image.getHeight(this) * (m_zoom - 1))));
		}

		/**
		 * Sets the new zoomed percentage
		 */

		public void setZoomPercentage(int zoomPercentage) {
			m_zoomPercentage = ((double) zoomPercentage) / 100;
		}

		/**
		 * This method set the image to the original size by setting the zoom
		 * factor to 1. i.e. 100%
		 */

		public void originalSize() {
			m_zoom = 1;
		}

		/**
		 * This method increments the zoom factor with the zoom percentage, to
		 * create the zoom in effect
		 */

		public void zoomIn() {
			m_zoom += m_zoomPercentage;
		}

		/**
		 * This method decrements the zoom factor with the zoom percentage, to
		 * create the zoom out effect
		 */

		public void zoomOut() {
			m_zoom -= m_zoomPercentage;

			if (m_zoom < m_zoomPercentage) {
				if (m_zoomPercentage > 1.0) {
					m_zoom = 1.0;
				} else {
					zoomIn();
				}
			}
		}

		/**
		 * This method returns the currently zoomed percentage
		 */

		public double getZoomedTo() {
			return m_zoom * 100;
		}
	}
}

class TextFileFilter extends javax.swing.filechooser.FileFilter {
	public boolean accept(File f) {
		return f.getName().toLowerCase().endsWith(".java") || f.isDirectory();
	}

	public String getDescription() {
		return "Java File";
	}
}

class TextFileFilter1 extends javax.swing.filechooser.FileFilter {
	public boolean accept(File f) {

		if (f.isDirectory()) {
			return true;
		} else {
			String fileExtn = null;
			String fname = f.getName();
			int i = fname.lastIndexOf('.');
			if (i > 0) {
				fileExtn = fname.substring(i + 1).toLowerCase();
				if (fileExtn.equals("jpg") || fileExtn.equals("jpeg")
						|| fileExtn.equals("bmp") || fileExtn.equals("png")
						|| fileExtn.equals("gif") || fileExtn.equals("tif")
						|| fileExtn.equals("tiff")) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}

	public String getDescription() {
		return "Image Files";
	}
}

