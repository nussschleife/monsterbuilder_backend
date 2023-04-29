import sys
sys.path.append("java")
from de.hsrm.mi.ssche003.monsterbuilder.akteur.dto import MonsterDTO

monster = MonsterDTO()

def calcAction():
    if(monster.lebenspunkte < 11):
        monster.name = 'dash'
    monster.name = 'run'
    return monster