/**
 * Tietokonepelaaja, joka tekee siirtonsa täysin satunnaisesti.
 * Pelaaja-luokan aliluokka.
 * @author Tuukka Sarvi
 */
import java.util.ArrayList;

public class PelaajaReiskaRandom extends Pelaaja{

    private static final String NIMI = "Reiska Random";

    /**
     * Luo uuden ReiskaRandom-pelaajan, joka pelaa annetuilla pelimerkeillä.
     * @param pelimerkki 'x' tai 'o'
     */
    public PelaajaReiskaRandom(char pelimerkki){
	super(PelaajaReiskaRandom.NIMI, pelimerkki);
    }

    /**
     * Valitsee siirron satunnaisesti mahdollisten siirtojen joukosta ja palauttaa
     * sen.
     * @return siirto, joka halutaan tehdä
     */
    public Siirto mietiSiirto(){
	Pelikentta pelikentta = this.kerroPelattavaPeli().kerroPelikentta();
	ArrayList mahdollisetSiirrot = new ArrayList();
	Koordinaatit paikka;

	for (paikka = new Koordinaatit(); paikka.kerroX() < pelikentta.kerroLeveys(); paikka.asetaX(paikka.kerroX()+1)) {
	    for (paikka.asetaY(0); paikka.kerroY() < pelikentta.kerroKorkeus(); paikka.asetaY(paikka.kerroY()+1)){
		if (pelikentta.onTyhja(paikka))
		    mahdollisetSiirrot.add(new Siirto(paikka.kerroX(), paikka.kerroY()));
	    }
	}

	return (Siirto)mahdollisetSiirrot.get((int)(Math.random() * (mahdollisetSiirrot.size()-1)));

    }

}
