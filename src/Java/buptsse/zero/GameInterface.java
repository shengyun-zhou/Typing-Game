package buptsse.zero;

import buptsse.zero.GlobalSettings;

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
    private Box ScrollBox = null;
    private Box ButtonAreaBox = null;
    private int WindowTotalHeight = 0;
    private boolean PlayingFlag = false;
    private boolean StartFlag = false;

    private final int VERTICAL_MARGIN = 10;
    private final int HORIZONTAL_MARGIN = 10;
    private final int WINDOW_WIDTH = 1000;
    private static final float TITLE_FONT_SIZE = (float)30.0;

    private void initWindow() {
        MainBox = Box.createVerticalBox();
        MainBox.add(Box.createVerticalStrut(VERTICAL_MARGIN));

        //Title Area
        Box TitleBox = Box.createHorizontalBox();
        TitleBox.add(Box.createHorizontalStrut(HORIZONTAL_MARGIN));
        TitleBox.setAlignmentY((float) 1.0);
        JLabel TitleLable = new JLabel(GlobalSettings.WINDOW_TITLE);
        TitleLable.setFont(GlobalSettings.GlobalFont.deriveFont(Font.BOLD, TITLE_FONT_SIZE));
        TitleBox.add(TitleLable);
        TitleBox.add(Box.createHorizontalGlue());
        final JLabel PlayerNameLabel = new JLabel(PlayerName, GlobalSettings.ICON_PERSON, 0);
        PlayerNameLabel.setFont(GlobalSettings.GlobalFont);
        TitleBox.add(PlayerNameLabel);
        TitleBox.add(Box.createHorizontalStrut(HORIZONTAL_MARGIN));
        final JLabel TimeLabel = new JLabel("00:00:00", GlobalSettings.ICON_CLOCK, 0);
        TimeLabel.setFont(GlobalSettings.GlobalFont);
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
        final JTextField InputField[] = new JTextField[MultiRowText.size()];
        ScrollView ScrollTextView = new ScrollView();
        ScrollBox = Box.createVerticalBox();
        ScrollBox.add(Box.createVerticalStrut(VERTICAL_MARGIN));
        int TotalHeight = 0;
        for(int i = 0; i < MultiRowText.size(); i++)
        {
            Box TextLabelBox = Box.createHorizontalBox();
            TextLabelBox.add(Box.createHorizontalStrut(HORIZONTAL_MARGIN));
            TextLabel[i] = new JLabel(MultiRowText.get(i));
            TextLabel[i].setFont(GlobalSettings.GlobalFont);
            TextLabelBox.add(TextLabel[i]);
            TextLabelBox.add(Box.createHorizontalGlue());

            Box InputFieldBox = Box.createHorizontalBox();
            InputFieldBox.add(Box.createHorizontalStrut(HORIZONTAL_MARGIN));
            InputField[i] = new JTextField();
            InputField[i].setFont(GlobalSettings.GlobalFont);
            InputField[i].setMargin(new Insets(2, 4, 2, 4));
            InputFieldBox.add(InputField[i]);
            InputFieldBox.add(Box.createHorizontalStrut(HORIZONTAL_MARGIN));
            JLabel IconIndicator = new JLabel(GlobalSettings.ICON_ERROR);
            InputFieldBox.add(IconIndicator);
            InputFieldBox.add(Box.createHorizontalStrut(HORIZONTAL_MARGIN));
            AutoChecker[i] = new AutoCheckDocument(MultiRowText.get(i), IconIndicator, GlobalSettings.ICON_OK, GlobalSettings.ICON_ERROR);
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
        ButtonAreaBox = Box.createVerticalBox();
        ButtonAreaBox.add(Box.createVerticalStrut(VERTICAL_MARGIN));
        Box ButtonBox = Box.createHorizontalBox();
        ButtonBox.add(Box.createHorizontalStrut(HORIZONTAL_MARGIN));

        JButton QuitButton = new JButton(GlobalSettings.LABEL_QUIT, GlobalSettings.ICON_QUIT);
        QuitButton.setFont(GlobalSettings.GlobalFont);
        QuitButton.setMargin(new Insets(2, 10, 2, 10));
        ButtonBox.add(QuitButton);
        ButtonBox.add(Box.createHorizontalGlue());

        final JButton ControlButton = new JButton(GlobalSettings.LABEL_START, GlobalSettings.ICON_START);
        ControlButton.setFont(GlobalSettings.GlobalFont);
        ControlButton.setMargin(new Insets(2, 40, 2, 40));
        ButtonBox.add(ControlButton);
        ButtonBox.add(Box.createHorizontalStrut(HORIZONTAL_MARGIN));

        final JButton FinishButton = new JButton(GlobalSettings.LABEL_FINISH, GlobalSettings.ICON_FINISH);
        FinishButton.setFont(GlobalSettings.GlobalFont);
        FinishButton.setMargin(new Insets(2, 15, 2, 15));
        FinishButton.setEnabled(false);
        ButtonBox.add(FinishButton);
        ButtonBox.add(Box.createHorizontalStrut(HORIZONTAL_MARGIN));

        final JButton ReplayButton = new JButton(GlobalSettings.LABEL_REPLAY, GlobalSettings.ICON_REPLAY);
        ReplayButton.setFont(GlobalSettings.GlobalFont);
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
                    ControlButton.setText(GlobalSettings.LABEL_PAUSE);
                    ControlButton.setIcon(GlobalSettings.ICON_PAUSE);
                }
                else if(StartFlag && PlayingFlag)
                {
                    GameChronometer.pause();
                    PlayingFlag = false;
                    ControlButton.setIcon(GlobalSettings.ICON_START);
                    ControlButton.setText(GlobalSettings.LABEL_CONTINUE);
                }
                else if(StartFlag && !PlayingFlag)
                {
                    GameChronometer.start();
                    PlayingFlag = true;
                    ControlButton.setText(GlobalSettings.LABEL_PAUSE);
                    ControlButton.setIcon(GlobalSettings.ICON_PAUSE);
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
                ControlButton.setText(GlobalSettings.LABEL_START);
                ControlButton.setIcon(GlobalSettings.ICON_START);
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
                        JOptionPane.showMessageDialog(GameWindow, GlobalSettings.MESSAGE_INPUT_ERROR, GlobalSettings.PRODUCT_NAME, JOptionPane.CLOSED_OPTION,
                                                        GlobalSettings.ICON_DIALOG_ERROR);
                        if(ContinueFlag)
                            GameChronometer.start();
                        return;
                    }
                }
                JOptionPane.showMessageDialog(GameWindow, GlobalSettings.MESSAGE_CONGRATULATION + PlayerNameLabel.getText() + ".\n" + GlobalSettings.MESSAGE_COMPLETE + '\n' + GlobalSettings.MESSAGE_PLAYER_TIME
                                + ':' + GameChronometer.getTimeString() + '.',
                        GlobalSettings.WINDOW_TITLE, JOptionPane.OK_OPTION,  GlobalSettings.ICON_DIALOG_INFO);
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
    }


    GameInterface(String PlayerName, ArrayList<String> text)
    {
        this.PlayerName = PlayerName;
        this.MultiRowText = text;
    }

    public void show()
    {
        GameWindow = new JFrame(GlobalSettings.PRODUCT_NAME);
        initWindow();
        GameWindow.setVisible(true);
        WindowTotalHeight = MainBox.getHeight() + ScrollBox.getHeight() + ButtonAreaBox.getHeight() + GameWindow.getInsets().top;
        if(WindowTotalHeight > GlobalSettings.ScreenSize.height - 50)
            WindowTotalHeight = GlobalSettings.ScreenSize.height - 50;
        GameWindow.setSize(WINDOW_WIDTH, WindowTotalHeight);
        GameWindow.setLocation((GlobalSettings.ScreenSize.width - GameWindow.getWidth()) / 2, (GlobalSettings.ScreenSize.height - GameWindow.getHeight()) / 2);
        GameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}

