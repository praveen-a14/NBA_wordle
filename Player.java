import java.io.Serializable;

public class Player implements Serializable{
    private String position;
    private String first_name;
    private String last_name;
    private String team;
    private String division;
    private String conference;
    private int age;

    public Player(String first_name, String last_name, String position, int age, String conference, String divison, String team){
        this.first_name = first_name;
        this.last_name = last_name;
        this.position = position;
        this.age = age;
        this.conference = conference;
        this.division = divison;
        this.team = team;
    }

    public String getFirstName(){
        return first_name;
    }
    public String getLastName(){
        return last_name;
    }
    public String getPosition(){
        return position;
    }
    public int getAge(){
        return age;
    }
    public String getConference(){
        return conference;
    }
    public String getDivision(){
        return division;
    }
    public String getTeam(){
        return team;
    }
    public String toString(){
        return first_name + " " + last_name + "\n";
    }
}
