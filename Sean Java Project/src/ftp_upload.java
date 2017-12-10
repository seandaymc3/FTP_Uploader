import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ProtocolCommandListener;;

public class ftp_upload extends JFrame{
     
    FTPClient ftp = null;
    private JTextField hosttext;
    private JTextField usernametext;
    private JTextField passwordtext;
    public static String directoryname;
    public static JLabel fileupload;
    public ftp_upload() throws Exception{
    	getContentPane().setLayout(null);
    	
    	JLabel lblNewLabel = new JLabel("Host");
    	lblNewLabel.setBounds(10, 10, 71, 25);
    	getContentPane().add(lblNewLabel);
    	
    	JLabel lblUserName = new JLabel("User Name");
    	lblUserName.setBounds(10, 41, 71, 25);
    	getContentPane().add(lblUserName);
    	
    	JLabel lblPassword = new JLabel("Password");
    	lblPassword.setBounds(10, 72, 71, 25);
    	getContentPane().add(lblPassword);
    	
    	hosttext = new JTextField();
    	hosttext.setBounds(91, 12, 160, 25);
    	getContentPane().add(hosttext);
    	hosttext.setColumns(10);
    	
    	usernametext = new JTextField();
    	usernametext.setColumns(10);
    	usernametext.setBounds(91, 41, 160, 25);
    	getContentPane().add(usernametext);
    	
    	passwordtext = new JTextField();
    	passwordtext.setColumns(10);
    	passwordtext.setBounds(91, 72, 160, 25);
    	getContentPane().add(passwordtext);
    	
    	JLabel lblNewLabel_1 = new JLabel("File");
    	lblNewLabel_1.setBounds(10, 115, 71, 25);
    	getContentPane().add(lblNewLabel_1);
    	
    	JLabel filedescription = new JLabel("");
    	filedescription.setBounds(50, 115, 201, 25);
    	getContentPane().add(filedescription);
    	filedescription.setText(searching.loggedfile);
    	JButton btnNewButton = new JButton("UPLOAD");
    	btnNewButton.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseClicked(MouseEvent arg0) {
    			ftp = new FTPClient();
    	        //ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
    	        int reply;
    	        try {
					ftp.connect(hosttext.getText());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	        reply = ftp.getReplyCode();
    	        if (!FTPReply.isPositiveCompletion(reply)) {
    	            try {
						ftp.disconnect();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	            try {
						throw new Exception("Exception in connecting to FTP Server");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    	        }
    	        try {
					ftp.login(usernametext.getText(), passwordtext.getText());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	        try {
					ftp.setFileType(FTP.BINARY_FILE_TYPE);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	        ftp.enterLocalPassiveMode();
    	        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
    	        try {
					directoryname = "/"+System.getProperty("user.name").toString()+"-"+InetAddress.getLocalHost().getHostName()+"-"+timeStamp+"";
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    	        try {
					ftp.makeDirectory(directoryname);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	        try {
					uploadFile(searching.completepath, ""+searching.loggedfile+"","");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	        disconnect();
    	        System.out.println("Done");
    		}
    	});
    	btnNewButton.setBounds(10, 151, 241, 25);
    	getContentPane().add(btnNewButton);
    	
    	fileupload = new JLabel("");
    	fileupload.setForeground(new Color(60, 179, 113));
    	fileupload.setBounds(10, 185, 241, 34);
    	getContentPane().add(fileupload);
        
    }
    public void uploadFile(String localFileFullName, String fileName, String hostDir)
            throws Exception {
        try(InputStream input = new FileInputStream(new File(localFileFullName))){
        	hostDir = "//"+directoryname+"//";
        	this.ftp.storeFile(hostDir + fileName, input);
        }
       fileupload.setText("File Uploaded Successfully");
    }
 
    public void disconnect(){
        if (this.ftp.isConnected()) {
            try {
                this.ftp.logout();
                this.ftp.disconnect();
            } catch (IOException f) {
                // do nothing as file is already saved to server
            }
        }
    }
}