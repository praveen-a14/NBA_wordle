import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.sound.sampled.*;

public class ClientScreen extends JPanel implements KeyListener{
    private Game game;
    private ObjectOutputStream outObject;
    private ObjectInputStream inObject;
    private PushbackInputStream inPush;
    private JTextField name_field;
    private int y;
    private Player temp;
    private Player temp1;
    private Player temp2;
    private Player temp3;
    private boolean draw;

    public ClientScreen() {
        game = new Game();

        name_field = new JTextField();
        name_field.setFont(new Font("Arial", Font.PLAIN, 20));
        name_field.setHorizontalAlignment(SwingConstants.LEFT);
        name_field.setBounds(360, 150, 320, 35);
        name_field.setText("");
        this.add(name_field);
        name_field.addKeyListener(this);
        name_field.setVisible(false);
  
        setFocusable(true);
        addKeyListener(this);
        setLayout(null);
    }

    public Dimension getPreferredSize() {
        //Sets the size of the panel
        return new Dimension(683,768);
    }
    public void playSound() {

        try {
            URL url = this.getClass().getClassLoader().getResource("sounds/win.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }
    public void playSound2() {

        try {
            URL url = this.getClass().getClassLoader().getResource("sounds/lose.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }
    public void guessPlayer1(Graphics g, Player p, Player mystery){
        y = 250;
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(p.getFirstName() + " " + p.getLastName(), 50, y);

        g.drawRect(200, y-40, 80, 80);
        if(p.getPosition().equalsIgnoreCase(mystery.getPosition())){
            g.setColor(Color.green);
            g.fillRect(200, y-40, 80, 80);
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString(p.getPosition(), 230, y);

        g.drawRect(285, y-40, 80, 80);
        if(p.getTeam().equalsIgnoreCase(mystery.getTeam())){
            g.setColor(Color.green);
            g.fillRect(285, y-40, 80, 80);
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString(p.getTeam(), 310, y);

        g.drawRect(370, y-40, 80, 80);
        if(p.getConference().equalsIgnoreCase(mystery.getConference())){
            g.setColor(Color.green);
            g.fillRect(370, y-40, 80, 80);
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString(p.getConference(), 395, y);

        g.drawRect(455, y-40, 80, 80);
        if(p.getDivision().equalsIgnoreCase(mystery.getDivision())){
            g.setColor(Color.green);
            g.fillRect(455, y-40, 80, 80);
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString(p.getDivision(), 480, y);

        g.drawRect(540, y-40, 80, 80);
        if(p.getAge() == mystery.getAge()){
            g.setColor(Color.green);
            g.fillRect(540, y-40, 80, 80);
        } else if(p.getAge() > mystery.getAge()){
            g.setColor(Color.cyan.darker());
            g.fillRect(540, y-40, 80, 80);
        } else if(p.getAge() < mystery.getAge()){
            g.setColor(Color.red);
            g.fillRect(540, y-40, 80, 80);
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString(p.getAge() + "", 565, y);

        g.setFont(new Font("Arial", Font.PLAIN, 10));
        g.setColor(Color.black);
        g.drawString("          Position                  Team               Conference             Division                 Age", 190, 200);

    }
    public void guessPlayer2(Graphics g, Player p, Player mystery){
        y = 340;
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(p.getFirstName() + " " + p.getLastName(), 50, y);

        g.drawRect(200, y-40, 80, 80);
        if(p.getPosition().equalsIgnoreCase(mystery.getPosition())){
            g.setColor(Color.green);
            g.fillRect(200, y-40, 80, 80);
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString(p.getPosition(), 230, y);

        g.drawRect(285, y-40, 80, 80);
        if(p.getTeam().equalsIgnoreCase(mystery.getTeam())){
            g.setColor(Color.green);
            g.fillRect(285, y-40, 80, 80);
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString(p.getTeam(), 310, y);

        g.drawRect(370, y-40, 80, 80);
        if(p.getConference().equalsIgnoreCase(mystery.getConference())){
            g.setColor(Color.green);
            g.fillRect(370, y-40, 80, 80);
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString(p.getConference(), 395, y);

        g.drawRect(455, y-40, 80, 80);
        if(p.getDivision().equalsIgnoreCase(mystery.getDivision())){
            g.setColor(Color.green);
            g.fillRect(455, y-40, 80, 80);
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString(p.getDivision(), 480, y);

        g.drawRect(540, y-40, 80, 80);
        if(p.getAge() == mystery.getAge()){
            g.setColor(Color.green);
            g.fillRect(540, y-40, 80, 80);
        } else if(p.getAge() > mystery.getAge()){
            g.setColor(Color.cyan.darker());
            g.fillRect(540, y-40, 80, 80);
        } else if(p.getAge() < mystery.getAge()){
            g.setColor(Color.red);
            g.fillRect(540, y-40, 80, 80);
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString(p.getAge() + "", 565, y);

        g.setFont(new Font("Arial", Font.BOLD, 30));
        if(p == mystery){
            g.drawString("YOU WIN", 300, y+80);
            this.playSound();
        }
    }
    public void guessPlayer3(Graphics g, Player p, Player mystery){
        y = 430;
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(p.getFirstName() + " " + p.getLastName(), 50, y);

        g.drawRect(200, y-40, 80, 80);
        if(p.getPosition().equalsIgnoreCase(mystery.getPosition())){
            g.setColor(Color.green);
            g.fillRect(200, y-40, 80, 80);
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString(p.getPosition(), 230, y);

        g.drawRect(285, y-40, 80, 80);
        if(p.getTeam().equalsIgnoreCase(mystery.getTeam())){
            g.setColor(Color.green);
            g.fillRect(285, y-40, 80, 80);
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString(p.getTeam(), 310, y);

        g.drawRect(370, y-40, 80, 80);
        if(p.getConference().equalsIgnoreCase(mystery.getConference())){
            g.setColor(Color.green);
            g.fillRect(370, y-40, 80, 80);
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString(p.getConference(), 395, y);

        g.drawRect(455, y-40, 80, 80);
        if(p.getDivision().equalsIgnoreCase(mystery.getDivision())){
            g.setColor(Color.green);
            g.fillRect(455, y-40, 80, 80);
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString(p.getDivision(), 480, y);

        g.drawRect(540, y-40, 80, 80);
        if(p.getAge() == mystery.getAge()){
            g.setColor(Color.green);
            g.fillRect(540, y-40, 80, 80);
        } else if(p.getAge() > mystery.getAge()){
            g.setColor(Color.cyan.darker());
            g.fillRect(540, y-40, 80, 80);
        } else if(p.getAge() < mystery.getAge()){
            g.setColor(Color.red);
            g.fillRect(540, y-40, 80, 80);
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString(p.getAge() + "", 565, y);

    }
    public void guessPlayer4(Graphics g, Player p, Player mystery){
        y = 520;
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(p.getFirstName() + " " + p.getLastName(), 50, y);

        g.drawRect(200, y-40, 80, 80);
        if(p.getPosition().equalsIgnoreCase(mystery.getPosition())){
            g.setColor(Color.green);
            g.fillRect(200, y-40, 80, 80);
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString(p.getPosition(), 230, y);

        g.drawRect(285, y-40, 80, 80);
        if(p.getTeam().equalsIgnoreCase(mystery.getTeam())){
            g.setColor(Color.green);
            g.fillRect(285, y-40, 80, 80);
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString(p.getTeam(), 310, y);

        g.drawRect(370, y-40, 80, 80);
        if(p.getConference().equalsIgnoreCase(mystery.getConference())){
            g.setColor(Color.green);
            g.fillRect(370, y-40, 80, 80);
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString(p.getConference(), 395, y);

        g.drawRect(455, y-40, 80, 80);
        if(p.getDivision().equalsIgnoreCase(mystery.getDivision())){
            g.setColor(Color.green);
            g.fillRect(455, y-40, 80, 80);
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString(p.getDivision(), 480, y);

        g.drawRect(540, y-40, 80, 80);
        if(p.getAge() == mystery.getAge()){
            g.setColor(Color.green);
            g.fillRect(540, y-40, 80, 80);
        } else if(p.getAge() > mystery.getAge()){
            g.setColor(Color.cyan.darker());
            g.fillRect(540, y-40, 80, 80);
        } else if(p.getAge() < mystery.getAge()){
            g.setColor(Color.red);
            g.fillRect(540, y-40, 80, 80);
        }
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString(p.getAge() + "", 565, y);

        g.setFont(new Font("Arial", Font.BOLD, 30));
        if(p == mystery){
            g.drawString("YOU WIN", 300, y+80);
            this.playSound();
        } else {
            this.playSound2();
            g.setColor(Color.red);
            g.drawString("YOU LOST", 300, y+80);
            g.setColor(Color.black);
            g.drawString("The correct answer was: " + mystery.toString(), 50, y+120);
        }
    }

    public void paintComponent(Graphics g) {
		
		super.paintComponent(g); 
        if((game.getGuesses()%2) == 1){
            name_field.setVisible(true);
        }else if((game.getGuesses() % 2) == 0){
            name_field.setVisible(false);
        }


        g.setColor(Color.CYAN);
        g.fillRect(0, 0, 1366, 150);
        g.setColor(Color.black);
        g.setFont(new Font("Rockwell", Font.PLAIN, 30));
        g.drawString("NBA Guessing Game - Player 2", 200, 70);
        g.setFont(new Font("Helvetica", Font.PLAIN, 18));
        g.drawString("Guesses remaining: " + game.getGuesses(), 400, 130);
        g.setFont(new Font("Courier", Font.PLAIN, 20));
        g.drawString("Enter a name and press enter: ", 5, 175);

        if(draw && game.getGuesses() == 3){
            guessPlayer1(g, game.getTemp(), game.getMystery());
        }else if(draw && game.getGuesses() == 2){
            guessPlayer1(g, game.getTemp(), game.getMystery());
            guessPlayer2(g, temp1, game.getMystery());
        }else if(draw && game.getGuesses() == 1){
            guessPlayer1(g, game.getTemp(), game.getMystery());
            guessPlayer2(g, temp1, game.getMystery());
            guessPlayer3(g, game.getTemp2(), game.getMystery());
        }else if(draw && game.getGuesses() == 0){
            guessPlayer1(g, game.getTemp(), game.getMystery());
            guessPlayer2(g, temp1, game.getMystery());
            guessPlayer3(g, game.getTemp2(), game.getMystery());
            guessPlayer4(g, temp3, game.getMystery());
        }
    }
    
    public void keyPressed(KeyEvent e) { 
        if((e.getKeyCode() == 10) && game.getGuesses() > 0){
            String[] parts = name_field.getText().split(" ", 2);
            if(game.contains(parts[0], parts[1]) != null){
                name_field.setText("");
                y+=100;
                if(game.getGuesses() == 4){
                    temp = game.contains(parts[0], parts[1]);
                    game.setTemp(temp);
                    game.increment();
                } else if(game.getGuesses() == 3){
                    temp1 = game.contains(parts[0], parts[1]);
                    game.setTemp1(temp1);
                    game.increment();
                }else if(game.getGuesses() == 2){
                    temp2 = game.contains(parts[0], parts[1]);
                    game.setTemp2(temp2);
                    game.increment();
                } else if(game.getGuesses() == 1){
                    temp3 = game.contains(parts[0], parts[1]);
                    game.setTemp3(temp3);
                    game.increment();
                }
                draw = true;
            }
        }
        try {
            outObject.reset();
            outObject.writeObject(game);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        repaint();
    }
    
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public void poll() throws IOException{

		String hostName = "localhost"; 
		int portNumber = 1111;
		Socket serverSocket = new Socket(hostName, portNumber);
        outObject = new ObjectOutputStream(serverSocket.getOutputStream());
        inPush = new PushbackInputStream(serverSocket.getInputStream());
        inObject = new ObjectInputStream(serverSocket.getInputStream());

        while (true) {
            if(inPush.available() != 0){
                try {
                    
                    Object thing = inObject.readObject();
                    if(thing instanceof Game){
                        game = (Game)thing;
                        if(game.getTurn() == 50){
                            break;
                        }
                        repaint();
                    }
                } catch (ClassNotFoundException e) {
                    //TODO: handle exception
                }
            }
        }
		//listens for inputs
	}


}