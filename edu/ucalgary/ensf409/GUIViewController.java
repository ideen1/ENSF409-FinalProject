package edu.ucalgary.ensf409;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.util.regex.Matcher;

import java.awt.Color;
import java.awt.event.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class GUIViewController extends JFrame implements ActionListener, MouseListener{

    private JPanel upperPanel = new JPanel();
    private JPanel midPanel = new JPanel();
    private JPanel footerPanel = new JPanel();
    private JDialog loadingPane;

    public GUIViewController(){
        super("Hamper Application");

        setSize(1000,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        
    }
    

    public void reloadGUI(){
        this.remove(upperPanel);
        this.remove(midPanel);
        this.remove(footerPanel);
        this.add(upperPanel, BorderLayout.NORTH);
        this.add(midPanel, BorderLayout.CENTER);
        this.add(footerPanel, BorderLayout.PAGE_END);

        upperPanel.revalidate();
        upperPanel.repaint();
        midPanel.revalidate();
        midPanel.repaint();
        footerPanel.revalidate();
        footerPanel.repaint();
        
    }

    public void resetLayouts(){
        upperPanel.removeAll();
        midPanel.removeAll();
        footerPanel.removeAll();
        
        upperPanel.setLayout(new FlowLayout());
        midPanel.setLayout(new FlowLayout());
        footerPanel.setLayout(new FlowLayout());
    }

    public void GUILoadHome(){
        resetLayouts();
        JLabel title = new JLabel("Please select what you would like to do:");
        JLabel inventorySize = new JLabel("There are " + HamperApp.inventory.getFoodlist().size() + " items in the inventory");

        JButton createOrder = new JButton("Create Order");
        createOrder.setVerticalAlignment(SwingConstants.CENTER);
        createOrder.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Create new Request Order
                HamperApp.currentRequest = new Request("Order", LocalDate.now());
                GUIViewController.this.GUILoadOrder();
            }
        });
        JProgressBar progress = new JProgressBar();
        upperPanel.add(title);
        midPanel.add(createOrder);
        midPanel.add(progress);
        footerPanel.add(inventorySize);
        

        reloadGUI();
    }

    public void GUILoadOrder(){
        resetLayouts();
        JLabel title = new JLabel("List of All current Hampers");

        for (Hamper hamper : HamperApp.currentRequest.getHampers()){
            JPanel pane = new JPanel();
            pane.setLayout(new FlowLayout());
            pane.setBorder(BorderFactory.createLineBorder(Color.black));
            JLabel clientName = new JLabel(hamper.getClientName());
            JLabel numAdultFemale = new JLabel("Adult Females: " + String.valueOf(hamper.getNumAdultFemales()));
            JLabel numAdultMale = new JLabel("Adult Males: " + String.valueOf(hamper.getNumAdultMales()));
            JLabel numChildUnder8 = new JLabel("Children Under 8: " + String.valueOf(hamper.getNumChildUnder8()));
            JLabel numChildOver8 = new JLabel("Children Over 8: " + String.valueOf(hamper.getNumChildOver8()));

            pane.add(clientName);
            pane.add(numAdultFemale);
            pane.add(numAdultMale);
            pane.add(numChildUnder8);
            pane.add(numChildOver8);
            midPanel.add(pane);
            
        }
        JButton processOrder = new JButton("Process Order");
        processOrder.addActionListener(this);
        processOrder.setHorizontalAlignment(SwingConstants.RIGHT);
        processOrder.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                boolean valid = true;
                if(HamperApp.currentRequest.getHampers().size() == 0){
                    displayError("You must have at least 1 Hamper to process an order!");
                    valid = false;
                }
                if (valid){
                    GUIViewController.this.GUIDisplayForm();
                }

            }
        });

        JButton addHamper = new JButton("Add Hamper");
        addHamper.setHorizontalAlignment(SwingConstants.LEFT);
        addHamper.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                GUIViewController.this.GUIAddHamperBox();

            }
        });
        
        upperPanel.add(title);
        footerPanel.add(addHamper);
        footerPanel.add(processOrder);

        reloadGUI();
    }

    public void GUIAddHamperBox(){
        resetLayouts();
        JLabel title = new JLabel("Add New Hamper:");

        JPanel pane = new JPanel();
        pane.setLayout(new FlowLayout());
        pane.setBorder(BorderFactory.createLineBorder(Color.black));


        JLabel hamperID = new JLabel("(#" + String.valueOf(HamperApp.currentRequest.getHampers().size() + 1) + ")");
        JTextFieldPlaceHolder clientName = new JTextFieldPlaceHolder("Client Name");
        JTextFieldPlaceHolder numAdultMale = new JTextFieldPlaceHolder("Adult Males");
        JTextFieldPlaceHolder numAdultFemale = new JTextFieldPlaceHolder("Adult Females");
        JTextFieldPlaceHolder numChildUnder8 = new JTextFieldPlaceHolder("Child < 8");
        JTextFieldPlaceHolder numChildOver8 = new JTextFieldPlaceHolder("Child > 8"); 

        clientName.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                hamperID.setText(clientName.getText() + "(#" + String.valueOf(HamperApp.currentRequest.getHampers().size() + 1) + ")");

            }
            public void removeUpdate(DocumentEvent e) {
                hamperID.setText(clientName.getText() + "(#" + String.valueOf(HamperApp.currentRequest.getHampers().size() + 1 + ")" ));

            }
            public void insertUpdate(DocumentEvent e) {
                hamperID.setText(clientName.getText() + "(#" + String.valueOf(HamperApp.currentRequest.getHampers().size() + 1 + ")" ));

            }
          
          });

        JButton back = new JButton("Back");
        back.setHorizontalAlignment(SwingConstants.LEFT);
        back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                GUIViewController.this.GUILoadOrder();

            }
        });

        JButton save = new JButton("Save");
        save.setHorizontalAlignment(SwingConstants.RIGHT);
        save.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Validate inputs add hamper and return if ok
                boolean valid = true;
                int male = 0;
                int female = 0;
                int childunder = 0;
                int childover = 0;
                Pattern stringPattern = Pattern.compile("[a-zA-Z\\s]*$");
                Matcher matcher = stringPattern.matcher(clientName.getText());
                if (!matcher.matches() || clientName.getText().equals("Client Name")){
                    valid = false;
                    displayError("Client name can only contain letters or spaces!");
                }

                try {
                    male = Integer.parseInt(numAdultMale.getText());
                } catch (Exception eevent ){
                    valid = false;
                    displayError(PersonType.ADULTMALE);
                }
                try {
                     female = Integer.parseInt(numAdultFemale.getText());
                } catch (Exception eevent ){
                    valid = false;
                    displayError(PersonType.ADULTFEMALE);
                }
                try {
                     childunder = Integer.parseInt(numChildUnder8.getText());
                } catch (Exception eevent ){
                    valid = false;
                    displayError(PersonType.CHILDUNDER8);
                }
                try {
                    childover = Integer.parseInt(numChildOver8.getText());
                } catch (Exception eevent ){
                    valid = false;
                    displayError(PersonType.CHILDOVER8);
                }

                if (valid){
                    HamperApp.currentRequest.addHamper(clientName.getText(), male, female, childunder, childover);;
                    GUIViewController.this.GUILoadOrder();
                }

            }

            
        });
        
        midPanel.add(hamperID);
        pane.add(clientName);
        pane.add(numAdultFemale);
        pane.add(numAdultMale);
        pane.add(numChildUnder8);
        pane.add(numChildOver8);

        upperPanel.add(title);
        midPanel.add(pane, BorderLayout.AFTER_LAST_LINE);
        footerPanel.add(back);
        footerPanel.add(save);
        reloadGUI();
    }

    public void GUIDisplayForm(){
        resetLayouts();
        JLabel title = new JLabel("Order Form:");
        JLabel amount = new JLabel(HamperApp.currentRequest.getHampers().size() + " Total Hampers");

        
        JButton home = new JButton("Home");
        home.addActionListener(this);
        home.setHorizontalAlignment(SwingConstants.CENTER);
        home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                GUIViewController.this.GUILoadHome();

            }
        });
        
        upperPanel.add(title);
        upperPanel.add(amount);
        footerPanel.add(home);

        reloadGUI();
    }

    public void displayError(PersonType type){
        JOptionPane.showMessageDialog(midPanel, type.toString() + " must be a number!", "Invalid input", JOptionPane.ERROR_MESSAGE);
    }
    public void displayError(String error){
        JOptionPane.showMessageDialog(midPanel, error, "Invalid input", JOptionPane.ERROR_MESSAGE);
    }
    public static void genericError(String error){
        JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.WARNING_MESSAGE);
    }
    public void genericLoader(){
        loadingPane = new JDialog();
        loadingPane.setTitle("Message");
        loadingPane.setModal(true);

        JOptionPane message = new JOptionPane("Hello world", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        loadingPane.setContentPane(message);
    }
    public void genericLoaderHide(){
        loadingPane.dispose();
    }

    public void actionPerformed(ActionEvent event){


    }
    
    public void mouseClicked(MouseEvent event){
 
                
    }

    public void mouseEntered(MouseEvent event){
        
    }

    public void mouseExited(MouseEvent event){
        
    }

    public void mousePressed(MouseEvent event){
        
    }

    public void mouseReleased(MouseEvent event){
        
    }
    
}

class JTextFieldPlaceHolder extends JTextField{
    public JTextFieldPlaceHolder(String placeholder){
        super(placeholder, 15);
        this.placeholder = placeholder;

        this.setForeground(Color.GRAY);
        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (JTextFieldPlaceHolder.this.getText().equals(placeholder)) {
                    JTextFieldPlaceHolder.this.setText("");
                    JTextFieldPlaceHolder.this.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (JTextFieldPlaceHolder.this.getText().isEmpty()) {
                    JTextFieldPlaceHolder.this.setForeground(Color.GRAY);
                    JTextFieldPlaceHolder.this.setText(placeholder);
                }
            }
            });
        
    }
    private String placeholder;

}