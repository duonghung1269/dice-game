package sample;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;

import java.util.*;

import javax.swing.border.Border;

public class DiceGame extends JFrame{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 3127764301745043970L;

	JFrame frame;
    
    private JPanel panelGame;
    private JPanel titlePan;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel dicePanel; // animation panel
    private JPanel panel4, panel4b;
    private JPanel panel5;
    private JPanel panel6;
    private JPanel panel7;
    private JPanel panel8;
    
    private JLabel title;
    
    private Label player1;
    public String user_name_1;
    private TextField name1;
    private Button playSub1;
    private int click1 = 0;
    
    
    private Label player2;
    private String user_name_2;
    private TextField name2;
    private Button playSub2;
    private int click2 = 0;
    
    private int turn; //turn counter for game altogether
    private int turn_1; //turn counter for player 1
    private int turn_2; //turn counter for player 2
    private int life_pt_1, life_pt_2; //life points left
    private int pts_1, pts_2; //number of points each player had
    private int res_1, res_2; //roll of each player
    private TextField life_1, life_2;
    private TextField p_1, p_2;
    private Label sub_name_1, sub_name_2;
    
    private Button roll;
    private Label rollNum;
    private TextField showRoll;
    private int random;
    
    private Button start;
    
    private Label player_names_1, player_names_2;
    private JTextField p_names_1, p_names_2;
    
    private Label life_lbl;
    private Label pts_lbl;
    private Label play_lbl;
    private Label tab_life, tab_pts;
    private JTextField life_field_1, life_field_2;
    private JTextField pts_field_1, pts_field_2;
    
    private JLabel diceImageIcon;
    
    Random elementGenerator;
    String element;
    
    private JLabel winner;
    private JTextField declare;
    
    
    private JButton reset;
    
    // store 6 faces of a dice
    private Dice[] diceFaces;
    
    {
    	diceFaces = new Dice[6];
    	for (int i = 0; i < 6; i++) {
    		int diceValue = i + 1;
    		Image img = ImageIO.read(getClass().getResource("dice-" + diceValue + ".png"));
    		Dice dice = new Dice(diceValue, new ImageIcon(img));
    		diceFaces[i] = dice;
    	}
    }
    
    private void updateDiceImage(int diceValue) {
        diceImageIcon.setIcon(diceFaces[diceValue].getIcon());
    }
    
    public DiceGame() throws IOException{
        
        
        //Customize Appearance of Game
        Border border1 = BorderFactory.createLineBorder(Color.BLUE, 5);
        Border border2 = BorderFactory.createLineBorder(Color.PINK, 5);
        Border border3 = BorderFactory.createLineBorder(Color.GRAY, 5);
        
        //Set Frame size and color
        setSize(500,600);
        
        life_pt_1 = 3;
        life_pt_2 = 3;
        pts_1 = 0;
        pts_2 = 0;
        
        elementGenerator = new Random();
        
        panelGame = new JPanel(); //Get player information
        panelGame.setLayout(new BoxLayout(panelGame, BoxLayout.Y_AXIS));
        add(panelGame);
        
        
        //Add title to the game
        titlePan = new JPanel(new FlowLayout());
        title = new JLabel("Ultimate Dice Game");
        titlePan.setBackground(Color.BLACK);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Impact", Font.PLAIN, 50));
        panelGame.add(titlePan);
        titlePan.add(title);
        
        
        //Get player 1 information
        panel1 = new JPanel(new FlowLayout()); //For getting player info
        panelGame.add(panel1, panel1.CENTER_ALIGNMENT);
        panel1.setBackground(Color.BLACK);
        
        player1 = new Label("Player 1: ");
        player1.setForeground(Color.WHITE);
        name1 = new TextField(10);
        
        playSub1 = new Button("Submit");
        
        panel1.add(player1);
        panel1.add(name1);
        panel1.add(playSub1);
        
        
        registerPlayer1SubmitAction();
        
        //Get Player 2 information
        panel2 = new JPanel(new FlowLayout()); //For getting player info
        panelGame.add(panel2, panel2.CENTER_ALIGNMENT);
        panel2.setBackground(Color.BLACK);
        
        player2 = new Label("Player 2: ");
        player2.setForeground(Color.WHITE);
        name2 = new TextField(10);
        playSub2 = new Button("Submit");
        
        panel2.add(player2);
        panel2.add(name2);
        panel2.add(playSub2);
        
        registerPlayer2SubmitAction();
        
        //Start game
        panel3 = new JPanel(new FlowLayout()); //For getting player info
        panelGame.add(panel3, panel3.CENTER_ALIGNMENT);
        panel3.setBackground(Color.BLACK);
        start = new Button("START");
        panel3.add(start);
        
        // Initialize dice panel to display animation
        dicePanel = new JPanel(new FlowLayout());
        panelGame.add(dicePanel, dicePanel.CENTER_ALIGNMENT);
        dicePanel.setBackground(Color.BLACK);
        diceImageIcon = new JLabel();
        dicePanel.add(diceImageIcon);
        
        //Roll
        panel4 = new JPanel(new FlowLayout()); //For getting player info
        panelGame.add(panel4, panel4.CENTER_ALIGNMENT);
        panel4.setBackground(Color.BLACK);
        
        roll = new Button("Roll!");
        rollNum = new Label("Your roll: ");
        rollNum.setForeground(Color.WHITE);
        roll.setEnabled(false);
        
        showRoll = new TextField(10);
        turn = 0;
        turn_1 = 0;
        turn_2 = 0;
        pts_1 = 0;
        pts_2 = 0;
        
        panel4.add(roll);
        panel4.add(rollNum);
        panel4.add(showRoll);
        
        registerRollAction();
        
        
        //Player Stats
        panel4b = new JPanel(new FlowLayout()); //For getting player info
        panelGame.add(panel4b, panel4b.CENTER_ALIGNMENT);
        panel4b.setBackground(Color.BLACK);
        
        player_names_1 = new Label("     Player 1:");
        player_names_1.setForeground(Color.WHITE);
        
        player_names_2 = new Label("Player 2:");
        player_names_2.setForeground(Color.WHITE);
        
        p_names_1 = new JTextField(5);
        p_names_2 = new JTextField(5);
        
        p_names_1.setBorder(border1);
        p_names_2.setBorder(border1);
        
        p_names_1.setEditable(false);
        p_names_2.setEditable(false);
        
        panel4b.add(player_names_1);
        panel4b.add(p_names_1);
        panel4b.add(player_names_2);
        panel4b.add(p_names_2);
        
        //Universal lab variable
        tab_life = new Label("            ");
        tab_pts = new Label("            ");
        
        //Life points stats
        panel5 = new JPanel(new FlowLayout()); //For getting player info
        panelGame.add(panel5, panel5.CENTER_ALIGNMENT);
        panel5.setBackground(Color.BLACK);
        
        
        life_lbl = new Label(" Life points: ");
        life_lbl.setForeground(Color.white);
        life_field_1 = new JTextField(5);
        life_field_2 = new JTextField(5);
        
        life_field_1.setBorder(border2);
        life_field_2.setBorder(border2);
        
        life_field_1.setEditable(false);
        life_field_2.setEditable(false);
        
        panel5.add(life_lbl);
        panel5.add(life_field_1);
        panel5.add(tab_life);
        panel5.add(life_field_2);
        
        
        //Points output
        panel6 = new JPanel(new FlowLayout()); //For getting player info
        panelGame.add(panel6, panel6.CENTER_ALIGNMENT);
        panel6.setBackground(Color.BLACK);
        
        pts_lbl = new Label("Total points: ");
        pts_lbl.setForeground(Color.WHITE);
        pts_field_1 = new JTextField(5);
        pts_field_2 = new JTextField(5);
        
        pts_field_1.setBorder(border2);
        pts_field_2.setBorder(border2);
        
        pts_field_1.setEditable(false);
        pts_field_2.setEditable(false);
        
        panel6.add(pts_lbl);
        panel6.add(pts_field_1);
        panel6.add(tab_pts);
        panel6.add(pts_field_2);
        
        registerStartAction();
        
        
        //Game output/ Winner Declaration
        panel7 = new JPanel(new FlowLayout()); //For getting player info
        panelGame.add(panel7, panel7.CENTER_ALIGNMENT);
        panel7.setBackground(Color.BLACK);
        
        winner = new JLabel("Winner: ");
        winner.setForeground(Color.WHITE);
        declare = new JTextField(15);
        
        declare.setBorder(border3);
        declare.setEditable(false);
        
        panel7.add(winner);
        panel7.add(declare);
        
        //Reset Game
        panel8 = new JPanel(new FlowLayout()); //For getting player info
        panelGame.add(panel8, panel8.CENTER_ALIGNMENT);
        panel8.setBackground(Color.BLACK);
        
        reset = new JButton("RESET");
        panel8.add(reset);
        
        registerResetAction();
        
        panelGame.setVisible(true);
        
        //Window listener
        addWindowListener
        (new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        }
         );
    }

	private void registerResetAction() {
		reset.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                start.setEnabled(true);
                
                name1.setEditable(true);
                playSub1.setEnabled(true);
                
                name2.setEditable(true);
                playSub2.setEnabled(true);
                
                p_names_2.setText(" ");
                p_names_1.setText(" ");
                declare.setText(" ");
                showRoll.setText(" ");
                
                p_names_1.setBackground(Color.WHITE);
                p_names_2.setBackground(Color.WHITE);
                
                life_pt_1 = 3;
                life_pt_2 = 3;
                pts_1 = 0;
                pts_2 = 0;
                
                life_field_1.setText(" ");
                life_field_2.setText(" ");
                
                pts_field_1.setText(" ");
                pts_field_2.setText(" ");
                
                
                
            }
        });
	}

	private void registerStartAction() {
		start.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                
                life_field_1.setText("3");
                life_field_2.setText("3");
                
                pts_field_1.setText("0");
                pts_field_2.setText("0");
                
                start.setEnabled(false);
                roll.setEnabled(true);
                
                
            }
        });
	}

	private void registerPlayer2SubmitAction() {
		playSub2.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                click2 = 1;
                user_name_2 = name2.getText();
                p_names_2.setText(user_name_2);
                name2.setText(" ");
                name2.setEditable(false);
                playSub2.setEnabled(false);
                
            }
        });
	}

	private void registerPlayer1SubmitAction() {
		playSub1.addActionListener(new ActionListener() {
            
            
            public void actionPerformed(ActionEvent e)
            {
                //Execute when button is pressed
                click1 = 1;
                user_name_1 = name1.getText();
                sub_name_1 = new Label(user_name_1);
                p_names_1.setText(user_name_1);
                name1.setText(" ");
                name1.setEditable(false);
                playSub1.setEnabled(false);
            }
        });
	}

	private void registerRollAction() {
		roll.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e)
            {
            	roll.setEnabled(false);
                Timer timer1 = new Timer(100, new ActionListener() {
        			private int counter = 0;
        			private int randomRollNumber = 5 + elementGenerator.nextInt(5);
        			
        			@Override
        			public void actionPerformed(ActionEvent timerEvent) {
        				if (counter < randomRollNumber) {
        					counter++;
            				random = elementGenerator.nextInt(6);
            				updateDiceImage(random);
            				if(random == 0){
                                element = "FIRE";
                                showRoll.setText("FIRE");
                            }else if(random == 1){
                                element = "WATER";
                                showRoll.setText("WATER");
                            }else if(random == 2){
                                element = "ELECTRIC";
                                showRoll.setText("ELECTRIC");
                            }else if(random == 3){
                                element = "GRASS";
                                showRoll.setText("GRASS");
                            }else if(random == 4){
                                element = "ICE";
                                showRoll.setText("ICE");
                            }else if(random == 5){
                                element = "EARTH";
                                showRoll.setText("EARTH");
                            }
            				System.out.println("dice value: " + (random + 1) );
        				} else {
        					System.out.println("last round");
        					roll.setEnabled(true);
        					((Timer)timerEvent.getSource()).stop();
        					doAfterRoll();
        				}
        			}
        		});
                timer1.start();
                
            }

            private void doAfterRoll() {
                turn++;
                
                //If player 1 rolls
                if(turn % 2 == 1)
                {
                    //p_names_1.setBackground(Color.RED);
                    //p_names_2.setBackground(Color.WHITE);
                    //player_names_1.setForeground(Color.RED);
                    //player_names_1.setForeground(Color.WHITE);
                    turn_1 = 1;
                    res_1 = random;
                    
                }else{
                    //If player 2 rolls
                    //	p_names_1.setForeground(Color.WHITE);
                    //p_names_2.setForeground(Color.RED);
                    turn_2 = 1;
                    res_2 = random;
                }
                
                //Color Changes for Player 1
                if(res_1  == 0)
                {
                    p_names_1.setBackground(Color.RED);
                }
                else if(res_1  == 1)
                {
                    p_names_1.setBackground(Color.BLUE);
                }
                else if(res_1  == 2)
                {
                    p_names_1.setBackground(Color.YELLOW);
                }
                else if(res_1  == 3)
                {
                    p_names_1.setBackground(Color.GREEN);
                }
                else if(res_1  == 4)
                {
                    p_names_1.setBackground(Color.CYAN);
                }
                else if(res_1  == 5)
                {
                    p_names_1.setBackground(Color.MAGENTA);
                }
                
                
                //Color Changes for Player 2
                if(res_2  == 0)
                {
                    p_names_2.setBackground(Color.RED);
                }
                else if(res_2  == 1)
                {
                    p_names_2.setBackground(Color.BLUE);
                }
                else if(res_2  == 2)
                {
                    p_names_2.setBackground(Color.YELLOW);
                }
                else if(res_2  == 3)
                {
                    p_names_2.setBackground(Color.GREEN);
                }
                else if(res_2  == 4)
                {
                    p_names_2.setBackground(Color.CYAN);
                }
                else if (res_2  == 5)
                {
                    p_names_2.setBackground(Color.MAGENTA);
                }
                else
                {
                    p_names_2.setBackground(Color.WHITE);
                }
                
                //Total points tracker
                
                if(turn_1 == 1 && turn_2 == 1)
                {
                    //If both players have rolled, player 2 beats player 1
                    //FIRE PLAYER 1
                    if(res_1 == 0 && (res_2 == 1 || res_2 == 5))
                    {
                        //FIRE is beat by WATER & EARTH
                        pts_2 += 10;
                        pts_field_2.setText(Integer.toString(pts_2));
                        life_pt_1--;
                        life_field_1.setText(Integer.toString(life_pt_1));
                        
                        
                        
                    }
                    //FIRE PLAYER 2
                    else if(res_2 == 0 && (res_1 == 1 || res_1 == 5))
                    {
                        //FIRE is beat by WATER & EARTH
                        pts_1 += 10;
                        pts_field_1.setText(Integer.toString(pts_1));
                        life_pt_2--;
                        life_field_2.setText(Integer.toString(life_pt_2));
                        
                        
                    }
                    else
                    {
                        pts_1 = pts_1;
                    }
                    //WATER PLAYER 1
                    if(res_1 == 1 && (res_2 == 2 || res_2 == 3))
                    {
                        //WATER is beat by ELECTRIC & GRASS
                        pts_2 += 20;
                        pts_field_2.setText(Integer.toString(pts_2));
                        life_pt_1--;
                        life_field_1.setText(Integer.toString(life_pt_1));
                        
                    }
                    //WATER PLAYER 2
                    else if(res_2 == 1 && (res_1 == 2 || res_1 == 3))
                    {
                        //WATER is beat by ELECTRIC & GRASS
                        pts_1 += 20;
                        pts_field_1.setText(Integer.toString(pts_1));
                        life_pt_2--;
                        life_field_2.setText(Integer.toString(life_pt_2));
                        
                    }
                    else
                    {
                        pts_1 = pts_1;
                    }
                    //ELECTRIC PLAYER 1
                    if(res_1 == 2 && (res_2 == 3 || res_2 == 5))
                    {
                        //ELECTRIC is beat by GRASS & EARTH
                        pts_2 += 30;
                        pts_field_2.setText(Integer.toString(pts_2));
                        life_pt_1--;
                        life_field_1.setText(Integer.toString(life_pt_1));
                        
                    }
                    //ELECTRIC PLAYER 2
                    else if(res_2 == 2 && (res_1 == 3 || res_1 == 5))
                    {
                        //ELECTRIC is beat by GRASS & EARTH
                        pts_1 += 30;
                        pts_field_1.setText(Integer.toString(pts_1));
                        life_pt_2--;
                        life_field_2.setText(Integer.toString(life_pt_2));
                        
                    }
                    else
                    {
                        pts_1 = pts_1;
                    }
                    //GRASS PLAYER 1
                    if(res_1 == 3 && (res_2 == 0 || res_2 == 4))
                    {
                        //GRASS is beat by FIRE & ICE
                        pts_2 += 40;
                        pts_field_2.setText(Integer.toString(pts_2));
                        life_pt_1--;
                        life_field_1.setText(Integer.toString(life_pt_1));
                        
                    }
                    //GRASS PLAYER 2
                    else if(res_2 == 3 && (res_1 == 0 || res_1 == 4))
                    {
                        //GRASS is beat by FIRE & ICE
                        pts_1 += 40;
                        pts_field_1.setText(Integer.toString(pts_1));
                        life_pt_2--;
                        life_field_2.setText(Integer.toString(life_pt_2));
                        
                    }
                    else
                    {
                        pts_1 = pts_1;
                    }
                    //ICE PLAYER 1
                    if(res_1 == 4 && (res_2 == 0 || res_2 == 2))
                    {
                        //ICE is beat by FIRE & ELECTRIC
                        pts_2 += 50;
                        pts_field_2.setText(Integer.toString(pts_2));
                        life_pt_1--;
                        life_field_1.setText(Integer.toString(life_pt_1));
                        
                    }
                    //ICE PLAYER 2
                    else if(res_2 == 4 && (res_1 == 0 || res_1 == 2))
                    {
                        //ICE is beat by FIRE & ELECTRIC
                        pts_1 += 50;
                        pts_field_1.setText(Integer.toString(pts_1));
                        life_pt_2--;
                        life_field_2.setText(Integer.toString(life_pt_2));
                        
                    }
                    else
                    {
                        pts_1 = pts_1;
                    }
                    //EARTH PLAYER 1
                    if(res_1 == 5 && (res_2 == 1 || res_2 == 4))
                    {
                        //EARTH is beat by WATER & ICE
                        pts_2 += 60;
                        pts_field_2.setText(Integer.toString(pts_2));
                        life_pt_1--;
                        life_field_1.setText(Integer.toString(life_pt_1));
                        
                    }
                    //EARTH PLAYER 2
                    else if(res_2 == 5 && (res_1 == 1 || res_1 == 4))
                    {
                        //EARTH is beat by WATER & ICE
                        pts_1 += 60;
                        pts_field_1.setText(Integer.toString(pts_1));
                        life_pt_2--;
                        life_field_2.setText(Integer.toString(life_pt_2));
                        
                    }
                    else
                    {
                        pts_1 = pts_1;
                    }
                    
                    
                    
                    //Winner declaration
                    if(life_pt_1 == 0)
                    {
                        declare.setText(user_name_2);
                        roll.setEnabled(false);
                    }
                    
                    if(life_pt_2 == 0)
                    {
                        declare.setText(user_name_1);
                        roll.setEnabled(false);
                    }
                    
                    turn_1 = 0;
                    turn_2 = 0;
                }
            }
            
//        	private void rollDiceAnimation(Timer timer1) {
//        		timer1.start();
//        		try {
//        			Thread.sleep(3000);
//        		} catch (InterruptedException e) {
//        			System.out.println("InterrupException: " + e);
//        		}
//        		timer1.stop();
//        	}
        });
	}
    

    
    public static void main(String[] args) throws IOException{
        
        Frame f = new DiceGame();
        f.show();
    }
    
}