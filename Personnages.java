import java.util.ArrayList;

public class Personnages extends Unite{
    //Nbr de pts de vie
    private int nbrVies;
  
    //True si nbrVies == 0
    private boolean isDead;
  
    public Personnages(){
  
    }

    public static boolean isInMap(int i, int j){
      return ((j>=0) && (j<LevelGenerator.HAUTEUR) && (i>=0) && (i<LevelGenerator.LARGEUR));
    }

    public static boolean isLegal(int x,int y){
      ArrayList<ArrayList<Unite>> carte = LegendOfZoe.niv.getContenuNiveau();
      return isInMap(x,y) && !(carte.get(y).get(x).getSymbole()=='#') && !(carte.get(y).get(x).getSymbole()=='$')
          && !(carte.get(y).get(x).getSymbole()=='_') && !(carte.get(y).get(x).getSymbole()=='E');
    }

    //avance dans la direction demandee si pas en dehors de la carte ou s'il n'y a pas de mur
    public void deplacer(int x,int y){
      if(isLegal(this.getXCoordinate()+x,this.getYCoordinate()+y)){
        this.setXCoordinate(this.getXCoordinate()+x);
        this.setYCoordinate(this.getYCoordinate()+y);
        LegendOfZoe.niv.setContenuNiveau();
        }
      if (LegendOfZoe.isNextTo(LegendOfZoe.niv.getSortie())){
        LegendOfZoe.zoe.takeExit();
      }
    }

    public void attack(){
    }
  
    public int getNbrVies(){
      return this.nbrVies;
    }
  
    public void setNbrVies(int nbDeVies){
      this.nbrVies = nbDeVies;
    }
  
    public boolean getIsDead(){
      return this.isDead;
    }
  
    public void setIsDead(boolean dead){
      this.isDead = dead;
    }
  }