/**
 * Kuvaa ihmispelaajaa. On Pelaaja-luokan lähes triviaali
 * aliluokka.
 * @author Tuukka Sarvi
 */
public class Ihmispelaaja extends Pelaaja {

    /**
     * Luo uuden Ihmispelaaja-olion, jolla annettu nimi ja pelimerkki ('x' tai 'o').
     * @param nimi pelaajan nimi
     * @param pelimerkki pelaajan pelimerkki ('x' tai 'o')
     */
    public Ihmispelaaja(String nimi, char pelimerkki){
	super(nimi,pelimerkki);
    }

    /**
     * Miettii siirron, eli ihmispelaajan tapauksessa palauttaa null:in, joka on
     * merkkinä siitä, että siirto saadaan syötteenä käyttöjärjestelmästä.
     * @return siirto (tässä aina null)
     */
    public Siirto mietiSiirto(){
	return null;
    }

}
