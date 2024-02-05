import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SquareGame extends JPanel{


    Boolean[][] board=new Boolean[51][51];
    ArrayList<Square> helper;
    int alive=0;
    int enemy=0;
    Timer timer;

    {
        for (int i=0;i<51;i++)
            Arrays.fill(board[i], false);

        board[25][25]=true;


    }

    ArrayList<Square> fires=new ArrayList<>();



     static class Square{

         boolean living=true;
         int locationX;
         int locationY;
         Color color;
         int way;//0 is left, 1 is right
         int friend=0;

         public Square(int locationX, int locationY, Color color) {
             this.locationX = locationX;
             this.locationY = locationY;
             this.color=color;
         }
     }

     public SquareGame(){

         JFrame frame = new JFrame("Square Game");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.getContentPane().add(this);

         helper=new ArrayList<>();
         setFocusable(true);

         timer = new Timer(100, new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {

                 for (int i=0;i<fires.size();i++){

                     if(fires.get(i).way==1)
                         fires.get(i).locationX += 10;
                     else
                         fires.get(i).locationX -= 10;

                     if(isEnemyFired(fires.get(i).locationX,fires.get(i).locationY,fires.get(i).friend))
                         fires.get(i).living=false;

                     winner();
                 }
                 clear();
                 repaint();
             }
         });
         timer.start();

         /*Timer timer1 = new Timer(500, new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {

                 for (int i=1;i<helper.size();i++){

                     int key=(int) (Math.random()*4);

                     if(key==0){
                         if(helper.get(i).locationX-10<0) {
                             i--;
                             continue;
                         }
                         helper.get(i).locationX -= 10;
                     }
                     if(key==1){
                         if(helper.get(i).locationX+10>getWidth()-10) {
                             i--;
                             continue;
                         }
                         helper.get(i).locationX += 10;
                     }
                     if(key==2){
                         if(helper.get(i).locationY-10<0) {
                             i--;
                             continue;
                         }
                         helper.get(i).locationY -= 10;
                     }
                     if(key==3){
                         if(helper.get(i).locationY+10>getHeight()-10) {
                             i--;
                             continue;
                         }
                         helper.get(i).locationY += 10;
                     }
                     ontoClear();


                 }

                 repaint();
             }
         });
         timer1.start();*/

         //forFire();

         addKeyListener(new KeyAdapter() {
             @Override
             public void keyPressed(KeyEvent e) {

                 if(!helper.get(0).living)
                     return;

                 if(e.getKeyCode()==KeyEvent.VK_W) {
                     if(helper.get(0).locationY>=10) {
                         helper.get(0).locationY -= 10;
                         repaint();
                     }
                 }
                 if(e.getKeyCode()==KeyEvent.VK_S) {
                     if(helper.get(0).locationY<=getHeight()-20) {
                         helper.get(0).locationY += 10;
                         repaint();
                     }
                 }
                 if(e.getKeyCode()==KeyEvent.VK_A) {
                     if(helper.get(0).locationX>=10) {
                         helper.get(0).locationX -= 10;
                         repaint();
                     }
                 }
                 if(e.getKeyCode()==KeyEvent.VK_D) {

                     if(helper.get(0).locationX<=getWidth()-20) {
                         helper.get(0).locationX += 10;
                         repaint();
                     }
                 }
             }
         });


         addMouseListener(new MouseInputListener() {
             @Override
             public void mouseClicked(MouseEvent e) {

             }

             @Override
             public void mousePressed(MouseEvent e) {

                 if(!helper.get(0).living)
                     return;

                 Square s1=new Square(helper.get(0).locationX,helper.get(0).locationY,Color.ORANGE);
                 s1.friend=1;
                 s1.way=1;
                 fires.add(s1);
                 Square s2=new Square(helper.get(0).locationX,helper.get(0).locationY,Color.ORANGE);
                 s2.friend=1;
                 s2.way=0;
                 fires.add(s2);
                 timer.start();
             }

             @Override
             public void mouseReleased(MouseEvent e) {

             }

             @Override
             public void mouseEntered(MouseEvent e) {

             }

             @Override
             public void mouseExited(MouseEvent e) {

             }

             @Override
             public void mouseDragged(MouseEvent e) {

             }

             @Override
             public void mouseMoved(MouseEvent e) {

             }
         });


         setPreferredSize(new Dimension(500, 500));
         setVisible(true);

         frame.pack();
         frame.setVisible(true);


     }

     /*public SquareGame(int alive, int enemy){

        JFrame frame = new JFrame("Square Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);


        this.alive=alive;
        this.enemy=enemy;
        helper=new Square[alive+enemy+1];
        fillHelper();
        setFocusable(true);

        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (int i=0;i<fires.size();i++){

                    if(fires.get(i).way==1)
                        fires.get(i).locationX += 10;
                    else
                        fires.get(i).locationX -= 10;

                    if(isEnemyFired(fires.get(i).locationX,fires.get(i).locationY,fires.get(i).friend))
                        fires.get(i).living=false;

                    winner();
                }
                clear();
                repaint();
            }
        });
        timer.start();

        Timer timer1 = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (int i=1;i<helper.length;i++){

                    int key=(int) (Math.random()*4);

                    if(key==0){
                        if(helper[i].locationX-10<0) {
                            i--;
                            continue;
                        }
                        helper[i].locationX -= 10;
                    }
                    if(key==1){
                        if(helper[i].locationX+10>getWidth()-10) {
                            i--;
                            continue;
                        }
                        helper[i].locationX += 10;
                    }
                    if(key==2){
                        if(helper[i].locationY-10<0) {
                            i--;
                            continue;
                        }
                        helper[i].locationY -= 10;
                    }
                    if(key==3){
                        if(helper[i].locationY+10>getHeight()-10) {
                            i--;
                            continue;
                        }
                        helper[i].locationY += 10;
                    }
                    ontoClear();


                }

                repaint();
            }
        });
        timer1.start();

        forFire();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if(!helper[0].living)
                    return;

                if(e.getKeyCode()==KeyEvent.VK_W) {
                    if(helper[0].locationY>=10) {
                        helper[0].locationY -= 10;
                        repaint();
                    }
                }
                if(e.getKeyCode()==KeyEvent.VK_S) {
                    if(helper[0].locationY<=getHeight()-20) {
                        helper[0].locationY += 10;
                        repaint();
                    }
                }
                if(e.getKeyCode()==KeyEvent.VK_A) {
                    if(helper[0].locationX>=10) {
                        helper[0].locationX -= 10;
                        repaint();
                    }
                }
                if(e.getKeyCode()==KeyEvent.VK_D) {

                    if(helper[0].locationX<=getWidth()-20) {
                        helper[0].locationX += 10;
                        repaint();
                    }
                }
            }
        });


        addMouseListener(new MouseInputListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                if(!helper[0].living)
                    return;

                Square s1=new Square(helper[0].locationX,helper[0].locationY,Color.ORANGE);
                s1.friend=1;
                s1.way=1;
                fires.add(s1);
                Square s2=new Square(helper[0].locationX,helper[0].locationY,Color.ORANGE);
                s2.friend=1;
                s2.way=0;
                fires.add(s2);
                timer.start();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });


        setPreferredSize(new Dimension(500, 500));
        setVisible(true);

        frame.pack();
        frame.setVisible(true);

    }*/

    @Override
    public void paintComponent(Graphics g) {

         if(helper.size()==0)
             return;

         super.paintComponent(g);

         for (int i=0;i<helper.size();i++) {

             if(!helper.get(i).living)
                 continue;

             g.setColor(helper.get(i).color);
             g.fillRect(helper.get(i).locationX, helper.get(i).locationY, 10, 10);
         }
         for (int i=0;i<fires.size();i++) {
             g.setColor(fires.get(i).color);
             g.fillRect(fires.get(i).locationX, fires.get(i).locationY, 5, 5);
         }

    }

    void clear(){

        fires.removeIf(square -> square.locationX>getWidth());
        fires.removeIf(square -> square.locationX<0);
        fires.removeIf(square -> square.locationY>getHeight());
        fires.removeIf(square -> square.locationX<0);
        fires.removeIf(square -> !square.living);

    }

    void fillHelper(){

         if(helper.size()==0)
             return;


         helper.add(new Square(250,250,Color.RED));
         helper.get(0).friend=1;

         int k=1;
         for (int i=0;i<alive;i++){

             int x=(int) (Math.random()*50)*10;
             int y=(int) (Math.random()*50)*10;

             if(!board[x/10][y/10]) {
                 helper.add(new Square(x, y, Color.GREEN));
                 helper.get(k).friend=1;
                 k++;
                 board[x/10][y/10]=true;
             }
             else i--;
         }

        for (int i=0;i<enemy;i++){

            int x=(int) (Math.random()*50)*10;
            int y=(int) (Math.random()*50)*10;

            if(!board[x/10][y/10]) {
                helper.add(new Square(x, y, Color.BLACK));
                k++;
                board[x/10][y/10]=true;
            }
            else i--;
        }

    }

    boolean isEnemyFired(int x,int y,int friend){

        if (friend==0)
            if(x==helper.get(0).locationX && y==helper.get(0).locationY)
                helper.get(0).living=false;

         for(int i=1;i<helper.size();i++){

             if (helper.get(i).friend==0 && friend==1 && helper.get(i).living)
                 if(y==helper.get(i).locationY && x==helper.get(i).locationX){
                     helper.get(i).living = false;
                     return true;
                 }

             if (helper.get(i).friend==1 && friend==0 && helper.get(i).living)
                 if(y==helper.get(i).locationY && x==helper.get(i).locationX) {
                     helper.get(i).living = false;
                     return true;
                 }

         }

         for (int i=0;i<helper.size();i++){

             if(x==helper.get(i).locationX && y==helper.get(i).locationY && helper.get(i).living)
                 return true;
         }
         return false;
    }

    void forFire(){

        Timer timer1 = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (int i=1;i<helper.size();i++){

                    if(!helper.get(i).living)
                        continue;

                    Color temp;
                    if(helper.get(i).friend==1)
                        temp=Color.magenta;
                    else
                        temp=Color.BLUE;

                    Square s1=new Square(helper.get(i).locationX,helper.get(i).locationY,temp);
                    s1.friend=helper.get(i).friend;
                    s1.way=1;
                    fires.add(s1);
                    Square s2=new Square(helper.get(i).locationX,helper.get(i).locationY,temp);
                    s2.friend=helper.get(i).friend;
                    s2.way=0;
                    fires.add(s2);

                }
                repaint();
            }
        });
        timer1.start();
    }

    int winStatus(){

         if(!helper.get(0).living)
             return -1;

         for (int i=0;i<helper.size();i++){

             if(helper.get(i).friend==0 && helper.get(i).living)
                 return 0;
         }

         return 1;
    }

    void winner(){

        int status=winStatus();

        if(status == -1) {
            JOptionPane.showMessageDialog(
                    this, "You lost!", "Game finished", JOptionPane.INFORMATION_MESSAGE
            );
            System.exit(0); // exit the program when the user closes the dialog
        }
        if(status == 1) {
            JOptionPane.showMessageDialog(
                    this, "You won!", "Game finished", JOptionPane.INFORMATION_MESSAGE
            );
            System.exit(0); // exit the program when the user closes the dialog
        }

    }

    void ontoClear(){

         for (int i=0;i<helper.size();i++)
             for (int j=i+1;j<helper.size();j++) {
                 if(helper.get(i).friend!=helper.get(j).friend && helper.get(i).living && helper.get(j).living)
                     if(helper.get(i).locationX==helper.get(j).locationX && helper.get(i).locationY==helper.get(j).locationY){

                         helper.get(i).living=false;
                         helper.get(i).living=false;

                     }
             }
    }

    void move(int i){
        Timer timer1 = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                while (true){

                    int key=(int) (Math.random()*4);

                    if(key==0){
                        if(helper.get(i).locationX-10<0) {
                            continue;
                        }
                        helper.get(i).locationX -= 10;
                    }
                    if(key==1){
                        if(helper.get(i).locationX+10>getWidth()-10) {
                            continue;
                        }
                        helper.get(i).locationX += 10;
                    }
                    if(key==2){
                        if(helper.get(i).locationY-10<0) {
                            continue;
                        }
                        helper.get(i).locationY -= 10;
                    }
                    if(key==3){
                        if(helper.get(i).locationY+10>getHeight()-10) {
                            continue;
                        }
                        helper.get(i).locationY += 10;
                    }
                    break;

                }
                ontoClear();
                repaint();
            }
        });
        timer1.start();
    }
    /*public static void main(String[]args){


        new SquareGame(10,10);


    }*/
}
