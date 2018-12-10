package com.ly.education.atomic.student.status.server.mapper.rollMagent;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ly.education.atomic.student.status.api.entity.rollMagent.StuPhotoEntity;
import com.ly.education.common.UUID.UUIDStringGenerator;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StuPhotoDao {
	@Value("${spring.datasource.url}")
	private String url;
	
	@Value("${spring.datasource.username}")
	private String username;
	
	@Value("${spring.datasource.password}")
	private String password;
	
	@Autowired
	private UUIDStringGenerator uuid;
	
	
	public void deleteStuPhotoList(List<String> idList) {
		Connection conn = null;
		PreparedStatement pst = null;
		
		try {
			conn = DriverManager.getConnection(url, username, password);
			conn.setAutoCommit(false); 
			StringBuffer buffer = new StringBuffer();
			for(String id : idList) {
				buffer.append("'"+id+"'").append(",");
			}
			pst = conn.prepareStatement("delete from T_JW_XJGL_XSZPXX20180917 where ZP_ID in (?)");
			pst.setString(1, buffer.substring(0, buffer.length()-1));
			
			pst.execute();
			
		}catch(Exception e) {
			log.error("批量插入失败", e);
		} finally {
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					log.error("关闭JDBC预处理器失败", e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					log.error("关闭JDBC连接失败", e);
				}
			}
		}
	}
	
	public int insertOrUpdateStuPhotoList(List<StuPhotoEntity> stuPhotoEntityList) {
		
		long startTime = System.currentTimeMillis();
		
		int count = 0;
		Connection conn = null;
		PreparedStatement pst1 = null;
		PreparedStatement pst2 = null;
		
		try {
			conn = DriverManager.getConnection(url, username, password);
			conn.setAutoCommit(false); 
			// 构造预处理statement 
			pst1 = conn.prepareStatement("insert into T_JW_XJGL_XSZPXX20180917(ZP_ID, XH, ZPLX, ZPMC, ZPNR, CJR, CJRXM, CJSJ, ZHXGR, ZHXGRXM, ZHXGSJ) values (?,?,?,?,?,?,?,?,?,?,?)");
			pst2 = conn.prepareStatement("update T_JW_XJGL_XSZPXX20180917 set ZPMC=?,ZPNR=?,ZHXGR=?,ZHXGRXM=?,ZHXGSJ=? where ZP_ID=?");
			int size = stuPhotoEntityList.size();
			for(int i = 0; i < stuPhotoEntityList.size(); i++){
				StuPhotoEntity stuPhoto = stuPhotoEntityList.get(i);
				if(stuPhoto.getId() == null) {
					stuPhoto.setId(uuid.nextUUID());
					pst1.setString(1, stuPhoto.getId());   
					pst1.setString(2, stuPhoto.getStuNumber());
					pst1.setString(3, stuPhoto.getPhotoType());
					pst1.setString(4, stuPhoto.getPhotoName());
					pst1.setBytes(5, stuPhoto.getPhotoContent());
					pst1.setString(6, stuPhoto.getCreator());
					pst1.setString(7, stuPhoto.getCreatorName());
					pst1.setDate(8, new Date(stuPhoto.getCreateTime().getTime()));
					pst1.setString(9, stuPhoto.getEditor());
					pst1.setString(10, stuPhoto.getEditorName());
					pst1.setDate(11, new Date(stuPhoto.getEditeTime().getTime()));
					
					pst1.addBatch();  
				}
				else {
					pst2.setString(1, stuPhoto.getPhotoName());
					pst2.setBytes(2, stuPhoto.getPhotoContent());
					pst2.setString(3, stuPhoto.getEditor());
					pst2.setString(4, stuPhoto.getEditorName());
					pst2.setDate(5, new Date(stuPhoto.getEditeTime().getTime()));
					pst2.setString(6, stuPhoto.getId());
					
					pst2.addBatch();
				}
				if (i == size-1) {
					int[] ret1 = pst1.executeBatch();  
					int[] ret2 = pst2.executeBatch();  
					for (int j : ret1) {
						count += j;
					}
					for (int j : ret2) {
						count += j;
					}
					conn.commit();   
					pst1.clearBatch();
					pst2.clearBatch();
				}
			}
			
		} catch(Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				log.error("回滚事物失败", e1);
			}
			log.error("批量插入失败", e);
		} finally {
			if (pst1 != null) {
				try {
					pst1.close();
				} catch (SQLException e) {
					log.error("关闭JDBC预处理器失败", e);
				}
			}
			if (pst2 != null) {
				try {
					pst2.close();
				} catch (SQLException e) {
					log.error("关闭JDBC预处理器失败", e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					log.error("关闭JDBC连接失败", e);
				}
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("-------插入时间: "+(endTime-startTime)/1000+" --------");
		return count;
	}
}
