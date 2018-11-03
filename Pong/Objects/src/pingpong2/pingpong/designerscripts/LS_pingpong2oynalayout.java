package pingpong2.pingpong.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_pingpong2oynalayout{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("panel1").vw.setHeight((int)((100d / 100 * height)));
views.get("panel1").vw.setWidth((int)((100d / 100 * width)));
views.get("label1").vw.setLeft((int)((50d / 100 * width) - (views.get("label1").vw.getWidth() / 2)));
views.get("label1").vw.setTop((int)((2d / 100 * height)));
views.get("player1skorlabel").vw.setLeft((int)((views.get("label1").vw.getLeft()) - (views.get("player1skorlabel").vw.getWidth())));
views.get("player1skorlabel").vw.setTop((int)((2d / 100 * height)));
views.get("player2skorlabel").vw.setLeft((int)((views.get("label1").vw.getLeft() + views.get("label1").vw.getWidth())));
views.get("player2skorlabel").vw.setTop((int)((2d / 100 * height)));
views.get("label2").vw.setLeft((int)((50d / 100 * width) - (views.get("label2").vw.getWidth() / 2)));
views.get("label2").vw.setTop((int)((99d / 100 * height) - (views.get("label2").vw.getHeight())));

}
}