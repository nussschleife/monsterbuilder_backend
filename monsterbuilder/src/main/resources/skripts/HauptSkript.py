# -*- coding: utf-8 -*-
from de.hsrm.mi.ssche003.monsterbuilder.akteur import Akteur
from de.hsrm.mi.ssche003.monsterbuilder.akteur.monster import Monster
from de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter import Charakter
from de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.angriff import WaffenAngriff as Angriff
from de.hsrm.mi.ssche003.monsterbuilder.akteur.regelelement.schaden import Wuerfel
from  de.hsrm.mi.ssche003.monsterbuilder.simulation.service import SimState
from  de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis import EreignisCode
from  de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis import AkteurEreignis

if not "alleCharaktere" in globals():
    alleCharaktere = {}
if not "alleMonster" in globals():
    alleMonster = {}

def initialisiere(): 
    global toInit, alleMonster, alleCharaktere, alleAkteure
    if isinstance(toInit, Charakter):
        alleCharaktere[str(toInit.getName())] = copyCharakter(toInit)
    else:
        alleMonster[str(toInit.getName())] = copyMonster(toInit)
    
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
    return copy

def copyCharakter(chara):
    return copyAkteur(chara, Charakter())

if isinstance(aktuellesEreignis, AkteurEreignis):
    handleEreignis() 