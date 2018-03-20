package com.io;

import java.awt.GridLayout;
import java.awt.TextField;

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
	TextField tf;
	public Login() {

		p = new JPanel();
		im = new ImageIcon("uesr.jpg");
		la = new JLabel(im);
		p.add(la);
		la=new JLabel("닉네임");
		p.add(la);
		tf=new TextField(10);
		
		p.add(tf);
		bt = new JButton("시작");
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
