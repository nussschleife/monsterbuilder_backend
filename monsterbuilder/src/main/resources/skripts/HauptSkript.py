# -*- coding: utf-8 -*-
from de.hsrm.mi.ssche003.monsterbuilder.akteur import Akteur
from de.hsrm.mi.ssche003.monsterbuilder.akteur.monster import Monster
from de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter import Charakter
from de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff import WaffenAngriff as Angriff
from de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden import Wuerfel
from  de.hsrm.mi.ssche003.monsterbuilder.simulation.simService import SimState
from  de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis import EreignisCode

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

alleCharaktere = {}
alleMonster = {}

javaMonster = []
javaCharaktere = []

akteur = Akteur()
state = SimState()

alleAkteure = {}
eventhandlers = {EreignisCode.ANGREIFEN: angreifen, EreignisCode.AKTION: findeAktion, EreignisCode.AUSWEICHEN: ausweichen, EreignisCode.SCHADEN: schaden}

def initialisiere(): #wäre natürlich premium wenn man die javasachen hier übergeben kann
    alleMonster.update({str(mon.getName()):MonsterVerhalten2(copyMonster(mon)) for mon in javaMonster})
    alleCharaktere.update({str(char.getName()):CharakterVerhalten2(copyCharakter(char)) for char in javaCharaktere})
    alleAkteure.update(alleMonster)
    alleAkteure.update(alleCharaktere)

def handleEreignis():
    akteur = alleAkteure[str(aktuellesEreignis.getAkteurName())]
    #akteur.getConditions().stream().foreach((c) -> c.senkeDauer(1))
    eventhandlers[aktuellesEreignis.getCode()](akteur, aktuellesEreignis)


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