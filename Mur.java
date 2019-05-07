public class Mur extends Unite{
    private boolean digged;

    public Mur(boolean digged){
        this.digged = digged;
        if (this.digged) {
            this.setSymbole(' ');
        } else {
            this.setSymbole('#');
        }
    }
}
