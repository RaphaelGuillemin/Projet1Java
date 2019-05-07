import java.util.ArrayList;

public class Monstre extends Personnages {

  private int force;
  private String contenu;
  private int depX;
  private int depY;

  /**
   * constructeur, prend en parametre le numéro du niveau
   */
  public Monstre(int nNiveau, String contenu) {
    this.force = (int)Math.max(0.4*nNiveau, 1);
    this.setNbrVies((int)Math.max(0.6*nNiveau, 1));
    this.setSymbole('@');
    this.contenu = contenu;
  }

  public void deplacer(){
    if (!this.getIsDead()) {
      int dx = LegendOfZoe.zoe.getXCoordinate() - this.getXCoordinate();
      if (dx > 0) {
        depX = 1;
      } else if (dx == 0) {
        depX = 0;
      } else {
        depX = -1;
      }
      int dy = LegendOfZoe.zoe.getYCoordinate() - this.getYCoordinate();
      if (dy > 0) {
        depY = 1;
      } else if (dy == 0) {
        depY = 0;
      } else {
        depY = -1;
      }
      int x = this.getXCoordinate() + depX;
      int y = this.getYCoordinate() + depY;
      if (isLegal(x, y)) {
        this.setXCoordinate(x);
        this.setYCoordinate(y);
      }
    }
  }

  /**
   * Enlève une vie au Monstre; si le nombre de vies tombe à zéro, le Monstre 
   * meurt
   */
  public void perdreVie() {
    this.setNbrVies(this.getNbrVies()-1);
    if (this.getNbrVies()<=0){
      this.setIsDead(true);
      this.setSymbole('x');
      LegendOfZoe.niv.organiserMonstresNiveau(LegendOfZoe.niv.getMonstresNiveau());
    }
  }

  @Override
  public void attack(){
    if(!getIsDead()) {
      int x = this.getXCoordinate();
      int y = this.getYCoordinate();
      ArrayList<ArrayList<Unite>> carte = LegendOfZoe.niv.getContenuNiveau();
      for (var j = y - 1; j <= y + 1; j++) {
        for (var i = x - 1; i <= x + 1; i++) {
          if (isInMap(i, j)) {
            if (carte.get(j).get(i).getSymbole() == '&') {
              System.out.println("Monstre attaque Zoe");
              LegendOfZoe.zoe.setNbrVies(LegendOfZoe.zoe.getNbrVies() - this.force);
              if (LegendOfZoe.zoe.getNbrVies()<=0){
                LegendOfZoe.zoe.setIsDead(true);
              }
            }
          }
        }
      }
    }
  }

  /**
   * Getter
   */

  public String getContenu(){
    return this.contenu;
  }
}