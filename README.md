## UntitledManiaGame
**UntitledManiaGame** è un gioco basato sulla fusione del genere Rythm e RPG.

In breve, lo scopo del gioco è colpire note a ritmo di musica (fa male al nemico!) e acquistare effetti che cambiano le regole di gioco.

Vedere la Wiki per spiegazioni (molto) più approfondite.

### Esecuzione

E' necessario almeno JDK 24 | Java 25.0.4.1.

`gradlew build` seguito da `gradlew run`.

`gradlew test` è utilizzabile per eseguire i test.

**Nota**: Se il nome utente di Windows contiene caratteri speciali, è necessario specificare una home di `gradlew` personalizzata (in modo che il percorso non contenga caratteri speciali).

Questo è stato scoperto a mie spese, ed è il comando utilizzato in locale.

### Attribuzioni

Molta ispirazione è stata tratta dalle seguenti repository: [osu!(lazer)](https://www.github.com/ppy/osu) e [osu-framework](https://www.github.com/ppy/osu-framework).

Asset provengono da [osu-resources](https://www.github.com/ppy/osu-resources).

Ogni utilizzo è in linea con le licenze (MIT o CC-BY-NC).

### TODO

Questo progetto è in uno stato di proof-of-concept.

#### Sviluppi futuri necessari

- [ ] Randomizzazione automatica di quale chart viene selezionato in un album.
- [ ] Implementazione degli "shop" durante le pause.
- [ ] Implementazione degli effetti acquistabili.
- [ ] Implementazione di nemici.
- [ ] Implementazione di un sistema di font basato su sprite.
- [ ] Estensione del sistema di `Transform` (e.g. per supportare ancoraggio nei container).
- [ ] Implementazione di UI reattiva (e.g. barre della salute e combo, correntemente visibili solo nella console).
