package GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import javax.swing.*;

class DatePicker {
    int month = Calendar.getInstance().get(java.util.Calendar.MONTH);
    int year = Calendar.getInstance().get(java.util.Calendar.YEAR);
    String day = "";
    JDialog d;
    JComboBox mesi = new JComboBox<>();
    JComboBox anni = new JComboBox<>();
    JButton[] button = new JButton[49];
    JButton previous = new JButton("<<");
    JButton next = new JButton(">>");
    java.util.Calendar cal = java.util.Calendar.getInstance();
    public DatePicker(JFrame parent) {
        d = new JDialog();
        d.setUndecorated(true);
        d.setModal(true);
        UIManager.put("Button.select", Color.TRANSLUCENT);



        String[] header = { "Dom", "Lun", "Mar", "Mer", "Gio", "Ven", "Sab" };

        JPanel p2 = new JPanel(new GridLayout(1, 3));
        p2.setBorder(BorderFactory.createEmptyBorder());
        p2.setBackground(Color.decode("#222831"));
        previous.setBackground(Color.decode("#222831"));
        previous.setForeground(Color.decode("#EEEEEE"));
        previous.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                month--;
                displayDate();
            }
        });
        p2.add(previous);

        ArrayList<String> mesiAnno = new ArrayList<String>(Arrays.asList("Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno","Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"));
        mesi.setModel(new DefaultComboBoxModel<String>(mesiAnno.toArray(new String[mesiAnno.size()])));
        mesi.setSelectedIndex(java.util.Calendar.getInstance().get(java.util.Calendar.MONTH));
        mesi.setBackground(Color.decode("#FFD369"));
        mesi.setForeground(Color.decode("#222831"));
        p2.add(mesi);


        mesi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String s = (String) mesi.getSelectedItem();
                switch (s){
                    case "Gennaio":
                        if (cal.getTime().getMonth()> 0){
                            month = month - (cal.getTime().getMonth() - 0);
                        }
                        break;
                    case "Febbraio":
                        if (cal.getTime().getMonth()> 1){
                            month = month - (cal.getTime().getMonth() - 1);
                        }
                        if (cal.getTime().getMonth()< 1){
                            month = month + (1 - cal.getTime().getMonth());
                        }
                        break;
                    case "Marzo":
                        if (cal.getTime().getMonth()> 2){
                            month = month - (cal.getTime().getMonth() - 2);
                        }
                        if (cal.getTime().getMonth()< 2){
                            month = month + (2 - cal.getTime().getMonth());
                        }
                        break;
                    case "Aprile":
                        if (cal.getTime().getMonth()> 3){
                            month = month - (cal.getTime().getMonth() - 3);
                        }
                        if (cal.getTime().getMonth()< 3){
                            month = month + (3 - cal.getTime().getMonth());
                        }
                        break;
                    case "Maggio":
                        if (cal.getTime().getMonth()> 4){
                            month = month - (cal.getTime().getMonth() - 4);
                        }
                        if (cal.getTime().getMonth()< 4){
                            month = month + (4 - cal.getTime().getMonth());
                        }
                        break;
                    case "Giugno":
                        if (cal.getTime().getMonth()> 5){
                            month = month - (cal.getTime().getMonth() - 5);
                        }
                        if (cal.getTime().getMonth()< 5){
                            month = month + (5 - cal.getTime().getMonth());
                        }
                        break;
                    case "Luglio":
                        if (cal.getTime().getMonth()> 6 && (java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)>=(cal.getTime().getYear()+1900) && java.util.Calendar.getInstance().get(java.util.Calendar.MONTH)>=cal.getTime().getMonth())){
                            month = month - (cal.getTime().getMonth() - 6);
                        }
                        if (cal.getTime().getMonth()< 6 && (java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)>=(cal.getTime().getYear()+1900) && java.util.Calendar.getInstance().get(java.util.Calendar.MONTH)<=cal.getTime().getMonth())){
                            month = month + (6 - cal.getTime().getMonth());
                        }
                        break;
                    case "Agosto":
                        if (cal.getTime().getMonth()> 7){
                            month = month - (cal.getTime().getMonth() - 7);
                        }
                        if (cal.getTime().getMonth()< 7){
                            month = month + (7 - cal.getTime().getMonth());
                        }
                        break;
                    case "Settembre":
                        if (cal.getTime().getMonth()> 8){
                            month = month - (cal.getTime().getMonth() - 8);
                        }
                        if (cal.getTime().getMonth()< 8){
                            month = month + (8 - cal.getTime().getMonth());
                        }
                        break;
                    case "Ottobre":
                        if (cal.getTime().getMonth()> 9){
                            month = month - (cal.getTime().getMonth() - 9);
                        }
                        if (cal.getTime().getMonth()< 9){
                            month = month + (9 - cal.getTime().getMonth());
                        }
                        break;
                    case "Novembre":
                        if (cal.getTime().getMonth()> 10){
                            month = month - (cal.getTime().getMonth() - 10);
                        }
                        if (cal.getTime().getMonth()< 10){
                            month = month + (10 - cal.getTime().getMonth());
                        }
                        break;
                    case "Dicembre":
                        if (cal.getTime().getMonth()< 11){
                            month = month + (11 - cal.getTime().getMonth());
                        }
                        break;
                    default:
                        if (cal.getTime().getMonth()< 12){
                            month = month + (11 - cal.getTime().getMonth());
                        }
                        break;
                }
                displayDate();
            }
        });

        //Aggiungere ActionPerformed Mesi e Anni
        // Mesi if Esemp gennaio: c

        for(int i = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR); i > 1899; i--) anni.addItem(i);
        anni.setBackground(Color.decode("#FFD369"));
        anni.setForeground(Color.decode("#222831"));
        p2.add(anni);

        anni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int y = (int) anni.getSelectedItem();
                if (y > (cal.getTime().getYear() + 1900)) {
                    month = month + (12 * (y-(cal.getTime().getYear()+1900)));
                }
                if (y < (cal.getTime().getYear()+1900)){
                    month = month - (12*((cal.getTime().getYear()+1900) - y));

                }
                if(y == Calendar.getInstance().get(java.util.Calendar.YEAR)){
                    for(int i = mesi.getItemCount(); i > (Calendar.getInstance().get(java.util.Calendar.MONTH)+1); i--){
                        mesi.removeItemAt((i-1));
                    }

                }
                displayDate();
            }
        });

        //Aggiungere Combobox Mese -> Anno

        next.setBackground(Color.decode("#222831"));
        next.setForeground(Color.decode("#EEEEEE"));
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                month++;
                displayDate();
            }
        });
        p2.add(next);

        JPanel p1 = new JPanel(new GridLayout(7, 7));
        p1.setBorder(BorderFactory.createEmptyBorder(-1, -1, -1, -1));
        p1.setBackground(Color.decode("#222831"));
        p1.setPreferredSize(new Dimension(430, 120));

        // for (int x = 0; x <button> 6) {
        for (int x = 0; x < button.length; x++) {
            final int selection = x;
            button[x] = new JButton();
            button[x].setBorder(BorderFactory.createEmptyBorder());
            button[x].setFocusPainted(false);
            button[x].setBackground(Color.decode("#222831"));
            button[x].setForeground(Color.decode("#EEEEEE"));
            if (x > 6) {
                button[x].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        day = button[selection].getActionCommand();
                        d.dispose();
                    }
                });
                button[x].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        day = button[selection].getActionCommand();
                        d.dispose();
                    }
                });
                button[x].addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        super.mouseEntered(e);
                        button[selection].setBackground(Color.decode("#393E46"));
                    }
                    public void mouseExited(MouseEvent e) {
                        super.mouseExited(e);
                        button[selection].setBackground(Color.decode("#222831"));
                    }
                    /*public void mousePressed(MouseEvent e){
                        super.mousePressed(e);
                        button[selection].setBackground(Color.decode("#EEEEEE"));
                        button[selection].setForeground(Color.decode("222831"));
                    }
                    public void mouseReleased(MouseEvent e){
                        super.mouseReleased(e);
                        button[selection].setBackground(Color.decode("#393E46"));
                        button[selection].setForeground(Color.decode("#EEEEEE"));
                    }*/

                });
            }
            if (x < 7) {
                button[x].setText(header[x]);
                button[x].setBorder(BorderFactory.createEmptyBorder());
                button[x].setContentAreaFilled(true);
                button[x].setForeground(Color.decode("#222831"));
                button[x].setBackground(Color.decode("#FFD369"));
            }
            p1.add(button[x]);
        }

        d.add(p1, BorderLayout.NORTH);
        d.add(p2, BorderLayout.SOUTH);
        d.pack();
        d.setLocationRelativeTo(parent);
        displayDate();
        d.setVisible(true);

    }

    public void displayDate() {
        for (int x = 7; x < button.length; x++)
            button[x].setText("");
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                "MMMM yyyy");
        cal.set(year, month, 1);
        int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
        int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        for (int x = 6 + dayOfWeek, day = 1; day <= daysInMonth; x++, day++)
            button[x].setText("" + day);

        mesi.setSelectedIndex(cal.getTime().getMonth());
        if((cal.getTime().getYear()+1900) <= java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)) anni.setSelectedIndex(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) - (cal.getTime().getYear()+1900));
        else anni.setSelectedItem((cal.getTime().getYear()+1900));
        if (((cal.getTime().getYear()+1900) == 1900) && (cal.getTime().getMonth() == 0)){
            previous.setEnabled(false);
        } else if ((cal.getTime().getYear()+1900) == java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) && cal.getTime().getMonth() == java.util.Calendar.getInstance().get(java.util.Calendar.MONTH)) next.setEnabled(false);
        else{
            previous.setEnabled(true);
            next.setEnabled(true);
        }

        d.setTitle("Date Picker");
    }

    public String setPickedDate() {
        if (day.equals(""))
            return day;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                "dd-MM-yyyy");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, Integer.parseInt(day));
        return sdf.format(cal.getTime());
    }
}

class DatePickerExample {
    public static void main(String[] args) {
        JLabel label = new JLabel("Seleziona Data:");
        final JTextField text = new JTextField(20);
        JButton b = new JButton("Apri");
        JPanel p = new JPanel();
        p.add(label);
        p.add(text);
        p.add(b);
        final JFrame f = new JFrame();
        f.getContentPane().add(p);
        f.pack();
        f.setVisible(true);
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                text.setText(new DatePicker(f).setPickedDate());
            }
        });
    }
}