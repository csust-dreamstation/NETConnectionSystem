package com.netconnection.service.impl;
import java.util.List;

import com.netconnection.dao.ExportReportManage;
import com.netconnection.service.ExportReportService;
public class ExportReportServiceImpl implements ExportReportService {
	private ExportReportManage  exportReportManage;

	public ExportReportManage getExportReportManage() {
		return exportReportManage;
	}

	public void setExportReportManage(ExportReportManage exportReportManage) {
		this.exportReportManage = exportReportManage;
	}

	@Override
	public List findSoftByPaging(int start, int number) {
		return exportReportManage.findSoftByPaging(start,number);
	}

	@Override
	public List findUnclosedByPaging(int start, int number) {
		return exportReportManage.findUnclosedByPaging(start,number);
	}

	@Override
	public List findInstallReport(int start, int number) {
		return exportReportManage.findInstallReport(start,number);
	}

	@Override
	public List findAll() {
		return exportReportManage.findAll();
	}

	@Override
	public List findIllegalAceessReport(int start, int number) {
		return exportReportManage.findIllegalAceessReport(start,number);
	}

}
