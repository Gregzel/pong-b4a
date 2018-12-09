package pingpong2.pingpong.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_pingpong2mainlayout{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
String _aralik1="";
String _aralik2="";
String _menusayisi="";
String _menusize="";
//BA.debugLineNum = 2;BA.debugLine="Aralik1 = 50"[pingpong2mainlayout/General script]
_aralik1 = "50";
//BA.debugLineNum = 3;BA.debugLine="Aralik2 = 5"[pingpong2mainlayout/General script]
_aralik2 = "5";
//BA.debugLineNum = 4;BA.debugLine="menusayisi = 5"[pingpong2mainlayout/General script]
_menusayisi = "5";
//BA.debugLineNum = 6;BA.debugLine="Panel1.Height  = 100%y"[pingpong2mainlayout/General script]
views.get("panel1").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 7;BA.debugLine="Panel1.Width  = 100%x"[pingpong2mainlayout/General script]
views.get("panel1").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 9;BA.debugLine="menusize = ((Panel1.Height-((menusayisi-1)*aralik2)) / menusayisi )"[pingpong2mainlayout/General script]
_menusize = BA.NumberToString((((views.get("panel1").vw.getHeight())-((Double.parseDouble(_menusayisi)-1d)*Double.parseDouble(_aralik2)))/Double.parseDouble(_menusayisi)));
//BA.debugLineNum = 15;BA.debugLine="BaslikButton.Height = menusize"[pingpong2mainlayout/General script]
views.get("baslikbutton").vw.setHeight((int)(Double.parseDouble(_menusize)));
//BA.debugLineNum = 16;BA.debugLine="BaslikButton.Width = Panel1.Width / 3"[pingpong2mainlayout/General script]
views.get("baslikbutton").vw.setWidth((int)((views.get("panel1").vw.getWidth())/3d));
//BA.debugLineNum = 17;BA.debugLine="BaslikButton.left = Panel1.Width / 3"[pingpong2mainlayout/General script]
views.get("baslikbutton").vw.setLeft((int)((views.get("panel1").vw.getWidth())/3d));
//BA.debugLineNum = 19;BA.debugLine="BaslikButton.top = 0%x"[pingpong2mainlayout/General script]
views.get("baslikbutton").vw.setTop((int)((0d / 100 * width)));
//BA.debugLineNum = 21;BA.debugLine="CheckBox1.Top = 0%x"[pingpong2mainlayout/General script]
views.get("checkbox1").vw.setTop((int)((0d / 100 * width)));
//BA.debugLineNum = 22;BA.debugLine="CheckBox2.Top = 0%x"[pingpong2mainlayout/General script]
views.get("checkbox2").vw.setTop((int)((0d / 100 * width)));
//BA.debugLineNum = 23;BA.debugLine="CheckBox1.Height = menusize"[pingpong2mainlayout/General script]
views.get("checkbox1").vw.setHeight((int)(Double.parseDouble(_menusize)));
//BA.debugLineNum = 24;BA.debugLine="CheckBox2.Height = menusize"[pingpong2mainlayout/General script]
views.get("checkbox2").vw.setHeight((int)(Double.parseDouble(_menusize)));
//BA.debugLineNum = 25;BA.debugLine="CheckBox1.Width = Panel1.Width / 3"[pingpong2mainlayout/General script]
views.get("checkbox1").vw.setWidth((int)((views.get("panel1").vw.getWidth())/3d));
//BA.debugLineNum = 26;BA.debugLine="CheckBox2.Width = Panel1.Width / 3"[pingpong2mainlayout/General script]
views.get("checkbox2").vw.setWidth((int)((views.get("panel1").vw.getWidth())/3d));
//BA.debugLineNum = 27;BA.debugLine="CheckBox1.left = 0%x"[pingpong2mainlayout/General script]
views.get("checkbox1").vw.setLeft((int)((0d / 100 * width)));
//BA.debugLineNum = 28;BA.debugLine="CheckBox2.Right = 100%x"[pingpong2mainlayout/General script]
views.get("checkbox2").vw.setLeft((int)((100d / 100 * width) - (views.get("checkbox2").vw.getWidth())));
//BA.debugLineNum = 31;BA.debugLine="AyniMakina.Height = menusize"[pingpong2mainlayout/General script]
views.get("aynimakina").vw.setHeight((int)(Double.parseDouble(_menusize)));
//BA.debugLineNum = 32;BA.debugLine="AyniMakina.Width = Panel1.Width"[pingpong2mainlayout/General script]
views.get("aynimakina").vw.setWidth((int)((views.get("panel1").vw.getWidth())));
//BA.debugLineNum = 33;BA.debugLine="AyniMakina.left = 0%x"[pingpong2mainlayout/General script]
views.get("aynimakina").vw.setLeft((int)((0d / 100 * width)));
//BA.debugLineNum = 34;BA.debugLine="AyniMakina.Right = 100%x"[pingpong2mainlayout/General script]
views.get("aynimakina").vw.setLeft((int)((100d / 100 * width) - (views.get("aynimakina").vw.getWidth())));
//BA.debugLineNum = 35;BA.debugLine="AyniMakina.top = BaslikButton.Bottom + Aralik2"[pingpong2mainlayout/General script]
views.get("aynimakina").vw.setTop((int)((views.get("baslikbutton").vw.getTop() + views.get("baslikbutton").vw.getHeight())+Double.parseDouble(_aralik2)));
//BA.debugLineNum = 38;BA.debugLine="CpuIle.Height = menusize"[pingpong2mainlayout/General script]
views.get("cpuile").vw.setHeight((int)(Double.parseDouble(_menusize)));
//BA.debugLineNum = 39;BA.debugLine="CpuIle.Width = Panel1.Width"[pingpong2mainlayout/General script]
views.get("cpuile").vw.setWidth((int)((views.get("panel1").vw.getWidth())));
//BA.debugLineNum = 40;BA.debugLine="CpuIle.left = 0%x"[pingpong2mainlayout/General script]
views.get("cpuile").vw.setLeft((int)((0d / 100 * width)));
//BA.debugLineNum = 41;BA.debugLine="CpuIle.Right = 100%x"[pingpong2mainlayout/General script]
views.get("cpuile").vw.setLeft((int)((100d / 100 * width) - (views.get("cpuile").vw.getWidth())));
//BA.debugLineNum = 42;BA.debugLine="CpuIle.top = AyniMakina.Bottom + Aralik2"[pingpong2mainlayout/General script]
views.get("cpuile").vw.setTop((int)((views.get("aynimakina").vw.getTop() + views.get("aynimakina").vw.getHeight())+Double.parseDouble(_aralik2)));
//BA.debugLineNum = 45;BA.debugLine="CpuCpu.Height = menusize"[pingpong2mainlayout/General script]
views.get("cpucpu").vw.setHeight((int)(Double.parseDouble(_menusize)));
//BA.debugLineNum = 46;BA.debugLine="CpuCpu.Width = Panel1.Width"[pingpong2mainlayout/General script]
views.get("cpucpu").vw.setWidth((int)((views.get("panel1").vw.getWidth())));
//BA.debugLineNum = 47;BA.debugLine="CpuCpu.left = 0%x"[pingpong2mainlayout/General script]
views.get("cpucpu").vw.setLeft((int)((0d / 100 * width)));
//BA.debugLineNum = 48;BA.debugLine="CpuCpu.Right = 100%x"[pingpong2mainlayout/General script]
views.get("cpucpu").vw.setLeft((int)((100d / 100 * width) - (views.get("cpucpu").vw.getWidth())));
//BA.debugLineNum = 49;BA.debugLine="CpuCpu.top = CpuIle.Bottom + Aralik2"[pingpong2mainlayout/General script]
views.get("cpucpu").vw.setTop((int)((views.get("cpuile").vw.getTop() + views.get("cpuile").vw.getHeight())+Double.parseDouble(_aralik2)));
//BA.debugLineNum = 51;BA.debugLine="CikisButton.Height = menusize"[pingpong2mainlayout/General script]
views.get("cikisbutton").vw.setHeight((int)(Double.parseDouble(_menusize)));
//BA.debugLineNum = 52;BA.debugLine="CikisButton.Width = Panel1.Width"[pingpong2mainlayout/General script]
views.get("cikisbutton").vw.setWidth((int)((views.get("panel1").vw.getWidth())));
//BA.debugLineNum = 53;BA.debugLine="CikisButton.left = 0%x"[pingpong2mainlayout/General script]
views.get("cikisbutton").vw.setLeft((int)((0d / 100 * width)));
//BA.debugLineNum = 54;BA.debugLine="CikisButton.Right = 100%x"[pingpong2mainlayout/General script]
views.get("cikisbutton").vw.setLeft((int)((100d / 100 * width) - (views.get("cikisbutton").vw.getWidth())));
//BA.debugLineNum = 55;BA.debugLine="CikisButton.top = CpuCpu.Bottom + Aralik2"[pingpong2mainlayout/General script]
views.get("cikisbutton").vw.setTop((int)((views.get("cpucpu").vw.getTop() + views.get("cpucpu").vw.getHeight())+Double.parseDouble(_aralik2)));

}
}