public class Unite {
    protected char symbole;
    protected String contenu;
    protected int xCoordinate;
    protected int yCoordinate;

    public Unite(){
    }

    public char getSymbole(){
        return this.symbole;
    }

    public void setSymbole(char nouveauSymbole){
        this.symbole = nouveauSymbole;
    }

    public String getContenu(){
        return this.contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public int getYCoordinate(){
        return this.yCoordinate;
    }

    public int getXCoordinate(){
        return this.xCoordinate;
    }

    public void setXCoordinate(int xCoordinate){
        this.xCoordinate = xCoordinate;
    }

    public void setYCoordinate(int yCoordinate){
        this.yCoordinate = yCoordinate;
    }
}
