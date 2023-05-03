# -*- coding: utf-8 -*-
from copy import copy
import random, sys
from operator import attrgetter
from de.hsrm.mi.ssche003.monsterbuilder.akteur import Akteur
from de.hsrm.mi.ssche003.monsterbuilder.akteur.monster import Monster
from de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter import Charakter
from de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff import Angriff
from de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden import Wuerfel
from  de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis import IEreignis
from  de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis import SchadenEreignis

javaMonster = []
javaCharaktere = []

#aktuellesEreignis = IEreignis()
alleCharaktere = []
alleMonster = []

akteur = Akteur()
#was wird zurückgegeben? Ereignis/Akteur??


def initialisiere(): #wäre natürlich premium wenn man die javasachen hier übergeben kann
    alleMonster.extend([copyMonster(mon) for mon in javaMonster])
    alleCharaktere.extend([copyCharakter(char) for char in javaCharaktere])


""" def copyAlle():
    for mon in javaMonster:
        cp = copyMonster(mon)
        alleAkteure.append(cp)
        alleMonster.append(cp)
    for char in javaCharaktere:
        cp = copyCharakter(char)
        alleAkteure.append(cp)
        alleCharaktere.append(cp) """

def ende():
    s = ""
    for mon in alleMonster:
        s += str(mon.getLebenspunkte()) + " " + mon.getName()

    for mon in alleCharaktere:
        s += str(mon.getLebenspunkte()) +" "+ mon.getName()
    return s

""" def log():
    s = ""
    for mon in alleAkteure:
        s = s+ str(mon.id)+" "
    return s

def dmglog():
    return str(alleAkteure[0].getAlleAngriffe().toArray()[0].getWuerfel()) """


#def holeAkteurMitId(id):
   #return [akteur for akteur in alleAkteure if akteur.id == id ][0]

def schaden():
    akteur.lebenspunkte -= aktuellesEreignis.schaden
    return akteur.lebenspunkte <= 0

def ausweichen():
    return Wuerfel.W20.wuerfle() >= aktuellesEreignis.dc

def angreifen():
    #TODO: angriff auswählen anhand von schwächen usw -> Zauber & Elementvertraeglichkeiten rein
    gegner = min(alleMonster, key=lambda x: x.getLebenspunkte())
    angriff = findeBestenAngriffGegenMonster(gegner, akteur.getAlleAngriffe())

    if(wirdGegnerGetroffen(gegner)):
        return SchadenEreignis(gegner, angriff.getWuerfel().wuerfle())
    return SchadenEreignis(gegner, 0)

def findeAktion():
    c = "d"
        

class MonsterVerhalten(Monster):

    def __init__(self):
        super(MonsterVerhalten, self)

    def schaden(self, schaden):
        self.lebenspunkte -= schaden
        return self.lebenspunkte <= 0

    def ausweichen(self, dc):
        return Wuerfel.W20.wuerfle() >= dc

    def angreifen(self):
        #TODO: angriff auswählen anhand von schwächen usw -> Zauber & Elementvertraeglichkeiten rein

        gegner = min(alleCharaktere, key=lambda x: x.getLebenspunkte())
        angriff = findeBestenAngriffGegenCharakter(gegner, self.getAlleAngriffe())

        if(wirdGegnerGetroffen(gegner)):
            return SchadenEreignis(gegner, angriff.getWuerfel().wuerfle())
        return SchadenEreignis(gegner, 0)

    def findeAktion(self):
        c = "d"

#def wirdEreignisAngenommen(id):
    #return len([akteur for akteur in alleAkteure if akteur.id == id ]) > 0

def findeBestenAngriffGegenMonster(gegner, angriffe):
    #nach elementvertraeglichkeiten suchen
    return angriffe.toArray()[0]

def findeBestenAngriffGegenCharakter(gegner, angriffe):
    #nach elementvertraeglichkeiten suchen
    return angriffe.toArray()[0]

def wirdGegnerGetroffen(gegner):
    return gegner.getRuestungsklasse() > Wuerfel.W20.wuerfle()
#wenn ereignis reingereicht wird, betrifft das ja nur einen akteur.
#muss ereignis reingereicht werden oder ruft es nur methoden auf?


class States:
   DEAD = 1
   ALIVE = 2
   WOUNDED = 3
   SCARED = 4
   PRONE = 5

""" class AkteurVerhalten(Akteur):
   
    state = States.ALIVE
    akteur = Akteur()
    gegner = []

    def __init__(self, akteur):
        self.akteur = akteur

    def initiative():
        return int(random()*20)

    def reaktion(self, save):
        return self._ausweichen()
        
    def aktion(self):
        i = int(random()*self.akteur.getAlleAngriffe().length)
        return self.akteur.getAlleAngriffe[i]
    
    def angreifen(self):
        a = b


    def bewegen(self, laufweite):
        raise Exception('not implemented') 
    
    def _ausweichen(self, dc):
        return int(random()*25) >= dc


class MonsterVerhalten2(AkteurVerhalten,):

    state = States.ALIVE
    monster = Monster()

    def __init__(self, monster):
        self.monster = monster
        AkteurVerhalten.akteur = monster

    def initiative():
       return AkteurVerhalten.initiative()
    
    def reaktion(self, dc):
        return random()*20 > dc
       
    def aktion(self):
       if(self.state is States.WOUNDED):
          return self.monster.getAlleAktionen()[0]

    def bewegen(self):
       r = 0

    def angreifen(self):
        a = b

class CharakterVerhalten2(AkteurVerhalten, Charakter):
    charakter = Charakter()
    state = States.ALIVE

    def __init__(self, charakter):
        self.charakter = charakter """

def copyAkteur(akteur, copy):
    copy.setLebenspunkte(akteur.getLebenspunkte())
    copy.setRuestungsklasse(akteur.getRuestungsklasse())
    copy.setName(akteur.getName())
    copy.setGeschwindigkeit_ft(akteur.getGeschwindigkeit_ft())
    copy.setId(akteur.getId())
    copy.setAlleAngriffe(akteur.getAlleAngriffe())
    return copy
        

def copyMonster(monster):
    copy = copyAkteur(monster, Monster())
    copy.setAlleTraits(monster.getAlleTraits())
    return copy

def copyCharakter(chara):
    return copyAkteur(chara, Charakter())

