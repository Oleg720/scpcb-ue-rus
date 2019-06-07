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
		If texture=0 Then texture=LoadTexture_Strict(texturepath,texflags%) : it\texpath = texturepath; : DebugLog texturepath
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
	
	;If imgpath<>"" Then
	;	it\img=LoadImage(imgpath)
	;	
	;	;DebugLog imgpath
	;	
	;	If it\img<>0 Then ResizeImage(it\img, ImageWidth(it\img) * MenuScale, ImageHeight(it\img) * MenuScale)
	;EndIf
	
	it\tempname = tempname
	it\name = name
	
	it\sound = 1

	HideEntity it\obj
	
	Return it
	
End Function

Function InitItemTemplates()
	Local it.ItemTemplates,it2.ItemTemplates
	
	it = CreateItemTemplate("Немного SCP-420-J", "scp420j", "GFX\items\scp420J.x", "GFX\items\INVscp420J.png", "", 0.0005) ;Some SCP-420-J
	it\sound = 2
	
	CreateItemTemplate("Ключ-карта 1-го уровня", "key1",  "GFX\items\keycard.x", "GFX\items\INVkey1.png", "", 0.0004,"GFX\items\keycard1.png") ;Level 1 Key Card
	CreateItemTemplate("Ключ-карта 2-го уровня", "key2",  "GFX\items\keycard.x", "GFX\items\INVkey2.png", "", 0.0004,"GFX\items\keycard2.png") ;Level 2 Key Card
	CreateItemTemplate("Ключ-карта 3-го уровня", "key3",  "GFX\items\keycard.x", "GFX\items\INVkey3.png", "", 0.0004,"GFX\items\keycard3.png") ;Level 3 Key Card
	CreateItemTemplate("Ключ-карта 4-го уровня", "key4",  "GFX\items\keycard.x", "GFX\items\INVkey4.png", "", 0.0004,"GFX\items\keycard4.png") ;Level 4 Key Card
	CreateItemTemplate("Ключ-карта 5-го уровня", "key5", "GFX\items\keycard.x", "GFX\items\INVkey5.png", "", 0.0004,"GFX\items\keycard5.png") ;Level 5 Key Card
	CreateItemTemplate("Игральная карта", "misc", "GFX\items\keycard.x", "GFX\items\INVcard.png", "", 0.0004,"GFX\items\card.png") ;Playing Card
	CreateItemTemplate("Mastercard", "misc", "GFX\items\keycard.x", "GFX\items\INVmastercard.png", "", 0.0004,"GFX\items\mastercard.png")
	CreateItemTemplate("Ключ-карта Омни", "key7", "GFX\items\keycard.x", "GFX\items\INVkeyomni.png", "", 0.0004,"GFX\items\keycardomni.png") ;Key Card Omni
	
	it = CreateItemTemplate("SCP-860", "scp860", "GFX\items\scp860.b3d", "GFX\items\INVscp860.png", "", 0.0026)
	it\sound = 3
	
	it = CreateItemTemplate("Документ об SCP-079", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc079.png", 0.003) : it\sound = 0 ;Document SCP-079
	it = CreateItemTemplate("Документ об SCP-895", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc895.png", 0.003) : it\sound = 0 ;Document SCP-895
	it = CreateItemTemplate("Документ об SCP-860", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc860.png", 0.003) : it\sound = 0 ;Document SCP-860
	it = CreateItemTemplate("Документ об SCP-860-1", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc8601.png", 0.003) : it\sound = 0 ;Document SCP-860-1
	it = CreateItemTemplate("Материалы, добытые посредством SCP-093", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc093rm.png", 0.003) : it\sound = 0 ;SCP-093 Recovered Materials
	it = CreateItemTemplate("Документ об SCP-106", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc106.png", 0.003) : it\sound = 0 ;Document SCP-106
	it = CreateItemTemplate("Записка д-ра Эллока", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc106_2.png", 0.0025) : it\sound = 0 ;Dr. Allok's Note
	it = CreateItemTemplate("Протокол возврата RP-106-N", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\docRP.png", 0.0025) : it\sound = 0 ;Recall Protocol RP-106-N
	it = CreateItemTemplate("Документ об SCP-682", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc682.png", 0.003) : it\sound = 0 ;Document SCP-682
	it = CreateItemTemplate("Документ об SCP-173", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc173.png", 0.003) : it\sound = 0 ;Document SCP-173
	it = CreateItemTemplate("Документ об SCP-372", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc372.png", 0.003) : it\sound = 0 ;Document SCP-372
	it = CreateItemTemplate("Документ об SCP-049", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc049.png", 0.003) : it\sound = 0 ;Document SCP-049
	it = CreateItemTemplate("Документ об SCP-096", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc096.png", 0.003) : it\sound = 0 ;Document SCP-096
	it = CreateItemTemplate("Документ об SCP-008", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc008.png", 0.003) : it\sound = 0 ;Document SCP-008
	it = CreateItemTemplate("Документ об SCP-012", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc012.png", 0.003) : it\sound = 0 ;Document SCP-012
	it = CreateItemTemplate("Документ об SCP-500", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc500.png", 0.003) : it\sound = 0 ;Document SCP-500
	it = CreateItemTemplate("Документ об SCP-714", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc714.png", 0.003) : it\sound = 0 ;Document SCP-714
	it = CreateItemTemplate("Документ об SCP-513", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc513.png", 0.003) : it\sound = 0 ;Document SCP-513
	it = CreateItemTemplate("Документ об SCP-035", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc035.png", 0.003) : it\sound = 0 ;Document SCP-035
	it = CreateItemTemplate("Дополнения к документу об SCP-035", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc035ad.png", 0.003) : it\sound = 0 ;SCP-035 Addendum
	it = CreateItemTemplate("Документ об SCP-939", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc939.png", 0.003) : it\sound = 0 ;Document SCP-939
	it = CreateItemTemplate("Документ об SCP-966", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc966.png", 0.003) : it\sound = 0 ;Document SCP-966
	it = CreateItemTemplate("Документ об SCP-970", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc970.png", 0.003) : it\sound = 0 ;Document SCP-970
	it = CreateItemTemplate("Документ об SCP-1048", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc1048.png", 0.003) : it\sound = 0 ;Document SCP-1048
	it = CreateItemTemplate("Документ об SCP-1123", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc1123.png", 0.003) : it\sound = 0 ;Document SCP-1123
	it = CreateItemTemplate("Документ об SCP-1162", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc1162.png", 0.003) : it\sound = 0 ;Document SCP-1162
	it = CreateItemTemplate("Документ об SCP-1499", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc1499.png", 0.003) : it\sound = 0 ;Document SCP-1499
	it = CreateItemTemplate("Отчёт об инциденте SCP-1048-A", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc1048a.png", 0.003) : it\sound = 0 ;Incident Report SCP-1048-A
	
	it = CreateItemTemplate("Рисунок", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc1048.png", 0.003) : it\sound = 0 ;Drawing
	
	it = CreateItemTemplate("Листовка", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\leaflet.png", 0.003, "GFX\items\notetexture.jpg") : it\sound = 0 ;Leaflet
	
	it = CreateItemTemplate("Записка д-ра Л.", "paper", "GFX\items\paper.x", "GFX\items\INVnote.png", "GFX\items\docL1.png", 0.0025, "GFX\items\notetexture.jpg") : it\sound = 0 ;Dr. L's Note
	it = CreateItemTemplate("Записка д-ра Л", "paper", "GFX\items\paper.x", "GFX\items\INVnote.png", "GFX\items\docL2.png", 0.0025, "GFX\items\notetexture.jpg") : it\sound = 0 ;Dr L's Note
	it = CreateItemTemplate("Записка с кровавым следом", "paper", "GFX\items\paper.x", "GFX\items\INVnote.png", "GFX\items\docL3.png", 0.0025, "GFX\items\notetexture.jpg") : it\sound = 0 ;Blood-stained Note
	it = CreateItemTemplate("Обгоревшая записка д-ра Л.", "paper", "GFX\items\paper.x", "GFX\items\INVbn.png", "GFX\items\docL4.png", 0.0025, "GFX\items\BurntNoteTexture.jpg") : it\sound = 0 ;Dr. L's Burnt Note
	it = CreateItemTemplate("Обгоревшая записка д-ра Л", "paper", "GFX\items\paper.x", "GFX\items\INVbn.png", "GFX\items\docL5.png", 0.0025, "GFX\items\BurntNoteTexture.jpg") : it\sound = 0 ;Dr L's Burnt Note
	it = CreateItemTemplate("Выжженная записка", "paper", "GFX\items\paper.x", "GFX\items\INVbn.png", "GFX\items\docL6.png", 0.0025, "GFX\items\BurntNoteTexture.jpg") : it\sound = 0 ;Scorched Note
	
	it = CreateItemTemplate("Страница из дневника", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\docGonzales.png", 0.0025) : it\sound = 0 ;Journal Page
	
	it = CreateItemTemplate("Запись №1", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\f4.png", 0.004, "GFX\items\f4.png") : it\sound = 0 ;Log #1
	it = CreateItemTemplate("Запись №2", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\f5.png", 0.004, "GFX\items\f4.png") : it\sound = 0 ;Log #2
	it = CreateItemTemplate("Запись №3", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\f6.png", 0.004, "GFX\items\f4.png") : it\sound = 0 ;Log #3
	
	it = CreateItemTemplate("Странная записка", "paper", "GFX\items\paper.x", "GFX\items\INVnote.png", "GFX\items\docStrange.png", 0.0025, "GFX\items\notetexture.jpg") : it\sound = 0 ;Strange Note
	
	it = CreateItemTemplate("Документ о ядерных устройствах", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\docNDP.png", 0.003) : it\sound = 0 ;Nuclear Device Document
	it = CreateItemTemplate("Информационная листовка для персонала класса D", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\docORI.png", 0.003) : it\sound = 0 ;Class D Orientation Leaflet
	
	it = CreateItemTemplate("Записка от Даниэля", "paper", "GFX\items\note.x", "GFX\items\INVnote2.png", "GFX\items\docdan.png", 0.0025) : it\sound = 0 ;Note from Daniel
	
	it = CreateItemTemplate("Обгоревший документ", "paper", "GFX\items\paper.x", "GFX\items\INVbn.png", "GFX\items\bn.it", 0.003, "GFX\items\BurntNoteTexture.jpg") ;Burnt Note
	it\img = BurntNote : it\sound = 0
	
	it = CreateItemTemplate("Загадочная записка", "paper", "GFX\items\paper.x", "GFX\items\INVnote.png", "GFX\items\sn.it", 0.003, "GFX\items\notetexture.jpg") : it\sound = 0	;Mysterious Note
	
	it = CreateItemTemplate("Мобильные оперативные группы", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\docMTF.png", 0.003) : it\sound = 0 ;Mobile Task Forces
	it = CreateItemTemplate("Уровни допуска", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\docSC.png", 0.003) : it\sound = 0 ;Security Clearance Levels
	it = CreateItemTemplate("Классы объектов", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\docOBJC.png", 0.003) : it\sound = 0 ;Object Classes
	it = CreateItemTemplate("Документ", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\docRAND3.png", 0.003) : it\sound = 0 ;Document
	it = CreateItemTemplate("Дополнение: Протокол исследований 5/14", "paper", "GFX\items\paper.x", "GFX\items\INVnote.png", "GFX\items\docRAND2.png", 0.003, "GFX\items\notetexture.jpg") : it\sound = 0 ;Addendum: 5/14 Test Log
	it = CreateItemTemplate("Уведомление", "paper", "GFX\items\paper.x", "GFX\items\INVnote.png", "GFX\items\docRAND1.png", 0.003, "GFX\items\notetexture.jpg") :it\sound = 0 ;Notification
	it = CreateItemTemplate("Отчёт об инциденте SCP-106-0204", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\docIR106.png", 0.003) : it\sound = 0 ;Incident Report SCP-106-0204
	
	it = CreateItemTemplate("Бронежилет", "vest", "GFX\items\vest.x", "GFX\items\INVvest.png", "", 0.02,"GFX\items\vest.png") : it\sound = 4 ;Ballistic Vest
	it = CreateItemTemplate("Тяжёлый бронежилет", "finevest", "GFX\items\vest.x", "GFX\items\INVvest.png", "", 0.022,"GFX\items\vest.png") ;Heavy Ballistic Vest
	it\sound = 4
	it = CreateItemTemplate("Громоздкий бронежилет", "veryfinevest", "GFX\items\vest.x", "GFX\items\INVvest.png", "", 0.025,"GFX\items\vest.png") ;Bulky Ballistic Vest
	it\sound = 2
	
	it = CreateItemTemplate("Защитный костюм", "hazmatsuit", "GFX\items\hazmat.b3d", "GFX\items\INVhazmat.png", "", 0.013) ;Hazmat Suit
	it\sound = 2
	it = CreateItemTemplate("Защитный костюм", "hazmatsuit2", "GFX\items\hazmat.b3d", "GFX\items\INVhazmat.png", "", 0.013) ;Hazmat Suit
	it\sound = 2
	it = CreateItemTemplate("Тяжёлый защитный костюм", "hazmatsuit3", "GFX\items\hazmat.b3d", "GFX\items\INVhazmat.png", "", 0.013) ;Heavy Hazmat Suit
	it\sound = 2
	
	it = CreateItemTemplate("cup", "cup", "GFX\items\cup.x", "GFX\items\INVcup.png", "", 0.04) : it\sound = 2
	
	it = CreateItemTemplate("Пустой стакан", "emptycup", "GFX\items\cup.x", "GFX\items\INVcup.png", "", 0.04) : it\sound = 2 ;Empty Cup
	
	it = CreateItemTemplate("SCP-500-01", "scp500pill", "GFX\items\pill.b3d", "GFX\items\INVscp500pill.png", "", 0.0001) : it\sound = 2
	EntityColor it\obj,255,0,0
	
	it = CreateItemTemplate("Аптечка первой помощи", "firstaid", "GFX\items\firstaid.x", "GFX\items\INVfirstaid.png", "", 0.05) ;First Aid Kit
	it = CreateItemTemplate("Маленькая аптечка первой помощи", "finefirstaid", "GFX\items\firstaid.x", "GFX\items\INVfirstaid.png", "", 0.03) ;Small First Aid Kit
	it = CreateItemTemplate("Синяя аптечка первой помощи", "firstaid2", "GFX\items\firstaid.x", "GFX\items\INVfirstaid2.png", "", 0.03, "GFX\items\firstaid_kit2.png") ;Blue First Aid Kit
	it = CreateItemTemplate("Странная бутылка", "veryfinefirstaid", "GFX\items\eyedrops.b3d", "GFX\items\INVbottle.png", "", 0.002, "GFX\items\bottle.png") ;Strange Bottle
	
	it = CreateItemTemplate("Противогаз", "gasmask", "GFX\items\gasmask.b3d", "GFX\items\INVgasmask.png", "", 0.02) : it\sound = 2 ;Gas Mask
	it = CreateItemTemplate("Противогаз", "supergasmask", "GFX\items\gasmask.b3d", "GFX\items\INVgasmask.png", "", 0.021) : it\sound = 2 ;Gas Mask
	it = CreateItemTemplate("Тяжёлый противогаз", "gasmask3", "GFX\items\gasmask.b3d", "GFX\items\INVgasmask.png", "", 0.021) : it\sound = 2 ;Heavy Gas Mask
	
	it = CreateItemTemplate("Оригами", "misc", "GFX\items\origami.b3d", "GFX\items\INVorigami.png", "", 0.003) : it\sound = 0 ;Origami
	
	CreateItemTemplate("Электронные компоненты", "misc", "GFX\items\electronics.x", "GFX\items\INVelectronics.png", "", 0.0011) ;Electronical components
	
	it = CreateItemTemplate("Металлическая панель", "scp148", "GFX\items\metalpanel.x", "GFX\items\INVmetalpanel.png", "", RoomScale) : it\sound = 2 ;Metal Panel
	it = CreateItemTemplate("Слиток SCP-148", "scp148ingot", "GFX\items\scp148.x", "GFX\items\INVscp148.png", "", RoomScale) : it\sound = 2;SCP-148 Ingot
	
	CreateItemTemplate("Навигатор S-NAV 300", "nav", "GFX\items\navigator.x", "GFX\items\INVnavigator.png", "GFX\items\navigator.png", 0.0008) ;S-NAV 300 Navigator
	CreateItemTemplate("Навигатор S-NAV", "nav", "GFX\items\navigator.x", "GFX\items\INVnavigator.png", "GFX\items\navigator.png", 0.0008) ;S-NAV Navigator
	CreateItemTemplate("Навигатор S-NAV Ultimate", "nav", "GFX\items\navigator.x", "GFX\items\INVnavigator.png", "GFX\items\navigator.png", 0.0008) ;S-NAV Navigator Ultimate
	CreateItemTemplate("Навигатор S-NAV 310", "nav", "GFX\items\navigator.x", "GFX\items\INVnavigator.png", "GFX\items\navigator.png", 0.0008) ;S-NAV 310 Navigator
	
	CreateItemTemplate("Рация", "radio", "GFX\items\radio.x", "GFX\items\INVradio.png", "GFX\items\radioHUD.png", 1.0);0.0010) ;Radio Transceiver
	CreateItemTemplate("Рация", "fineradio", "GFX\items\radio.x", "GFX\items\INVradio.png", "GFX\items\radioHUD.png", 1.0) ;Radio Transceiver
	CreateItemTemplate("Рация", "veryfineradio", "GFX\items\radio.x", "GFX\items\INVradio.png", "GFX\items\radioHUD.png", 1.0) ;Radio Transceiver
	CreateItemTemplate("Рация", "18vradio", "GFX\items\radio.x", "GFX\items\INVradio.png", "GFX\items\radioHUD.png", 1.02) ;Radio Transceiver
	
	it = CreateItemTemplate("Сигарета", "cigarette", "GFX\items\scp420J.x", "GFX\items\INVscp420J.png", "", 0.0004) : it\sound = 2 ;Cigarette
	
	it = CreateItemTemplate("Самокрутка", "scp420s", "GFX\items\scp420J.x", "GFX\items\INVscp420J.png", "", 0.0004) : it\sound = 2 ;Joint
	
	it = CreateItemTemplate("Пахучая самокрутка", "scp420s", "GFX\items\scp420J.x", "GFX\items\INVscp420J.png", "", 0.0004) : it\sound = 2 ;Smelly Joint
	
	it = CreateItemTemplate("Оторванная рука", "hand", "GFX\items\severedhand.b3d", "GFX\items\INVhand.png", "", 0.04) : it\sound = 2 ;Severed Hand
	it = CreateItemTemplate("Чёрная оторванная рука", "hand2", "GFX\items\severedhand.b3d", "GFX\items\INVhand2.png", "", 0.04, "GFX\items\shand2.png") : it\sound = 2 ;Black Severed Hand
	
	CreateItemTemplate("9V-батарейка", "bat", "GFX\items\battery.x", "GFX\items\INVbattery9v.png", "", 0.008) ;9V Battery
	CreateItemTemplate("18V-батарейка", "18vbat", "GFX\items\battery.x", "GFX\items\INVbattery18v.png", "", 0.01, "GFX\items\battery_18V.png") ;18V Battery
	CreateItemTemplate("Странная батарейка", "killbat", "GFX\items\battery.x", "GFX\items\INVbattery22900.png", "", 0.01,"GFX\items\strange_battery.png") ;Strange Battery
	
	CreateItemTemplate("Глазные капли", "fineeyedrops", "GFX\items\eyedrops.b3d", "GFX\items\INVeyedrops.png", "", 0.0012, "GFX\items\eyedrops.png") ;Eyedrops
	CreateItemTemplate("Глазные капли", "supereyedrops", "GFX\items\eyedrops.b3d", "GFX\items\INVeyedrops.png", "", 0.0012, "GFX\items\eyedrops.png") ;Eyedrops
	CreateItemTemplate("Глазные капли Зрение+", "eyedrops","GFX\items\eyedrops.b3d", "GFX\items\INVeyedrops.png", "", 0.0012, "GFX\items\eyedrops.png") ;ReVision Eyedrops
	CreateItemTemplate("Красные глазные капли Зрение+", "eyedrops2", "GFX\items\eyedrops.b3d", "GFX\items\INVeyedropsred.png", "", 0.0012,"GFX\items\eyedrops_red.png") ;RedVision Eyedrops
	
	it = CreateItemTemplate("SCP-714", "scp714", "GFX\items\scp714.b3d", "GFX\items\INVscp714.png", "", 0.3)
	it\sound = 3
	
	it = CreateItemTemplate("SCP-1025", "scp1025", "GFX\items\scp1025.b3d", "GFX\items\INVscp1025.png", "", 0.1)
	it\sound = 0
	
	it = CreateItemTemplate("SCP-513", "scp513", "GFX\items\scp513.x", "GFX\items\INVscp513.png", "", 0.1)
	it\sound = 2
	
	;BoH items (ported to original game)
	
	it = CreateItemTemplate("Планшет", "clipboard", "GFX\items\clipboard.b3d", "GFX\items\INVclipboard.png", "", 0.003, "", "GFX\items\INVclipboard2.png", 1) ;Clipboard
	
	it = CreateItemTemplate("SCP-1123", "scp1123", "GFX\items\scp1123.b3d", "GFX\items\INVscp1123.png", "", 0.015) : it\sound = 2
		
	it = CreateItemTemplate("Очки ночного виденья", "supernv", "GFX\items\NVG.b3d", "GFX\items\INVsupernightvision.png", "", 0.02) : it\sound = 2 ;Night Vision Goggles
	it = CreateItemTemplate("Очки ночного виденья", "nvgoggles", "GFX\items\NVG.b3d", "GFX\items\INVnightvision.png", "", 0.02) : it\sound = 2 ;Night Vision Goggles
	it = CreateItemTemplate("Очки ночного виденья", "finenvgoggles", "GFX\items\NVG.b3d", "GFX\items\INVveryfinenightvision.png", "", 0.02) : it\sound = 2 ;Night Vision Goggles
	
	it = CreateItemTemplate("Шприц", "syringe", "GFX\items\syringe.b3d", "GFX\items\INVsyringe.png", "", 0.005) : it\sound = 2 ;Syringe
	it = CreateItemTemplate("Шприц", "finesyringe", "GFX\items\syringe.b3d", "GFX\items\INVsyringe.png", "", 0.005) : it\sound = 2 ;Syringe
	it = CreateItemTemplate("Шприц", "veryfinesyringe", "GFX\items\syringe.b3d", "GFX\items\INVsyringe.png", "", 0.005) : it\sound = 2 ;Syringe
	
	;END
	
	;new Items in SCP:CB 1.3 - ENDSHN
	
	it = CreateItemTemplate("SCP-1499","scp1499","GFX\items\scp1499.b3d","GFX\items\INVscp1499.png", "", 0.023) : it\sound = 2
	it = CreateItemTemplate("SCP-1499","super1499","GFX\items\scp1499.b3d","GFX\items\INVscp1499.png", "", 0.023) : it\sound = 2
	CreateItemTemplate("Бейдж Эмили Росс", "badge", "GFX\items\badge.x", "GFX\items\INVbadge.png", "GFX\items\badge1.png", 0.0001, "GFX\items\badge1_tex.png") ;Emily Ross' Badge
	it = CreateItemTemplate("Потерянный ключ", "key", "GFX\items\key.b3d", "GFX\items\INVkey.png", "", 0.001, "GFX\items\key.png","",0,1+2+8) : it\sound = 3 ;Lost Key
	it = CreateItemTemplate("Дисциплинарное слушание DH-S-4137-17092", "oldpaper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\dh.s", 0.003) : it\sound = 0 ;Disciplinary Hearing DH-S-4137-17092
	it = CreateItemTemplate("Монета", "coin", "GFX\items\key.b3d", "GFX\items\INVcoin.png", "", 0.0005, "GFX\items\coin.png","",0,1+2+8) : it\sound = 3 ;Coin
	it = CreateItemTemplate("Билет в кино", "ticket", "GFX\items\key.b3d", "GFX\items\INVticket.png", "GFX\items\ticket.png", 0.002, "GFX\items\tickettexture.png","",0,1+2+8) : it\sound = 0 ;Movie Ticket
	CreateItemTemplate("Старый бейдж", "badge", "GFX\items\badge.x", "GFX\items\INVoldbadge.png", "GFX\items\badge2.png", 0.0001, "GFX\items\badge2_tex.png","",0,1+2+8) ;Old Badge
	
	it = CreateItemTemplate("Четвертак","25ct", "GFX\items\key.b3d", "GFX\items\INVcoin.png", "", 0.0005, "GFX\items\coin.png","",0,1+2+8) : it\sound = 3 ;Quarter
	it = CreateItemTemplate("Кошелёк","wallet", "GFX\items\wallet.b3d", "GFX\items\INVwallet.png", "", 0.0005,"","",1) : it\sound = 2 ;Wallet
	
	it = CreateItemTemplate("SCP-427","scp427","GFX\items\scp427.b3d","GFX\items\INVscp427.png", "", 0.001) : it\sound = 3
	it = CreateItemTemplate("Улучшенная пилюля", "scp500pilldeath", "GFX\items\pill.b3d", "GFX\items\INVscp500pill.png", "", 0.0001) : it\sound = 2 ;Upgraded pill
	EntityColor it\obj,255,0,0
	it = CreateItemTemplate("Пилюля", "pill", "GFX\items\pill.b3d", "GFX\items\INVpill.png", "", 0.0001) : it\sound = 2 ;Pill
	EntityColor it\obj,255,255,255
	
	it = CreateItemTemplate("Заметка", "paper", "GFX\items\note.x", "GFX\items\INVnote2.png", "GFX\items\note682.png", 0.0025) : it\sound = 0 ;Sticky Note
	it = CreateItemTemplate("Проект модульной зоны", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\docMSP.png", 0.003) : it\sound = 0 ;The Modular Site Project
	
	it = CreateItemTemplate("Схема исследовательского сектора-02", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\docmap.png", 0.003) : it\sound = 0 ;Research Sector-02 Scheme
	
	it = CreateItemTemplate("Документ об SCP-427", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc427.png", 0.003) : it\sound = 0 ;Document SCP-427
	
	;END
	
	;----Ultimate Edition Items----
	
	it = CreateItemTemplate("Шприц", "syringeinf", "GFX\items\syringe.b3d", "GFX\items\INVsyringeinf.png", "", 0.005, "GFX\items\syringeinf.png") : it\sound = 2 ;Syringe

    CreateItemTemplate("Ключ-карта 0-го уровня", "key0",  "GFX\items\keycard.x", "GFX\items\INVkey0.png", "", 0.0004,"GFX\items\keycard0.png") ;Level 0 Key Card
    CreateItemTemplate("Ключ-карта 6-го уровня", "key6",  "GFX\items\keycard.x", "GFX\items\INVkey6.png", "", 0.0004,"GFX\items\keycard6.png") ;Level 6 Key Card
    
    it = CreateItemTemplate("Порезанная бумага", "paperstrips", "GFX\items\paperstrips.x", "GFX\items\INVpaperstrips.png", "", 0.003) : it\sound = 0 ;Paper Strips
    it = CreateItemTemplate("Журнал полевого агента #235-001-CO5", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\docO5_1.png", 0.003) : it\sound = 0 ;Field Agent Log #235-001-CO5
    it = CreateItemTemplate("Записки групп интересов", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\docO5_2.png", 0.003) : it\sound = 0 ;Groups Of Interest Log
    it = CreateItemTemplate("Неизвестный документ", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\docphoto.png", 0.003) : it\sound = 0 ;Unknown Document
    it = CreateItemTemplate("Неизвестная записка", "paper", "GFX\items\paper.x", "GFX\items\INVnote.png", "GFX\items\unknownnote.png", 0.003, "GFX\items\notetexture.jpg") : it\sound = 0 ;Unknown Note

    CreateItemTemplate("SCP-215", "scp215", "GFX\items\scp215.b3d", "GFX\items\INVscp215.png", "", 0.022,"","",1)
    CreateItemTemplate("Футляр для очков", "glassescase", "GFX\items\glassescase.b3d", "GFX\items\INVglassescase.png","",0.022,"","",1) ;Glasses Case

    it = CreateItemTemplate("SCP-1033-RU", "scp1033ru", "GFX\items\scp1033ru.b3d", "GFX\items\INVscp1033ru.png", "", 0.7) : it\sound = 3
    
    it = CreateItemTemplate("SCP-207", "scp207", "GFX\items\scp207.b3d","GFX\items\INVscp207.png","",0.14) : it\sound = 5

    it = CreateItemTemplate("Документ об SCP-178", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc178.png", 0.003) : it\sound = 0 ;Document SCP-178
    it = CreateItemTemplate("Документ об SCP-215", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc215.png", 0.003) : it\sound = 0 ;Document SCP-215
    it = CreateItemTemplate("Документ об SCP-198", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc198.png", 0.003) : it\sound = 0 ;Document SCP-198
    it = CreateItemTemplate("Документ об SCP-447", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc447.png", 0.003) : it\sound = 0 ;Document SCP-447

    it = CreateItemTemplate("Инцидент O5-14", "paper", "GFX\items\paper.x", "GFX\items\INVbn.png", "GFX\items\docO5_14.png", 0.003, "GFX\items\BurntNoteTexture.jpg") : it\sound = 0 ;Incident O5-14

    it = CreateItemTemplate("SCP-500", "scp500", "GFX\items\scp500_bottle.b3d","GFX\items\INVscp500bottle.png","",0.05) : it\sound = 2

    it = CreateItemTemplate("SCP-402", "scp402", "GFX\items\scp402.b3d","GFX\items\INVscp402.png","",0.075) : it\sound = 3

    it = CreateItemTemplate("SCP-357", "scp357", "GFX\items\scp357.b3d","GFX\items\INVscp357.png","",0.04) : it\sound = 2

	;END
	
	;----Box Of Horrors Items----
	
	CreateItemTemplate("SCP-005", "scp005", "GFX\items\scp005.b3d", "GFX\items\INVscp005.png", "", 0.0004,"")
    
    it = CreateItemTemplate("Бинокль", "binocular", "GFX\items\binoculars.b3d", "GFX\items\INVbinoculars.png", "", 0.03) : it\sound = 2 ;Binoculars

    it = CreateItemTemplate("Документ об SCP-009", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc009.png", 0.003) : it\sound = 0 ;Document SCP-009
    it = CreateItemTemplate("Документ об SCP-409", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc409.png", 0.003) : it\sound = 0 ;Document SCP-409

    CreateItemTemplate("SCP-178", "scp178", "GFX\items\scp178.b3d", "GFX\items\INVscp178.png", "", 0.02,"","",1)

    it = CreateItemTemplate("Документ об SCP-005", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc005.png", 0.003) : it\sound = 0 ;Document SCP-005
    it = CreateItemTemplate("Записка от Мэйнарда", "paper", "GFX\items\paper.x", "GFX\items\INVnote.png", "GFX\items\maynard005note.png", 0.0025, "GFX\items\notetexture.jpg") : it\sound = 0 ;Note from Maynard

    ;END

    ;----SCP-914 Expansion Items----
	
	it = CreateItemTemplate("Мятный SCP-500-01", "mintscp500pill", "GFX\items\pill.b3d", "GFX\items\INVscp447pill.png", "", 0.0001) : it\sound = 2 ;Minty SCP-500-01
	EntityColor it\obj,0,140,0
	
	it = CreateItemTemplate("Мятная аптечка первой помощи", "mintfirstaid", "GFX\items\firstaid.x", "GFX\items\INVscp447firstaid.png", "", 0.05, "GFX\items\scp447_firstaid_kit.png") ;Minty First Aid Kit
	it = CreateItemTemplate("Мятная маленькая аптечка первой помощи", "mintfinefirstaid", "GFX\items\firstaid.x", "GFX\items\INVscp447firstaid.png", "", 0.03, "GFX\items\scp447_firstaid_kit.png") ;Minty Small First Aid Kit
	it = CreateItemTemplate("Мятная синяя аптечка первой помощи", "mintfirstaid2", "GFX\items\firstaid.x", "GFX\items\INVscp447firstaid2.png", "", 0.03, "GFX\items\scp447_firstaid_kit2.png") ;Minty Blue First Aid Kit
	it = CreateItemTemplate("Мятная странная бутылка", "mintveryfinefirstaid", "GFX\items\eyedrops.b3d", "GFX\items\INVscp447bottle.png", "", 0.002, "GFX\items\scp447_bottle.png") ;Minty Strange Bottle
		
	CreateItemTemplate("Мятные глазные капли", "mintfineeyedrops", "GFX\items\eyedrops.b3d", "GFX\items\INVscp447eyedrops.png", "", 0.0012, "GFX\items\scp447_eyedrops.png") ;Minty Eyedrops
	CreateItemTemplate("Мятные глазные капли", "mintsupereyedrops", "GFX\items\eyedrops.b3d", "GFX\items\INVscp447eyedrops.png", "", 0.0012, "GFX\items\scp447_eyedrops.png") ;Minty Eyedrops
	CreateItemTemplate("Мятные глазные капли Зрение+", "minteyedrops","GFX\items\eyedrops.b3d", "GFX\items\INVscp447eyedrops.png", "", 0.0012, "GFX\items\scp447_eyedrops.png") ;Minty ReVision Eyedrops
	CreateItemTemplate("Мятные красные глазные капли Зрение+", "minteyedrops2", "GFX\items\eyedrops.b3d", "GFX\items\INVscp447eyedropsred.png", "", 0.0012,"GFX\items\scp447_eyedrops_red.png") ;Minty RedVision Eyedrops
	
	it = CreateItemTemplate("SCP-447", "scp447", "GFX\items\scp447.b3d", "GFX\items\INVscp447.png", "", 0.003) : it\sound = 2
		
	it = CreateItemTemplate("Обезболивающее", "morphine", "GFX\items\syringe.b3d", "GFX\items\INVsyringe.png", "", 0.005, "GFX\items\syringe.png") : it\sound = 2 ;Painkiller
					
	;END
	
	;----Fan Breach Items----
   
    it = CreateItemTemplate("Документ об SCP-1079", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc1079.png", 0.003) : it\sound = 0	;Document SCP-1079
    it = CreateItemTemplate("Документ об SCP-650", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc650.png", 0.003) : it\sound = 0 ;Document SCP-650

    it = CreateItemTemplate("Разъеденный бронежилет", "corrvest", "GFX\items\vest.x", "GFX\items\INVvest.png", "", 0.02,"GFX\items\corrosive_vest.png") ;Corrosive Ballistic Vest
	it\sound = 2
	
	it = CreateItemTemplate("SCP-1079-01", "scp1079sweet", "GFX\items\scp1079.b3d", "GFX\items\INVscp1079.png", "", 0.01, "") : it\sound = 2

    ;END

    ;----Project Resurrection Items----
  
    it = CreateItemTemplate("SCP-1079", "scp1079", "GFX\items\scp1079_packet.b3d","GFX\items\INVscp1079P.png","",0.18) : it\sound = 2

    ;END

    ;----Nine Tailed Fox Items----

    it = CreateItemTemplate("Документ об SCP-457 Страница 1/2","paper","GFX\items\paper.x","GFX\items\INVpaper.png","GFX\items\doc457_1.png", 0.003) : it\sound = 0 ;Document SCP-457 Page 1/2
	it = CreateItemTemplate("Документ об SCP-457 Страница 2/2","paper","GFX\items\paper.x","GFX\items\INVpaper.png","GFX\items\doc457_2.png", 0.003) : it\sound = 0 ;Document SCP-457 Page 2/2

    it = CreateItemTemplate("SCP-198","scp198","GFX\items\scp198.b3d","GFX\items\INVscp198.png","",0.04) : it\sound = 2
    
    it = CreateItemTemplate("SCP-109","scp109","GFX\items\scp109.b3d","GFX\items\INVscp109.png","",0.0009) : it\sound = 5

    it = CreateItemTemplate("Документ об SCP-109", "paper", "GFX\items\paper.x", "GFX\items\INVpaper.png", "GFX\items\doc109.png", 0.003) : it\sound = 0 ;Document SCP-109

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
	CatchErrors("Uncaught (CreateItem)")
	
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
	
	CatchErrors("CreateItem")
	Return i
End Function

Function RemoveItem(i.Items)
	CatchErrors("Uncaught (RemoveItem)")
	Local n
	FreeEntity(i\model) : FreeEntity(i\collider) : i\collider = 0
	
	For n% = 0 To MaxItemAmount - 1
		If Inventory(n) = i
			DebugLog "Removed "+i\itemtemplate\name+" from slot "+n
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
				Wearing714 = False
			Case "scp1499","super1499"
				I_1499\Using = False
			Case "scp427"
				I_427\Using = False
			;MOD
			Case "scp447", "mintscp500pill", "mintfirstaid", "mintfirstaid2", "mintfinefirstaid", "mintveryfinefirstaid", "minteyedrops", "mintfineeyedrops", "mintsupereyedrops", "minteyedrops2"
			    I_447\Using = 0
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
	
	CatchErrors("RemoveItem")
End Function


Function UpdateItems()
	CatchErrors("Uncaught (UpdateItems)")
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
				
				If EntityY(i\collider) < - 35.0 Then DebugLog "remove: " + i\itemtemplate\name:RemoveItem(i):deletedItem=True
			Else
				HideEntity i\collider
			EndIf
		Else
			i\DropSpeed = 0
			i\xspeed = 0.0
			i\zspeed = 0.0
		EndIf
		
		If Not deletedItem Then
			CatchErrors(Chr(34)+i\itemtemplate\name+Chr(34)+" item")
		EndIf
		deletedItem = False
	Next
	
	If ClosestItem <> Null Then
		;DrawHandIcon = True
		
		If MouseHit1 Then PickItem(ClosestItem)
	EndIf
	CatchErrors("UpdateItems")
End Function

Function PickItem(item.Items)
	Local n% = 0
	Local canpickitem = True
	Local fullINV% = True
	
	For n% = 0 To MaxItemAmount - 1
		If Inventory(n)=Null
			fullINV = False
			Exit
		EndIf
	Next
	
	If WearingHazmat > 0 Then
		Msg = "Вы не можете подбирать предметы во время ношения защитного костюма." ;You cannot pick up any items while wearing a hazmat suit.
		MsgTimer = 70*5
		Return
	EndIf
	
	CatchErrors("Uncaught (PickItem)")
	If (Not fullINV) Then
		For n% = 0 To MaxItemAmount - 1
			If Inventory(n) = Null Then
				Select item\itemtemplate\tempname
					Case "scp1123"
						If Not (Wearing714 = 1) Then
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
					Case "scp447", "mintscp500", "mintfirstaid", "mintfirstaid2", "mintfinefirstaid", "mintveryfinefirstaid", "minteyedrops", "mintfineeyedrops", "mintsupereyedrops"
						I_447\Using = 1
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
						SetAnimTime item\model,19.0
					Case "glassescase"
					    SetAnimTime item\model,19.0
					Case "scp215"
					    SetAnimTime item\model,1.0
					Case "key6"
				        GiveAchievement(AchvKeyCard6)
				    Case "scp005"
				   	    GiveAchievement(Achv005)
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
		                    Damage1033RU(30+(5*SelectedDifficulty\aggressiveNPCs))
                        EndIf
                    Case "scp109", "syringe", "syringeinf", "finesyringe", "veryfinesyringe", "cup", "morphine", "scp207", "cola", "flask", "minteyedrops", "minteyedrops2", "mintfineeyedrops", "mintsupereyedrops"
					    If I_402\Timer > 0 Then
					        PlaySound_Strict(HorrorSFX(Rand(0,3)))
					        Msg = Chr(34)+"Я... не... могу..."+Chr(34) ;I... Can't...
					        MsgTimer = 70*6
					        Exit
					    EndIf
					Case "scp357"
                        GiveAchievement(Achv357)
                        Msg = "Вы взяли SCP-357 в руку." ;You grabbed the SCP-357 in your hand.
                        MsgTimer = 70*6
                        I_357\Timer = 1.0
                    Case "corrvest"
						Msg = Chr(34)+"Я не настолько глуп, чтобы подбирать это."+Chr(34) ;I'm not stupid enough to pick it up.
						MsgTimer = 70*6
						Exit
					Case "scp207"
					    GiveAchievement(Achv207)
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
	CatchErrors("PickItem")
End Function

Function DropItem(item.Items,playdropsound%=True)
	If WearingHazmat > 0 Then
		Msg = "Вы не можете выбрасывать предметы во время ношения защитного костюма." ;You cannot drop any items while wearing a hazmat suit.
		MsgTimer = 70*5
		Return
	EndIf
	
	CatchErrors("Uncaught (DropItem)")
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
			Wearing714 = False
		Case "scp1499","super1499"
			I_1499\Using = False
		Case "scp427"
			I_427\Using = False
		;MOD
		Case "scp178"
		    I_178\Using = False
		Case "scp447", "mintscp500pill", "mintfirstaid", "mintfirstaid2", "mintfinefirstaid", "mintveryfinefirstaid", "minteyedrops", "mintfineeyedrops", "mintsupereyedrops", "minteyedrops2"
			I_447\Using = 0
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
	
	CatchErrors("DropItem")
	
End Function

;Update any ailments inflicted by SCP-294 drinks.
Function Update294()
	CatchErrors("Uncaught (Update294)")
	
	Local fs.FPS_Settings = First FPS_Settings
	
	If CameraShakeTimer > 0 Then
		CameraShakeTimer = CameraShakeTimer - (fs\FPSfactor[0]/70)
		CameraShake = 2
	EndIf
	
	If VomitTimer > 0 Then
		DebugLog VomitTimer
		VomitTimer = VomitTimer - (fs\FPSfactor[0]/70)
		
		If (MilliSecs2() Mod 1600) < Rand(200, 400) Then
			If BlurTimer = 0 Then BlurTimer = Rnd(10, 20)*70
			CameraShake = Rnd(0, 2)
		EndIf
		
;		If (MilliSecs2() Mod 1000) < Rand(1200) Then 
		
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
	
	CatchErrors("Update294")
End Function

