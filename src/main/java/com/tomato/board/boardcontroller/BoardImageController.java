package com.tomato.board.boardcontroller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tomato.board.boardservice.ImageService;
import com.tomato.board.boardutil.MediaUtils;
import com.tomato.board.boardvo.UploadFile;

@RestController
@RequestMapping("/board")
public class BoardImageController {
	
	@Autowired
	ImageService imageService;
	
	
	 /**
     * 이미지 업로드 
     * @param fileId
     * @return
     */
	@GetMapping("/image.do/{fileId}")
    @ResponseBody
    public ResponseEntity<?> serveFile(@PathVariable int fileId) {
        
		try {
            UploadFile uploadedFile = imageService.load(fileId);
            HttpHeaders headers = new HttpHeaders();
            
            String fileName = uploadedFile.getFileName();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");

            if (MediaUtils.containsImageMediaType(uploadedFile.getContentType())) {
                headers.setContentType(MediaType.valueOf(uploadedFile.getContentType()));
            } else {
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            }

            Resource resource = imageService.loadAsResource(uploadedFile.getSaveFileName());
            return ResponseEntity.ok().headers(headers).body(resource);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
		
    }
	
	
	/**
	 * 이미지 첨부 
	 * @param file
	 * @param request
	 * @return
	 */
	@PostMapping("/image.do")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
        
		try {
            UploadFile uploadedFile = imageService.store(file);
            return ResponseEntity.ok().body(request.getContextPath()+"/board/image.do/" + uploadedFile.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

}
