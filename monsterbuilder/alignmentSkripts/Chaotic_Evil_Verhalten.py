# -*- coding: utf-8 -*-
global alleCharaktere, alleMonster, aktuellesEreignis, state, akteur
alleGegner = alleCharaktere if isinstance(akteur, Monster) else alleMonster
class States:
   DEAD = 1
   WOUNDED = 3
   SCARED = 4
   ALIVE = 5

akteurstate = States.ALIVE

def angreifen(akteur, ereignis):
    global aktuellesEreignis
    gegner = min(list(alleGegner.values()), key=lambda x: x.getLebenspunkte())
    angriff = findeBestenAngriffGegenCharakter(gegner, akteur) if isinstance(akteur, Monster) else findeBestenAngriffGegenMonster(gegner, akteur)
    if(angriff and gegner):
        gegner = akteur.aktionAusfuehren(angriff, gegner)
        ereignis.gegner = gegner.getName()
        aktuellesEreignis.toedlich = gegner.lebenspunkte <= 0
        if aktuellesEreignis.toedlich:
            del alleGegner[str(gegner.getName())]
    return gegner

def findeAktion(akteur, ereignis):
    if len(akteur.getAlleAktionen()) > 0:
        angreifen(akteur, ereignis)
    for con in akteur.getConditions():
        con.verringereDauer(1)

def findeBestenAngriffGegenMonster(gegner, charakter):
    if len(charakter.getAlleAngriffe()) > 0:
        if akteurstate is States.SCARED:
            return charakter.getAlleAngriffe().stream().max(Comparator.comparing(lambda a: a.berechneSchaden()))
        return charakter.getAlleAngriffe().toArray()[0]

def findeBestenAngriffGegenCharakter(gegner, monster):
    if len(gegner.getConditions()) == 0 and len(monster.getAlleZauber()) > 0:
        return monster.getAlleZauber().toArray()[0]
    if len(monster.getAlleAngriffe()) > 0:
        return monster.getAlleAngriffe().toArray()[0]


##passiert bei Skriptaufruf##
if(akteur.getLebenspunkte() < 10):
    akteurstate = States.SCARED

findeAktion(akteur, aktuellesEreignis)