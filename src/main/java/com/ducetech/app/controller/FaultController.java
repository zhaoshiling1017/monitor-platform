package com.ducetech.app.controller;

import com.ducetech.app.model.Dictionary;
import com.ducetech.app.model.Fault;
import com.ducetech.app.service.DictionaryService;
import com.ducetech.app.service.FaultService;
import com.ducetech.framework.controller.BaseController;
import com.ducetech.framework.model.BaseQuery;
import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.util.DateUtil;
import com.ducetech.framework.web.view.OperationResult;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lenzhao on 17-4-25.
 */
@Controller
public class FaultController extends BaseController {

    @Autowired
    private FaultService faultService;
    @Autowired
    private DictionaryService dictionaryService;

    private static final Logger LOG = LoggerFactory.getLogger(FaultController.class);

    @RequestMapping(value = "/faults/index", method = RequestMethod.GET)
    public String index(Model model) {
        List<Dictionary> dics = dictionaryService.queryAll();
        model.addAttribute("dics", dics);
        String nodes = dictionaryService.createTree();
        model.addAttribute("nodes", nodes);
        return "fault/index";
    }

    @RequestMapping(value = "/faults/{id}", method = RequestMethod.GET)
    public String show(@PathVariable(value = "id") String faultId, Model model) {
        Fault fault = faultService.queryById(faultId);
        fault.setDic(dictionaryService.findByCode(fault.getType()));
        model.addAttribute("fault", fault);
        return "fault/show";
    }

    @RequestMapping(value = "/faults", method = RequestMethod.GET)
    @ResponseBody
    public PagerRS<Fault> faultData(HttpServletRequest request) throws Exception {
        BaseQuery<Fault> query = Fault.getSearchCondition(Fault.class, request);
        PagerRS<Fault> rs = faultService.getFaultByPager(query);
        return rs;
    }

    @RequestMapping(value = "/faults/{id}/updateFaultType", method = RequestMethod.PUT)
    @ResponseBody
    public OperationResult updateFaultType(@PathVariable(value = "id") String faultId, String type) {
        faultService.updateFaultType(faultId, type);
        return OperationResult.buildSuccessResult("缺陷类型更新成功", 1);
    }

    @RequestMapping(value = "/printFault", method = RequestMethod.GET)
    public void printFault(Fault faultVO, HttpServletResponse response) {
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("缺陷表格");

        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        Row titleRow = sheet.createRow(0);
        Cell titleCell0 = titleRow.createCell(0);
        titleCell0.setCellStyle(style);
        titleCell0.setCellValue("序号");

        Cell titleCell1 = titleRow.createCell(1);
        titleCell1.setCellStyle(style);
        titleCell1.setCellValue("线路");

        Cell titleCell2 = titleRow.createCell(2);
        titleCell2.setCellStyle(style);
        titleCell2.setCellValue("车号");

        Cell titleCell3 = titleRow.createCell(3);
        titleCell3.setCellStyle(style);
        titleCell3.setCellValue("弓位置");

        Cell titleCell4 = titleRow.createCell(4);
        titleCell4.setCellStyle(style);
        titleCell4.setCellValue("缺陷类型");

        Cell titleCell5 = titleRow.createCell(5);
        titleCell5.setCellStyle(style);
        titleCell5.setCellValue("缺陷级别");

        Cell titleCell6 = titleRow.createCell(6);
        titleCell6.setCellStyle(style);
        titleCell6.setCellValue("缺陷信息");

        Cell titleCell7 = titleRow.createCell(7);
        titleCell7.setCellStyle(style);
        titleCell7.setCellValue("创建时间");

        List<Fault> faultList = faultService.getFaults(faultVO);

        for (int i = 0; i < faultList.size(); i++) {
            Fault fault = faultList.get(i);
            Row row = sheet.createRow(i + 1);

            Cell cell0 = row.createCell(0);
            cell0.setCellStyle(style);
            cell0.setCellValue(i + 1);

            Cell cell1 = row.createCell(1);
            cell1.setCellStyle(style);
            cell1.setCellValue(fault.getLine());

            Cell cell2 = row.createCell(2);
            cell2.setCellStyle(style);
            cell2.setCellValue(fault.getCarNumber());

            Cell cell3 = row.createCell(3);
            cell3.setCellStyle(style);
            cell3.setCellValue(fault.getBowPosition());

            Cell cell4 = row.createCell(4);
            cell4.setCellStyle(style);
            cell4.setCellValue(fault.getDic().getNodeName());

            Cell cell5 = row.createCell(5);
            cell5.setCellStyle(style);
            cell5.setCellValue(fault.getInterval());

            Cell cell6 = row.createCell(6);
            cell6.setCellStyle(style);
            cell6.setCellValue(fault.getFaultInfo());

            Cell cell7 = row.createCell(7);
            cell7.setCellStyle(style);
            cell7.setCellValue(DateUtil.formatDate(fault.getCreatedAt(), DateUtil.DEFAULT_TIME_FORMAT));
        }

        for (int j = 0; j < 8; j++) {
            sheet.autoSizeColumn(j, true);
        }

        OutputStream os = null;
        try {
            String fileName = format.format(new Date()) + ".xls";
            os = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            wb.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
