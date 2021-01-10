import java.io.*;
public class Naloga8 {
    //private static final long startTime = System.currentTimeMillis();


    static class Vozlisce
    {
        int id;
        char oznaka;
        int stSinov;

        Vozlisce oce;
        Vozlisce brat;
        Vozlisce sin;

        public Vozlisce(int valID, Vozlisce oce)
        {
            this.id = valID;
            this.oce = oce;
            this.sin = null;
            this.brat = null;
            this.stSinov = 0;
        }
    }

    public static class Drevo
    {
        Vozlisce koren;

        public Drevo(int valID)
        {
            koren = new Vozlisce(valID, null);
        }

        //Pomozna metoda: izpise poddrevo s korenom v vozliscu 'v'
        private void izpis(int zamik, Vozlisce v)
        {
            for(int i = 0; i < zamik; i++)
                System.out.print(" ");

            System.out.println(v.id + " "+ v.oznaka);
            Vozlisce sin = v.sin;

            while (sin != null)
            {
                izpis(zamik+1, sin);
                sin = sin.brat;
            }
        }

        //Metoda za izpis drevesa
        public void izpis()
        {
            izpis(0, koren);
        }


        //Pomozna metoda: vrne kazalec na vozlisce, ki vsebuje vozlisce s podanim id-jem in
        //se nahaja v poddrevesu s korenom 'v'
        private Vozlisce poisci(int valID, Vozlisce v)
        {
            if (v.id ==valID)
                return v;
            else
            {
                Vozlisce sin = v.sin;
                Vozlisce r;

                while (sin != null)
                {
                    r = poisci(valID, sin);

                    if (r != null)
                        return r;
                    else
                        sin = sin.brat;
                }

                return null;
            }
        }//konec poisci

        //Metoda doda sina podanemu ocetu
        public boolean dodajSina(int oceId, int sinId)
        {
            Vozlisce v = poisci(oceId, koren);

            if (v != null)
            {
                if(v.sin == null){
                    Vozlisce s = new Vozlisce(sinId, v);
                    v.sin = s;
                    v.stSinov ++;
                }else {
                    //pomeni da vozlisce ze ima sina torej moramo dodati temu sinu desnega brata
                    // če sin že ima brata moramo temu bratu dodati brata itd.
                    Vozlisce sin = v.sin;
                    //premaknemo se v desno dokler ni sin.brat == null
                    while(sin.brat != null) {
                        sin = sin.brat;
                    }
                    //najbol desnemu sinu dodamo novo vozlisce
                    Vozlisce b = new Vozlisce(sinId, v);
                    sin.brat = b;
                    v.stSinov++;

                }
                return true;
            }
            else
                return false;
        }//konec dodaj sina


        //Metoda podanemu vozlišču nastavi oznako
        public void setOznaka(int id, char ozn){
            Vozlisce nastaviTemu = poisci(id,koren);
            if(nastaviTemu == null){
                System.out.println("Tega vozlišča ne najdem " + id);
                return;
            }
            nastaviTemu.oznaka = ozn;
        }



    }

    public static boolean preveriEnkost( Vozlisce trenutniListT, Vozlisce trenutniListP){

        /*  -rekurzivna funkcija ki gleda ujemanje drevesa dP s poddrevedom drevesa dt, ki se začne v listu trenutniList
            -vrne true če najde ujemanje in false če ne
            -izstopni pogoj: če primerjaš list z listom
            -pregledujem v sirino (najprej pogledam vse sine pol pa se njihove sinove)
            -na začetku je trenutniListP vedno koren od P
        */
        //če je katerikoli null vrnem false
        if(trenutniListP == null || trenutniListT == null){
            return false;
        }

        //če najdem list ki je drugačen prekinem
        if(trenutniListP.oznaka != trenutniListT.oznaka){

            return false;
        }
        //izstopni pogoj

        // če pridem do končnega lista in sta enaka vrnem true
        if(trenutniListP.brat==null && trenutniListP.sin == null ){
            return true;

        }


        // če še nisem prišel do končnega lista najprej pogledam ali ima brate
        if(trenutniListP.brat != null ){
            // če ima brate pogledam ali so oni enaki
            if(!preveriEnkost(trenutniListT.brat,trenutniListP.brat)){
                //če niso prekinem
                return false;
            }
        }

        //če nima bratov ali pa sem pogledal že vse pogledam njegove sinove če jih ima
        if(trenutniListP.sin != null ){
            // če ima sinove pogledam ali so oni enaki
            if(!preveriEnkost(trenutniListT.sin,trenutniListP.sin)){
                //če niso prekinem
                return false;
            }
        }


        return  true;
    }


    public static void main(String[] args) throws IOException {
        //preberi datoreke in pripravi podatke
        FileReader fileIn=new FileReader(args[0]);
        BufferedReader brFileIn=new BufferedReader(fileIn);

        //preberem stevilo vozlišč v P
        String vrstica = brFileIn.readLine();
        String[] vrsticaSplit = vrstica.split(",");
        int stP = Integer.parseInt(vrsticaSplit[0]);

        //preberem prvo vozlišče in ga nastavim za koren
        int idTrenutnegaOceta;
        int idTrenutnegaSina;
        char znak;

        //preberem vrstico s korenom
        vrstica = brFileIn.readLine();
        vrsticaSplit = vrstica.split(",");

        //preberem ID korena
        idTrenutnegaOceta = Integer.parseInt(vrsticaSplit[0]);
        //preberem znak korena
        znak = vrsticaSplit[1].charAt(0);

        //ustvaris drevo P in korenu nastavim oznako
        Drevo drevoP = new Drevo(idTrenutnegaOceta);
        drevoP.setOznaka(idTrenutnegaOceta,znak);

        //pogledam če ima sinove in jih dodam
        for (int i = 2; i < vrsticaSplit.length; i++) {
            //pogledam če ima sinove in jih dodam
            idTrenutnegaSina = Integer.parseInt(vrsticaSplit[i]);
            drevoP.dodajSina(idTrenutnegaOceta, idTrenutnegaSina);

        }

        //grem še do konca drevesa in dodajam sinove
        for(int j = 0; j<stP-1;j++) {
            vrstica = brFileIn.readLine();
            vrsticaSplit = vrstica.split(",");
            //preberem ID trenutnega vozlisca
            idTrenutnegaOceta = Integer.parseInt(vrsticaSplit[0]);
            //preberem znak trenutnega vozlisca
            znak = vrsticaSplit[1].charAt(0);
            //najdem vozlisce in mu dodam oznako
            drevoP.setOznaka(idTrenutnegaOceta,znak);
            //pogledam ali ima še sinove in jih dodam
            for (int i = 2; i < vrsticaSplit.length; i++) {
                idTrenutnegaSina = Integer.parseInt(vrsticaSplit[i]);
                drevoP.dodajSina(idTrenutnegaOceta, idTrenutnegaSina);
            }

        }
        //revoP.izpis();
        //System.out.println("------------");
        // drevo T

        //preberem koliko je vozlišč v drevesu T
        vrstica = brFileIn.readLine();
        vrsticaSplit = vrstica.split(",");
        int stT = Integer.parseInt(vrsticaSplit[0]);

        //preberem vrstico s korenom
        vrstica = brFileIn.readLine();
        vrsticaSplit = vrstica.split(",");

        //preberem ID korena
        idTrenutnegaOceta = Integer.parseInt(vrsticaSplit[0]);
        //preberem znak korena
        znak = vrsticaSplit[1].charAt(0);

        //ustvaris drevo P in korenu nastavim oznako
        Drevo drevoT = new Drevo(idTrenutnegaOceta);
        drevoT.setOznaka(idTrenutnegaOceta,znak);

        //pogledam če ima sinove in jih dodam
        for (int i = 2; i < vrsticaSplit.length; i++) {
            idTrenutnegaSina = Integer.parseInt(vrsticaSplit[i]);
            drevoT.dodajSina(idTrenutnegaOceta, idTrenutnegaSina);
        }
        //grem še do konca tega drevesa in dodajam sinove
        for(int k = 0; k<stT-1;k++) {
            vrstica = brFileIn.readLine();
            vrsticaSplit = vrstica.split(",");
            //preberem ID trenutnega vozlisca
            idTrenutnegaOceta = Integer.parseInt(vrsticaSplit[0]);
            //preberem znak trenutnega vozlisca
            znak = vrsticaSplit[1].charAt(0);
            //najdem vozlisce in mu dodam oznako
            drevoT.setOznaka(idTrenutnegaOceta,znak);
            //pogledam ali ima še sinove in jih dodam
            for (int l = 2; l < vrsticaSplit.length; l++) {
                idTrenutnegaSina = Integer.parseInt(vrsticaSplit[l]);
                drevoT.dodajSina(idTrenutnegaOceta, idTrenutnegaSina);
            }

        }
        //drevoT.izpis();


        // sprehajaj se po večjem drevesu
        // k najdeš list k je isti kot koren P preveri ujemanje
        Vozlisce korenP = drevoP.koren;
        Vozlisce trenutno;
        boolean a = false;
        if(korenP.stSinov == 0){
            a = true;
        }
        int steviloEnakih = 0;
        for(int i = 1; i<= stT; i++){
            trenutno = drevoT.poisci(i, drevoT.koren);
            if(trenutno.stSinov == korenP.stSinov){
                a = true;
            }
            if (trenutno != null && trenutno.oznaka == korenP.oznaka && a) {
                //preveri enakost z rekurzijo
                if(preveriEnkost(trenutno, korenP)){
                    steviloEnakih++;
                    //System.out.println("našel sem poddrevo z začetkom v " + trenutno.id);
                }
            }
            a = false;
        }

        //izpis resitev
        PrintWriter resitevOut = new PrintWriter(new File(args[1]),"UTF-8");
        resitevOut.println(steviloEnakih);
        //System.out.println("stevilo enakih je "+ steviloEnakih);



        // zapiranje
        fileIn.close();
        brFileIn.close();
        resitevOut.close();

        /*
        long endTime = System.currentTimeMillis();
        System.out.println("\n"+(endTime - startTime) + " milliseconds");
         */
    }//konec main

}//konec Naloga8
