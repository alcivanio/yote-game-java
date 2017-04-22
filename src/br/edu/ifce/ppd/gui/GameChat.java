package br.edu.ifce.ppd.gui;

import br.edu.ifce.ppd.models.GameChatElement;
import br.edu.ifce.ppd.util.MyColors;
import br.edu.ifce.ppd.util.MyFonts;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by alcivanio on 14/04/17.
 */
public class GameChat {

    public final String SEND_FIELD_PLACEHOLDER   = "Escrever mensagem";

    public JPanel chatPanel;
    private JPanel messagesPanel;
    public JScrollPane chatScrollPane;
    public JTextField sendField;
    public JButton sendButton;
    public ArrayList<GameChatElement> elements;


    public GameChat() {
        int posX    = 325;
        int posY    = 108;
        int width   = 349;
        int height  = 403;

        chatPanel = new JPanel();
        chatPanel.setBounds(posX, posY, width, height);
        chatPanel.setLayout(null);
        chatPanel.setBackground(Color.white);

        viewDidLoad();
    }

    private void viewDidLoad() {
        messagesPanel = new JPanel();
        messagesPanel.setLayout(new BoxLayout(messagesPanel, BoxLayout.Y_AXIS));
        messagesPanel.setLocation(0,0);
        messagesPanel.setBackground(Color.white);
        setGraphicalElements();




        /*GameChatElement el = new GameChatElement(false, "TEXTO EXEMPLO");
        addNewMessage(el);
        GameChatElement el2 = new GameChatElement(true, "TEXTO EXEMPLO");
        addNewMessage(el2);
        addNewMessage(el2);
        addNewMessage(el2);
        addNewMessage(el2);*/

    }

    private void setGraphicalElements() {
        setControlElements();
        setMessagesScroller();
    }

    private void setMessagesScroller() {
        int posX    = 0;
        int posY    = 0;
        int width   = 349;
        int height  = 338;

        chatScrollPane = new JScrollPane(messagesPanel);
        chatScrollPane.setBounds(posX, posY, width, height);
        chatScrollPane.setBackground(Color.white);
        chatScrollPane.setBorder(BorderFactory.createEmptyBorder());
        chatScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        chatPanel.add(chatScrollPane);
    }

    private void setControlElements() {
        setFieldElement();
        setSentElement();
    }

    private void setFieldElement() {
        int posX    = 0;
        int posY    = 340;
        int width   = 274;
        int height  = 61;

        sendField = new JTextField();
        sendField.setBounds(posX, posY, width, height);
        sendField.setBackground(MyColors.eee);
        sendField.setForeground(MyColors.six);
        sendField.setText(SEND_FIELD_PLACEHOLDER);
        sendField.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        sendField.setFont(MyFonts.systemNormal);
        chatPanel.requestFocus();
        sendField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (sendField.getText().equals(SEND_FIELD_PLACEHOLDER)) {
                    sendField.setText("");
                    sendField.setForeground(MyColors.two);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (sendField.getText().isEmpty()) {
                    sendField.setForeground(MyColors.six);
                    sendField.setText(SEND_FIELD_PLACEHOLDER);
                }
            }
        });

        chatPanel.add(sendField);
    }

    private void setSentElement() {
        int posX    = 284;
        int posY    = 338;
        int width   = 65;
        int height  = 65;

        try {
            BufferedImage buttonIcon = ImageIO.read(new File("src/resources/next_icon.png"));
            ImageIcon imgIcon = new ImageIcon(buttonIcon);
            Image imgScaled = imgIcon.getImage().getScaledInstance(65, 65,Image.SCALE_DEFAULT);
            imgIcon = new ImageIcon(imgScaled);
            sendButton = new JButton(imgIcon);
        } catch (IOException e){
            String workingDir = System.getProperty("user.dir");
            System.out.println("Current working directory : " + workingDir);
            e.printStackTrace();

        }
        sendButton.setBounds(posX, posY, width, height);
        sendButton.setBackground(MyColors.blue);

        sendButton.setBorderPainted(false);
        sendButton.setFocusPainted(false);
        sendButton.setContentAreaFilled(false);

        chatPanel.add(sendButton);
    }

    public void addNewMessage(GameChatElement element) {
        int posX    = element.fromCurrentUser ? 70 : 0;
        int posY    = 0;
        int width   = 274;
        int height  = 61;
        int mHeight = 1000000;

        if (element.text == SEND_FIELD_PLACEHOLDER) {return;}

        Color cColor = element.fromCurrentUser ? MyColors.blue : MyColors.eee;
        Color fColor = element.fromCurrentUser ? Color.white : MyColors.two;
        ComponentOrientation orientation    = element.fromCurrentUser ? ComponentOrientation.RIGHT_TO_LEFT : ComponentOrientation.LEFT_TO_RIGHT;
        float alignment                     = element.fromCurrentUser ? JTextArea.LEFT_ALIGNMENT : JTextArea.RIGHT_ALIGNMENT;

        JTextArea tArea = new JTextArea();
        tArea.setBounds(posX, posY, width, height);
        tArea.setMaximumSize(new Dimension(width, mHeight));
        tArea.setAlignmentX(JTextArea.LEFT_ALIGNMENT);

        tArea.setBackground(cColor);
        tArea.setForeground(fColor);

        tArea.setComponentOrientation(orientation);
        tArea.setText(element.text);

        tArea.setLineWrap(true);
        tArea.setWrapStyleWord(true);
        tArea.setFont(MyFonts.systemNormal);
        tArea.setMargin(new Insets(20, 20, 20, 20));
        tArea.setEditable(false);
        tArea.add(Box.createRigidArea(new Dimension(100, 100)));


        //resizing the text view.
        Dimension preferredSize = tArea.getPreferredSize();
        //tArea.setBounds(posX, posY, width, preferredSize.height);

        messagesPanel.add(tArea);
        messagesPanel.add(Box.createRigidArea(new Dimension(0,10)));




    }


}




