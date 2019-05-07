public class Tresor extends Unite{
    private boolean isClosed;

    public Tresor(String contenu){
        this.isClosed = true;
        this.contenu = contenu;
        this.setSymbole('$');
    }

    //Faudrait mettre le même genre de choses pour Monstre, interface?
    //Methode appelee par Zoe quand elle veut ouvrir un tresor

    //Methode appelee par Zoe quand elle ouvre un tresor
    public void setIsClosed(boolean closed){
        if (!closed) {
            this.setSymbole('_');
        } else if (closed) {
            this.setSymbole('$');
        }
        this.isClosed = closed;
    }

    //Methode appelee par Zoe quand elle ouvre un tresor
    public String getContenu(){
        return this.contenu;
    }
}
