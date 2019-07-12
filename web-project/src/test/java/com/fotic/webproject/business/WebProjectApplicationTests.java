package com.fotic.webproject.business;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;

import com.fotic.webproject.business.entity.StudyInfo;
import com.fotic.webproject.business.repository.StudyInfoRepository;
import com.fotic.webproject.business.service.StudyInfoService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebProjectApplicationTests {

	@Autowired
	private StudyInfoService ss;
	
	@Autowired
	private StudyInfoRepository sir;
	
	private List<StudyInfo> sis;
	
	@Before
	public void init() {
		StudyInfo s = new StudyInfo();
		s.setCreateUser("bianhongjie");
		s.setStudentCount(3);
		s.setBpsStatus(0);
		s.setStudyAddr("asdfasdf");
		s.setDeptId(1L);
		s.setStudyCode("1234");
		s.setAllUserCode("qwe,wer");
		s.setAllUserName("周杰伦, 刘德华");
		s.setStudyOrg("黑马");
		s.setIsSignProtocol(0);
		s.setAllStudentCost(12.0);
		s.setIsPlan(0);
		s.setPerStudentCost(34.0);
		s.setStudyReason("爱学习");
		s.setStudyName("日语");
		
		StudyInfo s1 = new StudyInfo();
		s1.setCreateUser("bianhongjie");
		s1.setStudentCount(3);
		s1.setBpsStatus(0);
		s1.setStudyAddr("asdfasdf");
		s1.setDeptId(1L);
		s1.setStudyCode("1234");
		s1.setAllUserCode("qwe1,wer1");
		s1.setAllUserName("周杰伦1, 刘德华1");
		s1.setStudyOrg("黑马");
		s1.setIsSignProtocol(0);
		s1.setAllStudentCost(12.0);
		s1.setIsPlan(0);
		s1.setPerStudentCost(34.0);
		s1.setStudyReason("爱学习");
		s1.setStudyName("日语");
		
		List<StudyInfo> list = Arrays.asList(s,s1);
		sis = (List<StudyInfo>) ss.insertBatch(list);
	}
	
	@Test
	public void query() {
		//构造查询条件
		StudyInfo s = new StudyInfo();
		s.setAllUserName("周杰伦1, 刘德华1");
		//分页+排序实例
		Sort sort = Sort.by(Direction.ASC, "allUserCode");
		Pageable pageAndOrder = PageRequest.of(0, 3, sort);
		Pageable page = PageRequest.of(0, 3);
		
		//开始测试.................
		//查询所有
		List<StudyInfo> list3 = ss.findAll();
		assertThat(list3.stream().map(o->o.getAllUserCode()).collect(Collectors.toList())).hasSize(9);
		
		//条件查询
		List<StudyInfo> list = ss.findAll(s);
		System.out.println(list.size());
		assertThat(list).hasSize(1);
		
		//条件+分页+排序 查询
		Page<StudyInfo> pageInfo = ss.findAll(s, pageAndOrder);
		assertThat(pageInfo.getContent()).anyMatch(o->{
			return "周杰伦1, 刘德华1".equals(o.getAllUserName());
		});
		assertThat(pageInfo.getContent()).hasSize(1);
		assertThat(pageInfo.getContent().stream().map(o->o.getAllUserCode()).collect(Collectors.toList())).isSorted();
		
		//条件+排序 查询
		List<StudyInfo> list2 = ss.findAll(s, Sort.by(Direction.DESC, "allUserCode"));
		assertThat(list2.stream().map(o->{
			return o.getAllUserCode();
		}).collect(Collectors.toList())).isSortedAccordingTo(new Comparator<String>() {
			public int compare(String o1, String o2) {
				return o1.compareTo(o2) >= 0 ? -1 : 0;
			};
		});
		
		//分页查询
		Page<StudyInfo> page2 = ss.findAll(page);
		assertThat(page2.getContent().stream().map(o->o.getAllUserCode())
				.collect(Collectors.toList()))
				.hasSize(3);
		
		//排序查询
		List<StudyInfo> list4 = ss.findAll(Sort.by(Direction.ASC, "allUserCode"));
		assertThat(list4).hasSize(9);
		assertThat(list4.stream().map(o->o.getAllUserCode())
				.collect(Collectors.toList()))
				.isSorted();
	}
	
	@Test
	public void update() {
		try {
			Optional<StudyInfo> responseEntity = ss.findById(100546L);
			responseEntity.ifPresent(o->{
				o.setAllUserName("习近平20");
				o.setStudyName("AI算法");
				ss.update(o);
			});
			
			Optional<StudyInfo> s1 = sir.findById(100546L);
			s1.ifPresent(o->{
				assertThat(o).hasFieldOrPropertyWithValue("allUserName", "习近平20");
				assertThat(o).hasFieldOrPropertyWithValue("studyName", "AI算法");
			});
			
			StudyInfo studyInfo2 = ss.findById(100545L).get();
			studyInfo2.setAllUserName("马化腾2");
			ss.update(studyInfo2);
			StudyInfo s2 = sir.findById(100545L).get();
			assertThat(s2.getAllUserName()).isEqualTo("马化腾2");
			
			StudyInfo info = new StudyInfo();
			info.setAllUserName("习近平20");
			Optional<StudyInfo> one = ss.findOne(info);
			System.out.println(one);
			assertThat(one.get()).isNotNull();
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	@After
	public void delete() {
		sir.deleteAll(sis);
	}
	
}


