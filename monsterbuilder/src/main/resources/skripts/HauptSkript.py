# -*- coding: utf-8 -*-
from de.hsrm.mi.ssche003.monsterbuilder.akteur import Akteur
from de.hsrm.mi.ssche003.monsterbuilder.akteur.monster import Monster
from de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter import Charakter
from de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff import WaffenAngriff as Angriff
from de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden import Wuerfel
from  de.hsrm.mi.ssche003.monsterbuilder.simulation.simService import SimState
from  de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis import EreignisCode
from  de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis import AkteurEreignis

#TODO: alle verhaltensskripte importieren und in Map speichern: Alignment-skriptInimethode
#in initialisierungsmethode schauen welches alignment der akteur hat
#richtiges element in map finden
#ini methode aufrufen und akteur uebergeben
#man bekommt initialisiertes Objekt zurueck und gibt es an copy weiter?

def schaden(akteur, ereignis):
    akteur.schaden(ereignis)

def ausweichen(akteur, ereignis):
    akteur.ausweichen(ereignis)

def angreifen(akteur, ereignis):
    akteur.angriff(ereignis)

def findeAktion(akteur, ereignis):
    akteur.findeAktion(ereignis)

if not "alleCharaktere" in globals():
    alleCharaktere = {}
if not "alleMonster" in globals():
    alleMonster = {}
if not "alleAkteure" in globals():
    alleAkteure = {}
eventhandlers = {EreignisCode.ANGREIFEN: angreifen, EreignisCode.AKTION: findeAktion, EreignisCode.AUSWEICHEN: ausweichen, EreignisCode.SCHADEN: schaden}

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

def handleEreignis():
    global aktuellesEreignis
    for ver in alleAkteure:
        if ver == str(aktuellesEreignis.getAkteurName()):
            akteurver = alleAkteure[ver]
            return eventhandlers[aktuellesEreignis.getCode()](akteurver, aktuellesEreignis)
    return aktuellesEreignis.getAkteurName() + ' not found'


def copyAkteur(akteur, copy):
    copy.setLebenspunkte(akteur.getLebenspunkte())
    copy.setRuestungsklasse(akteur.getRuestungsklasse())
    copy.setName(akteur.getName())
    copy.setGeschwindigkeit_ft(akteur.getGeschwindigkeit_ft())
    copy.setAlleAngriffe(akteur.getAlleAngriffe())
    copy.setAlleZauber(akteur.getAlleZauber())
    return copy
        

def copyMonster(monster):
    if isinstance(monster, Charakter):
        return copyCharakter(monster)
    copy = copyAkteur(monster, Monster())
    copy.setAlleTraits(monster.getAlleTraits())
    return copy

def copyCharakter(chara):
    return copyAkteur(chara, Charakter())

if isinstance(aktuellesEreignis, AkteurEreignis):
    handleEreignis() 