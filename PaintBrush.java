import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class PaintBrush extends JFrame implements MouseInputListener {

    String pen="";
    String color="";
    Boolean tasiActive=false;
    JPanel toBePainted;
    boolean enough=false;
    boolean resizeHelper=false;
    int oldX,oldY;
    boolean moveBorder=false;
    boolean preview=false;
    int x,y;
    int width,height;
    int originX,originY;
    boolean complete=false;
    boolean press=false;
    int index=-1;
    ArrayList<Shape> list=new ArrayList<>(50);

    public PaintBrush(){

        toBePainted=new JPanel();

        setTitle("Paint");
        setSize(600,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel colorPanel= new JPanel(new BorderLayout());
        JPanel penPanel=new JPanel(new FlowLayout());

        JButton kalem=new JButton("Kalemle Cız");
        JButton dikdortgen=new JButton("Dıkdortgen Cız");
        JButton oval=new JButton("Oval Cız");
        JButton tasi=new JButton("Tası");

        kalem.addActionListener(e -> {
            pen="kalem";
            tasiActive=false;
        });
        dikdortgen.addActionListener(e -> {
            pen="dikdortgen";
            tasiActive=false;
        });
        oval.addActionListener(e -> {
            pen="oval";
            tasiActive=false;
        });
        tasi.addActionListener(e -> tasiActive=true);

        penPanel.add(dikdortgen);
        penPanel.add(oval);
        penPanel.add(kalem);
        penPanel.add(tasi);

        JPanel top=new JPanel(new GridLayout(2,1));

        top.add(colorPanel);
        top.add(penPanel);
        add(top,BorderLayout.NORTH);
        add(toBePainted);

        addMouseListener(this);
        addMouseMotionListener(this);

        setVisible(true);
    }

    class Shape{
        Color color;
        String shape;
        int oX,oY;
        int width,height;

        public Shape(Color color, String shape, int oX, int oY, int width, int height) {
            this.color = color;
            this.shape = shape;
            this.oX = oX;
            this.oY = oY;
            this.width = width;
            this.height = height;
        }
    }

    public void paint(Graphics g){

        if (tasiActive && !moveBorder)
            return;

        if(tasiActive) {
            list.get(list.size() - 1).oX = originX;
            list.get(list.size() - 1).oY = originY;

        }

        width = Math.abs(oldX-x);
        height = Math.abs(oldY-y);

        if(y<116)
            y=116;
        if(oldY<116)
            oldY=116;

        super.paint(g);

        for(int i=0;i<list.size();i++){

            if(list.get(i).shape.equals("line")){
                g.setColor(list.get(i).color);
                g.drawLine(list.get(i).oX,list.get(i).oY,list.get(i).width,list.get(i).height);
            }

            else if(list.get(i).shape.equals("oval")){
                g.setColor(list.get(i).color);
                g.fillOval(list.get(i).oX,list.get(i).oY,list.get(i).width,list.get(i).height);
            }

            else {
                g.setColor(list.get(i).color);
                g.fillRect(list.get(i).oX,list.get(i).oY,list.get(i).width,list.get(i).height);
            }
        }

        {
            g.setColor(Color.BLUE);
            g.fillRect(60,40,60,20);
            g.setColor(Color.RED);
            g.fillRect(130,40,60,20);
            Color darkGreen=new Color(0,153,76);
            g.setColor(darkGreen);
            g.fillRect(200,40,60,20);
            g.setColor(Color.YELLOW);
            g.fillRect(270,40,60,20);
            Color orange=new Color(255,128,0);
            g.setColor(orange);
            g.fillRect(340,40,60,20);
            Color purple= new Color(110,0,110);
            g.setColor(purple);
            g.fillRect(410,40,60,20);
            g.setColor(Color.BLACK);
            g.fillRect(480,40,60,20);
            g.setColor(Color.BLUE);
            g.fillRect(0,110,1920,6);
            g.setColor(Color.black);
        }

        if(color.equals("blue"))
            g.setColor(Color.BLUE);
        if(color.equals("red"))
            g.setColor(Color.red);
        if(color.equals("green"))
            g.setColor(new Color(0,153,76));
        if(color.equals("yellow"))
            g.setColor(Color.yellow);
        if(color.equals("orange"))
            g.setColor(new Color(255,128,0));
        if (color.equals("purple"))
            g.setColor(new Color(110,0,110));
        if (color.equals("black"))
            g.setColor(Color.black);

        if (tasiActive || !press)
            return;

        if(pen.equals("kalem")){

            if(enough)
                g.drawLine(oldX, oldY, x, y);
            if(complete && enough)
                list.add(new Shape(g.getColor(),"line",oldX,oldY,x,y));
            oldX=x;
            oldY=y;
            return;
        }

        if(pen.equals("dikdortgen") ){

            originX = Math.min(oldX, x);
            originY = Math.min(oldY, y);

            if(complete && enough)
                list.add(new Shape(g.getColor(),"rectangular",originX,originY,width,height));
            if (enough)
                g.fillRect(originX,originY,width,height);

        }

        if(pen.equals("oval") ){

            originX = Math.min(oldX, x);
            originY = Math.min(oldY, y);

            if(complete && enough)
                list.add(new Shape(g.getColor(),"oval",originX,originY,width,height));
            if(enough)
                g.fillOval(originX,originY,width,height);

        }
        enough=false;

    }

    public static void main(String[] args){

        new PaintBrush();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        if(e.getY()<=60 && e.getY()>=40){

            if(e.getX()>=60 && e.getX() <=120)
                color="blue";
            if(e.getX()>=130 && e.getX() <=190)
                color="red";
            if(e.getX()>=200 && e.getX() <=260)
                color="green";
            if(e.getX()>=270 && e.getX() <=330)
                color="yellow";
            if(e.getX()>=340 && e.getX() <=400)
                color="orange";
            if(e.getX()>=410 && e.getX() <=470)
                color="purple";
            if(e.getX()>=480 && e.getX() <=540)
                color="black";

        }

        pressCheck(e.getY());
        if(!press)
            return;

        resizeHelper=true;
        complete= pen.equals("kalem");


        if (tasiActive) {
            inBox(e.getX(), e.getY());
            index=lastIndexFounder(e.getX(),e.getY());

            Shape tmp=list.get(index);
            list.remove(index);
            list.add(tmp);

        }
        oldX=e.getX();
        oldY=e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        enough=true;
        complete=true;
        preview=false;
        resizeHelper=true;

        if(press && !tasiActive)
            repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

        if(!tasiActive)
            enough=true;

        pressCheck(e.getY());
        if(!press)
            return;

        if(tasiActive){

            originX=e.getX()-list.get(list.size()-1).width/2;
            originY=e.getY()-list.get(list.size()-1).height/2;

            if(originY<116)
                originY=116;

            if(press)
                repaint();


        } else {
            preview = true;
            x = e.getX();
            y = e.getY();

            if (pen.equals("kalem"))
                preview = false;

            if(press)
                repaint();

        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    void inBox(int x1,int y1){

        int x2 ;
        int y2 ;

        for(int i=0; i<list.size();i++) {

            x2 = list.get(i).oX;
            y2 = list.get(i).oY;

            if (x1 >= x2 && x1 <= x2 + list.get(i).width && y1 >= y2 && y1 <= y2 + list.get(i).height && !list.get(i).shape.equals("line")) {

                moveBorder = true;

                if(list.get(i).shape.equals("oval")){

                    if(!ovalFunc(x2+list.get(i).width/2,y2+list.get(i).height/2,x1,y1,list.get(i).width/2,list.get(i).height/2))
                        moveBorder=false;

                }
                return;
            }
        }
        moveBorder=false;
    }

    void pressCheck(int y){

        press= y >= 116;

    }

    boolean ovalFunc(int x, int y, int targetX, int targetY ,int width, int height){

        double max=Math.max(width,height);
        targetX-=x;
        targetY-=y;

        double xHelper=Math.sqrt(Math.abs(Math.pow(max,2)-(Math.pow(max,2)/Math.pow(height,2))*Math.pow(targetY,2)));
        double yHelper=Math.sqrt(Math.abs(Math.pow(max,2)-(Math.pow(max,2)/Math.pow(width,2))*Math.pow(targetX,2)));

        boolean bX=targetX<=xHelper && targetX>=xHelper*-1;
        boolean bY=targetY<=yHelper && targetY>=yHelper*-1;

        return bX && bY;

    }

    int lastIndexFounder(int x , int y){

        for(int i=list.size()-1;i>=0;i--){
            if(list.get(i).oX<=x && list.get(i).oX+list.get(i).width>=x && list.get(i).oY<=y && list.get(i).oY+list.get(i).height>=y && !list.get(i).shape.equals("line")) {

                if(list.get(i).shape.equals("oval")){

                    if(!ovalFunc(list.get(i).oX+list.get(i).width/2,list.get(i).oY+list.get(i).height/2,x,y,list.get(i).width/2,list.get(i).height/2))
                        continue;

                }

                return i;
            }
        }
        return list.size()-1;

    }
}
