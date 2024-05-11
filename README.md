
# Learning Agent

Questo è un piccolo programma d'esempio per mostrare come interfacciarsi ad un modello weka, e mostrare il miglioramento graduale di un certo modello che guarda sempre più istanze. 

# Utilizzo

Compila con `make main` ed esegui con `make run`.

# Note

Appena viene chiesto il nome del problema con cui si vuole lavorare, è possibile sceglierne uno dalla cartella `dataset` rispondendo al programma: `dataset/<nome_problema>`.

`<nome_problema>` può essere inserito anche senza specificarne l'estensione, ma questa deve comunque essere `.arff`.
Questo significa che se si vuole lavorare, ad esempio, con il problema `dataset/iris.arff.orig`, occorre prima copiare in file e poi rinominare la copia in `iris.arff`.