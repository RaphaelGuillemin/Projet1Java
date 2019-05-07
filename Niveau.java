import java.util.*;

public class Niveau {
    private int numero;
    private boolean hexaforceTrouve = false;
    private Paire niveau;
    private ArrayList<Monstre> monstresNiveau = new ArrayList<>();
    private ArrayList<Tresor> tresorsNiveau = new ArrayList<>();
    private int idxMonstres = 0;
    private int idxTresors = 0;
    private Zoe zoe;
    private Sortie sortie;
    private ArrayList<ArrayList<Unite>> contenuNiveau;

    public Niveau(int level){
        this.numero = level;
        this.niveau = LevelGenerator.generateLevel(this.numero);

        int xCoordinate;
        int yCoordinate;

        String[] unitesNiveauString = getValue();
        // Parse le tableau de string
        for (int i = 0; i<unitesNiveauString.length; i++) {
            String unite = unitesNiveauString[i];

            String[] sections = unite.split(":");

            switch (sections[0]) {
                case "sortie":
                    xCoordinate = Integer.parseInt(sections[1]);
                    yCoordinate = Integer.parseInt(sections[2]);
                    this.sortie = new Sortie();
                    this.sortie.setXCoordinate(xCoordinate);
                    this.sortie.setYCoordinate(yCoordinate);
                    break;
                case "zoe":
                    xCoordinate = Integer.parseInt(sections[1]);
                    yCoordinate = Integer.parseInt(sections[2]);
                    this.zoe = new Zoe(0,5);
                    this.zoe.setXCoordinate(xCoordinate);
                    this.zoe.setYCoordinate(yCoordinate);
                    break;
                case "tresor":
                    xCoordinate = Integer.parseInt(sections[2]);
                    yCoordinate = Integer.parseInt(sections[3]);
                    this.tresorsNiveau.add(new Tresor(sections[1]));
                    this.tresorsNiveau.get(idxTresors).setXCoordinate(xCoordinate);
                    this.tresorsNiveau.get(idxTresors).setYCoordinate(yCoordinate);
                    idxTresors++;
                    break;
                case "monstre":
                    xCoordinate = Integer.parseInt(sections[2]);
                    yCoordinate = Integer.parseInt(sections[3]);
                    this.monstresNiveau.add(new Monstre(this.numero, sections[1]));
                    this.monstresNiveau.get(idxMonstres).setXCoordinate(xCoordinate);
                    this.monstresNiveau.get(idxMonstres).setYCoordinate(yCoordinate);
                    idxMonstres++;
                    break;
            }
        }
        setContenuNiveau();
    }

    public Zoe getZoe() {
        return zoe;
    }

    //trier l'Array avec les morts en premier et les vivants ensuite
    public void organiserMonstresNiveau(ArrayList<Monstre> monstresNiveau){
        ArrayList<Monstre> nouveauArray= new ArrayList<>();
        for (int i=0;i<monstresNiveau.size();i++){
            if (monstresNiveau.get(i).getIsDead()){
                nouveauArray.add(monstresNiveau.get(i));
            }
        }
        for (int j=0;j<monstresNiveau.size();j++){
            if (!monstresNiveau.get(j).getIsDead()){
                nouveauArray.add(monstresNiveau.get(j));
            }
        }
        this.monstresNiveau=nouveauArray;
    }

    public ArrayList<ArrayList<Unite>> amalgameContenuNiveau(ArrayList<Monstre> monstresNiveau,
                                                                ArrayList<Tresor> tresorsNiveau, 
                                                                Zoe zoe, 
                                                                Sortie sortie) {
        ArrayList<ArrayList<Unite>> amalgame = new ArrayList<ArrayList<Unite>>();
        for (int y = 0; y<LevelGenerator.HAUTEUR; y++) {
            amalgame.add(new ArrayList<Unite>());
            for(int x = 0; x<LevelGenerator.LARGEUR; x++) {
                amalgame.get(y).add((Unite) (new Mur(true)));
            }
        }


        boolean[][] tableau = this.getKey();
        int xCoordinate;
        int yCoordinate;
        for (int i = 0; i<LevelGenerator.HAUTEUR;i++){
            for(int j=0; j<LevelGenerator.LARGEUR;j++){
                if(tableau[i][j]==true) {
                    amalgame.get(i).set(j, new Mur(false));
                } else {
                    amalgame.get(i).set(j, new Mur(true));
                }
            }
        }
        for (int i=0; i<tresorsNiveau.size(); i++) {
            xCoordinate = tresorsNiveau.get(i).getXCoordinate();
            yCoordinate = tresorsNiveau.get(i).getYCoordinate();
            amalgame.get(yCoordinate).set(xCoordinate,tresorsNiveau.get(i));
        }
        this.organiserMonstresNiveau(this.monstresNiveau);

        for (int i=0; i<monstresNiveau.size(); i++) {
            xCoordinate = monstresNiveau.get(i).getXCoordinate();
            yCoordinate = monstresNiveau.get(i).getYCoordinate();
            amalgame.get(yCoordinate).set(xCoordinate,(Unite)monstresNiveau.get(i));
        }
        xCoordinate = zoe.getXCoordinate();
        yCoordinate = zoe.getYCoordinate();
        amalgame.get(yCoordinate).set(xCoordinate,(Unite)zoe);
        xCoordinate = sortie.getXCoordinate();
        yCoordinate = sortie.getYCoordinate();
        amalgame.get(yCoordinate).set(xCoordinate,sortie);
        return amalgame;
    }

    public ArrayList<ArrayList<Unite>> getContenuNiveau() {
        return contenuNiveau;
    }

    public void setContenuNiveau(){
        contenuNiveau = this.amalgameContenuNiveau(this.monstresNiveau, this.tresorsNiveau, this.zoe, this.sortie);
    }

    public String donnees(){
        String donnees="Zoe : ";
        int vie;
        for (vie = 0; vie<zoe.getNbrVies(); vie++){
            donnees+="♥ ";
        }
        for (int nonVie = vie; nonVie<5;nonVie++){
            donnees+="_ ";
        }
        donnees+="| ";
        int nbreHexa;
        for (nbreHexa = 0; nbreHexa<zoe.getNbrHexaforces();nbreHexa++){
            donnees+="Δ ";
        }
        for (int nonNbreHexa = nbreHexa; nonNbreHexa<6;nonNbreHexa++){
            donnees+="_ ";
        }
        return donnees;
    }


    //juste pour le debuggage
    public void afficher() {
        System.out.println("Niveau : " + LegendOfZoe.niv.getNumero());
        System.out.println(donnees());
        setContenuNiveau();
        String niveauAffiche = new String();

        for (int i = 0; i < contenuNiveau.size(); i++) {
            for (int j = 0; j < contenuNiveau.get(i).size(); j++) {
                niveauAffiche += contenuNiveau.get(i).get(j).getSymbole();
            }
            niveauAffiche += "\n";
        }
        System.out.println(niveauAffiche);
    }

    //Getters et Setters
    public int getNumero() {
        return numero;
    }

    public boolean getHexaforceTrouve() {
        return hexaforceTrouve;
    }

    public void setHexaforceTrouve(){
        this.hexaforceTrouve=!this.hexaforceTrouve;
    }

    public boolean[][] getKey(){
        return (boolean[][])this.niveau.getKey();
    }

    public String[] getValue(){
        return (String[])this.niveau.getValue();
    }

    public ArrayList<Monstre> getMonstresNiveau() {
        return monstresNiveau;
    }

    public Sortie getSortie() {
        return sortie;
    }
}
