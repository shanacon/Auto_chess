package Auto_chess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class C_in_battle {
	public int prof= 0;//1 for tank,2 for archer,3 for sword
	public int HP;
	public int max_HP;
	public int ATK;
	public int ATKt;
	public int DEF;
	public int LV;
	public double far_atk;//1 for no,0.5 for half,0 for all
	public boolean col_atk;
	public boolean row_atk;
	public int CATK;
	public int Cmul;
	public double CP;
	public double CA;
	public int MD;
	public int position;
	public C_data_field df = new C_data_field();
	public Skill_O[] c_skill = new Skill_O[4];
	public C_in_battle(C_in_prepare in) {
		for(int i = 0;i < 4;i++) {
			if(c_skill[i]==null)
				c_skill[i] = new Skill_O(0,i,'a');
		}
		LV = in.LV;
		prof = in.prof;
		HP = in.HP;
		max_HP = in.HP;
		ATK = in.ATK;
		ATKt = in.ATKt;
		DEF = in.DEF;
		far_atk = in.far_atk;
		CATK = in.CATK;
		Cmul = in.Cmul;
		CP = in.CP;
		CA = in.CA;
		MD = in.MD;
		row_atk = in.row_atk;
		col_atk = in.col_atk;
		position = in.position;
		for(int i = 0;i < 4;i++)
			c_skill[i] = in.c_skill[i];
		maxhp.setHeight(5);
		maxhp.setWidth(118);
		maxhp.setFill(Color.RED);
		nowhp.setHeight(5);
		nowhp.setWidth(118);
		nowhp.setFill(Color.GREEN);
		df.set_text(this);
	}
	public void refresh_hp() {
		df.set_text(this);
		if(max_HP!=0) {
//			System.out.println("HP%¡G" + (double)(HP/max_HP) + " " + HP +" "+ max_HP);
			nowhp.setWidth(maxhp.getWidth()*((double)HP/max_HP));
		}
	}
	public ImageView cinb_t = new ImageView(new Image(getClass().getResourceAsStream("battle_p/c_onp_tank_n.png")));
	public ImageView cinb_a = new ImageView(new Image(getClass().getResourceAsStream("battle_p/c_onp_archer_n.png")));
	public ImageView cinb_s = new ImageView(new Image(getClass().getResourceAsStream("battle_p/c_onp_sword_n.png")));
	public Rectangle maxhp = new Rectangle();
	public Rectangle nowhp = new Rectangle();
}
