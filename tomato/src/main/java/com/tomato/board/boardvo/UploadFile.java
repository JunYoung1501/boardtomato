package com.tomato.board.boardvo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class UploadFile {
    
	/*아이디 */
    @Id
    @GeneratedValue
    int id;
    
    /* 파일 이름*/
    @Column
    String fileName;
    
    /*파일 저장 이름 */
    @Column
    String saveFileName;
    
    /* 파일 수정 */
    @Column
    String filePath;
    
    /* 파일 타입 ex ) png gif ..*/
    @Column
    String contentType;
    
    /* 파일 사이즈 */
    @Column
    long fileSize;
    
    Date regDate;
}
