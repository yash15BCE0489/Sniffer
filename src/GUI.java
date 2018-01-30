/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*15BCE0489*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import jpcap.*;
import java.io.*;
/**
 *
 * @author Yash Agarwal
 */
public class GUI {
    
    NetworkInterface[] NETWORK_INTERFACES;
    JpcapCaptor CAP;
    CaptureThread CAPTAIN;
    int INDEX = 0;
    int COUNTER = 0;
    boolean CaptureState = false;
    
    
    JFrame MainWindow = new JFrame("Yash's Sniffer v1.0");
    public static JTextArea TA_OUTPUT = new JTextArea();
    JScrollPane SP_OUTPUT = new JScrollPane();
    ButtonGroup BG_Filter_Enable_Disable = new ButtonGroup();
    ButtonGroup BG_Ports = new ButtonGroup();
    JButton B_CAPTURE = new JButton("CAPTURE");
    JButton B_STOP = new JButton("STOP");
    JButton B_SELECT = new JButton("SELECT");
    JButton B_LIST = new JButton("LIST");
    JButton B_FILTER = new JButton("FILTER");
    JButton B_INFO = new JButton("INFO");
    JButton B_SAVE = new JButton("SAVE");
    JButton B_LOAD = new JButton("LOAD");
    JButton B_ABOUT = new JButton("ABOUT");
    JButton B_HELP = new JButton("HELP");
    JButton B_EXIT = new JButton("EXIT");
    JRadioButton RB_Filter_Enable = new JRadioButton("Enable");
    JRadioButton RB_Filter_Disable = new JRadioButton("Disable");
    JRadioButton RB_Port_Special = new JRadioButton("Special Port");
    JRadioButton RB_Port_HTTP = new JRadioButton("HTTP (80)");
    JRadioButton RB_Port_SSL = new JRadioButton("HTTP SSL (443)");
    JRadioButton RB_Port_FTP = new JRadioButton("FTP (21)");
    JRadioButton RB_Port_SSH = new JRadioButton("SSH (22)");
    JRadioButton RB_Port_Telnet = new JRadioButton("Telnet (23)");
    JRadioButton RB_Port_SMTP = new JRadioButton("SMTP (25)");
    JRadioButton RB_Port_POP3 = new JRadioButton("POP3 (110)");
    JRadioButton RB_Port_IMAP = new JRadioButton("IMAP (143)");
    JRadioButton RB_Port_IMAPS = new JRadioButton("IMAPS (993)");
    JRadioButton RB_Port_DNS = new JRadioButton("DNS (53)");
    JRadioButton RB_Port_NetBIOS = new JRadioButton("NetBIOS (139)");
    JRadioButton RB_Port_SAMBA = new JRadioButton("SAMBA (137)");
    JRadioButton RB_Port_AD = new JRadioButton("AD (445)");
    JRadioButton RB_Port_SQL = new JRadioButton("SQL (118)");
    JRadioButton RB_Port_LDAP = new JRadioButton("LDAP (389)");
    JLabel L_Title = new JLabel("Yash's Sniffer v1.0");
    JLabel L_Interface = new JLabel("Interface");
    JLabel L_FilterStatus = new JLabel("Port Filter Status");
    JLabel L_FilterStatusBox = new JLabel("DISABLED (ALL PORTS)");
    JLabel L_FilterPresets = new JLabel("Port Filter Presents");
    JLabel L_SpecialPort = new JLabel("Special Port #");
    JTextField TF_SelectInterface = new JTextField();
    JTextField TF_SpecialPort = new JTextField();
    
    public static void main(String args[])
    {
        new GUI();
    }
    public GUI()
    {
        BuildGUI();
        DisableButtons();
    }
    public void BuildGUI()
    {
    MainWindow.setSize(765,480);
    MainWindow.setLocation(200, 200);
    MainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    MainWindow.getContentPane().setLayout(null);
    
    TA_OUTPUT.setEditable(false);
    TA_OUTPUT.setFont(new Font("Monospaced",0,12));
    TA_OUTPUT.setForeground(new Color(0,0,153));
    TA_OUTPUT.setLineWrap(true);
    
    SP_OUTPUT.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    SP_OUTPUT.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    SP_OUTPUT.setViewportView(TA_OUTPUT);
    
    MainWindow.getContentPane().add(SP_OUTPUT);
    SP_OUTPUT.setBounds(10, 16, 740, 290);
  //-----------------------------------------------------  
    B_CAPTURE.setBackground(new Color(255,0,0));
    B_CAPTURE.setForeground(new Color(255,255,255));
    B_CAPTURE.setMargin(new Insets(0,0,0,0));
    B_CAPTURE.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent X)
                {
                    Action_B_CAPTURE(X);
                }
            });
    MainWindow.getContentPane().add(B_CAPTURE);
    B_CAPTURE.setBounds(10,310,130,25);
  //------------------------------------------------------  
    B_STOP.setBackground(new Color(0,0,0));
    B_STOP.setForeground(new Color(255,255,255));
    B_STOP.setMargin(new Insets(0,0,0,0));
    B_STOP.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent X)
                {
                    Action_B_STOP(X);
                }
            });
    MainWindow.getContentPane().add(B_STOP);
    B_STOP.setBounds(145,310,110,25);
   //---------------------------------------------------------- 
    B_SELECT.setBackground(new Color(0,0,0));
    B_SELECT.setForeground(new Color(255,255,255));
    B_SELECT.setMargin(new Insets(0,0,0,0));
    B_SELECT.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent X)
                {
                    Action_B_SELECT(X);
                }
            });
    MainWindow.getContentPane().add(B_SELECT);
    B_SELECT.setBounds(0,388,75,20);
   //----------------------------------------------------------
   B_LIST.setBackground(new Color(0,0,0));
    B_LIST.setForeground(new Color(255,255,255));
    B_LIST.setMargin(new Insets(0,0,0,0));
    B_LIST.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent X)
                {
                    Action_B_LIST(X);
                }
            });
    MainWindow.getContentPane().add(B_LIST);
    B_LIST.setBounds(0,410,75,20);
   //----------------------------------------------------------
    B_FILTER.setBackground(new Color(0,0,0));
    B_FILTER.setForeground(new Color(255,255,255));
    B_FILTER.setMargin(new Insets(0,0,0,0));
    B_FILTER.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent X)
                {
                    Action_B_FILTER(X);
                }
            });
    MainWindow.getContentPane().add(B_FILTER);
    B_FILTER.setBounds(360,400,80,25);
   //----------------------------------------------------------
    B_INFO.setBackground(new Color(0,0,0));
    B_INFO.setForeground(new Color(255,255,255));
    B_INFO.setMargin(new Insets(0,0,0,0));
    B_INFO.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent X)
                {
                    Action_B_INFO(X);
                }
            });
    MainWindow.getContentPane().add(B_INFO);
    B_INFO.setBounds(100,400,75,25);
   //----------------------------------------------------------
    B_SAVE.setBackground(new Color(0,0,0));
    B_SAVE.setForeground(new Color(255,255,255));
    B_SAVE.setMargin(new Insets(0,0,0,0));
    B_SAVE.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent X)
                {
                    Action_B_SAVE(X);
                }
            });
    MainWindow.getContentPane().add(B_SAVE);
    B_SAVE.setBounds(100,340,75,25);
   //----------------------------------------------------------
    B_LOAD.setBackground(new Color(0,0,0));
    B_LOAD.setForeground(new Color(255,255,255));
    B_LOAD.setText("LOAD");
    B_LOAD.setToolTipText("");
    B_LOAD.setMargin(new Insets(0,0,0,0));
    B_LOAD.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent X)
                {
                    Action_B_LOAD(X);
                }
            });
    MainWindow.getContentPane().add(B_LOAD);
    B_LOAD.setBounds(180,340,75,25);
   //----------------------------------------------------------
   B_ABOUT.setBackground(new Color(0,0,0));
    B_ABOUT.setForeground(new Color(255,255,255));
    B_ABOUT.setMargin(new Insets(0,0,0,0));
    B_ABOUT.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent X)
                {
                    Action_B_ABOUT(X);
                }
            });
    MainWindow.getContentPane().add(B_ABOUT);
    B_ABOUT.setBounds(180,370,75,25);
   //----------------------------------------------------------
   B_HELP.setBackground(new Color(0,0,0));
    B_HELP.setForeground(new Color(255,255,255));
    B_HELP.setMargin(new Insets(0,0,0,0));
    B_HELP.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent X)
                {
                    Action_B_HELP(X);
                }
            });
    MainWindow.getContentPane().add(B_HELP);
    B_HELP.setBounds(100,370,75,25);
   //----------------------------------------------------------
    
    B_EXIT.setBackground(new Color(0,0,0));
    B_EXIT.setForeground(new Color(255,255,255));
    B_EXIT.setMargin(new Insets(0,0,0,0));
    B_EXIT.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent X)
                {
                    Action_B_EXIT(X);
                }
            });
    MainWindow.getContentPane().add(B_EXIT);
    B_EXIT.setBounds(180,400,75,25);
   //----------------------------------------------------------
   
    BG_Filter_Enable_Disable.add(RB_Filter_Enable);
    RB_Filter_Enable.addActionListener(new ActionListener()
    {
       public void actionPerformed(ActionEvent X)
       {
           Action_B_ENABLE(X);
       }});
     MainWindow.getContentPane().add(RB_Filter_Enable);
   RB_Filter_Enable.setBounds(290,350,70,25);
   //------------------------------------------------------------
   BG_Filter_Enable_Disable.add(RB_Filter_Disable);
    RB_Filter_Disable.setSelected(true);
   RB_Filter_Disable.addActionListener(new ActionListener()
    {
       public void actionPerformed(ActionEvent X)
       {
           Action_B_DISABLE(X);
       }});
     MainWindow.getContentPane().add(RB_Filter_Disable);
   RB_Filter_Disable.setBounds(360,350,70,25);
   //------------------------------------------------------------
    BG_Ports.add(RB_Port_Special);
    RB_Port_Special.setFont(new Font("Tahoma",0,11));
    MainWindow.getContentPane().add(RB_Port_Special);
    RB_Port_Special.setBounds(360, 380, 90,20);
    //---------------------------------------------------------
     BG_Ports.add(RB_Port_HTTP);
    RB_Port_HTTP.setFont(new Font("Tahoma",0,11));
    MainWindow.getContentPane().add(RB_Port_HTTP);
    RB_Port_HTTP.setBounds(460, 320, 90,23);
    //---------------------------------------------------------
     BG_Ports.add(RB_Port_SSL);
    RB_Port_SSL.setFont(new Font("Tahoma",0,11));
    MainWindow.getContentPane().add(RB_Port_SSL);
    RB_Port_SSL.setBounds(460, 340, 100,23);
    //---------------------------------------------------------
     BG_Ports.add(RB_Port_FTP);
    RB_Port_FTP.setFont(new Font("Tahoma",0,11));
    MainWindow.getContentPane().add(RB_Port_FTP);
    RB_Port_FTP.setBounds(460, 360, 90,25);
    //---------------------------------------------------------
     BG_Ports.add(RB_Port_SSH);
    RB_Port_SSH.setFont(new Font("Tahoma",0,11));
    MainWindow.getContentPane().add(RB_Port_SSH);
    RB_Port_SSH.setBounds(460, 380, 90,25);
    //---------------------------------------------------------
     BG_Ports.add(RB_Port_Telnet);
    RB_Port_Telnet.setFont(new Font("Tahoma",0,11));
    MainWindow.getContentPane().add(RB_Port_Telnet);
    RB_Port_Telnet.setBounds(460, 400, 90,25);
    //---------------------------------------------------------
     BG_Ports.add(RB_Port_SMTP);
    RB_Port_SMTP.setFont(new Font("Tahoma",0,11));
    MainWindow.getContentPane().add(RB_Port_SMTP);
    RB_Port_SMTP.setBounds(560, 320, 90,25);
    //---------------------------------------------------------
     BG_Ports.add(RB_Port_POP3);
    RB_Port_POP3.setFont(new Font("Tahoma",0,11));
    MainWindow.getContentPane().add(RB_Port_POP3);
    RB_Port_POP3.setBounds(560, 340, 90,25);
    //---------------------------------------------------------
     BG_Ports.add(RB_Port_IMAP);
    RB_Port_IMAP.setFont(new Font("Tahoma",0,11));
    MainWindow.getContentPane().add(RB_Port_IMAP);
    RB_Port_IMAP.setBounds(560, 360, 90,25);
    //---------------------------------------------------------
     BG_Ports.add(RB_Port_IMAPS);
    RB_Port_IMAPS.setFont(new Font("Tahoma",0,11));
    MainWindow.getContentPane().add(RB_Port_IMAPS);
    RB_Port_IMAPS.setBounds(560, 380, 90,25);
    //---------------------------------------------------------
    BG_Ports.add(RB_Port_DNS);
    RB_Port_DNS.setFont(new Font("Tahoma",0,11));
    MainWindow.getContentPane().add(RB_Port_DNS);
    RB_Port_DNS.setBounds(560, 400, 90,25);
    //---------------------------------------------------------
    BG_Ports.add(RB_Port_NetBIOS);
    RB_Port_NetBIOS.setFont(new Font("Tahoma",0,11));
    MainWindow.getContentPane().add(RB_Port_NetBIOS);
    RB_Port_NetBIOS.setBounds(660, 320, 90,25);
    //---------------------------------------------------------
    BG_Ports.add(RB_Port_SAMBA);
    RB_Port_SAMBA.setFont(new Font("Tahoma",0,11));
    MainWindow.getContentPane().add(RB_Port_SAMBA);
    RB_Port_SAMBA.setBounds(660, 340, 90,25);
    //---------------------------------------------------------
    BG_Ports.add(RB_Port_AD);
    RB_Port_AD.setFont(new Font("Tahoma",0,11));
    MainWindow.getContentPane().add(RB_Port_AD);
    RB_Port_AD.setBounds(660, 360, 90,25);
    //---------------------------------------------------------
    BG_Ports.add(RB_Port_SQL);
    RB_Port_SQL.setFont(new Font("Tahoma",0,11));
    MainWindow.getContentPane().add(RB_Port_SQL);
    RB_Port_SQL.setBounds(660, 380, 90,25);
    //---------------------------------------------------------
    BG_Ports.add(RB_Port_LDAP);
    RB_Port_LDAP.setFont(new Font("Tahoma",0,11));
    MainWindow.getContentPane().add(RB_Port_LDAP);
    RB_Port_LDAP.setBounds(660, 400, 90,25);
    //---------------------------------------------------------
    L_Title.setFont(new Font("Tahoma",0,12));
    L_Title.setForeground(new Color(0,0,255));
    L_Title.setHorizontalAlignment(SwingConstants.CENTER);
    MainWindow.getContentPane().add(L_Title);
    L_Title.setBounds(150, 0, 440,15);
    //---------------------------------------------------------
    L_Interface.setHorizontalAlignment(SwingConstants.CENTER);
    MainWindow.getContentPane().add(L_Interface);
    L_Interface.setBounds(10, 344, 60,16);
    //---------------------------------------------------------
    L_FilterStatus.setHorizontalAlignment(SwingConstants.CENTER);
    MainWindow.getContentPane().add(L_FilterStatus);
    L_FilterStatus.setBounds(300, 310, 110,16);
    //---------------------------------------------------------
    L_FilterStatusBox.setForeground(new Color(255,51,0));
    L_FilterStatusBox.setHorizontalAlignment(SwingConstants.CENTER);
    L_FilterStatusBox.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));
    MainWindow.getContentPane().add(L_FilterStatusBox);
    L_FilterStatusBox.setBounds(270, 330, 170,20);
    //---------------------------------------------------------
    L_FilterPresets.setFont(new Font("Tahoma",0,10));
    L_FilterPresets.setForeground(new Color(0,153,0));
    L_FilterPresets.setHorizontalAlignment(SwingConstants.CENTER);
    MainWindow.getContentPane().add(L_FilterPresets);
    L_FilterPresets.setBounds(550, 310, 110,10);
    //---------------------------------------------------------
    L_SpecialPort.setFont(new Font("Tahoma",0,11));
    L_SpecialPort.setHorizontalAlignment(SwingConstants.CENTER);
    MainWindow.getContentPane().add(L_SpecialPort);
    L_SpecialPort.setBounds(270, 380, 80,14);
    //---------------------------------------------------------
    TF_SelectInterface.setForeground(new Color(255,0,0));
    TF_SelectInterface.setHorizontalAlignment(JTextField.CENTER);
    MainWindow.getContentPane().add(TF_SelectInterface);
    TF_SelectInterface.setBounds(3, 364, 70,20);
    //---------------------------------------------------------
    TF_SpecialPort.setForeground(new Color(255,0,0));
    TF_SpecialPort.setHorizontalAlignment(JTextField.CENTER);
    MainWindow.getContentPane().add(TF_SpecialPort);
    TF_SpecialPort.setBounds(270, 400, 80,22);
    //---------------------------------------------------------
    MainWindow.setVisible(true);
    }
    public void Action_B_CAPTURE(ActionEvent X)
    {
        TA_OUTPUT.setText("");
        CaptureState = true;
        CapturePackets();
    }
    public void Action_B_STOP(ActionEvent X)
    {
        CaptureState = false;
        CAPTAIN.finished();
    }
    public void Action_B_SELECT(ActionEvent X)
    {
        ChooseInterface();
    }
    public void Action_B_LIST(ActionEvent X)
    {
        ListNetworkInterfaces();
        B_SELECT.setEnabled(true);
        TF_SelectInterface.requestFocus();
    }
    public void Action_B_FILTER(ActionEvent X)
    {
        try
        {
            if(RB_Filter_Enable.isSelected())
            {
                if(RB_Port_Special.isSelected())
                {
                    String PORT = TF_SpecialPort.getText();
                    CAP.setFilter("port " + PORT, true);
                }
                else if(RB_Port_HTTP.isSelected())
                { CAP.setFilter("port 80", true);}
                
                else if(RB_Port_SSL.isSelected())
                { CAP.setFilter("port 443", true);}
                
                else if(RB_Port_FTP.isSelected())
                { CAP.setFilter("port 21", true);}
                
                else if(RB_Port_SSH.isSelected())
                { CAP.setFilter("port 22", true);}
                
                else if(RB_Port_Telnet.isSelected())
                { CAP.setFilter("port 23", true);}
                
                else if(RB_Port_SMTP.isSelected())
                { CAP.setFilter("port 25", true);}
                
                else if(RB_Port_POP3.isSelected())
                { CAP.setFilter("port 110", true);}
                
                else if(RB_Port_IMAP.isSelected())
                { CAP.setFilter("port 143", true);}
                
                else if(RB_Port_IMAPS.isSelected())
                { CAP.setFilter("port 993", true);}
                
                else if(RB_Port_HTTP.isSelected())
                { CAP.setFilter("port 53", true);}
                
                else if(RB_Port_NetBIOS.isSelected())
                { CAP.setFilter("port 139", true);}
                
                else if(RB_Port_SAMBA.isSelected())
                { CAP.setFilter("port 137", true);}
                
                else if(RB_Port_AD.isSelected())
                { CAP.setFilter("port 445", true);}
                
                else if(RB_Port_SQL.isSelected())
                { CAP.setFilter("port 118", true);}
                
                else if(RB_Port_LDAP.isSelected())
                { CAP.setFilter("port 389", true);}
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Filtering is Disabled");
            }
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
    }
    public void Action_B_INFO(ActionEvent X)
    {
        NetworkInfo INFO = new NetworkInfo();
    }
    public void Action_B_SAVE(ActionEvent X)
    {
        SaveCaptureData();
    }
    public void Action_B_LOAD(ActionEvent X)
    {
        LoadCaptureData();
    }
    public void Action_B_ABOUT(ActionEvent X)
    {
        String M = "Yash's Sniffer v1.0";
        JOptionPane.showMessageDialog(null, M);
    }
    public void Action_B_HELP(ActionEvent X)
    {
        String M = " Yash's Sniffer is a Java packet capturing application.\n"+
                "\nCAPTURE = Start capturing packets on Selected Interfaces."+
                "\nSTOP = Stop Capturing packets." +
                "\nLIST = List network interfaces on host."+
                "\nSELECT = Select interfaces to capture packets with."+
                "\nFILTER = Filter on a selected port when filtering is enabled."+
                "\nENABLE = Enables port filtering (Only captures traffic on that port)."+
                "\nDISABLE = Disables port filtering (Captures traffic on ALL ports)."+
                "\nINFO = Detailed information for each network interface on host."+
                "\nHELP = Displays this help screen."+
                "\nABOUT = Displays application ABOUT information."+
                "\n\n                YASH's Sniffers";
        JOptionPane.showMessageDialog(null, M);
    }
    public void Action_B_EXIT(ActionEvent X)
    {
        MainWindow.setVisible(true);
        MainWindow.dispose();
    }
    public void Action_B_ENABLE(ActionEvent X)
    {
        L_FilterStatusBox.setText("Enabled (Selected port)");
    }
    public void Action_B_DISABLE(ActionEvent X)
    {
        L_FilterStatusBox.setText("Disabled (All port)");
    }
   
    public void CapturePackets()
    {
        CAPTAIN = new CaptureThread()
        {
            public Object construct()
            {
                TA_OUTPUT.setText("\nNow CAPTURING on Interface " + INDEX + "..." +
                        "\n-----------------------------------------"+
                        "---------------------------------------\n\n");
                try
                {
                    CAP = JpcapCaptor.openDevice(NETWORK_INTERFACES[INDEX], 65535, false, 20);
                    while(CaptureState)
                    {
                        CAP.processPacket(1,new PacketContents());
                        //CAP.loopPacket(1, new PacketContents());
                        //CAP.getPacket();
                    }
                    CAP.close();
                }
                catch(Exception e)
                {
                    System.out.print(e);
                }
                
                return 0;       
            }
            public void  finished()
            {
                this.interrupt();
            }
        };
        CAPTAIN.start();
    }
 
    public void DisableButtons()
    {
        B_CAPTURE.setEnabled(false);
        B_STOP.setEnabled(false);
        B_SELECT.setEnabled(false);
        B_FILTER.setEnabled(false);
        B_SAVE.setEnabled(false);
    }
    
    public void EnableButtons()
    {
        B_CAPTURE.setEnabled(true);
        B_STOP.setEnabled(true);
        B_SELECT.setEnabled(true);
        B_FILTER.setEnabled(true);
        B_SAVE.setEnabled(true);
        B_LOAD.setEnabled(true);
    }
    
    public void ListNetworkInterfaces()
    {
        NETWORK_INTERFACES = JpcapCaptor.getDeviceList();
        
        TA_OUTPUT.setText("");
        
        for(int i=0;i< NETWORK_INTERFACES.length;i++)
        {
            TA_OUTPUT.append("\n\n---------------------------------------------"
                    + "interface "+i+
                    "Info-----------------------------");
            TA_OUTPUT.append("\nInterface Number: "+ i);
            TA_OUTPUT.append("\n Description: "+
                    NETWORK_INTERFACES[i].name + "("+
                    NETWORK_INTERFACES[i].description+")");
            TA_OUTPUT.append("\nDatalink Name: "+
                    NETWORK_INTERFACES[i].datalink_name + "("+
                    NETWORK_INTERFACES[i].datalink_description +")");
            TA_OUTPUT.append("\nMAC address: ");
            
            byte[] R= NETWORK_INTERFACES[i].mac_address;
            for(int A=0;A<= NETWORK_INTERFACES.length;A++)
            {
                TA_OUTPUT.append(Integer.toHexString(R[A] & 0xff)+":");
            }
            NetworkInterfaceAddress [] INT = NETWORK_INTERFACES[i].addresses;
            TA_OUTPUT.append("\nIP Address: "+ INT[0].address);
            TA_OUTPUT.append("\nSubnet Mask: "+ INT[0].subnet);
            TA_OUTPUT.append("\nBroadcast Address: "+ INT[0].broadcast);
            COUNTER++;
        }
    }
    
    public void ChooseInterface()
    {
        int TEMP = Integer.parseInt(TF_SelectInterface.getText());
        if(TEMP>-1 && TEMP<COUNTER)
        {
            INDEX = TEMP;
            EnableButtons();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Outside of RANGE. # interfaces = 0-"
            + (COUNTER-1) + ".");
           
        }
        TF_SelectInterface.setText("");
    }
    public static void SaveCaptureData()
    {
        String CaptureData = TA_OUTPUT.getText();
        
        try{
            File DATA = new File("CaptureData.txt");
            FileOutputStream DATASTREAM = new FileOutputStream(DATA);
            PrintStream OUT = new PrintStream(DATASTREAM);
            OUT.print(CaptureData);
            OUT.close();
            DATASTREAM.close();
            JOptionPane.showMessageDialog(null, "DATA SAVED Successfully");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "File Access Error! Could not SAVE data.");
        }
    }
    
    public static void LoadCaptureData()
    {
        String CaptureData = "";
        try{
           File DATA = new File("CaptureData.txt");
            FileInputStream DATASTREAM = new FileInputStream(DATA);
            InputStreamReader INPUT = new InputStreamReader(DATASTREAM);
            BufferedReader IN = new BufferedReader(INPUT);
            while(IN.read() != -1)
            {
                CaptureData = CaptureData + IN.readLine();
            }
            IN.close();
            INPUT.close();
            DATASTREAM.close();
            TA_OUTPUT.setText(CaptureData);
            JOptionPane.showMessageDialog(null, "DATA LOADED Successfully");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "File Access Error! Could not LOAD data");
        }
    }
}
