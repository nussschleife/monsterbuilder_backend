import sys, json

path = sys.argv[1]

with open(path, 'r') as file:
  aktionsnummern = json.load(file)

print(aktionsnummern)