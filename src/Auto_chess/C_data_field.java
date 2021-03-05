package Auto_chess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class C_data_field {
	public ImageView background = new ImageView(new Image(getClass().getResourceAsStream("battle_p/c_back1.png")));
	public Text[] cst = new Text[4];
	public AnchorPane pane = new AnchorPane();
	public Text hp = new Text("HP:");
	public Text atk = new Text("ATK:");
	public Text def = new Text("DEF:");
	public Text level = new Text("LV:");
	public C_data_field() {
		background.setLayoutX(0);
		background.setLayoutY(0);
		hp.setLayoutX(23);
		hp.setLayoutY(25);
		hp.setStyle("-fx-font-size: 20px;");
		atk.setLayoutX(23);
		atk.setLayoutY(55);
		atk.setStyle("-fx-font-size: 20px;");
		def.setLayoutX(23);
		def.setLayoutY(85);
		def.setStyle("-fx-font-size: 20px;");
		level.setLayoutX(23);
		level.setLayoutY(115);
		level.setStyle("-fx-font-size: 20px;");
		pane.setVisible(false);
		for(int i = 0;i < 4;i++) {
			cst[i] = new Text("--------------");
			cst[i].setStyle("-fx-font-size: 20px;");
			cst[i].setLayoutX(200);
			cst[i].setLayoutY(25+i*30);
		}
		pane.getChildren().addAll(background,hp,atk,def,level);
		for(int i = 0;i < 4;i++)
			pane.getChildren().addAll(cst[i]);
	}
	public void set_text(C_in_prepare inc) {
		hp.setText("HP:"+inc.HP);;
		atk.setText("ATK:"+inc.ATK);
		def.setText("DEF:"+inc.DEF);
		level.setText("LV:"+inc.LV);
		for(int i = 0;i < 4;i++) {
//			if(inc.c_skill[i].skill==null)
//				inc.c_skill[i].skill = new String("----------");
			cst[i].setText(inc.c_skill[i].skill);
		}
	}
	public void set_text(C_in_battle inc) {
		hp.setText("HP:"+inc.HP);;
		atk.setText("ATK:"+inc.ATK);
		def.setText("DEF:"+inc.DEF);
		level.setText("LV:"+inc.LV);
		for(int i = 0;i < 4;i++) {
//			if(inc.c_skill[i].skill==null)
//				inc.c_skill[i].skill = new String("----------");
			cst[i].setText(inc.c_skill[i].skill);
		}
	}
}
