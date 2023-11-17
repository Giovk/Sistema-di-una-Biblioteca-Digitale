package GUI;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NewShowMessageDialog extends JDialog {
    private JPanel contentPane;
    private JLabel typeLabel;
    private JLabel typeMessageLabel;
    private JButton okButton;
    private int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    public int fontSize = getFontSize();
    public Font baseFontSize = new Font("Segoe UI", Font.PLAIN, fontSize);
    public Font impactFontSize = new Font("Impact", Font.PLAIN, fontSize);
    public Font textFieldFont = new Font("Berlin Sans FB", Font.PLAIN, fontSize-2);

    public NewShowMessageDialog(int typeIcon, String message) {
        this.setUndecorated(true);  //rimuove la decorazione della finestra
        this.setSize((int) (screenWidth/8.5333),(int) (screenHeight/7.2));  //imposta le dimensioni della finestra
        this.setLocationRelativeTo(null);   //posiziona il frame al centro dello schermo
        setContentPane(contentPane);
        setModal(true); //imposta la finestra come modal
        getRootPane().setDefaultButton(okButton); //imposta il JButton 'okButton' come bottone predefinito della finestra

        contentPane.setBorder(BorderFactory.createMatteBorder(2,2,2,2,new Color(0xEEEEEE))); //imposta il bordo del JPanel 'contentPane'

        switch (typeIcon){
            case 1:
                ImageIcon doneIco = new ImageIcon(this.getClass().getResource("/done.png"));    //carica l'immagine nel percorso /done.png
                Image doneImg = doneIco.getImage().getScaledInstance((int) (screenWidth/51.2),(int) (screenHeight/28.8), Image.SCALE_SMOOTH);   //imposta le dimensioni dell'immagine
                doneIco = new ImageIcon(doneImg);
                typeLabel.setIcon(doneIco); //imposta l'icona della JLabel 'typeLabel' con l'immagine
                break;

            case 2:
                ImageIcon errorIco = new ImageIcon(this.getClass().getResource("/error.png"));  //carica l'immagine nel percorso /error.png
                Image errorImg = errorIco.getImage().getScaledInstance((int) (screenWidth/51.2),(int) (screenHeight/28.8), Image.SCALE_SMOOTH); //imposta le dimensioni dell'immagine
                errorIco = new ImageIcon(errorImg);
                typeLabel.setIcon(errorIco);    //imposta l'icona della JLabel 'typeLabel' con l'immagine
                break;

            default:
                typeLabel.setText("Image Not Found Or Not Selected");   //imposta il testo della JLabel typeLabel
        }

        typeMessageLabel.setFont(baseFontSize);
        typeMessageLabel.setText(message);  //imposta il testo della JLabel 'typeMessageLabel' con 'message'

        okButton.setMinimumSize(new Dimension((screenWidth/16), -1));
        okButton.setFont(baseFontSize);

        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                okButton.setBackground(Color.decode("#cf9e29"));    //imposta il colore dello sfondo del JButton 'okButton'
            }
        });

        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                okButton.setBackground(Color.decode("#FFD369"));    //imposta il colore dello sfondo del JButton 'okButton'
            }
        });

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK(); //chiude la finestra
            }
        });


        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);  //imposta nessuna azione predefinita da eseguire quando viene chiusa la finestra

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel(); //chiude la finestra
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel(); //chiude la finestra
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.pack();
        this.setVisible(true); //rende visibile la finestra
    }

    private void onOK() {   //chiude la finestra
        dispose();
    }//fine onOK

    private void onCancel() {   //chiude la finestra
        dispose();
    }//fine onCancel
    public int getFontSize(){
        // Calcola la dimensione del font in base alle dimensioni dello schermo
        int fontSize = Math.min(screenWidth, screenHeight) / 50; // Modifica il coefficiente a seconda delle tue preferenze
        return fontSize;
    }
}