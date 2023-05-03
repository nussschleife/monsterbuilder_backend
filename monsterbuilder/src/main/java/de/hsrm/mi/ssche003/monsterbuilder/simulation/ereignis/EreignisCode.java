package de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis;

public enum EreignisCode {
    AUSWEICHEN("ausweichen"), ANGREIFEN("angreifen"), SCHADEN("schaden"), AKTION("findeAktion");

    private EreignisCode(String code) {
        this.value = code;
    }

    private String value;

    @Override
    public String toString() {
        return this.value;
    }
}
