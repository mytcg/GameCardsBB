package net.mytcg.fhm.ui.custom;

import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import net.mytcg.fhm.http.ConnectionGet;
import net.mytcg.fhm.util.Const;
import net.mytcg.fhm.util.SettingsBean;
import net.rim.device.api.math.Fixed32;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.GaugeField;
import net.rim.device.api.ui.component.NullField;
import net.rim.device.api.ui.container.HorizontalFieldManager;

public class HorizontalGamePlayManager extends HorizontalFieldManager
{
	//Bitmap img = Const.getGrey();
	private String url;
	private String file;
	public Bitmap image;
	private GaugeField progress;
	
	int nStatusHeight = 0;
	int nTitleHeight = 0;
	boolean useall = true;
	public boolean drawbox = false;
	String color = "";
	
	public HorizontalGamePlayManager(boolean useall)
	{
		super(HorizontalFieldManager.USE_ALL_WIDTH | Manager.FOCUSABLE | HorizontalFieldManager.FIELD_RIGHT);
		this.useall = useall;
		
		add(new NullField());
	}
	protected boolean navigationMovement(int dx, int dy, int status, int time)
    {
	        this.invalidate(); 
	        return super.navigationMovement(dx, dy, status, time);
	}
	public int getPreferredHeight() {
		if ((Const.getPortrait())) {
			return (Const.getHeight()-Const.getButtonCentre().getHeight())/2-17;
		}else
		return (Const.getHeight()-Const.getButtonCentre().getHeight());
	}
	public int getPreferredWidth() {
		if ((Const.getPortrait())) {
			return Const.getWidth()-17;
		}else return Const.getWidth()/2;
	}
	
	public HorizontalGamePlayManager()
	{
		this(true);
	}
	public void setImage(Bitmap image){
		this.image = image;
	}
	public void draw(boolean draw,String color){
		drawbox = draw;
		this.color = color;
		this.invalidate();
	}
	public void paint(Graphics g)
	{
		int xPts[] = {0,0,getPreferredWidth(),getPreferredWidth()};
		int yPts[] = {0,getPreferredHeight(),getPreferredHeight(),0};
		g.setColor(15792383);
		g.drawFilledPath(xPts, yPts, null, null);
		//g.drawTexturedPath(xPts,yPts,null,null,0,0,Fixed32.ONE,0,0,Fixed32.ONE,img);
		
		//g.drawBitmap(((getPreferredWidth()-(image.getWidth()))/2), (((getPreferredHeight())-((image.getHeight())))/2), image.getWidth(), image.getHeight(), image, 0, 0);
		g.drawBitmap(5, 5, image.getWidth(), image.getHeight(), image, 0, 0);
		if(drawbox){
			if(color.equals("RED")){
				g.setColor(65536*144);
			}else if(color.equals("GREEN")){
				g.setColor(256*144);
			}else if(color.equals("YELLOW")){
				g.setColor(65536*144+256*144);
			}
			g.drawRect(5, 5, image.getWidth(), image.getHeight());
		}
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
		this.url = url;
		if ((url != null)&&(url.length() > 0)){
			file = url.substring(url.indexOf(Const.cardsbb)+Const.cardsbb_length, url.indexOf(Const.jpeg));
		}
		construct();
	}
	public void construct() {
		int font = Const.FONT;
		image = Const.getThumbLoading();
		FileConnection _file = null;
		InputStream input = null;
		try {
			SettingsBean _instance = SettingsBean.getSettings();
			_file = (FileConnection)Connector.open(Const.getStorage()+Const.PREFIX+_instance.loadingflip);
			input = _file.openInputStream();
			byte[] data = new byte[(int) _file.fileSize()];
			input.read(data);
			input.close();
			_file.close();
			image = (EncodedImage.createEncodedImage(data, 0, data.length)).getBitmap();
			landscape();
			invalidate();
		} catch (Exception e) {
		}
		if (file != null) {
			getData();
		}
		Font _font = getFont();
		_font = _font.derive(Const.TYPE,font);
		setFont(_font);
	}
	public void getData() {
		FileConnection _file = null;
		InputStream input = null;
		try {
			_file = (FileConnection)Connector.open(Const.getStorage()+Const.PREFIX+file);
			if (!_file.exists()) {
				_file.close();
				progress = new GaugeField(null,0,100,0,GaugeField.NO_TEXT);
				synchronized(UiApplication.getEventLock()) {
					this.add(progress);
				}
				doConnect(url);
			} else {
				input = _file.openInputStream();
				byte[] data = new byte[(int) _file.fileSize()];
				input.read(data);
				input.close();
				_file.close();
				image = (EncodedImage.createEncodedImage(data, 0, data.length)).getBitmap();
				//Bitmap temp = (EncodedImage.createEncodedImage(data, 0, data.length)).getBitmap();
				//if ((Const.getPortrait())) {
				//	image = Const.getScaledBitmapImage((EncodedImage.createEncodedImage(data, 0, data.length)),((double)(getPreferredHeight()-20)/temp.getWidth()),((double)(getPreferredHeight()-20)/temp.getWidth()));
				//}else{
				//	image = Const.getScaledBitmapImage((EncodedImage.createEncodedImage(data, 0, data.length)),((double)(getPreferredWidth()-20)/temp.getWidth()),((double)(getPreferredHeight()-20)/temp.getHeight()));
				//}
				landscape();
				invalidate();
			}
		} catch (Exception e) {}
	}
	ConnectionGet cG;
	int timeout = 0;
	public void doConnect(String url) {
		cG = new ConnectionGet(url, this, progress);
		cG.start();
	}
	public void landscape() {
		if ((Const.getPortrait())) {
			try {
				image = Const.rotate(image, 180);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	public void process(byte[] data) {
		//Bitmap temp = (EncodedImage.createEncodedImage(data, 0, data.length)).getBitmap();
		//if ((Const.getPortrait())) {
		//	image = Const.getScaledBitmapImage((EncodedImage.createEncodedImage(data, 0, data.length)),((double)(getPreferredHeight()-20)/temp.getWidth()),((double)(getPreferredHeight()-20)/temp.getWidth()));
		//}else{
		//	image = Const.getScaledBitmapImage((EncodedImage.createEncodedImage(data, 0, data.length)),((double)(getPreferredWidth()-20)/temp.getWidth()),((double)(getPreferredHeight()-20)/temp.getHeight()));
		//}
		synchronized(UiApplication.getEventLock()) {
			try{
				this.delete(progress);
			}catch(Exception e){}
		}
		image = (EncodedImage.createEncodedImage(data, 0, data.length)).getBitmap();
		landscape();
		sublayout(0, 0);
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
            	StatField sField = (StatField) field;
            	setPositionChild(field,((14)/2)+sField.stat.getTop()*image.getWidth()/350, ((10)/2)+(250 - sField.stat.getLeft() - sField.stat.getWidth())*image.getHeight()/250);  //set the position for the field
            	layoutChild( field, sField.stat.getHeight()*image.getHeight()/250, sField.stat.getWidth()*image.getWidth()/350); //lay out the field
            	sField.setImage(image);
            }else if(field instanceof GaugeField){
            	setPositionChild(field, 15, (getPreferredHeight()/2));  //set the position for the field
            	layoutChild( field, (getPreferredWidth()-60), 100 ); //lay out the field
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