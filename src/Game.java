import java.awt.*;

public class Game {

    static int index=1;
    static SquareGame game;

    public Game() {
        game=new SquareGame();
        game.helper.add(new SquareGame.Square(250,250, Color.RED));
        game.helper.get(0).friend=1;
    }


    public static class Enemy extends Thread{

        int id;

        public Enemy(){

            id=index;

            boolean successful=true;
            while (successful){

                int x=(int) (Math.random()*50)*10;
                int y=(int) (Math.random()*50)*10;

                if(!game.board[x/10][y/10]) {
                    game.helper.add(new SquareGame.Square(x, y, Color.BLACK));
                    game.board[x/10][y/10]=true;
                    successful=false;
                }

            }
            game.enemy++;
            index++;

        }

        @Override
        public void run() {
            game.move(id);
        }
    }

    public static class Friend extends Thread{

        int id;

        public Friend(){

            id=index;

            boolean successful=true;
            while (successful){

                int x=(int) (Math.random()*50)*10;
                int y=(int) (Math.random()*50)*10;

                if(!game.board[x/10][y/10]) {
                    game.helper.add(new SquareGame.Square(x, y, Color.GREEN));
                    game.helper.get(id).friend=1;
                    game.board[x/10][y/10]=true;
                    successful=false;
                }
            }
            game.alive++;
            index++;
        }

        @Override
        public void run() {
            game.move(id);
        }
    }

    public static class AirCraft extends Thread{

        @Override
        public void run() {
            game.forFire();
        }
    }
}
