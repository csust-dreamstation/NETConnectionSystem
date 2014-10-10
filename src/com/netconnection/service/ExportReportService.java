package com.netconnection.service;
import java.util.List;

import com.netconnection.entity.PatchList;
import com.netconnection.entity.SoftList;
/**
 * @author penicillus
 *
 */
public interface ExportReportService {

	List findSoftByPaging(int start, int number);

	List findUnclosedByPaging(int start, int number);

	List findInstallReport(int start, int number);

	List findAll();

	List findIllegalAceessReport(int start, int number);

}
