package com.netconnection.action;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;
import org.apache.struts2.ServletActionContext;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.netconnection.entity.Pcinfo;
import com.netconnection.service.ExportReportService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author penicillus 
*根据关系表中的记录，在相应的表中设置相应的checked；
*取得list后，将list返回前台页面
 */
@SuppressWarnings("serial")
public class ExportReportAction extends ActionSupport{
	private String page;
	private String rows;
	private String inputPath;
	private JSONObject resultObj ;
	private ExportReportService exportReportService;

	@SuppressWarnings("unchecked")
	public String execute(){
		return "success";
	}
	
	public String showExportSoftReport(){
		int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        int number = Integer.parseInt((rows == null || rows == "0") ? "30":rows);  
        int start = (intPage-1)*number;
		List softReport = exportReportService.findSoftByPaging(start, number);
		Map<String,Object> obj = new HashMap<String,Object>();
		obj.put("total", exportReportService.findAll().size());
		obj.put("rows", softReport);
		this.setResultObj(JSONObject.fromObject(obj));
		return "success"; 
	}
	public String showUnclosedReport(){
		int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        int number = Integer.parseInt((rows == null || rows == "0") ? "30":rows);  
        int start = (intPage-1)*number;
        //获得softlist 联合查询出软件列表
		List softReport = exportReportService.findUnclosedByPaging(start, number);
		Map<String,Object> obj = new HashMap<String,Object>();
		obj.put("total", exportReportService.findAll().size());
		obj.put("rows", softReport);
		this.setResultObj(JSONObject.fromObject(obj));
		return "success"; 
	}
	public String showInstallReport(){
		int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        int number = Integer.parseInt((rows == null || rows == "0") ? "30":rows);  
        int start = (intPage-1)*number;
		List softReport = exportReportService.findInstallReport(start, number);
		Map<String,Object> obj = new HashMap<String,Object>();
		obj.put("total", exportReportService.findAll().size());
		obj.put("rows", softReport);
		this.setResultObj(JSONObject.fromObject(obj));
		return "success"; 
	}
	public String showIllegalAceessReport(){
		int intPage = Integer.parseInt((page == null || page == "0") ? "1":page);  
        int number = Integer.parseInt((rows == null || rows == "0") ? "30":rows);  
        int start = (intPage-1)*number;
		List softReport = exportReportService.findIllegalAceessReport(start, number);
		Map<String,Object> obj = new HashMap<String,Object>();
		obj.put("total", exportReportService.findAll().size());
		obj.put("rows", softReport);
		this.setResultObj(JSONObject.fromObject(obj));
		return "success"; 
	}
	@SuppressWarnings("deprecation")
	public String exportSoftReport() throws Exception{
		 	Date now = new Date();
			long longDate = now.getTime();	
		    String fileName=Long.toString(longDate)+".xls"; //    
	        String realpath = ServletActionContext.getServletContext().getRealPath("/downloadReport");
			HttpServletRequest request = ServletActionContext.getRequest();
			//只是导出当前页面上的数据
			String jsonStr = request.getParameter("daoChuJsonStr");
			String reportlist= request.getParameter("reportlist");
			System.out.println("这里是打印从前台传来的值"+reportlist);
			JSONArray jarry = JSONArray.fromObject(jsonStr);
			List<Pcinfo> listPcinfo = new ArrayList<Pcinfo>();
			for(int i=0;i<jarry.size();i++)
			 {  
				Pcinfo pcinfo = new Pcinfo();
				if(reportlist.equals("3")){
					JSONObject pcinfoObj = (JSONObject)jarry.get(i);
					pcinfo.setClientname(pcinfoObj.getString("clientname"));
					pcinfo.setId(Integer.parseInt(pcinfoObj.getString("id")));
					pcinfo.setIp(pcinfoObj.getString("ip"));
					pcinfo.setMac(pcinfoObj.getString("mac"));
					pcinfo.setOs(pcinfoObj.getString("os"));
					pcinfo.setItime(pcinfoObj.getString("itime"));
					pcinfo.setFtime(pcinfoObj.getString("ftime"));
					listPcinfo.add(pcinfo);	
				}else{
					JSONObject pcinfoObj = (JSONObject)jarry.get(i);
					pcinfo.setClientname(pcinfoObj.getString("clientname"));
					pcinfo.setId(Integer.parseInt(pcinfoObj.getString("id")));
					pcinfo.setIp(pcinfoObj.getString("ip"));
					pcinfo.setLoadflow(Long.parseLong(pcinfoObj.getString("loadflow")));
					pcinfo.setMac(pcinfoObj.getString("mac"));
					pcinfo.setOnlinestate(Integer.parseInt(pcinfoObj.getString("onlinestate")));
					pcinfo.setOs(pcinfoObj.getString("os"));
					pcinfo.setStatu(pcinfoObj.getString("statu"));
					pcinfo.setSoftlist(pcinfoObj.getString("softlist"));
					pcinfo.setEsoftlist(pcinfoObj.getString("esoftlist"));
					pcinfo.setUpflow(Long.parseLong(pcinfoObj.getString("upflow")));
					pcinfo.setItime(pcinfoObj.getString("itime"));
					pcinfo.setFtime(pcinfoObj.getString("ftime"));
					listPcinfo.add(pcinfo);}
			}
	        //第一步，创建一个webbook，对应一个Excel文件
	        HSSFWorkbook wb = new HSSFWorkbook();
	        //第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
	        HSSFSheet sheet = wb.createSheet("客户端基本信息报表");
	        //第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
	        //设置单元格的行高宽度大小
	        sheet.setDefaultRowHeightInPoints(20);    
	        sheet.setDefaultColumnWidth((short) 20);    
	        
	        HSSFRow row0 = sheet.createRow((int)0);
	        HSSFRow row1 = sheet.createRow((int)1);
	        HSSFRow row = sheet.createRow((int)2);
			//第四步，创建单元格，并设置值表头  设置表头居中
	        HSSFCellStyle style = wb.createCellStyle();
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); //创建一个居中格式
	        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	        
	        HSSFCell cell01 = row0.createCell(0);
	        String title="";
	        if(reportlist.equals("0")){
	        title="客户端基本信息报表"+"("+new Timestamp(System.currentTimeMillis()).toString()+")";
		      cell01.setCellValue(title);cell01.setCellStyle(style);
		      sheet.addMergedRegion(new Region(0, (short) 0, 1, (short) 4));
	        HSSFCell cell = row.createCell((int)0);
	        cell.setCellValue("id"); cell.setCellStyle(style);
	        cell = row.createCell((int)1);
	        cell.setCellValue("用户名"); cell.setCellStyle(style);
	        cell = row.createCell((int)2);
	        cell.setCellValue("ip地址"); cell.setCellStyle(style);
	        cell = row.createCell((int)3);
	        cell.setCellValue("网卡地址"); cell.setCellStyle(style);        
	        cell = row.createCell((int)4);
	        cell.setCellValue("操作系统版本"); cell.setCellStyle(style);
	     

	        for(int i=0;i<listPcinfo.size();i++){
	            row = sheet.createRow((int)i+3);
	            Pcinfo pcinfo =listPcinfo.get(i);
	            //第四步，创建单元格，并设置值
	            cell01=row.createCell((int)0);
	            cell01.setCellValue(pcinfo.getId());
	            cell01.setCellStyle(style);
	            cell01=row.createCell((int)1);
	            cell01.setCellValue(pcinfo.getClientname());
	            cell01.setCellStyle(style);
	            cell01=row.createCell((int)2);
	            cell01.setCellValue(pcinfo.getIp());
	            cell01.setCellStyle(style);
	            cell01=row.createCell((int)3);
	            cell01.setCellValue(pcinfo.getMac());
	            cell01.setCellStyle(style);
	            cell01=row.createCell((int)4);
	            cell01.setCellValue(pcinfo.getOs());
	            cell01.setCellStyle(style);
	        }
	        }
	        else if(reportlist.equals("1")){
		        title="客户端未关机终端报表"+"("+new Timestamp(System.currentTimeMillis()).toString()+")";
			      cell01.setCellValue(title);cell01.setCellStyle(style);
			      sheet.addMergedRegion(new Region(0, (short) 0, 1, (short) 4));
		        HSSFCell cell = row.createCell((int)0);
		        cell.setCellValue("id"); cell.setCellStyle(style);
		        cell = row.createCell((int)1);
		        cell.setCellValue("用户名"); cell.setCellStyle(style);
		        cell = row.createCell((int)2);
		        cell.setCellValue("ip地址"); cell.setCellStyle(style);
		        cell = row.createCell((int)3);
		        cell.setCellValue("网卡地址"); cell.setCellStyle(style);        
		        cell = row.createCell((int)4);
		        cell.setCellValue("软件列表"); cell.setCellStyle(style);
		     

		        for(int i=0;i<listPcinfo.size();i++){
		            row = sheet.createRow((int)i+3);
		            Pcinfo pcinfo =listPcinfo.get(i);
		            //第四步，创建单元格，并设置值
		            cell01=row.createCell((int)0);
		            cell01.setCellValue(pcinfo.getId());
		            cell01.setCellStyle(style);
		            cell01=row.createCell((int)1);
		            cell01.setCellValue(pcinfo.getClientname());
		            cell01.setCellStyle(style);
		            cell01=row.createCell((int)2);
		            cell01.setCellValue(pcinfo.getIp());
		            cell01.setCellStyle(style);
		            cell01=row.createCell((int)3);
		            cell01.setCellValue(pcinfo.getMac());
		            cell01.setCellStyle(style);
		            cell01=row.createCell((int)4);
		            cell01.setCellValue(pcinfo.getSoftlist());
		            cell01.setCellStyle(style);
		        }
	        }
	        else if(reportlist.equals("2")){
		        title="客户端软件安装统计报表"+"("+new Timestamp(System.currentTimeMillis()).toString()+")";
			      cell01.setCellValue(title);cell01.setCellStyle(style);
			      sheet.addMergedRegion(new Region(0, (short) 0, 1, (short) 5));
		        HSSFCell cell = row.createCell((int)0);
		        cell.setCellValue("id"); cell.setCellStyle(style);
		        cell = row.createCell((int)1);
		        cell.setCellValue("用户名"); cell.setCellStyle(style);
		        cell = row.createCell((int)2);
		        cell.setCellValue("ip地址"); cell.setCellStyle(style);
		        cell = row.createCell((int)3);
		        cell.setCellValue("网卡地址"); cell.setCellStyle(style);        
		        cell = row.createCell((int)4);
		        cell.setCellValue("运行的黑名单"); cell.setCellStyle(style);
		        cell = row.createCell((int)5);
		        cell.setCellValue("没有运行的白名单"); cell.setCellStyle(style);

		        for(int i=0;i<listPcinfo.size();i++){
		            row = sheet.createRow((int)i+3);
		            Pcinfo pcinfo =listPcinfo.get(i);
		            //第四步，创建单元格，并设置值
		            cell01=row.createCell((int)0);
		            cell01.setCellValue(pcinfo.getId());
		            cell01.setCellStyle(style);
		            cell01=row.createCell((int)1);
		            cell01.setCellValue(pcinfo.getClientname());
		            cell01.setCellStyle(style);
		            cell01=row.createCell((int)2);
		            cell01.setCellValue(pcinfo.getIp());
		            cell01.setCellStyle(style);
		            cell01=row.createCell((int)3);
		            cell01.setCellValue(pcinfo.getMac());
		            cell01.setCellStyle(style);
		            cell01=row.createCell((int)4);
		            cell01.setCellValue(pcinfo.getSoftlist());
		            cell01.setCellStyle(style);
		            cell01=row.createCell((int)5);
		            cell01.setCellValue(pcinfo.getEsoftlist());
		            cell01.setCellStyle(style);
		        }
	        } else{
		        title="客户端违规统计报表"+"("+new Timestamp(System.currentTimeMillis()).toString()+")";
		        cell01.setCellValue(title);cell01.setCellStyle(style);
			      sheet.addMergedRegion(new Region(0, (short) 0, 1, (short) 6));
		        HSSFCell cell = row.createCell((int)0);
		        cell.setCellValue("id"); cell.setCellStyle(style);
		        cell = row.createCell((int)1);
		        cell.setCellValue("用户名"); cell.setCellStyle(style);
		        cell = row.createCell((int)2);
		        cell.setCellValue("ip地址"); cell.setCellStyle(style);
		        cell = row.createCell((int)3);
		        cell.setCellValue("网卡地址"); cell.setCellStyle(style);        
		        cell = row.createCell((int)4);
		        cell.setCellValue("操作系统版本"); cell.setCellStyle(style);
		        cell = row.createCell((int)5);
		        cell.setCellValue("违规连网时间"); cell.setCellStyle(style);
		        cell = row.createCell((int)6);
		        cell.setCellValue("流量异常时间"); cell.setCellStyle(style);
		        for(int i=0;i<listPcinfo.size();i++){
		            row = sheet.createRow((int)i+3);
		            Pcinfo pcinfo =listPcinfo.get(i);
		            //第四步，创建单元格，并设置值
		            cell01=row.createCell((int)0);
		            cell01.setCellValue(pcinfo.getId());
		            cell01.setCellStyle(style);
		            cell01=row.createCell((int)1);
		            cell01.setCellValue(pcinfo.getClientname());
		            cell01.setCellStyle(style);
		            cell01=row.createCell((int)2);
		            cell01.setCellValue(pcinfo.getIp());
		            cell01.setCellStyle(style);
		            cell01=row.createCell((int)3);
		            cell01.setCellValue(pcinfo.getMac());
		            cell01.setCellStyle(style);
		            cell01=row.createCell((int)4);
		            cell01.setCellValue(pcinfo.getOs());
		            cell01.setCellStyle(style);
		            cell01=row.createCell((int)5);
		            cell01.setCellValue(pcinfo.getItime());
		            cell01.setCellStyle(style);
		            cell01=row.createCell((int)6);
		            cell01.setCellValue(pcinfo.getFtime());
		            cell01.setCellStyle(style);
		        }
		        }
	        //第六步，将文件存到指定位置
	        try {
	        	File file=new File(realpath+"/"+fileName);
	        	if (!file.exists()) {
	        	       file.createNewFile();
	        	       System.err.println(file + "已创建！");
	        	}
	            FileOutputStream fout = new FileOutputStream(realpath+"/"+fileName);
	            System.out.println(realpath);
	            inputPath = "downloadReport"+"/"+fileName;
	            wb.write(fout);
	            fout.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	    	HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			System.out.println(fileName);
			System.out.println(request.getContextPath());
			
			inputPath=request.getContextPath()+"/downloadExcel.action?inputPath="+inputPath;
			System.out.println(inputPath);
			out.print(inputPath);
			out.flush();        
	        return null;
	}
	public InputStream getTargetFile() throws Exception {
		System.out.println("getTargetFile方法调用！！！"+inputPath);
		System.out.println("文件流为："+ServletActionContext.getServletContext().getResourceAsStream(inputPath));
    	return ServletActionContext.getServletContext().getResourceAsStream(inputPath);
		//return is;
    }	
	
	public String downloadExcel(){
		return SUCCESS;
	}
	public String exportSoftInfoReport(){
		return "succcess";
	}
	public String getInputPath() {
		return inputPath;
	}
	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}
	public ExportReportService getExportReportService() {
		return exportReportService;
	}

	public void setExportReportService(ExportReportService exportReportService) {
		this.exportReportService = exportReportService;
	}

	public String getOtherReport(){

		return "success"; 
	}
	
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public JSONObject getResultObj() {
		return resultObj;
	}

	public void setResultObj(JSONObject resultObj) {
		this.resultObj = resultObj;
	}




	
}
