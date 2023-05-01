import random
from de.hsrm.mi.ssche003.monsterbuilder.akteur.monster import Monster
from de.hsrm.mi.ssche003.monsterbuilder.akteur.charakter import Charakter

class States:
   DEAD = 1
   ALIVE = 2
   WOUNDED = 3
   SCARED = 4
   PRONE = 5

class AkteurVerhalten(object):
    def initiative():
       raise Exception('not implemented') 
    
    def reaktion():
       raise Exception('not implemented') 
       
    def aktion():
       raise Exception('not implemented') 
      
    def bewegen():
       raise Exception('not implemented') 
    
class MonsterVerhalten(AkteurVerhalten):

    state = States.ALIVE
    monster = Monster()

    def __init__(self, monster):
        self.monster = monster

    def initiative():
       return int(random()*20)
    
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
