package com.io;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class main extends JFrame {
	JPanel p;
	GridBagLayout gbl;
	GridBagConstraints gbc;
	Label la;
	JTextField tf;
	String[][] lab = { { "유저1", "주제", "유저4" }, { "유저2", "그림판", "유저5" }, { "유저3", "채팅저장", "유저6" } };

	public main() {

		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		p = new JPanel(gbl);

		for (int i = 0; i < lab.length; i++) {
			gbc.gridy = i;
			gbc.gridwidth = 1;
			gbc.gridheight = 1;
			gbc.weightx = 1.0;
			gbc.weighty = 1.0;
			for (int j = 0; j < lab[i].length; j++) {

				gbc.gridx = j;

				if (i == 2 && j == 1) {
					tf = new JTextField(lab[i][j]);
					p.add(tf, gbc);
					j++;
					la = new Label(lab[i][j]);
					gbc.gridx = j;
					p.add(la, gbc);
					
					tf = new JTextField("채팅 입력");
					gbc.gridy = i+1;
					gbc.gridx = 1;
					p.add(tf);
					p.add(tf, gbc);
					break;
				}
				la = new Label(lab[i][j]);
				p.add(la, gbc);

			}
		}

		add(p);
		setBounds(500, 300, 800, 600);
		setVisible(true);
	}

	public static void main(String[] args) {
		new main();

	}
}