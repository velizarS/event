
package eventmanager;

/**
 *
 * @author Velizar
 */
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.converter.DateStringConverter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import jdk.nashorn.internal.parser.DateParser;

public class MyFrame extends JFrame {
    
	Connection conn = null;
	PreparedStatement state = null;
	ResultSet result = null;
	
	JTable table = new JTable();
	JScrollPane myScroll = new JScrollPane(table);
	
	JPanel upPanel = new JPanel();
        JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();
	
	JLabel eNLabel = new JLabel("Event Name");
	JLabel lLabel = new JLabel("Location");
	JLabel sDLabel = new JLabel("Start Date");
	JLabel fDLabel = new JLabel("Finale Date");
        
	JTextField eNField = new JTextField(30);
	JTextField lField = new JTextField(30);
	JTextField sDField = new JTextField(16);
	JTextField fDField = new JTextField(16);
	
	JButton addButton = new JButton("Add");
	JButton delButton = new JButton("Del");
	JButton changeButton = new JButton("Change");
	
	public MyFrame(){
		this.setVisible(true);
		this.setSize(500, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setLayout(new GridLayout(4,2));
		this.add(upPanel);
		this.add(midPanel);
        	this.add(downPanel);
		
                upPanel.setLayout(new GridLayout(5,1));
                
		upPanel.add(eNLabel);
		upPanel.add(eNField);
		upPanel.add(lLabel);
		upPanel.add(lField);
		upPanel.add(sDLabel);
		upPanel.add(sDField);
		upPanel.add(fDLabel);
		upPanel.add(fDField);
		
		//midPanel
		midPanel.add(addButton);
		midPanel.add(delButton);
		midPanel.add(changeButton);
                
		addButton.addActionListener(new AddAction());
		changeButton.addActionListener(new ChangeAction());
		delButton.addActionListener(new delAction());

		
		
		myScroll.setSize(500, 250);
		downPanel.add(myScroll);
		refreshTable();
		
	}// end constructor()
	

	public void clear(){
		
		eNField.setText("");
		lField.setText("");
		sDField.setText("");
		fDField.setText("");
		
	}// clear()
	
	
	
	

	public ResultSet getAll(){
		
		conn = DBconnection.getConnection();
		try {
			state = conn.prepareStatement("select name, location, startdate, finaldate from test ");
			result = state.executeQuery();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			return result;
		}	
	}// end getAll
	
	public void refreshTable(){
		try {
			table.setModel(new MyModel(getAll()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}// end refreshTable
	
	class ChangeAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			conn = DBconnection.getConnection();
			try {
				state = conn.prepareStatement("select name, location, startdate, finaldate from test ");
				state.setString(1, eNField.getText());
				result = state.executeQuery();
				clear();
				table.setModel(new MyModel(result));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}// end actionPerformed()
		
	}// end SearchAction
	
	class AddAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
                        String date =sDField.getText();
                        String date1 =fDField.getText();
                       SimpleDateFormat format = new SimpleDateFormat();
                      Date parsed = null;
                    try {
                        parsed = format.parse(date);
                         parsed = format.parse(date1);
                    } catch (ParseException ex) {
                        
                    }
                       java.sql.Date sDate = new java.sql.Date(parsed.getTime());
                       java.sql.Date fDate = new java.sql.Date(parsed.getTime());
			conn = DBconnection.getConnection();
                        
			String sql = "insert into TEST(name,location,startdate,finaldate,) values(?,?,?,?)";
			try {
				state = conn.prepareStatement(sql);
				state.setString(1, eNField.getText());
				state.setString(2, lField.getText());
				state.setDate(3, sDate);
				state.setDate(4, fDate);
				
				state.execute();
				clear();
				refreshTable();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}// end actionPerformed()

  
	}// end AddAction
	class delAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			conn = DBconnection.getConnection();
			String sql = "delete from  test where name=? ";
			try {
				state = conn.prepareStatement(sql);
				
				state.setString(1, (eNField.getText()));
				
				state.execute();
				clear();
				refreshTable();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}// end actionPerformed()
		
	}// end AddActionclass SearchAction implements ActionListener{
	
	

}// end class
