/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networks_project;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SignupFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JTextField mobileField;
    private JTextField addressField;

    private Connection con;
    private PreparedStatement memberSt;

    public SignupFrame() {
        setTitle("Signup Page");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(200, 230, 201));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Signup");
        titleLabel.setBounds(150, 30, 100, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(100, 70, 80, 25);
        panel.add(userLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(200, 70, 150, 25);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(100, 110, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(200, 110, 150, 25);
        panel.add(passwordField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(100, 150, 80, 25);
        panel.add(emailLabel);

        emailField = new JTextField(20);
        emailField.setBounds(200, 150, 150, 25);
        panel.add(emailField);

        JLabel mobileLabel = new JLabel("Mobile:");
        mobileLabel.setBounds(100, 190, 80, 25);
        panel.add(mobileLabel);

        mobileField = new JTextField(20);
        mobileField.setBounds(200, 190, 150, 25);
        panel.add(mobileField);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(100, 230, 80, 25);
        panel.add(addressLabel);

        addressField = new JTextField(20);
        addressField.setBounds(200, 230, 150, 25);
        panel.add(addressField);

        JButton signupButton = new JButton("Signup");
        signupButton.setBounds(150, 280, 100, 30);
        panel.add(signupButton);

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String email = emailField.getText();
                String mobile = mobileField.getText();
                String address = addressField.getText();

                memberSignup(username, password, email, mobile, address);
            }
        });
    }

    private void memberSignup(String username, String password, String email, String mobile, String address) {
        String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
        String dbUser = "system";
        String dbPassword = "karmegam";

        try {
            con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            String sql = "INSERT INTO userss (username, password, email, mobile_number, address) VALUES (?, ?, ?, ?, ?)";
            memberSt = con.prepareStatement(sql);
            memberSt.setString(1, username);
            memberSt.setString(2, password);
            memberSt.setString(3, email);
            memberSt.setString(4, mobile);
            memberSt.setString(5, address);
            memberSt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Member Signup Successful");
            dispose();
        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(null, "User already exists");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "An error occurred while signing up. Please try again.");
        } finally {
            closeResources();
        }
    }

    private void closeResources() {
        try {
            if (memberSt != null) memberSt.close();
            if (con != null) con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Use the system look and feel
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                new SignupFrame();
            }
        });
    }
}
