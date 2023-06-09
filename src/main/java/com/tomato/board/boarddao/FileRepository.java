package com.tomato.board.boarddao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tomato.board.boardvo.UploadFile;

/**
 * 파일 암호화 
 *
 *
 */
public interface FileRepository extends JpaRepository<UploadFile, Integer> {
 
	public UploadFile findOneByFileName(String fileName);
	
	public UploadFile findOneById(int id);
	
}