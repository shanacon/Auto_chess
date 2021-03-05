package Auto_chess;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class A_battle {
	public Battle_pane p1;
	public Battle_pane p2;
	public String p1n;
	public String p2n;
	public Text p1t = new Text();
	public Text p2t = new Text();
	public Scene scene;
	public AnchorPane anchor = new AnchorPane();
	public ImageView background = new ImageView(new Image(getClass().getResourceAsStream("battle_p/b_background.jpg")));
	public A_battle(C_in_prepare p1c[],C_in_prepare[] p2c,String p1n,String p2n,boolean left) {
		background.setLayoutX(0);
		background.setLayoutY(0);
		if(left==true) {
			p1 = new Battle_pane(p1c,true);
			p2 = new Battle_pane(p2c,false);
		}
		else {
			p1 = new Battle_pane(p2c,true);
			p2 = new Battle_pane(p1c,false);
			String tmp = p1n.toString();
			p1n = p2n.toString();
			p2n = tmp.toString();
		}
		p1t.setLayoutX(100);
		p1t.setLayoutY(50);
		p2t.setLayoutX(1000);
		p2t.setLayoutY(50);
		p1t.setText(p1n);
		p2t.setText(p2n);
		p1t.setStyle("-fx-font-size: 50px;");
		p2t.setStyle("-fx-font-size: 50px;");
		p1t.setFill(Color.WHITE);
		p2t.setFill(Color.WHITE);
		p1t.setText(p1n);
		p2t.setText(p2n);
		p1.pane.setLayoutX(175);
		p1.pane.setLayoutY(227);
		p2.pane.setLayoutX(714);
		p2.pane.setLayoutY(227);
		anchor.getChildren().addAll(background,p1.pane,p2.pane,p1t,p2t);
		scene  = new Scene(anchor,1280,720);
	}
	public String f_send_battle() {
		String ret = "";
//		for(int i = 5;i > -1;i--) {
//			if(p1.cb[i].prof!=0&&p1.cb[i].HP!=0) {
//				ret = ret+"l"+i+"0"+ " ";
//			}
//			if(p2.cb[i].prof!=0&&p2.cb[i].HP!=0) {
//				ret = ret+"r"+i+"0"+ " ";
//			}
//		}
		ret = ret+"HP" + "\t";
		for(int i = 0;i < 6;i++)
			ret = ret+p1.cb[i].HP+ "\t";
		for(int i = 0;i < 6;i++)
			ret = ret+p2.cb[i].HP+ "\t";
//		System.out.println(ret);
		return ret;
	}
	public boolean bend(String aya) {
		boolean ret = false;
		String[] data = aya.split("\t");
		int tag = 0;
		while(data[tag].equals("HP")==false)
			tag++;
		tag++;
		for(int i = 0;i < 6;i++) {
//			System.out.println("set HP¡G"+Integer.valueOf(data[tag]));
			p1.cb[i].HP = Integer.valueOf(data[tag]);
			p1.cb[i].refresh_hp();
			tag++;
		}
		for(int i = 0;i < 6;i++) {
//			System.out.println("set HP¡G"+Integer.valueOf(data[tag]));
			p2.cb[i].HP = Integer.valueOf(data[tag]);
			p2.cb[i].refresh_hp();
			tag++;
		}
		for(int i = 0;i < 6;i++) {
//			System.out.println("check HP¡G"+p1.cb[i].HP);
			if(p1.cb[i].HP != 0)
				break;
			else if(i==5)
				ret = true;
		}
		for(int i = 0;i < 6;i++) {
//			System.out.println("check HP¡G"+p2.cb[i].HP);
			if(p2.cb[i].HP != 0)
				break;
			else if(i==5)
				ret = true;
		}
//		System.out.println("return "+ ret);
		return ret;
	}
	public String b_action(String aya,boolean left) {
		String ret = "";
		String[] data = aya.split("\t");
		if(data[0].charAt(0)!='l'&&data[0].charAt(0)!='r') {
			refresh_all();
			if(bend(aya)==true)
				return "end";
			else {
				for(int i = 5;i > -1;i--) {
					if(p1.cb[i].prof!=0&&p1.cb[i].HP!=0) {
						ret = ret+"l"+i+"0"+ "\t";
					}
					if(p2.cb[i].prof!=0&&p2.cb[i].HP!=0) {
						ret = ret+"r"+i+"0"+ "\t";
					}
				}
				ret = ret + aya.toString();
				return ret;
			}
		}
		else if(data[0].charAt(0)=='l') {
			if(left==true) {
				if(data[0].charAt(2)=='0') {
					int tar = p2.get_target();
					refresh_all();
					if(p1.cb[Integer.valueOf(data[0].charAt(1)-48)].prof!=0&&tar!=-1&&p1.cb[Integer.valueOf(data[0].charAt(1)-48)].HP!=0) {
						attck(Integer.valueOf(data[0].charAt(1)-48),tar,left);///////////
					}
					ret = data[0].substring(0, 2);
					ret = ret+"1" + String.valueOf(tar)+"\t";
					int tag = 1;
					while(data[tag].equals("HP")==false) {
						ret =  ret + data[tag] + "\t";
						tag++;
					}
					ret = ret + "HP" + "\t";
					for(int i = 0;i < 6;i++)
						ret = ret+p1.cb[i].HP+ "\t";
					for(int i = 0;i < 6;i++)
						ret = ret+p2.cb[i].HP+ "\t";
				}
				else if(data[0].charAt(2)=='1') {
					if(p1.cb[Integer.valueOf(data[0].charAt(1))-48].prof!=0&&p1.cb[Integer.valueOf(data[0].charAt(1)-48)].HP!=0)
						atk_ani(Integer.valueOf(data[0].charAt(1))-48,Integer.valueOf(data[0].charAt(3))-48,left,false);
					for(int i = 1;i < data.length;i++)
						ret = ret + data[i] + "\t";
					if(bend(aya)==true)
						return "end";
				}
			}
			else if(left==false) {
				if(data[0].charAt(2)=='0') {
					System.out.println("atk_error");
				}
				else if(data[0].charAt(2)=='1') {
					if(p1.cb[Integer.valueOf(data[0].charAt(1))-48].prof!=0&&p1.cb[Integer.valueOf(data[0].charAt(1)-48)].HP!=0)
						atk_ani(Integer.valueOf(data[0].charAt(1))-48,Integer.valueOf(data[0].charAt(3))-48,left,true);
					for(int i = 1;i < data.length;i++)
						ret = ret + data[i] + "\t";
					if(bend(aya)==true)
						return "end";
				}
			}
		}
		else if(data[0].charAt(0)=='r') {
			if(left==true) {
				if(data[0].charAt(2)=='0') {
					System.out.println("atk_error");
				}
				else if(data[0].charAt(2)=='1') {
					if(p2.cb[Integer.valueOf(data[0].charAt(1))-48].prof!=0&&p2.cb[Integer.valueOf(data[0].charAt(1)-48)].HP!=0)
						atk_ani(Integer.valueOf(data[0].charAt(1))-48,Integer.valueOf(data[0].charAt(3))-48,left,true);
					for(int i = 1;i < data.length;i++)
						ret = ret + data[i] + "\t";
					if(bend(aya)==true)
						return "end";
				}
			}
			else if(left==false) {
				if(data[0].charAt(2)=='0') {
					refresh_all();
					int tar = p1.get_target();
					if(p2.cb[Integer.valueOf(data[0].charAt(1)-48)].prof!=0&&tar!=-1&&p2.cb[Integer.valueOf(data[0].charAt(1)-48)].HP!=0) {
						attck(Integer.valueOf(data[0].charAt(1)-48),tar,left);///////////
					}
					ret = data[0].substring(0, 2);
					ret = ret+"1" + String.valueOf(tar)+"\t";
					int tag = 1;
					while(data[tag].equals("HP")==false) {
						ret =  ret + data[tag] + "\t";
						tag++;
					}
					ret = ret + "HP" + "\t";
					for(int i = 0;i < 6;i++)
						ret = ret+p1.cb[i].HP+ "\t";
					for(int i = 0;i < 6;i++)
						ret = ret+p2.cb[i].HP+ "\t";
				}
				else if(data[0].charAt(2)=='1') {
					if(p2.cb[Integer.valueOf(data[0].charAt(1))-48].prof!=0&&p2.cb[Integer.valueOf(data[0].charAt(1)-48)].HP!=0)
						atk_ani(Integer.valueOf(data[0].charAt(1))-48,Integer.valueOf(data[0].charAt(3))-48,left,false);
					for(int i = 1;i < data.length;i++)
						ret = ret + data[i] + "\t";
					if(bend(aya)==true)
						return "end";
				}
			}
		}
		return ret;
	}
	public void attck(int ac,int tar,boolean left) {
		if(left==true) {
			double cc = 0;
			int dmg = 0;
			int cdmg = 0;
			int atkcount = 1;
			for(int i =0;i < p1.cb[ac].ATKt;i++) {
				cc = Math.random();
				if(p1.cb[ac].CP/100 > cc)
					dmg+=p1.cb[ac].ATK*(1+(p1.cb[ac].CA/100));
				else
					dmg+=p1.cb[ac].ATK;
			}
			if(dmg-(p2.cb[tar].DEF-p1.cb[ac].MD) > 0)
				p2.cb[tar].HP-=(dmg-(p2.cb[tar].DEF-p1.cb[ac].MD))*p1.cb[ac].ATKt;
			cdmg += p2.cb[tar].CATK*p1.cb[ac].ATKt;
			if(p1.cb[ac].row_atk==true) {
				if(tar > 2&&tar < 6) {
					for(int i = 3;i < 6;i++) {
						if(tar!=i&&p2.cb[i].prof!=0) {
							if((dmg-(p2.cb[i].DEF-p1.cb[ac].MD)) > 0)
								p2.cb[i].HP-=(dmg-(p2.cb[i].DEF-p1.cb[ac].MD))*p1.cb[ac].ATKt;
							cdmg += p2.cb[i].CATK*p1.cb[ac].ATKt;
							atkcount++;
						}
					}
				}
				else {
					for(int i = 0;i < 3;i++) {
						if(tar!=i&&p2.cb[i].prof!=0) {
							if(dmg-(p2.cb[i].DEF-p1.cb[ac].MD) > 0)
								p2.cb[i].HP-=(dmg-(p2.cb[i].DEF-p1.cb[ac].MD))*p1.cb[ac].ATKt;
							cdmg += p2.cb[i].CATK*p1.cb[ac].ATKt;
							atkcount++;
						}
					}
				}
			}
			if(p1.cb[ac].col_atk==true) {
				if(tar > 2) {
					if(p2.cb[tar-3].prof!=0) {
						if(dmg-(p2.cb[tar-3].DEF-p1.cb[ac].MD) > 0)
							p2.cb[tar-3].HP-=(dmg-(p2.cb[tar-3].DEF-p1.cb[ac].MD))*p1.cb[ac].ATKt;
						cdmg += p2.cb[tar-3].CATK*p1.cb[ac].ATKt;
						atkcount++;
					}
				}
			}
			cdmg*=p1.cb[ac].far_atk;
			if(cdmg-p1.cb[ac].DEF*atkcount*p1.cb[ac].ATKt > 0)
				p1.cb[ac].HP-=(cdmg-p1.cb[ac].DEF*atkcount*p1.cb[ac].ATKt);
		}
		else {
			double cc = 0;
			int dmg = 0;
			int cdmg = 0;
			int atkcount = 1;
			for(int i =0;i < p2.cb[ac].ATKt;i++) {
				cc = Math.random();
				if(p2.cb[ac].CP/100 > cc)
					dmg+=p2.cb[ac].ATK*(1+(p2.cb[ac].CA/100));
				else
					dmg+=p2.cb[ac].ATK;
			}
			if(dmg-(p1.cb[tar].DEF-p2.cb[ac].MD) > 0)
				p1.cb[tar].HP-=(dmg-(p1.cb[tar].DEF-p2.cb[ac].MD))*p2.cb[ac].ATKt;
			cdmg += p1.cb[tar].CATK*p2.cb[ac].ATKt;
			if(p2.cb[ac].row_atk==true) {
				if(tar > 2&&tar < 6) {
					for(int i = 3;i < 6;i++) {
						if(tar!=i&&p1.cb[i].prof!=0) {
							if((dmg-(p1.cb[i].DEF-p2.cb[ac].MD)) > 0)
								p1.cb[i].HP-=(dmg-(p1.cb[i].DEF-p2.cb[ac].MD))*p2.cb[ac].ATKt;
							cdmg += p1.cb[i].CATK*p2.cb[ac].ATKt;
							atkcount++;
						}
					}
				}
				else {
					for(int i = 0;i < 3;i++) {
						if(tar!=i&&p1.cb[i].prof!=0) {
							if(dmg-(p1.cb[i].DEF-p2.cb[ac].MD) > 0)
								p1.cb[i].HP-=(dmg-(p1.cb[i].DEF-p2.cb[ac].MD))*p2.cb[ac].ATKt;
							cdmg += p1.cb[i].CATK*p2.cb[ac].ATKt;
							atkcount++;
						}
					}
				}
			}
			if(p2.cb[ac].col_atk==true) {
				if(tar > 2) {
					if(p1.cb[tar-3].prof!=0) {
						if(dmg-(p1.cb[tar-3].DEF-p2.cb[ac].MD) > 0)
							p1.cb[tar-3].HP-=(dmg-(p1.cb[tar-3].DEF-p2.cb[ac].MD))*p2.cb[ac].ATKt;
						cdmg += p1.cb[tar-3].CATK*p2.cb[ac].ATKt;
						atkcount++;
					}
				}
			}
			cdmg*=p2.cb[ac].far_atk;
			if(cdmg-p2.cb[ac].DEF*atkcount*p2.cb[ac].ATKt > 0)
				p2.cb[ac].HP-=(cdmg-p2.cb[ac].DEF*atkcount*p2.cb[ac].ATKt);
		}
		for(int i = 0;i < 6;i++) {
			if(p1.cb[i].HP < 0) {
				p1.cb[i].HP = 0;
				p1.cb[i].prof = 0;
			}
		}
		for(int i = 0;i < 6;i++) {
			if(p2.cb[i].HP < 0) {
				p2.cb[i].HP = 0;
				p2.cb[i].prof = 0;
			}
		}
	}
	public void atk_ani(int ac,int tar,boolean left,boolean get_atk) {
		int q= ac,b = tar;
		if(q > 2)
			q-=3;
		if(b > 2)
			b-=3;
		if(left==true) {
			if(get_atk==true) {
				Timeline atk = new Timeline();
				double sx = p2.cbtn[ac].getLayoutX(),sy = p2.cbtn[ac].getLayoutY();
				double rx = sx-500,ry = sy - (b-q)*120;
				double ct = (double)300/p2.cb[ac].ATKt;
				atk.setCycleCount(p2.cb[ac].ATKt);
				atk.getKeyFrames().addAll(new KeyFrame(Duration.ZERO, new KeyValue(p2.cbtn[ac].layoutXProperty(), sx)),
										  new KeyFrame(Duration.ZERO, new KeyValue(p2.cbtn[ac].layoutYProperty(), sy)),
										  new KeyFrame(new Duration(ct/2), new KeyValue(p2.cbtn[ac].layoutXProperty(), rx)),
										  new KeyFrame(new Duration(ct/2), new KeyValue(p2.cbtn[ac].layoutYProperty(), ry)),
										  new KeyFrame(new Duration(ct/2-20),(a)-> {vibrate_check(true,tar,p2.cb[ac].row_atk,p2.cb[ac].col_atk);}),
										  new KeyFrame(new Duration(ct/2),(a)-> {refresh_all();}),
										  new KeyFrame(new Duration(ct), new KeyValue(p2.cbtn[ac].layoutXProperty(), sx)),
										  new KeyFrame(new Duration(ct), new KeyValue(p2.cbtn[ac].layoutYProperty(), sy)));
				atk.play();
			}
			if(get_atk==false) {
				Timeline atk = new Timeline();
				double sx = p1.cbtn[ac].getLayoutX(),sy = p1.cbtn[ac].getLayoutY();
				double rx =sx+500,ry = sy - (b-q)*120;
				double ct = (double)300/p1.cb[ac].ATKt;
				atk.setCycleCount(p1.cb[ac].ATKt);
				atk.getKeyFrames().addAll(new KeyFrame(Duration.ZERO, new KeyValue(p1.cbtn[ac].layoutXProperty(), sx)),
										  new KeyFrame(Duration.ZERO, new KeyValue(p1.cbtn[ac].layoutYProperty(), sy)),
										  new KeyFrame(new Duration(ct/2), new KeyValue(p1.cbtn[ac].layoutXProperty(), rx)),
										  new KeyFrame(new Duration(ct/2), new KeyValue(p1.cbtn[ac].layoutYProperty(), ry)),
										  new KeyFrame(new Duration(ct/2-20),(a)-> {vibrate_check(false,tar,p1.cb[ac].row_atk,p1.cb[ac].col_atk);}),
										  new KeyFrame(new Duration(ct/2),(a)-> {refresh_all();}),
										  new KeyFrame(new Duration(ct), new KeyValue(p1.cbtn[ac].layoutXProperty(), sx)),
										  new KeyFrame(new Duration(ct), new KeyValue(p1.cbtn[ac].layoutYProperty(), sy)));
				atk.play();
			}
		}
		if(left==false) {
			if(get_atk==true) {
				Timeline atk = new Timeline();
				double sx = p1.cbtn[ac].getLayoutX(),sy = p1.cbtn[ac].getLayoutY();
				double rx = sx+500,ry = sy - (b-q)*120;
				double ct = (double)300/p1.cb[ac].ATKt;
				atk.setCycleCount(p1.cb[ac].ATKt);
				atk.getKeyFrames().addAll(new KeyFrame(Duration.ZERO, new KeyValue(p1.cbtn[ac].layoutXProperty(), sx)),
										  new KeyFrame(Duration.ZERO, new KeyValue(p1.cbtn[ac].layoutYProperty(), sy)),
										  new KeyFrame(new Duration(ct/2), new KeyValue(p1.cbtn[ac].layoutXProperty(), rx)),
										  new KeyFrame(new Duration(ct/2), new KeyValue(p1.cbtn[ac].layoutYProperty(), ry)),
										  new KeyFrame(new Duration(ct/2-20),(a)-> {vibrate_check(false,tar,p1.cb[ac].row_atk,p1.cb[ac].col_atk);}),
										  new KeyFrame(new Duration(ct/2),(a)-> {refresh_all();}),
										  new KeyFrame(new Duration(ct), new KeyValue(p1.cbtn[ac].layoutXProperty(), sx)),
										  new KeyFrame(new Duration(ct), new KeyValue(p1.cbtn[ac].layoutYProperty(), sy)));
				atk.play();
			}
			if(get_atk==false) {
				Timeline atk = new Timeline();
				double sx = p2.cbtn[ac].getLayoutX(),sy = p2.cbtn[ac].getLayoutY();
				double rx = sx-500,ry = sy - (b-q)*120;
				double ct = (double)300/p2.cb[ac].ATKt;
				atk.setCycleCount(p2.cb[ac].ATKt);
				atk.getKeyFrames().addAll(new KeyFrame(Duration.ZERO, new KeyValue(p2.cbtn[ac].layoutXProperty(), sx)),
										  new KeyFrame(Duration.ZERO, new KeyValue(p2.cbtn[ac].layoutYProperty(), sy)),
										  new KeyFrame(new Duration(ct/2), new KeyValue(p2.cbtn[ac].layoutXProperty(), rx)),
										  new KeyFrame(new Duration(ct/2), new KeyValue(p2.cbtn[ac].layoutYProperty(), ry)),
										  new KeyFrame(new Duration(ct/2-20),(a)-> {vibrate_check(true,tar,p2.cb[ac].row_atk,p2.cb[ac].col_atk);}),
										  new KeyFrame(new Duration(ct/2),(a)-> {refresh_all();}),
										  new KeyFrame(new Duration(ct), new KeyValue(p2.cbtn[ac].layoutXProperty(), sx)),
										  new KeyFrame(new Duration(ct), new KeyValue(p2.cbtn[ac].layoutYProperty(), sy)));
				atk.play();
			}
		}
	}
	public void refresh_all() {
		for(int i = 0;i < 6;i++) {
			p1.cb[i].refresh_hp();
			if(p1.cb[i].HP==0)
				p1.cbtn[i].setVisible(false);
		}
		for(int i = 0;i < 6;i++) {
			p2.cb[i].refresh_hp();
			if(p2.cb[i].HP==0)
				p2.cbtn[i].setVisible(false);
		}
	}
	public void vibrate_check(boolean left,int tar,boolean row,boolean col) {
		if(left==true) {
			vib(p1.cbtn[tar]);
			if(row==true) {
				if(tar < 3) {
					for(int i = 0;i < 3;i++)
						vib(p1.cbtn[i]);
				}
				else {
					for(int i = 3;i < 6;i++)
						vib(p1.cbtn[i]);
				}
			}
			if(col==true&&tar > 2)
				vib(p1.cbtn[tar-3]);
		}
		else {
			vib(p2.cbtn[tar]);
			if(row==true) {
				if(tar < 3) {
					for(int i = 0;i < 3;i++)
						vib(p2.cbtn[i]);
				}
				else {
					for(int i = 3;i < 6;i++)
						vib(p2.cbtn[i]);
				}
			}
			if(col==true&&tar > 2)
				vib(p2.cbtn[tar-3]);
		}
	}
	public void vib(Button t) {
		Timeline v = new Timeline();
		v.setCycleCount(10);
		double sx = t.getLayoutX();
		v.getKeyFrames().addAll(new KeyFrame(Duration.ZERO, new KeyValue(t.layoutXProperty(), sx+15)),
								  new KeyFrame(new Duration(10), new KeyValue(t.layoutXProperty(), sx)),
								  new KeyFrame(new Duration(20), new KeyValue(t.layoutXProperty(), sx-15)),
								  new KeyFrame(new Duration(30), new KeyValue(t.layoutXProperty(), sx)));
		v.play();
	}
}
