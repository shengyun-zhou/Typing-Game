package buptsse.zero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameInterface
{
    private String PlayerName;
    private ArrayList<String> MultiRowText;
    private JFrame GameWindow = null;
    private Box MainBox = null;
    private int WindowTotalHeight = 0;
    private boolean PlayingFlag = false;
    private boolean StartFlag = false;

    private final int VERTICAL_MARGIN = 10;
    private final int HORIZONTAL_MARGIN = 10;
    private final int WINDOW_WIDTH = 1000;
    private static final float TITLE_FONT_SIZE = (float)30.0;

    private static ImageIcon IconOK = null;
    private static ImageIcon IconError = null;
    private static ImageIcon IconStart = null;
    private static ImageIcon IconPause = null;

    public static String LABEL_QUIT = "Quit";
    public static String LABEL_START = "Start";
    public static String LABEL_CONTINUE = "Continue";
    public static String LABEL_FINISH = "Finish";
    public static String LABEL_PAUSE = "Pause";
    public static String LABEL_REPLAY = "Replay";
    public static String MESSAGE_INPUT_ERROR = "Your input contains error(s), please check it!";
    public static String MESSAGE_CONGRATULATION = "Congratulation!";
    public static String MESSAGE_COMPLETE = "You haved completed all inputs correctly.";
    public static String MESSAGE_PLAYER_TIME = "Your time";

    private void initWindow() {
        MainBox = Box.createVerticalBox();
        MainBox.add(Box.createVerticalStrut(VERTICAL_MARGIN));

        //Title Area
        Box TitleBox = Box.createHorizontalBox();
        TitleBox.add(Box.createHorizontalStrut(HORIZONTAL_MARGIN));
        TitleBox.setAlignmentY((float) 1.0);
        JLabel TitleLable = new JLabel(MainInterface.PRODUCT_NAME);
        TitleLable.setFont(MainInterface.GlobalFont.deriveFont(Font.BOLD, TITLE_FONT_SIZE));
        TitleBox.add(TitleLable);
        TitleBox.add(Box.createHorizontalGlue());
        final JLabel PlayerNameLabel = new JLabel(PlayerName, new ImageIcon(GameInterface.class.getResource("res/icon/icon-person.png")), 0);
        PlayerNameLabel.setFont(MainInterface.GlobalFont);
        TitleBox.add(PlayerNameLabel);
        TitleBox.add(Box.createHorizontalStrut(HORIZONTAL_MARGIN));
        final JLabel TimeLabel = new JLabel("00:00:00", new ImageIcon(GameInterface.class.getResource("res/icon/icon-clock.png")), 0);
        TimeLabel.setFont(MainInterface.GlobalFont);
        TitleBox.add(TimeLabel);
        TitleBox.add(Box.createHorizontalStrut(HORIZONTAL_MARGIN));
        MainBox.add(TitleBox);
        MainBox.add(Box.createVerticalStrut(VERTICAL_MARGIN));
        GameWindow.add(MainBox, BorderLayout.NORTH);

        final Chronometer GameChronometer = new Chronometer(TimeLabel);
        GameChronometer.reset();

        //Load Text
        final AutoCheckDocument AutoChecker[] = new AutoCheckDocument[MultiRowText.size()];
        JLabel TextLabel[] = new JLabel[MultiRowText.size()];
        JTextField InputField[] = new JTextField[MultiRowText.size()];
        ScrollView ScrollTextView = new ScrollView();
        Box ScrollBox = Box.createVerticalBox();
        ScrollBox.add(Box.createVerticalStrut(VERTICAL_MARGIN));
        int TotalHeight = 0;
        for(int i = 0; i < MultiRowText.size(); i++)
        {
            Box TextLabelBox = Box.createHorizontalBox();
            TextLabelBox.add(Box.createHorizontalStrut(HORIZONTAL_MARGIN));
            TextLabel[i] = new JLabel(MultiRowText.get(i));
            TextLabel[i].setFont(MainInterface.GlobalFont);
            TextLabelBox.add(TextLabel[i]);
            TextLabelBox.add(Box.createHorizontalGlue());

            Box InputFieldBox = Box.createHorizontalBox();
            InputFieldBox.add(Box.createHorizontalStrut(HORIZONTAL_MARGIN));
            InputField[i] = new JTextField();
            InputField[i].setFont(MainInterface.GlobalFont);
            InputField[i].setMargin(new Insets(2, 4, 2, 4));
            InputFieldBox.add(InputField[i]);
            InputFieldBox.add(Box.createHorizontalStrut(HORIZONTAL_MARGIN));
            JLabel IconIndicator = new JLabel(IconError);
            InputFieldBox.add(IconIndicator);
            InputFieldBox.add(Box.createHorizontalStrut(HORIZONTAL_MARGIN));
            AutoChecker[i] = new AutoCheckDocument(MultiRowText.get(i), IconIndicator, IconOK, IconError);
            InputField[i].setDocument(AutoChecker[i]);

            ScrollBox.add(TextLabelBox);
            ScrollBox.add(Box.createVerticalStrut(VERTICAL_MARGIN / 2));
            ScrollBox.add(InputFieldBox);
            if(i < MultiRowText.size() - 1)
                ScrollBox.add(Box.createVerticalStrut(VERTICAL_MARGIN * 2));
        };
        ScrollBox.add(Box.createVerticalStrut(VERTICAL_MARGIN));
        TotalHeight = ScrollBox.getMinimumSize().height;
        //System.out.println("Scroll Box Height=" + ScrollBox.getMinimumSize().height);
        ScrollTextView.setContentBox(ScrollBox);
        ScrollTextView.setContentHeight(TotalHeight);
        ScrollTextView.setViewportView(ScrollBox);
        GameWindow.add(ScrollTextView, BorderLayout.CENTER);

        //Button Area
        Box ButtonAreaBox = Box.createVerticalBox();
        ButtonAreaBox.add(Box.createVerticalStrut(VERTICAL_MARGIN));
        Box ButtonBox = Box.createHorizontalBox();
        ButtonBox.add(Box.createHorizontalStrut(HORIZONTAL_MARGIN));

        JButton QuitButton = new JButton(LABEL_QUIT, new ImageIcon(GameInterface.class.getResource("res/icon/icon-quit.png")));
        QuitButton.setFont(MainInterface.GlobalFont);
        QuitButton.setMargin(new Insets(2, 10, 2, 10));
        ButtonBox.add(QuitButton);
        ButtonBox.add(Box.createHorizontalGlue());

        final JButton ControlButton = new JButton(LABEL_START, new ImageIcon(GameInterface.class.getResource("res/icon/icon-start.png")));
        ControlButton.setFont(MainInterface.GlobalFont);
        ControlButton.setMargin(new Insets(2, 40, 2, 40));
        ButtonBox.add(ControlButton);
        ButtonBox.add(Box.createHorizontalStrut(HORIZONTAL_MARGIN));

        final JButton FinishButton = new JButton(LABEL_FINISH, new ImageIcon(GameInterface.class.getResource("res/icon/icon-finish.png")));
        FinishButton.setFont(MainInterface.GlobalFont);
        FinishButton.setMargin(new Insets(2, 15, 2, 15));
        FinishButton.setEnabled(false);
        ButtonBox.add(FinishButton);
        ButtonBox.add(Box.createHorizontalStrut(HORIZONTAL_MARGIN));

        final JButton ReplayButton = new JButton(LABEL_REPLAY, new ImageIcon(GameInterface.class.getResource("res/icon/icon-replay.png")));
        ReplayButton.setFont(MainInterface.GlobalFont);
        ReplayButton.setMargin(new Insets(2, 10, 2, 10));
        ButtonBox.add(ReplayButton);
        ButtonBox.add(Box.createHorizontalStrut(HORIZONTAL_MARGIN));

        ButtonAreaBox.add(ButtonBox);
        ButtonAreaBox.add(Box.createVerticalStrut(VERTICAL_MARGIN));
        GameWindow.add(ButtonAreaBox, BorderLayout.SOUTH);

        //Key Listener
        for(int i = 0; i < InputField.length; i++)
        {
            final JTextField PreInput;
            final JTextField NextInput;
            if(i > 0)
                PreInput = InputField[i - 1];
            else
                PreInput = InputField[InputField.length - 1];
            if(i < InputField.length - 1)
                NextInput = InputField[i + 1];
            else
                NextInput = InputField[0];
            InputField[i].addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e)
                {
                    if(!PlayingFlag)
                    {
                        if(e.getKeyChar() == KeyEvent.VK_ENTER)
                            ControlButton.doClick();
                        else
                        {
                            e.consume();
                            return;
                        }
                    }
                    else if(PlayingFlag && e.getKeyChar() == KeyEvent.VK_ENTER)
                        FinishButton.doClick();
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.isControlDown() || e.isAltDown())         //exclude the Ctrl and Alt keys
                    {
                        e.consume();
                        return;
                    }
                    if (e.getKeyCode() == KeyEvent.VK_DOWN)
                        NextInput.requestFocus();
                    else if(e.getKeyCode() == KeyEvent.VK_UP)
                        PreInput.requestFocus();
                    else if(PlayingFlag == false)
                    {
                        if(e.getKeyChar() != KeyEvent.VK_ENTER)
                            e.consume();
                    }
                    else
                    {
                        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
                            ControlButton.doClick();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    if (e.isControlDown() || e.isAltDown())
                        e.consume();
                }
            });
        }

        //Button Listener
        ControlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!StartFlag)
                {
                    StartFlag = true;
                    PlayingFlag = true;
                    FinishButton.setEnabled(true);
                    GameChronometer.start();
                    ControlButton.setText(LABEL_PAUSE);
                    ControlButton.setIcon(IconPause);
                }
                else if(StartFlag && PlayingFlag)
                {
                    GameChronometer.pause();
                    PlayingFlag = false;
                    ControlButton.setIcon(IconStart);
                    ControlButton.setText(LABEL_CONTINUE);
                }
                else if(StartFlag && !PlayingFlag)
                {
                    GameChronometer.start();
                    PlayingFlag = true;
                    ControlButton.setText(LABEL_PAUSE);
                    ControlButton.setIcon(IconPause);
                }
            }
        });

        QuitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameChronometer.pause();
                GameWindow.dispose();
            }
        });

        ReplayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(JTextField input : InputField)
                    input.setText("");
                ControlButton.setText(LABEL_START);
                ControlButton.setIcon(IconStart);
                ControlButton.setEnabled(true);
                FinishButton.setEnabled(false);
                GameChronometer.reset();
                StartFlag = PlayingFlag = false;
            }
        });

        FinishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                boolean ContinueFlag = false;
                if(PlayingFlag) {
                    GameChronometer.pause();
                    ContinueFlag = true;
                }
                for(AutoCheckDocument checker : AutoChecker)
                {
                    if(checker.getCheckStatus() == false)
                    {
                        JOptionPane.showMessageDialog(GameWindow, MESSAGE_INPUT_ERROR, MainInterface.PRODUCT_NAME, JOptionPane.CLOSED_OPTION,
                                new ImageIcon(MainInterface.class.getResource("res/icon/dialog-error.png")));
                        if(ContinueFlag)
                            GameChronometer.start();
                        return;
                    }
                }
                JOptionPane.showMessageDialog(GameWindow, MESSAGE_CONGRATULATION + PlayerNameLabel.getText() + ".\n" + MESSAGE_COMPLETE + '\n' + MESSAGE_PLAYER_TIME
                                + ':' + GameChronometer.getTimeString() + '.',
                        MainInterface.PRODUCT_NAME, JOptionPane.OK_OPTION,
                        new ImageIcon(MainInterface.class.getResource("res/icon/dialog-information.png")));
                ControlButton.setEnabled(false);
                FinishButton.setEnabled(false);
            }
        });

        //Window Close Listener
        GameWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GameChronometer.pause();
            }
        });

        WindowTotalHeight = MainBox.getMinimumSize().height + ScrollBox.getMinimumSize().height + ButtonAreaBox.getMinimumSize().height + 20;
    }


    GameInterface(String PlayerName, ArrayList<String> text)
    {
        this.PlayerName = PlayerName;
        this.MultiRowText = text;
        if(IconError == null)
            IconError = new ImageIcon(GameInterface.class.getResource("res/icon/icon-error.png"));
        if(IconOK == null)
            IconOK = new ImageIcon(GameInterface.class.getResource("res/icon/icon-ok.png"));
        if(IconStart == null)
            IconStart = new ImageIcon(GameInterface.class.getResource("res/icon/icon-start.png"));
        if(IconPause == null)
            IconPause = new ImageIcon(GameInterface.class.getResource("res/icon/icon-pause.png"));
    }

    public void show()
    {
        GameWindow = new JFrame(MainInterface.PRODUCT_NAME);
        initWindow();
        GameWindow.setVisible(true);
        WindowTotalHeight += GameWindow.getInsets().top;
        if(WindowTotalHeight > MainInterface.ScreenSize.height - 50)
            WindowTotalHeight = MainInterface.ScreenSize.height - 50;
        GameWindow.setSize(WINDOW_WIDTH, WindowTotalHeight);
        GameWindow.setLocation((MainInterface.ScreenSize.width - GameWindow.getWidth()) / 2, (MainInterface.ScreenSize.height - GameWindow.getHeight()) / 2);
    }
}

