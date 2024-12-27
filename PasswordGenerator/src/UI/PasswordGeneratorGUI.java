package UI;

import Core.PasswordGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PasswordGeneratorGUI extends JFrame {
    private final PasswordGenerator passwordGenerator;

    public PasswordGeneratorGUI() {
        super("PG App");
        setSize(550, 580);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(225, 247, 245));


        ImageIcon icon = new ImageIcon("src\\Assets\\icon.png");

        Image image = icon.getImage();

        // Set the icon for the JFrame
        setIconImage(image);
        passwordGenerator = new PasswordGenerator();
        addGuiComponents();
    }
    private JToggleButton createToggleButton(String text, int x, int y) {

        JToggleButton toggleButton = new JToggleButton(text);
        toggleButton.setFont(new Font("Dialog", Font.PLAIN, 26));
        toggleButton.setBounds(x, y, 225, 56);
        toggleButton.setBackground(new Color(100, 100, 255));
        toggleButton.setForeground(Color.WHITE);
        return toggleButton;
    }
    private void addGuiComponents() {
        Font font = new Font("Arial", Font.BOLD, 22);
        Color textColor = Color.BLACK;

        JLabel titleLabel = new JLabel("Password Generator By Eng. Bakri Mousli");
        titleLabel.setFont(font);
        titleLabel.setForeground(textColor);
        titleLabel.setBounds(40, 30, 450, 50);
        add(titleLabel);


        JTextArea passwordOutput = new JTextArea();
        passwordOutput.setEditable(false);
        passwordOutput.setFont(new Font("Dialog", Font.BOLD, 32));
        JScrollPane passwordOutputPane = new JScrollPane(passwordOutput);
        passwordOutputPane.setBounds(25, 97, 479, 70);
<<<<<<< HEAD
        passwordOutputPane.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 200)));
=======
        passwordOutputPane.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 200))); 
>>>>>>> 3ab900264768866a456caf8f386599f37ad9133b
        add(passwordOutputPane);

        JLabel passwordLengthLabel = new JLabel("Password Length: ");
        passwordLengthLabel.setFont(new Font("Dialog", Font.PLAIN, 32));
        passwordLengthLabel.setBounds(25, 215, 272, 39);
        passwordLengthLabel.setForeground(new Color(100, 100, 255));
        add(passwordLengthLabel);

        JTextArea passwordLengthInputArea = new JTextArea();
        passwordLengthInputArea.setFont(new Font("Dialog", Font.PLAIN, 32));
<<<<<<< HEAD
        passwordLengthInputArea.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 200)));
=======
        passwordLengthInputArea.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 200))); 
>>>>>>> 3ab900264768866a456caf8f386599f37ad9133b
        passwordLengthInputArea.setBounds(310, 215, 192, 39);
        add(passwordLengthInputArea);

        JToggleButton uppercaseToggle = createToggleButton("Uppercase", 25, 302);
        JToggleButton lowercaseToggle = createToggleButton("Lowercase", 282, 302);
        JToggleButton numbersToggle = createToggleButton("Numbers", 25, 373);
        JToggleButton symbolsToggle = createToggleButton("Symbols", 282, 373);

        ActionListener toggleListener = e -> {
            AbstractButton button = (AbstractButton) e.getSource();
            if (button.isSelected()) {
                button.setForeground(Color.BLACK);
                button.setBackground(Color.RED);

            } else {
                button.setBackground(new Color(100, 100, 255));
                button.setForeground(Color.WHITE);
            }
        };


        uppercaseToggle.addActionListener(toggleListener);
        lowercaseToggle.addActionListener(toggleListener);
        numbersToggle.addActionListener(toggleListener);
        symbolsToggle.addActionListener(toggleListener);

        add(uppercaseToggle);
        add(lowercaseToggle);
        add(numbersToggle);
        add(symbolsToggle);


        JButton generateButton = new JButton("Generate");
        generateButton.setFont(new Font("Dialog", Font.PLAIN, 32));
        generateButton.setBounds(155, 477, 222, 50);
        generateButton.setBackground(new Color(50, 50, 200));
        generateButton.setForeground(Color.WHITE);
        generateButton.addActionListener(e -> {
            if(passwordLengthInputArea.getText().isEmpty()) return;
            boolean anyToggleSelected = lowercaseToggle.isSelected() ||
                    uppercaseToggle.isSelected() ||
                    numbersToggle.isSelected() ||
                    symbolsToggle.isSelected();
            int passwordLength = Integer.parseInt(passwordLengthInputArea.getText());
            if(anyToggleSelected){
                String generatedPassword = passwordGenerator.generatePassword(passwordLength,
                        uppercaseToggle.isSelected(),
                        lowercaseToggle.isSelected(),
                        numbersToggle.isSelected(),
                        symbolsToggle.isSelected());
                passwordOutput.setText(generatedPassword);
            }
        });
        add(generateButton);
    }

    }

