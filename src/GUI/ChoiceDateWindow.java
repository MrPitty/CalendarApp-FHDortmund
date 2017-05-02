package GUI;

import Appointment.DateUtil;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static Appointment.DateUtil.getMaxYearValue;
import static Appointment.DateUtil.getMinYearValue;
import static GUI.CalenderWindow.setNewLabel;
import static javax.swing.JOptionPane.showMessageDialog;

/*
  Created by Mr.Pitty on 28.10.2016.
 */

class ChoiceDateWindow extends JDialog {

    private static final long serialVersionUID = 1L;
    private final static short WEIGHT = 270, HEIGHT = 130;
    private final JComboBox<String> comboBoxMonth;
    private JTextField textYearGetYear;

    public ChoiceDateWindow(JFrame owner) {
        super(owner, "Datum angeben", true);
        setResizable(false);
        setSize(WEIGHT, HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, -3, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);

        JLabel lblMonth = new JLabel("Month:");
        GridBagConstraints gbc_lblMonth = new GridBagConstraints();
        gbc_lblMonth.anchor = GridBagConstraints.EAST;
        gbc_lblMonth.insets = new Insets(0, 0, 5, 5);
        gbc_lblMonth.gridx = 0;
        gbc_lblMonth.gridy = 1;
        getContentPane().add(lblMonth, gbc_lblMonth);

        comboBoxMonth = new JComboBox<>(DateUtil.getMonthOfYear());
        comboBoxMonth.setBounds(116, 10, 120, 19);
        GridBagConstraints gbc_comboBox = new GridBagConstraints();
        gbc_comboBox.anchor = GridBagConstraints.WEST;
        gbc_comboBox.insets = new Insets(0, 0, 5, 0);
        gbc_comboBox.gridx = 1;
        gbc_comboBox.gridy = 1;
        getContentPane().add(comboBoxMonth, gbc_comboBox);

        JLabel lblYear = new JLabel("Year:");
        GridBagConstraints gbc_lblYear = new GridBagConstraints();
        gbc_lblYear.anchor = GridBagConstraints.EAST;
        gbc_lblYear.insets = new Insets(0, 0, 5, 5);
        gbc_lblYear.gridx = 0;
        gbc_lblYear.gridy = 2;
        getContentPane().add(lblYear, gbc_lblYear);

        textYearGetYear = new JTextField("2016");
        GridBagConstraints gbc_textFieldGetYear = new GridBagConstraints();
        gbc_textFieldGetYear.anchor = GridBagConstraints.WEST;
        gbc_textFieldGetYear.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldGetYear.gridx = 1;
        gbc_textFieldGetYear.gridy = 2;
        getContentPane().add(textYearGetYear, gbc_textFieldGetYear);
        textYearGetYear.setColumns(8);

        textYearGetYear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    textYearGetYear.setText("");
                }
            }
        });

        JButton btnOk = new JButton("OK");
        btnOk.addActionListener(e -> {
            short year = 2016;
            try {
                year = Short.parseShort(textYearGetYear.getText());
            } catch (NumberFormatException numberFormatException) {
                showMessageDialog(null, "ooops, invalid date");
                return;
            }

            if (year < getMinYearValue() || year > getMaxYearValue()) {
                showMessageDialog(null, "invalid year");
                return;
            }

            setNewLabel((byte) comboBoxMonth.getSelectedIndex(), year);

            dispose();

        });
        GridBagConstraints gbc_btnOk = new GridBagConstraints();
        gbc_btnOk.anchor = GridBagConstraints.EAST;
        gbc_btnOk.insets = new Insets(0, 0, 5, 5);
        gbc_btnOk.gridx = 0;
        gbc_btnOk.gridy = 3;
        getContentPane().add(btnOk, gbc_btnOk);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> this.dispose());
        GridBagConstraints gbc_btnCancel = new GridBagConstraints();
        gbc_btnCancel.anchor = GridBagConstraints.WEST;
        gbc_btnCancel.insets = new Insets(0, 0, 5, 0);
        gbc_btnCancel.gridx = 1;
        gbc_btnCancel.gridy = 3;
        getContentPane().add(btnCancel, gbc_btnCancel);

        setVisible(true);
    }

}
