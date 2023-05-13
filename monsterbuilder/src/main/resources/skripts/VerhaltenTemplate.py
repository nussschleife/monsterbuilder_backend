# -*- coding: utf-8 -*-

#koennen beliebig erweitert werden
class States:
   DEAD = 1
   ALIVE = 2
   WOUNDED = 3
   SCARED = 4

def findeAktion(akteur, ereignis):
    if len(akteur.getAlleAktionen()) > 0:
    ##aktion aussuchen und verwenden##
        aktion = akteur.getAlleAktionen()[0]
        gegner = alleGegner[0]
        akteur.aktionAusfuehren(aktion, gegner)

    ##nach der Aktion werden potentielle conditions runtergezaehlt##
    for con in akteur.getConditions():
        con.verringereDauer(1)

    ##es wird geschaut ob der Gegner gestorben ist##
    ereignis.toedlich = gegner.lebenspunkte <= 0
    if ereignis.toedlich:
        del alleGegner[str(gegner.getName())]

##hier koennen States gesetzt werden##
akteurstate = States.ALIVE
##Aktion wird waehren der Simulation ausgefuehrt##
alleGegner = alleCharaktere if isinstance(akteur, Monster) else alleMonster
findeAktion(akteur, aktuellesEreignis)