package com.taotao.portal.service;

import java.util.List;

import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.FloorItem;
import com.taotao.portal.pojo.QuickReportItem;

public interface ContentService {

	List<TbContent> getContentList(long categoryId);
	String getJsonContent(String filePath);
	List<FloorItem> getFloorItemList();
	List<QuickReportItem> getQuickReportItemList();
}
