package zeldaminiclone;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Game extends Canvas implements Runnable, KeyListener{
	
	public static int WIDTH = 640, HEIGHT = 480;
	public static int SCALE = 3;
	public Player player;
	public World world;
	public List<Inimigo> inimigos = new ArrayList<Inimigo>();
	
	public Game() {
		this.addKeyListener(this);
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		new Spritesheet();
		world = new World();
		player = new Player(32,32);
		inimigos.add(new Inimigo(32,32));
	}
	
	/*Responsavel pela logica*/
	public void tick() {
		player.tick();
		
		for(int i = 0; i < inimigos.size(); i++) {
			inimigos.get(i).tick();
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(new Color(0,135,13));
		g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
		
		player.render(g);
		for(int i = 0; i < inimigos.size(); i++) {
			inimigos.get(i).render(g);
		}
		world.render(g);
		
		bs.show();
	}
	
	public static void main (String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame();
		
		frame.add(game);
		frame.setTitle("Mini Zelda");
		
		frame.pack();
		
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
		
		/*Metodo java que procura na classe >>Game<< outro metodo chamado >>run<<*/
		new Thread(game).start();
	}
	
	@Override
	public void run() {
		
		while(true) {
			/*Chamando o game looping*/
			tick();
			render();
			try {
			Thread.sleep(1000/60);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = true; 
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = true; 
		}
		
		if(e.getKeyCode() == KeyEvent.VK_Z) {
			player.shoot = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = true; 
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.down = true; 
		}
}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = false; 
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false; 
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = false; 
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.down = false; 
		}
	}
}
