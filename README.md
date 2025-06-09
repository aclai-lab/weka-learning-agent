
# Learning Agent

Questo è un piccolo programma d'esempio per mostrare come interfacciarsi ad un modello weka, e mostrare il miglioramento graduale di un certo modello che guarda sempre più istanze.

## Installazione

Per installare il programma è necessario avere installato `make` e `java` (la versione raccomandata di java è la `17` ma funziona correttamente anche l'ultima stabile, la `21`). Scaricare weka eseguendo lo script `install.sh` (on linux e mac) e lo script `install.bat` su windows. _You are good to go_!

> Su sistemi linux e mac è possibile anche utilizzare solo il comando `make install` per installare tutto.

## Utilizzo

Compila con `make main` ed esegui con `make run`. È possibile avviare l'interfaccia grafica di weka con `make start-weka`.

## Utilizzare i problemi esistenti

Per utilizzare uno dei problemi già esistenti, è necessario copiare il file `.arff.orig` dalla cartella `dataset` e rinominarlo in `<nome_problema>.arff` nella root del progetto.

## Note

Appena viene chiesto il nome del problema con cui si vuole lavorare, è possibile sceglierne uno dalla cartella `dataset` rispondendo al programma: `dataset/<nome_problema>`.

`<nome_problema>` può essere inserito anche senza specificarne l'estensione, ma questa deve comunque essere `.arff`.
Questo significa che se si vuole lavorare, ad esempio, con il problema `dataset/iris.arff.orig`, occorre prima copiare in file e poi rinominare la copia in `iris.arff`.

## Descrizione dei dataset

Nella cartella `datasets` sono presenti alcuni esempi di dataset utili per prendere dimestichezza con questo software. Per utilizzarne uno basta spostarlo nella root del progetto e rimuovere l'estensione `.orig` dal nome del file. Ad esempio, se volessi utilizzare il dataset `iris`, devo copiare il file `iris.arff.orig` dalla cartella `datasets` alla cartella in cui si trova il file `Makefile` e poi rinominarlo `iris.arff`; se adesso chiedessi all'agente di imparare il problema `iris`, l'agente si renderebbe conto che il problema è già in memoria e caricherà le istanze dal dataset originale.

- **Pen**: traccia temporale delle coordinate della punta di una penna – obiettivo: riconoscere la cifra disegnata;
- **Zoo**: informazioni su diversi animali – obiettivo: classificare l'animale nella sua classe (mammifero, rettile, ecc.);
- **Iris**: misurazioni di parti dei fiori (sepali e petali) – obiettivo: determinare la specie di iris;
- **Tic-Tac-Toe**: configurazione della griglia di gioco – obiettivo: stabilire se il giocatore X ha vinto;
- **Titanic**: dati anagrafici e di viaggio dei passeggeri del Titanic – obiettivo: prevedere se il passeggero è sopravvissuto;
- **Car**: caratteristiche tecniche e qualitative di un’auto – obiettivo: valutarne il livello di desiderabilità;
- **Mushroom**: descrizione delle caratteristiche di vari funghi – obiettivo: prevedere se sono velenosi o commestibili;
- **Personality**: descrizione delle abitudini di una persona - obiettivo: determinare se la persona e Introversa o Estroversa.
