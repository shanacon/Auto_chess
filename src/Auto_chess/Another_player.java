package Auto_chess;

import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Another_player {
	public Another_player() {
		;
	}
	public void set(String name){
		for(int i = 0;i < 6;i++) {
			putc[i] = new Button();
			putc[i].setStyle("-fx-background-color: transparent");
			conp_t_n[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/c_onp_tank_n.png")));
			conp_a_n[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/c_onp_archer_n.png")));
			conp_s_n[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/c_onp_sword_n.png")));
//			putc[i].setGraphic(conp_t_n[i]);
			final int t = i;
			putc[i].addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
				if(pdata.cinp[t].prof!=0)
					pdata.cinp[t].df.pane.setVisible(true);
			});
			putc[i].addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
				if(pdata.cinp[t].prof!=0)
					pdata.cinp[t].df.pane.setVisible(false);
			});
		}
		for(int i = 0;i < 3;i++) {
			putc[i].setLayoutX(204);
			putc[i].setLayoutY(589-138*i);
			pdata.cinp[i].df.pane.setLayoutX(350);
			pdata.cinp[i].df.pane.setLayoutY(589-138*i);
		}
		for(int i = 0;i < 3;i++) {
			putc[i+3].setLayoutX(359);
			putc[i+3].setLayoutY(589-138*i);
			pdata.cinp[i+3].df.pane.setLayoutX(508);
			pdata.cinp[i+3].df.pane.setLayoutY(589-138*i);
		}
		HP_t.setLayoutX(1000);
		HP_t.setLayoutY(260+3*149);
		HP_t.setText("HP："+String.valueOf(pdata.HP));
		HP_t.setStyle("-fx-font-size: 36px;");
		HP_t.setFill(Color.WHITE);
		level_t.setText("玩家等級："+pdata.level);
		level_t.setLayoutX(0);
		level_t.setLayoutY(220);
		level_t.setStyle("-fx-font-size: 36px;");
		level_t.setFill(Color.WHITE);
		money_t.setText("目前金錢："+pdata.money);
		money_t.setLayoutX(0);
		money_t.setLayoutY(270);
		money_t.setStyle("-fx-font-size: 36px;");
		money_t.setFill(Color.WHITE);
		name_t.setText(name);
		pdata.name = name;
		name_t.setLayoutX(0);
		name_t.setLayoutY(170);
		name_t.setStyle("-fx-font-size: 36px;");
		name_t.setFill(Color.WHITE);
		next_p.setLayoutX(1036);
		next_p.setLayoutY(388);
		next_p.setGraphic(next_n);
		next_p.setStyle("-fx-background-color: transparent");
		next_p.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			next_p.setGraphic(next_s);
		});
		next_p.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			if(next_p.getGraphic()==next_s)
				next_p.setGraphic(next_n);
		});
		pane.getChildren().addAll(background);
		for(int i = 0;i < 6;i++)
			pane.getChildren().addAll(putc[i],pdata.cinp[i].df.pane);
		pane.getChildren().addAll(next_p,level_t,money_t,name_t,HP_t);
		pane.setLayoutX(0);
		pane.setLayoutY(0);
	}
	public void refresh(String aya) {
		String data[] = aya.split("\t");
		pdata.level = Integer.valueOf(data[1]);
		pdata.money = Integer.valueOf(data[2]);
		for(int i = 0;i < 6;i++) {
			pdata.cinp[i].prof = Integer.valueOf(data[3+7*i]);
			pdata.cinp[i].HP = Integer.valueOf(data[4+7*i]);
			pdata.cinp[i].ATK = Integer.valueOf(data[5+7*i]);
			pdata.cinp[i].DEF = Integer.valueOf(data[6+7*i]);
			pdata.cinp[i].LV = Integer.valueOf(data[7+7*i]);
			///////////////////////
			pdata.cinp[i].c_skill[2].decode_tag(data[8+7*i]);
			pdata.cinp[i].c_skill[3].decode_tag(data[9+7*i]);
			pdata.cinp[i].anp_get_data(pdata.cinp[i].prof,pdata.cinp[i].LV);
		}
		pdata.HP = Integer.valueOf(data[45]);
		update_GUI();
	}
	public void update_GUI() {
//		System.out.print(pdata.name+" ");
		level_t.setText("玩家等級："+pdata.level);
		money_t.setText("目前金錢："+pdata.money);
		HP_t.setText("HP："+String.valueOf(pdata.HP));
		for(int i = 0;i < 6;i++) {
//			System.out.print(pdata.cinp[i].prof+" ");
			if(pdata.cinp[i].prof==0) {
				putc[i].setVisible(false);
				putc[i].setDisable(true);
			}
			else {
				putc[i].setVisible(true);
				putc[i].setDisable(false);
				final int t = i;
				if(pdata.cinp[i].prof==1) {
					Platform.runLater( () -> {
						putc[t].setGraphic(conp_t_n[t]);
					});
				}	
				if(pdata.cinp[i].prof==2) {
					Platform.runLater( () -> {
						putc[t].setGraphic(conp_a_n[t]);
					});
				}	
				if(pdata.cinp[i].prof==3) {
					Platform.runLater( () -> {
						putc[t].setGraphic(conp_s_n[t]);
					});
				}	
				pdata.cinp[i].df.set_text(pdata.cinp[i]);
			}
		}
//		System.out.println();
	}
	public Player_data pdata = new Player_data();
	public AnchorPane pane = new AnchorPane();
	public ImageView background = new ImageView(new Image(getClass().getResourceAsStream("battle_p/battle_back.png")));
	public Button[] putc = new Button[6];
	public Button   next_p = new Button();
	public ImageView next_n = new ImageView(new Image(getClass().getResourceAsStream("battle_p/battlep_next_normal.png")));
	public ImageView next_s = new ImageView(new Image(getClass().getResourceAsStream("battle_p/battlep_next_select.png")));
	public ImageView[] conp_t_n = new ImageView[6];
	public ImageView[] conp_a_n = new ImageView[6];
	public ImageView[] conp_s_n = new ImageView[6];
	public Text HP_t = new Text();
	public Text level_t = new Text();
	public Text money_t = new Text();
	public Text name_t = new Text();
}
