package GUI;

import Controller.Controller;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSpinnerUI;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;

public class CreaPresentazione {
    public JFrame frame;
    private JPanel contentPanel;
    private JPanel closePanel;
    private JLabel closeBT;
    private JLabel presentazioneLabel;
    private JTextField strutturaField;
    private JTextField viaField;
    private JTextField comuneField;
    private JTextField capField;
    private JTextField ncField;
    private JTextField provinciaField;
    private JTextField nazioneField;
    private JButton aggiungiButton;
    private JLabel calendarIMG;
    private JTextField dataField;
    private JSpinner hourSpinner;
    private JSpinner minutesSpinner;
    private JLabel separatorLabel;
    private DatePickerMoreDay datePicker;

    public CreaPresentazione(JFrame frameC, Controller controller, DefaultTableModel model2){
        datePicker = new DatePickerMoreDay(calendarIMG);

        frame = new JFrame("Crea Presentazione");
        frame.setUndecorated(true);
        frame.setContentPane(this.contentPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(720, 480);
        frame.setLocationRelativeTo(null);
        contentPanel.setBorder(BorderFactory.createMatteBorder(2,2,2,2,new Color(0xEEEEEE)));
        frame.setResizable(false);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                frame.setVisible(false);
                frameC.setEnabled(true);
                frame.dispose();
                frameC.toFront();
            }
        });

        closeBT.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.setVisible(false);
                frameC.setEnabled(true);
                frame.dispose();
                frameC.toFront();
            }
        });

        calendarIMG.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                datePicker.d.setVisible(true);
                dataField.setText(datePicker.setPickedDate());
            }
        });

        hourSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if((int)hourSpinner.getValue() == 24) hourSpinner.setValue(0);
                if((int)hourSpinner.getValue() < 0) hourSpinner.setValue(23);
            }
        });

        minutesSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if((int)minutesSpinner.getValue() == 60) {
                    minutesSpinner.setValue(0);
                    hourSpinner.setValue((int) hourSpinner.getValue() + 1);
                }
                if((int)minutesSpinner.getValue() < 0) {
                    minutesSpinner.setValue(59);
                    hourSpinner.setValue((int) hourSpinner.getValue() + -1);
                }
            }
        });

        aggiungiButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                aggiungiButton.setBackground(Color.decode("#cf9e29"));
            }
        });

        aggiungiButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                aggiungiButton.setBackground(Color.decode("#FFD369"));
            }
        });

        aggiungiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String orario = "";
                if ((int) hourSpinner.getValue() < 10){
                    orario += "0" + hourSpinner.getValue().toString() + ":";
                } else orario += hourSpinner.getValue().toString() + ":";

                if ((int) minutesSpinner.getValue() < 10){
                    orario += "0" + minutesSpinner.getValue().toString();
                } else orario += minutesSpinner.getValue().toString();

                System.out.println(orario);

                if (strutturaField.getText().isBlank() || viaField.getText().isBlank() || ncField.getText().isBlank() || comuneField.getText().isBlank() || capField.getText().isBlank() || nazioneField.getText().isBlank() || dataField.getText().isBlank()){
                    NewShowMessageDialog dialog = new NewShowMessageDialog(2, "Compilare tutti i campi obbligatori");
                } else {
                    if (controller.getDataLibro().after(Date.valueOf(dataField.getText())) == true){
                        NewShowMessageDialog dialog = new NewShowMessageDialog(2, "Questo libro è stato pubblicato dopo!");
                    } else {
                        String indirizzo = "";
                        if(provinciaField.getText().isBlank() || provinciaField.getText().equals("(Opzionale)")) indirizzo = viaField.getText() + " " + ncField.getText() + ", " + capField.getText() + ", " + comuneField.getText() + ", " + nazioneField.getText();
                        else indirizzo = viaField.getText() + " " + ncField.getText() + ", " + capField.getText() + ", " + comuneField.getText() + ", " + provinciaField.getText() + ", " + nazioneField.getText();

                        if(controller.addPresentazione(strutturaField.getText(), indirizzo, dataField.getText(), orario) == false) {
                            NewShowMessageDialog dialog = new NewShowMessageDialog(2, "Questa struttura è gia occupata!");
                        } else {
                            controller.getPresentazione();

                            model2.setRowCount(0);

                            for(int i = 0; i < controller.listaPresentazioni.size(); i++){
                                model2.addRow(new Object[]{controller.listaPresentazioni.get(i).luogo, controller.listaPresentazioni.get(i).struttura, controller.listaPresentazioni.get(i).data, controller.listaPresentazioni.get(i).ora});
                            }

                            frame.setVisible(false);
                            frameC.setEnabled(true);
                            frame.dispose();
                            frameC.toFront();
                        }
                    }

                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        ImageIcon closeImg = new ImageIcon(this.getClass().getResource("/close.png"));
        Image imagine3 = closeImg.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        closeImg = new ImageIcon(imagine3);
        closeBT = new JLabel(closeImg);

        ImageIcon calendarIco = new ImageIcon(this.getClass().getResource("/Calendar2.png"));
        Image calendarIm = calendarIco.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        calendarIco = new ImageIcon(calendarIm);
        calendarIMG = new JLabel(calendarIco);

        strutturaField = new JTextField();
        strutturaField.setBorder(BorderFactory.createLineBorder(new Color(0xFFD369)));

        viaField = new JTextField();
        viaField.setBorder(BorderFactory.createLineBorder(new Color(0xFFD369)));

        ncField = new JTextField();
        ncField.setBorder(BorderFactory.createLineBorder(new Color(0xFFD369)));

        comuneField = new JTextField();
        comuneField.setBorder(BorderFactory.createLineBorder(new Color(0xFFD369)));

        provinciaField = new JTextField();
        provinciaField.setBorder(BorderFactory.createLineBorder(new Color(0xFFD369)));

        capField = new JTextField();
        capField.setBorder(BorderFactory.createLineBorder(new Color(0xFFD369)));

        nazioneField = new JTextField();
        nazioneField.setBorder(BorderFactory.createLineBorder(new Color(0xFFD369)));

        dataField = new JTextField();
        dataField.setBorder(BorderFactory.createLineBorder(new Color(0xFFD369)));

        SpinnerNumberModel snm1 = new SpinnerNumberModel(00, -1, 24, 1);
        hourSpinner = new JSpinner(snm1);

        hourSpinner.setBorder(new LineBorder(new Color(0xFFD369)));

        JComponent editor = hourSpinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();
            textField.setForeground(new Color(0x222831));
            textField.setBackground(new Color(0xFFD369));
            textField.setBorder(new LineBorder(new Color(0x222831)));
        }

        hourSpinner.setUI(new BasicSpinnerUI(){
            ImageIcon upArrow = new ImageIcon(this.getClass().getResource("/up.png"));
            Image uA = upArrow.getImage().getScaledInstance(5, 5, Image.SCALE_SMOOTH);
            ImageIcon downArrow = new ImageIcon(this.getClass().getResource("/down.png"));
            Image dA = downArrow.getImage().getScaledInstance(5, 5, Image.SCALE_SMOOTH);

            @Override
            protected Component createPreviousButton() {
                JButton button = new JButton(new ImageIcon(dA)){
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(15, 15);
                    }
                };
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        spinner.setValue(spinner.getPreviousValue()); // Azione per decrementare il valore
                    }
                });
                button.setBackground(new Color(0x222831)); // Imposta il colore di sfondo del bottone
                button.setBorder(new LineBorder(new Color(0xFFD369)));
                return button;
            }

            @Override
            protected Component createNextButton() {
                JButton button = new JButton(new ImageIcon(uA)){
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(15, 15);
                    }
                };
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        spinner.setValue(spinner.getNextValue()); // Azione per incrementare il valore
                    }
                });
                button.setBackground(new Color(0x222831)); // Imposta il colore di sfondo del bottone
                button.setBorder(new LineBorder(new Color(0xFFD369)));
                return button;
            }
        });

        SpinnerNumberModel snm2 = new SpinnerNumberModel(00, -1, 60, 1);
        minutesSpinner = new JSpinner(snm2);

        minutesSpinner.setBorder(new LineBorder(new Color(0xFFD369)));

        JComponent editor2 = minutesSpinner.getEditor();
        if (editor2 instanceof JSpinner.DefaultEditor) {
            JTextField textField = ((JSpinner.DefaultEditor) editor2).getTextField();
            textField.setForeground(new Color(0x222831));
            textField.setBackground(new Color(0xFFD369));
            textField.setBorder(new LineBorder(new Color(0x222831)));
        }

        minutesSpinner.setUI(new BasicSpinnerUI(){
            ImageIcon upArrow = new ImageIcon(this.getClass().getResource("/up.png"));
            Image uA = upArrow.getImage().getScaledInstance(5, 5, Image.SCALE_SMOOTH);
            ImageIcon downArrow = new ImageIcon(this.getClass().getResource("/down.png"));
            Image dA = downArrow.getImage().getScaledInstance(5, 5, Image.SCALE_SMOOTH);

            @Override
            protected Component createPreviousButton() {
                JButton button = new JButton(new ImageIcon(dA)){
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(15, 15);
                    }
                };
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        spinner.setValue(spinner.getPreviousValue()); // Azione per decrementare il valore
                    }
                });
                button.setBackground(new Color(0x222831)); // Imposta il colore di sfondo del bottone
                button.setBorder(new LineBorder(new Color(0xFFD369)));
                return button;
            }

            @Override
            protected Component createNextButton() {
                JButton button = new JButton(new ImageIcon(uA)){
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(15, 15);
                    }
                };
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        spinner.setValue(spinner.getNextValue()); // Azione per incrementare il valore
                    }
                });
                button.setBackground(new Color(0x222831)); // Imposta il colore di sfondo del bottone
                button.setBorder(new LineBorder(new Color(0xFFD369)));
                return button;
            }
        });
    }
}
