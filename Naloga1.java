import java.util.Scanner;
import java.io.*;


public class Naloga1 {


    private static final long startTime = System.currentTimeMillis();
    public static void main(String[] args) throws IOException {

        FileReader vh = new FileReader(args[0]);
        Scanner vhsc = new Scanner(vh);

        // prebereš prvo vrstico in jo razdeliš na posamezne spremenljivke;
        String prvaVrstica = vhsc.nextLine();
        String[] prvaSplit = prvaVrstica.split(",");

        // inicalizacija začetnih parametrov
        int dolzina = Integer.parseInt(prvaSplit[0]);
        int kapaciteta = Integer.parseInt(prvaSplit[1]);
        int stPostaj = Integer.parseInt(prvaSplit[2]);



        // inacilizacija in polnjenje tabel razdalje in cene
        // cene[0] in razdalje[0] se nanašata na začeteno stnaje
        // razdalje[stPostaj+2] in cene[stPostaj+2] se nanašata na končno stanje cene[] je vedno 0 razdalje[] pa moram izračunat

        int[] cene = new int[stPostaj+2];
        int[] razdalje = new int[stPostaj+2];    // razdalje[i] je razdalja do prejšnje (i-1) posatje
        int[] prejsni = new int[stPostaj+2];   // za sledenje predhodnjih

        int vseRazdalje = 0;
        //napolni cene in razdalje
        for(int i = 1; i <= stPostaj ;i++){
            if(vhsc.hasNextLine()) {
                String next = vhsc.nextLine();
                String[] nextSplit = next.split(":|,");
                cene[i]= Integer.parseInt(nextSplit[2]);
                razdalje[i]= Integer.parseInt(nextSplit[1]);
                vseRazdalje += razdalje[i];

            }else {
                break;
            }
        }

        //postavitev končnega stanja
        razdalje[stPostaj+1] = dolzina -vseRazdalje;
        cene[stPostaj+1] = 0;


        //racunanje najcenejše poti

        int razdalja;
        //sem beležiš koliko je najcenejša pot dane postaje
        int[] placano = new int[stPostaj+2];
        // do zacetka je 0
        placano[0] = 0;

        for(int i = 1; i <= stPostaj+1; i++){

            int cenaPos = cene[i];
            razdalja = razdalje[i];
            int minimalnaCena = Integer.MAX_VALUE;

            for(int j = i-1; j>= 0;j--){
                // če je postaja dlje kot se lahko vozimo z polnim tankom
                if (razdalja > kapaciteta){
                    break;
                }
                //če je ceneje da pridemo po tej poti do te postaje zabeležiš minimalno ceno in prek katere si prišel
                if ( (placano[j] + razdalja*cenaPos) <= minimalnaCena){
                    minimalnaCena = placano[j] + razdalja*cenaPos;
                    prejsni[i] = j;
                }

                razdalja = razdalja + razdalje[j];
            }
            placano[i] = minimalnaCena;
        }

        //obracanje vrstnega reda staršev za vpis v datoteko
        PrintWriter resitevOut = new PrintWriter(new File(args[1]),"UTF-8");
        int stars = prejsni[stPostaj +1];
        int[] koncnaSekvenca = new int[stPostaj];
        int index = 0;
        while(stars != 0){
            koncnaSekvenca[index] = stars;
            stars = prejsni[stars];
            index++;
        }
        index--;
        while(index >= 0 ){
            resitevOut.print(koncnaSekvenca[index]);
            index--;
            if (index>=0)
                resitevOut.print(",");

        }

        resitevOut.print("\n");
        resitevOut.close();

        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) + " milliseconds");

    }

}
