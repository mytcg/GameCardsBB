package net.mytcg.dev.ui.custom;

import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import net.mytcg.dev.http.ConnectionGet;
import net.mytcg.dev.util.Const;
import net.rim.device.api.math.Fixed32;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.NullField;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class VerticalStatManager extends VerticalFieldManager
{
	Bitmap img = Const.getBackground();
	private String url;
	private String file;
	public Bitmap image;
	
	int nStatusHeight = 0;
	int nTitleHeight = 0;
	boolean useall = true;
	
	public VerticalStatManager(boolean useall)
	{
		super(VerticalFieldManager.USE_ALL_WIDTH | Manager.FOCUSABLE);
		this.useall = useall;
		
		add(new NullField());
	}
	protected boolean navigationMovement(int dx, int dy, int status, int time)
    {
	        this.invalidate(); 
	        return super.navigationMovement(dx, dy, status, time);
	}
	public int getPreferredHeight() {
		//int maxheight = Const.getHeight()-(Const.getButtonCentre().getHeight()+nTitleHeight);
		//if ((maxheight >= super.getPreferredHeight())&&(super.getPreferredHeight() != 0)) {
		//	return maxheight;
		//} else {
		//	return super.getPreferredHeight();
		//}
		//System.out.println("FFFFFFFFF "+(Const.getHeight()-Const.getButtonCentre().getHeight()));
		return Const.getHeight()-Const.getButtonCentre().getHeight();
	}
	public int getPreferredWidth() {
		return Const.getWidth();
	}
	
	public VerticalStatManager()
	{
		this(true);
	}
	public void setImage(Bitmap image){
		this.image = image;
	}
	public void paint(Graphics g)
	{
		int xPts[] = {0,0,getPreferredWidth(),getPreferredWidth()};
		int yPts[] = {0,Const.getHeight(),Const.getHeight(),0};
		g.drawTexturedPath(xPts,yPts,null,null,0,0,Fixed32.ONE,0,0,Fixed32.ONE,img);
		
		g.drawBitmap(((getPreferredWidth()-(image.getWidth()))/2), (((getPreferredHeight())-((image.getHeight())))/2), image.getWidth(), image.getHeight(), image, 0, 0);
		super.paint(g);
	}
	public void setTitleHeight(int height) {
		nTitleHeight = height;
		setExtent();
	}
	public void setStatusHeight(int height)
	{
		nStatusHeight = height;
		setExtent();
	}
	public void setUrl(String url) {
		System.out.println("url " + url);
		this.url = url;
		if ((url != null)&&(url.length() > 0)){
			file = url.substring(url.indexOf(Const.cards)+Const.cards_length, url.indexOf(Const.png));
		}
		System.out.println("construct(); ");
		construct();
	}
	public void construct() {
		int font = Const.FONT;
		image = Const.getLoading();
		landscape();
		if (file != null) {
			getData();
		}
		Font _font = getFont();
		_font = _font.derive(Font.BOLD,font);
		setFont(_font);
	}
	public void getData() {
		FileConnection _file = null;
		InputStream input = null;
		try {
			_file = (FileConnection)Connector.open(Const.getStorage()+Const.PREFIX+file);
			if (!_file.exists()) {
				_file.close();
				doConnect(url);
			} else {
				input = _file.openInputStream();
				byte[] data = new byte[(int) _file.fileSize()];
				input.read(data);
				input.close();
				_file.close();
				image = (EncodedImage.createEncodedImage(data, 0, data.length)).getBitmap();
				landscape();
				invalidate();
			}
		} catch (Exception e) {}
	}
	ConnectionGet cG;
	int timeout = 0;
	public void doConnect(String url) {
		cG = new ConnectionGet(url, this);
		cG.start();
	}
	public void landscape() {
		if (!(Const.getPortrait())) {
			try {
				image = Const.rotate(image, 270);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	public void process(byte[] data) {
		image = (EncodedImage.createEncodedImage(data, 0, data.length)).getBitmap();
		landscape();
		invalidate();
		saveData(data);
	}
	public void saveData(byte[] data) {
		FileConnection _file = null;
		OutputStream output = null;
		try {
			_file = (FileConnection)Connector.open(Const.getStorage()+Const.PREFIX+file);
			_file.create();
			output = _file.openOutputStream();
			output.write(data);
			output.close();
			_file.close();
		} catch (Exception e) {}
	}
	protected void sublayout(int width, int height)
	{
        //super.sublayout(width, height);
		Field field;
        int numberOfFields = getFieldCount();
        for (int i = 0;i < numberOfFields;i++) {
            field = getField(i); //get the field
            if(field instanceof StatField){
            	StatField sField = (StatField)field;
            	setPositionChild(field, ((getPreferredWidth()-(image.getWidth()))/2)+sField.stat.getLeft()*image.getWidth()/250, ((((Const.getHeight()-Const.getButtonCentre().getHeight()))-((image.getHeight())))/2)+sField.stat.getTop()*image.getHeight()/350);  //set the position for the field
            	layoutChild( field, sField.stat.getWidth()*image.getWidth()/250, sField.stat.getHeight()*image.getHeight()/350 ); //lay out the field
            } 	
        }
		setExtent();
	}
	
	private void setExtent()
	{
		setExtent(getPreferredWidth(), getPreferredHeight());
		setVirtualExtent(getPreferredWidth(), super.getPreferredHeight());
	}
}