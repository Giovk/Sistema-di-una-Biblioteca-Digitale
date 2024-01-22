package Model;

public class Recensione {
    public String testo;
    public int valutazione;
    public Utente utenteRecensore;
    public Elemento elementoRecensito;

    public Recensione(String t, int v, boolean p, Utente ur, Elemento er){
        testo = t;
        valutazione = v;
        utenteRecensore = ur;
        elementoRecensito = er;
    }

    public Utente getUtenteRecensore() {
        return utenteRecensore;
    }

    public int getValutazione() {
        return valutazione;
    }

    public String getTesto() {
        return testo;
    }
}
