package Auto_chess;

import java.util.HashMap;
import java.util.Map;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Battle_space {
	public Map<String,Integer> apm = new  HashMap<String,Integer>();
	public Battle_space() {
		setscene();
	}
	public void set_apm(String p1,String p2,String p3) {
		apm.put(p1, 0);
		anp[0].set(p1);
		apm.put(p2, 1);
		anp[1].set(p2);
		apm.put(p3, 2);
		anp[2].set(p3);
		anp[0].pane.setVisible(false);
		anp[1].pane.setVisible(false);
		anp[2].pane.setVisible(false);
		anp[0].pane.toFront();
		anp[1].pane.toFront();
		anp[2].pane.toFront();
		anchor.getChildren().addAll(anp[0].pane,anp[1].pane,anp[2].pane);
	}
	public void setscene() {
		for(int i = 0;i < 7;i++) {
			item_refresh_n[i]  = new ImageView(new Image(getClass().getResourceAsStream("battle_p/item_refresh_normal.png")));
			item_refresh_s[i]  = new ImageView(new Image(getClass().getResourceAsStream("battle_p/item_refresh_select.png")));
		}
		for(int i = 0;i < 6;i++)
			c_space[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/battlep_place.png")));
		b_c_n[0] =  new ImageView(new Image(getClass().getResourceAsStream("battle_p/c_tank_normal.png")));
		b_c_s[0] =  new ImageView(new Image(getClass().getResourceAsStream("battle_p/c_tank_select.png")));
		b_c_n[1] =  new ImageView(new Image(getClass().getResourceAsStream("battle_p/c_archer_normal.png")));
		b_c_s[1] =  new ImageView(new Image(getClass().getResourceAsStream("battle_p/c_archer_select.png")));
		b_c_n[2] =  new ImageView(new Image(getClass().getResourceAsStream("battle_p/c_sword_normal.png")));
		b_c_s[2] =  new ImageView(new Image(getClass().getResourceAsStream("battle_p/c_sword_select.png")));
		background.setLayoutX(0);
		background.setLayoutY(0);
		anchor.getChildren().addAll(this.background);
		setpc();
		setelse();
		setbuy();
		setir();
		setshop();
		setmode();
		win.setStyle("-fx-font-size: 50px;");
		win.setFill(Color.WHITE);
		win.setLayoutX(500);
		win.setLayoutY(500);
		win.setVisible(false);
		lose.setStyle("-fx-font-size: 50px;");
		lose.setFill(Color.WHITE);
		lose.setLayoutX(500);
		lose.setLayoutY(500);
		lose.setVisible(false);
		anchor.getChildren().addAll(win,lose);
		scene  = new Scene(anchor,1280,720);
	}
	public void setmode() {
		mode_n[0] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/mode_comp_normal.png")));
		mode_n[1] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/mode_equip_normal.png")));
		mode_n[2] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/mode_formation_normal.png")));
		mode_n[3] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/mode_sell_normal.png")));
		mode_s[0] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/mode_comp_select.png")));
		mode_s[1] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/mode_equip_select.png")));
		mode_s[2] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/mode_formation_select.png")));
		mode_s[3] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/mode_sell_select.png")));
		for(int i = 0;i < 4;i++) {
			mode[i] = new Button();
			mode[i].setGraphic(mode_n[i]);
			mode[i].setLayoutX(-20);
			mode[i].setLayoutY(415+72*i);
			mode[i].setStyle("-fx-background-color: transparent");
			final int t = i;
			mode[i].addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
				mode[t].setGraphic(mode_s[t]);
			});
			mode[i].addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
				if(mode[t].getGraphic()==mode_s[t]&&now_mode!=t)
					mode[t].setGraphic(mode_n[t]);
			});
			mode[i].addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
				set_mode(t);
			});
			anchor.getChildren().addAll(mode[i]);
		}
	}
	public void setpc() {
		for(int i = 0;i < 6;i++) {
			putc[i] = new Button();
			putc[i].setGraphic(c_space[i]);
			putc[i].setStyle("-fx-background-color: transparent");
			conp_t_n[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/c_onp_tank_n.png")));
			conp_t_s[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/c_onp_tank_s.png")));
			conp_a_n[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/c_onp_archer_n.png")));
			conp_a_s[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/c_onp_archer_s.png")));
			conp_s_n[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/c_onp_sword_n.png")));
			conp_s_s[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/c_onp_sword_s.png")));
			pdata.cinp[i].get_data(0,1,i);
			final int t = i;
			putc[i].addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
				if(pct2[t].getStatus()==Animation.Status.RUNNING) {
					pct[t].pause();
				}
				else {
					pdata.cinp[t].df.pane.setVisible(true);
				}
			});
			putc[i].addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
				if(pct[t].getStatus()==Animation.Status.PAUSED&&pdata.cinp[t].prof==0) {
					pct[t].play();
				}
				else {
					pdata.cinp[t].df.pane.setVisible(false);
				}
			});
			putc[i].addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
				if(now_mode==1) {
					int f1 = -1,f2 = -1;
					for(int j = 0;j < 7;j++) {
						if(e_i_choose[j]==true) {
							f1 = j;
							break;
						}
					}
					for(int j = 0;j < 3;j++) {
						if(e_up_choose[j]==true) {
							f2 = j;
							break;
						}
					}
					if(f1!=-1) {
						eq_skill(t,f1,true);
						reset_e_s();
					}
					else if(f2!=-1) {
						eq_skill(t,f2,false);
						reset_e_s();
					}
				}
				if(now_mode==2) {
					boolean flag = false,flag2 = false;
					int k,w;
					for(k=0;k < 3;k++) {
						if(c_choose[k] == true) {
							flag=true;
							break;
						}
					}
					for(w = 0;w < 6;w++) {
						if(cp_choose[w] == true) {
							flag2=true;
							break;
						}
					}
					if(flag==true&&pdata.cinp[t].prof==0) {
						pdata.cinp[t].get_data(k+1,1,t);
						pdata.cinp[t].df.set_text(pdata.cinp[t]);
						set_pc(k+1,t,true);
						reset_bc();
						set_bc_ani(2);
						putc[t].setRotate(0);
						putc[t].setOpacity(1);
					}
					else if(flag2==true) {
						if(w!=t) {
							C_in_prepare tmp = new C_in_prepare();
							tmp.copy(pdata.cinp[t]);
							pdata.cinp[t].copy(pdata.cinp[w]);
							pdata.cinp[w].copy(tmp);
							set_pc(pdata.cinp[t].prof,t,true);
							set_pc(pdata.cinp[w].prof,w,true);
							set_bc_ani(2);
							putc[t].setRotate(0);
							putc[t].setOpacity(1);
						}
						else {
							set_pc(pdata.cinp[w].prof,w,true);
							set_bc_ani(2);
						}
					}
					else {
						set_pc(pdata.cinp[t].prof,t,false);
						cp_choose[t]=true;
						set_bc_ani(1);
					}
				}
				if(now_mode==3) {
					if(pdata.cinp[t].prof!=0&&e.getClickCount() == 2) {
						putc[t].setGraphic(c_space[t]);
						putc[t].setVisible(false);
						if(pdata.cinp[t].LV>=2)
							pdata.up[pdata.cinp[t].LV-2]+=2;
						if(pdata.cinp[t].c_skill[2].level!=0) {
							if(pdata.cinp[t].c_skill[2].level==1)
								pdata.money+=1;
							if(pdata.cinp[t].c_skill[2].level==2)
								pdata.money+=2;
							if(pdata.cinp[t].c_skill[2].level==3)
								pdata.money+=7;
							if(pdata.cinp[t].c_skill[2].level==4)
								pdata.money+=25;
						}
						if(pdata.cinp[t].c_skill[3].level!=0) {
							if(pdata.cinp[t].c_skill[2].level==1)
								pdata.money+=1;
							if(pdata.cinp[t].c_skill[2].level==2)
								pdata.money+=2;
							if(pdata.cinp[t].c_skill[2].level==3)
								pdata.money+=7;
							if(pdata.cinp[t].c_skill[2].level==4)
								pdata.money+=25;
						}
						pdata.cinp[t].get_out(0);
					}
					refresh_have_i();
				}
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
		for(int i = 0;i < 6;i++)
			anchor.getChildren().addAll(putc[i]);
		for(int i = 0;i < 6;i++)
			anchor.getChildren().addAll(pdata.cinp[i].df.pane);
		for(int i = 0;i < 6;i++) {
			putc[i].setVisible(false);
			pct[i] = new RotateTransition(Duration.seconds(2),putc[i]);
			pct[i].setCycleCount(Animation.INDEFINITE);
			pct[i].setInterpolator(Interpolator.LINEAR);
			pct[i].setByAngle(360);
			pct2[i] = new Timeline();
			pct2[i].getKeyFrames().addAll(
					new KeyFrame(Duration.ZERO, new KeyValue(putc[i].opacityProperty(), 1))
					,new KeyFrame(new Duration(400), new KeyValue(putc[i].opacityProperty(), 0))
					,new KeyFrame(new Duration(800), new KeyValue(putc[i].opacityProperty(), 1)));
			pct2[i].setCycleCount(Animation.INDEFINITE);
		}
	}
	public void setbuy() {
		for(int i = 0;i < 4;i++)
			buyc[i] = new Button();
		for(int i = 0;i < 4;i++) {
			buyc[i].setLayoutX(101*i-20);
			buyc[i].setLayoutY(-15);
			buyc[i].setStyle("-fx-background-color: transparent");
		}
		for(int i = 0;i < 3;i++) {
			buyc[i].setGraphic(b_c_n[i]);
			final int t = i;
			buyc[i].addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
				buyc[t].setGraphic(b_c_s[t]);
			});
			buyc[i].addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
				if(c_choose[t]==false)
					buyc[t].setGraphic(b_c_n[t]);
			});
			buyc[i].addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
				if(now_mode ==2) {
					if(c_choose[t] == true) {
						c_choose[t] = false;
						set_bc_ani(2);
					}
					else {
						int p = count_c();
						if(p < pdata.level) {
							reset_bc();
							buyc[t].setGraphic(b_c_s[t]);
							c_choose[t] = true;
							set_bc_ani(1);
						}
					}
				}
			});
			anchor.getChildren().addAll(buyc[i]);
		}		
	}
	public void setir() {
		for(int i = 0;i < 3;i++) {
			i_up[i] = new Button();
			up_t[i] = new Text(":"+pdata.up[i]);
			up_t[i].setStyle("-fx-font-size: 36px;");
			up_t[i].setLayoutX(480+i*250);
			up_t[i].setLayoutY(175);
			i_up_n[i] =  new ImageView(new Image(getClass().getResourceAsStream("battle_p/sell_item/up_"+(i+1)+"_n.png")));
			i_up_s[i] =  new ImageView(new Image(getClass().getResourceAsStream("battle_p/sell_item/up_"+(i+1)+"_s.png")));
			i_up[i].setGraphic(i_up_n[i]);
			i_up[i].setStyle("-fx-background-color: transparent");
			i_up[i].setLayoutX(387+i*250);
			i_up[i].setLayoutY(100);
			final int t = i;
			i_up[i].addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
				i_up[t].setGraphic(i_up_s[t]);
			});
			i_up[i].addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
				if(m_up_hs[t] == false&&e_up_choose[t]==false)
					i_up[t].setGraphic(i_up_n[t]);
			});
			i_up[i].addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
				if(now_mode==0) {
					if(t!=2) {
						reset_m_s();
						i_up[t].setGraphic(i_up_s[t]);
						m_up_hs[t] = true;
					}
				}
				if(now_mode==1) {
					int f1 = -1,f2 = -1;
					for(int j = 0;j < 7;j++) {
						if(e_i_choose[j]==true) {
							f1 = j;
							break;
						}
					}
					for(int j = 0;j < 3;j++) {
						if(e_up_choose[j]==true) {
							f2 = j;
							break;
						}
					}
					if(f1==-1&&f2==-1)
						e_up_choose[t]=true;
					else{
						reset_e_s();
						if(f2!=t) {
							e_up_choose[t]=true;
							i_up[t].setGraphic(i_up_s[t]);
						}	
					}
				}
				if(now_mode==3) {
					if(e.getClickCount() == 2)
						sell_c_up(t);
				}
			});
			anchor.getChildren().addAll(i_up[i],up_t[i]);
		}
		for(int i = 0;i < 4;i++)
			m_i_hs[i] = 0;
		for(int i = 0;i < 7;i++) {
			item_refresh[i] =  new Button();
			item_have[i] = new Button();
			item_have[i].setStyle("-fx-background-color: transparent");
			item_have[i].setLayoutX(410+i*125);
			item_have[i].setLayoutY(10);
			pdata.sini[i] = new Skill_O(0,i,0);
			//////////
			item_h_c_n[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/sell_item/C_normal.png")));
			item_h_c_s[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/sell_item/C_choose.png")));
			item_h_b_n[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/sell_item/B_normal.png")));
			item_h_b_s[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/sell_item/B_choose.png")));
			item_h_a_n[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/sell_item/A_normal.png")));
			item_h_a_s[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/sell_item/A_choose.png")));
			item_h_s_n[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/sell_item/S_normal.png")));
			item_h_s_s[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/sell_item/S_choose.png")));
			/////////
//			item_have[i].setGraphic(item_h_c_n[i]);
			item_refresh[i].setGraphic(item_refresh_n[i]);
			item_refresh[i].setLayoutX(395+i*125);
			item_refresh[i].setLayoutY(-10);
			item_refresh[i].setStyle("-fx-background-color: transparent");
			final int t = i;
			item_refresh[i].addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
				item_refresh[t].setGraphic(item_refresh_s[t]);
			});
			item_refresh[i].addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
				if(item_refresh[t].getGraphic()==item_refresh_s[t])
					item_refresh[t].setGraphic(item_refresh_n[t]);
			});
			item_refresh[i].addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
					refresh_i_m(t);
					refresh_have_i();
					reset_m_s();
			});
			item_have[i].addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
				item_mouse_enter(t);
			});
			item_have[i].addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
				item_mouse_exit(t);
			});
			item_have[i].addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
				if(now_mode==0) {
					if(m_i_hl[t] == true) {
						m_i_hl[t]=false;
						int q = item_mouse_buy(t);
						m_i_hs[q]--;
					}
					else {
						int flag = -1,flag2 = -1;
						for(int j = 0;j < 4;j++) {
							if(m_i_hs[j]!=0) {
								flag = j;
								break;
							}
						}
						for(int j = 0;j < 3;j++) {
							if(m_up_hs[j]==true)
								flag2 = j;
						}
						int p = item_mouse_buy(t);
						if(flag==-1) {
							if(flag2!=-1) {
								for(int j = 0;j < 3;j++) {
									m_up_hs[j]=false;
									i_up[t].setGraphic(i_up_n[t]);
								}
							}
							int q = item_mouse_buy(t);
							m_i_hl[t] = true;
							m_i_hs[q]++;
						}
						else if(flag!=-1) {
							if(flag!=p){
								if(flag2!=-1) {
									for(int j = 0;j < 3;j++) {
										m_up_hs[j]=false;
										i_up[t].setGraphic(i_up_n[t]);
									}
								}
								reset_m_s();
								m_i_hl[t] = true;
								m_i_hs[p]++;
								item_mouse_enter(t);
							}
							else if(m_i_hs[flag]<3){
								m_i_hl[t] = true;
								m_i_hs[flag]++;	
							}
						}
					}
				}
				if(now_mode==1) {
					int f1 = -1,f2 = -1;
					for(int j = 0;j < 7;j++) {
						if(e_i_choose[j]==true) {
							f1 = j;
							break;
						}
					}
					for(int j = 0;j < 3;j++) {
						if(e_up_choose[j]==true) {
							f2 = j;
							break;
						}
					}
					if(f1==-1&&f2==-1)
						e_i_choose[t]=true;
					else{
						reset_e_s();
						if(f1!=t) {
							e_i_choose[t]=true;
							item_mouse_enter(t);
						}	
					}
				}
				if(now_mode==3) {
					if(e.getClickCount() == 2&&pdata.sini[t].level!=0)
						i_out(t,true);
				}
			});
			anchor.getChildren().addAll(item_have[i],item_refresh[i],pdata.sini[i].pane);
		}
	}
	public void setshop() {		
		for(int i = 0;i < 4;i++) {
			shop_s[i] = new Skill_O(0,i);
			shop_c_n[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/sell_item/C_normal.png")));
			shop_c_s[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/sell_item/C_choose.png")));
			shop_b_n[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/sell_item/B_normal.png")));
			shop_b_s[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/sell_item/B_choose.png")));
			shop_a_n[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/sell_item/A_normal.png")));
			shop_a_s[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/sell_item/A_choose.png")));
			shop_s_n[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/sell_item/S_normal.png")));
			shop_s_s[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/sell_item/S_choose.png")));
			shop_up_n[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/sell_item/up_1_n.png")));
			shop_up_s[i] = new ImageView(new Image(getClass().getResourceAsStream("battle_p/sell_item/up_1_s.png")));
			shop[i] = new Button();
			shop[i].setDisable(true);
			shop[i].setLayoutX(1153);
			shop[i].setLayoutY(146+i*149);
			shop[i].setStyle("-fx-background-color: transparent");
			anchor.getChildren().addAll(shop_s[i].pane,shop[i]);
			final int t = i;
			shop[i].addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
				shop_mouse_enter(t);
			});
			shop[i].addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
				shop_mouse_exit(t);
			});
			shop[i].addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
				buy_item(t);
			});
		}
	}
	public void setelse() {
		for(int i = 0;i < 3;i++)
			anp[i] = new Another_player();
		time_t.setLayoutX(1000);
		time_t.setLayoutY(160+3*149);
		time_t.setText(String.valueOf(time));
		time_t.setStyle("-fx-font-size: 36px;");
		time_t.setFill(Color.WHITE);
		HP_t.setLayoutX(1000);
		HP_t.setLayoutY(260+3*149);
		HP_t.setText("HP："+String.valueOf(pdata.HP));
		HP_t.setStyle("-fx-font-size: 36px;");
		HP_t.setFill(Color.WHITE);
		lastt.getKeyFrames().addAll(new KeyFrame(Duration.millis(1000), e -> rf_time()));
		lastt.setCycleCount(Timeline.INDEFINITE);
		level_t.setText("玩家等級："+pdata.level);
		money_t.setText("目前金錢："+pdata.money);
		upg_t.setText("升級所需金錢："+upgg);
		name_t.setText(pdata.name);
		name_t.setLayoutX(0);
		name_t.setLayoutY(170);
		name_t.setStyle("-fx-font-size: 36px;");
		name_t.setFill(Color.WHITE);
		level_t.setLayoutX(0);
		level_t.setLayoutY(220);
		level_t.setStyle("-fx-font-size: 36px;");
		level_t.setFill(Color.WHITE);
		money_t.setLayoutX(0);
		money_t.setLayoutY(270);
		money_t.setStyle("-fx-font-size: 36px;");
		money_t.setFill(Color.WHITE);
		upg_t.setLayoutX(0);
		upg_t.setLayoutY(320);
		upg_t.setStyle("-fx-font-size: 24px;");
		upg_t.setFill(Color.WHITE);
		next_p.setLayoutX(1036);
		next_p.setLayoutY(388);
		next_p.setGraphic(next_n);
		merge.setLayoutX(183);
		merge.setLayoutY(106);
		r_shop.setLayoutX(1018);
		r_shop.setLayoutY(180);
		r_shop.setGraphic(refresh_shop_n);
		merge.setGraphic(merge_n);
		upg.setGraphic(upg_n);
		upg.setLayoutX(284);
		upg.setLayoutY(106);
		upg.setStyle("-fx-background-color: transparent");
		r_shop.setStyle("-fx-background-color: transparent");
		next_p.setStyle("-fx-background-color: transparent");
		merge.setStyle("-fx-background-color: transparent");
		upg.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			upg.setGraphic(upg_s);
		});
		upg.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
				upg.setGraphic(upg_n);
		});
		upg.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			up_level();
		});
		next_p.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			next_p.setGraphic(next_s);
		});
		next_p.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			if(next_p.getGraphic()==next_s)
				next_p.setGraphic(next_n);
		});
		next_p.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			see_anp();
		});
		merge.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			merge.setGraphic(merge_s);
		});
		merge.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			if(merge.getGraphic()==merge_s)
				merge.setGraphic(merge_n);
		});
		merge.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			if(now_mode==0) {
				for(int i = 0;i < 3;i++) {
					if(m_up_hs[i]==true) {
						if(pdata.up[i]>2) {
							pdata.up[i]-=3;
							pdata.up[i+1]++;
							refresh_have_i();
							reset_m_s();
						}
					}
				}
				for(int i = 0;i < 4;i++) {
					int l = 0;
					if(m_i_hs[i]==3) {
						for(int j = 0;j < 7;j++) {
							if(m_i_hl[j]==true) {
								l = pdata.sini[j].level;
								break;
							}
						}
						if(l!=4&&l!=0) {
							for(int j = 0;j < 7;j++) {
								if(m_i_hl[j]==true) {
									pdata.sini[j].level = 0;
									pdata.sini[j].text.setText("--------");
									pdata.sini[j].refresh_skill(0);
									item_have[j].setVisible(false);
									item_have[j].setDisable(true);
								}
							}
							int p = get_i_place();
							pdata.sini[p].refresh_skill(l+1);
							refresh_have_i();
							reset_m_s();
						}
					}
				}
			}
		});
		r_shop.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			r_shop.setGraphic(refresh_shop_s);
		});
		r_shop.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
			if(pdata.money >= 3) {
				pdata.money-=3;
				refresh_shop(pdata.level);
			}
			reset_m_s();
			refresh_have_i();
		});
		r_shop.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			if(r_shop.getGraphic()==refresh_shop_s)
				r_shop.setGraphic(refresh_shop_n);
		});
		for(int i = 0;i < 3;i++) {
			anp[i].next_p.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
				see_anp();
			});
		}
		anchor.getChildren().addAll(next_p,merge,r_shop,level_t,name_t,money_t,upg,upg_t,time_t,HP_t);
	}
	public void refresh_pd() {
		level_t.setText("玩家等級："+pdata.level);
		money_t.setText("目前金錢："+pdata.money);
		name_t.setText(pdata.name);
	}
	public void set_mode(int m) {
		now_mode = m;
		mode_switch(m);
		for(int i = 0;i < 4;i++) {
			if(i!=m)
				mode[i].setGraphic(mode_n[i]);
		}
	}
	public void refresh_shop(int level){
		for(int i = 0;i < 4;i++) {
			shop[i].setDisable(false);
			shop[i].setVisible(true);
			double ra = Math.random();
			if(pro.p[level][0]<=ra&&ra < pro.p[level][1]) {
				shop[i].setGraphic(shop_up_n[i]);
				shop_s[i].refresh_skill(0);;
			}
			else if(pro.p[level][1] <= ra&pro.p[level][2] > ra) {
				shop[i].setGraphic(shop_c_n[i]);
				shop_s[i].refresh_skill(1);;
			}
			else if(pro.p[level][2] <= ra&pro.p[level][3] > ra) {
				shop[i].setGraphic(shop_b_n[i]);
				shop_s[i].refresh_skill(2);;
			}
			else if(pro.p[level][3] <= ra&pro.p[level][4] > ra) {
				shop[i].setGraphic(shop_a_n[i]);
				shop_s[i].refresh_skill(3);;
			}
			else if(pro.p[level][4] <= ra&pro.p[level][5] > ra) {
				shop[i].setGraphic(shop_s_n[i]);
				shop_s[i].refresh_skill(4);;
			}
		}
	}
	public void shop_mouse_enter(int num) {
		shop_s[num].pane.setVisible(true);
		if(shop[num].getGraphic()==shop_up_n[num])
			shop[num].setGraphic(shop_up_s[num]);
		if(shop[num].getGraphic()==shop_s_n[num])
			shop[num].setGraphic(shop_s_s[num]);
		if(shop[num].getGraphic()==shop_a_n[num])
			shop[num].setGraphic(shop_a_s[num]);
		if(shop[num].getGraphic()==shop_b_n[num])
			shop[num].setGraphic(shop_b_s[num]);
		if(shop[num].getGraphic()==shop_c_n[num])
			shop[num].setGraphic(shop_c_s[num]);
	}
	public void shop_mouse_exit(int num) {
		shop_s[num].pane.setVisible(false);
		if(shop[num].getGraphic()==shop_up_s[num])
			shop[num].setGraphic(shop_up_n[num]);
		if(shop[num].getGraphic()==shop_s_s[num])
			shop[num].setGraphic(shop_s_n[num]);
		if(shop[num].getGraphic()==shop_a_s[num])
			shop[num].setGraphic(shop_a_n[num]);
		if(shop[num].getGraphic()==shop_b_s[num])
			shop[num].setGraphic(shop_b_n[num]);
		if(shop[num].getGraphic()==shop_c_s[num])
			shop[num].setGraphic(shop_c_n[num]);
	}
	public void item_mouse_enter(int num) {
		pdata.sini[num].pane.setVisible(true);
		if(item_have[num].getGraphic()==item_h_s_n[num])
			item_have[num].setGraphic(item_h_s_s[num]);
		if(item_have[num].getGraphic()==item_h_a_n[num])
			item_have[num].setGraphic(item_h_a_s[num]);
		if(item_have[num].getGraphic()==item_h_b_n[num])
			item_have[num].setGraphic(item_h_b_s[num]);
		if(item_have[num].getGraphic()==item_h_c_n[num])
			item_have[num].setGraphic(item_h_c_s[num]);
	}
	public int item_mouse_buy(int num) {
		if(item_have[num].getGraphic()==item_h_s_s[num])
			return 3;
		if(item_have[num].getGraphic()==item_h_a_s[num])
			return 2;
		if(item_have[num].getGraphic()==item_h_b_s[num])
			return 1;
		if(item_have[num].getGraphic()==item_h_c_s[num])
			return 0;
		return -1;
	}
	public void item_mouse_exit(int num) {
		pdata.sini[num].pane.setVisible(false);
//		int flag = -1;
//		for(int j = 0;j < 7;j++) {
//			if(m_i_hs[j]!=0) {
//				flag = j;
//				break;
//			}
//		}
		if(m_i_hl[num]==false&&e_i_choose[num]==false) {
			if(item_have[num].getGraphic()==item_h_s_s[num])
				item_have[num].setGraphic(item_h_s_n[num]);
			if(item_have[num].getGraphic()==item_h_a_s[num])
				item_have[num].setGraphic(item_h_a_n[num]);
			if(item_have[num].getGraphic()==item_h_b_s[num])
				item_have[num].setGraphic(item_h_b_n[num]);
			if(item_have[num].getGraphic()==item_h_c_s[num])
				item_have[num].setGraphic(item_h_c_n[num]);
		}
	}
	public void mode_switch(int mode) {
		reset_m_s();
		reset_bc();
		refresh_have_i();
		reset_e_s();
		set_bc_ani(2);
		for(int i = 0;i < 6;i++)
			set_pc(pdata.cinp[i].prof,i,true);
		if(mode == 0) {
			for(int i = 0;i < 7;i++)
				item_have[i].setDisable(false);
			for(int i = 0;i < 4;i++)
				buyc[i].setDisable(true);
			for(int i = 0;i < 6;i++)
				putc[i].setDisable(true);
		}
		if(mode == 1) {
			for(int i = 0;i < 7;i++)
				item_have[i].setDisable(false);
			for(int i = 0;i < 4;i++)
				buyc[i].setDisable(true);
			for(int i = 0;i < 6;i++)
				putc[i].setDisable(false);
		}
		if(mode == 2) {
			for(int i = 0;i < 7;i++)
				item_have[i].setDisable(true);
			for(int i = 0;i < 4;i++)
				buyc[i].setDisable(false);
			for(int i = 0;i < 6;i++)
				putc[i].setDisable(false);
		}
		if(mode == 3) {
			for(int i = 0;i < 7;i++)
				item_have[i].setDisable(false);
			for(int i = 0;i < 4;i++)
				buyc[i].setDisable(true);
			for(int i = 0;i < 6;i++)
				putc[i].setDisable(false);
		}
	}
	public void buy_item(int n) {
		reset_m_s();
		reset_bc();
		refresh_have_i();
		reset_e_s();
		if(shop_s[n].level==0) {
			if(pdata.money>=10) {
				pdata.money-=10;
				pdata.up[0]++;
				shop[n].setDisable(true);
				shop[n].setVisible(false);
				refresh_have_i();
			}
			return;
		}
		int p = get_i_place();
		if(p==7){
			return;
		}
		boolean flag = false;
		if(shop_s[n].level==1) {
			if(pdata.money>=1) {
				flag=true;
				pdata.money-=1;
			}
		}
		else if(shop_s[n].level==2){
			if(pdata.money>=4) {
				flag=true;
				pdata.money-=4;
			}
		}
		else if(shop_s[n].level==3){
			if(pdata.money>=15) {
				flag=true;
				pdata.money-=15;
			}
		}
		else if(shop_s[n].level==4){
			if(pdata.money>=50) {
				flag=true;
				pdata.money-=50;
			}
		}
		if(flag==true) {
			pdata.sini[p].buy_S(shop_s[n],p);
			shop[n].setDisable(true);
			shop[n].setVisible(false);
		}
		refresh_have_i();
	}
	public int get_i_place() {
		int p;
		for(p = 0;p < 7;p++){
			if(pdata.sini[p].level==0)
				break;
		}
		return p;
	}
	public void refresh_have_i() {
		for(int i = 0;i < 7;i++) {
			if(pdata.sini[i].level!=0) {
				item_have[i].setVisible(true);
				item_have[i].setDisable(false);
			}
			if(pdata.sini[i].level==1)
				item_have[i].setGraphic(item_h_c_n[i]);
			else if(pdata.sini[i].level==2)
				item_have[i].setGraphic(item_h_b_n[i]);
			else if(pdata.sini[i].level==3)
				item_have[i].setGraphic(item_h_a_n[i]);
			else if(pdata.sini[i].level==4)
				item_have[i].setGraphic(item_h_s_n[i]);
		}
		for(int i = 0;i < 3;i++)
			up_t[i].setText(":"+pdata.up[i]);
		level_t.setText("玩家等級："+pdata.level);
		money_t.setText("目前金錢："+pdata.money);
		HP_t.setText("HP："+pdata.HP);
		upg_t.setText("升級所需金錢："+upgg);
		time_t.setText(String.valueOf(time));
	}
	public void reset_m_s(){
		for(int i = 0;i < 4;i++)
			m_i_hs[i] = 0;
		for(int i = 0;i < 3;i++)
			m_up_hs[i] = false;
		for(int i = 0;i < 7;i++)
			m_i_hl[i] = false;
		for(int i = 0;i < 7;i++)
			item_mouse_exit(i);
		for(int i = 0;i < 3;i++)
			i_up[i].setGraphic(i_up_n[i]);
	}
	public void reset_bc() {
		for(int i = 0;i < 3;i++) {
			buyc[i].setGraphic(b_c_n[i]);
			c_choose[i] = false;
		}
	}
	public void set_bc_ani(int s) { //1 for start 2 for stop;
		if(s==1) {
			for(int i = 0;i < 6;i++) {
				if(pdata.cinp[i].prof==0) {
					putc[i].setVisible(true);
					pct[i].play();
					pct2[i].play();
				}
			}
		}
		else {
			for(int i = 0;i < 6;i++) {
				if(pdata.cinp[i].prof==0)
					putc[i].setVisible(false);
				pct[i].stop();
				pct2[i].stop();
			}
		}
	}
	public void set_pc(int p,int pos,boolean flag) {
		if(flag==true) {
			if(p==0) {
				putc[pos].setGraphic(c_space[pos]);
				putc[pos].setOpacity(0);
			}
			if(p==1)
				putc[pos].setGraphic(conp_t_n[pos]);
			if(p==2)
				putc[pos].setGraphic(conp_a_n[pos]);
			if(p==3)
				putc[pos].setGraphic(conp_s_n[pos]);
			for(int i = 0;i < 6;i++)
				cp_choose[i] = false;
		}
		else {
			if(p==1)
				putc[pos].setGraphic(conp_t_s[pos]);
			if(p==2)
				putc[pos].setGraphic(conp_a_s[pos]);
			if(p==3)
				putc[pos].setGraphic(conp_s_s[pos]);
		}
	}
	public void up_level() {
		if(pdata.money>=upgg&&pdata.level<6) {
			pdata.money-=upgg;
			pdata.level++;
		}
		if(pdata.level==1)
			upgg = 5;
		else if(pdata.level==2)
			upgg = 15;
		else if(pdata.level==3)
			upgg = 30;
		else if(pdata.level==4)
			upgg = 55;
		else if(pdata.level==5)
			upgg = 80;
		else if(pdata.level==6)
			upgg = 9999;
		refresh_have_i();
	}
	public void refresh_i_m(int t) {
		if(pdata.sini[t].level==1) {
			if(pdata.money>=1) {
				pdata.money-=1;
				pdata.sini[t].refresh_skill(pdata.sini[t].level);
			}
		}
		else if(pdata.sini[t].level==2) {
			if(pdata.money>=2) {
				pdata.money-=2;
				pdata.sini[t].refresh_skill(pdata.sini[t].level);
			}
		}
		else if(pdata.sini[t].level==3) {
			if(pdata.money>=7) {
				pdata.money-=7;
				pdata.sini[t].refresh_skill(pdata.sini[t].level);
			}
		}
		else if(pdata.sini[t].level==4) {
			if(pdata.money>=24) {
				pdata.money-=24;
				pdata.sini[t].refresh_skill(pdata.sini[t].level);
			}
		}
	}
	public void reset_e_s() {
		for(int i = 0;i < 7;i++) {
			e_i_choose[i] = false;
			item_mouse_exit(i);
		}
		for(int i  = 0;i < 3;i++) {
			e_up_choose[i] = false;
			i_up[i].setGraphic(i_up_n[i]);
		}
	}
	public void eq_skill(int pos,int spos,boolean flag) {//true for skill,false for up
		if(flag == true) {
			int epos = 2;
			if(pdata.cinp[pos].c_skill[epos].level != 0)
				epos = 3;
			if(pdata.cinp[pos].c_skill[epos].level != 0)
				epos = 4;
			if(epos==4)
				return;
			pdata.cinp[pos].eq_skill(epos, pdata.sini[spos]);
			i_out(spos,false);
			pdata.cinp[pos].df.set_text(pdata.cinp[pos]);	
		}
		else {
			if(pdata.cinp[pos].LV==1&&spos==1&&pdata.up[1]>=1) {
				pdata.cinp[pos].get_data(pdata.cinp[pos].prof, 2, pos);
				pdata.up[1]--;
			}
			else if(pdata.cinp[pos].LV==2&&spos==2&&pdata.up[2]>=1) {
				pdata.cinp[pos].get_data(pdata.cinp[pos].prof, 3, pos);
				pdata.up[2]--;
			}
		}
		refresh_have_i();
	}
	public void i_out(int pos,boolean sell) {
		item_have[pos].setVisible(false);
		item_have[pos].setDisable(true);
		if(sell==true) {
			if(pdata.sini[pos].level==1)
				pdata.money+=1;
			if(pdata.sini[pos].level==2)
				pdata.money+=2;
			if(pdata.sini[pos].level==3)
				pdata.money+=7;
			if(pdata.sini[pos].level==4)
				pdata.money+=25;
		}
		pdata.sini[pos].level=0;
		refresh_have_i();
	}
	public void sell_c_up(int pos) {
		if(pos==0)
			pdata.money+=5;
		if(pos==1)
			pdata.money+=12;
		if(pos==2)
			pdata.money+=40;
		pdata.up[pos]--;
		refresh_have_i();
	}
	public int count_c() {
		int ret = 0;
		for(int i = 0;i < 6;i++) {
			if(pdata.cinp[i].prof!=0)
				ret++;
		}
		return ret;
	}
	public void see_anp() {
		now_p++;
		if(now_p==4) {
			anp[0].pane.setVisible(false);
			anp[1].pane.setVisible(false);
			anp[2].pane.setVisible(false);
			now_p = 0;
		}
		else {
			anp[0].pane.setVisible(false);
			anp[1].pane.setVisible(false);
			anp[2].pane.setVisible(false);
			anp[now_p-1].pane.setVisible(true);
		}
	}
	public void rf_time() {
		if(time > 0) {
			time--;
			time_t.setText(String.valueOf(time));
		}
		else
			lastt.stop();
	}
	public int time = 10;
	public Another_player[] anp = new Another_player[4];
	public int now_p = 0;
	public int[] m_i_hs = new int[4];
	public boolean[] m_i_hl = new boolean[7];
	public boolean[] m_up_hs = new boolean[3];
	public boolean[] c_choose = new boolean[3];
	public boolean[] cp_choose = new boolean[6];
	public boolean[] e_i_choose = new boolean[7];
	public boolean[] e_up_choose = new boolean[3];
	public int now_mode = -1;
	public Skill_O[] shop_s = new Skill_O[4];
	public Scene scene;
	public AnchorPane anchor = new AnchorPane();
	public Button   next_p = new Button();
	public Button   merge = new Button();
	public Button   r_shop = new Button();
	public Button   upg = new Button();
	public Button[] mode = new Button[4];
	public Button[] buyc = new Button[4];
	public Button[] putc = new Button[6];
	public Button[] shop = new Button[4];
	public Button[] i_up = new Button[3];
	public Button[] item_refresh = new Button[7];
	public Button[] item_have = new Button[7];
	public RotateTransition[] pct = new RotateTransition[6];
	public Timeline[] pct2 = new Timeline[6];
	public Timeline lastt = new Timeline();
	public ImageView background = new ImageView(new Image(getClass().getResourceAsStream("battle_p/battle_back.png")));
	public ImageView refresh_shop_n = new ImageView(new Image(getClass().getResourceAsStream("battle_p/refresh_normal.png")));
	public ImageView refresh_shop_s = new ImageView(new Image(getClass().getResourceAsStream("battle_p/refresh_select.png")));
	public ImageView merge_n = new ImageView(new Image(getClass().getResourceAsStream("battle_p/mode_comp_normal.png")));
	public ImageView merge_s = new ImageView(new Image(getClass().getResourceAsStream("battle_p/mode_comp_select.png")));
	public ImageView next_n = new ImageView(new Image(getClass().getResourceAsStream("battle_p/battlep_next_normal.png")));
	public ImageView next_s = new ImageView(new Image(getClass().getResourceAsStream("battle_p/battlep_next_select.png")));
	public ImageView upg_n = new ImageView(new Image(getClass().getResourceAsStream("battle_p/upg_n.png")));
	public ImageView upg_s = new ImageView(new Image(getClass().getResourceAsStream("battle_p/upg_s.png")));
	public ImageView[] conp_t_n = new ImageView[6];
	public ImageView[] conp_t_s = new ImageView[6];
	public ImageView[] conp_a_n = new ImageView[6];
	public ImageView[] conp_a_s = new ImageView[6];
	public ImageView[] conp_s_n = new ImageView[6];
	public ImageView[] conp_s_s = new ImageView[6];
	public ImageView i_up_n[] = new ImageView[3];
	public ImageView i_up_s[]= new ImageView[3];
	public ImageView[] shop_up_n = new ImageView[4];
	public ImageView[] shop_up_s = new ImageView[4];
	public ImageView[] shop_c_n = new ImageView[4];
	public ImageView[] shop_c_s = new ImageView[4];
	public ImageView[] shop_b_n = new ImageView[4];
	public ImageView[] shop_b_s = new ImageView[4];
	public ImageView[] shop_a_n = new ImageView[4];
	public ImageView[] shop_a_s = new ImageView[4];
	public ImageView[] shop_s_n = new ImageView[4];
	public ImageView[] shop_s_s = new ImageView[4];
	public ImageView[] b_c_n = new ImageView[3];
	public ImageView[] b_c_s = new ImageView[3];
	public ImageView[] c_space = new ImageView[6];
	public ImageView[] item_h_c_n = new ImageView[7];
	public ImageView[] item_h_c_s = new ImageView[7];
	public ImageView[] item_h_b_n = new ImageView[7];
	public ImageView[] item_h_b_s = new ImageView[7];
	public ImageView[] item_h_a_n = new ImageView[7];
	public ImageView[] item_h_a_s = new ImageView[7];
	public ImageView[] item_h_s_n = new ImageView[7];
	public ImageView[] item_h_s_s = new ImageView[7];
	public ImageView[] item_refresh_n = new ImageView[7];
	public ImageView[] item_refresh_s = new ImageView[7];
	public ImageView[] mode_n = new ImageView[4];
	public ImageView[] mode_s = new ImageView[4];
	public Probability pro = new Probability();
	public Player_data pdata = new Player_data();
	public Text level_t = new Text();
	public Text win = new Text("You win！！！");
	public Text lose = new Text("You lose！！！");
	public Text money_t = new Text();
	public Text HP_t = new Text();
	public Text upg_t = new Text();
	public Text name_t = new Text();
	public Text time_t = new Text();
	public Text[] up_t = new Text[3];
	public int upgg = 5;
}
