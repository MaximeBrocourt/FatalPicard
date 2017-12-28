package fr.miage.pav.entites;

import fr.miage.pav.plugins.Attaque;
import fr.miage.pav.terrain.War;

import java.awt.*;
import java.util.ArrayList;

public class Robot
{
    private static final int VIE_BASE = 100, ENERGIE_BASE = 100;
    private static final int LARGEUR_BASE = 10, DISTANCE_BASE = 5;

    private int vie, energie;
    private int x, y;
    private War war;
    private Attaque atq;

    /**
     * Constructeur.
     * @param x position en absisses du robot
     * @param y position en ordonnées du robot
     * @param war war dont dépend le robot
     */
    //TODO examiner l’intéret de fournir war en paramètre
    public Robot(int x, int y, War war)
    {
        this.vie = VIE_BASE;
        this.energie = ENERGIE_BASE;
        this.x = x;
        this.y = y;
        this.war = war;
        this.atq = new Attaque(this, 10);
    }

    public void dessiner(Graphics g)
    {
        g.fillRect(x - LARGEUR_BASE / 2, y - LARGEUR_BASE / 2, LARGEUR_BASE, LARGEUR_BASE);
    }

    public void bouger(ArrayList<Robot> robots)
    {
        Robot plusProche = trouverPlusProche(robots);
        int distance = plusProche.calculerDistance(this);
        if(distance == 0)
            distance = 1;
        x = (DISTANCE_BASE * (plusProche.getX() - x)) / distance + x;
        if(x < LARGEUR_BASE / 2)
        {
            x = LARGEUR_BASE / 2;
        } else if(x > war.getWidth() - LARGEUR_BASE / 2)
        {
            x = war.getWidth() + LARGEUR_BASE / 2;
        }
        y = (DISTANCE_BASE * (plusProche.getY() - y)) / distance + y;
        if(y < LARGEUR_BASE / 2)
        {
            y = LARGEUR_BASE / 2;
        } else if(y > war.getHeight() - LARGEUR_BASE / 2)
        {
            y = war.getHeight() + LARGEUR_BASE / 2;
        }
    }

    public void attaquer(int decrease)
    {
        vie -= decrease;
        energie -= 20;
    }

    public void agir(ArrayList<Robot> robots)
    {
        bouger(robots);
    }

    /**
     * Permet de calculer la distance du robot avec un autre
     * @param r autre robot
     * @return distance
     */
    private int calculerDistance(Robot r)
    {
        return (int)Math.hypot(this.x - r.getX(), this.y - r.getY());
    }

    /**
     * Permet de trouver le robot le plus proche
     * @param robots liste des robots
     * @return robot le plus proche
     */
    public Robot trouverPlusProche(ArrayList<Robot> robots)
    {
        Robot retour = null;
        int distanceMinimale = Integer.MAX_VALUE, tampon;
        for(Robot r : robots)
        {
            if(!r.equals(this))
            {
                tampon = r.calculerDistance(this);
                if(tampon < distanceMinimale)
                {
                    distanceMinimale = tampon;
                    retour = r;
                }
            }
        }
        return retour;
    }

    public int getEnergie()
    {
        return energie;
    }

    public int getVie()
    {
        return vie;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setEnergie(int energie) {
        this.energie = energie;
    }

    public void setVie(int vie) {
        this.vie = vie;
    }

    public Attaque getAtq() {
        return atq;
    }
}
