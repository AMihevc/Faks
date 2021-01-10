import java.io.*;

public class Naloga7 {
    
    static class ListNode
    {
        ParKrizisc par;
        ListNode next;

        public ListNode(ParKrizisc parkriz, ListNode nextNode)
        {
            par = parkriz;
            next = nextNode;
        }
    }//konec ListNode

    public static class LinkedList
    {
        private ListNode first;
        private int size;

        public LinkedList()
        {
            makenull();
        }

        public void makenull()
        {
            first = new ListNode(null, null);
            size = 0;
        }


        public void add(ParKrizisc element)
        {
            //case če dodam prvega
            ListNode trenutni = first;
            if(first.next == null){
                first.next = new ListNode(element, null);
                size++;
            }else{
                while(trenutni.next != null){

                    if(element.prva <= trenutni.next.par.prva ){

                        if(element.prva == trenutni.next.par.prva){
                            if(element.druga < trenutni.next.par.druga){
                                ListNode nova = new ListNode(element, trenutni.next);
                                trenutni.next = nova;
                                size++;
                                break;

                            }
                        }
                        if(element.prva < trenutni.next.par.prva){
                            ListNode nova = new ListNode(element, trenutni.next);
                            trenutni.next = nova;
                            size++;
                            break;
                        }

                    }
                    trenutni = trenutni.next;
                    //System.out.println("tuki se vrtim");
                    if (trenutni.next  == null){
                        trenutni.next = new ListNode(element, null);
                        size++;
                        break;
                    }
                }
            }
        }//konec add

        public void print()
        {
            ListNode curNode = first.next;
            while (curNode != null) {
                System.out.print(curNode.par.prva + " " + curNode.par.druga +"\n");
                curNode = curNode.next;
            }

            System.out.println();
        }

        public void print(PrintWriter Out)
        {
            ListNode curNode = first.next;
            while (curNode != null) {
                Out.print(curNode.par.prva + " " + curNode.par.druga +"\n");
                curNode = curNode.next;
            }
        }
    } //konec LinkedList


    static class QueueElement
    {
        Object element;
        QueueElement next;

        QueueElement()
        {
            element = null;
            next = null;
        }
    } // konec QueueElement

    static class Queue
    {
        //QueueElement -> QueueElement -> QueueElement -> ... -> QueueElement
        //    front                                                   rear
        //
        // nove elemente dodajamo na rear strani
        // elemente jemljemo s front strani

        private QueueElement front;
        private QueueElement rear;

        public Queue()
        {
            makenull();
        }

        public void makenull()
        {
            front = null;
            rear = null;
        }

        public boolean empty()
        {
            return (front == null);
        }

        public Object front()
        {
            if (!empty())
                return front.element;
            else
                return null;
        }

        public void enqueue(Object obj)
        {
            QueueElement el = new QueueElement();
            el.element = obj;
            el.next = null;

            if (empty())
            {
                front = el;
            }
            else
            {
                rear.next = el;
            }

            rear = el;
        }

        public void dequeue()
        {
            if (!empty())
            {
                front = front.next;

                if (front == null)
                    rear = null;
            }
        }
    }//konec Queue


    static class Krizisce
    {
        Object id;
        Povezava prvaPovezvava;
        Krizisce naslednjeKriz;
        Krizisce stars;
        boolean obdelano;

        public Krizisce(Object val)
        {
            id = val;
            prvaPovezvava = null;
            naslednjeKriz = null;
            stars = null;
            obdelano = false;

        }

        public void setStars(Krizisce parent){
            if(stars == null)
                stars = parent;
        }

        public void setObdelano(boolean val){
            obdelano = val;
        }

        public boolean jeObdelano(){
            return obdelano;
        }

        public int getID()
        {
            return (int) id;
        }
    }//konec Krizisce

    static class Povezava
    {
        Krizisce povezanoKrizisce;
        Povezava naslednjaPovezava;
        boolean ignoriraj;

        public Povezava( Krizisce poveKriz, Povezava nPovez)
        {
            povezanoKrizisce = poveKriz;
            naslednjaPovezava = nPovez;
            ignoriraj = false;
        }
    } //konec Povezava

    public static class Graf
    {

        protected Krizisce pKrizisce;

        public void makenull()
        {
            pKrizisce = null;
        }

        public void vstaviKrizisce(Krizisce v)
        {
            v.naslednjeKriz = pKrizisce;
            pKrizisce = v;
        }

        public void vstaviPovezavo(Krizisce v1, Krizisce v2)
        {
            Povezava novaPovezava = new Povezava( v2, v1.prvaPovezvava);
            v1.prvaPovezvava = novaPovezava;
        }
        //vrne prvo križišče v grafu
        public Krizisce getPrvoKrizisce()
        {
            return pKrizisce;
        }

        //vrne naslednje križišče v grafu
        public Krizisce getNaslednjeKrizisce (Krizisce v)
        {
            return v.naslednjeKriz;
        }

        //vrne prvo povezavo od danega križišča
        public Povezava getPrvaPovezava(Krizisce v)
        {
            return v.prvaPovezvava;
        }

        //vrne naslednjo povezavo od dane povezave (zadnja povezava ima za naslednjo povezavo null)
        public Povezava getNaslednjaPovezava(Povezava e)
        {
            return e.naslednjaPovezava;
        }

        //vrne križišče ki je povezano z dano povezavo
        public Krizisce getPovezanoKrizisce(Povezava e)
        {
            return e.povezanoKrizisce;
        }

        public void resetObdelano(){
            Krizisce temp =this.getPrvoKrizisce();
            while(temp != null){
                temp.setObdelano(false);
                temp = getNaslednjeKrizisce(temp);

            }

        }

        public Povezava getPovezavaWith(Krizisce originalno, Krizisce povezano){
            Povezava temp = getPrvaPovezava(originalno);
            while (temp != null){
                if(this.getPovezanoKrizisce(temp) == povezano){
                    return temp;
                }
                temp = getNaslednjaPovezava(temp);
            }
         return temp;
        }

        public void print()
        {
            for (Krizisce v = getPrvoKrizisce(); v != null; v = getNaslednjeKrizisce(v))
            {
                System.out.print(v.getID() + ": ");
                for (Povezava e = getPrvaPovezava(v); e != null; e = getNaslednjaPovezava(e))
                    System.out.print(getPovezanoKrizisce(e).getID() +  ", ");
                System.out.println();
            }
        }

    } // konec Graf


    public static class ParKrizisc{
        int prva;
        int druga;

        public ParKrizisc(int val_prva, int val_druga){
            prva = val_prva;
            druga = val_druga;
        }
    }//konec parKrizisc


    public static int najkrajsaPot(Krizisce zacetek, int koncnoID, Graf roads)
    {
        Queue kue = new Queue();

        kue.enqueue(zacetek);
        Krizisce trenutno;
        Krizisce temp;
        Povezava curPovezava;

        while (!kue.empty())
        {
            trenutno = (Krizisce) kue.front();
            kue.dequeue();

            //preveri če je to križišče že bilo obdelano
            if (trenutno.jeObdelano()){
                continue;
            }

            //preveri če je to križišče končno
            if(trenutno.getID() == koncnoID){
                //System.out.println("našel sem najkrajšo pot");
                return 1;
            }

            // če ni končno in še ni bilo obdelano
            // nastavim da je obdelano
            trenutno.setObdelano( true);

            //pogledam če ima sploh še kakšne povezave naprej
            if(roads.getPrvaPovezava(trenutno) == null){
                continue;
            }

            // in dodam v queue vse njegove poti
            // križiščem ki jih dodajam v queue nastavim trenutnega za starša

            //najdem prvo povezavo križišča
            curPovezava = roads.getPrvaPovezava(trenutno);
            //najdem povezano križišče
            temp = roads.getPovezanoKrizisce(curPovezava);
            //nastavim mu starša in ga dodam v que
            temp.setStars(trenutno);
            kue.enqueue(temp);


            // pogledaš ali ima križišče še več povezav in dodaš vse v que
            while (roads.getNaslednjaPovezava(curPovezava) !=null){
                //najdem naslednjo povezavo
                curPovezava = roads.getNaslednjaPovezava(curPovezava);
                //najdem povezano križišče
                temp = roads.getPovezanoKrizisce(curPovezava);
                //nastavim mu starša in ga dodam v que
                temp.setStars(trenutno);
                kue.enqueue(temp);
            }


        }
        return 0;

    } // konec najkrajsaPot


    public static boolean jeDelVseh(Krizisce konec, int zacetnoID, Graf roads)
    {
        Queue kue = new Queue();

        kue.enqueue(konec);
        Krizisce trenutno;
        Krizisce temp;
        Povezava curPovezava;

        while (!kue.empty())
        {
            trenutno = (Krizisce) kue.front();
            kue.dequeue();
            //System.out.println("sem na element " + trenutno.getID());
            //preveri če je to križišče že bilo obdelano
            if (trenutno.jeObdelano()){
                continue;
            }

            //preveri če je to križišče zacetno
            if(trenutno.getID() == zacetnoID){
                //System.out.println("našel sem alternativno pot za križišče " + trenutno.getID());
                return false;
            }

            // če ni zacetno in še ni bilo obdelano
            // nastavim da je obdelano
            trenutno.setObdelano(true);

            //pogledam če ima sploh še kakšne povezave naprej
            if(roads.getPrvaPovezava(trenutno) == null){
                continue;
            }

            // in dodam v queue vse njegove poti

            //najdem prvo povezavo križišča
            curPovezava = roads.getPrvaPovezava(trenutno);
            //najdem povezano križišče
            temp = roads.getPovezanoKrizisce(curPovezava);
            //ga dodam v que
            if(!curPovezava.ignoriraj){
                //System.out.println(" dodam "+ temp.getID() );
                kue.enqueue(temp);
            }



            // pogledam ali ima križišče še več povezav in dodam vse v que
            while (roads.getNaslednjaPovezava(curPovezava) !=null){
                //najdem naslednjo povezavo
                curPovezava = roads.getNaslednjaPovezava(curPovezava);
                //najdem povezano križišče
                temp = roads.getPovezanoKrizisce(curPovezava);
                //in ga dodam v que oz ignoriram če je to povezava ki jo trenutno testiram
                if(!curPovezava.ignoriraj){
                    //System.out.println(" dodam "+ temp.getID() );
                    kue.enqueue(temp);
                }

            }
        }
        //System.out.println("nisem prišel do konca");
        return true;
    } // konec jeDelVseh


    public static void main(String[] args) throws IOException {

        //preberi datoreke in pripravi podatke
        FileReader fileIn=new FileReader(args[0]);
        BufferedReader brFileIn=new BufferedReader(fileIn);

        // prva vrstica št križišč, št povezav
        // druga vrstica začetno in končno križišče
        // naprej so povezave

        int st_krizisc;
        int st_povezav;
        int zacetno;
        int koncno;

        String vrstica;

        //preberi prvo vrstico
        vrstica = brFileIn.readLine();
        String[] vrsticaSplit = vrstica.split(" ");
        st_krizisc = Integer.parseInt(vrsticaSplit[0]);
        st_povezav = Integer.parseInt(vrsticaSplit[1]);


        Graf ceste = new Graf();
        Krizisce[] vsaKrizisca = new Krizisce[st_krizisc];

        for(int i = 0; i<st_krizisc;i++) {
            vsaKrizisca[i] = new Krizisce(i);
            ceste.vstaviKrizisce(vsaKrizisca[i]);
        }


        //preberi drugo vrstico
        vrstica = brFileIn.readLine();
        vrsticaSplit = vrstica.split(" ");
        zacetno = Integer.parseInt(vrsticaSplit[0]);
        koncno = Integer.parseInt(vrsticaSplit[1]);


        //preberi ostale vrstice
        int prvoKriz;
        int drugoKriz;
        for (int i = 0; i< st_povezav;i++){
            String nextVrs = brFileIn.readLine();
            String[] nextVrsSplit = nextVrs.split(" ");

            prvoKriz= Integer.parseInt(nextVrsSplit[0]);
            drugoKriz = Integer.parseInt(nextVrsSplit[1]);

            ceste.vstaviPovezavo(vsaKrizisca[prvoKriz],vsaKrizisca[drugoKriz]);
            ceste.vstaviPovezavo(vsaKrizisca[drugoKriz],vsaKrizisca[prvoKriz]);
          }


        //kličem algoritem za najkrajšo pot
        najkrajsaPot(vsaKrizisca[zacetno],koncno,ceste);
        ceste.resetObdelano();



        //algoritem za bridges
        /*
        - starš je povezava, ki je del najkrajše poti. Zanima me če te povezave ne bi bilo ali še vedno pridem od A do B.
          Če ne pridem do "začetka" potem je ta povezava del vseh poti med A in B.
        - potrebujem skoraj isti algoritem kot za iskanje najkrajše poti samo da tokrat začneš v poljubnem križišču, ignoriraš povezavo do starša in
          ne nastavljaš novih staršev.
        - če ta algoritem vrne true potem sta trenutno in njegov starš del rešitve.
        -dodaš ju v seznam rešitev ki sproti ureja povezave
         */


        Krizisce trenutnoKriz= vsaKrizisca[koncno];
        //seznam povezav ki so v resitvi
        LinkedList resitev = new LinkedList();
        ParKrizisc novo;

        while (trenutnoKriz != vsaKrizisca[zacetno]) {

             // najdem in nastavim povezavo, ki jo želim ignorirati
            ceste.getPovezavaWith(trenutnoKriz,trenutnoKriz.stars).ignoriraj = true;
            ceste.getPovezavaWith(trenutnoKriz.stars,trenutnoKriz).ignoriraj = true;

            //pogledam ali brez te povezave še vedno pridem od A do B
            if(jeDelVseh(trenutnoKriz, zacetno, ceste)){
                //povezavo urejeno dodam v seznam rešitev ki tudi uredi elemente pri tem ko jih dodajaš
                if(trenutnoKriz.getID() < trenutnoKriz.stars.getID()){
                    novo =  new ParKrizisc(trenutnoKriz.getID(),trenutnoKriz.stars.getID());
                    resitev.add(novo);

                }else{
                    novo =  new ParKrizisc(trenutnoKriz.stars.getID(),trenutnoKriz.getID());
                    resitev.add(novo);
                }

            }

            //ponastavi povezave ki si jih ignoriral
            ceste.getPovezavaWith(trenutnoKriz,trenutnoKriz.stars).ignoriraj = false;
            ceste.getPovezavaWith(trenutnoKriz.stars,trenutnoKriz).ignoriraj = false;

            // resetiram status obdelano
            ceste.resetObdelano();

            //pogledam naslednje križišče na najkrajši poti
            trenutnoKriz = trenutnoKriz.stars;
        }

        //pisanje rešitev
        //izpišem rešitev v datoteko
        PrintWriter resitevOut = new PrintWriter(new File(args[1]),"UTF-8");
        resitev.print(resitevOut);


        // zapiranje
        fileIn.close();
        brFileIn.close();
        resitevOut.close();

    }//konec main
}//konec Naloga7
