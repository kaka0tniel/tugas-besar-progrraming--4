import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static Scanner scan = new Scanner(System.in);
    static ArrayList<String[]> listBrg = new ArrayList<String[]>();
    static ArrayList<String[]> listBelanja = new ArrayList<String[]>();
    public static void main(String[] argas) {
        int numMenu;
        do{
            System.out.print("Daftar Menu\n"
                    +"1. Transaksi Kasir\n"
                    +"2. Penambahan Inventory\n"
                    +"3. Update Inventory\n"
                    +"4. Daftar Barang Inventory\n"
                    +"5. Exit\n"
                    +"Masukkan Pilihan Anda : ");
            numMenu= scan.nextInt();
            if(numMenu>0 && numMenu <5){
                chooseMenu(numMenu);
            } else System.exit(0);
        }while (numMenu!=5);
        scan.close();
    }

    private static void chooseMenu(int menu) {
        switch(menu) {
            case 1:
                transaksiKasir();
                break;
            case 2:
                tambahInventory();
                break;
            case 3:
                updateBarang();
                break;
            case 4:
                listInventory();
                break;
            default:
                break;
        }
    }

    private static void tambahInventory(){
        System.out.print ("\nMasukan Jumlah Barang Baru : ");
        int NumOfMK = scan.nextInt();

        for (int i = 0; i < NumOfMK; i++){
            String arrMK[]= new String[4];

            System.out.print ("Masukan Kode Barang: ");
            arrMK[0]=scan.next();

            System.out.print ("Masukan Nama Barang: ");
            arrMK[1]=scan.next();

            System.out.print ("Masukan harga: ");
            arrMK[2]=scan.next().toUpperCase();

            System.out.print ("Masukan Stok: ");
            arrMK[3]=scan.next();

            listBrg.add(arrMK);
            System.out.println();
        }
    }

    static void updateBarang(){
        System.out.print("\nMasukan Kode Barang: ");
        String kodeBrg=scan.next();
        System.out.print("Masukan Harga Baru: ");
        String hargaBrg=scan.next();
        System.out.print("Masukan Stok Barang: ");
        String stokBrg=scan.next();
        update(kodeBrg,hargaBrg,stokBrg);

    }

    static void update(String kodeBrg,String hargaBrg, String stokBrg){
        for (int i=0;i< listBrg.size(); i++) {
            if (listBrg.get(i)[0].toLowerCase().equals(kodeBrg.toLowerCase()) )
                listBrg.get(i)[2]=hargaBrg.toUpperCase();
                listBrg.get(i)[3]=stokBrg.toUpperCase();
        }
    }

    static void listInventory(){
        for (int i=0; i< listBrg.size() ;i++){
            String str= Arrays.toString(listBrg.get(i)).replace("["," ");
            str=str.replace("]","");
            str=str.replaceFirst(",","");
            str=str.replace(",","\t");
            System.out.println(String.format(str)) ;
        }
    }

    static void transaksiKasir(){
        System.out.println("masukkan jumlah barang belanja");
        int NumOfMK = scan.nextInt();
        double total = 0;
        String jumlah = "";
        String arrMK[]= new String[4];
        for (int i = 0; i < NumOfMK; i++){
            System.out.print("Kode Barang");
            String kodeBrg=scan.next();
            arrMK[0] = kodeBrg;
            System.out.print("jumlah");
            do {
                jumlah=scan.next();
                arrMK[1] = kodeBrg;
            }
            while (!validasiStok(kodeBrg, jumlah));
            total = total + hitung(kodeBrg,jumlah);
            listBelanja.add(arrMK);
        }
        System.out.println("Total Belanjaan: " + total);
        System.out.println("Apakah setuju dengan transaksi yang dilakukan? (ya/tidak)");
        String answer = scan.nextLine();
        if(answer.equals("ya")){
            for (int i = 0; i < NumOfMK; i++){
                kurangiStok(listBelanja.get(i)[0], listBelanja.get(i)[1]);
            }
            System.out.println("Terima kasih :)");
        }
    }

    static boolean validasiStok(String kodeBrg, String jumlah){
        for (int i=0;i< listBrg.size(); i++) {
            if (listBrg.get(i)[0].toLowerCase().equals(kodeBrg.toLowerCase()) ){
                int stok = Integer.parseInt(listBrg.get(i)[3]);
                if(stok < Integer.parseInt(jumlah)) {
                    System.out.println("Stok tidak cukup");
                    return false;
                }
            }
        }
        return true;
    }

    static double hitung(String kodeBrg, String jumlah){
        double total = 0;
        for (int i=0;i< listBrg.size(); i++) {
            if (listBrg.get(i)[0].toLowerCase().equals(kodeBrg.toLowerCase()) ){
                double harga = Double.parseDouble(listBrg.get(i)[2]);
                total = harga * Double.parseDouble(jumlah);
            }
        }
        return total;
    }

    static void kurangiStok(String kodeBrg,String jumlah) {
        for (int i=0;i< listBrg.size(); i++) {
            if (listBrg.get(i)[0].toLowerCase().equals(kodeBrg.toLowerCase()) ){
                double sisaStok = Double.parseDouble(listBrg.get(i)[3]);
                sisaStok = sisaStok - Double.parseDouble(jumlah);
                listBrg.get(i)[3]= String.valueOf(sisaStok);
            }
        }
    }

}