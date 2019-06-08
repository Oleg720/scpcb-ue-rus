Type Difficulty
	Field name$
	Field description$
	Field permaDeath%
	Field aggressiveNPCs
	Field saveType%
	Field otherFactors%
	Field menu%
	
	Field r%
	Field g%
	Field b%
	
	Field customizable%
	Field twoslots%
End Type

Dim difficulties.Difficulty(4)

Global SelectedDifficulty.Difficulty

difficulties(SAFE) = New Difficulty
difficulties(SAFE)\menu = True
difficulties(SAFE)\name = "���������" ;Safe
difficulties(SAFE)\description ="����� ��������� ���� � ����� �����. ������, ��� � � ��������� SCP ����� ������, ���������� ������� ��������� �� ��������, ��� ����� ������������." ;The game can be saved any time. However, as in the case of SCP Objects, a Safe classification does not mean that handling it does not pose a threat.
difficulties(SAFE)\permaDeath = False
difficulties(SAFE)\aggressiveNPCs = False
difficulties(SAFE)\saveType = SAVEANYWHERE
difficulties(SAFE)\otherFactors = EASY
difficulties(SAFE)\twoslots = False
difficulties(SAFE)\r = 120
difficulties(SAFE)\g = 150
difficulties(SAFE)\b = 50

difficulties(EUCLID) = New Difficulty
difficulties(EUCLID)\menu = True
difficulties(EUCLID)\name = "������" ;Euclid
difficulties(EUCLID)\description = "�� ��������� ������ ���������� �������� ������ ����� ���������� ������� �����������. " ;In Euclid difficulty, saving is only allowed at specific locations marked by lit up computer screens. 
difficulties(EUCLID)\description = difficulties(EUCLID)\description +"� �������� ������ ������ ��������� ������������ ��������� ��� ��������������� ��������, ������� ���������� ������� �� ������ ��������." ;Euclid-class objects are inherently unpredictable, so that reliable containment is not always possible.
difficulties(EUCLID)\permaDeath = False
difficulties(EUCLID)\aggressiveNPCs = False
difficulties(EUCLID)\saveType = SAVEONSCREENS
difficulties(EUCLID)\otherFactors = NORMAL
difficulties(EUCLID)\twoslots = False
difficulties(EUCLID)\r = 200
difficulties(EUCLID)\g = 200
difficulties(EUCLID)\b = 0

difficulties(KETER) = New Difficulty
difficulties(KETER)\menu = True
difficulties(KETER)\name = "�����" ;Keter
difficulties(KETER)\description = "������� ������ ����� - ��������, ������� ��������� ��������� � ������������ ��������� ��� ����������� �����. " ;Keter-class objects are considered the most dangerous ones in Foundation containment. 
difficulties(KETER)\description = difficulties(KETER)\description +"� �������� ������ ������ ��������� ������������ ��������� ��� ��������������� ��������, ������� ���������� ������� �� ������ ��������." ;Euclid-class objects are inherently unpredictable, so that reliable containment is not always possible.
difficulties(KETER)\permaDeath = True
difficulties(KETER)\aggressiveNPCs = True
difficulties(KETER)\saveType = SAVEONQUIT
difficulties(KETER)\otherFactors = HARD
difficulties(KETER)\twoslots = False
difficulties(KETER)\r = 200
difficulties(KETER)\g = 0
difficulties(KETER)\b = 0

difficulties(THAUMIEL) = New Difficulty
difficulties(THAUMIEL)\menu = False
difficulties(THAUMIEL)\name = "��������" ;Thaumiel
difficulties(THAUMIEL)\description = "������� ������ �������� - ��������, ������� ���� ���������� ��� ����������� ��� ��������������� ������ ���������. ���� ���������� � ������������� �������� ������ �������� �������� ��������� " ;Thaumiel-class SCPs are anomalies that the Foundation uses to contain or counteract other SCPs or anomalous phenomena. Even the mere existence of Thaumiel-class objects 
difficulties(THAUMIEL)\description = difficulties(THAUMIEL)\description +"� �������� ������ ������������������ ����������� �����. ������� ���� ����������������" ;is classified at the highest levels of the Foundation. Take your precautions.
difficulties(THAUMIEL)\permaDeath = True
difficulties(THAUMIEL)\aggressiveNPCs = True
difficulties(THAUMIEL)\saveType = SAVEONSCREENS
difficulties(THAUMIEL)\otherFactors = HARD
difficulties(THAUMIEL)\twoslots = True
difficulties(THAUMIEL)\r = 100
difficulties(THAUMIEL)\g = 100
difficulties(THAUMIEL)\b = 100

difficulties(CUSTOM) = New Difficulty
difficulties(CUSTOM)\name = "����" ;Custom
difficulties(CUSTOM)\menu = True
difficulties(CUSTOM)\permaDeath = False
difficulties(CUSTOM)\aggressiveNPCs = True
difficulties(CUSTOM)\saveType = SAVEANYWHERE
difficulties(CUSTOM)\customizable = True
difficulties(CUSTOM)\otherFactors = EASY
difficulties(CUSTOM)\twoslots = False
difficulties(CUSTOM)\r = 255
difficulties(CUSTOM)\g = 255
difficulties(CUSTOM)\b = 255

SelectedDifficulty = difficulties(SAFE)
		
