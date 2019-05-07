import java.util.ArrayList;

public class Zoe extends Personnages {
  
  // le nombre d'Hexaforces accumulés
  private int nbrHexaforces;
  private int maxVies;
  // si zoe est sur la sortie et a l'Hexaforce du niveau
  private boolean hasExit = false;

  // **Utile de pouvoir préciser le nombre de vies max et hexaforce pour débuggage
  /**
   * Constructeur
   */
  public Zoe(int nbrHexaforces, int maxVies) {
    this.nbrHexaforces = nbrHexaforces;
    this.maxVies = maxVies;
    this.setNbrVies(5);
    this.setSymbole('&');
    this.contenu=null;
  }
  

  //Actions de Zoe

  //attaquer monstres
  @Override
  public void attack(){
    int x = this.getXCoordinate();
    int y = this.getYCoordinate();
    ArrayList<ArrayList<Unite>> carte = LegendOfZoe.niv.getContenuNiveau();
    ArrayList<Monstre> monstres = LegendOfZoe.niv.getMonstresNiveau();
    for (var j=y-1; j<=y+1; j++) {
      for (var i=x-1; i<=x+1;i++) {
        if(isInMap(i,j)){
          for (int l = 0; l<monstres.size();l++){
            if(monstres.get(l).getXCoordinate()==i&&monstres.get(l).getYCoordinate()==j && !monstres.get(l).getIsDead()) {
              System.out.println("Zoe attaque Monstre");
              monstres.get(l).perdreVie();
              if (monstres.get(l).getIsDead()){
                LegendOfZoe.zoe.effetItem(monstres.get(l).getContenu());
              }
            }
          }
        }
      }
    }
  }

  //retire les murs adjacents a Zoe
  public void dig() {
      int x = this.getXCoordinate();
      int y = this.getYCoordinate();
      boolean[][] carte = LegendOfZoe.niv.getKey();
      for (var j=y-1; j<=y+1; j++) {
        for (var i=x-1; i<=x+1;i++) {
          if(isInMap(i,j)){
            if(carte[j][i]){
              carte[j][i]=false;
              LegendOfZoe.niv.setContenuNiveau();
          }
        }
      }
    }
  }

  //ouvre un tresor et utilise l'objet
  public void open(){
    int x = this.getXCoordinate();
    int y = this.getYCoordinate();
    ArrayList<ArrayList<Unite>> carte = LegendOfZoe.niv.getContenuNiveau();
    for (var j=y-1; j<=y+1; j++) {
      for (var i=x-1; i<=x+1;i++) {
        if(isInMap(i,j)&&carte.get(j).get(i).getSymbole()=='$'){
          Tresor coffre = (Tresor)carte.get(j).get(i);
          coffre.setIsClosed(false);
          this.effetItem(coffre.getContenu());
        }
      }
    }
  }

  //utilise la sortie
  public void takeExit(){
    if (LegendOfZoe.niv.getHexaforceTrouve()) {
      this.hasExit = true;
    }
  }

  public boolean hasExit() {
    return hasExit;
  }

  //Zoe reçoit l'effet d'un item
  public void effetItem(String item) {
    if (item.toLowerCase().equals("coeur")) {
      System.out.println("Vous trouvez un coeur!");
      if (!(this.getNbrVies()==this.maxVies)) {
        this.setNbrVies(this.getNbrVies() + 1);
      }
    } else if (item.toLowerCase().equals("hexaforce")) {
      System.out.println("Vous trouvez une pièce d'Hexaforce!");
      this.setNbrHexaforces(this.getNbrHexaforces()+1);
      LegendOfZoe.niv.setHexaforceTrouve();
      LegendOfZoe.niv.getSortie().setIsOpen(true);
    } else if (item.toLowerCase().equals("potionvie")) {
      System.out.println("Vous trouvez une potion de vie!");
      this.setNbrVies(maxVies);
    }
  }


  /* Getters et setters */
  //retourne le nombre d'Hexaforces
  public int getNbrHexaforces(){
    return this.nbrHexaforces;
  }

  //modifie le nombre d'hexaforces
  public void setNbrHexaforces(int nbrHexaforces){
    this.nbrHexaforces = nbrHexaforces;
  }
}