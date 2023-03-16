package de.hsrm.mi.ssche003.monsterbuilder;

public class Schaden {
    Wuerfel wuerfel;
    byte wuerfelanzahl;
    byte modifikator;

    public Wuerfel getWuerfel() {
        return wuerfel;
    }
    public void setWuerfel(Wuerfel wuerfel) {
        this.wuerfel = wuerfel;
    }
    public byte getWuerfelanzahl() {
        return wuerfelanzahl;
    }
    public void setWuerfelanzahl(byte wuerfelanzahl) {
        this.wuerfelanzahl = wuerfelanzahl;
    }
    public byte getModifikator() {
        return modifikator;
    }
    public void setModifikator(byte modifikator) {
        this.modifikator = modifikator;
    }
    
}
