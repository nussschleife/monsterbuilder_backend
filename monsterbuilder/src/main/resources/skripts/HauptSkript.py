# -*- coding: utf-8 -*-
from de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung import Akteur
from de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.monster import Monster
from de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.charakter import Charakter
from  de.hsrm.mi.ssche003.monsterbuilder.simulation.service import SimState
from  de.hsrm.mi.ssche003.monsterbuilder.simulation.service.ereignis import AkteurEreignis
from de.hsrm.mi.ssche003.monsterbuilder.akteurverwaltung.regelelement.condition import Condition


if not "alleCharaktere" in globals():
    alleCharaktere = {}
if not "alleMonster" in globals():
    alleMonster = {}

if not "alleAkteute" in globals():
    alleAkteure = {}

def initialisiere(): 
    global toInit, alleMonster, alleCharaktere, alleAkteure
    if isinstance(toInit, Charakter):
        alleCharaktere[str(toInit.getName())] = copyCharakter(toInit)
        alleAkteure[str(toInit.getName())] = alleCharaktere[str(toInit.getName())]
    else:
        alleMonster[str(toInit.getName())] = copyMonster(toInit)  
        alleAkteure[str(toInit.getName())] = alleMonster[str(toInit.getName())]  
    
def copyAkteur(akteur, copy):
    copy.setLebenspunkte(akteur.getLebenspunkte())
    copy.setRuestungsklasse(akteur.getRuestungsklasse())
    copy.setName(akteur.getName())
    copy.setGeschwindigkeit_ft(akteur.getGeschwindigkeit_ft())
    copy.setAlleAngriffe(akteur.getAlleAngriffe())
    copy.setAlleZauber(akteur.getAlleZauber())
    copy.setConditions([])
    return copy

def copyMonster(monster):
    if isinstance(monster, Charakter):
        return copyCharakter(monster)
    copy = copyAkteur(monster, Monster())
    return copy

def copyCharakter(chara):
    return copyAkteur(chara, Charakter())

if isinstance(aktuellesEreignis, AkteurEreignis):
    handleEreignis() 