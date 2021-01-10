import java.util.Scanner;
import java.io.*;

public class Naloga3 {
    //elementi v vrsti
    public static class QueueElement
    {
        Object element;
        QueueElement next;

        QueueElement()
        {
            element = null;
            next = null;
        }
    }

    public static class Vrsta
    {
        private QueueElement zacetek;
        private QueueElement konec;
        //inicializacija vrste
        public Vrsta()
        {
            makenull();
        }

        public void makenull()
        {
            zacetek = null;
            konec = null;
        }

        //preveri če je vrsta prazna
        public boolean empty()
        {
            return (zacetek == null);
        }

        //najdi in izpiši začetek a ga ne pobriši
        public Object zacetek()
        {
            if (!empty())
                return zacetek.element;
            else
                return null;
        }

        //dodaj element na konec vrste
        public void enqueue(Object obj)
        {
            QueueElement novi = new QueueElement();
            novi.element = obj;
            novi.next = null;

            if (empty())
                zacetek = novi;

            else
                konec.next = novi;

            konec = novi;
        }

        //odstrani element iz začetka vrste
        public void dequeue()
        {
            if (!empty())
            {
                zacetek = zacetek.next;
                if (zacetek == null)
                    konec = null;
            }
        }
    }


    public static void main(String[] args) throws IOException {

        FileReader vh = new FileReader(args[0]);
        Scanner vhsc = new Scanner(vh);

        // prebereš prvo vrstico in jo razdeliš na posamezne spremenljivke;
        String prvaVrstica = vhsc.nextLine();
        String[] prvaSplit = prvaVrstica.split(",");

        //inicializacija začetnih parametrov
        //N-število vrstic  V- zadnja vrstica  K-število premikov P-premik do nove vrstice
        int stVrstic = Integer.parseInt(prvaSplit[0]);
        int zadnja = Integer.parseInt(prvaSplit[1]);
        int stPremikov = Integer.parseInt(prvaSplit[2]);
        int P = Integer.parseInt(prvaSplit[3]);

        int stElementov = 0;
        //inicializacija tabele vrst
        Vrsta[] vrste = new Vrsta[stVrstic];
        for (int i = 0; i < stVrstic; i++){

            vrste[i] = new Vrsta();
            String vrstica = vhsc.nextLine();
            String[] vrsticaSplit = vrstica.split(",");


            for( int j = vrsticaSplit.length -1; j>= 0; j--){
                if (vrstica.isEmpty()) {
                    vrste[i].makenull();
                }else {
                    vrste[i].enqueue(Integer.parseInt(vrsticaSplit[j]));
                    stElementov++;
                }
            }
        }

        //3. korak

        //odstrani prvi element in ga dodaj na konec vrstice (vrstica + element)% št vrstic

        for(int i = 0; i< stPremikov;i++) {
            //prebereš zadnji element ki je bil prestavljen v vrsti
            int element = (int) vrste[zadnja].zacetek();
            // ga daš vn iz vrste
            vrste[zadnja].dequeue();
            //poiščeš iz kje je bil premaknjen oz kam ga moraš dat
            int indeksVrste = (zadnja - element) % stVrstic;
            if (indeksVrste<0){
                indeksVrste =stVrstic + indeksVrste;
            }
            //ga daš v pravo vrsto
            vrste[indeksVrste].enqueue(element);
            //poiščeš naslednjo vrsto iz katere boš uzel znak
            zadnja = (indeksVrste - P) % stVrstic;
            if (zadnja<0){
                zadnja = stVrstic + zadnja;
            }
        }

        // 2. korak

        int[] koncniSeznam= new int [stElementov];
        int trenutna;
        stElementov --;
        for (int i = 0; i<=stElementov;i++) {
            trenutna = (stElementov - i) % stVrstic;
            koncniSeznam[stElementov - i] = (int) vrste[trenutna].zacetek();
            vrste[trenutna].dequeue();
        }



        // 1. korak

        //tabela črk
        char[] crke =  {'A','B','C','Č','D','E','F','G','H','I','J','K','L','M','N','O','P','R','S','Š','T','U'
                ,'V','Z','Ž','a','b','c','č','d','e','f','g','h','i','j','k','l','m','n','o','p','r','s','š','t','u','v','z','ž',' '};


        // tabela stevilk
        int[] stevilke = new int[51];
        for(int i =0; i<51; i++){
            stevilke[i] = i;
        }


        //izpisovanje rešitve
        PrintWriter resitevOut = new PrintWriter(new File(args[1]), "UTF-8");

        for (int i = 0; i< koncniSeznam.length; i++ ){
            //najdi indeks črke
            int index =-1;
            for(int j = 0; j< stevilke.length; j++){
                if (koncniSeznam[i] == stevilke[j])
                    index = j;
            }
            //izpisi crke
            resitevOut.print(crke[index]);
        }
        resitevOut.print("\n");
        resitevOut.close();

    }

}
