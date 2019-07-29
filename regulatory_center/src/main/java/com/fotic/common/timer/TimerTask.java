package com.fotic.common.timer;

public class TimerTask{
	
	/*private static Logger logger = LoggerFactory.getLogger(TimerTask.class);
	
	private SmsSendDetailMapper smsSendDetailMapper;
	
	private HttpClientService httpClientService;
	
	private  static final String SMS_SERVER_URL = "http://localhost:8080/regulatory_center/smsServer/sendSms";
	private static final String CALL_BACK_URL = "http://localhost:8080/regulatory_center/sms/callBack";
	private static final String PLATFORM = "REG";
	
	
	@Scheduled(cron="0 0 18 * * ?")
//	@Scheduled(cron="0/3 * 15 * * ?")
	public void execute(){
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
		
		String nowStr = formatter.format(now);
		String theDayBefore = formatter.format(now.minusHours(24L));
		
//		List<SmsSendDetail> list = smsSendDetailMapper.queryOverdueUserInfo("2018-03-30 02:15:46", "2018-03-29 02:15:46");
		List<SmsSendDetail> list = smsSendDetailMapper.queryOverdueUserInfo(nowStr, theDayBefore);
		
		List<SmsSendDetail> senedSms = new ArrayList<SmsSendDetail>();
		
//		SmsSendDetail sms = list.get(12);
		for (SmsSendDetail sms : list) {
			String sendContent = convertContent(sms);
			String smsCode = SmsUtil.SmsSend(sms.getPhone(), sendContent);
			sms.setSmsCode(smsCode);
			sms.setSmsSendContent(sendContent);
			senedSms.add(sms);
			
			boolean repeatStatus = repeatSend(smsCode, sms, senedSms);
			//无须重发
			if(repeatStatus){
				sms.setRepeatSendStatus(1);
			}
			
		}
		
		smsSendDetailMapper.insertEntity(senedSms);
	}
	
	private static String convertContent(SmsSendDetail sms){
		return sms.getSmsSendContent().replace("#客户姓名#", Objects.isNull(sms.getCustName()) ? "【客户姓名】" : sms.getCustName())
				.replace("#称呼#", Objects.isNull(sms.getGender()) ? "先生（女士）" : sms.getGender())
				.replace("#合作机构#", Objects.isNull(sms.getCoOrgName()) ? "【合作机构】" : sms.getCoOrgName());
	}
	
	*//**
	 * 
	 * @param smsCode 上次发送的返回码
	 * @param sms 上次发送的实体对象
	 * @param senedSms 待持久实体集合
	 * @return false: 重发了， true:不需要重发
	 *//*
	public boolean repeatSend(String smsCode, SmsSendDetail sms, List<SmsSendDetail> senedSms){
		
		//需要重发
		if(!Objects.isNull(smsCode) && (smsCode.equals("1") || smsCode.equals("2") || smsCode.equals("3") || smsCode.equals("4") || smsCode.equals("7") || smsCode.equals("8:Overlimit！") || smsCode.equals("9") || smsCode.equals("10") || smsCode.equals("15") || smsCode.contains("ERR"))){
			SmsSendDetail repeatEntity = new SmsSendDetail();
			smsCode = SmsUtil.SmsSend(sms.getPhone(), sms.getSmsSendContent());
			BeanUtils.copyProperties(sms, repeatEntity);
			repeatEntity.setSmsCode(smsCode);
			repeatEntity.setRepeatSendStatus(1);
			senedSms.add(repeatEntity);
			
			return false;
		}
		return true;
	}
	
	public void sendSms(){
		try {
			List<SmsSendDetail> list = this.queryOverdueUserInfo();
			logger.info("-------------------------: "+list.size());
			JSONArray requestParams = new JSONArray(list.size());
			for (SmsSendDetail ssd : list) {
				this.settingMoreProperty(ssd);
				JSONObject object = this.convertSmsSendDetailToJsonObj(ssd, CALL_BACK_URL);
				requestParams.add(object);
			}
			if(requestParams.size() > 0){
				//发送消息到短信平台
				HttpResult repponse = httpClientService.doPostJson(SMS_SERVER_URL, requestParams.toString());
				//持久化发送中的短信详细
				if(repponse.getStatus() == 200 && Boolean.parseBoolean(repponse.getData())){
					smsSendDetailMapper.insertEntity(list);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	*//**
	 * 查询逾期用户的数据
	 * @return
	 *//*
	public List<SmsSendDetail> queryOverdueUserInfo(){
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
		
		String nowStr = formatter.format(now);
		String theDayBefore = formatter.format(now.minusHours(24L));
		
		//"2018-04-16 02:15:46", "2018-04-15 02:15:46"
		return smsSendDetailMapper.queryOverdueUserInfo(nowStr, theDayBefore);
	}
	
	*//**
	 * 给查询到的逾期用户的临时数据初始化一些必要的属性：sendStatus  SmsSendContent  uuid
	 * @param ssd
	 * @return
	 *//*
	public SmsSendDetail settingMoreProperty(SmsSendDetail ssd){
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		String sendContent = convertContent(ssd);
		ssd.setSendStatus("0"); //0: 发送中  1：发送完成
		ssd.setSmsSendContent(sendContent);
		ssd.setUuid(uuid);
		
		return ssd;
	}
	
	*//**
	 * 将业务系统中的短信实体转化可http传输的json字符串
	 * @param ssds
	 * @return
	 *//*
	public JSONObject convertSmsSendDetailToJsonObj(SmsSendDetail ssd, String callBackUrl){
		JSONObject object = new JSONObject();
		object.put("phone", ssd.getPhone());
		object.put("smsContent", ssd.getSmsSendContent());
		object.put("sendDate", ssd.getSendDate());
		object.put("uuid", ssd.getUuid());
		object.put("callBackUrl", callBackUrl);
		object.put("platform", PLATFORM);
		return object;
	}*/
}
