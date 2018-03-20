package com.io;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Login extends JFrame {

	JPanel p;
	GridLayout gl;
	JLabel la;
	JButton bt;
	ImageIcon im;

	public Login() {

		p = new JPanel();
		im = new ImageIcon("login.png");

		la = new JLabel(im);
	
		p.add(la);
		bt = new JButton("Ω√¿€");
		bt.setSize(200, 50);
		p.add(bt);

		add(p);
		setBounds(500, 200, 450, 150);
		setVisible(true);

	}

	public static void main(String[] args) {
		new Login();

	}
}