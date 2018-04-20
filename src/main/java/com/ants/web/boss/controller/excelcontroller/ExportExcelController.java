package com.ants.web.boss.controller.excelcontroller;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ants.facade.user.service.ExcelFacade;
import com.ants.web.boss.base.BaseController;

/**
 * @ClassName: ExportExcelController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 马高伟
 * @date 2018年4月8日
 * 
 */
public class ExportExcelController extends BaseController {

	@Autowired
	private ExcelFacade excelFacade;

	@RequestMapping("exportExcel")
	public String exportExcel(HttpServletResponse response) {
		try {
			String fileName = new String(("导出excel例子").getBytes(), "ISO8859_1");

			String[] titles = { "商品名", "商品单价", "商品单位" };
			excelFacade.exportExcel("", titles);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
