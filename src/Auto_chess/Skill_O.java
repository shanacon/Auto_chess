package Auto_chess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class Skill_O {
	public int level;//1 for C,2 for B,3 for A,4 for S,0 for nothing
	public String skill = new String();
	public ImageView background = new ImageView(new Image(getClass().getResourceAsStream("battle_p/skill_e.png")));
	public Text text;
	public AnchorPane pane = new AnchorPane();
	public Skill_L s_data = new Skill_L();
	public Skill_O(int level,int y,int useless) {
		this.level = level;
		skill = "--------------";
		text = new Text(skill);;
		text.setStyle("-fx-font-size: 36px;");
		background.setLayoutX(0);
		background.setLayoutY(0);
		text.setLayoutX(3);
		text.setLayoutY(40);
		pane.setLayoutX(354+y*155);
		pane.setLayoutY(128);
		pane.getChildren().addAll(background,text);
		pane.setVisible(false);
	}
	public Skill_O(int level,int y,char useless) {
		this.level = level;
		skill = s_data.getskill(level);
		text = new Text(skill);;
	}
	public void buy_S(Skill_O sin,int y) {
		this.level = sin.level;
		skill = sin.skill;
		text.setText(sin.text.getText());;
	}
	public Skill_O(int level,int y) {
		this.level = level;
		skill = s_data.getskill(level);
		text = new Text(skill);
		text.setStyle("-fx-font-size: 36px;");
		background.setLayoutX(0);
		background.setLayoutY(0);
		text.setLayoutX(3);
		text.setLayoutY(40);
		pane.getChildren().addAll(background,text);
		pane.setLayoutX(1050);
		pane.setLayoutY(80+y*149);
		pane.setVisible(false);
	}
	public void refresh_skill(int level) {
		skill = s_data.getskill(level);
		this.level = level;
		text.setText(skill);
	}
	public void decode_tag(String in) {
//		System.out.println(in);
		if(in.charAt(0)=='n')
			skill = "--------------";
		else if(in.charAt(0)=='c')
			skill = s_data.sc[Integer.valueOf(in.charAt(1))-48].toString();
		else if(in.charAt(0)=='b')
			skill = s_data.sb[Integer.valueOf(in.charAt(1))-48].toString();
		else if(in.charAt(0)=='a')
			skill = s_data.sa[Integer.valueOf(in.charAt(1))-48].toString();
		else if(in.charAt(0)=='s')
			skill = s_data.ss[Integer.valueOf(in.charAt(1))-48].toString();
	}
	public String send_tag() {
		String ret = "";
		if(level==0) {
			ret = "n";
			return ret;
		}
		if(level==1) {
			ret = "c";
			for(int i = 0;i < 6;i++) {
				if(skill.equals(s_data.sc[i])==true) {
					ret = ret + i ;
				}
			}
		}
		if(level==2) {
			ret = "b";
			for(int i = 0;i < 8;i++) {
				if(skill.equals(s_data.sb[i])==true) {
					ret = ret + i ;
				}
			}
		}
		if(level==3) {
			ret = "a";
			for(int i = 0;i < 8;i++) {
				if(skill.equals(s_data.sa[i])==true) {
					ret = ret + i ;
				}
			}
		}
		if(level==4) {
			ret = "s";
			for(int i = 0;i < 8;i++) {
				if(skill.equals(s_data.ss[i])==true) {
					ret = ret + i ;
				}
			}
		}
		return ret;
	}
	public void set_s_e(C_in_prepare cin,String skill,boolean set) {
		if(skill.equals("--------------")==true)
			return;
		String ff = skill.substring(0, 4);
		int num = 0;
		if(skill.charAt(5)==' ')
			num = Integer.valueOf(skill.substring(6, 7));
		else
			num = Integer.valueOf(skill.substring(5, 7));
		if(set==true) {
			if(ff.equals("血量增加")==true)
				cin.HP+=num;
			if(ff.equals("攻擊增加")==true)
				cin.ATK+=num;
			if(ff.equals("防禦增加")==true)
				cin.DEF+=num;
			if(ff.equals("必殺增加")==true)
				cin.CP+=num;
			if(ff.equals("致命必殺")==true)
				cin.CA+=num;
			if(ff.equals("裝甲無視")==true)
				cin.MD+=num;
			if(ff.equals("遠隔攻擊")==true)
				cin.far_atk=0.5;
			if(ff.equals("反擊加倍")==true)
				cin.Cmul+=num;
			if(ff.equals("追加攻擊")==true)
				cin.ATKt+=num;
			if(ff.equals("次元攻擊")==true) {
				cin.far_atk=0;
				cin.MD+=30;
			}
			if(ff.equals("貫通攻擊")==true)
				cin.col_atk = true;
			if(ff.equals("扇形攻擊")==true)
				cin.row_atk = true;
		}
		else {
			if(ff.equals("血量增加")==true)
				cin.HP-=num;
			if(ff.equals("攻擊增加")==true)
				cin.ATK-=num;
			if(ff.equals("防禦增加")==true)
				cin.DEF-=num;
			if(ff.equals("必殺增加")==true)
				cin.CP-=num;
			if(ff.equals("致命必殺")==true)
				cin.CA-=num;
			if(ff.equals("裝甲無視")==true)
				cin.MD-=num;
			if(ff.equals("遠隔攻擊")==true)
				cin.far_atk=1;
			if(ff.equals("反擊加倍")==true)
				cin.Cmul-=num;
			if(ff.equals("追加攻擊")==true)
				cin.ATKt-=num;
			if(ff.equals("次元攻擊")==true) {
				cin.far_atk=1;
				cin.MD-=30;
			}
			if(ff.equals("貫通攻擊")==true)
				cin.col_atk = false;
			if(ff.equals("扇形攻擊")==true)
				cin.row_atk = false;
		}
	}
}
