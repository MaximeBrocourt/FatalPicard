package identity;

import java.awt.*;
import java.util.ArrayList;

public interface IRobot
{
    void act(ArrayList<IRobot> foes) throws Exception;
    void draw(Graphics g) throws Exception;;
    void attack(IRobot target) throws Exception;
    int calculateDistance(IRobot robot);
    void decreaseLife(int amount);
    void increaseLife(int amount);
    void decreaseEnergy(int amount);
    void increaseEnergy(int amount);
    int getEnergy();
    int getLife();
    int getX();
    int getY();
    void setX(int x);
    void setY(int y);
    void setAttack(Class<?> attack);
    void setGraphic(Class<?> attack);
    void setMove(Class<?> attack);
}
