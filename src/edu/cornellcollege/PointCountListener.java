
package edu.cornellcollege;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PointCountListener implements ActionListener {
    private WavesPanel panel;

    public PointCountListener( WavesPanel panel ) {
        this.panel = panel;
    } // PointCountListener( WavesPanel )

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        int count = Integer.parseInt( action );
        this.panel.setNumberOfPoints(count);
    } // actionPerformed( ActionEvent )
} // ColorCountListener
