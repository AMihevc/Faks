import java.io.*;
import java.util.*;

public class Naloga10 {

    private static boolean flag = true;

    static class ListNode
    {
        Vozlisce vozlisce;
        ListNode next;

        public ListNode(Vozlisce vozl, ListNode nextNode)
        {
            vozlisce = vozl;
            next = nextNode;
        }

        public ListNode(Vozlisce vozl)
        {
            vozlisce = vozl;
            next = null;
        }

    }//konec ListNode

    public static class LinkedList
    {
        private ListNode first;
        private ListNode last;
        private int size;

        public LinkedList()
        {
            makenull();
        }

        public void makenull()
        {
            first = new ListNode(null, null);
            last = null;
            size = 0;
        }

        //Funkcija addLast doda nov element na konec seznama
        public void addLast(Vozlisce obj)
        {
            //najprej naredimo nov element
            ListNode newEl = new ListNode(obj, null);

            //ali je seznam prazen?
            //  ce je seznam prazen, potem kazalec "last" ne kaze nikamor
            if (last == null)
            {
                //ce seznam vsebuje samo en element, kazalca "first" in "last" kazeta na glavo seznama
                first.next = newEl;
                last = first;
            }
            else
            {
                last.next.next = newEl;
                last = last.next;
            }
            size++;
        }//konec addLast

        public void print()
        {
            ListNode curNode = first.next;
            while (curNode.next != null) {
                System.out.print(curNode.vozlisce.getID() +", ");
                curNode = curNode.next;
            }
            System.out.print(curNode.vozlisce.getID()+ "\n");
        }//konec print v terminal

        public void print(PrintWriter Out)
        {
            ListNode curNode = first.next;
            while (curNode.next != null) {
                Out.print(curNode.vozlisce.getID() +",");
                curNode.vozlisce.setObdelano(false);
                curNode = curNode.next;
            }
            curNode.vozlisce.setObdelano(false);
            Out.print(curNode.vozlisce.getID()+ "\n");
        }//konec print v datoteko

        //funkcija primerjaj primerja podani seznam in ta seznam in vrne true če sta enaka in false če sta različna
        public boolean primerjaj(LinkedList seznam){

            //če sta različnih velikosti takoj prekinem
            if(this.size != seznam.size)
                return false;
            //drugače pogledam vsak node ali ima iste elemente
            ListNode curNodeTega = first.next;
            ListNode curNodePodan = seznam.first.next;

            while (curNodeTega != null || curNodePodan !=null) {
                //če se element razlikuje prekinem
                if(curNodeTega.vozlisce != curNodePodan.vozlisce){
                    return false;
                }

               curNodeTega = curNodeTega.next;
               curNodePodan = curNodePodan.next;
            }

            // če so vsi enaki potem vrnem true
            return true;
        }//konec primerjaj

    } //konec LinkedList

    static class Vozlisce
    {
        Object id;
        Povezava prvaPovezvava;
        Vozlisce naslednjeKriz;
        boolean obdelano;
        int ocenaDoKonca;
        boolean koncno;
        int trenutnacena;

        public Vozlisce(Object val)
        {
            id = val;
            prvaPovezvava = null;
            naslednjeKriz = null;
            koncno =false;
            obdelano = false;
            ocenaDoKonca = 0;
            trenutnacena = 0;

        }


        public void setObdelano(boolean val){
            obdelano = val;
        }


        public int getID()
        {
            return (int) id;
        }
    }//konec Krizisce

    static class Povezava
    {
        Vozlisce povezanoKrizisce;
        Povezava naslednjaPovezava;
        int cena;
        boolean ignoriraj;

        public Povezava( Vozlisce poveKriz, Povezava nPovez, int cenaVal)
        {
            povezanoKrizisce = poveKriz;
            naslednjaPovezava = nPovez;
            ignoriraj = false;
            cena=cenaVal;
        }
    } //konec Povezava

    public static class Graf
    {

        protected Vozlisce pVozlisce;

        public void makenull()
        {
            pVozlisce = null;
        }

        public void vstaviKrizisce(Vozlisce v)
        {
            v.naslednjeKriz = pVozlisce;
            pVozlisce = v;
        }

        public void vstaviPovezavo(Vozlisce v1, Vozlisce v2,int cenaVal)
        {
            Povezava novaPovezava = new Povezava( v2, v1.prvaPovezvava,cenaVal);
            v1.prvaPovezvava = novaPovezava;
        }
        //vrne prvo križišče v grafu
        public Vozlisce getPrvoKrizisce()
        {
            return pVozlisce;
        }

        //vrne naslednje križišče v grafu
        public Vozlisce getNaslednjeVozlisce (Vozlisce v)
        {
            return v.naslednjeKriz;
        }

        //vrne prvo povezavo od danega križišča
        public Povezava getPrvaPovezava(Vozlisce v)
        {
            return v.prvaPovezvava;
        }

        //vrne naslednjo povezavo od dane povezave (zadnja povezava ima za naslednjo povezavo null)
        public Povezava getNaslednjaPovezava(Povezava e)
        {
            return e.naslednjaPovezava;
        }

        //vrne križišče ki je povezano z dano povezavo
        public Vozlisce getPovezanoVozlisce(Povezava e)
        {
            return e.povezanoKrizisce;
        }

        public void print()
        {
            for (Vozlisce v = getPrvoKrizisce(); v != null; v = getNaslednjeVozlisce(v))
            {
                System.out.print(v.getID() + ": ");
                for (Povezava e = getPrvaPovezava(v); e != null; e = getNaslednjaPovezava(e))
                    System.out.print(getPovezanoVozlisce(e).getID() +  ", ");
                System.out.println();
                System.out.print("   ");
                for (Povezava e = getPrvaPovezava(v); e != null; e = getNaslednjaPovezava(e))
                    System.out.print(e.cena +  ", ");
                System.out.println();
            }
        }//konec print

        // funkcija izračuna katero vozlišče je naslednje
        public Vozlisce naslednje(Vozlisce curVozl){
            Povezava prva = getPrvaPovezava(curVozl);
            //če vozlišče nima outgoing povezav in ni končno mu moramo oceno nastaviti na neskončno
            if(prva==null && !curVozl.koncno){
                curVozl.ocenaDoKonca = Integer.MAX_VALUE /2;
                return null;
            }

            Vozlisce potencialNext;
            Vozlisce next = getPovezanoVozlisce(getPrvaPovezava(curVozl));
            int minCena = Integer.MAX_VALUE;
            //grem čez vsa povezana vozlišča
            for (Povezava e = getPrvaPovezava(curVozl); e != null; e = getNaslednjaPovezava(e)){
                potencialNext = getPovezanoVozlisce(e);
                //če smo ga v tej iteraciji že obdelali ga preskočimo
                if(potencialNext.obdelano){
                    continue;
                }
                //izračunam ceno do danega vozlišča
                potencialNext.trenutnacena = e.cena + potencialNext.ocenaDoKonca;
                // če je cena manjaša kot prejšna najmanjša se bom potencialno premaknil sem
                if(potencialNext.trenutnacena<minCena){
                    minCena = potencialNext.trenutnacena;
                    next = potencialNext;
                }else if(potencialNext.trenutnacena == minCena){
                    // če sta ceni isti izberem tistega z manjšim ID-jem
                    if(potencialNext.getID() < next.getID()){
                        next=potencialNext;
                    }
                }
            }
            // če pridem čez vsa vozlišča in so vsa že obdelana bo next prva povezava
            // preverim če je ta že obdelana in če je vrnem null pri tem pa ne posodabljam oceneDoKonca
            if(next.obdelano){
                return null;
            }

            //našel sem kam more naprej
            //preden vrnem naslednje križišče moram trenutnemu posodobiti ocenoDoKonca

            if(curVozl.ocenaDoKonca < next.trenutnacena){
                curVozl.ocenaDoKonca = next.trenutnacena ;
                // ce se ocena spremeni moram nastavi flag na true
                flag = true;
            }

            //če pridem do sem nisem naletel na noben poseben primer in vrnem vozlišče z minimalno ceno
            return next;

        }//konec naslednje

    } // konec Graf


    public static void main(String[] args) throws IOException {

        //preberi datoreke in pripravi podatke
        FileReader fileIn=new FileReader(args[0]);
        BufferedReader brFileIn=new BufferedReader(fileIn);


        /*
        Oblika podatkov:
        prva vrstica 1 int = število povezav = stPovezav
        naslednjih stPovezav vrstic so povezave v obliki:
            id_prvega,id_drugega,cena_povezave \n

        zadnja vrstica je id_zacetnega,id_koncnega
         */

        //priprava potrebnih spremenljivk
        int stPovezav,id_prvega,id_drugega,cena_povezave,id_zacetnega,id_koncnega;

        //potrebujem hashmap ker potrebujem način kako hitro dostopati do vozlišč
        HashMap<Integer,Vozlisce> vozlisca = new HashMap<Integer,Vozlisce>();

        //potrebujem Graf vozlišč da jim bom lahko dodajal povezave
        Graf graf = new Graf();

        //da dodam Vozlisce v hashmap uporabim vozlisca.put(id,Vozlisce);
        //preberem prvo vrstico
        String vrstica = brFileIn.readLine();
        String[] vrsticaSplit = vrstica.split(",");

        stPovezav = Integer.parseInt(vrsticaSplit[0]);

        //pripravim vozlisce s katerim bom operiral in tisto ki bo povezano z njim
        Vozlisce trenutno;
        Vozlisce povezano;

        //preberem vse povezave in jih dodajam v hashmap in graf
        for (int i = 0;i<stPovezav;i++){
            //preberem vrstico in jo razdelim v posamezne spremenljivke
            vrstica = brFileIn.readLine();
            vrsticaSplit = vrstica.split(",");
            id_prvega = Integer.parseInt(vrsticaSplit[0]);
            id_drugega = Integer.parseInt(vrsticaSplit[1]);
            cena_povezave = Integer.parseInt(vrsticaSplit[2]);

            // če vozlišča še ni v hashmapu potem ga moram ustvariti in dodati v hashmap ter Graf
            if(!vozlisca.containsKey(id_prvega)){
                trenutno = new Vozlisce(id_prvega);
                vozlisca.put(id_prvega,trenutno);
                graf.vstaviKrizisce(trenutno);
            }else {
                trenutno = vozlisca.get(id_prvega);
            }

            // podobno preverim še za drugo vozlišče
            if(!vozlisca.containsKey(id_drugega)){
                povezano= new Vozlisce(id_drugega);
                vozlisca.put(id_drugega,povezano);
                graf.vstaviKrizisce(povezano);
            }else {
                povezano = vozlisca.get(id_drugega);
            }

            // sedaj sem ali naredil novo vozlisca ali pa jih izbral iz hashamapa
            // tako da lahko dodam trenutno povezavo v graf

            graf.vstaviPovezavo(trenutno,povezano,cena_povezave);

        }

        // prebrati moram še zadnojo vrstico, ki določa zacetno in koncno krizisce
        vrstica = brFileIn.readLine();
        vrsticaSplit = vrstica.split(",");
        id_zacetnega = Integer.parseInt(vrsticaSplit[0]);
        id_koncnega = Integer.parseInt(vrsticaSplit[1]);

        //nastavim koncno vozlisce
        vozlisca.get(id_koncnega).koncno =true;


        /*
            -pripravim linkedlist za pot
            -pripravim flag za če se je katera ocenaDoKoncnega spremenila
            -nove poti računam dokler nista dve poti enaki in nobenemu križišču nisem popravil oceneDoKoncnega (flag = false)
         */

        LinkedList prejsnjaPot = new LinkedList();
        LinkedList trenutnaPot = new LinkedList();

        Vozlisce koncno = vozlisca.get(id_koncnega);

        //priprava file-a za izpis rešitev
        PrintWriter resitevOut = new PrintWriter(new File(args[1]),"UTF-8");

        //iščem vse poti
        while (!trenutnaPot.primerjaj(prejsnjaPot) || flag){
            //nastavim prejsno pot ker sta bili različni ali pa sem še popravljal oceno
            prejsnjaPot = trenutnaPot;
            //začnem graditi novo pot
            trenutnaPot = new LinkedList();
            //vedno začnem na začetku tako da lahko začetek dodam kar takoj
            trenutno = vozlisca.get(id_zacetnega);
            trenutnaPot.addLast(trenutno);

            //konstruiram trenutno pot
            flag = false;
            while (trenutno != koncno){
                //pogledam vse povezave od trenutnega in se premaknem v tisto ki ima najbolšo povezavo
                // formula za vsoto je cena + ocenaDoKonca
                trenutno.setObdelano(true);

                trenutno = graf.naslednje(trenutno);

                //če funklcija naslednje vrne null je prišlo da special case-a in prekinem trenutno iteracijo
                // prekineš trenutno iteracijo
                if(trenutno == null){
                    break;
                }

                //če funkcija naslednje vrne neko vozlišče ga dodaš v linkedlist
                trenutnaPot.addLast(trenutno);

            }
            // izpiši pot ki si jo izračunal
            // hkrati print tudi resetira obdelano vseh vozlišč na poti na false
            trenutnaPot.print(resitevOut);


        }


        //close
        fileIn.close();
        brFileIn.close();
        resitevOut.close();

    }//konec main
}//konec Naloga10
