import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.*;

/**
 * Classe principale du programme.
 *
 * NOTEZ : VOUS NE DEVEZ PAS RENOMMER CETTE CLASSE
 */
public class LegendOfZoe {
  public static Niveau niv;
  public static Zoe zoe;
  public static int n;
  public static String contenu;
  public static int nbreVies;

  // Vrai fonction main - laisser en commentaire pour faire des tests
  public static void main(String[] args) {
    Scanner key = new Scanner(System.in);
    init();
    while(zoe.getNbrHexaforces()!=6 && !zoe.getIsDead()){
      if(n!=1) {
        niv = new Niveau(n);
        zoe = niv.getZoe();
        zoe.setNbrVies(nbreVies);
        zoe.setNbrHexaforces(n-1);
        zoe.setContenu(contenu);
      }
      while(!zoe.hasExit()){
        //le jeu roule la
        niv.afficher();
        String entree = key.nextLine();
        //chaque caractere est un tour
        for (int i = 0; i < entree.length(); i++) {
          char caractere = entree.charAt(i);
          //tour de Zoe
          keyPressed(zoe, caractere);
          //tour des monstres
          for (int l = 0; l<niv.getMonstresNiveau().size();l++){
            if (isNextTo(niv.getMonstresNiveau().get(l))){
              niv.getMonstresNiveau().get(l).attack();
            } else {
              niv.getMonstresNiveau().get(l).deplacer();
            }
          }
        }
        if(zoe.getIsDead()){
          Messages.afficherDefaite();
          break;
        }
      }
      contenu = zoe.getContenu();
      nbreVies = zoe.getNbrVies();
      n++;
    }
    if(!zoe.getIsDead()) {
      Messages.afficherVictoire();
    }
  }

  public static void  init(){
    //Messages.afficherIntro();
    n = 1;
    niv = new Niveau(n);
    zoe = niv.getZoe();
  }

  public static void keyPressed(Zoe zoe, char caractere){
    switch (caractere){
      case 'a' : zoe.deplacer(-1,0);break;
      case 'w' : zoe.deplacer(0,-1);break;
      case 'd' : zoe.deplacer(1,0);break;
      case 's' : zoe.deplacer(0,1);break;
      case 'c' : zoe.dig();break;
      case 'x' : zoe.attack();break;
      case 'o' : zoe.open();break;
      case 'q' : System.exit(0);break;
      default : return;
    }
  }

  public static boolean isNextTo(Unite unite){
    int y = unite.getYCoordinate();
    int x = unite.getXCoordinate();

    for (var j=y-1; j<=y+1; j++) {
      for (var i = x - 1; i <= x + 1; i++) {
        if (Personnages.isInMap(i, j) && j == zoe.getYCoordinate() && i == zoe.getXCoordinate()) {
          return true;
        }
      }
    }
    return false;
  }
}