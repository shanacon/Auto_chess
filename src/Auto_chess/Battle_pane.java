package Auto_chess;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Battle_pane {
	public C_in_battle[] cb = new C_in_battle[6];
	public Button[] cbtn = new Button[6];
	public AnchorPane pane = new AnchorPane();
	public ImageView back = new ImageView(new Image(getClass().getResourceAsStream("battle_p/b_pane_back.png")));
	public Battle_pane(C_in_prepare cp[],boolean left) {
		back.setLayoutX(0);
		back.setLayoutY(0);
		pane.getChildren().addAll(back);
		for(int i = 0;i < 6;i++) {
			cb[i] = new C_in_battle(cp[i]);
			cbtn[i] = new Button();
			cbtn[i].setStyle("-fx-background-color: transparent");
		}
		if(left == true) {
			for(int i = 0;i < 3;i++) {
				if(cb[i].prof!=0) {
					cb[i].maxhp.setLayoutX(27);
					cb[i].maxhp.setLayoutY(421-134*i);
					cb[i].nowhp.setLayoutX(27);
					cb[i].nowhp.setLayoutY(421-134*i);
					pane.getChildren().addAll(cb[i].maxhp,cb[i].nowhp);
				}
				cbtn[i].setLayoutX(29);
				cbtn[i].setLayoutY(308-140*i);
				if(cb[i].prof==1)
					cbtn[i].setGraphic(cb[i].cinb_t);
				if(cb[i].prof==2)
					cbtn[i].setGraphic(cb[i].cinb_a);
				if(cb[i].prof==3)
					cbtn[i].setGraphic(cb[i].cinb_s);
				pane.getChildren().addAll(cbtn[i]);
			}
			for(int i = 0;i < 3;i++) {
				if(cb[i+3].prof!=0) {
					cb[i+3].maxhp.setLayoutX(182);
					cb[i+3].maxhp.setLayoutY(421-134*i);
					cb[i+3].nowhp.setLayoutX(182);
					cb[i+3].nowhp.setLayoutY(421-134*i);
					pane.getChildren().addAll(cb[i+3].maxhp,cb[i+3].nowhp);
				}
				cbtn[i+3].setLayoutX(183);
				cbtn[i+3].setLayoutY(308-140*i);
				if(cb[i+3].prof==1)
					cbtn[i+3].setGraphic(cb[i+3].cinb_t);
				if(cb[i+3].prof==2)
					cbtn[i+3].setGraphic(cb[i+3].cinb_a);
				if(cb[i+3].prof==3)
					cbtn[i+3].setGraphic(cb[i+3].cinb_s);
				pane.getChildren().addAll(cbtn[i+3]);
			}
		}
		else {
			for(int i = 0;i < 3;i++) {
				if(cb[i].prof!=0) {
					cb[i].maxhp.setLayoutX(182);
					cb[i].maxhp.setLayoutY(421-134*i);
					cb[i].nowhp.setLayoutX(182);
					cb[i].nowhp.setLayoutY(421-134*i);
					pane.getChildren().addAll(cb[i].maxhp,cb[i].nowhp);
				}
				cbtn[i].setLayoutX(183);
				cbtn[i].setLayoutY(308-140*i);
				cbtn[i].setRotate(180);
				if(cb[i].prof==1)
					cbtn[i].setGraphic(cb[i].cinb_t);
				if(cb[i].prof==2)
					cbtn[i].setGraphic(cb[i].cinb_a);
				if(cb[i].prof==3)
					cbtn[i].setGraphic(cb[i].cinb_s);
				pane.getChildren().addAll(cbtn[i]);
			}
			for(int i = 0;i < 3;i++) {
				if(cb[i+3].prof!=0) {
					cb[i+3].maxhp.setLayoutX(27);
					cb[i+3].maxhp.setLayoutY(421-134*i);
					cb[i+3].nowhp.setLayoutX(27);
					cb[i+3].nowhp.setLayoutY(421-134*i);
					pane.getChildren().addAll(cb[i+3].maxhp,cb[i+3].nowhp);
				}
				cbtn[i+3].setLayoutX(29);
				cbtn[i+3].setLayoutY(308-140*i);
				cbtn[i+3].setRotate(180);
				if(cb[i+3].prof==1)
					cbtn[i+3].setGraphic(cb[i+3].cinb_t);
				if(cb[i+3].prof==2)
					cbtn[i+3].setGraphic(cb[i+3].cinb_a);
				if(cb[i+3].prof==3)
					cbtn[i+3].setGraphic(cb[i+3].cinb_s);
				pane.getChildren().addAll(cbtn[i+3]);
			}
		}
		set_df();
	}
	public void set_df() {
		for(int i = 0;i < 6;i++) {
			if(cb[i].prof!=0) {
				cb[i].df.pane.setLayoutX(cbtn[i].getLayoutX()+175);
				cb[i].df.pane.setLayoutY(cbtn[i].getLayoutY());
				cb[i].df.set_text(cb[i]);
				cb[i].df.pane.setVisible(false);
				pane.getChildren().addAll(cb[i].df.pane);
			}
			final int t = i;
			cbtn[i].addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
				cb[t].df.pane.setVisible(true);
			});
			cbtn[i].addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
				cb[t].df.pane.setVisible(false);
			});
		}
	}
	public int get_target() {
		for(int i = 5;i > -1;i--) {
			if(cb[i].HP > 0)
				return i;
		}
		return -1;
	}
}
