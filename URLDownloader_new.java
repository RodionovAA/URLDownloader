package urldownloader_new;

import java.awt.Desktop;
import java.io.*;
import java.net.*;
import java.nio.*;
import javax.swing.JOptionPane;
import raa.params.Global;

/**
 *
 * @author AleAlRodionov
 */
public class URLDownloader_new {
    /**
     * @param args the command line arguments
     */
        
    public static void main(String[] args) throws MalformedURLException, IOException {
        
        if (args.length != 0){
            
            try{   
            Global.url = args[0];
            Global.puth = args[1];  
            Global.option = args[2];
            
            } catch (Exception ex){
                if (Global.url != null) {
                    Global.url = args[0];
                }
                 if (Global.puth != null) {
                    Global.puth = args[1];
                } else {
                    Global.puth = ""; 
                 }
                  if (Global.option != null) {
                    Global.option = args[2];
                }
            }
       } else {
           System.err.println("Строка параметров пуста");
           return;
       }
       
       String fileName = "";
       String ext = "";

       if (Global.url.indexOf("?") > -1){
           System.out.println(Global.url.indexOf("/",8 ));
           fileName = Global.url.substring(Global.url.indexOf("/",8 )+1, Global.url.indexOf("?")).replace("/", "");
           ext = "";
       } else{
           fileName = "index";
           ext = ".html";
       }
       
      File file = checkDir(Global.puth,fileName,ext);
       
       String url = new String(Global.url);
       
       URL adress = new URL(url);
       URLConnection connect = adress.openConnection();
       connect.connect();
        
        try {
            downloadUsingStream(url, file.getAbsolutePath());
             
        } catch (IOException e) {
            e.printStackTrace();
        }
       
       if (Global.option != null){
            Desktop desktop = Desktop.getDesktop();
            desktop.open(new java.io.File(file.getAbsolutePath()));
        }
    }
    
    public static File checkDir(String puth , String fileName, String ext){
        int dial = 0;
        File res;
        if( new File(puth+fileName + ext).exists()){
            dial =  JOptionPane.showConfirmDialog(
                                      null, 
                                      "Файл " + puth + fileName + ext + " уже существует. Заменить?",
                                      null,
                                      JOptionPane.YES_NO_OPTION);
            
        } else {
            return new File(puth + fileName + ext);
        }
        if (dial == 0){
            File file = new File(puth+fileName + ext);
            return file;
        } else{

          fileName = fileName + "_new";
          res =  checkDir(puth,fileName,ext);
          return res;
        } 
    }
    
    private static void downloadUsingStream(String urlStr, String file) throws IOException{
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
    
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }

}
