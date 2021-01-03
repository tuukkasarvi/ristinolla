/**
 * Siirto-luokka kuvaa yhtä siirtoa eli yhtä rastin tai renkaan paikkaa.
 * Luokka on Koordinaatit luokan triviaali aliluokka. Luokan tarkoituksena
 * on selkiyttää eroa siirron ja koordinaatin välillä - missä on kyse pelkästä
 * paikasta ja missä taas sirrosta. 
 * @author Tuukka Sarvi
 */
public class Siirto extends Koordinaatit {

    /**
     * Konstruktori luo uuden siirron annetuista koordinaateista.
     * @param x x-koordinaatti
     * @param y y-koordinaatti
     */
    public Siirto (int x, int y) {
	super(x, y);
    }

    /**
     * Luo uuden siirron. Siirron koordinaatit asetetaan origoon (0,0).
     */
    public Siirto(){
	super();
    } 


}
 
