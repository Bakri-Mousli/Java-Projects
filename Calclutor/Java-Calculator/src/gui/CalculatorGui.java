package gui;
import constants.CommonConstants;
import service.CalculatorService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorGui extends JFrame implements ActionListener {
    private final SpringLayout springLayout = new SpringLayout();
    private final CalculatorService calculatorService;

    // Display field
    private final JTextField displayField;

    // Buttons
    private final JButton[] buttons;

    // Flags
    private boolean pressedOperator = false;
    private boolean pressedEquals = false;

    public CalculatorGui() {
        super(CommonConstants.APP_NAME);
        setSize(CommonConstants.APP_SIZE[0], CommonConstants.APP_SIZE[1]);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(springLayout);
        getContentPane().setBackground(new Color(251, 168, 52)); // Set background color
        ImageIcon icon = new ImageIcon("C:\\Projects-and-Folders\\Informatics-Engineering\\Third-Year\\Second-Season\\Advanced-Algorithms\\Java-Projects\\Calclutor\\Java-Calculator\\src\\Assets\\icon.png");
        Image image = icon.getImage();

        setIconImage(image);
        calculatorService = new CalculatorService();

        // Initialize display field
        displayField = new JTextField(CommonConstants.TEXTFIELD_LENGTH);
        displayField.setFont(new Font("Arial", Font.PLAIN, CommonConstants.TEXTFIELD_FONTSIZE));
        displayField.setEditable(false);
        displayField.setText("0");
        displayField.setHorizontalAlignment(SwingConstants.RIGHT);
        displayField.setBackground(new Color(255, 255, 255)); // Set background color
        displayField.setForeground(new Color(51, 58, 115)); // Set foreground color
        addComponent(displayField, springLayout.NORTH, CommonConstants.TEXTFIELD_SPRINGLAYOUT_NORTHPAD,
                springLayout.WEST, CommonConstants.TEXTFIELD_SPRINGLAYOUT_WESTPAD, this);

        // Initialize buttons
        buttons = new JButton[CommonConstants.BUTTON_COUNT];
        for (int i = 0; i < CommonConstants.BUTTON_COUNT; i++) {
            JButton button = new JButton(getButtonLabel(i));
            button.setFont(new Font("Arial", Font.BOLD, CommonConstants.BUTTON_FONTSIZE));
            button.setBackground(new Color(255, 255, 255)); // Set background color
            button.setForeground(new Color(51, 58, 115)); // Set foreground color
            button.addActionListener(this);
            buttons[i] = button;
        }
        addButtons(buttons, springLayout.NORTH, CommonConstants.BUTTON_SPRINGLAYOUT_NORTHPAD,
                springLayout.WEST, CommonConstants.BUTTON_SPRINGLAYOUT_WESTPAD, this);
    }

    // Add component with SpringLayout constraints
    private void addComponent(Component component, String verticalConstraint, int verticalPad,
                              String horizontalConstraint, int horizontalPad, Container container) {
        container.add(component);
        springLayout.putConstraint(verticalConstraint, component, verticalPad, verticalConstraint, container);
        springLayout.putConstraint(horizontalConstraint, component, horizontalPad, horizontalConstraint, container);
    }

    // Add buttons with SpringLayout constraints
    private void addButtons(JButton[] buttons, String verticalConstraint, int verticalPad,
                            String horizontalConstraint, int horizontalPad, Container container) {
        JPanel buttonPanel = new JPanel(new GridLayout(CommonConstants.BUTTON_ROWCOUNT, CommonConstants.BUTTON_COLCOUNT,
                CommonConstants.BUTTON_HGAP, CommonConstants.BUTTON_VGAP));
        buttonPanel.setBackground(new Color(251, 168, 52)); // Set background color
        for (JButton button : buttons) {
            buttonPanel.add(button);
        }
        addComponent(buttonPanel, verticalConstraint, verticalPad, horizontalConstraint, horizontalPad, container);
    }

    // Get label for button index
    public String getButtonLabel(int buttonIndex) {
        switch (buttonIndex) {
            case 0:
                return "7";
            case 1:
                return "8";
            case 2:
                return "9";
            case 3:
                return "/";
            case 4:
                return "4";
            case 5:
                return "5";
            case 6:
                return "6";
            case 7:
                return "x";
            case 8:
                return "1";
            case 9:
                return "2";
            case 10:
                return "3";
            case 11:
                return "-";
            case 12:
                return "0";
            case 13:
                return ".";
            case 14:
                return "+";
            case 15:
                return "=";
        }
        return "";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonCommand = e.getActionCommand();
        if (buttonCommand.matches("[0-9]")) {
            if (pressedEquals || pressedOperator || displayField.getText().equals("0"))
                displayField.setText(buttonCommand);
            else
                displayField.setText(displayField.getText() + buttonCommand);

            // update flags
            pressedOperator = false;
            pressedEquals = false;
        } else if (buttonCommand.equals("=")) {
            // calculate
            calculatorService.setNum2(Double.parseDouble(displayField.getText()));

            double result = 0;
            switch (calculatorService.getMathSymbol()) {
                case '+':
                    result = calculatorService.add();
                    break;
                case '-':
                    result = calculatorService.subtract();
                    break;
                case '/':
                    result = calculatorService.divide();
                    break;
                case 'x':
                    result = calculatorService.multiply();
                    break;
            }

            // update the display field
            displayField.setText(Double.toString(result));

            // update flags
            pressedEquals = true;
            pressedOperator = false;

        } else if (buttonCommand.equals(".")) {
            if (!displayField.getText().contains(".")) {
                displayField.setText(displayField.getText() + buttonCommand);
            }
        } else { // operator
            if (!pressedOperator)
                calculatorService.setNum1(Double.parseDouble(displayField.getText()));

            calculatorService.setMathSymbol(buttonCommand.charAt(0));

            // update flags
            pressedOperator = true;
            pressedEquals = false;
        }
    }
}
