/**
 * Luokka kuvaa tapauksia, jolloin käsitelty ristinollapelitiedosto on
 * viallinen. Exception-luokan triviaali aliluokka.
 * @author Tuukka Sarvi
 */
 
public class KelvotonRistinollapelitiedosto extends Exception {

    /**
     * Luo uuden poikkeuksen annetulla kuvauksella.
     * @param kuvausmerkkijono
     */
    public KelvotonRistinollapelitiedosto (String s){
	super(s);    
    } 

}
