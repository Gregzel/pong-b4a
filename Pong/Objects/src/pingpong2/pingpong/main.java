package pingpong2.pingpong;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = true;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "pingpong2.pingpong", "pingpong2.pingpong.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, true))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "pingpong2.pingpong", "pingpong2.pingpong.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "pingpong2.pingpong.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEvent(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return main.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        Object[] o;
        if (permissions.length > 0)
            o = new Object[] {permissions[0], grantResults[0] == 0};
        else
            o = new Object[] {"", false};
        processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static int _seekbar1val = 0;
public static int _seekbar2val = 0;
public static int _seekbar3val = 0;
public static int _seekbar4val = 0;
public static anywheresoftware.b4a.objects.GameViewWrapper.BitmapData _smiley = null;
public static anywheresoftware.b4a.objects.GameViewWrapper.BitmapData _raket1 = null;
public static anywheresoftware.b4a.objects.GameViewWrapper.BitmapData _raket2 = null;
public static anywheresoftware.b4a.objects.GameViewWrapper.BitmapData _backgroundbitmap = null;
public static int _vx = 0;
public static int _vy = 0;
public static int _player1skor = 0;
public static int _player2skor = 0;
public static int _player1skoro = 0;
public static int _player2skoro = 0;
public static int _temp1 = 0;
public static int _temp2 = 0;
public static int _temp3 = 0;
public static int _temp4 = 0;
public static int _loadid1 = 0;
public static int _loadid2 = 0;
public static int _loadid3 = 0;
public static int _loadid4 = 0;
public static int _loadid5 = 0;
public static int _loadid6 = 0;
public static int _maxstream = 0;
public anywheresoftware.b4a.audio.SoundPoolWrapper _soundpool1 = null;
public anywheresoftware.b4a.objects.MediaPlayerWrapper _mediaplayer1 = null;
public static float _rate = 0f;
public anywheresoftware.b4a.objects.GameViewWrapper _gv = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper _canvas1 = null;
public anywheresoftware.b4a.agraham.gestures.Gestures _gestures1 = null;
public anywheresoftware.b4a.objects.collections.Map _touchmap = null;
public anywheresoftware.b4a.objects.ButtonWrapper _cikisbutton = null;
public anywheresoftware.b4a.objects.Timer _timer1 = null;
public anywheresoftware.b4a.objects.Timer _timerscore = null;
public static String _exit = "";
public static String _clear = "";
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _activitybackgroundbmp = null;
public static int _raketuzunlugu = 0;
public static int _raketgenisligi = 0;
public static int _tophizix = 0;
public static int _topcapi = 0;
public static int _timerinterval = 0;
public static int _raket1x1 = 0;
public static int _raket1x2 = 0;
public static int _raket1y1 = 0;
public static int _raket1y2 = 0;
public static int _raket2x1 = 0;
public static int _raket2x2 = 0;
public static int _raket2y1 = 0;
public static int _raket2y2 = 0;
public static int _xekseni = 0;
public static int _yekseni = 0;
public static int _oyuncesidi = 0;
public anywheresoftware.b4a.objects.LabelWrapper _aynimakina = null;
public anywheresoftware.b4a.objects.LabelWrapper _baslikbutton = null;
public anywheresoftware.b4a.objects.LabelWrapper _bluetooth = null;
public anywheresoftware.b4a.objects.LabelWrapper _cpuile = null;
public anywheresoftware.b4a.objects.LabelWrapper _reserve = null;
public anywheresoftware.b4a.objects.LabelWrapper _cpucpu = null;
public anywheresoftware.b4a.objects.LabelWrapper _player1skorlabel = null;
public anywheresoftware.b4a.objects.LabelWrapper _player2skorlabel = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public static float _rate1 = 0f;
public static float _rate2 = 0f;
public static float _rate3 = 0f;
public static float _rate4 = 0f;
public static float _soundvolume = 0f;
public static int _repeat1 = 0;
public static int _repeat2 = 0;
public static int _repeat3 = 0;
public static int _repeat4 = 0;
public static int _musicvolume = 0;
public static int _sayialanplayer = 0;
public static int _animationcounter1 = 0;
public static int _animationcounter2 = 0;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _checkbox1 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _checkbox2 = null;
public anywheresoftware.b4a.phone.Phone _phone1 = null;
public static boolean _mediaplayercaliyor = false;
public static boolean _sesacik = false;
public static class _point{
public boolean IsInitialized;
public int Id;
public int prevX;
public int prevY;
public int Color;
public void Initialize() {
IsInitialized = true;
Id = 0;
prevX = 0;
prevY = 0;
Color = 0;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 59;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 60;BA.debugLine="Activity.LoadLayout(\"pingpong2mainlayout\")";
mostCurrent._activity.LoadLayout("pingpong2mainlayout",mostCurrent.activityBA);
 //BA.debugLineNum = 61;BA.debugLine="Xekseni = Activity.Height";
_xekseni = mostCurrent._activity.getHeight();
 //BA.debugLineNum = 62;BA.debugLine="Yekseni = Activity.Width";
_yekseni = mostCurrent._activity.getWidth();
 //BA.debugLineNum = 63;BA.debugLine="oyuncesidi = 3  ' CPUVSPLAYER";
_oyuncesidi = (int) (3);
 //BA.debugLineNum = 64;BA.debugLine="oyuncesidi = 2  ' PLAYERVSPLAYER";
_oyuncesidi = (int) (2);
 //BA.debugLineNum = 65;BA.debugLine="oyuncesidi = 1  ' CPUVSCPU";
_oyuncesidi = (int) (1);
 //BA.debugLineNum = 66;BA.debugLine="oyuncesidi = 0  ' none";
_oyuncesidi = (int) (0);
 //BA.debugLineNum = 67;BA.debugLine="TimerInterval = 25";
_timerinterval = (int) (25);
 //BA.debugLineNum = 68;BA.debugLine="RaketUzunlugu = 100";
_raketuzunlugu = (int) (100);
 //BA.debugLineNum = 69;BA.debugLine="RaketGenisligi = 50";
_raketgenisligi = (int) (50);
 //BA.debugLineNum = 70;BA.debugLine="Topcapi = 50dip 'The size (width and height) of t";
_topcapi = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50));
 //BA.debugLineNum = 71;BA.debugLine="TopHiziX = 10dip";
_tophizix = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10));
 //BA.debugLineNum = 72;BA.debugLine="Player1skor = 0";
_player1skor = (int) (0);
 //BA.debugLineNum = 73;BA.debugLine="player2skor = 0";
_player2skor = (int) (0);
 //BA.debugLineNum = 74;BA.debugLine="vx = 10dip";
_vx = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10));
 //BA.debugLineNum = 75;BA.debugLine="vy = 10dip";
_vy = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10));
 //BA.debugLineNum = 76;BA.debugLine="MaxStream = 5";
_maxstream = (int) (5);
 //BA.debugLineNum = 77;BA.debugLine="rate1 = 1.30";
_rate1 = (float) (1.30);
 //BA.debugLineNum = 78;BA.debugLine="repeat1 = 1";
_repeat1 = (int) (1);
 //BA.debugLineNum = 79;BA.debugLine="sayialanplayer = 0";
_sayialanplayer = (int) (0);
 //BA.debugLineNum = 80;BA.debugLine="animationcounter1 = 0";
_animationcounter1 = (int) (0);
 //BA.debugLineNum = 81;BA.debugLine="animationcounter2 = 0";
_animationcounter2 = (int) (0);
 //BA.debugLineNum = 83;BA.debugLine="Mediaplayercaliyor = True";
_mediaplayercaliyor = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 84;BA.debugLine="sesacik = True";
_sesacik = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 86;BA.debugLine="SoundPool1.Initialize(MaxStream)";
mostCurrent._soundpool1.Initialize(_maxstream);
 //BA.debugLineNum = 87;BA.debugLine="LoadId1 = SoundPool1.Load(File.DirAssets, \"raket1";
_loadid1 = mostCurrent._soundpool1.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"raket1ses.wav");
 //BA.debugLineNum = 88;BA.debugLine="LoadId2 = SoundPool1.Load(File.DirAssets, \"beep1.";
_loadid2 = mostCurrent._soundpool1.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"beep1.wav");
 //BA.debugLineNum = 89;BA.debugLine="mediaplayer1.Initialize2(\"mediaplayer1\")";
mostCurrent._mediaplayer1.Initialize2(processBA,"mediaplayer1");
 //BA.debugLineNum = 90;BA.debugLine="mediaplayer1.Load(File.DirAssets, \"popcorn.mid\")";
mostCurrent._mediaplayer1.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"popcorn.mid");
 //BA.debugLineNum = 93;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 382;BA.debugLine="Sub Activity_KeyPress(KeyCode As Int) As Boolean";
 //BA.debugLineNum = 383;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then	' Checks";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 384;BA.debugLine="Activity.LoadLayout(\"pingpong2mainlayout\")";
mostCurrent._activity.LoadLayout("pingpong2mainlayout",mostCurrent.activityBA);
 //BA.debugLineNum = 385;BA.debugLine="timer1.Enabled = False";
mostCurrent._timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 386;BA.debugLine="If soundvolume  = 1 Then";
if (_soundvolume==1) { 
 //BA.debugLineNum = 387;BA.debugLine="CheckBox2.Checked = True";
mostCurrent._checkbox2.setChecked(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 389;BA.debugLine="CheckBox2.Checked = False";
mostCurrent._checkbox2.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 392;BA.debugLine="If mediaplayer1.IsPlaying Then";
if (mostCurrent._mediaplayer1.IsPlaying()) { 
 //BA.debugLineNum = 393;BA.debugLine="CheckBox1.Checked = True";
mostCurrent._checkbox1.setChecked(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 395;BA.debugLine="CheckBox1.Checked = False";
mostCurrent._checkbox1.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 397;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 400;BA.debugLine="Return True		' Return = True  	the Event will be";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 402;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 160;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 161;BA.debugLine="timer1.Enabled = False";
mostCurrent._timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 162;BA.debugLine="mediaplayer1.Pause";
mostCurrent._mediaplayer1.Pause();
 //BA.debugLineNum = 163;BA.debugLine="phone1.SetVolume(phone1.VOLUME_MUSIC,musicvolume";
mostCurrent._phone1.SetVolume(mostCurrent._phone1.VOLUME_MUSIC,_musicvolume,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 164;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 95;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 96;BA.debugLine="If SeekBar1Val = 0 Then";
if (_seekbar1val==0) { 
 //BA.debugLineNum = 97;BA.debugLine="SeekBar1Val	=	Topcapi    		'top capı";
_seekbar1val = _topcapi;
 //BA.debugLineNum = 98;BA.debugLine="SeekBar2Val	=	RaketUzunlugu  	'raket uzunlugu";
_seekbar2val = _raketuzunlugu;
 //BA.debugLineNum = 99;BA.debugLine="SeekBar3Val=	TimerInterval	' Timer interval";
_seekbar3val = _timerinterval;
 //BA.debugLineNum = 100;BA.debugLine="SeekBar4Val	=	TopHiziX 		' top hizi";
_seekbar4val = _tophizix;
 }else {
 //BA.debugLineNum = 102;BA.debugLine="Topcapi			= SeekBar1Val    		'top capı";
_topcapi = _seekbar1val;
 //BA.debugLineNum = 103;BA.debugLine="RaketUzunlugu	= SeekBar2Val    	'raket uzunlugu";
_raketuzunlugu = _seekbar2val;
 //BA.debugLineNum = 104;BA.debugLine="TimerInterval	= SeekBar3Val    ' raket genisligi";
_timerinterval = _seekbar3val;
 //BA.debugLineNum = 105;BA.debugLine="TopHiziX		= SeekBar4Val 			' top hizi";
_tophizix = _seekbar4val;
 };
 //BA.debugLineNum = 108;BA.debugLine="Raket1X1	=	7%X";
_raket1x1 = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (7),mostCurrent.activityBA);
 //BA.debugLineNum = 109;BA.debugLine="Raket1X2	=	Raket1X1+RaketGenisligi";
_raket1x2 = (int) (_raket1x1+_raketgenisligi);
 //BA.debugLineNum = 110;BA.debugLine="Raket1Y1	=	5%Y";
_raket1y1 = anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA);
 //BA.debugLineNum = 111;BA.debugLine="Raket1Y2	=	Raket1Y1+RaketUzunlugu";
_raket1y2 = (int) (_raket1y1+_raketuzunlugu);
 //BA.debugLineNum = 112;BA.debugLine="Raket2X1	=	85%X";
_raket2x1 = anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (85),mostCurrent.activityBA);
 //BA.debugLineNum = 113;BA.debugLine="Raket2X2	=	Raket2X1+RaketGenisligi";
_raket2x2 = (int) (_raket2x1+_raketgenisligi);
 //BA.debugLineNum = 114;BA.debugLine="Raket2Y1	=	85%Y";
_raket2y1 = anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (85),mostCurrent.activityBA);
 //BA.debugLineNum = 115;BA.debugLine="Raket2Y2	=	Raket2Y1+RaketUzunlugu";
_raket2y2 = (int) (_raket2y1+_raketuzunlugu);
 //BA.debugLineNum = 117;BA.debugLine="raket1.DestRect.Initialize(Raket1X1,Raket1Y1,Rake";
_raket1.DestRect.Initialize(_raket1x1,_raket1y1,_raket1x2,_raket1y2);
 //BA.debugLineNum = 118;BA.debugLine="raket2.DestRect.Initialize(Raket2X1,Raket2Y1,Rake";
_raket2.DestRect.Initialize(_raket2x1,_raket2y1,_raket2x2,_raket2y2);
 //BA.debugLineNum = 120;BA.debugLine="backgroundbitmap.Bitmap = LoadBitmap(File.DirAsse";
_backgroundbitmap.Bitmap = anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"tennis-court-hi.png");
 //BA.debugLineNum = 121;BA.debugLine="smiley.Bitmap = LoadBitmap(File.DirAssets, \"smile";
_smiley.Bitmap = anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"smiley.png");
 //BA.debugLineNum = 122;BA.debugLine="raket2.Bitmap = LoadBitmap(File.DirAssets, \"raket";
_raket2.Bitmap = anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"raket.png");
 //BA.debugLineNum = 123;BA.debugLine="raket1.Bitmap = LoadBitmap(File.DirAssets, \"raket";
_raket1.Bitmap = anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"raket.png");
 //BA.debugLineNum = 124;BA.debugLine="smiley.DestRect.Initialize(100dip, 100dip, 100dip";
_smiley.DestRect.Initialize(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))+_topcapi),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))+_topcapi));
 //BA.debugLineNum = 126;BA.debugLine="Player1skor = Player1skoro";
_player1skor = _player1skoro;
 //BA.debugLineNum = 127;BA.debugLine="player2skor = player2skoro";
_player2skor = _player2skoro;
 //BA.debugLineNum = 129;BA.debugLine="Activity.AddMenuItem(\"Settings\", \"Settings\")";
mostCurrent._activity.AddMenuItem((java.lang.CharSequence)("Settings"),"Settings");
 //BA.debugLineNum = 130;BA.debugLine="Activity.AddMenuItem(\"BlueToothBaglanti\", \"BlueTo";
mostCurrent._activity.AddMenuItem((java.lang.CharSequence)("BlueToothBaglanti"),"BlueToothBaglanti");
 //BA.debugLineNum = 131;BA.debugLine="Activity.AddMenuItem(\"Exit\",\"Exit\")";
mostCurrent._activity.AddMenuItem((java.lang.CharSequence)("Exit"),"Exit");
 //BA.debugLineNum = 133;BA.debugLine="timer1.Initialize(\"Timer1\", TimerInterval)";
mostCurrent._timer1.Initialize(processBA,"Timer1",(long) (_timerinterval));
 //BA.debugLineNum = 134;BA.debugLine="timer1.Enabled = False";
mostCurrent._timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 136;BA.debugLine="timerscore.Initialize(\"timerscore\", TimerInterval";
mostCurrent._timerscore.Initialize(processBA,"timerscore",(long) (_timerinterval));
 //BA.debugLineNum = 137;BA.debugLine="timerscore.Enabled = False";
mostCurrent._timerscore.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 139;BA.debugLine="Gv.Initialize(\"gv\")";
mostCurrent._gv.Initialize(mostCurrent.activityBA,"gv");
 //BA.debugLineNum = 140;BA.debugLine="Gv.BitmapsData.Add(smiley)";
mostCurrent._gv.getBitmapsData().Add((Object)(_smiley));
 //BA.debugLineNum = 141;BA.debugLine="Gv.BitmapsData.Add(raket1)";
mostCurrent._gv.getBitmapsData().Add((Object)(_raket1));
 //BA.debugLineNum = 142;BA.debugLine="Gv.BitmapsData.Add(raket2)";
mostCurrent._gv.getBitmapsData().Add((Object)(_raket2));
 //BA.debugLineNum = 143;BA.debugLine="If Gv.IsHardwareAccelerated = False Then";
if (mostCurrent._gv.getIsHardwareAccelerated()==anywheresoftware.b4a.keywords.Common.False) { 
 };
 //BA.debugLineNum = 148;BA.debugLine="mediaplayer1.Play";
mostCurrent._mediaplayer1.Play();
 //BA.debugLineNum = 149;BA.debugLine="mediaplayer1.Looping = True";
mostCurrent._mediaplayer1.setLooping(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 150;BA.debugLine="mediaplayer1.SetVolume(0.3,0.3)";
mostCurrent._mediaplayer1.SetVolume((float) (0.3),(float) (0.3));
 //BA.debugLineNum = 151;BA.debugLine="soundvolume = 1.000";
_soundvolume = (float) (1.000);
 //BA.debugLineNum = 152;BA.debugLine="musicvolume = phone1.GetVolume(phone1.VOLUME_MUSI";
_musicvolume = mostCurrent._phone1.GetVolume(mostCurrent._phone1.VOLUME_MUSIC);
 //BA.debugLineNum = 153;BA.debugLine="Activity.LoadLayout(\"pingpong2mainlayout\")";
mostCurrent._activity.LoadLayout("pingpong2mainlayout",mostCurrent.activityBA);
 //BA.debugLineNum = 155;BA.debugLine="CheckBox1.Checked = Mediaplayercaliyor";
mostCurrent._checkbox1.setChecked(_mediaplayercaliyor);
 //BA.debugLineNum = 156;BA.debugLine="CheckBox2.Checked = sesacik";
mostCurrent._checkbox2.setChecked(_sesacik);
 //BA.debugLineNum = 158;BA.debugLine="End Sub";
return "";
}
public static String  _activity_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 404;BA.debugLine="Sub Activity_Touch (Action As Int, X As Float, Y A";
 //BA.debugLineNum = 406;BA.debugLine="End Sub";
return "";
}
public static String  _aynimakina_click() throws Exception{
 //BA.debugLineNum = 229;BA.debugLine="Sub AyniMakina_Click";
 //BA.debugLineNum = 230;BA.debugLine="oyuncesidi = 2  ' PLAYERVSPLAYER";
_oyuncesidi = (int) (2);
 //BA.debugLineNum = 231;BA.debugLine="INITPLAYGROUND";
_initplayground();
 //BA.debugLineNum = 232;BA.debugLine="End Sub";
return "";
}
public static String  _bluetooth_click() throws Exception{
 //BA.debugLineNum = 417;BA.debugLine="Sub BlueTooth_Click";
 //BA.debugLineNum = 418;BA.debugLine="Activity.OpenMenu";
mostCurrent._activity.OpenMenu();
 //BA.debugLineNum = 419;BA.debugLine="End Sub";
return "";
}
public static String  _checkbox1_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 422;BA.debugLine="Sub CheckBox1_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 423;BA.debugLine="If Checked Then";
if (_checked) { 
 //BA.debugLineNum = 424;BA.debugLine="mediaplayer1.Play";
mostCurrent._mediaplayer1.Play();
 //BA.debugLineNum = 425;BA.debugLine="Mediaplayercaliyor = True";
_mediaplayercaliyor = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 427;BA.debugLine="mediaplayer1.Pause";
mostCurrent._mediaplayer1.Pause();
 //BA.debugLineNum = 428;BA.debugLine="Mediaplayercaliyor = False";
_mediaplayercaliyor = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 430;BA.debugLine="End Sub";
return "";
}
public static String  _checkbox2_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 432;BA.debugLine="Sub CheckBox2_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 433;BA.debugLine="If Checked Then";
if (_checked) { 
 //BA.debugLineNum = 434;BA.debugLine="soundvolume = 1.000";
_soundvolume = (float) (1.000);
 //BA.debugLineNum = 435;BA.debugLine="SoundPool1.SetVolume(LoadId1,1,1)";
mostCurrent._soundpool1.SetVolume(_loadid1,(float) (1),(float) (1));
 //BA.debugLineNum = 436;BA.debugLine="phone1.SetVolume(phone1.VOLUME_MUSIC,musicvolume";
mostCurrent._phone1.SetVolume(mostCurrent._phone1.VOLUME_MUSIC,_musicvolume,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 437;BA.debugLine="sesacik = True";
_sesacik = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 439;BA.debugLine="soundvolume = 0.000";
_soundvolume = (float) (0.000);
 //BA.debugLineNum = 440;BA.debugLine="SoundPool1.SetVolume(LoadId1,0,0)";
mostCurrent._soundpool1.SetVolume(_loadid1,(float) (0),(float) (0));
 //BA.debugLineNum = 441;BA.debugLine="phone1.SetVolume(phone1.VOLUME_MUSIC,0,False)";
mostCurrent._phone1.SetVolume(mostCurrent._phone1.VOLUME_MUSIC,(int) (0),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 442;BA.debugLine="sesacik = False";
_sesacik = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 444;BA.debugLine="End Sub";
return "";
}
public static String  _cikisbutton_click() throws Exception{
 //BA.debugLineNum = 413;BA.debugLine="Sub CikisButton_Click";
 //BA.debugLineNum = 414;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 415;BA.debugLine="End Sub";
return "";
}
public static String  _cpucpu_click() throws Exception{
 //BA.debugLineNum = 234;BA.debugLine="Sub CpuCpu_Click";
 //BA.debugLineNum = 235;BA.debugLine="oyuncesidi = 1  ' cpuvscpu";
_oyuncesidi = (int) (1);
 //BA.debugLineNum = 236;BA.debugLine="INITPLAYGROUND";
_initplayground();
 //BA.debugLineNum = 237;BA.debugLine="End Sub";
return "";
}
public static String  _cpuile_click() throws Exception{
 //BA.debugLineNum = 224;BA.debugLine="Sub CpuIle_Click";
 //BA.debugLineNum = 225;BA.debugLine="oyuncesidi = 3  ' CPUVSPLAYER";
_oyuncesidi = (int) (3);
 //BA.debugLineNum = 226;BA.debugLine="INITPLAYGROUND";
_initplayground();
 //BA.debugLineNum = 227;BA.debugLine="End Sub";
return "";
}
public static String  _exit_click() throws Exception{
 //BA.debugLineNum = 408;BA.debugLine="Sub Exit_Click";
 //BA.debugLineNum = 409;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 410;BA.debugLine="End Sub";
return "";
}
public static boolean  _gesturestouch1(Object _view,int _pointerid,int _action,float _x,float _y) throws Exception{
pingpong2.pingpong.main._point _p = null;
int _px = 0;
int _py = 0;
int _i = 0;
 //BA.debugLineNum = 306;BA.debugLine="Sub GesturesTouch1(View As Object, PointerID As In";
 //BA.debugLineNum = 307;BA.debugLine="Dim p As Point";
_p = new pingpong2.pingpong.main._point();
 //BA.debugLineNum = 308;BA.debugLine="Select Action";
switch (BA.switchObjectToInt(_action,mostCurrent._gestures1.ACTION_DOWN,mostCurrent._gestures1.ACTION_POINTER_DOWN,mostCurrent._gestures1.ACTION_POINTER_UP,mostCurrent._gestures1.ACTION_UP)) {
case 0: 
case 1: {
 //BA.debugLineNum = 311;BA.debugLine="p.Id = PointerID";
_p.Id = _pointerid;
 //BA.debugLineNum = 312;BA.debugLine="TouchMap.Put(PointerID, p)";
mostCurrent._touchmap.Put((Object)(_pointerid),(Object)(_p));
 break; }
case 2: {
 //BA.debugLineNum = 314;BA.debugLine="TouchMap.Remove(PointerID)";
mostCurrent._touchmap.Remove((Object)(_pointerid));
 break; }
case 3: {
 //BA.debugLineNum = 317;BA.debugLine="TouchMap.Clear";
mostCurrent._touchmap.Clear();
 break; }
}
;
 //BA.debugLineNum = 319;BA.debugLine="Dim px, py As Int";
_px = 0;
_py = 0;
 //BA.debugLineNum = 320;BA.debugLine="Log(TouchMap.Size)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(mostCurrent._touchmap.getSize()));
 //BA.debugLineNum = 321;BA.debugLine="For İ = 0 To TouchMap.size - 1";
{
final int step13 = 1;
final int limit13 = (int) (mostCurrent._touchmap.getSize()-1);
for (_i = (int) (0) ; (step13 > 0 && _i <= limit13) || (step13 < 0 && _i >= limit13); _i = ((int)(0 + _i + step13)) ) {
 //BA.debugLineNum = 322;BA.debugLine="p = TouchMap.GetValueAt(İ)";
_p = (pingpong2.pingpong.main._point)(mostCurrent._touchmap.GetValueAt(_i));
 //BA.debugLineNum = 323;BA.debugLine="px = Gestures1.GetX(p.id)";
_px = (int) (mostCurrent._gestures1.GetX(_p.Id));
 //BA.debugLineNum = 324;BA.debugLine="py = Gestures1.GetY(p.id)";
_py = (int) (mostCurrent._gestures1.GetY(_p.Id));
 //BA.debugLineNum = 325;BA.debugLine="p.prevX = px";
_p.prevX = _px;
 //BA.debugLineNum = 326;BA.debugLine="p.prevY = py";
_p.prevY = _py;
 //BA.debugLineNum = 327;BA.debugLine="Select oyuncesidi";
switch (_oyuncesidi) {
case 1: {
 break; }
case 2: {
 //BA.debugLineNum = 330;BA.debugLine="If px < (Xekseni/100)*50 Then";
if (_px<(_xekseni/(double)100)*50) { 
 //BA.debugLineNum = 331;BA.debugLine="Raket1Y1				=	py - (RaketUzunlugu/2)";
_raket1y1 = (int) (_py-(_raketuzunlugu/(double)2));
 //BA.debugLineNum = 332;BA.debugLine="Raket1Y2				=	Raket1Y1+RaketUzunlugu";
_raket1y2 = (int) (_raket1y1+_raketuzunlugu);
 //BA.debugLineNum = 333;BA.debugLine="raket1.DestRect.Top 	=	Raket1Y1";
_raket1.DestRect.setTop(_raket1y1);
 //BA.debugLineNum = 334;BA.debugLine="raket1.DestRect.Bottom 	=	Raket1Y2";
_raket1.DestRect.setBottom(_raket1y2);
 }else {
 //BA.debugLineNum = 336;BA.debugLine="Raket2Y1				=	py - (RaketUzunlugu/2)";
_raket2y1 = (int) (_py-(_raketuzunlugu/(double)2));
 //BA.debugLineNum = 337;BA.debugLine="Raket2Y2				=	Raket2Y1+RaketUzunlugu";
_raket2y2 = (int) (_raket2y1+_raketuzunlugu);
 //BA.debugLineNum = 338;BA.debugLine="raket2.DestRect.Top 	=	Raket2Y1";
_raket2.DestRect.setTop(_raket2y1);
 //BA.debugLineNum = 339;BA.debugLine="raket2.DestRect.Bottom	=	Raket2Y2";
_raket2.DestRect.setBottom(_raket2y2);
 };
 break; }
case 3: {
 //BA.debugLineNum = 342;BA.debugLine="If px < 50%X Then";
if (_px<anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)) { 
 //BA.debugLineNum = 343;BA.debugLine="Raket1Y1				=	py - (RaketUzunlugu/2)";
_raket1y1 = (int) (_py-(_raketuzunlugu/(double)2));
 //BA.debugLineNum = 344;BA.debugLine="Raket1Y2				=	Raket1Y1+RaketUzunlugu";
_raket1y2 = (int) (_raket1y1+_raketuzunlugu);
 //BA.debugLineNum = 345;BA.debugLine="raket1.DestRect.Top 	=	Raket1Y1";
_raket1.DestRect.setTop(_raket1y1);
 //BA.debugLineNum = 346;BA.debugLine="raket1.DestRect.Bottom	=	Raket1Y2";
_raket1.DestRect.setBottom(_raket1y2);
 }else {
 };
 break; }
default: {
 break; }
}
;
 }
};
 //BA.debugLineNum = 352;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 353;BA.debugLine="End Sub";
return false;
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 22;BA.debugLine="Dim Temp1,Temp2, Temp3,Temp4 As Int";
_temp1 = 0;
_temp2 = 0;
_temp3 = 0;
_temp4 = 0;
 //BA.debugLineNum = 23;BA.debugLine="Dim LoadId1 As Int";
_loadid1 = 0;
 //BA.debugLineNum = 24;BA.debugLine="Dim LoadId2 As Int";
_loadid2 = 0;
 //BA.debugLineNum = 25;BA.debugLine="Dim LoadId3 As Int";
_loadid3 = 0;
 //BA.debugLineNum = 26;BA.debugLine="Dim LoadId4 As Int";
_loadid4 = 0;
 //BA.debugLineNum = 27;BA.debugLine="Dim LoadId5 As Int";
_loadid5 = 0;
 //BA.debugLineNum = 28;BA.debugLine="Dim LoadId6 As Int";
_loadid6 = 0;
 //BA.debugLineNum = 29;BA.debugLine="Dim MaxStream As Int";
_maxstream = 0;
 //BA.debugLineNum = 30;BA.debugLine="Dim SoundPool1 As SoundPool";
mostCurrent._soundpool1 = new anywheresoftware.b4a.audio.SoundPoolWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Dim mediaplayer1 As MediaPlayer";
mostCurrent._mediaplayer1 = new anywheresoftware.b4a.objects.MediaPlayerWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Dim rate As Float";
_rate = 0f;
 //BA.debugLineNum = 34;BA.debugLine="Dim Gv As GameView";
mostCurrent._gv = new anywheresoftware.b4a.objects.GameViewWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Dim Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Dim Canvas1 As Canvas";
mostCurrent._canvas1 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim Gestures1 As Gestures";
mostCurrent._gestures1 = new anywheresoftware.b4a.agraham.gestures.Gestures();
 //BA.debugLineNum = 38;BA.debugLine="Dim TouchMap As Map";
mostCurrent._touchmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 39;BA.debugLine="Dim CikisButton As Button";
mostCurrent._cikisbutton = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Dim timer1,timerscore As Timer";
mostCurrent._timer1 = new anywheresoftware.b4a.objects.Timer();
mostCurrent._timerscore = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 41;BA.debugLine="Dim Exit , Clear As String";
mostCurrent._exit = "";
mostCurrent._clear = "";
 //BA.debugLineNum = 42;BA.debugLine="Dim ActivityBackgroundBmp As Bitmap";
mostCurrent._activitybackgroundbmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Type Point(Id As Int, prevX As Int, prevY As Int,";
;
 //BA.debugLineNum = 44;BA.debugLine="Dim RaketUzunlugu , RaketGenisligi , TopHiziX , T";
_raketuzunlugu = 0;
_raketgenisligi = 0;
_tophizix = 0;
_topcapi = 0;
_timerinterval = 0;
 //BA.debugLineNum = 45;BA.debugLine="Dim Raket1X1, Raket1X2, Raket1Y1, Raket1Y2, Raket";
_raket1x1 = 0;
_raket1x2 = 0;
_raket1y1 = 0;
_raket1y2 = 0;
_raket2x1 = 0;
_raket2x2 = 0;
_raket2y1 = 0;
_raket2y2 = 0;
_xekseni = 0;
_yekseni = 0;
_oyuncesidi = 0;
 //BA.debugLineNum = 46;BA.debugLine="Dim AyniMakina , BaslikButton , BlueTooth ,CpuIle";
mostCurrent._aynimakina = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._baslikbutton = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._bluetooth = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._cpuile = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._reserve = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._cpucpu = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._player1skorlabel = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._player2skorlabel = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Dim rate1, rate2, rate3, rate4,soundvolume As Flo";
_rate1 = 0f;
_rate2 = 0f;
_rate3 = 0f;
_rate4 = 0f;
_soundvolume = 0f;
 //BA.debugLineNum = 49;BA.debugLine="Dim repeat1, repeat2,repeat3,repeat4, musicvolume";
_repeat1 = 0;
_repeat2 = 0;
_repeat3 = 0;
_repeat4 = 0;
_musicvolume = 0;
_sayialanplayer = 0;
_animationcounter1 = 0;
_animationcounter2 = 0;
 //BA.debugLineNum = 51;BA.debugLine="Dim CheckBox1 As CheckBox";
mostCurrent._checkbox1 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Dim CheckBox2 As CheckBox";
mostCurrent._checkbox2 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Dim phone1 As Phone";
mostCurrent._phone1 = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 55;BA.debugLine="Dim Mediaplayercaliyor, sesacik As Boolean";
_mediaplayercaliyor = false;
_sesacik = false;
 //BA.debugLineNum = 57;BA.debugLine="End Sub";
return "";
}
public static String  _initplayground() throws Exception{
 //BA.debugLineNum = 239;BA.debugLine="Sub INITPLAYGROUND";
 //BA.debugLineNum = 240;BA.debugLine="Player1skor = 0";
_player1skor = (int) (0);
 //BA.debugLineNum = 241;BA.debugLine="player2skor = 0";
_player2skor = (int) (0);
 //BA.debugLineNum = 242;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 243;BA.debugLine="Activity.LoadLayout(\"Pingpong2OynaLayout\")";
mostCurrent._activity.LoadLayout("Pingpong2OynaLayout",mostCurrent.activityBA);
 //BA.debugLineNum = 244;BA.debugLine="Activity.AddView(Gv, 0, 0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._gv.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 245;BA.debugLine="Panel1.SetBackgroundImage(backgroundbitmap.Bitmap";
mostCurrent._panel1.SetBackgroundImage((android.graphics.Bitmap)(_backgroundbitmap.Bitmap.getObject()));
 //BA.debugLineNum = 246;BA.debugLine="Canvas1.Initialize(Panel1)";
mostCurrent._canvas1.Initialize((android.view.View)(mostCurrent._panel1.getObject()));
 //BA.debugLineNum = 247;BA.debugLine="TouchMap.Initialize";
mostCurrent._touchmap.Initialize();
 //BA.debugLineNum = 248;BA.debugLine="timer1.Enabled = True";
mostCurrent._timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 249;BA.debugLine="Gestures1.SetOnTouchListener(Panel1, \"GesturesTou";
mostCurrent._gestures1.SetOnTouchListener(mostCurrent.activityBA,(android.view.View)(mostCurrent._panel1.getObject()),"GesturesTouch1");
 //BA.debugLineNum = 250;BA.debugLine="End Sub";
return "";
}
public static String  _label2_click() throws Exception{
 //BA.debugLineNum = 356;BA.debugLine="Sub Label2_Click";
 //BA.debugLineNum = 357;BA.debugLine="Activity.LoadLayout(\"pingpong2mainlayout\")";
mostCurrent._activity.LoadLayout("pingpong2mainlayout",mostCurrent.activityBA);
 //BA.debugLineNum = 358;BA.debugLine="timer1.Enabled = False";
mostCurrent._timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 359;BA.debugLine="If soundvolume  = 1 Then";
if (_soundvolume==1) { 
 //BA.debugLineNum = 360;BA.debugLine="CheckBox2.Checked = True";
mostCurrent._checkbox2.setChecked(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 362;BA.debugLine="CheckBox2.Checked = False";
mostCurrent._checkbox2.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 365;BA.debugLine="If mediaplayer1.IsPlaying Then";
if (mostCurrent._mediaplayer1.IsPlaying()) { 
 //BA.debugLineNum = 366;BA.debugLine="CheckBox1.Checked = True";
mostCurrent._checkbox1.setChecked(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 368;BA.debugLine="CheckBox1.Checked = False";
mostCurrent._checkbox1.setChecked(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 372;BA.debugLine="End Sub";
return "";
}
public static String  _movesmiley() throws Exception{
 //BA.debugLineNum = 252;BA.debugLine="Sub MoveSmiley";
 //BA.debugLineNum = 254;BA.debugLine="If smiley.DestRect.Right > 99%x Then";
if (_smiley.DestRect.getRight()>anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (99),mostCurrent.activityBA)) { 
 //BA.debugLineNum = 255;BA.debugLine="TopHiziX = -1 * Abs(TopHiziX)";
_tophizix = (int) (-1*anywheresoftware.b4a.keywords.Common.Abs(_tophizix));
 //BA.debugLineNum = 256;BA.debugLine="Player1skor = Player1skor + 1";
_player1skor = (int) (_player1skor+1);
 //BA.debugLineNum = 257;BA.debugLine="Player1skoro = Player1skor";
_player1skoro = _player1skor;
 //BA.debugLineNum = 258;BA.debugLine="Player1SkorLabel.Text = Player1skor";
mostCurrent._player1skorlabel.setText((Object)(_player1skor));
 //BA.debugLineNum = 259;BA.debugLine="Temp1 = SoundPool1.Play(LoadId1,1,soundvolume,so";
_temp1 = mostCurrent._soundpool1.Play(_loadid1,(float) (1),_soundvolume,(int) (_soundvolume),_repeat1,_rate1);
 //BA.debugLineNum = 260;BA.debugLine="sayialanplayer = 1";
_sayialanplayer = (int) (1);
 //BA.debugLineNum = 261;BA.debugLine="timerscore_Tick";
_timerscore_tick();
 }else if(_smiley.DestRect.getLeft()<anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (1),mostCurrent.activityBA)) { 
 //BA.debugLineNum = 263;BA.debugLine="TopHiziX = Abs(TopHiziX)";
_tophizix = (int) (anywheresoftware.b4a.keywords.Common.Abs(_tophizix));
 //BA.debugLineNum = 264;BA.debugLine="player2skor = player2skor + 1";
_player2skor = (int) (_player2skor+1);
 //BA.debugLineNum = 265;BA.debugLine="player2skoro = player2skor";
_player2skoro = _player2skor;
 //BA.debugLineNum = 266;BA.debugLine="Player2SkorLabel.Text = player2skor";
mostCurrent._player2skorlabel.setText((Object)(_player2skor));
 //BA.debugLineNum = 267;BA.debugLine="Temp1 = SoundPool1.Play(LoadId1,1,soundvolume,so";
_temp1 = mostCurrent._soundpool1.Play(_loadid1,(float) (1),_soundvolume,(int) (_soundvolume),_repeat1,_rate1);
 //BA.debugLineNum = 268;BA.debugLine="sayialanplayer = 2";
_sayialanplayer = (int) (2);
 //BA.debugLineNum = 269;BA.debugLine="timerscore_Tick";
_timerscore_tick();
 };
 //BA.debugLineNum = 272;BA.debugLine="If (smiley.DestRect.CenterX > Raket2X1) And (smil";
if ((_smiley.DestRect.getCenterX()>_raket2x1) && (_smiley.DestRect.getCenterX()<_raket2x2) && (_smiley.DestRect.getCenterY()<_raket2y2) && (_smiley.DestRect.getCenterY()>_raket2y1)) { 
 //BA.debugLineNum = 273;BA.debugLine="Temp1 = SoundPool1.Play(LoadId1,1,soundvolume,so";
_temp1 = mostCurrent._soundpool1.Play(_loadid1,(float) (1),_soundvolume,(int) (_soundvolume),(int) (0),(float) (1));
 //BA.debugLineNum = 274;BA.debugLine="TopHiziX = (-1 * Abs(TopHiziX))";
_tophizix = (int) ((-1*anywheresoftware.b4a.keywords.Common.Abs(_tophizix)));
 //BA.debugLineNum = 275;BA.debugLine="If vy < 0 Then";
if (_vy<0) { 
 //BA.debugLineNum = 276;BA.debugLine="vy = vy + Rnd(0,15)";
_vy = (int) (_vy+anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (15)));
 }else {
 //BA.debugLineNum = 278;BA.debugLine="vy = vy - Rnd(0,15)";
_vy = (int) (_vy-anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (15)));
 };
 }else if((_smiley.DestRect.getCenterX()>_raket1x1) && (_smiley.DestRect.getCenterX()<_raket1x2) && (_smiley.DestRect.getCenterY()<_raket1y2) && (_smiley.DestRect.getCenterY()>_raket1y1)) { 
 //BA.debugLineNum = 281;BA.debugLine="Temp1 = SoundPool1.Play(LoadId1,1,soundvolume,so";
_temp1 = mostCurrent._soundpool1.Play(_loadid1,(float) (1),_soundvolume,(int) (_soundvolume),(int) (0),(float) (1));
 //BA.debugLineNum = 282;BA.debugLine="TopHiziX = (Abs(TopHiziX))";
_tophizix = (int) ((anywheresoftware.b4a.keywords.Common.Abs(_tophizix)));
 //BA.debugLineNum = 283;BA.debugLine="If vy < 0 Then";
if (_vy<0) { 
 //BA.debugLineNum = 284;BA.debugLine="vy = vy + Rnd(0,15)";
_vy = (int) (_vy+anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (15)));
 }else {
 //BA.debugLineNum = 286;BA.debugLine="vy = vy - Rnd(0,15)";
_vy = (int) (_vy-anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (15)));
 };
 };
 //BA.debugLineNum = 290;BA.debugLine="If smiley.DestRect.Bottom > 99%y Then";
if (_smiley.DestRect.getBottom()>anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (99),mostCurrent.activityBA)) { 
 //BA.debugLineNum = 291;BA.debugLine="vy = -1 * Abs(vy)";
_vy = (int) (-1*anywheresoftware.b4a.keywords.Common.Abs(_vy));
 //BA.debugLineNum = 292;BA.debugLine="Temp1 = SoundPool1.Play(LoadId1,1,soundvolume,so";
_temp1 = mostCurrent._soundpool1.Play(_loadid1,(float) (1),_soundvolume,(int) (_soundvolume),_repeat1,_rate1);
 }else if(_smiley.DestRect.getTop()<anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1),mostCurrent.activityBA)) { 
 //BA.debugLineNum = 294;BA.debugLine="vy = Abs(vy)";
_vy = (int) (anywheresoftware.b4a.keywords.Common.Abs(_vy));
 //BA.debugLineNum = 295;BA.debugLine="Temp1 = SoundPool1.Play(LoadId1,1,soundvolume,so";
_temp1 = mostCurrent._soundpool1.Play(_loadid1,(float) (1),_soundvolume,(int) (_soundvolume),_repeat1,_rate1);
 };
 //BA.debugLineNum = 299;BA.debugLine="smiley.DestRect.Left = smiley.DestRect.Left + Top";
_smiley.DestRect.setLeft((int) (_smiley.DestRect.getLeft()+_tophizix));
 //BA.debugLineNum = 300;BA.debugLine="smiley.DestRect.Top = smiley.DestRect.Top + vy";
_smiley.DestRect.setTop((int) (_smiley.DestRect.getTop()+_vy));
 //BA.debugLineNum = 301;BA.debugLine="smiley.DestRect.Right = smiley.DestRect.Left + To";
_smiley.DestRect.setRight((int) (_smiley.DestRect.getLeft()+_topcapi));
 //BA.debugLineNum = 302;BA.debugLine="smiley.DestRect.Bottom = smiley.DestRect.Top + To";
_smiley.DestRect.setBottom((int) (_smiley.DestRect.getTop()+_topcapi));
 //BA.debugLineNum = 303;BA.debugLine="Gv.Invalidate 'Mark the whole GameView as \"dirty\"";
mostCurrent._gv.Invalidate();
 //BA.debugLineNum = 304;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 13;BA.debugLine="Dim SeekBar1Val As Int	'top capı";
_seekbar1val = 0;
 //BA.debugLineNum = 14;BA.debugLine="Dim SeekBar2Val As Int	'raket uzunlugu";
_seekbar2val = 0;
 //BA.debugLineNum = 15;BA.debugLine="Dim SeekBar3Val As Int	'timer interval";
_seekbar3val = 0;
 //BA.debugLineNum = 16;BA.debugLine="Dim SeekBar4Val As Int	'top hızı";
_seekbar4val = 0;
 //BA.debugLineNum = 17;BA.debugLine="Dim smiley,raket1, raket2, backgroundbitmap  As B";
_smiley = new anywheresoftware.b4a.objects.GameViewWrapper.BitmapData();
_raket1 = new anywheresoftware.b4a.objects.GameViewWrapper.BitmapData();
_raket2 = new anywheresoftware.b4a.objects.GameViewWrapper.BitmapData();
_backgroundbitmap = new anywheresoftware.b4a.objects.GameViewWrapper.BitmapData();
 //BA.debugLineNum = 18;BA.debugLine="Dim vx, vy, Player1skor, player2skor, Player1skor";
_vx = 0;
_vy = 0;
_player1skor = 0;
_player2skor = 0;
_player1skoro = 0;
_player2skoro = 0;
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return "";
}
public static String  _scoreanimation() throws Exception{
 //BA.debugLineNum = 446;BA.debugLine="Sub ScoreAnimation";
 //BA.debugLineNum = 447;BA.debugLine="End Sub";
return "";
}
public static String  _settings_click() throws Exception{
 //BA.debugLineNum = 374;BA.debugLine="Sub Settings_Click";
 //BA.debugLineNum = 375;BA.debugLine="SeekBar1Val  =	Topcapi  		'	top capı";
_seekbar1val = _topcapi;
 //BA.debugLineNum = 376;BA.debugLine="SeekBar2Val =	RaketUzunlugu   '	raket uzunlugu";
_seekbar2val = _raketuzunlugu;
 //BA.debugLineNum = 377;BA.debugLine="SeekBar3Val =	TimerInterval ' 	timer interval";
_seekbar3val = _timerinterval;
 //BA.debugLineNum = 378;BA.debugLine="SeekBar4Val =	Abs(TopHiziX)		' 	top hizi";
_seekbar4val = (int) (anywheresoftware.b4a.keywords.Common.Abs(_tophizix));
 //BA.debugLineNum = 380;BA.debugLine="End Sub";
return "";
}
public static String  _timer1_tick() throws Exception{
 //BA.debugLineNum = 194;BA.debugLine="Sub timer1_Tick";
 //BA.debugLineNum = 195;BA.debugLine="Select oyuncesidi";
switch (_oyuncesidi) {
case 1: {
 //BA.debugLineNum = 197;BA.debugLine="MoveSmiley";
_movesmiley();
 //BA.debugLineNum = 199;BA.debugLine="If smiley.DestRect.CenterX < 50%x Then";
if (_smiley.DestRect.getCenterX()<anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA)) { 
 //BA.debugLineNum = 200;BA.debugLine="Raket1Y1	=	smiley.DestRect.CenterY - (RaketUzu";
_raket1y1 = (int) (_smiley.DestRect.getCenterY()-(_raketuzunlugu/(double)2));
 //BA.debugLineNum = 201;BA.debugLine="Raket1Y2	=	Raket1Y1+RaketUzunlugu";
_raket1y2 = (int) (_raket1y1+_raketuzunlugu);
 //BA.debugLineNum = 202;BA.debugLine="raket1.DestRect.Top = smiley.DestRect.CenterY";
_raket1.DestRect.setTop((int) (_smiley.DestRect.getCenterY()-(_raketuzunlugu/(double)2)));
 //BA.debugLineNum = 203;BA.debugLine="raket1.DestRect.Bottom = raket1.DestRect.Top+R";
_raket1.DestRect.setBottom((int) (_raket1.DestRect.getTop()+_raketuzunlugu));
 }else {
 //BA.debugLineNum = 205;BA.debugLine="Raket2Y1	=	smiley.DestRect.CenterY - (RaketUzu";
_raket2y1 = (int) (_smiley.DestRect.getCenterY()-(_raketuzunlugu/(double)2));
 //BA.debugLineNum = 206;BA.debugLine="Raket2Y2	=	Raket2Y1+RaketUzunlugu";
_raket2y2 = (int) (_raket2y1+_raketuzunlugu);
 //BA.debugLineNum = 207;BA.debugLine="raket2.DestRect.Top = smiley.DestRect.CenterY";
_raket2.DestRect.setTop((int) (_smiley.DestRect.getCenterY()-(_raketuzunlugu/(double)2)));
 //BA.debugLineNum = 208;BA.debugLine="raket2.DestRect.Bottom = raket2.DestRect.Top+R";
_raket2.DestRect.setBottom((int) (_raket2.DestRect.getTop()+_raketuzunlugu));
 };
 break; }
case 2: {
 //BA.debugLineNum = 211;BA.debugLine="MoveSmiley";
_movesmiley();
 break; }
case 3: {
 //BA.debugLineNum = 213;BA.debugLine="MoveSmiley";
_movesmiley();
 //BA.debugLineNum = 215;BA.debugLine="Raket2Y1	=	smiley.DestRect.CenterY - (RaketUzun";
_raket2y1 = (int) (_smiley.DestRect.getCenterY()-(_raketuzunlugu/(double)2));
 //BA.debugLineNum = 216;BA.debugLine="Raket2Y2	=	Raket2Y1+RaketUzunlugu";
_raket2y2 = (int) (_raket2y1+_raketuzunlugu);
 //BA.debugLineNum = 217;BA.debugLine="raket2.DestRect.Top = smiley.DestRect.CenterY -";
_raket2.DestRect.setTop((int) (_smiley.DestRect.getCenterY()-(_raketuzunlugu/(double)2)));
 //BA.debugLineNum = 218;BA.debugLine="raket2.DestRect.Bottom = raket2.DestRect.Top+Ra";
_raket2.DestRect.setBottom((int) (_raket2.DestRect.getTop()+_raketuzunlugu));
 break; }
case 4: {
 break; }
default: {
 break; }
}
;
 //BA.debugLineNum = 222;BA.debugLine="End Sub";
return "";
}
public static String  _timerscore_tick() throws Exception{
 //BA.debugLineNum = 166;BA.debugLine="Sub timerscore_Tick";
 //BA.debugLineNum = 167;BA.debugLine="If animationcounter1 = 0 Then";
if (_animationcounter1==0) { 
 //BA.debugLineNum = 168;BA.debugLine="timerscore.Enabled = True";
mostCurrent._timerscore.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else {
 };
 //BA.debugLineNum = 171;BA.debugLine="animationcounter1 = animationcounter1 + 1";
_animationcounter1 = (int) (_animationcounter1+1);
 //BA.debugLineNum = 172;BA.debugLine="If (( animationcounter1 Mod 3) = 0) Then";
if (((_animationcounter1%3)==0)) { 
 //BA.debugLineNum = 173;BA.debugLine="If sayialanplayer = 1 Then";
if (_sayialanplayer==1) { 
 //BA.debugLineNum = 174;BA.debugLine="Player1SkorLabel.Color = Colors.RGB(Rnd(0,25";
mostCurrent._player1skorlabel.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB(anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (255)),anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (255)),anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (255))));
 }else {
 //BA.debugLineNum = 176;BA.debugLine="Player2SkorLabel.Color = Colors.RGB(Rnd(0,255),";
mostCurrent._player2skorlabel.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB(anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (255)),anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (255)),anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (255))));
 };
 }else {
 };
 //BA.debugLineNum = 181;BA.debugLine="If animationcounter1 = 40 Then";
if (_animationcounter1==40) { 
 //BA.debugLineNum = 182;BA.debugLine="timerscore.Enabled = False";
mostCurrent._timerscore.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 183;BA.debugLine="animationcounter1 = 0";
_animationcounter1 = (int) (0);
 //BA.debugLineNum = 184;BA.debugLine="Player1SkorLabel.Color = Colors.Blue";
mostCurrent._player1skorlabel.setColor(anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 185;BA.debugLine="Player2SkorLabel.Color = Colors.Blue";
mostCurrent._player2skorlabel.setColor(anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 186;BA.debugLine="If Player1skor > player2skor Then";
if (_player1skor>_player2skor) { 
 //BA.debugLineNum = 187;BA.debugLine="Player1SkorLabel.Color = Colors.Red";
mostCurrent._player1skorlabel.setColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 }else {
 //BA.debugLineNum = 189;BA.debugLine="Player2SkorLabel.Color = Colors.Red";
mostCurrent._player2skorlabel.setColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 };
 }else {
 };
 //BA.debugLineNum = 193;BA.debugLine="End Sub";
return "";
}
}
