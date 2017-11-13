import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.io.IOException;
import java.io.FileWriter;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.GraphicsEnvironment;
import java.io.BufferedWriter;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import org.jsoup.*;
import java.io.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import javax.swing.ImageIcon;
import org.jsoup.select.Elements;
import java.net.URL;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;


public class TextFileReaderV5 {

   private String filePath, fileName;
   private int[] asciiArray;
   private int[] redArray, greenArray, blueArray;
   private int type, height, width;
   private BufferedImage img;
   private String src, name;
   private static final String folderPath = "C:\\Users\\ahlin_000\\Google Drive\\CS 211\\Lab 7 File Visualizer\\";

   public TextFileReaderV5()
   {
   }
   
   public void saveImg(String myUrl)
   {
      try
      {
         Response resultImageResponse = Jsoup.connect(myUrl).ignoreContentType(true).execute();
      
      // output here
         FileOutputStream out = (new FileOutputStream(new java.io.File("cs" + ".jpeg")));
         out.write(resultImageResponse.bodyAsBytes());  // resultImageResponse.body() is where the image's contents are.
         out.close();
         readImg("cs.jpeg");
      }
      catch (IOException e)
      {

      }
   }
   public int[] readFile(String myPath) {
      try {
         filePath = myPath;
         File file = new File(filePath);
         FileReader fileReader = new FileReader(file);
         asciiArray = new int[256];
         //"C:\\Users\\ahlin_000\\Google Drive\\CS 211\\Lab 7 File Visualizer\\test.txt"
         BufferedReader bufferedReader = new BufferedReader(fileReader);
         StringBuffer stringBuffer = new StringBuffer();
         String line;
         while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
            stringBuffer.append("\n");
         }
         for (int i = 0; i < stringBuffer.length(); i++)
         {
            char character = stringBuffer.charAt(i);
            int ascii = (int)character;
            asciiArray[ascii]++;
         }
         fileReader.close();
      } 
      catch (IOException e) {
         e.printStackTrace();
      }
      return asciiArray;
   }
      
   public void readImg (String pathName)
   {
      try {
         //"C:\\Users\\ahlin_000\\Google Drive\\CS 211\\Lab 7 File Visualizer\\test.txt"
         filePath = pathName;
         redArray = new int[256];
         greenArray = new int[256];
         blueArray = new int[256];
         File file = new File(filePath);
         img = ImageIO.read(new File(filePath));
         type = img.getType();
         height = img.getHeight();
         width = img.getWidth();
         BufferedImage img2 = new BufferedImage(width, height, type);
         for (int row = 0; row < height; row++)
         {
            for(int col = 0; col < width; col++)
            {
            // Read 24 bit RGB pixel
               int pixel = img.getRGB(col, row);
               int red   = (pixel & 0xFF0000) >> 16; // Red = upper 8 bits
               int green = (pixel & 0x00FF00) >>  8; // Green = middle 8 bits
               int blue  = (pixel & 0x0000FF);
               redArray[red]++;
               greenArray[green]++;
               blueArray[blue]++;
            } 
         }
         JFrame imageFrame = new JFrame();
         JPanel imagePanel = new JPanel();
      
         JLabel imageLabel = new JLabel();
         ImageIcon img = new ImageIcon("cs.jpeg");
         imageFrame.getContentPane().removeAll();
         imageFrame.repaint();
         imageFrame.validate();

         imageLabel.setIcon(img);
         imagePanel.add(imageLabel);
         imageFrame.setSize(new Dimension(width, height+100));
         imageFrame.add(imagePanel);
         imageFrame.pack();
         imageFrame.setVisible(true);
      }
      catch (IOException e) {
         e.printStackTrace();
      }
   }
   
   public int[] getRedArray()
   {
      return redArray;
   }
   
   public int[] getGreenArray()
   {
      return greenArray;
   }
   
   public int[] getBlueArray()
   {
      return blueArray;
   }
   public void writeFile(String myFileName)
   {
      try
      {
         fileName = myFileName;
         File file = new File(fileName);
         file.createNewFile();
         FileWriter writer = new FileWriter(file);
         BufferedWriter bWriter = new BufferedWriter(writer);
         for (int i = 0; i < 256; i++)
         {
            bWriter.write(Character.toString((char)i) + ":" + asciiArray[i] + " ");
         }
         bWriter.flush();
         bWriter.close();
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }
}