package com.netconnection.dao;

import java.util.List;

public interface ExportReportManage {

	List findSoftByPaging(int start, int number);

	List findUnclosedByPaging(int start, int number);

	List findInstallReport(int start, int number);

	List findAll();

	List findIllegalAceessReport(int start, int number);

}
