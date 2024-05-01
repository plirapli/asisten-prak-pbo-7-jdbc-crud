package pbo_7_crud;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewData extends JFrame {
    List<Mahasiswa> daftarMahasiswa = new ArrayList<>();
    
    JLabel header = new JLabel("Selamat Datang!");
    JTable table;
    DefaultTableModel tableModel; // Otomatis dibuat kalo buat JTable
    JScrollPane scrollPane;
    
    Object[] namaKolom = {"ID", "Nama", "NIM"};

    public ViewData() {
        tableModel = new DefaultTableModel(namaKolom, 0);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        
        setTitle("Daftar Mahasiswa");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setSize(800,600);
        // setResizable(false);
        
        add(header);
        add(scrollPane);
        
        header.setBounds(20, 8, 440, 24);
        scrollPane.setBounds(20, 36, 500, 300);
        
        showData();
    }
    
    private void showData() {
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