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

if not "alleCharaktere" in globals():
    alleCharaktere = {}
if not "alleMonster" in globals():
    alleMonster = {}
if not "alleAkteure" in globals():
    alleAkteure = {}

def initialisiere(): 
    global toInit, alleMonster, alleCharaktere, alleAkteure
    if isinstance(toInit, Charakter):
        alleCharaktere[str(toInit.getName())] = copyCharakter(toInit)
        alleAkteure.update(alleCharaktere)
    else:
        alleMonster[str(toInit.getName())] = copyMonster(toInit)
        alleAkteure.update(alleMonster)
    
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
    #copy.setAlleTraits(monster.getAlleTraits())
    return copy

def copyCharakter(chara):
    return copyAkteur(chara, Charakter())

if isinstance(aktuellesEreignis, AkteurEreignis):
    handleEreignis() 