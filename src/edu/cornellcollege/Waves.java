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

    // Name of menu item
    private static final String FSAVE = "Save";

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

        for (ColorCount cc : ColorCount.values()) {
            createMenuItem(colorCountMenu, cc.getName(), ccListener);
        } // for

        ColorPatternListener cpListener=
                new ColorPatternListener( this.panel);
        JMenu colorPatternMenu = createMenu(menuBar, CPATTERN);
        for( ColorPattern cp: ColorPattern.values() ) {
            createMenuItem(colorPatternMenu,
                    cp.getName(), cpListener);
        } // for

        PointCountListener pcListener =
                new PointCountListener(panel);

        JMenu pointCountMenu = createMenu(menuBar, PCOUNT);
        for (PointCount pc : PointCount.values()) {
            createMenuItem(pointCountMenu,
                    pc.getName(), pcListener);
        } // for

        PointPatternListener ppListener =
                new PointPatternListener( this.panel );
        JMenu pointPatternMenu = createMenu(menuBar, PPATTERN);
        for( PointPattern pp: PointPattern.values() ) {
            createMenuItem( pointPatternMenu, pp.getName(), ppListener );
        }

        FrequencyCountListener frequencyListener =
                new FrequencyCountListener(panel);

        JMenu frequencyMenu = createMenu(menuBar, FREQUENCY);
        for (FrequencyCount fc : FrequencyCount.values()) {
            createMenuItem(frequencyMenu,
                    fc.getName(),
                    frequencyListener);
        } // for

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

        if( command.equals( FSAVE )) {
            this.panel.writeToFile();
        } // if

    } // actionPerformed( ActionEvent )
} // Waves
