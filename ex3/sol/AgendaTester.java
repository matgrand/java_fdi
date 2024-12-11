// nome e cognome del candidato, matricola, data, numero della postazione

/*
    Commenti alla proposta di soluzione

    La difficolta` principale di questo compito sta nel fatto che bisogna
    capire un nuovo ADT (la coda di priorita`) e fornirne una realizzazione
      - la soluzione qui proposta usa un array riempito solo in parte, in cui
        gli elementi vengono mantenuti ordinati secondo la loro priorita`
      - si e` scelto di tenere le chiavi piu` alte (cioe` gli elementi con
        priorita` piu` bassa) all'inizio dell'array. In questo modo il metodo
        removeMin, che rimuove l'elemento di priorita` massima, deve agire
        "in cima" all'array ed avra` di conseguenza prestazioni O(1)
      - se avessimo mantenuto ordinato l'array in senso opposto il metodo
        removeMin avrebbe avuto prestazioni O(n) perche` si sarebbe dovuto
        ricompattare l'array dopo la rimozione del primo elemento
      - il metodo insert e` comunque O(n) perche` bisogna usare insertionSort
        per mantenere ordinato l'array, qualunque sia il senso dell'ordinamento
    Si noti che si sarebbe potuta fornire una realizzazione ancora migliore
    per la classe Agenda
      - una tabella, ovvero un array di 4 elementi i cui indici sono associati
        ai 4 livelli di priorita`
      - ogni elemento di questo array e` a sua volta un array di impegni, tutti
        di uguale priorita`
      - con questa realizzazione anche l'inserimento diventa O(1)
    La classe di collaudo non contiene particolari insidie
      - osservare la realizzazione di un menu a scelta multipla tramite il 
        costrutto del "ciclo-e-mezzo"
      - osservare come viene realizzato un inserimento: bisogna scandire la
        riga inserita da standard input per riconoscere la priorita` ed il
        messaggio, ovvero i campi key e value della coppia da inserire
      - la classe di collaudo andrebbe migliorata aggiungendo la gestione delle
        eccezioni: fatto
*/

//istruzioni di import
import java.io.*;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class AgendaTester {   
    public static void main(String[] args) throws IOException {
        Agenda a = new Agenda();
        Scanner in = new Scanner(System.in); //leggo input standard
        
        //ciclo per gestire input standard
        boolean done = false;
        while (!done) {
            //stampa per spiegare i possibili comandi
            System.out.println("Comando? I=inserisci,R=rimuovi,L=leggi,Q=quit");
            
            String cmd = in.nextLine(); //leggo riga per poter interpretare possibile comando
            
            //ignoriamo maiuscola/minuscola
            if (cmd.equalsIgnoreCase("Q")) {
                System.out.println("Ciao"); //se leggo "Q", esco
                done = true;
            }
            else {
                if (cmd.equalsIgnoreCase("I")) {
                    //se leggo "I", provo ad inserire il nuovo impegno
                    System.out.println("Inserisci impegno");
                    
                    Scanner line = new Scanner(in.nextLine());
                    int key = 0;
                    
                    //provo a leggere priorità
                    try {
                        key = Integer.parseInt(line.next());
                        
                        String value = "";
                        while (line.hasNext()) {
                            value = value + line.next() + " ";
                        }
                        
                        a.insert(key, value); //chiamo metodo di inserimento ordinato
                    }
                    catch(NumberFormatException e) {
                        System.out.println("Formato priorità non valido");
                    }
                    catch(IllegalArgumentException e) {
                        System.out.println("Formato memo non valido");
                    }
                    catch(NoSuchElementException e) {
                        System.out.println("Non è stato inserito alcun impegno");
                    }
                }
                else if (cmd.equalsIgnoreCase("R")) {
                    //se leggo "R", rimuovo impegno con priorità maggiore
                    try {
                        String value = (String)a.removeMin();
                        System.out.println("Rimosso impegno piu` urgente: " + value);
                    }
                    catch(EmptyQueueException e) {
                        System.out.println("Impossibile rimuovere impegno: la coda è vuota!");
                    }
                    
                }
                else if (cmd.equalsIgnoreCase("L")) {
                    //se leggo "L", stampo qual è l'impegno con priorità maggiore
                    try {
                        String value = (String)a.getMin();
                        System.out.println("L'impegno piu` urgente e`: " + value);    
                    }
                    catch(EmptyQueueException e) {
                        System.out.println("Impossibile leggere impegno: la coda è vuota!");
                    }
                }
                else {
                    //gestione del caso di comando non valido
                    System.out.println("Comando non valido, prego inserire nuovo comando.");
                }
                
                //stampo per cortesia contenuto dell'agenda ad ogni giro
                System.out.println("Contenuto dell'agenda:");
                System.out.println(a);
            }
        }
        //chiudo lo scanner
        in.close();
    }
}

class Agenda implements PriorityQueue {
    //costruttore
    public Agenda() {
        v = new Impegno[INITSIZE];
        makeEmpty();
    }
    
    //metodo che ritorna se l'agenda è vuota o meno
    public boolean isEmpty() {
        return vSize == 0;
    }
  
    //medoto che svuota l'agenda
    public void makeEmpty() {
        vSize = 0;
    }
    
    //metodo che inserisce impegno in agenda in maniera ordinata
    public void insert (int key, Object value) {
        //precondizioni: controllo che 1) value sia una stringa
        if (!(value instanceof String)) {
            throw new IllegalArgumentException();
        }
        
        //definiamo subito un oggetto di tipo impegno, in modo da controllare se ci sono problemi prima di iniziare la procedura di inserimento
        Impegno newImp = new Impegno(key, (String)value);
        
        //prima di inserire, controllo se array è pieno e se serve lo ridimensiono
        if (vSize == v.length) {
            v = resize(2*v.length); //decido di raddoppiare la dimensione dell'array
        }
        
        //insertionSort: O(n), perche` inseriamo in un array ordinato
        //attenzione: l'array e` ordinato con le chiavi piu` alte all'inizio e
        //quelle piu` basse alla fine: cosi` removeMin e` O(1)
        int i = vSize;
        while ((i > 0) && (v[i-1].getPriority() <= key)) {
            v[i] = v[i-1];
            i--;
        }
        
        v[i] =  newImp;//inserisco il nuovo impegno nella posizione corretta
        vSize++; //incremento variabile che tiene conto del riempimento dell'array
    }
    
    //medoto ausiliario per ridimensionare array
    private Impegno[] resize(int newLength) { //metodo ausiliario (privato)
        //gestisco il caso in cui per errore andrei a rimpicciolire l'array
        if (newLength < v.length) {
            throw new IllegalArgumentException();
        }
        Impegno[] newv = new Impegno[newLength];
        System.arraycopy(v, 0, newv, 0, v.length);
        return newv;
    }
    
    //metodo che rimuove l'impegno di priorità più elevata
    //è sufficiente rimuovere l'ultimo elemento dell'array in quanto esso è ordinato
    public Object removeMin() throws EmptyQueueException {
        //gestione del caso in cui array è vuoto
        if (isEmpty()) {
            throw new EmptyQueueException();
        }
        //decremento dimensione e ritorno il memo dell'impegno rimosso
        vSize--;
        return v[vSize].getMemo();  //N.B. accedo alla componente vSize in quanto ho appena decrementato la variabile   
    }
    
    //metodo che permette di leggere l'impegno con priorità più elevata
    //basta leggere l'ultimo impegno dell'array in quanto esso è ordinato
    public Object getMin() throws EmptyQueueException {
        //gestione del caso in cui l'array è vuoto
        if (isEmpty()) {
            throw new EmptyQueueException();
        }
        return v[vSize-1].getMemo();
    }
    
    //metodo che stampa il contenuto dell'agenda, sfruttando il metodo toString della classe interna Impegno
    public String toString() {
        String s = "";
        for (int i = vSize-1; i >= 0; i-- ) {
           s = s + v[i] + "\n";
        }
        return s;
    }
    
    //campi di esemplare di Agenda
    private Impegno[] v;                   //array di impegni
    private int vSize;                     //dimensione di riempimento dell'array
    private final static int INITSIZE = 1; //dimensione iniziale array
    
    /*
        classe privata Impegno: rappresenta gli elementi della classe Agenda ed
        e` costituita da coppie "chiave valore" in cui il campo chiave e` di
        int e rappresenta la priorita` dell'impegno e il campo valore e` una
        stringa contenente i dettagli dell'impegno. Si considerano 4 livelli di
        priorita`, numerati da 0 a 3. Conseguentemente il campo chiave puo`
        assumere valori solo in questo intervallo, dove il valore 0 significa 
        "massima priorita`" e il valore 3 significa "minima priorita`" 
    */
    private class Impegno { //non modificare!!
        //costruttore
        public Impegno(int priority, String memo) {
            //gestione di priorità invalida
            if ((priority > 3) || (priority < 0)) {
                throw new IllegalArgumentException();
            }
            //gestione memo vuoto non valido
            if (memo.length() <= 0) {
                throw new IllegalArgumentException();
            }
            this.priority = priority;
            this.memo = memo;
        }
        //getter per la priorità
        public int getPriority() {
            return priority;
        }
        //getter per il memo
        public Object getMemo() {
            return memo;
        } 
        //medoto toString
        public String toString() {
            return priority + " " + memo;
        }
        //campi di esemplare della classe Impegno
        private int priority; //priorita` dell'impegno (da 0 a 3)
        private String memo;  //promemoria dell'impegno
    }
}

/*  
    Interfaccia PriorityQueue (non modificare!!). 
    - Questo tipo di dato astratto definisce un contenitore di coppie 
      "chiave valore", dove il campo chiave e` un numero in formato int che 
      specifica il livello di priorita` della coppia mentre il campo valore 
      rappresenta il dato della coppia. 
    - Si assume che date due chiavi k1 e k2 tali che k1 < k2, allora k1 ha 
      priorita` *piu` alta* di k2.
    - Naturalmente possono essere presenti nel contenitore coppie diverse con 
      chiavi uguali, cioe` con uguale priorita`
*/
interface PriorityQueue //non modificare!!
{   /*
        svuota la coda di priorita`
    */
    void makeEmpty();
    
    /*
        restituisce true se la coda e' vuota, false altrimenti
    */
    boolean isEmpty();
    
    /*
        Metodo di inserimento
        Inserisce la coppia "chiave valore" nella coda di priorita`. Notare che 
        la coda di priorita` puo` contenere piu` coppie con la stessa chiave. In
        questo contesto il campo chiave non serve ad identificare univocamente
        un elemento (come nel caso di un dizionario), ma serve invece a definire
        la priorita` di un elemento. E` ovvio che piu` elementi possono avere 
        la stessa priorita`. 
    */
    void insert (int key, Object value);
    
    /*
        Metodo di rimozione
        Rimuove dalla coda la coppia con chiave minima, e restituisce un 
        riferimento al suo campo value. Se sono presenti piu` coppie con chiave
        minima, effettua la rimozione di una qualsiasi delle coppie con chiave 
        minima (ad esempio la prima coppia con chiave minima che viene trovata)
        Lancia EmptyQueueException se la coda di priorita` e` vuota
    */
    Object removeMin() throws EmptyQueueException;
    
    /*
        Metodo di ispezione
        Restituisce un riferimento al campo value della coppia con chiave minima
        (ma *non* rimuove la coppia dalla coda).  Se sono presenti piu` coppie 
        con chiave minima, restituisce il campo value di una qualsiasi delle   
        coppie con chiave minima (ad esempio la prima coppia con chiave minima 
        che viene trovata). Lancia EmptyQueueException se la coda e' vuota
    */
    Object getMin() throws EmptyQueueException;
}

/*
    Eccezione lanciata dai metodi removeMin e getMin di PriorityQueue quando
    la coda di priorita` e` vuota 
*/
class EmptyQueueException extends RuntimeException {}
