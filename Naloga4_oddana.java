import java.io.*;
import java.util.Scanner;

public class Naloga4_oddana {

    public static class Blok
    {
        int id;
        int zacetnaPoz;
        int koncnaPoz;
        Blok next;
        Blok prej;

        //nardiš nov Blok
        Blok(int i, int zac, int vel, Blok nxt, Blok prejsni){
            id = i;
            zacetnaPoz = zac;
            koncnaPoz = zac + vel -1;
            next = nxt;
            prej = prejsni;
        }
    }//konec Blok

    public static class Spomin
    {
        protected  Blok zacetek;
        protected  Blok konec;
        protected  int kapaciteta;

        public Spomin(int kap)
        {
            init(kap);
        }

        public void init(int kap)
        {
            kapaciteta = kap;
            // ustvari začetek in konec spomina tako da je med njima prostor za cel spomin
            zacetek = new Blok(-1,0,0,null,null);
            konec= new Blok(-1,kap,0,null,zacetek);
            zacetek.next = konec;
        }

        public  boolean alloc(int size, int id){

            //preveri da ni size slučajno 0
            if(size ==0) return false;

            Blok trenutni = zacetek.next;

            //preveri če ta id že obstaja
            while (trenutni != konec){
                if(trenutni.id == id) return false;
                trenutni = trenutni.next;
            }

            trenutni = zacetek;
            //preveri če ga lahko vrineš nekam v spomin
            while(trenutni != konec){
                //če je med dvema blokoma dovolj prosotra lahko gre ta blok na to mesto
                if(trenutni.next.zacetnaPoz - trenutni.koncnaPoz > size){
                    Blok novi = new Blok(id, trenutni.koncnaPoz+1,size,trenutni.next,trenutni);
                    trenutni.next.prej = novi;
                    trenutni.next = novi;
                    return true;
                }
                trenutni = trenutni.next;
            }
            //če pride do sem potem ni prostora v spominu
            return false;
        }//konec alloc

        public int free(int id){

            Blok trenutni = zacetek;
            //greš od začetka spomina in iščeš id
            while (trenutni != konec){
                //če ga najdeš samo povežeš njegova soseda drugega z drugim
                if(trenutni.id == id){
                    trenutni.prej.next = trenutni.next;
                    trenutni.next.prej = trenutni.prej;
                    return id;
                }
                trenutni = trenutni.next;
            }
            return 0;
        }//konec free

        public  void defrag(int n){
            Blok trenutni = zacetek;

            for(int i = 0; i<n;i++){
                while(trenutni != konec) {
                    //če je med dvema blokoma luknja prestavi blok
                    if (trenutni.next.zacetnaPoz > trenutni.koncnaPoz + 1) {
                        int velikost = trenutni.next.koncnaPoz - trenutni.next.zacetnaPoz;
                        trenutni.next.zacetnaPoz = trenutni.koncnaPoz + 1;
                        trenutni.next.koncnaPoz = trenutni.next.zacetnaPoz + velikost;
                        break;
                    }

                    trenutni = trenutni.next;
                }
            }
        }//konc defrag

        //izpis v output file
        public  void izpis(PrintWriter out){

            //preskocimo zacetni blok
            Blok trenutni = zacetek.next;

            while (trenutni != konec){

                int A = trenutni.id;
                int B = trenutni.zacetnaPoz;
                int C = trenutni.koncnaPoz;
                out.println(A+","+B+","+C);
                trenutni = trenutni.next;

            }

        }//konec izpisa

        //izpis v terminal za testiranje
        /*______________________________________
        public  void izpis(){

            Blok trenutni = zacetek.next;

            while (trenutni != konec){

                int A = trenutni.id;
                int B = trenutni.zacetnaPoz;
                int C = trenutni.koncnaPoz;
                System.out.println(A+","+B+","+C);
                trenutni = trenutni.next;

            }

        }

         ______________________________________*/

    }//konec class Spomin

    public static void main(String[] args) throws IOException {


        FileReader vh = new FileReader(args[0]);
        Scanner vhsc = new Scanner(vh);

        // prebereš prvo vrstico
        String prvaVrstica = vhsc.nextLine();

        //inicializacija začetnih parametrov
        int id;
        int size;
        int steps;
        String ukaz;
        String[] znaki;
        Spomin spom ;

        //preberem prvo vrstico ki vsebije init
        ukaz = vhsc.nextLine();
        znaki = ukaz.split(",");

        //inicializiram spomin
        size =  Integer.parseInt(znaki[1]);
        spom = new Spomin(size);

        //izvajanje ukazov
        while(vhsc.hasNextLine()){

            //preberem naslednjo vrstico
            ukaz = vhsc.nextLine();
            znaki = ukaz.split(",");

            //če je znak a naredim alloc
            if(znaki[0].charAt(0) == 'a'){
                size =  Integer.parseInt(znaki[1]);
                id =  Integer.parseInt(znaki[2]);
                spom.alloc(size,id);
            }

            //če je znak f naredim free
            if(znaki[0].charAt(0) == 'f'){
                id =  Integer.parseInt(znaki[1]);
                spom.free(id);
            }

            //če je znak d naredim defrag
            if(znaki[0].charAt(0) == 'd'){
                steps =  Integer.parseInt(znaki[1]);
                spom.defrag(steps);
            }
        }//konec while


        // izpis resitve
        PrintWriter resitevOut = new PrintWriter(new File(args[1]), "UTF-8");

        spom.izpis(resitevOut);

        resitevOut.close();

    }//konec main
}
