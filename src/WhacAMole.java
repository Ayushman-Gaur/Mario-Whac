import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Random;

public class WhacAMole {

    // These bellow two are dimension for the frame
    int boardWidth =600;
    int boardHeight= 650;

    // This is the frame
    JFrame frame = new JFrame("Mario: Whac A Mole");

    // These are text like score
    // There is panel for the text
    // Than a board panel
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    // This is array is for storing button
    JButton[] board = new JButton[9];

    // These are Images for the tile
    ImageIcon moleIcon;
    ImageIcon plantIcon;

    // this will keep track of the tile
    JButton currMoleTile;
    JButton currPlantTile;


    Random random = new Random();
    Timer setMoleTimer;
    Timer setPlantTimer;

    int score;

    public WhacAMole() {

        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());


        textLabel.setFont(new Font("Arial", Font.PLAIN,50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Score: 0");
        textLabel.setOpaque(true);


        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel,BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3,3));
        frame.add(boardPanel);

        //plantIcon = new ImageIcon(getClass().getResource("/Images/piranha.png"));
        Image plantImg= new ImageIcon(Objects.requireNonNull(getClass().getResource("./Images/piranha.png"))).getImage();
        plantIcon = new ImageIcon(plantImg.getScaledInstance(150,150, Image.SCALE_SMOOTH));


        Image moleImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("Images/monty.png"))).getImage();
        moleIcon = new ImageIcon(moleImg.getScaledInstance(150,150,Image.SCALE_SMOOTH));

        score=0;

        for(int i =0;i<9;i++){
            JButton tile= new JButton();
            board[i]= tile;
            boardPanel.add(tile);
            tile.setFocusable(false);
//            tile.setIcon(moleIcon);

            tile.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton tile = (JButton) e.getSource();
                    if(tile== currMoleTile){
                        score+=10;
                        textLabel.setText("Score: "+ Integer.toString(score));
                    }
                    else if(tile==currPlantTile){
                        textLabel.setText("Game Over:"+ Integer.toString(score));
                        setMoleTimer.stop();
                        setPlantTimer.stop();
                        for(int i =0;i<9;i++){
                            board[i].setEnabled(false);
                        }
                    }
                }
            });


        }
        setMoleTimer= new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove mole from current tile
                if(currMoleTile!=null){
                    currMoleTile.setIcon(null);
                    currMoleTile=null;
                }
                // Randomly select another tile
                int num = random.nextInt(9);
                JButton tile = board[num];

                //if tile is occupied than skip
                if(currPlantTile==tile) return;

                // set tile to mole
                currMoleTile = tile;
                currMoleTile.setIcon(moleIcon);
            }
        });


        setPlantTimer= new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currPlantTile!=null){
                    currPlantTile.setIcon(null);
                    currPlantTile=null;
                }

                int num = random.nextInt(9);
                JButton tile = board[num];

                if(currMoleTile==tile) return;


                currPlantTile= tile;
                currPlantTile.setIcon(plantIcon);
            }
        });

        setMoleTimer.start();
        setPlantTimer.start();
        frame.setVisible(true);
    }
}
