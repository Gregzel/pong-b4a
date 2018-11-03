Type=Activity
Version=2.5
@EndOfDesignText@
#Region Module Attributes
	#FullScreen: True
	#IncludeTitle: False
#End Region

'Activity module
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	
	Dim SeekBar1Val As Int
	Dim SeekBar2Val As Int
	Dim SeekBar3Val As Int
	Dim SeekBar4Val As Int
'	Dim SeekBar5Val As Int
'	Dim SeekBar6Val As Int
'	Dim SeekBar7Val As Int
'	Dim SeekBar8Val As Int

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Dim TimerSettings As Timer
	
	Dim GeriButton As Button
	Dim SeekBar1 As SeekBar
	Dim SeekBar2 As SeekBar
	Dim SeekBar3 As SeekBar
	Dim SeekBar4 As SeekBar

	Dim Label1 As Label
	Dim Label2 As Label
	Dim Label3 As Label
	Dim label4 As Label
	Dim Label5 As Label
	Dim ButtonReset As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("pingpong2settingslayout")
	TimerSettings.Initialize("TimerSettings",2000)
	TimerSettings.Enabled = True
End Sub

Sub Activity_Resume
	SeekBar1.Value = KarsilikliOyna.SeekBar1Val
	SeekBar2.Value = KarsilikliOyna.SeekBar2Val
	SeekBar3.Value = KarsilikliOyna.SeekBar3Val
	SeekBar4.Value = KarsilikliOyna.SeekBar4Val
		
	Label1.Text = SeekBar1.Value
	Label1.Text = SeekBar1.Value
	Label1.Text = SeekBar1.Value
	Label1.Text = SeekBar1.Value
				

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub TimerSettings_Tick
	Label5.Text = "Ayarlar"
End Sub

Sub GeriButton_Click
	Activity.Finish
End Sub

Sub SeekBar1_ValueChanged (Value As Int, UserChanged As Boolean)
	KarsilikliOyna.SeekBar1Val = SeekBar1.Value
	CpuOyna.SeekBar1Val = SeekBar1.Value
	CpuCpuOyna.SeekBar1Val = SeekBar1.Value
	Label5.Text = SeekBar1.Value
	Label1.Text = SeekBar1.Value
End Sub

Sub SeekBar3_ValueChanged (Value As Int, UserChanged As Boolean)
	KarsilikliOyna.SeekBar3Val = SeekBar3.Value
	CpuOyna.SeekBar3Val = SeekBar3.Value
	CpuCpuOyna.SeekBar3Val = SeekBar3.Value
	Label5.Text = SeekBar3.Value
	Label3.Text = SeekBar3.Value
End Sub
Sub SeekBar2_ValueChanged (Value As Int, UserChanged As Boolean)
	KarsilikliOyna.SeekBar2Val = SeekBar2.Value
	CpuOyna.SeekBar2Val = SeekBar2.Value
	CpuCpuOyna.SeekBar2Val = SeekBar2.Value
	Label5.Text = SeekBar2.Value
	Label2.Text = SeekBar2.Value
End Sub

Sub SeekBar4_ValueChanged (Value As Int, UserChanged As Boolean)
	KarsilikliOyna.SeekBar4Val = SeekBar4.Value
	CpuOyna.SeekBar4Val = SeekBar4.Value
	CpuCpuOyna.SeekBar4Val = SeekBar4.Value
	Label5.Text = SeekBar4.Value
	label4.Text = SeekBar4.Value
End Sub

Sub Label5_Click
	
End Sub
Sub Label5_LongClick
	
End Sub
Sub ButtonReset_Click
	SeekBar1.Value = 50dip
	SeekBar2.Value = 100
	SeekBar3.Value = 25
	SeekBar4.Value = 10dip
End Sub
Sub ButtonReset_LongClick
	
End Sub