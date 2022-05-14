package edu.cornellcollege;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PointPatternListener implements ActionListener {
    private WavesPanel panel;

    public PointPatternListener(WavesPanel panel) {
        this.panel = panel;
    } // PointPatternListener( WavesPanel )

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals(PointPattern.GRID.getName())) {
            this.panel.setPointPattern(PointPattern.GRID);
        } // if
        else if (command.equals(PointPattern.POLYGON.getName())) {
            this.panel.setPointPattern(PointPattern.POLYGON);
        } // else if
        else if (command.equals(PointPattern.RANDOM.getName())) {
            this.panel.setPointPattern(PointPattern.RANDOM);
        } // else if
        else if (command.equals(PointPattern.STAR_POLYGON.getName())) {
            this.panel.setPointPattern(PointPattern.STAR_POLYGON);
        } // else if

    } // actionPerformed( ActionEvent )
} // PointPatternListener
