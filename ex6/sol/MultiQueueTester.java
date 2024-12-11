// nome e cognome del candidato, matricola, data, numero della postazione

/*
    Commenti alla proposta di soluzione

    La difficolta` principale del compito sta nella realizzazione di un nuovo
    tipo di dato astratto, la "multicoda". In realta` la realizzazione non e`
    complicata e non richiede molte righe di codice
      - Avendo a disposizione una struttura dati di tipo Coda, la realizzazione
        di una multicoda puo` essere ottenuta usando come campo di esemplare
        un array di code. Ogni elemento dell'array e` un riferimento di tipo
        Queue, che punta ad un oggetto di tipo ArrayQueue. Una volta capito 
        questo, la realizzazione dei metodi di MultiQueue e` abbastanza veloce.
      - Nel metodo add, l'unico accorgimento da realizzare e` che l'inserimento
        va fatto nella coda piu` corta. Per fare questo basta confrontare le
        code tramite i loro metodi size().
      - La realizzazione dei rimanenti metodi e` abbastanza immediata.
    La classe ArrayQueue e` identica alle realizzazioni di code tramite
    array viste a lezione. La classe di collaudo non contiene difficolta` ma
    richiede solo la realizzazione (un po' tediosa) di un ciclo-e-mezzo per
    gestire il menu di comandi.
    
*/


import java.util.Scanner;

public class MultiQueueTester
{   
	public static void main(String[] args)
    {
		//controllo che l'argomento passato sia uno, e che esso sia effettivamente un intero positivo
        if ((args.length != 1) || !( args[0].matches("[0-9]+")) || (Integer.parseInt(args[0]) <= 0))
        {   
			System.out.println("Uso: \"$MultiQueueTester N\", con N intero positivo");
            System.exit(1);
        } 

        int N = Integer.parseInt(args[0]); //appoggio argomento in variabile
        MultiQueue biglietteria = new ArrayMultiQueue(N); //creo un oggetto multicolda di dimensione N
        Scanner in = new Scanner(System.in);
		
		//ciclo per gestione comandi
        boolean done = false;
        while (!done)
        {   
			System.out.println("Comando? A=aggiungi,R=rimuovi,P=stampa,Q=quit");
            String cmd = in.nextLine(); //leggo riga per capire che comando è stato inserito
            
			//se "Q", allora esco
			if (cmd.equalsIgnoreCase("Q"))
            {
				System.out.println("La biglietteria chiude, arrivederci");
                done = true;
            }
            else
            {
				if (cmd.equalsIgnoreCase("A"))
                {   
					System.out.println("Nome persona da aggiungere?");
                    String persona = in.nextLine(); //leggo nome persona da aggiungere
					
					try
					{
						int queueNum = biglietteria.add(persona); //aggiungo persona
						System.out.println("Aggiunto " + persona + " a coda " + queueNum);
					}
					catch(IllegalArgumentException e)
					{
                      System.out.println("Il nome inserito non è valido!");
                    }         
                }
                else if (cmd.equalsIgnoreCase("R") )
                {   
					System.out.println("Numero coda da cui rimuovere?");
                    int i = 0;
					
                    try
					{
                        i = Integer.parseInt(in.nextLine());
                        String persona = (String) biglietteria.remove(i); //rimuovo da coda i-esima
                        System.out.println("Rimosso " + persona + " da coda " + i);
                    }
                    catch(EmptyQueueException e)
					{
                      System.out.println("La coda " + i + " e` vuota!");
                    }
                    catch(NumberFormatException e)
					{
                      System.out.println("Devi inserire un intero 0 <=i < " + N);
                    }
                    catch(IllegalArgumentException e)
					{
                      System.out.println("Devi inserire un intero 0 <= i < " + N);
                    }
                }
                else if (cmd.equalsIgnoreCase("P"))
                {   
					System.out.println("Situazione della biglietteria:");
                    System.out.println(biglietteria); //stampo coda per coda
                }
				else
                {   
					System.out.println("Il comando inserito non è valido!");
                }
            }
        }
    }
}


//-----------------------------------------------------------------------------

// Classe che implementa l'interfaccia Queue usando un array (array riempito
// solo in parte, oppure array riempito solo nella parte centrale, oppure
// array circolare), con o senza ridimensionamento. La classe sovrascrive 
// il metodo toString

class ArrayQueue implements Queue
{
	//costruttore
    public ArrayQueue()
    {   
		v = new Object[INITSIZE]; //inizializzo una coda di dimensione unitaria, vuota
        makeEmpty();
	}

	//metodo che svuota la coda
    public void makeEmpty()
    {
		front = back = 0;
	}

	//metodo che ritorna se la coda è vuota
    public boolean isEmpty()
    {
		return (back == front);
	}

	//metodo che ritorna la dimensione della coda
    public int size()
    {
		int retVal = 0;
		
		//devo distinguere il caso in cui ho "fatto il giro" riempiendo l'array
		if(back >= front)
		{
			retVal = back - front;
		}
		else
		{
			retVal = back - front + v.length;
		}
		
		return retVal;
	}

	//metodo che accoda un elemento in fondo
    public void enqueue(Object obj)
    {   
		//controllo se ho spazio per inserire un nuovo elemento
		if (increment(back) == front)
        {
			resize(); //ridimensiono l'array
        }

        v[back] = obj; //accordo nuovo elemento
        back = increment(back); //incremento back perchè ho inserito elemento
    }

	//metodo che rimuove primo elemento
    public Object dequeue()
    {   
		Object obj = getFront();
        front = increment(front);
        return obj;
    }

	//metodo che restituisce qual è il primo elemento
    public Object getFront()
    {  
		//controllo se coda è vuota
		if (isEmpty())
		{
			throw new EmptyQueueException();
		}
        return v[front];
	}


	//metodo toString
    public String toString()
    {
        String s = "";
		if (front <= back)
		{
			for (int i = front; i<back; i++)
			{
				s = s + v[i] + "\n";
			}
			s = s + "\b";
			
		}
		else
		{
			for (int i = front; i<v.length; i++)
			{
				s = s + v[i] + "\n";
			}
			
			for (int i = 0; i<back; i++)
			{
				s = s + v[i] + "\n";
			}
			
			s = s + "\b";		
		}
        
        return s;
    }

	//metodo ausiliario (quindi private) per incrementare puntatore di posizione
    private int increment(int index)
    {  
		return (index + 1) % v.length;
    }

	//metodo ausiliario (quindi private) per ridimensionare la coda
    private void resize() //niente parametri espliciti e valori restituiti: ho deciso che raddoppio sempre la dimensione.
    {   		
		//ridimensiono la coda
        Object[] newv = new Object[2*v.length];
        System.arraycopy(v, 0, newv, 0, v.length);			
		v = newv;
		
		//controllo di non essere nella situazione in cui avevo "fatto il giro" con il riempimento dell'array
		//in tal caso devo spostare le componenti iniziali in coda alle ultime occupate
		if(back < front)
		{
			System.arraycopy(v, 0, v, v.length/2, back); //sposto le componenti iniziali
			back = back + v.length/2; //aggiorno il valore di back in modo che punti all'ultima occupata
		}
    }

	//variabili di esemplare
    private Object[] v;
    private int front; //punta a prima componente libera, ovvero componente successiva ad ultimo elmento accodato
	private int back; //punta a prima componente occupata, ovvero elmento accodato per primo
    private static int INITSIZE = 1;
}


//-----------------------------------------------------------------------------

// Classe che implementa l'interfaccia MultiQueue usando un array di N code. 
// La classe sovrascrive il metodo toString
class ArrayMultiQueue implements MultiQueue
{
    //costruttore: crea una multicoda vuota, costituita da una sequenza di N>0 code vuote,
    public ArrayMultiQueue(int N)
    {
		//controllo che N sia un numero positivo
		if (N <= 0)
		{
			throw new IllegalArgumentException();
		}
		
        code = new Queue[N]; //creo nuovo array di code di dimensione N
		
		//inizializzo ogni coda della multicoda
        for (int i = 0; i < code.length; i++)
		{
            code[i] = new ArrayQueue();
		}
    }          
    
	//medoto che restituisce se la multicoda è vuota
    public boolean isEmpty()
    {   
		boolean retVal = true;
		
		//controllo ogni coda della multicoda: basta che una non sia vuota affichè la multicoda non sia vuota
		for (int i = 0; i < code.length; i++)
		{
            if (!code[i].isEmpty())
			{
				retVal = false;
			}
		}
		
        return retVal;
    }

	//metodo che svuota la multicoda
    public void makeEmpty()
    {
		//svuoto ogni coda della multicoda
		for (int i = 0; i < code.length; i++)
		{
            code[i].makeEmpty();
		}
    }

	//metodo che accoda un elemento alla multicoda, accodandolo alla coda con meno elmenti
    public int add(Object obj)
    {   
		//controllo che l'oggetto sia una stringa e che non sia vuota
		if (!(obj instanceof String) || (((String)obj).length() <= 0)) 
		{
			throw new IllegalArgumentException();
		}
		
		//cerco qual è la coda con meno elementi
        int min = 0;
        for (int i = 1; i < code.length; i++)
		{
            if (code[i].size() < code[min].size())
			{
                min = i;
			}
        }
		
		code[min].enqueue(obj); //accodo l'elemento
		
		return min;
    }

	//metodo che rimuove il primo elemento dalla i-esima coda
    public Object remove(int i)
    {   
		//controllo che la coda selezionata sia valida
		if ((i < 0) || (i >= code.length))
		{
			throw new IllegalArgumentException();
		}
        return code[i].dequeue();
    }
   
    //metodo toString
    public String toString()
    {   
		String s = "";
        for (int i = 0; i < code.length; i++)
		{
            s = s + "\nCODA " + i + ":\n" + code[i].toString();
		}
        return s;
    }

    //campi di esemplare
    private Queue[] code;
}


//-----------------------------------------------------------------------------

// NON MODIFICARE!
// Interfaccia che rappresenta il tipo di dati astratto coda

interface Queue
{   // Restituisce true se la coda e` vuota. Restituisce false se la coda
    // contiene almeno un elemento
    boolean isEmpty(); 

    // Svuota la coda
    void makeEmpty();
  
    // Restituisce il numero di elementi presenti nella coda
    int size();

    // Inserisce l'oggetto obj nella coda
    void enqueue(Object obj);

    // Elimina dalla coda il primo oggetto, e lo restituisce.
    // Lancia EmptyQueueException se la coda e` vuota
    Object dequeue() throws EmptyQueueException;

    // Restituisce il primo oggetto della coda, senza estrarlo
    // Lancia EmptyQueueException se la coda e` vuota
    Object getFront() throws EmptyQueueException;
}

//-----------------------------------------------------------------------------

// NON MODIFICARE!
// Eccezione lanciata da dequeue e getFront quando la coda e` vuota

class EmptyQueueException extends RuntimeException { }


//-----------------------------------------------------------------------------

// NON MODIFICARE!
// Interfaccia che rappresenta il tipo di dati astratto "multicoda".
// Una multicoda e` una sequenza di N code. Ciascuna delle N code e` 
// identificata da un indice intero i, dove 0 <= i < N.

interface MultiQueue     
{
    // Restituisce true se la multicoda e` vuota, cioe` se tutte le N
    // code della multicoda sono vuote. Restituisce false se la multicoda
    // contiene almeno un elemento, cioe` se almeno una delle N code della
    // multicoda contiene almeno un elemento
    boolean isEmpty(); 

    // Svuota la multicoda, cioe` svuota tutte le N code della multicoda
    void makeEmpty();
  
    // Inserisce l'oggetto obj nella multicoda. Tra tutte le N code della
    // multicoda, l'oggetto viene accodato a quella che contiene il minor
    // numero di elementi. Nel caso in cui piu` code contengano un numero
    // di elementi pari al minimo, la scelta è indifferente
    int add(Object obj);

    // Disaccoda dalla i-esima coda il suo primo elemento e lo restituisce.
    // L'indice intero i specifica quale e` la coda da cui disaccodare il 
    // primo elemento. Di conseguenza i deve essere tale che 0 <= i < N.
    // Lancia EmptyQueueException se la la i-esima coda e` vuota
    Object remove(int i) throws EmptyQueueException;
}

