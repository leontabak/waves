package edu.cornellcollege;

import jdk.jfr.Frequency;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrequencyCountListener implements ActionListener {
    private WavesPanel panel;

    public FrequencyCountListener( WavesPanel panel ) {
        this.panel = panel;
    } // FrequencyCountListener( WavesPanel )

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = e.getActionCommand();
        int frequency = Integer.parseInt( name );
        this.panel.setFrequency( frequency );
    } // actionPerformed( ActionEvent )
} // FrequencyCountListener
