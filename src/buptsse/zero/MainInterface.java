package buptsse.zero;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import com.sun.java.swing.plaf.gtk.GTKLookAndFeel;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

public class MainInterface {
	private static JFrame MainWindow = null;
	private static Box MainBox = null;
	public static Font GlobalFont = null;
	public static Dimension ScreenSize = null;
	
	private static final int WINDOW_WIDTH = 700;
	private static String WINDOW_TITLE = "Typing Game";
	private static final int VERTICAL_MARGIN = 10;
	private static final int HORIZONTAL_MARGIN = 10;
	private static final float TITLE_FONT_SIZE = (float)40.0;
	private static final float TEXT_FONT_SIZE = (float)18.0;
	private static String LABEL_PLAYER_NAME = "Player Name";
	private static String LABEL_INTERNAL_TEXT = "Internal Text(ID number)";
	private static String LABEL_EXTERNAL_TEXT = "External Text File";
	private static String LABEL_BROWSE = "Browse";
	private static String LABEL_ENTER = "Enter";
	private static String LABEL_EXIT = "Exit";
	private static String LABEL_ABOUT = "About";
	private static String TEXT_QUERY_EXIT = "Do you really want to exit?";
	//Set the UI font to the system default font and the theme to the system theme.
	private static void setUI()
	{
		try
		{
			String OSName = System.getProperty("os.name");
			if(OSName.toLowerCase().startsWith("win"))
				UIManager.setLookAndFeel(WindowsLookAndFeel.class.getName());
			else
				UIManager.setLookAndFeel(GTKLookAndFeel.class.getName());
		}catch(Exception e){
			System.err.println("Can't load the system theme.");
		}
		Enumeration<Object> KeyVector = UIManager.getDefaults().keys();
		while(KeyVector.hasMoreElements())
		{
			Object key = KeyVector.nextElement();
			Object value = UIManager.get(key);
			if(value instanceof FontUIResource)
				GlobalFont = ((FontUIResource)value).deriveFont(TEXT_FONT_SIZE);
		}
		
	}
	
	private static void initWindow()
	{
		MainBox = Box.createVerticalBox();
		setUI();
		
		//Title
		Box TitleBox = Box.createVerticalBox();
		TitleBox.setAlignmentX((float)0.5);
		TitleBox.add(Box.createRigidArea(new Dimension(0, VERTICAL_MARGIN)));
		JLabel TitleLabel = new JLabel(WINDOW_TITLE);
		TitleLabel.setFont(GlobalFont.deriveFont(Font.BOLD, TITLE_FONT_SIZE));		
		TitleBox.add(TitleLabel);
		TitleBox.add(Box.createRigidArea(new Dimension(0, VERTICAL_MARGIN)));
		MainBox.add(TitleBox);
		
		//Border
		Box DummyBox = Box.createHorizontalBox();
		DummyBox.add(Box.createRigidArea(new Dimension(HORIZONTAL_MARGIN, 0)));
		Box TextFieldBox = Box.createVerticalBox();
		TextFieldBox.add(Box.createRigidArea(new Dimension(0, VERTICAL_MARGIN)));
		TextFieldBox.setBorder(BorderFactory.createLineBorder(new Color(189, 189, 189)));
		DummyBox.add(TextFieldBox);
		DummyBox.add(Box.createRigidArea(new Dimension(HORIZONTAL_MARGIN, 0)));
		
		//Label Player Name and TextField
		Box PlayerNameInputBox = Box.createHorizontalBox();
		PlayerNameInputBox.add(Box.createRigidArea(new Dimension(HORIZONTAL_MARGIN, 0)));
		JLabel LabelPlayerName = new JLabel(LABEL_PLAYER_NAME + ":");
		LabelPlayerName.setFont(GlobalFont.deriveFont(TEXT_FONT_SIZE));
		PlayerNameInputBox.add(LabelPlayerName);
		PlayerNameInputBox.add(Box.createRigidArea(new Dimension(HORIZONTAL_MARGIN, 0)));
		JTextField PlayerNameInput = new JTextField();
		PlayerNameInput.setFont(GlobalFont.deriveFont(TEXT_FONT_SIZE));
		PlayerNameInput.setMargin(new Insets(2, 4, 2, HORIZONTAL_MARGIN));
		PlayerNameInputBox.add(PlayerNameInput);
		PlayerNameInputBox.add(Box.createRigidArea(new Dimension(HORIZONTAL_MARGIN, 0)));
		TextFieldBox.add(PlayerNameInputBox);
		TextFieldBox.add(Box.createRigidArea(new Dimension(0, VERTICAL_MARGIN)));
		
		
		//RadioButton1 and TextField
		Box Option1Box = Box.createHorizontalBox();
		Option1Box.add(Box.createRigidArea(new Dimension(HORIZONTAL_MARGIN, 0)));
		JRadioButton Option1Radio = new JRadioButton(LABEL_INTERNAL_TEXT + ":");
		Option1Radio.setFont(GlobalFont);
		Option1Radio.setSelected(true);
		Option1Box.add(Option1Radio);
		Option1Box.add(Box.createRigidArea(new Dimension(HORIZONTAL_MARGIN, 0)));
		final JTextField Option1TextField = new JTextField();
		Option1TextField.setMargin(new Insets(2, 4, 2, 4));
		Option1TextField.setFont(GlobalFont);
		Option1Radio.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.DESELECTED)
					Option1TextField.setEnabled(false);
				else 
					Option1TextField.setEnabled(true);
			}
		});
		Option1Box.add(Option1TextField);
		Option1Box.add(Box.createRigidArea(new Dimension(HORIZONTAL_MARGIN, 0)));
		TextFieldBox.add(Option1Box);
		TextFieldBox.add(Box.createRigidArea(new Dimension(0, VERTICAL_MARGIN)));
		
		//RadioButton2 and TextField
		Box Option2Box = Box.createHorizontalBox();
		Option2Box.add(Box.createRigidArea(new Dimension(HORIZONTAL_MARGIN, 0)));
		JRadioButton Option2Radio = new JRadioButton(LABEL_EXTERNAL_TEXT + "(.xml):");
		Option2Radio.setFont(GlobalFont);
		Option2Box.add(Option2Radio);
		Option2Box.add(Box.createRigidArea(new Dimension(HORIZONTAL_MARGIN, 0)));
		final JTextField Option2TextField = new JTextField();
		Option2TextField.setMargin(new Insets(2, 4, 2, 4));
		Option2TextField.setFont(GlobalFont);
		Option2TextField.setEnabled(false);
		Option2Box.add(Option2TextField);
		Option2Box.add(Box.createRigidArea(new Dimension(HORIZONTAL_MARGIN, 0)));
		final JButton BrowseButton = new JButton(LABEL_BROWSE + "...");
		BrowseButton.setIcon(new ImageIcon(MainInterface.class.getResource("res/icon/icon-open.png")));
		BrowseButton.setFont(GlobalFont);
		BrowseButton.setMargin(new Insets(2, 4, 2, 4));
		BrowseButton.setEnabled(false);
		Option2Radio.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.DESELECTED)
				{
					Option2TextField.setEnabled(false);
					BrowseButton.setEnabled(false);
				}
				else
				{
					Option2TextField.setEnabled(true);
					BrowseButton.setEnabled(true);
				}
			}
		});
		final FileDialog FileBrowseDialog = new FileDialog(MainWindow);
		FileBrowseDialog.setMode(FileDialog.LOAD);
		FileBrowseDialog.setTitle(LABEL_BROWSE);
		FileBrowseDialog.setFilenameFilter(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				if(name.toLowerCase().endsWith(".xml"))
					return true;
				return false;
			}
		});
		BrowseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FileBrowseDialog.setVisible(true);
				if(FileBrowseDialog.getFile() != null)
					Option2TextField.setText(FileBrowseDialog.getFile());
			}
		});
		Option2Box.add(BrowseButton);
		Option2Box.add(Box.createRigidArea(new Dimension(HORIZONTAL_MARGIN, 0)));
		TextFieldBox.add(Option2Box);
		TextFieldBox.add(Box.createRigidArea(new Dimension(0, VERTICAL_MARGIN)));
		
		ButtonGroup OptionRadioGroup = new ButtonGroup();
		OptionRadioGroup.add(Option1Radio);
		OptionRadioGroup.add(Option2Radio);
		MainBox.add(DummyBox);
		MainBox.add(Box.createVerticalStrut(VERTICAL_MARGIN));
		
		//Button Area
		Box ButtonBox = Box.createHorizontalBox();
		ButtonBox.add(Box.createHorizontalStrut(HORIZONTAL_MARGIN));
		JButton AboutButton = new JButton(LABEL_ABOUT, new ImageIcon(MainInterface.class.getResource("res/icon/icon-about.png")));
		AboutButton.setFont(GlobalFont);
		AboutButton.setMargin(new Insets(2, 10, 2, 10));
		AboutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(MainWindow, WINDOW_TITLE + "\n" + "Powered by Java Swing\nCopyright© 2015 BUPTSSE-Zero", LABEL_ABOUT, 
											  JOptionPane.INFORMATION_MESSAGE, new ImageIcon(MainInterface.class.getResource("res/icon/dialog-information.png")));
			}
		});
		ButtonBox.add(AboutButton);
		JButton EnterButton = new JButton(LABEL_ENTER, new ImageIcon(MainInterface.class.getResource("res/icon/icon-enter.png")));
		EnterButton.setFont(GlobalFont);
		EnterButton.setMargin(new Insets(2, 20, 2, 20));
		ButtonBox.add(Box.createHorizontalGlue());
		ButtonBox.add(EnterButton);
		ButtonBox.add(Box.createHorizontalStrut(HORIZONTAL_MARGIN));
		JButton ExitButton = new JButton(LABEL_EXIT, new ImageIcon(MainInterface.class.getResource("res/icon/icon-exit.png")));
		ExitButton.setFont(GlobalFont);
		ExitButton.setMargin(new Insets(2, 10, 2, 10));
		ExitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int ret = JOptionPane.showConfirmDialog(MainWindow, TEXT_QUERY_EXIT, WINDOW_TITLE, 
														JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(MainInterface.class.getResource("res/icon/dialog-question.png")));
				if(ret == JOptionPane.OK_OPTION)
					MainWindow.dispose();
			}
		});
		ButtonBox.add(ExitButton);
		ButtonBox.add(Box.createHorizontalStrut(HORIZONTAL_MARGIN));
		MainBox.add(ButtonBox);
		MainBox.add(Box.createVerticalStrut(VERTICAL_MARGIN + 5));
		MainWindow.add(MainBox, BorderLayout.NORTH);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		MainWindow = new JFrame();
		MainWindow.setTitle(WINDOW_TITLE + " - Powered By Java Swing");
		MainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initWindow();
		MainWindow.setVisible(true);
		int TotalHeight = MainBox.getHeight() + MainWindow.getInsets().top;
		MainWindow.setSize(new Dimension(WINDOW_WIDTH, TotalHeight));
		MainWindow.setLocation((ScreenSize.width - MainWindow.getWidth()) / 2, (ScreenSize.height - MainWindow.getHeight()) / 2);
	}

}
