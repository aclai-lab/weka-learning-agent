
# Learning Agent

Questo è un piccolo programma d'esempio per mostrare come interfacciarsi ad un modello weka, e mostrare il miglioramento graduale di un certo modello che guarda sempre più istanze.

## Installazione

Per installare il programma è necessario avere installato `make` e `java` (la versione raccomandata di java è la `17` ma funziona correttamente anche l'ultima stabile, la `21`). Scaricare weka eseguendo lo script `install.sh` (on linux e mac) e lo script `install.bat` su windows. _You are good to go_!

## Utilizzo

Compila con `make main` ed esegui con `make run`.

## Utilizzare i problemi esistenti

Per utilizzare uno dei problemi già esistenti, è necessario copiare il file `.arff.orig` dalla cartella `dataset` e rinominarlo in `<nome_problema>.arff` nella root del progetto.

## Note

Appena viene chiesto il nome del problema con cui si vuole lavorare, è possibile sceglierne uno dalla cartella `dataset` rispondendo al programma: `dataset/<nome_problema>`.

`<nome_problema>` può essere inserito anche senza specificarne l'estensione, ma questa deve comunque essere `.arff`.
Questo significa che se si vuole lavorare, ad esempio, con il problema `dataset/iris.arff.orig`, occorre prima copiare in file e poi rinominare la copia in `iris.arff`.