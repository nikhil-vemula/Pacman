import java.awt.*;
import java.awt.event.*;
import java.util.*;
class Dot
{
	boolean e=false;
	int x,y;
	Dot(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
}
public class Pacman extends Frame implements Runnable,ActionListener
{
	String mode="p1";
	String pname;
	Color pcolor;
	int no_of_dots;
	TextField name,ndots;	
	Choice color;
	Button start;
	TextArea controls;
	boolean started=false;
	String dir="right";
	int r=20;
	int x=100;
	int y=100;
	int theta=15;
	Set<Dot> dots;
	Pacman()
	{
		new Thread(this).start();
		addKeyListener(new MyKeyAdapter());
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we)
			{
				System.exit(0);
			}
		});
		dots=new HashSet<Dot>();
	}
	public static void main(String[] args)
	{
		String usage="Controls:\nUp:w or Up_arrow\nLeft:a or Left_arrow\nDown:s or Down_arrow\nRight:d or Right_arrow";
		Pacman pacman=new Pacman();
		pacman.setLayout(new FlowLayout(FlowLayout.CENTER));
		pacman.name=new TextField("Player Name");
		pacman.ndots=new TextField("10");
		pacman.color=new Choice();
		pacman.color.add("red");
		pacman.color.add("green");
		pacman.start=new Button("Start");
		pacman.controls=new TextArea(usage);
		pacman.controls.setEditable(false);
		pacman.add(pacman.name);
		pacman.add(new Label("No of dots:"));
		pacman.add(pacman.ndots);
		pacman.add(new Label("Dots Color:"));
		pacman.add(pacman.color);
		pacman.add(pacman.start);
		pacman.add(pacman.controls);
		pacman.start.addActionListener(pacman);
		pacman.pack();
		pacman.setVisible(true);
	}
	public void actionPerformed(ActionEvent ae)
	{
		java.util.Random random=new java.util.Random();
		String cmd=ae.getActionCommand();
		if(cmd.equals("Start"))
		{
			removeAll();
			started=true;
			pname=name.getText();
			switch(color.getSelectedIndex())
			{
				case 0:
					pcolor=Color.red;
					break;
				case 1:
					pcolor=Color.green;
					break;
			}
			no_of_dots=Integer.parseInt(ndots.getText());
			for(int i=0;i<no_of_dots;i++)
				dots.add(new Dot(random.nextInt(1325)+8,random.nextInt(620)+40));
			setExtendedState(Frame.MAXIMIZED_BOTH);
			requestFocusInWindow();
			repaint();
		}
	}
	public void paint(Graphics g) 
	{
		if(no_of_dots>0)
		{
			if(started)
			{
				g.setFont(new Font("Arial",Font.BOLD,50));
				g.setColor(Color.red);
				g.drawString(String.valueOf(no_of_dots),1300,100);
				for(Dot i:dots)
				{
					if(!i.e)
					{
						g.setColor(pcolor);
						g.fillOval(i.x,i.y,10,10);
					}
				}
				g.setColor(Color.yellow);
				if(dir.equals("right"))
					g.fillArc(x,y,2*r,2*r,theta,360-(2*theta));
				else if(dir.equals("left"))
					g.fillArc(x,y,2*r,2*r,180-theta,-(360-(2*theta)));
				else if(dir.equals("up"))
					g.fillArc(x,y,2*r,2*r,90-theta,-(360-(2*theta)));
				else if(dir.equals("down"))
					g.fillArc(x,y,2*r,2*r,270+theta,(360-(2*theta)));	
			}
		}
		else
		{
			g.setFont(new Font("Arial",Font.BOLD,100));
			g.setColor(Color.red);
			g.drawString("GameOver",400,400);
		}
	}
	synchronized void eaten()
	{
		for(Dot i:dots)
			{
				if((Math.pow((i.x-(x+r)),2)+Math.pow((i.y-(y+r)),2))<(r*r)&&i.e==false)
				{
					i.e=true;
					no_of_dots--;
				}
			}
	}
	public void run()
	{
		int i=0;
		while(true)
		{
			if((i++)%2==0)
				theta=30;
			else
				theta=15;
			try{Thread.sleep(100);}catch(Exception e){}
		}
	}
	class MyKeyAdapter extends KeyAdapter
	{
		public void keyPressed(KeyEvent ke) 
		{
			int i=ke.getKeyCode();
			switch(i) 
			{
				case KeyEvent.VK_RIGHT:
				dir="right";
				if((x+2*r)<1350)
					x=x+10;
				break;
				case KeyEvent.VK_LEFT:
				dir="left";
				if(x>8)
					x=(x-10);
				break;
				case KeyEvent.VK_UP:
				dir="up";
				if(y>30)
					y=(y-10);
				break;
				case KeyEvent.VK_DOWN:
				dir="down";
				if((y+2*r)<650)
					y=(y+10);
				break;
			}
				eaten();
				repaint();
				
		}
		public void keyTyped(KeyEvent ke) 
		{
			char ch=ke.getKeyChar();
			switch(ch) 
			{
				case 'd':
				dir="right";
				if((x+2*r)<1350)
					x=x+10;
				break;
				case 'a':
				dir="left";
				if(x>8)
					x=(x-10);
				break;
				case 'w':
				dir="up";
				if(y>30)
					y=(y-10);
				break;
				case 's':
				dir="down";
				if((y+2*r)<650)
					y=(y+10);
				break;
			}
				eaten();
				repaint();
				
		}
	}
}