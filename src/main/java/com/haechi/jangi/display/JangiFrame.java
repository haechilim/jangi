package com.haechi.jangi.display;

import com.haechi.jangi.core.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JangiFrame extends JFrame implements ActionListener {
    private Dimension frameDim;
    private Dimension boardDim;

    private Button exit;
    private JangiBoard jangiBoard;
    private Board board;

    public JangiFrame(String title, Board board) throws HeadlessException {
        super(title);
        
        this.board = board;
    }

    public void init() {
        //setUndecorated(true);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBounds(0, 0, 1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(49, 46, 43));

        setLayout(null);
        initDimensions();
        makeComponents();
        setVisible(true);
    }

    public void redraw() {
        jangiBoard.redraw();
    }

    private void initDimensions() {
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        //frameDim = new Dimension(screenDim.width, screenDim.height - 60);
        frameDim = new Dimension(getWidth(), getHeight());

        boardDim = new Dimension();
        boardDim.height = frameDim.height - 80;
        boardDim.width = boardDim.height;
    }

    private void makeComponents() {
        jangiBoard = new JangiBoard(board);
        jangiBoard.setBounds(frameDim.width/2 - boardDim.width/2,
                frameDim.height/2 - boardDim.height/2 - 20,
                boardDim.width, boardDim.height);
        jangiBoard.init();
        add(jangiBoard);

        exit = new Button("Exit");
        exit.setBounds(frameDim.width - 100, 10, 90, 60);
        exit.addActionListener(this);
        //add(exit);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == exit) dispose();
    }
}
