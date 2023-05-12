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
alleAkteure = {}
eventhandlers = {EreignisCode.ANGREIFEN: angreifen, EreignisCode.AKTION: findeAktion, EreignisCode.AUSWEICHEN: ausweichen, EreignisCode.SCHADEN: schaden}
class States:
   DEAD = 1
   ALIVE = 2
   WOUNDED = 3
   SCARED = 4
   PRONE = 5

class AkteurVerhalten(object):
   
    gegner = []

    def __init__(self, akteur):
        self.akteur = akteur
        self.state = States.ALIVE

    def reaktion(self, ereignis):
        return self._ausweichen()
        
    def aktion(self, ereignis):
        return self.monster.getAlleAktionen()[0]
    
    def angriff(self, ereignis):
        a = b

    def bewegen(self, ereignis):
        raise Exception('not implemented') 


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
        return self.angriff(ereignis)
        for con in self.akteur.getConditions():
            con.verringereDauer(1)

    def _sterben(self): #direkt aus lebende raus?
        self.state = States.DEAD
        del alleAkteure[self.akteur.getName()]
        del alleMonster[self.akteur.getName()]
        state.toeteAkteur(str(self.akteur.getName()))


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
        state.toeteAkteur(str(self.akteur.getName()))

def initialisiere(): #wäre natürlich premium wenn man die javasachen hier übergeben kann
    global alleMonster, alleAkteure, alleCharaktere
    alleMonster.update({str(mon.getName()):MonsterVerhalten2(copyMonster(mon)) for mon in javaMonster})
    alleCharaktere.update({str(char.getName()):CharakterVerhalten2(copyCharakter(char)) for char in javaCharaktere})
    alleAkteure.update(alleMonster)
    alleAkteure.update(alleCharaktere)
    return str(alleAkteure.keys()) + " " + str(alleAkteure.values())

def handleEreignis():
    global aktuellesEreignis
    for ver in alleAkteure:
        if ver == str(aktuellesEreignis.getAkteurName()):
            akteurver = alleAkteure[ver]
            return akteurver.findeAktion(aktuellesEreignis)
    return aktuellesEreignis.getAkteurName() + ' not found'
  #  if akteur in alleMonster:
   #     akteur = alleMonster[str(aktuellesEreignis.getAkteurName())]
  #  if akteur in alleCharaktere:
    #    akteur =  alleCharaktere[str(aktuellesEreignis.getAkteurName())]
 #   verhalten = str(aktuellesEreignis.getAkteurName()) =='BLIZZARD ARBOR'
    #akteur.akteur.getConditions().stream().foreach((c) -> c.senkeDauer(1))
    #eventhandlers[aktuellesEreignis.getCode()](akteur, aktuellesEreignis)
    


def findeBestenAngriffGegenMonster(gegner, charakter):
    #TODO:nach elementvertraeglichkeiten suchen
    return charakter.getAlleAngriffe().toArray()[0]

def findeBestenAngriffGegenCharakter(gegner, monster):
    if len(gegner.getConditions()) is 0:
        return monster.getAlleZauber().toArray()[0]
    return monster.getAlleAngriffe().toArray()[0]


def copyAkteur(akteur, copy):
    copy.setLebenspunkte(akteur.getLebenspunkte())
    copy.setRuestungsklasse(akteur.getRuestungsklasse())
    copy.setName(akteur.getName())
    copy.setGeschwindigkeit_ft(akteur.getGeschwindigkeit_ft())
    copy.setId(akteur.getId())
    copy.setAlleAngriffe(akteur.getAlleAngriffe())
    copy.setAlignment(akteur.getAlignment())
    copy.setAlleZauber(akteur.getAlleZauber())
    return copy
        

def copyMonster(monster):
    copy = copyAkteur(monster, Monster())
    copy.setAlleTraits(monster.getAlleTraits())
    return copy

def copyCharakter(chara):
    return copyAkteur(chara, Charakter())