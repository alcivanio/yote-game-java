package br.edu.ifce.ppd.gui;

import br.edu.ifce.ppd.util.MyColors;
import br.edu.ifce.ppd.util.MyFonts;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by alcivanio on 12/04/17.
 */
public class GameHeader {

    JPanel headerPanel;//the main header panel!

    JPanel turnColor;
    JLabel turnLabel;
    JLabel usedLabel;
    JLabel descUserLabel;
    JLabel capturedsLabel;
    JLabel descCapturedsLabel;
    JLabel restartLabel;
    JLabel giveupLabel;

    private int topTextBaseY    = 19;
    private int bottomTextBaseY = 56;


    public GameHeader() {
        setGraphicalElements();
    }

    private void setGraphicalElements() {
        setHeaderElement();
        setTurnElements();
        setUsedsElement();
        setCapturedsElement();
        setRightElements();
    }

    private void setHeaderElement() {
        //position of this specific panel
        int posX    = 0;
        int posY    = 0;
        int sizeW   = 684;
        int sizeH   = 92;

        headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBounds(posX, posY, sizeW, sizeH);
        headerPanel.setBackground(MyColors.eee);
    }

    private void addSeparator(int posX) {
        JPanel separator = new JPanel();
        separator.setBounds(posX, 24, 1, 48);
        separator.setBackground(MyColors.ccc);
        separator.setVisible(true);

        headerPanel.add(separator);
    }

    private void setTurnElements() {
        setTurnCircle();
        setDescTurnElements();
    }

    private void setTurnCircle() {
        int posX    = 20;
        int posY    = 20;
        int sizeW   = 28;
        int sizeH   = 28;

        turnColor = new JPanel();
        turnColor.setLayout(null);
        turnColor.setBounds(posX, posY, sizeW, sizeH);
        turnColor.setBackground(MyColors.blue);

        headerPanel.add(turnColor);
    }

    private void setDescTurnElements() {
        //position of this specific panel
        int posX    = 14;
        int sizeW   = 90;
        int sizeH   = 25;

        turnLabel = new JLabel();
        turnLabel.setBounds(posX, bottomTextBaseY, sizeW, sizeH);
        turnLabel.setText("It' your turn!");
        turnLabel.setFont(MyFonts.systemDescription);
        turnLabel.setForeground(MyColors.two);

        headerPanel.add(turnLabel);//and finally adding it to the panel
    }

    private void setUsedsElement() {
        addSeparator(118);
        setUsedsElementTop();
        setUsedElementsDesc();
    }

    private void setUsedsElementTop() {
        //position of this specific panel
        int posX    = 133;
        int sizeW   = 50;
        int sizeH   = 46;

        usedLabel = new JLabel();
        usedLabel.setBounds(posX, topTextBaseY, sizeW, sizeH);
        usedLabel.setText("-");
        usedLabel.setFont(MyFonts.systemHeadings);
        usedLabel.setForeground(MyColors.two);

        headerPanel.add(usedLabel);//and finally adding it to the panel
    }

    private void setUsedElementsDesc() {
        int posX    = 133;
        int sizeW   = 50;
        int sizeH   = 25;

        descUserLabel = new JLabel();
        descUserLabel.setBounds(posX, bottomTextBaseY, sizeW, sizeH);
        descUserLabel.setText("usadas");
        descUserLabel.setFont(MyFonts.systemDescription);
        descUserLabel.setForeground(MyColors.two);

        headerPanel.add(descUserLabel);
    }

    private void setCapturedsElement() {
        addSeparator(194);
        setCapturedsElementsTop();
        setCapturedsElementsDesc();
    }

    private void setCapturedsElementsTop() {
        //position of this specific panel
        int posX    = 208;
        int sizeW   = 40;
        int sizeH   = 46;

        capturedsLabel = new JLabel();
        capturedsLabel.setBounds(posX, topTextBaseY, sizeW, sizeH);
        capturedsLabel.setText("-");
        capturedsLabel.setFont(MyFonts.systemHeadings);
        capturedsLabel.setForeground(MyColors.two);

        headerPanel.add(capturedsLabel);//and finally adding it to the panel
    }

    private void setCapturedsElementsDesc() {
        int posX    = 208;
        int sizeW   = 75;
        int sizeH   = 25;

        descCapturedsLabel = new JLabel();
        descCapturedsLabel.setBounds(posX, bottomTextBaseY, sizeW, sizeH);
        descCapturedsLabel.setText("capturadas");
        descCapturedsLabel.setFont(MyFonts.systemDescription);
        descCapturedsLabel.setForeground(MyColors.two);

        headerPanel.add(descCapturedsLabel);
    }

    private void setRightElements() {
        addSeparator(593);
        setRestartElement();
        setGiveUpElement();
    }

    private void setRestartElement() {
        //position of this specific panel
        int posX    = 612;
        int posY    = 28;
        int sizeW   = 60;
        int sizeH   = 21;

        restartLabel = new JLabel();
        restartLabel.setBounds(posX, posY, sizeW, sizeH);
        restartLabel.setText("Reiniciar");
        restartLabel.setFont(MyFonts.systemDescription);
        restartLabel.setForeground(MyColors.two);

        headerPanel.add(restartLabel);//and finally adding it to the panel
    }

    private void setGiveUpElement() {
        //position of this specific panel
        int posX    = 612;
        int posY    = 48;
        int sizeW   = 60;
        int sizeH   = 21;

        giveupLabel = new JLabel();
        giveupLabel.setBounds(posX, posY, sizeW, sizeH);
        giveupLabel.setText("Desistir");
        giveupLabel.setFont(MyFonts.systemDescription);
        giveupLabel.setForeground(MyColors.two);

        headerPanel.add(giveupLabel);//and finally adding it to the panel
    }
}