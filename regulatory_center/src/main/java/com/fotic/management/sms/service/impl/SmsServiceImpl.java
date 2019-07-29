package com.fotic.management.sms.service.impl;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import javax.swing.event.ListSelectionEvent;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.collections4.ListUtils;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fotic.common.constant.Constant;
import com.fotic.common.httpclient.HttpResult;
import com.fotic.common.httpclient.service.HttpClientService;
import com.fotic.common.quartz.QuartzManager;
import com.fotic.common.quartz.dao.QuartzDao;
import com.fotic.common.timer.TimerTask;
import com.fotic.common.util.PropertiesUtil;
import com.fotic.management.sms.dao.SmsCodeMapper;
import com.fotic.management.sms.dao.SmsModuleMapper;
import com.fotic.management.sms.dao.SmsPrepareSendRecordMapper;
import com.fotic.management.sms.dao.SmsSendDetailMapper;
import com.fotic.management.sms.dao.SmsSettingRuleMapper;
import com.fotic.management.sms.dao.SmsVariablesSettingMapper;
import com.fotic.management.sms.entity.RhzxSubmtSmsTime;
import com.fotic.management.sms.entity.SmsCode;
import com.fotic.management.sms.entity.SmsModule;
import com.fotic.management.sms.entity.SmsPrepareSendRecord;
import com.fotic.management.sms.entity.SmsSendDetail;
import com.fotic.management.sms.entity.SmsSettingRule;
import com.fotic.management.sms.entity.SmsVariablesSetting;
import com.fotic.management.sms.service.ISmsService;
import com.fotic.sms.SmsBody;
import com.fotic.sms.service.SmsSendService;

@Service
public class SmsServiceImpl implements ISmsService {
	private static Logger logger = LoggerFactory.getLogger(TimerTask.class);

	@Autowired
	private HttpClientService httpClientService;
	@Autowired
	private SmsModuleMapper smsModuleMapper;
	@Autowired
	private SmsSettingRuleMapper smsSettingRuleMapper;
	@Autowired
	private SmsVariablesSettingMapper smsVariablesSettingMapper;
	@Autowired
	private SmsSendDetailMapper smsSendDetailMapper;
	@Autowired
	private SmsPrepareSendRecordMapper SmsPrepareSendRecordMapper;
	@Autowired
	private SmsCodeMapper smsCodeMapper;
	@Autowired
	private SmsSendService sendService;
	@Autowired
	private QuartzDao quartzDao;

	private static final String SMS_SERVER_URL = PropertiesUtil
			.get("sms_server_url");
	private static final String CALL_BACK_URL = PropertiesUtil
			.get("call_back_url");
	private static final String PLATFORM = PropertiesUtil.get("platform");

	@Override
	public List<SmsModule> queryModuleList(String createTime,
			String smsModuleType) {
		List<SmsModule> moduleList = null;
		/*
		 * if(PubMethod.isEmpty(createTime)){ moduleList =
		 * smsModuleMapper.queryModuleList(); }else{
		 */
		moduleList = smsModuleMapper.queryModuleListByCondition(createTime,
				smsModuleType);
		// }
		return moduleList;
	}

	@Override
	public List<SmsSendDetail> querySmsSendDetailList(
			SmsSendDetail smsSendDetail) {
		return smsSendDetailMapper.querySmsSendDetail(smsSendDetail);
	}

	@Override
	public SmsSendDetail querySmsSendDetailById(Integer id) {
		return smsSendDetailMapper.querySmsSendDetailById(id);
	}

	@Override
	public List<SmsSendDetail> querySmsSendStatistical(
			SmsSendDetail smsSendDetail) {
		return smsSendDetailMapper.querySmsSendStatistical(smsSendDetail);
	}

	@Override
	@Transactional
	public int updateStatus(Integer moduleId, String status) {
		String statusParam = "";
		if (Constant.SMS_MODULE_NO_OPERATR.equals(status)) {
			statusParam = Constant.SMS_MODULE_ENABLE;
		} else if (Constant.SMS_MODULE_ENABLE.equals(status)) {
			statusParam = Constant.SMS_MODULE_DISABLE;
		} else if (Constant.SMS_MODULE_DISABLE.equals(status)) {
			statusParam = Constant.SMS_MODULE_ENABLE;
		}

		else {
			statusParam = null;
		}
		int i = smsModuleMapper.updateStatus(moduleId, statusParam);
		return i;
	}

	@Override
	public int addSmsModule(SmsModule smsModule,
			List<SmsSettingRule> smsSettingRuleList) {
		smsModuleMapper.addSmsModule(smsModule);
		if (smsSettingRuleList.size() > 0) {
			for (SmsSettingRule smsSettingRule : smsSettingRuleList) {
				smsSettingRule.setModuleId(smsModule.getId());
			}
			smsSettingRuleMapper.addSmsSetting(smsSettingRuleList);
		}
		return smsModule.getId();
	}

	@Override
	public List<SmsVariablesSetting> querySmsVariablesSettingList() {
		List<SmsVariablesSetting> smsVariablesSettingList = smsVariablesSettingMapper
				.querySmsVariablesSettingList();
		return smsVariablesSettingList;
	}

	@Override
	public SmsModule querySmsModuleById(Integer id) {
		SmsModule smsModule = smsModuleMapper.getEntityById(id);
		return smsModule;
	}

	@Override
	public List<SmsSettingRule> querySmsSettingRulesByModuleId(Integer moduleId) {
		return smsSettingRuleMapper.querySmsSettingRules(moduleId);
	}

	@Override
	public int saveSmsPrepareSendRecord(
			SmsPrepareSendRecord smsPrepareSendRecord) {
		return SmsPrepareSendRecordMapper
				.saveSmsPrepareSendRecord(smsPrepareSendRecord);
	}

	@Override
	public void editModule(SmsModule smsModule,
			List<SmsSettingRule> smsSettingRuleList) {
		smsModule.setUpdateTime(new Date());

		smsSettingRuleMapper.deleteEntity(smsModule.getId());
		if (smsSettingRuleList.size() > 0) {
			for (SmsSettingRule smsSettingRule : smsSettingRuleList) {
				smsSettingRule.setModuleId(smsModule.getId());
			}
			smsSettingRuleMapper.addSmsSetting(smsSettingRuleList);
		}

		smsModuleMapper.editModule(smsModule);
	}

	@Override
	public void saveSmsSendDetail(List<SmsSendDetail> smsSendDetailList) {
		smsSendDetailMapper.insertEntity(smsSendDetailList);
		;
	}

	@Override
	public SmsCode querySmsCodeDetailByCode(String smsCode) {
		SmsCode codeDetail = smsCodeMapper.querySmsCodeDetailByCode(smsCode);
		return codeDetail;
	}

	@Override
	public void updateSmssenddetailOfSmscodeAndSendstatusByUuid(
			List<SmsSendDetail> ssds) {

		for (SmsSendDetail ssd : ssds) {
			smsSendDetailMapper
					.editSmsSendDetailForSmscodeAndRepeatSendCountByUUID(
							ssd.getSmsCode(), ssd.getRepeatSendCount(),
							ssd.getUuid());
		}
	}

	@Override
	public void sendSms() {
		try {
			List<SmsSendDetail> list = this.queryOverdueUserInfo();
			logger.info("-------------------------: " + list.size());
			JSONArray requestParams = new JSONArray(list.size());
			for (SmsSendDetail ssd : list) {
				this.settingMoreProperty(ssd);
				JSONObject object = this.convertSmsSendDetailToJsonObj(ssd,
						CALL_BACK_URL);
				requestParams.add(object);
				System.out.println(ssd.getCredNo());
			}

			if (requestParams.size() > 0) {
				// 发送消息到短信平台
				HttpResult repponse = httpClientService.doPostJson(
						SMS_SERVER_URL, requestParams.toString());
				// 持久化发送中的短信详细
				if (repponse.getStatus() == 200
						&& Boolean.parseBoolean(repponse.getData())) {
					//大数据量是为避免持久化实时间过长 导致持久化失败的情况，故做切片处理
					List<List<SmsSendDetail>> listNew = ListUtils.partition(list, 500);
					for (int i = 0; i < listNew.size(); i++) {
						smsSendDetailMapper.insertEntity(listNew.get(i));
					}
					//smsSendDetailMapper.insertEntity(list);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询逾期用户的数据 短信功能关闭期间的不做同步
	 * 
	 * @return
	 * @throws ParseException
	 */
	private List<SmsSendDetail> queryOverdueUserInfo() throws ParseException {
		LocalDateTime now = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String nowStr = formatter.format(now);
		String theDayBefore = formatter.format(now.minusHours(24L));

		List<SmsSendDetail> listSendDetails = null;
		/*
		 * //查询任务关闭与开启时间 List<Map<String, Object>> list = quartzDao.jobList();
		 * 
		 * String startDate =(String) list.get(0).get("STARTDATE"); String
		 * stopDate =(String) list.get(0).get("STOPDATE"); Date nowStrDate =
		 * format.parse(nowStr); Date nowStartDate = format.parse(startDate);
		 * Date nowStopDate = format.parse(stopDate); Date nowTheDayBefore =
		 * format.parse(theDayBefore); if (nowStartDate.before(nowStrDate) &&
		 * nowStartDate.after(nowTheDayBefore)) { startDate = startDate; }else {
		 * startDate = theDayBefore; } if(nowStopDate.before(nowStrDate) &&
		 * nowStopDate.after(nowTheDayBefore)){ stopDate = stopDate ; }else {
		 * stopDate = nowStr; }
		 */

		List<RhzxSubmtSmsTime> list2 = quartzDao.insertJob4(theDayBefore,
				nowStr);
		listSendDetails = smsSendDetailMapper.queryOverdueUserInfo(nowStr,
				theDayBefore);
		Iterator<SmsSendDetail> iterator = listSendDetails.iterator();
		Iterator<RhzxSubmtSmsTime> iteratortime = list2.iterator();
		if (listSendDetails.size() > 0) {

			while (iterator.hasNext()) {
				SmsSendDetail msSendDetail = iterator.next();
				for (int i = 0; i < list2.size(); i++) {

					// RhzxSubmtSmsTime rhzxSubmtSmsTime = iteratortime.next();
					String str = list2.get(i).getStoptime();
					// Date strdateString =
					// listSendDetails.get(i).getInsertDttm();
					// String ttString =
					// listSendDetails.get(6068).getInsertDttm().toString();
					if (str == null) {
						str = nowStr;
					}
					// System.out.println(str+msSendDetail.getInsertDttm());
					// 关闭之后，开启之前
					if (msSendDetail.getInsertDttm().before(format.parse(str))
							&& msSendDetail.getInsertDttm().after(
									format.parse(list2.get(i).getStarttime()))) {
						iterator.remove();
						System.out
								.println("删除成功" + iterator.toString());

					}

				}

			}
		}
		/*
		 * @SuppressWarnings("unchecked") List<SmsSendDetail> listSendDetailsn=
		 * IteratorUtils.toList(iterator);
		 */
		while (iterator.hasNext()) {
			listSendDetails.add(iterator.next());

		}

		return listSendDetails;
		// "2018-04-16 02:15:46", "2018-04-15 02:15:46"

		/*
		 * ArrayList<SmsSendDetail> ssds = new ArrayList<SmsSendDetail>();
		 * SmsSendDetail ssd1 = new SmsSendDetail("001", "001", "001", "DGQ",
		 * "001", "邓国泉你好！", "001", "411121212121", "1861131277", "001", new
		 * Date(), UUID.randomUUID().toString().replaceAll("-", ""));
		 * SmsSendDetail ssd2 = new SmsSendDetail("002", "002", "002", "DGQ2",
		 * "002", "邓国泉你好！", "002", "411121212121", "18611312771", "002", new
		 * Date(), UUID.randomUUID().toString().replaceAll("-", ""));
		 * ssds.add(ssd1); ssds.add(ssd2); return ssds;
		 */
	}

	/**
	 * 给查询到的逾期用户的临时数据初始化一些必要的属性：sendStatus SmsSendContent uuid
	 * 
	 * @param ssd
	 * @return
	 */
	private SmsSendDetail settingMoreProperty(SmsSendDetail ssd) {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		String sendContent = convertContent(ssd);
		ssd.setSmsSendContent(sendContent);
		ssd.setUuid(uuid);
		ssd.setSendDate(new Date());

		return ssd;
	}

	/**
	 * 将业务系统中的短信实体转化可http传输的json字符串
	 * 
	 * @param ssds
	 * @return
	 */
	private JSONObject convertSmsSendDetailToJsonObj(SmsSendDetail ssd,
			String callBackUrl) {
		JSONObject object = new JSONObject();
		object.put("phone", ssd.getPhone());
		object.put("smsContent", ssd.getSmsSendContent());
		object.put("smsModuleType", ssd.getSmsModuleType());
		object.put("sendDate", ssd.getSendDate());
		object.put("uuid", ssd.getUuid());
		object.put("callBackUrl", callBackUrl);
		object.put("platform", PLATFORM);
		object.put("repeatSendCount", 1);
		object.put("repeatRole", "1|2|3|4|7|8:Overlimit！|9|10|15|ERR");
		object.put("account", ssd.getIouNo());
		object.put("coOrgCode", ssd.getCoOrgCode());
		object.put("custName", ssd.getCustName());
		object.put("credNo", ssd.getCredNo());
		object.put("projId", ssd.getProjId());
		return object;
	}

	private static String convertContent(SmsSendDetail sms) {
		return sms
				.getSmsSendContent()
				.replace(
						"#客户姓名#",
						Objects.isNull(sms.getCustName()) ? "【客户姓名】" : sms
								.getCustName())
				.replace(
						"#称呼#",
						Objects.isNull(sms.getGender()) ? "先生（女士）" : sms
								.getGender())
				.replace(
						"#合作机构#",
						Objects.isNull(sms.getCoOrgName()) ? "【合作机构】" : sms
								.getCoOrgName())
				.replace(
						"#合作机构客服电话#",
						Objects.isNull(sms.getPhone()) ? "【合作机构客服电话】" : sms
								.getCoOrgName());
	}

	@Override
	public List<SmsBody> querySmsBodyList(SmsBody SmsBody) {
		// TODO Auto-generated method stub
		return smsSendDetailMapper.querySmsBodyList(SmsBody);
	}

}
