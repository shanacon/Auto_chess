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
建立準備階段時滑鼠指向角色時顯示的腳色資料  
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
  
