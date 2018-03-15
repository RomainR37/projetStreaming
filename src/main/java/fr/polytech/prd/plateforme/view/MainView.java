package fr.polytech.prd.plateforme.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * class MainView: Fenêtre principale de l'application
 * 
 */
public class MainView implements ActionListener{


    private final JFrame frame;
    
    private JButton buttonTest;

	public MainView(){
        frame = new JFrame("Plateforme d'acquisition");
		frame.setSize(300,150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setLocationRelativeTo(null);
       
        JPanel contentPane = new JPanel();

        JLabel textTitle = new JLabel("Plateforme Streaming");
        
        
        buttonTest = new JButton("Test lancement stream");
        buttonTest.addActionListener(this);
        
        
        contentPane.add(textTitle);
        contentPane.add(buttonTest);
        
        
        frame.setContentPane(contentPane);
        frame.setVisible(true);
	
	}

	public void actionPerformed(ActionEvent e) {
		new StreamView();
	}
	
}
