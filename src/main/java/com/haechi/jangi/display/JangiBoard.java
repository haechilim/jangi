package com.haechi.jangi.display;

import com.haechi.jangi.core.Board;
import com.haechi.jangi.core.Cell;
import com.haechi.jangi.core.Piece;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class JangiBoard extends Panel implements MouseListener {
    private static final Color colorSelected = new Color(0, 0, 0);
    private static final Color colorMovable = new Color(50, 50,50);
    private static final Color colorLine = new Color(0, 0, 0);

    private Board board;
    private Cell[][] cells;
    private double cellMargin;
    private double cellWidth;
    private double cellHeight;
    private double circleSize;

    private Image offscreen;
    private Image background;
    private Map<String, Image> imagePiecies;

    public JangiBoard(Board board) {
        this.board = board;
        this.cells = board.getCells();
    }

    public void init() {
        imagePiecies = new HashMap<>();

        setLayout(null);
        initDimensions();
        addMouseListener(this);
    }

    private void initDimensions() {
        cellWidth = getWidth() / 9.0;
        cellHeight = getHeight() / 10.0;
        cellMargin = cellWidth * 0.1;
        circleSize = cellHeight / 3.0;
    }

    public void redraw() {
        if(offscreen == null) offscreen = createImage(getWidth(), getHeight());

        Graphics graphics = offscreen.getGraphics();
        drawBackground();
        graphics.drawImage(background, 0, 0, getWidth(), getHeight(), this);

        if(cells == null) return;

        for(int x=0; x<cells.length; x++) {
            for(int y=0; y<cells[x].length; y++) {
                drawSelected(graphics, x, y, cells[x][y]);
                drawPiece(graphics, x, y, cells[x][y]);
                drawMovable(graphics, x, y, cells[x][y]);
            }
        }

        revalidate();
        repaint();
    }

    private void drawBackground() {
        if(background != null) return;

        background = createImage(getWidth(), getHeight());
        Image back = Util.loadImage(getClass().getClassLoader().getResource("woodpattern.jpg"));

        Graphics graphics = background.getGraphics();
        graphics.drawImage(back, 0, 0, getWidth(), getHeight(), null);
        drawLines(graphics);
    }

    private void drawLines(Graphics graphics) {
        int marginX = (int)(cellWidth / 2);
        int marginY = (int)(cellHeight / 2);

        graphics.setColor(colorLine);

        for(double y = marginY; y < getHeight(); y += cellHeight)
            graphics.drawLine(marginX, (int)y, getWidth() - marginX - 1, (int)y);

        for(double x = marginX; x < getWidth(); x += cellWidth)
            graphics.drawLine((int)x, marginY, (int)x, getHeight() - marginY);

        graphics.drawLine((int)(marginX + cellWidth*3), marginY, (int)(marginX + cellWidth*5), (int)(marginY + cellHeight*2));
        graphics.drawLine((int)(marginX + cellWidth*5), marginY, (int)(marginX + cellWidth*3), (int)(marginY + cellHeight*2));

        graphics.drawLine((int)(marginX + cellWidth*3), (int)(getHeight() - marginY - cellHeight*2),
                (int)(marginX + cellWidth*5), (int)(getHeight() - marginY));
        graphics.drawLine((int)(marginX + cellWidth*5), (int)(getHeight() - marginY - cellHeight*2),
                (int)(marginX + cellWidth*3), (int)(getHeight() - marginY));

        graphics.drawRect(marginX - 1, marginY - 1, getWidth() - marginX*2, getHeight() - marginY*2 + 2);
    }

    private void drawSelected(Graphics graphics, int posX, int posY, Cell cell) {
        if(!cell.isSelected()) return;

        int x = (int)(posX * cellWidth + cellMargin);
        int y = (int)(posY * cellHeight);
        int offset = (int)(cellHeight * 0.1);
        graphics.setColor(colorSelected);
        graphics.fillOval(x, y, (int)cellHeight - offset, (int)cellHeight - offset);
    }

    private void drawMovable(Graphics graphics, int posX, int posY, Cell cell) {
        if(!cell.isMovable()) return;

        int x = posX * (int)cellWidth + ((int)cellWidth/2 - (int)circleSize/2);
        int y = posY * (int)cellHeight + ((int)cellHeight/2 - (int)circleSize/2);
        graphics.setColor(colorMovable);
        graphics.fillOval(x, y, (int)circleSize, (int)circleSize);
    }

    private void drawPiece(Graphics graphics, int posX, int posY, Cell cell) {
        Piece piece = cell.getPiece();
        if(piece == null) return;

        int x = (int)(posX * cellWidth + cellMargin);
        int y = (int)(posY * cellHeight);
        graphics.drawImage(getPieceImage(piece.isRed(), piece.getType()), x, y, (int)cellHeight, (int)cellHeight, this);
    }

    private Image getPieceImage(boolean red, int type) {
        String key = JangiPiece.getKey(red, type);
        Image image = imagePiecies.get(key);

        if(image == null) {
            String path = JangiPiece.getPiecePath(red, type);
            image = Util.loadImage(getClass().getClassLoader().getResource(path));
            imagePiecies.put(key, image);
        }

        return image;
    }

    @Override
    public void update(Graphics graphics) {
        paint(graphics);
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.drawImage(offscreen, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void mousePressed(MouseEvent event) {}

    @Override
    public void mouseReleased(MouseEvent event) {
        board.cellClicked(event.getX() / (int)cellWidth, event.getY() / (int)cellHeight);
    }

    @Override
    public void mouseClicked(MouseEvent event) {}

    @Override
    public void mouseEntered(MouseEvent event) {}

    @Override
    public void mouseExited(MouseEvent event) {}
}
