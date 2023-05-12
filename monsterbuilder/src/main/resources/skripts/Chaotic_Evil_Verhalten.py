# -*- coding: utf-8 -*-
from de.hsrm.mi.ssche003.monsterbuilder.akteur import Akteur
from de.hsrm.mi.ssche003.monsterbuilder.akteur.monster import Monster
from de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter import Charakter
from de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff import WaffenAngriff as Angriff
from de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden import Wuerfel
from  de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis import AkteurEreignis
from  de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis import EreignisCode

class States:
   DEAD = 1
   ALIVE = 2
   WOUNDED = 3
   SCARED = 4
   PRONE = 5

class MonsterVerhalten2(AkteurVerhalten):

    gegner = alleCharaktere

    def __init__(self, monster):
            self.akteur = monster
            super(MonsterVerhalten2, self)

    def angriff(self,ereignis):
        #TODO: angriff auswählen anhand von schwächen, state usw -> Zauber & Elementvertraeglichkeiten rein
        global aktuellesEreignis
        gegnerverhalten = min(list(alleCharaktere.values()), key=lambda x: x.akteur.getLebenspunkte())
        gegner = gegnerverhalten.akteur
        angriff = findeBestenAngriffGegenCharakter(gegner, self.akteur)

        gegner = self.akteur.angriffAusfuehren(angriff, gegner)
        ereignis.gegner = gegner.getName()
        aktuellesEreignis.toedlich = gegner.lebenspunkte <= 0
        if aktuellesEreignis.toedlich:
            gegnerverhalten._sterben()
        return gegner
        #TODO: ereignisresult

    def findeAktion(self, ereignis):
        self.angriff(ereignis)
        for con in self.akteur.getConditions():
            con.verringereDauer(1)

    def _sterben(self): #direkt aus lebende raus?
        self.state = States.DEAD
        del alleAkteure[self.akteur.getName()]
        del alleMonster[self.akteur.getName()]


class CharakterVerhalten2(AkteurVerhalten):
    gegner = alleMonster

    def __init__(self, charakter):
        self.akteur = charakter 
        super(CharakterVerhalten2, self)

    def angriff(self,ereignis):
        global aktuellesEreignis
        #TODO: angriff auswählen anhand von schwächen usw -> Zauber & Elementvertraeglichkeiten rein
        gegnerverhalten = min(list(alleMonster.values()), key=lambda x: x.akteur.getLebenspunkte())
        gegner = gegnerverhalten.akteur
        angriff = findeBestenAngriffGegenMonster(gegner, self.akteur)
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

def initialisiere(): 
    global toInit, alleMonster, alleCharaktere, alleAkteure
    if isinstance(toInit, Charakter):
        alleCharaktere[str(toInit.getName())] = CharakterVerhalten2(copyCharakter(toInit))
        alleAkteure.update(alleCharaktere)
    else:
        alleMonster[str(toInit.getName())] = MonsterVerhalten2(copyMonster(toInit))
        alleAkteure.update(alleMonster)
    
def findeBestenAngriffGegenMonster(gegner, charakter):
    #TODO:nach elementvertraeglichkeiten suchen
    return charakter.getAlleAngriffe().toArray()[0]

def findeBestenAngriffGegenCharakter(gegner, monster):
    if len(gegner.getConditions()) is 0:
        return monster.getAlleZauber().toArray()[0]
    return monster.getAlleAngriffe().toArray()[0]

initialisiere()