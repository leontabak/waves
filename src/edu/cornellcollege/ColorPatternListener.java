package edu.cornellcollege;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorPatternListener implements ActionListener {

    private WavesPanel panel;

    public ColorPatternListener(WavesPanel panel) {

        this.panel = panel;
    } // ColorPatternListener( WavesPanel )

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals(ColorPattern.BLACK_AND_WHITE.getName())) {
            this.panel.setColorPattern(ColorPattern.BLACK_AND_WHITE);
        } // if
        else if (command.equals(ColorPattern.GRADIENT.getName())) {
            this.panel.setColorPattern(ColorPattern.GRADIENT);
        } // else if
        else if (command.equals(ColorPattern.INTERLEAVED.getName())) {
            this.panel.setColorPattern(ColorPattern.INTERLEAVED);
        } // else if
        else if (command.equals(ColorPattern.RANDOM.getName())) {
            this.panel.setColorPattern(ColorPattern.RANDOM);
        } // else if

    } // actionPerformed( ActionEvent )
} // ColorPatternListener
