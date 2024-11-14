import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.sound.sampled.*;

public class ServerScreen extends JPanel implements ActionListener,KeyListener{
    private Game game;
    private ObjectOutputStream outObject;
    private ObjectInputStream inObject;
    private PushbackInputStream inPush;
    private JTextField name_field;
    private JButton instructions;
    private JButton list;
    private JButton newGame;
    private int y;
    private Player temp;
    private Player temp1;
    private Player temp2;
    private Player temp3;
    private boolean draw;
    private JTextArea players;
    private JButton view_div;

    public ServerScreen() {
        game = new Game();

        players = new JTextArea();
        players.setEditable(false);
        players.setFont(new Font("Arial", Font.PLAIN, 18));
        players.setBounds(0, 580, 683, 190);
        players.append("Press 'New Game' in the top left to start the game\nEnter a player's name exactly as it appears on the name list (not case sensitive)\n\n4 guesses total, 2 per player, take turns guessing\n\nGreen means the section is correct\nBlue in the age column means the age is too high\nRed means it is too low" );
        this.add(players);

        name_field = new JTextField();
        name_field.setFont(new Font("Arial", Font.PLAIN, 20));
        name_field.setHorizontalAlignment(SwingConstants.LEFT);
        name_field.setBounds(360, 150, 320, 35);
        name_field.setText("");
        this.add(name_field);
        name_field.addKeyListener(this);
        name_field.setVisible(false);
 
        instructions = new JButton();
        instructions.setFont(new Font("Arial", Font.PLAIN, 20));
        instructions.setHorizontalAlignment(SwingConstants.CENTER);
        instructions.setBounds(17, 50, 160, 22);
        instructions.setText("View Instructions");
        this.add(instructions);
        instructions.addActionListener(this);

        list = new JButton();
        list.setFont(new Font("Arial", Font.PLAIN, 20));
        list.setHorizontalAlignment(SwingConstants.CENTER);
        list.setBounds(17, 80, 180, 22);
        list.setText("View List of Players");
        this.add(list);
        list.addActionListener(this);

        view_div = new JButton();
        view_div.setFont(new Font("Arial", Font.PLAIN, 20));
        view_div.setHorizontalAlignment(SwingConstants.CENTER);
        view_div.setBounds(17, 110, 130, 22);
        view_div.setText("View Divisions");
        this.add(view_div);
        view_div.addActionListener(this);

        newGame = new JButton();
        newGame.setFont(new Font("Arial", Font.PLAIN, 20));
        newGame.setHorizontalAlignment(SwingConstants.CENTER);
        newGame.setBounds(17, 20, 110, 22);
        newGame.setText("New Game");
        this.add(newGame);
        newGame.addActionListener(this);

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

        g.setFont(new Font("Arial", Font.BOLD, 30));
        if(p == mystery){
            g.drawString("YOU WIN", 300, y+80);
            this.playSound();
        }
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

        g.setFont(new Font("Arial", Font.BOLD, 30));
        if(p == mystery){
            g.drawString("YOU WIN", 300, y+80);
            this.playSound();
        }
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

    }

    public void paintComponent(Graphics g) {
		
		super.paintComponent(g); 
        if(game.getGuesses() == 2){
            name_field.setVisible(true);
        }else if((game.getGuesses() % 2) == 1){
            name_field.setVisible(false);
        }
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, 683, 150);
        g.setColor(Color.black);
        g.setFont(new Font("Rockwell", Font.PLAIN, 30));
        g.drawString("NBA Guessing Game - Player 1", 200, 70);
        g.setFont(new Font("Helvetica", Font.PLAIN, 18));
        g.drawString("Guesses remaining: " + game.getGuesses(), 400, 130);
        g.setFont(new Font("Courier", Font.PLAIN, 20));
        g.drawString("Enter a name and press enter: ", 5, 175);

        if(draw  && game.getGuesses() == 3 ){
            guessPlayer1(g, temp, game.getMystery());
        }else if(draw && game.getGuesses() == 2){
            guessPlayer1(g, temp, game.getMystery());
            guessPlayer2(g, game.getTemp1(), game.getMystery());
        }else if(draw && game.getGuesses() == 1){
            guessPlayer1(g, temp, game.getMystery());
            guessPlayer2(g, game.getTemp1(), game.getMystery());
            guessPlayer3(g, temp2, game.getMystery());
        }else if(draw && game.getGuesses() == 0){
            guessPlayer1(g, temp, game.getMystery());
            guessPlayer2(g, game.getTemp1(), game.getMystery());
            guessPlayer3(g, temp2, game.getMystery());
            guessPlayer4(g, game.getTemp3(), game.getMystery());
        }

    }
    
    public void keyPressed(KeyEvent e) { 
        if((e.getKeyCode() == 10)){
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
                } else if(game.getGuesses() == 2){
                    temp2 = game.contains(parts[0], parts[1]);
                    game.setTemp2(temp2);
                    game.increment();
                }else if(game.getGuesses() == 1){
                    temp3 = game.contains(parts[0], parts[1]);
                    game.setTemp3(temp3);
                    game.increment();
                }
                draw = true;
            }
        }
        repaint();
        try {
            outObject.reset();
            outObject.writeObject(game);
            
        } catch (IOException r) {
            System.out.println("broken");
        }
    }
    
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public void poll(){
        try {
            ServerSocket serverSocket = new ServerSocket(1111);
            Socket clientSocket = serverSocket.accept();

            System.out.println("Connected to the server!");

            outObject = new ObjectOutputStream(clientSocket.getOutputStream());
            inPush = new PushbackInputStream(clientSocket.getInputStream());
            inObject = new ObjectInputStream(clientSocket.getInputStream());

            outObject.writeObject(game);
            try {

                while (true) {
                    if(inPush.available() != 0){
                        try {
                            
                            Object thing = inObject.readObject();
                            if(thing instanceof Game){
                                game = (Game)thing;
                                repaint();
                            }
                        } catch (ClassNotFoundException e) {
                        }
                    }    
                }
            } catch (UnknownHostException e) {
                System.err.println("Host unkown: ");
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to ");
                System.exit(1);
            }
    

        } catch (IOException e) {
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == newGame){
            draw = false;
            game.makeList();
            game.setGuesses(4);
            name_field.setVisible(true);
            name_field.setText("");
        } else if(e.getSource() == instructions){
            players.setText("");
            players.setFont(new Font("Arial", Font.PLAIN, 18));
            players.append("Press 'New Game' in the top left to start the game\nEnter a player's name exactly as it appears on the name list (not case sensitive)\n\n4 guesses total, 2 per player, take turns guessing\n\nGreen means the section is correct\nBlue in the age column means the age is too high\nRed means it is too low" );
        } else if(e.getSource() == list){
            players.setText("");
            players.setFont(new Font("Arial", Font.PLAIN, 18));
            players.append(" Joel Embiid, LeBron James, Giannis Ant, Kevin Durant, Luka Doncic, Trae Young, \n DeMar DeRozan, Kyrie Irving, Ja Morant, Nikola Jokic, Jayson Tatum, Devin Booker, \n Stephen Curry, Karl Towns, Paul George, Damian Lillard, Anthony Davis, \n James Harden, Jimmy Butler, Klay Thompson\n\n Make sure you type the name exactly how it appears on this list");
        } else if(e.getSource() == view_div){
            players.setText("");
            players.setFont(new Font("Arial", Font.PLAIN, 16));
            players.append("                                     EAST                                                                         WEST\n\n          ATL: TOR, BOS, NYK, BRK, PHI                          PAC: GSW, LAC, LAL, PHO, SAC\n\n          CEN: CLE, DET, IND, MIL, CHI                            SW: SAS, MEM, HOU, DAL, NOP\n\n          SE: ATL, WAS, CHA, ORL, MIA                           NW: UTA, POR, OKC, MIN, DEN");
        }
        repaint();
        try {
            outObject.reset();
            outObject.writeObject(game);
            
        } catch (IOException r) {
            System.out.println("broken");
        }

    }

}