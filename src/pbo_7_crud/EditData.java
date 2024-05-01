package pbo_7_crud;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class EditData extends JFrame {

    JLabel header = new JLabel("Edit Mahasiswa");
    JLabel labelInputNama = new JLabel("Nama");
    JLabel labelInputNIM = new JLabel("NIM");
    JTextField inputNama = new JTextField();
    JTextField inputNIM = new JTextField();
    JButton tombolEdit = new JButton("Edit Mahasiswa");
    JButton tombolKembali = new JButton("Kembali");

    public EditData(int id, String nama, String nim) {
        setTitle("Edit Mahasiswa");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setSize(480, 240);

        add(header);
        add(labelInputNama);
        add(labelInputNIM);
        add(inputNama);
        add(inputNIM);
        add(tombolEdit);
        add(tombolKembali);

        header.setBounds(20, 8, 440, 24);
        labelInputNama.setBounds(20, 32, 440, 24);
        inputNama.setBounds(18, 56, 440, 36);
        labelInputNIM.setBounds(20, 96, 440, 24);
        inputNIM.setBounds(18, 120, 440, 36);
        tombolKembali.setBounds(20, 160, 215, 40);
        tombolEdit.setBounds(240, 160, 215, 40);
        
        inputNama.setText(nama);
        inputNIM.setText(nim);

        /* 
          Memberikan event handling untuk tombol kembali,
          Ketika tombol kembali diklik, maka akan kembali ke halaman ViewData().
         */
        tombolKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ViewData();
            }
        });

        tombolEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Mengambil input nama dan nim dan disimpan ke dalam variabel
                    String nama = inputNama.getText();
                    String nim = inputNIM.getText();
                    
                    /*
                      Mengecek apakah input dari nama atau nim kosong/tidak.
                      Jika kosong, maka buatlah sebuah exception.
                    */
                    if ("".equals(nama) || "".equals(nim)) {
                        throw new Exception("Nama atau NIM tidak boleh kosong!");
                    }
                    
                    // Menjalankan update data mahasiswa
                    Connector conn = new Connector();
                    conn.update(id, nama, nim);
                    
                    // Menampilkan pop-up ketika berhasil mengedit data
                    JOptionPane.showMessageDialog(null, "Berhasil mengedit data.");
                    
                    // Terakhir, program akan pindah ke halaman ViewData()
                    dispose();
                    new ViewData();
                } catch (Exception exception) {
                    // Menampilkan pop-up ketika terjadi error
                    JOptionPane.showMessageDialog(null, "Error: " + exception.getMessage());
                }
            }
        });
    }
}
