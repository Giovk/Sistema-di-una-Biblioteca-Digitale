package GUI;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * La classe LSForm implemeta l'interfaccia grafica del form iniziale.
 */
public class LSForm {
    private Controller controller;
    private static JFrame frame;
    private JButton btSignIn;
    private JButton btSignUp;
    private JLabel txtPF;
    private JLabel image;
    private JPanel lsPanel;

    /**
     * Istanzia un nuovo LSForm.
     */
    public LSForm() {
        controller = new Controller();
        frame = new JFrame("Biblioteca Digitale");

        Image icona = new ImageIcon(this.getClass().getResource("/icon.png")).getImage();   //carica l'immagine nel percorso /icon.png

        frame.setIconImage(icona);  //imposta la l'icona dell'applicazione
        frame.setUndecorated(true); //abilita le decorazioni del frame
        frame.setContentPane(lsPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //imposta la terminazione dell'applicazione come operazione predefinita da eseguire quando viene chiuso il frame
        frame.pack();
        frame.setSize(controller.screenWidth, controller.screenHeight);   //imposta larghezza e altezza del frame
        frame.setLocationRelativeTo(null);  //posiziona il frame al centro dello schermo
        frame.setResizable(false);  //evita che l'utente modifichi le dimensioni del frame
        frame.setVisible(true); //rende visibile il frame

        btSignIn.setFont(controller.titleImpact);   //imposta il font del JButton 'btSignIn'
        btSignUp.setFont(controller.titleImpact);   //imposta il font del JButton 'btSignUp'

        btSignIn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (frame.isEnabled()) {    //controlla se è stato abilitato il frame
                    NewLoginForm newLoginForm = new NewLoginForm(0, frame, controller); //chiama il JDialog (NewLoginForm) 'newLoginForm'
                    frame.setEnabled(false); //disabilita il frame
                }
            }
        });

        btSignUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if(frame.isEnabled() ){ //controlla se è stato abilitato il frame
                    NewLoginForm newLoginForm = new NewLoginForm(1, frame, controller); //chiama il JDialog (NewLoginForm) 'newLoginForm'
                    frame.setEnabled(false);    //disabilita il frame
                }
            }
        });

        btSignIn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);

                ImageIcon bt1igm = new ImageIcon(this.getClass().getResource("/button-type1.png")); //carica l'immagine nel percorso /button-type1.png
                Image resbt1img = bt1igm.getImage().getScaledInstance((int) (controller.screenWidth/8.5333), (int) (controller.screenHeight/17.56097), Image.SCALE_SMOOTH); //imposta le dimensioni dell'immagine

                bt1igm = new ImageIcon(resbt1img);  //reinizializza l'ImageIcon 'bt1igm' con l'Image 'resbt1img'
                btSignIn.setIcon(bt1igm);   //imposta l'icona del JButton 'btSignIn' con l'immagine
                btSignIn.setForeground(Color.decode("#EEEEEE"));    //imposta il colore dello sfondo del JButton 'btSignIn'
            }
        });

        btSignIn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                ImageIcon bt1igm = new ImageIcon(this.getClass().getResource("/button-type2.png")); //carica l'immagine nel percorso /button-type2.png
                Image resbt1img = bt1igm.getImage().getScaledInstance((int) (controller.screenWidth/8.5333), (int) (controller.screenHeight/17.56097), Image.SCALE_SMOOTH); //imposta le dimensioni dell'immagine

                bt1igm = new ImageIcon(resbt1img);  //reinizializza l'ImageIcon 'bt1igm' con l'Image 'resbt1img'
                btSignIn.setIcon(bt1igm);   //imposta l'icona del JButton 'btSignIn' con l'immagine
                btSignIn.setForeground(Color.decode("#D6D4D4"));    //imposta il colore dello sfondo del JButton 'btSignIn'
            }
        });

        btSignUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);

                ImageIcon bt2igm = new ImageIcon(this.getClass().getResource("/button-type1.png")); //carica l'immagine nel percorso /button-type1.png
                Image resbt2img = bt2igm.getImage().getScaledInstance((int) (controller.screenWidth/8.5333), (int) (controller.screenHeight/17.56097), Image.SCALE_SMOOTH); //imposta le dimensioni dell'immagine

                bt2igm = new ImageIcon(resbt2img);  //reinizializza l'ImageIcon 'bt2igm' con l'Image 'resbt2img'
                btSignUp.setIcon(bt2igm);   //imposta l'icona del JButton 'btSignUp' con l'immagine
                btSignUp.setForeground(Color.decode("#EEEEEE"));    //imposta il colore dello sfondo del JButton 'btSignUp'
            }
        });

        btSignUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                ImageIcon bt2igm = new ImageIcon(this.getClass().getResource("/button-type2.png")); //carica l'immagine nel percorso /button-type2.png
                Image resbt2img = bt2igm.getImage().getScaledInstance((int) (controller.screenWidth/8.5333), (int) (controller.screenHeight/17.56097), Image.SCALE_SMOOTH); //imposta le dimensioni dell'immagine

                bt2igm = new ImageIcon(resbt2img);  //reinizializza l'ImageIcon 'bt2igm' con l'Image 'resbt2img'
                btSignUp.setIcon(bt2igm);   //imposta l'icona del JButton 'btSignUp' con l'immagine
                btSignUp.setForeground(Color.decode("#D6D4D4"));    //imposta il colore delllo sfondo del JButton 'btSignUp'
            }
        });

        txtPF.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                txtPF.setForeground(Color.decode("#D6D4D4"));   //imposta il colore dello sfondo della JLabel 'txtPF'
            }
        });

        txtPF.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                txtPF.setForeground(Color.decode("#EEEEEE"));   //imposta il colore dello sfondo della JLabel 'txtPF'
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //dimensioni dello schermo
        int screenWidht = screenSize.width; //larghezza dello schermo
        int screenHeight = screenSize.height;   //altezza dello schermo

        txtPF = new JLabel();   //inizializza il JLabel 'txtPF'
        txtPF.setForeground(Color.decode("#EEEEEE"));   //imposta il colore dello sfondo della JLabel 'txtPF'

        image = new JLabel();   //inizializza il JLabel 'image'
        image.setText("");  //imposta il testo della 'JLabel'

        ImageIcon lb1igm = new ImageIcon(this.getClass().getResource("/b.png"));    //carica l'immagine nel percorso /b.png
        Image reslb1img = lb1igm.getImage().getScaledInstance((int) (screenWidht/4.2667), (int) (screenHeight/4.2603), Image.SCALE_SMOOTH);  //imposta le dimensioni dell'immagine

        lb1igm = new ImageIcon(reslb1img);  //reinizializza l'ImageIcon 'lb1igm' con l'Image 'reslb1img'
        image.setIcon(lb1igm);  //imposta l'icona della JLabel 'image' con l'immagine

        btSignIn = new JButton();
        btSignIn.setContentAreaFilled(false);   //toglie lo sfondo dell JButton 'btSignIn'
        btSignIn.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));    //toglie il bordo del JButton 'btSignIn'
        btSignIn.setMargin(new Insets(0, 0, 0, 0)); //toglie il margine all'interno del JButton 'btSignIn'

        ImageIcon bt1igm = new ImageIcon(this.getClass().getResource("/button-type1.png")); //carica l'immagine nel percorso /button-type1.png
        Image resbt1img = bt1igm.getImage().getScaledInstance((int) (screenWidht/8.5333), (int) (screenHeight/17.56097), Image.SCALE_SMOOTH); //imposta le dimensioni dell'immagine

        bt1igm = new ImageIcon(resbt1img);  //reinizializza l'ImageIcon 'lb1igm' con l'Image 'reslb1img'
        btSignIn.setIcon(bt1igm);   //imposta l'icona del JButton 'btSignIn' con l'immagine
        btSignIn.setHorizontalTextPosition(JButton.CENTER); //centra orizzontalmente il contenuto dell JButton 'btSignIn'
        btSignIn.setVerticalTextPosition(JButton.CENTER);   //centra verticalmente il contenuto dell JButton 'btSignIn'
        btSignIn.setForeground(Color.decode("#EEEEEE"));    //imposta il colore dello sfondo del JButton 'btSignIn'

        btSignUp = new JButton();
        btSignUp.setContentAreaFilled(false);   //toglie lo sfondo dell JButton 'btSignUp'
        btSignUp.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));    //toglie il bordo dell JButton 'btSignUp'
        btSignUp.setMargin(new Insets(0, 0, 0, 0)); //toglie il margine all'interno del JButton 'btSignUp'

        ImageIcon bt2igm = new ImageIcon(this.getClass().getResource("/button-type1.png")); //carica l'immagine nel percorso /button-type1.png
        Image resbt2img = bt2igm.getImage().getScaledInstance((int) (screenWidht/8.5333), (int) (screenHeight/17.56097), Image.SCALE_SMOOTH); //imposta le dimensioni dell'immagin

        bt2igm = new ImageIcon(resbt2img);  //reinizializza l'ImageIcon 'bt2igm' con l'Image 'resbt2img'
        btSignUp.setIcon(bt2igm);   //imposta l'icona del JButton 'btSignIn' con l'immagine
        btSignUp.setHorizontalTextPosition(JButton.CENTER); //centra orizzontalmente il contenuto dell JButton 'btSignUp'
        btSignUp.setVerticalTextPosition(JButton.CENTER);   //centra verticalmente il contenuto dell JButton 'btSignUp'
        btSignUp.setForeground(Color.decode("#EEEEEE"));    //imposta il colore dello sfondo del JButton 'btSignUp'
    }
}