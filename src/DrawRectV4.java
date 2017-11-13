import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.Dimension; //https://store-images.s-microsoft.com/image/apps.61355.9007199266242611.428fecfc-b289-4255-a7da-bdae68dd1ced.b2e96d7f-d450-4247-8674-ab5d7ab17cd6?w=96&h=96&q=60
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.geom.Rectangle2D.Double;
import javax.swing.JFileChooser;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;
import java.io.FileReader;
import org.jsoup.*;
import java.io.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import javax.swing.ImageIcon;

//Use with "TextFileReaderV5"

public class DrawRectV4 extends JPanel
{
   private static int type;
   private static JFrame initFrame = new JFrame ("File Chooser");
   private static JComboBox myBox;
   private static JPanel labelPanel = new JPanel();
   private static JPanel mainPanel = new JPanel();
   private static JPanel titlePanel = new JPanel();
   private static FileNameExtensionFilter filter;
   private static JFileChooser fileChooser;
   private static JTextField urlField;
   private static JButton chooseButton;
   private static SubmitListener l1;
   private static JFrame mainFrame;
   static TextFileReaderV5 fileReader = new TextFileReaderV5();
   static int[] redArray, greenArray, blueArray;
   static int[] array;
   
   public static void main(String[]args)
   {
      JPanel chooserPanel = new JPanel();
      urlField = new JTextField(20);
      urlField.setText
         ("http://a5.mzstatic.com/us/r30/Purple5/v4/5a/2e/e9/5a2ee9b3-8f0e-4f8b-4043-dd3e3ea29766/icon128-2x.png");
         //https://apps.google.com/images/logo/apps-for-work-social-icon.png
      JPanel buttonPanel = new JPanel();
      chooseButton = new JButton("Submit");
      l1 = new SubmitListener();
      JLabel urlLabel = new JLabel("Enter URL:");
      buttonPanel.add(chooseButton);
      chooserPanel.add(urlLabel);
      chooserPanel.add(urlField);
      
      JPanel initPanel = new JPanel();
      initPanel.setLayout(new BorderLayout());
      initPanel.add(buttonPanel, BorderLayout.CENTER);
      initPanel.add(chooserPanel, BorderLayout.NORTH);
      initFrame.add(initPanel);
      initFrame.setSize(400,150);
      initFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      initFrame.pack();
      chooseButton.addActionListener(l1);
      initFrame.setVisible(true);
      mainFrame = new JFrame ("Histogram");
   }
   private static class BarPanel extends JPanel {
   
      @Override
        public void paintComponent(Graphics g) {
         super.paintComponent(g);
         g.setColor(new Color(0x867970));
         for (int i=0; i < 300; i++)
         {
            g.drawLine(20, i*10 , 2000 , i*10);
         }
         g.setColor(new Color(0x000000));
         for (int i=0; i < 800; i+=20)
         {
            g.drawString(Integer.toString(760-i), 0, i+5);
         }
         g.setColor(new Color(114,147,203));
         for (int i = 0; i < 255; i++)
         {
            g.fillRect(i+(6*i), 833-array[i], 10, array[i]);
         }
      }
   
      @Override
        public Dimension getPreferredSize() {
         return new Dimension(300, 200);
      }
   }
   
   static class SubmitListener implements ActionListener
   {
      public void actionPerformed(ActionEvent s)
      {
         String url = urlField.getText();
         type = 0;
         mainFrame = new JFrame ("Histogram");
        
         mainFrame.getContentPane().removeAll();
         mainFrame.getContentPane().validate();
         mainFrame.getContentPane().repaint();
         mainPanel.removeAll();
         titlePanel.removeAll();
         labelPanel.removeAll();
         fileReader.saveImg(url);
         JLabel rLabel = new JLabel("Red");
         rLabel.setFont(new Font("Calibri", Font.PLAIN, 50));
         rLabel.setForeground(Color.RED);
         titlePanel.setLayout(new GridBagLayout());
         titlePanel.add(rLabel);
         JPanel p2 = new JPanel();
         JLabel gLabel = new JLabel("Green");
         gLabel.setFont(new Font("Calibri", Font.PLAIN, 50));
         gLabel.setForeground(Color.GREEN);
         p2.setLayout(new GridBagLayout());
         p2.add(gLabel);
         JPanel p3 = new JPanel();
         JLabel bLabel = new JLabel("Blue");
         bLabel.setForeground(Color.BLUE);
         bLabel.setFont(new Font("Calibri", Font.PLAIN, 50));
         p3.setLayout(new GridBagLayout());
         p3.add(bLabel);
                             
               
         greenArray = fileReader.getGreenArray();
         blueArray = fileReader.getBlueArray();
         RedBarPanel redBarPanel = new RedBarPanel();
         GreenBarPanel greenBarPanel = new GreenBarPanel();
         BlueBarPanel blueBarPanel = new BlueBarPanel();
         mainPanel.setLayout(new GridBagLayout());
         redBarPanel.setBackground(new Color(0xd3d3d3));
         greenBarPanel.setBackground(new Color(0xbdbdbd));
         blueBarPanel.setBackground(new Color(0xa8a8a8));
         GridBagConstraints c = new GridBagConstraints();
         titlePanel.setBackground(new Color(0,0,0));
               
         c.anchor = c.PAGE_START;
         c.fill = GridBagConstraints.BOTH;   
         c.weightx = 1.0;
         c.weighty = 0.0;
         c.gridx = 0;
         c.gridy = 0;
         c.ipady = 0;
         c.ipadx = 0;
         mainPanel.add(titlePanel, c);
               
         c.fill = GridBagConstraints.BOTH;     
         c.weightx = 1.0;
         c.weighty = 0.0;
         c.gridx = 1;
         c.gridy = 0;
         p2.setBackground(new Color(0,0,0));
         mainPanel.add(p2, c);
               
         c.fill = GridBagConstraints.BOTH; 
         c.weightx = 1.0;
         c.weighty = 0.0;
         c.gridx = 2;
         c.gridy = 0;
         p3.setBackground(new Color(0x000000));
         mainPanel.add(p3, c);
               
         c.fill = GridBagConstraints.BOTH;
         c.weightx = 1.0;
         c.weighty = 1.0;
         c.gridx = 0;
         c.gridy = 1;
         c.ipady = 875;
         c.ipadx = 212;
         mainPanel.add(redBarPanel, c);
               
         c.fill = GridBagConstraints.BOTH;
         c.weightx = 1.0;
         c.weighty = 1.0;
         c.gridx = 1;
         c.gridy = 1;
         mainPanel.add(greenBarPanel, c);
               
         c.fill = GridBagConstraints.BOTH;
         c.weightx = 1.0;
         c.weighty = 1.0;
         c.gridx = 2;
         c.gridy = 1;
         mainPanel.add(blueBarPanel, c);
               
         RedLabelPanel rPanel = new RedLabelPanel();
         c.fill = GridBagConstraints.BOTH; 
         c.weightx = 1.0;
         c.weighty = 1.0;
         c.gridx = 0;
         c.gridy = 2;
         rPanel.setBackground(new Color(0xEEEEEE));
         c.ipady = 2;
         c.ipadx = 0;
         mainPanel.add(rPanel, c);
               
         GreenLabelPanel gPanel = new GreenLabelPanel();
         c.fill = GridBagConstraints.BOTH; 
         c.weightx = 1.0;
         c.weighty = 1.0;
         c.gridx = 1;
         c.gridy = 2;
         gPanel.setLayout(new GridBagLayout());
         gPanel.add(urlField);
         gPanel.add(chooseButton);
         gPanel.setBackground(new Color(0xEEEEEE));
         mainPanel.add(gPanel, c);
               
         BlueLabelPanel bPanel = new BlueLabelPanel();
         c.fill = GridBagConstraints.BOTH; 
         c.anchor = c.PAGE_END;
         c.weightx = 1.0;
         c.weighty = 1.0;
         c.gridx = 2;
         c.gridy = 2;
         bPanel.setBackground(new Color(0xEEEEEE));
         mainPanel.add(bPanel, c);
            
         mainPanel.setBackground(new Color(214,211,190));
         mainFrame.add(mainPanel);
         mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         //mainFrame.setSize(new Dimension(1900, 1080));
         mainFrame.setResizable(true);
         mainFrame.pack();
         mainFrame.setVisible(true);
      }   
   }
   
   private static class LabelPanel extends JPanel
   {
      @Override
      public void paintComponent(Graphics g) {
         super.paintComponent(g);
         g.setFont(new Font("Calibri", Font.BOLD, 15)); 
         for (int i = 0; i < 255; i++)
         {
            g.drawString(Character.toString((char)i), i+(6*i), 13);  
         }
      }
   
      @Override
        public Dimension getPreferredSize() {
         return new Dimension(300, 200);
      }  
   } 
   
   private static class RedBarPanel extends JPanel
   {
      @Override
      public void paintComponent(Graphics g) {
         super.paintComponent(g);
         redArray = fileReader.getRedArray(); 
         g.setColor(new Color(0x000000));
         for (int i=0; i < 1200; i+=20)
         {
            g.drawString(Integer.toString(920-i), 0, i+5);
         }
         for (int i=0; i < 300; i++)
         {
            g.drawLine(20, i*10 , 2000 , i*10);
         }  
         for (int i = 0; i < 256; i++)
         {
            g.setColor(new Color(i,0,0,200));
            g.fillRect(i*2, 920-redArray[i], 2, redArray[i]);
         }  
      }
   
      @Override
        public Dimension getPreferredSize() {
         return new Dimension(300, 200);
      }
   }
   
   private static class GreenBarPanel extends JPanel
   {
      @Override
      public void paintComponent(Graphics g) {
         super.paintComponent(g);
         greenArray = fileReader.getGreenArray();
         g.setFont(new Font("Calibri", Font.BOLD, 15)); 
         g.setColor(new Color(0x000000));
         for (int i=0; i < 300; i++)
         {
            g.drawLine(0, i*10 , 2000 , i*10);
         }
         for (int i = 0; i < 255; i++)
         {
            g.setColor(new Color(0,i,0,200));
            g.fillRect(i*2, 920-greenArray[i], 2, greenArray[i]);
         }
      }
   
      @Override
        public Dimension getPreferredSize() {
         return new Dimension(300, 200);
      }
   }
   private static class BlueBarPanel extends JPanel
   {
      @Override
      public void paintComponent(Graphics g) {
         super.paintComponent(g);
         blueArray = fileReader.getBlueArray();
         g.setFont(new Font("Calibri", Font.BOLD, 15)); 
         g.setColor(new Color(0x000000));
         for (int i=0; i < 300; i++)
         {
            g.drawLine(0, i*10 , 2000 , i*10);
         }
         for (int i = 0; i < 255; i++)
         {
            g.setColor(new Color(0,0,i,200));
            g.fillRect(i*2, 920-blueArray[i], 2, blueArray[i]);
         }  
      }
   
      @Override
        public Dimension getPreferredSize() {
         return new Dimension(300, 200);
      }
   }
   
   private static class RedLabelPanel extends JPanel
   {
      @Override
      public void paintComponent(Graphics g) {
         super.paintComponent(g);
         g.setFont(new Font("Calibri", Font.BOLD, 15));
         g.drawString(Integer.toString(0), 0, 13);
         g.drawString(Integer.toString(85), 160, 13);
         g.drawString(Integer.toString(170), 320, 13);
         g.drawString(Integer.toString(255), 485, 13);          
      }
   
      @Override
        public Dimension getPreferredSize() {
         return new Dimension(300, 200);
      }
   }
   
   private static class GreenLabelPanel extends JPanel
   {
      @Override
      public void paintComponent(Graphics g) {
         super.paintComponent(g);
         g.setFont(new Font("Calibri", Font.BOLD, 15));
         g.drawString(Integer.toString(0), 5, 13);
         g.drawString(Integer.toString(85), 160, 13);
         g.drawString(Integer.toString(170), 320, 13);
         g.drawString(Integer.toString(255), 485, 13); 
                 
      }
   
      @Override
        public Dimension getPreferredSize() {
         return new Dimension(300, 200);
      }
   }
   private static class BlueLabelPanel extends JPanel
   {
      @Override
      public void paintComponent(Graphics g) {
         super.paintComponent(g);
         g.setFont(new Font("Calibri", Font.BOLD, 15));
         g.drawString(Integer.toString(0), 5, 13);
         g.drawString(Integer.toString(85), 160, 13);
         g.drawString(Integer.toString(170), 320, 13);
         g.drawString(Integer.toString(255), 485, 13);          
      }
   
      @Override
        public Dimension getPreferredSize() {
         return new Dimension(300, 200);
      }
   }
}

