/**
 * Kuvaa yhtä peliruudukon ruutua graafisessa käyttöliittymässä.
 * @author Tuukka Sarvi
 */

import javax.swing.*;
import java.awt.Insets;

public class Ruutunappi extends JButton {

    private Koordinaatit paikka;

    /**
     * Luo uuden Ruutunapin.
     * @param paikka napin edustaman ruudun koordinaatit
     * @param kuva ikoni, joka asetetaan napin ulkoasuksi
     */
    public Ruutunappi(Koordinaatit paikka, ImageIcon kuva){
	super();
	this.asetaKuva(kuva);
	this.setFocusPainted(false);
	this.setMargin(new Insets(0,0,0,0));
	//this.setVerticalAlignment(SwingConstants.CENTER);
	//	this.setHorizontalAlignment(SwingConstants.CENTER);
        this.paikka = paikka;
    }

    /**
     * Asetaa napin ikoniksi annetun kuvan.
     * @param kuva napin kuva
     */
    public void asetaKuva(ImageIcon kuva){
	this.setIcon(kuva);
	this.setDisabledIcon(kuva);
	//	this.setPressedIcon(kuva);
	//this.setSelectedIcon(kuva);
	//this.setRolloverIcon(kuva);
	//this.setRolloverSelectedIcon(kuva);
	//super.setDisabledSelectedIcon(kuva);
    }

    /**
     * Palauttaa napin edustaman ruudun koordinaatit.
     * @return ruudun koordinaatit
     */
    public Koordinaatit kerroPaikka(){
	return this.paikka;
    }

}
