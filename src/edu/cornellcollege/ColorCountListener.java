package edu.cornellcollege;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorCountListener implements ActionListener {
    private WavesPanel panel;

    public ColorCountListener( WavesPanel panel ) {

        this.panel = panel;
    } // ColorCountListener( WavesPanel )

    @Override
    public void actionPerformed(ActionEvent e) {
       String action = e.getActionCommand();
       int count = Integer.parseInt( action );
       this.panel.setNumberOfColors(count);
    } // actionPerformed( ActionEvent )
} // ColorCountListener
