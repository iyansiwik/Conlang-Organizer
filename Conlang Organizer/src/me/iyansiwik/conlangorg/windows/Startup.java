package me.iyansiwik.conlangorg.windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import me.iyansiwik.conlangorg.Path;
import me.iyansiwik.conlangorg.Reference;
import me.iyansiwik.conlangorg.windows.utilities.MotionTabbedPane;

public class Startup {

	private JFrame frame;

	private Thread thread;
	private JTextField txtLanguageName;
	private JTextField txtProjectPath;
	private JTextField txtOpenPath;
	
	public Startup() {
		thread = new Thread() {
			public void run() {
				initialize();
			}
		};
		thread.start();
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 480, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		try {
			frame.setIconImage(ImageIO.read(new File("res/logo.png")));
		} catch (IOException e3) {
			e3.printStackTrace();
		}
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		File appDir = new File(Path.getDataFolder());
		if(!appDir.exists()) appDir.mkdirs();
		File recent = new File(Path.getDataFolder()+"\\recent.dat");
		if(!recent.exists())
			try {
				recent.createNewFile();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		System.out.println(appDir.getPath());
		System.out.println(recent.getPath());
		
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		frame.setBackground(new Color(0x00ffffff, true));
		JTabbedPane tabbedPane = new MotionTabbedPane(JTabbedPane.TOP, frame);
		tabbedPane.setBounds(0, 0, 480, 300);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panelWelcome = new JPanel();
		tabbedPane.addTab("Welcome", null, panelWelcome, null);
		panelWelcome.setLayout(null);
		
		JButton btnCloseWelcome = new JButton("Close");
		btnCloseWelcome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					frame.dispose();
					thread.join();
				} catch(InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnCloseWelcome.setBounds(376, 238, 89, 23);
		panelWelcome.add(btnCloseWelcome);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnUpdate.setBounds(10, 238, 89, 23);
		panelWelcome.add(btnUpdate);
		
		boolean updateExists = false; // TODO Look this up instead of arbitrarily deciding this if possible.
		
		if(updateExists) {
			JLabel lblUpdateAvailable = new JLabel("Update Available");
			lblUpdateAvailable.setForeground(new Color(50, 205, 50));
			lblUpdateAvailable.setBounds(109, 242, 132, 14);
			panelWelcome.add(lblUpdateAvailable);
			
			btnUpdate.setEnabled(true);
		} else {
			JLabel lblNoUpdatesDetected = new JLabel("No Updates Detected");
			lblNoUpdatesDetected.setForeground(Color.GRAY);
			lblNoUpdatesDetected.setBounds(109, 242, 118, 14);
			panelWelcome.add(lblNoUpdatesDetected);
			
			btnUpdate.setEnabled(false);
		}
		
		JLabel lblCreatedByIyan = new JLabel("Created by Iyan Siwik");
		lblCreatedByIyan.setForeground(Color.BLACK);
		lblCreatedByIyan.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCreatedByIyan.setBounds(290, 11, 175, 14);
		panelWelcome.add(lblCreatedByIyan);
		
		JLabel lblVersionWelcome = new JLabel("Version "+Reference.getVersion());
		lblVersionWelcome.setHorizontalAlignment(SwingConstants.LEFT);
		lblVersionWelcome.setBounds(10, 26, 112, 14);
		panelWelcome.add(lblVersionWelcome);
		
		JLabel lblTitleWelcome = new JLabel(Reference.TITLE);
		lblTitleWelcome.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitleWelcome.setForeground(Color.BLACK);
		lblTitleWelcome.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTitleWelcome.setBounds(10, 11, 175, 14);
		panelWelcome.add(lblTitleWelcome);
		
		
		JPanel panelNew = new JPanel();
		tabbedPane.addTab("New", null, panelNew, null);
		panelNew.setLayout(null);
		
		JLabel lblNew = new JLabel("New Conlang");
		lblNew.setBounds(69, 11, 337, 70);
		panelNew.add(lblNew);
		lblNew.setHorizontalAlignment(SwingConstants.CENTER);
		lblNew.setFont(new Font("Tahoma", Font.BOLD, 28));
		
		JButton btnCloseNew = new JButton("Close");
		btnCloseNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					frame.dispose();
					thread.join();
				} catch(InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnCloseNew.setBounds(376, 238, 89, 23);
		panelNew.add(btnCloseNew);
		
		txtLanguageName = new JTextField();
		txtLanguageName.setToolTipText("This is the name of your conlang. You can change it later.");
		txtLanguageName.setHorizontalAlignment(SwingConstants.CENTER);
		txtLanguageName.setBounds(132, 112, 210, 20);
		panelNew.add(txtLanguageName);
		txtLanguageName.setColumns(10);
		
		JLabel lblConlangName = new JLabel("Conlang Name");
		lblConlangName.setBounds(132, 99, 104, 14);
		panelNew.add(lblConlangName);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtProjectPath.getText().isEmpty() || txtLanguageName.getText().isEmpty()) return;
				File dir = new File(txtProjectPath.getText());
				if(!dir.exists()) dir.mkdir();
				if(dir.listFiles().length > 0) {
					JOptionPane.showMessageDialog(frame, "This directory is not empty. Please choose an empty directory as to prevent accidental overwriting.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				File file = new File(txtProjectPath.getText()+"\\"+txtLanguageName.getText()+".conlang");
				try {
					file.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				openProject(txtLanguageName.getText(), txtProjectPath.getText());
			}
		});
		btnCreate.setBounds(10, 238, 89, 23);
		panelNew.add(btnCreate);
		
		txtProjectPath = new JTextField();
		txtProjectPath.setToolTipText("The directory in which your conlang's files will be saved.");
		txtProjectPath.setBounds(132, 156, 133, 20);
		panelNew.add(txtProjectPath);
		txtProjectPath.setColumns(10);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if(chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
					txtProjectPath.setText(chooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		btnBrowse.setBounds(275, 155, 67, 22);
		panelNew.add(btnBrowse);
		
		JLabel lblProjectPath = new JLabel("Project Directory");
		lblProjectPath.setBounds(132, 143, 104, 14);
		panelNew.add(lblProjectPath);
		
		JLabel label = new JLabel("Created by Iyan Siwik");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setForeground(Color.BLACK);
		label.setBounds(290, 11, 175, 14);
		panelNew.add(label);
		
		JLabel lblTitleNew = new JLabel(Reference.TITLE);
		lblTitleNew.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTitleNew.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitleNew.setForeground(Color.BLACK);
		lblTitleNew.setBounds(10, 11, 175, 14);
		panelNew.add(lblTitleNew);
		
		JLabel lblVersionNew = new JLabel("Version "+Reference.getVersion());
		lblVersionNew.setHorizontalAlignment(SwingConstants.LEFT);
		lblVersionNew.setBounds(10, 26, 112, 14);
		panelNew.add(lblVersionNew);
		
		JPanel panelOpen = new JPanel();
		tabbedPane.addTab("Open", null, panelOpen, null);
		panelOpen.setLayout(null);
		
		JLabel lblOpen = new JLabel("Open Conlang");
		lblOpen.setHorizontalAlignment(SwingConstants.CENTER);
		lblOpen.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblOpen.setBounds(69, 11, 337, 70);
		panelOpen.add(lblOpen);
		
		JButton btnCloseOpen = new JButton("Close");
		btnCloseOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					frame.dispose();
					thread.join();
				} catch(InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnCloseOpen.setBounds(376, 238, 89, 23);
		panelOpen.add(btnCloseOpen);
		
		JList<String> list = new JList<String>();
		list.setBorder(new LineBorder(new Color(171, 173, 179)));
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				txtOpenPath.setText(list.getSelectedValue());
			}
		});
		list.setVisibleRowCount(8);
		try {
			list.setModel(new AbstractListModel<String>() {
				private static final long serialVersionUID = 1L;
				String[] values = getRecentProjects();
				public int getSize() {
					return values.length;
				}
				public String getElementAt(int index) {
					return values[index];
				}
			});
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(132, 82, 210, 129);
		panelOpen.add(list);
		
		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(txtOpenPath.getText().isEmpty()) return;
				String[] path = txtOpenPath.getText().split("\\\\");
				String name = path[path.length-1];
				name = name.substring(0, name.length()-8); // ".conlang" has 8 chars
				openProject(name, txtOpenPath.getText().substring(0, txtOpenPath.getText().length()-8-name.length()-1));
			}
		});
		btnOpen.setBounds(10, 238, 89, 23);
		panelOpen.add(btnOpen);
		
		txtOpenPath = new JTextField();
		txtOpenPath.setBounds(132, 215, 133, 20);
		panelOpen.add(txtOpenPath);
		txtOpenPath.setColumns(10);
		
		JButton btnBrowseOpen = new JButton("Browse");
		btnBrowseOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setFileFilter(new FileNameExtensionFilter("Conlang Files (.conlang)", "conlang"));
				if(chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					txtOpenPath.setText(chooser.getSelectedFile().getPath());
				}
			}
		});
		btnBrowseOpen.setBounds(275, 214, 67, 22);
		panelOpen.add(btnBrowseOpen);
		
		JLabel lblTitleOpen = new JLabel("Created by Iyan Siwik");
		lblTitleOpen.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTitleOpen.setForeground(Color.BLACK);
		lblTitleOpen.setBounds(290, 11, 175, 14);
		panelOpen.add(lblTitleOpen);
		
		JLabel lblTitel = new JLabel(Reference.TITLE);
		lblTitel.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitel.setForeground(Color.BLACK);
		lblTitel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTitel.setBounds(10, 11, 175, 14);
		panelOpen.add(lblTitel);
		
		JLabel lblVersionOpen = new JLabel("Version "+Reference.getVersion());
		lblVersionOpen.setHorizontalAlignment(SwingConstants.LEFT);
		lblVersionOpen.setBounds(10, 26, 112, 14);
		panelOpen.add(lblVersionOpen);
		
		frame.setVisible(true);
	}
	
	private void openProject(String projectName, String projectPath) {
		try {
			appendRecents(8, projectPath+"\\"+projectName+".conlang");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		frame.dispose();
		new ProjectEditor(projectName, projectPath, thread);
	}
	
	private void appendRecents(int maxRecents, String newRecent) throws FileNotFoundException, IOException {
		String recentFile = Path.getDataFolder()+"\\recent.dat";
		
		List<String> recents = new ArrayList<String>();
		Scanner reader = new Scanner(new File(recentFile));
		while(reader.hasNextLine()) {
			recents.add(reader.nextLine());
		}
		reader.close();
		
		for(int i=0;i<recents.size();i++) {
			if(recents.get(i).equalsIgnoreCase(newRecent)) {
				recents.remove(i);
			}
		}
		
		while(recents.size() >= maxRecents) {
			recents.remove(recents.size()-1);
		}

		new File(recentFile).delete();
		new File(recentFile).createNewFile();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(recentFile));
		writer.write(newRecent);
		for(String recent : recents) {
			writer.newLine();
			writer.write(recent);
		}
		writer.close();
	}
	
	private String[] getRecentProjects() throws FileNotFoundException {
		List<String> recents = new ArrayList<String>();
		Scanner reader = new Scanner(new File(Path.getDataFolder()+"\\recent.dat"));
		while(reader.hasNextLine()) {
			recents.add(reader.nextLine());
		}
		reader.close();
		String[] array = new String[recents.size()];
		for(int i=0;i<recents.size();i++) {
			array[i] = recents.get(i);
		}
		return array;
	}
}
