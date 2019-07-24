Global BurntNote%

Global MaxItemAmount% = 10
Global ItemAmount%
Dim Inventory.Items(MaxItemAmount + 1)
Global InvSelect%, SelectedItem.Items

Global ClosestItem.Items

Global LastItemID%

Type ItemTemplates
	Field name$
	Field tempname$
	
	Field sound%
	
	Field found%
	
	Field obj%, objpath$, parentobjpath$
	Field invimg%,invimg2%,invimgpath$
	Field imgpath$, img%
	
	Field isAnim%
	
	Field scale#
	Field tex%, texpath$
End Type 

Function CreateItemTemplate.ItemTemplates(name$, tempname$, objpath$, invimgpath$, imgpath$, scale#, texturepath$ = "",invimgpath2$="",Anim%=0, texflags%=9)
	Local it.ItemTemplates = New ItemTemplates, n
	
	
	;if another item shares the same object, copy it
	For it2.itemtemplates = Each ItemTemplates
		If it2\objpath = objpath And it2\obj <> 0 Then it\obj = CopyEntity(it2\obj) : it\parentobjpath=it2\objpath : Exit
	Next
	
	If it\obj = 0 Then; it\obj = LoadMesh(objpath)
		If Anim<>0 Then
			it\obj = LoadAnimMesh_Strict(objpath)
			it\isAnim=True
		Else
			it\obj = LoadMesh_Strict(objpath)
			it\isAnim=False
		EndIf
		it\objpath = objpath
	EndIf
	it\objpath = objpath
	
	Local texture%
	
	If texturepath <> "" Then
		For it2.itemtemplates = Each ItemTemplates
			If it2\texpath = texturepath And it2\tex<>0 Then
				texture = it2\tex
				Exit
			EndIf
		Next
		If texture=0 Then texture=LoadTexture_Strict(texturepath,texflags%) : it\texpath = texturepath
		EntityTexture it\obj, texture
		it\tex = texture
	EndIf  
	
	it\scale = scale
	ScaleEntity it\obj, scale, scale, scale, True
	
	;if another item shares the same object, copy it
	For it2.itemtemplates = Each ItemTemplates
		If it2\invimgpath = invimgpath And it2\invimg <> 0 Then
			it\invimg = it2\invimg ;CopyImage()
			If it2\invimg2<>0 Then
				it\invimg2=it2\invimg2 ;CopyImage()
			EndIf
			Exit
		EndIf
	Next
	If it\invimg=0 Then
		it\invimg = LoadImage_Strict(invimgpath)
		it\invimgpath = invimgpath
		MaskImage(it\invimg, 255, 0, 255)
	EndIf
	
	If (invimgpath2 <> "") Then
		If it\invimg2=0 Then
			it\invimg2 = LoadImage_Strict(invimgpath2)
			MaskImage(it\invimg2,255,0,255)
		EndIf
	Else
		it\invimg2 = 0
	EndIf
	
	it\imgpath = imgpath
	
	it\tempname = tempname
	it\name = name
	
	it\sound = 1

	HideEntity it\obj
	
	Return it
	
End Function

Function InitItemTemplates()
	Local it.ItemTemplates,it2.ItemTemplates

	;---SCP:CB Items---
	
	it = CreateItemTemplate("Немного SCP-420-J", "scp420j", "GFX\items\scp_420_j.x", "GFX\items\INV_scp_420_j.png", "", 0.0005) ;Some SCP-420-J
	it\sound = 2
	
	CreateItemTemplate("Ключ-карта 1-го уровня", "key1", "GFX\items\key_card.x", "GFX\items\INV_key_card_lvl_1.png", "", 0.0004,"GFX\items\key_card_lvl_1.png") ;Level 1 Key Card
	CreateItemTemplate("Ключ-карта 2-го уровня", "key2", "GFX\items\key_card.x", "GFX\items\INV_key_card_lvl_2.png", "", 0.0004,"GFX\items\key_card_lvl_2.png") ;Level 2 Key Card
	CreateItemTemplate("Ключ-карта 3-го уровня", "key3", "GFX\items\key_card.x", "GFX\items\INV_key_card_lvl_3.png", "", 0.0004,"GFX\items\key_card_lvl_3.png") ;Level 3 Key Card
	CreateItemTemplate("Ключ-карта 4-го уровня", "key4", "GFX\items\key_card.x", "GFX\items\INV_key_card_lvl_4.png", "", 0.0004,"GFX\items\key_card_lvl_4.png") ;Level 4 Key Card
	CreateItemTemplate("Ключ-карта 5-го уровня", "key5", "GFX\items\key_card.x", "GFX\items\INV_key_card_lvl_5.png", "", 0.0004,"GFX\items\key_card_lvl_5.png") ;Level 5 Key Card
	CreateItemTemplate("Игральная карта", "misc", "GFX\items\key_card.x", "GFX\items\INV_playing_card.png", "", 0.0004,"GFX\items\playing_card.png") ;Playing Card
	CreateItemTemplate("Mastercard", "misc", "GFX\items\key_card.x", "GFX\items\INV_master_card.png", "", 0.0004,"GFX\items\master_card.png")
	CreateItemTemplate("Ключ-карта Омни", "key7", "GFX\items\key_card.x", "GFX\items\INV_key_card_lvl_omni.png", "", 0.0004,"GFX\items\key_card_lvl_omni.png") ;Key Card Omni
	
	it = CreateItemTemplate("SCP-860", "scp860", "GFX\items\scp_860.b3d", "GFX\items\INV_scp_860.png", "", 0.0026)
	it\sound = 3
	
	it = CreateItemTemplate("Документ об SCP-079", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_079.png", 0.003) : it\sound = 0 ;Document SCP-079
	it = CreateItemTemplate("Документ об SCP-895", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_895.png", 0.003) : it\sound = 0 ;Document SCP-895
	it = CreateItemTemplate("Документ об SCP-860", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_860.png", 0.003) : it\sound = 0 ;Document SCP-860
	it = CreateItemTemplate("Документ об SCP-860-1", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_860_1.png", 0.003) : it\sound = 0 ;Document SCP-860-1
	it = CreateItemTemplate("Материалы, добытые посредством SCP-093", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_093_rm.png", 0.003) : it\sound = 0 ;SCP-093 Recovered Materials
	it = CreateItemTemplate("Документ об SCP-106", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_106.png", 0.003) : it\sound = 0 ;Document SCP-106
	it = CreateItemTemplate("Записка д-ра Эллока", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_106(2).png", 0.0025) : it\sound = 0 ;Dr. Allok's Note
	it = CreateItemTemplate("Протокол возврата RP-106-N", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_RP.png", 0.0025) : it\sound = 0 ;Recall Protocol RP-106-N
	it = CreateItemTemplate("Документ об SCP-682", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_682.png", 0.003) : it\sound = 0 ;Document SCP-682
	it = CreateItemTemplate("Документ об SCP-173", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_173.png", 0.003) : it\sound = 0 ;Document SCP-173
	it = CreateItemTemplate("Документ об SCP-372", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_372.png", 0.003) : it\sound = 0 ;Document SCP-372
	it = CreateItemTemplate("Документ об SCP-049", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_049.png", 0.003) : it\sound = 0 ;Document SCP-049
	it = CreateItemTemplate("Документ об SCP-096", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_096.png", 0.003) : it\sound = 0 ;Document SCP-096
	it = CreateItemTemplate("Документ об SCP-008", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_008.png", 0.003) : it\sound = 0 ;Document SCP-008
	it = CreateItemTemplate("Документ об SCP-012", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_012.png", 0.003) : it\sound = 0 ;Document SCP-012
	it = CreateItemTemplate("Документ об SCP-500", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_500.png", 0.003) : it\sound = 0 ;Document SCP-500
	it = CreateItemTemplate("Документ об SCP-714", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_714.png", 0.003) : it\sound = 0 ;Document SCP-714
	it = CreateItemTemplate("Документ об SCP-513", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_513.png", 0.003) : it\sound = 0 ;Document SCP-513
	it = CreateItemTemplate("Документ об SCP-035", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_035.png", 0.003) : it\sound = 0 ;Document SCP-035
	it = CreateItemTemplate("Дополнения к документу об SCP-035", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_035_ad.png", 0.003) : it\sound = 0 ;SCP-035 Addendum
	it = CreateItemTemplate("Документ об SCP-939", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_939.png", 0.003) : it\sound = 0 ;Document SCP-939
	it = CreateItemTemplate("Документ об SCP-966", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_966.png", 0.003) : it\sound = 0 ;Document SCP-966
	it = CreateItemTemplate("Документ об SCP-970", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_970.png", 0.003) : it\sound = 0 ;Document SCP-970
	it = CreateItemTemplate("Документ об SCP-1048", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_1048.png", 0.003) : it\sound = 0 ;Document SCP-1048
	it = CreateItemTemplate("Документ об SCP-1123", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_1123.png", 0.003) : it\sound = 0 ;Document SCP-1123
	it = CreateItemTemplate("Документ об SCP-1162", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_1162.png", 0.003) : it\sound = 0 ;Document SCP-1162
	it = CreateItemTemplate("Документ об SCP-1499", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_1499.png", 0.003) : it\sound = 0 ;Document SCP-1499
	it = CreateItemTemplate("Отчёт об инциденте SCP-1048-A", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_1048_a.png", 0.003) : it\sound = 0 ;Incident Report SCP-1048-A
	
	it = CreateItemTemplate("Рисунок", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_1048.png", 0.003) : it\sound = 0 ;Drawing
	
	it = CreateItemTemplate("Листовка", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\leaflet.png", 0.003, "GFX\items\note.png") : it\sound = 0 ;Leaflet
	
	it = CreateItemTemplate("Записка д-ра Л.", "paper", "GFX\items\paper.x", "GFX\items\INV_note.png", "GFX\items\doc_L.png", 0.0025, "GFX\items\note.png") : it\sound = 0 ;Dr. L's Note
	it = CreateItemTemplate("Записка д-ра Л", "paper", "GFX\items\paper.x", "GFX\items\INV_note.png", "GFX\items\doc_L(2).png", 0.0025, "GFX\items\note.png") : it\sound = 0 ;Dr L's Note
	it = CreateItemTemplate("Записка с кровавым следом", "paper", "GFX\items\paper.x", "GFX\items\INV_note_bloody.png", "GFX\items\doc_L(3).png", 0.0025, "GFX\items\note_bloody.png") : it\sound = 0 ;Blood-stained Note
	it = CreateItemTemplate("Обгоревшая записка д-ра Л.", "paper", "GFX\items\paper.x", "GFX\items\INV_burnt_note.png", "GFX\items\doc_L(4).png", 0.0025, "GFX\items\burnt_note.png") : it\sound = 0 ;Dr. L's Burnt Note
	it = CreateItemTemplate("Обгоревшая записка д-ра Л", "paper", "GFX\items\paper.x", "GFX\items\INV_burnt_note.png", "GFX\items\doc_L(5).png", 0.0025, "GFX\items\burnt_note.png") : it\sound = 0 ;Dr L's Burnt Note
	it = CreateItemTemplate("Выжженная записка", "paper", "GFX\items\paper.x", "GFX\items\INV_burnt_note.png", "GFX\items\doc_L(6).png", 0.0025, "GFX\items\burnt_note.png") : it\sound = 0 ;Scorched Note
	
	it = CreateItemTemplate("Страница из дневника", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_Gonzales.png", 0.0025) : it\sound = 0 ;Journal Page
	
	it = CreateItemTemplate("Запись №1", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\f(4).png", 0.004, "GFX\items\f(4).png") : it\sound = 0 ;Log #1
	it = CreateItemTemplate("Запись №2", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\f(5).png", 0.004, "GFX\items\f(4).png") : it\sound = 0 ;Log #2
	it = CreateItemTemplate("Запись №3", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\f(6).png", 0.004, "GFX\items\f(4).png") : it\sound = 0 ;Log #3
	
	it = CreateItemTemplate("Странная записка", "paper", "GFX\items\paper.x", "GFX\items\INV_note.png", "GFX\items\doc_Strange.png", 0.0025, "GFX\items\note.png") : it\sound = 0 ;Strange Note
	
	it = CreateItemTemplate("Документ о ядерных устройствах", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_NDP.png", 0.003) : it\sound = 0 ;Nuclear Device Document
	it = CreateItemTemplate("Информационная листовка для персонала класса D", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_ORI.png", 0.003) : it\sound = 0 ;Class D Orientation Leaflet
	
	it = CreateItemTemplate("Записка от Даниэля", "paper", "GFX\items\note.x", "GFX\items\INV_note(2).png", "GFX\items\doc_dan.png", 0.0025) : it\sound = 0 ;Note from Daniel
	
	it = CreateItemTemplate("Обгоревший документ", "paper", "GFX\items\paper.x", "GFX\items\INV_burnt_note.png", "GFX\items\bn.it", 0.003, "GFX\items\burnt_note.png") ;Burnt Note
	it\img = BurntNote : it\sound = 0
	
	it = CreateItemTemplate("Загадочная записка", "paper", "GFX\items\paper.x", "GFX\items\INV_note.png", "GFX\items\sn.it", 0.003, "GFX\items\note.png") : it\sound = 0	;Mysterious Note
	
	it = CreateItemTemplate("Мобильные оперативные группы", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_MTF.png", 0.003) : it\sound = 0 ;Mobile Task Forces
	it = CreateItemTemplate("Уровни допуска", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_SC.png", 0.003) : it\sound = 0 ;Security Clearance Levels
	it = CreateItemTemplate("Классы объектов", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_OBJC.png", 0.003) : it\sound = 0 ;Object Classes
	it = CreateItemTemplate("Документ", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_RAND(3).png", 0.003) : it\sound = 0 ;Document
	it = CreateItemTemplate("Дополнение: Протокол исследований 5/14", "paper", "GFX\items\paper.x", "GFX\items\INV_note.png", "GFX\items\doc_RAND(2).png", 0.003, "GFX\items\note.png") : it\sound = 0 ;Addendum: 5/14 Test Log
	it = CreateItemTemplate("Уведомление", "paper", "GFX\items\paper.x", "GFX\items\INV_note.png", "GFX\items\doc_RAND.png", 0.003, "GFX\items\note.png") :it\sound = 0 ;Notification
	it = CreateItemTemplate("Отчёт об инциденте SCP-106-0204", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_IR_106.png", 0.003) : it\sound = 0 ;Incident Report SCP-106-0204
	
	it = CreateItemTemplate("Бронежилет", "vest", "GFX\items\vest.x", "GFX\items\INV_vest.png", "", 0.02,"GFX\items\vest.png") : it\sound = 4 ;Ballistic Vest
	it = CreateItemTemplate("Тяжёлый бронежилет", "finevest", "GFX\items\vest.x", "GFX\items\INV_vest.png", "", 0.022,"GFX\items\vest.png") ;Heavy Ballistic Vest
	it\sound = 4
	it = CreateItemTemplate("Громоздкий бронежилет", "veryfinevest", "GFX\items\vest.x", "GFX\items\INV_vest.png", "", 0.025,"GFX\items\vest.png") ;Bulky Ballistic Vest
	it\sound = 2
	
	it = CreateItemTemplate("Защитный костюм", "hazmatsuit", "GFX\items\hazmat_suit.b3d", "GFX\items\INV_hazmat_suit.png", "", 0.013) ;Hazmat Suit
	it\sound = 2
	it = CreateItemTemplate("Защитный костюм", "hazmatsuit2", "GFX\items\hazmat_suit.b3d", "GFX\items\INV_hazmat_suit.png", "", 0.013) ;Hazmat Suit
	it\sound = 2
	it = CreateItemTemplate("Тяжёлый защитный костюм", "hazmatsuit3", "GFX\items\hazmat_suit.b3d", "GFX\items\INV_hazmat_suit.png", "", 0.013) ;Heavy Hazmat Suit
	it\sound = 2
	
	it = CreateItemTemplate("cup", "cup", "GFX\items\cup.x", "GFX\items\INV_cup.png", "", 0.04) : it\sound = 2
	
	it = CreateItemTemplate("Пустой стакан", "emptycup", "GFX\items\cup.x", "GFX\items\INV_cup.png", "", 0.04) : it\sound = 2 ;Empty Cup
	
	it = CreateItemTemplate("SCP-500-01", "scp500pill", "GFX\items\pill.b3d", "GFX\items\INV_scp_500_pill.png", "", 0.0001) : it\sound = 2
	EntityColor it\obj,255,0,0
	
	it = CreateItemTemplate("Аптечка первой помощи", "firstaid", "GFX\items\first_aid.x", "GFX\items\INV_first_aid.png", "", 0.05) ;First Aid Kit
	it = CreateItemTemplate("Маленькая аптечка первой помощи", "finefirstaid", "GFX\items\first_aid.x", "GFX\items\INV_first_aid.png", "", 0.03) ;Small First Aid Kit
	it = CreateItemTemplate("Синяя аптечка первой помощи", "firstaid2", "GFX\items\first_aid.x", "GFX\items\INV_first_aid(2).png", "", 0.03, "GFX\items\first_aid_kit(2).png") ;Blue First Aid Kit
	it = CreateItemTemplate("Странная бутылка", "veryfinefirstaid", "GFX\items\eye_drops.b3d", "GFX\items\INV_strange_bottle.png", "", 0.002, "GFX\items\strange_bottle.png") ;Strange Bottle
	
	it = CreateItemTemplate("Противогаз", "gasmask", "GFX\items\gas_mask.b3d", "GFX\items\INV_gas_mask.png", "", 0.02) : it\sound = 2 ;Gas Mask
	it = CreateItemTemplate("Противогаз", "supergasmask", "GFX\items\gas_mask.b3d", "GFX\items\INV_gas_mask.png", "", 0.021) : it\sound = 2 ;Gas Mask
	it = CreateItemTemplate("Тяжёлый противогаз", "gasmask3", "GFX\items\gas_mask.b3d", "GFX\items\INV_gas_mask.png", "", 0.021) : it\sound = 2 ;Heavy Gas Mask
	
	it = CreateItemTemplate("Оригами", "misc", "GFX\items\origami.b3d", "GFX\items\INV_origami.png", "", 0.003) : it\sound = 0 ;Origami
	
	CreateItemTemplate("Электронные компоненты", "misc", "GFX\items\electronics.x", "GFX\items\INV_electronics.png", "", 0.0011) ;Electronical components
	
	it = CreateItemTemplate("Металлическая панель", "scp148", "GFX\items\metal_panel.x", "GFX\items\INV_metal_panel.png", "", RoomScale) : it\sound = 2 ;Metal Panel
	it = CreateItemTemplate("Слиток SCP-148", "scp148ingot", "GFX\items\scp_148.x", "GFX\items\INV_scp_148.png", "", RoomScale) : it\sound = 2;SCP-148 Ingot
	
	CreateItemTemplate("Навигатор S-NAV 300", "nav", "GFX\items\navigator.x", "GFX\items\INV_navigator.png", "GFX\items\navigator_HUD.png", 0.0008) ;S-NAV 300 Navigator
	CreateItemTemplate("Навигатор S-NAV", "nav", "GFX\items\navigator.x", "GFX\items\INV_navigator.png", "GFX\items\navigator_HUD.png", 0.0008) ;S-NAV Navigator
	CreateItemTemplate("Навигатор S-NAV Ultimate", "nav", "GFX\items\navigator.x", "GFX\items\INV_navigator.png", "GFX\items\navigator_HUD.png", 0.0008) ;S-NAV Navigator Ultimate
	CreateItemTemplate("Навигатор S-NAV 310", "nav", "GFX\items\navigator.x", "GFX\items\INV_navigator.png", "GFX\items\navigator_HUD.png", 0.0008) ;S-NAV 310 Navigator
	
	CreateItemTemplate("Рация", "radio", "GFX\items\radio.x", "GFX\items\INV_radio.png", "GFX\items\radio_HUD.png", 1.0);0.0010) ;Radio Transceiver
	CreateItemTemplate("Рация", "fineradio", "GFX\items\radio.x", "GFX\items\INV_radio.png", "GFX\items\radio_HUD.png", 1.0) ;Radio Transceiver
	CreateItemTemplate("Рация", "veryfineradio", "GFX\items\radio.x", "GFX\items\INV_radio.png", "GFX\items\radio_HUD.png", 1.0) ;Radio Transceiver
	CreateItemTemplate("Рация", "18vradio", "GFX\items\radio.x", "GFX\items\INV_radio.png", "GFX\items\radio_HUD.png", 1.02) ;Radio Transceiver
	
	it = CreateItemTemplate("Сигарета", "cigarette", "GFX\items\scp_420_j.x", "GFX\items\INV_scp_420_j.png", "", 0.0004) : it\sound = 2 ;Cigarette
	
	it = CreateItemTemplate("Самокрутка", "scp420s", "GFX\items\scp_420_j.x", "GFX\items\INV_scp_420_j.png", "", 0.0004) : it\sound = 2 ;Joint
	
	it = CreateItemTemplate("Пахучая самокрутка", "scp420s", "GFX\items\scp_420_j.x", "GFX\items\INV_scp_420_j.png", "", 0.0004) : it\sound = 2 ;Smelly Joint
	
	it = CreateItemTemplate("Оторванная рука", "hand", "GFX\items\severed_hand.b3d", "GFX\items\INV_severed_hand.png", "", 0.04) : it\sound = 2 ;Severed Hand
	it = CreateItemTemplate("Чёрная оторванная рука", "hand2", "GFX\items\severed_hand.b3d", "GFX\items\INV_severed_hand(2).png", "", 0.04, "GFX\items\shand2.png") : it\sound = 2 ;Black Severed Hand
	
	CreateItemTemplate("9V-батарейка", "bat", "GFX\items\battery.x", "GFX\items\INV_battery_9v.png", "", 0.008) ;9V Battery
	CreateItemTemplate("18V-батарейка", "18vbat", "GFX\items\battery.x", "GFX\items\INV_battery_18v.png", "", 0.01, "GFX\items\battery_18V.png") ;18V Battery
	CreateItemTemplate("Странная батарейка", "killbat", "GFX\items\battery.x", "GFX\items\INV_strange_battery.png", "", 0.01,"GFX\items\strange_battery.png") ;Strange Battery
	
	CreateItemTemplate("Глазные капли", "fineeyedrops", "GFX\items\eye_drops.b3d", "GFX\items\INV_eye_drops.png", "", 0.0012, "GFX\items\eye_drops.png") ;Eyedrops
	CreateItemTemplate("Глазные капли", "supereyedrops", "GFX\items\eye_drops.b3d", "GFX\items\INV_eye_drops.png", "", 0.0012, "GFX\items\eye_drops.png") ;Eyedrops
	CreateItemTemplate("Глазные капли Зрение+", "eyedrops","GFX\items\eye_drops.b3d", "GFX\items\INV_eye_drops.png", "", 0.0012, "GFX\items\eye_drops.png") ;ReVision Eyedrops
	CreateItemTemplate("Красные глазные капли Зрение+", "eyedrops2", "GFX\items\eye_drops.b3d", "GFX\items\INV_eye_drops_red.png", "", 0.0012,"GFX\items\eye_drops_red.png") ;RedVision Eyedrops
	
	it = CreateItemTemplate("SCP-714", "scp714", "GFX\items\scp_714.b3d", "GFX\items\INV_scp_714.png", "", 0.3)
	it\sound = 3
	
	it = CreateItemTemplate("SCP-1025", "scp1025", "GFX\items\scp_1025.b3d", "GFX\items\INV_scp_1025.png", "", 0.1)
	it\sound = 0
	
	it = CreateItemTemplate("SCP-513", "scp513", "GFX\items\scp_513.x", "GFX\items\INV_scp_513.png", "", 0.1)
	it\sound = 2

	it = CreateItemTemplate("Планшет", "clipboard", "GFX\items\clipboard.b3d", "GFX\items\INV_clipboard.png", "", 0.003, "", "GFX\items\INV_clipboard2.png", 1) ;Clipboard
	
	it = CreateItemTemplate("SCP-1123", "scp1123", "GFX\items\scp_1123.b3d", "GFX\items\INV_scp_1123.png", "", 0.015) : it\sound = 2
		
	it = CreateItemTemplate("Очки ночного виденья", "supernv", "GFX\items\night_vision_goggles.b3d", "GFX\items\INV_super_night_vision_goggles.png", "", 0.02) : it\sound = 2 ;Night Vision Goggles
	it = CreateItemTemplate("Очки ночного виденья", "nvgoggles", "GFX\items\night_vision_goggles.b3d", "GFX\items\INV_night_vision_goggles.png", "", 0.02) : it\sound = 2 ;Night Vision Goggles
	it = CreateItemTemplate("Очки ночного виденья", "finenvgoggles", "GFX\items\night_vision_goggles.b3d", "GFX\items\INV_very_fine_night_vision_goggles.png", "", 0.02) : it\sound = 2 ;Night Vision Goggles
	
	it = CreateItemTemplate("Шприц", "syringe", "GFX\items\syringe.b3d", "GFX\items\INV_syringe.png", "", 0.005) : it\sound = 2 ;Syringe
	it = CreateItemTemplate("Шприц", "finesyringe", "GFX\items\syringe.b3d", "GFX\items\INV_syringe.png", "", 0.005) : it\sound = 2 ;Syringe
	it = CreateItemTemplate("Шприц", "veryfinesyringe", "GFX\items\syringe.b3d", "GFX\items\INV_syringe.png", "", 0.005) : it\sound = 2 ;Syringe

	it = CreateItemTemplate("SCP-1499","scp1499","GFX\items\scp_1499.b3d","GFX\items\INV_scp_1499.png", "", 0.023) : it\sound = 2
	it = CreateItemTemplate("SCP-1499","super1499","GFX\items\scp_1499.b3d","GFX\items\INV_scp_1499.png", "", 0.023) : it\sound = 2
	CreateItemTemplate("Бейдж Эмили Росс", "badge", "GFX\items\badge.x", "GFX\items\INV_Emily_badge.png", "GFX\items\Emily_badge_HUD.png", 0.0001, "GFX\items\badge1_tex.png") ;Emily Ross' Badge
	it = CreateItemTemplate("Потерянный ключ", "key", "GFX\items\key.b3d", "GFX\items\INV_key.png", "", 0.001, "GFX\items\key.png","",0,1 + 2 + 8) : it\sound = 3 ;Lost Key
	it = CreateItemTemplate("Дисциплинарное слушание DH-S-4137-17092", "oldpaper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\dh.s", 0.003) : it\sound = 0 ;Disciplinary Hearing DH-S-4137-17092
	it = CreateItemTemplate("Монета", "coin", "GFX\items\key.b3d", "GFX\items\INV_coin.png", "", 0.0005, "GFX\items\coin.png","",0, 1 + 2 + 8) : it\sound = 3 ;Coin
	it = CreateItemTemplate("Билет в кино", "ticket", "GFX\items\key.b3d", "GFX\items\INV_ticket.png", "GFX\items\ticket.png", 0.002, "GFX\items\ticket.png","",0,1+2+8) : it\sound = 0 ;Movie Ticket
	CreateItemTemplate("Старый бейдж", "badge", "GFX\items\badge.x", "GFX\items\INV_d_9341_badge.png", "GFX\items\d_9341_badge_HUD.png", 0.0001, "GFX\items\d_9341_badge.png","",0,1+2+8) ;Old Badge
	
	it = CreateItemTemplate("Четвертак","25ct", "GFX\items\key.b3d", "GFX\items\INV_coin.png", "", 0.0005, "GFX\items\coin.png", "", 0, 1 + 2 + 8) : it\sound = 3 ;Quarter
	it = CreateItemTemplate("Кошелёк","wallet", "GFX\items\wallet.b3d", "GFX\items\INV_wallet.png", "", 0.0005, "", "", 1) : it\sound = 2 ;Wallet
	
	it = CreateItemTemplate("SCP-427","scp427","GFX\items\scp_427.b3d","GFX\items\INV_scp_427.png", "", 0.001) : it\sound = 3
	it = CreateItemTemplate("Улучшенная пилюля", "scp500pilldeath", "GFX\items\pill.b3d", "GFX\items\INV_scp_500_pill.png", "", 0.0001) : it\sound = 2 ;Upgraded pill
	EntityColor it\obj, 255, 0, 0
	it = CreateItemTemplate("Пилюля", "pill", "GFX\items\pill.b3d", "GFX\items\INV_pill.png", "", 0.0001) : it\sound = 2 ;Pill
	EntityColor it\obj, 255, 255, 255
	
	it = CreateItemTemplate("Заметка", "paper", "GFX\items\note.x", "GFX\items\INV_note(2).png", "GFX\items\note_682.png", 0.0025) : it\sound = 0 ;Sticky Note
	it = CreateItemTemplate("Проект модульной зоны", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_MSP.png", 0.003) : it\sound = 0 ;The Modular Site Project
	
	it = CreateItemTemplate("Схема исследовательского сектора-02", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_map.png", 0.003) : it\sound = 0 ;Research Sector-02 Scheme
	
	it = CreateItemTemplate("Документ об SCP-427", "paper", "GFX\items\paper.x", "GFX\items\INV_paper_bloody.png", "GFX\items\doc_427.png", 0.003, "GFX\items\paper_bloody.png") : it\sound = 0 ;Document SCP-427
	
	;END
	
	;----Ultimate Edition Items----
	
	it = CreateItemTemplate("Шприц", "syringeinf", "GFX\items\syringe.b3d", "GFX\items\INV_syringe_infected.png", "", 0.005, "GFX\items\syringe_infected.png") : it\sound = 2 ;Syringe

    CreateItemTemplate("Ключ-карта 0-го уровня", "key0",  "GFX\items\key_card.x", "GFX\items\INV_key_card_lvl_0.png", "", 0.0004,"GFX\items\key_card_lvl_0.png") ;Level 0 Key Card
    CreateItemTemplate("Ключ-карта 6-го уровня", "key6",  "GFX\items\key_card.x", "GFX\items\INV_key_card_lvl_6.png", "", 0.0004,"GFX\items\key_card_lvl_6.png") ;Level 6 Key Card
    
    it = CreateItemTemplate("Порезанная бумага", "paperstrips", "GFX\items\paper_strips.x", "GFX\items\INV_paper_strips.png", "", 0.003) : it\sound = 0 ;Paper Strips
    it = CreateItemTemplate("Журнал полевого агента #235-001-CO5", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_O5.png", 0.003) : it\sound = 0 ;Field Agent Log #235-001-CO5
    it = CreateItemTemplate("Записки групп интересов", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_O5(2).png", 0.003) : it\sound = 0 ;Groups Of Interest Log
    it = CreateItemTemplate("Неизвестный документ", "paper", "GFX\items\paper.x", "GFX\items\INV_paper_bloody.png", "GFX\items\doc_unknown.png", 0.003, "GFX\items\paper_bloody.png") : it\sound = 0 ;Unknown Document
    it = CreateItemTemplate("Неизвестная записка", "paper", "GFX\items\paper.x", "GFX\items\INV_note_bloody.png", "GFX\items\unknown_note.png", 0.003, "GFX\items\note.png") : it\sound = 0 ;Unknown Note

    CreateItemTemplate("SCP-215", "scp215", "GFX\items\scp_215.b3d", "GFX\items\INV_scp_215.png", "", 0.022, "", "", 1)
    CreateItemTemplate("Футляр для очков", "glassescase", "GFX\items\glasses_case.b3d", "GFX\items\INV_glasses_case.png","",0.022, "", "", 1) ;Glasses Case

    it = CreateItemTemplate("SCP-1033-RU", "scp1033ru", "GFX\items\scp_1033_ru.b3d", "GFX\items\INV_scp_1033_ru.png", "", 0.7) : it\sound = 3
	it = CreateItemTemplate("SCP-1033-RU", "super1033ru", "GFX\items\scp_1033_ru.b3d", "GFX\items\INV_scp_1033_ru.png", "", 0.7) : it\sound = 3
    
    it = CreateItemTemplate("SCP-207", "scp207", "GFX\items\scp_207.b3d","GFX\items\INV_scp_207.png", "", 0.14) : it\sound = 5

    it = CreateItemTemplate("Документ об SCP-178", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_178.png", 0.003) : it\sound = 0 ;Document
    it = CreateItemTemplate("Документ об SCP-215", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_215.png", 0.003) : it\sound = 0 ;Document
    it = CreateItemTemplate("Документ об SCP-198", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_198.png", 0.003) : it\sound = 0 ;Document
    it = CreateItemTemplate("Документ об SCP-447", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_447.png", 0.003) : it\sound = 0 ;Document
    it = CreateItemTemplate("Документ об SCP-207", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_207.png", 0.003) : it\sound = 0 ;Document
    it = CreateItemTemplate("Документ об SCP-402", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_402.png", 0.003) : it\sound = 0 ;Document
    it = CreateItemTemplate("Документ об SCP-1033-RU", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_1033_ru.png", 0.003) : it\sound = 0 ;Document
    it = CreateItemTemplate("Документ об SCP-357", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_357.png", 0.003) : it\sound = 0 ;Document

    it = CreateItemTemplate("Инцидент O5-14", "paper", "GFX\items\paper.x", "GFX\items\INV_burnt_note.png", "GFX\items\doc_O5_14.png", 0.0025, "GFX\items\burnt_note.png") : it\sound = 0 ;Incident O5-14

    it = CreateItemTemplate("SCP-500", "scp500", "GFX\items\scp_500_bottle.b3d","GFX\items\INV_scp_500_bottle.png","",0.05) : it\sound = 2

    it = CreateItemTemplate("SCP-402", "scp402", "GFX\items\scp_402.b3d","GFX\items\INV_scp_402.png","",0.075) : it\sound = 3

    it = CreateItemTemplate("SCP-357", "scp357", "GFX\items\scp_357.b3d","GFX\items\INV_scp_357.png","",0.04) : it\sound = 2

    CreateItemTemplate("Мятная 9V-батарейка", "mintbat", "GFX\items\battery.x", "GFX\items\INV_scp_447_battery_9v.png", "", 0.008, "GFX\items\scp_447_battery_9V.png") ;Minty 9V Battery
	CreateItemTemplate("Мятная 18V-батарейка", "mint18vbat", "GFX\items\battery.x", "GFX\items\INV_scp_447_battery_18v.png", "", 0.01, "GFX\items\scp_447_battery_18V.png") ;Minty 18V Battery

    it = CreateItemTemplate("Оторванная рука", "hand3", "GFX\items\severed_hand.b3d", "GFX\items\INV_severed_hand(3).png", "", 0.04, "GFX\items\severed_hand(3).png") : it\sound = 2 ;Severed Hand

    it = CreateItemTemplate("Отчёт", "paper", "GFX\items\paper.x", "GFX\items\INV_paper_bloody.png", "GFX\items\doc_data.png", 0.003, "GFX\items\paper_bloody.png") : it\sound = 0 ;Data Report

	it = CreateItemTemplate("Записка SCP-085", "paper", "GFX\items\note.x", "GFX\items\INV_note.png", "GFX\items\note_085.png", 0.003, "GFX\items\note3.png") : it\sound = 0 ;SCP-085 Note

    it = CreateItemTemplate("Справочный документ", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_references.png", 0.003) : it\sound = 0 ;References Document

	;END
	
	;----Box Of Horrors Items----
	
	CreateItemTemplate("SCP-005", "scp005", "GFX\items\scp_005.b3d", "GFX\items\INV_scp_005.png", "", 0.0004,"")
    
    it = CreateItemTemplate("Бинокль", "binocular", "GFX\items\binoculars.b3d", "GFX\items\INV_binoculars.png", "", 0.03) : it\sound = 2 ;Binoculars

    it = CreateItemTemplate("Документ об SCP-009", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_009.png", 0.003) : it\sound = 0 ;Document SCP-009
    it = CreateItemTemplate("Документ об SCP-409", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_409.png", 0.003) : it\sound = 0 ;Document SCP-409

    CreateItemTemplate("SCP-178", "scp178", "GFX\items\scp_178.b3d", "GFX\items\INV_scp_178.png", "", 0.02, "", "", 1)

    it = CreateItemTemplate("Документ об SCP-005", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_005.png", 0.003) : it\sound = 0 ;Document SCP-005
    it = CreateItemTemplate("Записка от Мэйнарда", "paper", "GFX\items\paper.x", "GFX\items\INV_note.png", "GFX\items\scp_005_note.png", 0.0025, "GFX\items\note.png") : it\sound = 0 ;Note from Maynard

    ;END

    ;----SCP-914 Expansion Items----
	
	it = CreateItemTemplate("Мятный SCP-500-01", "mintscp500pill", "GFX\items\pill.b3d", "GFX\items\INV_scp_447_pill.png", "", 0.0001) : it\sound = 2 ;Minty SCP-500-01
	EntityColor it\obj, 0, 140, 0
	
	it = CreateItemTemplate("Мятная аптечка первой помощи", "mintfirstaid", "GFX\items\first_aid.x", "GFX\items\INV_scp_447_first_aid.png", "", 0.05, "GFX\items\scp_447_first_aid_kit.png") ;Minty First Aid Kit
	it = CreateItemTemplate("Мятная маленькая аптечка первой помощи", "mintfinefirstaid", "GFX\items\first_aid.x", "GFX\items\INV_scp_447_first_aid.png", "", 0.03, "GFX\items\scp_447_first_aid_kit.png") ;Minty Small First Aid Kit
	it = CreateItemTemplate("Мятная синяя аптечка первой помощи", "mintfirstaid2", "GFX\items\first_aid.x", "GFX\items\INV_scp_447_first_aid(2).png", "", 0.03, "GFX\items\scp_447_first_aid_kit(2).png") ;Minty Blue First Aid Kit
	it = CreateItemTemplate("Мятная странная бутылка", "mintveryfinefirstaid", "GFX\items\eye_drops.b3d", "GFX\items\INV_scp_447_strange_bottle.png", "", 0.002, "GFX\items\scp_447_strange_bottle.png") ;Minty Strange Bottle
		
	CreateItemTemplate("Мятные глазные капли", "mintfineeyedrops", "GFX\items\eye_drops.b3d", "GFX\items\INV_scp_447_eye_drops.png", "", 0.0012, "GFX\items\scp_447_eye_drops.png") ;Minty Eyedrops
	CreateItemTemplate("Мятные глазные капли", "mintsupereyedrops", "GFX\items\eye_drops.b3d", "GFX\items\INV_scp_447_eye_drops.png", "", 0.0012, "GFX\items\scp_447_eye_drops.png") ;Minty Eyedrops
	CreateItemTemplate("Мятные глазные капли Зрение+", "minteyedrops","GFX\items\eye_drops.b3d", "GFX\items\INV_scp_447_eye_drops.png", "", 0.0012, "GFX\items\scp_447_eye_drops.png") ;Minty ReVision Eyedrops
	CreateItemTemplate("Мятные красные глазные капли Зрение+", "minteyedrops2", "GFX\items\eye_drops.b3d", "GFX\items\INV_scp_447_eye_drops_red.png", "", 0.0012,"GFX\items\scp_447_eye_drops_red.png") ;Minty RedVision Eyedrops
	
	it = CreateItemTemplate("SCP-447", "scp447", "GFX\items\scp_447.b3d", "GFX\items\INV_scp_447.png", "", 0.003) : it\sound = 2
		
	it = CreateItemTemplate("Обезболивающее", "morphine", "GFX\items\syringe.b3d", "GFX\items\INV_syringe.png", "", 0.005, "GFX\items\syringe.png") : it\sound = 2 ;Painkiller
					
	;END
	
	;----Fan Breach Items----
   
    it = CreateItemTemplate("Документ об SCP-1079", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_1079.png", 0.003) : it\sound = 0	;Document SCP-1079
    it = CreateItemTemplate("Документ об SCP-650", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_650.png", 0.003) : it\sound = 0 ;Document SCP-650

    it = CreateItemTemplate("Разъеденный бронежилет", "corrvest", "GFX\items\vest.x", "GFX\items\INV_vest.png", "", 0.02,"GFX\items\corrosive_vest.png") ;Corrosive Ballistic Vest
	it\sound = 2
	
	it = CreateItemTemplate("SCP-1079-01", "scp1079sweet", "GFX\items\scp_1079_sweet.b3d", "GFX\items\INV_scp_1079_sweet.png", "", 0.01, "") : it\sound = 2

    ;END

    ;----Project Resurrection Items----
  
    it = CreateItemTemplate("SCP-1079", "scp1079", "GFX\items\scp_1079_packet.b3d","GFX\items\INV_scp_1079_packet.png","",0.18) : it\sound = 2

    ;END

    ;----Nine Tailed Fox Items----

    it = CreateItemTemplate("Документ об SCP-457 Страница 1/2","paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_457.png", 0.003) : it\sound = 0 ;Document SCP-457 Page 1/2
	it = CreateItemTemplate("Документ об SCP-457 Страница 2/2","paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_457_ad.png", 0.003) : it\sound = 0 ;Document SCP-457 Page 2/2

    it = CreateItemTemplate("SCP-198","scp198", "GFX\items\scp_198.b3d", "GFX\items\INV_scp_198.png", "", 0.04) : it\sound = 2
    
    it = CreateItemTemplate("SCP-109","scp109", "GFX\items\scp_109.b3d", "GFX\items\INV_scp_109.png", "", 0.0009) : it\sound = 5

    it = CreateItemTemplate("Документ об SCP-109", "paper", "GFX\items\paper.x", "GFX\items\INV_paper.png", "GFX\items\doc_109.png", 0.003) : it\sound = 0 ;Document SCP-109

    ;END

	For it = Each ItemTemplates
		If (it\tex<>0) Then
			If (it\texpath<>"") Then
				For it2=Each ItemTemplates
					If (it2<>it) And (it2\tex=it\tex) Then
						it2\tex = 0
					EndIf
				Next
			EndIf
			FreeTexture it\tex : it\tex = 0
		EndIf
	Next
	
End Function 

Type Items
	Field name$
	Field collider%,model%
	Field itemtemplate.ItemTemplates
	Field DropSpeed#
	
	Field r%,g%,b%,a#
	
	Field level
	
	Field SoundChn%
	
	Field dist#, disttimer#
	
	Field state#, state2#
	
	Field Picked%,Dropped%
	
	Field invimg%
	Field WontColl% = False
	Field xspeed#,zspeed#
	Field SecondInv.Items[20]
	Field ID%
	Field invSlots%
End Type 

Function CreateItem.Items(name$, tempname$, x#, y#, z#, r%=0,g%=0,b%=0,a#=1.0,invSlots%=0)
	Local i.Items = New Items
	Local it.ItemTemplates
	Local o.Objects = First Objects
	
	name = Lower(name)
	tempname = Lower (tempname)
	
	For it.ItemTemplates = Each ItemTemplates
		If Lower(it\name) = name Then
			If Lower(it\tempname) = tempname Then
				i\itemtemplate = it
				i\collider = CreatePivot()			
				EntityRadius i\collider, 0.01
				EntityPickMode i\collider, 1, False
				i\model = CopyEntity(it\obj,i\collider)
				i\name = it\name
				ShowEntity i\collider
				ShowEntity i\model
			EndIf
		EndIf
	Next 
	
	i\WontColl = False
	
	If i\itemtemplate = Null Then RuntimeError("Не найден шаблон предмета ("+name+", "+tempname+")") ;Item template not found
	
	ResetEntity i\collider		
	PositionEntity(i\collider, x, y, z, True)
	RotateEntity (i\collider, 0, Rand(360), 0)
	i\dist = EntityDistance(Collider, i\collider)
	i\DropSpeed = 0.0
	
	If tempname = "cup" Then
		i\r=r
		i\g=g
		i\b=b
		i\a=a
		
		Local liquid = CopyEntity(o\OtherModelsID[0])
		ScaleEntity liquid, i\itemtemplate\scale,i\itemtemplate\scale,i\itemtemplate\scale,True
		PositionEntity liquid, EntityX(i\collider,True),EntityY(i\collider,True),EntityZ(i\collider,True)
		EntityParent liquid, i\model
		EntityColor liquid, r,g,b
		
		If a < 0 Then 
			EntityFX liquid, 1
			EntityAlpha liquid, Abs(a)
		Else
			EntityAlpha liquid, Abs(a)
		EndIf
		
		
		EntityShininess liquid, 1.0
	EndIf
	
	i\invimg = i\itemtemplate\invimg
	If (tempname="clipboard") And (invSlots=0) Then
		invSlots = 10
		SetAnimTime i\model,17.0
		i\invimg = i\itemtemplate\invimg2
	ElseIf (tempname="wallet") And (invSlots=0) Then
		invSlots = 10
		SetAnimTime i\model,0.0
	EndIf
	
	i\invSlots=invSlots
	
	i\ID=LastItemID+1
	LastItemID=i\ID

	Return i
End Function

Function RemoveItem(i.Items)
	Local n
	FreeEntity(i\model) : FreeEntity(i\collider) : i\collider = 0
	
	For n% = 0 To MaxItemAmount - 1
		If Inventory(n) = i
			Inventory(n) = Null
			ItemAmount = ItemAmount-1
			Exit
		EndIf
	Next
	If SelectedItem = i Then
		Select SelectedItem\itemtemplate\tempname 
			Case "nvgoggles", "supernv"
				WearingNightVision = False
			Case "gasmask", "supergasmask", "gasmask2", "gasmask3"
				WearingGasMask = False
			Case "vest", "finevest", "veryfinevest"
				WearingVest = False
			Case "hazmatsuit","hazmatsuit2","hazmatsuit3"
				WearingHazmat = False	
			Case "scp714"
				I_714\Using = False
			Case "scp1499","super1499"
				I_1499\Using = False
			Case "scp427"
				I_427\Using = False
			;MOD
			Case "scp178"
		        I_178\Using = False
		    Case "scp215"
			    I_215\Using = False
		    Case "scp1033ru"
			    I_1033RU\Using = False
			Case "scp402"
			    I_402\Using = False
			Case "scp357"
			    I_357\Timer = 0
            ;END
		End Select
		
		SelectedItem = Null
	EndIf
	If i\itemtemplate\img <> 0
		FreeImage i\itemtemplate\img
		i\itemtemplate\img = 0
	EndIf
	Delete i

End Function

Function UpdateItems()
	Local n, i.Items, i2.Items
	Local xtemp#, ytemp#, ztemp#
	Local temp%, np.NPCs
	Local pick%
	Local fs.FPS_Settings = First FPS_Settings
	Local HideDist = HideDistance*0.5
	Local deletedItem% = False
	
	ClosestItem = Null
	For i.Items = Each Items
		i\Dropped = 0
		
		If (Not i\Picked) Then
			If i\disttimer < MilliSecs2() Then
				i\dist = EntityDistance(Camera, i\collider)
				i\disttimer = MilliSecs2() + 700
				If i\dist < HideDist Then ShowEntity i\collider
			EndIf
			
			If i\dist < HideDist Then
				ShowEntity i\collider
				
				If i\dist < 1.2 Then
					If ClosestItem = Null Then
						If EntityInView(i\model, Camera) Then
							;pick = LinePick(EntityX(Camera),EntityY(i\collider),EntityZ(Camera),EntityX(i\collider)-EntityX(Camera),0,EntityZ(i\collider)-EntityZ(Camera),0.0)
							;If pick=i\collider Or pick=0 Then
							If EntityVisible(i\collider,Camera) Then							
								ClosestItem = i
							EndIf
						EndIf
					ElseIf ClosestItem = i Or i\dist < EntityDistance(Camera, ClosestItem\collider) Then 
						If EntityInView(i\model, Camera) Then
							;pick = LinePick(EntityX(Camera),EntityY(i\collider),EntityZ(Camera),EntityX(i\collider)-EntityX(Camera),0,EntityZ(i\collider)-EntityZ(Camera),0.0)
							;If pick=i\collider Or pick=0 Then
							If EntityVisible(i\collider,Camera) Then
								ClosestItem = i
							EndIf
						EndIf
					EndIf
				EndIf
				
				If EntityCollided(i\collider, HIT_MAP) Then
					i\DropSpeed = 0
					i\xspeed = 0.0
					i\zspeed = 0.0
				Else
					If ShouldEntitiesFall
						pick = LinePick(EntityX(i\collider),EntityY(i\collider),EntityZ(i\collider),0,-10,0)
						If pick
							i\DropSpeed = i\DropSpeed - 0.0004 * fs\FPSfactor[0]
							TranslateEntity i\collider, i\xspeed*fs\FPSfactor[0], i\DropSpeed * fs\FPSfactor[0], i\zspeed*fs\FPSfactor[0]
							If i\WontColl Then ResetEntity(i\collider)
						Else
							i\DropSpeed = 0
							i\xspeed = 0.0
							i\zspeed = 0.0
						EndIf
					Else
						i\DropSpeed = 0
						i\xspeed = 0.0
						i\zspeed = 0.0
					EndIf
				EndIf
				
				If i\dist<HideDist*0.2 Then
					For i2.Items = Each Items
						If i<>i2 And (Not i2\Picked) And i2\dist<HideDist*0.2 Then
							
							xtemp# = (EntityX(i2\collider,True)-EntityX(i\collider,True))
							ytemp# = (EntityY(i2\collider,True)-EntityY(i\collider,True))
							ztemp# = (EntityZ(i2\collider,True)-EntityZ(i\collider,True))
							
							ed# = (xtemp*xtemp+ztemp*ztemp)
							If ed<0.07 And Abs(ytemp)<0.25 Then
								;items are too close together, push away
								If PlayerRoom\RoomTemplate\Name	<> "room2storage" Then
									xtemp = xtemp*(0.07-ed)
									ztemp = ztemp*(0.07-ed)
									
									While Abs(xtemp)+Abs(ztemp)<0.001
										xtemp = xtemp+Rnd(-0.002,0.002)
										ztemp = ztemp+Rnd(-0.002,0.002)
									Wend
									
									TranslateEntity i2\collider,xtemp,0,ztemp
									TranslateEntity i\collider,-xtemp,0,-ztemp
								EndIf
							EndIf
						EndIf
					Next
				EndIf
			Else
				HideEntity i\collider
			EndIf
		Else
			i\DropSpeed = 0
			i\xspeed = 0.0
			i\zspeed = 0.0
		EndIf

		deletedItem = False
	Next
	
	If ClosestItem <> Null Then
		
		If MouseHit1 Then PickItem(ClosestItem)
	EndIf
End Function

Function PickItem(item.Items)
	Local n% = 0
	Local canpickitem = True
	Local fullINV% = True
	
	For n% = 0 To MaxItemAmount - 1
		If Inventory(n) = Null
			fullINV = False
			Exit
		EndIf
	Next
	
	If WearingHazmat > 0 Then
		Msg = "Вы не можете подбирать предметы во время ношения защитного костюма." ;You cannot pick up any items while wearing a hazmat suit.
		MsgTimer = 70*5
		Return
	EndIf

	If (Not fullINV) Then
		For n% = 0 To MaxItemAmount - 1
			If Inventory(n) = Null Then
				Select item\itemtemplate\tempname
					Case "scp1123"
						If (Not I_714\Using = 1) And (Not WearingGasMask = 3) And (Not WearingHazmat = 3) Then
							If PlayerRoom\RoomTemplate\Name <> "room1123" Then
								ShowEntity at\OverlayID[14]
								LightFlash = 7
								PlaySound_Strict(LoadTempSound("SFX\SCP\1123\Touch.ogg"))
								If I_1033RU\HP = 0		
								    DeathMSG = SubjectName$+" был застрелен после попытки напасть на члена Девятихвостой Лисы. Записи с камер наблюдения показывают, " ;Subject D-9341 was shot dead after attempting to attack a member of Nine-Tailed Fox. Surveillance tapes show that the subject had been 
								DeathMSG = DeathMSG + "что примерно за 9 минут до этого субъект блуждал по Зоне, выкрикивая фразу " + Chr(34) + "Избавиться от четырёх вредителей!" + Chr(34) ;wandering around the site approximately 9 minutes prior, shouting the phrase " + Chr(34) + "get rid of the four pests
								DeathMSG = DeathMSG + " на китайском языке. Неподалёку в [УДАЛЕНО] был обнаружен SCP-1123, что даёт основания предполагать, что субъект вступил с ним в физический контакт. Как " ; in chinese. SCP-1123 was found in [REDACTED] nearby, suggesting the subject had come into physical contact with it. How 
								DeathMSG = DeathMSG + "именно SCP-1123 был изъят из своей камеры содержания, до сих пор неизвестно." ;exactly SCP-1123 was removed from its containment chamber is still unknown.
								    Kill()
								Else
								    Damage1033RU(70+(5*SelectedDifficulty\aggressiveNPCs))
                                EndIf
							EndIf
							For e.Events = Each Events
								If e\EventName = "room1123" Then 
									If e\eventstate = 0 Then
										ShowEntity at\OverlayID[14]
										LightFlash = 3
										PlaySound_Strict(LoadTempSound("SFX\SCP\1123\Touch.ogg"))
									EndIf
									e\eventstate = Max(1, e\eventstate)
									Exit
								EndIf
							Next
						EndIf
						
						Return
					Case "killbat"
						ShowEntity at\OverlayID[14]
						LightFlash = 1.0
						PlaySound_Strict(IntroSFX(11))
						If I_1033RU\HP = 0
						    DeathMSG = SubjectName$+" найден мёртвым в отсеке вывода SCP-914 рядом с предметом, похожим на обычную 9V-батарейку. Субъект покрыт сильными " ;Subject D-9341 found dead inside SCP-914's output booth next to what appears to be an ordinary nine-volt battery. The subject is covered in severe 
							DeathMSG = DeathMSG + "электрическими ожогами, предполагается, что он был убит током от этой батарейки. Батарейка взята на хранение для дальнейшего изучения." ;electrical burns, and assumed to be killed via an electrical shock caused by the battery. The battery has been stored for further study.
						    Kill()
						Else
						    Damage1033RU(50+(5*SelectedDifficulty\aggressiveNPCs))
						EndIf
					Case "scp148"
						GiveAchievement(Achv148)	
					Case "scp513"
						GiveAchievement(Achv513)
					Case "scp860"
						GiveAchievement(Achv860)
					Case "key7"
						GiveAchievement(AchvOmni)
					Case "veryfinevest"
						Msg = "The vest is too heavy to pick up."
						MsgTimer = 70*6
						Exit
					Case "firstaid", "finefirstaid", "veryfinefirstaid", "firstaid2"
						item\state = 0
					Case "navigator", "nav"
						If item\itemtemplate\name = "Навигатор S-NAV Ultimate" Then GiveAchievement(AchvSNAV) ;S-NAV Navigator Ultimate
					Case "hazmatsuit", "hazmatsuit2", "hazmatsuit3"
						canpickitem = True
						For z% = 0 To MaxItemAmount - 1
							If Inventory(z) <> Null Then
								If Inventory(z)\itemtemplate\tempname="hazmatsuit" Or Inventory(z)\itemtemplate\tempname="hazmatsuit2" Or Inventory(z)\itemtemplate\tempname="hazmatsuit3" Then
									canpickitem% = False
									Exit
								ElseIf Inventory(z)\itemtemplate\tempname="vest" Or Inventory(z)\itemtemplate\tempname="finevest" Then
									canpickitem% = 2
									Exit
								EndIf
							EndIf
						Next
						
						If canpickitem=False Then
							Msg = "Вы не можете носить два защитных костюма одновременно." ;You are not able to wear two hazmat suits at the same time.
							MsgTimer = 70 * 5
							Return
						ElseIf canpickitem=2 Then
							Msg = "Вы не можете носить жилет и защитный костюм одновременно." ;You are not able to wear a vest and a hazmat suit at the same time.
							MsgTimer = 70 * 5
							Return
						Else
							SelectedItem = item
						EndIf
					Case "vest","finevest"
						canpickitem = True
						For z% = 0 To MaxItemAmount - 1
							If Inventory(z) <> Null Then
								If Inventory(z)\itemtemplate\tempname="vest" Or Inventory(z)\itemtemplate\tempname="finevest" Then
									canpickitem% = False
									Exit
								ElseIf Inventory(z)\itemtemplate\tempname="hazmatsuit" Or Inventory(z)\itemtemplate\tempname="hazmatsuit2" Or Inventory(z)\itemtemplate\tempname="hazmatsuit3" Then
									canpickitem% = 2
									Exit
								EndIf
							EndIf
						Next
						
						If canpickitem=False Then
							Msg = "Вы не можете носить два жилета одновременно." ;You are not able to wear two vests at the same time.
							MsgTimer = 70 * 5
							Return
						ElseIf canpickitem=2 Then
							Msg = "Вы не можете носить жилет и защитный костюм одновременно." ;You are not able to wear a vest and a hazmat suit at the same time.
							MsgTimer = 70 * 5
							Return
						Else
							SelectedItem = item
						EndIf
					;MOD
					Case "scp178"
						SetAnimTime item\model, 19.0
					Case "glassescase"
					    SetAnimTime item\model, 19.0
					Case "scp215"
					    SetAnimTime item\model, 1.0
					Case "key6"
				        GiveAchievement(AchvKeyCard6)
				    Case "scp005"
				   	    GiveAchievement(Achv005)
				    Case "scp207"
				        If I_402\Timer > 0 Then
					        PlaySound_Strict(HorrorSFX(Rand(0, 3)))
					        Msg = Chr(34) + "Я... Не могу..." + Chr(34) ;I... Can't...
					        MsgTimer = 70 * 6
					        Exit
					    Else
					        GiveAchievement(Achv207)
					    EndIf
				    Case "scp198"
					    GiveAchievement(Achv198)
						Msg = "Вы чувствуете лёгкую боль, а SCP-198 теперь привязан к вам." ;You feel slight pain and SCP-198 is now attached at you.
						MsgTimer = 70*6
						PlaySound_Strict LoadTempSound("SFX\SCP\198\198shock.ogg")
						LightFlash = 2.5
						BlurTimer = 1000
					    StaminaEffect = Min(Stamina, 10)
				        StaminaEffectTimer = 240
		                Sanity = Max(-850, Sanity)
		                If I_1033RU\HP = 0
		                    Injuries = Injuries + 0.5
		                Else
		                    Damage1033RU(30 + (5 * SelectedDifficulty\aggressiveNPCs))
                        EndIf
                    Case "scp447", "mintveryfinefirstaid", "minteyedrops", "mintfineeyedrops", "mintsupereyedrops", "minteyedrops2"
                        If I_402\Timer > 0 Then
					        PlaySound_Strict(HorrorSFX(Rand(0, 3)))
					        Msg = Chr(34) + "Я... Не могу..." + Chr(34) ;I... Can't...
					        MsgTimer = 70 * 6
					        Exit
					    EndIf
					Case "scp109"
					    SetAnimTime item\model, 19.0
                        If I_402\Timer > 0 Then
					        PlaySound_Strict(HorrorSFX(Rand(0, 3)))
					        Msg = Chr(34) + "Я... Не могу..." + Chr(34) ;I... Can't...
					        MsgTimer = 70 * 6
					        Exit   
					    EndIf
                    Case "syringe", "syringeinf", "finesyringe", "veryfinesyringe", "cup", "morphine", "eyedrops", "eyedrops2", "fineeyedrops", "supereyedrops", "veryfinefirstaid"
					    If I_402\Timer > 0 Then
					        PlaySound_Strict(HorrorSFX(Rand(0,3)))
					        Msg = Chr(34)+"Я... не... могу..."+Chr(34) ;I... Can't...
					        MsgTimer = 70 * 6
					        Exit
					    EndIf
					Case "scp357"
                        GiveAchievement(Achv357)
                        Msg = "Вы взяли SCP-357 в руку." ;You grabbed the SCP-357 in your hand.
                        MsgTimer = 70 * 6
                        I_357\Timer = 1.0
                    Case "corrvest"
						Msg = Chr(34)+"Я не настолько глуп, чтобы подбирать это."+Chr(34) ;I'm not stupid enough to pick it up.
						MsgTimer = 70*6
						Exit
				    ;END
				End Select
				
				If item\itemtemplate\sound <> 66 Then PlaySound_Strict(PickSFX(item\itemtemplate\sound))
				item\Picked = True
				item\Dropped = -1
				
				item\itemtemplate\found=True
				ItemAmount = ItemAmount + 1
				
				Inventory(n) = item
				HideEntity(item\collider)
				Exit
			EndIf
		Next
	Else
		Msg = "Вы не можете носить больше предметов." ;You cannot carry any more items.
		MsgTimer = 70 * 5
	EndIf
End Function

Function DropItem(item.Items,playdropsound%=True)
	If WearingHazmat > 0 Then
		Msg = "Вы не можете выбрасывать предметы во время ношения защитного костюма." ;You cannot drop any items while wearing a hazmat suit.
		MsgTimer = 70*5
		Return
	EndIf
	If playdropsound Then
		If item\itemtemplate\sound <> 66 Then PlaySound_Strict(PickSFX(item\itemtemplate\sound))
	EndIf
	
	item\Dropped = 1
	
	ShowEntity(item\collider)
	PositionEntity(item\collider, EntityX(Camera), EntityY(Camera), EntityZ(Camera))
	RotateEntity(item\collider, EntityPitch(Camera), EntityYaw(Camera)+Rnd(-20,20), 0)
	MoveEntity(item\collider, 0, -0.1, 0.1)
	RotateEntity(item\collider, 0, EntityYaw(Camera)+Rnd(-110,110), 0)
	
	ResetEntity (item\collider)
	
	item\Picked = False
	For z% = 0 To MaxItemAmount - 1
		If Inventory(z) = item Then Inventory(z) = Null
	Next
	Select item\itemtemplate\tempname
		Case "gasmask", "supergasmask", "gasmask3"
			WearingGasMask = False
		Case "hazmatsuit",  "hazmatsuit2", "hazmatsuit3"
			WearingHazmat = False
		Case "vest", "finevest"
			WearingVest = False
		Case "nvgoggles"
			If WearingNightVision = 1 Then CameraFogFar = StoredCameraFogFar : WearingNightVision = False
		Case "supernv"
			If WearingNightVision = 2 Then CameraFogFar = StoredCameraFogFar : WearingNightVision = False
		Case "finenvgoggles"
			If WearingNightVision = 3 Then CameraFogFar = StoredCameraFogFar : WearingNightVision = False
		Case "scp714"
			I_714\Using = False
		Case "scp1499","super1499"
			I_1499\Using = False
		Case "scp427"
			I_427\Using = False
		;MOD
		Case "scp178"
		    I_178\Using = False
		Case "scp215"
			I_215\Using = False
		Case "scp1033ru"
			I_1033RU\Using = False
		Case "scp402"
			I_402\Using = False
	    Case "scp357"
			I_357\Timer = 0
        ;END
	End Select

End Function

;Update any ailments inflicted by SCP-294 drinks.
Function Update294()
	Local fs.FPS_Settings = First FPS_Settings
	
	If CameraShakeTimer > 0 Then
		CameraShakeTimer = CameraShakeTimer - (fs\FPSfactor[0]/70)
		CameraShake = 2
	EndIf
	
	If VomitTimer > 0 Then
		VomitTimer = VomitTimer - (fs\FPSfactor[0]/70)
		
		If (MilliSecs2() Mod 1600) < Rand(200, 400) Then
			If BlurTimer = 0 Then BlurTimer = Rnd(10, 20)*70
			CameraShake = Rnd(0, 2)
		EndIf

		If Rand(50) = 50 And (MilliSecs2() Mod 4000) < 200 Then PlaySound_Strict(CoughSFX(Rand(0,2)))
		
		;Regurgitate when timer is below 10 seconds. (ew)
		If VomitTimer < 10 And Rnd(0, 500 * VomitTimer) < 2 Then
			If (Not ChannelPlaying(VomitCHN)) And (Not Regurgitate) Then
				VomitCHN = PlaySound_Strict(LoadTempSound("SFX\SCP\294\Retch" + Rand(1, 2) + ".ogg"))
				Regurgitate = MilliSecs2() + 50
			EndIf
		EndIf
		
		If Regurgitate > MilliSecs2() And Regurgitate <> 0 Then
			mouse_y_speed_1 = mouse_y_speed_1 + 1.0
		Else
			Regurgitate = 0
		EndIf
		
	ElseIf VomitTimer < 0 Then ;vomit
		VomitTimer = VomitTimer - (fs\FPSfactor[0]/70)
		
		If VomitTimer > -5 Then
			If (MilliSecs2() Mod 400) < 50 Then CameraShake = 4 
			mouse_x_speed_1 = 0.0
			Playable = False
		Else
			Playable = True
		EndIf
		
		If (Not Vomit) Then
			BlurTimer = 40 * 70
			VomitSFX = LoadSound_Strict("SFX\SCP\294\Vomit.ogg")
			VomitCHN = PlaySound_Strict(VomitSFX)
			PrevInjuries = Injuries
			PrevBloodloss = Bloodloss
			Injuries = 1.5
			Bloodloss = 70
			EyeIrritation = 9 * 70
			
			pvt = CreatePivot()
			PositionEntity(pvt, EntityX(Camera), EntityY(Collider) - 0.05, EntityZ(Camera))
			TurnEntity(pvt, 90, 0, 0)
			EntityPick(pvt, 0.3)
			de.decals = CreateDecal(5, PickedX(), PickedY() + 0.005, PickedZ(), 90, 180, 0)
			de\Size = 0.001 : de\SizeChange = 0.001 : de\MaxSize = 0.6 : EntityAlpha(de\obj, 1.0) : EntityColor(de\obj, 0.0, Rnd(200, 255), 0.0) : ScaleSprite de\obj, de\size, de\size
			FreeEntity pvt
			Vomit = True
		EndIf
		
		UpdateDecals()
		
		mouse_y_speed_1 = mouse_y_speed_1 + Max((1.0 + VomitTimer / 10), 0.0)
		
		If VomitTimer < -15 Then
			FreeSound_Strict(VomitSFX)
			VomitTimer = 0
			If KillTimer >= 0 Then
				PlaySound_Strict(BreathSFX(0,0))
			EndIf
			Injuries = PrevInjuries
			Bloodloss = PrevBloodloss
			Vomit = False
		EndIf
	EndIf
End Function

