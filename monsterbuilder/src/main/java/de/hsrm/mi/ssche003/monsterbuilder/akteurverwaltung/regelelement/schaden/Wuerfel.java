package de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.schaden;

public enum Wuerfel {
    W4 {
        public int wuerfle() {
            return (int)(Math.random() * 4 + 1);
        }
    }, 
    W6 {
        public int wuerfle() {
            return (int)(Math.random() * 6 + 1);
        }
    }, 
    W8 {
        public int wuerfle() {
            return (int)(Math.random() * 8 + 1);
        }
    },
    W10 {
        public int wuerfle() {
            return (int)(Math.random() * 10 + 1);
        }
    }, 
    W12 {
        public int wuerfle() {
            return (int)(Math.random() * 12 + 1);
        }
    }, 
    W20 {
        public int wuerfle() {
            return (int)(Math.random() * 20 + 1);
        }
    }, 
    W100 {
        public int wuerfle() {
            return (int)(Math.random() * 100 + 1);
        }
    };

    public abstract int wuerfle();
}
