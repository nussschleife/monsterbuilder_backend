from  de.hsrm.mi.ssche003.monsterbuilder.simulation.ereignis import EreignisCode
# -*- coding: utf-8 -*-
global alleCharaktere, alleMonster, alleAkteure, aktuellesEreignis, state, akteur, eventhandlers
alleGegner = alleCharaktere if isinstance(akteur, Monster) else alleMonster
class States:
   DEAD = 1
   ALIVE = 2
   WOUNDED = 3
   SCARED = 4
   PRONE = 5

state = States.ALIVE

def angreifen(akteur, ereignis):
    #TODO: angriff auswählen anhand von schwächen, state usw -> Zauber & Elementvertraeglichkeiten rein
    global aktuellesEreignis
    gegner = min(list(alleGegner.values()), key=lambda x: x.getLebenspunkte())
    angriff = findeBestenAngriffGegenCharakter(gegner, akteur) if isinstance(akteur, Monster) else findeBestenAngriffGegenMonster(gegner, akteur)

    gegner = akteur.angriffAusfuehren(angriff, gegner)
    ereignis.gegner = gegner.getName()
    aktuellesEreignis.toedlich = gegner.lebenspunkte <= 0
    if aktuellesEreignis.toedlich:
        del alleGegner[str(gegner.getName())]
    return gegner
    #TODO: ereignisresult

def findeAktion(akteur, ereignis):
    angreifen(akteur, ereignis)
    for con in akteur.getConditions():
        con.verringereDauer(1)

def findeBestenAngriffGegenMonster(gegner, charakter):
    #TODO:nach elementvertraeglichkeiten suchen
    return charakter.getAlleAngriffe().toArray()[0]

def findeBestenAngriffGegenCharakter(gegner, monster):
    if len(gegner.getConditions()) is 0:
        return monster.getAlleZauber().toArray()[0]
    return monster.getAlleAngriffe().toArray()[0]


eventhandlers = {EreignisCode.ANGREIFEN: angreifen, EreignisCode.AKTION: findeAktion}


if(akteur.getLebenspunkte() < 10):
    state = States.SCARED

if aktuellesEreignis.getCode() == EreignisCode.ANGREIFEN:
    angreifen(akteur, aktuellesEreignis)
elif aktuellesEreignis.getCode() == EreignisCode.AKTION:
    findeAktion(akteur, aktuellesEreignis)