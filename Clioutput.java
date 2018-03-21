package text;

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
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import text.Game.EventHandler;


public class Clioutput extends Frame implements ActionListener{
	
	Panel pLeft, p1, p2, pRight;
	Button blue, orange, green, plus, minus, erase, eraseAll;
	CanvasDemo cd;
	TextArea ta;
	TextField tf;
	static String msg = "";

	public Clioutput(){

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
		blue = new Button();
		blue.setBackground(Color.blue);
		orange = new Button();
		orange.setBackground(Color.orange);
		green = new Button();
		green.setBackground(Color.green);

		p1.add(blue);
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
		cd.setSize(300, 300);
		pRight.add(cd, gbc);
		gbc.gridy = 1;

		ta = new TextArea(); // 채팅창
		pRight.add(ta, gbc);
		gbc.gridy = 2;
		tf = new TextField();
		tf.addActionListener(this);
		pRight.add(tf, gbc);

		gbc.gridy = 1;

		cd.setBackground(Color.WHITE);
		blue.addActionListener(new EventHandler());
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

	
	}
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

			if (btnCol == Color.blue) {
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
		final String ip = "203.236.209.195";
		final int port = 3000;
		Scanner sc = new Scanner(System.in);
		Socket sock = null;
		Thread th1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				Clioutput ps = new Clioutput();
				ps.setBounds(300, 100, 600, 680);
				ps.setVisible(true);
			}  // run end
		}); //thread end
		th1.start();
		
	
			try {
				sock =new Socket(ip,port);
				OutputStream os = sock.getOutputStream();
				OutputStreamWriter osw=new OutputStreamWriter(os);
				
				while(true){
					osw.write(msg+"\n");
					if(msg.equals("\n")){break;}
					osw.flush();
					}
			}
	
		catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				sock.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
		
	}//main end

public void actionPerformed(ActionEvent e) {
	
	msg += tf.getText() + "\n";
	tf.setText("");
	ta.setText(msg);
}

}
class CanvasDemo extends Canvas {
	int x = -10, y = -10, w = 10, h = 10;
	int sw = 0;
	Color color = Color.blue;
	
	public void update(Graphics g) {
		paint(g);
	}
	
	public void paint(Graphics g) {
		if (sw == 0) {
			g.setColor(color);
			g.fillOval(x, y, w, h); // 원 출력
		} else if (sw == 1) {
			g.clearRect(0, 0, 300, 300);
		}
	}
}

	