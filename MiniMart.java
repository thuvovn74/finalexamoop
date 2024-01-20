package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MiniMart {
    private JTabbedPane tabbedPane1;
    private JPanel panel_hh;
    private JPanel panel_kh;
    private JPanel pnal_main;
    private JTable table_hh;
    private JTextField textField_id_hh;
    private JTextField textField_ten_hh;
    private JTextField textField_gia_hh;
    private JComboBox comboBox_loai;
    private JTextField textField_soLuong_hh;
    private JButton luu_hh;
    private JButton capNhat_hh;
    private JButton button_xem;
    private JTextField textField_idhh_tk;
    private JButton timKiem_hh;
    private JButton xoa_hh;
    private JPanel panel_main;
    private JPanel panel_kh_Main;
    private JScrollPane jScrollPane;
    private JTable table_kh;
    private JTextField textField_idkh;
    private JTextField textField_sdt;
    private JTextField textField_email;
    private JButton button_luu_kh;
    private JButton button_capnhat_kh;
    private JButton button_xem_kh;
    private JTextField textField_idkh_tk;
    private JButton button_timKiem_kh;
    private JButton button_xoa_kh;
    private JTextField textField_diachi;
    private JTextField textField_tenkh;
    private JComboBox comboBox_tinhTrang;
    private Connection connection;
    private PreparedStatement pst;

    public MiniMart() throws SQLException {
        Connect();
        table_load_hh();
        table_load_kh();

        //LƯU KHÁCH HÀNG
        button_luu_kh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id, name, address, phoneNum, email;

                id = textField_idkh.getText();
                name = textField_tenkh.getText();
                address = textField_diachi.getText();
                phoneNum = textField_sdt.getText();
                email = textField_email.getText();

                try {
                    pst = connection.prepareStatement("insert into Khachhang(Id_kh,Ten_kh,Diachi,Sdt,Email)values(?,?,?,?,?)");
                    pst.setString(1, id);
                    pst.setString(2, name);
                    pst.setString(3, address);
                    pst.setString(4, phoneNum);
                    pst.setString(5, email);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
                    table_load_kh();
                    textField_idkh.setText("");
                    textField_tenkh.setText("");
                    textField_diachi.setText("");
                    textField_sdt.setText("");
                    textField_email.setText("");
                    textField_idkh.requestFocus();
                    table_load_kh();
                }

                catch (SQLException e1)
                {
                    JOptionPane.showConfirmDialog(null, "Lỗi" + e1.getMessage());
                    e1.printStackTrace();
                }
            }
        });

        //XÓA KHÁCH HÀNG
        button_xoa_kh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_tk;
                id_tk = textField_idkh_tk.getText();

                try {
                    pst = connection.prepareStatement("delete from Khachhang  where Id_kh = ?");

                    pst.setString(1, id_tk);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleteeeeee!!!!!");
                    table_load_kh();
                    textField_idkh.requestFocus();
                }

                catch (SQLException e1)
                {
                    JOptionPane.showConfirmDialog(null, "Error: " + e1.getMessage());
                    e1.printStackTrace();
                }
            }
        });

        //TÌM KIẾM KHÁCH HÀNG
        button_timKiem_kh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    try {

                        String idkh_tk = textField_idkh_tk.getText();

                        pst = connection.prepareStatement("SELECT * FROM Khachhang WHERE Id_kh LIKE ?");
                        pst.setString(1, "%" + idkh_tk + "%");
                        ResultSet rs = pst.executeQuery();

                        if(rs.next()==true)
                        {
                            String id = rs.getString(1);
                            String name = rs.getString(2);
                            String address = rs.getString(3);
                            String phoneNumber = rs.getString(4);
                            String email = rs.getString(5);

                            textField_idkh.setText(id);
                            textField_tenkh.setText(name);
                            textField_diachi.setText(address);
                            textField_sdt.setText(phoneNumber);
                            textField_email.setText(email);

                        }
                        else
                        {
                            textField_idkh.setText("");
                            textField_tenkh.setText("");
                            textField_diachi.setText("");
                            textField_sdt.setText("");
                            textField_email.setText("");
                            textField_idkh.requestFocus();
                            JOptionPane.showMessageDialog(null,"Invalid Employee No");
                        }

                    } catch (SQLException ex) {
                        JOptionPane.showConfirmDialog(null, "Lỗi: " + ex.getMessage());
                        ex.printStackTrace();
                    }
            }
        });

        //XEM DANH SÁCH KHÁCH HÀNG
        button_xem_kh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table_load_kh();
            }
        });

        //CẬP NHẬT DANH SÁCH KHÁCH HÀNG
        button_capnhat_kh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id, name, address, phoneNum, email;

                id = textField_idkh.getText();
                name = textField_tenkh.getText();
                address = textField_diachi.getText();
                phoneNum = textField_sdt.getText();
                email = textField_email.getText();

                try {
                    pst = connection.prepareStatement("update Khachhang set Ten_kh = ?,Diachi = ?,Sdt = ?,Email = ? where Id_kh = ?");
                    pst.setString(1, name);
                    pst.setString(2, address);
                    pst.setString(3, phoneNum);
                    pst.setString(4, email);
                    pst.setString(5, id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updateee!!!!!");
                    table_load_kh();
                    textField_idkh.setText("");
                    textField_tenkh.setText("");
                    textField_diachi.setText("");
                    textField_sdt.setText("");
                    textField_email.setText("");
                    textField_idkh.requestFocus();
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });

        //LƯU HÀNG HÓA
        luu_hh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id, name, price, type, condition, quantity;

                Object type_ob = comboBox_loai.getSelectedItem();
                Object condition_ob = comboBox_tinhTrang.getSelectedItem();
                id = textField_id_hh.getText();
                name = textField_ten_hh.getText();
                price = textField_gia_hh.getText();
                type = (String) type_ob;
                condition = (String) condition_ob;
                quantity = textField_soLuong_hh.getText();

                try {
                    pst = connection.prepareStatement("insert into Hanghoa(Id_hh,Ten_hh,Gia,Loai,Tinhtrang,Soluong)values(?,?,?,?,?,?)");
                    pst.setString(1, id);
                    pst.setString(2, name);
                    pst.setString(3, price);
                    pst.setString(4, type);
                    pst.setString(5, condition);
                    pst.setString(6,quantity);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");
                    table_load_kh();
                    textField_id_hh.setText("");
                    textField_ten_hh.setText("");
                    textField_gia_hh.setText("");
                    comboBox_loai.setSelectedItem("Thực phẩm");
                    comboBox_tinhTrang.setSelectedItem("Còn");
                    textField_soLuong_hh.setText("");
                    textField_id_hh.requestFocus();
                    table_load_hh();
                }

                catch (SQLException e1)
                {
                    JOptionPane.showConfirmDialog(null, "Lỗi" + e1.getMessage());
                    e1.printStackTrace();
                }
            }
        });

        //XEM DANH SÁCH HÀNG HÓA
        button_xem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table_load_hh();
            }
        });

        //XÓA HÀNG HÓA
        xoa_hh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id_tk;
                id_tk = textField_idhh_tk.getText();

                try {
                    pst = connection.prepareStatement("delete from Hanghoa  where Id_hh = ?");

                    pst.setString(1, id_tk);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleteeeeee!!!!!");
                    table_load_hh();
                    textField_idkh.requestFocus();
                }

                catch (SQLException e1)
                {
                    JOptionPane.showConfirmDialog(null, "Error: " + e1.getMessage());
                    e1.printStackTrace();
                }
            }
        });
        timKiem_hh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String idhh_tk = textField_idhh_tk.getText();

                    pst = connection.prepareStatement("SELECT * FROM Hanghoa WHERE Id_hh LIKE ?");
                    pst.setString(1, "%" + idhh_tk + "%");
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String id = rs.getString(1);
                        String name = rs.getString(2);
                        String price = rs.getString(3);
                        String type = rs.getString(4);
                        String condition = rs.getString(5);
                        String quantity = rs.getString(6);

                        textField_id_hh.setText(id);
                        textField_ten_hh.setText(name);
                        textField_gia_hh.setText(price);
                        comboBox_loai.setSelectedItem(type);
                        comboBox_tinhTrang.setSelectedItem(condition);
                        textField_soLuong_hh.setText(quantity);

                    }
                    else
                    {
                        textField_id_hh.setText("");
                        textField_ten_hh.setText("");
                        textField_gia_hh.setText("");
                        comboBox_loai.setSelectedItem("Thực phẩm");
                        comboBox_tinhTrang.setSelectedItem("Còn");
                        textField_soLuong_hh.setText("");
                        textField_id_hh.requestFocus();
                        JOptionPane.showMessageDialog(null,"Invalid Employee No");
                    }

                } catch (SQLException ex) {
                    JOptionPane.showConfirmDialog(null, "Lỗi: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        capNhat_hh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id, name, price, type, condition, quantity;

                Object type_ob = comboBox_loai.getSelectedItem();
                Object condition_ob = comboBox_tinhTrang.getSelectedItem();

                id = textField_id_hh.getText();
                name = textField_ten_hh.getText();
                price = textField_gia_hh.getText();
                type = (String) type_ob;
                condition = (String) condition_ob;
                quantity = textField_soLuong_hh.getText();
                try {
                        pst = connection.prepareStatement("update Hanghoa set Ten_hh = ?,Gia = ?,Loai = ?,Tinhtrang = ?,Soluong = ? where Id_hh = ?");
                        pst.setString(1, name);
                        pst.setString(2, price);
                        pst.setString(3, type);
                        pst.setString(4, condition);
                        pst.setString(5, quantity);
                        pst.setString(6, id);

                        pst.executeUpdate();
                        table_load_hh();

                        JOptionPane.showMessageDialog(null, "Record Updateee!!!!!");
                        textField_id_hh.setText("");
                        textField_ten_hh.setText("");
                        textField_gia_hh.setText("");
                        comboBox_loai.setSelectedItem("Thực phẩm");
                        comboBox_tinhTrang.setSelectedItem("Còn");
                        textField_soLuong_hh.setText("");
                        textField_id_hh.requestFocus();
                        table_load_hh();
                }

                catch (SQLException e1)
                {
                    JOptionPane.showConfirmDialog(null, "Lỗi: " + e1.getMessage());
                    e1.printStackTrace();
                }
            }
        });
    }

    public void Connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MiniMart", "root", "123456");
            System.out.println("Thành công");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Thất bại");
        }
    }

    public void table_load_kh() {
        try
        {
            pst = connection.prepareStatement("select * from Khachhang");
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new Object[]{"Id_kh", "Ten_kh", "Diachi", "Sdt", "Email"});

            table_kh.setModel(model);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getObject("Id_kh"),
                        rs.getObject("Ten_kh"),
                        rs.getObject("Diachi"),
                        rs.getObject("Sdt"),
                        rs.getObject("Email")
                });
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void table_load_hh() {
        try
        {
            pst = connection.prepareStatement("select * from Hanghoa");
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new Object[]{"Id_hh", "Ten_hh", "Gia", "Tinhtrang", "Loai","Soluong"});

            table_hh.setModel(model);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getObject("Id_hh"),
                        rs.getObject("Ten_hh"),
                        rs.getObject("Gia"),
                        rs.getObject("Tinhtrang"),
                        rs.getObject("Loai"),
                        rs.getObject("Soluong")
                });
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        JFrame jFrame = new JFrame("Mini mart");
        jFrame.setContentPane(new MiniMart().panel_main);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setSize(900,600);
        jFrame.setVisible(true);
    }
}
