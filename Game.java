import java.io.*;
import java.util.*;
public class Game implements Serializable{
    private DLList<Player> players;
    private int turn;
    private Player mystery;
    private int guesses; 
    private Player temp;
    private Player temp1;
    private Player temp2;
    private Player temp3;

    public Game(){
        players = new DLList<Player>();
        guesses = 4;
        turn = 1;
    }
    public void makeList(){
        if(players.size() == 0){
        try{
            Scanner scan = new Scanner(new FileReader("PlayerList.txt"));
            while(scan.hasNextLine()){
                String temp = scan.nextLine();
                String[] parts = temp.split(" ", 7);
                String firstName = parts[0];
                String lastName = parts[1];
                String position = parts[2];
                int age = Integer.parseInt(parts[3]);
                String conf = parts[4];
                String div = parts[5];
                String team = parts[6];

                Player p = new Player(firstName, lastName, position, age, conf, div, team);
                players.add(p);
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } 
    }
        int rand = (int)(Math.random()*20);
        mystery = players.get(rand);
        System.out.println(mystery);
    }
    public Player contains(String firstName, String lastName){
        for(int i=0; i<players.size(); i++){
            if(players.get(i).getFirstName().equalsIgnoreCase(firstName) && players.get(i).getLastName().equalsIgnoreCase(lastName)){
                return players.get(i);
            }
        }
        if(turn==1){
            turn = 2;
        }else{
            turn = 1;
        }
        return null;
    }
    public Player getMystery(){
        return mystery;
    }
    public int getTurn(){
        return turn;
    }
    public int getGuesses(){
        return guesses;
    }
    public void increment(){
        guesses--;
    }
    public void setGuesses(int guesses){
        this.guesses = guesses;
    }
    public DLList<Player> getList(){
        return players;
    }
    public void setTemp(Player temp){
        this.temp = temp;
    }
    public void setTemp1(Player temp1){
        this.temp1 = temp1;
    }
    public void setTemp2(Player temp2){
        this.temp2 = temp2;
    }
    public void setTemp3(Player temp3){
        this.temp3 = temp3;
    }

    public Player getTemp(){
        return temp;
    }
    public Player getTemp1(){
        return temp1;
    }
    public Player getTemp2(){
        return temp2;
    }
    public Player getTemp3(){
        return temp3;
    }

    public void print(){
        System.out.println(players.toString());
    }
}

