;ALL CONSTANTS OF THE MOD

;[FONTS]

Const MaxFontAmount = 5
Const MaxCreditsFontAmount = 2

;[COLLISIONS]

Const HIT_MAP% = 1
Const HIT_PLAYER% = 2
Const HIT_ITEM% = 3
Const HIT_APACHE% = 4
Const HIT_178% = 5
Const HIT_DEAD% = 6
Const HIT_LADDER% = 7

;[?]

Const INFINITY# = (999.0) ^ (99999.0)
Const NAN# = (-1.0) ^ (0.5)

;[TEXTURES FOR CLASS-D MODEL]

Const MaxDTextures = 16

;[OBJECTS]

Const MaxOBJTunnelAmount = 7
Const MaxMonitorAmount = 3
Const MaxDoorIDAmount = 10
Const MaxButtonIDAmount = 4
Const MaxLeverIDAmount = 2
Const MaxCamIDAmount = 2
Const MaxOtherModelsIDAmount = 2

;[FOREST]

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

;[3D MENU]

Const MaxRenderingObjAmount = 3
Const MaxSpriteAmount = 3
Const MaxMiscAmount = 11

Const MaxMenuLightsAmount = 11
Const MaxMenuLightSpritesAmount = 11
Const MaxMenuLightSprites2Amount = 11

;[PLAYER]

Const SubjectName$ = "Субъект D-9341"

;[ACHIEVEMENTS]

Const MAXACHIEVEMENTS = 57

Const Achv008% = 0, Achv012% = 1, Achv035% = 2, Achv049% = 3, Achv055 = 4,  Achv079% = 5, Achv096% = 6, Achv106% = 7, Achv148% = 8
Const Achv205 = 9, Achv294% = 10, Achv372% = 11, Achv420% = 12, Achv427=13, Achv500% = 14, Achv513% = 15, Achv714% = 16
Const Achv789% = 17, Achv860% = 18, Achv895% = 19, Achv914% = 20, Achv939% = 21, Achv966% = 22, Achv970 = 23
Const Achv1025% = 24, Achv1048 = 25, Achv1123 = 26, AchvMaynard% = 27, AchvHarp% = 28, AchvSNAV% = 29, AchvOmni% = 30
Const AchvConsole% = 31, AchvTesla% = 32, AchvPD% = 33, Achv1162% = 34, Achv1499% = 35, AchvKeter% = 36

;MODS

Const Achv005% = 37, Achv009% = 38, Achv109% = 39, Achv178% = 40, Achv198% = 41, Achv207% = 42, Achv215% = 43, Achv357% = 44
Const Achv402% = 45, Achv409% = 46, Achv447% = 47, Achv457% = 48, Achv650% = 49, Achv1033RU% = 50, Achv1079% = 51
Const AchvDuck% = 52, AchvMTF% = 53, AchvO5% = 54, AchvKeyCard6% = 55, AchvThaumiel% = 56

;END

;[NPCs]

Const MaxNPCModelIDAmount = 35

Const NPCtype173% = 1, NPCtypeOldMan% = 2, NPCtypeGuard% = 3, NPCtypeD% = 4
Const NPCtype372% = 5, NPCtypeApache% = 6, NPCtypeMTF% = 7, NPCtype096% = 8
Const NPCtype049% = 9, NPCtypeZombie% = 10, NPCtype5131% = 11, NPCtypeTentacle% = 12
Const NPCtype860% = 13, NPCtype939% = 14, NPCtype066% = 15, NPCtypePdPlane% = 16
Const NPCtype966% = 17, NPCtype1048a = 18, NPCtype1499% = 19, NPCtype008% = 20, NPCtypeClerk% = 21

;MODS

Const NPCtype0083% = 22, NPCtype650% = 23, NPCtype457% = 24, NPCtypeZombie2% = 25, NPCtype0082% = 26
Const NPCtype178% = 27, NPCtypeMTF2% = 28, NPCtypeCI% = 29

;END

;[DIFFICULTY]

Const SAFE = 0, EUCLID = 1, KETER = 2, THAUMIEL = 3, CUSTOM = 4

Const SAVEANYWHERE = 0, SAVEONQUIT = 1, SAVEONSCREENS = 2, NOTSAVE = 3

Const EASY = 0, NORMAL = 1, HARD = 2

;[FMOD]

Const Freq = 44100	;Hz
Const Channels = 64		;Standartwert
Const Flags	= 0
Const Mode	= 2		;Mode = 2 means that the sounds play in a loop
Const F_Offset = 0
Const Lenght = 0
Const MaxVol = 255
Const MinVol = 0
Const PanLeft = 0
Const PanRight = 255
Const PanMid = -1
Const AllChannel = -3
Const FreeChannel = -1

;[DECLARE WINDOWS API]

Const C_GWL_STYLE = -16
Const C_WS_POPUP = $80000000
Const C_HWND_TOP = 0
Const C_SWP_SHOWWINDOW = $0040

;[MUSIC]

Const MusicPath$ = "SFX\Music\"
Const MusicPath2$ = "SFX\Radio\UserTracks\"

;[VERSIONS]

Const GameVersionNumber$ = "1.3.11"
Const ModVersionNumber$ = "5.4 [Rus v0.1-TEST]" 
Const CompatibleNumber$ = "5.4"

;[OPTIONS]

Const OptionFile$ = "options.ini"

;[TEXTURES]

Const MaxDecalTextureIDAmount = 24
Const MaxOtherTextureIDAmount = 15
Const MaxParticleTextureIDAmount = 10
Const MaxLightSpriteTextureIDAmount = 11
Const MaxOverlayIDAmount = 16
Const MaxOverlayTextureIDAmount = 22

