import java.util.Scanner;
import java.io.*;


public class Naloga2 {
    public static int najdalsa = 0;
    public static int[] koncnaPot;
    public static int[] koncno = new int[2];



    public static boolean najdiPot(char[][] tabela, char[][] najdalsaPot, int x, int y, char zacetna, int dolzpoti, int[] potencialna, int[]zacetno)
    {

        // preveri ali sta x in  y koordinati veljavni
        if (y<0 || x<0 || y > (najdalsaPot[0].length -1) || x > (najdalsaPot.length -1))
            return false;

        // ali je na trenutni lokaciji druga crka kot zacetna
        // ce je vrni "false"
        if (tabela[x][y] != zacetna)
            return false;


        // ali smo v tej tocki ze bili?
        // ce smo vrni "false"
        if (najdalsaPot[x][y] == '.')
            return false;

        // ce smo prispeli do sem, pomeni, da nismo v končnem stanju
        // oznaci, da je trenutni polozaj na potencialno najdalsi poti
        najdalsaPot[x][y] ='.';
        dolzpoti++;

        //rekurzivni klic - ali pridemo do končenga položaja, če se premaknemo DOL
        potencialna[dolzpoti] = 0;
        if (najdiPot(tabela, najdalsaPot, x+1,y,zacetna, dolzpoti, potencialna, zacetno)) {
            return true;
        }
        //rekurzivni klic - ali pridemo do končenga položaja, če se premaknemo LEVO
        potencialna[dolzpoti] = 1;
        if( najdiPot(tabela, najdalsaPot, x,y-1, zacetna, dolzpoti, potencialna, zacetno)) {
            return true;
        }
        //rekurzivni klic - ali pridemo do končenga položaja, če se premaknemo GOR
        potencialna[dolzpoti] = 2;
        if (najdiPot(tabela, najdalsaPot, x-1,y, zacetna, dolzpoti, potencialna, zacetno)) {
            return true;
        }
        // rekurzivni klic - ali pridemo do končenga položaja, če se premaknemo DESNO
        potencialna[dolzpoti] = 3;
        if(najdiPot(tabela, najdalsaPot, x,y+1, zacetna, dolzpoti, potencialna, zacetno)) {
            return true;
        }
        // ce smo prisli do sem, pomeni, da je to koncni polozaj
        // nastavi najdalso pot če je nova najdalša
        if (dolzpoti>najdalsa){
            najdalsa = dolzpoti;
            koncno[0]=zacetno[0];
            koncno[1]=zacetno[1];
            for(int i = 0; i<dolzpoti; i++){
                koncnaPot[i] = potencialna[i];
            }

        }

        najdalsaPot[x][y] = ' ';
        return false;
    }//konec rekurzije



    public static void main(String[] args) throws IOException {

        //priprava za odpiranje in branje datoteke
        FileReader vhodna = new FileReader(args[0]);
        Scanner vhsc = new Scanner(vhodna);

        // prebereš prvo vrstico in jo razdeliš na posamezne spremenljivke;
        String prvaVrstica = vhsc.nextLine();
        String[] prvaSplit = prvaVrstica.split(",");

        // inicalizacija začetnih parametrov
        int vrstice = Integer.parseInt(prvaSplit[0]);
        int stolpci = Integer.parseInt(prvaSplit[1]);

        // inicalizacija tabele črk
        char [][] tabela = new char[vrstice][stolpci];
        for(int i = 0; i<vrstice; i++) {
            String vrstica = vhsc.nextLine();
            String[] vrsitcaSplit = vrstica.split(",");
            for(int j = 0; j<stolpci;j++){
                tabela[i][j] = vrsitcaSplit[j].charAt(0);
            }
        }

        //inicializacija tabele v katero bomo pisali koncno pot
        koncnaPot = new int[vrstice*stolpci];

        //inicializacija buffer tabele v katero bomo pisali potencialno koncno pot
        int[] potencialna = new int[vrstice*stolpci];

        //inicialzacija "obiskani" tabele v katerte bom zapisaoval potencialne koncne poti
        char [][] obiskani = new char[vrstice][stolpci];
        int [] zacetek = new int[2];

        //klic rekurzije
        for(int i = 0; i<vrstice; i++){
            for (int j = 0; j<stolpci;j++){
                zacetek[0] = i;
                zacetek[1] = j;
                najdiPot(tabela,obiskani,i,j,tabela[i][j],0,potencialna, zacetek);
            }
        }


        PrintWriter resitevOut = new PrintWriter(new FileWriter(args[1]));
        resitevOut.print(koncno[0] +"," +koncno[1] +"\n");

        for (int i = 1; i < najdalsa;i++) {

            if(koncnaPot[i] == 0){

                resitevOut.print("DOL");
            }
            if(koncnaPot[i] == 1){

                resitevOut.print("LEVO");
            }
            if(koncnaPot[i] == 2){

                resitevOut.print("GOR");
            }
            if(koncnaPot[i] == 3){

                resitevOut.print("DESNO");
            }
            if (i<najdalsa-1) {

                resitevOut.print(",");
            }


        }
        resitevOut.print("\n");
        resitevOut.close();



    }
}
