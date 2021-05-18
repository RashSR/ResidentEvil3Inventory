package resInv.inventorys;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import resInv.GUI.GUI;

public class ExamineItems {
	public static JLabel examineItem;
	public static JLabel examineFrame;
	public static boolean visible;
	
	public static void showExamineItem(Item item) {
		examineItem=new JLabel(new ImageIcon("big_pics/"+split2big(item)+"_BIG.png"));
		examineFrame=new JLabel(new ImageIcon("rsc/examineFrame.png"));
		GUI.nemesisLabel.add(examineItem);
		GUI.nemesisLabel.add(examineFrame);
		examineItem.setBounds(40, 100, 300, 300);
		examineFrame.setBounds(50, 150, 300, 200);
		visible=true;
		examineItem.setVisible(visible);
		examineFrame.setVisible(visible);
		GUI.nemesisLabel.updateUI();
	}
	public static void hideExamineItem() {
		if(examineFrame!=null) {
			visible=false;
			examineItem.setVisible(visible);
			examineFrame.setVisible(visible);
		}
	}
	private static String split2big(Item item){
		String[] parts = item.getFileName().split("/");
		String[] teile = parts[1].split("\\.");
		return teile[0];
	}
}
