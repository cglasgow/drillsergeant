package ui;

import java.awt.EventQueue;

import javax.swing.UIManager;

public class Main {
	protected static final Drill_Sergeant ds = new Drill_Sergeant();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			//Drill_Sergeant ds = new Drill_Sergeant();
			public void run() {
				try {
					ds.frmDrillSergeant.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
