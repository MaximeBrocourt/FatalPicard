package plugins.attack;

import identity.IRobot;

public interface IAttack
{
    int getPower();
    int getRange();
    void attack(IRobot me, IRobot target) throws Exception;
}