/**
 * Pelaaja-luokka kuvaa ristinollan pelaajan yleisiä ominaisuuksia ja
 * toimia. Pelaajalla oma pelimerkki ('x' tai 'o'), nimi ja tieto
 * siitä, että mihin peliin kuuluu. Pelaaja-luokka toimii yliluokkana
 * tarkemmille, eri pelaajatyyppejä kuvaaville luokille.
 * @author Tuukka Sarvi
 */

public abstract class Pelaaja {

    private Ristinollapeli peli;
    private String nimi;
    private char pelimerkki;

    /**
     * Konstruktori luo uuden Pelaaja-olion annettujen tietojen pohjalta.
     * @param nimi pelaajan nimi
     * @param pelimerkki joko 'x' tai 'o'
     */
    public Pelaaja(String nimi, char pelimerkki){
	this.nimi=nimi;
	this.pelimerkki=pelimerkki; 
    }

    /**
     * Lisää Pelaaja-olion annettuun peliin eli kertoo sille, että
     * mihin ristinollapeliin se kuuluu.
     * @param peli se Ristinollapeli-olio, johon pelaaja kuuluu
     */
    public void lisaaPeliin(Ristinollapeli peli){
	this.peli = peli;
    } 

    /**
     * Kertoo missä pelissä Pelaaja on osallisena.
     * @return ristinollapeli, jota Pelaaja pelaa
     */
    public Ristinollapeli kerroPelattavaPeli(){
	return this.peli;
    }

    /**
     * Tekee annetun siirron eli lisää sen siirtolistaan ja 
     * päivittää pelikenttää yhden siirron eteenpäin.
     * @param siirto tehtävä siirto
     */
    public void siirra(Siirto siirto){
	this.peli.lisaaSiirto(siirto);
	this.peli.siirtoEteen();   
    }

    /**
     * Kertoo pelaajan nimen.
     * @return pelaajan nimi
     */
    public String kerroNimi(){
	return this.nimi;
    }

    /**
     * Asettaa pelaajan nimen.
     * @param nimi pelaajan nimi
     */
    public void asetaNimi(String nimi){
	this.nimi = nimi;
    }

    /**
     * Kertoo pelaajan pelimerkin ('x' tai 'o').
     * @return pelimerkki
     */
    public char kerroPelimerkki(){
	return this.pelimerkki;
    }

    /**
     * Käskee pelaajaa keksimään siirron. Harkinnan tuloksena syntynyt 
     * siirto annetaan palautusarvona. Aliluokat määrittävät tämän metodin
     * toteutuksen.
     * @return siirto, johon päädyttiin
     */
    public abstract Siirto mietiSiirto();

}
  
  
