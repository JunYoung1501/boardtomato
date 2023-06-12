package com.tomato.board.boardservice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tomato.board.boarddao.FileRepository;
import com.tomato.board.boardutil.UploadFileUtils;
import com.tomato.board.boardvo.UploadFile;

import lombok.extern.slf4j.Slf4j;


/**
 * 이미지파일 저장 
 * */
@Service
@Slf4j
public class ImageService {
    
    private final Path rootLocation;
    
    @Autowired
    public ImageService(@Value("${imageupload.path}") String uploadPath) {
       
    	log.info("PATH :: " + uploadPath);
        this.rootLocation = Paths.get(uploadPath);
    }
    
    @Autowired
    FileRepository fileRepository;
    
    /**
     * 이미지 업로드 
     * */
    public Stream<Integer> loadAll() {
    	
        List<UploadFile> files = fileRepository.findAll();
        return files.stream().map(file -> file.getId());
    }
    /**
     * 파일 업로드 (아이디)
     * */
    public UploadFile load(int fileId) {
    	
    	return fileRepository.findOneById(fileId);
    }
    
    /**
     * 파일 업로드 (파일 이름)
     * */
    public Resource loadAsResource(String fileName) throws Exception {
       
    	try {
    		
            if (fileName.toCharArray()[0] == '/') {
                fileName = fileName.substring(1);
            }
            
            Path file = loadPath(fileName);
            Resource resource = new UrlResource(file.toUri());
            
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new Exception("Could not read file: " + fileName);
            }
            
        } catch (Exception e) {
            throw new Exception("Could not read file: " + fileName);
        }
    }
    
    private Path loadPath(String fileName) {
        return rootLocation.resolve(fileName);
    }
    
    /**
     * 업로드 파일 저장 
     *  */    
    public UploadFile store(MultipartFile file) throws Exception {
    	
        try {
            if (file.isEmpty()) {
                throw new Exception("Failed to store empty file " + file.getOriginalFilename());
            }
            
            String saveFileName = UploadFileUtils.fileSave(rootLocation.toString(), file);
            
            if (saveFileName.toCharArray()[0] == '/') {
                saveFileName = saveFileName.substring(1);
            }
            
            Resource resource = loadAsResource(saveFileName);
            
            UploadFile saveFile = new UploadFile();
            saveFile.setSaveFileName(saveFileName);
            saveFile.setFileName(file.getOriginalFilename());
            saveFile.setContentType(file.getContentType());
            
            log.info("경로찾기:"+rootLocation);
            String tempPath = rootLocation.toString()
            		.replace(File.separatorChar, '/') + File.separator + saveFileName;
            saveFile.setFilePath(tempPath.replace("\\", "/"));
            log.info("이미지 파일 저장경로:"+saveFile.getFilePath());
            
            saveFile.setFileSize(resource.contentLength());
            saveFile.setRegDate(new Date());
            saveFile = fileRepository.save(saveFile);
            
            return saveFile;
            
        } catch (IOException e) {
            throw new Exception("Failed to store file " + file.getOriginalFilename(), e);
        }
        
    }
}
