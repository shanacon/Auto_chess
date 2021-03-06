# Auto_chess
# JAVA socket 自走棋遊戲
## 簡介
這是一款用JAVAFX建GUI，並用socket實現自走棋連線的遊戲  
## 各檔案介紹
### Server.java 
內含Server的GUI以及Server應管理的遊戲流程  
用Socket陣列來建立四個玩家(Client)的連線  
**class ThreadClient**負責進入遊戲後的整個流程，四個Client各有一個thread控制  
**class Supdata**負責遊戲開始後的準備階段接收Client傳送的玩家資料，並再傳給其他Client。還有之後的戰鬥資料傳送  
### Client.java  
**class Ctask**控制遊戲流程(準備階段、戰鬥階段)  
**class Cupdata**負責傳送玩家資料給Server  
**class Cupdata2**負責接收其他玩家的資料
  
### A_battle.java
建立戰鬥畫面的GUI  
**f_send_battle**會在戰鬥前傳送最後一次玩家資料給server  
**boolean bend** 戰鬥中確認自己是否已經被全滅(-->結束戰鬥)  
其他function主要用於控制戰鬥流程(角色攻擊、傷害計算等等)  
### Another_player.java
準備階段可以觀察其他玩家的佈陣等資訊  
該資料便是在此份檔案中  
上述**class Cupdata2**接收資料後會更新這個class的內容  
### Battle_pane.java
建立戰鬥時GUI的細節(角色資訊、角色位置等等)  
### Battle_space.java
建立準備階段的GUI以及按鈕控制等等  
### C_data_field.java
建立準備階段時滑鼠指向角色時顯示的角色資料  
### C_in_battle.java
角色在戰鬥時需要用到的資料  
### C_in_prepare.java
儲存角色在準備階段時的資料  
### Player_data.java 
紀錄玩家暱稱、剩餘生命、等級等等資訊  
### Probability.java
商店在不同玩家等級刷出技能的機率表  
### Room.java
等待其他玩家連線完成時使用的GUI  
### Server_database.java
記錄四位玩家的名字(其實好像沒用到)  
### Skill_L.java
技能名字列表以及刷新技能那道該技能的機率  
### Skill_O.java
實際的技能物件，另外還負責技能效果的實裝(void set_s_e)
  
## 遊戲玩法
遊戲剛開始輸入名字後按下enter等待其他玩家  
四名玩家連線後開始，進入以下準備階段畫面  
![image](https://user-images.githubusercontent.com/79785416/110120332-e1958a00-7df7-11eb-91b5-d3af344cfb2f.png)  
1.擁有的裝備，按下左上角的紅色回收可以刷新技能(要花錢)，三個同等級裝備可以合成一個高一等的裝備  
2.升級球，藍色球裝備給角色可以把LV1的角色升級成LV2，紅色是從LV2到LV3，合成時3個綠色球合成1個藍色球，3個藍色合成1個紅色  
3.商店區，可以刷新商店的裝備(要花錢)，玩家等級越高刷出的裝備越好  
4.可以查看其他玩家的佈陣，等級和HP等等資料  
5.準備剩餘時間和玩家生命值，剩餘時間倒數計時可以從server控制  
6.佈陣區，模式為佈陣時可以把角色放到該區域(點擊角色再點擊該區域，不能用拖曳的)，放的角色數量不能超過玩家等級  
7.模式區，根據當前要進行的操作選取對應模式，例如要合成裝備或升級球時就要選合成模式，要把升級球或裝備給角色時就是裝備模式，調整陣行就是佈陣模式，出售角色或裝備時就用出售模式(連點兩下出售)  
8.角色區，佈陣時使用  
9.合成和升級，在合成模式下選好要合成的東西再按該鍵即可合成，升級則是提升玩家等級。  
  
時間到的時候便會進入戰鬥，隨機配對對手，輸家會根據對手場上剩餘角色扣除玩家生命值，低於0仍可以繼續玩，玩到只剩一名玩家生命值高於0為止。
## 戰鬥階段前的資料傳送
在Client的倒數計時數到0時，Client端的區域變數now_mod會更改為3(準備階段時該值是2)並停止傳送佈陣資料  
now_mod在準備階段時就會順便包在佈陣資料中傳給Server  
Server在確認所有Client的now_mod都更改為3後便會停止接收佈陣資料並進行battle_match()來做戰鬥前的配對決定誰會要和誰打  
停止傳送佈陣資料的Client會傳送六筆角色資料給Server  
做完battle_match()的Server會根據自動配對的結果把該資料傳給他的對手，Server確定都傳完且Client都接收完之後戰鬥就會開始  
## 戰鬥階段的資料傳送
Client端會先各自傳送一筆字串為”HP” + 場上12個角色的血量(若沒有放角色血量為0)，中間以”\t”隔開  
Server收到後(此時Server有4個thread做戰鬥的輸入輸出處理)會確認下一個先攻擊的角色是哪邊的角色並原封不動的把字串傳給他  
收到訊息的Client會在收到的字串前加上一組攻擊順序回傳給Server，Server再按照下一位攻擊的角色是誰就傳給他之後  
該Client計算完傷害結果，不更新GUI，先回傳給Server，Server再把傷害結果傳給兩個Client，此時再同步做更新GUI的部分  
攻擊結束後Client會確認場上剩餘角色，若有一方全滅則傳"end"給server，server再傳給另一位Client通知戰鬥結束，四位玩家都收到"end"後則回到準備畫面
### 戰鬥的時候傳送字串的意義
第一個字元為l或r表示下一次發動攻擊的角色是左邊還是右邊的，第二個字元表示發動攻擊的角色的位置  
第三個字元是0或1，0表示該資料是傳給攻擊方做傷害計算的  
做完傷害計算後該Client就會把0改成1並在後面加上被攻擊者的位置回傳給Server，1就是表示要更新GUI的訊號。  
## 目前已知問題
1.盾兵防禦太高，若是兩邊都只放盾兵戰鬥無法結束(因為沒有限制戰鬥時間)  
