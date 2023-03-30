import sys, json

path = sys.argv[1]
with open(path, "r") as infile:
    monster = json.loads(infile.read())
if monster['lebenspunkte'] > 10:
    print("stark")
else:
    print("schwach")