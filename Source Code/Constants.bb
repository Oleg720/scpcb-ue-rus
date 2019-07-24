;ALL CONSTANTS OF THE MOD

;MOD

;[FONTS]

Const MaxFontAmount = 5
Const MaxCreditsFontAmount = 2

;END

;[COLLISIONS]

Const HIT_MAP% = 1
Const HIT_PLAYER% = 2
Const HIT_ITEM% = 3
Const HIT_APACHE% = 4
Const HIT_178% = 5
Const HIT_DEAD% = 6

;[?]

Const INFINITY# = (999.0) ^ (99999.0)
Const NAN# = (-1.0) ^ (0.5)

Const ClrR = 50
Const ClrG = 50
Const ClrB = 50

;[TEXTURES]

Const MaxDTextures = 17

;MOD

Const MaxDecalTextureIDAmount = 24
Const MaxOtherTextureIDAmount = 15
Const MaxParticleTextureIDAmount = 10
Const MaxLightSpriteTextureIDAmount = 11
Const MaxOverlayIDAmount = 16
Const MaxOverlayTextureIDAmount = 22


;[OBJECTS]

Const MaxOBJTunnelAmount = 7
Const MaxMonitorAmount = 3
Const MaxDoorIDAmount = 11
Const MaxButtonIDAmount = 4
Const MaxLeverIDAmount = 2
Const MaxCamIDAmount = 2
Const MaxOtherModelsIDAmount = 2

;END

;[FOREST GENERATION]

Const gridsize% = 10
Const deviation_chance% = 40 ;out of 100
Const branch_chance% = 65
Const branch_max_life% = 4
Const branch_die_chance% = 18
Const max_deviation_distance% = 3
Const return_chance% = 27
Const center = 5 ;(gridsize-1) / 2

;[MAP]

Const MaxRoomLights% = 32
Const MaxRoomEmitters% = 8
Const MaxRoomObjects% = 30

Const ROOM1% = 1
Const ROOM2% = 2
Const ROOM2C% = 3
Const ROOM3% = 4
Const ROOM4% = 5

Const ZONEAMOUNT = 3

Const gridsz% = 19 ;Same size as the main map itself (better for the map creator)

;MOD

;[3D MENU]

Const MaxRenderingObjAmount = 3
Const MaxSpriteAmount = 3
Const MaxMiscAmount = 11

Const MaxMenuLightsAmount = 11
Const MaxMenuLightSpritesAmount = 11
Const MaxMenuLightSprites2Amount = 11

;[PLAYER]

Const SubjectName$ = "Субъект D-9341"
Const SubjectName2$ = "Субъекта D-9341" ;Вариант в Родительном падеже

;END

;[ACHIEVEMENTS]

Const MAXACHIEVEMENTS = 58

Const Achv008% = 0, Achv012% = 1, Achv035% = 2, Achv049% = 3, Achv055 = 4,  Achv079% = 5, Achv096% = 6, Achv106% = 7, Achv148% = 8
Const Achv205 = 9, Achv294% = 10, Achv372% = 11, Achv420% = 12, Achv427 = 13, Achv500% = 14, Achv513% = 15, Achv714% = 16
Const Achv789% = 17, Achv860% = 18, Achv895% = 19, Achv914% = 20, Achv939% = 21, Achv966% = 22, Achv970 = 23
Const Achv1025% = 24, Achv1048 = 25, Achv1123 = 26, Achv1162% = 27, Achv1499% = 28

Const AchvConsole% = 29, AchvHarp% = 30, AchvKeter% = 31, AchvMaynard% = 32, AchvOmni% = 33
Const AchvPD% = 34, AchvSNAV% = 35, AchvTesla% = 36

;MOD

Const Achv005% = 37, Achv009% = 38, Achv109% = 39, Achv178% = 40, Achv198% = 41, Achv207% = 42, Achv215% = 43, Achv357% = 44
Const Achv402% = 45, Achv409% = 46, Achv447% = 47, Achv457% = 48, Achv650% = 49, Achv1033RU% = 50, Achv1079% = 51

Const AchvDuck% = 52, AchvGears% = 53, AchvMTF% = 54, AchvO5% = 55, AchvKeyCard6% = 56, AchvThaumiel% = 57
;END


;[NPCs]

Const MaxNPCModelIDAmount = 35

Const NPCtype008_1% = 1, NPCtype035_Tentacle% = 2, NPCtype049% = 3, NPCtype049_2% = 4, NPCtype066% = 5
Const NPCtype096% = 6, NPCtype106% = 7, NPCtype173% = 8, NPCtype372% = 9, NPCtype513_1% = 10, NPCtype860_2% = 11
Const NPCtype939% = 12, NPCtype966% = 13, NPCtype1048_A% = 14, NPCtype1499_1% = 15

Const NPCtypeApache% = 16, NPCtypeClerk% = 17, NPCtypeD% = 18
Const NPCtypeGuard% = 19, NPCtypeMTF% = 20, NPCtypePdPlane% = 21

;MOD

Const NPCtype008_2% = 22, NPCtype049_3% = 23, NPCtype178_1% = 24, NPCtype457% = 25, NPCtype650% = 26

Const NPCtypeCI% = 27, NPCtypeMTF2% = 28


;END

;[DIFFICULTY]

Const SAFE = 0
Const EUCLID = 1
Const KETER = 2
;MOD
Const THAUMIEL = 3
;END
Const CUSTOM = 4

Const SAVEANYWHERE = 0, SAVEONQUIT = 1, SAVEONSCREENS = 2, NOTSAVE = 3

Const EASY = 0, NORMAL = 1, HARD = 2

;[FMOD]

Const Freq = 44100 ;Hz
Const Channels = 64	;Standartwert
Const Flags	= 0
Const Mode	= 2	;Mode = 2 means that the sounds play in a loop
Const F_Offset = 0
Const Lenght = 0
Const MaxVol = 255
Const MinVol = 0
Const PanLeft = 0
Const PanRight = 255
Const PanMid = -1
Const AllChannel = -3
Const FreeChannel = -1

;[FULLSCREEN_FIX]

Const C_GWL_STYLE = -16
Const C_WS_POPUP = $80000000
Const C_HWND_TOP = 0
Const C_SWP_SHOWWINDOW = $0040

;[MUSIC]

Const MusicPath$ = "SFX\Music\"
Const MusicPath2$ = "SFX\Radio\UserTracks\"

;[VERSIONS]

Const GameVersionNumber$ = "1.3.11"
Const ModVersionNumber$ = "5.4.1 [Rus vPre-1.0]"

;[OPTIONS]

Const OptionFile$ = "Source Code\options.ini"

;[SAVE]

Const SavePath$ = "Saves\"

