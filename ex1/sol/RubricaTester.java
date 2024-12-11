// nome e cognome del candidato, matricola, data, numero della postazione

/*
    Commenti alla proposta di soluzione
    
    La realizzazione della classe Rubrica non dovrebbe richiede eccessivo
    impegno: e` la mera riscrittura di un dizionario con array ordinato, che
    gestisce particolari coppie Pair (chiave di tipo String e valore long). 
      - Notare che il testo richiede esplicitamente che il metodo find sia 
        O(log n), quindi *bisogna* saper realizzare l'algoritmo di ricerca
        binaria su un array ordinato.
      - Inoltre *bisogna* saper realizzare almeno un algoritmo di ordinamento
        su array, ed utilizzarlo all'interno del metodo insert in modo da
        mantenere ordinato l'array ad ogni inserimento.
      - La soluzione proposta utilizza insertionSort: e` la scelta migliore dal
        punto di vista delle prestazioni, tuttavia sarebbe stato accettabile
        anche l'uso di selectionSort o mergeSort dal momento che il testo non
        fa richieste sulle prestazioni di insert.
        
    La classe di collaudo richiede qualche attenzione
      - Inserimento dati da file
        . Bisogna interpretare correttamente le righe, usando i ":" come
          carattere di separazione tra la chiave (nome) e il valore (numero
          telefonico) della coppia.
        . La soluzione proposta consente di interpretare correttamente righe in
          cui i nomi sono composti da piu` parole, ad esempio:
            Zio Paperone : 4683457923
        . Nella fase di scansione delle righe del file sono state gestite le
          eccezioni di tipo NoSuchElementException e NumberFormatException.
      - Lettura/scrittura di file
        . Ricordiamoci che le eccezioni di tipo IO sono a gestione obbligatoria
        . Si sono usati costrutti try/catch per catturare le eccezioni 
          lanciabili dai costruttori di FileReader e PrintWriter.
        . Se non ci si sente sicuri su questo aspetto, si puo` evitare di
          gestire queste eccezioni dichiarando che il metodo main le lancia:
                public static void main(String[] args) throws IOException
        . Infine bisogna ricordarsi di "chiudere" i file, usando i metodi close.
          Se in particolare ci dimentichiamo di chiudere il file file2, aperto
          in scrittura, il file rischia di non venire scritto!
      - Ricerca/cancellazione di un nome nella prima rubrica, e inserimento
        nella seconda
        . Poiche' il testo specifica che il numero di ricerche non e`
          prefissato, bisogna realizzare un "ciclo-e-mezzo" che consente di
          ripetere l'operazione fino all'inserimento del carattere "Q"
        . Attenzione a come viene realizzato lo spostamento di una coppia dalla
          prima rubrica alla seconda: *prima* si scrive la coppia nella seconda
          rubrica, *poi* la si cancella dalla prima. Se facessimo il contrario
          la coppia andrebbe perduta!
        . L'eventualita` che un nome cercato non sia presente nella prima
          rubrica viene gestita catturando DictionaryItemNotFoundException, che
          puo` essere lanciata da find e da remove
*/

import java.io.*;
import java.util.Scanner;
import java.util.NoSuchElementException; //Attenzione: questa eccezione va importata per poter essere usata

public class RubricaTester
{
    public static void main(String[] args) {
        //controllo parametri del metodo main
        if ((args.length != 2) || (args[0].equals(args[1]))) {
            System.out.println("Uso: $java RubricaTester filename1 filename2");
            System.out.println("Non usare stesso nome file in lettura/scrittura");
            System.exit(1);
        }
        
        String filename1 = args[0];
        String filename2 = args[1];
        
        //apertura di file1 in lettura
        Scanner file1 = null;
        try {
            file1 = new Scanner(new FileReader(filename1));
        }
        catch(FileNotFoundException e) {
            System.out.println("Problema in apertura File1! Termino");
            System.exit(1);
        }
        
        //creazione e  scrittura di rubrica1
        Rubrica rubrica1 = new Rubrica();
        
        //leggo file1 riga per riga
        while (file1.hasNextLine()) {
            String line = file1.nextLine();
            Scanner linescan = new Scanner(line);
            try {
                String name = linescan.next(); //nome : campo "key" del dizionario
                String word; //stringa di appoggio per la costruzione del nome
                //considero anche nomi eventualmente composti da piu` parole
                while (!(word = linescan.next()).equals(":")) {
                    name += " " + word;
                }
                long phone = Long.parseLong(linescan.next()); //dopo i ":" ci deve essere un numero in formato long                    
                rubrica1.insert(name, phone); //inserisco in rubrica1
            }
            catch(NoSuchElementException e) {//NoSuchElementException puo` essere lanciata da next se non vengono trovati token
                System.out.println("Formato inserimento sbagliato");
            }     
            catch(NumberFormatException e) { //NumberFormatException puo` essere lanciata da parseLong se la stringa dopo i ":" non e` un intero
                System.out.println("Formato del numero telefonico sbagliato");
            }
            linescan.close(); //chiudo lo scanner per la riga
        }
        System.out.println("Rubrica1:\n" + rubrica1); //controllo il contenuto di rubrica1
        file1.close(); //chiudo il file, importante affiche' venga scritto
        //creazione di rubrica2, ricerca e rimozione dati da rubrica1 
        //inserimento in rubrica2 di dati rimossi da rubrica1
        Scanner in = new Scanner(System.in); //apertura standard input
        Rubrica rubrica2 = new Rubrica();
        boolean done = false;
        //ciclo finche' non verrà digitato "Q"
        while(!done) {
            System.out.println("Nome in Rubrica1 da spostare in Rubrica2?"); 
            System.out.println("(\"Q\" per terminare)"); 
            String name = in.nextLine();
            //controllo se è il momento di uscire
            if (name.equalsIgnoreCase("Q")) {
                done = true;
            }
            else {
                //cerco il nome da spostare, se presente lo inserisco in rubrica2 e poi lo cancello da rubrica1
                try {
                    rubrica2.insert(name, rubrica1.find(name)); //prima copio e
                    rubrica1.remove(name); //poi cancello. Non viceversa!
                } 
                catch(DictionaryItemNotFoundException e) {
                    System.out.println("Nome \"" + name + "\" non presente in Rubrica1" );
                }
                System.out.println("Rubrica1:\n" + rubrica1); //controllo i
                System.out.println("Rubrica2:\n" + rubrica2); //contenuti
            }
        }
        //chiudo lo scanner a System.in
        in.close();
        //apertura di file2 in scrittura, salvataggio di rubrica2 in file2
        PrintWriter file2 = null;
        try {
            file2 = new PrintWriter(filename2);
        }
        catch(FileNotFoundException e) {
            System.out.println("Problema in apertura File2! Termino");
            System.exit(1);
        }
        file2.print(rubrica2);
        file2.close();  //Se non chiudo rischio che file2 non venga scritto!!!
        System.out.println("Arrivederci");
    }
}

class Rubrica implements Dictionary {
    //costruttore
    public Rubrica() {
        v = new Pair[INITSIZE];
        makeEmpty();
    }
    
    //metodo che controlla se rubrica e' vuota
    public boolean isEmpty() {
        return vSize == 0;
    }
    
    //metodo che svuota la rubrica
    public void makeEmpty() {
        vSize = 0;
    }
    
    //metodo per inserire nuovi campi in rubrica
    //bisogna realizzare il comportamento richiesto nell'interfaccia Dictionary:
    //in particolare sovrascrivere coppie gia` presenti e lanciare eccezioni
    //La realizzazione qui proposta ha prestazioni O(n), perche` abbiamo usato
    //un algoritmo di ordinamento per inserimento
    public void insert(Comparable key, Object value) {
        //precondizioni: controllo che 1) key non sia null e 2) che value sia un numero long
        if ((key == null) || !(value instanceof Long)) {
            throw new IllegalArgumentException();
        }
        //se la coppia c'e` gia`, prima la rimuovo
        try {
            remove(key); 
        }
        catch(DictionaryItemNotFoundException e) {
            //altrimenti tutto ok!
        }
        //creo un nuovo Pair (attenzione ai cast)
        Pair newPair = new Pair((String)key, (Long)value);
        //uso array ridimensionabile!
        if(vSize == v.length) {
            resize();
        }
        //riordinamento per inserimento. Attenzione ai cast: v[i-1].getName() 
        //e` di tipo String, e puo` essere comparato solo a String
        //quindi è necessario fare un cast a String di key
        int i = vSize; // questo ciclo ha tempi di esecuzione O(n)
        while ((i > 0) && ((v[i-1].getName()).compareTo((String)key) > 0)) {
            v[i] = v[i-1];
            i--;
        }
        v[i] = newPair; //inserisco nuovo Pair nel punto giusto
        vSize++; // aggiorno la dimensione
    }
    
    //metodo ausiliario: lo rendo private, non deve essere usato da altri
    private void resize() { //niente parametri espliciti e valori restituiti: ho deciso che raddoppio sempre la dimensione.                     
        Pair[] newv = new Pair[2*v.length]; 
        System.arraycopy(v, 0, newv, 0, v.length);
        v = newv; //funziona: v non e` una var. locale ma un campo di esemplare 
    }
    
    //metodo per rimuovere elemento dalla rubrica
    public void remove(Comparable key) {   
        //uso binSearch per cercare la chiave nell'array non ordinato
        //se la chiave non c'e` lancio DictionaryItemNotFoundException come da
        //specifiche (viene lanciata da binSearch)
        int i = binSearch(0, vSize-1, key);
        //scalo indietro le componenti della rubrica a partire dall'indice trovato
        for (int j = i; j < vSize-1; j++) {
            v[j] = v[j+1];
        }
        vSize--;
    }
    
    //metodo per cercare elemento in rubrica tramite chiave
    public Object find(Comparable key) {   
        //uso binSearch per cercare la chiave nell'array non ordinato
        //se la chiave non c'e` lancio DictionaryItemNotFoundException come da
        //specifiche (viene lanciata da binSearch)
        return v[binSearch(0, vSize-1, key)].getPhone();
    }
    
    //metodo ausiliario (e quindi private): restituisce l'indice in cui ha trovato l'elemento
    private int binSearch(int from, int to, Comparable key) {
        //controllo che gli indici siano compatibili
        if (from > to) {
            throw new DictionaryItemNotFoundException(); //importante lanciarla, e' richiesto
        }
        
        int mid = (from + to) / 2; // circa in mezzo
        Comparable middlekey = v[mid].getName(); //ottengo chiave dell'elemento circa in mezzo
        
        //controllo se l'ho trovato, altrimenti procedo a cercare a destra o a sinistra in base a key
        if (middlekey.compareTo(key) == 0) {
            //In questo caso funzionerebbe anche if (middle.getKey().equals(key))
            //perche` le chiavi sono di tipo String, e il metodo equals e` stato
            //sovrascritto in String in modo da essere coerente con compareTo
            return mid; //elemento trovato
        }
        else if (middlekey.compareTo(key) < 0) {            
            return binSearch(mid + 1, to, key); //cerca a destra
        }
        else {
            return binSearch(from, mid - 1, key); //cerca a sinistra
        }
    }
    
    //metodo per convertire rubrica a stringa
    public String toString() {
        String s = "";
        for (int i = 0; i < vSize; i++) {
            s = s + v[i] + "\n"; //sfrutto il metodo toString di Pair!
        }
        return s;
    }
    
    //campi di esemplare e variabili statiche di Rubrica
    private Pair[] v; //array di elementi Pair
    private int vSize; //dimensione rubrica
    private final static int INITSIZE = 1; //dimensione iniziale
    
    //classe privata Pair: non modificare!!
    private class Pair {
        public Pair(String aName, long aPhone) {
            name= aName; 
            phone = aPhone;
        }
        
        public String getName() {
            return name;
        }
        
        public long getPhone() {
            return phone;
        }
        /*
            Restituisce una stringa contenente
            - la nome, "name"
            - un carattere di separazione ( : )
            - il numero telefonico, "phone"
        */
        
        public String toString() {
            return name + " : " + phone;
        }

        //campi di esemplare
        private String name;
        private long phone;
    }
}

interface Dictionary {
    /*
        verifica se il dizionario contiene almeno una coppia chiave/valore
    */
    boolean isEmpty();
    
    /*
        svuota il dizionario
    */
    void makeEmpty();
    
    /*
     Inserisce un elemento nel dizionario. L'inserimento va sempre a buon fine.
     Se la chiave non esiste la coppia key/value viene aggiunta al dizionario; 
     se la chiave esiste gia' il valore ad essa associato viene sovrascritto 
     con il nuovo valore; se key e` null viene lanciata IllegalArgumentException
    */
    void insert(Comparable key, Object value);
    
    /*
     Rimuove dal dizionario l'elemento specificato dalla chiave key
     Se la chiave non esiste viene lanciata DictionaryItemNotFoundException 
    */
    void remove(Comparable key);
    
    /*
     Cerca nel dizionario l'elemento specificato dalla chiave key
     La ricerca per chiave restituisce soltanto il valore ad essa associato
     Se la chiave non esiste viene lanciata DictionaryItemNotFoundException
    */
    Object find(Comparable key);
}

class DictionaryItemNotFoundException extends RuntimeException {}
