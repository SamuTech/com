package com;
//this is the code for the registration form

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.Font;

import javax.swing.border.BevelBorder;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Stack;

public class RegistrationForm extends JFrame implements AutoCloseable {

    JPanel panel = new JPanel();
    JPanel panel2 = new JPanel();

    JComboBox<String> yearsField = new JComboBox<>();
    JComboBox<String> addressField;

    JTextField firstNameField = new JTextField(20);
    JTextField lastNameField = new JTextField(20);
    JTextField userNameField = new JTextField(20);
    JTextField phoneNumbersField = new JTextField(20);
    JTextField emailAddressField = new JTextField(40);

    JPasswordField confirmPasswordField = new JPasswordField();
    JPasswordField passwordField = new JPasswordField();

    private static JLabel userNameLabel = new JLabel("User Name");
    private static JLabel yearOfBirthLabel = new JLabel("Year of Birth");
    private static JLabel firstNameLabel = new JLabel("First Name");
    private static JLabel lastNameLabel = new JLabel("Last Name");
    private static JLabel addressLabel = new JLabel("Address");
    private static JLabel EmailAddressLabel = new JLabel("Email Address");
    private static JLabel phoneNumberLabel = new JLabel("Phone Number");
    private static JLabel passwordLabel = new JLabel("Password");
    private static JLabel confirmPasswordLabel = new JLabel("Confirm Password");
    private static JLabel registrationFormTitleLabel = new JLabel("UNimail SIGN UP FORM");

    JButton signInButton = new JButton("BACK TO \tSIGN IN");
    JButton submitButton = new JButton("SUBMIT");

    JPanel errorPane = new JPanel();

    private static Stack selectableYears;
    private static Stack placeNamesIn_DB;

    public RegistrationForm() {

        //setVisible(true);
        setSize(600, 700);
        setTitle("DeskTop Gmail");
        setLocation(300, 50);
        setResizable(false);
        setBackground(Color.WHITE);
        setIconImage(new ImageIcon("anonimas.jpg").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //String[] phoneNumbers = {"07347864", "573356", "347634546"};
        placeNamesIn_DB = new Stack();
        selectableYears = new Stack();
        submitButton.setSize(30, 30);
        
        errorPane.setLayout(null);
        errorPane.setBackground(Color.BLUE);
        registrationFormTitleLabel.setBounds((errorPane.getLocation().x) + 30,(errorPane.getLocation().y) -10 ,540,50);
        registrationFormTitleLabel.setForeground(Color.WHITE);
        registrationFormTitleLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
        errorPane.add(registrationFormTitleLabel);
        submitButton.setBackground(Color.blue);
        submitButton.setForeground(Color.WHITE);

        //Connect to the database and obtain  years
        try {

            String SELECT_QUERY = "SELECT year FROM years ORDER BY year ";
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gmail", "root", "");
            Statement sqlStatement = connection.createStatement();
            ResultSet resultSet = sqlStatement.executeQuery(SELECT_QUERY);

            while (resultSet.next()) {

                selectableYears.push(resultSet.getString("year"));

            }
            connection.close();

        } catch (SQLException e) {

            Toolkit.getDefaultToolkit().beep();
            //JOptionPane.showMessageDialog(panel, e.getMessage());
            System.err.println("Line 111"+e.getMessage());
            
        } catch (SecurityException e) {

            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "A security breach was detected in your System\n An automatic shut down is scheduled", "SECURITY BREACH WARNING", JOptionPane.WARNING_MESSAGE);

        } catch (StringIndexOutOfBoundsException e) {
        }
        //ADD  YEAR VALUES TO YEARS TEXT FIELD 
        yearsField = new JComboBox<>(selectableYears);

        //CONNECT TO DB AND OBTAIN NAMES OF PLACES
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gmail", "root", "");
            Statement sqlStatement = connection.createStatement();) {

            String SELECT_QUERY = "SELECT place FROM places ORDER BY place";
            ResultSet resultSet = sqlStatement.executeQuery(SELECT_QUERY);

            while (resultSet.next()) {

                placeNamesIn_DB.push(resultSet.getString("place"));

            }
            
            connection.close();
            
        } catch (SQLException e) {

            Toolkit.getDefaultToolkit().beep();
            //JOptionPane.showMessageDialog(panel, e.getMessage());
            System.err.println("Line 142"+e.getMessage());
            
        } catch (SecurityException e) {

            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "A security breach was detected in your System\n An automatic shut down is scheduled", "SECURITY BREACH WARNING" + e.getMessage(), JOptionPane.WARNING_MESSAGE);

        } catch (StringIndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, "" + e.getMessage());
        }

        //ADD  PLACE NAMES  TO ADDRESS  TEXT FIELD 
        addressField = new JComboBox<>(placeNamesIn_DB);

        //SET UP COMBO BOX FOR THE DATE_OF_BIRTH FIELD
        yearsField.setPrototypeDisplayValue("1997");
        yearsField.setEditable(true);

        //errorPane.setEditable(false);

        //SET UP FIELDS FOR THE ADDRESSES FIELD
        yearsField.setPrototypeDisplayValue("Kakamega");
        addressField.setEditable(true);

        //SET UP PHONE NUMBER FIELD BOX
        phoneNumbersField.setEditable(true);
        emailAddressField.setEditable(true);
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridLayout(20, 1, 2, 2));
        panel2.setBackground(Color.WHITE);

        panel.add(errorPane);
        panel.add(firstNameLabel);
        panel.add(firstNameField);
        panel.add(lastNameLabel);
        panel.add(lastNameField);
        panel.add(addressLabel);
        panel.add(addressField);
        panel.add(yearOfBirthLabel);
        panel.add(yearsField);
        panel.add(phoneNumberLabel);
        panel.add(phoneNumbersField);
        panel.add(userNameLabel);
        panel.add(userNameField);
        panel.add(EmailAddressLabel);
        panel.add(emailAddressField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(confirmPasswordLabel);
        panel.add(confirmPasswordField);
        panel.add(panel2);

        signInButton.setBackground(Color.blue);
        signInButton.setBackground(Color.WHITE);

        //ADD COMPONENTS TO BOTTOM PANEL 2
        panel2.add(submitButton, BorderLayout.WEST);
        panel2.add(signInButton, BorderLayout.EAST);
        submitButton.setEnabled(false);

        add(panel);

        //ADD FOCUS LISTENERS
        firstNameField.addFocusListener(
                new FocusListener() {

            @Override
            public void focusGained(FocusEvent event) {

                firstNameField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLUE, Color.BLUE));
            }

            @Override
            public void focusLost(FocusEvent event) {

                if (firstNameField.getText().equals("")) {

                    firstNameField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.RED, Color.RED));
                } else {

                    firstNameField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.lightGray, Color.lightGray));
                }

            }
        }
        );

        lastNameField.addFocusListener(
                new FocusListener() {

            @Override
            public void focusGained(FocusEvent event) {

                lastNameField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLUE, Color.BLUE));
            }

            @Override
            public void focusLost(FocusEvent event) {

                if (lastNameField.getText().equals("")) {

                    lastNameField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.RED, Color.RED));
                } else {

                    lastNameField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.lightGray, Color.lightGray));
                }

            }
        }
        );

        addressField.addFocusListener(
                new FocusListener() {

            @Override
            public void focusGained(FocusEvent event) {

                addressField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLUE, Color.BLUE));
            }

            @Override
            public void focusLost(FocusEvent event) {

                if (addressField.getSelectedItem().equals("")) {

                    addressField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.RED, Color.RED));
                } else {

                    addressField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.lightGray, Color.lightGray));
                }

            }
        }
        );

        yearsField.addFocusListener(
                new FocusListener() {

            @Override
            public void focusGained(FocusEvent event) {

                yearsField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLUE, Color.BLUE));
            }

            @Override
            public void focusLost(FocusEvent event) {

                if (yearsField.getSelectedItem().equals("")) {

                    yearsField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.RED, Color.RED));
                } else {

                    yearsField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.lightGray, Color.lightGray));
                }

            }
        }
        );

        phoneNumbersField.addFocusListener(
                new FocusListener() {

            @Override
            public void focusGained(FocusEvent event) {

                phoneNumbersField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLUE, Color.BLUE));
            }

            @Override
            public void focusLost(FocusEvent event) {

                if (phoneNumbersField.getText().equals("")) {

                    phoneNumbersField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.RED, Color.RED));
                } else {

                    phoneNumbersField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.lightGray, Color.lightGray));
                }

            }
        }
        );

        userNameField.addFocusListener(
                new FocusListener() {

            @Override
            public void focusGained(FocusEvent event) {

                userNameField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLUE, Color.BLUE));
            }

            @Override
            public void focusLost(FocusEvent event) {

                if (userNameField.getText().equals("")) {

                    userNameField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.RED, Color.RED));
                } else {

                    userNameField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.lightGray, Color.lightGray));
                }

            }
        }
        );

        passwordField.addFocusListener(
                new FocusListener() {

            @Override
            public void focusGained(FocusEvent event) {

                passwordField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLUE, Color.BLUE));
            }

            @Override
            public void focusLost(FocusEvent event) {

                if (passwordField.getText().equals("")) {

                    Toolkit.getDefaultToolkit().beep();
                    passwordField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.RED, Color.RED));
                } else {

                    passwordField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.lightGray, Color.lightGray));
                }

            }
        }
        );

        confirmPasswordField.addFocusListener(
                new FocusListener() {

            @Override
            public void focusGained(FocusEvent event) {

                confirmPasswordField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLUE, Color.BLUE));
            }

            @Override
            public void focusLost(FocusEvent event) {

                if (confirmPasswordField.getText().equals("")) {

                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(panel2, "Kindly Confirm you password!");
                    confirmPasswordField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.RED, Color.RED));
                } else {

                    confirmPasswordField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.lightGray, Color.lightGray));
                }

            }
        }
        );

        emailAddressField.addFocusListener(
                new FocusListener() {

            @Override
            public void focusGained(FocusEvent event) {

                emailAddressField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLUE, Color.BLUE));
            }

            @Override
            public void focusLost(FocusEvent event) {

                if (emailAddressField.getText().equals("")) {

                    Toolkit.getDefaultToolkit().beep();
                    emailAddressField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.RED, Color.RED));
                } else {

                    emailAddressField.setBorder(new BevelBorder(BevelBorder.RAISED, Color.lightGray, Color.lightGray));
                }

            }
        }
        );
    }
    
    @Override
    public void close(){
        
        System.err.println("All reasorces are now closed...");
    }

    public synchronized Boolean comparePasswords(String entered_Pass, String confimr_pass) {

        if (entered_Pass.equals(confimr_pass)) {
            return true;
        } else {
            return false;
        }
    }

}
