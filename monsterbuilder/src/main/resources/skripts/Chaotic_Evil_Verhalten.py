import random
from de.hsrm.mi.ssche003.monsterbuilder.akteur import Akteur
from de.hsrm.mi.ssche003.monsterbuilder.akteur.monster import Monster
from de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter import Charakter

alleMonster = set() #am Kampf beteiligte Monster mit diesem Verhalten
alleCharaktere = set()  #am Kampf beteiligte Charaktere mit diesem Verhalten
alleAkteure = set()

#was wird zurückgegeben? Ereignis/Akteur??

def initialisiere(charaktere, monster):
    alleMonster = set([Monster(mon) for mon in monster])
    alleCharaktere = set([Charakter(chara) for chara in charaktere])
    
    for chara in alleCharaktere:
        chara.gegner = monster
    for mon in alleMonster:
        mon.gegner = alleCharaktere

    alleAkteure = alleMonster.union(alleCharaktere) #geht das? sind ja einmal mosnter einmal charaktere

def holeAkteurMitId(id):
    return [akteur for akteur in alleAkteure if akteur.getId() is id ][0]


def aktion(akteurid, state, gegner):
    #wir suchen akteur der dran ist (akteurid)
    akteur = holeAkteurMitId(akteurid)
    akteur.aktion()
    #wozu braucht man den state? -> gucken wie es anderen charakteren geht
    #anhand der gegner aussuchen welche aktion man macht
    #gibt man den neuen akteur zurück oder die aktion? oder folgeereignisse?kjlk

def reaktion(akteurid, savingthrow):
    akteur = holeAkteurMitId(akteurid)
    akteur.reaktion(savingthrow)


#wenn ereignis reingereicht wird, betrifft das ja nur einen akteur.
#muss ereignis reingereicht werden oder ruft es nur methoden auf?


class States:
   DEAD = 1
   ALIVE = 2
   WOUNDED = 3
   SCARED = 4
   PRONE = 5

class AkteurVerhalten(object):
   
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


    def bewegen(self, laufweite):
        raise Exception('not implemented') 
    
    def _ausweichen(self, dc):
        return int(random()*25) >= dc


class MonsterVerhalten(AkteurVerhalten):

    state = States.ALIVE
    monster = Monster()

    def __init__(self, monster):
        self.monster = monster
        AkteurVerhalten.akteur = monster

    def initiative():
       return AkteurVerhalten.initiative()
    
    def reaktion(self):
       a = 2
       
    def aktion(self):
       if(self.state is States.WOUNDED):
          return self.monster.getAlleAktionen()[0]

    def bewegen(self):
       r = 0

class CharakterVerhalten(AkteurVerhalten):
    charakter = Charakter()
    state = States.ALIVE

    def __init__(self, charakter):
        self.charakter = charakter


def berechneAktion():
   return Aktion()
