package edu.cornellcollege;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Waves extends JFrame implements ActionListener {

    private static final int FRAME_WIDTH = 768;
    private static final int FRAME_HEIGHT = 768;
    private static final String FRAME_TITLE = "Waves";

    // Names of menus
    private static final String FILE = "File";
    private static final String CCOUNT = "Color count";
    private static final String CPATTERN = "Color pattern";
    private static final String PCOUNT = "Point count";
    private static final String PPATTERN = "Point pattern";

    // Names of menu items
    private static final String FSAVE = "Save";

    private static final String CC002 = "2";
    private static final String CC004 = "4";
    private static final String CC008 = "8";
    private static final String CC016 = "16";
    private static final String CC032 = "32";
    private static final String CC064 = "64";
    private static final String CC128 = "128";
    private static final String CC256 = "256";

    private static final String CPGRADIENT = "Gradient";
    private static final String CPINTERLEAVED = "Interleaved";
    private static final String CPRANDOM = "Random";

    private static final String PC03 = "3";
    private static final String PC04 = "4";
    private static final String PC05 = "5";
    private static final String PC06 = "6";
    private static final String PC07 = "7";
    private static final String PC08 = "8";
    private static final String PC09 = "9";
    private static final String PC10 = "10";
    private static final String PC11 = "11";
    private static final String PC12 = "12";

    private static final String PPGRID = "Grid";
    private static final String PPPOLYGON = "Polygon";
    private static final String PPRANDOM = "Random";

    public Waves() {
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle(FRAME_TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = this.getContentPane();
        WavesPanel panel = new WavesPanel();
        pane.add(panel);

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu fileMenu = createMenu(menuBar, FILE);
        createMenuItem(fileMenu, FSAVE, this);

        JMenu colorCountMenu = createMenu(menuBar, CCOUNT);
        createMenuItem(colorCountMenu, CC002, this);
        createMenuItem(colorCountMenu, CC004, this);
        createMenuItem(colorCountMenu, CC008, this);
        createMenuItem(colorCountMenu, CC016, this);
        createMenuItem(colorCountMenu, CC032, this);
        createMenuItem(colorCountMenu, CC064, this);
        createMenuItem(colorCountMenu, CC128, this);
        createMenuItem(colorCountMenu, CC256, this);

        JMenu colorPatternMenu = createMenu(menuBar, CPATTERN);
        createMenuItem(colorPatternMenu, CPGRADIENT, this);
        createMenuItem(colorPatternMenu, CPINTERLEAVED, this);
        createMenuItem(colorPatternMenu, CPRANDOM, this);

        JMenu pointCountMenu = createMenu(menuBar, PCOUNT);
        createMenuItem(pointCountMenu, PC03, this);
        createMenuItem(pointCountMenu, PC04, this);
        createMenuItem(pointCountMenu, PC05, this);
        createMenuItem(pointCountMenu, PC06, this);
        createMenuItem(pointCountMenu, PC07, this);
        createMenuItem(pointCountMenu, PC08, this);
        createMenuItem(pointCountMenu, PC09, this);
        createMenuItem(pointCountMenu, PC10, this);
        createMenuItem(pointCountMenu, PC11, this);
        createMenuItem(pointCountMenu, PC12, this);

        JMenu pointPatternMenu = createMenu(menuBar, PPATTERN);
        createMenuItem(pointPatternMenu, PPGRID, this);
        createMenuItem(pointPatternMenu, PPPOLYGON, this);
        createMenuItem(pointPatternMenu, PPRANDOM, this);

        this.setVisible(true);
    } // Waves()

    private JMenu createMenu(JMenuBar menuBar, String name) {
        JMenu menu = new JMenu(name);
        menuBar.add(menu);
        return menu;
    } // createMenu( JMenuBar, String )

    private void createMenuItem(JMenu menu, String name,
                                ActionListener listener) {
        JMenuItem item = new JMenuItem(name);
        item.setActionCommand(name);
        item.addActionListener(listener);
        menu.add(item);
    } // createMenuItem( JMenu, String )

    public static void main(String[] args) {
        Waves waves = new Waves();
    } // main( String [] )

    @Override
    public void actionPerformed(ActionEvent e) {

    } // actionPerformed( ActionEvent )
} // Waves
