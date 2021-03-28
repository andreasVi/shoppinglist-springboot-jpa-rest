package shoppinglist;

//import com.sun.tools.jdeprscan.scan.Scan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import shoppinglist.entity.DaftarBelanja;
import shoppinglist.entity.DaftarBelanjaDetil;
import shoppinglist.repository.DaftarBelanjaRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class DemoShoppingListSpringBootApplication implements CommandLineRunner
{

    @Autowired
    private DaftarBelanjaRepo repo;

    public static void main(String[] args)
    {
        SpringApplication.run(DemoShoppingListSpringBootApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception
    {
        Scanner scan = new Scanner(System.in);
        int masukan;
        while(true){
            System.out.print("Main Menu\n" +
                    "1. Menampilkan semua daftar belanja\n" +
                    "2. Mencari daftar belanja berdasarkan id\n" +
                    "3. Mencari daftar belanja berdasarkan judul\n" +
                    "4. Menambah daftar belanja\n" +
                    "5. Mengupdate daftar belanja\n" +
                    "6. Menghapus daftar belanja\n"+
                    "Masukkan pilihan anda: ");
            masukan = scan.nextInt();

            switch (masukan){
                case 1:
                    MenampilkanSemuaDaftarBarang();
                    break;
                case 2:
                    DaftarBarangBerdasarkanId();
                    break;
                case 3:
                    DaftarBarangBerdasarkanJudul();
                    break;
                case 4:
                    AddDaftarBelanja();
                    break;
                case 5:
                    UpdateDaftarBelanja();
                    break;
                case 6:
                    HapusDaftarBelanja();
                    break;
            }
        }

    }

    public void MenampilkanSemuaDaftarBarang(){
        System.out.println("-Menampilkan Semua DaftarBelanja-");
        List<DaftarBelanja> all = repo.findAll();
        for (DaftarBelanja db: all) {
            System.out.println("Judul: " + db.getJudul());
            List<DaftarBelanjaDetil> listDbDetil = db.getDaftarBarang();
            System.out.println("Detail daftar belanja:");
            for (DaftarBelanjaDetil dbDetil : listDbDetil) {
                System.out.println(
                        "\tNama barang: " + dbDetil.getNamaBarang() +
                        "\n\tJumlah: " + dbDetil.getByk() +
                        "\n\tSatuan: " + dbDetil.getSatuan()
                );
            }
        }
    }

    public void DaftarBarangBerdasarkanId(){
        Scanner scan = new Scanner(System.in);
        System.out.println("-Mencari data daftar belanja berdasarkan Id-");
        System.out.print("Masukkan Id: ");
        String inpId = scan.nextLine();
        long id = Long.parseLong(inpId);

        Optional<DaftarBelanja> oDbelanja = repo.findById(id);
        if (oDbelanja.isPresent()) {
            DaftarBelanja db = oDbelanja.get();
            System.out.println("Judul: " + db.getJudul());
            List<DaftarBelanjaDetil> listDbDetil = db.getDaftarBarang();
            System.out.println("Detail daftar belanja: ");
            for (DaftarBelanjaDetil dbDetil : listDbDetil) {
                System.out.println(
                        "\tNama barang: " + dbDetil.getNamaBarang() +
                        "\n\tJumlah: " + dbDetil.getByk() +
                        "\n\tSatuan: " + dbDetil.getSatuan()
                );
            }
        }
        else {
            System.out.println("-Tidak ada data-");
        }
    }

    public void DaftarBarangBerdasarkanJudul() {
        Scanner scan = new Scanner(System.in);
        System.out.println("-Mencari daftar belanja berdasarkan judul-");
        System.out.print("Masukkan judul: ");
        String Judul = scan.nextLine();

        DaftarBelanja jDbelanja = null;
        List<DaftarBelanja> all = repo.findAll();
        for (DaftarBelanja db : all) {
            if(db.getJudul().equals(Judul)){
                jDbelanja = db;
            }
        }
        if(jDbelanja != null){
            System.out.println("Judul: " + jDbelanja.getJudul());
            List<DaftarBelanjaDetil> listDbDetil = jDbelanja.getDaftarBarang();
            System.out.println("Detail daftar belanja:");

            for (DaftarBelanjaDetil dbDetil : listDbDetil) {
                System.out.println(
                        "\tNama barang: " + dbDetil.getNamaBarang() +
                        "\n\tJumlah: " + dbDetil.getByk() +
                        "\n\tSatuan: " + dbDetil.getSatuan()
                );
            }
        }else{
            System.out.println("-Tidak ada data-");
        }
    }

    public void AddDaftarBelanja(){
        LocalDateTime tglBuat = LocalDateTime.now().withNano(0);
        Scanner scan = new Scanner(System.in);
        System.out.println("-Tambah daftar belanja-");
        System.out.print("Masukkan judul: ");
        String judul = scan.nextLine();

        DaftarBelanja db = new DaftarBelanja();
        db.setJudul(judul);
        db.setTanggal(tglBuat);
        repo.save(db);
        System.out.println("-Berhasil ditambahkan!-");
    }

    public void UpdateDaftarBelanja(){
        LocalDateTime tglBuat = LocalDateTime.now().withNano(0);
        Scanner scan = new Scanner(System.in);
        System.out.println("-Update daftar belanja-");
        System.out.print("Masukkan Id: ");
        String inpId = scan.nextLine();

        long id = Long.parseLong(inpId);
        Optional<DaftarBelanja> optDB = repo.findById(id);
        if (optDB.isPresent()) {
            DaftarBelanja db = optDB.get();
            System.out.println("Judul: " + db.getJudul());
            System.out.println("-Data akan diupdate-");
            System.out.print("Masukan judul baru: ");
            String judul = scan.nextLine();
            db.setJudul(judul);
            db.setTanggal(tglBuat);

            repo.save(db);
            System.out.println("-Berhasil diupdate!-");
        }
        else {
            System.out.println("-Tidak ada data-");
        }
    }

    public void HapusDaftarBelanja(){
        Scanner scan = new Scanner(System.in);
        System.out.println("-Hapus daftar belanja-");
        System.out.print("Masukkan Id: ");
        String inpId = scan.nextLine();

        long id = Long.parseLong(inpId);
        Optional<DaftarBelanja> oDBelanja = repo.findById(id);
        System.out.println("-Data yang dihapus-");
        if (oDBelanja.isPresent()) {
            DaftarBelanja db = oDBelanja.get();
            System.out.println("Judul: " + db.getJudul());
            repo.deleteById(id);
            System.out.println("-Data berhasil dihapus-");
        }
        else {
            System.out.println("-Tidak ada data-");
        }
    }
}
