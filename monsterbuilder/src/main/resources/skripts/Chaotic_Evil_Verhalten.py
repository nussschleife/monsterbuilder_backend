# -*- coding: utf-8 -*-
from de.hsrm.mi.ssche003.monsterbuilder.akteur import Akteur
from de.hsrm.mi.ssche003.monsterbuilder.akteur.monster import Monster
from de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter import Charakter
from de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff import WaffenAngriff as Angriff
from de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden import Wuerfel
from  de.hsrm.mi.ssche003.monsterbuilder.simulation.simService import SimState
from  de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis import EreignisCode

def schaden(akteur, ereignis):
    akteur.schaden(ereignis)

def ausweichen(akteur, ereignis):
    akteur.ausweichen(ereignis)

def angreifen(akteur, ereignis):
    akteur.angriff(ereignis)

def findeAktion(akteur, ereignis):
    akteur.findeAktion(ereignis)

alleCharaktere = {}
alleMonster = {}

javaMonster = []
javaCharaktere = []

akteur = Akteur()
state = SimState()

alleAkteure = {}
eventhandlers = {EreignisCode.ANGREIFEN: angreifen, EreignisCode.AKTION: findeAktion, EreignisCode.AUSWEICHEN: ausweichen, EreignisCode.SCHADEN: schaden}
class States:
   DEAD = 1
   ALIVE = 2
   WOUNDED = 3
   SCARED = 4
   PRONE = 5

class AkteurVerhalten(Akteur):
   
    state = States.ALIVE
    akteur = Akteur()
    gegner = []

    def __init__(self, akteur):
        self.akteur = akteur

    def initiative():
        return akteur.wuerfleInitiative()

    def reaktion(self, ereignis):
        return self._ausweichen()
        
    def aktion(self, ereignis):
        return self.monster.getAlleAktionen()[0]
    
    def angriff(self, ereignis):
        a = b

    def bewegen(self, ereignis):
        raise Exception('not implemented') 
    
    def _ausweichen(self, ereignis):
        return int(random()*25) >= dc


class MonsterVerhalten2(AkteurVerhalten):

    state = States.ALIVE

    def __init__(self, monster):
            self.akteur = monster
            super(MonsterVerhalten2, self)

    def initiative():
       return AkteurVerhalten.initiative()
    
    def angriff(self,ereignis):
        #TODO: angriff auswählen anhand von schwächen, state usw -> Zauber & Elementvertraeglichkeiten rein
        gegnerverhalten = min(list(alleCharaktere.values()), key=lambda x: x.akteur.getLebenspunkte())
        gegner = gegnerverhalten.akteur
        angriff = findeBestenAngriffGegenCharakter(gegner, self.akteur.getAlleAngriffe())

        gegner = self.akteur.angreifen(angriff, gegner)
        ereignis.gegner = gegner.getName()
        ereignis.toedlich = gegner.lebenspunkte <= 0
        if ereignis.toedlich:
            gegnerverhalten._sterben()
        #TODO: ereignisresult

    def findeAktion(self, ereignis):
        #tTODO: entscheiden ob man sich bewegt oder so, wie weit ist gegner weg etc. dnan vllt zaubern oder effektzauber wirken
        angreifen(ereignis)
        c = "d"

    def _sterben(self): #direkt aus lebende raus?
        self.state = States.DEAD
        del alleAkteure[self.akteur.getName()]
        del alleMonster[self.akteur.getName()]


class CharakterVerhalten2(AkteurVerhalten):
    state = States.ALIVE

    def __init__(self, charakter):
        self.akteur = charakter 
        super(CharakterVerhalten2, self)

    def initiative():
       return AkteurVerhalten.initiative()
    
    def schaden(self, ereignis):
        self.akteur.lebenspunkte -= ereignis.schaden
        tot = self.akteur.lebenspunkte <= 0
        if tot:
            self._sterben()
        ereignis.setChange(tot)

    def ausweichen(self, ereignis):
        ereignis.ausgewichen = Wuerfel.W20.wuerfle() >= ereignis.getSchwierigkeit()

    def angriff(self,ereignis):
        #TODO: angriff auswählen anhand von schwächen usw -> Zauber & Elementvertraeglichkeiten rein
        gegnerverhalten = min(list(alleCharaktere.values()), key=lambda x: x.akteur.getLebenspunkte())
        gegner = gegnerverhalten.akteur
        angriff = findeBestenAngriffGegenCharakter(gegner, self.akteur.getAlleAngriffe())

        gegner = self.akteur.angreifen(angriff, gegner)
        ereignis.gegner = gegner.getName()
        ereignis.toedlich = gegner.lebenspunkte <= 0
        if ereignis.toedlich:
            gegnerverhalten._sterben()

    def findeAktion(self, ereignis):
        c = "d"

    def _sterben(self):
        self.state = States.DEAD
        del alleAkteure[self.akteur.getName()]
        del alleCharaktere[self.akteur.getName()]
        state.toeteAkteur(str(self.akteur.getName()))

def initialisiere(): #wäre natürlich premium wenn man die javasachen hier übergeben kann
    alleMonster.update({str(mon.getName()):MonsterVerhalten2(copyMonster(mon)) for mon in javaMonster})
    alleCharaktere.update({str(char.getName()):CharakterVerhalten2(copyCharakter(char)) for char in javaCharaktere})
    alleAkteure.update(alleMonster)
    alleAkteure.update(alleCharaktere)

def handleEreignis():
    akteur = alleAkteure[str(aktuellesEreignis.getAkteurName())]
    #akteur.getConditions().stream().foreach((c) -> c.senkeDauer(1))
    eventhandlers[aktuellesEreignis.getCode()](akteur, aktuellesEreignis)


def findeBestenAngriffGegenMonster(gegner, angriffe):
    #nach elementvertraeglichkeiten suchen
    return angriffe.toArray()[0]

def findeBestenAngriffGegenCharakter(gegner, angriffe):
    #nach elementvertraeglichkeiten suchen
    return angriffe.toArray()[0]

def wirdGegnerGetroffen(gegner):
    return gegner.getRuestungsklasse() <= Wuerfel.W20.wuerfle()

def copyAkteur(akteur, copy):
    copy.setLebenspunkte(akteur.getLebenspunkte())
    copy.setRuestungsklasse(akteur.getRuestungsklasse())
    copy.setName(akteur.getName())
    copy.setGeschwindigkeit_ft(akteur.getGeschwindigkeit_ft())
    copy.setId(akteur.getId())
    copy.setAlleAngriffe(akteur.getAlleAngriffe())
    copy.setAlignment(akteur.getAlignment())
    return copy
        

def copyMonster(monster):
    copy = copyAkteur(monster, Monster())
    copy.setAlleTraits(monster.getAlleTraits())
    return copy

def copyCharakter(chara):
    return copyAkteur(chara, Charakter())

