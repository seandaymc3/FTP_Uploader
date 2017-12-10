import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class searching extends JFrame {
	public static JTextField dir;
	public static JTextField ext;
	private JScrollPane scrollPane;
	public static JList list;
	public static File[] newlist; 
	public static ArrayList<String> listt;
	public static String loggedfile;
	public static String completepath;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	public static JLabel lblNewLabel_3;
	public searching() {
		super("                                 FTP UPLOADER SEAN DAY");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Specify Drive");
		lblNewLabel.setBounds(10, 11, 86, 30);
		getContentPane().add(lblNewLabel);
		
		dir = new JTextField();
		dir.setBounds(144, 10, 290, 33);
		getContentPane().add(dir);
		dir.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Specify file extension");
		lblNewLabel_1.setBounds(10, 55, 130, 30);
		getContentPane().add(lblNewLabel_1);
		
		ext = new JTextField();
		ext.setColumns(10);
		ext.setBounds(144, 54, 290, 33);
		getContentPane().add(ext);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				search(dir.getText(),ext.getText());
			}
		});
		btnSearch.setBounds(10, 171, 424, 23);
		getContentPane().add(btnSearch);
		
		
		  list = new JList();
				scrollPane = new JScrollPane(list);
				scrollPane.setBounds(10, 205, 424, 317);
				getContentPane().add(scrollPane);
				scrollPane.setViewportView(list);
				
				btnNewButton = new JButton("Log These Files");
				btnNewButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						System.getenv("APPDATA");
						List<String> lines = listt;
						loggedfile = ""+ext.getText()+"_FilesLog.txt";
						Path file = Paths.get(loggedfile);
						completepath = file.getFileName().toAbsolutePath().toString();
						
						
						try {
							Files.write(file, lines, Charset.forName("UTF-8"));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						lblNewLabel_3.setText("LOG File Created SUCCESSFULLY!");
					}
				});
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					}
				});
				btnNewButton.setBounds(10, 533, 424, 23);
				getContentPane().add(btnNewButton);
				
				JButton btnUploadTheLogged = new JButton("Upload the Logged File");
				btnUploadTheLogged.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					}
				});
				btnUploadTheLogged.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						try {
							ftp_upload object = new ftp_upload();
							object.setBounds(550, 250,280, 270);
							object.setVisible(true);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				btnUploadTheLogged.setBounds(10, 567, 424, 23);
				getContentPane().add(btnUploadTheLogged);
				
				btnNewButton_1 = new JButton("Quick System Lookup");
				btnNewButton_1.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
						int result = fileChooser.showOpenDialog(null);
						dir.setText(fileChooser.getCurrentDirectory().getAbsolutePath().toString());
					}
				});
				btnNewButton_1.setBounds(10, 99, 424, 23);
				getContentPane().add(btnNewButton_1);
				
				JLabel lblNewLabel_2 = new JLabel("* Navigate INTO the Desired Directory, and press CANCEL to fetch its");
				lblNewLabel_2.setForeground(new Color(0, 0, 139));
				lblNewLabel_2.setBounds(10, 125, 424, 20);
				getContentPane().add(lblNewLabel_2);
				
				JLabel lblPathAutomatically = new JLabel("   PATH automatically.");
				lblPathAutomatically.setForeground(new Color(0, 0, 139));
				lblPathAutomatically.setBounds(10, 146, 424, 14);
				getContentPane().add(lblPathAutomatically);
				
				lblNewLabel_3 = new JLabel("");
				lblNewLabel_3.setForeground(new Color(60, 179, 113));
				lblNewLabel_3.setBounds(10, 601, 424, 19);
				getContentPane().add(lblNewLabel_3);

	}
	public void search(String directory, String extension)
	{
		File folder = new File(directory);
		  File[] listOfFiles = folder.listFiles();
		  newlist = new File[listOfFiles.length];
		   listt = new ArrayList<String>();

		   getFile(directory,extension);
		list.setListData(listt.toArray());
		list.removeAll();
	}
	public static void getFile(String directoryName,String extension) {
	    File directory = new File(directoryName);
	    File[] fList = directory.listFiles();
	    if(fList!=null){
	        for (File file : fList) {
	            if (file.isFile()&&   (file.getName().substring(file.getName().lastIndexOf('.')+1).equals(extension))) {
	                listt.add(file.toString());
	                list.setListData(listt.toArray());
	        		//list.removeAll();
	            } else if (file.isDirectory()) {
	                getFile(file.getAbsolutePath(),extension);
	            }
	        }
	    }
	    }
	public static void main(String [] args)
	{
		searching obj = new searching();
		obj.setBounds(450, 60, 460, 660);
		obj.setVisible(true);
	}
}
