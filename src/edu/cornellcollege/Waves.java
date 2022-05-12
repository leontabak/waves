package edu.cornellcollege;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class models a window in which we
 * can place an image.
 */
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
    private static final String FREQUENCY = "Frequency";

    // Names of menu items
    private static final String FSAVE = "Save";

    private static final String CPGRADIENT =
            "Gradient colors";
    private static final String CPINTERLEAVED =
            "Interleaved colors";
    private static final String CPRANDOM =
            "Random colors";

    private static final String PPGRID =
            "Points on a grid";
    private static final String PPPOLYGON =
            "Points on a circle (a polygon)";
    private static final String PPRANDOM =
            "Points scattered randomly";

    private WavesPanel panel;

    public Waves() {
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle(FRAME_TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container pane = this.getContentPane();
        this.panel = new WavesPanel();
        pane.add(panel);

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu fileMenu = createMenu(menuBar, FILE);
        createMenuItem(fileMenu, FSAVE, this);

        ColorCountListener ccListener =
                new ColorCountListener(panel);

        JMenu colorCountMenu = createMenu(menuBar, CCOUNT);
        createMenuItem(colorCountMenu,
                ColorCount.CC002.getName(), ccListener);
        createMenuItem(colorCountMenu,
                ColorCount.CC004.getName(), ccListener);
        createMenuItem(colorCountMenu,
                ColorCount.CC008.getName(), ccListener);
        createMenuItem(colorCountMenu,
                ColorCount.CC016.getName(), ccListener);
        createMenuItem(colorCountMenu,
                ColorCount.CC032.getName(), ccListener);
        createMenuItem(colorCountMenu,
                ColorCount.CC064.getName(), ccListener);
        createMenuItem(colorCountMenu,
                ColorCount.CC128.getName(), ccListener);
        createMenuItem(colorCountMenu,
                ColorCount.CC256.getName(), ccListener);

        JMenu colorPatternMenu = createMenu(menuBar, CPATTERN);
        createMenuItem(colorPatternMenu, CPGRADIENT, this);
        createMenuItem(colorPatternMenu, CPINTERLEAVED, this);
        createMenuItem(colorPatternMenu, CPRANDOM, this);

        PointCountListener pcListener =
                new PointCountListener(panel);

        JMenu pointCountMenu = createMenu(menuBar, PCOUNT);
        createMenuItem(pointCountMenu,
                PointCount.PC03.getName(), pcListener);
        createMenuItem(pointCountMenu,
                PointCount.PC04.getName(), pcListener);
        createMenuItem(pointCountMenu,
                PointCount.PC05.getName(), pcListener);
        createMenuItem(pointCountMenu,
                PointCount.PC06.getName(), pcListener);
        createMenuItem(pointCountMenu,
                PointCount.PC07.getName(), pcListener);
        createMenuItem(pointCountMenu,
                PointCount.PC08.getName(), pcListener);
        createMenuItem(pointCountMenu,
                PointCount.PC09.getName(), pcListener);
        createMenuItem(pointCountMenu,
                PointCount.PC10.getName(), pcListener);
        createMenuItem(pointCountMenu,
                PointCount.PC11.getName(), pcListener);
        createMenuItem(pointCountMenu,
                PointCount.PC12.getName(), pcListener);

        JMenu pointPatternMenu = createMenu(menuBar, PPATTERN);
        createMenuItem(pointPatternMenu, PPGRID, this);
        createMenuItem(pointPatternMenu, PPPOLYGON, this);
        createMenuItem(pointPatternMenu, PPRANDOM, this);

        FrequencyCountListener frequencyListener =
                new FrequencyCountListener(panel);

        JMenu frequencyMenu = createMenu( menuBar, FREQUENCY );
        createMenuItem( frequencyMenu,
                FrequencyCount.FREQ02.getName(),
                frequencyListener);
        createMenuItem( frequencyMenu,
                FrequencyCount.FREQ04.getName(),
                frequencyListener);
        createMenuItem( frequencyMenu,
                FrequencyCount.FREQ08.getName(),
                frequencyListener);
        createMenuItem( frequencyMenu,
                FrequencyCount.FREQ16.getName(),
                frequencyListener);
        createMenuItem( frequencyMenu,
                FrequencyCount.FREQ32.getName(),
                frequencyListener);
        createMenuItem( frequencyMenu,
                FrequencyCount.FREQ64.getName(),
                frequencyListener);

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
        String command = e.getActionCommand();

        switch (command) {
            case CPGRADIENT:
                this.panel.setColorPattern(
                        ColorPattern.GRADIENT);
                break;
            case CPINTERLEAVED:
                this.panel.setColorPattern(
                        ColorPattern.INTERLEAVED);
                break;
            case CPRANDOM:
                this.panel.setColorPattern(
                        ColorPattern.RANDOM);
                break;
            case PPGRID:
                this.panel.setPointPattern(
                        PointPattern.GRID
                );
                break;
            case PPPOLYGON:
                this.panel.setPointPattern(
                        PointPattern.POLYGON
                );
                break;
            case PPRANDOM:
                this.panel.setPointPattern(
                        PointPattern.RANDOM
                );
                break;
            case FSAVE:
                this.panel.writeToFile();
                break;
            default:
                break;
        } // switch

    } // actionPerformed( ActionEvent )
} // Waves
