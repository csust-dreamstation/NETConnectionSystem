package cn.com.thread;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import cn.com.util.ConfigTable;

import com.netconnection.entity.CopyScreen;
import com.netconnection.entity.Illegal;
import com.netconnection.entity.Log;
import com.netconnection.entity.Message;
import com.netconnection.entity.Onlinetime;
import com.netconnection.service.ICopyScreenService;
import com.netconnection.service.ILogService;
import com.netconnection.service.IMessageService;
import com.netconnection.service.IOnlineTimeService;
import com.netconnection.service.IllegalService;
public class DelData extends TimerTask{
    public static final int hour_of_day=1;//设定为每天的几小时删除
    public static final int day_of_week=2;//设定为每周的星期几删除
    public static final int day_of_month=3;//设定为每月的几号删除
    public static final int month_of_year=4;//设定为每年的几月删除
    private ILogService logService;
    private ICopyScreenService copyScreenService;
    private IMessageService messageService;
    private IllegalService illegalService;
    private IOnlineTimeService onlineTimeService;
    /*
     * 定时删除数据库的类型
     *
     */
	public void run() {
		try{
		Calendar c=Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		c.setFirstDayOfWeek(Calendar.MONDAY);
	    switch (ConfigTable.deltype) {
			case hour_of_day:
				if(ConfigTable.hour_of_day==c.get(Calendar.HOUR_OF_DAY)){
					clearData();
				}
				break;
	        case day_of_week:
				if(ConfigTable.day_of_week==c.get(Calendar.DAY_OF_WEEK)&&c.get(Calendar.HOUR_OF_DAY)==0){
					//执行清除数据库方法
					clearData();
				}
	        	break;
	        case day_of_month:
	        	if(ConfigTable.day_of_month-1==c.get(Calendar.DAY_OF_MONTH)&&c.get(Calendar.HOUR_OF_DAY)==1){
	        		//执行清除数据库方法
	        		clearData();
	        	}
	        	else{
	        	int month=c.get(Calendar.MONTH);
	        	c.add(Calendar.DAY_OF_MONTH, 1);
	        	if(c.get(Calendar.MONTH)>month){
	        		clearData();
	        	}
	        	}
	        	break;
	        case month_of_year:
	        	if((ConfigTable.month_of_year-1)==c.get(Calendar.MONTH)&&c.get(Calendar.DAY_OF_MONTH)==1&&c.get(Calendar.HOUR_OF_DAY)==0){
	        		//执行清除数据库方法
	        		clearData();
	        	}
	        	break;
			}
	        clearData(ConfigTable.dely_time_del);
		}catch (Exception e) {
			e.printStackTrace();
		}
	    /*
	     * 注意：每周的第一天是星期天
	     *     月份是从0开始计算，1-12月表示为（0-11），前台取数据时记得减去1
	     *     小时为24小时制，即0-23
	     *     
	     */
	}
	
	private void clearData(int day){
		
		List<Log> ll=logService.findAll();
		for(Log log:ll){
			SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
			try {
				Date d = dateformat.parse(log.getTime());
				long dely=System.currentTimeMillis()-d.getTime();
				if(dely/86400000>day){
					logService.delete(log);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		List<CopyScreen> list = copyScreenService.findRest();
		for(CopyScreen cs:list){
			if((System.currentTimeMillis()-cs.getRecordtime().getTime())/86400000>day){				
				copyScreenService.deleteCopyScreenInfo(cs);
			}
		}
		
		List<Message> lm=messageService.findAll();
		for(Message mes:lm){
			if((System.currentTimeMillis()-mes.getRecordtime().getTime())/86400000>day){				
				messageService.deleteMessage(mes);
			}
		}
		
		List<Illegal> li=illegalService.findAll();
		for(Illegal ie:li){
			if((System.currentTimeMillis()-ie.getRecordtime().getTime())/86400000>day){				
				illegalService.deleteIllegal(ie);
			}
		}
		
		List<Onlinetime> lo=onlineTimeService.findAllOnlineTime();
		for(Onlinetime ot:lo){
			SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");		
			try {
				Date d = dateformat.parse(ot.getBegintime());
				long dely=System.currentTimeMillis()-d.getTime();
				if(dely/86400000>day){
				      onlineTimeService.delonlinetime(ot);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private void clearData(){
		//清空日志
		logService.deleteAllData();
		//清空截屏
		List<CopyScreen> list = copyScreenService.findRest();
		for(CopyScreen cs:list){
			copyScreenService.deleteCopyScreenInfo(cs);
		}
	}

	public ILogService getLogService() {
		return logService;
	}

	public void setLogService(ILogService logService) {
		this.logService = logService;
	}

	public ICopyScreenService getCopyScreenService() {
		return copyScreenService;
	}

	public void setCopyScreenService(ICopyScreenService copyScreenService) {
		this.copyScreenService = copyScreenService;
	}

	public IMessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	public IllegalService getIllegalService() {
		return illegalService;
	}

	public void setIllegalService(IllegalService illegalService) {
		this.illegalService = illegalService;
	}

	public IOnlineTimeService getOnlineTimeService() {
		return onlineTimeService;
	}

	public void setOnlineTimeService(IOnlineTimeService onlineTimeService) {
		this.onlineTimeService = onlineTimeService;
	}
	
	
}
