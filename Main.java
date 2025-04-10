import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class Main {

    public class Kigyo{
        String nev;
        Integer hosz;
        String elohely;
        String merges;

        public Kigyo(String sor){
            String[] s =sor.split(";");
            nev=s[0];
            hosz=Integer.parseInt(s[1]);
            elohely=s[2];
            merges=s[3];
        }
    }

    public ArrayList<Kigyo> kigyok = new ArrayList<>();

    public Main(){
        betolt("kigyok.csv");

        //1.feladat
        System.out.printf("0) Összesen %d kígyó adata beolvasva\n",kigyok.size());
        int sumVen=0;
        int sumNonVen=0;
        for (Kigyo kigyo:kigyok){
            if(kigyo.merges.equals("Igen")) sumVen++;
            else sumNonVen++;
        }
        System.out.printf("   Közülük %d mérges és %d nem mérges\n",sumVen,sumNonVen);

        //2.feladat
        double sumLength=0;
        for (Kigyo kigyo:kigyok){
            sumLength+=kigyo.hosz;
        }
        System.out.printf("1) A kígyók teljes hossza méterben: %.2f\n",sumLength/100);

        //3.feladat
        Kigyo longestVen=kigyok.get(0);
        for (Kigyo kigyo:kigyok){
            if(kigyo.merges.equals("Igen") && longestVen.hosz<kigyo.hosz) longestVen=kigyo;
        }
        System.out.printf("2) A leghosszabb mérges kígyó: %s (%dcm)\n",longestVen.nev,longestVen.hosz);

        //4.feladat
        TreeSet<String> szarmazasok = new TreeSet<>();
        for (Kigyo kigyo:kigyok){
            szarmazasok.add(kigyo.elohely);
        }

        System.out.print("3) A kígyók származási helye (abc): ");
        for (String hely:szarmazasok){
            System.out.print(hely+" ");
        }
        System.out.println();

        //5.feladat
        ArrayList<Kigyo> mergesKigyok = new ArrayList<>();
        for(Kigyo kigyo:kigyok){
            if (kigyo.merges.equals("Igen")) mergesKigyok.add(kigyo);
        }
        Kigyo randomSnake=mergesKigyok.get(new Random().nextInt(mergesKigyok.size()));
        System.out.printf("4) Egy véletlen kiválasztott mérges kígyó: %s\n",randomSnake.nev);
        System.out.printf("   Származási helye %s, hossza %dcm\n",randomSnake.elohely,randomSnake.hosz);

        //6.feladat
        TreeMap<String,Integer> fajokDb= new TreeMap<>();
        for(Kigyo kigyo:kigyok){
            String[] nev=kigyo.nev.split(" ");
            if(!fajokDb.containsKey(nev[nev.length-1])){
                fajokDb.put(nev[nev.length-1],1);
            }else {
                fajokDb.put(nev[nev.length-1],fajokDb.get(nev[nev.length-1])+1);
            }
        }
        System.out.println("5) Adott fajhoz (abc) tartozó kígyók darabszáma:");
        for (String faj:fajokDb.keySet()){
            System.out.printf("   %s : %d féle\n",faj,fajokDb.get(faj));
        }

        //7.feladat
        Integer lastMamba=0;
        for (int i=0;i<kigyok.size();i++){
            String[] nev=kigyok.get(i).nev.split(" ");
            if(nev[nev.length-1].equals("Mamba")) lastMamba=i;
        }

        System.out.printf("6) Az utolsó Mamba fajtája: %s\n",kigyok.get(lastMamba).nev);

        //8.feldat
        PrintWriter ki = null;
        try{
            ki = new PrintWriter(new File("kobra.txt"),"utf-8");
            for(Kigyo kigyo:kigyok){
                String[] nev=kigyo.nev.split(" ");
                if(nev[nev.length-1].equals("Kobra")) {
                    ki.printf("%s (%d)\r\n",kigyo.nev,kigyo.hosz);
                };
            }
        }catch (UnsupportedEncodingException e){
            System.out.println(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(ki !=null) ki.close();
        }
        System.out.printf("7) Minden Kobra mentve a kobra.txt fájlba");
    }

    private void betolt(String FajNev){
        Scanner be = null;
        try{
            be= new Scanner(new File(FajNev),"utf-8");
            be.nextLine();
            while (be.hasNextLine()){
                kigyok.add(new Kigyo(be.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(be != null) be.close();
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}