import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PlayingPerson extends JFrame { //extension might not be necessary

    protected Enemies current; //the enemy both the player and character are currently against. Choosing of it will be done in subclasses
    protected Character player; //burada ilk olarak caveman için obje yaratılacak, ve her karakter yaratılışında enemy tipi yollanacak constructor'a
    protected ArrayList<Item> collection;
    protected ArrayList<Weapons> shortRangeWeapons;//to collect the short range weapons that were bought
    protected ArrayList<Weapons> longRangeWeapons;//to collect the long range weapons that were bought
    protected int XP;
    protected JButton forward = new JButton("forward");
    protected JButton backward = new JButton("backward");
    protected JButton shortRangeAttackButton = new JButton("attack short range");
    protected JButton longRangeAttackButton = new JButton("attack long range");
    protected int money;
    protected ToolStore toolstore;
    protected int XPlimit;
    protected int level;

    public PlayingPerson(){
       //main method main adı verilen bir class'ta oluşturulacak ve bir menuframe objesi oluşturulacak 
       //buradan karakter yaratan method çağırılacak
        shortRangeWeapons = new ArrayList<Weapons>();
        longRangeWeapons = new ArrayList<Weapons>();
        toolstore = new ToolStore(); //every player will have acces to toolstore
        money = 0;//both money and xp are 0 at the beginning
        XP = 0;
        XPlimit = 50;//the amount of XP needed to finish the first level
        level = 1;
        forward.addActionListener(new goForward());
        backward.addActionListener(new goBackward());
        shortRangeAttackButton.addActionListener(new shortRange());
        longRangeAttackButton.addActionListener(new longRange());
    }

    class goForward implements ActionListener{
        public void actionPerformed(ActionEvent event){
            
            if((current.x > player.x + 160) && player.x + 80 < 320){ //düşmanın x koordinatını alıp onunla çakışmayacağına emin olmak lazım
                player.x+=80;
                current.move();//in this case enemy will move after the character and will be able to move if the player made a valid move
            } 
        }
    }

    class goBackward implements ActionListener{
        public void actionPerformed(ActionEvent event){

            if (player.x - 80 > 0){//to check limits 
                player.x-= 80;
                current.move(); 
            } 
        }
    }

    class shortRange implements ActionListener{
        public void actionPerformed(ActionEvent event){

            player.setShortRangeDamage(shortRangeWeapons.get(shortRangeWeapons.size() - 1));
            current.extractLoss(player.finalDamage);//the opponent will lose the calculated amount of points
            player.checkForMoney();
            checkLevel();
            current.move();
        }
    }

    class longRange implements ActionListener{
        public void actionPerformed(ActionEvent event){

            player.setLongRangeDamage(longRangeWeapons.get(longRangeWeapons.size() - 1));
            current.extractLoss(player.finalDamage);//the opponent will lose the calculated amount
            player.checkForMoney();
            checkLevel();
            current.move();
        }
    }

    public void attackFromEnemy(){//when opponent attacks the health of the player is decreased accordingly

        current.attackRange();
        player.modifyDamageFromEnemy(current.damageFromEnemy);
        player.extractLoss();
    }

    public void checkLevel(){//to level up if necessary
        if (XP >= XPlimit){
            XP = XP - XPlimit; 
            level++;
            XPlimit = XPlimit * 2;//level points are doubled for every next level
        }
    }
}
