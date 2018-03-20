package Catchmind;

/*
 import java.awt.Button;
 import java.awt.GridLayout;
 import java.awt.Insets;
 import java.awt.Panel;
 //import java.awt.event.ActionEvent;
 //import java.awt.event.ActionListener;
 */

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Paint extends Frame implements ActionListener, Runnable {

	Panel pLeft, p1, p2, pRight;
	Button black, orange, green, plus, minus, erase, eraseAll;
	CanvasDemo cd;
	TextArea ta;
	TextField tf;
	static BufferedReader br;
	static PrintWriter pw;
	static String msg = "";
	static Socket socket;
	String ip = "203.236.209.196";
	int port = 3000;
	InputStream is = null;

	// BorderLayout()이용하여 전체 레이아웃 설정

	public Paint() {
		// super("paint Application Test");
		pLeft = new Panel(new BorderLayout());
		pLeft.setBackground(Color.lightGray);
		add(pLeft, "West");

		p1 = new Panel(new GridLayout(1, 3)) {
			public Insets getInsets() {
				return new Insets(10, 10, 10, 10); // 여백
			}
		};
		pLeft.add(p1, "North");

		// p1 버튼 생성
		black = new Button();
		black.setBackground(Color.black);
		orange = new Button();
		orange.setBackground(Color.orange);
		green = new Button();
		green.setBackground(Color.green);

		p1.add(black);
		p1.add(orange);
		p1.add(green);

		// p2 버튼 생성
		plus = new Button("+");
		minus = new Button("-");
		erase = new Button("지우기");
		eraseAll = new Button("모두 지우기");

		p2 = new Panel(new GridLayout(4, 1)) {
			public Insets getInsets() {
				return new Insets(20, 10, 220, 10);
			}
		};

		p2.add(plus);
		p2.add(minus);
		p2.add(erase);
		p2.add(eraseAll);
		Label la = new Label("문제: " + "컴퓨터");
		p2.add(la);

		pLeft.add(p2, "Center");

		pRight = new Panel(new GridBagLayout()) {
			public Insets getInsets() {
				return new Insets(70, 20, 20, 20);
			}
		};

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.BOTH;

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;

		pRight.setBackground(Color.gray);
		add(pRight, "Center");

		cd = new CanvasDemo();
		cd.setSize(500, 400);
		pRight.add(cd, gbc);

		gbc.gridy = 1;

		ta = new TextArea(); // 채팅창
		pRight.add(ta, gbc);
		gbc.gridy = 2;
		tf = new TextField();

		tf.addActionListener(this);

		pRight.add(tf, gbc);

		cd.setBackground(Color.WHITE);

		black.addActionListener(new EventHandler());
		orange.addActionListener(new EventHandler());
		green.addActionListener(new EventHandler());
		plus.addActionListener(new EventHandler());
		green.addActionListener(new EventHandler());
		minus.addActionListener(new EventHandler());
		erase.addActionListener(new EventHandler());
		eraseAll.addActionListener(new EventHandler());
		cd.addMouseMotionListener(new EventHandler());

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		try {
			ta.setText(br.readLine());
		} catch (IOException e) {

			e.printStackTrace();
		}

	} // end of paintSampleEx() 생성자

	class EventHandler extends MouseMotionAdapter implements ActionListener {
		public void mouseDragged(MouseEvent me) {
			cd.x = me.getX();
			cd.y = me.getY();
			cd.repaint();
		}

		public void actionPerformed(ActionEvent ae) {
			Object obj = ae.getSource();
			Color btnCol = ((Button) obj).getBackground();
			// System.out.println(btnCol);
			cd.sw = 0;

			if (btnCol == Color.black) {
				cd.color = btnCol;
			} else if (btnCol == Color.orange) {
				cd.color = btnCol;
			} else if (btnCol == Color.green) {
				cd.color = btnCol;
			} else if (obj == plus) { // 크기 조정
				cd.w++;
				cd.h++;
			} else if (obj == minus) {
				if (cd.w > 3) { // 최소값 설정
					cd.w--;
					cd.h--;
				}
			} else if (obj == erase) {
				cd.color = cd.getBackground();
			} else if (obj == eraseAll) {
				cd.sw = 1;
				cd.repaint();
			}
		}
	}

	public static void main(String[] args) {

		try {

			socket = new Socket("203.236.209.196", 3000);
			br = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			pw = new PrintWriter(socket.getOutputStream(), true);
		} catch (Exception e) {
			System.out.println("연결 실패");
		}

		try {
			OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			while (true) {
				osw.write(msg + "\n");
				osw.flush();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	class CanvasDemo extends Canvas {
		int x = -10, y = -10, w = 10, h = 10;
		int sw = 0;
		Color color = Color.black;

		public void update(Graphics g) {
			paint(g);
		}

		public void paint(Graphics g) {
			if (sw == 0) {
				g.setColor(color);
				g.fillOval(x, y, w, h); // 원 출력
			} else if (sw == 1) {
				g.clearRect(0, 0, 500, 400);
			}
		}
	}

	@Override
	public void run() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		msg += tf.getText() + "\n";
		tf.setText("");
		ta.setText(msg);

	}
}