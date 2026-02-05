package utils;

import javax.swing.*;
import java.awt.*;

public class TabHeader extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TabHeader(JTabbedPane tabbedPane, String title, Component tab) {

        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        JLabel label = new JLabel(title);

        JButton btnClose = new JButton("✖");
        btnClose.setMargin(new Insets(0, 4, 0, 4));
        btnClose.setFocusable(false);
        btnClose.setBorder(null);

        btnClose.addActionListener(e -> {
            int index = tabbedPane.indexOfComponent(tab);
            if (index != -1) {
                tabbedPane.remove(index);
            }
        });

        add(label);
        add(btnClose);
    }
}
