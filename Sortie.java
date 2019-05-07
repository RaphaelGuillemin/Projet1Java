public class Sortie extends Unite{
    private boolean isOpen;

    public Sortie(){
        this.isOpen = false;
        this.setSymbole('E');
    }

    public void setIsOpen(boolean open){
        this.isOpen = open;
    }
}
