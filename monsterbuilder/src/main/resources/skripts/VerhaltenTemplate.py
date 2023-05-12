
class CustomVerhalten(AkteurVerhalten):
    def __init__(self, akteur):
        self.akteur = akteur 
        super(AkteurVerhalten, self)

    def angriff(self,ereignis):
        global aktuellesEreignis
        #TODO: angriff auswählen anhand von schwächen usw -> Zauber & Elementvertraeglichkeiten rein
        gegnerverhalten = min(list(alleMonster.values()), key=lambda x: x.akteur.getLebenspunkte())
        gegner = gegnerverhalten.akteur
        angriff =self.akteur.getAlleAngriffe().toArray()[0]
        gegner = self.akteur.angriffAusfuehren(angriff, gegner)
        ereignis.gegner = gegner.getName()
        aktuellesEreignis.toedlich = gegner.lebenspunkte <= 0
        if aktuellesEreignis.toedlich:
            gegnerverhalten._sterben()
        return gegner

    def findeAktion(self, ereignis):
        a = self.angriff(ereignis)
        for con in self.akteur.getConditions():
            con.verringereDauer(1)
        return a

    def _sterben(self):
        self.state = States.DEAD
        del alleAkteure[self.akteur.getName()]
        del alleCharaktere[self.akteur.getName()]

def initialise():
    global toInit, alleMonster, alleCharaktere, alleAkteure
    if isinstance(toInit, Charakter):
        alleCharaktere[str(toInit.getName())] = CustomVerhalten(copyCharakter(toInit))
        alleAkteure.update(alleCharaktere)
    else:
        alleMonster[str(toInit.getName())] = CustomVerhalten(copyMonster(toInit))
        alleAkteure.update(alleMonster)

initialise()

#akteur pseudoklasse
#angriff etc.