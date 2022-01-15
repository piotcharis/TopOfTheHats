**Top of the Hats**

Nach dem großen Erfolg von Pingu-Schach hat Carol sofort mit der Entwicklung eines neuen Spiels angefangen: Top of the Hats
Dabei versuchen mehrere "Wölfe" und Hamster in begrenzter Zeit möglichst lange eine Weihnachtsmütze zu tragen. Dafür können die Figuren sich gegenseitig die Hüte wegnehmen. Die Spielfiguren bewegen sich kästchenweise auf einem Spielfeld, wobei eine Figur mehrere Spielfelder einnehmen kann. Ein anderer Pinguin hat schon mal mit der Implementierung angefangen, und während er an der graphischen Oberfläche arbeitet, sollst du die Spiellogik implementieren.

**Allgemeine Hinweise / Tipps**

- Alle Aufgabenteile sollen in pgdp.game bearbeitet werden.
- Alle privaten Attribute bekommen Getter und Setter.
- Getter für boolean Werte bekommen den Prefix is statt get.
- Die meisten IDEs können Getter und Setter automatisch generieren
- Viele der benutzten Klassen aus der Standardbibliothek haben praktische Methoden die Konzepte aus den P Aufgaben dieser Woche nutzen. Ihre Nutzung ist aber optional.
- Schon vorgegebene Klassen (außer Entity) werden zum Testen mit der Version aus dem Template überschrieben und sollten nicht verändert werden.
- Es dürfen keine public Attribute hinzugefügt werden.

**Die Entities**

Die Spiellogik wird mit der folgenden Klassenhierarchie modelliert. Alle Entities erben dabei von der Entity Klasse. Es gibt insgesamt 4 Entities, für die 5 Klassen implementiert werden müssen. Außerdem wird eine Game-Klasse hinzugefügt, die die Daten des Spiels enthält und eine Controls-Interface, die die Bewegung der Figuren speichert.

Füge erst mal nur die Methodenköpfe hinzu, bevor du mit der Implementierung der Methoden beginnst.

Im Folgenden nicht aufgeführte Methoden sind abstrakt. Die Größe der Hit Box ist am Ende der Aufgabenstellung zu finden.
Dabei helfen die öffentlichen Methoden von CollisionBoard, es ist also empfehlenswert, sich mit ihnen vertraut zu machen.

**Game**

Das Game enthält alle Entities des Spiels und ist zuständig für das High Level verhalten des Spiels: Entities entfernen, hinzufügen und jeden Tick den Zustand aller Entities aktualisieren.

- Game(CollisionBoard): Initialisiert die Listen jeweils mit einer leeren Liste und setzt collisionBoard passend.

- runTick(): Auf jedem Entity in entities wird visit() aufgerufen. Wenn false zurückgegeben wird, soll dieses Entity aus der Liste und dem CollisionBoard gelöscht werden. Danach sollen alle Entities in entityAdd zum CollisionBoard hinzugefügt werden. Wenn das für dieses Entity erfolgreich war, sollen sie zu entities hinzugefügt werden und schließlich immer entityAdd geleert werden.

**Entity**

Die Wurzel unserer Klassenhierarchie. Hier wird das von dem Game genutzte Interface definiert.

- Entity(LocatedBoundingBox): Initialisiert hitBox. Der Bounding-Box Anteil ist für jedes Entity in der Tabelle am Ende der Aufgabenstellung zu finden.

**Hat**

- Hat(Position): Erstellt einen neuen Hat. dontRemove soll auf true gesetzt werden.

- visit(Game): Gibt dontRemove zurück.

**Cookie**

- Cookie(Position, Direction): Erstellt einen neuen Cookie und setzt direction.

- visit(Game): Wenn der Cookie bei einer Bewegung in direction mit entities oder dem Spielfeldrand kollidieren würde, wird am Ende der Methode false zurückgegeben. Außerdem wird bei allen Figuren, mit denen der Cookie kollidieren würde, ihr disabledCooldown auf 60 gesetzt und wenn sie einen Hut haben, wird dieser weggenommen und ein neuer Hut mit der Position der Figur zu entityAdd hinzugefügt. Wenn keine Kollision auftreten würde, wird der Cookie mit der entsprechenden Methode aus CollisionBoard bewegt. Falls die eigene hitBox leer ist, soll auch false zurückgegeben werden.

**Figure**

Figure ist die Oberklasse der Spielbaren Entities. Sie enthält eine Controls Instanz, die über Bewegungsrichtung und ob angegriffen werden soll, entscheidet. Außerdem wird hier über cooldown counter entschieden, ob eine Figur in diesem Tick angreifen und/oder sich bewegen kann. Die Entscheidung, ob und wie sie dann handelt, wird in Implementierungen von Controls ausgelagert.

- Figure(LocatedBoundingBox): Initialisiert die entsprechenden Attribute. moveCooldown wird mit dem Rückgabewert von getFullMoveCooldown initialisiert, attackCooldown analog dazu. hasHat wird mit false, lastDirection mit UP und disabledCooldown mit 0 initialisiert. controls muss nicht initialisiert werden.

- moveTo(Game, Direction): Setzt lastDirection. Für jeden Hat wird, wenn man bei einer Bewegung in diese Richtung mit ihm kollidiert werden würde und zu diesem Zeitpunkt hasHat false ist, hasHat auf true gesetzt und das Entity von dem CollisionBoard entfernt sowie dontRemove des Hats auf false gesetzt. Bei mehreren Hüten kann hasHat also nur beim Ersten false sein. Zum Schluss wird die Figur bewegt.

- visit(Game):
  1. Wenn disabledCooldown > 0 ist, wird es dekrementiert.
  2. wenn es danach immer noch > 0 ist, wird sofort true zurückgegeben.
  3. moveCooldown wird dekrementiert.
  4. wenn es 0 ist, wird es wieder auf getFullMoveCooldown zurückgesetzt und auf controls move aufgerufen. Dabei kann davon ausgegangen werden, das controls nicht null ist.
  5. Danach wird attackCooldown dekrementiert, wenn es > 0 ist.
  6. wenn attackCooldown 0 ist, wird auf controls attack aufgerufen. Wenn dabei true zurückgegeben wird, wird attackCooldown auf getFullAttackCooldown zurückgesetzt und attack aufgerufen.
  7. Zum Schluss wird immer true zurückgegeben.

**WolfPingu**

- WolfPingu(Position): Erstellt einen neuen WolfPingu.

- attack(Game): Jede Figur, mit dem bei einer Bewegung in Richtung lastDirection kollidiert werden würde,
  1. bekommt einen disabledCooldown von 60 und
  2. wenn es einen Hut hat und man selbst nicht, wird hasHat des anderen auf false und das eigene auf true gesetzt.

- getFullAttackCooldown(): Gibt 120 zurück.

- getFullMoveCooldown(): Gibt 10 zurück.

**Hamster**

- Hamster(Position): Erstellt einen neuen Hamster.

- attack(Game):

  1. Jede Figur, mit dem bei einer Bewegung in Richtung lastDirection kollidiert werden würde, bekommt einen disabledCooldown von 60. Wenn die Figur einen Hut hat und man selbst nicht, wird hasHat des anderen auf false und das eigene auf true gesetzt.
  1. Wenn keine solche Figur existiert, wird in lastDirection ein Cookie geworfen. Dafür wird ein Cookie mit Direction lastDirection an entsprechender Position (siehe Grafik) zu entityAdd hinzugefüg.

- getFullAttackCooldown(): Gibt 140 zurück.

- getFullMoveCooldown(): Gibt 15 zurück.

**Steuerung**

**PlayerControls**

Instanzen dieser Klasse erlauben dem Spieler, die entsprechende Figur zu steuern.

Das Steuern der Figuren wird von Implementierungen des Controls Interfaces übernommen. Sie entscheiden, wie sich die Figur verhalten soll.

- Nutzt die pgdp.game.helper.PlayerCtl Klasse, um Inputs des Spielers zu lesen und die Figur entsprechend zu bewegen. Eingaben sollen kombiniert werden, z.B. wird aus rechts und oben rechts-oben. Rechts, Oben und Links sollen in Kombination nur Oben ergeben.

- attack(Game, Figure) gibt den entsprechenden Wert aus PlayerCtl zurück. Wird nur aufgerufen, wenn man auch angreifen kann.

**AIControls**

Diese Oberklasse implementiert gemeinsame Funktionalität für die konkreten Steuerungen der NPC Gegner.

- selectTarget(game): Überprüft ob das Entity in target null ist oder keine hitBox hat oder, wenn es ein Figure ist, hasHat false ist. Wenn ja:
  1. Sucht in der Liste der Entities nach allen Hats oder Figures mit hitBox. Figures müssen zusätzlich hasHat auf true haben.
  2. Wählt zufällig mit pgdp.game.helper.Random eines der Entities aus.
  3. Speichert es in target.
 target wird zurückgegeben
 
- selectDirection(Entity other, Figure self): Wenn die Entities oder genauer deren Position auf einer Linie parallel zu den Koordinatenachsen zueinander liegen, soll die Richtung auf den Gegner zu zurückgegeben werden. Ansonsten soll die diagonale Richtung, die am genauesten in Richtung des Ziels zu geht. Wenn die Position der beiden übereinstimmt oder eines keine Position hat, soll UP zurückgegeben werden.

- move(): Wenn die Figur selbst keinen Hut hat, soll sie sich auf das mit selectTarget(Game) spezifizierte Entity zubewegen. ansonsten wird zufällig eine der 8 Richtungen ausgewählt.

**HamsterAI**

- attack(Game, Figure): Hamster greifen immer an, sobald es möglich ist.

**WolfPinguAI**

- attack(Game, Figure): WolfPingus greifen nur an, wenn eine Figur bei einem Angriff getroffen werden würde.

**Rendern**

Im Template findest du das folgende Enum:

**getImage**

Füge in Entity die folgende abstrakte Methode ein: public Image getImage()

Sie soll in den Leaf-Unterklassen implementiert werden und die entsprechende Konstante zurückgegeben.

**Tests**

Schreibe zu 3 der geforderten Methoden Tests, die je 3 verschiedene Fälle abdecken und schreibe in einem Kommentar dazu, welcher Fall gerade abgedeckt wird. Wenn du mehr als die geforderten Fälle abgibst, muss gut erkennbar sein, welche Tests für die Bewertung genutzt werden sollen. Alle Tests müssen in dem Package pgdp.game.testing stehen und von Artemis erfolgreich ausgeführt werden. Die Punkte werden erst in der manuellen Korrektur vergeben.

**Hit Box Tabelle**

Name | Hit Box width | Hit Box height

Cookie	1	1

Hat	2	1

Hamster	3	3

WolfPingu	3	3

**Ausführen**

Im Template ist schon eine Main-Klasse enthalten. Mit ihr kannst du das fertige Spiel testen. Die Steuerung ist WASD für Bewegungen und Space für Angriffe.
"It just works" - Todd Howard
