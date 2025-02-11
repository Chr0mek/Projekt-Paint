package JComponents;

import Enums.ShapeType;
import Enums.Status;
import Shapes.Figura;
import Shapes.Kolo;
import Shapes.Kwadrat;
import Shapes.Linia;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class mainFrame extends JFrame implements ActionListener, MouseListener, MouseMotionListener, KeyListener {
    /**
     * ===================================================================
     * =                                                                 =
     * =                       Variables                                 =
     * =                                                                 =
     * ===================================================================
     */
    private JMenuBar menuBar;
    private JMenu fileMenu, drawMenu;
    private JMenuItem open, save, saveAs, exit, chooseFigure, chooseColor, clear;
    private JRadioButtonMenuItem chooseCircle, chooseSquare, choosePen;
    private JPanel drawPanel;
    private JToolBar toolBar;
    private JLabel currentShapeDisplay, fileStatusDisplay;
    private ShapeType currentShape = ShapeType.PEN;
    private Status status = Status.New;
    private Color color = Color.BLACK;
    private ArrayList<Figura> drawnObjects = new ArrayList<>();
    private int XcurrentMousePosition, YcurrentMousePosition;
    private String selectedFilePath;
    private JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    private FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Semi-Graphic Logos Editor Picture Image Format", "sge");
    private PrintWriter printWriter;
    private File file;
    private Scanner sc;

    /**
     * ===================================================================
     * =                                                                 =
     * =              Main Frame create Methode                          =
     * =                                                                 =
     * ===================================================================
     */
    public mainFrame() {
        this.setTitle("Simple Draw");
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        addMenu();
        addDrawingPanel();
        addToolBar();


        this.setSize(640, 640);
        this.setVisible(true);


    }
    /**
     * ===================================================================
     * =                                                                 =
     * =               Draw Panel create Methode                         =
     * =                                                                 =
     * ===================================================================
     */

    private void addDrawingPanel() {

        drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);


                for (Figura rysunek : drawnObjects) {
                    if (rysunek instanceof Linia) {
                        Linia r = (Linia) rysunek;
                        g.setColor(rysunek.getColor());
                        g.drawLine(r.getX(), r.getY(), r.getX(), r.getY());
                    }
                    if (rysunek instanceof Kwadrat) {
                        g.setColor(rysunek.getColor());
                        g.fillRect(rysunek.getX(), rysunek.getY(), 50, 50);
                    }
                    if (rysunek instanceof Kolo) {
                        g.setColor(rysunek.getColor());
                        g.fillOval(rysunek.getX(), rysunek.getY(), 50, 50);
                    }
                }
            }
        };
        this.addKeyListener(this);
        drawPanel.addMouseListener(this);
        drawPanel.addMouseMotionListener(this);
        drawPanel.setBackground(Color.WHITE);
        this.add(drawPanel);

    }

    /**
     * ===================================================================
     * =                                                                 =
     * =                     Shape Chooser Methode                       =
     * =                                                                 =
     * ===================================================================
     */

    private void chooseFigureToPaint(MouseEvent e) {
        if (currentShape == ShapeType.CIRCLE) {
            drawnObjects.add(new Kolo(e.getX() - 25, e.getY() - 25, color));
        }
        if (currentShape == ShapeType.SQUARE) {
            drawnObjects.add(new Kwadrat(e.getX() - 25, e.getY() - 25, color));
        }
        if (currentShape == ShapeType.PEN) {
            drawnObjects.add(new Linia(e.getX(), e.getY(), e.getX(), e.getY(), color));
        }
        if(status == Status.Saved){
            status = Status.Modified;
            fileStatusDisplay.setText(status.toString());
        }
        repaint();
    }



    /**
     * ===================================================================
     * =                                                                 =
     * =                  Color Randomizer Methode                       =
     * =                                                                 =
     * ===================================================================
     */

    private Color getRandomColor() {
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        return new Color(red, green, blue);
    }

    /**
     * ===================================================================
     * =                                                                 =
     * =                 Menu Options create Methode                     =
     * =                                                                 =
     * ===================================================================
     */

    private void addMenu() {

        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        open = new JMenuItem("Open");
        save = new JMenuItem("Save");
        saveAs = new JMenuItem("Save as");
        exit = new JMenuItem("Quit");

        open.setMnemonic(KeyEvent.VK_O);
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        save.setMnemonic(KeyEvent.VK_S);
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        saveAs.setMnemonic(KeyEvent.VK_S);
        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
        exit.setMnemonic(KeyEvent.VK_Q);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));

        open.addActionListener(this);
        save.addActionListener(this);
        saveAs.addActionListener(this);
        exit.addActionListener(this);

        fileMenu.add(open);
        fileMenu.add(save);
        fileMenu.add(saveAs);
        fileMenu.addSeparator();
        fileMenu.add(exit);

        menuBar.add(fileMenu);


        //Draw menu
        drawMenu = new JMenu("Draw");
        chooseFigure = new JMenu("Figure");
        ///////////////////Submenu for figure
        chooseCircle = new JRadioButtonMenuItem("Circle");
        chooseSquare = new JRadioButtonMenuItem("Square");
        choosePen = new JRadioButtonMenuItem("Pen");

        choosePen.setSelected(true);

        chooseCircle.addActionListener(this);
        chooseSquare.addActionListener(this);
        choosePen.addActionListener(this);

        chooseCircle.setMnemonic(KeyEvent.VK_C);
        chooseCircle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        chooseSquare.setMnemonic(KeyEvent.VK_R);
        chooseSquare.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
        choosePen.setMnemonic(KeyEvent.VK_E);
        choosePen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));

        chooseFigure.add(chooseCircle);
        chooseFigure.add(chooseSquare);
        chooseFigure.add(choosePen);
        ///////////////////

        chooseColor = new JMenuItem("Color");
        clear = new JMenuItem("Clear");

        chooseFigure.addActionListener(this);
        chooseColor.addActionListener(this);
        clear.addActionListener(this);

        chooseColor.setMnemonic(KeyEvent.VK_C);
        chooseColor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
        clear.setMnemonic(KeyEvent.VK_N);
        clear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));

        drawMenu.add(chooseFigure);
        drawMenu.addSeparator();
        drawMenu.add(chooseColor);
        drawMenu.addSeparator();
        drawMenu.add(clear);

        menuBar.add(drawMenu);


        this.setJMenuBar(menuBar);
    }

    /**
     * ===================================================================
     * =                                                                 =
     * =                       Tool Bar create Methode                   =
     * =                                                                 =
     * ===================================================================
     */

    private void addToolBar() {
        toolBar = new JToolBar();
        toolBar.setLayout(new BorderLayout());
        toolBar.add(currentShapeDisplay = new JLabel(String.valueOf(currentShape)), BorderLayout.WEST);
        toolBar.add(fileStatusDisplay = new JLabel(status.toString()), BorderLayout.EAST);
        this.add(toolBar, BorderLayout.SOUTH);
    }

    /**
     * ===================================================================
     * =                                                                 =
     * =                       File methodes                             =
     * =                                                                 =
     * ===================================================================
     */
    private void save() {
        try {
            printWriter = new PrintWriter(selectedFilePath);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this,"Error","Error",JOptionPane.ERROR_MESSAGE);
        }
        for (Figura rysunek : drawnObjects) {
            printWriter.println(rysunek);
        }
        printWriter.close();
        status = Status.Saved;
        fileStatusDisplay.setText(status.toString());
        file = new File(selectedFilePath);
        this.setTitle("Simple Draw: " + file.getName());
        JOptionPane.showMessageDialog(this,"File saved","Success",JOptionPane.INFORMATION_MESSAGE);

    }
    private void saveAs(int approve) {
        if (approve == JFileChooser.APPROVE_OPTION) {
            selectedFilePath = fileChooser.getSelectedFile().getPath() + ".sge";
            save();
        }
    }
    private void open(){
        int approve = fileChooser.showOpenDialog(this);
        if (approve == JFileChooser.APPROVE_OPTION) {
            selectedFilePath = fileChooser.getSelectedFile().getPath();
            file = new File(selectedFilePath);
            if (file.exists()) {
                drawnObjects = new ArrayList<>();
                try {
                    sc = new Scanner(file);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                while (sc.hasNextLine()) {
                    String obiektNazwa = sc.nextLine();
                    int objectX = Integer.parseInt(sc.nextLine());
                    int objectY = Integer.parseInt(sc.nextLine());
                    Color objectColor = new Color(Integer.parseInt(sc.nextLine()), Integer.parseInt(sc.nextLine()), Integer.parseInt(sc.nextLine()));
                    if (obiektNazwa.equals("Linia")) {
                        int oldX = Integer.parseInt(sc.nextLine());
                        int oldY = Integer.parseInt(sc.nextLine());
                        drawnObjects.add(new Linia(objectX, objectY, oldX, oldY, objectColor));
                    } else if (obiektNazwa.equals("Kwadrat")) {
                        drawnObjects.add(new Kwadrat(objectX, objectY, objectColor));
                    } else {
                        drawnObjects.add(new Kolo(objectX, objectY, objectColor));
                    }
                    sc.nextLine();

                }
                repaint();
                status = Status.Saved;
                fileStatusDisplay.setText(status.toString());
                this.setTitle("Simple Draw: " + file.getName());
                JOptionPane.showMessageDialog(this, "File uploaded", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "File upload failure!", "Fail", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * ===================================================================
     * =                                                                 =
     * =                       Listeners                                 =
     * =                                                                 =
     * ===================================================================
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        fileChooser.setFileFilter(filter);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (e.getSource() == open) {
            open();
        }

        if(e.getSource()==save){
            int approve;
            if (file == null) {
                approve = fileChooser.showSaveDialog(this);
                saveAs(approve);

            } else {
                save();
            }
        }
        if(e.getSource()==saveAs){
            int approve;
            approve = fileChooser.showSaveDialog(this);
            saveAs(approve);
        }

        if(e.getSource()==exit){
            if(status == Status.New|| status == Status.Modified){
                int approve = JOptionPane.showConfirmDialog(this,"Are you sure to exit without saving?","Confirm", JOptionPane.OK_CANCEL_OPTION);
                if(approve == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            } else {
                System.exit(0);
            }
        }

        if(e.getSource()==chooseCircle){
            currentShape = ShapeType.CIRCLE;
            currentShapeDisplay.setText(currentShape.toString());
            chooseCircle.setSelected(true);
            chooseSquare.setSelected(false);
            choosePen.setSelected(false);
        }

        if(e.getSource()==chooseSquare) {
            currentShape = ShapeType.SQUARE;
            currentShapeDisplay.setText(currentShape.toString());
            chooseCircle.setSelected(false);
            chooseSquare.setSelected(true);
            choosePen.setSelected(false);
        }

        if(e.getSource()==choosePen) {
            currentShape = ShapeType.PEN;
            currentShapeDisplay.setText(currentShape.toString());
            chooseCircle.setSelected(false);
            chooseSquare.setSelected(false);
            choosePen.setSelected(true);
        }

        if(e.getSource()==chooseColor) {
            color = JColorChooser.showDialog(this, "Select color", Color.BLACK);
        }
        if(e.getSource()==clear){
            drawnObjects = new ArrayList<>();
            repaint();
        }

}

    @Override
    public void mouseClicked(MouseEvent e) {
        chooseFigureToPaint(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }


    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        chooseFigureToPaint(e);
    }


    @Override
    public void mouseMoved(MouseEvent e) {
        XcurrentMousePosition = e.getX();
        YcurrentMousePosition = e.getY();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F11) {
            if (currentShape == ShapeType.CIRCLE) {
                drawnObjects.add(new Kolo(XcurrentMousePosition - 25, YcurrentMousePosition - 25, getRandomColor()));
            }
            if (currentShape == ShapeType.SQUARE) {
                drawnObjects.add(new Kwadrat(XcurrentMousePosition - 25, YcurrentMousePosition - 25, getRandomColor()));
            }
            repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            Figura toRemove = null;
            for (Figura rysunek : drawnObjects) {
                if (XcurrentMousePosition <= rysunek.getX() + 50 && XcurrentMousePosition >= rysunek.getX() && YcurrentMousePosition <= rysunek.getY() + 50 && YcurrentMousePosition >= rysunek.getY()) {
                    toRemove = rysunek;
                }
            }
            if (toRemove != null) drawnObjects.remove(toRemove);
            if(status == Status.Saved){
                status = Status.Modified;
                fileStatusDisplay.setText(status.toString());
            }
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
