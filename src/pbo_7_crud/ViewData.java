package pbo_7_crud;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewData extends JFrame {

    /* 
      Membuat variabel "baris" untuk menyimpan baris ke berapa yang dipilih saat 
      user memilih salah satu data yang ada di tabel 
     */
    Integer baris;

    // Membuat variabel "daftarMahasiswa" untuk menyimpan data mahasiswa yg diambil dari DB.
    List<Mahasiswa> daftarMahasiswa = new ArrayList<>();

    // Menginisiasi komponen
    JLabel header = new JLabel("Selamat Datang!");
    JButton tombolTambah = new JButton("Tambah Mahasiswa");
    JButton tombolEdit = new JButton("Edit Mahasiswa");
    JButton tombolHapus = new JButton("Hapus Mahasiswa");

    /*
      Untuk membuat Tabel, kita memerlukan 3 komponen, yaitu:
      1. JTable sebagai komponen tabelnya
      2. DefaultTableModel untuk model atau isinya
      3. JScrollPane supaya tabel dapat di-scroll saat datanya melebihi ukuran layar.
     */
    JTable table;
    DefaultTableModel tableModel;
    JScrollPane scrollPane;

    /*
      Nama kolom tabelnya disimpan ke dalam variabel "namaKolom" yang memiliki 
      tipe data Array String.
     */
    String namaKolom[] = {"ID", "Nama", "NIM"};

    public ViewData() {
        tableModel = new DefaultTableModel(namaKolom, 0);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);

        setTitle("Daftar Mahasiswa");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setSize(552, 540);

        add(header);
        add(scrollPane);
        add(tombolTambah);
        add(tombolEdit);
        add(tombolHapus);

        header.setBounds(20, 8, 440, 24);
        scrollPane.setBounds(20, 36, 512, 320);
        tombolTambah.setBounds(20, 370, 512, 40);
        tombolEdit.setBounds(20, 414, 512, 40);
        tombolHapus.setBounds(20, 456, 512, 40);

        table.setCellSelectionEnabled(false);
        table.setRowSelectionAllowed(true);

        // Memanggil method showData() untuk mengisi tabel dengan data yang diambil dari DB
        showData();

        // Menambahkan event handling ketika salah satu baris di tabel dipilih
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                // Mengambil baris ke-n dari tabel
                baris = table.getSelectedRow();
            }
        });

        // Memberikan event handling ketika tombol "Tambah Mahasiswa" diklik
        tombolTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ketika tombol tambah diklik, maka program akan berpindah ke halaman InputData()
                dispose();
                new InputData();
            }
        });

        // Memberikan event handling ketika tombol "Edit Mahasiswa" diklik
        tombolEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mengecek apakah ada baris di dalam tabel yang dipilih atau tidak
                if (baris != null) {
                    // Mengambil id dan nama berdasarkan baris yang dipilih
                    Integer id = (int) table.getValueAt(baris, 0);
                    String nama = table.getValueAt(baris, 1).toString();
                    String nim = table.getValueAt(baris, 2).toString();
                    
                    /* 
                      Ketika tombol edit diklik, maka program akan berpindah ke 
                      halaman EditData() dengan membawa id, nama, dan nim untuk
                      diberikan ke halaman EditData()
                    */
                    dispose();
                    new EditData(id, nama, nim);
                } else {
                    JOptionPane.showMessageDialog(null, "Data belum dipilih.");
                }
            }
        });

        // Memberikan event handling ketika tombol "Hapus Mahasiswa" diklik
        tombolHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mengecek apakah ada baris di dalam tabel yang dipilih atau tidak
                if (baris != null) {
                    // Mengambil id dan nama berdasarkan baris yang dipilih
                    Integer id = (int) table.getValueAt(baris, 0);
                    String nama = table.getValueAt(baris, 1).toString();

                    // Membuat Pop-Up untuk mengonfirmasi apakah ingin menghapus data
                    int input = JOptionPane.showConfirmDialog(
                            null, 
                            "Hapus " + nama + "?", 
                            "Hapus Mahasiswa", 
                            JOptionPane.YES_NO_OPTION
                    );
                    
                    // Jika user memilih opsi "yes", maka hapus data.
                    if (input == 0) {
                        /* 
                      Memanggil method delete() untuk menghaous data dari DB
                      berdasarkan id yang dipilih.
                         */
                        Connector conn = new Connector();
                        conn.delete(id);

                        JOptionPane.showMessageDialog(null, "Berhasil menghapus data.");

                        dispose();
                        new ViewData();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Data belum dipilih.");
                }
            }
        });
    }

    private void showData() {
        // Memanggil method getAll() untuk mengambil data dari DB.
        Connector conn = new Connector();
        daftarMahasiswa = conn.getAll();

        /*
          Karena daftarMahasiswa memiliki tipe data List, kita harus mengubahnya
          terlebih dahulu ke dalam tipe data Array Object supaya dapat 
          dimasukkan ke dalam table.
         */
        for (int i = 0; i < daftarMahasiswa.size(); i++) {
            int id = daftarMahasiswa.get(i).id;
            String nama = daftarMahasiswa.get(i).nama;
            String nim = daftarMahasiswa.get(i).nim;

            Object[] data = {id, nama, nim};
            tableModel.addRow(data);
        }
    }
}
