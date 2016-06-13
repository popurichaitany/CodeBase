/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Index;

import JSONparser.BusinessObjparser;
import JSONparser.userobjparser;
import DbManip.JDBCconn;
import Model.userobject;
import JSONparser.reviewsobjparser;
import Model.BusinessObject;
import Model.reviewobject;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author anand
 */
public class Main extends javax.swing.JFrame{
    /**
     * Creates new form Main
     */
     public static ArrayList<String> subCatList = new ArrayList<>();
     public static ArrayList<String> AttributeList = new ArrayList<>();
     public static ArrayList<BusinessObject> rtvDb = new ArrayList<>();
     public static ArrayList<String> maincat = new ArrayList<>(); 
     ArrayList<String> categoList = new ArrayList<>();
     static final int db_business_count=20544;
     static final int db_review_count=822898;
     static final int db_user_count=211002; 
     static String mainCategory="",day="",criteria="",zip="",city="",state="";
     static BusinessObject Business=null;
     
    userInputJFrame frame; 
    JPanel dPanel = new JPanel();
    JScrollPane jsp =new JScrollPane(dPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
    final userInputJFrame frame2; 
    final JPanel dPanel2 = new JPanel();
    final JScrollPane jsp2 =new JScrollPane(dPanel2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
    public Main() {
        initComponents();
        frame = new userInputJFrame();
        frame2 = new userInputJFrame();
        categoList.add("Active Life");
        categoList.add("Arts & Entertainment");
        categoList.add("Automotive"); 
        categoList.add("Car Rental");
        categoList.add("Cafes");
        categoList.add("Beauty & Spas");
        categoList.add("Convenience Stores");
        categoList.add("Dentists");
        categoList.add("Doctors");
        categoList.add("Drugstores");
        categoList.add("Department Stores");
        categoList.add("Education");
        categoList.add("Event Planning & Services");
        categoList.add("Flowers & Gifts");
        categoList.add("Food");
        categoList.add("Health & Medical");
        categoList.add("Home Services");
        categoList.add("Home & Garden");
        categoList.add("Hospitals");
        categoList.add("Hotels & Travel");
        categoList.add("Hardware Stores");
        categoList.add("Grocery");
        categoList.add("Medical Centers");
        categoList.add("Nurseries & Gardening");
        categoList.add("Nightlife");
        categoList.add("Restaurants");
        categoList.add("Shopping");
        categoList.add("Transportation");
        jLabel1.setText("Sub Category:[None]");
        jLabel2.setText("Attributes:[None]"); 
        

        choice5.addItem(" "); 
        choice5.addItem("Sunday"); 
        choice5.addItem("Monday"); 
        choice5.addItem("Tuesday"); 
        choice5.addItem("Wednesday");
        choice5.addItem("Thursday");
        choice5.addItem("Friday");
        choice5.addItem("Saturday");
        
        choice6.addItem(" ");
        choice6.addItem("1:00");
        choice6.addItem("2:00");
        choice6.addItem("3:00");
        choice6.addItem("4:00");
        choice6.addItem("5:00");
        choice6.addItem("6:00");
        choice6.addItem("7:00");
        choice6.addItem("8:00");
        choice6.addItem("9:00");
        choice6.addItem("10:00");
        choice6.addItem("11:00");
        choice6.addItem("12:00");
        choice6.addItem("13:00");
        choice6.addItem("14:00");
        choice6.addItem("15:00");
        choice6.addItem("16:00");
        choice6.addItem("17:00");
        choice6.addItem("18:00");
        choice6.addItem("19:00");
        choice6.addItem("20:00");
        choice6.addItem("21:00");
        choice6.addItem("22:00");
        choice6.addItem("23:00");
        choice6.addItem("00:00");
        
        
        choice7.addItem(" "); 
        choice7.addItem("1:00");
        choice7.addItem("2:00");
        choice7.addItem("3:00");
        choice7.addItem("4:00");
        choice7.addItem("5:00");
        choice7.addItem("6:00");
        choice7.addItem("7:00");
        choice7.addItem("8:00");
        choice7.addItem("9:00");
        choice7.addItem("10:00");
        choice7.addItem("11:00");
        choice7.addItem("12:00");
        choice7.addItem("13:00");
        choice7.addItem("14:00");
        choice7.addItem("15:00");
        choice7.addItem("16:00");
        choice7.addItem("17:00");
        choice7.addItem("18:00");
        choice7.addItem("19:00");
        choice7.addItem("20:00");
        choice7.addItem("21:00");
        choice7.addItem("22:00");
        choice7.addItem("23:00");
        choice7.addItem("00:00");
        
        choice8.addItem(" "); 
        choice8.addItem("All");
        choice8.addItem("Any Attribute");
        choice8.addItem("City");
        choice8.addItem("State");
        choice8.addItem("Zip");
        choice8.addItem("Days of Week"); 
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        checkbox22 = new java.awt.Checkbox();
        jPanel1 = new javax.swing.JPanel();
        checkbox1 = new java.awt.Checkbox();
        checkbox2 = new java.awt.Checkbox();
        checkbox3 = new java.awt.Checkbox();
        checkbox4 = new java.awt.Checkbox();
        checkbox5 = new java.awt.Checkbox();
        checkbox6 = new java.awt.Checkbox();
        checkbox7 = new java.awt.Checkbox();
        checkbox8 = new java.awt.Checkbox();
        checkbox9 = new java.awt.Checkbox();
        checkbox10 = new java.awt.Checkbox();
        checkbox11 = new java.awt.Checkbox();
        checkbox12 = new java.awt.Checkbox();
        checkbox13 = new java.awt.Checkbox();
        checkbox14 = new java.awt.Checkbox();
        checkbox15 = new java.awt.Checkbox();
        checkbox16 = new java.awt.Checkbox();
        checkbox17 = new java.awt.Checkbox();
        checkbox18 = new java.awt.Checkbox();
        checkbox19 = new java.awt.Checkbox();
        checkbox20 = new java.awt.Checkbox();
        checkbox21 = new java.awt.Checkbox();
        checkbox23 = new java.awt.Checkbox();
        checkbox24 = new java.awt.Checkbox();
        checkbox25 = new java.awt.Checkbox();
        checkbox26 = new java.awt.Checkbox();
        checkbox27 = new java.awt.Checkbox();
        checkbox28 = new java.awt.Checkbox();
        checkbox29 = new java.awt.Checkbox();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        choice5 = new java.awt.Choice();
        choice6 = new java.awt.Choice();
        choice7 = new java.awt.Choice();
        choice8 = new java.awt.Choice();
        button3 = new java.awt.Button();
        button4 = new java.awt.Button();
        button1 = new java.awt.Button();
        label1 = new java.awt.Label();
        label2 = new java.awt.Label();
        label3 = new java.awt.Label();
        label4 = new java.awt.Label();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        checkbox22.setLabel("checkbox22");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 153, 255));

        jPanel1.setBackground(new java.awt.Color(255, 204, 51));
        jPanel1.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel1.setAutoscrolls(true);

        checkbox1.setLabel("Active Life");
        checkbox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox1ItemStateChanged(evt);
            }
        });

        checkbox2.setLabel("Arts & Entertainment");
        checkbox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox2ItemStateChanged(evt);
            }
        });

        checkbox3.setLabel("Automotive");
        checkbox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox3ItemStateChanged(evt);
            }
        });

        checkbox4.setLabel("Car Rental");
        checkbox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox4ItemStateChanged(evt);
            }
        });

        checkbox5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        checkbox5.setLabel("Beauty & Spas");
        checkbox5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox5ItemStateChanged(evt);
            }
        });

        checkbox6.setLabel("Cafes");
        checkbox6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox6ItemStateChanged(evt);
            }
        });

        checkbox7.setLabel("Convenience Stores");
        checkbox7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox7ItemStateChanged(evt);
            }
        });

        checkbox8.setLabel("Dentists");
        checkbox8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox8ItemStateChanged(evt);
            }
        });

        checkbox9.setLabel("Doctors");
        checkbox9.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox9ItemStateChanged(evt);
            }
        });

        checkbox10.setLabel("Drugstores");
        checkbox10.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox10ItemStateChanged(evt);
            }
        });

        checkbox11.setLabel("Department Stores");
        checkbox11.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox11ItemStateChanged(evt);
            }
        });

        checkbox12.setLabel("Education");
        checkbox12.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox12ItemStateChanged(evt);
            }
        });

        checkbox13.setLabel("Event Planning & Services");
        checkbox13.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox13ItemStateChanged(evt);
            }
        });

        checkbox14.setLabel("Flowers & Gifts");
        checkbox14.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox14ItemStateChanged(evt);
            }
        });

        checkbox15.setLabel("Food");
        checkbox15.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox15ItemStateChanged(evt);
            }
        });

        checkbox16.setLabel("Health & Medical");
        checkbox16.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox16ItemStateChanged(evt);
            }
        });

        checkbox17.setLabel("Home Services");
        checkbox17.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox17ItemStateChanged(evt);
            }
        });

        checkbox18.setLabel("Home & Garden");
        checkbox18.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox18ItemStateChanged(evt);
            }
        });

        checkbox19.setLabel("Hospitals");
        checkbox19.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox19ItemStateChanged(evt);
            }
        });

        checkbox20.setLabel("Hotels & Travels");
        checkbox20.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox20ItemStateChanged(evt);
            }
        });

        checkbox21.setLabel("Hardware Stores");
        checkbox21.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox21ItemStateChanged(evt);
            }
        });

        checkbox23.setLabel("Grocery");
        checkbox23.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox23ItemStateChanged(evt);
            }
        });

        checkbox24.setLabel(" Medical Centers");
        checkbox24.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox24ItemStateChanged(evt);
            }
        });

        checkbox25.setLabel("Nurseries & Gardening");
        checkbox25.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox25ItemStateChanged(evt);
            }
        });

        checkbox26.setLabel("Nihghtlife");
        checkbox26.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox26ItemStateChanged(evt);
            }
        });

        checkbox27.setLabel("Restaurants");
        checkbox27.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox27ItemStateChanged(evt);
            }
        });

        checkbox28.setLabel("Shopping");
        checkbox28.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox28ItemStateChanged(evt);
            }
        });

        checkbox29.setLabel("Transportation");
        checkbox29.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkbox29ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkbox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(102, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkbox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Business", "City", "State", "Stars"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setColumnSelectionAllowed(true);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setHeaderValue("Business");
            jTable1.getColumnModel().getColumn(1).setHeaderValue("City");
            jTable1.getColumnModel().getColumn(2).setHeaderValue("State");
            jTable1.getColumnModel().getColumn(3).setHeaderValue("Stars");
        }

        choice5.setName(""); // NOI18N
        choice5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                choice5ItemStateChanged(evt);
            }
        });

        choice8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                choice8ItemStateChanged(evt);
            }
        });

        button3.setLabel("SEARCH");
        button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button3ActionPerformed(evt);
            }
        });

        button4.setLabel("CLOSE");
        button4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button4ActionPerformed(evt);
            }
        });

        button1.setLabel("START");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        label1.setText("Day of the Week");

        label2.setText("From");

        label3.setText("To");

        label4.setText("Search For");

        jLabel1.setText("jLabel1");

        jLabel2.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(choice5, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(choice6, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(choice7, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(choice8, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(36, 36, 36)
                                        .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(27, 27, 27)
                                        .addComponent(button4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(54, 54, 54))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(771, 771, 771))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(choice5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(choice6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(choice7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(choice8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(button1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 862, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button3ActionPerformed
        // TODO add your handling code here:
        String resultSubCat="",resultAttr="",rtvBObj="", resultmaincat=""; 
        JDBCconn jdbc = new JDBCconn();
        jdbc.connect();
        PreparedStatement psSelBObj;
        ResultSet rsBObj;
        
        
        if(criteria.equals("")){
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Please select search criteria first", "Error", em);
            return;
        }
        
        if(criteria.equals("All"))
        {
            
            if((maincat.isEmpty()))
            {
            
                int em = JOptionPane.ERROR_MESSAGE;
                JOptionPane.showMessageDialog (null, "Please select category first", "Error", em);
                return;
            }
        }
    
            
        if(subCatList.size()>0){
            Iterator itrSub = subCatList.iterator();
            while(itrSub.hasNext()){
                if(resultSubCat.equals(""))
                    resultSubCat+=itrSub.next();
                else
                    resultSubCat+="'"+" OR subcat.subcategoryname ='"+itrSub.next();
            }
        }
        
        if(AttributeList.size()>0){
            Iterator itrAttr = AttributeList.iterator();
            while(itrAttr.hasNext()){
                if(resultAttr.equals(""))
                    resultAttr+=itrAttr.next();
                else
                    resultAttr+="'"+" OR attr.attribut ='"+itrAttr.next();
            }
        }
    try{    
        if(criteria.equals("All")){
            String resultMainCat="";
        if(maincat.size()>0){
                                
                                Iterator itrMain = maincat.iterator();
                                while(itrMain.hasNext()){
                                    if(resultMainCat.equals(""))
                                        resultMainCat+=itrMain.next();
                                    else
                                        resultMainCat+="'"+" OR cat.categoryname ='"+itrMain.next();
                                }
        }
            choice8.select(" "); 
             if(resultSubCat.equals("") && !resultAttr.equals("") && day.equals("")){
                rtvBObj = "select distinct obj.BusinessID,obj.bname,obj.city,obj.state,obj.stars from businessobject1 obj,bcategory cat, battribute attr " +
                "where obj.BusinessID = cat.businessid " +
                "and obj.BusinessID = attr.businessid " +
                        "and (cat.categoryname = '"+resultMainCat+"')" +
               // "and cat.categoryname = '"+mainCategory+"' " +
                "and (attr.attribut= '"+resultAttr+"')";
            } else if(!resultSubCat.equals("") && resultAttr.equals("") && day.equals("")){
                rtvBObj = "select distinct obj.BusinessID,obj.bname,obj.city,obj.state,obj.stars from businessobject1 obj,bcategory cat,bsubcategory subcat" +
                "where obj.BusinessID = subcat.businessid " +
                "and cat.BusinessID = obj.businessid " +
                        "and (cat.categoryname = '"+resultMainCat+"')" +
                //"and cat.categoryname = '"+mainCategory+"' " +
                "and (subcat.subcategoryname= '"+resultSubCat+"')";
            } else if(resultSubCat.equals("") && resultAttr.equals("") && day.equals("")){
                rtvBObj = "select distinct obj.BusinessID,obj.bname,obj.city,obj.state,obj.stars from businessobject1 obj,bcategory cat" +
                "and cat.BusinessID = obj.businessid " +
                        "and (cat.categoryname = '"+resultMainCat+"')";
               // "and cat.categoryname = '"+mainCategory+"' ";
            }else if(!resultSubCat.equals("") && !resultAttr.equals("") && day.equals("")){
                System.out.println("Case 3");
                rtvBObj = "select distinct obj.BusinessID,obj.bname,obj.city,obj.state,obj.stars from businessobject1 obj,bcategory cat,bsubcategory subcat, battribute attr " +
                "where obj.BusinessID = subcat.businessid " +
                "and obj.BusinessID = attr.businessid " +
                "and obj.BusinessID = cat.businessid " +       
                //"and cat.categoryname = '"+mainCategory+"' " +
                        "and (cat.categoryname = '"+resultMainCat+"')" +
                "and (subcat.subcategoryname= '"+resultSubCat+"') " +
                "and (attr.attribut= '"+resultAttr+"')";
            } else if(resultSubCat.equals("") && !resultAttr.equals("") && !day.equals("")){
                rtvBObj = "select distinct obj.BusinessID,obj.bname,obj.city,obj.state,obj.stars from businessobject1 obj,bcategory cat, battribute attr, businesshrs bhrs " +
                "where obj.BusinessID = cat.businessid " +
                "and obj.BusinessID = attr.businessid " +
                "and obj.BusinessID = bhrs.bhid " +
                "and bhrs.bday = '" + day + "' " +     
                        "and (cat.categoryname = '"+resultMainCat+"')" +
              //  "and cat.categoryname = '"+mainCategory+"' " +
                "and (attr.attribut= '"+resultAttr+"')";
            } else if(!resultSubCat.equals("") && resultAttr.equals("") && !day.equals("")){
                rtvBObj = "select distinct obj.BusinessID,obj.bname,obj.city,obj.state,obj.stars from businessobject1 obj,bcategory cat,bsubcategory subcat, businesshrs bhrs " +
                "where obj.BusinessID = subcat.businessid " +
                "and cat.BusinessID = obj.businessid " +
                "and obj.BusinessID = bhrs.bhid " +        
                "and bhrs.bday = '" + day + "' " +     
                        "and (cat.categoryname = '"+resultMainCat+"')" +
                //"and cat.categoryname = '"+mainCategory+"' " +
                "and (subcat.subcategoryname= '"+resultSubCat+"')";
            } else if(resultSubCat.equals("") && resultAttr.equals("") && !day.equals("")){
                rtvBObj = "select distinct obj.BusinessID,obj.bname,obj.city,obj.state,obj.stars from businessobject1 obj,bcategory cat, businesshrs bhrs " +
                "and cat.BusinessID = obj.businessid " +
                "and obj.BusinessID = bhrs.bhid " +        
                "and bhrs.bday = '" + day + "' " +  
                        "and (cat.categoryname = '"+resultMainCat+"')" ;
                //"and cat.categoryname = '"+mainCategory+"' ";
            }else if(!resultSubCat.equals("") && !resultAttr.equals("") && !day.equals("")){
                rtvBObj = "select distinct obj.BusinessID,obj.bname,obj.city,obj.state,obj.stars from businessobject1 obj,bcategory cat,bsubcategory subcat, battribute attr, businesshrs bhrs  " +
                "where obj.BusinessID = subcat.businessid " +
                "and obj.BusinessID = attr.businessid " +
                "and obj.BusinessID = cat.businessid " +
                "and obj.BusinessID = bhrs.bhid " +        
                "and bhrs.bday = '" + day + "' " +                        
                //"and cat.categoryname = '"+mainCategory+"' " +
                        "and (cat.categoryname = '"+resultMainCat+"')" +
                "and (subcat.subcategoryname= '"+resultSubCat+"') " +
                "and (attr.attribut= '"+resultAttr+"')";
            }
            try{
                   int rowCount=0;
                   DefaultTableModel dmTable = new DefaultTableModel();
                   dmTable.addColumn("Business"); 
                   dmTable.addColumn("City");
                   dmTable.addColumn("State");
                   dmTable.addColumn("Stars");
                   jdbc.connect();
                   psSelBObj = jdbc.conn.prepareStatement(rtvBObj);
                   rsBObj = jdbc.select(psSelBObj);
                   while(rsBObj.next()){
                        System.out.println("BID:"+rsBObj.getString(1)); 
                        BusinessObject obj = new BusinessObject();
                        Vector<String> row=new Vector<>();
                        row.add(rsBObj.getString(2));
                        row.add(rsBObj.getString(3));
                        row.add(rsBObj.getString(4));
                        row.add(""+rsBObj.getFloat(5));
                        dmTable.insertRow(rowCount++,row);

                        obj.setBusinessID(rsBObj.getString(1));
                        obj.setBusinessName(rsBObj.getString(2)); 
                        obj.setCity(rsBObj.getString(3));
                        obj.setState(rsBObj.getString(4)); 
                        obj.setStars(rsBObj.getFloat(5)); 
                        rtvDb.add(obj);
                   }
                   jTable1.setModel(dmTable);
                   jTable1.setRowSelectionAllowed(true);
                   jTable1.setColumnSelectionAllowed(false);
            }catch(Exception ie){
                           System.out.println("BID error:"+ie.getMessage()); 
                           ie.printStackTrace();
            } finally{
                try{
                    jdbc.close();
                }catch(Exception e){

                }
            } 
        } else if(criteria.equals("Any Attribute")) {
            if(AttributeList.size()>0){
                        Iterator itrAttr2 = AttributeList.iterator();
                        while(itrAttr2.hasNext()){
                                if(resultAttr.equals(""))
                                    resultAttr+=itrAttr2.next();
                                else
                                    resultAttr+="'"+" OR attr.attribut ='"+itrAttr2.next();
                            }
                            rtvBObj = "select distinct obj.BusinessID,obj.bname,obj.city,obj.state,obj.stars from businessobject1 obj, battribute attr " +
                            "where obj.BusinessID = attr.businessid " +
                            "and (attr.attribut= '"+resultAttr+"')";
                            System.out.println("Attr sql:"+rtvBObj);
                             try{
                                   int rowCount=0;
                                   DefaultTableModel dmTable = new DefaultTableModel();
                                   dmTable.addColumn("Business"); 
                                   dmTable.addColumn("City");
                                   dmTable.addColumn("State");
                                   dmTable.addColumn("Stars");
                                   jdbc.connect();
                                   psSelBObj = jdbc.conn.prepareStatement(rtvBObj);
                                   rsBObj = jdbc.select(psSelBObj);
                                   while(rsBObj.next()){
                                        System.out.println("BID:"+rsBObj.getString(1)); 
                                        BusinessObject obj = new BusinessObject();
                                        Vector<String> row=new Vector<>();
                                        row.add(rsBObj.getString(2));
                                        row.add(rsBObj.getString(3));
                                        row.add(rsBObj.getString(4));
                                        row.add(""+rsBObj.getFloat(5));
                                        dmTable.insertRow(rowCount++,row);

                                        obj.setBusinessID(rsBObj.getString(1));
                                        obj.setBusinessName(rsBObj.getString(2)); 
                                        obj.setCity(rsBObj.getString(3));
                                        obj.setState(rsBObj.getString(4)); 
                                        obj.setStars(rsBObj.getFloat(5)); 
                                        rtvDb.add(obj);
                                   }
                                   jTable1.setModel(dmTable);
                                   jTable1.setRowSelectionAllowed(true);
                                   jTable1.setColumnSelectionAllowed(false);
                            }catch(Exception ie){
                                           System.out.println("BID error:"+ie.getMessage()); 
                                           ie.printStackTrace();
                            } finally{
                                try{
                                    jdbc.close();
                                }catch(Exception ex){

                                }
                            }
                }
        } else if(criteria.equals("City") || criteria.equals("State") || criteria.equals("Zip")){
            String sqlBObj="";
        
            switch(criteria){
                case "City":
                            sqlBObj = "Select distinct BusinessID,bname,city,state,stars from businessobject1 where city='"+city+"'";
                        break;    
                case "State":
                            sqlBObj = "Select distinct BusinessID,bname,city,state,stars from businessobject1 where state='"+state+"'";       
                        break;
                case "Zip":
                            zip = "%"+zip;
                            sqlBObj = "Select distinct BusinessID,bname,city,state,stars from businessobject1 where full_address like '"+zip+"'";
                        break;  
            }
            try{
                   if(!criteria.equals("Any Attribute")){
                       int rowCount=0;
                       DefaultTableModel dmTable = new DefaultTableModel();
                       dmTable.addColumn("Business"); 
                       dmTable.addColumn("City");
                       dmTable.addColumn("State");
                       dmTable.addColumn("Stars");
                       jdbc.connect();
                       psSelBObj = jdbc.conn.prepareStatement(sqlBObj);
                       rsBObj = jdbc.select(psSelBObj);
                       while(rsBObj.next()){
                            System.out.println("BID:"+rsBObj.getString(1)); 
                            BusinessObject obj = new BusinessObject();
                            Vector<String> row=new Vector<>();
                            row.add(rsBObj.getString(2));
                            row.add(rsBObj.getString(3));
                            row.add(rsBObj.getString(4));
                            row.add(""+rsBObj.getFloat(5));
                            dmTable.insertRow(rowCount++,row);

                            obj.setBusinessID(rsBObj.getString(1));
                            obj.setBusinessName(rsBObj.getString(2)); 
                            obj.setCity(rsBObj.getString(3));
                            obj.setState(rsBObj.getString(4)); 
                            obj.setStars(rsBObj.getFloat(5)); 
                            rtvDb.add(obj);
                       }
                       jTable1.setModel(dmTable);
                       jTable1.setRowSelectionAllowed(true);
                       jTable1.setColumnSelectionAllowed(false);
               }
            }catch(Exception regnExp){}
        }
    }catch(Exception e){
        int em = JOptionPane.ERROR_MESSAGE;
        JOptionPane.showMessageDialog (null, "Retrieving the relevant business details failed", "Error", em);
    } finally{
        try{
            jdbc.close();
        }catch(Exception e){}
    }
    }//GEN-LAST:event_button3ActionPerformed

    private void button4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button4ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_button4ActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        // TODO add your handling code here:
        if(Business != null){
            new Reviews(Business).setVisible(true); 
            Business = null;
        }
    }//GEN-LAST:event_button1ActionPerformed

    private void checkbox23ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox23ItemStateChanged
        // TODO add your handling code here:
        
        if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                 maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox23.setState(false);
        } 
    }//GEN-LAST:event_checkbox23ItemStateChanged

    private void checkbox27ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox27ItemStateChanged
        // TODO add your handling code here:
        if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                 maincat.add(evt.getItem().toString());
                populateSubCategory(evt); 
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        }else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox27.setState(false); 
        } 
            
    }//GEN-LAST:event_checkbox27ItemStateChanged

    private void choice8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_choice8ItemStateChanged
        // TODO add your handling code here:
        criteria = evt.getItem().toString();
        JDBCconn jdbc = new JDBCconn();
        jdbc.connect();
        PreparedStatement psSelBObj;
        ResultSet rsBObj;
        dPanel2.removeAll();
        dPanel2.revalidate();
        dPanel2.repaint();
        dPanel2.setLayout(new GridLayout(277,500));
        if(criteria.equals("City") || criteria.equals("State") || criteria.equals("Zip") || criteria.equals("Any Attribute")){
            switch(criteria){
                case "City":
                     zip=""; state="";
                     try{ 
                        String sqlCity = "Select distinct city from businessobject1";                        
                        psSelBObj = jdbc.conn.prepareStatement(sqlCity);
                        rsBObj = jdbc.select(psSelBObj);
                        ButtonGroup bgCity = new ButtonGroup();
                        while(rsBObj.next()){
                            String addr = rsBObj.getString(1);
                            String spAddr[] = addr.split(" ");
                            String State = spAddr[spAddr.length-1];
                            JRadioButton jrCity = new JRadioButton(State);
                            jrCity.addItemListener(new ItemListener(){
                            @Override
                            public void itemStateChanged(ItemEvent e) {
                                        if(e.getStateChange() == ItemEvent.SELECTED){
                                                city = e.toString().substring(e.toString().indexOf("text=")+5,e.toString().indexOf("stateChange")-2);
                                        }
                                }
                            });
                            bgCity.add(jrCity);
                            dPanel2.add(jrCity);
                            dPanel2.revalidate();
                            dPanel2.repaint();
                        }
                        frame2.add(jsp2);
                        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame2.setSize(300, 200);
                        frame2.setLocationRelativeTo(null);
                        frame2.setVisible(true);
                     } catch(Exception e){e.printStackTrace();}
                    break;
                case "State":
                    city="";zip="";
                    try{
                        String sqlCity = "Select distinct state from businessobject1";                        
                        psSelBObj = jdbc.conn.prepareStatement(sqlCity);
                        rsBObj = jdbc.select(psSelBObj);
                        ButtonGroup bgState = new ButtonGroup();
                        while(rsBObj.next()){
                            String addr = rsBObj.getString(1);
                            String spAddr[] = addr.split(" ");
                            String State = spAddr[spAddr.length-1];
                            JRadioButton jrState = new JRadioButton(State);
                            jrState.addItemListener(new ItemListener(){
                            @Override
                            public void itemStateChanged(ItemEvent e) {
                                        if(e.getStateChange() == ItemEvent.SELECTED){
                                                state = e.toString().substring(e.toString().indexOf("text=")+5,e.toString().indexOf("stateChange")-2);
                                        }
                                }
                            });
                            bgState.add(jrState);
                            dPanel2.add(jrState);
                            dPanel2.revalidate();
                            dPanel2.repaint();
                        }
                        frame2.add(jsp2);
                        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame2.setSize(300, 200);
                        frame2.setLocationRelativeTo(null);
                        frame2.setVisible(true);
                     } catch(Exception e){e.printStackTrace();}
                    break;
                case "Zip":
                    city=""; state="";
                    try{
                        String sqlCity = "Select distinct full_address from businessobject1";                        
                        psSelBObj = jdbc.conn.prepareStatement(sqlCity);
                        rsBObj = jdbc.select(psSelBObj);
                        ButtonGroup bgZip = new ButtonGroup();
                        while(rsBObj.next()){
                            String addr = rsBObj.getString(1);
                            String spAddr[] = addr.split(" ");
                            String Zip = spAddr[spAddr.length-1];
                            JRadioButton jrZip = new JRadioButton(Zip);
                            jrZip.addItemListener(new ItemListener(){
                            @Override
                            public void itemStateChanged(ItemEvent e) {
                                        if(e.getStateChange() == ItemEvent.SELECTED){
                                                zip = e.toString().substring(e.toString().indexOf("text=")+5,e.toString().indexOf("stateChange")-2);
                                        }
                                }
                            });
                            bgZip.add(jrZip);
                            dPanel2.add(jrZip);
                            dPanel2.revalidate();
                            dPanel2.repaint();
                        }
                        frame2.add(jsp2);
                        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame2.setSize(300, 200);
                        frame2.setLocationRelativeTo(null);
                        frame2.setVisible(true);
                     } catch(Exception e){e.printStackTrace();}
                    break;
                case "Any Attribute":
                    city="";zip="";state="";
                    try{
                        String sqlCity = "Select distinct attribut from battribute";                        
                        psSelBObj = jdbc.conn.prepareStatement(sqlCity);
                        rsBObj = jdbc.select(psSelBObj);
                        while(rsBObj.next()){
                            String Attribute= rsBObj.getString(1);
                            JCheckBox jcb = new JCheckBox(Attribute);
                            jcb.addItemListener(new ItemListener(){
                            @Override
                            public void itemStateChanged(ItemEvent e) {
                                        String currAttribute = e.toString().substring(e.toString().indexOf("text=")+5,e.toString().indexOf("stateChange")-2);
                                        String resultAttr="";
                                        if(e.getStateChange() == ItemEvent.SELECTED){
                                                AttributeList.add(currAttribute);
                                        } else if(e.getStateChange() == ItemEvent.DESELECTED){
                                                if(Main.AttributeList.contains(currAttribute))
                                                Main.AttributeList.remove(currAttribute);
                                        }
                                        String Attr="",AllAttr="<html>Attributes:" ;
                                        Iterator itrAttr = AttributeList.iterator();
                                        while(itrAttr.hasNext()){
                                            Attr = (String) itrAttr.next();
                                            AllAttr += "<br>" + Attr;
                                        }
                                        AllAttr = AllAttr + "</html>";
                                        jLabel2.setText(AllAttr);
                                        
                            }
                        });
                        dPanel2.add(jcb);
                        dPanel2.revalidate();
                        dPanel2.repaint();                                       
                    }
                    frame2.add(jsp2);
                    frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame2.setSize(300, 200);
                    frame2.setLocationRelativeTo(null);
                    frame2.setVisible(true);
                    } catch(Exception e){e.printStackTrace();}
                    break;    
            }
        }
    }//GEN-LAST:event_choice8ItemStateChanged

    private void choice5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_choice5ItemStateChanged
        // TODO add your handling code here:
        day = evt.getItem().toString();
        if(day.equals(" "))
            day = "";
    }//GEN-LAST:event_choice5ItemStateChanged

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        int rowNumber = jTable1.rowAtPoint(evt.getPoint());
        Business = rtvDb.get(rowNumber);
    }//GEN-LAST:event_jTable1MouseClicked

    private void checkbox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox1ItemStateChanged
        // TODO add your handling code here:
         if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                 maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox1.setState(false);
        } 
    }//GEN-LAST:event_checkbox1ItemStateChanged

    private void checkbox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox2ItemStateChanged
        // TODO add your handling code here:
         if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                 maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox2.setState(false);
        } 
    }//GEN-LAST:event_checkbox2ItemStateChanged

    private void checkbox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox3ItemStateChanged
        // TODO add your handling code here:
         if(choice8.getSelectedItem().equals("All"))
         {
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                 maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox3.setState(false);
        } 
    }//GEN-LAST:event_checkbox3ItemStateChanged

    private void checkbox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox4ItemStateChanged
        // TODO add your handling code here:
         if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                 maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox4.setState(false);
        } 
    }//GEN-LAST:event_checkbox4ItemStateChanged

    private void checkbox5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox5ItemStateChanged
        // TODO add your handling code here:
        if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                 maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox5.setState(false);
        }
    }//GEN-LAST:event_checkbox5ItemStateChanged

    private void checkbox6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox6ItemStateChanged
        // TODO add your handling code here:
        if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                 maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox6.setState(false);
        }
    }//GEN-LAST:event_checkbox6ItemStateChanged

    private void checkbox7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox7ItemStateChanged
        // TODO add your handling code here:
        if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                 maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox7.setState(false);
        }
    }//GEN-LAST:event_checkbox7ItemStateChanged

    private void checkbox8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox8ItemStateChanged
        // TODO add your handling code here:
        if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                 maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox8.setState(false);
        }
    }//GEN-LAST:event_checkbox8ItemStateChanged

    private void checkbox9ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox9ItemStateChanged
        // TODO add your handling code here:
        if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                 maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox9.setState(false);
        }
    }//GEN-LAST:event_checkbox9ItemStateChanged

    private void checkbox10ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox10ItemStateChanged
        // TODO add your handling code here:
        if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                 maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox10.setState(false);
        }
    }//GEN-LAST:event_checkbox10ItemStateChanged

    private void checkbox11ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox11ItemStateChanged
        // TODO add your handling code here:
        if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                 maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox11.setState(false);
        }
    }//GEN-LAST:event_checkbox11ItemStateChanged

    private void checkbox12ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox12ItemStateChanged
        // TODO add your handling code here:
        if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                 maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox12.setState(false);
        }
    }//GEN-LAST:event_checkbox12ItemStateChanged

    private void checkbox13ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox13ItemStateChanged
        // TODO add your handling code here:
        if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox13.setState(false);
        }
        
        
    }//GEN-LAST:event_checkbox13ItemStateChanged

    private void checkbox14ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox14ItemStateChanged
        // TODO add your handling code here:
        if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox14.setState(false);
        }
    }//GEN-LAST:event_checkbox14ItemStateChanged

    private void checkbox15ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox15ItemStateChanged
        // TODO add your handling code here:
        if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox15.setState(false);
        }
    }//GEN-LAST:event_checkbox15ItemStateChanged

    private void checkbox16ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox16ItemStateChanged
        // TODO add your handling code here:
        if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox16.setState(false);
        }
    }//GEN-LAST:event_checkbox16ItemStateChanged

    private void checkbox17ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox17ItemStateChanged
        // TODO add your handling code here:
        if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox17.setState(false);
        }
        
    }//GEN-LAST:event_checkbox17ItemStateChanged

    private void checkbox18ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox18ItemStateChanged
        // TODO add your handling code here:
        
        if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();           
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox18.setState(false);
        }
    }//GEN-LAST:event_checkbox18ItemStateChanged

    private void checkbox19ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox19ItemStateChanged
        // TODO add your handling code here:
        if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox19.setState(false);
        }
    }//GEN-LAST:event_checkbox19ItemStateChanged

    private void checkbox20ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox20ItemStateChanged
        // TODO add your handling code here:
        if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox20.setState(false);
        }
        
    }//GEN-LAST:event_checkbox20ItemStateChanged

    private void checkbox21ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox21ItemStateChanged
        // TODO add your handling code here:
        if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox21.setState(false);
        }
    }//GEN-LAST:event_checkbox21ItemStateChanged

    private void checkbox24ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox24ItemStateChanged
        // TODO add your handling code here:
        if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox24.setState(false);
        }
    }//GEN-LAST:event_checkbox24ItemStateChanged

    private void checkbox25ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox25ItemStateChanged
        // TODO add your handling code here:
        if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox25.setState(false);
        }
    }//GEN-LAST:event_checkbox25ItemStateChanged

    private void checkbox26ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox26ItemStateChanged
        // TODO add your handling code here:
        if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox26.setState(false);
        }
    }//GEN-LAST:event_checkbox26ItemStateChanged

    private void checkbox28ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox28ItemStateChanged
        // TODO add your handling code here:
        if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox28.setState(false);
        }
    }//GEN-LAST:event_checkbox28ItemStateChanged

    private void checkbox29ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkbox29ItemStateChanged
        // TODO add your handling code here:
        if(choice8.getSelectedItem().equals("All")){
            if(evt.getStateChange()==ItemEvent.SELECTED){
                refreshComponents();
                maincat.add(evt.getItem().toString());
                populateSubCategory(evt);
            } else if(evt.getStateChange()==ItemEvent.DESELECTED){
                refreshComponents();
            }
        } else{
            int em = JOptionPane.ERROR_MESSAGE;
            JOptionPane.showMessageDialog (null, "Search criteria selection mismatch", "Error", em);
            checkbox29.setState(false);
        }
    }//GEN-LAST:event_checkbox29ItemStateChanged
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*Set the Nimbus look and feel */ 
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html */
         
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
       JDBCconn jdbc = new JDBCconn();
        if(args.length==4)
        {
            String Arguments = args[0] +" "+ args[1] +" "+ args[2] +" "+ args[3] ;
            System.out.println(Arguments);
            
            if(Arguments.contains("yelp_business.json") &&  Arguments.contains("yelp_review.json") && Arguments.contains("yelp_checkin.json") && Arguments.contains("yelp_user.json"))
            {
                 

               // System.out.println("Review table created");
                
       /*          System.out.println("Deleting business data");
                String sqlBusinessesDelete = "drop table BusinessObject1";
                String sqlBusinessesCreate = "CREATE TABLE BUSINESSOBJECT1 (BUSINESSID VARCHAR2(30),FULL_ADDRESS VARCHAR2(256),OPENHRS CHAR(64),CITY VARCHAR2(32),STATE VARCHAR2(32),LATITUDE FLOAT,LONGITUDE FLOAT,RCOUNT NUMBER,BNAME VARCHAR2(256),STARS FLOAT,BTYPE VARCHAR2(256))";
                PreparedStatement psDelObj;
                try{
                     jdbc.connect();
                     psDelObj = jdbc.conn.prepareStatement(sqlBusinessesDelete);
                     jdbc.delete(psDelObj);
                     psDelObj.getConnection().commit();
                } catch(Exception se){ System.out.println("Business objects deletion failed");
                } finally{ 
                    try{
                        //jdbc.close();
                    }catch(Exception close){

                    }
                }
                
                
                try
                {
                 JDBCconn.create(sqlBusinessesCreate);   
                }catch(Exception e){ System.out.println(" U failed");
                }
                System.out.println("Business table created");
           
            
                //System.out.println("Deleting user data");
                String sqlUsersDelete = "drop table UserObject1";
                String sqlUsercreate = "create table userobject1(usid varchar(32),yelp_since varchar(24),rccount int,rname varchar(64),fan float,avgstar float,utype varchar(32))";
                PreparedStatement psDelObj2;
                try{
                    // jdbc.connect();
                     psDelObj = jdbc.conn.prepareStatement(sqlUsersDelete);
                     System.out.println("Attempting to delete user");
                     jdbc.delete(psDelObj);
                     psDelObj.getConnection().commit();
                     System.out.println("Users deleted");
                     
                } catch(Exception se){ System.out.println("User objects deletion failed");
                } 
                  finally{ 
                    try{
                       // jdbc.close();
                    }catch(Exception close){ 

                    }
                }
                try{
                    JDBCconn.create(sqlUsercreate);
                }catch(Exception e){System.out.println("Failed");}
                
                System.out.println("User table created");
               
                
                System.out.println("Deleting review data");
                String sqlReviewsDelete = "drop table ReviewObject1";
                String sqlReviewCreate = "CREATE TABLE REVIEWOBJECT1(REVIEW_ID VARCHAR2(32),USER_ID VARCHAR2(32),STARS FLOAT,RDATE VARCHAR2(32),TEXT VARCHAR2(4000),RTYPE VARCHAR2(32),BUSINESSID VARCHAR2(32),USEFULVOTES INT)";
                
//                PreparedStatement psDelObj;
                try{
                     //jdbc.connect();
                     psDelObj = jdbc.conn.prepareStatement(sqlReviewsDelete);
                     jdbc.delete(psDelObj);
                } catch(Exception se){ System.out.println("Reviews objects deletion failed");
                }     //jdbc.close();
                    
                try
                {
                    JDBCconn.create(sqlReviewCreate);
                }catch(Exception e){System.out.println("R failed");
                
                }*/
               Main m = new Main();
               m.init();   
                             
            
        } 
        else
        {
            System.out.println("Anti command line block");
            System.out.println("Arguments:"+args.length);
            PreparedStatement psSelCntObj;
            ResultSet rsCntObj;
            
            String sqlBusinessCount = "Select count(*) from BusinessObject1";
            
            try{
                 jdbc.connect();
                 psSelCntObj = jdbc.conn.prepareStatement(sqlBusinessCount);
                 rsCntObj = psSelCntObj.executeQuery();
                 rsCntObj.next();
                 int businessCnt = rsCntObj.getInt(1);
                 if(businessCnt != db_business_count){
                        System.out.println("Starting business insertion");
                        try{
                            List<BusinessObject> businessObjList = BusinessObjparser.ReadJSON("UTF-8");
                            System.out.println(businessObjList.size()+" businesses populated");
                        }catch(Exception e){}
                      //populateBusinesses();
                 }else
                     System.out.println("Business objects are populated in database correctly");
            } catch(Exception se){ System.out.println("Business object population decision failed:"+se.getMessage());
             se.printStackTrace();
            } finally{ 
                try{
                    jdbc.close();
                }catch(Exception close){
                    
                }
            }
            PreparedStatement psSelCntObj1;
            ResultSet rsCntObj1;
            
            String sqlReviewCount = "Select count(*) from ReviewObject1";
            
            try
            {
                jdbc.connect();
                psSelCntObj1 = jdbc.conn.prepareStatement(sqlReviewCount);
                rsCntObj1 = psSelCntObj1.executeQuery();
                rsCntObj1.next();
                int reviewcnt = rsCntObj1.getInt(1);
                if(reviewcnt != db_review_count){
                    System.out.println("Starting review insertion");
                    try{
                        List<reviewobject> reviewObjList = reviewsobjparser.ReadJSON("UTF-8");
                        System.out.println(reviewObjList.size()+" reviews added");
                    }catch(Exception e){}
                    //populateReviews();
                }else
                    System.out.println("Review objects are populated in database correctly");
            }
            catch(Exception e) 
            { 
                System.out.println("Review object popultion decision failed:"+e.getMessage());
            e.printStackTrace();
            }
            finally {
                try{
                    jdbc.close();
                } catch(Exception e){}
            }    
            
            PreparedStatement psSelCntObj2;
            ResultSet rsCntObj2;
            
            String sqlUserCount = "Select count(*) from UserObject1";
            
            try
            {
                jdbc.connect();
                psSelCntObj2 = jdbc.conn.prepareStatement(sqlUserCount);
                rsCntObj2 = psSelCntObj2.executeQuery();
                rsCntObj2.next();
                int reviewcnt = rsCntObj2.getInt(1);
                if(reviewcnt != db_user_count){
                    System.out.println("Starting user insertion");
                    try{
                        List<userobject>userObjList = userobjparser.ReadJSON("UTF-8");
                        System.out.println(userObjList.size()+" users added");
                    }catch(Exception e){}
                }else
                    System.out.println("User objects are populated in database correctly");
            }
            catch(Exception e) 
            { 
                System.out.println("User object popultion decision failed:"+e.getMessage());
            e.printStackTrace();
            }
            finally {
                try{
                    jdbc.close();
                } catch(Exception e){}
            }
        }
    
        
            }
      

    }
       private void init(){
               new populateRows("Businesses").start();
               new populateRows("Reviews").start();
               new populateRows("Users").start();
    }
    
   
        
    private void refreshComponents(){
            dPanel.removeAll();
            dPanel.revalidate();
            dPanel.repaint();
            dPanel2.removeAll();
            dPanel2.revalidate();
            dPanel2.repaint();
            frame.dispose();
            frame2.dispose();
    }
    
    private void populateMainCategory(){
        ArrayList<String> chkValues = new ArrayList<>();
        String resultMainCat="";
        if(maincat.size()>0){
                                
                                Iterator itrMain = maincat.iterator();
                                while(itrMain.hasNext()){
                                    if(resultMainCat.equals(""))
                                        resultMainCat+=itrMain.next();
                                    else
                                        resultMainCat+="'"+" OR cat.categoryname ='"+itrMain.next();
                                }
        
        //if(!mainCategory.equals("")){
            try{
                JDBCconn jdbc = new JDBCconn();
                jdbc.connect();
                PreparedStatement psSelCObj = jdbc.conn.prepareStatement("select distinct subcat.subcategoryname from bcategory cat,bsubcategory subcat where cat.BusinessID = subcat.businessid and (cat.categoryname = '"+resultMainCat+"')",ResultSet.TYPE_SCROLL_INSENSITIVE);
                
                ResultSet rsSubCat = jdbc.select(psSelCObj);
                if(rsSubCat != null){
                    //rsSubCat.beforeFirst();
                    while(rsSubCat.next()){
                        String subCat = rsSubCat.getString(1);
                        chkValues.add(subCat);
                    }
                }
            }catch(Exception e){e.printStackTrace();}
                //jdbc.select(psSelCObj);
                populateCheckBox(chkValues);
           }
    }
    
    private void populateSubCategory(ItemEvent evt){
      if(evt.getStateChange() == ItemEvent.SELECTED){
            if(subCatList.size()>0){
                subCatList.clear();
                AttributeList.clear();
                jLabel1.setText("Sub Category:[None]");   
            }
            //mainCategory = evt.getItem().toString();
            populateMainCategory();
        } else if(evt.getStateChange() == ItemEvent.DESELECTED){
            subCatList.clear();
            AttributeList.clear();
            jLabel1.setText("Sub Category:[None]"); 
        }   
    }
        
    private void populateCheckBox(ArrayList chkBoxs){
        Iterator itrSub = chkBoxs.iterator();
        frame.setTitle("Sub Category"); 
        frame2.setTitle("Attributes"); 
        dPanel.setLayout(new GridLayout(375,500));
        dPanel2.setLayout(new GridLayout(277,500));
        JButton jbAttr = new JButton();
        jbAttr.setText("Attributes"); 
        jbAttr.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                String resultMainCat="";
        if(maincat.size()>0){
                                
                                Iterator itrMain = maincat.iterator();
                                while(itrMain.hasNext()){
                                    if(resultMainCat.equals(""))
                                        resultMainCat+=itrMain.next();
                                    else
                                        resultMainCat+="'"+" OR cat.categoryname ='"+itrMain.next();
                                }
        }
                         if(subCatList.size()>0){
                                String resultSubCat="";
                                Iterator itrSub = subCatList.iterator();
                                while(itrSub.hasNext()){
                                    if(resultSubCat.equals(""))
                                        resultSubCat+=itrSub.next();
                                    else
                                        resultSubCat+="'"+" OR subcat.subcategoryname ='"+itrSub.next();
                                }
                                try{
                                    AttributeList.clear();
                                    JDBCconn jdbc = new JDBCconn();
                                    jdbc.connect();
                                    String rtvAttr = "select distinct attr.attribut from bcategory cat,bsubcategory subcat, battribute attr " +
                                    "where cat.BusinessID = subcat.businessid " +
                                    "and cat.BusinessID = attr.businessid " +
                                    "and subcat.BusinessID = attr.businessid " +
                                    "and (cat.categoryname = '"+resultMainCat+"')" +
                                    "and (subcat.subcategoryname= '"+resultSubCat+"')";
                                    PreparedStatement psSelAObj = jdbc.conn.prepareStatement(rtvAttr);
                                    ResultSet rsAttr = jdbc.select(psSelAObj);
                                    if(rsAttr != null){
                                        while(rsAttr.next()){
                                            String Attribute= rsAttr.getString(1);
                                            JCheckBox jcb = new JCheckBox(Attribute);
                                            jcb.addItemListener(new ItemListener(){
                                            @Override
                                            public void itemStateChanged(ItemEvent e) {
                                                        String currAttribute = e.toString().substring(e.toString().indexOf("text=")+5,e.toString().indexOf("stateChange")-2);
                                                        if(e.getStateChange() == ItemEvent.SELECTED){
                                                                AttributeList.add(currAttribute);
                                                        } else if(e.getStateChange() == ItemEvent.DESELECTED){
                                                                if(Main.AttributeList.contains(currAttribute))
                                                                Main.AttributeList.remove(currAttribute);
                                                        }
                                                        String Attr="",AllAttr="<html>Attributes:" ;
                                                        Iterator itrAttr = AttributeList.iterator();
                                                        while(itrAttr.hasNext()){
                                                            Attr = (String) itrAttr.next();
                                                            AllAttr += "<br>" + Attr;
                                                        }
                                                        AllAttr = AllAttr + "</html>";
                                                        jLabel2.setText(AllAttr);
                                                }
                                            });
                                            dPanel2.add(jcb);
                                            dPanel2.revalidate();
                                            dPanel2.repaint();                                       
                                        }
                                        frame2.add(jsp2);
                                        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                        frame2.setSize(300, 200);
                                        frame2.setLocation(getContentPane().getX() + 600, getContentPane().getY() + 275); 
                                        frame2.setVisible(true); 
                                    }else
                                        System.out.println("No result");
                                    
                                } catch(Exception se){}
                            }
            }

            @Override
            public void mousePressed(MouseEvent e) { }

            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mouseEntered(MouseEvent e) {  }

            @Override
            public void mouseExited(MouseEvent e) {  }
        });
        dPanel.add(jbAttr);
        dPanel.revalidate();
        dPanel.repaint();
        while(itrSub.hasNext()){
            try{
                String currentCategory = itrSub.next().toString();
                JCheckBox jcb = new JCheckBox(currentCategory);
                jcb.addItemListener(new ItemListener(){
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        String subCat = e.toString().substring(e.toString().indexOf("text=")+5,e.toString().indexOf("stateChange")-2);
                         if(e.getStateChange() == ItemEvent.SELECTED){
                                subCatList.add(subCat);
                                jLabel1.setText("Sub Category:"+subCatList.toString());
                                frame2.dispose();
                         } else if(e.getStateChange() == ItemEvent.DESELECTED){
                            if(Main.subCatList.contains(subCat)){
                                Main.subCatList.remove(subCat);
                                jLabel1.setText("Sub Category:"+subCatList.toString());
                            }
                         }
                    } 
                });
                dPanel.add(jcb);
                dPanel.revalidate();
                dPanel.repaint();
            }catch(Exception exp){exp.printStackTrace();}
        }
        frame.add(jsp);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLocation(getContentPane().getX() + 300, getContentPane().getY() + 275); 
        frame.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button button1;
    private java.awt.Button button3;
    private java.awt.Button button4;
    private java.awt.Checkbox checkbox1;
    private java.awt.Checkbox checkbox10;
    private java.awt.Checkbox checkbox11;
    private java.awt.Checkbox checkbox12;
    private java.awt.Checkbox checkbox13;
    private java.awt.Checkbox checkbox14;
    private java.awt.Checkbox checkbox15;
    private java.awt.Checkbox checkbox16;
    private java.awt.Checkbox checkbox17;
    private java.awt.Checkbox checkbox18;
    private java.awt.Checkbox checkbox19;
    private java.awt.Checkbox checkbox2;
    private java.awt.Checkbox checkbox20;
    private java.awt.Checkbox checkbox21;
    private java.awt.Checkbox checkbox22;
    private java.awt.Checkbox checkbox23;
    private java.awt.Checkbox checkbox24;
    private java.awt.Checkbox checkbox25;
    private java.awt.Checkbox checkbox26;
    private java.awt.Checkbox checkbox27;
    private java.awt.Checkbox checkbox28;
    private java.awt.Checkbox checkbox29;
    private java.awt.Checkbox checkbox3;
    private java.awt.Checkbox checkbox4;
    private java.awt.Checkbox checkbox5;
    private java.awt.Checkbox checkbox6;
    private java.awt.Checkbox checkbox7;
    private java.awt.Checkbox checkbox8;
    private java.awt.Checkbox checkbox9;
    private java.awt.Choice choice5;
    private java.awt.Choice choice6;
    private java.awt.Choice choice7;
    private java.awt.Choice choice8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label label4;
    // End of variables declaration//GEN-END:variables

 class populateRows extends Thread{
    String table;
    public populateRows(String t){
        table = t;
    }
    public void run(){
        switch(table){
            case "Reviews":
                System.out.println("Starting review insertion");
                try{
                    List<reviewobject> reviewObjList = reviewsobjparser.ReadJSON("UTF-8");
                    System.out.println(reviewObjList.size()+" reviews added");
                }catch(Exception e){}
                break;
            case "Users":
                System.out.println("Starting user insertion");
                try{
                    List<userobject>userObjList = userobjparser.ReadJSON("UTF-8");
                    System.out.println(userObjList.size()+" users added");
                }catch(Exception e){}
                break;
            case "Businesses":
                System.out.println("Starting business insertion");
                try{
                    List<BusinessObject> businessObjList = BusinessObjparser.ReadJSON("UTF-8");
                    System.out.println(businessObjList.size()+" businesses populated");
                }catch(Exception e){}
                break;
        }
    }
}
 
    
}
