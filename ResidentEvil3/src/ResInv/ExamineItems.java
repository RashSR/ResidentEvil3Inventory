package ResInv;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ExamineItems {
	public static JLabel examineItem;
	public static boolean visible;
	
	public static void showExamieItem(Item item) {
		examineItem=new JLabel(new ImageIcon("big_pics/"+split2big(item)+"_BIG.png"));
		GUI.nemesisLabel.add(examineItem);
		examineItem.setBounds(40, 100, 300, 300);
		visible=true;
		examineItem.setVisible(true);
		GUI.nemesisLabel.updateUI();
	}
	public static void hideExamineItem() {
		examineItem.setVisible(false);
		visible=false;
	}
	private static String split2big(Item item){
		String[] parts = item.getFileName().split("/");
		String[] teile = parts[1].split("\\.");
		return teile[0];
	}
}
